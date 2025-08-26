package com.android.server.servicewatcher;

import android.app.ActivityManagerInternal;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.os.BatteryStats;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;
import android.util.TypedValue;
import com.android.internal.hidden_from_bootclasspath.android.location.flags.Flags;
import com.android.internal.util.Preconditions;
import com.android.server.FgThread;
import com.android.server.LocalServices;
import com.android.server.servicewatcher.CurrentUserServiceSupplier;
import com.android.server.servicewatcher.ServiceWatcher;
import java.util.Comparator;
import java.util.Objects;

/* loaded from: classes6.dex */
public final class CurrentUserServiceSupplier extends BroadcastReceiver implements ServiceWatcher.ServiceSupplier<BoundServiceInfo> {
    private static final String EXTRA_SERVICE_IS_MULTIUSER = "serviceIsMultiuser";
    private static final String EXTRA_SERVICE_VERSION = "serviceVersion";
    private static final String NO_MATCH_PACKAGE = "";
    private static final String TAG = "CurrentUserServiceSupplier";
    private static final Comparator<BoundServiceInfo> sBoundServiceInfoComparator = new Comparator() { // from class: com.android.server.servicewatcher.CurrentUserServiceSupplier$$ExternalSyntheticLambda0
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return CurrentUserServiceSupplier.lambda$static$0((CurrentUserServiceSupplier.BoundServiceInfo) obj, (CurrentUserServiceSupplier.BoundServiceInfo) obj2);
        }
    };
    private final ActivityManagerInternal mActivityManager = (ActivityManagerInternal) Objects.requireNonNull((ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class));
    private final String mCallerPermission;
    private final Context mContext;
    private final Intent mIntent;
    private volatile ServiceWatcher.ServiceChangedListener mListener;
    private final boolean mMatchSystemAppsOnly;
    private final String mServicePermission;
    private String mUnstableService;

    static /* synthetic */ int lambda$static$0(BoundServiceInfo boundServiceInfo, BoundServiceInfo boundServiceInfo2) {
        if (boundServiceInfo == boundServiceInfo2) {
            return 0;
        }
        if (boundServiceInfo == null) {
            return -1;
        }
        if (boundServiceInfo2 == null) {
            return 1;
        }
        int iCompare = Integer.compare(boundServiceInfo.getVersion(), boundServiceInfo2.getVersion());
        if (iCompare == 0) {
            if (boundServiceInfo.getUserId() != 0 && boundServiceInfo2.getUserId() == 0) {
                return -1;
            }
            if (boundServiceInfo.getUserId() == 0 && boundServiceInfo2.getUserId() != 0) {
                return 1;
            }
        }
        return iCompare;
    }

    public static class BoundServiceInfo extends ServiceWatcher.BoundServiceInfo {
        private final Bundle mMetadata;
        private final int mVersion;

        private static int parseUid(ResolveInfo resolveInfo) {
            int i = resolveInfo.serviceInfo.applicationInfo.uid;
            Bundle bundle = resolveInfo.serviceInfo.metaData;
            return (bundle == null || !bundle.getBoolean(CurrentUserServiceSupplier.EXTRA_SERVICE_IS_MULTIUSER, false)) ? i : UserHandle.getUid(0, UserHandle.getAppId(i));
        }

        private static int parseVersion(ResolveInfo resolveInfo) {
            if (resolveInfo.serviceInfo.metaData != null) {
                return resolveInfo.serviceInfo.metaData.getInt(CurrentUserServiceSupplier.EXTRA_SERVICE_VERSION, Integer.MIN_VALUE);
            }
            return Integer.MIN_VALUE;
        }

        protected BoundServiceInfo(String str, ResolveInfo resolveInfo) {
            this(str, parseUid(resolveInfo), resolveInfo.serviceInfo.getComponentName(), parseVersion(resolveInfo), resolveInfo.serviceInfo.metaData);
        }

        protected BoundServiceInfo(String str, int i, ComponentName componentName, int i2, Bundle bundle) {
            super(str, i, componentName);
            this.mVersion = i2;
            this.mMetadata = bundle;
        }

        public int getVersion() {
            return this.mVersion;
        }

        public Bundle getMetadata() {
            return this.mMetadata;
        }

        @Override // com.android.server.servicewatcher.ServiceWatcher.BoundServiceInfo
        public String toString() {
            return super.toString() + "@" + this.mVersion;
        }
    }

    public static CurrentUserServiceSupplier createFromConfig(Context context, String str, int i, int i2) {
        return create(context, str, retrieveExplicitPackage(context, i, i2), null, null);
    }

    public static CurrentUserServiceSupplier create(Context context, String str, String str2, String str3, String str4) {
        return new CurrentUserServiceSupplier(context, str, str2, str3, str4, true);
    }

    public static CurrentUserServiceSupplier createUnsafeForTestsOnly(Context context, String str, String str2, String str3, String str4) {
        return new CurrentUserServiceSupplier(context, str, str2, str3, str4, false);
    }

    private static String retrieveExplicitPackage(Context context, int i, int i2) throws Resources.NotFoundException {
        Resources resources = context.getResources();
        if (resources.getBoolean(i)) {
            return null;
        }
        if (Flags.fixServiceWatcher()) {
            TypedValue typedValue = new TypedValue();
            resources.getValue(i2, typedValue, true);
            CharSequence charSequenceCoerceToString = typedValue.coerceToString();
            if (charSequenceCoerceToString == null) {
                return "";
            }
            return charSequenceCoerceToString.toString();
        }
        return resources.getString(i2);
    }

    private CurrentUserServiceSupplier(Context context, String str, String str2, String str3, String str4, boolean z) {
        this.mContext = context;
        Intent intent = new Intent(str);
        this.mIntent = intent;
        if (str2 != null) {
            intent.setPackage(str2);
        }
        this.mCallerPermission = str3;
        this.mServicePermission = str4;
        this.mMatchSystemAppsOnly = z;
    }

    @Override // com.android.server.servicewatcher.ServiceWatcher.ServiceSupplier
    public boolean hasMatchingService() {
        if (Flags.fixServiceWatcher() && "".equals(this.mIntent.getPackage())) {
            return false;
        }
        return !this.mContext.getPackageManager().queryIntentServicesAsUser(this.mIntent, this.mMatchSystemAppsOnly ? BatteryStats.HistoryItem.MOST_INTERESTING_STATES : 786432, 0).isEmpty();
    }

    @Override // com.android.server.servicewatcher.ServiceWatcher.ServiceSupplier
    public void register(ServiceWatcher.ServiceChangedListener serviceChangedListener) {
        Preconditions.checkState(this.mListener == null);
        this.mListener = serviceChangedListener;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.USER_SWITCHED");
        intentFilter.addAction(Intent.ACTION_USER_UNLOCKED);
        intentFilter.setPriority(1000);
        this.mContext.registerReceiverAsUser(this, UserHandle.ALL, intentFilter, null, FgThread.getHandler());
    }

    @Override // com.android.server.servicewatcher.ServiceWatcher.ServiceSupplier
    public void unregister() {
        Preconditions.checkArgument(this.mListener != null);
        this.mListener = null;
        this.mContext.unregisterReceiver(this);
    }

    @Override // com.android.server.servicewatcher.ServiceWatcher.ServiceSupplier
    public BoundServiceInfo getServiceInfo() {
        BoundServiceInfo boundServiceInfo = null;
        if (Flags.fixServiceWatcher() && "".equals(this.mIntent.getPackage())) {
            return null;
        }
        for (ResolveInfo resolveInfo : this.mContext.getPackageManager().queryIntentServicesAsUser(this.mIntent, this.mMatchSystemAppsOnly ? 269484160 : 268435584, this.mActivityManager.getCurrentUserId())) {
            ServiceInfo serviceInfo = (ServiceInfo) Objects.requireNonNull(resolveInfo.serviceInfo);
            String str = this.mCallerPermission;
            if (str == null || str.equals(serviceInfo.permission)) {
                BoundServiceInfo boundServiceInfo2 = new BoundServiceInfo(this.mIntent.getAction(), resolveInfo);
                String str2 = this.mServicePermission;
                if (str2 == null || this.mContext.checkPermission(str2, -1, boundServiceInfo2.mUid) == 0) {
                    String str3 = this.mUnstableService;
                    if (str3 != null && boundServiceInfo != null) {
                        if (str3.equals(boundServiceInfo2.toString())) {
                            Log.d(TAG, "Not choosing unstable service " + this.mUnstableService + " as we already have a service " + boundServiceInfo.toString());
                        } else {
                            if (this.mUnstableService.equals(boundServiceInfo.toString())) {
                                Log.d(TAG, "Choosing service " + boundServiceInfo2.toString() + " over the unstable service " + this.mUnstableService);
                            }
                            boundServiceInfo = boundServiceInfo2;
                        }
                    }
                    if (sBoundServiceInfoComparator.compare(boundServiceInfo2, boundServiceInfo) > 0) {
                        boundServiceInfo = boundServiceInfo2;
                    }
                } else {
                    Log.d(TAG, boundServiceInfo2.getComponentName().flattenToShortString() + " disqualified due to not holding " + this.mCallerPermission);
                }
            } else {
                Log.d(TAG, serviceInfo.getComponentName().flattenToShortString() + " disqualified due to not requiring " + this.mCallerPermission);
            }
        }
        return boundServiceInfo;
    }

    @Override // com.android.server.servicewatcher.ServiceWatcher.ServiceSupplier
    public void alertUnstableService(String str) {
        this.mUnstableService = str;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        int intExtra;
        ServiceWatcher.ServiceChangedListener serviceChangedListener;
        String action = intent.getAction();
        if (action == null || (intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000)) == -10000 || (serviceChangedListener = this.mListener) == null) {
            return;
        }
        action.hashCode();
        if (action.equals(Intent.ACTION_USER_UNLOCKED)) {
            if (intExtra == this.mActivityManager.getCurrentUserId()) {
                serviceChangedListener.onServiceChanged();
            }
        } else if (action.equals("android.intent.action.USER_SWITCHED")) {
            serviceChangedListener.onServiceChanged();
        }
    }
}
