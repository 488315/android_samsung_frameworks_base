package com.android.server.location.provider;

import android.app.ActivityManagerInternal;
import android.app.AlarmManager;
import android.app.BroadcastOptions;
import android.app.PendingIntent;
import android.app.compat.CompatChanges;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.location.ILocationCallback;
import android.location.ILocationListener;
import android.location.Location;
import android.location.LocationConstants;
import android.location.LocationManagerInternal;
import android.location.LocationRequest;
import android.location.LocationResult;
import android.location.altitude.AltitudeConverter;
import android.location.flags.Flags;
import android.location.provider.ProviderProperties;
import android.location.provider.ProviderRequest;
import android.location.util.identity.CallerIdentity;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IRemoteCallback;
import android.os.Parcelable;
import android.os.PowerManager;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.WorkSource;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.util.ArraySet;
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
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.IoThread;
import com.android.server.LocalServices;
import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;
import com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticOutline0;
import com.android.server.audio.AudioService$$ExternalSyntheticOutline0;
import com.android.server.clipboard.ClipboardService;
import com.android.server.location.LocationManagerService;
import com.android.server.location.LocationManagerService$$ExternalSyntheticLambda12;
import com.android.server.location.LocationPermissions;
import com.android.server.location.LocationServiceThread;
import com.android.server.location.eventlog.LocationEventLog;
import com.android.server.location.fudger.LocationFudger;
import com.android.server.location.injector.AppForegroundHelper$AppForegroundListener;
import com.android.server.location.injector.EmergencyHelper;
import com.android.server.location.injector.Injector;
import com.android.server.location.injector.LocationPermissionsHelper$LocationPermissionsListener;
import com.android.server.location.injector.LocationPowerSaveModeHelper$LocationPowerSaveModeChangedListener;
import com.android.server.location.injector.LocationUsageLogger;
import com.android.server.location.injector.PackageResetHelper$Responder;
import com.android.server.location.injector.SettingsHelper$GlobalSettingChangedListener;
import com.android.server.location.injector.SettingsHelper$UserSettingChangedListener;
import com.android.server.location.injector.SystemAlarmHelper;
import com.android.server.location.injector.SystemAppForegroundHelper;
import com.android.server.location.injector.SystemAppOpsHelper;
import com.android.server.location.injector.SystemEmergencyHelper;
import com.android.server.location.injector.SystemLocationPermissionsHelper;
import com.android.server.location.injector.SystemLocationPowerSaveModeHelper;
import com.android.server.location.injector.SystemPackageResetHelper;
import com.android.server.location.injector.SystemScreenInteractiveHelper;
import com.android.server.location.injector.SystemSettingsHelper;
import com.android.server.location.injector.UserInfoHelper$UserListener;
import com.android.server.location.listeners.ListenerMultiplexer;
import com.android.server.location.listeners.RemovableListenerRegistration;
import com.android.server.location.nsflp.NSConnectionHelper;
import com.android.server.location.nsflp.NSConnectionHelper$$ExternalSyntheticLambda0;
import com.android.server.location.nsflp.NSLocationProviderHelper;
import com.android.server.location.nsflp.NSLocationProviderHelper$$ExternalSyntheticLambda0;
import com.android.server.location.settings.LocationSettings;
import com.android.server.location.settings.LocationUserSettings;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class LocationProviderManager extends ListenerMultiplexer
        implements AbstractLocationProvider.Listener {
    public final HashSet mActiveOriginalRegistrations;
    public final LocationProviderManager$$ExternalSyntheticLambda5
            mAdasPackageAllowlistChangedListener;
    public final SystemAlarmHelper mAlarmHelper;
    public final AltitudeConverter mAltitudeConverter;
    public final LocationProviderManager$$ExternalSyntheticLambda7 mAppForegroundChangedListener;
    public final SystemAppForegroundHelper mAppForegroundHelper;
    public final SystemAppOpsHelper mAppOpsHelper;
    public final LocationProviderManager$$ExternalSyntheticLambda5
            mBackgroundThrottleIntervalChangedListener;
    public final LocationProviderManager$$ExternalSyntheticLambda5
            mBackgroundThrottlePackageWhitelistChangedListener;
    public final Context mContext;
    public AlarmManager.OnAlarmListener mDelayedRegister;
    public final SystemEmergencyHelper mEmergencyHelper;
    public final LocationProviderManager$$ExternalSyntheticLambda2 mEmergencyStateChangedListener;
    public final SparseBooleanArray mEnabled;
    public final ArrayList mEnabledListeners;
    public final LocationProviderManager$$ExternalSyntheticLambda5
            mIgnoreSettingsPackageWhitelistChangedListener;
    public final HashSet mInactiveMotionRegistrations;
    public volatile boolean mIsAltitudeConverterIdle;
    public boolean mIsSettingsIgnored;
    public final SparseArray mLastLocations;
    public final LocationProviderManager$$ExternalSyntheticLambda4 mLocationEnabledChangedListener;
    public final LocationFudger mLocationFudger;
    public final LocationManagerInternal mLocationManagerInternal;
    public final LocationProviderManager$$ExternalSyntheticLambda4
            mLocationPackageBlacklistChangedListener;
    public final SystemLocationPermissionsHelper mLocationPermissionsHelper;
    public final AnonymousClass1 mLocationPermissionsListener;
    public final LocationProviderManager$$ExternalSyntheticLambda11
            mLocationPowerSaveModeChangedListener;
    public final SystemLocationPowerSaveModeHelper mLocationPowerSaveModeHelper;
    public final LocationSettings mLocationSettings;
    public final LocationUsageLogger mLocationUsageLogger;
    public final LocationProviderManager$$ExternalSyntheticLambda3 mLocationUserSettingsListener;
    public final LocationProviderManager$$ExternalSyntheticLambda1
            mMotionPowerSaveModeChangedListener;
    public final NSConnectionHelper mNSConnectionHelper;
    public final NSLocationProviderHelper mNSLocationProviderHelper;
    public final String mName;
    public final SystemPackageResetHelper mPackageResetHelper;
    public final AnonymousClass2 mPackageResetResponder;
    public final PassiveLocationProviderManager mPassiveManager;
    public final MockableLocationProvider mProvider;
    public final CopyOnWriteArrayList mProviderRequestListeners;
    public final Collection mRequiredPermissions;
    public final LocationProviderManager$$ExternalSyntheticLambda1
            mScreenInteractiveChangedListener;
    public final SystemScreenInteractiveHelper mScreenInteractiveHelper;
    public final SystemSettingsHelper mSettingsHelper;
    public int mState;
    public StateChangedListener mStateChangedListener;
    public final LocationProviderManager$$ExternalSyntheticLambda0 mUserChangedListener;
    public final LocationManagerService.Lifecycle.LifecycleUserInfoHelper mUserHelper;

    public final class ExternalWakeLockReleaser extends IRemoteCallback.Stub {
        public final CallerIdentity mIdentity;
        public final PowerManager.WakeLock mWakeLock;

        public ExternalWakeLockReleaser(
                CallerIdentity callerIdentity, PowerManager.WakeLock wakeLock) {
            this.mIdentity = callerIdentity;
            Objects.requireNonNull(wakeLock);
            this.mWakeLock = wakeLock;
        }

        public final void sendResult(Bundle bundle) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    this.mWakeLock.release();
                } catch (RuntimeException e) {
                    if (e.getClass() != RuntimeException.class) {
                        LocationServiceThread.getExecutor()
                                .execute(
                                        new LocationProviderManager$$ExternalSyntheticLambda22(
                                                1, e));
                        throw e;
                    }
                    Log.e(
                            "LocationManagerService",
                            "wakelock over-released by " + this.mIdentity,
                            e);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final class GetCurrentLocationListenerRegistration extends Registration
            implements IBinder.DeathRecipient, AlarmManager.OnAlarmListener {
        public long mExpirationRealtimeMs;

        public GetCurrentLocationListenerRegistration(
                LocationRequest locationRequest,
                CallerIdentity callerIdentity,
                GetCurrentLocationTransport getCurrentLocationTransport,
                int i) {
            super(
                    locationRequest,
                    callerIdentity,
                    callerIdentity.isMyProcess()
                            ? LocationServiceThread.getExecutor()
                            : ConcurrentUtils.DIRECT_EXECUTOR,
                    getCurrentLocationTransport,
                    i);
            this.mExpirationRealtimeMs = Long.MAX_VALUE;
        }

        /* JADX WARN: Removed duplicated region for block: B:14:0x0076  */
        @Override // com.android.server.location.provider.LocationProviderManager.Registration
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final com.android.internal.listeners.ListenerExecutor.ListenerOperation
                acceptLocationChange(android.location.LocationResult r6) {
            /*
                r5 = this;
                long r0 = android.os.SystemClock.elapsedRealtime()
                long r2 = r5.mExpirationRealtimeMs
                int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
                r1 = 0
                java.lang.String r2 = "LocationManagerService"
                if (r0 < 0) goto L39
                java.lang.StringBuilder r6 = new java.lang.StringBuilder
                r6.<init>()
                com.android.server.location.provider.LocationProviderManager r0 = com.android.server.location.provider.LocationProviderManager.this
                java.lang.String r0 = r0.mName
                r6.append(r0)
                java.lang.String r0 = " provider registration "
                r6.append(r0)
                android.location.util.identity.CallerIdentity r0 = r5.mIdentity
                r6.append(r0)
                java.lang.String r0 = " expired at "
                r6.append(r0)
                long r3 = r5.mExpirationRealtimeMs
                java.lang.String r0 = android.util.TimeUtils.formatRealtime(r3)
                r6.append(r0)
                java.lang.String r6 = r6.toString()
                android.util.Log.d(r2, r6)
                r6 = r1
            L39:
                if (r6 == 0) goto L73
                boolean r0 = android.location.flags.Flags.enableLocationBypass()
                if (r0 == 0) goto L4a
                boolean r0 = r5.isOnlyBypassPermitted()
                if (r0 == 0) goto L4a
                r0 = 147(0x93, float:2.06E-43)
                goto L52
            L4a:
                int r0 = r5.getPermissionLevel()
                int r0 = com.android.server.location.LocationPermissions.asAppOp(r0)
            L52:
                com.android.server.location.provider.LocationProviderManager r3 = com.android.server.location.provider.LocationProviderManager.this
                com.android.server.location.injector.SystemAppOpsHelper r3 = r3.mAppOpsHelper
                android.location.util.identity.CallerIdentity r4 = r5.mIdentity
                boolean r0 = r3.noteOpNoThrow(r0, r4)
                if (r0 != 0) goto L73
                java.lang.StringBuilder r6 = new java.lang.StringBuilder
                java.lang.String r0 = "noteOp denied for "
                r6.<init>(r0)
                android.location.util.identity.CallerIdentity r0 = r5.mIdentity
                r6.append(r0)
                java.lang.String r6 = r6.toString()
                android.util.Log.w(r2, r6)
                goto L74
            L73:
                r1 = r6
            L74:
                if (r1 == 0) goto L7a
                android.location.LocationResult r1 = r1.asLastLocationResult()
            L7a:
                com.android.server.location.provider.LocationProviderManager r6 = com.android.server.location.provider.LocationProviderManager.this
                int r0 = r5.getPermissionLevel()
                android.location.LocationResult r6 = r6.getPermittedLocationResult(r1, r0)
                com.android.server.location.provider.LocationProviderManager$GetCurrentLocationListenerRegistration$1 r0 = new com.android.server.location.provider.LocationProviderManager$GetCurrentLocationListenerRegistration$1
                r0.<init>()
                return r0
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.location.provider.LocationProviderManager.GetCurrentLocationListenerRegistration.acceptLocationChange(android.location.LocationResult):com.android.internal.listeners.ListenerExecutor$ListenerOperation");
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            try {
                Log.d(
                        "LocationManagerService",
                        LocationProviderManager.this.mName
                                + " provider registration "
                                + this.mIdentity
                                + " died");
                remove();
            } catch (RuntimeException e) {
                throw new AssertionError(e);
            }
        }

        @Override // com.android.server.location.provider.LocationProviderManager.Registration,
                  // com.android.server.location.listeners.RemovableListenerRegistration
        public final void onActive() {
            super.onActive();
            Location lastLocationUnsafe =
                    LocationProviderManager.this.getLastLocationUnsafe(
                            this.mIdentity.getUserId(),
                            getPermissionLevel(),
                            getRequest().isBypass(),
                            30000L);
            if (lastLocationUnsafe != null) {
                executeOperation(
                        acceptLocationChange(
                                LocationResult.wrap(new Location[] {lastLocationUnsafe})));
            }
        }

        @Override // android.app.AlarmManager.OnAlarmListener
        public final void onAlarm() {
            Log.d(
                    "LocationManagerService",
                    LocationProviderManager.this.mName
                            + " provider registration "
                            + this.mIdentity
                            + " expired at "
                            + TimeUtils.formatRealtime(this.mExpirationRealtimeMs));
            synchronized (LocationProviderManager.this.mMultiplexerLock) {
                this.mExpirationRealtimeMs = Long.MAX_VALUE;
                executeOperation(acceptLocationChange(null));
            }
        }

        @Override // com.android.server.location.provider.LocationProviderManager.Registration,
                  // com.android.server.location.listeners.RemovableListenerRegistration
        public final void onInactive() {
            executeOperation(acceptLocationChange(null));
            super.onInactive();
        }

        @Override // com.android.server.location.listeners.RemovableListenerRegistration
        public final void onOperationFailure(
                ListenerExecutor.ListenerOperation listenerOperation, Exception exc) {
            if (!(exc instanceof RemoteException)) {
                throw new AssertionError(exc);
            }
            Log.w(
                    "LocationManagerService",
                    LocationProviderManager.this.mName
                            + " provider registration "
                            + this.mIdentity
                            + " removed",
                    exc);
            remove();
        }

        @Override // com.android.server.location.provider.LocationProviderManager.Registration,
                  // com.android.server.location.listeners.RemovableListenerRegistration
        public final void onRegister() {
            super.onRegister();
            try {
                Object obj = this.mKey;
                Objects.requireNonNull(obj);
                ((IBinder) obj).linkToDeath(this, 0);
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
                        expirationRealtimeMs - elapsedRealtime, this);
            }
        }

        @Override // com.android.server.location.provider.LocationProviderManager.Registration,
                  // com.android.server.location.listeners.RemovableListenerRegistration
        public final void onUnregister() {
            if (this.mExpirationRealtimeMs < Long.MAX_VALUE) {
                LocationProviderManager.this.mAlarmHelper.cancel(this);
            }
            try {
                Object obj = this.mKey;
                Objects.requireNonNull(obj);
                ((IBinder) obj).unlinkToDeath(this, 0);
            } catch (NoSuchElementException e) {
                Log.w("ListenerRegistration", "failed to unregister binder death listener", e);
            }
            super.onUnregister();
        }
    }

    public final class GetCurrentLocationTransport implements LocationTransport {
        public final ILocationCallback mCallback;

        public GetCurrentLocationTransport(ILocationCallback iLocationCallback) {
            Objects.requireNonNull(iLocationCallback);
            this.mCallback = iLocationCallback;
        }

        @Override // com.android.server.location.provider.LocationProviderManager.LocationTransport
        public final void deliverOnFlushComplete(int i) {}

        @Override // com.android.server.location.provider.LocationProviderManager.LocationTransport
        public final void deliverOnLocationChanged(
                LocationResult locationResult, ExternalWakeLockReleaser externalWakeLockReleaser) {
            Preconditions.checkState(externalWakeLockReleaser == null);
            try {
                if (locationResult != null) {
                    this.mCallback.onLocation(locationResult.getLastLocation());
                } else {
                    this.mCallback.onLocation((Location) null);
                }
            } catch (RuntimeException e) {
                LocationServiceThread.getExecutor()
                        .execute(
                                new LocationProviderManager$$ExternalSyntheticLambda22(
                                        2, new RuntimeException(e)));
            }
        }
    }

    public final class LastLocation {
        public Location mCoarseBypassLocation;
        public Location mCoarseLocation;
        public Location mFineBypassLocation;
        public Location mFineLocation;
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

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            try {
                Log.d(
                        "LocationManagerService",
                        LocationProviderManager.this.mName
                                + " provider registration "
                                + this.mIdentity
                                + " died");
                remove();
            } catch (RuntimeException e) {
                throw new AssertionError(e);
            }
        }

        @Override // com.android.server.location.listeners.RemovableListenerRegistration
        public final void onOperationFailure(
                ListenerExecutor.ListenerOperation listenerOperation, Exception exc) {
            onTransportFailure(exc);
        }

        @Override // com.android.server.location.provider.LocationProviderManager.LocationRegistration
        public final void onProviderOperationFailure(Exception exc) {
            onTransportFailure(exc);
        }

        @Override // com.android.server.location.provider.LocationProviderManager.LocationRegistration, com.android.server.location.provider.LocationProviderManager.Registration, com.android.server.location.listeners.RemovableListenerRegistration
        public final void onRegister() {
            super.onRegister();
            try {
                Object obj = this.mKey;
                Objects.requireNonNull(obj);
                ((IBinder) obj).linkToDeath(this, 0);
            } catch (RemoteException unused) {
                remove();
            }
        }

        public final void onTransportFailure(Exception exc) {
            if (!(exc instanceof RemoteException)) {
                throw new AssertionError(exc);
            }
            Log.w(
                    "LocationManagerService",
                    LocationProviderManager.this.mName
                            + " provider registration "
                            + this.mIdentity
                            + " removed",
                    exc);
            remove();
        }

        @Override // com.android.server.location.provider.LocationProviderManager.LocationRegistration, com.android.server.location.provider.LocationProviderManager.Registration, com.android.server.location.listeners.RemovableListenerRegistration
        public final void onUnregister() {
            try {
                Object obj = this.mKey;
                Objects.requireNonNull(obj);
                ((IBinder) obj).unlinkToDeath(this, 0);
            } catch (NoSuchElementException e) {
                Log.w("ListenerRegistration", "failed to unregister binder death listener", e);
            }
            super.onUnregister();
        }
    }

    public final class LocationListenerTransport implements LocationTransport, ProviderTransport {
        public final ILocationListener mListener;

        public LocationListenerTransport(ILocationListener iLocationListener) {
            Objects.requireNonNull(iLocationListener);
            this.mListener = iLocationListener;
        }

        @Override // com.android.server.location.provider.LocationProviderManager.LocationTransport
        public final void deliverOnFlushComplete(int i) {
            try {
                this.mListener.onFlushComplete(i);
            } catch (RuntimeException e) {
                RuntimeException runtimeException = new RuntimeException(e);
                LocationServiceThread.getExecutor()
                        .execute(
                                new LocationProviderManager$$ExternalSyntheticLambda22(
                                        5, runtimeException));
            }
        }

        @Override // com.android.server.location.provider.LocationProviderManager.LocationTransport
        public final void deliverOnLocationChanged(
                LocationResult locationResult, ExternalWakeLockReleaser externalWakeLockReleaser) {
            try {
                this.mListener.onLocationChanged(locationResult.asList(), externalWakeLockReleaser);
            } catch (RuntimeException e) {
                RuntimeException runtimeException = new RuntimeException(e);
                LocationServiceThread.getExecutor()
                        .execute(
                                new LocationProviderManager$$ExternalSyntheticLambda22(
                                        3, runtimeException));
            }
        }

        @Override // com.android.server.location.provider.LocationProviderManager.ProviderTransport
        public final void deliverOnProviderEnabledChanged(String str, boolean z) {
            try {
                this.mListener.onProviderEnabledChanged(str, z);
            } catch (RuntimeException e) {
                RuntimeException runtimeException = new RuntimeException(e);
                LocationServiceThread.getExecutor()
                        .execute(
                                new LocationProviderManager$$ExternalSyntheticLambda22(
                                        4, runtimeException));
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

        public final void onCanceled(PendingIntent pendingIntent) {
            Log.d(
                    "LocationManagerService",
                    LocationProviderManager.this.mName
                            + " provider registration "
                            + this.mIdentity
                            + " canceled");
            remove();
        }

        @Override // com.android.server.location.listeners.RemovableListenerRegistration
        public final void onOperationFailure(
                ListenerExecutor.ListenerOperation listenerOperation, Exception exc) {
            onTransportFailure$1(exc);
        }

        @Override // com.android.server.location.provider.LocationProviderManager.LocationRegistration
        public final void onProviderOperationFailure(Exception exc) {
            onTransportFailure$1(exc);
        }

        @Override // com.android.server.location.provider.LocationProviderManager.LocationRegistration, com.android.server.location.provider.LocationProviderManager.Registration, com.android.server.location.listeners.RemovableListenerRegistration
        public final void onRegister() {
            super.onRegister();
            Object obj = this.mKey;
            Objects.requireNonNull(obj);
            if (((PendingIntent) obj).addCancelListener(ConcurrentUtils.DIRECT_EXECUTOR, this)) {
                return;
            }
            remove();
        }

        public final void onTransportFailure$1(Exception exc) {
            if (!(exc instanceof PendingIntent.CanceledException)) {
                throw new AssertionError(exc);
            }
            Log.w(
                    "LocationManagerService",
                    LocationProviderManager.this.mName
                            + " provider registration "
                            + this.mIdentity
                            + " removed",
                    exc);
            remove();
        }

        @Override // com.android.server.location.provider.LocationProviderManager.LocationRegistration, com.android.server.location.provider.LocationProviderManager.Registration, com.android.server.location.listeners.RemovableListenerRegistration
        public final void onUnregister() {
            Object obj = this.mKey;
            Objects.requireNonNull(obj);
            ((PendingIntent) obj).removeCancelListener(this);
            super.onUnregister();
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
        public final void deliverOnFlushComplete(int i) {
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

        @Override // com.android.server.location.provider.LocationProviderManager.LocationTransport
        public final void deliverOnLocationChanged(
                LocationResult locationResult, ExternalWakeLockReleaser externalWakeLockReleaser) {
            LocationProviderManager$PendingIntentSender$GatedCallback
                    locationProviderManager$PendingIntentSender$GatedCallback;
            PendingIntent.OnFinished onFinished;
            Runnable runnable;
            BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
            makeBasic.setDontSendToRestrictedApps(true);
            makeBasic.setTemporaryAppAllowlist(
                    10000L,
                    0,
                    FrameworkStatsLog
                            .APP_BACKGROUND_RESTRICTIONS_INFO__EXEMPTION_REASON__REASON_LOCATION_PROVIDER,
                    "");
            Intent putExtra = new Intent().putExtra("location", locationResult.getLastLocation());
            if (locationResult.size() > 1) {
                putExtra.putExtra(
                        "locations",
                        (Parcelable[]) locationResult.asList().toArray(new Location[0]));
            }
            Runnable runnable2 = null;
            LocationProviderManager$$ExternalSyntheticLambda22
                    locationProviderManager$$ExternalSyntheticLambda22 =
                            externalWakeLockReleaser != null
                                    ? new LocationProviderManager$$ExternalSyntheticLambda22(
                                            6, externalWakeLockReleaser)
                                    : null;
            PendingIntent pendingIntent = this.mPendingIntent;
            Context context = this.mContext;
            Bundle bundle = makeBasic.toBundle();
            if (locationProviderManager$$ExternalSyntheticLambda22 != null) {
                final LocationProviderManager$PendingIntentSender$GatedCallback
                        locationProviderManager$PendingIntentSender$GatedCallback2 =
                                new LocationProviderManager$PendingIntentSender$GatedCallback();
                locationProviderManager$PendingIntentSender$GatedCallback2.mCallback =
                        locationProviderManager$$ExternalSyntheticLambda22;
                onFinished =
                        new PendingIntent
                                .OnFinished() { // from class:
                                                // com.android.server.location.provider.LocationProviderManager$PendingIntentSender$$ExternalSyntheticLambda0
                            @Override // android.app.PendingIntent.OnFinished
                            public final void onSendFinished(
                                    PendingIntent pendingIntent2,
                                    Intent intent,
                                    int i,
                                    String str,
                                    Bundle bundle2) {
                                LocationProviderManager$PendingIntentSender$GatedCallback.this
                                        .run();
                            }
                        };
                locationProviderManager$PendingIntentSender$GatedCallback =
                        locationProviderManager$PendingIntentSender$GatedCallback2;
            } else {
                locationProviderManager$PendingIntentSender$GatedCallback = null;
                onFinished = null;
            }
            pendingIntent.send(context, 0, putExtra, onFinished, null, null, bundle);
            if (locationProviderManager$PendingIntentSender$GatedCallback != null) {
                synchronized (locationProviderManager$PendingIntentSender$GatedCallback) {
                    try {
                        locationProviderManager$PendingIntentSender$GatedCallback.mGate = true;
                        if (locationProviderManager$PendingIntentSender$GatedCallback.mRun
                                && (runnable =
                                                locationProviderManager$PendingIntentSender$GatedCallback
                                                        .mCallback)
                                        != null) {
                            locationProviderManager$PendingIntentSender$GatedCallback.mCallback =
                                    null;
                            runnable2 = runnable;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                if (runnable2 != null) {
                    runnable2.run();
                }
            }
        }

        @Override // com.android.server.location.provider.LocationProviderManager.ProviderTransport
        public final void deliverOnProviderEnabledChanged(String str, boolean z) {
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

    public abstract class LocationRegistration extends Registration
            implements AlarmManager.OnAlarmListener,
                    LocationManagerInternal.ProviderEnabledListener {
        public long mExpirationRealtimeMs;
        public int mNumLocationsDelivered;
        public volatile ProviderTransport mProviderTransport;
        public final PowerManager.WakeLock mWakeLock;
        public final ExternalWakeLockReleaser mWakeLockReleaser;

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
                    (PowerManager)
                            LocationProviderManager.this.mContext.getSystemService(
                                    PowerManager.class);
            Objects.requireNonNull(powerManager);
            PowerManager.WakeLock newWakeLock = powerManager.newWakeLock(1, "*location*");
            this.mWakeLock = newWakeLock;
            newWakeLock.setReferenceCounted(true);
            newWakeLock.setWorkSource(locationRequest.getWorkSource());
            this.mWakeLockReleaser = new ExternalWakeLockReleaser(callerIdentity, newWakeLock);
        }

        @Override // com.android.server.location.provider.LocationProviderManager.Registration
        public final ListenerExecutor.ListenerOperation acceptLocationChange(
                LocationResult locationResult) {
            if (SystemClock.elapsedRealtime() >= this.mExpirationRealtimeMs) {
                Log.d(
                        "LocationManagerService",
                        LocationProviderManager.this.mName
                                + " provider registration "
                                + this.mIdentity
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
                                    this.mPreviousLocation =
                                            LocationRegistration.this.getLastDeliveredLocation();
                                }

                                @Override // java.util.function.Predicate
                                public final boolean test(Object obj) {
                                    Location location = (Location) obj;
                                    if (Double.isNaN(location.getLatitude())
                                            || location.getLatitude() < -90.0d
                                            || location.getLatitude() > 90.0d
                                            || Double.isNaN(location.getLongitude())
                                            || location.getLongitude() < -180.0d
                                            || location.getLongitude() > 180.0d) {
                                        Log.e(
                                                "LocationManagerService",
                                                LocationProviderManager.this.mName
                                                        + " provider registration "
                                                        + LocationRegistration.this.mIdentity
                                                        + " dropped delivery - invalid latitude or"
                                                        + " longitude.");
                                        return false;
                                    }
                                    if (this.mPreviousLocation != null) {
                                        if (location.getElapsedRealtimeMillis()
                                                        - this.mPreviousLocation
                                                                .getElapsedRealtimeMillis()
                                                < LocationRegistration.this
                                                                .getRequest()
                                                                .getMinUpdateIntervalMillis()
                                                        - Math.min(
                                                                (long)
                                                                        (LocationRegistration.this
                                                                                        .getRequest()
                                                                                        .getIntervalMillis()
                                                                                * 0.1f),
                                                                30000L)) {
                                            return false;
                                        }
                                        double minUpdateDistanceMeters =
                                                LocationRegistration.this
                                                        .getRequest()
                                                        .getMinUpdateDistanceMeters();
                                        if (minUpdateDistanceMeters > 0.0d
                                                && location.distanceTo(this.mPreviousLocation)
                                                        <= minUpdateDistanceMeters) {
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
            if (LocationProviderManager.this.mAppOpsHelper.noteOpNoThrow(
                    (Flags.enableLocationBypass() && isOnlyBypassPermitted())
                            ? 147
                            : LocationPermissions.asAppOp(getPermissionLevel()),
                    this.mIdentity)) {
                final boolean z = getRequest().getIntervalMillis() != Long.MAX_VALUE;
                return new ListenerExecutor
                        .ListenerOperation() { // from class:
                                               // com.android.server.location.provider.LocationProviderManager.LocationRegistration.2
                    public final void onPostExecute(boolean z2) {
                        if (!z2 && z) {
                            LocationRegistration.this.mWakeLock.release();
                        }
                        if (z2) {
                            LocationRegistration locationRegistration = LocationRegistration.this;
                            int i = locationRegistration.mNumLocationsDelivered + 1;
                            locationRegistration.mNumLocationsDelivered = i;
                            if (i >= locationRegistration.getRequest().getMaxUpdates()) {
                                StringBuilder sb = new StringBuilder();
                                sb.append(LocationProviderManager.this.mName);
                                sb.append(" provider registration ");
                                sb.append(LocationRegistration.this.mIdentity);
                                sb.append(" finished after ");
                                AudioService$$ExternalSyntheticOutline0.m(
                                        sb,
                                        LocationRegistration.this.mNumLocationsDelivered,
                                        " updates",
                                        "LocationManagerService");
                                LocationRegistration.this.remove();
                            }
                        }
                    }

                    public final void onPreExecute() {
                        LocationRegistration.this.mLastLocation = filter.getLastLocation();
                        if (z) {
                            LocationRegistration.this.mWakeLock.acquire(30000L);
                        }
                    }

                    public final void operate(Object obj) {
                        ((LocationTransport) obj)
                                .deliverOnLocationChanged(
                                        LocationRegistration.this.mIdentity.getPid()
                                                        == Process.myPid()
                                                ? filter.deepCopy()
                                                : filter,
                                        z ? LocationRegistration.this.mWakeLockReleaser : null);
                        LocationEventLog.EVENT_LOG.logProviderDeliveredLocations(
                                LocationProviderManager.this.mName,
                                filter.size(),
                                LocationRegistration.this.mIdentity);
                    }
                };
            }
            Log.w(
                    "LocationManagerService",
                    LocationProviderManager.this.mName
                            + " provider registration "
                            + this.mIdentity
                            + " noteOp denied");
            return null;
        }

        @Override // com.android.server.location.provider.LocationProviderManager.Registration,
                  // com.android.server.location.listeners.RemovableListenerRegistration
        public final void onActive() {
            Location lastLocationUnsafe;
            super.onActive();
            if (CompatChanges.isChangeEnabled(73144566L, this.mIdentity.getUid())) {
                long intervalMillis = getRequest().getIntervalMillis();
                Location lastDeliveredLocation = getLastDeliveredLocation();
                if (lastDeliveredLocation != null) {
                    intervalMillis =
                            Math.min(
                                    intervalMillis,
                                    lastDeliveredLocation.getElapsedRealtimeAgeMillis() - 1);
                }
                long j = intervalMillis;
                if (j <= 30000
                        || (lastLocationUnsafe =
                                        LocationProviderManager.this.getLastLocationUnsafe(
                                                this.mIdentity.getUserId(),
                                                getPermissionLevel(),
                                                getRequest().isBypass(),
                                                j))
                                == null) {
                    return;
                }
                executeOperation(
                        acceptLocationChange(
                                LocationResult.wrap(new Location[] {lastLocationUnsafe})));
            }
        }

        @Override // android.app.AlarmManager.OnAlarmListener
        public final void onAlarm() {
            Log.d(
                    "LocationManagerService",
                    LocationProviderManager.this.mName
                            + " provider registration "
                            + this.mIdentity
                            + " expired at "
                            + TimeUtils.formatRealtime(this.mExpirationRealtimeMs));
            synchronized (LocationProviderManager.this.mMultiplexerLock) {
                this.mExpirationRealtimeMs = Long.MAX_VALUE;
                remove();
            }
        }

        @Override // com.android.server.location.listeners.RemovableListenerRegistration
        public final void onListenerUnregister() {
            this.mProviderTransport = null;
        }

        public final void onProviderEnabledChanged(String str, int i, final boolean z) {
            Preconditions.checkState(LocationProviderManager.this.mName.equals(str));
            if (i != this.mIdentity.getUserId()) {
                return;
            }
            executeSafely(
                    this.mExecutor,
                    new Supplier() { // from class:
                                     // com.android.server.location.provider.LocationProviderManager$LocationRegistration$$ExternalSyntheticLambda0
                        @Override // java.util.function.Supplier
                        public final Object get() {
                            return LocationProviderManager.LocationRegistration.this
                                    .mProviderTransport;
                        }
                    },
                    new ListenerExecutor
                            .ListenerOperation() { // from class:
                                                   // com.android.server.location.provider.LocationProviderManager$LocationRegistration$$ExternalSyntheticLambda1
                        public final void operate(Object obj) {
                            LocationProviderManager.LocationRegistration locationRegistration =
                                    LocationProviderManager.LocationRegistration.this;
                            ((LocationProviderManager.ProviderTransport) obj)
                                    .deliverOnProviderEnabledChanged(
                                            LocationProviderManager.this.mName, z);
                        }
                    },
                    new ListenerExecutor
                            .FailureCallback() { // from class:
                                                 // com.android.server.location.provider.LocationProviderManager$LocationRegistration$$ExternalSyntheticLambda2
                        public final void onFailure(
                                ListenerExecutor.ListenerOperation listenerOperation,
                                Exception exc) {
                            LocationProviderManager.LocationRegistration.this
                                    .onProviderOperationFailure(exc);
                        }
                    });
        }

        public abstract void onProviderOperationFailure(Exception exc);

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
                        expirationRealtimeMs - elapsedRealtime, this);
            }
            LocationProviderManager.this.addEnabledListener(this);
            int userId = this.mIdentity.getUserId();
            if (LocationProviderManager.this.isEnabled(userId)) {
                return;
            }
            onProviderEnabledChanged(LocationProviderManager.this.mName, userId, false);
        }

        @Override // com.android.server.location.provider.LocationProviderManager.Registration,
                  // com.android.server.location.listeners.RemovableListenerRegistration
        public void onUnregister() {
            LocationProviderManager.this.removeEnabledListener(this);
            if (this.mExpirationRealtimeMs < Long.MAX_VALUE) {
                LocationProviderManager.this.mAlarmHelper.cancel(this);
            }
            super.onUnregister();
        }
    }

    public interface LocationTransport {
        void deliverOnFlushComplete(int i);

        void deliverOnLocationChanged(
                LocationResult locationResult, ExternalWakeLockReleaser externalWakeLockReleaser);
    }

    public interface ProviderTransport {
        void deliverOnProviderEnabledChanged(String str, boolean z);
    }

    public abstract class Registration extends RemovableListenerRegistration {
        public boolean isListenerType;
        public final LocationRequest mBaseRequest;
        public boolean mBypassPermitted;
        public boolean mForeground;
        public final CallerIdentity mIdentity;
        public boolean mIsUsingHighPower;
        public Location mLastLocation;
        public String mListenerId;
        public final int mPermissionLevel;
        public boolean mPermitted;
        public LocationRequest mProviderLocationRequest;
        public final int mUid;

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

        public abstract ListenerExecutor.ListenerOperation acceptLocationChange(
                LocationResult locationResult);

        public final LocationRequest calculateProviderLocationRequest() {
            boolean z;
            long clearCallingIdentity;
            boolean z2;
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
            boolean z3 = false;
            if (isLocationSettingsIgnored) {
                if (!LocationProviderManager.this
                                .mSettingsHelper
                                .mIgnoreSettingsPackageAllowlist
                                .getValue()
                                .contains(
                                        this.mIdentity.getPackageName(),
                                        this.mIdentity.getAttributionTag())
                        && !LocationProviderManager.this.mLocationManagerInternal.isProvider(
                                (String) null, this.mIdentity)) {
                    isLocationSettingsIgnored = false;
                }
                builder.setLocationSettingsIgnored(isLocationSettingsIgnored);
            }
            boolean isAdasGnssBypass = this.mBaseRequest.isAdasGnssBypass();
            if (isAdasGnssBypass) {
                if ("gps".equals(LocationProviderManager.this.mName)) {
                    LocationManagerService.Lifecycle.LifecycleUserInfoHelper
                            lifecycleUserInfoHelper = LocationProviderManager.this.mUserHelper;
                    int userId = this.mIdentity.getUserId();
                    ActivityManagerInternal activityManagerInternal =
                            lifecycleUserInfoHelper.getActivityManagerInternal();
                    if (activityManagerInternal != null) {
                        clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            z2 = activityManagerInternal.isCurrentProfile(userId);
                        } finally {
                        }
                    } else {
                        z2 = false;
                    }
                    if (z2
                            && LocationProviderManager.this.mLocationSettings.getUserSettings(
                                            this.mIdentity.getUserId())
                                    .mAdasGnssLocationEnabled
                            && LocationProviderManager.this
                                    .mSettingsHelper
                                    .mAdasPackageAllowlist
                                    .getValue()
                                    .contains(
                                            this.mIdentity.getPackageName(),
                                            this.mIdentity.getAttributionTag())) {
                        z3 = isAdasGnssBypass;
                    }
                } else {
                    Log.e(
                            "LocationManagerService",
                            "adas gnss bypass request received in non-gps provider");
                }
                builder.setAdasGnssBypass(z3);
            }
            if (!isLocationSettingsIgnored) {
                if (!((ArraySet)
                                LocationProviderManager.this.mSettingsHelper
                                        .mBackgroundThrottlePackageWhitelist.getValue())
                        .contains(this.mIdentity.getPackageName())) {
                    if (!LocationProviderManager.this.mSettingsHelper
                            .mBackgroundThrottlePackageAllowlistByNsflp.contains(
                            this.mIdentity.getPackageName())) {
                        z =
                                LocationProviderManager.this.mLocationManagerInternal.isProvider(
                                        (String) null, this.mIdentity);
                        if (!z && !this.mForeground) {
                            long intervalMillis = this.mBaseRequest.getIntervalMillis();
                            SystemSettingsHelper.LongGlobalSetting longGlobalSetting =
                                    LocationProviderManager.this
                                            .mSettingsHelper
                                            .mBackgroundThrottleIntervalMs;
                            longGlobalSetting.getClass();
                            clearCallingIdentity = Binder.clearCallingIdentity();
                            try {
                                long j =
                                        Settings.Global.getLong(
                                                longGlobalSetting.mContext.getContentResolver(),
                                                longGlobalSetting.mSettingName,
                                                1800000L);
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                builder.setIntervalMillis(Math.max(intervalMillis, j));
                            } finally {
                            }
                        }
                    }
                }
                z = true;
                if (!z) {
                    long intervalMillis2 = this.mBaseRequest.getIntervalMillis();
                    SystemSettingsHelper.LongGlobalSetting longGlobalSetting2 =
                            LocationProviderManager.this
                                    .mSettingsHelper
                                    .mBackgroundThrottleIntervalMs;
                    longGlobalSetting2.getClass();
                    clearCallingIdentity = Binder.clearCallingIdentity();
                    long j2 =
                            Settings.Global.getLong(
                                    longGlobalSetting2.mContext.getContentResolver(),
                                    longGlobalSetting2.mSettingName,
                                    1800000L);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    builder.setIntervalMillis(Math.max(intervalMillis2, j2));
                }
            }
            return builder.build();
        }

        public final Location getLastDeliveredLocation() {
            Location location;
            synchronized (LocationProviderManager.this.mMultiplexerLock) {
                location = this.mLastLocation;
            }
            return location;
        }

        @Override // com.android.server.location.listeners.RemovableListenerRegistration
        public final ListenerMultiplexer getOwner() {
            return LocationProviderManager.this;
        }

        public final int getPermissionLevel() {
            int i;
            synchronized (LocationProviderManager.this.mMultiplexerLock) {
                i = this.mPermissionLevel;
            }
            return i;
        }

        public final LocationRequest getRequest() {
            LocationRequest locationRequest;
            synchronized (LocationProviderManager.this.mMultiplexerLock) {
                locationRequest = this.mProviderLocationRequest;
            }
            return locationRequest;
        }

        public final boolean isForeground() {
            boolean z;
            synchronized (LocationProviderManager.this.mMultiplexerLock) {
                z = this.mForeground;
            }
            return z;
        }

        public final boolean isOnlyBypassPermitted() {
            boolean z;
            synchronized (LocationProviderManager.this.mMultiplexerLock) {
                try {
                    z = this.mBypassPermitted && !this.mPermitted;
                } finally {
                }
            }
            return z;
        }

        public final boolean isPermitted() {
            boolean z;
            synchronized (LocationProviderManager.this.mMultiplexerLock) {
                try {
                    z = this.mPermitted || this.mBypassPermitted;
                } finally {
                }
            }
            return z;
        }

        public final boolean isUsingHighPower() {
            ProviderProperties providerProperties =
                    ((AbstractLocationProvider.InternalState)
                                    LocationProviderManager.this.mProvider.mInternalState.get())
                            .state
                            .properties;
            return providerProperties != null
                    && this.mActive
                    && getRequest().getIntervalMillis() < 300000
                    && providerProperties.getPowerUsage() == 3;
        }

        @Override // com.android.server.location.listeners.RemovableListenerRegistration
        public void onActive() {
            LocationEventLog.AggregateStats aggregateStats =
                    LocationEventLog.EVENT_LOG.getAggregateStats(
                            LocationProviderManager.this.mName, this.mIdentity);
            synchronized (aggregateStats) {
                Preconditions.checkState(aggregateStats.mAddedRequestCount > 0);
                int i = aggregateStats.mActiveRequestCount;
                aggregateStats.mActiveRequestCount = i + 1;
                if (i == 0) {
                    aggregateStats.mActiveTimeLastUpdateRealtimeMs = SystemClock.elapsedRealtime();
                }
            }
            if (!getRequest().isHiddenFromAppOps()) {
                LocationProviderManager.this.mAppOpsHelper.startOpNoThrow(41, this.mIdentity);
            }
            onHighPowerUsageChanged();
        }

        public final boolean onBypassLocationPermissionsChanged(boolean z) {
            synchronized (LocationProviderManager.this.mMultiplexerLock) {
                try {
                    boolean z2 =
                            Flags.enableLocationBypass()
                                    && z
                                    && LocationProviderManager.this.mContext.checkPermission(
                                                    "android.permission.LOCATION_BYPASS",
                                                    this.mIdentity.getPid(),
                                                    this.mIdentity.getUid())
                                            == 0;
                    if (this.mBypassPermitted == z2) {
                        return false;
                    }
                    Log.v(
                            "LocationManagerService",
                            LocationProviderManager.this.mName
                                    + " provider package "
                                    + this.mIdentity.getPackageName()
                                    + " bypass permitted = "
                                    + z2);
                    this.mBypassPermitted = z2;
                    return true;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void onHighPowerUsageChanged() {
            boolean isUsingHighPower = isUsingHighPower();
            if (isUsingHighPower != this.mIsUsingHighPower) {
                this.mIsUsingHighPower = isUsingHighPower;
                if (getRequest().isHiddenFromAppOps()) {
                    return;
                }
                if (this.mIsUsingHighPower) {
                    LocationProviderManager.this.mAppOpsHelper.startOpNoThrow(42, this.mIdentity);
                } else {
                    LocationProviderManager.this.mAppOpsHelper.finishOp(42, this.mIdentity);
                }
            }
        }

        @Override // com.android.server.location.listeners.RemovableListenerRegistration
        public void onInactive() {
            onHighPowerUsageChanged();
            if (!getRequest().isHiddenFromAppOps()) {
                LocationProviderManager.this.mAppOpsHelper.finishOp(41, this.mIdentity);
            }
            LocationEventLog.AggregateStats aggregateStats =
                    LocationEventLog.EVENT_LOG.getAggregateStats(
                            LocationProviderManager.this.mName, this.mIdentity);
            synchronized (aggregateStats) {
                aggregateStats.updateTotals();
                boolean z = true;
                int i = aggregateStats.mActiveRequestCount - 1;
                aggregateStats.mActiveRequestCount = i;
                if (i < 0) {
                    z = false;
                }
                Preconditions.checkState(z);
            }
        }

        public final boolean onLocationPermissionsChanged$2$1() {
            String str;
            boolean hasLocationPermissions =
                    LocationProviderManager.this.mLocationPermissionsHelper.hasLocationPermissions(
                            this.mPermissionLevel, this.mIdentity);
            boolean z = false;
            if (hasLocationPermissions == this.mPermitted) {
                return false;
            }
            Log.v(
                    "LocationManagerService",
                    LocationProviderManager.this.mName
                            + " provider package "
                            + this.mIdentity.getPackageName()
                            + " permitted = "
                            + hasLocationPermissions);
            this.mPermitted = hasLocationPermissions;
            boolean z2 = true;
            if (hasLocationPermissions) {
                LocationEventLog locationEventLog = LocationEventLog.EVENT_LOG;
                String str2 = LocationProviderManager.this.mName;
                CallerIdentity callerIdentity = this.mIdentity;
                locationEventLog.getClass();
                locationEventLog.addLog$1(
                        new LocationEventLog.ProviderClientPermittedEvent(
                                str2, z2, callerIdentity, 0));
            } else {
                LocationEventLog locationEventLog2 = LocationEventLog.EVENT_LOG;
                String str3 = LocationProviderManager.this.mName;
                CallerIdentity callerIdentity2 = this.mIdentity;
                locationEventLog2.getClass();
                locationEventLog2.addLog$1(
                        new LocationEventLog.ProviderClientPermittedEvent(
                                str3, z, callerIdentity2, 0));
            }
            Parcelable parcelable =
                    (LocationConstants.PAUSED_BY)
                            ((ConcurrentHashMap)
                                            LocationProviderManager.this
                                                    .mLocationPermissionsHelper
                                                    .mPauseReasonByCaller)
                                    .getOrDefault(
                                            Integer.valueOf(this.mIdentity.getUid()),
                                            LocationConstants.PAUSED_BY.UNKNOWN);
            LocationProviderManager locationProviderManager = LocationProviderManager.this;
            NSLocationProviderHelper nSLocationProviderHelper =
                    locationProviderManager.mNSLocationProviderHelper;
            Set set =
                    (Set)
                            ((ConcurrentHashMap) nSLocationProviderHelper.mRegistrationMap)
                                    .get(locationProviderManager.mName);
            if (set != null && set.contains(this)) {
                boolean isPermitted = isPermitted();
                StringBuilder sb = new StringBuilder("Receiver status was changed, isAllowed=");
                sb.append(isPermitted);
                if (isPermitted) {
                    str = "";
                } else {
                    str = " by " + parcelable;
                }
                sb.append(str);
                Log.w("NSLocationProviderHelper", sb.toString());
                Bundle bundle = new Bundle();
                bundle.putString("listenerid", this.mListenerId);
                bundle.putBoolean("isAllowed", isPermitted);
                bundle.putParcelable("reason", parcelable);
                nSLocationProviderHelper.updateUidProcState(this.mUid, bundle);
                bundle.putInt("permissionLevel", getPermissionLevel());
                nSLocationProviderHelper.mNSConnectionHelper.onStateUpdated(
                        LocationConstants.STATE_TYPE.LOCATION_UPDATE, bundle);
            }
            return true;
        }

        public final boolean onProviderLocationRequestChanged() {
            synchronized (LocationProviderManager.this.mMultiplexerLock) {
                try {
                    LocationRequest calculateProviderLocationRequest =
                            calculateProviderLocationRequest();
                    if (this.mProviderLocationRequest.equals(calculateProviderLocationRequest)) {
                        return false;
                    }
                    LocationRequest locationRequest = this.mProviderLocationRequest;
                    this.mProviderLocationRequest = calculateProviderLocationRequest;
                    onHighPowerUsageChanged();
                    LocationProviderManager.this.updateService();
                    return locationRequest.isBypass()
                            != calculateProviderLocationRequest.isBypass();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.location.listeners.RemovableListenerRegistration
        public void onRegister() {
            Log.d(
                    "LocationManagerService",
                    LocationProviderManager.this.mName
                            + " provider added registration from "
                            + this.mIdentity
                            + " -> "
                            + getRequest());
            LocationEventLog locationEventLog = LocationEventLog.EVENT_LOG;
            String str = LocationProviderManager.this.mName;
            CallerIdentity callerIdentity = this.mIdentity;
            LocationRequest locationRequest = this.mBaseRequest;
            locationEventLog.getClass();
            locationEventLog.addLog$1(
                    new LocationEventLog.ProviderClientRegisterEvent(
                            str, true, callerIdentity, locationRequest));
            LocationEventLog.AggregateStats aggregateStats =
                    locationEventLog.getAggregateStats(str, callerIdentity);
            long intervalMillis = locationRequest.getIntervalMillis();
            synchronized (aggregateStats) {
                try {
                    int i = aggregateStats.mAddedRequestCount;
                    aggregateStats.mAddedRequestCount = i + 1;
                    if (i == 0) {
                        aggregateStats.mAddedTimeLastUpdateRealtimeMs =
                                SystemClock.elapsedRealtime();
                    }
                    aggregateStats.mFastestIntervalMs =
                            Math.min(intervalMillis, aggregateStats.mFastestIntervalMs);
                    aggregateStats.mSlowestIntervalMs =
                            Math.max(intervalMillis, aggregateStats.mSlowestIntervalMs);
                } catch (Throwable th) {
                    throw th;
                }
            }
            onLocationPermissionsChanged$2$1();
            onBypassLocationPermissionsChanged(
                    LocationProviderManager.this.mEmergencyHelper.isInEmergency(0L));
            this.mForeground =
                    LocationProviderManager.this.mAppForegroundHelper.isAppForeground(
                            this.mIdentity.getUid());
            this.mProviderLocationRequest = calculateProviderLocationRequest();
            this.mIsUsingHighPower = isUsingHighPower();
            if (this.mForeground) {
                locationEventLog.logProviderClientForeground(
                        LocationProviderManager.this.mName, this.mIdentity);
            }
        }

        @Override // com.android.server.location.listeners.RemovableListenerRegistration
        public void onUnregister() {
            LocationEventLog locationEventLog = LocationEventLog.EVENT_LOG;
            String str = LocationProviderManager.this.mName;
            CallerIdentity callerIdentity = this.mIdentity;
            locationEventLog.getClass();
            locationEventLog.addLog$1(
                    new LocationEventLog.ProviderClientRegisterEvent(
                            str, false, callerIdentity, null));
            LocationEventLog.AggregateStats aggregateStats =
                    locationEventLog.getAggregateStats(str, callerIdentity);
            synchronized (aggregateStats) {
                aggregateStats.updateTotals();
                int i = aggregateStats.mAddedRequestCount - 1;
                aggregateStats.mAddedRequestCount = i;
                Preconditions.checkState(i >= 0);
                aggregateStats.mActiveRequestCount =
                        Math.min(
                                aggregateStats.mAddedRequestCount,
                                aggregateStats.mActiveRequestCount);
                aggregateStats.mForegroundRequestCount =
                        Math.min(
                                aggregateStats.mAddedRequestCount,
                                aggregateStats.mForegroundRequestCount);
            }
            Log.d(
                    "LocationManagerService",
                    LocationProviderManager.this.mName
                            + " provider removed registration from "
                            + this.mIdentity);
            this.mKey = null;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.mIdentity);
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

    public interface StateChangedListener {}

    public LocationProviderManager(
            Context context,
            Injector injector,
            String str,
            PassiveLocationProviderManager passiveLocationProviderManager) {
        this(context, injector, str, passiveLocationProviderManager, Collections.emptyList());
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda3] */
    /* JADX WARN: Type inference failed for: r0v10, types: [com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda11] */
    /* JADX WARN: Type inference failed for: r0v12, types: [com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda2] */
    /* JADX WARN: Type inference failed for: r0v14, types: [com.android.server.location.provider.LocationProviderManager$2] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda4] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda5] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda4] */
    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.server.location.provider.LocationProviderManager$1] */
    /* JADX WARN: Type inference failed for: r0v6, types: [com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda7] */
    /* JADX WARN: Type inference failed for: r0v7, types: [com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda5] */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda5] */
    /* JADX WARN: Type inference failed for: r0v9, types: [com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda5] */
    public LocationProviderManager(
            Context context,
            Injector injector,
            String str,
            PassiveLocationProviderManager passiveLocationProviderManager,
            Collection collection) {
        this.mUserChangedListener =
                new UserInfoHelper$UserListener() { // from class:
                                                    // com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda0
                    @Override // com.android.server.location.injector.UserInfoHelper$UserListener
                    public final void onUserChanged(int i, int i2) {
                        LocationProviderManager locationProviderManager =
                                LocationProviderManager.this;
                        synchronized (locationProviderManager.mMultiplexerLock) {
                            try {
                                if (locationProviderManager.mState == 2) {
                                    return;
                                }
                                boolean z = true;
                                if (i2 != 1) {
                                    if (i2 == 2) {
                                        locationProviderManager.onUserStarted(i);
                                    } else if (i2 != 3) {
                                        if (i2 != 4) {}
                                    } else if (i != -10000) {
                                        if (i == -1) {
                                            locationProviderManager.mEnabled.clear();
                                            locationProviderManager.mLastLocations.clear();
                                        } else {
                                            if (i < 0) {
                                                z = false;
                                            }
                                            Preconditions.checkArgument(z);
                                            locationProviderManager.mEnabled.delete(i);
                                            locationProviderManager.mLastLocations.remove(i);
                                        }
                                    }
                                }
                                locationProviderManager.updateRegistrations(
                                        new LocationProviderManager$$ExternalSyntheticLambda19(
                                                i, 3));
                            } finally {
                            }
                        }
                    }
                };
        this.mLocationUserSettingsListener =
                new LocationSettings
                        .LocationUserSettingsListener() { // from class:
                                                          // com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda3
                    @Override // com.android.server.location.settings.LocationSettings.LocationUserSettingsListener
                    public final void onLocationUserSettingsChanged(
                            int i,
                            LocationUserSettings locationUserSettings,
                            LocationUserSettings locationUserSettings2) {
                        LocationProviderManager locationProviderManager =
                                LocationProviderManager.this;
                        locationProviderManager.getClass();
                        if (locationUserSettings.mAdasGnssLocationEnabled
                                != locationUserSettings2.mAdasGnssLocationEnabled) {
                            locationProviderManager.updateRegistrations(
                                    new LocationProviderManager$$ExternalSyntheticLambda19(i, 6));
                        }
                    }
                };
        final int i = 0;
        this.mLocationEnabledChangedListener =
                new SettingsHelper$UserSettingChangedListener(
                        this) { // from class:
                                // com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda4
                    public final /* synthetic */ LocationProviderManager f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.server.location.injector.SettingsHelper$UserSettingChangedListener
                    public final void onSettingChanged(int i2) {
                        int i3 = i;
                        LocationProviderManager locationProviderManager = this.f$0;
                        switch (i3) {
                            case 0:
                                synchronized (locationProviderManager.mMultiplexerLock) {
                                    try {
                                        if (locationProviderManager.mState == 2) {
                                            return;
                                        }
                                        locationProviderManager.onEnabledChanged(i2);
                                        return;
                                    } finally {
                                    }
                                }
                            default:
                                locationProviderManager.getClass();
                                locationProviderManager.updateRegistrations(
                                        new LocationProviderManager$$ExternalSyntheticLambda19(
                                                i2, 5));
                                return;
                        }
                    }
                };
        this.mBackgroundThrottlePackageWhitelistChangedListener =
                new SettingsHelper$GlobalSettingChangedListener(
                        this) { // from class:
                                // com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda5
                    public final /* synthetic */ LocationProviderManager f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.server.location.injector.SettingsHelper$GlobalSettingChangedListener
                    public final void onSettingChanged() {
                        int i2 = i;
                        LocationProviderManager locationProviderManager = this.f$0;
                        locationProviderManager.getClass();
                        switch (i2) {
                            case 0:
                                locationProviderManager.updateRegistrations(
                                        new LocationProviderManager$$ExternalSyntheticLambda12(4));
                                break;
                            case 1:
                                locationProviderManager.updateRegistrations(
                                        new LocationProviderManager$$ExternalSyntheticLambda12(4));
                                break;
                            case 2:
                                locationProviderManager.updateRegistrations(
                                        new LocationProviderManager$$ExternalSyntheticLambda12(4));
                                break;
                            default:
                                locationProviderManager.updateRegistrations(
                                        new LocationProviderManager$$ExternalSyntheticLambda12(4));
                                break;
                        }
                    }
                };
        final int i2 = 1;
        this.mLocationPackageBlacklistChangedListener =
                new SettingsHelper$UserSettingChangedListener(
                        this) { // from class:
                                // com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda4
                    public final /* synthetic */ LocationProviderManager f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.server.location.injector.SettingsHelper$UserSettingChangedListener
                    public final void onSettingChanged(int i22) {
                        int i3 = i2;
                        LocationProviderManager locationProviderManager = this.f$0;
                        switch (i3) {
                            case 0:
                                synchronized (locationProviderManager.mMultiplexerLock) {
                                    try {
                                        if (locationProviderManager.mState == 2) {
                                            return;
                                        }
                                        locationProviderManager.onEnabledChanged(i22);
                                        return;
                                    } finally {
                                    }
                                }
                            default:
                                locationProviderManager.getClass();
                                locationProviderManager.updateRegistrations(
                                        new LocationProviderManager$$ExternalSyntheticLambda19(
                                                i22, 5));
                                return;
                        }
                    }
                };
        this.mLocationPermissionsListener =
                new LocationPermissionsHelper$LocationPermissionsListener() { // from class:
                                                                              // com.android.server.location.provider.LocationProviderManager.1
                    @Override // com.android.server.location.injector.LocationPermissionsHelper$LocationPermissionsListener
                    public final void onLocationPermissionsChanged(int i3) {
                        LocationProviderManager locationProviderManager =
                                LocationProviderManager.this;
                        locationProviderManager.getClass();
                        locationProviderManager.updateRegistrations(
                                new LocationProviderManager$$ExternalSyntheticLambda19(i3, 4));
                    }

                    @Override // com.android.server.location.injector.LocationPermissionsHelper$LocationPermissionsListener
                    public final void onLocationPermissionsChanged(String str2) {
                        LocationProviderManager locationProviderManager =
                                LocationProviderManager.this;
                        locationProviderManager.getClass();
                        locationProviderManager.updateRegistrations(
                                new LocationProviderManager$$ExternalSyntheticLambda35(1, str2));
                    }
                };
        this.mAppForegroundChangedListener =
                new AppForegroundHelper$AppForegroundListener() { // from class:
                    // com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda7
                    @Override // com.android.server.location.injector.AppForegroundHelper$AppForegroundListener
                    public final void onAppForegroundChanged(final int i3, final boolean z) {
                        LocationProviderManager locationProviderManager =
                                LocationProviderManager.this;
                        locationProviderManager.getClass();
                        locationProviderManager.updateRegistrations(
                                new Predicate() { // from class:
                                    // com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda33
                                    /* JADX WARN: Code restructure failed: missing block: B:14:0x008c, code lost:

                                       if (r7.mLocationPowerSaveMode == 3) goto L25;
                                    */
                                    @Override // java.util.function.Predicate
                                    /*
                                        Code decompiled incorrectly, please refer to instructions dump.
                                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                                    */
                                    public final boolean test(java.lang.Object r8) {
                                        /*
                                            r7 = this;
                                            int r0 = r1
                                            boolean r7 = r2
                                            com.android.server.location.provider.LocationProviderManager$Registration r8 = (com.android.server.location.provider.LocationProviderManager.Registration) r8
                                            com.android.server.location.provider.LocationProviderManager r1 = com.android.server.location.provider.LocationProviderManager.this
                                            java.lang.Object r1 = r1.mMultiplexerLock
                                            monitor-enter(r1)
                                            android.location.util.identity.CallerIdentity r2 = r8.mIdentity     // Catch: java.lang.Throwable -> L4e
                                            int r2 = r2.getUid()     // Catch: java.lang.Throwable -> L4e
                                            r3 = 0
                                            if (r2 != r0) goto L94
                                            boolean r2 = r8.mForeground     // Catch: java.lang.Throwable -> L4e
                                            if (r7 == r2) goto L94
                                            java.lang.String r2 = "LocationManagerService"
                                            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L4e
                                            r4.<init>()     // Catch: java.lang.Throwable -> L4e
                                            com.android.server.location.provider.LocationProviderManager r5 = com.android.server.location.provider.LocationProviderManager.this     // Catch: java.lang.Throwable -> L4e
                                            java.lang.String r5 = r5.mName     // Catch: java.lang.Throwable -> L4e
                                            r4.append(r5)     // Catch: java.lang.Throwable -> L4e
                                            java.lang.String r5 = " provider uid "
                                            r4.append(r5)     // Catch: java.lang.Throwable -> L4e
                                            r4.append(r0)     // Catch: java.lang.Throwable -> L4e
                                            java.lang.String r0 = " foreground = "
                                            r4.append(r0)     // Catch: java.lang.Throwable -> L4e
                                            r4.append(r7)     // Catch: java.lang.Throwable -> L4e
                                            java.lang.String r0 = r4.toString()     // Catch: java.lang.Throwable -> L4e
                                            android.util.Log.v(r2, r0)     // Catch: java.lang.Throwable -> L4e
                                            r8.mForeground = r7     // Catch: java.lang.Throwable -> L4e
                                            r0 = 1
                                            if (r7 == 0) goto L50
                                            com.android.server.location.eventlog.LocationEventLog r7 = com.android.server.location.eventlog.LocationEventLog.EVENT_LOG     // Catch: java.lang.Throwable -> L4e
                                            com.android.server.location.provider.LocationProviderManager r2 = com.android.server.location.provider.LocationProviderManager.this     // Catch: java.lang.Throwable -> L4e
                                            java.lang.String r2 = r2.mName     // Catch: java.lang.Throwable -> L4e
                                            android.location.util.identity.CallerIdentity r4 = r8.mIdentity     // Catch: java.lang.Throwable -> L4e
                                            r7.logProviderClientForeground(r2, r4)     // Catch: java.lang.Throwable -> L4e
                                            goto L7a
                                        L4e:
                                            r7 = move-exception
                                            goto L96
                                        L50:
                                            com.android.server.location.eventlog.LocationEventLog r7 = com.android.server.location.eventlog.LocationEventLog.EVENT_LOG     // Catch: java.lang.Throwable -> L4e
                                            com.android.server.location.provider.LocationProviderManager r2 = com.android.server.location.provider.LocationProviderManager.this     // Catch: java.lang.Throwable -> L4e
                                            java.lang.String r2 = r2.mName     // Catch: java.lang.Throwable -> L4e
                                            android.location.util.identity.CallerIdentity r4 = r8.mIdentity     // Catch: java.lang.Throwable -> L4e
                                            r7.getClass()     // Catch: java.lang.Throwable -> L4e
                                            com.android.server.location.eventlog.LocationEventLog$ProviderClientPermittedEvent r5 = new com.android.server.location.eventlog.LocationEventLog$ProviderClientPermittedEvent     // Catch: java.lang.Throwable -> L4e
                                            r6 = 1
                                            r5.<init>(r2, r3, r4, r6)     // Catch: java.lang.Throwable -> L4e
                                            r7.addLog$1(r5)     // Catch: java.lang.Throwable -> L4e
                                            com.android.server.location.eventlog.LocationEventLog$AggregateStats r7 = r7.getAggregateStats(r2, r4)     // Catch: java.lang.Throwable -> L4e
                                            monitor-enter(r7)     // Catch: java.lang.Throwable -> L4e
                                            r7.updateTotals()     // Catch: java.lang.Throwable -> L91
                                            int r2 = r7.mForegroundRequestCount     // Catch: java.lang.Throwable -> L91
                                            int r2 = r2 - r0
                                            r7.mForegroundRequestCount = r2     // Catch: java.lang.Throwable -> L91
                                            if (r2 < 0) goto L75
                                            r2 = r0
                                            goto L76
                                        L75:
                                            r2 = r3
                                        L76:
                                            com.android.internal.util.Preconditions.checkState(r2)     // Catch: java.lang.Throwable -> L91
                                            monitor-exit(r7)     // Catch: java.lang.Throwable -> L4e
                                        L7a:
                                            boolean r7 = r8.onProviderLocationRequestChanged()     // Catch: java.lang.Throwable -> L4e
                                            if (r7 != 0) goto L8e
                                            com.android.server.location.provider.LocationProviderManager r7 = com.android.server.location.provider.LocationProviderManager.this     // Catch: java.lang.Throwable -> L4e
                                            com.android.server.location.injector.SystemLocationPowerSaveModeHelper r7 = r7.mLocationPowerSaveModeHelper     // Catch: java.lang.Throwable -> L4e
                                            boolean r8 = r7.mReady     // Catch: java.lang.Throwable -> L4e
                                            com.android.internal.util.Preconditions.checkState(r8)     // Catch: java.lang.Throwable -> L4e
                                            int r7 = r7.mLocationPowerSaveMode     // Catch: java.lang.Throwable -> L4e
                                            r8 = 3
                                            if (r7 != r8) goto L8f
                                        L8e:
                                            r3 = r0
                                        L8f:
                                            monitor-exit(r1)     // Catch: java.lang.Throwable -> L4e
                                            goto L95
                                        L91:
                                            r8 = move-exception
                                            monitor-exit(r7)     // Catch: java.lang.Throwable -> L4e
                                            throw r8     // Catch: java.lang.Throwable -> L4e
                                        L94:
                                            monitor-exit(r1)     // Catch: java.lang.Throwable -> L4e
                                        L95:
                                            return r3
                                        L96:
                                            monitor-exit(r1)     // Catch: java.lang.Throwable -> L4e
                                            throw r7
                                        */
                                        throw new UnsupportedOperationException(
                                                "Method not decompiled:"
                                                    + " com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda33.test(java.lang.Object):boolean");
                                    }
                                });
                    }
                };
        final int i3 = 2;
        this.mBackgroundThrottleIntervalChangedListener =
                new SettingsHelper$GlobalSettingChangedListener(
                        this) { // from class:
                                // com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda5
                    public final /* synthetic */ LocationProviderManager f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.server.location.injector.SettingsHelper$GlobalSettingChangedListener
                    public final void onSettingChanged() {
                        int i22 = i3;
                        LocationProviderManager locationProviderManager = this.f$0;
                        locationProviderManager.getClass();
                        switch (i22) {
                            case 0:
                                locationProviderManager.updateRegistrations(
                                        new LocationProviderManager$$ExternalSyntheticLambda12(4));
                                break;
                            case 1:
                                locationProviderManager.updateRegistrations(
                                        new LocationProviderManager$$ExternalSyntheticLambda12(4));
                                break;
                            case 2:
                                locationProviderManager.updateRegistrations(
                                        new LocationProviderManager$$ExternalSyntheticLambda12(4));
                                break;
                            default:
                                locationProviderManager.updateRegistrations(
                                        new LocationProviderManager$$ExternalSyntheticLambda12(4));
                                break;
                        }
                    }
                };
        final int i4 = 3;
        this.mAdasPackageAllowlistChangedListener =
                new SettingsHelper$GlobalSettingChangedListener(
                        this) { // from class:
                                // com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda5
                    public final /* synthetic */ LocationProviderManager f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.server.location.injector.SettingsHelper$GlobalSettingChangedListener
                    public final void onSettingChanged() {
                        int i22 = i4;
                        LocationProviderManager locationProviderManager = this.f$0;
                        locationProviderManager.getClass();
                        switch (i22) {
                            case 0:
                                locationProviderManager.updateRegistrations(
                                        new LocationProviderManager$$ExternalSyntheticLambda12(4));
                                break;
                            case 1:
                                locationProviderManager.updateRegistrations(
                                        new LocationProviderManager$$ExternalSyntheticLambda12(4));
                                break;
                            case 2:
                                locationProviderManager.updateRegistrations(
                                        new LocationProviderManager$$ExternalSyntheticLambda12(4));
                                break;
                            default:
                                locationProviderManager.updateRegistrations(
                                        new LocationProviderManager$$ExternalSyntheticLambda12(4));
                                break;
                        }
                    }
                };
        final int i5 = 1;
        this.mIgnoreSettingsPackageWhitelistChangedListener =
                new SettingsHelper$GlobalSettingChangedListener(
                        this) { // from class:
                                // com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda5
                    public final /* synthetic */ LocationProviderManager f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // com.android.server.location.injector.SettingsHelper$GlobalSettingChangedListener
                    public final void onSettingChanged() {
                        int i22 = i5;
                        LocationProviderManager locationProviderManager = this.f$0;
                        locationProviderManager.getClass();
                        switch (i22) {
                            case 0:
                                locationProviderManager.updateRegistrations(
                                        new LocationProviderManager$$ExternalSyntheticLambda12(4));
                                break;
                            case 1:
                                locationProviderManager.updateRegistrations(
                                        new LocationProviderManager$$ExternalSyntheticLambda12(4));
                                break;
                            case 2:
                                locationProviderManager.updateRegistrations(
                                        new LocationProviderManager$$ExternalSyntheticLambda12(4));
                                break;
                            default:
                                locationProviderManager.updateRegistrations(
                                        new LocationProviderManager$$ExternalSyntheticLambda12(4));
                                break;
                        }
                    }
                };
        this.mLocationPowerSaveModeChangedListener =
                new LocationPowerSaveModeHelper$LocationPowerSaveModeChangedListener() { // from
                                                                                         // class:
                                                                                         // com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda11
                    @Override // com.android.server.location.injector.LocationPowerSaveModeHelper$LocationPowerSaveModeChangedListener
                    public final void onLocationPowerSaveModeChanged(int i6) {
                        LocationProviderManager locationProviderManager =
                                LocationProviderManager.this;
                        locationProviderManager.getClass();
                        locationProviderManager.updateRegistrations(
                                new LocationProviderManager$$ExternalSyntheticLambda12(2));
                    }
                };
        this.mScreenInteractiveChangedListener =
                new LocationProviderManager$$ExternalSyntheticLambda1(this);
        this.mEmergencyStateChangedListener =
                new EmergencyHelper
                        .EmergencyStateChangedListener() { // from class:
                                                           // com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda2
                    @Override // com.android.server.location.injector.EmergencyHelper.EmergencyStateChangedListener
                    public final void onStateChanged() {
                        LocationProviderManager locationProviderManager =
                                LocationProviderManager.this;
                        final boolean isInEmergency =
                                locationProviderManager.mEmergencyHelper.isInEmergency(0L);
                        locationProviderManager.updateRegistrations(
                                new Predicate() { // from class:
                                                  // com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda26
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        return ((LocationProviderManager.Registration) obj)
                                                .onBypassLocationPermissionsChanged(isInEmergency);
                                    }
                                });
                    }
                };
        this.mMotionPowerSaveModeChangedListener =
                new LocationProviderManager$$ExternalSyntheticLambda1(this);
        this.mPackageResetResponder =
                new PackageResetHelper$Responder() { // from class:
                                                     // com.android.server.location.provider.LocationProviderManager.2
                    @Override // com.android.server.location.injector.PackageResetHelper$Responder
                    public final boolean isResetableForPackage(String str2) {
                        LocationProviderManager locationProviderManager =
                                LocationProviderManager.this;
                        locationProviderManager.getClass();
                        return locationProviderManager.findRegistration(
                                new LocationProviderManager$$ExternalSyntheticLambda35(0, str2));
                    }

                    @Override // com.android.server.location.injector.PackageResetHelper$Responder
                    public final void onPackageReset(String str2) {
                        LocationProviderManager locationProviderManager =
                                LocationProviderManager.this;
                        locationProviderManager.getClass();
                        locationProviderManager.updateRegistrations(
                                new LocationProviderManager$$ExternalSyntheticLambda35(2, str2));
                    }
                };
        this.mAltitudeConverter = new AltitudeConverter();
        this.mIsAltitudeConverterIdle = true;
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
        LocationManagerService.SystemInjector systemInjector =
                (LocationManagerService.SystemInjector) injector;
        this.mLocationSettings = systemInjector.mLocationSettings;
        SystemSettingsHelper systemSettingsHelper = systemInjector.mSettingsHelper;
        this.mSettingsHelper = systemSettingsHelper;
        this.mUserHelper = systemInjector.mUserInfoHelper;
        this.mAlarmHelper = systemInjector.mAlarmHelper;
        this.mAppOpsHelper = systemInjector.mAppOpsHelper;
        this.mLocationPermissionsHelper = systemInjector.mLocationPermissionsHelper;
        this.mAppForegroundHelper = systemInjector.mAppForegroundHelper;
        this.mLocationPowerSaveModeHelper = systemInjector.mLocationPowerSaveModeHelper;
        this.mScreenInteractiveHelper = systemInjector.mScreenInteractiveHelper;
        this.mLocationUsageLogger = systemInjector.mLocationUsageLogger;
        systemSettingsHelper.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        ContentResolver contentResolver = systemSettingsHelper.mContext.getContentResolver();
        try {
            float floatForUser =
                    Settings.Secure.getFloatForUser(
                            contentResolver,
                            "locationCoarseAccuracy",
                            2000.0f,
                            contentResolver.getUserId());
            Binder.restoreCallingIdentity(clearCallingIdentity);
            this.mLocationFudger =
                    new LocationFudger(
                            floatForUser, SystemClock.elapsedRealtimeClock(), new SecureRandom());
            this.mEmergencyHelper = systemInjector.getEmergencyHelper();
            this.mPackageResetHelper = systemInjector.mPackageResetHelper;
            MockableLocationProvider mockableLocationProvider =
                    new MockableLocationProvider(this.mMultiplexerLock);
            this.mProvider = mockableLocationProvider;
            mockableLocationProvider.mController.setListener(this);
            this.mNSLocationProviderHelper = systemInjector.mNSLocationProviderHelper;
            this.mNSConnectionHelper = systemInjector.mNSConnectionHelper;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void addEnabledListener(
            LocationManagerInternal.ProviderEnabledListener providerEnabledListener) {
        synchronized (this.mMultiplexerLock) {
            Preconditions.checkState(this.mState != 2);
            this.mEnabledListeners.add(providerEnabledListener);
        }
    }

    public long calculateRequestDelayMillis(long j, Collection collection) {
        Iterator it = ((ArrayList) collection).iterator();
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
                                registration.mIdentity.getUserId(),
                                registration.getPermissionLevel(),
                                false,
                                request.getIntervalMillis());
            }
            if (lastDeliveredLocation != null) {
                j2 =
                        Math.max(
                                0L,
                                request.getIntervalMillis()
                                        - lastDeliveredLocation.getElapsedRealtimeAgeMillis());
            }
            j = Math.min(j, j2);
        }
        return j;
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
                                            new LocationProviderManager$$ExternalSyntheticLambda12(
                                                    0))
                                    .findFirst()
                                    .orElse(null);
        }
        NSLocationProviderHelper nSLocationProviderHelper = this.mNSLocationProviderHelper;
        nSLocationProviderHelper.getClass();
        Bundle bundle = new Bundle();
        if (z2 && registration != null) {
            if (registration.mIdentity.getUid() == 1000) {
                bundle.putSerializable("throwable", new Throwable("stack dump"));
            } else {
                bundle.putString("packageName", registration.mIdentity.getPackageName());
            }
        }
        bundle.putString("provider", this.mName);
        bundle.putBoolean("ignored", z2);
        nSLocationProviderHelper.mNSConnectionHelper.onStateUpdated(
                LocationConstants.STATE_TYPE.SETTINGS_IGNORED_STATE_CHANGED, bundle);
    }

    public final void dump(
            FileDescriptor fileDescriptor,
            IndentingPrintWriter indentingPrintWriter,
            String[] strArr) {
        synchronized (this.mMultiplexerLock) {
            try {
                indentingPrintWriter.print(this.mName);
                indentingPrintWriter.print(" provider");
                if (this.mProvider.isMock()) {
                    indentingPrintWriter.print(" [mock]");
                }
                indentingPrintWriter.println(":");
                indentingPrintWriter.increaseIndent();
                dump(fileDescriptor, (PrintWriter) indentingPrintWriter, strArr);
                int[] runningUserIds = this.mUserHelper.getRunningUserIds();
                for (int i : runningUserIds) {
                    if (runningUserIds.length != 1) {
                        indentingPrintWriter.print("user ");
                        indentingPrintWriter.print(i);
                        indentingPrintWriter.println(":");
                        indentingPrintWriter.increaseIndent();
                    }
                    indentingPrintWriter.print("last location=");
                    indentingPrintWriter.println(
                            getLastLocationUnsafe(i, 2, false, Long.MAX_VALUE));
                    indentingPrintWriter.print("enabled=");
                    indentingPrintWriter.println(isEnabled(i));
                    if (runningUserIds.length != 1) {
                        indentingPrintWriter.decreaseIndent();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        this.mProvider.dump(fileDescriptor, indentingPrintWriter, strArr);
        indentingPrintWriter.decreaseIndent();
    }

    public final Location getLastLocationUnsafe(int i, int i2, boolean z, long j) {
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
            if (lastLocation == null) {
                location = null;
            } else if (i2 == 1) {
                location = z ? lastLocation.mCoarseBypassLocation : lastLocation.mCoarseLocation;
            } else {
                if (i2 != 2) {
                    throw new AssertionError();
                }
                location = z ? lastLocation.mFineBypassLocation : lastLocation.mFineLocation;
            }
        }
        if (location != null && location.getElapsedRealtimeAgeMillis() <= j) {
            return location;
        }
        return null;
    }

    public final LocationResult getPermittedLocationResult(LocationResult locationResult, int i) {
        LocationResult locationResult2;
        if (i != 1) {
            if (i == 2) {
                return locationResult;
            }
            throw new AssertionError();
        }
        if (locationResult == null) {
            return null;
        }
        final LocationFudger locationFudger = this.mLocationFudger;
        synchronized (locationFudger) {
            if (locationResult != locationFudger.mCachedFineLocationResult
                    && locationResult != locationFudger.mCachedCoarseLocationResult) {
                locationResult2 =
                        locationResult.map(
                                new Function() { // from class:
                                                 // com.android.server.location.fudger.LocationFudger$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        return LocationFudger.this.createCoarse((Location) obj);
                                    }
                                });
                synchronized (locationFudger) {
                    locationFudger.mCachedFineLocationResult = locationResult;
                    locationFudger.mCachedCoarseLocationResult = locationResult2;
                }
            }
            locationResult2 = locationFudger.mCachedCoarseLocationResult;
        }
        return locationResult2;
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public String getServiceState() {
        ProviderRequest providerRequest;
        MockableLocationProvider mockableLocationProvider = this.mProvider;
        synchronized (mockableLocationProvider.mOwnerLock) {
            providerRequest = mockableLocationProvider.mRequest;
        }
        return providerRequest.toString();
    }

    /* JADX WARN: Code restructure failed: missing block: B:50:0x0035, code lost:

       if (r0 != 4) goto L34;
    */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x003c, code lost:

       if (r10.isForeground() == false) goto L4;
    */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x0045, code lost:

       if ("gps".equals(r9.mName) == false) goto L34;
    */
    /* JADX WARN: Removed duplicated region for block: B:40:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:6:0x0089  */
    @Override // com.android.server.location.listeners.ListenerMultiplexer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isActive(
            com.android.server.location.listeners.RemovableListenerRegistration r10) {
        /*
            Method dump skipped, instructions count: 258
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.location.provider.LocationProviderManager.isActive(com.android.server.location.listeners.RemovableListenerRegistration):boolean");
    }

    public final boolean isActive(boolean z, CallerIdentity callerIdentity) {
        boolean isSystemServer = callerIdentity.isSystemServer();
        LocationManagerService.Lifecycle.LifecycleUserInfoHelper lifecycleUserInfoHelper =
                this.mUserHelper;
        if (isSystemServer) {
            return z || isEnabled(lifecycleUserInfoHelper.getCurrentUserId());
        }
        if (z
                || (isEnabled(callerIdentity.getUserId())
                        && lifecycleUserInfoHelper.isVisibleUserId(callerIdentity.getUserId()))) {
            return !this.mSettingsHelper.isLocationPackageBlacklisted(
                    callerIdentity.getUserId(), callerIdentity.getPackageName());
        }
        return false;
    }

    public final boolean isEnabled(int i) {
        boolean valueAt;
        if (i == -10000) {
            return false;
        }
        if (i == -2) {
            return isEnabled(this.mUserHelper.getCurrentUserId());
        }
        Preconditions.checkArgument(i >= 0);
        synchronized (this.mMultiplexerLock) {
            try {
                int indexOfKey = this.mEnabled.indexOfKey(i);
                if (indexOfKey < 0) {
                    Log.w(
                            "LocationManagerService",
                            this.mName + " provider saw user " + i + " unexpectedly");
                    onEnabledChanged(i);
                    indexOfKey = this.mEnabled.indexOfKey(i);
                }
                valueAt = this.mEnabled.valueAt(indexOfKey);
            } catch (Throwable th) {
                throw th;
            }
        }
        return valueAt;
    }

    public final boolean isVisibleToCaller() {
        if (Binder.getCallingUid() == 1000 || this.mProvider.isMock()) {
            return true;
        }
        Iterator it = this.mRequiredPermissions.iterator();
        while (it.hasNext()) {
            if (this.mContext.checkCallingOrSelfPermission((String) it.next()) != 0) {
                return false;
            }
        }
        return true;
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public ProviderRequest mergeRegistrations(Collection collection) {
        long j;
        ArrayList arrayList = (ArrayList) collection;
        Iterator it = arrayList.iterator();
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
        Iterator it2 = arrayList.iterator();
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
                        && ((AbstractLocationProvider.InternalState)
                                        this.mProvider.mInternalState.get())
                                .state
                                .allowed
                        && this.mSettingsHelper.isLocationEnabled(i);
        int indexOfKey = this.mEnabled.indexOfKey(i);
        Boolean valueOf =
                indexOfKey < 0 ? null : Boolean.valueOf(this.mEnabled.valueAt(indexOfKey));
        if (valueOf == null || valueOf.booleanValue() != z) {
            this.mEnabled.put(i, z);
            String str = this.mName;
            if (valueOf != null || z) {
                RCPManagerService$$ExternalSyntheticOutline0.m(
                        "LocationManagerService",
                        DirEncryptService$$ExternalSyntheticOutline0.m(
                                i, "[u", "] ", str, " provider enabled = "),
                        z);
                LocationEventLog locationEventLog = LocationEventLog.EVENT_LOG;
                locationEventLog.getClass();
                locationEventLog.addLog$1(new LocationEventLog.ProviderEnabledEvent(i, str, z));
            }
            if (!z && (lastLocation = (LastLocation) this.mLastLocations.get(i)) != null) {
                lastLocation.mFineLocation = null;
                lastLocation.mCoarseLocation = null;
            }
            if (valueOf != null) {
                if (!"passive".equals(str)) {
                    this.mContext.sendBroadcastAsUser(
                            new Intent("android.location.PROVIDERS_CHANGED")
                                    .putExtra("android.location.extra.PROVIDER_NAME", str)
                                    .putExtra("android.location.extra.PROVIDER_ENABLED", z)
                                    .addFlags(1073741824)
                                    .addFlags(268435456),
                            UserHandle.of(i));
                }
                if (!this.mEnabledListeners.isEmpty()) {
                    final LocationManagerInternal.ProviderEnabledListener[]
                            providerEnabledListenerArr =
                                    (LocationManagerInternal.ProviderEnabledListener[])
                                            this.mEnabledListeners.toArray(
                                                    new LocationManagerInternal
                                                                    .ProviderEnabledListener[0]);
                    LocationServiceThread.getHandler()
                            .post(
                                    new Runnable() { // from class:
                                                     // com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda18
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            LocationProviderManager locationProviderManager =
                                                    LocationProviderManager.this;
                                            LocationManagerInternal.ProviderEnabledListener[]
                                                    providerEnabledListenerArr2 =
                                                            providerEnabledListenerArr;
                                            int i3 = i;
                                            boolean z2 = z;
                                            locationProviderManager.getClass();
                                            for (LocationManagerInternal.ProviderEnabledListener
                                                    providerEnabledListener :
                                                            providerEnabledListenerArr2) {
                                                providerEnabledListener.onProviderEnabledChanged(
                                                        locationProviderManager.mName, i3, z2);
                                            }
                                        }
                                    });
                }
            }
            updateRegistrations(new LocationProviderManager$$ExternalSyntheticLambda19(i, 0));
        }
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public final void onRegister() {
        SystemSettingsHelper systemSettingsHelper = this.mSettingsHelper;
        systemSettingsHelper.mBackgroundThrottleIntervalMs.addListener(
                this.mBackgroundThrottleIntervalChangedListener);
        systemSettingsHelper.mBackgroundThrottlePackageWhitelist.addListener(
                this.mBackgroundThrottlePackageWhitelistChangedListener);
        SystemSettingsHelper.StringListCachedSecureSetting stringListCachedSecureSetting =
                systemSettingsHelper.mLocationPackageBlacklist;
        LocationProviderManager$$ExternalSyntheticLambda4
                locationProviderManager$$ExternalSyntheticLambda4 =
                        this.mLocationPackageBlacklistChangedListener;
        stringListCachedSecureSetting.addListener(
                locationProviderManager$$ExternalSyntheticLambda4);
        systemSettingsHelper.mLocationPackageWhitelist.addListener(
                locationProviderManager$$ExternalSyntheticLambda4);
        systemSettingsHelper.mAdasPackageAllowlist.mListeners.add(
                this.mAdasPackageAllowlistChangedListener);
        systemSettingsHelper.mIgnoreSettingsPackageAllowlist.mListeners.add(
                this.mIgnoreSettingsPackageWhitelistChangedListener);
        this.mLocationPermissionsHelper.mListeners.add(this.mLocationPermissionsListener);
        this.mAppForegroundHelper.mListeners.add(this.mAppForegroundChangedListener);
        this.mLocationPowerSaveModeHelper.mListeners.add(
                this.mLocationPowerSaveModeChangedListener);
        this.mScreenInteractiveHelper.mListeners.add(this.mScreenInteractiveChangedListener);
        if (Flags.enableLocationBypass()) {
            this.mEmergencyHelper.mListeners.add(this.mEmergencyStateChangedListener);
        }
        this.mPackageResetHelper.register(this.mPackageResetResponder);
        this.mNSLocationProviderHelper.mMotionPowerListeners.add(
                this.mMotionPowerSaveModeChangedListener);
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public final void onRegistrationAdded(
            Object obj, RemovableListenerRegistration removableListenerRegistration) {
        Registration registration = (Registration) removableListenerRegistration;
        this.mLocationUsageLogger.logLocationApiUsage(
                0,
                1,
                registration.mIdentity.getPackageName(),
                registration.mIdentity.getAttributionTag(),
                this.mName,
                registration.getRequest(),
                obj instanceof PendingIntent,
                obj instanceof IBinder,
                null,
                registration.isForeground());
        NSLocationProviderHelper nSLocationProviderHelper = this.mNSLocationProviderHelper;
        if (nSLocationProviderHelper != null) {
            CallerIdentity callerIdentity = registration.mIdentity;
            int uid = callerIdentity.getUid();
            DirEncryptService$$ExternalSyntheticOutline0.m(
                    uid, "updateRequestInfo, uid=", "NSLocationProviderHelper");
            String str = registration.mListenerId;
            Map map = nSLocationProviderHelper.mRegistrationMap;
            NSLocationProviderHelper$$ExternalSyntheticLambda0
                    nSLocationProviderHelper$$ExternalSyntheticLambda0 =
                            new NSLocationProviderHelper$$ExternalSyntheticLambda0();
            String str2 = this.mName;
            ((Set)
                            ((ConcurrentHashMap) map)
                                    .computeIfAbsent(
                                            str2,
                                            nSLocationProviderHelper$$ExternalSyntheticLambda0))
                    .add(registration);
            Throwable th =
                    (uid != 1000 || "passive".equals(str2)) ? null : new Throwable("stack dump");
            LocationRequest request = registration.getRequest();
            Bundle bundle = new Bundle();
            bundle.putString("packageName", callerIdentity.getPackageName());
            bundle.putString("provider", str2);
            bundle.putString("listenerid", str);
            bundle.putLong("interval", request.getIntervalMillis());
            bundle.putLong("minUpdateInterval", request.getMinUpdateIntervalMillis());
            bundle.putInt("quality", request.getQuality());
            bundle.putInt("pid", callerIdentity.getPid());
            bundle.putInt("uid", uid);
            bundle.putBoolean("foreground", registration.isForeground());
            bundle.putSerializable("throwable", th);
            bundle.putBoolean("listenerType", registration.isListenerType);
            bundle.putBoolean("isAllowed", registration.isPermitted());
            bundle.putFloat("smallestDisplacement", request.getMinUpdateDistanceMeters());
            bundle.putInt("numUpdates", request.getMaxUpdates());
            bundle.putInt("permissionLevel", registration.getPermissionLevel());
            bundle.putString("attributionTag", callerIdentity.getAttributionTag());
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
                    Log.w(
                            "NSLocationProviderHelper",
                            "Failed to put worksource[" + workSource + "]");
                }
            }
            nSLocationProviderHelper.updateUidProcState(uid, bundle);
            nSLocationProviderHelper.mNSConnectionHelper.onStateUpdated(
                    LocationConstants.STATE_TYPE.LOCATION_REQUEST, bundle);
        }
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public final void onRegistrationRemoved(
            Object obj, RemovableListenerRegistration removableListenerRegistration) {
        Registration registration = (Registration) removableListenerRegistration;
        boolean isForeground = registration.isForeground();
        this.mLocationUsageLogger.logLocationApiUsage(
                1,
                1,
                registration.mIdentity.getPackageName(),
                registration.mIdentity.getAttributionTag(),
                this.mName,
                registration.getRequest(),
                obj instanceof PendingIntent,
                obj instanceof IBinder,
                null,
                isForeground);
        NSLocationProviderHelper nSLocationProviderHelper = this.mNSLocationProviderHelper;
        if (nSLocationProviderHelper != null) {
            CallerIdentity callerIdentity = registration.mIdentity;
            int uid = callerIdentity.getUid();
            DirEncryptService$$ExternalSyntheticOutline0.m(
                    uid, "updateRemoveInfo, uid=", "NSLocationProviderHelper");
            ConcurrentHashMap concurrentHashMap =
                    (ConcurrentHashMap) nSLocationProviderHelper.mRegistrationMap;
            String str = this.mName;
            if (!concurrentHashMap.containsKey(str)) {
                AudioDeviceInventory$$ExternalSyntheticOutline0.m(
                        "updateRemoveInfo, Registration map does not contains key ",
                        str,
                        ". So return",
                        "NSLocationProviderHelper");
                return;
            }
            Set set =
                    (Set) ((ConcurrentHashMap) nSLocationProviderHelper.mRegistrationMap).get(str);
            if (set == null || set.isEmpty()) {
                AudioDeviceInventory$$ExternalSyntheticOutline0.m(
                        "updateRemoveInfo, Registration ",
                        str,
                        " set is null or empty. So return",
                        "NSLocationProviderHelper");
                return;
            }
            set.remove(registration);
            Bundle bundle = new Bundle();
            bundle.putString("packageName", callerIdentity.getPackageName());
            bundle.putString("listenerid", registration.mListenerId);
            bundle.putInt("pid", callerIdentity.getPid());
            bundle.putInt("uid", uid);
            nSLocationProviderHelper.updateUidProcState(uid, bundle);
            nSLocationProviderHelper.mNSConnectionHelper.onStateUpdated(
                    LocationConstants.STATE_TYPE.LOCATION_REMOVE, bundle);
        }
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public final void onRegistrationReplaced(
            Object obj,
            RemovableListenerRegistration removableListenerRegistration,
            Object obj2,
            RemovableListenerRegistration removableListenerRegistration2) {
        Registration registration = (Registration) removableListenerRegistration;
        Registration registration2 = (Registration) removableListenerRegistration2;
        registration2.mLastLocation = registration.getLastDeliveredLocation();
        super.onRegistrationReplaced(obj, registration, obj2, registration2);
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider.Listener
    public final void onReportLocation(LocationResult locationResult) {
        final LocationResult locationResult2;
        Handler handler;
        AbstractLocationProvider abstractLocationProvider;
        Location lastLocationUnsafe;
        boolean z = true;
        if (this.mPassiveManager != null) {
            try {
                locationResult.validate();
                if (DeviceConfig.getBoolean(
                        "location", "enable_location_provider_manager_msl", true)) {
                    final int i = 1;
                    locationResult2 =
                            locationResult.map(
                                    new Function() { // from class:
                                        // com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda14
                                        @Override // java.util.function.Function
                                        public final Object apply(Object obj) {
                                            int i2 = i;
                                            Object obj2 = this;
                                            switch (i2) {
                                                case 0:
                                                    return ((LocationProviderManager.Registration)
                                                                    obj)
                                                            .acceptLocationChange(
                                                                    (LocationResult) obj2);
                                                default:
                                                    LocationProviderManager
                                                            locationProviderManager =
                                                                    (LocationProviderManager) obj2;
                                                    Location location = (Location) obj;
                                                    locationProviderManager.getClass();
                                                    if (location.hasMslAltitude()
                                                            || !location.hasAltitude()) {
                                                        return location;
                                                    }
                                                    try {
                                                        Location location2 = new Location(location);
                                                        if (locationProviderManager
                                                                .mAltitudeConverter
                                                                .tryAddMslAltitudeToLocation(
                                                                        location2)) {
                                                            return location2;
                                                        }
                                                        if (!locationProviderManager
                                                                .mIsAltitudeConverterIdle) {
                                                            return location;
                                                        }
                                                        locationProviderManager
                                                                        .mIsAltitudeConverterIdle =
                                                                false;
                                                        IoThread.getExecutor()
                                                                .execute(
                                                                        new LocationProviderManager$$ExternalSyntheticLambda16(
                                                                                locationProviderManager,
                                                                                location2));
                                                        return location;
                                                    } catch (IllegalArgumentException e) {
                                                        Log.e(
                                                                "LocationManagerService",
                                                                "not adding MSL altitude to"
                                                                    + " location: "
                                                                        + e);
                                                        return location;
                                                    }
                                            }
                                        }
                                    });
                } else {
                    locationResult2 = locationResult;
                }
            } catch (LocationResult.BadLocationException e) {
                Log.e("LocationManagerService", "Dropping invalid locations: " + e);
                locationResult2 = null;
            }
            if (locationResult2 == null) {
                return;
            }
            LocationEventLog locationEventLog = LocationEventLog.EVENT_LOG;
            String str = this.mName;
            int size = locationResult2.size();
            synchronized (locationEventLog) {
                LocationEventLog.LocationsEventLog locationsEventLog =
                        locationEventLog.mLocationsLog;
                locationsEventLog.getClass();
                locationsEventLog.addLog(
                        SystemClock.elapsedRealtime(),
                        new LocationEventLog.ProviderReceiveLocationEvent(str, size));
            }
            NSConnectionHelper nSConnectionHelper = this.mNSConnectionHelper;
            Location lastLocation = locationResult2.getLastLocation();
            if (nSConnectionHelper.mHasNsflpFeature
                    && (handler = nSConnectionHelper.mHandler) != null
                    && lastLocation != null) {
                handler.post(
                        new NSConnectionHelper$$ExternalSyntheticLambda0(
                                nSConnectionHelper, lastLocation));
            }
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
        setLastLocation(-1, locationResult2.getLastLocation());
        final int i2 = 0;
        deliverToListeners(
                new Function() { // from class:
                                 // com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda14
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        int i22 = i2;
                        Object obj2 = locationResult2;
                        switch (i22) {
                            case 0:
                                return ((LocationProviderManager.Registration) obj)
                                        .acceptLocationChange((LocationResult) obj2);
                            default:
                                LocationProviderManager locationProviderManager =
                                        (LocationProviderManager) obj2;
                                Location location = (Location) obj;
                                locationProviderManager.getClass();
                                if (location.hasMslAltitude() || !location.hasAltitude()) {
                                    return location;
                                }
                                try {
                                    Location location2 = new Location(location);
                                    if (locationProviderManager.mAltitudeConverter
                                            .tryAddMslAltitudeToLocation(location2)) {
                                        return location2;
                                    }
                                    if (!locationProviderManager.mIsAltitudeConverterIdle) {
                                        return location;
                                    }
                                    locationProviderManager.mIsAltitudeConverterIdle = false;
                                    IoThread.getExecutor()
                                            .execute(
                                                    new LocationProviderManager$$ExternalSyntheticLambda16(
                                                            locationProviderManager, location2));
                                    return location;
                                } catch (IllegalArgumentException e2) {
                                    Log.e(
                                            "LocationManagerService",
                                            "not adding MSL altitude to location: " + e2);
                                    return location;
                                }
                        }
                    }
                });
        PassiveLocationProviderManager passiveLocationProviderManager = this.mPassiveManager;
        if (passiveLocationProviderManager != null) {
            synchronized (passiveLocationProviderManager.mMultiplexerLock) {
                try {
                    MockableLocationProvider mockableLocationProvider =
                            passiveLocationProviderManager.mProvider;
                    synchronized (mockableLocationProvider.mOwnerLock) {
                        abstractLocationProvider = mockableLocationProvider.mProvider;
                    }
                    PassiveLocationProvider passiveLocationProvider =
                            (PassiveLocationProvider) abstractLocationProvider;
                    if (passiveLocationProvider == null) {
                        z = false;
                    }
                    Preconditions.checkState(z);
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        passiveLocationProvider.reportLocation(locationResult2);
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } finally {
                }
            }
        }
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider.Listener
    public final void onStateChanged(
            final AbstractLocationProvider.State state,
            final AbstractLocationProvider.State state2) {
        if (state.allowed != state2.allowed) {
            onEnabledChanged(-1);
        }
        if (!Objects.equals(state.properties, state2.properties)) {
            updateRegistrations(new LocationProviderManager$$ExternalSyntheticLambda12(3));
        }
        final StateChangedListener stateChangedListener = this.mStateChangedListener;
        if (stateChangedListener != null) {
            LocationServiceThread.getExecutor()
                    .execute(
                            new Runnable() { // from class:
                                             // com.android.server.location.provider.LocationProviderManager$$ExternalSyntheticLambda28
                                @Override // java.lang.Runnable
                                public final void run() {
                                    LocationProviderManager locationProviderManager =
                                            LocationProviderManager.this;
                                    LocationProviderManager.StateChangedListener
                                            stateChangedListener2 = stateChangedListener;
                                    AbstractLocationProvider.State state3 = state;
                                    AbstractLocationProvider.State state4 = state2;
                                    locationProviderManager.getClass();
                                    LocationManagerService locationManagerService =
                                            (LocationManagerService) stateChangedListener2;
                                    locationManagerService.getClass();
                                    if (!Objects.equals(state3.identity, state4.identity)) {
                                        locationManagerService.refreshAppOpsRestrictions(-1);
                                    }
                                    if (state3.extraAttributionTags.equals(
                                                    state4.extraAttributionTags)
                                            && Objects.equals(state3.identity, state4.identity)) {
                                        return;
                                    }
                                    synchronized (locationManagerService.mLock) {
                                        try {
                                            LocationManagerInternal.LocationPackageTagsListener
                                                    locationPackageTagsListener =
                                                            locationManagerService
                                                                    .mLocationTagsChangedListener;
                                            if (locationPackageTagsListener != null) {
                                                CallerIdentity callerIdentity = state3.identity;
                                                int uid =
                                                        callerIdentity != null
                                                                ? callerIdentity.getUid()
                                                                : -1;
                                                CallerIdentity callerIdentity2 = state4.identity;
                                                int uid2 =
                                                        callerIdentity2 != null
                                                                ? callerIdentity2.getUid()
                                                                : -1;
                                                if (uid != -1) {
                                                    locationManagerService.mHandler.post(
                                                            new LocationManagerService$$ExternalSyntheticLambda12(
                                                                    locationPackageTagsListener,
                                                                    uid,
                                                                    locationManagerService
                                                                            .calculateAppOpsLocationSourceTags(
                                                                                    uid),
                                                                    0));
                                                }
                                                if (uid2 != -1 && uid2 != uid) {
                                                    locationManagerService.mHandler.post(
                                                            new LocationManagerService$$ExternalSyntheticLambda12(
                                                                    locationPackageTagsListener,
                                                                    uid2,
                                                                    locationManagerService
                                                                            .calculateAppOpsLocationSourceTags(
                                                                                    uid2),
                                                                    1));
                                                }
                                            }
                                        } catch (Throwable th) {
                                            throw th;
                                        }
                                    }
                                }
                            });
        }
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public final void onTransferUnregisteredRegistration(
            RemovableListenerRegistration removableListenerRegistration) {
        Registration registration = (Registration) removableListenerRegistration;
        if ("gps".equals(this.mName)) {
            this.mActiveOriginalRegistrations.remove(registration);
            if (registration.mActiveMotionControl) {
                registration.mActiveMotionControl = false;
            }
            if (this.mInactiveMotionRegistrations.remove(registration)
                    && this.mInactiveMotionRegistrations.isEmpty()) {
                this.mNSLocationProviderHelper.onAvailableMotionStop(true);
            }
        }
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public final void onUnregister() {
        SystemSettingsHelper systemSettingsHelper = this.mSettingsHelper;
        systemSettingsHelper.mBackgroundThrottleIntervalMs.removeListener(
                this.mBackgroundThrottleIntervalChangedListener);
        systemSettingsHelper.mBackgroundThrottlePackageWhitelist.removeListener(
                this.mBackgroundThrottlePackageWhitelistChangedListener);
        SystemSettingsHelper.StringListCachedSecureSetting stringListCachedSecureSetting =
                systemSettingsHelper.mLocationPackageBlacklist;
        LocationProviderManager$$ExternalSyntheticLambda4
                locationProviderManager$$ExternalSyntheticLambda4 =
                        this.mLocationPackageBlacklistChangedListener;
        stringListCachedSecureSetting.removeListener(
                locationProviderManager$$ExternalSyntheticLambda4);
        systemSettingsHelper.mLocationPackageWhitelist.removeListener(
                locationProviderManager$$ExternalSyntheticLambda4);
        systemSettingsHelper.mAdasPackageAllowlist.mListeners.remove(
                this.mAdasPackageAllowlistChangedListener);
        systemSettingsHelper.mIgnoreSettingsPackageAllowlist.mListeners.remove(
                this.mIgnoreSettingsPackageWhitelistChangedListener);
        this.mLocationPermissionsHelper.mListeners.remove(this.mLocationPermissionsListener);
        this.mAppForegroundHelper.mListeners.remove(this.mAppForegroundChangedListener);
        this.mLocationPowerSaveModeHelper.mListeners.remove(
                this.mLocationPowerSaveModeChangedListener);
        this.mScreenInteractiveHelper.mListeners.remove(this.mScreenInteractiveChangedListener);
        if (Flags.enableLocationBypass()) {
            this.mEmergencyHelper.mListeners.remove(this.mEmergencyStateChangedListener);
        }
        this.mPackageResetHelper.unregister(this.mPackageResetResponder);
        this.mNSLocationProviderHelper.mMotionPowerListeners.remove(
                this.mMotionPowerSaveModeChangedListener);
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

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public final boolean registerWithService(Collection collection, Object obj) {
        ProviderRequest providerRequest = (ProviderRequest) obj;
        if (!providerRequest.isActive()) {
            return true;
        }
        reregisterWithService(ProviderRequest.EMPTY_REQUEST, providerRequest, collection);
        return true;
    }

    public final void removeEnabledListener(
            LocationManagerInternal.ProviderEnabledListener providerEnabledListener) {
        synchronized (this.mMultiplexerLock) {
            Preconditions.checkState(this.mState != 2);
            this.mEnabledListeners.remove(providerEnabledListener);
        }
    }

    public final void reregisterWithService(
            ProviderRequest providerRequest,
            final ProviderRequest providerRequest2,
            Collection collection) {
        long calculateRequestDelayMillis =
                ((providerRequest.isBypass() || !providerRequest2.isBypass())
                                && providerRequest2.getIntervalMillis()
                                        <= providerRequest.getIntervalMillis())
                        ? calculateRequestDelayMillis(
                                providerRequest2.getIntervalMillis(), collection)
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
            SystemAlarmHelper systemAlarmHelper = this.mAlarmHelper;
            if (onAlarmListener != null) {
                systemAlarmHelper.cancel(onAlarmListener);
                this.mDelayedRegister = null;
            }
            AlarmManager.OnAlarmListener onAlarmListener2 =
                    new AlarmManager
                            .OnAlarmListener() { // from class:
                                                 // com.android.server.location.provider.LocationProviderManager.5
                        @Override // android.app.AlarmManager.OnAlarmListener
                        public final void onAlarm() {
                            synchronized (LocationProviderManager.this.mMultiplexerLock) {
                                try {
                                    LocationProviderManager locationProviderManager =
                                            LocationProviderManager.this;
                                    if (locationProviderManager.mDelayedRegister == this) {
                                        locationProviderManager.mDelayedRegister = null;
                                        locationProviderManager.setProviderRequest(
                                                providerRequest2);
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        }
                    };
            this.mDelayedRegister = onAlarmListener2;
            systemAlarmHelper.setDelayedAlarm(calculateRequestDelayMillis, onAlarmListener2);
        }
        checkSettingsIgnoredChanged(
                providerRequest.isLocationSettingsIgnored(),
                providerRequest2.isLocationSettingsIgnored(),
                collection);
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public final /* bridge */ /* synthetic */ boolean reregisterWithService(
            Object obj, Object obj2, Collection collection) {
        reregisterWithService((ProviderRequest) obj, (ProviderRequest) obj2, collection);
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x0068 A[Catch: all -> 0x0041, TryCatch #0 {all -> 0x0041, blocks: (B:19:0x002c, B:21:0x0036, B:22:0x0044, B:24:0x004d, B:27:0x0060, B:30:0x0076, B:31:0x0068, B:34:0x0053, B:37:0x0078, B:40:0x008b, B:43:0x00a1, B:44:0x00a3, B:47:0x0092, B:51:0x007e), top: B:18:0x002c }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setLastLocation(int r9, android.location.Location r10) {
        /*
            r8 = this;
            r0 = -1
            r1 = 0
            if (r9 != r0) goto L16
            com.android.server.location.LocationManagerService$Lifecycle$LifecycleUserInfoHelper r9 = r8.mUserHelper
            int[] r9 = r9.getRunningUserIds()
        La:
            int r0 = r9.length
            if (r1 >= r0) goto L15
            r0 = r9[r1]
            r8.setLastLocation(r0, r10)
            int r1 = r1 + 1
            goto La
        L15:
            return
        L16:
            r0 = -2
            if (r9 != r0) goto L23
            com.android.server.location.LocationManagerService$Lifecycle$LifecycleUserInfoHelper r9 = r8.mUserHelper
            int r9 = r9.getCurrentUserId()
            r8.setLastLocation(r9, r10)
            return
        L23:
            if (r9 < 0) goto L26
            r1 = 1
        L26:
            com.android.internal.util.Preconditions.checkArgument(r1)
            java.lang.Object r0 = r8.mMultiplexerLock
            monitor-enter(r0)
            android.util.SparseArray r1 = r8.mLastLocations     // Catch: java.lang.Throwable -> L41
            java.lang.Object r1 = r1.get(r9)     // Catch: java.lang.Throwable -> L41
            com.android.server.location.provider.LocationProviderManager$LastLocation r1 = (com.android.server.location.provider.LocationProviderManager.LastLocation) r1     // Catch: java.lang.Throwable -> L41
            if (r1 != 0) goto L44
            com.android.server.location.provider.LocationProviderManager$LastLocation r1 = new com.android.server.location.provider.LocationProviderManager$LastLocation     // Catch: java.lang.Throwable -> L41
            r1.<init>()     // Catch: java.lang.Throwable -> L41
            android.util.SparseArray r2 = r8.mLastLocations     // Catch: java.lang.Throwable -> L41
            r2.put(r9, r1)     // Catch: java.lang.Throwable -> L41
            goto L44
        L41:
            r8 = move-exception
            goto La5
        L44:
            boolean r8 = r8.isEnabled(r9)     // Catch: java.lang.Throwable -> L41
            r2 = 600000(0x927c0, double:2.964394E-318)
            if (r8 == 0) goto L78
            android.location.Location r8 = r1.mFineLocation     // Catch: java.lang.Throwable -> L41
            if (r8 != 0) goto L53
        L51:
            r8 = r10
            goto L60
        L53:
            long r4 = r10.getElapsedRealtimeNanos()     // Catch: java.lang.Throwable -> L41
            long r6 = r8.getElapsedRealtimeNanos()     // Catch: java.lang.Throwable -> L41
            int r9 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r9 <= 0) goto L60
            goto L51
        L60:
            r1.mFineLocation = r8     // Catch: java.lang.Throwable -> L41
            android.location.Location r8 = r1.mCoarseLocation     // Catch: java.lang.Throwable -> L41
            if (r8 != 0) goto L68
        L66:
            r8 = r10
            goto L76
        L68:
            long r4 = r10.getElapsedRealtimeMillis()     // Catch: java.lang.Throwable -> L41
            long r4 = r4 - r2
            long r6 = r8.getElapsedRealtimeMillis()     // Catch: java.lang.Throwable -> L41
            int r9 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r9 <= 0) goto L76
            goto L66
        L76:
            r1.mCoarseLocation = r8     // Catch: java.lang.Throwable -> L41
        L78:
            android.location.Location r8 = r1.mFineBypassLocation     // Catch: java.lang.Throwable -> L41
            if (r8 != 0) goto L7e
        L7c:
            r8 = r10
            goto L8b
        L7e:
            long r4 = r10.getElapsedRealtimeNanos()     // Catch: java.lang.Throwable -> L41
            long r6 = r8.getElapsedRealtimeNanos()     // Catch: java.lang.Throwable -> L41
            int r9 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r9 <= 0) goto L8b
            goto L7c
        L8b:
            r1.mFineBypassLocation = r8     // Catch: java.lang.Throwable -> L41
            android.location.Location r8 = r1.mCoarseBypassLocation     // Catch: java.lang.Throwable -> L41
            if (r8 != 0) goto L92
            goto La1
        L92:
            long r4 = r10.getElapsedRealtimeMillis()     // Catch: java.lang.Throwable -> L41
            long r4 = r4 - r2
            long r2 = r8.getElapsedRealtimeMillis()     // Catch: java.lang.Throwable -> L41
            int r9 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r9 <= 0) goto La0
            goto La1
        La0:
            r10 = r8
        La1:
            r1.mCoarseBypassLocation = r10     // Catch: java.lang.Throwable -> L41
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L41
            return
        La5:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L41
            throw r8
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.location.provider.LocationProviderManager.setLastLocation(int,"
                    + " android.location.Location):void");
    }

    public void setMockProvider(MockLocationProvider mockLocationProvider) {
        synchronized (this.mMultiplexerLock) {
            try {
                boolean z = true;
                Preconditions.checkState(this.mState != 2);
                LocationEventLog locationEventLog = LocationEventLog.EVENT_LOG;
                String str = this.mName;
                if (mockLocationProvider == null) {
                    z = false;
                }
                locationEventLog.getClass();
                locationEventLog.addLog$1(new LocationEventLog.ProviderMockedEvent(str, z));
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    MockableLocationProvider mockableLocationProvider = this.mProvider;
                    synchronized (mockableLocationProvider.mOwnerLock) {
                        try {
                            if (mockableLocationProvider.mMockProvider != mockLocationProvider) {
                                mockableLocationProvider.mMockProvider = mockLocationProvider;
                                if (mockLocationProvider != null) {
                                    mockableLocationProvider.setProviderLocked(
                                            mockLocationProvider);
                                } else {
                                    mockableLocationProvider.setProviderLocked(
                                            mockableLocationProvider.mRealProvider);
                                }
                            }
                        } finally {
                        }
                    }
                    if (mockLocationProvider == null) {
                        int size = this.mLastLocations.size();
                        for (int i = 0; i < size; i++) {
                            LastLocation lastLocation =
                                    (LastLocation) this.mLastLocations.valueAt(i);
                            Location location = lastLocation.mFineLocation;
                            if (location != null && location.isMock()) {
                                lastLocation.mFineLocation = null;
                            }
                            Location location2 = lastLocation.mCoarseLocation;
                            if (location2 != null && location2.isMock()) {
                                lastLocation.mCoarseLocation = null;
                            }
                            Location location3 = lastLocation.mFineBypassLocation;
                            if (location3 != null && location3.isMock()) {
                                lastLocation.mFineBypassLocation = null;
                            }
                            Location location4 = lastLocation.mCoarseBypassLocation;
                            if (location4 != null && location4.isMock()) {
                                lastLocation.mCoarseBypassLocation = null;
                            }
                        }
                        LocationFudger locationFudger = this.mLocationFudger;
                        locationFudger.mLatitudeOffsetM = locationFudger.nextRandomOffset();
                        locationFudger.mLongitudeOffsetM = locationFudger.nextRandomOffset();
                        locationFudger.mNextUpdateRealtimeMs =
                                locationFudger.mClock.millis()
                                        + ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS;
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } finally {
            }
        }
    }

    public final void setProviderRequest(ProviderRequest providerRequest) {
        AlarmManager.OnAlarmListener onAlarmListener = this.mDelayedRegister;
        if (onAlarmListener != null) {
            this.mAlarmHelper.cancel(onAlarmListener);
            this.mDelayedRegister = null;
        }
        LocationEventLog locationEventLog = LocationEventLog.EVENT_LOG;
        locationEventLog.getClass();
        String str = this.mName;
        locationEventLog.addLog$1(new LocationEventLog.ProviderUpdateEvent(str, providerRequest));
        Log.d("LocationManagerService", str + " provider request changed to " + providerRequest);
        this.mProvider.mController.setRequest(providerRequest);
        LocationServiceThread.getHandler()
                .post(
                        new LocationProviderManager$$ExternalSyntheticLambda16(
                                this, providerRequest));
    }

    public void setRealProvider(AbstractLocationProvider abstractLocationProvider) {
        synchronized (this.mMultiplexerLock) {
            Preconditions.checkState(this.mState != 2);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                MockableLocationProvider mockableLocationProvider = this.mProvider;
                synchronized (mockableLocationProvider.mOwnerLock) {
                    try {
                        if (mockableLocationProvider.mRealProvider != abstractLocationProvider) {
                            mockableLocationProvider.mRealProvider = abstractLocationProvider;
                            if (!mockableLocationProvider.isMock()) {
                                mockableLocationProvider.setProviderLocked(
                                        mockableLocationProvider.mRealProvider);
                            }
                        }
                    } finally {
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final void startManager(StateChangedListener stateChangedListener) {
        synchronized (this.mMultiplexerLock) {
            try {
                Preconditions.checkState(this.mState == 2);
                this.mState = 0;
                this.mStateChangedListener = stateChangedListener;
                this.mUserHelper.mListeners.add(this.mUserChangedListener);
                this.mLocationSettings.mUserSettingsListeners.add(
                        this.mLocationUserSettingsListener);
                this.mSettingsHelper.mLocationMode.addListener(
                        this.mLocationEnabledChangedListener);
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mProvider.mController.start();
                    onUserStarted(-1);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void stopManager() {
        synchronized (this.mMultiplexerLock) {
            try {
                Preconditions.checkState(this.mState == 0);
                this.mState = 1;
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    onEnabledChanged(-1);
                    removeRegistrationIf(new LocationProviderManager$$ExternalSyntheticLambda12(1));
                    this.mProvider.mController.stop();
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    this.mUserHelper.mListeners.remove(this.mUserChangedListener);
                    this.mLocationSettings.mUserSettingsListeners.remove(
                            this.mLocationUserSettingsListener);
                    this.mSettingsHelper.mLocationMode.removeListener(
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
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public final void unregisterWithService() {
        setProviderRequest(ProviderRequest.EMPTY_REQUEST);
        checkSettingsIgnoredChanged(this.mIsSettingsIgnored, false, null);
    }
}
