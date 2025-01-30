package com.android.server.location.nsflp;

import android.content.Context;
import android.location.LocationConstants;
import android.location.LocationRequest;
import android.location.util.identity.CallerIdentity;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcelable;
import android.os.SystemClock;
import android.os.WorkSource;
import android.util.Log;
import com.android.server.location.LocationPermissions;
import com.android.server.location.provider.LocationProviderManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Function;

/* loaded from: classes2.dex */
public class NSLocationProviderHelper {
  public final Context mContext;
  public boolean mIsMotionPowerSaveMode;
  public final CopyOnWriteArrayList mMotionPowerListeners;
  public final NSConnectionHelper mNSConnectionHelper;
  public final NSPermissionHelper mNSPermissionHelper;
  public String mSuplAddress;
  public final Map mRegistrationMap = new ConcurrentHashMap();
  public final Object mRegistrationSetLock = new Object();
  public boolean mIsAvailableMotionStop = true;

  public interface MotionPowerSaveModeChangedListener {
    void onMotionPowerSaveModeChanged(boolean z);
  }

  public final void addListener(
      MotionPowerSaveModeChangedListener motionPowerSaveModeChangedListener) {
    this.mMotionPowerListeners.add(motionPowerSaveModeChangedListener);
  }

  public final void removeListener(
      MotionPowerSaveModeChangedListener motionPowerSaveModeChangedListener) {
    this.mMotionPowerListeners.remove(motionPowerSaveModeChangedListener);
  }

  public final void notifyMotionPowerSaveModeChanged(boolean z) {
    if (this.mIsMotionPowerSaveMode == z) {
      return;
    }
    this.mIsMotionPowerSaveMode = z;
    Iterator it = this.mMotionPowerListeners.iterator();
    while (it.hasNext()) {
      ((MotionPowerSaveModeChangedListener) it.next()).onMotionPowerSaveModeChanged(z);
    }
  }

  public NSLocationProviderHelper(
      Context context,
      NSPermissionHelper nSPermissionHelper,
      NSConnectionHelper nSConnectionHelper) {
    Log.i("NSLocationProviderHelper", "constructor");
    this.mContext = context;
    this.mNSPermissionHelper = nSPermissionHelper;
    this.mNSConnectionHelper = nSConnectionHelper;
    this.mMotionPowerListeners = new CopyOnWriteArrayList();
  }

  public void updateRequestInfo(String str, LocationProviderManager.Registration registration) {
    CallerIdentity identity = registration.getIdentity();
    int uid = identity.getUid();
    Log.i("NSLocationProviderHelper", "updateRequestInfo, uid=" + uid);
    String listenerId = registration.getListenerId();
    ((Set)
            this.mRegistrationMap.computeIfAbsent(
                str,
                new Function() { // from class:
                                 // com.android.server.location.nsflp.NSLocationProviderHelper$$ExternalSyntheticLambda0
                  @Override // java.util.function.Function
                  public final Object apply(Object obj) {
                    Set lambda$updateRequestInfo$0;
                    lambda$updateRequestInfo$0 =
                        NSLocationProviderHelper.lambda$updateRequestInfo$0((String) obj);
                    return lambda$updateRequestInfo$0;
                  }
                }))
        .add(registration);
    Throwable th = (uid != 1000 || "passive".equals(str)) ? null : new Throwable("stack dump");
    LocationRequest request = registration.getRequest();
    Bundle bundle = new Bundle();
    bundle.putString("packageName", identity.getPackageName());
    bundle.putString("provider", str);
    bundle.putString("listenerid", listenerId);
    bundle.putLong("interval", request.getIntervalMillis());
    bundle.putLong("minUpdateInterval", request.getMinUpdateIntervalMillis());
    bundle.putInt("quality", request.getQuality());
    bundle.putInt("pid", identity.getPid());
    bundle.putInt("uid", uid);
    bundle.putBoolean("foreground", registration.isForeground());
    bundle.putSerializable("throwable", th);
    bundle.putBoolean("listenerType", registration.isListenerType());
    bundle.putBoolean("isAllowed", registration.isPermitted());
    bundle.putFloat("smallestDisplacement", request.getMinUpdateDistanceMeters());
    bundle.putInt("numUpdates", request.getMaxUpdates());
    bundle.putInt("permissionLevel", registration.getPermissionLevel());
    WorkSource workSource = request.getWorkSource();
    if (workSource != null && workSource.size() > 0) {
      try {
        int size = workSource.size();
        int[] iArr = new int[size];
        String[] strArr = new String[size];
        for (int i = 0; i < size; i++) {
          iArr[i] = workSource.getUid(i);
          strArr[i] = workSource.getPackageName(i);
        }
        bundle.putInt("workSourceSize", size);
        bundle.putIntArray("workSourceUids", iArr);
        bundle.putStringArray("workSourceNames", strArr);
      } catch (Exception unused) {
        Log.w("NSLocationProviderHelper", "Failed to put worksource[" + workSource + "]");
      }
    }
    updateUidProcState(uid, bundle);
    this.mNSConnectionHelper.onStateUpdated(LocationConstants.STATE_TYPE.LOCATION_REQUEST, bundle);
  }

