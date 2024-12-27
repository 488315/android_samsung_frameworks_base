package com.android.server.devicepolicy;

import android.content.ComponentName;

public final class CallerIdentity {
    public final ComponentName mComponentName;
    public final String mPackageName;
    public final int mUid;

    public CallerIdentity(ComponentName componentName, int i, String str) {
        this.mUid = i;
        this.mPackageName = str;
        this.mComponentName = componentName;
    }

    public final boolean hasAdminComponent() {
        return this.mComponentName != null;
    }

    public final boolean hasPackage() {
        return this.mPackageName != null;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("CallerIdentity[uid=");
        sb.append(this.mUid);
        String str = this.mPackageName;
        if (str != null) {
            sb.append(", pkg=");
            sb.append(str);
        }
        if (this.mComponentName != null) {
            sb.append(", cmp=");
            sb.append(this.mComponentName.flattenToShortString());
        }
        sb.append("]");
        return sb.toString();
    }
}
