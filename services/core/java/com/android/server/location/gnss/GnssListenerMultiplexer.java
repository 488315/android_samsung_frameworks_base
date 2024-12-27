package com.android.server.location.gnss;

import android.location.LocationConstants;
import android.location.LocationManagerInternal;
import android.location.util.identity.CallerIdentity;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.IInterface;
import android.util.ArraySet;

import com.android.internal.util.ConcurrentUtils;
import com.android.internal.util.Preconditions;
import com.android.server.LocalServices;
import com.android.server.location.LocationManagerService;
import com.android.server.location.LocationServiceThread;
import com.android.server.location.injector.AppForegroundHelper$AppForegroundListener;
import com.android.server.location.injector.Injector;
import com.android.server.location.injector.LocationPermissionsHelper$LocationPermissionsListener;
import com.android.server.location.injector.PackageResetHelper$Responder;
import com.android.server.location.injector.SettingsHelper$GlobalSettingChangedListener;
import com.android.server.location.injector.SettingsHelper$UserSettingChangedListener;
import com.android.server.location.injector.SystemAppForegroundHelper;
import com.android.server.location.injector.SystemLocationPermissionsHelper;
import com.android.server.location.injector.SystemPackageResetHelper;
import com.android.server.location.injector.SystemSettingsHelper;
import com.android.server.location.injector.UserInfoHelper$UserListener;
import com.android.server.location.listeners.BinderListenerRegistration;
import com.android.server.location.listeners.ListenerMultiplexer;
import com.android.server.location.listeners.RemovableListenerRegistration;
import com.android.server.location.nsflp.NSLocationProviderHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;

