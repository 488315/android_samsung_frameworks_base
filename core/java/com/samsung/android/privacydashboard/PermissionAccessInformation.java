package com.samsung.android.privacydashboard;

import android.p009os.SystemProperties;
import android.text.format.DateFormat;
import java.util.Objects;

/* loaded from: classes5.dex */
public class PermissionAccessInformation {
    private static final long MIN_MS_SEC = 60000;
    private long mAccessTime;
    private boolean mIsBackground;
    private int mOp;
    private String mPackageName;
    private String mProxyAttributionTag;
    private String mProxyPackageName;
    private int mUid;

    public PermissionAccessInformation(int op, int uid, String packageName, String proxyPackageName, String proxyAttributionTag, boolean isBackground, long accessTime) {
        this.mOp = op;
        this.mUid = uid;
        this.mPackageName = packageName;
        this.mProxyAttributionTag = proxyAttributionTag != null ? proxyAttributionTag : "";
        this.mIsBackground = isBackground;
        this.mAccessTime = accessTime;
        this.mProxyPackageName = proxyPackageName != null ? proxyPackageName : "";
    }

    public int getOp() {
        return this.mOp;
    }

    public int getUid() {
        return this.mUid;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public String getProxyAttributionTag() {
        return this.mProxyAttributionTag;
    }

    public boolean isBackground() {
        return this.mIsBackground;
    }

    public long getAccessTime() {
        return this.mAccessTime;
    }

    public String getProxyPackageName() {
        return this.mProxyPackageName;
    }

    public boolean equals(Object o) {
        boolean accessTime;
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PermissionAccessInformation that = (PermissionAccessInformation) o;
        if (!"CHINA".equalsIgnoreCase(SystemProperties.get("ro.csc.country_code")) ? this.mAccessTime / 60000 == that.mAccessTime / 60000 : this.mAccessTime == that.mAccessTime) {
            accessTime = true;
        } else {
            accessTime = false;
        }
        if (this.mOp == that.mOp && this.mUid == that.mUid && this.mIsBackground == that.mIsBackground && accessTime && this.mPackageName.equals(that.mPackageName) && this.mProxyPackageName.equals(that.mProxyPackageName) && this.mProxyAttributionTag.equals(that.mProxyAttributionTag)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mOp), Integer.valueOf(this.mUid), this.mPackageName, this.mProxyPackageName, this.mProxyAttributionTag, Boolean.valueOf(this.mIsBackground), Long.valueOf(this.mAccessTime / 60000));
    }

    public String toString() {
        return "PermissionAccessInformation{op=" + this.mOp + ", uid=" + this.mUid + DateFormat.QUOTE + ", packageName='" + this.mPackageName + DateFormat.QUOTE + ", proxyPackageName='" + this.mProxyPackageName + DateFormat.QUOTE + ", proxyAttributionTag='" + this.mProxyAttributionTag + DateFormat.QUOTE + ", isBackground=" + this.mIsBackground + ", accessTime=" + this.mAccessTime + '}';
    }
}
