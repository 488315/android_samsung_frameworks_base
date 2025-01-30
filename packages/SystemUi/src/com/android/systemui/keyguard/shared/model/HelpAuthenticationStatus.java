package com.android.systemui.keyguard.shared.model;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class HelpAuthenticationStatus extends AuthenticationStatus {
    public final String msg;
    public final int msgId;

    public HelpAuthenticationStatus(int i, String str) {
        super(null);
        this.msgId = i;
        this.msg = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof HelpAuthenticationStatus)) {
            return false;
        }
        HelpAuthenticationStatus helpAuthenticationStatus = (HelpAuthenticationStatus) obj;
        return this.msgId == helpAuthenticationStatus.msgId && Intrinsics.areEqual(this.msg, helpAuthenticationStatus.msg);
    }

    public final int hashCode() {
        int hashCode = Integer.hashCode(this.msgId) * 31;
        String str = this.msg;
        return hashCode + (str == null ? 0 : str.hashCode());
    }

    public final String toString() {
        return "HelpAuthenticationStatus(msgId=" + this.msgId + ", msg=" + this.msg + ")";
    }
}
