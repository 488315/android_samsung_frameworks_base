package com.android.systemui.keyguard.shared.model;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ErrorAuthenticationStatus extends AuthenticationStatus {
    public final String msg;
    public final int msgId;

    public /* synthetic */ ErrorAuthenticationStatus(int i, String str, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, (i2 & 2) != 0 ? null : str);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ErrorAuthenticationStatus)) {
            return false;
        }
        ErrorAuthenticationStatus errorAuthenticationStatus = (ErrorAuthenticationStatus) obj;
        return this.msgId == errorAuthenticationStatus.msgId && Intrinsics.areEqual(this.msg, errorAuthenticationStatus.msg);
    }

    public final int hashCode() {
        int hashCode = Integer.hashCode(this.msgId) * 31;
        String str = this.msg;
        return hashCode + (str == null ? 0 : str.hashCode());
    }

    public final String toString() {
        return "ErrorAuthenticationStatus(msgId=" + this.msgId + ", msg=" + this.msg + ")";
    }

    public ErrorAuthenticationStatus(int i, String str) {
        super(null);
        this.msgId = i;
        this.msg = str;
    }
}
