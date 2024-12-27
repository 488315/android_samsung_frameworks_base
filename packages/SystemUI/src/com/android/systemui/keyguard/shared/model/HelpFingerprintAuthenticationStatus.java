package com.android.systemui.keyguard.shared.model;

import kotlin.jvm.internal.Intrinsics;

public final class HelpFingerprintAuthenticationStatus extends FingerprintAuthenticationStatus {
    public final String msg;
    public final int msgId;

    public HelpFingerprintAuthenticationStatus(int i, String str) {
        super(null, null);
        this.msgId = i;
        this.msg = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof HelpFingerprintAuthenticationStatus)) {
            return false;
        }
        HelpFingerprintAuthenticationStatus helpFingerprintAuthenticationStatus = (HelpFingerprintAuthenticationStatus) obj;
        return this.msgId == helpFingerprintAuthenticationStatus.msgId && Intrinsics.areEqual(this.msg, helpFingerprintAuthenticationStatus.msg);
    }

    public final int hashCode() {
        int hashCode = Integer.hashCode(this.msgId) * 31;
        String str = this.msg;
        return hashCode + (str == null ? 0 : str.hashCode());
    }

    public final String toString() {
        return "HelpFingerprintAuthenticationStatus(msgId=" + this.msgId + ", msg=" + this.msg + ")";
    }
}