  public static /* synthetic */ Set lambda$updateRequestInfo$0(String str) {
    return new CopyOnWriteArraySet();
  }

  public void updateRemoveInfo(String str, LocationProviderManager.Registration registration) {
    CallerIdentity identity = registration.getIdentity();
    int uid = identity.getUid();
    Log.i("NSLocationProviderHelper", "updateRemoveInfo, uid=" + uid);
    if (!this.mRegistrationMap.containsKey(str)) {
      Log.i(
          "NSLocationProviderHelper",
          "updateRemoveInfo, Registration map does not contains key " + str + ". So return");
      return;
    }
    Set set = (Set) this.mRegistrationMap.get(str);
    if (set == null || set.isEmpty()) {
      Log.i(
          "NSLocationProviderHelper",
          "updateRemoveInfo, Registration " + str + " set is null or empty. So return");
      return;
    }
    set.remove(registration);
    Bundle bundle = new Bundle();
    bundle.putString("packageName", identity.getPackageName());
    bundle.putString("listenerid", registration.getListenerId());
    bundle.putInt("pid", identity.getPid());
    bundle.putInt("uid", uid);
    updateUidProcState(uid, bundle);
    this.mNSConnectionHelper.onStateUpdated(LocationConstants.STATE_TYPE.LOCATION_REMOVE, bundle);
  }

  public void updateSLocationRequestInfo(
      boolean z, int i, int i2, String str, String str2, boolean z2, int i3) {
    Message obtain = Message.obtain();
    obtain.what = z ? 201 : 202;
    Bundle bundle = new Bundle();
    bundle.putInt("uid", i);
    bundle.putInt("pid", i2);
    bundle.putString("packageName", str);
    bundle.putString("listenerid", str2);
    bundle.putInt("numUpdates", z2 ? 1 : Integer.MAX_VALUE);
    bundle.putBoolean("listenerType", i3 == 1);
    updateUidProcState(i, bundle);
    obtain.obj = bundle;
    this.mNSConnectionHelper.onMessageUpdated(obtain);
  }

  public void updateRegistrationAccessStatus(
      String str,
      LocationProviderManager.Registration registration,
      LocationConstants.PAUSED_BY paused_by) {
    Set set;
    String str2;
    if (registration == null
        || (set = (Set) this.mRegistrationMap.get(str)) == null
        || !set.contains(registration)) {
      return;
    }
    boolean isPermitted = registration.isPermitted();
    StringBuilder sb = new StringBuilder();
    sb.append("Receiver status was changed, isAllowed=");
    sb.append(isPermitted);
    if (isPermitted) {
      str2 = "";
    } else {
      str2 = " by " + paused_by;
    }
    sb.append(str2);
    Log.w("NSLocationProviderHelper", sb.toString());
    Bundle bundle = new Bundle();
    bundle.putString("listenerid", registration.getListenerId());
    bundle.putBoolean("isAllowed", isPermitted);
    bundle.putParcelable("reason", paused_by);
    updateUidProcState(registration.getUid(), bundle);
    bundle.putInt("permissionLevel", registration.getPermissionLevel());
    this.mNSConnectionHelper.onStateUpdated(LocationConstants.STATE_TYPE.LOCATION_UPDATE, bundle);
  }

