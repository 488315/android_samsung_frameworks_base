package com.android.server.media;

import android.app.ActivityManager;
import android.app.ActivityThread;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.IMediaRouter2;
import android.media.IMediaRouter2Manager;
import android.media.MediaRoute2Info;
import android.media.MediaRoute2ProviderInfo;
import android.media.MediaRouter2Utils;
import android.media.RouteDiscoveryPreference;
import android.media.RouteListingPreference;
import android.media.RoutingSessionInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.util.function.HeptConsumer;
import com.android.internal.util.function.HexConsumer;
import com.android.internal.util.function.QuadConsumer;
import com.android.internal.util.function.TriConsumer;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.server.LocalServices;
import com.android.server.pm.UserManagerInternal;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* loaded from: classes2.dex */
public class MediaRouter2ServiceImpl {
  public final ActivityManager mActivityManager;
  public final Context mContext;
  public final ActivityManager.OnUidImportanceListener mOnUidImportanceListener;
  public final PowerManager mPowerManager;
  public final BroadcastReceiver mScreenOnOffReceiver;
  public final UserManagerInternal mUserManagerInternal;
  public static final boolean DEBUG = Log.isLoggable("MR2ServiceImpl", 3);
  public static final String[] BLUETOOTH_PERMISSIONS_FOR_SYSTEM_ROUTING = {
    "android.permission.BLUETOOTH_CONNECT", "android.permission.BLUETOOTH_SCAN"
  };
  public static int sPackageImportanceForScanning =
      DeviceConfig.getInt("media_better_together", "scanning_package_minimum_importance", 125);
  public final Object mLock = new Object();
  public final AtomicInteger mNextRouterOrManagerId = new AtomicInteger(1);
  public final SparseArray mUserRecords = new SparseArray();
  public final ArrayMap mAllRouterRecords = new ArrayMap();
  public final ArrayMap mAllManagerRecords = new ArrayMap();
  public int mCurrentActiveUserId = -1;

  public static int toOriginalRequestId(long j) {
    return (int) j;
  }

  public static int toRequesterId(long j) {
    return (int) (j >> 32);
  }

