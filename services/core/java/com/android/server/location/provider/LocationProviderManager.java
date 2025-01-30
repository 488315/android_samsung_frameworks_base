package com.android.server.location.provider;

import android.app.AlarmManager;
import android.app.BroadcastOptions;
import android.app.PendingIntent;
import android.app.compat.CompatChanges;
import android.content.Context;
import android.content.Intent;
import android.location.ILocationCallback;
import android.location.ILocationListener;
import android.location.LastLocationRequest;
import android.location.Location;
import android.location.LocationConstants;
import android.location.LocationManagerInternal;
import android.location.LocationRequest;
import android.location.LocationResult;
import android.location.provider.IProviderRequestListener;
import android.location.provider.ProviderProperties;
import android.location.provider.ProviderRequest;
import android.location.util.identity.CallerIdentity;
import android.os.Binder;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.IRemoteCallback;
import android.os.Parcelable;
import android.os.PowerManager;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.WorkSource;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Base64;
import android.util.EventLog;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.TimeUtils;
import com.android.internal.listeners.ListenerExecutor;
import com.android.internal.util.ConcurrentUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.HexDump;
import com.android.internal.util.Preconditions;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.LocalServices;
import com.android.server.backup.BackupAgentTimeoutParameters;
import com.android.server.location.LocationPermissions;
import com.android.server.location.LocationServiceThread;
import com.android.server.location.eventlog.LocationEventLog;
import com.android.server.location.fudger.LocationFudger;
import com.android.server.location.gnss.sec.CarrierConfig;
import com.android.server.location.gnss.sec.GnssConstants;
import com.android.server.location.injector.AlarmHelper;
import com.android.server.location.injector.AppForegroundHelper;
import com.android.server.location.injector.AppOpsHelper;
import com.android.server.location.injector.Injector;
import com.android.server.location.injector.LocationPermissionsHelper;
import com.android.server.location.injector.LocationPowerSaveModeHelper;
import com.android.server.location.injector.LocationUsageLogger;
import com.android.server.location.injector.PackageResetHelper;
import com.android.server.location.injector.ScreenInteractiveHelper;
import com.android.server.location.injector.SettingsHelper;
import com.android.server.location.injector.UserInfoHelper;
import com.android.server.location.listeners.ListenerMultiplexer;
import com.android.server.location.listeners.ListenerRegistration;
import com.android.server.location.listeners.RemovableListenerRegistration;
import com.android.server.location.nsflp.NSConnectionHelper;
import com.android.server.location.nsflp.NSLocationProviderHelper;
import com.android.server.location.settings.LocationSettings;
import com.android.server.location.settings.LocationUserSettings;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* loaded from: classes2.dex */
public class LocationProviderManager extends ListenerMultiplexer
    implements AbstractLocationProvider.Listener {
  public HashSet mActiveOriginalRegistrations;
  public final SettingsHelper.GlobalSettingChangedListener mAdasPackageAllowlistChangedListener;
  public final AlarmHelper mAlarmHelper;
  public final AppForegroundHelper.AppForegroundListener mAppForegroundChangedListener;
  public final AppForegroundHelper mAppForegroundHelper;
  public final AppOpsHelper mAppOpsHelper;
  public final SettingsHelper.GlobalSettingChangedListener
      mBackgroundThrottleIntervalChangedListener;
  public final SettingsHelper.GlobalSettingChangedListener
      mBackgroundThrottlePackageWhitelistChangedListener;
  public final Context mContext;
  public AlarmManager.OnAlarmListener mDelayedRegister;
  public final SparseBooleanArray mEnabled;
  public final ArrayList mEnabledListeners;
  public final SettingsHelper.GlobalSettingChangedListener
      mIgnoreSettingsPackageWhitelistChangedListener;
  public HashSet mInactiveMotionRegistrations;
  public boolean mIsSettingsIgnored;
  public final SparseArray mLastLocations;
  public final SettingsHelper.UserSettingChangedListener mLocationEnabledChangedListener;
  public final LocationFudger mLocationFudger;
  public final LocationManagerInternal mLocationManagerInternal;
  public final SettingsHelper.UserSettingChangedListener mLocationPackageBlacklistChangedListener;
  public final LocationPermissionsHelper mLocationPermissionsHelper;
  public final LocationPermissionsHelper.LocationPermissionsListener mLocationPermissionsListener;
  public final LocationPowerSaveModeHelper.LocationPowerSaveModeChangedListener
      mLocationPowerSaveModeChangedListener;
  public final LocationPowerSaveModeHelper mLocationPowerSaveModeHelper;
  public final LocationSettings mLocationSettings;
  public final LocationUsageLogger mLocationUsageLogger;
  public final LocationSettings.LocationUserSettingsListener mLocationUserSettingsListener;
  public final NSLocationProviderHelper.MotionPowerSaveModeChangedListener
      mMotionPowerSaveModeChangedListener;
  public final NSConnectionHelper mNSConnectionHelper;
  public final NSLocationProviderHelper mNSLocationProviderHelper;
  public final String mName;
  public final PackageResetHelper mPackageResetHelper;
  public final PackageResetHelper.Responder mPackageResetResponder;
  public final PassiveLocationProviderManager mPassiveManager;
  public final MockableLocationProvider mProvider;
  public final CopyOnWriteArrayList mProviderRequestListeners;
  public final Collection mRequiredPermissions;
  public final ScreenInteractiveHelper.ScreenInteractiveChangedListener
      mScreenInteractiveChangedListener;
  public final ScreenInteractiveHelper mScreenInteractiveHelper;
  public final SettingsHelper mSettingsHelper;
  public int mState;
  public StateChangedListener mStateChangedListener;
  public final UserInfoHelper.UserListener mUserChangedListener;
  public final UserInfoHelper mUserHelper;

  public interface LocationTransport {
    void deliverOnFlushComplete(int i);

    void deliverOnLocationChanged(LocationResult locationResult, IRemoteCallback iRemoteCallback);
  }

  public interface ProviderTransport {
    void deliverOnProviderEnabledChanged(String str, boolean z);
  }

  public interface StateChangedListener {
    void onStateChanged(
        String str, AbstractLocationProvider.State state, AbstractLocationProvider.State state2);
  }

  public static /* synthetic */ boolean lambda$onLocationPowerSaveModeChanged$10(
      Registration registration) {
    return true;
  }

  public static /* synthetic */ boolean lambda$onMotionPowerSaveModeChanged$11(
      Registration registration) {
    return true;
  }

  public static /* synthetic */ boolean lambda$onScreenInteractiveChanged$9(
      Registration registration) {
    return true;
  }

  public static /* synthetic */ boolean lambda$stopManager$0(Object obj) {
    return true;
  }

  public final class LocationListenerTransport implements LocationTransport, ProviderTransport {
    public final ILocationListener mListener;

    public LocationListenerTransport(ILocationListener iLocationListener) {
      Objects.requireNonNull(iLocationListener);
      this.mListener = iLocationListener;
    }

    @Override // com.android.server.location.provider.LocationProviderManager.LocationTransport
    public void deliverOnLocationChanged(
        LocationResult locationResult, IRemoteCallback iRemoteCallback) {
      try {
        this.mListener.onLocationChanged(locationResult.asList(), iRemoteCallback);
      } catch (RuntimeException e) {
        final RuntimeException runtimeException = new RuntimeException(e);
        LocationServiceThread.getExecutor()
            .execute(
                new Runnable() { // from class:
                                 // com.android.server.location.provider.LocationProviderManager$LocationListenerTransport$$ExternalSyntheticLambda0
                  @Override // java.lang.Runnable
                  public final void run() {
                    LocationProviderManager.LocationListenerTransport
                        .lambda$deliverOnLocationChanged$0(runtimeException);
                  }
                });
      }
    }

    public static /* synthetic */ void lambda$deliverOnLocationChanged$0(
        RuntimeException runtimeException) {
      throw runtimeException;
    }

    @Override // com.android.server.location.provider.LocationProviderManager.LocationTransport
    public void deliverOnFlushComplete(int i) {
      try {
        this.mListener.onFlushComplete(i);
      } catch (RuntimeException e) {
        final RuntimeException runtimeException = new RuntimeException(e);
        LocationServiceThread.getExecutor()
            .execute(
                new Runnable() { // from class:
                                 // com.android.server.location.provider.LocationProviderManager$LocationListenerTransport$$ExternalSyntheticLambda2
                  @Override // java.lang.Runnable
                  public final void run() {
                    LocationProviderManager.LocationListenerTransport
                        .lambda$deliverOnFlushComplete$1(runtimeException);
                  }
                });
      }
    }

    public static /* synthetic */ void lambda$deliverOnFlushComplete$1(
        RuntimeException runtimeException) {
      throw runtimeException;
    }

    @Override // com.android.server.location.provider.LocationProviderManager.ProviderTransport
    public void deliverOnProviderEnabledChanged(String str, boolean z) {
      try {
        this.mListener.onProviderEnabledChanged(str, z);
      } catch (RuntimeException e) {
        final RuntimeException runtimeException = new RuntimeException(e);
        LocationServiceThread.getExecutor()
            .execute(
                new Runnable() { // from class:
                                 // com.android.server.location.provider.LocationProviderManager$LocationListenerTransport$$ExternalSyntheticLambda1
                  @Override // java.lang.Runnable
                  public final void run() {
                    LocationProviderManager.LocationListenerTransport
                        .lambda$deliverOnProviderEnabledChanged$2(runtimeException);
                  }
                });
      }
    }

    public static /* synthetic */ void lambda$deliverOnProviderEnabledChanged$2(
        RuntimeException runtimeException) {
      throw runtimeException;
    }
  }

  public final class LocationPendingIntentTransport
      implements LocationTransport, ProviderTransport {
    public final Context mContext;
    public final PendingIntent mPendingIntent;

    public LocationPendingIntentTransport(Context context, PendingIntent pendingIntent) {
      this.mContext = context;
      this.mPendingIntent = pendingIntent;
    }

    @Override // com.android.server.location.provider.LocationProviderManager.LocationTransport
    public void deliverOnLocationChanged(
        LocationResult locationResult, final IRemoteCallback iRemoteCallback) {
      BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
      makeBasic.setDontSendToRestrictedApps(true);
      makeBasic.setTemporaryAppAllowlist(10000L, 0, FrameworkStatsLog.f87x84af90cd, "");
      Intent putExtra = new Intent().putExtra("location", locationResult.getLastLocation());
      if (locationResult.size() > 1) {
        putExtra.putExtra(
            "locations", (Parcelable[]) locationResult.asList().toArray(new Location[0]));
      }
      PendingIntentSender.send(
          this.mPendingIntent,
          this.mContext,
          putExtra,
          iRemoteCallback != null
              ? new Runnable() { // from class:
                                 // com.android.server.location.provider.LocationProviderManager$LocationPendingIntentTransport$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                  LocationProviderManager.LocationPendingIntentTransport
                      .lambda$deliverOnLocationChanged$0(iRemoteCallback);
                }
              }
              : null,
          makeBasic.toBundle());
    }

    public static /* synthetic */ void lambda$deliverOnLocationChanged$0(
        IRemoteCallback iRemoteCallback) {
      try {
        iRemoteCallback.sendResult((Bundle) null);
      } catch (RemoteException e) {
        throw e.rethrowFromSystemServer();
      }
    }

    @Override // com.android.server.location.provider.LocationProviderManager.LocationTransport
    public void deliverOnFlushComplete(int i) {
      BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
      makeBasic.setDontSendToRestrictedApps(true);
      makeBasic.setPendingIntentBackgroundActivityLaunchAllowed(false);
      this.mPendingIntent.send(
          this.mContext,
          0,
          new Intent().putExtra("flushComplete", i),
          null,
          null,
          null,
          makeBasic.toBundle());
    }

    @Override // com.android.server.location.provider.LocationProviderManager.ProviderTransport
    public void deliverOnProviderEnabledChanged(String str, boolean z) {
      BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
      makeBasic.setDontSendToRestrictedApps(true);
      this.mPendingIntent.send(
          this.mContext,
          0,
          new Intent().putExtra("providerEnabled", z),
          null,
          null,
          null,
          makeBasic.toBundle());
    }
  }

  public final class GetCurrentLocationTransport implements LocationTransport {
    public final ILocationCallback mCallback;

    @Override // com.android.server.location.provider.LocationProviderManager.LocationTransport
    public void deliverOnFlushComplete(int i) {}

    public GetCurrentLocationTransport(ILocationCallback iLocationCallback) {
      Objects.requireNonNull(iLocationCallback);
      this.mCallback = iLocationCallback;
    }

    @Override // com.android.server.location.provider.LocationProviderManager.LocationTransport
    public void deliverOnLocationChanged(
        LocationResult locationResult, IRemoteCallback iRemoteCallback) {
      Preconditions.checkState(iRemoteCallback == null);
      try {
        if (locationResult != null) {
          this.mCallback.onLocation(locationResult.getLastLocation());
        } else {
          this.mCallback.onLocation((Location) null);
        }
      } catch (RuntimeException e) {
        final RuntimeException runtimeException = new RuntimeException(e);
        LocationServiceThread.getExecutor()
            .execute(
                new Runnable() { // from class:
                                 // com.android.server.location.provider.LocationProviderManager$GetCurrentLocationTransport$$ExternalSyntheticLambda0
                  @Override // java.lang.Runnable
                  public final void run() {
                    LocationProviderManager.GetCurrentLocationTransport
                        .lambda$deliverOnLocationChanged$0(runtimeException);
                  }
                });
      }
    }

    public static /* synthetic */ void lambda$deliverOnLocationChanged$0(
        RuntimeException runtimeException) {
      throw runtimeException;
    }
  }

  public abstract class Registration extends RemovableListenerRegistration {
    public boolean isListenerType;
    public final LocationRequest mBaseRequest;
    public boolean mForeground;
    public final CallerIdentity mIdentity;
    public boolean mIsUsingHighPower;
    public Location mLastLocation;
    public String mListenerId;
    public final int mPermissionLevel;
    public boolean mPermitted;
    public LocationRequest mProviderLocationRequest;
    public final int mUid;

    public abstract ListenerExecutor.ListenerOperation acceptLocationChange(
        LocationResult locationResult);

    public Registration(
        LocationRequest locationRequest,
        CallerIdentity callerIdentity,
        Executor executor,
        LocationTransport locationTransport,
        int i) {
      super(executor, locationTransport);
      this.mLastLocation = null;
      this.isListenerType = true;
      Preconditions.checkArgument(callerIdentity.getListenerId() != null);
      Preconditions.checkArgument(i > 0);
      Preconditions.checkArgument(true ^ locationRequest.getWorkSource().isEmpty());
      this.mBaseRequest = locationRequest;
      this.mIdentity = callerIdentity;
      this.mPermissionLevel = i;
      this.mProviderLocationRequest = locationRequest;
      this.mListenerId = HexDump.toHexString(callerIdentity.getListenerId().hashCode());
      this.mUid = callerIdentity.getUid();
    }

    public void setPendingIntentId(PendingIntent pendingIntent) {
      this.mListenerId = HexDump.toHexString(pendingIntent.hashCode());
      this.isListenerType = false;
    }

    public String getListenerId() {
      return this.mListenerId;
    }

    public int getUid() {
      return this.mUid;
    }

    public boolean isListenerType() {
      return this.isListenerType;
    }

    public final CallerIdentity getIdentity() {
      return this.mIdentity;
    }

    public final LocationRequest getRequest() {
      LocationRequest locationRequest;
      synchronized (LocationProviderManager.this.mMultiplexerLock) {
        locationRequest = this.mProviderLocationRequest;
      }
      return locationRequest;
    }

    @Override // com.android.server.location.listeners.RemovableListenerRegistration
    public void onRegister() {
      super.onRegister();
      Log.d(
          "LocationManagerService",
          LocationProviderManager.this.mName
              + " provider added registration from "
              + getIdentity()
              + " -> "
              + getRequest());
      LocationEventLog locationEventLog = LocationEventLog.EVENT_LOG;
      locationEventLog.logProviderClientRegistered(
          LocationProviderManager.this.mName, getIdentity(), this.mBaseRequest);
      this.mPermitted =
          LocationProviderManager.this.mLocationPermissionsHelper.hasLocationPermissions(
              this.mPermissionLevel, getIdentity());
      this.mForeground =
          LocationProviderManager.this.mAppForegroundHelper.isAppForeground(getIdentity().getUid());
      this.mProviderLocationRequest = calculateProviderLocationRequest();
      this.mIsUsingHighPower = isUsingHighPower();
      if (this.mForeground) {
        locationEventLog.logProviderClientForeground(
            LocationProviderManager.this.mName, getIdentity());
      }
    }

    @Override // com.android.server.location.listeners.RemovableListenerRegistration,
              // com.android.server.location.listeners.ListenerRegistration
    public void onUnregister() {
      LocationEventLog.EVENT_LOG.logProviderClientUnregistered(
          LocationProviderManager.this.mName, getIdentity());
      Log.d(
          "LocationManagerService",
          LocationProviderManager.this.mName
              + " provider removed registration from "
              + getIdentity());
      super.onUnregister();
    }

    @Override // com.android.server.location.listeners.ListenerRegistration
    public void onActive() {
      LocationEventLog.EVENT_LOG.logProviderClientActive(
          LocationProviderManager.this.mName, getIdentity());
      if (!getRequest().isHiddenFromAppOps()) {
        LocationProviderManager.this.mAppOpsHelper.startOpNoThrow(41, getIdentity());
      }
      onHighPowerUsageChanged();
    }

    @Override // com.android.server.location.listeners.ListenerRegistration
    public void onInactive() {
      onHighPowerUsageChanged();
      if (!getRequest().isHiddenFromAppOps()) {
        LocationProviderManager.this.mAppOpsHelper.finishOp(41, getIdentity());
      }
      LocationEventLog.EVENT_LOG.logProviderClientInactive(
          LocationProviderManager.this.mName, getIdentity());
    }

    public final void setLastDeliveredLocation(Location location) {
      this.mLastLocation = location;
    }

    public final Location getLastDeliveredLocation() {
      Location location;
      synchronized (LocationProviderManager.this.mMultiplexerLock) {
        location = this.mLastLocation;
      }
      return location;
    }

    public int getPermissionLevel() {
      int i;
      synchronized (LocationProviderManager.this.mMultiplexerLock) {
        i = this.mPermissionLevel;
      }
      return i;
    }

    public final boolean isForeground() {
      boolean z;
      synchronized (LocationProviderManager.this.mMultiplexerLock) {
        z = this.mForeground;
      }
      return z;
    }

    public final boolean isPermitted() {
      boolean z;
      synchronized (LocationProviderManager.this.mMultiplexerLock) {
        z = this.mPermitted;
      }
      return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$flush$1(final int i) {
      executeOperation(
          new ListenerExecutor
              .ListenerOperation() { // from class:
                                     // com.android.server.location.provider.LocationProviderManager$Registration$$ExternalSyntheticLambda1
            public final void operate(Object obj) {
              ((LocationProviderManager.LocationTransport) obj).deliverOnFlushComplete(i);
            }
          });
    }

    public final void flush(final int i) {
      LocationProviderManager.this
          .mProvider
          .getController()
          .flush(
              new Runnable() { // from class:
                               // com.android.server.location.provider.LocationProviderManager$Registration$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                  LocationProviderManager.Registration.this.lambda$flush$1(i);
                }
              });
    }

    @Override // com.android.server.location.listeners.RemovableListenerRegistration
    public final LocationProviderManager getOwner() {
      return LocationProviderManager.this;
    }

    public final boolean onProviderPropertiesChanged() {
      synchronized (LocationProviderManager.this.mMultiplexerLock) {
        onHighPowerUsageChanged();
      }
      return false;
    }

    public final void onHighPowerUsageChanged() {
      boolean isUsingHighPower = isUsingHighPower();
      if (isUsingHighPower != this.mIsUsingHighPower) {
        this.mIsUsingHighPower = isUsingHighPower;
        if (getRequest().isHiddenFromAppOps()) {
          return;
        }
        if (this.mIsUsingHighPower) {
          LocationProviderManager.this.mAppOpsHelper.startOpNoThrow(42, getIdentity());
        } else {
          LocationProviderManager.this.mAppOpsHelper.finishOp(42, getIdentity());
        }
      }
    }

    public final boolean isUsingHighPower() {
      ProviderProperties properties = LocationProviderManager.this.getProperties();
      return properties != null
          && isActive()
          && getRequest().getIntervalMillis()
              < BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS
          && properties.getPowerUsage() == 3;
    }

    final boolean onLocationPermissionsChanged(String str) {
      synchronized (LocationProviderManager.this.mMultiplexerLock) {
        if (str != null) {
          if (!getIdentity().getPackageName().equals(str)) {
            return false;
          }
        }
        return onLocationPermissionsChanged();
      }
    }

    final boolean onLocationPermissionsChanged(int i) {
      synchronized (LocationProviderManager.this.mMultiplexerLock) {
        if (getIdentity().getUid() != i) {
          return false;
        }
        return onLocationPermissionsChanged();
      }
    }

    private boolean onLocationPermissionsChanged() {
      boolean hasLocationPermissions =
          LocationProviderManager.this.mLocationPermissionsHelper.hasLocationPermissions(
              this.mPermissionLevel, getIdentity());
      if (hasLocationPermissions == this.mPermitted) {
        return false;
      }
      Log.v(
          "LocationManagerService",
          LocationProviderManager.this.mName
              + " provider package "
              + getIdentity().getPackageName()
              + " permitted = "
              + hasLocationPermissions);
      this.mPermitted = hasLocationPermissions;
      if (hasLocationPermissions) {
        LocationEventLog.EVENT_LOG.logProviderClientPermitted(
            LocationProviderManager.this.mName, getIdentity());
      } else {
        LocationEventLog.EVENT_LOG.logProviderClientUnpermitted(
            LocationProviderManager.this.mName, getIdentity());
      }
      LocationProviderManager.this.mNSLocationProviderHelper.updateRegistrationAccessStatus(
          LocationProviderManager.this.mName,
          this,
          LocationProviderManager.this.mLocationPermissionsHelper.getReasonByCaller(
              getIdentity().getUid()));
      return true;
    }

    public final boolean onAdasGnssLocationEnabledChanged(int i) {
      synchronized (LocationProviderManager.this.mMultiplexerLock) {
        if (getIdentity().getUserId() != i) {
          return false;
        }
        return onProviderLocationRequestChanged();
      }
    }

    final boolean onForegroundChanged(int i, boolean z) {
      synchronized (LocationProviderManager.this.mMultiplexerLock) {
        if (getIdentity().getUid() != i || z == this.mForeground) {
          return false;
        }
        Log.v(
            "LocationManagerService",
            LocationProviderManager.this.mName + " provider uid " + i + " foreground = " + z);
        this.mForeground = z;
        if (z) {
          LocationEventLog.EVENT_LOG.logProviderClientForeground(
              LocationProviderManager.this.mName, getIdentity());
        } else {
          LocationEventLog.EVENT_LOG.logProviderClientBackground(
              LocationProviderManager.this.mName, getIdentity());
        }
        return onProviderLocationRequestChanged()
            || LocationProviderManager.this.mLocationPowerSaveModeHelper.getLocationPowerSaveMode()
                == 3;
      }
    }

    public final boolean onProviderLocationRequestChanged() {
      synchronized (LocationProviderManager.this.mMultiplexerLock) {
        LocationRequest calculateProviderLocationRequest = calculateProviderLocationRequest();
        if (this.mProviderLocationRequest.equals(calculateProviderLocationRequest)) {
          return false;
        }
        LocationRequest locationRequest = this.mProviderLocationRequest;
        this.mProviderLocationRequest = calculateProviderLocationRequest;
        onHighPowerUsageChanged();
        LocationProviderManager.this.updateService();
        return locationRequest.isBypass() != calculateProviderLocationRequest.isBypass();
      }
    }

    public final LocationRequest calculateProviderLocationRequest() {
      LocationRequest.Builder builder = new LocationRequest.Builder(this.mBaseRequest);
      if (this.mPermissionLevel < 2) {
        builder.setQuality(104);
        if (this.mBaseRequest.getIntervalMillis() < 600000) {
          builder.setIntervalMillis(600000L);
        }
        if (this.mBaseRequest.getMinUpdateIntervalMillis() < 600000) {
          builder.setMinUpdateIntervalMillis(600000L);
        }
      }
      boolean isLocationSettingsIgnored = this.mBaseRequest.isLocationSettingsIgnored();
      boolean z = false;
      if (isLocationSettingsIgnored) {
        if (!LocationProviderManager.this
                .mSettingsHelper
                .getIgnoreSettingsAllowlist()
                .contains(getIdentity().getPackageName(), getIdentity().getAttributionTag())
            && !LocationProviderManager.this.mLocationManagerInternal.isProvider(
                (String) null, getIdentity())) {
          isLocationSettingsIgnored = false;
        }
        builder.setLocationSettingsIgnored(isLocationSettingsIgnored);
      }
      boolean isAdasGnssBypass = this.mBaseRequest.isAdasGnssBypass();
      if (isAdasGnssBypass) {
        if (!"gps".equals(LocationProviderManager.this.mName)) {
          Log.e("LocationManagerService", "adas gnss bypass request received in non-gps provider");
        } else if (LocationProviderManager.this.mUserHelper.isCurrentUserId(
                getIdentity().getUserId())
            && LocationProviderManager.this
                .mLocationSettings
                .getUserSettings(getIdentity().getUserId())
                .isAdasGnssLocationEnabled()
            && LocationProviderManager.this
                .mSettingsHelper
                .getAdasAllowlist()
                .contains(getIdentity().getPackageName(), getIdentity().getAttributionTag())) {
          z = isAdasGnssBypass;
        }
        builder.setAdasGnssBypass(z);
      }
      if (!isLocationSettingsIgnored && !isThrottlingExempt() && !this.mForeground) {
        builder.setIntervalMillis(
            Math.max(
                this.mBaseRequest.getIntervalMillis(),
                LocationProviderManager.this.mSettingsHelper.getBackgroundThrottleIntervalMs()));
      }
      return builder.build();
    }

    public final boolean isThrottlingExempt() {
      if (LocationProviderManager.this
              .mSettingsHelper
              .getBackgroundThrottlePackageWhitelist()
              .contains(getIdentity().getPackageName())
          || LocationProviderManager.this.mSettingsHelper.isBackgroundThrottlingAllowlistByNsflp(
              getIdentity().getPackageName())) {
        return true;
      }
      return LocationProviderManager.this.mLocationManagerInternal.isProvider(
          (String) null, getIdentity());
    }

    public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append(getIdentity());
      ArraySet arraySet = new ArraySet(2);
      if (!isForeground()) {
        arraySet.add("bg");
      }
      if (!isPermitted()) {
        arraySet.add("na");
      }
      if (!arraySet.isEmpty()) {
        sb.append(" ");
        sb.append(arraySet);
      }
      if (this.mPermissionLevel == 1) {
        sb.append(" (COARSE)");
      }
      sb.append(" ");
      sb.append(getRequest());
      return sb.toString();
    }
  }

  public abstract class LocationRegistration extends Registration
      implements AlarmManager.OnAlarmListener, LocationManagerInternal.ProviderEnabledListener {
    public long mExpirationRealtimeMs;
    public int mNumLocationsDelivered;
    public volatile ProviderTransport mProviderTransport;
    public final PowerManager.WakeLock mWakeLock;
    public final ExternalWakeLockReleaser mWakeLockReleaser;

    public abstract void onProviderOperationFailure(
        ListenerExecutor.ListenerOperation listenerOperation, Exception exc);

    public LocationRegistration(
        LocationRequest locationRequest,
        CallerIdentity callerIdentity,
        Executor executor,
        LocationTransport locationTransport,
        int i) {
      super(locationRequest, callerIdentity, executor, locationTransport, i);
      this.mNumLocationsDelivered = 0;
      this.mExpirationRealtimeMs = Long.MAX_VALUE;
      this.mProviderTransport = (ProviderTransport) locationTransport;
      PowerManager powerManager =
          (PowerManager) LocationProviderManager.this.mContext.getSystemService(PowerManager.class);
      Objects.requireNonNull(powerManager);
      PowerManager.WakeLock newWakeLock = powerManager.newWakeLock(1, "*location*");
      this.mWakeLock = newWakeLock;
      newWakeLock.setReferenceCounted(true);
      newWakeLock.setWorkSource(locationRequest.getWorkSource());
      this.mWakeLockReleaser = new ExternalWakeLockReleaser(callerIdentity, newWakeLock);
    }

    @Override // com.android.server.location.listeners.ListenerRegistration
    public void onListenerUnregister() {
      this.mProviderTransport = null;
    }

    @Override // com.android.server.location.provider.LocationProviderManager.Registration,
              // com.android.server.location.listeners.RemovableListenerRegistration
    public void onRegister() {
      super.onRegister();
      long elapsedRealtime = SystemClock.elapsedRealtime();
      long expirationRealtimeMs = getRequest().getExpirationRealtimeMs(elapsedRealtime);
      this.mExpirationRealtimeMs = expirationRealtimeMs;
      if (expirationRealtimeMs <= elapsedRealtime) {
        onAlarm();
      } else if (expirationRealtimeMs < Long.MAX_VALUE) {
        LocationProviderManager.this.mAlarmHelper.setDelayedAlarm(
            expirationRealtimeMs - elapsedRealtime, this, null);
      }
      LocationProviderManager.this.addEnabledListener(this);
      int userId = getIdentity().getUserId();
      if (LocationProviderManager.this.isEnabled(userId)) {
        return;
      }
      onProviderEnabledChanged(LocationProviderManager.this.mName, userId, false);
    }

    @Override // com.android.server.location.provider.LocationProviderManager.Registration,
              // com.android.server.location.listeners.RemovableListenerRegistration,
              // com.android.server.location.listeners.ListenerRegistration
    public void onUnregister() {
      LocationProviderManager.this.removeEnabledListener(this);
      if (this.mExpirationRealtimeMs < Long.MAX_VALUE) {
        LocationProviderManager.this.mAlarmHelper.cancel(this);
      }
      super.onUnregister();
    }

    @Override // com.android.server.location.provider.LocationProviderManager.Registration,
              // com.android.server.location.listeners.ListenerRegistration
    public void onActive() {
      Location lastLocationUnsafe;
      super.onActive();
      if (CompatChanges.isChangeEnabled(73144566L, getIdentity().getUid())) {
        long intervalMillis = getRequest().getIntervalMillis();
        Location lastDeliveredLocation = getLastDeliveredLocation();
        if (lastDeliveredLocation != null) {
          intervalMillis =
              Math.min(intervalMillis, lastDeliveredLocation.getElapsedRealtimeAgeMillis() - 1);
        }
        long j = intervalMillis;
        if (j <= 30000
            || (lastLocationUnsafe =
                    LocationProviderManager.this.getLastLocationUnsafe(
                        getIdentity().getUserId(),
                        getPermissionLevel(),
                        getRequest().isBypass(),
                        j))
                == null) {
          return;
        }
        executeOperation(
            acceptLocationChange(LocationResult.wrap(new Location[] {lastLocationUnsafe})));
      }
    }

    @Override // android.app.AlarmManager.OnAlarmListener
    public void onAlarm() {
      Log.d(
          "LocationManagerService",
          LocationProviderManager.this.mName
              + " provider registration "
              + getIdentity()
              + " expired at "
              + TimeUtils.formatRealtime(this.mExpirationRealtimeMs));
      synchronized (LocationProviderManager.this.mMultiplexerLock) {
        this.mExpirationRealtimeMs = Long.MAX_VALUE;
        remove();
      }
    }

    @Override // com.android.server.location.provider.LocationProviderManager.Registration
    public ListenerExecutor.ListenerOperation acceptLocationChange(LocationResult locationResult) {
      if (SystemClock.elapsedRealtime() >= this.mExpirationRealtimeMs) {
        Log.d(
            "LocationManagerService",
            LocationProviderManager.this.mName
                + " provider registration "
                + getIdentity()
                + " expired at "
                + TimeUtils.formatRealtime(this.mExpirationRealtimeMs));
        remove();
        return null;
      }
      LocationResult permittedLocationResult =
          LocationProviderManager.this.getPermittedLocationResult(
              locationResult, getPermissionLevel());
      Objects.requireNonNull(permittedLocationResult);
      final LocationResult filter =
          permittedLocationResult.filter(
              new Predicate() { // from class:
                                // com.android.server.location.provider.LocationProviderManager.LocationRegistration.1
                public Location mPreviousLocation;

                {
                  this.mPreviousLocation = LocationRegistration.this.getLastDeliveredLocation();
                }

                @Override // java.util.function.Predicate
                public boolean test(Location location) {
                  if (this.mPreviousLocation != null) {
                    if (location.getElapsedRealtimeMillis()
                            - this.mPreviousLocation.getElapsedRealtimeMillis()
                        < LocationRegistration.this.getRequest().getMinUpdateIntervalMillis()
                            - Math.min(
                                (long)
                                    (LocationRegistration.this.getRequest().getIntervalMillis()
                                        * 0.1f),
                                30000L)) {
                      return false;
                    }
                    double minUpdateDistanceMeters =
                        LocationRegistration.this.getRequest().getMinUpdateDistanceMeters();
                    if (minUpdateDistanceMeters > 0.0d
                        && location.distanceTo(this.mPreviousLocation) <= minUpdateDistanceMeters) {
                      return false;
                    }
                  }
                  this.mPreviousLocation = location;
                  return true;
                }
              });
      if (filter == null) {
        return null;
      }
      if (!LocationProviderManager.this.mAppOpsHelper.noteOpNoThrow(
          LocationPermissions.asAppOp(getPermissionLevel()), getIdentity())) {
        Log.w(
            "LocationManagerService",
            LocationProviderManager.this.mName
                + " provider registration "
                + getIdentity()
                + " noteOp denied");
        return null;
      }
      final boolean z = getRequest().getIntervalMillis() != Long.MAX_VALUE;
      return new ListenerExecutor
          .ListenerOperation() { // from class:
                                 // com.android.server.location.provider.LocationProviderManager.LocationRegistration.2
        public void onPreExecute() {
          LocationRegistration.this.setLastDeliveredLocation(filter.getLastLocation());
          if (z) {
            LocationRegistration.this.mWakeLock.acquire(30000L);
          }
        }

        public void operate(LocationTransport locationTransport) {
          LocationResult locationResult2;
          if (LocationRegistration.this.getIdentity().getPid() == Process.myPid()) {
            locationResult2 = filter.deepCopy();
          } else {
            locationResult2 = filter;
          }
          locationTransport.deliverOnLocationChanged(
              locationResult2, z ? LocationRegistration.this.mWakeLockReleaser : null);
          LocationEventLog.EVENT_LOG.logProviderDeliveredLocations(
              LocationProviderManager.this.mName,
              filter.size(),
              LocationRegistration.this.getIdentity());
        }

        public void onPostExecute(boolean z2) {
          if (!z2 && z) {
            LocationRegistration.this.mWakeLock.release();
          }
          if (z2) {
            LocationRegistration locationRegistration = LocationRegistration.this;
            int i = locationRegistration.mNumLocationsDelivered + 1;
            locationRegistration.mNumLocationsDelivered = i;
            if (i >= LocationRegistration.this.getRequest().getMaxUpdates()) {
              Log.d(
                  "LocationManagerService",
                  LocationProviderManager.this.mName
                      + " provider registration "
                      + LocationRegistration.this.getIdentity()
                      + " finished after "
                      + LocationRegistration.this.mNumLocationsDelivered
                      + " updates");
              LocationRegistration.this.remove();
            }
          }
        }
      };
    }

    public void onProviderEnabledChanged(String str, int i, final boolean z) {
      Preconditions.checkState(LocationProviderManager.this.mName.equals(str));
      if (i != getIdentity().getUserId()) {
        return;
      }
      executeSafely(
          getExecutor(),
          new Supplier() { // from class:
                           // com.android.server.location.provider.LocationProviderManager$LocationRegistration$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
              LocationProviderManager.ProviderTransport lambda$onProviderEnabledChanged$0;
              lambda$onProviderEnabledChanged$0 =
                  LocationProviderManager.LocationRegistration.this
                      .lambda$onProviderEnabledChanged$0();
              return lambda$onProviderEnabledChanged$0;
            }
          },
          new ListenerExecutor
              .ListenerOperation() { // from class:
                                     // com.android.server.location.provider.LocationProviderManager$LocationRegistration$$ExternalSyntheticLambda1
            public final void operate(Object obj) {
              LocationProviderManager.LocationRegistration.this.lambda$onProviderEnabledChanged$1(
                  z, (LocationProviderManager.ProviderTransport) obj);
            }
          },
          new ListenerExecutor
              .FailureCallback() { // from class:
                                   // com.android.server.location.provider.LocationProviderManager$LocationRegistration$$ExternalSyntheticLambda2
            public final void onFailure(
                ListenerExecutor.ListenerOperation listenerOperation, Exception exc) {
              LocationProviderManager.LocationRegistration.this.onProviderOperationFailure(
                  listenerOperation, exc);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ ProviderTransport lambda$onProviderEnabledChanged$0() {
      return this.mProviderTransport;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onProviderEnabledChanged$1(
        boolean z, ProviderTransport providerTransport) {
      providerTransport.deliverOnProviderEnabledChanged(LocationProviderManager.this.mName, z);
    }
  }

  public final class LocationListenerRegistration extends LocationRegistration
      implements IBinder.DeathRecipient {
    public LocationListenerRegistration(
        LocationRequest locationRequest,
        CallerIdentity callerIdentity,
        LocationListenerTransport locationListenerTransport,
        int i) {
      super(
          locationRequest,
          callerIdentity,
          callerIdentity.isMyProcess()
              ? LocationServiceThread.getExecutor()
              : ConcurrentUtils.DIRECT_EXECUTOR,
          locationListenerTransport,
          i);
    }

    @Override // com.android.server.location.provider.LocationProviderManager.LocationRegistration,
              // com.android.server.location.provider.LocationProviderManager.Registration,
              // com.android.server.location.listeners.RemovableListenerRegistration
    public void onRegister() {
      super.onRegister();
      try {
        ((IBinder) getKey()).linkToDeath(this, 0);
      } catch (RemoteException unused) {
        remove();
      }
    }

    @Override // com.android.server.location.provider.LocationProviderManager.LocationRegistration,
              // com.android.server.location.provider.LocationProviderManager.Registration,
              // com.android.server.location.listeners.RemovableListenerRegistration,
              // com.android.server.location.listeners.ListenerRegistration
    public void onUnregister() {
      try {
        ((IBinder) getKey()).unlinkToDeath(this, 0);
      } catch (NoSuchElementException e) {
        Log.w(getTag(), "failed to unregister binder death listener", e);
      }
      super.onUnregister();
    }

    @Override // com.android.server.location.provider.LocationProviderManager.LocationRegistration
    public void onProviderOperationFailure(
        ListenerExecutor.ListenerOperation listenerOperation, Exception exc) {
      onTransportFailure(exc);
    }

    @Override // com.android.server.location.listeners.ListenerRegistration
    public void onOperationFailure(
        ListenerExecutor.ListenerOperation listenerOperation, Exception exc) {
      onTransportFailure(exc);
    }

    public final void onTransportFailure(Exception exc) {
      if (exc instanceof RemoteException) {
        Log.w(
            "LocationManagerService",
            LocationProviderManager.this.mName
                + " provider registration "
                + getIdentity()
                + " removed",
            exc);
        remove();
        return;
      }
      throw new AssertionError(exc);
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
      try {
        Log.d(
            "LocationManagerService",
            LocationProviderManager.this.mName
                + " provider registration "
                + getIdentity()
                + " died");
        remove();
      } catch (RuntimeException e) {
        throw new AssertionError(e);
      }
    }
  }

  public final class LocationPendingIntentRegistration extends LocationRegistration
      implements PendingIntent.CancelListener {
    public LocationPendingIntentRegistration(
        LocationRequest locationRequest,
        CallerIdentity callerIdentity,
        LocationPendingIntentTransport locationPendingIntentTransport,
        int i) {
      super(
          locationRequest,
          callerIdentity,
          ConcurrentUtils.DIRECT_EXECUTOR,
          locationPendingIntentTransport,
          i);
    }

    @Override // com.android.server.location.provider.LocationProviderManager.LocationRegistration,
              // com.android.server.location.provider.LocationProviderManager.Registration,
              // com.android.server.location.listeners.RemovableListenerRegistration
    public void onRegister() {
      super.onRegister();
      if (((PendingIntent) getKey()).addCancelListener(ConcurrentUtils.DIRECT_EXECUTOR, this)) {
        return;
      }
      remove();
    }

    @Override // com.android.server.location.provider.LocationProviderManager.LocationRegistration,
              // com.android.server.location.provider.LocationProviderManager.Registration,
              // com.android.server.location.listeners.RemovableListenerRegistration,
              // com.android.server.location.listeners.ListenerRegistration
    public void onUnregister() {
      ((PendingIntent) getKey()).removeCancelListener(this);
      super.onUnregister();
    }

    @Override // com.android.server.location.provider.LocationProviderManager.LocationRegistration
    public void onProviderOperationFailure(
        ListenerExecutor.ListenerOperation listenerOperation, Exception exc) {
      onTransportFailure(exc);
    }

    @Override // com.android.server.location.listeners.ListenerRegistration
    public void onOperationFailure(
        ListenerExecutor.ListenerOperation listenerOperation, Exception exc) {
      onTransportFailure(exc);
    }

    public final void onTransportFailure(Exception exc) {
      if (exc instanceof PendingIntent.CanceledException) {
        Log.w(
            "LocationManagerService",
            LocationProviderManager.this.mName
                + " provider registration "
                + getIdentity()
                + " removed",
            exc);
        remove();
        return;
      }
      throw new AssertionError(exc);
    }

    public void onCanceled(PendingIntent pendingIntent) {
      Log.d(
          "LocationManagerService",
          LocationProviderManager.this.mName
              + " provider registration "
              + getIdentity()
              + " canceled");
      remove();
    }
  }

  public final class GetCurrentLocationListenerRegistration extends Registration
      implements IBinder.DeathRecipient, AlarmManager.OnAlarmListener {
    public long mExpirationRealtimeMs;

    public GetCurrentLocationListenerRegistration(
        LocationRequest locationRequest,
        CallerIdentity callerIdentity,
        LocationTransport locationTransport,
        int i) {
      super(
          locationRequest,
          callerIdentity,
          callerIdentity.isMyProcess()
              ? LocationServiceThread.getExecutor()
              : ConcurrentUtils.DIRECT_EXECUTOR,
          locationTransport,
          i);
      this.mExpirationRealtimeMs = Long.MAX_VALUE;
    }

    @Override // com.android.server.location.provider.LocationProviderManager.Registration,
              // com.android.server.location.listeners.RemovableListenerRegistration
    public void onRegister() {
      super.onRegister();
      try {
        ((IBinder) getKey()).linkToDeath(this, 0);
      } catch (RemoteException unused) {
        remove();
      }
      long elapsedRealtime = SystemClock.elapsedRealtime();
      long expirationRealtimeMs = getRequest().getExpirationRealtimeMs(elapsedRealtime);
      this.mExpirationRealtimeMs = expirationRealtimeMs;
      if (expirationRealtimeMs <= elapsedRealtime) {
        onAlarm();
      } else if (expirationRealtimeMs < Long.MAX_VALUE) {
        LocationProviderManager.this.mAlarmHelper.setDelayedAlarm(
            expirationRealtimeMs - elapsedRealtime, this, null);
      }
    }

    @Override // com.android.server.location.provider.LocationProviderManager.Registration,
              // com.android.server.location.listeners.RemovableListenerRegistration,
              // com.android.server.location.listeners.ListenerRegistration
    public void onUnregister() {
      if (this.mExpirationRealtimeMs < Long.MAX_VALUE) {
        LocationProviderManager.this.mAlarmHelper.cancel(this);
      }
      try {
        ((IBinder) getKey()).unlinkToDeath(this, 0);
      } catch (NoSuchElementException e) {
        Log.w(getTag(), "failed to unregister binder death listener", e);
      }
      super.onUnregister();
    }

    @Override // com.android.server.location.provider.LocationProviderManager.Registration,
              // com.android.server.location.listeners.ListenerRegistration
    public void onActive() {
      super.onActive();
      Location lastLocationUnsafe =
          LocationProviderManager.this.getLastLocationUnsafe(
              getIdentity().getUserId(), getPermissionLevel(), getRequest().isBypass(), 30000L);
      if (lastLocationUnsafe != null) {
        executeOperation(
            acceptLocationChange(LocationResult.wrap(new Location[] {lastLocationUnsafe})));
      }
    }

    @Override // com.android.server.location.provider.LocationProviderManager.Registration,
              // com.android.server.location.listeners.ListenerRegistration
    public void onInactive() {
      executeOperation(acceptLocationChange(null));
      super.onInactive();
    }

    public void deliverNull() {
      executeOperation(acceptLocationChange(null));
    }

    @Override // android.app.AlarmManager.OnAlarmListener
    public void onAlarm() {
      Log.d(
          "LocationManagerService",
          LocationProviderManager.this.mName
              + " provider registration "
              + getIdentity()
              + " expired at "
              + TimeUtils.formatRealtime(this.mExpirationRealtimeMs));
      synchronized (LocationProviderManager.this.mMultiplexerLock) {
        this.mExpirationRealtimeMs = Long.MAX_VALUE;
        executeOperation(acceptLocationChange(null));
      }
    }

    @Override // com.android.server.location.provider.LocationProviderManager.Registration
    public ListenerExecutor.ListenerOperation acceptLocationChange(LocationResult locationResult) {
      LocationResult locationResult2 = null;
      if (SystemClock.elapsedRealtime() >= this.mExpirationRealtimeMs) {
        Log.d(
            "LocationManagerService",
            LocationProviderManager.this.mName
                + " provider registration "
                + getIdentity()
                + " expired at "
                + TimeUtils.formatRealtime(this.mExpirationRealtimeMs));
        locationResult = null;
      }
      if (locationResult == null
          || LocationProviderManager.this.mAppOpsHelper.noteOpNoThrow(
              LocationPermissions.asAppOp(getPermissionLevel()), getIdentity())) {
        locationResult2 = locationResult;
      } else {
        Log.w("LocationManagerService", "noteOp denied for " + getIdentity());
      }
      if (locationResult2 != null) {
        locationResult2 = locationResult2.asLastLocationResult();
      }
      final LocationResult permittedLocationResult =
          LocationProviderManager.this.getPermittedLocationResult(
              locationResult2, getPermissionLevel());
      return new ListenerExecutor
          .ListenerOperation() { // from class:
                                 // com.android.server.location.provider.LocationProviderManager.GetCurrentLocationListenerRegistration.1
        public void operate(LocationTransport locationTransport) {
          LocationResult locationResult3;
          LocationResult locationResult4;
          if (GetCurrentLocationListenerRegistration.this.getIdentity().getPid() == Process.myPid()
              && (locationResult4 = permittedLocationResult) != null) {
            locationResult3 = locationResult4.deepCopy();
          } else {
            locationResult3 = permittedLocationResult;
          }
          locationTransport.deliverOnLocationChanged(locationResult3, null);
          LocationEventLog locationEventLog = LocationEventLog.EVENT_LOG;
          String str = LocationProviderManager.this.mName;
          LocationResult locationResult5 = permittedLocationResult;
          locationEventLog.logProviderDeliveredLocations(
              str,
              locationResult5 != null ? locationResult5.size() : 0,
              GetCurrentLocationListenerRegistration.this.getIdentity());
        }

        public void onPostExecute(boolean z) {
          if (z) {
            GetCurrentLocationListenerRegistration.this.remove();
          }
        }
      };
    }

    @Override // com.android.server.location.listeners.ListenerRegistration
    public void onOperationFailure(
        ListenerExecutor.ListenerOperation listenerOperation, Exception exc) {
      if (exc instanceof RemoteException) {
        Log.w(
            "LocationManagerService",
            LocationProviderManager.this.mName
                + " provider registration "
                + getIdentity()
                + " removed",
            exc);
        remove();
        return;
      }
      throw new AssertionError(exc);
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
      try {
        Log.d(
            "LocationManagerService",
            LocationProviderManager.this.mName
                + " provider registration "
                + getIdentity()
                + " died");
        remove();
      } catch (RuntimeException e) {
        throw new AssertionError(e);
      }
    }
  }

  public LocationProviderManager(
      Context context,
      Injector injector,
      String str,
      PassiveLocationProviderManager passiveLocationProviderManager) {
    this(context, injector, str, passiveLocationProviderManager, Collections.emptyList());
  }

  public LocationProviderManager(
      Context context,
      Injector injector,
      String str,
      PassiveLocationProviderManager passiveLocationProviderManager,
      Collection collection) {
    this.mUserChangedListener =
        new UserInfoHelper
            .UserListener() { // from class:
                              // com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda7
          @Override // com.android.server.location.injector.UserInfoHelper.UserListener
          public final void onUserChanged(int i, int i2) {
            LocationProviderManager.this.onUserChanged(i, i2);
          }
        };
    this.mLocationUserSettingsListener =
        new LocationSettings
            .LocationUserSettingsListener() { // from class:
                                              // com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda10
          @Override // com.android.server.location.settings.LocationSettings.LocationUserSettingsListener
          public final void onLocationUserSettingsChanged(
              int i,
              LocationUserSettings locationUserSettings,
              LocationUserSettings locationUserSettings2) {
            LocationProviderManager.this.onLocationUserSettingsChanged(
                i, locationUserSettings, locationUserSettings2);
          }
        };
    this.mLocationEnabledChangedListener =
        new SettingsHelper
            .UserSettingChangedListener() { // from class:
                                            // com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda11
          @Override // com.android.server.location.injector.SettingsHelper.UserSettingChangedListener
          public final void onSettingChanged(int i) {
            LocationProviderManager.this.onLocationEnabledChanged(i);
          }
        };
    this.mBackgroundThrottlePackageWhitelistChangedListener =
        new SettingsHelper
            .GlobalSettingChangedListener() { // from class:
                                              // com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda12
          @Override // com.android.server.location.injector.SettingsHelper.GlobalSettingChangedListener
          public final void onSettingChanged() {
            LocationProviderManager.this.onBackgroundThrottlePackageWhitelistChanged();
          }
        };
    this.mLocationPackageBlacklistChangedListener =
        new SettingsHelper
            .UserSettingChangedListener() { // from class:
                                            // com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda13
          @Override // com.android.server.location.injector.SettingsHelper.UserSettingChangedListener
          public final void onSettingChanged(int i) {
            LocationProviderManager.this.onLocationPackageBlacklistChanged(i);
          }
        };
    this.mLocationPermissionsListener =
        new LocationPermissionsHelper
            .LocationPermissionsListener() { // from class:
                                             // com.android.server.location.provider.LocationProviderManager.1
          @Override // com.android.server.location.injector.LocationPermissionsHelper.LocationPermissionsListener
          public void onLocationPermissionsChanged(String str2) {
            LocationProviderManager.this.onLocationPermissionsChanged(str2);
          }

          @Override // com.android.server.location.injector.LocationPermissionsHelper.LocationPermissionsListener
          public void onLocationPermissionsChanged(int i) {
            LocationProviderManager.this.onLocationPermissionsChanged(i);
          }
        };
    this.mAppForegroundChangedListener =
        new AppForegroundHelper
            .AppForegroundListener() { // from class:
                                       // com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda14
          @Override // com.android.server.location.injector.AppForegroundHelper.AppForegroundListener
          public final void onAppForegroundChanged(int i, boolean z) {
            LocationProviderManager.this.onAppForegroundChanged(i, z);
          }
        };
    this.mBackgroundThrottleIntervalChangedListener =
        new SettingsHelper
            .GlobalSettingChangedListener() { // from class:
                                              // com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda15
          @Override // com.android.server.location.injector.SettingsHelper.GlobalSettingChangedListener
          public final void onSettingChanged() {
            LocationProviderManager.this.onBackgroundThrottleIntervalChanged();
          }
        };
    this.mAdasPackageAllowlistChangedListener =
        new SettingsHelper
            .GlobalSettingChangedListener() { // from class:
                                              // com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda16
          @Override // com.android.server.location.injector.SettingsHelper.GlobalSettingChangedListener
          public final void onSettingChanged() {
            LocationProviderManager.this.onAdasAllowlistChanged();
          }
        };
    this.mIgnoreSettingsPackageWhitelistChangedListener =
        new SettingsHelper
            .GlobalSettingChangedListener() { // from class:
                                              // com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda17
          @Override // com.android.server.location.injector.SettingsHelper.GlobalSettingChangedListener
          public final void onSettingChanged() {
            LocationProviderManager.this.onIgnoreSettingsWhitelistChanged();
          }
        };
    this.mLocationPowerSaveModeChangedListener =
        new LocationPowerSaveModeHelper
            .LocationPowerSaveModeChangedListener() { // from class:
                                                      // com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda18
          @Override // com.android.server.location.injector.LocationPowerSaveModeHelper.LocationPowerSaveModeChangedListener
          public final void onLocationPowerSaveModeChanged(int i) {
            LocationProviderManager.this.onLocationPowerSaveModeChanged(i);
          }
        };
    this.mScreenInteractiveChangedListener =
        new ScreenInteractiveHelper
            .ScreenInteractiveChangedListener() { // from class:
                                                  // com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda8
          @Override // com.android.server.location.injector.ScreenInteractiveHelper.ScreenInteractiveChangedListener
          public final void onScreenInteractiveChanged(boolean z) {
            LocationProviderManager.this.onScreenInteractiveChanged(z);
          }
        };
    this.mMotionPowerSaveModeChangedListener =
        new NSLocationProviderHelper
            .MotionPowerSaveModeChangedListener() { // from class:
                                                    // com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda9
          @Override // com.android.server.location.nsflp.NSLocationProviderHelper.MotionPowerSaveModeChangedListener
          public final void onMotionPowerSaveModeChanged(boolean z) {
            LocationProviderManager.this.onMotionPowerSaveModeChanged(z);
          }
        };
    this.mPackageResetResponder =
        new PackageResetHelper
            .Responder() { // from class:
                           // com.android.server.location.provider.LocationProviderManager.2
          @Override // com.android.server.location.injector.PackageResetHelper.Responder
          public void onPackageReset(String str2) {
            LocationProviderManager.this.onPackageReset(str2);
          }

          @Override // com.android.server.location.injector.PackageResetHelper.Responder
          public boolean isResetableForPackage(String str2) {
            return LocationProviderManager.this.isResetableForPackage(str2);
          }
        };
    this.mIsSettingsIgnored = false;
    this.mInactiveMotionRegistrations = new HashSet();
    this.mActiveOriginalRegistrations = new HashSet();
    this.mContext = context;
    Objects.requireNonNull(str);
    this.mName = str;
    this.mPassiveManager = passiveLocationProviderManager;
    this.mState = 2;
    this.mEnabled = new SparseBooleanArray(2);
    this.mLastLocations = new SparseArray(2);
    this.mRequiredPermissions = collection;
    this.mEnabledListeners = new ArrayList();
    this.mProviderRequestListeners = new CopyOnWriteArrayList();
    LocationManagerInternal locationManagerInternal =
        (LocationManagerInternal) LocalServices.getService(LocationManagerInternal.class);
    Objects.requireNonNull(locationManagerInternal);
    this.mLocationManagerInternal = locationManagerInternal;
    this.mLocationSettings = injector.getLocationSettings();
    SettingsHelper settingsHelper = injector.getSettingsHelper();
    this.mSettingsHelper = settingsHelper;
    this.mUserHelper = injector.getUserInfoHelper();
    this.mAlarmHelper = injector.getAlarmHelper();
    this.mAppOpsHelper = injector.getAppOpsHelper();
    this.mLocationPermissionsHelper = injector.getLocationPermissionsHelper();
    this.mAppForegroundHelper = injector.getAppForegroundHelper();
    this.mLocationPowerSaveModeHelper = injector.getLocationPowerSaveModeHelper();
    this.mScreenInteractiveHelper = injector.getScreenInteractiveHelper();
    this.mLocationUsageLogger = injector.getLocationUsageLogger();
    this.mLocationFudger = new LocationFudger(settingsHelper.getCoarseLocationAccuracyM());
    this.mPackageResetHelper = injector.getPackageResetHelper();
    MockableLocationProvider mockableLocationProvider =
        new MockableLocationProvider(this.mMultiplexerLock);
    this.mProvider = mockableLocationProvider;
    mockableLocationProvider.getController().setListener(this);
    this.mNSLocationProviderHelper = injector.getNSLocationProviderHelper();
    this.mNSConnectionHelper = injector.getNSConnectionHelper();
  }

  public void startManager(StateChangedListener stateChangedListener) {
    synchronized (this.mMultiplexerLock) {
      Preconditions.checkState(this.mState == 2);
      this.mState = 0;
      this.mStateChangedListener = stateChangedListener;
      this.mUserHelper.addListener(this.mUserChangedListener);
      this.mLocationSettings.registerLocationUserSettingsListener(
          this.mLocationUserSettingsListener);
      this.mSettingsHelper.addOnLocationEnabledChangedListener(
          this.mLocationEnabledChangedListener);
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        this.mProvider.getController().start();
        onUserStarted(-1);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
  }

  public void stopManager() {
    synchronized (this.mMultiplexerLock) {
      Preconditions.checkState(this.mState == 0);
      this.mState = 1;
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        onEnabledChanged(-1);
        removeRegistrationIf(
            new Predicate() { // from class:
                              // com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda20
              @Override // java.util.function.Predicate
              public final boolean test(Object obj) {
                boolean lambda$stopManager$0;
                lambda$stopManager$0 = LocationProviderManager.lambda$stopManager$0(obj);
                return lambda$stopManager$0;
              }
            });
        this.mProvider.getController().stop();
        Binder.restoreCallingIdentity(clearCallingIdentity);
        this.mUserHelper.removeListener(this.mUserChangedListener);
        this.mLocationSettings.unregisterLocationUserSettingsListener(
            this.mLocationUserSettingsListener);
        this.mSettingsHelper.removeOnLocationEnabledChangedListener(
            this.mLocationEnabledChangedListener);
        Preconditions.checkState(this.mEnabledListeners.isEmpty());
        this.mProviderRequestListeners.clear();
        this.mEnabled.clear();
        this.mLastLocations.clear();
        this.mStateChangedListener = null;
        this.mState = 2;
      } catch (Throwable th) {
        Binder.restoreCallingIdentity(clearCallingIdentity);
        throw th;
      }
    }
  }

  public String getName() {
    return this.mName;
  }

  public AbstractLocationProvider.State getState() {
    return this.mProvider.getState();
  }

  public CallerIdentity getProviderIdentity() {
    return this.mProvider.getState().identity;
  }

  public ProviderProperties getProperties() {
    return this.mProvider.getState().properties;
  }

  public boolean hasProvider() {
    return this.mProvider.getProvider() != null;
  }

  public boolean isEnabled(int i) {
    boolean valueAt;
    if (i == -10000) {
      return false;
    }
    if (i == -2) {
      return isEnabled(this.mUserHelper.getCurrentUserId());
    }
    Preconditions.checkArgument(i >= 0);
    synchronized (this.mMultiplexerLock) {
      int indexOfKey = this.mEnabled.indexOfKey(i);
      if (indexOfKey < 0) {
        Log.w("LocationManagerService", this.mName + " provider saw user " + i + " unexpectedly");
        onEnabledChanged(i);
        indexOfKey = this.mEnabled.indexOfKey(i);
      }
      valueAt = this.mEnabled.valueAt(indexOfKey);
    }
    return valueAt;
  }

  public boolean isVisibleToCaller() {
    Iterator it = this.mRequiredPermissions.iterator();
    while (it.hasNext()) {
      if (this.mContext.checkCallingOrSelfPermission((String) it.next()) != 0) {
        return false;
      }
    }
    return true;
  }

  public void addEnabledListener(
      LocationManagerInternal.ProviderEnabledListener providerEnabledListener) {
    synchronized (this.mMultiplexerLock) {
      Preconditions.checkState(this.mState != 2);
      this.mEnabledListeners.add(providerEnabledListener);
    }
  }

  public void removeEnabledListener(
      LocationManagerInternal.ProviderEnabledListener providerEnabledListener) {
    synchronized (this.mMultiplexerLock) {
      Preconditions.checkState(this.mState != 2);
      this.mEnabledListeners.remove(providerEnabledListener);
    }
  }

  public void addProviderRequestListener(IProviderRequestListener iProviderRequestListener) {
    this.mProviderRequestListeners.add(iProviderRequestListener);
  }

  public void removeProviderRequestListener(IProviderRequestListener iProviderRequestListener) {
    this.mProviderRequestListeners.remove(iProviderRequestListener);
  }

  public void setRealProvider(AbstractLocationProvider abstractLocationProvider) {
    synchronized (this.mMultiplexerLock) {
      Preconditions.checkState(this.mState != 2);
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        this.mProvider.setRealProvider(abstractLocationProvider);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
  }

  public void setMockProvider(MockLocationProvider mockLocationProvider) {
    synchronized (this.mMultiplexerLock) {
      boolean z = true;
      Preconditions.checkState(this.mState != 2);
      LocationEventLog locationEventLog = LocationEventLog.EVENT_LOG;
      String str = this.mName;
      if (mockLocationProvider == null) {
        z = false;
      }
      locationEventLog.logProviderMocked(str, z);
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        this.mProvider.setMockProvider(mockLocationProvider);
        if (mockLocationProvider == null) {
          int size = this.mLastLocations.size();
          for (int i = 0; i < size; i++) {
            ((LastLocation) this.mLastLocations.valueAt(i)).clearMock();
          }
          this.mLocationFudger.resetOffsets();
        }
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
  }

  public void setMockProviderAllowed(boolean z) {
    synchronized (this.mMultiplexerLock) {
      if (!this.mProvider.isMock()) {
        throw new IllegalArgumentException(this.mName + " provider is not a test provider");
      }
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        this.mProvider.setMockProviderAllowed(z);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
  }

  public void setMockProviderLocation(Location location) {
    synchronized (this.mMultiplexerLock) {
      if (!this.mProvider.isMock()) {
        throw new IllegalArgumentException(this.mName + " provider is not a test provider");
      }
      String provider = location.getProvider();
      if (!TextUtils.isEmpty(provider) && !this.mName.equals(provider)) {
        EventLog.writeEvent(
            1397638484,
            "33091107",
            Integer.valueOf(Binder.getCallingUid()),
            this.mName + "!=" + provider);
      }
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        this.mProvider.setMockProviderLocation(location);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
  }

  public Location getLastLocation(
      LastLocationRequest lastLocationRequest, CallerIdentity callerIdentity, int i) {
    LastLocationRequest calculateLastLocationRequest =
        calculateLastLocationRequest(lastLocationRequest, callerIdentity);
    if (!isActive(calculateLastLocationRequest.isBypass(), callerIdentity)
        || !this.mAppOpsHelper.noteOpNoThrow(LocationPermissions.asAppOp(i), callerIdentity)) {
      return null;
    }
    Location permittedLocation =
        getPermittedLocation(
            getLastLocationUnsafe(
                callerIdentity.getUserId(),
                i,
                calculateLastLocationRequest.isBypass(),
                Long.MAX_VALUE),
            i);
    return (permittedLocation == null || callerIdentity.getPid() != Process.myPid())
        ? permittedLocation
        : new Location(permittedLocation);
  }

  public final LastLocationRequest calculateLastLocationRequest(
      LastLocationRequest lastLocationRequest, CallerIdentity callerIdentity) {
    LastLocationRequest.Builder builder = new LastLocationRequest.Builder(lastLocationRequest);
    boolean isLocationSettingsIgnored = lastLocationRequest.isLocationSettingsIgnored();
    boolean z = false;
    if (isLocationSettingsIgnored) {
      if (!this.mSettingsHelper
              .getIgnoreSettingsAllowlist()
              .contains(callerIdentity.getPackageName(), callerIdentity.getAttributionTag())
          && !this.mLocationManagerInternal.isProvider((String) null, callerIdentity)) {
        isLocationSettingsIgnored = false;
      }
      builder.setLocationSettingsIgnored(isLocationSettingsIgnored);
    }
    boolean isAdasGnssBypass = lastLocationRequest.isAdasGnssBypass();
    if (isAdasGnssBypass) {
      if (!"gps".equals(this.mName)) {
        Log.e("LocationManagerService", "adas gnss bypass request received in non-gps provider");
      } else if (this.mUserHelper.isCurrentUserId(callerIdentity.getUserId())
          && this.mLocationSettings
              .getUserSettings(callerIdentity.getUserId())
              .isAdasGnssLocationEnabled()
          && this.mSettingsHelper
              .getAdasAllowlist()
              .contains(callerIdentity.getPackageName(), callerIdentity.getAttributionTag())) {
        z = isAdasGnssBypass;
      }
      builder.setAdasGnssBypass(z);
    }
    return builder.build();
  }

  public Location getLastLocationUnsafe(int i, int i2, boolean z, long j) {
    Location location;
    Location location2 = null;
    if (i == -1) {
      for (int i3 : this.mUserHelper.getRunningUserIds()) {
        Location lastLocationUnsafe = getLastLocationUnsafe(i3, i2, z, j);
        if (location2 == null
            || (lastLocationUnsafe != null
                && lastLocationUnsafe.getElapsedRealtimeNanos()
                    > location2.getElapsedRealtimeNanos())) {
          location2 = lastLocationUnsafe;
        }
      }
      return location2;
    }
    if (i == -2) {
      return getLastLocationUnsafe(this.mUserHelper.getCurrentUserId(), i2, z, j);
    }
    Preconditions.checkArgument(i >= 0);
    synchronized (this.mMultiplexerLock) {
      Preconditions.checkState(this.mState != 2);
      LastLocation lastLocation = (LastLocation) this.mLastLocations.get(i);
      location = lastLocation == null ? null : lastLocation.get(i2, z);
    }
    if (location != null && location.getElapsedRealtimeAgeMillis() <= j) {
      return location;
    }
    return null;
  }

  public void injectLastLocation(Location location, int i) {
    synchronized (this.mMultiplexerLock) {
      Preconditions.checkState(this.mState != 2);
      if (getLastLocationUnsafe(i, 2, false, Long.MAX_VALUE) == null) {
        setLastLocation(location, i);
      }
    }
  }

  public final void setLastLocation(Location location, int i) {
    if (i == -1) {
      for (int i2 : this.mUserHelper.getRunningUserIds()) {
        setLastLocation(location, i2);
      }
      return;
    }
    if (i == -2) {
      setLastLocation(location, this.mUserHelper.getCurrentUserId());
      return;
    }
    Preconditions.checkArgument(i >= 0);
    synchronized (this.mMultiplexerLock) {
      LastLocation lastLocation = (LastLocation) this.mLastLocations.get(i);
      if (lastLocation == null) {
        lastLocation = new LastLocation();
        this.mLastLocations.put(i, lastLocation);
      }
      if (isEnabled(i)) {
        lastLocation.set(location);
      }
      lastLocation.setBypass(location);
    }
  }

  public ICancellationSignal getCurrentLocation(
      LocationRequest locationRequest,
      CallerIdentity callerIdentity,
      int i,
      final ILocationCallback iLocationCallback) {
    if (locationRequest.getDurationMillis() > 30000) {
      locationRequest =
          new LocationRequest.Builder(locationRequest).setDurationMillis(30000L).build();
    }
    final GetCurrentLocationListenerRegistration getCurrentLocationListenerRegistration =
        new GetCurrentLocationListenerRegistration(
            locationRequest, callerIdentity, new GetCurrentLocationTransport(iLocationCallback), i);
    synchronized (this.mMultiplexerLock) {
      Preconditions.checkState(this.mState != 2);
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        putRegistration(iLocationCallback.asBinder(), getCurrentLocationListenerRegistration);
        if (!getCurrentLocationListenerRegistration.isActive()) {
          getCurrentLocationListenerRegistration.deliverNull();
        }
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
    ICancellationSignal createTransport = CancellationSignal.createTransport();
    CancellationSignal.fromTransport(createTransport)
        .setOnCancelListener(
            new CancellationSignal
                .OnCancelListener() { // from class:
                                      // com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda6
              @Override // android.os.CancellationSignal.OnCancelListener
              public final void onCancel() {
                LocationProviderManager.this.lambda$getCurrentLocation$2(
                    iLocationCallback, getCurrentLocationListenerRegistration);
              }
            });
    return createTransport;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$getCurrentLocation$2(
      ILocationCallback iLocationCallback,
      GetCurrentLocationListenerRegistration getCurrentLocationListenerRegistration) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      try {
        removeRegistration(iLocationCallback.asBinder(), getCurrentLocationListenerRegistration);
      } catch (RuntimeException e) {
        LocationServiceThread.getExecutor()
            .execute(
                new Runnable() { // from class:
                                 // com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda27
                  @Override // java.lang.Runnable
                  public final void run() {
                    LocationProviderManager.lambda$getCurrentLocation$1(e);
                  }
                });
        throw e;
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public static /* synthetic */ void lambda$getCurrentLocation$1(
      RuntimeException runtimeException) {
    throw new AssertionError(runtimeException);
  }

  public void sendExtraCommand(int i, int i2, String str, Bundle bundle) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      this.mProvider.getController().sendExtraCommand(i, i2, str, bundle);
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public void registerLocationRequest(
      LocationRequest locationRequest,
      CallerIdentity callerIdentity,
      int i,
      final ILocationListener iLocationListener) {
    final LocationListenerRegistration locationListenerRegistration =
        new LocationListenerRegistration(
            locationRequest, callerIdentity, new LocationListenerTransport(iLocationListener), i);
    synchronized (this.mMultiplexerLock) {
      Preconditions.checkState(this.mState != 2);
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        checkLimitAndPutRegistration(
            new Runnable() { // from class:
                             // com.android.server.location.provider.LocationProviderManager.3
              @Override // java.lang.Runnable
              public void run() {
                LocationProviderManager.this.putRegistration(
                    iLocationListener.asBinder(), locationListenerRegistration);
              }
            },
            callerIdentity);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
  }

  public void registerLocationRequest(
      LocationRequest locationRequest,
      CallerIdentity callerIdentity,
      int i,
      final PendingIntent pendingIntent) {
    final LocationPendingIntentRegistration locationPendingIntentRegistration =
        new LocationPendingIntentRegistration(
            locationRequest,
            callerIdentity,
            new LocationPendingIntentTransport(this.mContext, pendingIntent),
            i);
    if (pendingIntent != null) {
      locationPendingIntentRegistration.setPendingIntentId(pendingIntent);
    }
    synchronized (this.mMultiplexerLock) {
      Preconditions.checkState(this.mState != 2);
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        checkLimitAndPutRegistration(
            new Runnable() { // from class:
                             // com.android.server.location.provider.LocationProviderManager.4
              @Override // java.lang.Runnable
              public void run() {
                LocationProviderManager.this.putRegistration(
                    pendingIntent, locationPendingIntentRegistration);
              }
            },
            callerIdentity);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
  }

  public void checkLimitAndPutRegistration(Runnable runnable, CallerIdentity callerIdentity) {
    if (callerIdentity.getUid() == 1000
        || getRegistrationCountWith(getPredicate(callerIdentity)) < 30) {
      runnable.run();
    }
  }

  public static /* synthetic */ boolean lambda$getPredicate$3(
      CallerIdentity callerIdentity, Registration registration) {
    return registration.getIdentity().getUid() == callerIdentity.getUid();
  }

  public Predicate getPredicate(final CallerIdentity callerIdentity) {
    return new Predicate() { // from class:
                             // com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda19
      @Override // java.util.function.Predicate
      public final boolean test(Object obj) {
        boolean lambda$getPredicate$3;
        lambda$getPredicate$3 =
            LocationProviderManager.lambda$getPredicate$3(
                callerIdentity, (LocationProviderManager.Registration) obj);
        return lambda$getPredicate$3;
      }
    };
  }

  public void flush(ILocationListener iLocationListener, final int i) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      if (updateRegistration(
          iLocationListener.asBinder(),
          new Predicate() { // from class:
                            // com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
              boolean lambda$flush$4;
              lambda$flush$4 =
                  LocationProviderManager.lambda$flush$4(
                      i, (LocationProviderManager.Registration) obj);
              return lambda$flush$4;
            }
          })) {
      } else {
        throw new IllegalArgumentException("unregistered listener cannot be flushed");
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public static /* synthetic */ boolean lambda$flush$4(int i, Registration registration) {
    registration.flush(i);
    return false;
  }

  public void flush(PendingIntent pendingIntent, final int i) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      if (updateRegistration(
          pendingIntent,
          new Predicate() { // from class:
                            // com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
              boolean lambda$flush$5;
              lambda$flush$5 =
                  LocationProviderManager.lambda$flush$5(
                      i, (LocationProviderManager.Registration) obj);
              return lambda$flush$5;
            }
          })) {
      } else {
        throw new IllegalArgumentException("unregistered pending intent cannot be flushed");
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public static /* synthetic */ boolean lambda$flush$5(int i, Registration registration) {
    registration.flush(i);
    return false;
  }

  public void unregisterLocationRequest(ILocationListener iLocationListener) {
    synchronized (this.mMultiplexerLock) {
      Preconditions.checkState(this.mState != 2);
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        removeRegistration(iLocationListener.asBinder());
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
  }

  public void unregisterLocationRequest(PendingIntent pendingIntent) {
    synchronized (this.mMultiplexerLock) {
      Preconditions.checkState(this.mState != 2);
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        removeRegistration(pendingIntent);
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }
  }

  @Override // com.android.server.location.listeners.ListenerMultiplexer
  public void onRegister() {
    this.mSettingsHelper.addOnBackgroundThrottleIntervalChangedListener(
        this.mBackgroundThrottleIntervalChangedListener);
    this.mSettingsHelper.addOnBackgroundThrottlePackageWhitelistChangedListener(
        this.mBackgroundThrottlePackageWhitelistChangedListener);
    this.mSettingsHelper.addOnLocationPackageBlacklistChangedListener(
        this.mLocationPackageBlacklistChangedListener);
    this.mSettingsHelper.addAdasAllowlistChangedListener(this.mAdasPackageAllowlistChangedListener);
    this.mSettingsHelper.addIgnoreSettingsAllowlistChangedListener(
        this.mIgnoreSettingsPackageWhitelistChangedListener);
    this.mLocationPermissionsHelper.addListener(this.mLocationPermissionsListener);
    this.mAppForegroundHelper.addListener(this.mAppForegroundChangedListener);
    this.mLocationPowerSaveModeHelper.addListener(this.mLocationPowerSaveModeChangedListener);
    this.mScreenInteractiveHelper.addListener(this.mScreenInteractiveChangedListener);
    this.mPackageResetHelper.register(this.mPackageResetResponder);
    this.mNSLocationProviderHelper.addListener(this.mMotionPowerSaveModeChangedListener);
  }

  @Override // com.android.server.location.listeners.ListenerMultiplexer
  public void onUnregister() {
    this.mSettingsHelper.removeOnBackgroundThrottleIntervalChangedListener(
        this.mBackgroundThrottleIntervalChangedListener);
    this.mSettingsHelper.removeOnBackgroundThrottlePackageWhitelistChangedListener(
        this.mBackgroundThrottlePackageWhitelistChangedListener);
    this.mSettingsHelper.removeOnLocationPackageBlacklistChangedListener(
        this.mLocationPackageBlacklistChangedListener);
    this.mSettingsHelper.removeAdasAllowlistChangedListener(
        this.mAdasPackageAllowlistChangedListener);
    this.mSettingsHelper.removeIgnoreSettingsAllowlistChangedListener(
        this.mIgnoreSettingsPackageWhitelistChangedListener);
    this.mLocationPermissionsHelper.removeListener(this.mLocationPermissionsListener);
    this.mAppForegroundHelper.removeListener(this.mAppForegroundChangedListener);
    this.mLocationPowerSaveModeHelper.removeListener(this.mLocationPowerSaveModeChangedListener);
    this.mScreenInteractiveHelper.removeListener(this.mScreenInteractiveChangedListener);
    this.mPackageResetHelper.unregister(this.mPackageResetResponder);
    this.mNSLocationProviderHelper.removeListener(this.mMotionPowerSaveModeChangedListener);
  }

  @Override // com.android.server.location.listeners.ListenerMultiplexer
  public void onRegistrationAdded(Object obj, Registration registration) {
    this.mLocationUsageLogger.logLocationApiUsage(
        0,
        1,
        registration.getIdentity().getPackageName(),
        registration.getIdentity().getAttributionTag(),
        this.mName,
        registration.getRequest(),
        obj instanceof PendingIntent,
        obj instanceof IBinder,
        null,
        registration.isForeground());
    NSLocationProviderHelper nSLocationProviderHelper = this.mNSLocationProviderHelper;
    if (nSLocationProviderHelper != null) {
      nSLocationProviderHelper.updateRequestInfo(this.mName, registration);
    }
  }

  @Override // com.android.server.location.listeners.ListenerMultiplexer
  public void onRegistrationReplaced(
      Object obj, Registration registration, Object obj2, Registration registration2) {
    registration2.setLastDeliveredLocation(registration.getLastDeliveredLocation());
    super.onRegistrationReplaced(
        obj, (ListenerRegistration) registration, obj2, (ListenerRegistration) registration2);
  }

  @Override // com.android.server.location.listeners.ListenerMultiplexer
  public void onRegistrationRemoved(Object obj, Registration registration) {
    this.mLocationUsageLogger.logLocationApiUsage(
        1,
        1,
        registration.getIdentity().getPackageName(),
        registration.getIdentity().getAttributionTag(),
        this.mName,
        registration.getRequest(),
        obj instanceof PendingIntent,
        obj instanceof IBinder,
        null,
        registration.isForeground());
    NSLocationProviderHelper nSLocationProviderHelper = this.mNSLocationProviderHelper;
    if (nSLocationProviderHelper != null) {
      nSLocationProviderHelper.updateRemoveInfo(this.mName, registration);
    }
  }

  @Override // com.android.server.location.listeners.ListenerMultiplexer
  public boolean registerWithService(ProviderRequest providerRequest, Collection collection) {
    if (providerRequest.isActive()) {
      return reregisterWithService(ProviderRequest.EMPTY_REQUEST, providerRequest, collection);
    }
    return true;
  }

  @Override // com.android.server.location.listeners.ListenerMultiplexer
  public void onTransferUnregisteredRegistration(Registration registration) {
    if ("gps".equals(this.mName)) {
      this.mActiveOriginalRegistrations.remove(registration);
      registration.setActiveMotionControl(false);
      if (this.mInactiveMotionRegistrations.remove(registration)
          && this.mInactiveMotionRegistrations.isEmpty()) {
        this.mNSLocationProviderHelper.onAvailableMotionStop(true);
      }
    }
  }

  @Override // com.android.server.location.listeners.ListenerMultiplexer
  public boolean reregisterWithService(
      ProviderRequest providerRequest,
      final ProviderRequest providerRequest2,
      Collection collection) {
    long calculateRequestDelayMillis =
        ((providerRequest.isBypass() || !providerRequest2.isBypass())
                && providerRequest2.getIntervalMillis() <= providerRequest.getIntervalMillis())
            ? calculateRequestDelayMillis(providerRequest2.getIntervalMillis(), collection)
            : 0L;
    Preconditions.checkState(
        calculateRequestDelayMillis >= 0
            && calculateRequestDelayMillis <= providerRequest2.getIntervalMillis());
    if (calculateRequestDelayMillis < 30000) {
      setProviderRequest(providerRequest2);
    } else {
      Log.d(
          "LocationManagerService",
          this.mName
              + " provider delaying request update "
              + providerRequest2
              + " by "
              + TimeUtils.formatDuration(calculateRequestDelayMillis));
      AlarmManager.OnAlarmListener onAlarmListener = this.mDelayedRegister;
      if (onAlarmListener != null) {
        this.mAlarmHelper.cancel(onAlarmListener);
        this.mDelayedRegister = null;
      }
      AlarmManager.OnAlarmListener onAlarmListener2 =
          new AlarmManager
              .OnAlarmListener() { // from class:
                                   // com.android.server.location.provider.LocationProviderManager.5
            @Override // android.app.AlarmManager.OnAlarmListener
            public void onAlarm() {
              synchronized (LocationProviderManager.this.mMultiplexerLock) {
                if (LocationProviderManager.this.mDelayedRegister == this) {
                  LocationProviderManager.this.mDelayedRegister = null;
                  LocationProviderManager.this.setProviderRequest(providerRequest2);
                }
              }
            }
          };
      this.mDelayedRegister = onAlarmListener2;
      this.mAlarmHelper.setDelayedAlarm(calculateRequestDelayMillis, onAlarmListener2, null);
    }
    checkSettingsIgnoredChanged(
        providerRequest.isLocationSettingsIgnored(),
        providerRequest2.isLocationSettingsIgnored(),
        collection);
    return true;
  }

  @Override // com.android.server.location.listeners.ListenerMultiplexer
  public void unregisterWithService() {
    setProviderRequest(ProviderRequest.EMPTY_REQUEST);
    checkSettingsIgnoredChanged(this.mIsSettingsIgnored, false, null);
  }

  public void setProviderRequest(final ProviderRequest providerRequest) {
    AlarmManager.OnAlarmListener onAlarmListener = this.mDelayedRegister;
    if (onAlarmListener != null) {
      this.mAlarmHelper.cancel(onAlarmListener);
      this.mDelayedRegister = null;
    }
    LocationEventLog.EVENT_LOG.logProviderUpdateRequest(this.mName, providerRequest);
    Log.d("LocationManagerService", this.mName + " provider request changed to " + providerRequest);
    this.mProvider.getController().setRequest(providerRequest);
    LocationServiceThread.getHandler()
        .post(
            new Runnable() { // from class:
                             // com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda1
              @Override // java.lang.Runnable
              public final void run() {
                LocationProviderManager.this.lambda$setProviderRequest$6(providerRequest);
              }
            });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$setProviderRequest$6(ProviderRequest providerRequest) {
    Iterator it = this.mProviderRequestListeners.iterator();
    while (it.hasNext()) {
      IProviderRequestListener iProviderRequestListener = (IProviderRequestListener) it.next();
      try {
        iProviderRequestListener.onProviderRequestChanged(this.mName, providerRequest);
      } catch (RemoteException unused) {
        this.mProviderRequestListeners.remove(iProviderRequestListener);
      }
    }
  }

  public final boolean isChnNavigationApp(String str) {
    if (!CarrierConfig.getInstance().isChinaCarrier()) {
      return false;
    }
    for (String str2 : GnssConstants.NAVIGATION_APP_LIST) {
      if (new String(Base64.decode(str2, 0)).equals(str)) {
        return true;
      }
    }
    return false;
  }

  @Override // com.android.server.location.listeners.ListenerMultiplexer
  public boolean isActive(Registration registration) {
    return checkActiveNsflp(isActiveOriginal(registration), registration);
  }

  public final boolean checkActiveNsflp(boolean z, Registration registration) {
    if (!"gps".equals(this.mName)) {
      return z;
    }
    if (z) {
      if (this.mActiveOriginalRegistrations.size() == 0) {
        Log.d(
            "LocationManagerService", "try to release motion power when coming new active request");
        checkMotionPowerSaveMode();
      }
      this.mActiveOriginalRegistrations.add(registration);
    } else {
      this.mActiveOriginalRegistrations.remove(registration);
    }
    if (z && !registration.getRequest().isBypass()) {
      registration.setActiveMotionControl(true);
      if (this.mInactiveMotionRegistrations.remove(registration)
          && this.mInactiveMotionRegistrations.isEmpty()) {
        this.mNSLocationProviderHelper.onAvailableMotionStop(true);
      }
      return !this.mNSLocationProviderHelper.isMotionPowerSaveMode();
    }
    registration.setActiveMotionControl(false);
    if (this.mInactiveMotionRegistrations.add(registration)
        && this.mInactiveMotionRegistrations.size() == 1) {
      this.mNSLocationProviderHelper.onAvailableMotionStop(false);
    }
    return z;
  }

  /* JADX WARN: Code restructure failed: missing block: B:17:0x002d, code lost:

     if (r0 != 4) goto L30;
  */
  /* JADX WARN: Code restructure failed: missing block: B:27:0x003f, code lost:

     if ("gps".equals(r4.mName) == false) goto L30;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final boolean isActiveOriginal(Registration registration) {
    if (!registration.isPermitted()) {
      return false;
    }
    boolean isBypass = registration.getRequest().isBypass();
    if (!isActive(isBypass, registration.getIdentity())) {
      return false;
    }
    if (!isBypass) {
      int locationPowerSaveMode = this.mLocationPowerSaveModeHelper.getLocationPowerSaveMode();
      if (locationPowerSaveMode != 1) {
        if (locationPowerSaveMode != 2) {
          if (locationPowerSaveMode == 3) {
            if (!registration.isForeground()) {
              return false;
            }
          }
        }
        if (!this.mScreenInteractiveHelper.isInteractive()
            && !isChnNavigationApp(registration.getIdentity().getPackageName())) {
          return false;
        }
      }
    }
    return true;
  }

  public final boolean isActive(boolean z, CallerIdentity callerIdentity) {
    return callerIdentity.isSystemServer()
        ? z || isEnabled(this.mUserHelper.getCurrentUserId())
        : (z
                || (isEnabled(callerIdentity.getUserId())
                    && this.mUserHelper.isVisibleUserId(callerIdentity.getUserId())))
            && !this.mSettingsHelper.isLocationPackageBlacklisted(
                callerIdentity.getUserId(), callerIdentity.getPackageName());
  }

  @Override // com.android.server.location.listeners.ListenerMultiplexer
  public ProviderRequest mergeRegistrations(Collection collection) {
    long j;
    Iterator it = collection.iterator();
    int i = 104;
    boolean z = false;
    long j2 = Long.MAX_VALUE;
    long j3 = Long.MAX_VALUE;
    boolean z2 = true;
    boolean z3 = false;
    while (it.hasNext()) {
      LocationRequest request = ((Registration) it.next()).getRequest();
      if (request.getIntervalMillis() != Long.MAX_VALUE) {
        j2 = Math.min(request.getIntervalMillis(), j2);
        i = Math.min(request.getQuality(), i);
        j3 = Math.min(request.getMaxUpdateDelayMillis(), j3);
        z |= request.isAdasGnssBypass();
        z3 |= request.isLocationSettingsIgnored();
        z2 &= request.isLowPower();
      }
    }
    if (j2 == Long.MAX_VALUE) {
      return ProviderRequest.EMPTY_REQUEST;
    }
    if (j3 / 2 < j2) {
      j3 = 0;
    }
    try {
      j = Math.multiplyExact(Math.addExact(j2, 1000L) / 2, 3);
    } catch (ArithmeticException unused) {
      j = 9223372036854775806L;
    }
    WorkSource workSource = new WorkSource();
    Iterator it2 = collection.iterator();
    while (it2.hasNext()) {
      Registration registration = (Registration) it2.next();
      if (registration.getRequest().getIntervalMillis() <= j) {
        workSource.add(registration.getRequest().getWorkSource());
      }
    }
    return new ProviderRequest.Builder()
        .setIntervalMillis(j2)
        .setQuality(i)
        .setMaxUpdateDelayMillis(j3)
        .setAdasGnssBypass(z)
        .setLocationSettingsIgnored(z3)
        .setLowPower(z2)
        .setWorkSource(workSource)
        .build();
  }

  public long calculateRequestDelayMillis(long j, Collection collection) {
    Iterator it = collection.iterator();
    while (it.hasNext()) {
      Registration registration = (Registration) it.next();
      long j2 = 0;
      if (j == 0) {
        break;
      }
      LocationRequest request = registration.getRequest();
      Location lastDeliveredLocation = registration.getLastDeliveredLocation();
      if (lastDeliveredLocation == null && !request.isLocationSettingsIgnored()) {
        lastDeliveredLocation =
            getLastLocationUnsafe(
                registration.getIdentity().getUserId(),
                registration.getPermissionLevel(),
                false,
                request.getIntervalMillis());
      }
      if (lastDeliveredLocation != null) {
        j2 =
            Math.max(
                0L,
                request.getIntervalMillis() - lastDeliveredLocation.getElapsedRealtimeAgeMillis());
      }
      j = Math.min(j, j2);
    }
    return j;
  }

  public final void onUserChanged(final int i, int i2) {
    synchronized (this.mMultiplexerLock) {
      if (this.mState == 2) {
        return;
      }
      if (i2 != 1) {
        if (i2 == 2) {
          onUserStarted(i);
        } else if (i2 == 3) {
          onUserStopped(i);
        } else if (i2 != 4) {
        }
      }
      updateRegistrations(
          new Predicate() { // from class:
                            // com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda24
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
              boolean lambda$onUserChanged$7;
              lambda$onUserChanged$7 =
                  LocationProviderManager.lambda$onUserChanged$7(
                      i, (LocationProviderManager.Registration) obj);
              return lambda$onUserChanged$7;
            }
          });
    }
  }

  public static /* synthetic */ boolean lambda$onUserChanged$7(int i, Registration registration) {
    return registration.getIdentity().getUserId() == i;
  }

  public final void onLocationUserSettingsChanged(
      final int i,
      LocationUserSettings locationUserSettings,
      LocationUserSettings locationUserSettings2) {
    if (locationUserSettings.isAdasGnssLocationEnabled()
        != locationUserSettings2.isAdasGnssLocationEnabled()) {
      updateRegistrations(
          new Predicate() { // from class:
                            // com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda37
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
              boolean lambda$onLocationUserSettingsChanged$8;
              lambda$onLocationUserSettingsChanged$8 =
                  LocationProviderManager.lambda$onLocationUserSettingsChanged$8(
                      i, (LocationProviderManager.Registration) obj);
              return lambda$onLocationUserSettingsChanged$8;
            }
          });
    }
  }

  public static /* synthetic */ boolean lambda$onLocationUserSettingsChanged$8(
      int i, Registration registration) {
    return registration.onAdasGnssLocationEnabledChanged(i);
  }

  public final void onLocationEnabledChanged(int i) {
    synchronized (this.mMultiplexerLock) {
      if (this.mState == 2) {
        return;
      }
      onEnabledChanged(i);
    }
  }

  public final void onScreenInteractiveChanged(boolean z) {
    int locationPowerSaveMode = this.mLocationPowerSaveModeHelper.getLocationPowerSaveMode();
    if (locationPowerSaveMode != 1) {
      if (locationPowerSaveMode != 2 && locationPowerSaveMode != 4) {
        return;
      }
    } else if (!"gps".equals(this.mName)) {
      return;
    }
    updateRegistrations(
        new Predicate() { // from class:
                          // com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda25
          @Override // java.util.function.Predicate
          public final boolean test(Object obj) {
            boolean lambda$onScreenInteractiveChanged$9;
            lambda$onScreenInteractiveChanged$9 =
                LocationProviderManager.lambda$onScreenInteractiveChanged$9(
                    (LocationProviderManager.Registration) obj);
            return lambda$onScreenInteractiveChanged$9;
          }
        });
  }

  public final void onBackgroundThrottlePackageWhitelistChanged() {
    updateRegistrations(new LocationProviderManager$$ExternalSyntheticLambda23());
  }

  public final void onBackgroundThrottleIntervalChanged() {
    updateRegistrations(new LocationProviderManager$$ExternalSyntheticLambda23());
  }

  public final void onLocationPowerSaveModeChanged(int i) {
    updateRegistrations(
        new Predicate() { // from class:
                          // com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda26
          @Override // java.util.function.Predicate
          public final boolean test(Object obj) {
            boolean lambda$onLocationPowerSaveModeChanged$10;
            lambda$onLocationPowerSaveModeChanged$10 =
                LocationProviderManager.lambda$onLocationPowerSaveModeChanged$10(
                    (LocationProviderManager.Registration) obj);
            return lambda$onLocationPowerSaveModeChanged$10;
          }
        });
  }

  public final void onMotionPowerSaveModeChanged(boolean z) {
    synchronized (this.mMultiplexerLock) {
      updateRegistrations(
          new Predicate() { // from class:
                            // com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda28
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
              boolean lambda$onMotionPowerSaveModeChanged$11;
              lambda$onMotionPowerSaveModeChanged$11 =
                  LocationProviderManager.lambda$onMotionPowerSaveModeChanged$11(
                      (LocationProviderManager.Registration) obj);
              return lambda$onMotionPowerSaveModeChanged$11;
            }
          });
    }
  }

  public static /* synthetic */ boolean lambda$onAppForegroundChanged$12(
      int i, boolean z, Registration registration) {
    return registration.onForegroundChanged(i, z);
  }

  public final void onAppForegroundChanged(final int i, final boolean z) {
    updateRegistrations(
        new Predicate() { // from class:
                          // com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda34
          @Override // java.util.function.Predicate
          public final boolean test(Object obj) {
            boolean lambda$onAppForegroundChanged$12;
            lambda$onAppForegroundChanged$12 =
                LocationProviderManager.lambda$onAppForegroundChanged$12(
                    i, z, (LocationProviderManager.Registration) obj);
            return lambda$onAppForegroundChanged$12;
          }
        });
  }

  public final void onAdasAllowlistChanged() {
    updateRegistrations(new LocationProviderManager$$ExternalSyntheticLambda23());
  }

  public final void onIgnoreSettingsWhitelistChanged() {
    updateRegistrations(new LocationProviderManager$$ExternalSyntheticLambda23());
  }

  public static /* synthetic */ boolean lambda$onLocationPackageBlacklistChanged$13(
      int i, Registration registration) {
    return registration.getIdentity().getUserId() == i;
  }

  public final void onLocationPackageBlacklistChanged(final int i) {
    updateRegistrations(
        new Predicate() { // from class:
                          // com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda29
          @Override // java.util.function.Predicate
          public final boolean test(Object obj) {
            boolean lambda$onLocationPackageBlacklistChanged$13;
            lambda$onLocationPackageBlacklistChanged$13 =
                LocationProviderManager.lambda$onLocationPackageBlacklistChanged$13(
                    i, (LocationProviderManager.Registration) obj);
            return lambda$onLocationPackageBlacklistChanged$13;
          }
        });
  }

  public final void onLocationPermissionsChanged(final String str) {
    updateRegistrations(
        new Predicate() { // from class:
                          // com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda36
          @Override // java.util.function.Predicate
          public final boolean test(Object obj) {
            boolean lambda$onLocationPermissionsChanged$14;
            lambda$onLocationPermissionsChanged$14 =
                LocationProviderManager.lambda$onLocationPermissionsChanged$14(
                    str, (LocationProviderManager.Registration) obj);
            return lambda$onLocationPermissionsChanged$14;
          }
        });
  }

  public static /* synthetic */ boolean lambda$onLocationPermissionsChanged$14(
      String str, Registration registration) {
    return registration.onLocationPermissionsChanged(str);
  }

  public static /* synthetic */ boolean lambda$onLocationPermissionsChanged$15(
      int i, Registration registration) {
    return registration.onLocationPermissionsChanged(i);
  }

  public final void onLocationPermissionsChanged(final int i) {
    updateRegistrations(
        new Predicate() { // from class:
                          // com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda35
          @Override // java.util.function.Predicate
          public final boolean test(Object obj) {
            boolean lambda$onLocationPermissionsChanged$15;
            lambda$onLocationPermissionsChanged$15 =
                LocationProviderManager.lambda$onLocationPermissionsChanged$15(
                    i, (LocationProviderManager.Registration) obj);
            return lambda$onLocationPermissionsChanged$15;
          }
        });
  }

  public final void onPackageReset(final String str) {
    updateRegistrations(
        new Predicate() { // from class:
                          // com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda30
          @Override // java.util.function.Predicate
          public final boolean test(Object obj) {
            boolean lambda$onPackageReset$16;
            lambda$onPackageReset$16 =
                LocationProviderManager.lambda$onPackageReset$16(
                    str, (LocationProviderManager.Registration) obj);
            return lambda$onPackageReset$16;
          }
        });
  }

  public static /* synthetic */ boolean lambda$onPackageReset$16(
      String str, Registration registration) {
    if (!registration.getIdentity().getPackageName().equals(str)) {
      return false;
    }
    registration.remove();
    return false;
  }

  public final boolean isResetableForPackage(final String str) {
    return findRegistration(
        new Predicate() { // from class:
                          // com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda31
          @Override // java.util.function.Predicate
          public final boolean test(Object obj) {
            boolean lambda$isResetableForPackage$17;
            lambda$isResetableForPackage$17 =
                LocationProviderManager.lambda$isResetableForPackage$17(
                    str, (LocationProviderManager.Registration) obj);
            return lambda$isResetableForPackage$17;
          }
        });
  }

  public static /* synthetic */ boolean lambda$isResetableForPackage$17(
      String str, Registration registration) {
    return registration.getIdentity().getPackageName().equals(str);
  }

  @Override // com.android.server.location.provider.AbstractLocationProvider.Listener
  public void onStateChanged(
      final AbstractLocationProvider.State state, final AbstractLocationProvider.State state2) {
    if (state.allowed != state2.allowed) {
      onEnabledChanged(-1);
    }
    if (!Objects.equals(state.properties, state2.properties)) {
      updateRegistrations(
          new Predicate() { // from class:
                            // com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda32
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
              return ((LocationProviderManager.Registration) obj).onProviderPropertiesChanged();
            }
          });
    }
    final StateChangedListener stateChangedListener = this.mStateChangedListener;
    if (stateChangedListener != null) {
      LocationServiceThread.getExecutor()
          .execute(
              new Runnable() { // from class:
                               // com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda33
                @Override // java.lang.Runnable
                public final void run() {
                  LocationProviderManager.this.lambda$onStateChanged$18(
                      stateChangedListener, state, state2);
                }
              });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$onStateChanged$18(
      StateChangedListener stateChangedListener,
      AbstractLocationProvider.State state,
      AbstractLocationProvider.State state2) {
    stateChangedListener.onStateChanged(this.mName, state, state2);
  }

  @Override // com.android.server.location.provider.AbstractLocationProvider.Listener
  public void onReportLocation(LocationResult locationResult) {
    final LocationResult locationResult2;
    Location lastLocationUnsafe;
    if (this.mPassiveManager != null) {
      locationResult2 =
          locationResult.filter(
              new Predicate() { // from class:
                                // com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda21
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                  boolean lambda$onReportLocation$19;
                  lambda$onReportLocation$19 =
                      LocationProviderManager.this.lambda$onReportLocation$19((Location) obj);
                  return lambda$onReportLocation$19;
                }
              });
      if (locationResult2 == null) {
        return;
      }
      LocationEventLog.EVENT_LOG.logProviderReceivedLocations(this.mName, locationResult2.size());
      this.mNSConnectionHelper.onPassiveLocationReported(locationResult2.getLastLocation());
    } else {
      locationResult2 = locationResult;
    }
    if (this.mPassiveManager != null
        && (lastLocationUnsafe = getLastLocationUnsafe(-2, 2, true, Long.MAX_VALUE)) != null
        && locationResult.get(0).getElapsedRealtimeNanos()
            < lastLocationUnsafe.getElapsedRealtimeNanos()) {
      Log.e(
          "LocationManagerService",
          "non-monotonic location received from " + this.mName + " provider");
    }
    setLastLocation(locationResult2.getLastLocation(), -1);
    deliverToListeners(
        new Function() { // from class:
                         // com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda22
          @Override // java.util.function.Function
          public final Object apply(Object obj) {
            ListenerExecutor.ListenerOperation lambda$onReportLocation$20;
            lambda$onReportLocation$20 =
                LocationProviderManager.lambda$onReportLocation$20(
                    locationResult2, (LocationProviderManager.Registration) obj);
            return lambda$onReportLocation$20;
          }
        });
    PassiveLocationProviderManager passiveLocationProviderManager = this.mPassiveManager;
    if (passiveLocationProviderManager != null) {
      passiveLocationProviderManager.updateLocation(locationResult2);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ boolean lambda$onReportLocation$19(Location location) {
    if (!location.isMock() && location.getLatitude() == 0.0d && location.getLongitude() == 0.0d) {
      Log.e("LocationManagerService", "blocking 0,0 location from " + this.mName + " provider");
      return false;
    }
    if (location.isComplete()) {
      return true;
    }
    Log.e(
        "LocationManagerService", "blocking incomplete location from " + this.mName + " provider");
    return false;
  }

  public static /* synthetic */ ListenerExecutor.ListenerOperation lambda$onReportLocation$20(
      LocationResult locationResult, Registration registration) {
    return registration.acceptLocationChange(locationResult);
  }

  public final void onUserStarted(int i) {
    if (i == -10000) {
      return;
    }
    if (i == -1) {
      this.mEnabled.clear();
      onEnabledChanged(-1);
    } else {
      Preconditions.checkArgument(i >= 0);
      this.mEnabled.delete(i);
      onEnabledChanged(i);
    }
  }

  public final void onUserStopped(int i) {
    if (i == -10000) {
      return;
    }
    if (i == -1) {
      this.mEnabled.clear();
      this.mLastLocations.clear();
    } else {
      Preconditions.checkArgument(i >= 0);
      this.mEnabled.delete(i);
      this.mLastLocations.remove(i);
    }
  }

  public final void onEnabledChanged(final int i) {
    LastLocation lastLocation;
    if (i == -10000) {
      return;
    }
    if (i == -1) {
      for (int i2 : this.mUserHelper.getRunningUserIds()) {
        onEnabledChanged(i2);
      }
      return;
    }
    Preconditions.checkArgument(i >= 0);
    final boolean z =
        this.mState == 0
            && this.mProvider.getState().allowed
            && this.mSettingsHelper.isLocationEnabled(i);
    int indexOfKey = this.mEnabled.indexOfKey(i);
    Boolean valueOf = indexOfKey < 0 ? null : Boolean.valueOf(this.mEnabled.valueAt(indexOfKey));
    if (valueOf == null || valueOf.booleanValue() != z) {
      this.mEnabled.put(i, z);
      if (valueOf != null || z) {
        Log.d("LocationManagerService", "[u" + i + "] " + this.mName + " provider enabled = " + z);
        LocationEventLog.EVENT_LOG.logProviderEnabled(this.mName, i, z);
      }
      if (!z && (lastLocation = (LastLocation) this.mLastLocations.get(i)) != null) {
        lastLocation.clearLocations();
      }
      if (valueOf != null) {
        if (!"passive".equals(this.mName)) {
          this.mContext.sendBroadcastAsUser(
              new Intent("android.location.PROVIDERS_CHANGED")
                  .putExtra("android.location.extra.PROVIDER_NAME", this.mName)
                  .putExtra("android.location.extra.PROVIDER_ENABLED", z)
                  .addFlags(1073741824)
                  .addFlags(268435456),
              UserHandle.of(i));
        }
        if (!this.mEnabledListeners.isEmpty()) {
          final LocationManagerInternal.ProviderEnabledListener[] providerEnabledListenerArr =
              (LocationManagerInternal.ProviderEnabledListener[])
                  this.mEnabledListeners.toArray(
                      new LocationManagerInternal.ProviderEnabledListener[0]);
          LocationServiceThread.getHandler()
              .post(
                  new Runnable() { // from class:
                                   // com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                      LocationProviderManager.this.lambda$onEnabledChanged$21(
                          providerEnabledListenerArr, i, z);
                    }
                  });
        }
      }
      updateRegistrations(
          new Predicate() { // from class:
                            // com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
              boolean lambda$onEnabledChanged$22;
              lambda$onEnabledChanged$22 =
                  LocationProviderManager.lambda$onEnabledChanged$22(
                      i, (LocationProviderManager.Registration) obj);
              return lambda$onEnabledChanged$22;
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$onEnabledChanged$21(
      LocationManagerInternal.ProviderEnabledListener[] providerEnabledListenerArr,
      int i,
      boolean z) {
    for (LocationManagerInternal.ProviderEnabledListener providerEnabledListener :
        providerEnabledListenerArr) {
      providerEnabledListener.onProviderEnabledChanged(this.mName, i, z);
    }
  }

  public static /* synthetic */ boolean lambda$onEnabledChanged$22(
      int i, Registration registration) {
    return registration.getIdentity().getUserId() == i;
  }

  public Location getPermittedLocation(Location location, int i) {
    if (i != 1) {
      if (i == 2) {
        return location;
      }
      throw new AssertionError();
    }
    if (location != null) {
      return this.mLocationFudger.createCoarse(location);
    }
    return null;
  }

  public LocationResult getPermittedLocationResult(LocationResult locationResult, int i) {
    if (i != 1) {
      if (i == 2) {
        return locationResult;
      }
      throw new AssertionError();
    }
    if (locationResult != null) {
      return this.mLocationFudger.createCoarse(locationResult);
    }
    return null;
  }

  public void dump(
      FileDescriptor fileDescriptor, IndentingPrintWriter indentingPrintWriter, String[] strArr) {
    synchronized (this.mMultiplexerLock) {
      indentingPrintWriter.print(this.mName);
      indentingPrintWriter.print(" provider");
      if (this.mProvider.isMock()) {
        indentingPrintWriter.print(" [mock]");
      }
      indentingPrintWriter.println(XmlUtils.STRING_ARRAY_SEPARATOR);
      indentingPrintWriter.increaseIndent();
      super.dump(fileDescriptor, (PrintWriter) indentingPrintWriter, strArr);
      int[] runningUserIds = this.mUserHelper.getRunningUserIds();
      for (int i : runningUserIds) {
        if (runningUserIds.length != 1) {
          indentingPrintWriter.print("user ");
          indentingPrintWriter.print(i);
          indentingPrintWriter.println(XmlUtils.STRING_ARRAY_SEPARATOR);
          indentingPrintWriter.increaseIndent();
        }
        indentingPrintWriter.print("last location=");
        indentingPrintWriter.println(getLastLocationUnsafe(i, 2, false, Long.MAX_VALUE));
        indentingPrintWriter.print("enabled=");
        indentingPrintWriter.println(isEnabled(i));
        if (runningUserIds.length != 1) {
          indentingPrintWriter.decreaseIndent();
        }
      }
    }
    this.mProvider.dump(fileDescriptor, indentingPrintWriter, strArr);
    indentingPrintWriter.decreaseIndent();
  }

  @Override // com.android.server.location.listeners.ListenerMultiplexer
  public String getServiceState() {
    return this.mProvider.getCurrentRequest().toString();
  }

  public class LastLocation {
    public Location mCoarseBypassLocation;
    public Location mCoarseLocation;
    public Location mFineBypassLocation;
    public Location mFineLocation;

    public void clearMock() {
      Location location = this.mFineLocation;
      if (location != null && location.isMock()) {
        this.mFineLocation = null;
      }
      Location location2 = this.mCoarseLocation;
      if (location2 != null && location2.isMock()) {
        this.mCoarseLocation = null;
      }
      Location location3 = this.mFineBypassLocation;
      if (location3 != null && location3.isMock()) {
        this.mFineBypassLocation = null;
      }
      Location location4 = this.mCoarseBypassLocation;
      if (location4 == null || !location4.isMock()) {
        return;
      }
      this.mCoarseBypassLocation = null;
    }

    public void clearLocations() {
      this.mFineLocation = null;
      this.mCoarseLocation = null;
    }

    public Location get(int i, boolean z) {
      if (i == 1) {
        if (z) {
          return this.mCoarseBypassLocation;
        }
        return this.mCoarseLocation;
      }
      if (i != 2) {
        throw new AssertionError();
      }
      if (z) {
        return this.mFineBypassLocation;
      }
      return this.mFineLocation;
    }

    public void set(Location location) {
      this.mFineLocation = calculateNextFine(this.mFineLocation, location);
      this.mCoarseLocation = calculateNextCoarse(this.mCoarseLocation, location);
    }

    public void setBypass(Location location) {
      this.mFineBypassLocation = calculateNextFine(this.mFineBypassLocation, location);
      this.mCoarseBypassLocation = calculateNextCoarse(this.mCoarseBypassLocation, location);
    }

    public final Location calculateNextFine(Location location, Location location2) {
      return (location != null
              && location2.getElapsedRealtimeNanos() <= location.getElapsedRealtimeNanos())
          ? location
          : location2;
    }

    public final Location calculateNextCoarse(Location location, Location location2) {
      return (location != null
              && location2.getElapsedRealtimeMillis() - 600000
                  <= location.getElapsedRealtimeMillis())
          ? location
          : location2;
    }
  }

  public abstract class PendingIntentSender {
    /* JADX WARN: Multi-variable type inference failed */
    public static void send(
        PendingIntent pendingIntent,
        Context context,
        Intent intent,
        Runnable runnable,
        Bundle bundle) {
      PendingIntent.OnFinished onFinished;
      GatedCallback gatedCallback = null;
      Object[] objArr = 0;
      if (runnable != null) {
        final GatedCallback gatedCallback2 = new GatedCallback(runnable);
        onFinished =
            new PendingIntent
                .OnFinished() { // from class:
                                // com.android.server.location.provider.LocationProviderManager$PendingIntentSender$$ExternalSyntheticLambda0
              @Override // android.app.PendingIntent.OnFinished
              public final void onSendFinished(
                  PendingIntent pendingIntent2, Intent intent2, int i, String str, Bundle bundle2) {
                LocationProviderManager.PendingIntentSender.GatedCallback.this.run();
              }
            };
        gatedCallback = gatedCallback2;
      } else {
        onFinished = null;
      }
      pendingIntent.send(context, 0, intent, onFinished, null, null, bundle);
      if (gatedCallback != null) {
        gatedCallback.allow();
      }
    }

    public class GatedCallback implements Runnable {
      public Runnable mCallback;
      public boolean mGate;
      public boolean mRun;

      public GatedCallback(Runnable runnable) {
        this.mCallback = runnable;
      }

      public void allow() {
        Runnable runnable;
        Runnable runnable2;
        synchronized (this) {
          this.mGate = true;
          runnable = null;
          if (this.mRun && (runnable2 = this.mCallback) != null) {
            this.mCallback = null;
            runnable = runnable2;
          }
        }
        if (runnable != null) {
          runnable.run();
        }
      }

      @Override // java.lang.Runnable
      public void run() {
        Runnable runnable;
        Runnable runnable2;
        synchronized (this) {
          this.mRun = true;
          runnable = null;
          if (this.mGate && (runnable2 = this.mCallback) != null) {
            this.mCallback = null;
            runnable = runnable2;
          }
        }
        if (runnable != null) {
          runnable.run();
        }
      }
    }
  }

  public class ExternalWakeLockReleaser extends IRemoteCallback.Stub {
    public final CallerIdentity mIdentity;
    public final PowerManager.WakeLock mWakeLock;

    public ExternalWakeLockReleaser(CallerIdentity callerIdentity, PowerManager.WakeLock wakeLock) {
      this.mIdentity = callerIdentity;
      Objects.requireNonNull(wakeLock);
      this.mWakeLock = wakeLock;
    }

    public void sendResult(Bundle bundle) {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        try {
          this.mWakeLock.release();
        } catch (RuntimeException e) {
          if (e.getClass() == RuntimeException.class) {
            Log.e("LocationManagerService", "wakelock over-released by " + this.mIdentity, e);
          } else {
            LocationServiceThread.getExecutor()
                .execute(
                    new Runnable() { // from class:
                                     // com.android.server.location.provider.LocationProviderManager$ExternalWakeLockReleaser$$ExternalSyntheticLambda0
                      @Override // java.lang.Runnable
                      public final void run() {
                        LocationProviderManager.ExternalWakeLockReleaser.lambda$sendResult$0(e);
                      }
                    });
            throw e;
          }
        }
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public static /* synthetic */ void lambda$sendResult$0(RuntimeException runtimeException) {
      throw new AssertionError(runtimeException);
    }
  }

  public final void checkSettingsIgnoredChanged(boolean z, boolean z2, Collection collection) {
    if (z == z2) {
      return;
    }
    this.mIsSettingsIgnored = z2;
    Registration registration = null;
    if (z2 && collection != null) {
      registration =
          (Registration)
              collection.stream()
                  .filter(
                      new Predicate() { // from class:
                                        // com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda0
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                          boolean lambda$checkSettingsIgnoredChanged$23;
                          lambda$checkSettingsIgnoredChanged$23 =
                              LocationProviderManager.lambda$checkSettingsIgnoredChanged$23(
                                  (LocationProviderManager.Registration) obj);
                          return lambda$checkSettingsIgnoredChanged$23;
                        }
                      })
                  .findFirst()
                  .orElse(null);
    }
    this.mNSLocationProviderHelper.sendSettingsIgnoreInfo(this.mName, z2, registration);
  }

  public static /* synthetic */ boolean lambda$checkSettingsIgnoredChanged$23(
      Registration registration) {
    return registration.getRequest().isLocationSettingsIgnored();
  }

  public final void checkMotionPowerSaveMode() {
    if (this.mNSLocationProviderHelper.isMotionPowerSaveMode()) {
      Log.d(
          "LocationManagerService",
          "checkMotionPowerSaveMode, motion power save mode is changed to false");
      this.mNSConnectionHelper.onStateUpdated(
          LocationConstants.STATE_TYPE.MOTION_POWER_DISABLE, null);
      this.mNSLocationProviderHelper.notifyMotionPowerSaveModeChanged(false);
    }
  }
}
