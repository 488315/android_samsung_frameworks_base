package com.android.server.wm;

import android.os.IBinder;
import android.util.ArraySet;
import android.view.flags.Flags;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SensitiveContentPackages {
    public final ArraySet mProtectedPackages = new ArraySet();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PackageInfo {
        public final String mPkg;
        public final int mUid;
        public final IBinder mWindowToken;

        public PackageInfo(int i, IBinder iBinder, String str) {
            this.mPkg = str;
            this.mUid = i;
            this.mWindowToken = iBinder;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof PackageInfo)) {
                return false;
            }
            PackageInfo packageInfo = (PackageInfo) obj;
            return this.mUid == packageInfo.mUid
                    && Objects.equals(this.mPkg, packageInfo.mPkg)
                    && Objects.equals(this.mWindowToken, packageInfo.mWindowToken);
        }

        public final int hashCode() {
            return Objects.hash(this.mPkg, Integer.valueOf(this.mUid), this.mWindowToken);
        }

        public final String toString() {
            return "package="
                    + this.mPkg
                    + "  uid="
                    + this.mUid
                    + " windowToken="
                    + this.mWindowToken;
        }
    }

    public final void dump(PrintWriter printWriter) {
        StringBuilder m$1 =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(
                        printWriter,
                        "SensitiveContentPackages:",
                        "  Packages that should block screen capture (");
        m$1.append(this.mProtectedPackages.size());
        m$1.append("):");
        printWriter.println(m$1.toString());
        Iterator it = this.mProtectedPackages.iterator();
        while (it.hasNext()) {
            PackageInfo packageInfo = (PackageInfo) it.next();
            printWriter.println(
                    "    package="
                            + packageInfo.mPkg
                            + "  uid="
                            + packageInfo.mUid
                            + " windowToken="
                            + packageInfo.mWindowToken);
        }
    }

    public final boolean shouldBlockScreenCaptureForApp(int i, IBinder iBinder, String str) {
        if (!Flags.sensitiveContentAppProtection()
                && !com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags
                        .sensitiveNotificationAppProtection()) {
            return false;
        }
        for (int i2 = 0; i2 < this.mProtectedPackages.size(); i2++) {
            PackageInfo packageInfo = (PackageInfo) this.mProtectedPackages.valueAt(i2);
            if (packageInfo != null && packageInfo.mPkg.equals(str) && packageInfo.mUid == i) {
                if (Flags.sensitiveContentAppProtection() && iBinder == packageInfo.mWindowToken) {
                    return true;
                }
                if (com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags
                                .sensitiveNotificationAppProtection()
                        && packageInfo.mWindowToken == null) {
                    return true;
                }
            }
        }
        return false;
    }

    public int size() {
        return this.mProtectedPackages.size();
    }
}