  public static long toUniqueRequestId(int i, int i2) {
    return i2 | (i << 32);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$new$0(int i, int i2) {
    synchronized (this.mLock) {
      int size = this.mUserRecords.size();
      for (int i3 = 0; i3 < size; i3++) {
        ((UserRecord) this.mUserRecords.valueAt(i3))
            .mHandler.maybeUpdateDiscoveryPreferenceForUid(i);
      }
    }
  }

  /* renamed from: com.android.server.media.MediaRouter2ServiceImpl$1 */
  public class C18771 extends BroadcastReceiver {
    public C18771() {}

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
      synchronized (MediaRouter2ServiceImpl.this.mLock) {
        int size = MediaRouter2ServiceImpl.this.mUserRecords.size();
        for (int i = 0; i < size; i++) {
          UserHandler userHandler =
              ((UserRecord) MediaRouter2ServiceImpl.this.mUserRecords.valueAt(i)).mHandler;
          userHandler.sendMessage(
              PooledLambda.obtainMessage(
                  new Consumer() { // from class:
                    // com.android.server.media.MediaRouter2ServiceImpl$1$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                      ((MediaRouter2ServiceImpl.UserHandler) obj)
                          .updateDiscoveryPreferenceOnHandler();
                    }
                  },
                  userHandler));
        }
      }
    }
  }

  public MediaRouter2ServiceImpl(Context context) {
    ActivityManager.OnUidImportanceListener onUidImportanceListener =
        new ActivityManager.OnUidImportanceListener() { // from class:
          // com.android.server.media.MediaRouter2ServiceImpl$$ExternalSyntheticLambda10
          public final void onUidImportance(int i, int i2) {
            MediaRouter2ServiceImpl.this.lambda$new$0(i, i2);
          }
        };
    this.mOnUidImportanceListener = onUidImportanceListener;
    C18771 c18771 = new C18771();
    this.mScreenOnOffReceiver = c18771;
    this.mContext = context;
    ActivityManager activityManager =
        (ActivityManager) context.getSystemService(ActivityManager.class);
    this.mActivityManager = activityManager;
    activityManager.addOnUidImportanceListener(
        onUidImportanceListener, sPackageImportanceForScanning);
    this.mPowerManager = (PowerManager) context.getSystemService(PowerManager.class);
    this.mUserManagerInternal =
        (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("android.intent.action.SCREEN_ON");
    intentFilter.addAction("android.intent.action.SCREEN_OFF");
    context.registerReceiver(c18771, intentFilter);
    context
        .getPackageManager()
        .addOnPermissionsChangeListener(
            new PackageManager.OnPermissionsChangedListener() { // from class:
              // com.android.server.media.MediaRouter2ServiceImpl$$ExternalSyntheticLambda11
              public final void onPermissionsChanged(int i) {
                MediaRouter2ServiceImpl.this.onPermissionsChanged(i);
              }
            });
    DeviceConfig.addOnPropertiesChangedListener(
        "media_better_together",
        ActivityThread.currentApplication().getMainExecutor(),
        new DeviceConfig.OnPropertiesChangedListener() { // from class:
          // com.android.server.media.MediaRouter2ServiceImpl$$ExternalSyntheticLambda12
          public final void onPropertiesChanged(DeviceConfig.Properties properties) {
            MediaRouter2ServiceImpl.this.onDeviceConfigChange(properties);
          }
        });
  }

  public final void onPermissionsChanged(final int i) {
    synchronized (this.mLock) {
      Optional findFirst =
          this.mAllRouterRecords.values().stream()
              .filter(
                  new Predicate() { // from class:
                    // com.android.server.media.MediaRouter2ServiceImpl$$ExternalSyntheticLambda21
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                      boolean lambda$onPermissionsChanged$1;
                      lambda$onPermissionsChanged$1 =
                          MediaRouter2ServiceImpl.lambda$onPermissionsChanged$1(
                              i, (MediaRouter2ServiceImpl.RouterRecord) obj);
                      return lambda$onPermissionsChanged$1;
                    }
                  })
              .findFirst();
      if (findFirst.isPresent()) {
        ((RouterRecord) findFirst.get()).maybeUpdateSystemRoutingPermissionLocked();
      }
    }
  }

  public static /* synthetic */ boolean lambda$onPermissionsChanged$1(
      int i, RouterRecord routerRecord) {
    return routerRecord.mUid == i;
  }

  public boolean verifyPackageExists(String str) {
    int callingPid = Binder.getCallingPid();
    int callingUid = Binder.getCallingUid();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      this.mContext.enforcePermission(
          "android.permission.MEDIA_CONTENT_CONTROL",
          callingPid,
          callingUid,
          "Must hold MEDIA_CONTENT_CONTROL permission.");
      this.mContext.getPackageManager().getPackageInfo(str, PackageManager.PackageInfoFlags.of(0L));
      Binder.restoreCallingIdentity(clearCallingIdentity);
      return true;
    } catch (PackageManager.NameNotFoundException unused) {
      Binder.restoreCallingIdentity(clearCallingIdentity);
      return false;
    } catch (Throwable th) {
      Binder.restoreCallingIdentity(clearCallingIdentity);
      throw th;
    }
  }

  public List getSystemRoutes() {
    Collection collection;
    int identifier = UserHandle.getUserHandleForUid(Binder.getCallingUid()).getIdentifier();
    boolean z =
        this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_AUDIO_ROUTING") == 0;
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      synchronized (this.mLock) {
        UserRecord orCreateUserRecordLocked = getOrCreateUserRecordLocked(identifier);
        if (z) {
          MediaRoute2ProviderInfo providerInfo =
              orCreateUserRecordLocked.mHandler.mSystemProvider.getProviderInfo();
          if (providerInfo != null) {
            collection = providerInfo.getRoutes();
          } else {
            collection = Collections.emptyList();
          }
        } else {
          ArrayList arrayList = new ArrayList();
          arrayList.add(orCreateUserRecordLocked.mHandler.mSystemProvider.getDefaultRoute());
          collection = arrayList;
        }
      }
      return new ArrayList(collection);
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void registerRouter2(IMediaRouter2 iMediaRouter2, String str) {
    Objects.requireNonNull(iMediaRouter2, "router must not be null");
    if (TextUtils.isEmpty(str)) {
      throw new IllegalArgumentException("packageName must not be empty");
    }
    int callingUid = Binder.getCallingUid();
    int callingPid = Binder.getCallingPid();
    int identifier = UserHandle.getUserHandleForUid(callingUid).getIdentifier();
    boolean z =
        this.mContext.checkCallingOrSelfPermission("android.permission.CONFIGURE_WIFI_DISPLAY")
            == 0;
    boolean z2 =
        this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_AUDIO_ROUTING") == 0;
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      synchronized (this.mLock) {
        registerRouter2Locked(iMediaRouter2, callingUid, callingPid, str, identifier, z, z2);
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void unregisterRouter2(IMediaRouter2 iMediaRouter2) {
    Objects.requireNonNull(iMediaRouter2, "router must not be null");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      synchronized (this.mLock) {
        unregisterRouter2Locked(iMediaRouter2, false);
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void setDiscoveryRequestWithRouter2(
      IMediaRouter2 iMediaRouter2, RouteDiscoveryPreference routeDiscoveryPreference) {
    Objects.requireNonNull(iMediaRouter2, "router must not be null");
    Objects.requireNonNull(routeDiscoveryPreference, "preference must not be null");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      synchronized (this.mLock) {
        RouterRecord routerRecord =
            (RouterRecord) this.mAllRouterRecords.get(iMediaRouter2.asBinder());
        if (routerRecord == null) {
          Slog.w("MR2ServiceImpl", "Ignoring updating discoveryRequest of null routerRecord.");
        } else {
          setDiscoveryRequestWithRouter2Locked(routerRecord, routeDiscoveryPreference);
        }
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void setRouteListingPreference(
      IMediaRouter2 iMediaRouter2, RouteListingPreference routeListingPreference) {
    ComponentName linkedItemComponentName =
        routeListingPreference != null ? routeListingPreference.getLinkedItemComponentName() : null;
    if (linkedItemComponentName != null) {
      MediaServerUtils.enforcePackageName(
          linkedItemComponentName.getPackageName(), Binder.getCallingUid());
      if (!MediaServerUtils.isValidActivityComponentName(
          this.mContext,
          linkedItemComponentName,
          "android.media.action.TRANSFER_MEDIA",
          Binder.getCallingUserHandle())) {
        throw new IllegalArgumentException(
            "Unable to resolve "
                + linkedItemComponentName
                + " to a valid activity for android.media.action.TRANSFER_MEDIA");
      }
    }
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      synchronized (this.mLock) {
        RouterRecord routerRecord =
            (RouterRecord) this.mAllRouterRecords.get(iMediaRouter2.asBinder());
        if (routerRecord == null) {
          Slog.w("MR2ServiceImpl", "Ignoring updating route listing of null routerRecord.");
        } else {
          setRouteListingPreferenceLocked(routerRecord, routeListingPreference);
        }
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void setRouteVolumeWithRouter2(
      IMediaRouter2 iMediaRouter2, MediaRoute2Info mediaRoute2Info, int i) {
    Objects.requireNonNull(iMediaRouter2, "router must not be null");
    Objects.requireNonNull(mediaRoute2Info, "route must not be null");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      synchronized (this.mLock) {
        setRouteVolumeWithRouter2Locked(iMediaRouter2, mediaRoute2Info, i);
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void requestCreateSessionWithRouter2(
      IMediaRouter2 iMediaRouter2,
      int i,
      long j,
      RoutingSessionInfo routingSessionInfo,
      MediaRoute2Info mediaRoute2Info,
      Bundle bundle) {
    Objects.requireNonNull(iMediaRouter2, "router must not be null");
    Objects.requireNonNull(routingSessionInfo, "oldSession must not be null");
    Objects.requireNonNull(mediaRoute2Info, "route must not be null");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      synchronized (this.mLock) {
        requestCreateSessionWithRouter2Locked(
            i, j, iMediaRouter2, routingSessionInfo, mediaRoute2Info, bundle);
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void selectRouteWithRouter2(
      IMediaRouter2 iMediaRouter2, String str, MediaRoute2Info mediaRoute2Info) {
    Objects.requireNonNull(iMediaRouter2, "router must not be null");
    Objects.requireNonNull(mediaRoute2Info, "route must not be null");
    if (TextUtils.isEmpty(str)) {
      throw new IllegalArgumentException("uniqueSessionId must not be empty");
    }
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      synchronized (this.mLock) {
        selectRouteWithRouter2Locked(iMediaRouter2, str, mediaRoute2Info);
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void deselectRouteWithRouter2(
      IMediaRouter2 iMediaRouter2, String str, MediaRoute2Info mediaRoute2Info) {
    Objects.requireNonNull(iMediaRouter2, "router must not be null");
    Objects.requireNonNull(mediaRoute2Info, "route must not be null");
    if (TextUtils.isEmpty(str)) {
      throw new IllegalArgumentException("uniqueSessionId must not be empty");
    }
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      synchronized (this.mLock) {
        deselectRouteWithRouter2Locked(iMediaRouter2, str, mediaRoute2Info);
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void transferToRouteWithRouter2(
      IMediaRouter2 iMediaRouter2, String str, MediaRoute2Info mediaRoute2Info) {
    Objects.requireNonNull(iMediaRouter2, "router must not be null");
    Objects.requireNonNull(mediaRoute2Info, "route must not be null");
    if (TextUtils.isEmpty(str)) {
      throw new IllegalArgumentException("uniqueSessionId must not be empty");
    }
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      synchronized (this.mLock) {
        transferToRouteWithRouter2Locked(iMediaRouter2, str, mediaRoute2Info);
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void setSessionVolumeWithRouter2(IMediaRouter2 iMediaRouter2, String str, int i) {
    Objects.requireNonNull(iMediaRouter2, "router must not be null");
    Objects.requireNonNull(str, "uniqueSessionId must not be null");
    if (TextUtils.isEmpty(str)) {
      throw new IllegalArgumentException("uniqueSessionId must not be empty");
    }
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      synchronized (this.mLock) {
        setSessionVolumeWithRouter2Locked(iMediaRouter2, str, i);
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void releaseSessionWithRouter2(IMediaRouter2 iMediaRouter2, String str) {
    Objects.requireNonNull(iMediaRouter2, "router must not be null");
    if (TextUtils.isEmpty(str)) {
      throw new IllegalArgumentException("uniqueSessionId must not be empty");
    }
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      synchronized (this.mLock) {
        releaseSessionWithRouter2Locked(iMediaRouter2, str);
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public List getRemoteSessions(IMediaRouter2Manager iMediaRouter2Manager) {
    List remoteSessionsLocked;
    Objects.requireNonNull(iMediaRouter2Manager, "manager must not be null");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      synchronized (this.mLock) {
        remoteSessionsLocked = getRemoteSessionsLocked(iMediaRouter2Manager);
      }
      return remoteSessionsLocked;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void registerManager(IMediaRouter2Manager iMediaRouter2Manager, String str) {
    Objects.requireNonNull(iMediaRouter2Manager, "manager must not be null");
    if (TextUtils.isEmpty(str)) {
      throw new IllegalArgumentException("packageName must not be empty");
    }
    int callingUid = Binder.getCallingUid();
    int callingPid = Binder.getCallingPid();
    int identifier = UserHandle.getUserHandleForUid(callingUid).getIdentifier();
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      synchronized (this.mLock) {
        registerManagerLocked(iMediaRouter2Manager, callingUid, callingPid, str, identifier);
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void unregisterManager(IMediaRouter2Manager iMediaRouter2Manager) {
    Objects.requireNonNull(iMediaRouter2Manager, "manager must not be null");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      synchronized (this.mLock) {
        unregisterManagerLocked(iMediaRouter2Manager, false);
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void startScan(IMediaRouter2Manager iMediaRouter2Manager) {
    Objects.requireNonNull(iMediaRouter2Manager, "manager must not be null");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      synchronized (this.mLock) {
        startScanLocked(iMediaRouter2Manager);
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void stopScan(IMediaRouter2Manager iMediaRouter2Manager) {
    Objects.requireNonNull(iMediaRouter2Manager, "manager must not be null");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      synchronized (this.mLock) {
        stopScanLocked(iMediaRouter2Manager);
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void setRouteVolumeWithManager(
      IMediaRouter2Manager iMediaRouter2Manager, int i, MediaRoute2Info mediaRoute2Info, int i2) {
    Objects.requireNonNull(iMediaRouter2Manager, "manager must not be null");
    Objects.requireNonNull(mediaRoute2Info, "route must not be null");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      synchronized (this.mLock) {
        setRouteVolumeWithManagerLocked(i, iMediaRouter2Manager, mediaRoute2Info, i2);
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void requestCreateSessionWithManager(
      IMediaRouter2Manager iMediaRouter2Manager,
      int i,
      RoutingSessionInfo routingSessionInfo,
      MediaRoute2Info mediaRoute2Info) {
    Objects.requireNonNull(iMediaRouter2Manager, "manager must not be null");
    Objects.requireNonNull(routingSessionInfo, "oldSession must not be null");
    Objects.requireNonNull(mediaRoute2Info, "route must not be null");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      synchronized (this.mLock) {
        requestCreateSessionWithManagerLocked(
            i, iMediaRouter2Manager, routingSessionInfo, mediaRoute2Info);
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void selectRouteWithManager(
      IMediaRouter2Manager iMediaRouter2Manager,
      int i,
      String str,
      MediaRoute2Info mediaRoute2Info) {
    Objects.requireNonNull(iMediaRouter2Manager, "manager must not be null");
    if (TextUtils.isEmpty(str)) {
      throw new IllegalArgumentException("uniqueSessionId must not be empty");
    }
    Objects.requireNonNull(mediaRoute2Info, "route must not be null");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      synchronized (this.mLock) {
        selectRouteWithManagerLocked(i, iMediaRouter2Manager, str, mediaRoute2Info);
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void deselectRouteWithManager(
      IMediaRouter2Manager iMediaRouter2Manager,
      int i,
      String str,
      MediaRoute2Info mediaRoute2Info) {
    Objects.requireNonNull(iMediaRouter2Manager, "manager must not be null");
    if (TextUtils.isEmpty(str)) {
      throw new IllegalArgumentException("uniqueSessionId must not be empty");
    }
    Objects.requireNonNull(mediaRoute2Info, "route must not be null");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      synchronized (this.mLock) {
        deselectRouteWithManagerLocked(i, iMediaRouter2Manager, str, mediaRoute2Info);
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void transferToRouteWithManager(
      IMediaRouter2Manager iMediaRouter2Manager,
      int i,
      String str,
      MediaRoute2Info mediaRoute2Info) {
    Objects.requireNonNull(iMediaRouter2Manager, "manager must not be null");
    if (TextUtils.isEmpty(str)) {
      throw new IllegalArgumentException("uniqueSessionId must not be empty");
    }
    Objects.requireNonNull(mediaRoute2Info, "route must not be null");
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      synchronized (this.mLock) {
        transferToRouteWithManagerLocked(i, iMediaRouter2Manager, str, mediaRoute2Info);
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void setSessionVolumeWithManager(
      IMediaRouter2Manager iMediaRouter2Manager, int i, String str, int i2) {
    Objects.requireNonNull(iMediaRouter2Manager, "manager must not be null");
    if (TextUtils.isEmpty(str)) {
      throw new IllegalArgumentException("uniqueSessionId must not be empty");
    }
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      synchronized (this.mLock) {
        setSessionVolumeWithManagerLocked(i, iMediaRouter2Manager, str, i2);
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void releaseSessionWithManager(
      IMediaRouter2Manager iMediaRouter2Manager, int i, String str) {
    Objects.requireNonNull(iMediaRouter2Manager, "manager must not be null");
    if (TextUtils.isEmpty(str)) {
      throw new IllegalArgumentException("uniqueSessionId must not be empty");
    }
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      synchronized (this.mLock) {
        releaseSessionWithManagerLocked(i, iMediaRouter2Manager, str);
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public RoutingSessionInfo getSystemSessionInfo(String str, boolean z) {
    RoutingSessionInfo build;
    int identifier = UserHandle.getUserHandleForUid(Binder.getCallingUid()).getIdentifier();
    boolean z2 =
        this.mContext.checkCallingOrSelfPermission("android.permission.MODIFY_AUDIO_ROUTING") == 0;
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      synchronized (this.mLock) {
        UserRecord orCreateUserRecordLocked = getOrCreateUserRecordLocked(identifier);
        if (!z2) {
          build =
              new RoutingSessionInfo.Builder(
                      orCreateUserRecordLocked.mHandler.mSystemProvider.getDefaultSessionInfo())
                  .setClientPackageName(str)
                  .build();
        } else if (z) {
          build =
              orCreateUserRecordLocked.mHandler.mSystemProvider
                  .generateDeviceRouteSelectedSessionInfo(str);
        } else {
          List sessionInfos = orCreateUserRecordLocked.mHandler.mSystemProvider.getSessionInfos();
          if (sessionInfos != null && !sessionInfos.isEmpty()) {
            build =
                new RoutingSessionInfo.Builder((RoutingSessionInfo) sessionInfos.get(0))
                    .setClientPackageName(str)
                    .build();
          } else {
            Slog.w("MR2ServiceImpl", "System provider does not have any session info.");
            build = null;
          }
        }
      }
      return build;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void dump(PrintWriter printWriter, String str) {
    printWriter.println(str + "MediaRouter2ServiceImpl");
    String str2 = str + "  ";
    synchronized (this.mLock) {
      printWriter.println(str2 + "mNextRouterOrManagerId=" + this.mNextRouterOrManagerId.get());
      printWriter.println(str2 + "mCurrentActiveUserId=" + this.mCurrentActiveUserId);
      StringBuilder sb = new StringBuilder();
      sb.append(str2);
      sb.append("UserRecords:");
      printWriter.println(sb.toString());
      if (this.mUserRecords.size() > 0) {
        for (int i = 0; i < this.mUserRecords.size(); i++) {
          ((UserRecord) this.mUserRecords.valueAt(i)).dump(printWriter, str2 + "  ");
        }
      } else {
        printWriter.println(str2 + "  <no user records>");
      }
    }
  }

  public void updateRunningUserAndProfiles(int i) {
    synchronized (this.mLock) {
      if (this.mCurrentActiveUserId != i) {
        Slog.i(
            "MR2ServiceImpl",
            TextUtils.formatSimple("switchUser | user: %d", new Object[] {Integer.valueOf(i)}));
        this.mCurrentActiveUserId = i;
        SparseArray clone = this.mUserRecords.clone();
        for (int i2 = 0; i2 < clone.size(); i2++) {
          int keyAt = clone.keyAt(i2);
          UserRecord userRecord = (UserRecord) clone.valueAt(i2);
          if (isUserActiveLocked(keyAt)) {
            userRecord.mHandler.sendMessage(
                PooledLambda.obtainMessage(
                    new MediaRouter2ServiceImpl$$ExternalSyntheticLambda8(), userRecord.mHandler));
          } else {
            userRecord.mHandler.sendMessage(
                PooledLambda.obtainMessage(
                    new MediaRouter2ServiceImpl$$ExternalSyntheticLambda9(), userRecord.mHandler));
            disposeUserIfNeededLocked(userRecord);
          }
        }
      }
    }
  }

  public void routerDied(RouterRecord routerRecord) {
    synchronized (this.mLock) {
      unregisterRouter2Locked(routerRecord.mRouter, true);
    }
  }

  public void managerDied(ManagerRecord managerRecord) {
    synchronized (this.mLock) {
      unregisterManagerLocked(managerRecord.mManager, true);
    }
  }

  public final boolean isUserActiveLocked(int i) {
    return this.mUserManagerInternal.getProfileParentId(i) == this.mCurrentActiveUserId;
  }

  public final void registerRouter2Locked(
      IMediaRouter2 iMediaRouter2, int i, int i2, String str, int i3, boolean z, boolean z2) {
    IBinder asBinder = iMediaRouter2.asBinder();
    if (this.mAllRouterRecords.get(asBinder) != null) {
      Slog.w(
          "MR2ServiceImpl",
          "registerRouter2Locked: Same router already exists. packageName=" + str);
      return;
    }
    UserRecord orCreateUserRecordLocked = getOrCreateUserRecordLocked(i3);
    RouterRecord routerRecord =
        new RouterRecord(orCreateUserRecordLocked, iMediaRouter2, i, i2, str, z, z2);
    try {
      asBinder.linkToDeath(routerRecord, 0);
      orCreateUserRecordLocked.mRouterRecords.add(routerRecord);
      this.mAllRouterRecords.put(asBinder, routerRecord);
      orCreateUserRecordLocked.mHandler.sendMessage(
          PooledLambda.obtainMessage(
              new BiConsumer() { // from class:
                // com.android.server.media.MediaRouter2ServiceImpl$$ExternalSyntheticLambda5
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                  ((MediaRouter2ServiceImpl.UserHandler) obj)
                      .notifyRouterRegistered((MediaRouter2ServiceImpl.RouterRecord) obj2);
                }
              },
              orCreateUserRecordLocked.mHandler,
              routerRecord));
      Slog.i(
          "MR2ServiceImpl",
          TextUtils.formatSimple(
              "registerRouter2 | package: %s, uid: %d, pid: %d, router id: %d",
              new Object[] {
                str,
                Integer.valueOf(i),
                Integer.valueOf(i2),
                Integer.valueOf(routerRecord.mRouterId)
              }));
    } catch (RemoteException e) {
      throw new RuntimeException("MediaRouter2 died prematurely.", e);
    }
  }

  public final void unregisterRouter2Locked(IMediaRouter2 iMediaRouter2, boolean z) {
    RouterRecord routerRecord =
        (RouterRecord) this.mAllRouterRecords.remove(iMediaRouter2.asBinder());
    if (routerRecord == null) {
      Slog.w("MR2ServiceImpl", "Ignoring unregistering unknown router2");
      return;
    }
    Slog.i(
        "MR2ServiceImpl",
        TextUtils.formatSimple(
            "unregisterRouter2 | package: %s, router id: %d",
            new Object[] {routerRecord.mPackageName, Integer.valueOf(routerRecord.mRouterId)}));
    UserRecord userRecord = routerRecord.mUserRecord;
    userRecord.mRouterRecords.remove(routerRecord);
    routerRecord.mUserRecord.mHandler.sendMessage(
        PooledLambda.obtainMessage(
            new MediaRouter2ServiceImpl$$ExternalSyntheticLambda15(),
            routerRecord.mUserRecord.mHandler,
            routerRecord.mPackageName,
            (Object) null));
    routerRecord.mUserRecord.mHandler.sendMessage(
        PooledLambda.obtainMessage(
            new MediaRouter2ServiceImpl$$ExternalSyntheticLambda4(),
            routerRecord.mUserRecord.mHandler,
            routerRecord.mPackageName,
            (Object) null));
    userRecord.mHandler.sendMessage(
        PooledLambda.obtainMessage(
            new MediaRouter2ServiceImpl$$ExternalSyntheticLambda16(), userRecord.mHandler));
    routerRecord.dispose();
    disposeUserIfNeededLocked(userRecord);
  }

  public final void setDiscoveryRequestWithRouter2Locked(
      RouterRecord routerRecord, RouteDiscoveryPreference routeDiscoveryPreference) {
    if (routerRecord.mDiscoveryPreference.equals(routeDiscoveryPreference)) {
      return;
    }
    Slog.i(
        "MR2ServiceImpl",
        TextUtils.formatSimple(
            "setDiscoveryRequestWithRouter2 | router: %s(id: %d), discovery request: %s",
            new Object[] {
              routerRecord.mPackageName,
              Integer.valueOf(routerRecord.mRouterId),
              routeDiscoveryPreference.toString()
            }));
    routerRecord.mDiscoveryPreference = routeDiscoveryPreference;
    routerRecord.mUserRecord.mHandler.sendMessage(
        PooledLambda.obtainMessage(
            new MediaRouter2ServiceImpl$$ExternalSyntheticLambda15(),
            routerRecord.mUserRecord.mHandler,
            routerRecord.mPackageName,
            routerRecord.mDiscoveryPreference));
    routerRecord.mUserRecord.mHandler.sendMessage(
        PooledLambda.obtainMessage(
            new MediaRouter2ServiceImpl$$ExternalSyntheticLambda16(),
            routerRecord.mUserRecord.mHandler));
  }

  public final void setRouteListingPreferenceLocked(
      RouterRecord routerRecord, RouteListingPreference routeListingPreference) {
    routerRecord.mRouteListingPreference = routeListingPreference;
    Slog.i(
        "MR2ServiceImpl",
        TextUtils.formatSimple(
            "setRouteListingPreference | router: %s(id: %d), route listing preference: [%s]",
            new Object[] {
              routerRecord.mPackageName,
              Integer.valueOf(routerRecord.mRouterId),
              routeListingPreference != null
                  ? (String)
                      routeListingPreference.getItems().stream()
                          .map(
                              new Function() { // from class:
                                // com.android.server.media.MediaRouter2ServiceImpl$$ExternalSyntheticLambda3
                                @Override // java.util.function.Function
                                public final Object apply(Object obj) {
                                  return ((RouteListingPreference.Item) obj).getRouteId();
                                }
                              })
                          .collect(Collectors.joining(","))
                  : null
            }));
    routerRecord.mUserRecord.mHandler.sendMessage(
        PooledLambda.obtainMessage(
            new MediaRouter2ServiceImpl$$ExternalSyntheticLambda4(),
            routerRecord.mUserRecord.mHandler,
            routerRecord.mPackageName,
            routeListingPreference));
  }

  public final void setRouteVolumeWithRouter2Locked(
      IMediaRouter2 iMediaRouter2, MediaRoute2Info mediaRoute2Info, int i) {
    RouterRecord routerRecord = (RouterRecord) this.mAllRouterRecords.get(iMediaRouter2.asBinder());
    if (routerRecord != null) {
      Slog.i(
          "MR2ServiceImpl",
          TextUtils.formatSimple(
              "setRouteVolumeWithRouter2 | router: %s(id: %d), volume: %d",
              new Object[] {
                routerRecord.mPackageName,
                Integer.valueOf(routerRecord.mRouterId),
                Integer.valueOf(i)
              }));
      routerRecord.mUserRecord.mHandler.sendMessage(
          PooledLambda.obtainMessage(
              new MediaRouter2ServiceImpl$$ExternalSyntheticLambda0(),
              routerRecord.mUserRecord.mHandler,
              -1L,
              mediaRoute2Info,
              Integer.valueOf(i)));
    }
  }

  public final void requestCreateSessionWithRouter2Locked(
      int i,
      long j,
      IMediaRouter2 iMediaRouter2,
      RoutingSessionInfo routingSessionInfo,
      MediaRoute2Info mediaRoute2Info,
      Bundle bundle) {
    SessionCreationRequest sessionCreationRequest;
    RouterRecord routerRecord = (RouterRecord) this.mAllRouterRecords.get(iMediaRouter2.asBinder());
    if (routerRecord == null) {
      return;
    }
    if (j != 0) {
      ManagerRecord findManagerWithId =
          routerRecord.mUserRecord.mHandler.findManagerWithId(toRequesterId(j));
      if (findManagerWithId == null
          || (sessionCreationRequest = findManagerWithId.mLastSessionCreationRequest) == null) {
        Slog.w(
            "MR2ServiceImpl", "requestCreateSessionWithRouter2Locked: Ignoring unknown request.");
        routerRecord.mUserRecord.mHandler.notifySessionCreationFailedToRouter(routerRecord, i);
        return;
      }
      if (!TextUtils.equals(
          sessionCreationRequest.mOldSession.getId(), routingSessionInfo.getId())) {
        Slog.w(
            "MR2ServiceImpl",
            "requestCreateSessionWithRouter2Locked: Ignoring unmatched routing session.");
        routerRecord.mUserRecord.mHandler.notifySessionCreationFailedToRouter(routerRecord, i);
        return;
      }
      if (!TextUtils.equals(
          findManagerWithId.mLastSessionCreationRequest.mRoute.getId(), mediaRoute2Info.getId())) {
        if (!routerRecord.hasSystemRoutingPermission()
            && findManagerWithId.mLastSessionCreationRequest.mRoute.isSystemRoute()
            && mediaRoute2Info.isSystemRoute()) {
          mediaRoute2Info = findManagerWithId.mLastSessionCreationRequest.mRoute;
        } else {
          Slog.w(
              "MR2ServiceImpl", "requestCreateSessionWithRouter2Locked: Ignoring unmatched route.");
          routerRecord.mUserRecord.mHandler.notifySessionCreationFailedToRouter(routerRecord, i);
          return;
        }
      }
      findManagerWithId.mLastSessionCreationRequest = null;
    } else if (mediaRoute2Info.isSystemRoute()
        && !routerRecord.hasSystemRoutingPermission()
        && !TextUtils.equals(
            mediaRoute2Info.getId(),
            routerRecord.mUserRecord.mHandler.mSystemProvider.getDefaultRoute().getId())) {
      Slog.w(
          "MR2ServiceImpl",
          "MODIFY_AUDIO_ROUTING permission is required to transfer to" + mediaRoute2Info);
      routerRecord.mUserRecord.mHandler.notifySessionCreationFailedToRouter(routerRecord, i);
      return;
    }
    routerRecord.mUserRecord.mHandler.sendMessage(
        PooledLambda.obtainMessage(
            new HeptConsumer() { // from class:
              // com.android.server.media.MediaRouter2ServiceImpl$$ExternalSyntheticLambda19
              public final void accept(
                  Object obj,
                  Object obj2,
                  Object obj3,
                  Object obj4,
                  Object obj5,
                  Object obj6,
                  Object obj7) {
                ((MediaRouter2ServiceImpl.UserHandler) obj)
                    .requestCreateSessionWithRouter2OnHandler(
                        ((Long) obj2).longValue(),
                        ((Long) obj3).longValue(),
                        (MediaRouter2ServiceImpl.RouterRecord) obj4,
                        (RoutingSessionInfo) obj5,
                        (MediaRoute2Info) obj6,
                        (Bundle) obj7);
              }
            },
            routerRecord.mUserRecord.mHandler,
            Long.valueOf(toUniqueRequestId(routerRecord.mRouterId, i)),
            Long.valueOf(j),
            routerRecord,
            routingSessionInfo,
            mediaRoute2Info,
            bundle));
  }

  public final void selectRouteWithRouter2Locked(
      IMediaRouter2 iMediaRouter2, String str, MediaRoute2Info mediaRoute2Info) {
    RouterRecord routerRecord = (RouterRecord) this.mAllRouterRecords.get(iMediaRouter2.asBinder());
    if (routerRecord == null) {
      return;
    }
    Slog.i(
        "MR2ServiceImpl",
        TextUtils.formatSimple(
            "selectRouteWithRouter2 | router: %s(id: %d), route: %s",
            new Object[] {
              routerRecord.mPackageName,
              Integer.valueOf(routerRecord.mRouterId),
              mediaRoute2Info.getId()
            }));
    routerRecord.mUserRecord.mHandler.sendMessage(
        PooledLambda.obtainMessage(
            new MediaRouter2ServiceImpl$$ExternalSyntheticLambda7(),
            routerRecord.mUserRecord.mHandler,
            -1L,
            routerRecord,
            str,
            mediaRoute2Info));
  }

  public final void deselectRouteWithRouter2Locked(
      IMediaRouter2 iMediaRouter2, String str, MediaRoute2Info mediaRoute2Info) {
    RouterRecord routerRecord = (RouterRecord) this.mAllRouterRecords.get(iMediaRouter2.asBinder());
    if (routerRecord == null) {
      return;
    }
    Slog.i(
        "MR2ServiceImpl",
        TextUtils.formatSimple(
            "deselectRouteWithRouter2 | router: %s(id: %d), route: %s",
            new Object[] {
              routerRecord.mPackageName,
              Integer.valueOf(routerRecord.mRouterId),
              mediaRoute2Info.getId()
            }));
    routerRecord.mUserRecord.mHandler.sendMessage(
        PooledLambda.obtainMessage(
            new MediaRouter2ServiceImpl$$ExternalSyntheticLambda6(),
            routerRecord.mUserRecord.mHandler,
            -1L,
            routerRecord,
            str,
            mediaRoute2Info));
  }

  public final void transferToRouteWithRouter2Locked(
      IMediaRouter2 iMediaRouter2, String str, MediaRoute2Info mediaRoute2Info) {
    RouterRecord routerRecord = (RouterRecord) this.mAllRouterRecords.get(iMediaRouter2.asBinder());
    if (routerRecord == null) {
      return;
    }
    Slog.i(
        "MR2ServiceImpl",
        TextUtils.formatSimple(
            "transferToRouteWithRouter2 | router: %s(id: %d), route: %s",
            new Object[] {
              routerRecord.mPackageName,
              Integer.valueOf(routerRecord.mRouterId),
              mediaRoute2Info.getId()
            }));
    String id = routerRecord.mUserRecord.mHandler.mSystemProvider.getDefaultRoute().getId();
    if (mediaRoute2Info.isSystemRoute()
        && !routerRecord.hasSystemRoutingPermission()
        && !TextUtils.equals(mediaRoute2Info.getId(), id)) {
      routerRecord.mUserRecord.mHandler.sendMessage(
          PooledLambda.obtainMessage(
              new TriConsumer() { // from class:
                // com.android.server.media.MediaRouter2ServiceImpl$$ExternalSyntheticLambda17
                public final void accept(Object obj, Object obj2, Object obj3) {
                  ((MediaRouter2ServiceImpl.UserHandler) obj)
                      .notifySessionCreationFailedToRouter(
                          (MediaRouter2ServiceImpl.RouterRecord) obj2, ((Integer) obj3).intValue());
                }
              },
              routerRecord.mUserRecord.mHandler,
              routerRecord,
              Integer.valueOf(toOriginalRequestId(-1L))));
    } else {
      routerRecord.mUserRecord.mHandler.sendMessage(
          PooledLambda.obtainMessage(
              new MediaRouter2ServiceImpl$$ExternalSyntheticLambda18(),
              routerRecord.mUserRecord.mHandler,
              -1L,
              routerRecord,
              str,
              mediaRoute2Info));
    }
  }

  public final void setSessionVolumeWithRouter2Locked(
      IMediaRouter2 iMediaRouter2, String str, int i) {
    RouterRecord routerRecord = (RouterRecord) this.mAllRouterRecords.get(iMediaRouter2.asBinder());
    if (routerRecord == null) {
      return;
    }
    Slog.i(
        "MR2ServiceImpl",
        TextUtils.formatSimple(
            "setSessionVolumeWithRouter2 | router: %s(id: %d), session: %s, volume: %d",
            new Object[] {
              routerRecord.mPackageName,
              Integer.valueOf(routerRecord.mRouterId),
              str,
              Integer.valueOf(i)
            }));
    routerRecord.mUserRecord.mHandler.sendMessage(
        PooledLambda.obtainMessage(
            new MediaRouter2ServiceImpl$$ExternalSyntheticLambda2(),
            routerRecord.mUserRecord.mHandler,
            -1L,
            str,
            Integer.valueOf(i)));
  }

  public final void releaseSessionWithRouter2Locked(IMediaRouter2 iMediaRouter2, String str) {
    RouterRecord routerRecord = (RouterRecord) this.mAllRouterRecords.get(iMediaRouter2.asBinder());
    if (routerRecord == null) {
      return;
    }
    Slog.i(
        "MR2ServiceImpl",
        TextUtils.formatSimple(
            "releaseSessionWithRouter2 | router: %s(id: %d), session: %s",
            new Object[] {
              routerRecord.mPackageName, Integer.valueOf(routerRecord.mRouterId), str
            }));
    routerRecord.mUserRecord.mHandler.sendMessage(
        PooledLambda.obtainMessage(
            new MediaRouter2ServiceImpl$$ExternalSyntheticLambda1(),
            routerRecord.mUserRecord.mHandler,
            -1L,
            routerRecord,
            str));
  }

  public final List getRemoteSessionsLocked(IMediaRouter2Manager iMediaRouter2Manager) {
    ManagerRecord managerRecord =
        (ManagerRecord) this.mAllManagerRecords.get(iMediaRouter2Manager.asBinder());
    if (managerRecord == null) {
      Slog.w("MR2ServiceImpl", "getRemoteSessionLocked: Ignoring unknown manager");
      return Collections.emptyList();
    }
    ArrayList arrayList = new ArrayList();
    Iterator it = managerRecord.mUserRecord.mHandler.mRouteProviders.iterator();
    while (it.hasNext()) {
      MediaRoute2Provider mediaRoute2Provider = (MediaRoute2Provider) it.next();
      if (!mediaRoute2Provider.mIsSystemRouteProvider) {
        arrayList.addAll(mediaRoute2Provider.getSessionInfos());
      }
    }
    return arrayList;
  }

  public final void registerManagerLocked(
      IMediaRouter2Manager iMediaRouter2Manager, int i, int i2, String str, int i3) {
    IBinder asBinder = iMediaRouter2Manager.asBinder();
    if (((ManagerRecord) this.mAllManagerRecords.get(asBinder)) != null) {
      Slog.w(
          "MR2ServiceImpl",
          "registerManagerLocked: Same manager already exists. packageName=" + str);
      return;
    }
    Slog.i(
        "MR2ServiceImpl",
        TextUtils.formatSimple(
            "registerManager | uid: %d, pid: %d, package: %s, user: %d",
            new Object[] {Integer.valueOf(i), Integer.valueOf(i2), str, Integer.valueOf(i3)}));
    this.mContext.enforcePermission(
        "android.permission.MEDIA_CONTENT_CONTROL",
        i2,
        i,
        "Must hold MEDIA_CONTENT_CONTROL permission.");
    UserRecord orCreateUserRecordLocked = getOrCreateUserRecordLocked(i3);
    ManagerRecord managerRecord =
        new ManagerRecord(orCreateUserRecordLocked, iMediaRouter2Manager, i, i2, str);
    try {
      asBinder.linkToDeath(managerRecord, 0);
      orCreateUserRecordLocked.mManagerRecords.add(managerRecord);
      this.mAllManagerRecords.put(asBinder, managerRecord);
      Iterator it = orCreateUserRecordLocked.mRouterRecords.iterator();
      while (it.hasNext()) {
        RouterRecord routerRecord = (RouterRecord) it.next();
        orCreateUserRecordLocked.mHandler.sendMessage(
            PooledLambda.obtainMessage(
                new MediaRouter2ServiceImpl$$ExternalSyntheticLambda4(),
                routerRecord.mUserRecord.mHandler,
                routerRecord.mPackageName,
                routerRecord.mRouteListingPreference));
        routerRecord.mUserRecord.mHandler.sendMessage(
            PooledLambda.obtainMessage(
                new TriConsumer() { // from class:
                  // com.android.server.media.MediaRouter2ServiceImpl$$ExternalSyntheticLambda13
                  public final void accept(Object obj, Object obj2, Object obj3) {
                    ((MediaRouter2ServiceImpl.UserHandler) obj)
                        .notifyDiscoveryPreferenceChangedToManager(
                            (MediaRouter2ServiceImpl.RouterRecord) obj2,
                            (IMediaRouter2Manager) obj3);
                  }
                },
                routerRecord.mUserRecord.mHandler,
                routerRecord,
                iMediaRouter2Manager));
      }
      orCreateUserRecordLocked.mHandler.sendMessage(
          PooledLambda.obtainMessage(
              new BiConsumer() { // from class:
                // com.android.server.media.MediaRouter2ServiceImpl$$ExternalSyntheticLambda14
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                  ((MediaRouter2ServiceImpl.UserHandler) obj)
                      .notifyInitialRoutesToManager((IMediaRouter2Manager) obj2);
                }
              },
              orCreateUserRecordLocked.mHandler,
              iMediaRouter2Manager));
    } catch (RemoteException e) {
      throw new RuntimeException("Media router manager died prematurely.", e);
    }
  }

  public final void unregisterManagerLocked(IMediaRouter2Manager iMediaRouter2Manager, boolean z) {
    ManagerRecord managerRecord =
        (ManagerRecord) this.mAllManagerRecords.remove(iMediaRouter2Manager.asBinder());
    if (managerRecord == null) {
      return;
    }
    UserRecord userRecord = managerRecord.mUserRecord;
    Slog.i(
        "MR2ServiceImpl",
        TextUtils.formatSimple(
            "unregisterManager | package: %s, user: %d, manager: %d",
            new Object[] {
              managerRecord.mPackageName,
              Integer.valueOf(userRecord.mUserId),
              Integer.valueOf(managerRecord.mManagerId)
            }));
    userRecord.mManagerRecords.remove(managerRecord);
    managerRecord.dispose();
    disposeUserIfNeededLocked(userRecord);
  }

  public final void startScanLocked(IMediaRouter2Manager iMediaRouter2Manager) {
    ManagerRecord managerRecord =
        (ManagerRecord) this.mAllManagerRecords.get(iMediaRouter2Manager.asBinder());
    if (managerRecord == null) {
      return;
    }
    Slog.i(
        "MR2ServiceImpl",
        TextUtils.formatSimple(
            "startScan | manager: %d", new Object[] {Integer.valueOf(managerRecord.mManagerId)}));
    managerRecord.startScan();
  }

  public final void stopScanLocked(IMediaRouter2Manager iMediaRouter2Manager) {
    ManagerRecord managerRecord =
        (ManagerRecord) this.mAllManagerRecords.get(iMediaRouter2Manager.asBinder());
    if (managerRecord == null) {
      return;
    }
    Slog.i(
        "MR2ServiceImpl",
        TextUtils.formatSimple(
            "stopScan | manager: %d", new Object[] {Integer.valueOf(managerRecord.mManagerId)}));
    managerRecord.stopScan();
  }

  public final void setRouteVolumeWithManagerLocked(
      int i, IMediaRouter2Manager iMediaRouter2Manager, MediaRoute2Info mediaRoute2Info, int i2) {
    ManagerRecord managerRecord =
        (ManagerRecord) this.mAllManagerRecords.get(iMediaRouter2Manager.asBinder());
    if (managerRecord == null) {
      return;
    }
    Slog.i(
        "MR2ServiceImpl",
        TextUtils.formatSimple(
            "setRouteVolumeWithManager | manager: %d, route: %s, volume: %d",
            new Object[] {
              Integer.valueOf(managerRecord.mManagerId),
              mediaRoute2Info.getId(),
              Integer.valueOf(i2)
            }));
    managerRecord.mUserRecord.mHandler.sendMessage(
        PooledLambda.obtainMessage(
            new MediaRouter2ServiceImpl$$ExternalSyntheticLambda0(),
            managerRecord.mUserRecord.mHandler,
            Long.valueOf(toUniqueRequestId(managerRecord.mManagerId, i)),
            mediaRoute2Info,
            Integer.valueOf(i2)));
  }

  public final void requestCreateSessionWithManagerLocked(
      int i,
      IMediaRouter2Manager iMediaRouter2Manager,
      RoutingSessionInfo routingSessionInfo,
      MediaRoute2Info mediaRoute2Info) {
    ManagerRecord managerRecord =
        (ManagerRecord) this.mAllManagerRecords.get(iMediaRouter2Manager.asBinder());
    if (managerRecord == null) {
      return;
    }
    Slog.i(
        "MR2ServiceImpl",
        TextUtils.formatSimple(
            "requestCreateSessionWithManager | manager: %d, route: %s",
            new Object[] {Integer.valueOf(managerRecord.mManagerId), mediaRoute2Info.getId()}));
    RouterRecord findRouterRecordLocked =
        managerRecord.mUserRecord.findRouterRecordLocked(routingSessionInfo.getClientPackageName());
    if (findRouterRecordLocked == null) {
      Slog.w(
          "MR2ServiceImpl",
          "requestCreateSessionWithManagerLocked: Ignoring session creation for unknown router.");
      try {
        managerRecord.mManager.notifyRequestFailed(i, 0);
        return;
      } catch (RemoteException unused) {
        Slog.w(
            "MR2ServiceImpl",
            "requestCreateSessionWithManagerLocked: Failed to notify failure. Manager probably"
                + " died.");
        return;
      }
    }
    long uniqueRequestId = toUniqueRequestId(managerRecord.mManagerId, i);
    SessionCreationRequest sessionCreationRequest = managerRecord.mLastSessionCreationRequest;
    if (sessionCreationRequest != null) {
      managerRecord.mUserRecord.mHandler.notifyRequestFailedToManager(
          managerRecord.mManager, toOriginalRequestId(sessionCreationRequest.mManagerRequestId), 0);
      managerRecord.mLastSessionCreationRequest = null;
    }
    managerRecord.mLastSessionCreationRequest =
        new SessionCreationRequest(
            findRouterRecordLocked, 0L, uniqueRequestId, routingSessionInfo, mediaRoute2Info);
    findRouterRecordLocked.mUserRecord.mHandler.sendMessage(
        PooledLambda.obtainMessage(
            new HexConsumer() { // from class:
              // com.android.server.media.MediaRouter2ServiceImpl$$ExternalSyntheticLambda20
              public final void accept(
                  Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
                ((MediaRouter2ServiceImpl.UserHandler) obj)
                    .requestRouterCreateSessionOnHandler(
                        ((Long) obj2).longValue(),
                        (MediaRouter2ServiceImpl.RouterRecord) obj3,
                        (MediaRouter2ServiceImpl.ManagerRecord) obj4,
                        (RoutingSessionInfo) obj5,
                        (MediaRoute2Info) obj6);
              }
            },
            findRouterRecordLocked.mUserRecord.mHandler,
            Long.valueOf(uniqueRequestId),
            findRouterRecordLocked,
            managerRecord,
            routingSessionInfo,
            mediaRoute2Info));
  }

  public final void selectRouteWithManagerLocked(
      int i,
      IMediaRouter2Manager iMediaRouter2Manager,
      String str,
      MediaRoute2Info mediaRoute2Info) {
    ManagerRecord managerRecord =
        (ManagerRecord) this.mAllManagerRecords.get(iMediaRouter2Manager.asBinder());
    if (managerRecord == null) {
      return;
    }
    Slog.i(
        "MR2ServiceImpl",
        TextUtils.formatSimple(
            "selectRouteWithManager | manager: %d, session: %s, route: %s",
            new Object[] {
              Integer.valueOf(managerRecord.mManagerId), str, mediaRoute2Info.getId()
            }));
    managerRecord.mUserRecord.mHandler.sendMessage(
        PooledLambda.obtainMessage(
            new MediaRouter2ServiceImpl$$ExternalSyntheticLambda7(),
            managerRecord.mUserRecord.mHandler,
            Long.valueOf(toUniqueRequestId(managerRecord.mManagerId, i)),
            managerRecord.mUserRecord.mHandler.findRouterWithSessionLocked(str),
            str,
            mediaRoute2Info));
  }

  public final void deselectRouteWithManagerLocked(
      int i,
      IMediaRouter2Manager iMediaRouter2Manager,
      String str,
      MediaRoute2Info mediaRoute2Info) {
    ManagerRecord managerRecord =
        (ManagerRecord) this.mAllManagerRecords.get(iMediaRouter2Manager.asBinder());
    if (managerRecord == null) {
      return;
    }
    Slog.i(
        "MR2ServiceImpl",
        TextUtils.formatSimple(
            "deselectRouteWithManager | manager: %d, session: %s, route: %s",
            new Object[] {
              Integer.valueOf(managerRecord.mManagerId), str, mediaRoute2Info.getId()
            }));
    managerRecord.mUserRecord.mHandler.sendMessage(
        PooledLambda.obtainMessage(
            new MediaRouter2ServiceImpl$$ExternalSyntheticLambda6(),
            managerRecord.mUserRecord.mHandler,
            Long.valueOf(toUniqueRequestId(managerRecord.mManagerId, i)),
            managerRecord.mUserRecord.mHandler.findRouterWithSessionLocked(str),
            str,
            mediaRoute2Info));
  }

  public final void transferToRouteWithManagerLocked(
      int i,
      IMediaRouter2Manager iMediaRouter2Manager,
      String str,
      MediaRoute2Info mediaRoute2Info) {
    ManagerRecord managerRecord =
        (ManagerRecord) this.mAllManagerRecords.get(iMediaRouter2Manager.asBinder());
    if (managerRecord == null) {
      return;
    }
    Slog.i(
        "MR2ServiceImpl",
        TextUtils.formatSimple(
            "transferToRouteWithManager | manager: %d, session: %s, route: %s",
            new Object[] {
              Integer.valueOf(managerRecord.mManagerId), str, mediaRoute2Info.getId()
            }));
    managerRecord.mUserRecord.mHandler.sendMessage(
        PooledLambda.obtainMessage(
            new MediaRouter2ServiceImpl$$ExternalSyntheticLambda18(),
            managerRecord.mUserRecord.mHandler,
            Long.valueOf(toUniqueRequestId(managerRecord.mManagerId, i)),
            managerRecord.mUserRecord.mHandler.findRouterWithSessionLocked(str),
            str,
            mediaRoute2Info));
  }

  public final void setSessionVolumeWithManagerLocked(
      int i, IMediaRouter2Manager iMediaRouter2Manager, String str, int i2) {
    ManagerRecord managerRecord =
        (ManagerRecord) this.mAllManagerRecords.get(iMediaRouter2Manager.asBinder());
    if (managerRecord == null) {
      return;
    }
    Slog.i(
        "MR2ServiceImpl",
        TextUtils.formatSimple(
            "setSessionVolumeWithManager | manager: %d, session: %s, volume: %d",
            new Object[] {Integer.valueOf(managerRecord.mManagerId), str, Integer.valueOf(i2)}));
    managerRecord.mUserRecord.mHandler.sendMessage(
        PooledLambda.obtainMessage(
            new MediaRouter2ServiceImpl$$ExternalSyntheticLambda2(),
            managerRecord.mUserRecord.mHandler,
            Long.valueOf(toUniqueRequestId(managerRecord.mManagerId, i)),
            str,
            Integer.valueOf(i2)));
  }

  public final void releaseSessionWithManagerLocked(
      int i, IMediaRouter2Manager iMediaRouter2Manager, String str) {
    ManagerRecord managerRecord =
        (ManagerRecord) this.mAllManagerRecords.get(iMediaRouter2Manager.asBinder());
    if (managerRecord == null) {
      return;
    }
    Slog.i(
        "MR2ServiceImpl",
        TextUtils.formatSimple(
            "releaseSessionWithManager | manager: %d, session: %s",
            new Object[] {Integer.valueOf(managerRecord.mManagerId), str}));
    managerRecord.mUserRecord.mHandler.sendMessage(
        PooledLambda.obtainMessage(
            new MediaRouter2ServiceImpl$$ExternalSyntheticLambda1(),
            managerRecord.mUserRecord.mHandler,
            Long.valueOf(toUniqueRequestId(managerRecord.mManagerId, i)),
            managerRecord.mUserRecord.mHandler.findRouterWithSessionLocked(str),
            str));
  }

  public final UserRecord getOrCreateUserRecordLocked(int i) {
    UserRecord userRecord = (UserRecord) this.mUserRecords.get(i);
    if (userRecord == null) {
      userRecord = new UserRecord(i);
      this.mUserRecords.put(i, userRecord);
      userRecord.init();
      if (isUserActiveLocked(i)) {
        userRecord.mHandler.sendMessage(
            PooledLambda.obtainMessage(
                new MediaRouter2ServiceImpl$$ExternalSyntheticLambda8(), userRecord.mHandler));
      }
    }
    return userRecord;
  }

  public final void disposeUserIfNeededLocked(UserRecord userRecord) {
    if (!isUserActiveLocked(userRecord.mUserId)
        && userRecord.mRouterRecords.isEmpty()
        && userRecord.mManagerRecords.isEmpty()) {
      if (DEBUG) {
        Slog.d("MR2ServiceImpl", userRecord + ": Disposed");
      }
      userRecord.mHandler.sendMessage(
          PooledLambda.obtainMessage(
              new MediaRouter2ServiceImpl$$ExternalSyntheticLambda9(), userRecord.mHandler));
      this.mUserRecords.remove(userRecord.mUserId);
    }
  }

  public final void onDeviceConfigChange(DeviceConfig.Properties properties) {
    sPackageImportanceForScanning = properties.getInt("scanning_package_minimum_importance", 125);
  }

  public final class UserRecord {
    public final UserHandler mHandler;
    public final int mUserId;
    public final ArrayList mRouterRecords = new ArrayList();
    public final ArrayList mManagerRecords = new ArrayList();
    public RouteDiscoveryPreference mCompositeDiscoveryPreference = RouteDiscoveryPreference.EMPTY;
    public Set mActivelyScanningPackages = Set.of();

    public UserRecord(int i) {
      this.mUserId = i;
      this.mHandler = new UserHandler(MediaRouter2ServiceImpl.this, this);
    }

    public void init() {
      this.mHandler.init();
    }

    public RouterRecord findRouterRecordLocked(String str) {
      Iterator it = this.mRouterRecords.iterator();
      while (it.hasNext()) {
        RouterRecord routerRecord = (RouterRecord) it.next();
        if (TextUtils.equals(routerRecord.mPackageName, str)) {
          return routerRecord;
        }
      }
      return null;
    }

    public void dump(final PrintWriter printWriter, String str) {
      printWriter.println(str + "UserRecord");
      final String str2 = str + "  ";
      printWriter.println(str2 + "mUserId=" + this.mUserId);
      StringBuilder sb = new StringBuilder();
      sb.append(str2);
      sb.append("Router Records:");
      printWriter.println(sb.toString());
      if (!this.mRouterRecords.isEmpty()) {
        Iterator it = this.mRouterRecords.iterator();
        while (it.hasNext()) {
          ((RouterRecord) it.next()).dump(printWriter, str2 + "  ");
        }
      } else {
        printWriter.println(str2 + "<no router records>");
      }
      printWriter.println(str2 + "Manager Records:");
      if (!this.mManagerRecords.isEmpty()) {
        Iterator it2 = this.mManagerRecords.iterator();
        while (it2.hasNext()) {
          ((ManagerRecord) it2.next()).dump(printWriter, str2 + "  ");
        }
      } else {
        printWriter.println(str2 + "<no manager records>");
      }
      printWriter.println(str2 + "Composite discovery preference:");
      this.mCompositeDiscoveryPreference.dump(printWriter, str2 + "  ");
      printWriter.println(
          str2
              + "Packages actively scanning: "
              + String.join(", ", this.mActivelyScanningPackages));
      if (this.mHandler.runWithScissors(
          new Runnable() { // from class:
            // com.android.server.media.MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              MediaRouter2ServiceImpl.UserRecord.this.lambda$dump$0(printWriter, str2);
            }
          },
          1000L)) {
        return;
      }
      printWriter.println(str2 + "<could not dump handler state>");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dump$0(PrintWriter printWriter, String str) {
      this.mHandler.dump(printWriter, str);
    }
  }

  public final class RouterRecord implements IBinder.DeathRecipient {
    public final boolean mHasConfigureWifiDisplayPermission;
    public final boolean mHasModifyAudioRoutingPermission;
    public final String mPackageName;
    public final int mPid;
    public RouteListingPreference mRouteListingPreference;
    public final IMediaRouter2 mRouter;
    public final int mRouterId;
    public final int mUid;
    public final UserRecord mUserRecord;
    public final List mSelectRouteSequenceNumbers = new ArrayList();
    public RouteDiscoveryPreference mDiscoveryPreference = RouteDiscoveryPreference.EMPTY;
    public final AtomicBoolean mHasBluetoothRoutingPermission =
        new AtomicBoolean(fetchBluetoothPermission());

    public RouterRecord(
        UserRecord userRecord,
        IMediaRouter2 iMediaRouter2,
        int i,
        int i2,
        String str,
        boolean z,
        boolean z2) {
      this.mUserRecord = userRecord;
      this.mPackageName = str;
      this.mRouter = iMediaRouter2;
      this.mUid = i;
      this.mPid = i2;
      this.mHasConfigureWifiDisplayPermission = z;
      this.mHasModifyAudioRoutingPermission = z2;
      this.mRouterId = MediaRouter2ServiceImpl.this.mNextRouterOrManagerId.getAndIncrement();
    }

    public final boolean fetchBluetoothPermission() {
      boolean z = true;
      for (String str : MediaRouter2ServiceImpl.BLUETOOTH_PERMISSIONS_FOR_SYSTEM_ROUTING) {
        z &= MediaRouter2ServiceImpl.this.mContext.checkPermission(str, this.mPid, this.mUid) == 0;
      }
      return z;
    }

    public boolean hasSystemRoutingPermission() {
      return this.mHasModifyAudioRoutingPermission || this.mHasBluetoothRoutingPermission.get();
    }

    public void maybeUpdateSystemRoutingPermissionLocked() {
      Map map;
      RoutingSessionInfo defaultSessionInfo;
      boolean hasSystemRoutingPermission = hasSystemRoutingPermission();
      this.mHasBluetoothRoutingPermission.set(fetchBluetoothPermission());
      boolean hasSystemRoutingPermission2 = hasSystemRoutingPermission();
      if (hasSystemRoutingPermission != hasSystemRoutingPermission2) {
        if (hasSystemRoutingPermission2) {
          map = this.mUserRecord.mHandler.mLastNotifiedRoutesToPrivilegedRouters;
        } else {
          map = this.mUserRecord.mHandler.mLastNotifiedRoutesToNonPrivilegedRouters;
        }
        notifyRoutesUpdated(map.values().stream().toList());
        List sessionInfos = this.mUserRecord.mHandler.mSystemProvider.getSessionInfos();
        if (hasSystemRoutingPermission2 && !sessionInfos.isEmpty()) {
          defaultSessionInfo = (RoutingSessionInfo) sessionInfos.get(0);
        } else {
          defaultSessionInfo = this.mUserRecord.mHandler.mSystemProvider.getDefaultSessionInfo();
        }
        notifySessionInfoChanged(defaultSessionInfo);
      }
    }

    public void dispose() {
      this.mRouter.asBinder().unlinkToDeath(this, 0);
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
      MediaRouter2ServiceImpl.this.routerDied(this);
    }

    public void dump(PrintWriter printWriter, String str) {
      printWriter.println(str + "RouterRecord");
      String str2 = str + "  ";
      printWriter.println(str2 + "mPackageName=" + this.mPackageName);
      printWriter.println(str2 + "mSelectRouteSequenceNumbers=" + this.mSelectRouteSequenceNumbers);
      printWriter.println(str2 + "mUid=" + this.mUid);
      printWriter.println(str2 + "mPid=" + this.mPid);
      printWriter.println(
          str2 + "mHasConfigureWifiDisplayPermission=" + this.mHasConfigureWifiDisplayPermission);
      printWriter.println(
          str2 + "mHasModifyAudioRoutingPermission=" + this.mHasModifyAudioRoutingPermission);
      printWriter.println(
          str2 + "mHasBluetoothRoutingPermission=" + this.mHasBluetoothRoutingPermission.get());
      printWriter.println(str2 + "hasSystemRoutingPermission=" + hasSystemRoutingPermission());
      printWriter.println(str2 + "mRouterId=" + this.mRouterId);
      this.mDiscoveryPreference.dump(printWriter, str2);
    }

    public void notifyRegistered(List list, RoutingSessionInfo routingSessionInfo) {
      try {
        this.mRouter.notifyRouterRegistered(getVisibleRoutes(list), routingSessionInfo);
      } catch (RemoteException e) {
        Slog.w("MR2ServiceImpl", "Failed to notify router registered. Router probably died.", e);
      }
    }

    public void notifyRoutesUpdated(List list) {
      try {
        this.mRouter.notifyRoutesUpdated(getVisibleRoutes(list));
      } catch (RemoteException e) {
        Slog.w("MR2ServiceImpl", "Failed to notify routes updated. Router probably died.", e);
      }
    }

    public void notifySessionInfoChanged(RoutingSessionInfo routingSessionInfo) {
      try {
        this.mRouter.notifySessionInfoChanged(routingSessionInfo);
      } catch (RemoteException e) {
        Slog.w("MR2ServiceImpl", "Failed to notify session info changed. Router probably died.", e);
      }
    }

    public final List getVisibleRoutes(List list) {
      ArrayList arrayList = new ArrayList();
      Iterator it = list.iterator();
      while (it.hasNext()) {
        MediaRoute2Info mediaRoute2Info = (MediaRoute2Info) it.next();
        if (mediaRoute2Info.isVisibleTo(this.mPackageName)) {
          arrayList.add(mediaRoute2Info);
        }
      }
      return arrayList;
    }
  }

  public final class ManagerRecord implements IBinder.DeathRecipient {
    public boolean mIsScanning;
    public SessionCreationRequest mLastSessionCreationRequest;
    public final IMediaRouter2Manager mManager;
    public final int mManagerId;
    public final String mPackageName;
    public final int mPid;
    public final int mUid;
    public final UserRecord mUserRecord;

    public ManagerRecord(
        UserRecord userRecord,
        IMediaRouter2Manager iMediaRouter2Manager,
        int i,
        int i2,
        String str) {
      this.mUserRecord = userRecord;
      this.mManager = iMediaRouter2Manager;
      this.mUid = i;
      this.mPid = i2;
      this.mPackageName = str;
      this.mManagerId = MediaRouter2ServiceImpl.this.mNextRouterOrManagerId.getAndIncrement();
    }

    public void dispose() {
      this.mManager.asBinder().unlinkToDeath(this, 0);
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
      MediaRouter2ServiceImpl.this.managerDied(this);
    }

    public void dump(PrintWriter printWriter, String str) {
      printWriter.println(str + "ManagerRecord");
      String str2 = str + "  ";
      printWriter.println(str2 + "mPackageName=" + this.mPackageName);
      printWriter.println(str2 + "mManagerId=" + this.mManagerId);
      printWriter.println(str2 + "mUid=" + this.mUid);
      printWriter.println(str2 + "mPid=" + this.mPid);
      printWriter.println(str2 + "mIsScanning=" + this.mIsScanning);
      SessionCreationRequest sessionCreationRequest = this.mLastSessionCreationRequest;
      if (sessionCreationRequest != null) {
        sessionCreationRequest.dump(printWriter, str2);
      }
    }

    public void startScan() {
      if (this.mIsScanning) {
        return;
      }
      this.mIsScanning = true;
      this.mUserRecord.mHandler.sendMessage(
          PooledLambda.obtainMessage(
              new MediaRouter2ServiceImpl$ManagerRecord$$ExternalSyntheticLambda0(),
              this.mUserRecord.mHandler));
    }

    public void stopScan() {
      if (this.mIsScanning) {
        this.mIsScanning = false;
        this.mUserRecord.mHandler.sendMessage(
            PooledLambda.obtainMessage(
                new MediaRouter2ServiceImpl$ManagerRecord$$ExternalSyntheticLambda0(),
                this.mUserRecord.mHandler));
      }
    }

    public String toString() {
      return "Manager " + this.mPackageName + " (pid " + this.mPid + ")";
    }
  }

  public final class UserHandler extends Handler
      implements MediaRoute2ProviderWatcher.Callback, MediaRoute2Provider.Callback {
    public final Map mLastNotifiedRoutesToNonPrivilegedRouters;
    public final Map mLastNotifiedRoutesToPrivilegedRouters;
    public final List mLastProviderInfos;
    public final ArrayList mRouteProviders;
    public boolean mRunning;
    public final WeakReference mServiceRef;
    public final CopyOnWriteArrayList mSessionCreationRequests;
    public final Map mSessionToRouterMap;
    public final SystemMediaRoute2Provider mSystemProvider;
    public final UserRecord mUserRecord;
    public final MediaRoute2ProviderWatcher mWatcher;

    /* renamed from: -$$Nest$mdeselectRouteOnHandler, reason: not valid java name */
    public static /* bridge */ /* synthetic */ void m8453$$Nest$mdeselectRouteOnHandler(
        UserHandler userHandler,
        long j,
        RouterRecord routerRecord,
        String str,
        MediaRoute2Info mediaRoute2Info) {
      userHandler.deselectRouteOnHandler(j, routerRecord, str, mediaRoute2Info);
    }

    /* renamed from: -$$Nest$mnotifyRouteListingPreferenceChangeToManagers, reason: not valid java name */
    public static /* bridge */ /* synthetic */ void
        m8458$$Nest$mnotifyRouteListingPreferenceChangeToManagers(
            UserHandler userHandler, String str, RouteListingPreference routeListingPreference) {
      userHandler.notifyRouteListingPreferenceChangeToManagers(str, routeListingPreference);
    }

    /* renamed from: -$$Nest$mselectRouteOnHandler, reason: not valid java name */
    public static /* bridge */ /* synthetic */ void m8464$$Nest$mselectRouteOnHandler(
        UserHandler userHandler,
        long j,
        RouterRecord routerRecord,
        String str,
        MediaRoute2Info mediaRoute2Info) {
      userHandler.selectRouteOnHandler(j, routerRecord, str, mediaRoute2Info);
    }

    /* renamed from: -$$Nest$msetSessionVolumeOnHandler, reason: not valid java name */
    public static /* bridge */ /* synthetic */ void m8466$$Nest$msetSessionVolumeOnHandler(
        UserHandler userHandler, long j, String str, int i) {
      userHandler.setSessionVolumeOnHandler(j, str, i);
    }

    /* renamed from: -$$Nest$mstart, reason: not valid java name */
    public static /* bridge */ /* synthetic */ void m8467$$Nest$mstart(UserHandler userHandler) {
      userHandler.start();
    }

    /* renamed from: -$$Nest$mstop, reason: not valid java name */
    public static /* bridge */ /* synthetic */ void m8468$$Nest$mstop(UserHandler userHandler) {
      userHandler.stop();
    }

    /* renamed from: -$$Nest$mupdateDiscoveryPreferenceOnHandler, reason: not valid java name */
    public static /* bridge */ /* synthetic */ void m8470$$Nest$mupdateDiscoveryPreferenceOnHandler(
        UserHandler userHandler) {
      userHandler.updateDiscoveryPreferenceOnHandler();
    }

    public UserHandler(MediaRouter2ServiceImpl mediaRouter2ServiceImpl, UserRecord userRecord) {
      super(Looper.getMainLooper(), null, true);
      ArrayList arrayList = new ArrayList();
      this.mRouteProviders = arrayList;
      this.mLastProviderInfos = new ArrayList();
      this.mSessionCreationRequests = new CopyOnWriteArrayList();
      this.mSessionToRouterMap = new ArrayMap();
      this.mLastNotifiedRoutesToPrivilegedRouters = new ArrayMap();
      this.mLastNotifiedRoutesToNonPrivilegedRouters = new ArrayMap();
      this.mServiceRef = new WeakReference(mediaRouter2ServiceImpl);
      this.mUserRecord = userRecord;
      SystemMediaRoute2Provider systemMediaRoute2Provider =
          new SystemMediaRoute2Provider(
              mediaRouter2ServiceImpl.mContext, UserHandle.of(userRecord.mUserId));
      this.mSystemProvider = systemMediaRoute2Provider;
      arrayList.add(systemMediaRoute2Provider);
      this.mWatcher =
          new MediaRoute2ProviderWatcher(
              mediaRouter2ServiceImpl.mContext, this, this, userRecord.mUserId);
    }

    public void init() {
      this.mSystemProvider.setCallback(this);
    }

    public final void start() {
      if (this.mRunning) {
        return;
      }
      this.mRunning = true;
      this.mSystemProvider.start();
      this.mWatcher.start();
    }

    public final void stop() {
      if (this.mRunning) {
        this.mRunning = false;
        this.mWatcher.stop();
        this.mSystemProvider.stop();
      }
    }

    @Override // com.android.server.media.MediaRoute2ProviderWatcher.Callback
    public void onAddProviderService(
        MediaRoute2ProviderServiceProxy mediaRoute2ProviderServiceProxy) {
      mediaRoute2ProviderServiceProxy.setCallback(this);
      this.mRouteProviders.add(mediaRoute2ProviderServiceProxy);
      UserRecord userRecord = this.mUserRecord;
      mediaRoute2ProviderServiceProxy.updateDiscoveryPreference(
          userRecord.mActivelyScanningPackages, userRecord.mCompositeDiscoveryPreference);
    }

    @Override // com.android.server.media.MediaRoute2ProviderWatcher.Callback
    public void onRemoveProviderService(
        MediaRoute2ProviderServiceProxy mediaRoute2ProviderServiceProxy) {
      this.mRouteProviders.remove(mediaRoute2ProviderServiceProxy);
    }

    @Override // com.android.server.media.MediaRoute2Provider.Callback
    public void onProviderStateChanged(MediaRoute2Provider mediaRoute2Provider) {
      sendMessage(
          PooledLambda.obtainMessage(
              new BiConsumer() { // from class:
                // com.android.server.media.MediaRouter2ServiceImpl$UserHandler$$ExternalSyntheticLambda8
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                  ((MediaRouter2ServiceImpl.UserHandler) obj)
                      .onProviderStateChangedOnHandler((MediaRoute2Provider) obj2);
                }
              },
              this,
              mediaRoute2Provider));
    }

    @Override // com.android.server.media.MediaRoute2Provider.Callback
    public void onSessionCreated(
        MediaRoute2Provider mediaRoute2Provider, long j, RoutingSessionInfo routingSessionInfo) {
      sendMessage(
          PooledLambda.obtainMessage(
              new QuadConsumer() { // from class:
                // com.android.server.media.MediaRouter2ServiceImpl$UserHandler$$ExternalSyntheticLambda9
                public final void accept(Object obj, Object obj2, Object obj3, Object obj4) {
                  ((MediaRouter2ServiceImpl.UserHandler) obj)
                      .onSessionCreatedOnHandler(
                          (MediaRoute2Provider) obj2,
                          ((Long) obj3).longValue(),
                          (RoutingSessionInfo) obj4);
                }
              },
              this,
              mediaRoute2Provider,
              Long.valueOf(j),
              routingSessionInfo));
    }

    @Override // com.android.server.media.MediaRoute2Provider.Callback
    public void onSessionUpdated(
        MediaRoute2Provider mediaRoute2Provider, RoutingSessionInfo routingSessionInfo) {
      sendMessage(
          PooledLambda.obtainMessage(
              new TriConsumer() { // from class:
                // com.android.server.media.MediaRouter2ServiceImpl$UserHandler$$ExternalSyntheticLambda7
                public final void accept(Object obj, Object obj2, Object obj3) {
                  ((MediaRouter2ServiceImpl.UserHandler) obj)
                      .onSessionInfoChangedOnHandler(
                          (MediaRoute2Provider) obj2, (RoutingSessionInfo) obj3);
                }
              },
              this,
              mediaRoute2Provider,
              routingSessionInfo));
    }

    @Override // com.android.server.media.MediaRoute2Provider.Callback
    public void onSessionReleased(
        MediaRoute2Provider mediaRoute2Provider, RoutingSessionInfo routingSessionInfo) {
      sendMessage(
          PooledLambda.obtainMessage(
              new TriConsumer() { // from class:
                // com.android.server.media.MediaRouter2ServiceImpl$UserHandler$$ExternalSyntheticLambda2
                public final void accept(Object obj, Object obj2, Object obj3) {
                  ((MediaRouter2ServiceImpl.UserHandler) obj)
                      .onSessionReleasedOnHandler(
                          (MediaRoute2Provider) obj2, (RoutingSessionInfo) obj3);
                }
              },
              this,
              mediaRoute2Provider,
              routingSessionInfo));
    }

    @Override // com.android.server.media.MediaRoute2Provider.Callback
    public void onRequestFailed(MediaRoute2Provider mediaRoute2Provider, long j, int i) {
      sendMessage(
          PooledLambda.obtainMessage(
              new QuadConsumer() { // from class:
                // com.android.server.media.MediaRouter2ServiceImpl$UserHandler$$ExternalSyntheticLambda3
                public final void accept(Object obj, Object obj2, Object obj3, Object obj4) {
                  ((MediaRouter2ServiceImpl.UserHandler) obj)
                      .onRequestFailedOnHandler(
                          (MediaRoute2Provider) obj2,
                          ((Long) obj3).longValue(),
                          ((Integer) obj4).intValue());
                }
              },
              this,
              mediaRoute2Provider,
              Long.valueOf(j),
              Integer.valueOf(i)));
    }

    public RouterRecord findRouterWithSessionLocked(String str) {
      return (RouterRecord) this.mSessionToRouterMap.get(str);
    }

    public ManagerRecord findManagerWithId(int i) {
      for (ManagerRecord managerRecord : getManagerRecords()) {
        if (managerRecord.mManagerId == i) {
          return managerRecord;
        }
      }
      return null;
    }

    public void maybeUpdateDiscoveryPreferenceForUid(final int i) {
      boolean anyMatch;
      MediaRouter2ServiceImpl mediaRouter2ServiceImpl =
          (MediaRouter2ServiceImpl) this.mServiceRef.get();
      if (mediaRouter2ServiceImpl == null) {
        return;
      }
      synchronized (mediaRouter2ServiceImpl.mLock) {
        anyMatch =
            this.mUserRecord.mManagerRecords.stream()
                    .anyMatch(
                        new Predicate() { // from class:
                          // com.android.server.media.MediaRouter2ServiceImpl$UserHandler$$ExternalSyntheticLambda5
                          @Override // java.util.function.Predicate
                          public final boolean test(Object obj) {
                            boolean lambda$maybeUpdateDiscoveryPreferenceForUid$1;
                            lambda$maybeUpdateDiscoveryPreferenceForUid$1 =
                                MediaRouter2ServiceImpl.UserHandler
                                    .lambda$maybeUpdateDiscoveryPreferenceForUid$1(
                                        i, (MediaRouter2ServiceImpl.ManagerRecord) obj);
                            return lambda$maybeUpdateDiscoveryPreferenceForUid$1;
                          }
                        })
                | this.mUserRecord.mRouterRecords.stream()
                    .anyMatch(
                        new Predicate() { // from class:
                          // com.android.server.media.MediaRouter2ServiceImpl$UserHandler$$ExternalSyntheticLambda4
                          @Override // java.util.function.Predicate
                          public final boolean test(Object obj) {
                            boolean lambda$maybeUpdateDiscoveryPreferenceForUid$0;
                            lambda$maybeUpdateDiscoveryPreferenceForUid$0 =
                                MediaRouter2ServiceImpl.UserHandler
                                    .lambda$maybeUpdateDiscoveryPreferenceForUid$0(
                                        i, (MediaRouter2ServiceImpl.RouterRecord) obj);
                            return lambda$maybeUpdateDiscoveryPreferenceForUid$0;
                          }
                        });
      }
      if (anyMatch) {
        sendMessage(
            PooledLambda.obtainMessage(
                new Consumer() { // from class:
                  // com.android.server.media.MediaRouter2ServiceImpl$UserHandler$$ExternalSyntheticLambda6
                  @Override // java.util.function.Consumer
                  public final void accept(Object obj) {
                    ((MediaRouter2ServiceImpl.UserHandler) obj)
                        .updateDiscoveryPreferenceOnHandler();
                  }
                },
                this));
      }
    }

    public static /* synthetic */ boolean lambda$maybeUpdateDiscoveryPreferenceForUid$0(
        int i, RouterRecord routerRecord) {
      return routerRecord.mUid == i;
    }

    public static /* synthetic */ boolean lambda$maybeUpdateDiscoveryPreferenceForUid$1(
        int i, ManagerRecord managerRecord) {
      return managerRecord.mUid == i;
    }

    public void dump(PrintWriter printWriter, String str) {
      printWriter.println(str + "UserHandler");
      printWriter.println((str + "  ") + "mRunning=" + this.mRunning);
      this.mSystemProvider.dump(printWriter, str);
      this.mWatcher.dump(printWriter, str);
    }

    public final void onProviderStateChangedOnHandler(MediaRoute2Provider mediaRoute2Provider) {
      Set emptySet;
      Collection<MediaRoute2Info> emptySet2;
      MediaRoute2ProviderInfo providerInfo = mediaRoute2Provider.getProviderInfo();
      int indexOfRouteProviderInfoByUniqueId =
          indexOfRouteProviderInfoByUniqueId(
              mediaRoute2Provider.getUniqueId(), this.mLastProviderInfos);
      MediaRoute2ProviderInfo mediaRoute2ProviderInfo =
          indexOfRouteProviderInfoByUniqueId == -1
              ? null
              : (MediaRoute2ProviderInfo)
                  this.mLastProviderInfos.get(indexOfRouteProviderInfoByUniqueId);
      if (mediaRoute2ProviderInfo == providerInfo) {
        return;
      }
      if (providerInfo != null) {
        emptySet2 = providerInfo.getRoutes();
        emptySet =
            (Set)
                emptySet2.stream()
                    .map(
                        new Function() { // from class:
                          // com.android.server.media.MediaRouter2ServiceImpl$UserHandler$$ExternalSyntheticLambda10
                          @Override // java.util.function.Function
                          public final Object apply(Object obj) {
                            return ((MediaRoute2Info) obj).getId();
                          }
                        })
                    .collect(Collectors.toSet());
        if (indexOfRouteProviderInfoByUniqueId >= 0) {
          this.mLastProviderInfos.set(indexOfRouteProviderInfoByUniqueId, providerInfo);
        } else {
          this.mLastProviderInfos.add(providerInfo);
        }
      } else {
        this.mLastProviderInfos.remove(mediaRoute2ProviderInfo);
        emptySet = Collections.emptySet();
        emptySet2 = Collections.emptySet();
      }
      ArrayList arrayList = new ArrayList();
      boolean z = false;
      boolean z2 = false;
      for (MediaRoute2Info mediaRoute2Info : emptySet2) {
        if (mediaRoute2Info.isValid()) {
          if (!mediaRoute2Provider.mIsSystemRouteProvider) {
            this.mLastNotifiedRoutesToNonPrivilegedRouters.put(
                mediaRoute2Info.getId(), mediaRoute2Info);
          }
          MediaRoute2Info mediaRoute2Info2 =
              (MediaRoute2Info)
                  this.mLastNotifiedRoutesToPrivilegedRouters.put(
                      mediaRoute2Info.getId(), mediaRoute2Info);
          z2 |= true ^ mediaRoute2Info.equals(mediaRoute2Info2);
          if (mediaRoute2Info2 == null) {
            arrayList.add(mediaRoute2Info);
          }
        } else {
          Slog.w(
              "MR2ServiceImpl",
              "onProviderStateChangedOnHandler: Ignoring invalid route : " + mediaRoute2Info);
        }
      }
      ArrayList arrayList2 = new ArrayList();
      for (MediaRoute2Info mediaRoute2Info3 :
          mediaRoute2ProviderInfo == null
              ? Collections.emptyList()
              : mediaRoute2ProviderInfo.getRoutes()) {
        String id = mediaRoute2Info3.getId();
        if (!emptySet.contains(id)) {
          this.mLastNotifiedRoutesToPrivilegedRouters.remove(id);
          this.mLastNotifiedRoutesToNonPrivilegedRouters.remove(id);
          arrayList2.add(mediaRoute2Info3);
          z = true;
        }
      }
      if (!arrayList.isEmpty()) {
        Slog.i(
            "MR2ServiceImpl",
            toLoggingMessage("addProviderRoutes", providerInfo.getUniqueId(), arrayList));
      }
      if (!arrayList2.isEmpty()) {
        Slog.i(
            "MR2ServiceImpl",
            toLoggingMessage(
                "removeProviderRoutes", mediaRoute2ProviderInfo.getUniqueId(), arrayList2));
      }
      dispatchUpdates(
          z2,
          z,
          mediaRoute2Provider.mIsSystemRouteProvider,
          this.mSystemProvider.getDefaultRoute());
    }

    public static String toLoggingMessage(String str, String str2, ArrayList arrayList) {
      return TextUtils.formatSimple(
          "%s | provider: %s, routes: [%s]",
          new Object[] {
            str,
            str2,
            (String)
                arrayList.stream()
                    .map(
                        new Function() { // from class:
                          // com.android.server.media.MediaRouter2ServiceImpl$UserHandler$$ExternalSyntheticLambda11
                          @Override // java.util.function.Function
                          public final Object apply(Object obj) {
                            String lambda$toLoggingMessage$2;
                            lambda$toLoggingMessage$2 =
                                MediaRouter2ServiceImpl.UserHandler.lambda$toLoggingMessage$2(
                                    (MediaRoute2Info) obj);
                            return lambda$toLoggingMessage$2;
                          }
                        })
                    .collect(Collectors.joining(", "))
          });
    }

    public static /* synthetic */ String lambda$toLoggingMessage$2(
        MediaRoute2Info mediaRoute2Info) {
      return String.format("%s | %s", mediaRoute2Info.getOriginalId(), mediaRoute2Info.getName());
    }

    public final void dispatchUpdates(
        boolean z, boolean z2, boolean z3, MediaRoute2Info mediaRoute2Info) {
      if (z || z2) {
        List routerRecords = getRouterRecords(true);
        List routerRecords2 = getRouterRecords(false);
        notifyRoutesUpdatedToManagers(
            getManagers(), new ArrayList(this.mLastNotifiedRoutesToPrivilegedRouters.values()));
        notifyRoutesUpdatedToRouterRecords(
            routerRecords, new ArrayList(this.mLastNotifiedRoutesToPrivilegedRouters.values()));
        if (!z3) {
          notifyRoutesUpdatedToRouterRecords(
              routerRecords2,
              new ArrayList(this.mLastNotifiedRoutesToNonPrivilegedRouters.values()));
        } else if (z) {
          this.mLastNotifiedRoutesToNonPrivilegedRouters.put(
              mediaRoute2Info.getId(), mediaRoute2Info);
          notifyRoutesUpdatedToRouterRecords(
              routerRecords2,
              new ArrayList(this.mLastNotifiedRoutesToNonPrivilegedRouters.values()));
        }
      }
    }

    public static int indexOfRouteProviderInfoByUniqueId(String str, List list) {
      for (int i = 0; i < list.size(); i++) {
        if (TextUtils.equals(((MediaRoute2ProviderInfo) list.get(i)).getUniqueId(), str)) {
          return i;
        }
      }
      return -1;
    }

    public final void requestRouterCreateSessionOnHandler(
        long j,
        RouterRecord routerRecord,
        ManagerRecord managerRecord,
        RoutingSessionInfo routingSessionInfo,
        MediaRoute2Info mediaRoute2Info) {
      try {
        if (mediaRoute2Info.isSystemRoute() && !routerRecord.hasSystemRoutingPermission()) {
          mediaRoute2Info = this.mSystemProvider.getDefaultRoute();
        }
        routerRecord.mRouter.requestCreateSessionByManager(j, routingSessionInfo, mediaRoute2Info);
      } catch (RemoteException e) {
        Slog.w(
            "MR2ServiceImpl",
            "getSessionHintsForCreatingSessionOnHandler: Failed to request. Router probably died.",
            e);
        notifyRequestFailedToManager(
            managerRecord.mManager, MediaRouter2ServiceImpl.toOriginalRequestId(j), 0);
      }
    }

    public final void requestCreateSessionWithRouter2OnHandler(
        long j,
        long j2,
        RouterRecord routerRecord,
        RoutingSessionInfo routingSessionInfo,
        MediaRoute2Info mediaRoute2Info,
        Bundle bundle) {
      MediaRoute2Provider findProvider = findProvider(mediaRoute2Info.getProviderId());
      if (findProvider == null) {
        Slog.w(
            "MR2ServiceImpl",
            "requestCreateSessionWithRouter2OnHandler: Ignoring session creation request since no"
                + " provider found for given route="
                + mediaRoute2Info);
        notifySessionCreationFailedToRouter(
            routerRecord, MediaRouter2ServiceImpl.toOriginalRequestId(j));
        return;
      }
      this.mSessionCreationRequests.add(
          new SessionCreationRequest(routerRecord, j, j2, routingSessionInfo, mediaRoute2Info));
      findProvider.requestCreateSession(
          j, routerRecord.mPackageName, mediaRoute2Info.getOriginalId(), bundle);
    }

    public final void selectRouteOnHandler(
        long j, RouterRecord routerRecord, String str, MediaRoute2Info mediaRoute2Info) {
      MediaRoute2Provider findProvider;
      if (checkArgumentsForSessionControl(routerRecord, str, mediaRoute2Info, "selecting")
          && (findProvider = findProvider(mediaRoute2Info.getProviderId())) != null) {
        findProvider.selectRoute(
            j, MediaRouter2Utils.getOriginalId(str), mediaRoute2Info.getOriginalId());
      }
    }

    public final void deselectRouteOnHandler(
        long j, RouterRecord routerRecord, String str, MediaRoute2Info mediaRoute2Info) {
      MediaRoute2Provider findProvider;
      if (checkArgumentsForSessionControl(routerRecord, str, mediaRoute2Info, "deselecting")
          && (findProvider = findProvider(mediaRoute2Info.getProviderId())) != null) {
        findProvider.deselectRoute(
            j, MediaRouter2Utils.getOriginalId(str), mediaRoute2Info.getOriginalId());
      }
    }

    public final void transferToRouteOnHandler(
        long j, RouterRecord routerRecord, String str, MediaRoute2Info mediaRoute2Info) {
      MediaRoute2Provider findProvider;
      if (checkArgumentsForSessionControl(routerRecord, str, mediaRoute2Info, "transferring to")
          && (findProvider = findProvider(mediaRoute2Info.getProviderId())) != null) {
        findProvider.transferToRoute(
            j, MediaRouter2Utils.getOriginalId(str), mediaRoute2Info.getOriginalId());
      }
    }

    public final boolean checkArgumentsForSessionControl(
        RouterRecord routerRecord, String str, MediaRoute2Info mediaRoute2Info, String str2) {
      if (findProvider(mediaRoute2Info.getProviderId()) == null) {
        Slog.w(
            "MR2ServiceImpl",
            "Ignoring "
                + str2
                + " route since no provider found for given route="
                + mediaRoute2Info);
        return false;
      }
      if (TextUtils.equals(
          MediaRouter2Utils.getProviderId(str), this.mSystemProvider.getUniqueId())) {
        return true;
      }
      if (((RouterRecord) this.mSessionToRouterMap.get(str)) != routerRecord) {
        Slog.w(
            "MR2ServiceImpl",
            "Ignoring "
                + str2
                + " route from non-matching router. packageName="
                + routerRecord.mPackageName
                + " route="
                + mediaRoute2Info);
        return false;
      }
      if (MediaRouter2Utils.getOriginalId(str) != null) {
        return true;
      }
      Slog.w(
          "MR2ServiceImpl",
          "Failed to get original session id from unique session id. uniqueSessionId=" + str);
      return false;
    }

    public final void setRouteVolumeOnHandler(long j, MediaRoute2Info mediaRoute2Info, int i) {
      MediaRoute2Provider findProvider = findProvider(mediaRoute2Info.getProviderId());
      if (findProvider == null) {
        Slog.w(
            "MR2ServiceImpl",
            "setRouteVolumeOnHandler: Couldn't find provider for route=" + mediaRoute2Info);
        return;
      }
      findProvider.setRouteVolume(j, mediaRoute2Info.getOriginalId(), i);
    }

    public final void setSessionVolumeOnHandler(long j, String str, int i) {
      MediaRoute2Provider findProvider = findProvider(MediaRouter2Utils.getProviderId(str));
      if (findProvider == null) {
        Slog.w(
            "MR2ServiceImpl",
            "setSessionVolumeOnHandler: Couldn't find provider for session id=" + str);
        return;
      }
      findProvider.setSessionVolume(j, MediaRouter2Utils.getOriginalId(str), i);
    }

    public final void releaseSessionOnHandler(long j, RouterRecord routerRecord, String str) {
      if (((RouterRecord) this.mSessionToRouterMap.get(str)) != routerRecord) {
        StringBuilder sb = new StringBuilder();
        sb.append("Ignoring releasing session from non-matching router. packageName=");
        sb.append(routerRecord == null ? null : routerRecord.mPackageName);
        sb.append(" uniqueSessionId=");
        sb.append(str);
        Slog.w("MR2ServiceImpl", sb.toString());
        return;
      }
      String providerId = MediaRouter2Utils.getProviderId(str);
      if (providerId == null) {
        Slog.w(
            "MR2ServiceImpl",
            "Ignoring releasing session with invalid unique session ID. uniqueSessionId=" + str);
        return;
      }
      String originalId = MediaRouter2Utils.getOriginalId(str);
      if (originalId == null) {
        Slog.w(
            "MR2ServiceImpl",
            "Ignoring releasing session with invalid unique session ID. uniqueSessionId="
                + str
                + " providerId="
                + providerId);
        return;
      }
      MediaRoute2Provider findProvider = findProvider(providerId);
      if (findProvider == null) {
        Slog.w(
            "MR2ServiceImpl",
            "Ignoring releasing session since no provider found for given providerId="
                + providerId);
        return;
      }
      findProvider.releaseSession(j, originalId);
    }

    public final void onSessionCreatedOnHandler(
        MediaRoute2Provider mediaRoute2Provider, long j, RoutingSessionInfo routingSessionInfo) {
      SessionCreationRequest sessionCreationRequest;
      Iterator it = this.mSessionCreationRequests.iterator();
      while (true) {
        if (!it.hasNext()) {
          sessionCreationRequest = null;
          break;
        }
        sessionCreationRequest = (SessionCreationRequest) it.next();
        if (sessionCreationRequest.mUniqueRequestId == j
            && TextUtils.equals(
                sessionCreationRequest.mRoute.getProviderId(), mediaRoute2Provider.getUniqueId())) {
          break;
        }
      }
      notifySessionCreatedToManagers(
          sessionCreationRequest == null ? 0L : sessionCreationRequest.mManagerRequestId,
          routingSessionInfo);
      if (sessionCreationRequest == null) {
        Slog.w(
            "MR2ServiceImpl",
            "Ignoring session creation result for unknown request. uniqueRequestId="
                + j
                + ", sessionInfo="
                + routingSessionInfo);
        return;
      }
      this.mSessionCreationRequests.remove(sessionCreationRequest);
      MediaRoute2Provider findProvider =
          findProvider(sessionCreationRequest.mOldSession.getProviderId());
      if (findProvider != null) {
        findProvider.prepareReleaseSession(sessionCreationRequest.mOldSession.getId());
      } else {
        Slog.w(
            "MR2ServiceImpl",
            "onSessionCreatedOnHandler: Can't find provider for an old session. session="
                + sessionCreationRequest.mOldSession);
      }
      this.mSessionToRouterMap.put(
          routingSessionInfo.getId(), sessionCreationRequest.mRouterRecord);
      if (routingSessionInfo.isSystemSession()
          && !sessionCreationRequest.mRouterRecord.hasSystemRoutingPermission()) {
        routingSessionInfo = this.mSystemProvider.getDefaultSessionInfo();
      }
      notifySessionCreatedToRouter(
          sessionCreationRequest.mRouterRecord,
          MediaRouter2ServiceImpl.toOriginalRequestId(j),
          routingSessionInfo);
    }

    public final void onSessionInfoChangedOnHandler(
        MediaRoute2Provider mediaRoute2Provider, RoutingSessionInfo routingSessionInfo) {
      notifySessionUpdatedToManagers(getManagers(), routingSessionInfo);
      if (mediaRoute2Provider == this.mSystemProvider) {
        if (this.mServiceRef.get() == null) {
          return;
        }
        notifySessionInfoChangedToRouters(getRouterRecords(true), routingSessionInfo);
        notifySessionInfoChangedToRouters(
            getRouterRecords(false), this.mSystemProvider.getDefaultSessionInfo());
        return;
      }
      RouterRecord routerRecord =
          (RouterRecord) this.mSessionToRouterMap.get(routingSessionInfo.getId());
      if (routerRecord == null) {
        Slog.w(
            "MR2ServiceImpl",
            "onSessionInfoChangedOnHandler: No matching router found for session="
                + routingSessionInfo);
        return;
      }
      notifySessionInfoChangedToRouters(Arrays.asList(routerRecord), routingSessionInfo);
    }

    public final void onSessionReleasedOnHandler(
        MediaRoute2Provider mediaRoute2Provider, RoutingSessionInfo routingSessionInfo) {
      notifySessionReleasedToManagers(getManagers(), routingSessionInfo);
      RouterRecord routerRecord =
          (RouterRecord) this.mSessionToRouterMap.get(routingSessionInfo.getId());
      if (routerRecord == null) {
        Slog.w(
            "MR2ServiceImpl",
            "onSessionReleasedOnHandler: No matching router found for session="
                + routingSessionInfo);
        return;
      }
      notifySessionReleasedToRouter(routerRecord, routingSessionInfo);
    }

    public final void onRequestFailedOnHandler(
        MediaRoute2Provider mediaRoute2Provider, long j, int i) {
      ManagerRecord findManagerWithId;
      if (handleSessionCreationRequestFailed(mediaRoute2Provider, j, i)
          || (findManagerWithId = findManagerWithId(MediaRouter2ServiceImpl.toRequesterId(j)))
              == null) {
        return;
      }
      notifyRequestFailedToManager(
          findManagerWithId.mManager, MediaRouter2ServiceImpl.toOriginalRequestId(j), i);
    }

    public final boolean handleSessionCreationRequestFailed(
        MediaRoute2Provider mediaRoute2Provider, long j, int i) {
      SessionCreationRequest sessionCreationRequest;
      Iterator it = this.mSessionCreationRequests.iterator();
      while (true) {
        if (!it.hasNext()) {
          sessionCreationRequest = null;
          break;
        }
        sessionCreationRequest = (SessionCreationRequest) it.next();
        if (sessionCreationRequest.mUniqueRequestId == j
            && TextUtils.equals(
                sessionCreationRequest.mRoute.getProviderId(), mediaRoute2Provider.getUniqueId())) {
          break;
        }
      }
      if (sessionCreationRequest == null) {
        return false;
      }
      this.mSessionCreationRequests.remove(sessionCreationRequest);
      long j2 = sessionCreationRequest.mManagerRequestId;
      if (j2 == 0) {
        notifySessionCreationFailedToRouter(
            sessionCreationRequest.mRouterRecord, MediaRouter2ServiceImpl.toOriginalRequestId(j));
        return true;
      }
      ManagerRecord findManagerWithId =
          findManagerWithId(MediaRouter2ServiceImpl.toRequesterId(j2));
      if (findManagerWithId == null) {
        return true;
      }
      notifyRequestFailedToManager(
          findManagerWithId.mManager,
          MediaRouter2ServiceImpl.toOriginalRequestId(sessionCreationRequest.mManagerRequestId),
          i);
      return true;
    }

    public final void notifySessionCreatedToRouter(
        RouterRecord routerRecord, int i, RoutingSessionInfo routingSessionInfo) {
      try {
        routerRecord.mRouter.notifySessionCreated(i, routingSessionInfo);
      } catch (RemoteException e) {
        Slog.w(
            "MR2ServiceImpl",
            "Failed to notify router of the session creation. Router probably died.",
            e);
      }
    }

    public final void notifySessionCreationFailedToRouter(RouterRecord routerRecord, int i) {
      try {
        routerRecord.mRouter.notifySessionCreated(i, (RoutingSessionInfo) null);
      } catch (RemoteException e) {
        Slog.w(
            "MR2ServiceImpl",
            "Failed to notify router of the session creation failure. Router probably died.",
            e);
      }
    }

    public final void notifySessionReleasedToRouter(
        RouterRecord routerRecord, RoutingSessionInfo routingSessionInfo) {
      try {
        routerRecord.mRouter.notifySessionReleased(routingSessionInfo);
      } catch (RemoteException e) {
        Slog.w(
            "MR2ServiceImpl",
            "Failed to notify router of the session release. Router probably died.",
            e);
      }
    }

    public final List getManagers() {
      ArrayList arrayList = new ArrayList();
      MediaRouter2ServiceImpl mediaRouter2ServiceImpl =
          (MediaRouter2ServiceImpl) this.mServiceRef.get();
      if (mediaRouter2ServiceImpl == null) {
        return arrayList;
      }
      synchronized (mediaRouter2ServiceImpl.mLock) {
        Iterator it = this.mUserRecord.mManagerRecords.iterator();
        while (it.hasNext()) {
          arrayList.add(((ManagerRecord) it.next()).mManager);
        }
      }
      return arrayList;
    }

    public final List getRouterRecords() {
      ArrayList arrayList;
      MediaRouter2ServiceImpl mediaRouter2ServiceImpl =
          (MediaRouter2ServiceImpl) this.mServiceRef.get();
      if (mediaRouter2ServiceImpl == null) {
        return Collections.emptyList();
      }
      synchronized (mediaRouter2ServiceImpl.mLock) {
        arrayList = new ArrayList(this.mUserRecord.mRouterRecords);
      }
      return arrayList;
    }

    public final List getRouterRecords(boolean z) {
      MediaRouter2ServiceImpl mediaRouter2ServiceImpl =
          (MediaRouter2ServiceImpl) this.mServiceRef.get();
      ArrayList arrayList = new ArrayList();
      if (mediaRouter2ServiceImpl == null) {
        return arrayList;
      }
      synchronized (mediaRouter2ServiceImpl.mLock) {
        Iterator it = this.mUserRecord.mRouterRecords.iterator();
        while (it.hasNext()) {
          RouterRecord routerRecord = (RouterRecord) it.next();
          if (z == routerRecord.hasSystemRoutingPermission()) {
            arrayList.add(routerRecord);
          }
        }
      }
      return arrayList;
    }

    public final List getManagerRecords() {
      ArrayList arrayList;
      MediaRouter2ServiceImpl mediaRouter2ServiceImpl =
          (MediaRouter2ServiceImpl) this.mServiceRef.get();
      if (mediaRouter2ServiceImpl == null) {
        return Collections.emptyList();
      }
      synchronized (mediaRouter2ServiceImpl.mLock) {
        arrayList = new ArrayList(this.mUserRecord.mManagerRecords);
      }
      return arrayList;
    }

    public final void notifyRouterRegistered(RouterRecord routerRecord) {
      RoutingSessionInfo defaultSessionInfo;
      ArrayList arrayList = new ArrayList();
      MediaRoute2ProviderInfo mediaRoute2ProviderInfo = null;
      for (MediaRoute2ProviderInfo mediaRoute2ProviderInfo2 : this.mLastProviderInfos) {
        if (TextUtils.equals(
            mediaRoute2ProviderInfo2.getUniqueId(), this.mSystemProvider.getUniqueId())) {
          mediaRoute2ProviderInfo = mediaRoute2ProviderInfo2;
        } else {
          arrayList.addAll(mediaRoute2ProviderInfo2.getRoutes());
        }
      }
      if (routerRecord.hasSystemRoutingPermission()) {
        if (mediaRoute2ProviderInfo != null) {
          arrayList.addAll(mediaRoute2ProviderInfo.getRoutes());
        } else {
          Slog.wtf("MR2ServiceImpl", "System route provider not found.");
        }
        defaultSessionInfo = (RoutingSessionInfo) this.mSystemProvider.getSessionInfos().get(0);
      } else {
        arrayList.add(this.mSystemProvider.getDefaultRoute());
        defaultSessionInfo = this.mSystemProvider.getDefaultSessionInfo();
      }
      if (arrayList.size() == 0) {
        return;
      }
      routerRecord.notifyRegistered(arrayList, defaultSessionInfo);
    }

    public static void notifyRoutesUpdatedToRouterRecords(List list, List list2) {
      Iterator it = list.iterator();
      while (it.hasNext()) {
        ((RouterRecord) it.next()).notifyRoutesUpdated(list2);
      }
    }

    public final void notifySessionInfoChangedToRouters(
        List list, RoutingSessionInfo routingSessionInfo) {
      Iterator it = list.iterator();
      while (it.hasNext()) {
        ((RouterRecord) it.next()).notifySessionInfoChanged(routingSessionInfo);
      }
    }

    public final void notifyInitialRoutesToManager(IMediaRouter2Manager iMediaRouter2Manager) {
      if (this.mLastNotifiedRoutesToPrivilegedRouters.isEmpty()) {
        return;
      }
      try {
        iMediaRouter2Manager.notifyRoutesUpdated(
            new ArrayList(this.mLastNotifiedRoutesToPrivilegedRouters.values()));
      } catch (RemoteException e) {
        Slog.w("MR2ServiceImpl", "Failed to notify all routes. Manager probably died.", e);
      }
    }

    public final void notifyRoutesUpdatedToManagers(List list, List list2) {
      Iterator it = list.iterator();
      while (it.hasNext()) {
        try {
          ((IMediaRouter2Manager) it.next()).notifyRoutesUpdated(list2);
        } catch (RemoteException e) {
          Slog.w("MR2ServiceImpl", "Failed to notify routes changed. Manager probably died.", e);
        }
      }
    }

    public final void notifySessionCreatedToManagers(
        long j, RoutingSessionInfo routingSessionInfo) {
      int requesterId = MediaRouter2ServiceImpl.toRequesterId(j);
      int originalRequestId = MediaRouter2ServiceImpl.toOriginalRequestId(j);
      for (ManagerRecord managerRecord : getManagerRecords()) {
        try {
          managerRecord.mManager.notifySessionCreated(
              managerRecord.mManagerId == requesterId ? originalRequestId : 0, routingSessionInfo);
        } catch (RemoteException e) {
          Slog.w(
              "MR2ServiceImpl",
              "notifySessionCreatedToManagers: Failed to notify. Manager probably died.",
              e);
        }
      }
    }

    public final void notifySessionUpdatedToManagers(
        List list, RoutingSessionInfo routingSessionInfo) {
      Iterator it = list.iterator();
      while (it.hasNext()) {
        try {
          ((IMediaRouter2Manager) it.next()).notifySessionUpdated(routingSessionInfo);
        } catch (RemoteException e) {
          Slog.w(
              "MR2ServiceImpl",
              "notifySessionUpdatedToManagers: Failed to notify. Manager probably died.",
              e);
        }
      }
    }

    public final void notifySessionReleasedToManagers(
        List list, RoutingSessionInfo routingSessionInfo) {
      Iterator it = list.iterator();
      while (it.hasNext()) {
        try {
          ((IMediaRouter2Manager) it.next()).notifySessionReleased(routingSessionInfo);
        } catch (RemoteException e) {
          Slog.w(
              "MR2ServiceImpl",
              "notifySessionReleasedToManagers: Failed to notify. Manager probably died.",
              e);
        }
      }
    }

    public final void notifyDiscoveryPreferenceChangedToManager(
        RouterRecord routerRecord, IMediaRouter2Manager iMediaRouter2Manager) {
      try {
        iMediaRouter2Manager.notifyDiscoveryPreferenceChanged(
            routerRecord.mPackageName, routerRecord.mDiscoveryPreference);
      } catch (RemoteException e) {
        Slog.w(
            "MR2ServiceImpl",
            "Failed to notify preferred features changed. Manager probably died.",
            e);
      }
    }

    public final void notifyDiscoveryPreferenceChangedToManagers(
        String str, RouteDiscoveryPreference routeDiscoveryPreference) {
      MediaRouter2ServiceImpl mediaRouter2ServiceImpl =
          (MediaRouter2ServiceImpl) this.mServiceRef.get();
      if (mediaRouter2ServiceImpl == null) {
        return;
      }
      ArrayList arrayList = new ArrayList();
      synchronized (mediaRouter2ServiceImpl.mLock) {
        Iterator it = this.mUserRecord.mManagerRecords.iterator();
        while (it.hasNext()) {
          arrayList.add(((ManagerRecord) it.next()).mManager);
        }
      }
      Iterator it2 = arrayList.iterator();
      while (it2.hasNext()) {
        try {
          ((IMediaRouter2Manager) it2.next())
              .notifyDiscoveryPreferenceChanged(str, routeDiscoveryPreference);
        } catch (RemoteException e) {
          Slog.w(
              "MR2ServiceImpl",
              "Failed to notify preferred features changed. Manager probably died.",
              e);
        }
      }
    }

    public final void notifyRouteListingPreferenceChangeToManagers(
        String str, RouteListingPreference routeListingPreference) {
      MediaRouter2ServiceImpl mediaRouter2ServiceImpl =
          (MediaRouter2ServiceImpl) this.mServiceRef.get();
      if (mediaRouter2ServiceImpl == null) {
        return;
      }
      ArrayList arrayList = new ArrayList();
      synchronized (mediaRouter2ServiceImpl.mLock) {
        Iterator it = this.mUserRecord.mManagerRecords.iterator();
        while (it.hasNext()) {
          arrayList.add(((ManagerRecord) it.next()).mManager);
        }
      }
      Iterator it2 = arrayList.iterator();
      while (it2.hasNext()) {
        try {
          ((IMediaRouter2Manager) it2.next())
              .notifyRouteListingPreferenceChange(str, routeListingPreference);
        } catch (RemoteException e) {
          Slog.w(
              "MR2ServiceImpl",
              "Failed to notify preferred features changed. Manager probably died.",
              e);
        }
      }
    }

    public final void notifyRequestFailedToManager(
        IMediaRouter2Manager iMediaRouter2Manager, int i, int i2) {
      try {
        iMediaRouter2Manager.notifyRequestFailed(i, i2);
      } catch (RemoteException e) {
        Slog.w(
            "MR2ServiceImpl",
            "Failed to notify manager of the request failure. Manager probably died.",
            e);
      }
    }

    public final void updateDiscoveryPreferenceOnHandler() {
      boolean z;
      final MediaRouter2ServiceImpl mediaRouter2ServiceImpl =
          (MediaRouter2ServiceImpl) this.mServiceRef.get();
      if (mediaRouter2ServiceImpl == null) {
        return;
      }
      List<RouterRecord> emptyList = Collections.emptyList();
      List routerRecords = getRouterRecords();
      List managerRecords = getManagerRecords();
      if (mediaRouter2ServiceImpl.mPowerManager.isInteractive()) {
        boolean anyMatch =
            managerRecords.stream()
                .anyMatch(
                    new Predicate() { // from class:
                      // com.android.server.media.MediaRouter2ServiceImpl$UserHandler$$ExternalSyntheticLambda0
                      @Override // java.util.function.Predicate
                      public final boolean test(Object obj) {
                        boolean lambda$updateDiscoveryPreferenceOnHandler$3;
                        lambda$updateDiscoveryPreferenceOnHandler$3 =
                            MediaRouter2ServiceImpl.UserHandler
                                .lambda$updateDiscoveryPreferenceOnHandler$3(
                                    MediaRouter2ServiceImpl.this,
                                    (MediaRouter2ServiceImpl.ManagerRecord) obj);
                        return lambda$updateDiscoveryPreferenceOnHandler$3;
                      }
                    });
        if (!anyMatch) {
          routerRecords =
              (List)
                  routerRecords.stream()
                      .filter(
                          new Predicate() { // from class:
                            // com.android.server.media.MediaRouter2ServiceImpl$UserHandler$$ExternalSyntheticLambda1
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                              boolean lambda$updateDiscoveryPreferenceOnHandler$4;
                              lambda$updateDiscoveryPreferenceOnHandler$4 =
                                  MediaRouter2ServiceImpl.UserHandler
                                      .lambda$updateDiscoveryPreferenceOnHandler$4(
                                          MediaRouter2ServiceImpl.this,
                                          (MediaRouter2ServiceImpl.RouterRecord) obj);
                              return lambda$updateDiscoveryPreferenceOnHandler$4;
                            }
                          })
                      .collect(Collectors.toList());
        }
        List list = routerRecords;
        z = anyMatch;
        emptyList = list;
      } else {
        z = false;
      }
      Iterator it = this.mRouteProviders.iterator();
      while (it.hasNext()) {
        MediaRoute2Provider mediaRoute2Provider = (MediaRoute2Provider) it.next();
        if (mediaRoute2Provider instanceof MediaRoute2ProviderServiceProxy) {
          ((MediaRoute2ProviderServiceProxy) mediaRoute2Provider).setManagerScanning(z);
        }
      }
      HashSet hashSet = new HashSet();
      HashSet hashSet2 = new HashSet();
      boolean z2 = false;
      for (RouterRecord routerRecord : emptyList) {
        RouteDiscoveryPreference routeDiscoveryPreference = routerRecord.mDiscoveryPreference;
        hashSet.addAll(routeDiscoveryPreference.getPreferredFeatures());
        if (routeDiscoveryPreference.shouldPerformActiveScan()) {
          hashSet2.add(routerRecord.mPackageName);
          z2 = true;
        }
      }
      RouteDiscoveryPreference build =
          new RouteDiscoveryPreference.Builder(List.copyOf(hashSet), z2 || z).build();
      synchronized (mediaRouter2ServiceImpl.mLock) {
        if (build.equals(this.mUserRecord.mCompositeDiscoveryPreference)
            && hashSet2.equals(this.mUserRecord.mActivelyScanningPackages)) {
          return;
        }
        UserRecord userRecord = this.mUserRecord;
        userRecord.mCompositeDiscoveryPreference = build;
        userRecord.mActivelyScanningPackages = hashSet2;
        Iterator it2 = this.mRouteProviders.iterator();
        while (it2.hasNext()) {
          ((MediaRoute2Provider) it2.next())
              .updateDiscoveryPreference(hashSet2, this.mUserRecord.mCompositeDiscoveryPreference);
        }
      }
    }

    public static /* synthetic */ boolean lambda$updateDiscoveryPreferenceOnHandler$3(
        MediaRouter2ServiceImpl mediaRouter2ServiceImpl, ManagerRecord managerRecord) {
      return managerRecord.mIsScanning
          && mediaRouter2ServiceImpl.mActivityManager.getPackageImportance(
                  managerRecord.mPackageName)
              <= MediaRouter2ServiceImpl.sPackageImportanceForScanning;
    }

    public static /* synthetic */ boolean lambda$updateDiscoveryPreferenceOnHandler$4(
        MediaRouter2ServiceImpl mediaRouter2ServiceImpl, RouterRecord routerRecord) {
      return mediaRouter2ServiceImpl.mActivityManager.getPackageImportance(
              routerRecord.mPackageName)
          <= MediaRouter2ServiceImpl.sPackageImportanceForScanning;
    }

    public final MediaRoute2Provider findProvider(String str) {
      Iterator it = this.mRouteProviders.iterator();
      while (it.hasNext()) {
        MediaRoute2Provider mediaRoute2Provider = (MediaRoute2Provider) it.next();
        if (TextUtils.equals(mediaRoute2Provider.getUniqueId(), str)) {
          return mediaRoute2Provider;
        }
      }
      return null;
    }
  }

  public final class SessionCreationRequest {
    public final long mManagerRequestId;
    public final RoutingSessionInfo mOldSession;
    public final MediaRoute2Info mRoute;
    public final RouterRecord mRouterRecord;
    public final long mUniqueRequestId;

    public SessionCreationRequest(
        RouterRecord routerRecord,
        long j,
        long j2,
        RoutingSessionInfo routingSessionInfo,
        MediaRoute2Info mediaRoute2Info) {
      this.mRouterRecord = routerRecord;
      this.mUniqueRequestId = j;
      this.mManagerRequestId = j2;
      this.mOldSession = routingSessionInfo;
      this.mRoute = mediaRoute2Info;
    }

    public void dump(PrintWriter printWriter, String str) {
      printWriter.println(str + "SessionCreationRequest");
      String str2 = str + "  ";
      printWriter.println(str2 + "mUniqueRequestId=" + this.mUniqueRequestId);
      printWriter.println(str2 + "mManagerRequestId=" + this.mManagerRequestId);
      this.mOldSession.dump(printWriter, str2);
      this.mRoute.dump(printWriter, str);
    }
  }
}
