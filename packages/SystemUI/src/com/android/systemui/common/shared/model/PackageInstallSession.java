package com.android.systemui.common.shared.model;

import android.graphics.Bitmap;
import android.os.UserHandle;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class PackageInstallSession {
    public final Bitmap icon;
    public final String packageName;
    public final int sessionId;
    public final UserHandle user;

    public PackageInstallSession(int i, String str, Bitmap bitmap, UserHandle userHandle) {
        this.sessionId = i;
        this.packageName = str;
        this.icon = bitmap;
        this.user = userHandle;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PackageInstallSession)) {
            return false;
        }
        PackageInstallSession packageInstallSession = (PackageInstallSession) obj;
        return this.sessionId == packageInstallSession.sessionId && Intrinsics.areEqual(this.packageName, packageInstallSession.packageName) && Intrinsics.areEqual(this.icon, packageInstallSession.icon) && Intrinsics.areEqual(this.user, packageInstallSession.user);
    }

    public final int hashCode() {
        int m = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(Integer.hashCode(this.sessionId) * 31, 31, this.packageName);
        Bitmap bitmap = this.icon;
        return this.user.hashCode() + ((m + (bitmap == null ? 0 : bitmap.hashCode())) * 31);
    }

    public final String toString() {
        return "PackageInstallSession(sessionId=" + this.sessionId + ", packageName=" + this.packageName + ", icon=" + this.icon + ", user=" + this.user + ")";
    }
}