  public Bundle getActiveRequests(String str) {
    ArrayList<? extends Parcelable> arrayList = new ArrayList<>();
    Set<LocationProviderManager.Registration> set = (Set) this.mRegistrationMap.get(str);
    if (set == null || set.isEmpty()) {
      return null;
    }
    for (LocationProviderManager.Registration registration : set) {
      CallerIdentity identity = registration.getIdentity();
      LocationRequest request = registration.getRequest();
      int quality = request.getQuality();
      LocationRequestInfo build =
          new LocationRequestInfo.Builder()
              .setPackageName(identity.getPackageName())
              .setUid(identity.getUid())
              .setPid(identity.getPid())
              .setProvider(str)
              .setQuality(quality)
              .setHighPowerRequest(quality == 100)
              .setForeground(registration.isForeground())
              .setListenerId(registration.getListenerId())
              .setInterval(request.getIntervalMillis())
              .setMinUpdateInterval(request.getMinUpdateIntervalMillis())
              .setMaxWaitTime(request.getIntervalMillis())
              .setListenerType(registration.isListenerType())
              .setRequestTime(SystemClock.elapsedRealtime())
              .setAllowed(registration.isPermitted())
              .build();
      Log.i("NSLocationProviderHelper", "onActiveRequestSync, added " + build);
      arrayList.add(build);
    }
    Log.i("NSLocationProviderHelper", "onActiveRequestSync, size=" + arrayList.size());
    if (arrayList.isEmpty()) {
      return null;
    }
    Bundle bundle = new Bundle();
    bundle.putString("provider", str);
    bundle.putParcelableArrayList("activeRequests", arrayList);
    return bundle;
  }

  public void addGnssDataListener(
      IBinder iBinder,
      String str,
      LocationConstants.LISTENER_TYPE listener_type,
      int i,
      int i2,
      boolean z,
      boolean z2) {
    if (iBinder == null) {
      Log.w("NSLocationProviderHelper", "addGnssDataListener, binder is null");
      return;
    }
    Bundle bundle = new Bundle();
    bundle.putString("packageName", str);
    bundle.putParcelable("listenerType", listener_type);
    bundle.putInt("uid", i);
    bundle.putInt("pid", i2);
    bundle.putString("listenerid", Integer.toHexString(System.identityHashCode(iBinder)));
    bundle.putBoolean("isAllowed", z);
    bundle.putBoolean("foreground", z2);
    bundle.putInt("permissionLevel", LocationPermissions.getPermissionLevel(this.mContext, i, i2));
    updateUidProcState(i, bundle);
    this.mNSConnectionHelper.onStateUpdated(LocationConstants.STATE_TYPE.ADD_DATA_LISTENER, bundle);
  }

  public void removeGnssDataListener(
      IBinder iBinder, LocationConstants.LISTENER_TYPE listener_type, int i, int i2) {
    if (iBinder == null) {
      Log.w("NSLocationProviderHelper", "removeGnssDataListener, binder is null");
      return;
    }
    Bundle bundle = new Bundle();
    bundle.putString("listenerid", Integer.toHexString(System.identityHashCode(iBinder)));
    bundle.putParcelable("listenerType", listener_type);
    bundle.putInt("uid", i);
    bundle.putInt("pid", i2);
    updateUidProcState(i, bundle);
    this.mNSConnectionHelper.onStateUpdated(
        LocationConstants.STATE_TYPE.REMOVE_DATA_LISTENER, bundle);
  }