public abstract class GnssListenerMultiplexer extends ListenerMultiplexer {
    public final SystemAppForegroundHelper mAppForegroundHelper;
    public final LocationManagerInternal mLocationManagerInternal;
    public final SystemLocationPermissionsHelper mLocationPermissionsHelper;
    public final NSLocationProviderHelper mNSLocationProviderHelper;
    public final SystemPackageResetHelper mPackageResetHelper;
    public final SystemSettingsHelper mSettingsHelper;
    public final LocationManagerService.Lifecycle.LifecycleUserInfoHelper mUserInfoHelper;
    public final GnssListenerMultiplexer$$ExternalSyntheticLambda0 mUserChangedListener =
            new UserInfoHelper$UserListener() { // from class:
                                                // com.android.server.location.gnss.GnssListenerMultiplexer$$ExternalSyntheticLambda0
                @Override // com.android.server.location.injector.UserInfoHelper$UserListener
                public final void onUserChanged(int i, int i2) {
                    GnssListenerMultiplexer gnssListenerMultiplexer = GnssListenerMultiplexer.this;
                    gnssListenerMultiplexer.getClass();
                    if (i2 == 1 || i2 == 4) {
                        gnssListenerMultiplexer.updateRegistrations(
                                new GnssListenerMultiplexer$$ExternalSyntheticLambda6(i, 2));
                    }
                }
            };
    public final GnssListenerMultiplexer$$ExternalSyntheticLambda1 mProviderEnabledChangedListener =
            new LocationManagerInternal
                    .ProviderEnabledListener() { // from class:
                                                 // com.android.server.location.gnss.GnssListenerMultiplexer$$ExternalSyntheticLambda1
                public final void onProviderEnabledChanged(String str, int i, boolean z) {
                    GnssListenerMultiplexer gnssListenerMultiplexer = GnssListenerMultiplexer.this;
                    gnssListenerMultiplexer.getClass();
                    Preconditions.checkState("gps".equals(str));
                    gnssListenerMultiplexer.updateRegistrations(
                            new GnssListenerMultiplexer$$ExternalSyntheticLambda6(i, 0));
                }
            };
    public final GnssListenerMultiplexer$$ExternalSyntheticLambda2
            mBackgroundThrottlePackageWhitelistChangedListener =
                    new SettingsHelper$GlobalSettingChangedListener() { // from class:
                                                                        // com.android.server.location.gnss.GnssListenerMultiplexer$$ExternalSyntheticLambda2
                        @Override // com.android.server.location.injector.SettingsHelper$GlobalSettingChangedListener
                        public final void onSettingChanged() {
                            GnssListenerMultiplexer gnssListenerMultiplexer =
                                    GnssListenerMultiplexer.this;
                            gnssListenerMultiplexer.getClass();
                            gnssListenerMultiplexer.updateRegistrations(
                                    new GnssListenerMultiplexer$$ExternalSyntheticLambda9());
                        }
                    };
    public final GnssListenerMultiplexer$$ExternalSyntheticLambda3
            mLocationPackageBlacklistChangedListener =
                    new SettingsHelper$UserSettingChangedListener() { // from class:
                                                                      // com.android.server.location.gnss.GnssListenerMultiplexer$$ExternalSyntheticLambda3
                        @Override // com.android.server.location.injector.SettingsHelper$UserSettingChangedListener
                        public final void onSettingChanged(int i) {
                            GnssListenerMultiplexer gnssListenerMultiplexer =
                                    GnssListenerMultiplexer.this;
                            gnssListenerMultiplexer.getClass();
                            gnssListenerMultiplexer.updateRegistrations(
                                    new GnssListenerMultiplexer$$ExternalSyntheticLambda6(i, 1));
                        }
                    };
    public final AnonymousClass1 mLocationPermissionsListener =
            new LocationPermissionsHelper$LocationPermissionsListener() { // from class:
                                                                          // com.android.server.location.gnss.GnssListenerMultiplexer.1
                @Override // com.android.server.location.injector.LocationPermissionsHelper$LocationPermissionsListener
                public final void onLocationPermissionsChanged(int i) {
                    GnssListenerMultiplexer gnssListenerMultiplexer = GnssListenerMultiplexer.this;
                    gnssListenerMultiplexer.getClass();
                    gnssListenerMultiplexer.updateRegistrations(
                            new GnssListenerMultiplexer$$ExternalSyntheticLambda6(i, 3));
                }

                @Override // com.android.server.location.injector.LocationPermissionsHelper$LocationPermissionsListener
                public final void onLocationPermissionsChanged(String str) {
                    GnssListenerMultiplexer gnssListenerMultiplexer = GnssListenerMultiplexer.this;
                    gnssListenerMultiplexer.getClass();
                    gnssListenerMultiplexer.updateRegistrations(
                            new GnssListenerMultiplexer$$ExternalSyntheticLambda5(1, str));
                }
            };
    public final GnssListenerMultiplexer$$ExternalSyntheticLambda4 mAppForegroundChangedListener =
            new AppForegroundHelper$AppForegroundListener() { // from class:
                                                              // com.android.server.location.gnss.GnssListenerMultiplexer$$ExternalSyntheticLambda4
                @Override // com.android.server.location.injector.AppForegroundHelper$AppForegroundListener
                public final void onAppForegroundChanged(final int i, final boolean z) {
                    GnssListenerMultiplexer gnssListenerMultiplexer = GnssListenerMultiplexer.this;
                    gnssListenerMultiplexer.getClass();
                    gnssListenerMultiplexer.updateRegistrations(
                            new Predicate() { // from class:
                                              // com.android.server.location.gnss.GnssListenerMultiplexer$$ExternalSyntheticLambda11
                                @Override // java.util.function.Predicate
                                public final boolean test(Object obj) {
                                    int i2 = i;
                                    boolean z2 = z;
                                    GnssListenerMultiplexer.GnssListenerRegistration
                                            gnssListenerRegistration =
                                                    (GnssListenerMultiplexer
                                                                    .GnssListenerRegistration)
                                                            obj;
                                    if (gnssListenerRegistration.mIdentity.getUid() != i2
                                            || z2 == gnssListenerRegistration.mForeground) {
                                        return false;
                                    }
                                    gnssListenerRegistration.mForeground = z2;
                                    return true;
                                }
                            });
                }
            };
    public final AnonymousClass2 mPackageResetResponder =
            new PackageResetHelper$Responder() { // from class:
                                                 // com.android.server.location.gnss.GnssListenerMultiplexer.2
                @Override // com.android.server.location.injector.PackageResetHelper$Responder
                public final boolean isResetableForPackage(String str) {
                    GnssListenerMultiplexer gnssListenerMultiplexer = GnssListenerMultiplexer.this;
                    gnssListenerMultiplexer.getClass();
                    return gnssListenerMultiplexer.findRegistration(
                            new GnssListenerMultiplexer$$ExternalSyntheticLambda5(2, str));
                }

                @Override // com.android.server.location.injector.PackageResetHelper$Responder
                public final void onPackageReset(String str) {
                    GnssListenerMultiplexer gnssListenerMultiplexer = GnssListenerMultiplexer.this;
                    gnssListenerMultiplexer.getClass();
                    gnssListenerMultiplexer.updateRegistrations(
                            new GnssListenerMultiplexer$$ExternalSyntheticLambda5(3, str));
                }
            };

