package android.content.pm;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public final class AuxiliaryResolveInfo {
    public final Intent failureIntent;
    public final List<AuxiliaryFilter> filters;
    public final int[] hostDigestPrefixSecure;
    public final ComponentName installFailureActivity;
    public final boolean needsPhaseTwo;
    public final String token;

    public AuxiliaryResolveInfo(String str, boolean z, Intent intent, List<AuxiliaryFilter> list, int[] iArr) {
        this.token = str;
        this.needsPhaseTwo = z;
        this.failureIntent = intent;
        this.filters = list;
        this.installFailureActivity = null;
        this.hostDigestPrefixSecure = iArr;
    }

    public AuxiliaryResolveInfo(ComponentName componentName, Intent intent, List<AuxiliaryFilter> list) {
        this.installFailureActivity = componentName;
        this.filters = list;
        this.token = null;
        this.needsPhaseTwo = false;
        this.failureIntent = intent;
        this.hostDigestPrefixSecure = null;
    }

    public AuxiliaryResolveInfo(ComponentName componentName, String str, long j, String str2) {
        this(componentName, null, Collections.singletonList(new AuxiliaryFilter(str, j, str2)));
    }

    public static final class AuxiliaryFilter extends IntentFilter {
        public final Bundle extras;
        public final String packageName;
        public final InstantAppResolveInfo resolveInfo;
        public final String splitName;
        public final long versionCode;

        public AuxiliaryFilter(IntentFilter intentFilter, InstantAppResolveInfo instantAppResolveInfo, String str, Bundle bundle) {
            super(intentFilter);
            this.resolveInfo = instantAppResolveInfo;
            this.packageName = instantAppResolveInfo.getPackageName();
            this.versionCode = instantAppResolveInfo.getLongVersionCode();
            this.splitName = str;
            this.extras = bundle;
        }

        public AuxiliaryFilter(InstantAppResolveInfo instantAppResolveInfo, String str, Bundle bundle) {
            this.resolveInfo = instantAppResolveInfo;
            this.packageName = instantAppResolveInfo.getPackageName();
            this.versionCode = instantAppResolveInfo.getLongVersionCode();
            this.splitName = str;
            this.extras = bundle;
        }

        public AuxiliaryFilter(String str, long j, String str2) {
            this.resolveInfo = null;
            this.packageName = str;
            this.versionCode = j;
            this.splitName = str2;
            this.extras = null;
        }

        public String toString() {
            return "AuxiliaryFilter{packageName='" + this.packageName + "', versionCode=" + this.versionCode + ", splitName='" + this.splitName + "'}";
        }
    }
}
