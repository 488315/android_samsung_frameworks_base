package com.android.server.enterprise.accessControl;

import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.os.Binder;

import com.android.server.enterprise.utils.Utils;

import com.samsung.android.knox.ContextInfo;

import java.util.List;

public final class AccessControl {
    public String mApi;
    public ContextInfo mContextInfo;
    public int mContextUid;
    public boolean mIsDangerousApi;
    public boolean mIsDoPoEnforce;
    public boolean mIsOwnerOnly;
    public boolean mIsUserPolicy;
    public List mPermissions;
    public int mPid;
    public int mScope;
    public boolean mShouldCheckAdmin;
    public int mUid;

    public AccessControl(int i, String str, boolean z) {
        this(true, false, z, true, false, List.of(str), i);
    }

    public AccessControl(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, String str) {
        this(z, z2, z3, z4, z5, List.of(str), 0);
    }

    public AccessControl(
            boolean z, boolean z2, boolean z3, boolean z4, boolean z5, List list, int i) {
        this.mUid = -1;
        this.mPid = -1;
        this.mContextUid = -1;
        this.mIsOwnerOnly = z2;
        this.mIsUserPolicy = z;
        this.mIsDoPoEnforce = z4;
        this.mShouldCheckAdmin = z3;
        this.mIsDangerousApi = z5;
        this.mPermissions = list;
        this.mScope = i;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public final AccessControl m493clone() {
        AccessControl accessControl = new AccessControl();
        accessControl.mUid = -1;
        accessControl.mPid = -1;
        accessControl.mContextUid = -1;
        accessControl.mUid = this.mUid;
        accessControl.mContextUid = this.mContextUid;
        accessControl.mScope = this.mScope;
        accessControl.mApi = this.mApi;
        accessControl.mIsOwnerOnly = this.mIsOwnerOnly;
        accessControl.mIsUserPolicy = this.mIsUserPolicy;
        accessControl.mIsDoPoEnforce = this.mIsDoPoEnforce;
        accessControl.mShouldCheckAdmin = this.mShouldCheckAdmin;
        accessControl.mIsDangerousApi = this.mIsDangerousApi;
        accessControl.mContextInfo = this.mContextInfo;
        accessControl.mPermissions = this.mPermissions;
        return accessControl;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("{ mUid = ");
        sb.append(this.mUid);
        sb.append(" | mPid = ");
        sb.append(this.mPid);
        sb.append(" | mContextUid = ");
        sb.append(this.mContextUid);
        sb.append(" | mIsOwnerOnly = ");
        sb.append(this.mIsOwnerOnly);
        sb.append(" | mIsUserPolicy = ");
        sb.append(this.mIsUserPolicy);
        sb.append(" | mIsDoPoEnforce = ");
        sb.append(this.mIsDoPoEnforce);
        sb.append(" | mShouldCheckAdmin = ");
        sb.append(this.mShouldCheckAdmin);
        sb.append(" | mIsDangerousApi = ");
        sb.append(this.mIsDangerousApi);
        sb.append(" | mPermissions = ");
        sb.append(this.mPermissions);
        sb.append(" | mScopes = ");
        return AmFmBandRange$$ExternalSyntheticOutline0.m(this.mScope, sb, " }");
    }

    public final void updateCallerInfo(ContextInfo contextInfo) {
        this.mUid = Binder.getCallingUid();
        this.mPid = Binder.getCallingPid();
        this.mContextUid = Utils.getCallingOrUserUid(contextInfo);
        this.mContextInfo = contextInfo;
        if (contextInfo == null) {
            this.mContextInfo = new ContextInfo(Binder.getCallingUid());
        }
    }
}
