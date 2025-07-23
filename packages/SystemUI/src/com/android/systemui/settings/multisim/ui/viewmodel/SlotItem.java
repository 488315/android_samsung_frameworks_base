package com.android.systemui.settings.multisim.ui.viewmodel;

import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SlotItem {
    public final String carrierName;
    public final String phoneNumber;
    public final int simIconRes;
    public final String simName;

    public SlotItem(String str, int i, String str2, String str3) {
        this.simName = str;
        this.simIconRes = i;
        this.carrierName = str2;
        this.phoneNumber = str3;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SlotItem)) {
            return false;
        }
        SlotItem slotItem = (SlotItem) obj;
        return Intrinsics.areEqual(this.simName, slotItem.simName) && this.simIconRes == slotItem.simIconRes && Intrinsics.areEqual(this.carrierName, slotItem.carrierName) && Intrinsics.areEqual(this.phoneNumber, slotItem.phoneNumber);
    }

    public final int hashCode() {
        return this.phoneNumber.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(ReorderTile$$ExternalSyntheticOutline0.m(this.simIconRes, this.simName.hashCode() * 31, 31), 31, this.carrierName);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("SlotItem(simName=");
        sb.append(this.simName);
        sb.append(", simIconRes=");
        sb.append(this.simIconRes);
        sb.append(", carrierName=");
        sb.append(this.carrierName);
        sb.append(", phoneNumber=");
        return TransitionKt$$ExternalSyntheticOutline0.m(sb, this.phoneNumber, ")");
    }
}
