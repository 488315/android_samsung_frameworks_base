package com.android.systemui.kairos.internal;

import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class ActivationResult {
    public final NodeConnection connection;
    public final boolean needsEval;

    public ActivationResult(NodeConnection nodeConnection, boolean z) {
        this.connection = nodeConnection;
        this.needsEval = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ActivationResult)) {
            return false;
        }
        ActivationResult activationResult = (ActivationResult) obj;
        return Intrinsics.areEqual(this.connection, activationResult.connection) && this.needsEval == activationResult.needsEval;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.needsEval) + (this.connection.hashCode() * 31);
    }

    public final String toString() {
        return "ActivationResult(connection=" + this.connection + ", needsEval=" + this.needsEval + ")";
    }
}
