package com.android.systemui.controls.ui;

import android.content.ComponentName;
import android.service.controls.Control;
import com.android.systemui.controls.controller.ControlInfo;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ControlWithState {

    /* renamed from: ci */
    public final ControlInfo f249ci;
    public final ComponentName componentName;
    public final Control control;

    public ControlWithState(ComponentName componentName, ControlInfo controlInfo, Control control) {
        this.componentName = componentName;
        this.f249ci = controlInfo;
        this.control = control;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ControlWithState)) {
            return false;
        }
        ControlWithState controlWithState = (ControlWithState) obj;
        return Intrinsics.areEqual(this.componentName, controlWithState.componentName) && Intrinsics.areEqual(this.f249ci, controlWithState.f249ci) && Intrinsics.areEqual(this.control, controlWithState.control);
    }

    public final int hashCode() {
        int hashCode = (this.f249ci.hashCode() + (this.componentName.hashCode() * 31)) * 31;
        Control control = this.control;
        return hashCode + (control == null ? 0 : control.hashCode());
    }

    public final String toString() {
        return "ControlWithState(componentName=" + this.componentName + ", ci=" + this.f249ci + ", control=" + this.control + ")";
    }
}
