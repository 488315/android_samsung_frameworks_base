package com.android.server.servicewatcher;

import android.app.ActivityManagerInternal;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.location.flags.Flags;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.TypedValue;
import com.android.server.LocalServices;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class CurrentUserServiceSupplier extends BroadcastReceiver implements ServiceWatcher$ServiceSupplier {
    public static final CurrentUserServiceSupplier$$ExternalSyntheticLambda0 sBoundServiceInfoComparator = new CurrentUserServiceSupplier$$ExternalSyntheticLambda0();
    public final ActivityManagerInternal mActivityManager;
    public final String mCallerPermission;
    public final Context mContext;
    public final Intent mIntent;
    public volatile ServiceWatcher$ServiceChangedListener mListener;
    public final boolean mMatchSystemAppsOnly;
    public final String mServicePermission;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BoundServiceInfo {
        public final String mAction;
        public final ComponentName mComponentName;
        public final Bundle mMetadata;
        public final int mUid;
        public final int mVersion;

        public BoundServiceInfo(String str, ResolveInfo resolveInfo) {
            ServiceInfo serviceInfo = resolveInfo.serviceInfo;
            int i = serviceInfo.applicationInfo.uid;
            Bundle bundle = serviceInfo.metaData;
            if (bundle != null && bundle.getBoolean("serviceIsMultiuser", false)) {
                i = UserHandle.getUid(0, UserHandle.getAppId(i));
            }
            ComponentName componentName = resolveInfo.serviceInfo.getComponentName();
            Bundle bundle2 = resolveInfo.serviceInfo.metaData;
            int i2 = bundle2 != null ? bundle2.getInt("serviceVersion", Integer.MIN_VALUE) : Integer.MIN_VALUE;
            Bundle bundle3 = resolveInfo.serviceInfo.metaData;
            this.mAction = str;
            this.mUid = i;
            Objects.requireNonNull(componentName);
            this.mComponentName = componentName;
            this.mVersion = i2;
            this.mMetadata = bundle3;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof BoundServiceInfo)) {
                return false;
            }
            BoundServiceInfo boundServiceInfo = (BoundServiceInfo) obj;
            return this.mUid == boundServiceInfo.mUid && Objects.equals(this.mAction, boundServiceInfo.mAction) && this.mComponentName.equals(boundServiceInfo.mComponentName);
        }

        public final int hashCode() {
            return Objects.hash(this.mAction, Integer.valueOf(this.mUid), this.mComponentName);
        }

        public final String toString() {
            return toString$com$android$server$servicewatcher$ServiceWatcher$BoundServiceInfo() + "@" + this.mVersion;
        }

        public final String toString$com$android$server$servicewatcher$ServiceWatcher$BoundServiceInfo() {
            if (this.mComponentName == null) {
                return "none";
            }
            return this.mUid + "/" + this.mComponentName.flattenToShortString();
        }
    }

    public CurrentUserServiceSupplier(Context context, String str, String str2, String str3, String str4, boolean z) {
        this.mContext = context;
        ActivityManagerInternal activityManagerInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        Objects.requireNonNull(activityManagerInternal);
        this.mActivityManager = activityManagerInternal;
        Intent intent = new Intent(str);
        this.mIntent = intent;
        if (str2 != null) {
            intent.setPackage(str2);
        }
        this.mCallerPermission = str3;
        this.mServicePermission = str4;
        this.mMatchSystemAppsOnly = z;
    }

    public static CurrentUserServiceSupplier createFromConfig(Context context, String str, int i, int i2) {
        String str2;
        String string;
        Resources resources = context.getResources();
        if (resources.getBoolean(i)) {
            str2 = null;
        } else {
            if (Flags.fixServiceWatcher()) {
                TypedValue typedValue = new TypedValue();
                resources.getValue(i2, typedValue, true);
                CharSequence coerceToString = typedValue.coerceToString();
                string = coerceToString == null ? "" : coerceToString.toString();
            } else {
                string = resources.getString(i2);
            }
            str2 = string;
        }
        return new CurrentUserServiceSupplier(context, str, str2, null, null, true);
    }

    public final BoundServiceInfo getServiceInfo() {
        BoundServiceInfo boundServiceInfo = null;
        if (!Flags.fixServiceWatcher() || !"".equals(this.mIntent.getPackage())) {
            for (ResolveInfo resolveInfo : this.mContext.getPackageManager().queryIntentServicesAsUser(this.mIntent, this.mMatchSystemAppsOnly ? 269484160 : 268435584, this.mActivityManager.getCurrentUserId())) {
                ServiceInfo serviceInfo = resolveInfo.serviceInfo;
                Objects.requireNonNull(serviceInfo);
                String str = this.mCallerPermission;
                if (str == null || str.equals(serviceInfo.permission)) {
                    BoundServiceInfo boundServiceInfo2 = new BoundServiceInfo(this.mIntent.getAction(), resolveInfo);
                    String str2 = this.mServicePermission;
                    if (str2 != null && this.mContext.checkPermission(str2, -1, boundServiceInfo2.mUid) != 0) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(boundServiceInfo2.mComponentName.flattenToShortString());
                        sb.append(" disqualified due to not holding ");
                        VpnManagerService$$ExternalSyntheticOutline0.m(sb, this.mCallerPermission, "CurrentUserServiceSupplier");
                    } else if (sBoundServiceInfoComparator.compare(boundServiceInfo2, boundServiceInfo) > 0) {
                        boundServiceInfo = boundServiceInfo2;
                    }
                } else {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(serviceInfo.getComponentName().flattenToShortString());
                    sb2.append(" disqualified due to not requiring ");
                    VpnManagerService$$ExternalSyntheticOutline0.m(sb2, this.mCallerPermission, "CurrentUserServiceSupplier");
                }
            }
        }
        return boundServiceInfo;
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        int intExtra;
        ServiceWatcher$ServiceChangedListener serviceWatcher$ServiceChangedListener;
        String action = intent.getAction();
        if (action == null || (intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000)) == -10000 || (serviceWatcher$ServiceChangedListener = this.mListener) == null) {
            return;
        }
        if (action.equals("android.intent.action.USER_UNLOCKED")) {
            if (intExtra == this.mActivityManager.getCurrentUserId()) {
                ServiceWatcherImpl serviceWatcherImpl = (ServiceWatcherImpl) serviceWatcher$ServiceChangedListener;
                synchronized (serviceWatcherImpl) {
                    serviceWatcherImpl.onServiceChanged(false);
                }
                return;
            }
            return;
        }
        if (action.equals("android.intent.action.USER_SWITCHED")) {
            ServiceWatcherImpl serviceWatcherImpl2 = (ServiceWatcherImpl) serviceWatcher$ServiceChangedListener;
            synchronized (serviceWatcherImpl2) {
                serviceWatcherImpl2.onServiceChanged(false);
            }
        }
    }
}