    public class GnssListenerRegistration extends BinderListenerRegistration {
        public boolean mForeground;
        public final CallerIdentity mIdentity;
        public boolean mPermitted;
        public final Object mRequest;
        public final /* synthetic */ GnssListenerMultiplexer this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public GnssListenerRegistration(
                CallerIdentity callerIdentity,
                IInterface iInterface,
                GnssListenerMultiplexer gnssListenerMultiplexer,
                Object obj) {
            super(
                    callerIdentity.isMyProcess()
                            ? LocationServiceThread.getExecutor()
                            : ConcurrentUtils.DIRECT_EXECUTOR,
                    iInterface);
            this.this$0 = gnssListenerMultiplexer;
            this.mRequest = obj;
            this.mIdentity = callerIdentity;
        }

        @Override // com.android.server.location.listeners.BinderListenerRegistration
        public final IBinder getBinderFromKey(Object obj) {
            return (IBinder) obj;
        }

        @Override // com.android.server.location.listeners.RemovableListenerRegistration
        public final ListenerMultiplexer getOwner() {
            return this.this$0;
        }

        @Override // com.android.server.location.listeners.RemovableListenerRegistration
        public final String getTag() {
            return "GnssManager";
        }

        @Override // com.android.server.location.listeners.RemovableListenerRegistration
        public void onActive() {
            updateGnssListener();
        }

        @Override // com.android.server.location.listeners.RemovableListenerRegistration
        public void onInactive() {
            updateGnssListener();
        }

        public final boolean onLocationPermissionsChanged$2() {
            boolean hasLocationPermissions =
                    this.this$0.mLocationPermissionsHelper.hasLocationPermissions(
                            2, this.mIdentity);
            if (hasLocationPermissions == this.mPermitted) {
                return false;
            }
            this.mPermitted = hasLocationPermissions;
            return true;
        }

        @Override // com.android.server.location.listeners.BinderListenerRegistration,
                  // com.android.server.location.listeners.RemovableListenerRegistration
        public void onRegister() {
            super.onRegister();
            this.mPermitted =
                    this.this$0.mLocationPermissionsHelper.hasLocationPermissions(
                            2, this.mIdentity);
            this.mForeground =
                    this.this$0.mAppForegroundHelper.isAppForeground(this.mIdentity.getUid());
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.mIdentity);
            ArraySet arraySet = new ArraySet(2);
            if (!this.mForeground) {
                arraySet.add("bg");
            }
            if (!this.mPermitted) {
                arraySet.add("na");
            }
            if (!arraySet.isEmpty()) {
                sb.append(" ");
                sb.append(arraySet);
            }
            if (this.mRequest != null) {
                sb.append(" ");
                sb.append(this.mRequest);
            }
            return sb.toString();
        }

