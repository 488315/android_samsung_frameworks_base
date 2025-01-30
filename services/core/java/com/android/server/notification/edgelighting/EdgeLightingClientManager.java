package com.android.server.notification.edgelighting;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Binder;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Pair;
import android.util.Slog;
import com.samsung.android.edge.IEdgeLightingCallback;
import com.samsung.android.edge.SemEdgeLightingInfo;
import com.samsung.android.feature.SemCarrierFeature;
import com.samsung.android.feature.SemCscFeature;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class EdgeLightingClientManager {
  public static final boolean DEBUG = Debug.semIsProductDev();
  public static final String TAG = "EdgeLightingClientManager";
  public Context mContext;
  public EdgeLightingListenerManager mEdgeLightingListenerManager;
  public boolean mIsConnectedMode = false;
  public boolean mNeedToRecheckSetting = true;
  public List mEdgeLightingList = new ArrayList();
  public final ArrayList mHosts = new ArrayList();
  public int mCondition = getCondition();
  public Handler mHandler =
      new Handler() { // from class:
                      // com.android.server.notification.edgelighting.EdgeLightingClientManager.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
          super.handleMessage(message);
          int i = message.arg1;
          Pair pair = (Pair) message.obj;
          Intent intent = new Intent();
          intent.setClassName(
              "com.android.systemui", "com.android.systemui.edgelighting.EdgeLightingService");
          intent.putExtra("packagename", (String) pair.first);
          intent.putExtra("info", (Parcelable) pair.second);
          intent.putExtra("reason", i);
          Slog.d(
              EdgeLightingClientManager.TAG,
              "startService packagename=" + ((String) pair.first) + ",reason=" + i);
          try {
            EdgeLightingClientManager.this.mContext.startServiceAsUser(
                intent, UserHandle.SEM_OWNER);
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      };
  public EdgeLightingSettingObserver mEdgeLightingSettingObserver =
      new EdgeLightingSettingObserver(this.mHandler);

  public EdgeLightingClientManager(Context context) {
    this.mContext = context;
    this.mEdgeLightingListenerManager = new EdgeLightingListenerManager(context);
  }

  public final int getCondition() {
    int intForUser =
        Settings.System.getIntForUser(
            this.mContext.getContentResolver(), "edge_lighting_show_condition", 0, -2);
    if (intForUser == -1) {
      return 0;
    }
    if (intForUser == 0) {
      return 3;
    }
    if (intForUser != 1) {
      return intForUser != 2 ? 0 : 3;
    }
    return 1;
  }

  public void setConnectedMode(boolean z) {
    this.mIsConnectedMode = z;
  }

  public void onBootPhaseAppsCanStart() {
    Slog.d(TAG, "onBootPhaseAppsCanStart");
    createEdgeLightingService(UserHandle.SEM_OWNER);
  }

  public void onSwitchUser(int i) {
    Slog.d(TAG, "onSwitchUser : " + i);
    createEdgeLightingService(UserHandle.SEM_OWNER);
    this.mEdgeLightingSettingObserver.resetEdgeLightingEnabled();
  }

  public void onUnlockUser(int i) {
    Slog.d(TAG, "onUnlockUser : " + i);
    createEdgeLightingService(UserHandle.SEM_OWNER);
  }

  public final void createEdgeLightingService(UserHandle userHandle) {
    if (this.mIsConnectedMode) {
      Intent intent = new Intent();
      intent.setClassName(
          "com.android.systemui", "com.android.systemui.edgelighting.EdgeLightingService");
      try {
        this.mContext.startServiceAsUser(intent, userHandle);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public void startEdgeLightingService(String str, SemEdgeLightingInfo semEdgeLightingInfo, int i) {
    if (EdgeLightingHistory.IS_DEV_DEBUG || DEBUG) {
      Slog.d(
          TAG,
          "startEdgeLightingService : pkg = "
              + str
              + ", info = "
              + semEdgeLightingInfo
              + ", reason = "
              + i);
    }
    Handler handler = this.mHandler;
    handler.sendMessage(handler.obtainMessage(0, i, 0, new Pair(str, semEdgeLightingInfo)));
  }

  public void bind(IBinder iBinder, int i, ComponentName componentName) {
    synchronized (this.mHosts) {
      if (EdgeLightingHistory.IS_DEV_DEBUG || DEBUG) {
        Slog.d(
            TAG,
            "bind : pkg = "
                + componentName.getPackageName()
                + ", condition = "
                + i
                + ", mHosts = "
                + this.mHosts.size());
      }
      Iterator it = this.mHosts.iterator();
      while (it.hasNext()) {
        EdgeLightingHost edgeLightingHost = (EdgeLightingHost) it.next();
        if (edgeLightingHost != null && iBinder.equals(edgeLightingHost.token)) {
          Slog.w(TAG, "bind : already registered");
          edgeLightingHost.updateCondition(i);
          return;
        }
      }
      this.mHosts.add(
          new EdgeLightingHost(
              iBinder, componentName, i, Binder.getCallingPid(), Binder.getCallingUid()));
      EdgeLightingHistory.getInstance().updateHostHistory(componentName.getPackageName(), "bind.");
    }
  }

  public void unbind(IBinder iBinder, String str) {
    synchronized (this.mHosts) {
      if (EdgeLightingHistory.IS_DEV_DEBUG || DEBUG) {
        Slog.d(TAG, "unbind : pkg = " + str + ", mHosts = " + this.mHosts.size());
      }
      Iterator it = this.mHosts.iterator();
      EdgeLightingHost edgeLightingHost = null;
      while (it.hasNext()) {
        EdgeLightingHost edgeLightingHost2 = (EdgeLightingHost) it.next();
        if (edgeLightingHost2 != null && iBinder.equals(edgeLightingHost2.token)) {
          edgeLightingHost = edgeLightingHost2;
        }
      }
      if (edgeLightingHost == null) {
        Slog.w(TAG, "unbind : cannot find the matched host");
        return;
      }
      if (!this.mHosts.isEmpty()) {
        this.mHosts.remove(edgeLightingHost);
        EdgeLightingHistory.getInstance().updateHostHistory(str, "unbind.");
      }
      iBinder.unlinkToDeath(edgeLightingHost, 0);
    }
  }

  public boolean isAvailableEdgeLighting(int i) {
    synchronized (this.mHosts) {
      if (this.mNeedToRecheckSetting) {
        this.mNeedToRecheckSetting = false;
        this.mEdgeLightingSettingObserver.recheckEdgeLightingDefaultValue();
      }
      if (EdgeLightingHistory.IS_DEV_DEBUG || DEBUG) {
        Slog.d(
            TAG,
            "isAvailableEdgeLighting: condition="
                + i
                + " mCondition="
                + this.mCondition
                + " mIsConnected="
                + this.mIsConnectedMode
                + this.mHosts.size());
      }
      if (!this.mEdgeLightingSettingObserver.isEdgeLightingEnabled()) {
        return false;
      }
      if (!this.mIsConnectedMode) {
        int i2 = this.mCondition;
        return i2 == 7 || (i2 & i) != 0;
      }
      if (this.mHosts.size() < 1) {
        return false;
      }
      if (i == 7) {
        return true;
      }
      Iterator it = this.mHosts.iterator();
      while (it.hasNext()) {
        int i3 = ((EdgeLightingHost) it.next()).condition;
        if (i3 == 7 || (i3 & i) != 0) {
          return true;
        }
      }
      return false;
    }
  }

  public boolean existsHosts() {
    boolean z;
    synchronized (this.mHosts) {
      z = this.mHosts.size() > 0;
    }
    return z;
  }

  public int getEdgeLightingCondition() {
    synchronized (this.mHosts) {
      if (this.mHosts.size() < 1) {
        if (this.mIsConnectedMode) {
          return 0;
        }
        return this.mCondition;
      }
      Iterator it = this.mHosts.iterator();
      if (!it.hasNext()) {
        return 0;
      }
      return ((EdgeLightingHost) it.next()).condition;
    }
  }

  public int getEdgeLightingState() {
    synchronized (this.mEdgeLightingList) {
      return this.mEdgeLightingList.size() > 1 ? 1 : 0;
    }
  }

  public void updateEdgeLightingPackageList(String str, List list) {
    synchronized (this.mEdgeLightingList) {
      int size = this.mEdgeLightingList.size();
      this.mEdgeLightingList.clear();
      if (list != null) {
        this.mEdgeLightingList.addAll(list);
      }
      if (size == 0 && this.mEdgeLightingList.size() > 0) {
        this.mEdgeLightingListenerManager.startEdgeLighting();
      } else if (size != 0 && this.mEdgeLightingList.size() == 0) {
        this.mEdgeLightingListenerManager.stopEdgeLighting();
      }
    }
  }

  public void registerListener(IBinder iBinder, ComponentName componentName) {
    this.mEdgeLightingListenerManager.register(iBinder, componentName);
  }

  public void unregisterListener(IBinder iBinder, String str) {
    this.mEdgeLightingListenerManager.unregister(iBinder, str);
  }

  public void startEdgeLighting(String str, SemEdgeLightingInfo semEdgeLightingInfo) {
    if (!isAvailableEdgeLighting(7)) {
      Slog.d(TAG, "startEdgeLighting : edge lighting is disable");
    } else {
      startEdgeLightingInternal(str, semEdgeLightingInfo, 0);
    }
  }

  public void startEdgeLightingInternal(
      String str, SemEdgeLightingInfo semEdgeLightingInfo, int i) {
    PackageManager packageManager = this.mContext.getPackageManager();
    try {
      packageManager.getApplicationInfo(str, 0);
      if (packageManager.isPackageSuspended(str)) {
        EdgeLightingHistory.getInstance()
            .updateRejectHistory(
                "startEdgeLightingInternal() reject edge lighting because application suspend"
                    + " package="
                    + str);
        return;
      }
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
    synchronized (this.mHosts) {
      if (!this.mIsConnectedMode && this.mHosts.size() < 1) {
        startEdgeLightingService(str, semEdgeLightingInfo, i);
      }
      Iterator it = this.mHosts.iterator();
      while (it.hasNext()) {
        EdgeLightingHost edgeLightingHost = (EdgeLightingHost) it.next();
        if (edgeLightingHost != null) {
          edgeLightingHost.onStartEdgeLighting(str, semEdgeLightingInfo, i);
        }
      }
      EdgeLightingHistory.getInstance()
          .updateEdgeLightingHistory(
              "start : " + str + " : " + semEdgeLightingInfo.toString() + "," + i);
    }
  }

  public void stopEdgeLighting(String str) {
    if (!isAvailableEdgeLighting(7)) {
      Slog.d(TAG, "stopEdgeLighting : edge lighting is disable");
    } else {
      stopEdgeLightingInternal(str, 0);
    }
  }

  public void stopEdgeLightingInternal(String str, int i) {
    synchronized (this.mHosts) {
      if (!this.mIsConnectedMode && this.mHosts.size() < 1) {
        Slog.e(TAG, "stopEdgeLightingInternal mHosts is null. pkg=" + str + ",reason=" + i);
      }
      Iterator it = this.mHosts.iterator();
      while (it.hasNext()) {
        EdgeLightingHost edgeLightingHost = (EdgeLightingHost) it.next();
        if (edgeLightingHost != null) {
          edgeLightingHost.onStopEdgeLighting(str, i);
        }
      }
      EdgeLightingHistory.getInstance().updateEdgeLightingHistory("stop : " + str + "," + i);
    }
  }

  public boolean isEdgeLightingSettingEnabled(ContentResolver contentResolver) {
    return Settings.System.getIntForUser(contentResolver, "edge_lighting", 1, -2) == 1;
  }

  public void onScreenChanged(boolean z) {
    synchronized (this.mHosts) {
      Iterator it = this.mHosts.iterator();
      while (it.hasNext()) {
        EdgeLightingHost edgeLightingHost = (EdgeLightingHost) it.next();
        if (edgeLightingHost != null) {
          edgeLightingHost.onScreenChanged(z);
        }
      }
    }
  }

  public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    printWriter.println("-ClientManager start");
    synchronized (this.mHosts) {
      Iterator it = this.mHosts.iterator();
      while (it.hasNext()) {
        printWriter.println((EdgeLightingHost) it.next());
      }
    }
    printWriter.println("");
    if (EdgeLightingHistory.IS_DEV_DEBUG || DEBUG) {
      synchronized (this.mEdgeLightingList) {
        StringBuilder sb = new StringBuilder();
        sb.append("-EdgeLightingList : [");
        int size = this.mEdgeLightingList.size();
        for (int i = 0; i < size; i++) {
          sb.append((String) this.mEdgeLightingList.get(i));
          if (i != size - 1) {
            sb.append(", ");
          }
        }
        sb.append(']');
        printWriter.println(sb);
        printWriter.println("");
      }
    }
    printWriter.println("mCondition=" + this.mCondition);
    this.mEdgeLightingListenerManager.dump(fileDescriptor, printWriter, strArr);
  }

  public class EdgeLightingHost implements IBinder.DeathRecipient {
    public final ComponentName component;
    public int condition;
    public Handler mHandler;
    public final int pid;
    public final IBinder token;
    public final int uid;

    public class HostLightingInfo {
      public SemEdgeLightingInfo mInfo;
      public String mPackageName;

      public HostLightingInfo(String str, SemEdgeLightingInfo semEdgeLightingInfo) {
        this.mPackageName = str;
        this.mInfo = semEdgeLightingInfo;
      }

      public String getPackageName() {
        return this.mPackageName;
      }

      public SemEdgeLightingInfo getLightingInfo() {
        return this.mInfo;
      }
    }

    public EdgeLightingHost(IBinder iBinder, ComponentName componentName, int i, int i2, int i3) {
      this.mHandler =
          new Handler(
              EdgeLightingClientManager.this.mHandler
                  .getLooper()) { // from class:
                                  // com.android.server.notification.edgelighting.EdgeLightingClientManager.EdgeLightingHost.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
              super.handleMessage(message);
              int i4 = message.what;
              if (i4 == 1) {
                Object obj = message.obj;
                if (obj instanceof HostLightingInfo) {
                  HostLightingInfo hostLightingInfo = (HostLightingInfo) obj;
                  EdgeLightingHost.this._onStartEdgeLighting(
                      hostLightingInfo.getPackageName(),
                      hostLightingInfo.getLightingInfo(),
                      message.arg1);
                  return;
                }
                return;
              }
              if (i4 != 2) {
                if (i4 != 3) {
                  return;
                }
                EdgeLightingHost.this._onScreenChanged(message.arg1 == 1);
              } else {
                Object obj2 = message.obj;
                if (obj2 instanceof String) {
                  EdgeLightingHost.this._onStopEdgeLighting((String) obj2, message.arg1);
                }
              }
            }
          };
      this.token = iBinder;
      this.component = componentName;
      this.condition = i;
      this.pid = i2;
      this.uid = i3;
      if (iBinder != null) {
        try {
          iBinder.linkToDeath(this, 0);
        } catch (RemoteException unused) {
          Slog.e(EdgeLightingClientManager.TAG, "EdgeLightingHost : linkToDeath error");
        }
      }
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
      Slog.v(
          EdgeLightingClientManager.TAG,
          "binderDied : component = " + this.component.toShortString());
      synchronized (EdgeLightingClientManager.this.mEdgeLightingList) {
        int size = EdgeLightingClientManager.this.mEdgeLightingList.size();
        EdgeLightingClientManager.this.mEdgeLightingList.clear();
        if (size != 0 && EdgeLightingClientManager.this.mEdgeLightingList.size() == 0) {
          EdgeLightingClientManager.this.mEdgeLightingListenerManager.stopEdgeLighting();
        }
      }
      this.mHandler.removeCallbacksAndMessages(null);
      synchronized (EdgeLightingClientManager.this.mHosts) {
        EdgeLightingClientManager.this.mHosts.remove(this);
      }
      this.token.unlinkToDeath(this, 0);
      EdgeLightingHistory.getInstance()
          .updateHostHistory(this.component.getPackageName(), "binderDied.");
      if (EdgeLightingClientManager.this.mIsConnectedMode) {
        final String packageName = this.component.getPackageName();
        this.mHandler.postDelayed(
            new Runnable() { // from class:
                             // com.android.server.notification.edgelighting.EdgeLightingClientManager.EdgeLightingHost.2
              @Override // java.lang.Runnable
              public void run() {
                EdgeLightingHost.this.startEdgeLightingService(packageName);
              }
            },
            1000L);
      }
    }

    public void updateCondition(int i) {
      this.condition = i;
    }

    public final void startEdgeLightingService(String str) {
      if ("com.android.systemui".equals(str)) {
        EdgeLightingClientManager edgeLightingClientManager = EdgeLightingClientManager.this;
        if (edgeLightingClientManager.isEdgeLightingSettingEnabled(
            edgeLightingClientManager.mContext.getContentResolver())) {
          Slog.v(EdgeLightingClientManager.TAG, "startEdgeLightingService");
          EdgeLightingClientManager.this.createEdgeLightingService(UserHandle.SEM_OWNER);
        }
      }
    }

    public void onStartEdgeLighting(String str, SemEdgeLightingInfo semEdgeLightingInfo, int i) {
      if (EdgeLightingHistory.IS_DEV_DEBUG || EdgeLightingClientManager.DEBUG) {
        Slog.d(
            EdgeLightingClientManager.TAG,
            "onStartEdgeLighting pkg = "
                + str
                + ", info = "
                + semEdgeLightingInfo
                + ", reason = "
                + i);
      }
      if (this.token == null) {
        Slog.w(EdgeLightingClientManager.TAG, "onStartEdgeLighting : token is null");
      } else {
        Handler handler = this.mHandler;
        handler.sendMessage(
            handler.obtainMessage(1, i, 0, new HostLightingInfo(str, semEdgeLightingInfo)));
      }
    }

    public void onStopEdgeLighting(String str, int i) {
      if (EdgeLightingHistory.IS_DEV_DEBUG || EdgeLightingClientManager.DEBUG) {
        Slog.d(
            EdgeLightingClientManager.TAG, "onStopEdgeLighting pkg = " + str + ", reason = " + i);
      }
      if (this.token == null) {
        Slog.w(EdgeLightingClientManager.TAG, "onStopEdgeLighting : token is null");
      } else {
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(2, i, 0, str));
      }
    }

    public void onScreenChanged(boolean z) {
      if (EdgeLightingHistory.IS_DEV_DEBUG || EdgeLightingClientManager.DEBUG) {
        Slog.d(EdgeLightingClientManager.TAG, "onScreenChanged on = " + z);
      }
      if (this.token == null) {
        Slog.w(EdgeLightingClientManager.TAG, "onScreenChanged : token is null");
      } else {
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(3, z ? 1 : 0, 0));
      }
    }

    public final void _onStartEdgeLighting(
        String str, SemEdgeLightingInfo semEdgeLightingInfo, int i) {
      try {
        IEdgeLightingCallback asInterface = IEdgeLightingCallback.Stub.asInterface(this.token);
        if (asInterface != null) {
          asInterface.onStartEdgeLighting(str, semEdgeLightingInfo, i);
        }
      } catch (RemoteException e) {
        Slog.e(EdgeLightingClientManager.TAG, "onStartEdgeLighting : RemoteException : ", e);
      }
    }

    public final void _onStopEdgeLighting(String str, int i) {
      try {
        IEdgeLightingCallback asInterface = IEdgeLightingCallback.Stub.asInterface(this.token);
        if (asInterface != null) {
          asInterface.onStopEdgeLighting(str, i);
        }
      } catch (RemoteException e) {
        Slog.e(EdgeLightingClientManager.TAG, "onStopEdgeLighting : RemoteException : ", e);
      }
    }

    public final void _onScreenChanged(boolean z) {
      try {
        IEdgeLightingCallback asInterface = IEdgeLightingCallback.Stub.asInterface(this.token);
        if (asInterface != null) {
          asInterface.onScreenChanged(z);
        }
      } catch (RemoteException e) {
        Slog.e(EdgeLightingClientManager.TAG, "onScreenChanged : RemoteException : ", e);
      }
    }

    public String toString() {
      return "  [Host: component:("
          + this.component.toShortString()
          + ") condition:("
          + this.condition
          + ") pid:("
          + this.pid
          + ") uid:("
          + this.uid
          + ")]";
    }
  }

  public class EdgeLightingSettingObserver extends ContentObserver {
    public final Uri EDGE_LIGHTING_SETTING;
    public final Uri EDGE_LIGHTING_SHOW_CONDITION;
    public final String defaultCarrierEdgeLighting;
    public final String defaultEdgeLighting;
    public int mDefaultValue;
    public boolean mLastEnabled;

    public EdgeLightingSettingObserver(Handler handler) {
      super(handler);
      this.mLastEnabled = false;
      Uri uriFor = Settings.System.getUriFor("edge_lighting");
      this.EDGE_LIGHTING_SETTING = uriFor;
      Uri uriFor2 = Settings.System.getUriFor("edge_lighting_show_condition");
      this.EDGE_LIGHTING_SHOW_CONDITION = uriFor2;
      String string =
          SemCscFeature.getInstance().getString("CscFeature_Framework_ConfigDefStatusEdgeLighting");
      this.defaultEdgeLighting = string;
      String string2 =
          SemCarrierFeature.getInstance()
              .getString(0, "CarrierFeature_SystemUI_ConfigDefStatusEdgeLighting", "", false);
      this.defaultCarrierEdgeLighting = string2;
      this.mDefaultValue =
          ((string == null || !string.contains("-defaulton"))
                  && (string2 == null || !string2.contains("-defaulton")))
              ? 1
              : 0;
      this.mLastEnabled =
          Settings.System.getIntForUser(
                  EdgeLightingClientManager.this.mContext.getContentResolver(),
                  "edge_lighting",
                  this.mDefaultValue,
                  -2)
              == 1;
      ContentResolver contentResolver =
          EdgeLightingClientManager.this.mContext.getContentResolver();
      contentResolver.registerContentObserver(uriFor, false, this, -1);
      contentResolver.registerContentObserver(uriFor2, false, this, -1);
      Slog.d(
          EdgeLightingClientManager.TAG,
          "EdgeLightingSettingObserver : mLastEnabled = "
              + this.mLastEnabled
              + " mDefaultValue = "
              + this.mDefaultValue);
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z, Uri uri) {
      if (uri == null || this.EDGE_LIGHTING_SETTING.equals(uri)) {
        boolean z2 =
            Settings.System.getIntForUser(
                    EdgeLightingClientManager.this.mContext.getContentResolver(),
                    "edge_lighting",
                    this.mDefaultValue,
                    -2)
                == 1;
        Slog.d(
            EdgeLightingClientManager.TAG,
            "onChange - edge_lighting : newValue = " + z2 + " mLastEnabled = " + this.mLastEnabled);
        if (z2 != this.mLastEnabled) {
          this.mLastEnabled = z2;
          if (z2) {
            Intent intent = new Intent();
            intent.setClassName(
                "com.android.systemui", "com.android.systemui.edgelighting.EdgeLightingService");
            try {
              EdgeLightingClientManager.this.mContext.startServiceAsUser(
                  intent, UserHandle.SEM_OWNER);
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        }
      }
      if (uri == null || this.EDGE_LIGHTING_SHOW_CONDITION.equals(uri)) {
        int intForUser =
            Settings.System.getIntForUser(
                EdgeLightingClientManager.this.mContext.getContentResolver(),
                "edge_lighting_show_condition",
                0,
                -2);
        if (intForUser != 0) {
          if (intForUser == 1) {
            EdgeLightingClientManager.this.mCondition = 1;
          } else if (intForUser != 2) {
            EdgeLightingClientManager.this.mCondition = 0;
          }
          Slog.d(
              EdgeLightingClientManager.TAG,
              "onChange - edge_lighting_show_condition : mCondition = "
                  + EdgeLightingClientManager.this.mCondition);
        }
        EdgeLightingClientManager.this.mCondition = 3;
        Slog.d(
            EdgeLightingClientManager.TAG,
            "onChange - edge_lighting_show_condition : mCondition = "
                + EdgeLightingClientManager.this.mCondition);
      }
    }

    public boolean isEdgeLightingEnabled() {
      return this.mLastEnabled;
    }

    public void resetEdgeLightingEnabled() {
      this.mLastEnabled =
          Settings.System.getIntForUser(
                  EdgeLightingClientManager.this.mContext.getContentResolver(),
                  "edge_lighting",
                  this.mDefaultValue,
                  -2)
              == 1;
    }

    public void recheckEdgeLightingDefaultValue() {
      String str;
      String str2 = this.defaultEdgeLighting;
      this.mDefaultValue =
          ((str2 == null || !str2.contains("-defaulton"))
                  && ((str = this.defaultCarrierEdgeLighting) == null
                      || !str.contains("-defaulton")))
              ? 1
              : 0;
      Slog.d(
          EdgeLightingClientManager.TAG,
          "recheckEdgeLightingDefaultValue : mDefaultValue = " + this.mDefaultValue);
      try {
        this.mLastEnabled =
            Settings.System.getIntForUser(
                    EdgeLightingClientManager.this.mContext.getContentResolver(),
                    "edge_lighting",
                    -2)
                == 1;
        Slog.d(
            EdgeLightingClientManager.TAG,
            "recheckEdgeLightingDefaultValue : mLastEnabled = " + this.mLastEnabled);
      } catch (Settings.SettingNotFoundException unused) {
        this.mLastEnabled = this.mDefaultValue == 1;
        Settings.System.putIntForUser(
            EdgeLightingClientManager.this.mContext.getContentResolver(),
            "edge_lighting",
            this.mDefaultValue,
            -2);
        Slog.d(
            EdgeLightingClientManager.TAG,
            "recheckEdgeLightingDefaultValue - SettingNotFoundException : mLastEnabled = "
                + this.mLastEnabled);
      }
    }
  }
}
