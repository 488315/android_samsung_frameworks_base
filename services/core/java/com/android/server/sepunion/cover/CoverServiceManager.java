package com.android.server.sepunion.cover;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.SemSystemProperties;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.SparseArray;
import com.samsung.android.cover.CoverState;
import com.samsung.android.cover.ICoverService;
import com.samsung.android.cover.ISViewCoverBaseService;
import com.samsung.android.sepunion.Log;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes3.dex */
public class CoverServiceManager {
  public static final boolean IS_B6_DEVICE;
  public static final int ONEUI_VERSION;
  public static final boolean SUPPORT_FLIP_SUITCASE;
  public Context mContext;
  public final SparseArray mCoverServices;
  public final OnCoverStateProvider mCoverStateProvider;
  public PowerManager.WakeLock mCoverWakeLock;
  public final HandlerC2561H mHandler;
  public boolean mIsUserSwitching;
  public final PowerManager mPowerManager;
  public Runnable mWakeLockRunnable;
  public static final String TAG = "CoverManager_" + CoverServiceManager.class.getSimpleName();
  public static final ComponentName LED_COVER =
      new ComponentName(
          "com.sec.android.cover.ledcover", "com.sec.android.cover.ledcover.LedCoverService");
  public static final ComponentName LED_SUIT =
      new ComponentName(
          "com.samsung.android.ledcasesuit", "com.samsung.android.ledcasesuit.LedCaseSuitService");
  public static final ComponentName SYSTEM_UI_COVER =
      new ComponentName("com.android.systemui", "com.android.systemui.cover.SysUICoverService");
  public static final ComponentName GAMEPACK_COVER =
      new ComponentName(
          "com.sec.android.usb.fancontrol", "com.sec.android.usb.fancontrol.FanControlService");
  public final ArrayList mActiveServices = new ArrayList();
  public final HashMap mBindingTimestamp = new HashMap();
  public final Object mLock = new Object();
  public String[] mBindHistory = new String[10];
  public int mBindHistoryIdx = 0;
  public boolean mRegisterBroadcast = false;
  public BroadcastReceiver mBroadcastReceiver =
      new BroadcastReceiver() { // from class:
                                // com.android.server.sepunion.cover.CoverServiceManager.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
          String schemeSpecificPart;
          boolean updateCoverService;
          String action = intent.getAction();
          Uri data = intent.getData();
          if (data == null || (schemeSpecificPart = data.getSchemeSpecificPart()) == null) {
            return;
          }
          boolean equals = "android.intent.action.PACKAGE_ADDED".equals(action);
          boolean equals2 = "android.intent.action.PACKAGE_CHANGED".equals(action);
          CoverState coverStateFromCoverServiceManager =
              CoverServiceManager.this.mCoverStateProvider.getCoverStateFromCoverServiceManager();
          if (equals || equals2) {
            Bundle extras = intent.getExtras();
            if (equals2
                || (extras != null && extras.getBoolean("android.intent.extra.REPLACING", false))) {
              updateCoverService = CoverServiceManager.this.updateCoverService(schemeSpecificPart);
            } else {
              updateCoverService = CoverServiceManager.this.addCoverService(schemeSpecificPart);
            }
            if (coverStateFromCoverServiceManager.getType() == 13) {
              CoverServiceManager.this.bindCoverService(
                  coverStateFromCoverServiceManager.getType(), false);
            } else if (updateCoverService && coverStateFromCoverServiceManager.getAttachState()) {
              ComponentName coverServiceNameLocked =
                  CoverServiceManager.this.getCoverServiceNameLocked(
                      coverStateFromCoverServiceManager.getType(), true, false);
              if (coverServiceNameLocked != null
                  && CoverServiceManager.this.findActiveServiceByComponentLocked(
                          coverServiceNameLocked)
                      == null
                  && CoverServiceManager.this.containsBindingServiceLocked(
                      coverServiceNameLocked)) {
                CoverServiceManager.this.removeBindingServiceLocked(coverServiceNameLocked);
              }
              CoverServiceManager.this.bindCoverService(
                  coverStateFromCoverServiceManager.getType(), false);
            }
          }
          if ("com.android.systemui".equals(schemeSpecificPart)
              && "android.intent.action.PACKAGE_REPLACED".equals(action)) {
            CoverServiceManager.this.bindCoverService(
                coverStateFromCoverServiceManager.getType(), false);
          }
        }
      };

  public interface OnCoverStateProvider {
    CoverState getCoverStateFromCoverServiceManager();
  }

  static {
    int i = SystemProperties.getInt("ro.build.version.oneui", 0);
    ONEUI_VERSION = i;
    SUPPORT_FLIP_SUITCASE = i >= 60101;
    IS_B6_DEVICE =
        TextUtils.equals("b6q", SemSystemProperties.get("ro.product.vendor.device", "NONE"));
  }

  public CoverServiceManager(
      Context context, Looper looper, OnCoverStateProvider onCoverStateProvider) {
    this.mContext = context;
    this.mCoverStateProvider = onCoverStateProvider;
    this.mHandler = new HandlerC2561H(looper);
    PowerManager powerManager = (PowerManager) context.getSystemService("power");
    this.mPowerManager = powerManager;
    PowerManager.WakeLock newWakeLock = powerManager.newWakeLock(1, "CoverServiceManager");
    this.mCoverWakeLock = newWakeLock;
    newWakeLock.setReferenceCounted(false);
    this.mCoverServices = queryInstalledCoverServices();
  }

  public final void registerBroadcastReceiver(String str) {
    if (this.mRegisterBroadcast) {
      return;
    }
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
    intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
    if (Debug.semIsProductDev()) {
      intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
    }
    intentFilter.addDataScheme("package");
    intentFilter.addDataSchemeSpecificPart(str, 0);
    this.mContext.registerReceiverAsUser(
        this.mBroadcastReceiver, UserHandle.ALL, intentFilter, null, null);
    this.mRegisterBroadcast = true;
  }

  public final void unregisterBroadcastReceiver() {
    if (this.mRegisterBroadcast) {
      this.mContext.unregisterReceiver(this.mBroadcastReceiver);
      this.mRegisterBroadcast = false;
    }
  }

  public void bindCoverService(int i, boolean z) {
    Log.d(TAG, "bindCoverService : type = " + i);
    if (isShouldNotBindCoverService(i, z)) {
      return;
    }
    UserHandle userHandle = UserHandle.SYSTEM;
    ComponentName coverServiceNameLocked = getCoverServiceNameLocked(i, false, true);
    if (coverServiceNameLocked != null) {
      userHandle = new UserHandle(ActivityManager.getCurrentUser());
    } else {
      coverServiceNameLocked = getPredefinedCoverServiceNameLocked(i);
    }
    if (coverServiceNameLocked == null) {
      return;
    }
    if (bindCoverServiceLocked(coverServiceNameLocked, i, userHandle)) {
      registerBroadcastReceiver(coverServiceNameLocked.getPackageName());
    } else if (i == 13) {
      registerBroadcastReceiver("com.sec.android.usb.fancontrol");
      Intent intent = new Intent("com.sec.android.app.applinker.GAME_PACK_ADDED");
      intent.setPackage("com.sec.android.app.applinker");
      this.mContext.sendBroadcast(intent);
      return;
    }
    ComponentName systemUICoverService = getSystemUICoverService(i);
    if (systemUICoverService == null || coverServiceNameLocked.equals(systemUICoverService)) {
      return;
    }
    bindCoverServiceLocked(systemUICoverService, i, UserHandle.SYSTEM);
  }

  public final boolean bindCoverServiceLocked(
      ComponentName componentName, final int i, UserHandle userHandle) {
    if (componentName == null) {
      Log.e(TAG, "bindCoverServiceLocked : component is null");
      return false;
    }
    if (containsBindingServiceLocked(componentName)) {
      return false;
    }
    Intent intent = new Intent();
    intent.setComponent(componentName);
    try {
      ServiceConnection serviceConnection =
          new ServiceConnection() { // from class:
                                    // com.android.server.sepunion.cover.CoverServiceManager.2
            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName2, IBinder iBinder) {
              CoverServiceInfo semCoverServiceInfo;
              Log.d(
                  CoverServiceManager.TAG,
                  "onServiceConnected : name = " + componentName2 + ", binder = " + iBinder);
              synchronized (CoverServiceManager.this.mLock) {
                CoverServiceInfo findActiveServiceByComponentLocked =
                    CoverServiceManager.this.findActiveServiceByComponentLocked(componentName2);
                if (findActiveServiceByComponentLocked != null) {
                  findActiveServiceByComponentLocked.unbind();
                  CoverServiceManager.this.removeCoverServiceLocked(
                      findActiveServiceByComponentLocked);
                }
                if (!CoverServiceManager.SYSTEM_UI_COVER.equals(componentName2)
                    && CoverServiceManager.this.findCoverServiceByComponentLocked(componentName2)
                        == 2) {
                  semCoverServiceInfo =
                      CoverServiceManager.this
                      .new SViewCoverBaseServiceInfo(
                          componentName2, i, iBinder, this, UserHandle.SYSTEM);
                  CoverServiceManager.this.updateBindHistoryLocked(
                      "bound:cn=" + componentName2 + ",type=" + i);
                  CoverServiceManager.this.mActiveServices.add(semCoverServiceInfo);
                  CoverServiceManager.this.addBindingServiceLocked(semCoverServiceInfo.component);
                  CoverServiceManager.this.mIsUserSwitching = false;
                }
                semCoverServiceInfo =
                    CoverServiceManager.this
                    .new SemCoverServiceInfo(
                        componentName2,
                        i,
                        iBinder,
                        this,
                        new UserHandle(ActivityManager.getCurrentUser()));
                CoverServiceManager.this.updateBindHistoryLocked(
                    "bound:cn=" + componentName2 + ",type=" + i);
                CoverServiceManager.this.mActiveServices.add(semCoverServiceInfo);
                CoverServiceManager.this.addBindingServiceLocked(semCoverServiceInfo.component);
                CoverServiceManager.this.mIsUserSwitching = false;
              }
              semCoverServiceInfo.systemReady();
              semCoverServiceInfo.updateCoverState(
                  CoverServiceManager.this.mCoverStateProvider
                      .getCoverStateFromCoverServiceManager());
            }

            /* JADX WARN: Code restructure failed: missing block: B:11:0x0083, code lost:

               if (com.android.server.sepunion.cover.CoverServiceManager.LED_COVER.equals(r1.component) == false) goto L19;
            */
            /* JADX WARN: Code restructure failed: missing block: B:13:0x0093, code lost:

               if (r5.this$0.findCoverServiceByComponentLocked(r1.component) == r1.type) goto L21;
            */
            @Override // android.content.ServiceConnection
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public void onServiceDisconnected(ComponentName componentName2) {
              Log.d(CoverServiceManager.TAG, "onServiceDisconnected : name = " + componentName2);
              synchronized (CoverServiceManager.this.mLock) {
                CoverServiceInfo findActiveServiceByComponentLocked =
                    CoverServiceManager.this.findActiveServiceByComponentLocked(componentName2);
                if (findActiveServiceByComponentLocked != null) {
                  findActiveServiceByComponentLocked.unbind();
                  CoverServiceManager.this.removeCoverServiceLocked(
                      findActiveServiceByComponentLocked);
                  CoverServiceManager.this.updateBindHistoryLocked(
                      "unbound:cn=" + componentName2 + ",type=" + i);
                  if ((findActiveServiceByComponentLocked.type == 7
                          || findActiveServiceByComponentLocked.type == 14)
                      && CoverServiceManager.IS_B6_DEVICE
                      && CoverServiceManager.SUPPORT_FLIP_SUITCASE) {
                    if (CoverServiceManager.LED_SUIT.equals(
                        findActiveServiceByComponentLocked.component)) {
                      if (findActiveServiceByComponentLocked.disconnect()) {
                        Log.d(
                            CoverServiceManager.TAG,
                            "onServiceDisconnected : retry to connect cover service, "
                                + findActiveServiceByComponentLocked.type);
                        CoverServiceManager.this.reconnectCoverService(
                            findActiveServiceByComponentLocked);
                      } else {
                        Log.d(
                            CoverServiceManager.TAG,
                            "onServiceDisconnected : give up to connect cover service, "
                                + findActiveServiceByComponentLocked.type);
                      }
                    }
                  }
                }
              }
            }
          };
      String str = TAG;
      Log.d(str, "bindCoverServiceLocked : type = " + i);
      if (!this.mContext.bindServiceAsUser(intent, serviceConnection, 16777221, userHandle)) {
        Log.e(str, "Unable to bind service: " + intent);
        return false;
      }
      addBindingServiceLocked(componentName);
      updateBindHistoryLocked("binding:cn=" + componentName + ",type=" + i);
      return true;
    } catch (SecurityException e) {
      Log.e(TAG, "Unable to bind service: " + intent, e);
      return false;
    }
  }

  public final boolean isShouldRebindCoverServiceLocked(
      CoverState coverState, ComponentName componentName) {
    Long l;
    if (componentName == null
        || !coverState.getAttachState()
        || isShouldNotBindCoverService(coverState.getType(), false)) {
      return false;
    }
    synchronized (this.mLock) {
      if (findActiveServiceByComponentLocked(componentName) != null
          || (l = (Long) this.mBindingTimestamp.get(componentName)) == null) {
        return false;
      }
      return System.currentTimeMillis() - l.longValue() > 30000;
    }
  }

  public final boolean isShouldNotBindCoverService(int i, boolean z) {
    if (!z && !verifySystemFeature(this.mContext, i)) {
      Log.d(TAG, "isShouldNotBindCoverService : not support cover type(" + i + ")");
      return true;
    }
    if (CoverTestModeUtils.getTestCoverType() != 255) {
      return false;
    }
    Log.d(TAG, "isShouldNotBindCoverService : return because of test mode for nfc smart cover");
    return true;
  }

  public void unbindCoverService(int i) {
    Log.d(TAG, "unbindCoverService : type = " + i);
    synchronized (this.mLock) {
      Iterator it = this.mActiveServices.iterator();
      while (it.hasNext()) {
        unbindCoverServiceLocked((CoverServiceInfo) it.next());
      }
    }
    removeAllCoverServiceLocked();
    unregisterBroadcastReceiver();
  }

  public final boolean unbindCoverServiceLocked(CoverServiceInfo coverServiceInfo) {
    if (coverServiceInfo == null) {
      Log.d(TAG, "unbindCoverServiceLocked : info is null");
      return false;
    }
    coverServiceInfo.unbind();
    try {
      this.mContext.unbindService(coverServiceInfo.connection);
      updateBindHistoryLocked(
          "unbinding:cn=" + coverServiceInfo.component + ",type=" + coverServiceInfo.type);
      return true;
    } catch (IllegalArgumentException e) {
      Log.e(TAG, " could not be unbound: " + coverServiceInfo.component + ", " + e);
      return false;
    }
  }

  public void switchCoverService(int i, int i2) {
    CoverServiceInfo findActiveServiceByComponentLocked;
    Log.d(TAG, "switchCoverService : type = " + i + " userId = " + i2);
    synchronized (this.mLock) {
      ComponentName componentName = (ComponentName) this.mCoverServices.get(i);
      if (componentName != null
          && (findActiveServiceByComponentLocked =
                  findActiveServiceByComponentLocked(componentName))
              != null) {
        this.mIsUserSwitching = true;
        removeCoverServiceLocked(findActiveServiceByComponentLocked);
        bindCoverServiceLocked(componentName, i, new UserHandle(ActivityManager.getCurrentUser()));
        unbindCoverServiceLocked(findActiveServiceByComponentLocked);
      }
    }
  }

  public void unbindActiveCoverService(int i) {
    CoverServiceInfo findActiveServiceByComponentLocked;
    Log.d(TAG, "unbindActiveCoverService : type = " + i);
    synchronized (this.mLock) {
      ComponentName componentName = (ComponentName) this.mCoverServices.get(i);
      if (componentName != null
          && (findActiveServiceByComponentLocked =
                  findActiveServiceByComponentLocked(componentName))
              != null) {
        removeCoverServiceLocked(findActiveServiceByComponentLocked);
        unbindCoverServiceLocked(findActiveServiceByComponentLocked);
      }
    }
  }

  public final void removeCoverServiceLocked(CoverServiceInfo coverServiceInfo) {
    synchronized (this.mLock) {
      this.mActiveServices.remove(coverServiceInfo);
    }
    removeBindingServiceLocked(coverServiceInfo.component);
  }

  public final void removeAllCoverServiceLocked() {
    synchronized (this.mLock) {
      this.mActiveServices.clear();
      this.mBindingTimestamp.clear();
    }
  }

  public final ComponentName getSystemUICoverService(int i) {
    if (i == 255 && Feature.getInstance(this.mContext).isNfcAuthEnabled()) {
      Log.d(
          TAG,
          "getSystemUICoverService : return because of nfc smart cover supporting nfc"
              + " authentication");
      return null;
    }
    return SYSTEM_UI_COVER;
  }

  public final ComponentName getCoverServiceNameLocked(int i, boolean z, boolean z2) {
    synchronized (this.mLock) {
      if (i == 11 && z2) {
        if (this.mCoverServices.size() == 0) {
          refreshCoverServicesLocked();
        }
      }
      ComponentName componentName = (ComponentName) this.mCoverServices.get(i);
      if (componentName != null) {
        return componentName;
      }
      return z ? getPredefinedCoverServiceNameLocked(i) : null;
    }
  }

  public final void refreshCoverServicesLocked() {
    this.mCoverServices.clear();
    SparseArray queryInstalledCoverServices = queryInstalledCoverServices();
    int size = queryInstalledCoverServices.size();
    for (int i = 0; i < size; i++) {
      this.mCoverServices.put(
          queryInstalledCoverServices.keyAt(i),
          (ComponentName) queryInstalledCoverServices.valueAt(i));
    }
  }

  public final ComponentName getPredefinedCoverServiceNameLocked(int i) {
    if (i != 7) {
      if (i == 255) {
        if (Feature.getInstance(this.mContext).isNfcAuthEnabled()) {
          Log.d(
              TAG,
              "getPredefinedCoverServiceNameLocked : return because of nfc smart cover supporting"
                  + " nfc authentication");
          return null;
        }
        return SYSTEM_UI_COVER;
      }
      if (i == 13) {
        return GAMEPACK_COVER;
      }
      if (i != 14) {
        return SYSTEM_UI_COVER;
      }
    }
    return (IS_B6_DEVICE && SUPPORT_FLIP_SUITCASE) ? LED_SUIT : LED_COVER;
  }

  public final int findCoverServiceByComponentLocked(ComponentName componentName) {
    synchronized (this.mLock) {
      int size = this.mCoverServices.size();
      for (int i = 0; i < size; i++) {
        if (((ComponentName) this.mCoverServices.valueAt(i)).equals(componentName)) {
          return this.mCoverServices.keyAt(i);
        }
      }
      return 2;
    }
  }

  public final CoverServiceInfo findActiveServiceByComponentLocked(ComponentName componentName) {
    synchronized (this.mLock) {
      Iterator it = this.mActiveServices.iterator();
      while (it.hasNext()) {
        CoverServiceInfo coverServiceInfo = (CoverServiceInfo) it.next();
        if (componentName.equals(coverServiceInfo.component)) {
          return coverServiceInfo;
        }
      }
      return null;
    }
  }

  public final void addBindingServiceLocked(ComponentName componentName) {
    synchronized (this.mLock) {
      this.mBindingTimestamp.put(componentName, Long.valueOf(System.currentTimeMillis()));
    }
  }

  public final void removeBindingServiceLocked(ComponentName componentName) {
    synchronized (this.mLock) {
      this.mBindingTimestamp.remove(componentName);
    }
  }

  public final boolean containsBindingServiceLocked(ComponentName componentName) {
    boolean contains;
    synchronized (this.mLock) {
      contains = this.mBindingTimestamp.keySet().contains(componentName);
    }
    return contains;
  }

  public final SparseArray queryInstalledCoverServices() {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      return getVerifiedCoverService(
          queryIntentService(new Intent("com.samsung.android.cover.CoverService")));
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final boolean addCoverService(String str) {
    SparseArray verifiedCoverService;
    int size;
    Log.d(TAG, "addCoverService : " + str);
    Intent intent = new Intent("com.samsung.android.cover.CoverService");
    intent.setPackage(str);
    List queryIntentService = queryIntentService(intent);
    if (queryIntentService == null
        || queryIntentService.size() <= 0
        || (size = (verifiedCoverService = getVerifiedCoverService(queryIntentService)).size())
            == 0) {
      return false;
    }
    boolean z = false;
    for (int i = 0; i < size; i++) {
      int keyAt = verifiedCoverService.keyAt(i);
      synchronized (this.mLock) {
        if (((ComponentName) this.mCoverServices.get(keyAt)) == null) {
          this.mCoverServices.put(keyAt, (ComponentName) verifiedCoverService.valueAt(i));
          z = true;
        } else {
          Log.d(TAG, "addCoverService : Cover Type(" + keyAt + ") is already added");
        }
      }
    }
    return z;
  }

  public final boolean updateCoverService(String str) {
    SparseArray verifiedCoverService;
    int size;
    Log.d(TAG, "updateCoverService : " + str);
    Intent intent = new Intent("com.samsung.android.cover.CoverService");
    intent.setPackage(str);
    List queryIntentService = queryIntentService(intent);
    if (queryIntentService == null
        || queryIntentService.size() <= 0
        || (size = (verifiedCoverService = getVerifiedCoverService(queryIntentService)).size())
            == 0) {
      return false;
    }
    synchronized (this.mLock) {
      for (int i = 0; i < size; i++) {
        this.mCoverServices.put(
            verifiedCoverService.keyAt(i), (ComponentName) verifiedCoverService.valueAt(i));
      }
    }
    return true;
  }

  public final List queryIntentService(Intent intent) {
    return this.mContext.getPackageManager().queryIntentServicesAsUser(intent, 786564, 0);
  }

  public final SparseArray getVerifiedCoverService(List list) {
    SparseArray sparseArray = new SparseArray();
    if (list != null && list.size() > 0) {
      Iterator it = list.iterator();
      while (it.hasNext()) {
        ServiceInfo serviceInfo = ((ResolveInfo) it.next()).serviceInfo;
        ComponentName componentName = new ComponentName(serviceInfo.packageName, serviceInfo.name);
        if (!"com.samsung.android.permission.BIND_COVER_SERVICE".equals(serviceInfo.permission)) {
          Log.w(
              TAG,
              "service("
                  + serviceInfo.packageName
                  + "/"
                  + serviceInfo.name
                  + ") has no permission");
        } else {
          Bundle bundle = serviceInfo.metaData;
          if (bundle == null) {
            Log.w(
                TAG,
                "service("
                    + serviceInfo.packageName
                    + "/"
                    + serviceInfo.name
                    + ") has no meta data");
          } else {
            int i = bundle.getInt("com.samsung.android.cover.service", -1);
            if (!verifySystemFeature(this.mContext, i)) {
              Log.w(
                  TAG,
                  "service("
                      + serviceInfo.packageName
                      + "/"
                      + serviceInfo.name
                      + ") has wrong cover type("
                      + i
                      + ")");
            } else {
              ComponentName componentName2 = (ComponentName) sparseArray.get(i);
              if (componentName2 != null) {
                Log.w(
                    TAG,
                    "type("
                        + i
                        + ") of service("
                        + serviceInfo.packageName
                        + "/"
                        + serviceInfo.name
                        + ") is duplicated  with "
                        + componentName2
                        + ")");
              } else {
                sparseArray.put(i, componentName);
              }
            }
          }
        }
      }
    }
    return sparseArray;
  }

  public static boolean verifySystemFeature(Context context, int i) {
    if (i == 0) {
      return Feature.getInstance(context).isSupportFlipCover();
    }
    if (i == 11) {
      return Feature.getInstance(context).isSupportNeonCover();
    }
    if (i == 7) {
      return Feature.getInstance(context).isSupportNfcLedCover();
    }
    if (i == 8) {
      return Feature.getInstance(context).isSupportClearCover();
    }
    switch (i) {
      case 13:
        return Feature.getInstance(context).isSupportGamePackCover();
      case 14:
        return Feature.getInstance(context).isSupportLEDBackCover();
      case 15:
        return Feature.getInstance(context).isSupportClearSideViewCover();
      case 16:
        return Feature.getInstance(context).isSupportMiniSviewWalletCover();
      case 17:
        return Feature.getInstance(context).isSupportClearCameraViewCover();
      default:
        return false;
    }
  }

  public boolean isBindingCoverService() {
    boolean z;
    synchronized (this.mLock) {
      z = this.mActiveServices.size() > 0;
    }
    return z;
  }

  public int onCoverAppStateChanged(boolean z) {
    synchronized (this.mLock) {
      int i = 0;
      if (this.mActiveServices.size() == 0) {
        return 0;
      }
      Iterator it = this.mActiveServices.iterator();
      while (it.hasNext()) {
        CoverServiceInfo coverServiceInfo = (CoverServiceInfo) it.next();
        if (SYSTEM_UI_COVER.equals(coverServiceInfo.component)) {
          i = coverServiceInfo.onCoverAppStateChanged(z);
        } else {
          coverServiceInfo.onCoverAppStateChanged(z);
        }
      }
      return i;
    }
  }

  public final void reconnectCoverService(CoverServiceInfo coverServiceInfo) {
    this.mHandler.removeMessages(2);
    this.mHandler.sendMessageDelayed(
        Message.obtain(this.mHandler, 2, 0, 0, coverServiceInfo), 1000L);
  }

  public void updateCoverState(CoverState coverState) {
    ComponentName coverServiceNameLocked =
        getCoverServiceNameLocked(coverState.getType(), false, false);
    boolean isShouldRebindCoverServiceLocked =
        isShouldRebindCoverServiceLocked(coverState, coverServiceNameLocked);
    if (isShouldRebindCoverServiceLocked) {
      removeBindingServiceLocked(coverServiceNameLocked);
    }
    if (isShouldRebindCoverServiceLocked) {
      bindCoverService(coverState.getType(), false);
      return;
    }
    if (!this.mCoverWakeLock.isHeld()) {
      this.mCoverWakeLock.acquire();
    }
    this.mHandler.obtainMessage(1, coverState).sendToTarget();
  }

  public final void handleUpdateCoverState(CoverState coverState) {
    synchronized (this.mLock) {
      Iterator it = this.mActiveServices.iterator();
      while (it.hasNext()) {
        ((CoverServiceInfo) it.next()).updateCoverState(coverState);
      }
    }
    Runnable runnable = this.mWakeLockRunnable;
    if (runnable != null) {
      this.mHandler.removeCallbacks(runnable);
    }
    Runnable runnable2 =
        new Runnable() { // from class: com.android.server.sepunion.cover.CoverServiceManager.3
          @Override // java.lang.Runnable
          public void run() {
            if (CoverServiceManager.this.mCoverWakeLock.isHeld()) {
              CoverServiceManager.this.mCoverWakeLock.release();
            }
          }
        };
    this.mWakeLockRunnable = runnable2;
    this.mHandler.postDelayed(runnable2, 1000L);
  }

  public final void updateBindHistoryLocked(String str) {
    String timestampFormat = toTimestampFormat(str);
    String[] strArr = this.mBindHistory;
    int length = strArr.length;
    int i = this.mBindHistoryIdx;
    if (i >= 0 && i < length) {
      this.mBindHistoryIdx = i + 1;
      strArr[i] = timestampFormat;
    }
    if (this.mBindHistoryIdx >= length) {
      this.mBindHistoryIdx = 0;
    }
  }

  public final String toTimestampFormat(String str) {
    Calendar calendar = Calendar.getInstance();
    return String.format(
        Locale.US,
        "[%02d-%02d %02d:%02d:%02d.%03d] %s",
        Integer.valueOf(calendar.get(2) + 1),
        Integer.valueOf(calendar.get(5)),
        Integer.valueOf(calendar.get(11)),
        Integer.valueOf(calendar.get(12)),
        Integer.valueOf(calendar.get(13)),
        Integer.valueOf(calendar.get(14)),
        str);
  }

  public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    synchronized (this.mLock) {
      printWriter.println(" Active Cover Service: ");
      Iterator it = this.mActiveServices.iterator();
      while (it.hasNext()) {
        printWriter.println("  " + ((CoverServiceInfo) it.next()));
      }
      printWriter.println(" ");
      printWriter.println(" Binding Cover Service: ");
      long currentTimeMillis = System.currentTimeMillis();
      for (Map.Entry entry : this.mBindingTimestamp.entrySet()) {
        printWriter.println(
            "  "
                + entry.getKey()
                + " : "
                + (currentTimeMillis - ((Long) entry.getValue()).longValue())
                + "ms ago");
      }
      printWriter.println(" ");
      printWriter.println(" Bind history:");
      for (String str : this.mBindHistory) {
        if (str != null) {
          printWriter.println("  " + str);
        }
      }
      printWriter.println(" ");
    }
  }

  public class SViewCoverBaseServiceInfo extends CoverServiceInfo {
    public SViewCoverBaseServiceInfo(
        ComponentName componentName,
        int i,
        IBinder iBinder,
        ServiceConnection serviceConnection,
        UserHandle userHandle) {
      super(componentName, i, iBinder, serviceConnection, userHandle);
    }

    @Override // com.android.server.sepunion.cover.CoverServiceManager.CoverServiceInfo
    public IInterface asInterface(IBinder iBinder) {
      return ISViewCoverBaseService.Stub.asInterface(iBinder);
    }

    @Override // com.android.server.sepunion.cover.CoverServiceManager.CoverServiceInfo
    public void systemReady() {
      try {
        ISViewCoverBaseService iSViewCoverBaseService = this.service;
        if (iSViewCoverBaseService != null) {
          iSViewCoverBaseService.onSystemReady();
        }
      } catch (RemoteException e) {
        e.printStackTrace();
      }
    }

    @Override // com.android.server.sepunion.cover.CoverServiceManager.CoverServiceInfo
    public void updateCoverState(CoverState coverState) {
      try {
        ISViewCoverBaseService iSViewCoverBaseService = this.service;
        if (iSViewCoverBaseService != null) {
          iSViewCoverBaseService.updateCoverState(coverState);
        }
      } catch (RemoteException e) {
        e.printStackTrace();
      }
    }

    @Override // com.android.server.sepunion.cover.CoverServiceManager.CoverServiceInfo
    public int onCoverAppStateChanged(boolean z) {
      try {
        ISViewCoverBaseService iSViewCoverBaseService = this.service;
        if (iSViewCoverBaseService != null) {
          return iSViewCoverBaseService.onCoverAppCovered(z);
        }
        return 0;
      } catch (RemoteException e) {
        e.printStackTrace();
        return 0;
      }
    }
  }

  public class SemCoverServiceInfo extends CoverServiceInfo {
    public SemCoverServiceInfo(
        ComponentName componentName,
        int i,
        IBinder iBinder,
        ServiceConnection serviceConnection,
        UserHandle userHandle) {
      super(componentName, i, iBinder, serviceConnection, userHandle);
    }

    @Override // com.android.server.sepunion.cover.CoverServiceManager.CoverServiceInfo
    public IInterface asInterface(IBinder iBinder) {
      return ICoverService.Stub.asInterface(iBinder);
    }

    @Override // com.android.server.sepunion.cover.CoverServiceManager.CoverServiceInfo
    public void systemReady() {
      try {
        ICoverService iCoverService = this.service;
        if (iCoverService != null) {
          iCoverService.onSystemReady();
        }
      } catch (RemoteException e) {
        e.printStackTrace();
      }
    }

    @Override // com.android.server.sepunion.cover.CoverServiceManager.CoverServiceInfo
    public void updateCoverState(CoverState coverState) {
      try {
        ICoverService iCoverService = this.service;
        if (iCoverService != null) {
          iCoverService.onUpdateCoverState(coverState);
        }
      } catch (RemoteException e) {
        e.printStackTrace();
      }
    }

    @Override // com.android.server.sepunion.cover.CoverServiceManager.CoverServiceInfo
    public int onCoverAppStateChanged(boolean z) {
      try {
        ICoverService iCoverService = this.service;
        if (iCoverService != null) {
          return iCoverService.onCoverAppCovered(z);
        }
        return 0;
      } catch (RemoteException e) {
        e.printStackTrace();
        return 0;
      }
    }
  }

  public abstract class CoverServiceInfo implements IBinder.DeathRecipient {
    public final IBinder binder;
    public final ComponentName component;
    public final ServiceConnection connection;
    public int disconnectionCount = 0;
    public final IInterface service;
    public final int type;
    public UserHandle user;

    public abstract IInterface asInterface(IBinder iBinder);

    public abstract int onCoverAppStateChanged(boolean z);

    public abstract void systemReady();

    public void unbind() {}

    public abstract void updateCoverState(CoverState coverState);

    public CoverServiceInfo(
        ComponentName componentName,
        int i,
        IBinder iBinder,
        ServiceConnection serviceConnection,
        UserHandle userHandle) {
      this.component = componentName;
      this.type = i;
      this.binder = iBinder;
      this.service = asInterface(iBinder);
      this.connection = serviceConnection;
      this.user = userHandle;
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
      Log.d("CoverServiceInfo", "binderDied : " + toString());
      CoverServiceManager.this.removeCoverServiceLocked(this);
    }

    public String toString() {
      return String.format(
          "CoverServiceInfo[component=%s, type=%s, service=%s, user=%s]",
          this.component, Integer.valueOf(this.type), this.service, this.user);
    }

    public boolean disconnect() {
      int i = this.disconnectionCount + 1;
      this.disconnectionCount = i;
      return i < 5;
    }
  }

  /* renamed from: com.android.server.sepunion.cover.CoverServiceManager$H */
  public final class HandlerC2561H extends Handler {
    public HandlerC2561H(Looper looper) {
      super(looper, null, true);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      int i = message.what;
      if (i == 1) {
        CoverServiceManager.this.handleUpdateCoverState((CoverState) message.obj);
      } else {
        if (i != 2) {
          return;
        }
        CoverServiceInfo coverServiceInfo = (CoverServiceInfo) message.obj;
        CoverServiceManager.this.bindCoverServiceLocked(
            coverServiceInfo.component, coverServiceInfo.type, coverServiceInfo.user);
      }
    }
  }
}
