package com.android.systemui.bouncer.shared.model;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class BouncerMessageModel {
    public final Message message;
    public final Message secondaryMessage;

    public BouncerMessageModel() {
        this(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BouncerMessageModel)) {
            return false;
        }
        BouncerMessageModel bouncerMessageModel = (BouncerMessageModel) obj;
        return Intrinsics.areEqual(this.message, bouncerMessageModel.message) && Intrinsics.areEqual(this.secondaryMessage, bouncerMessageModel.secondaryMessage);
    }

    public final int hashCode() {
        Message message = this.message;
        int hashCode = (message == null ? 0 : message.hashCode()) * 31;
        Message message2 = this.secondaryMessage;
        return hashCode + (message2 != null ? message2.hashCode() : 0);
    }

    public final String toString() {
        return "BouncerMessageModel(message=" + this.message + ", secondaryMessage=" + this.secondaryMessage + ")";
    }

    public BouncerMessageModel(Message message, Message message2) {
        this.message = message;
        this.secondaryMessage = message2;
    }

    public /* synthetic */ BouncerMessageModel(Message message, Message message2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : message, (i & 2) != 0 ? null : message2);
    }
}
