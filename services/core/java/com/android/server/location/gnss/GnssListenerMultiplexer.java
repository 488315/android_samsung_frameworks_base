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
import com.android.server.location.LocationServiceThread;
import com.android.server.location.gnss.GnssListenerMultiplexer;
import com.android.server.location.injector.AppForegroundHelper;
import com.android.server.location.injector.Injector;
import com.android.server.location.injector.LocationPermissionsHelper;
import com.android.server.location.injector.PackageResetHelper;
import com.android.server.location.injector.SettingsHelper;
import com.android.server.location.injector.UserInfoHelper;
import com.android.server.location.listeners.BinderListenerRegistration;
import com.android.server.location.listeners.ListenerMultiplexer;
import com.android.server.location.nsflp.NSLocationProviderHelper;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;

/* loaded from: classes2.dex */
public abstract class GnssListenerMultiplexer extends ListenerMultiplexer {
    public final AppForegroundHelper mAppForegroundHelper;
    public final LocationManagerInternal mLocationManagerInternal;
    public final LocationPermissionsHelper mLocationPermissionsHelper;
    public final NSLocationProviderHelper mNSLocationProviderHelper;
    public final PackageResetHelper mPackageResetHelper;
    public final SettingsHelper mSettingsHelper;
    public final UserInfoHelper mUserInfoHelper;
    public final UserInfoHelper.UserListener mUserChangedListener = new UserInfoHelper.UserListener() { // from class: com.android.server.location.gnss.GnssListenerMultiplexer$$ExternalSyntheticLambda0
        @Override // com.android.server.location.injector.UserInfoHelper.UserListener
        public final void onUserChanged(int i, int i2) {
            GnssListenerMultiplexer.this.onUserChanged(i, i2);
        }
    };
    public final LocationManagerInternal.ProviderEnabledListener mProviderEnabledChangedListener = new LocationManagerInternal.ProviderEnabledListener() { // from class: com.android.server.location.gnss.GnssListenerMultiplexer$$ExternalSyntheticLambda1
        public final void onProviderEnabledChanged(String str, int i, boolean z) {
            GnssListenerMultiplexer.this.onProviderEnabledChanged(str, i, z);
        }
    };
    public final SettingsHelper.GlobalSettingChangedListener mBackgroundThrottlePackageWhitelistChangedListener = new SettingsHelper.GlobalSettingChangedListener() { // from class: com.android.server.location.gnss.GnssListenerMultiplexer$$ExternalSyntheticLambda2
        @Override // com.android.server.location.injector.SettingsHelper.GlobalSettingChangedListener
        public final void onSettingChanged() {
            GnssListenerMultiplexer.this.onBackgroundThrottlePackageWhitelistChanged();
        }
    };
    public final SettingsHelper.UserSettingChangedListener mLocationPackageBlacklistChangedListener = new SettingsHelper.UserSettingChangedListener() { // from class: com.android.server.location.gnss.GnssListenerMultiplexer$$ExternalSyntheticLambda3
        @Override // com.android.server.location.injector.SettingsHelper.UserSettingChangedListener
        public final void onSettingChanged(int i) {
            GnssListenerMultiplexer.this.onLocationPackageBlacklistChanged(i);
        }
    };
    public final LocationPermissionsHelper.LocationPermissionsListener mLocationPermissionsListener = new LocationPermissionsHelper.LocationPermissionsListener() { // from class: com.android.server.location.gnss.GnssListenerMultiplexer.1
        @Override // com.android.server.location.injector.LocationPermissionsHelper.LocationPermissionsListener
        public void onLocationPermissionsChanged(String str) {
            GnssListenerMultiplexer.this.onLocationPermissionsChanged(str);
        }

        @Override // com.android.server.location.injector.LocationPermissionsHelper.LocationPermissionsListener
        public void onLocationPermissionsChanged(int i) {
            GnssListenerMultiplexer.this.onLocationPermissionsChanged(i);
        }
    };
    public final AppForegroundHelper.AppForegroundListener mAppForegroundChangedListener = new AppForegroundHelper.AppForegroundListener() { // from class: com.android.server.location.gnss.GnssListenerMultiplexer$$ExternalSyntheticLambda4
        @Override // com.android.server.location.injector.AppForegroundHelper.AppForegroundListener
        public final void onAppForegroundChanged(int i, boolean z) {
            GnssListenerMultiplexer.this.onAppForegroundChanged(i, z);
        }
    };
    public final PackageResetHelper.Responder mPackageResetResponder = new PackageResetHelper.Responder() { // from class: com.android.server.location.gnss.GnssListenerMultiplexer.2
        @Override // com.android.server.location.injector.PackageResetHelper.Responder
        public void onPackageReset(String str) {
            GnssListenerMultiplexer.this.onPackageReset(str);
        }

        @Override // com.android.server.location.injector.PackageResetHelper.Responder
        public boolean isResetableForPackage(String str) {
            return GnssListenerMultiplexer.this.isResetableForPackage(str);
        }
    };

