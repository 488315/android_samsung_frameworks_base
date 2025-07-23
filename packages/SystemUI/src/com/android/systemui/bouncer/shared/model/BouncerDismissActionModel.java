package com.android.systemui.bouncer.shared.model;

import com.android.systemui.plugins.ActivityStarter;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class BouncerDismissActionModel {
    public final Runnable onCancel;
    public final ActivityStarter.OnDismissAction onDismissAction;
    public final ActivityStarter.OnDismissAction onDismissActionForDex;

    public BouncerDismissActionModel(ActivityStarter.OnDismissAction onDismissAction, Runnable runnable, ActivityStarter.OnDismissAction onDismissAction2) {
        this.onDismissAction = onDismissAction;
        this.onCancel = runnable;
        this.onDismissActionForDex = onDismissAction2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BouncerDismissActionModel)) {
            return false;
        }
        BouncerDismissActionModel bouncerDismissActionModel = (BouncerDismissActionModel) obj;
        return Intrinsics.areEqual(this.onDismissAction, bouncerDismissActionModel.onDismissAction) && Intrinsics.areEqual(this.onCancel, bouncerDismissActionModel.onCancel) && Intrinsics.areEqual(this.onDismissActionForDex, bouncerDismissActionModel.onDismissActionForDex);
    }

    public final int hashCode() {
        ActivityStarter.OnDismissAction onDismissAction = this.onDismissAction;
        int hashCode = (onDismissAction == null ? 0 : onDismissAction.hashCode()) * 31;
        Runnable runnable = this.onCancel;
        int hashCode2 = (hashCode + (runnable == null ? 0 : runnable.hashCode())) * 31;
        ActivityStarter.OnDismissAction onDismissAction2 = this.onDismissActionForDex;
        return hashCode2 + (onDismissAction2 != null ? onDismissAction2.hashCode() : 0);
    }

    public final String toString() {
        return "BouncerDismissActionModel(onDismissAction=" + this.onDismissAction + ", onCancel=" + this.onCancel + ", onDismissActionForDex=" + this.onDismissActionForDex + ")";
    }

    public /* synthetic */ BouncerDismissActionModel(ActivityStarter.OnDismissAction onDismissAction, Runnable runnable, ActivityStarter.OnDismissAction onDismissAction2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(onDismissAction, runnable, (i & 4) != 0 ? null : onDismissAction2);
    }
}
