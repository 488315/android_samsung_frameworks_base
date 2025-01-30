package com.android.systemui.controls.management.model;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import com.android.systemui.controls.controller.ControlInfo;
import com.android.systemui.controls.management.model.MainModel;
import com.android.systemui.controls.p005ui.ControlWithState;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MainControlModel extends MainModel {
    public ControlWithState controlWithState;
    public boolean needToHide;
    public String structure;

    public /* synthetic */ MainControlModel(String str, ControlWithState controlWithState, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, controlWithState, (i & 4) != 0 ? false : z);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MainControlModel)) {
            return false;
        }
        MainControlModel mainControlModel = (MainControlModel) obj;
        return Intrinsics.areEqual(this.structure, mainControlModel.structure) && Intrinsics.areEqual(this.controlWithState, mainControlModel.controlWithState) && this.needToHide == mainControlModel.needToHide;
    }

    @Override // com.android.systemui.controls.management.model.MainModel
    public final MainModel.Type getType() {
        ControlInfo controlInfo;
        ControlWithState controlWithState = this.controlWithState;
        if (controlWithState != null && (controlInfo = controlWithState.f249ci) != null) {
            MainModel.Type type = controlInfo.customControlInfo.layoutType == 1 ? MainModel.Type.SMALL_CONTROL : MainModel.Type.CONTROL;
            if (type != null) {
                return type;
            }
        }
        return MainModel.Type.STRUCTURE;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        int hashCode = this.structure.hashCode() * 31;
        ControlWithState controlWithState = this.controlWithState;
        int hashCode2 = (hashCode + (controlWithState == null ? 0 : controlWithState.hashCode())) * 31;
        boolean z = this.needToHide;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        return hashCode2 + i;
    }

    public final String toString() {
        String str = this.structure;
        ControlWithState controlWithState = this.controlWithState;
        boolean z = this.needToHide;
        StringBuilder sb = new StringBuilder("MainControlModel(structure=");
        sb.append(str);
        sb.append(", controlWithState=");
        sb.append(controlWithState);
        sb.append(", needToHide=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(sb, z, ")");
    }

    public MainControlModel(String str, ControlWithState controlWithState, boolean z) {
        this.structure = str;
        this.controlWithState = controlWithState;
        this.needToHide = z;
    }
}