    public static /* synthetic */ boolean lambda$onBackgroundThrottlePackageWhitelistChanged$3(GnssListenerRegistration gnssListenerRegistration) {
        return true;
    }

    public abstract LocationConstants.LISTENER_TYPE getListenerType();

    public boolean isSupported() {
        return true;
    }

    public class GnssListenerRegistration extends BinderListenerRegistration {
        public boolean mForeground;
        public final CallerIdentity mIdentity;
        public boolean mPermitted;
        public final Object mRequest;

        @Override // com.android.server.location.listeners.BinderListenerRegistration
        public IBinder getBinderFromKey(IBinder iBinder) {
            return iBinder;
        }

        @Override // com.android.server.location.listeners.ListenerRegistration
        public String getTag() {
            return "GnssManager";
        }

        public GnssListenerRegistration(Object obj, CallerIdentity callerIdentity, IInterface iInterface) {
            super(callerIdentity.isMyProcess() ? LocationServiceThread.getExecutor() : ConcurrentUtils.DIRECT_EXECUTOR, iInterface);
            this.mRequest = obj;
            this.mIdentity = callerIdentity;
        }

        public final Object getRequest() {
            return this.mRequest;
        }

        public final CallerIdentity getIdentity() {
            return this.mIdentity;
        }

        @Override // com.android.server.location.listeners.RemovableListenerRegistration
        public GnssListenerMultiplexer getOwner() {
            return GnssListenerMultiplexer.this;
        }

        public boolean isForeground() {
            return this.mForeground;
        }

        public boolean isPermitted() {
            return this.mPermitted;
        }

        @Override // com.android.server.location.listeners.BinderListenerRegistration, com.android.server.location.listeners.RemovableListenerRegistration
        public void onRegister() {
            super.onRegister();
            this.mPermitted = GnssListenerMultiplexer.this.mLocationPermissionsHelper.hasLocationPermissions(2, this.mIdentity);
            this.mForeground = GnssListenerMultiplexer.this.mAppForegroundHelper.isAppForeground(this.mIdentity.getUid());
        }

        public boolean onLocationPermissionsChanged(String str) {
            if (str == null || this.mIdentity.getPackageName().equals(str)) {
                return onLocationPermissionsChanged();
            }
            return false;
        }

        public boolean onLocationPermissionsChanged(int i) {
            if (this.mIdentity.getUid() == i) {
                return onLocationPermissionsChanged();
            }
            return false;
        }

        public final boolean onLocationPermissionsChanged() {
            boolean hasLocationPermissions = GnssListenerMultiplexer.this.mLocationPermissionsHelper.hasLocationPermissions(2, this.mIdentity);
            if (hasLocationPermissions == this.mPermitted) {
                return false;
            }
            this.mPermitted = hasLocationPermissions;
            return true;
        }

        public boolean onForegroundChanged(int i, boolean z) {
            if (this.mIdentity.getUid() != i || z == this.mForeground) {
                return false;
            }
            this.mForeground = z;
            return true;
        }

        public String toString() {
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

        @Override // com.android.server.location.listeners.ListenerRegistration
        public void onActive() {
            super.onActive();
            updateGnssListener();
        }

        @Override // com.android.server.location.listeners.ListenerRegistration
        public void onInactive() {
            super.onInactive();
            updateGnssListener();
        }

        public final void updateGnssListener() {
            CallerIdentity identity = getIdentity();
            GnssListenerMultiplexer.this.updateGnssDataListener(getBinder(), isActive(), identity.getPackageName(), identity.getUid(), identity.getPid());
        }
    }

    public GnssListenerMultiplexer(Injector injector) {
        this.mUserInfoHelper = injector.getUserInfoHelper();
        this.mSettingsHelper = injector.getSettingsHelper();
        this.mLocationPermissionsHelper = injector.getLocationPermissionsHelper();
        this.mAppForegroundHelper = injector.getAppForegroundHelper();
        this.mPackageResetHelper = injector.getPackageResetHelper();
        LocationManagerInternal locationManagerInternal = (LocationManagerInternal) LocalServices.getService(LocationManagerInternal.class);
        Objects.requireNonNull(locationManagerInternal);
        this.mLocationManagerInternal = locationManagerInternal;
        this.mNSLocationProviderHelper = injector.getNSLocationProviderHelper();
    }

