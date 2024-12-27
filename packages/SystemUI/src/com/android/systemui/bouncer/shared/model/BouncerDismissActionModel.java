package com.android.systemui.bouncer.shared.model;

import com.android.systemui.plugins.ActivityStarter;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class BouncerDismissActionModel {
    public final Runnable onCancel;
    public final ActivityStarter.OnDismissAction onDismissAction;

    public BouncerDismissActionModel(ActivityStarter.OnDismissAction onDismissAction, Runnable runnable) {
        this.onDismissAction = onDismissAction;
        this.onCancel = runnable;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BouncerDismissActionModel)) {
            return false;
        }
        BouncerDismissActionModel bouncerDismissActionModel = (BouncerDismissActionModel) obj;
        return Intrinsics.areEqual(this.onDismissAction, bouncerDismissActionModel.onDismissAction) && Intrinsics.areEqual(this.onCancel, bouncerDismissActionModel.onCancel);
    }

    public final int hashCode() {
        ActivityStarter.OnDismissAction onDismissAction = this.onDismissAction;
        int hashCode = (onDismissAction == null ? 0 : onDismissAction.hashCode()) * 31;
        Runnable runnable = this.onCancel;
        return hashCode + (runnable != null ? runnable.hashCode() : 0);
    }

    public final String toString() {
        return "BouncerDismissActionModel(onDismissAction=" + this.onDismissAction + ", onCancel=" + this.onCancel + ")";
    }
}
