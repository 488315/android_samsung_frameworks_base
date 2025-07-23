package com.samsung.android.kmxservice.ai.privacy;

import android.content.ContentValues;
import java.util.HashMap;
import java.util.Objects;

/* loaded from: classes6.dex */
public class PermissionData {
    private static final long MIN_MS_SEC = 60000;
    private static final HashMap<Integer, Boolean> isPermissionGroupForExcessiveUsage = new HashMap<Integer, Boolean>() { // from class: com.samsung.android.kmxservice.ai.privacy.PermissionData.1
        {
            put(0, true);
            put(1, true);
            put(41, true);
            put(42, true);
            put(26, true);
            put(27, true);
        }
    };
    private long mAccessTimeMilliSeconds;
    private long mAccessTimeMinute;
    private int mOp;
    private String mPackageName;
    private int mState;
    private int mUid;

    public PermissionData(int i, int i2, String str, int i3) {
        this.mOp = i;
        this.mUid = i2;
        this.mPackageName = str;
        this.mState = i3;
        long currentTimeMillis = System.currentTimeMillis();
        this.mAccessTimeMilliSeconds = currentTimeMillis;
        this.mAccessTimeMinute = currentTimeMillis / 60000;
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

    public int getState() {
        return this.mState;
    }

    public long getAccessTime() {
        return this.mAccessTimeMilliSeconds;
    }

    public ContentValues toContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("op", String.valueOf(this.mOp));
        contentValues.put("uid", String.valueOf(this.mUid));
        contentValues.put("packageName", this.mPackageName);
        contentValues.put("state", String.valueOf(this.mState));
        contentValues.put("accessTime", String.valueOf(this.mAccessTimeMinute));
        return contentValues;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mOp), Integer.valueOf(this.mUid), this.mPackageName, Integer.valueOf(this.mState), Long.valueOf(this.mAccessTimeMinute));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            PermissionData permissionData = (PermissionData) obj;
            boolean z = !isOpExcessive(permissionData.mOp) ? this.mAccessTimeMinute != permissionData.mAccessTimeMinute : this.mAccessTimeMilliSeconds != permissionData.mAccessTimeMilliSeconds;
            if (this.mOp == permissionData.mOp && this.mUid == permissionData.mUid && this.mState == permissionData.mState && z && this.mPackageName.equals(permissionData.mPackageName)) {
                return true;
            }
        }
        return false;
    }

    private boolean isOpExcessive(int i) {
        return isPermissionGroupForExcessiveUsage.containsKey(Integer.valueOf(i));
    }
}