    public void addListener(CallerIdentity callerIdentity, IInterface iInterface) {
        addListener(null, callerIdentity, iInterface);
    }

    public void addListener(final Object obj, final CallerIdentity callerIdentity, final IInterface iInterface) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            checkLimitAndPutRegistration(new Runnable() { // from class: com.android.server.location.gnss.GnssListenerMultiplexer.3
                @Override // java.lang.Runnable
                public void run() {
                    GnssListenerMultiplexer.this.putRegistration(iInterface.asBinder(), GnssListenerMultiplexer.this.createRegistration(obj, callerIdentity, iInterface));
                }
            }, callerIdentity);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void checkLimitAndPutRegistration(Runnable runnable, CallerIdentity callerIdentity) {
        if (callerIdentity.getUid() == 1000 || getRegistrationCountWith(getPredicate(callerIdentity)) < 30) {
            runnable.run();
        }
    }

    public static /* synthetic */ boolean lambda$getPredicate$0(CallerIdentity callerIdentity, GnssListenerRegistration gnssListenerRegistration) {
        return gnssListenerRegistration.getIdentity().getUid() == callerIdentity.getUid();
    }

    public Predicate getPredicate(final CallerIdentity callerIdentity) {
        return new Predicate() { // from class: com.android.server.location.gnss.GnssListenerMultiplexer$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$getPredicate$0;
                lambda$getPredicate$0 = GnssListenerMultiplexer.lambda$getPredicate$0(callerIdentity, (GnssListenerMultiplexer.GnssListenerRegistration) obj);
                return lambda$getPredicate$0;
            }
        };
    }

    public GnssListenerRegistration createRegistration(Object obj, CallerIdentity callerIdentity, IInterface iInterface) {
        return new GnssListenerRegistration(obj, callerIdentity, iInterface);
    }

    public void removeListener(IInterface iInterface) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            removeRegistration(iInterface.asBinder());
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public boolean isActive(GnssListenerRegistration gnssListenerRegistration) {
        if (!isSupported()) {
            return false;
        }
        CallerIdentity identity = gnssListenerRegistration.getIdentity();
        if (gnssListenerRegistration.isPermitted()) {
            return (gnssListenerRegistration.isForeground() || isBackgroundRestrictionExempt(identity)) && isActive(identity);
        }
        return false;
    }

    public final boolean isActive(CallerIdentity callerIdentity) {
        return callerIdentity.isSystemServer() ? this.mLocationManagerInternal.isProviderEnabledForUser("gps", this.mUserInfoHelper.getCurrentUserId()) : this.mLocationManagerInternal.isProviderEnabledForUser("gps", callerIdentity.getUserId()) && this.mUserInfoHelper.isVisibleUserId(callerIdentity.getUserId()) && !this.mSettingsHelper.isLocationPackageBlacklisted(callerIdentity.getUserId(), callerIdentity.getPackageName());
    }

