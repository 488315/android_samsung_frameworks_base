package com.samsung.android.desktopsystemui.sharedlib.system;

import android.app.AppGlobals;
import android.permission.PermissionGroupUsage;
import android.permission.PermissionManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class PermissionManagerWrapper {
    private static final PermissionManagerWrapper sInstance = new PermissionManagerWrapper();
    private final PermissionManager mPermissionManager = (PermissionManager) AppGlobals.getInitialApplication().getSystemService(PermissionManager.class);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class PermissionGroupUsageItem {
        boolean mActive;
        CharSequence mAttributionLabel;
        CharSequence mAttributionTag;
        long mLastAccessTimeMillis;
        String mPackageName;
        String mPermissionGroupName;
        boolean mPhoneCall;
        CharSequence mProxyLabel;
        int mUid;

        public PermissionGroupUsageItem() {
        }

        public PermissionGroupUsageItem(PermissionGroupUsage permissionGroupUsage) {
            this.mPackageName = permissionGroupUsage.getPackageName();
            this.mUid = permissionGroupUsage.getUid();
            this.mLastAccessTimeMillis = permissionGroupUsage.getLastAccessTimeMillis();
            this.mPermissionGroupName = permissionGroupUsage.getPermissionGroupName();
            this.mActive = permissionGroupUsage.isActive();
            this.mPhoneCall = permissionGroupUsage.isPhoneCall();
            this.mAttributionTag = permissionGroupUsage.getAttributionTag();
            this.mAttributionLabel = permissionGroupUsage.getAttributionLabel();
            this.mProxyLabel = permissionGroupUsage.getProxyLabel();
        }

        public CharSequence getAttributionLabel() {
            return this.mAttributionLabel;
        }

        public CharSequence getAttributionTag() {
            return this.mAttributionTag;
        }

        public long getLastAccessTimeMillis() {
            return this.mLastAccessTimeMillis;
        }

        public String getPackageName() {
            return this.mPackageName;
        }

        public String getPermissionGroupName() {
            return this.mPermissionGroupName;
        }

        public CharSequence getProxyLabel() {
            return this.mProxyLabel;
        }

        public int getUid() {
            return this.mUid;
        }

        public boolean isActive() {
            return this.mActive;
        }

        public boolean isPhoneCall() {
            return this.mPhoneCall;
        }

        public String toString() {
            return "PermissionGroupUsage { packageName = " + this.mPackageName + ", uid = " + this.mUid + ", lastAccessTimeMillis = " + this.mLastAccessTimeMillis + ", permissionGroupName = " + this.mPermissionGroupName + ", active = " + this.mActive + ", phoneCall = " + this.mPhoneCall + ", attributionTag = " + ((Object) this.mAttributionTag) + ", attributionLabel = " + ((Object) this.mAttributionLabel) + ", proxyLabel = " + ((Object) this.mProxyLabel) + " }";
        }
    }

    public static PermissionManagerWrapper getInstance() {
        return sInstance;
    }

    public List<PermissionGroupUsageItem> permGroupUsage(boolean z) {
        List indicatorAppOpUsageData = this.mPermissionManager.getIndicatorAppOpUsageData(z);
        ArrayList arrayList = new ArrayList();
        Iterator it = indicatorAppOpUsageData.iterator();
        while (it.hasNext()) {
            arrayList.add(new PermissionGroupUsageItem((PermissionGroupUsage) it.next()));
        }
        return arrayList;
    }
}
