package com.android.systemui.keyguard.bouncer.shared.model;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class BouncerMessageModel {
    public final Message message;
    public final Message secondaryMessage;

    /* JADX WARN: Multi-variable type inference failed */
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