    public final boolean isBackgroundRestrictionExempt(CallerIdentity callerIdentity) {
        if (callerIdentity.getUid() == 1000 || this.mSettingsHelper.getBackgroundThrottlePackageWhitelist().contains(callerIdentity.getPackageName()) || this.mSettingsHelper.isBackgroundThrottlingAllowlistByNsflp(callerIdentity.getPackageName())) {
            return true;
        }
        return this.mLocationManagerInternal.isProvider((String) null, callerIdentity);
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public Object mergeRegistrations(Collection collection) {
        if (!Build.IS_DEBUGGABLE) {
            return null;
        }
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            Preconditions.checkState(((GnssListenerRegistration) it.next()).getRequest() == null);
        }
        return null;
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public void onRegister() {
        if (isSupported()) {
            this.mUserInfoHelper.addListener(this.mUserChangedListener);
            this.mLocationManagerInternal.addProviderEnabledListener("gps", this.mProviderEnabledChangedListener);
            this.mSettingsHelper.addOnBackgroundThrottlePackageWhitelistChangedListener(this.mBackgroundThrottlePackageWhitelistChangedListener);
            this.mSettingsHelper.addOnLocationPackageBlacklistChangedListener(this.mLocationPackageBlacklistChangedListener);
            this.mLocationPermissionsHelper.addListener(this.mLocationPermissionsListener);
            this.mAppForegroundHelper.addListener(this.mAppForegroundChangedListener);
            this.mPackageResetHelper.register(this.mPackageResetResponder);
        }
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public void onUnregister() {
        if (isSupported()) {
            this.mUserInfoHelper.removeListener(this.mUserChangedListener);
            this.mLocationManagerInternal.removeProviderEnabledListener("gps", this.mProviderEnabledChangedListener);
            this.mSettingsHelper.removeOnBackgroundThrottlePackageWhitelistChangedListener(this.mBackgroundThrottlePackageWhitelistChangedListener);
            this.mSettingsHelper.removeOnLocationPackageBlacklistChangedListener(this.mLocationPackageBlacklistChangedListener);
            this.mLocationPermissionsHelper.removeListener(this.mLocationPermissionsListener);
            this.mAppForegroundHelper.removeListener(this.mAppForegroundChangedListener);
            this.mPackageResetHelper.unregister(this.mPackageResetResponder);
        }
    }

    public static /* synthetic */ boolean lambda$onUserChanged$1(int i, GnssListenerRegistration gnssListenerRegistration) {
        return gnssListenerRegistration.getIdentity().getUserId() == i;
    }

    public final void onUserChanged(final int i, int i2) {
        if (i2 == 1 || i2 == 4) {
            updateRegistrations(new Predicate() { // from class: com.android.server.location.gnss.GnssListenerMultiplexer$$ExternalSyntheticLambda6
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$onUserChanged$1;
                    lambda$onUserChanged$1 = GnssListenerMultiplexer.lambda$onUserChanged$1(i, (GnssListenerMultiplexer.GnssListenerRegistration) obj);
                    return lambda$onUserChanged$1;
                }
            });
        }
    }

    public final void onProviderEnabledChanged(String str, final int i, boolean z) {
        Preconditions.checkState("gps".equals(str));
        updateRegistrations(new Predicate() { // from class: com.android.server.location.gnss.GnssListenerMultiplexer$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$onProviderEnabledChanged$2;
                lambda$onProviderEnabledChanged$2 = GnssListenerMultiplexer.lambda$onProviderEnabledChanged$2(i, (GnssListenerMultiplexer.GnssListenerRegistration) obj);
                return lambda$onProviderEnabledChanged$2;
            }
        });
    }

    public static /* synthetic */ boolean lambda$onProviderEnabledChanged$2(int i, GnssListenerRegistration gnssListenerRegistration) {
        return gnssListenerRegistration.getIdentity().getUserId() == i;
    }