        public final void updateGnssListener() {
            IBinder iBinder;
            CallerIdentity callerIdentity = this.mIdentity;
            GnssListenerMultiplexer gnssListenerMultiplexer = this.this$0;
            try {
                Object obj = this.mKey;
                Objects.requireNonNull(obj);
                iBinder = getBinderFromKey(obj);
            } catch (IllegalArgumentException | NullPointerException unused) {
                iBinder = null;
            }
            IBinder iBinder2 = iBinder;
            boolean z = this.mActive;
            String packageName = callerIdentity.getPackageName();
            int uid = callerIdentity.getUid();
            int pid = callerIdentity.getPid();
            NSLocationProviderHelper nSLocationProviderHelper =
                    gnssListenerMultiplexer.mNSLocationProviderHelper;
            if (nSLocationProviderHelper == null) {
                return;
            }
            nSLocationProviderHelper.updateGnssDataListener(
                    iBinder2, z, packageName, gnssListenerMultiplexer.getListenerType(), uid, pid);
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.server.location.gnss.GnssListenerMultiplexer$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.server.location.gnss.GnssListenerMultiplexer$$ExternalSyntheticLambda1] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.server.location.gnss.GnssListenerMultiplexer$$ExternalSyntheticLambda2] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.server.location.gnss.GnssListenerMultiplexer$$ExternalSyntheticLambda3] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.server.location.gnss.GnssListenerMultiplexer$1] */
    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.server.location.gnss.GnssListenerMultiplexer$$ExternalSyntheticLambda4] */
    /* JADX WARN: Type inference failed for: r0v6, types: [com.android.server.location.gnss.GnssListenerMultiplexer$2] */
    public GnssListenerMultiplexer(Injector injector) {
        LocationManagerService.SystemInjector systemInjector =
                (LocationManagerService.SystemInjector) injector;
        this.mUserInfoHelper = systemInjector.mUserInfoHelper;
        this.mSettingsHelper = systemInjector.mSettingsHelper;
        this.mLocationPermissionsHelper = systemInjector.mLocationPermissionsHelper;
        this.mAppForegroundHelper = systemInjector.mAppForegroundHelper;
        this.mPackageResetHelper = systemInjector.mPackageResetHelper;
        LocationManagerInternal locationManagerInternal =
                (LocationManagerInternal) LocalServices.getService(LocationManagerInternal.class);
        Objects.requireNonNull(locationManagerInternal);
        this.mLocationManagerInternal = locationManagerInternal;
        this.mNSLocationProviderHelper = systemInjector.mNSLocationProviderHelper;
    }

    public final void addGnssDataListener(
            IBinder iBinder, GnssListenerRegistration gnssListenerRegistration) {
        NSLocationProviderHelper nSLocationProviderHelper = this.mNSLocationProviderHelper;
        if (nSLocationProviderHelper != null) {
            CallerIdentity callerIdentity = gnssListenerRegistration.mIdentity;
            nSLocationProviderHelper.addGnssDataListener(
                    iBinder,
                    callerIdentity.getPackageName(),
                    getListenerType(),
                    callerIdentity.getUid(),
                    callerIdentity.getPid(),
                    gnssListenerRegistration.mPermitted,
                    gnssListenerRegistration.mForeground);
        }
    }

    public void addListener(
            final Object obj, final CallerIdentity callerIdentity, final IInterface iInterface) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Runnable runnable =
                    new Runnable() { // from class:
                                     // com.android.server.location.gnss.GnssListenerMultiplexer.3
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.putRegistration(
                                    iInterface.asBinder(),
                                    this.createRegistration(obj, callerIdentity, iInterface));
                        }
                    };
            if (callerIdentity.getUid() == 1000
                    || getRegistrationCountWith(
                                    new GnssListenerMultiplexer$$ExternalSyntheticLambda5(
                                            0, callerIdentity))
                            < 30) {
                runnable.run();
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public GnssListenerRegistration createRegistration(
            Object obj, CallerIdentity callerIdentity, IInterface iInterface) {
        return new GnssListenerRegistration(callerIdentity, iInterface, this, obj);
    }

    public abstract LocationConstants.LISTENER_TYPE getListenerType();

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public final String getServiceState() {
        return !isSupported() ? "unsupported" : super.getServiceState();
    }

    /* JADX WARN: Removed duplicated region for block: B:11:? A[RETURN, SYNTHETIC] */
    @Override // com.android.server.location.listeners.ListenerMultiplexer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isActive(
            com.android.server.location.listeners.RemovableListenerRegistration r7) {
        /*
            r6 = this;
            com.android.server.location.gnss.GnssListenerMultiplexer$GnssListenerRegistration r7 = (com.android.server.location.gnss.GnssListenerMultiplexer.GnssListenerRegistration) r7
            boolean r0 = r6.isSupported()
            r1 = 0
            if (r0 != 0) goto Lb
            goto L8e
        Lb:
            android.location.util.identity.CallerIdentity r0 = r7.mIdentity
            boolean r2 = r7.mPermitted
            if (r2 == 0) goto L8e
            boolean r7 = r7.mForeground
            com.android.server.location.injector.SystemSettingsHelper r2 = r6.mSettingsHelper
            r3 = 1
            if (r7 != 0) goto L4a
            int r7 = r0.getUid()
            r4 = 1000(0x3e8, float:1.401E-42)
            if (r7 != r4) goto L22
        L20:
            r7 = r3
            goto L48
        L22:
            com.android.server.location.injector.SystemSettingsHelper$StringSetCachedGlobalSetting r7 = r2.mBackgroundThrottlePackageWhitelist
            java.util.Set r7 = r7.getValue()
            java.lang.String r4 = r0.getPackageName()
            android.util.ArraySet r7 = (android.util.ArraySet) r7
            boolean r7 = r7.contains(r4)
            if (r7 != 0) goto L20
            java.lang.String r7 = r0.getPackageName()
            java.util.HashSet r4 = r2.mBackgroundThrottlePackageAllowlistByNsflp
            boolean r7 = r4.contains(r7)
            if (r7 == 0) goto L41
            goto L20
        L41:
            android.location.LocationManagerInternal r7 = r6.mLocationManagerInternal
            r4 = 0
            boolean r7 = r7.isProvider(r4, r0)
        L48:
            if (r7 == 0) goto L8e
        L4a:
            boolean r7 = r0.isSystemServer()
            com.android.server.location.LocationManagerService$Lifecycle$LifecycleUserInfoHelper r4 = r6.mUserInfoHelper
            java.lang.String r5 = "gps"
            if (r7 == 0) goto L63
            android.location.LocationManagerInternal r6 = r6.mLocationManagerInternal
            int r7 = r4.getCurrentUserId()
            boolean r6 = r6.isProviderEnabledForUser(r5, r7)
            if (r6 != 0) goto L8a
        L61:
            r6 = r1
            goto L8b
        L63:
            android.location.LocationManagerInternal r6 = r6.mLocationManagerInternal
            int r7 = r0.getUserId()
            boolean r6 = r6.isProviderEnabledForUser(r5, r7)
            if (r6 != 0) goto L70
            goto L61
        L70:
            int r6 = r0.getUserId()
            boolean r6 = r4.isVisibleUserId(r6)
            if (r6 != 0) goto L7b
            goto L61
        L7b:
            int r6 = r0.getUserId()
            java.lang.String r7 = r0.getPackageName()
            boolean r6 = r2.isLocationPackageBlacklisted(r6, r7)
            if (r6 == 0) goto L8a
            goto L61
        L8a:
            r6 = r3
        L8b:
            if (r6 == 0) goto L8e
            r1 = r3
        L8e:
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.location.gnss.GnssListenerMultiplexer.isActive(com.android.server.location.listeners.RemovableListenerRegistration):boolean");
    }

    public boolean isSupported() {
        return true;
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public Object mergeRegistrations(Collection collection) {
        if (!Build.IS_DEBUGGABLE) {
            return null;
        }
        Iterator it = ((ArrayList) collection).iterator();
        while (it.hasNext()) {
            Preconditions.checkState(((GnssListenerRegistration) it.next()).mRequest == null);
        }
        return null;
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public final void onRegister() {
        if (isSupported()) {
            this.mUserInfoHelper.mListeners.add(this.mUserChangedListener);
            this.mLocationManagerInternal.addProviderEnabledListener(
                    "gps", this.mProviderEnabledChangedListener);
            SystemSettingsHelper systemSettingsHelper = this.mSettingsHelper;
            systemSettingsHelper.mBackgroundThrottlePackageWhitelist.addListener(
                    this.mBackgroundThrottlePackageWhitelistChangedListener);
            SystemSettingsHelper.StringListCachedSecureSetting stringListCachedSecureSetting =
                    systemSettingsHelper.mLocationPackageBlacklist;
            GnssListenerMultiplexer$$ExternalSyntheticLambda3
                    gnssListenerMultiplexer$$ExternalSyntheticLambda3 =
                            this.mLocationPackageBlacklistChangedListener;
            stringListCachedSecureSetting.addListener(
                    gnssListenerMultiplexer$$ExternalSyntheticLambda3);
            systemSettingsHelper.mLocationPackageWhitelist.addListener(
                    gnssListenerMultiplexer$$ExternalSyntheticLambda3);
            this.mLocationPermissionsHelper.mListeners.add(this.mLocationPermissionsListener);
            this.mAppForegroundHelper.mListeners.add(this.mAppForegroundChangedListener);
            this.mPackageResetHelper.register(this.mPackageResetResponder);
        }
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public void onRegistrationAdded(
            Object obj, RemovableListenerRegistration removableListenerRegistration) {
        addGnssDataListener(
                (IBinder) obj, (GnssListenerRegistration) removableListenerRegistration);
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public void onRegistrationRemoved(
            Object obj, RemovableListenerRegistration removableListenerRegistration) {
        removeGnssDataListener(
                (IBinder) obj, (GnssListenerRegistration) removableListenerRegistration);
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public final void onUnregister() {
        if (isSupported()) {
            this.mUserInfoHelper.mListeners.remove(this.mUserChangedListener);
            this.mLocationManagerInternal.removeProviderEnabledListener(
                    "gps", this.mProviderEnabledChangedListener);
            SystemSettingsHelper systemSettingsHelper = this.mSettingsHelper;
            systemSettingsHelper.mBackgroundThrottlePackageWhitelist.removeListener(
                    this.mBackgroundThrottlePackageWhitelistChangedListener);
            SystemSettingsHelper.StringListCachedSecureSetting stringListCachedSecureSetting =
                    systemSettingsHelper.mLocationPackageBlacklist;
            GnssListenerMultiplexer$$ExternalSyntheticLambda3
                    gnssListenerMultiplexer$$ExternalSyntheticLambda3 =
                            this.mLocationPackageBlacklistChangedListener;
            stringListCachedSecureSetting.removeListener(
                    gnssListenerMultiplexer$$ExternalSyntheticLambda3);
            systemSettingsHelper.mLocationPackageWhitelist.removeListener(
                    gnssListenerMultiplexer$$ExternalSyntheticLambda3);
            this.mLocationPermissionsHelper.mListeners.remove(this.mLocationPermissionsListener);
            this.mAppForegroundHelper.mListeners.remove(this.mAppForegroundChangedListener);
            this.mPackageResetHelper.unregister(this.mPackageResetResponder);
        }
    }

    public final void removeGnssDataListener(
            IBinder iBinder, GnssListenerRegistration gnssListenerRegistration) {
        NSLocationProviderHelper nSLocationProviderHelper = this.mNSLocationProviderHelper;
        if (nSLocationProviderHelper != null) {
            CallerIdentity callerIdentity = gnssListenerRegistration.mIdentity;
            nSLocationProviderHelper.removeGnssDataListener(
                    iBinder, getListenerType(), callerIdentity.getUid(), callerIdentity.getPid());
        }
    }

    public final void removeListener(IInterface iInterface) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            removeRegistration(iInterface.asBinder());
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
