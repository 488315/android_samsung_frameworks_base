package com.samsung.android.privacydashboard;

import android.os.SystemProperties;
import java.util.Objects;

/* loaded from: classes6.dex */
public class PermissionAccessInformation {
    private static final long MIN_MS_SEC = 60000;
    private long mAccessTime;
    private boolean mIsBackground;
    private int mOp;
    private String mPackageName;
    private String mProxyAttributionTag;
    private String mProxyPackageName;
    private int mUid;

    public PermissionAccessInformation(int i, int i2, String str, String str2, String str3, boolean z, long j) {
        this.mOp = i;
        this.mUid = i2;
        this.mPackageName = str;
        this.mProxyAttributionTag = str3 == null ? "" : str3;
        this.mIsBackground = z;
        this.mAccessTime = j;
        this.mProxyPackageName = str2 == null ? "" : str2;
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

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            PermissionAccessInformation permissionAccessInformation = (PermissionAccessInformation) obj;
            boolean z = !"CHINA".equalsIgnoreCase(SystemProperties.get("ro.csc.country_code")) ? this.mAccessTime / 60000 != permissionAccessInformation.mAccessTime / 60000 : this.mAccessTime != permissionAccessInformation.mAccessTime;
            if (this.mOp == permissionAccessInformation.mOp && this.mUid == permissionAccessInformation.mUid && this.mIsBackground == permissionAccessInformation.mIsBackground && z && this.mPackageName.equals(permissionAccessInformation.mPackageName) && this.mProxyPackageName.equals(permissionAccessInformation.mProxyPackageName) && this.mProxyAttributionTag.equals(permissionAccessInformation.mProxyAttributionTag)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mOp), Integer.valueOf(this.mUid), this.mPackageName, this.mProxyPackageName, this.mProxyAttributionTag, Boolean.valueOf(this.mIsBackground), Long.valueOf(this.mAccessTime / 60000));
    }

    public String toString() {
        return "PermissionAccessInformation{op=" + this.mOp + ", uid=" + this.mUid + "', packageName='" + this.mPackageName + "', proxyPackageName='" + this.mProxyPackageName + "', proxyAttributionTag='" + this.mProxyAttributionTag + "', isBackground=" + this.mIsBackground + ", accessTime=" + this.mAccessTime + '}';
    }
}
