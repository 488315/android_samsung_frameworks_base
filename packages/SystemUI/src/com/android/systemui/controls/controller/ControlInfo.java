package com.android.systemui.controls.controller;

import android.service.controls.Control;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class ControlInfo {
    public static final Companion Companion = new Companion(null);
    public final String controlId;
    public final CharSequence controlSubtitle;
    public final CharSequence controlTitle;
    public final int deviceType;
    public int layoutType;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public static ControlInfo fromControl(Control control) {
            ControlInfo controlInfo = new ControlInfo(control.getControlId(), control.getTitle(), control.getSubtitle(), control.getDeviceType(), 0, 16, null);
            controlInfo.layoutType = control.getCustomControl().getLayoutType();
            return controlInfo;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public ControlInfo(String str, CharSequence charSequence, CharSequence charSequence2, int i, int i2) {
        this.controlId = str;
        this.controlTitle = charSequence;
        this.controlSubtitle = charSequence2;
        this.deviceType = i;
        this.layoutType = i2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ControlInfo)) {
            return false;
        }
        ControlInfo controlInfo = (ControlInfo) obj;
        return Intrinsics.areEqual(this.controlId, controlInfo.controlId) && Intrinsics.areEqual(this.controlTitle, controlInfo.controlTitle) && Intrinsics.areEqual(this.controlSubtitle, controlInfo.controlSubtitle) && this.deviceType == controlInfo.deviceType && this.layoutType == controlInfo.layoutType;
    }

    public final int hashCode() {
        return Integer.hashCode(this.layoutType) + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.deviceType, ControlInfo$$ExternalSyntheticOutline0.m(ControlInfo$$ExternalSyntheticOutline0.m(this.controlId.hashCode() * 31, 31, this.controlTitle), 31, this.controlSubtitle), 31);
    }

    public final String toString() {
        CharSequence charSequence = this.controlTitle;
        return ": id = " + this.controlId + ", title = " + ((Object) charSequence) + ", deviceType = " + this.deviceType + ", layoutType = " + this.layoutType;
    }

    public /* synthetic */ ControlInfo(String str, CharSequence charSequence, CharSequence charSequence2, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, charSequence, charSequence2, i, (i3 & 16) != 0 ? 0 : i2);
    }
}