    public final void onBackgroundThrottlePackageWhitelistChanged() {
        updateRegistrations(new Predicate() { // from class: com.android.server.location.gnss.GnssListenerMultiplexer$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$onBackgroundThrottlePackageWhitelistChanged$3;
                lambda$onBackgroundThrottlePackageWhitelistChanged$3 = GnssListenerMultiplexer.lambda$onBackgroundThrottlePackageWhitelistChanged$3((GnssListenerMultiplexer.GnssListenerRegistration) obj);
                return lambda$onBackgroundThrottlePackageWhitelistChanged$3;
            }
        });
    }

    public static /* synthetic */ boolean lambda$onLocationPackageBlacklistChanged$4(int i, GnssListenerRegistration gnssListenerRegistration) {
        return gnssListenerRegistration.getIdentity().getUserId() == i;
    }

    public final void onLocationPackageBlacklistChanged(final int i) {
        updateRegistrations(new Predicate() { // from class: com.android.server.location.gnss.GnssListenerMultiplexer$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$onLocationPackageBlacklistChanged$4;
                lambda$onLocationPackageBlacklistChanged$4 = GnssListenerMultiplexer.lambda$onLocationPackageBlacklistChanged$4(i, (GnssListenerMultiplexer.GnssListenerRegistration) obj);
                return lambda$onLocationPackageBlacklistChanged$4;
            }
        });
    }

    public static /* synthetic */ boolean lambda$onLocationPermissionsChanged$5(String str, GnssListenerRegistration gnssListenerRegistration) {
        return gnssListenerRegistration.onLocationPermissionsChanged(str);
    }

    public final void onLocationPermissionsChanged(final String str) {
        updateRegistrations(new Predicate() { // from class: com.android.server.location.gnss.GnssListenerMultiplexer$$ExternalSyntheticLambda14
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$onLocationPermissionsChanged$5;
                lambda$onLocationPermissionsChanged$5 = GnssListenerMultiplexer.lambda$onLocationPermissionsChanged$5(str, (GnssListenerMultiplexer.GnssListenerRegistration) obj);
                return lambda$onLocationPermissionsChanged$5;
            }
        });
    }

    public static /* synthetic */ boolean lambda$onLocationPermissionsChanged$6(int i, GnssListenerRegistration gnssListenerRegistration) {
        return gnssListenerRegistration.onLocationPermissionsChanged(i);
    }

    public final void onLocationPermissionsChanged(final int i) {
        updateRegistrations(new Predicate() { // from class: com.android.server.location.gnss.GnssListenerMultiplexer$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$onLocationPermissionsChanged$6;
                lambda$onLocationPermissionsChanged$6 = GnssListenerMultiplexer.lambda$onLocationPermissionsChanged$6(i, (GnssListenerMultiplexer.GnssListenerRegistration) obj);
                return lambda$onLocationPermissionsChanged$6;
            }
        });
    }

    public static /* synthetic */ boolean lambda$onAppForegroundChanged$7(int i, boolean z, GnssListenerRegistration gnssListenerRegistration) {
        return gnssListenerRegistration.onForegroundChanged(i, z);
    }

    public final void onAppForegroundChanged(final int i, final boolean z) {
        updateRegistrations(new Predicate() { // from class: com.android.server.location.gnss.GnssListenerMultiplexer$$ExternalSyntheticLambda11
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$onAppForegroundChanged$7;
                lambda$onAppForegroundChanged$7 = GnssListenerMultiplexer.lambda$onAppForegroundChanged$7(i, z, (GnssListenerMultiplexer.GnssListenerRegistration) obj);
                return lambda$onAppForegroundChanged$7;
            }
        });
    }

    public final void onPackageReset(final String str) {
        updateRegistrations(new Predicate() { // from class: com.android.server.location.gnss.GnssListenerMultiplexer$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$onPackageReset$8;
                lambda$onPackageReset$8 = GnssListenerMultiplexer.lambda$onPackageReset$8(str, (GnssListenerMultiplexer.GnssListenerRegistration) obj);
                return lambda$onPackageReset$8;
            }
        });
    }

    public static /* synthetic */ boolean lambda$onPackageReset$8(String str, GnssListenerRegistration gnssListenerRegistration) {
        if (!gnssListenerRegistration.getIdentity().getPackageName().equals(str)) {
            return false;
        }
        gnssListenerRegistration.remove();
        return false;
    }

    public final boolean isResetableForPackage(final String str) {
        return findRegistration(new Predicate() { // from class: com.android.server.location.gnss.GnssListenerMultiplexer$$ExternalSyntheticLambda13
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$isResetableForPackage$9;
                lambda$isResetableForPackage$9 = GnssListenerMultiplexer.lambda$isResetableForPackage$9(str, (GnssListenerMultiplexer.GnssListenerRegistration) obj);
                return lambda$isResetableForPackage$9;
            }
        });
    }

    public static /* synthetic */ boolean lambda$isResetableForPackage$9(String str, GnssListenerRegistration gnssListenerRegistration) {
        return gnssListenerRegistration.getIdentity().getPackageName().equals(str);
    }

    @Override // com.android.server.location.listeners.ListenerMultiplexer
    public String getServiceState() {
        return !isSupported() ? "unsupported" : super.getServiceState();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateGnssDataListener(IBinder iBinder, boolean z, String str, int i, int i2) {
        NSLocationProviderHelper nSLocationProviderHelper = this.mNSLocationProviderHelper;
        if (nSLocationProviderHelper == null) {
            return;
        }
        nSLocationProviderHelper.updateGnssDataListener(iBinder, z, str, getListenerType(), i, i2);
    }

    public final void addGnssDataListener(IBinder iBinder, GnssListenerRegistration gnssListenerRegistration) {
        if (this.mNSLocationProviderHelper != null) {
            CallerIdentity identity = gnssListenerRegistration.getIdentity();
            this.mNSLocationProviderHelper.addGnssDataListener(iBinder, identity.getPackageName(), getListenerType(), identity.getUid(), identity.getPid(), gnssListenerRegistration.isPermitted(), gnssListenerRegistration.isForeground());
        }
    }

    public final void removeGnssDataListener(IBinder iBinder, GnssListenerRegistration gnssListenerRegistration) {
        if (this.mNSLocationProviderHelper != null) {
            CallerIdentity identity = gnssListenerRegistration.getIdentity();
            this.mNSLocationProviderHelper.removeGnssDataListener(iBinder, getListenerType(), identity.getUid(), identity.getPid());
        }
    }
}