  public void updateGnssDataListener(
      IBinder iBinder,
      boolean z,
      String str,
      LocationConstants.LISTENER_TYPE listener_type,
      int i,
      int i2) {
    if (iBinder == null) {
      Log.w("NSLocationProviderHelper", "updateGnssDataListener, binder is null");
      return;
    }
    Bundle bundle = new Bundle();
    bundle.putBoolean("isActive", z);
    bundle.putString("packageName", str);
    bundle.putParcelable("listenerType", listener_type);
    bundle.putInt("uid", i);
    bundle.putInt("pid", i2);
    bundle.putString("listenerid", Integer.toHexString(System.identityHashCode(iBinder)));
    bundle.putInt("permissionLevel", LocationPermissions.getPermissionLevel(this.mContext, i, i2));
    updateUidProcState(i, bundle);
    this.mNSConnectionHelper.onStateUpdated(
        LocationConstants.STATE_TYPE.UPDATE_DATA_LISTENER, bundle);
  }

  public void onGmsApiRequest(Message message) {
    Bundle data;
    String string;
    try {
      if (message.arg1 == 59
          && (data = message.getData()) != null
          && (string = data.getString("interfaceName")) != null
          && "com.google.android.gms.location.internal.IGoogleLocationManagerService"
              .equals(string)) {
        Integer valueOf = Integer.valueOf(data.getInt("uid"));
        Integer valueOf2 = Integer.valueOf(data.getInt("pid"));
        if (valueOf != null) {
          updateUidProcState(valueOf.intValue(), data);
          if (valueOf2 != null) {
            data.putInt(
                "permissionLevel",
                LocationPermissions.getPermissionLevel(
                    this.mContext, valueOf.intValue(), valueOf2.intValue()));
          }
        }
        this.mNSConnectionHelper.onMessageUpdated(message);
      }
    } catch (Exception e) {
      Log.w("NSLocationProviderHelper", "Failed to update GmsApiRequest, " + e.toString());
    }
  }

  public void reportProviderStatus(
      LocationConstants.STATE_TYPE state_type, Integer num, Integer num2) {
    Bundle bundle = new Bundle();
    if (num != null) {
      bundle.putInt("status", num.intValue());
    }
    if (num2 != null) {
      bundle.putInt("value", num2.intValue());
    }
    this.mNSConnectionHelper.onStateUpdated(state_type, bundle);
  }

  public void sendSettingsIgnoreInfo(
      String str, boolean z, LocationProviderManager.Registration registration) {
    Bundle bundle = new Bundle();
    if (z && registration != null) {
      if (registration.getIdentity().getUid() == 1000) {
        bundle.putSerializable("throwable", new Throwable("stack dump"));
      } else {
        bundle.putString("packageName", registration.getIdentity().getPackageName());
      }
    }
    bundle.putString("provider", str);
    bundle.putBoolean("ignored", z);
    this.mNSConnectionHelper.onStateUpdated(
        LocationConstants.STATE_TYPE.SETTINGS_IGNORED_STATE_CHANGED, bundle);
  }

  public void updateSuplAddress(String str) {
    if (str == null) {
      return;
    }
    this.mSuplAddress = str;
    Bundle bundle = new Bundle();
    bundle.putString("supl_hostname", str);
    this.mNSConnectionHelper.onStateUpdated(LocationConstants.STATE_TYPE.SUPL_ADDRESS, bundle);
  }

  public String getSuplAddress() {
    return this.mSuplAddress;
  }

  public final void updateUidProcState(int i, Bundle bundle) {
    NSPermissionHelper.UidState uidState;
    if (bundle == null || (uidState = this.mNSPermissionHelper.getUidState(i)) == null) {
      return;
    }
    bundle.putInt("procState", uidState.getProcState());
    bundle.putBoolean("hasLocationCapability", uidState.hasLocationCapability());
  }

  public boolean isMotionPowerSaveMode() {
    return this.mIsMotionPowerSaveMode;
  }

  public void onAvailableMotionStop(boolean z) {
    if (this.mIsAvailableMotionStop == z) {
      return;
    }
    this.mIsAvailableMotionStop = z;
    Bundle bundle = new Bundle();
    bundle.putBoolean("isAvailableMotionStop", z);
    this.mNSConnectionHelper.onStateUpdated(
        LocationConstants.STATE_TYPE.AVAILABLE_MOTION_STOP, bundle);
  }
}
