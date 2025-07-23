package com.android.systemui.statusbar.featurepods.popups.shared.model;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.android.systemui.common.shared.model.Icon;
import java.util.Objects;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class PopupChipModel {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Hidden extends PopupChipModel {
        public final PopupChipId chipId;
        public final boolean shouldAnimate;

        public /* synthetic */ Hidden(PopupChipId popupChipId, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(popupChipId, (i & 2) != 0 ? true : z);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Hidden)) {
                return false;
            }
            Hidden hidden = (Hidden) obj;
            return Intrinsics.areEqual(this.chipId, hidden.chipId) && this.shouldAnimate == hidden.shouldAnimate;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.shouldAnimate) + (this.chipId.hashCode() * 31);
        }

        public final String toString() {
            return "Hidden(chipId=" + this.chipId + ", shouldAnimate=" + this.shouldAnimate + ")";
        }

        public Hidden(PopupChipId popupChipId, boolean z) {
            super(null);
            this.chipId = popupChipId;
            this.shouldAnimate = z;
            Objects.toString(popupChipId);
        }
    }

    public /* synthetic */ PopupChipModel(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private PopupChipModel() {
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Shown extends PopupChipModel {
        public final PopupChipId chipId;
        public final String chipText;
        public final Function0 hidePopup;
        public final HoverBehavior hoverBehavior;
        public final Icon icon;
        public final boolean isPopupShown;
        public final Function0 showPopup;

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public /* synthetic */ Shown(com.android.systemui.statusbar.featurepods.popups.shared.model.PopupChipId r9, com.android.systemui.common.shared.model.Icon r10, java.lang.String r11, boolean r12, kotlin.jvm.functions.Function0 r13, kotlin.jvm.functions.Function0 r14, com.android.systemui.statusbar.featurepods.popups.shared.model.HoverBehavior r15, int r16, kotlin.jvm.internal.DefaultConstructorMarker r17) {
            /*
                r8 = this;
                r0 = r16 & 8
                if (r0 == 0) goto L5
                r12 = 0
            L5:
                r4 = r12
                r12 = r16 & 16
                if (r12 == 0) goto L10
                com.android.systemui.statusbar.featurepods.popups.shared.model.PopupChipModel$Shown$$ExternalSyntheticLambda0 r13 = new com.android.systemui.statusbar.featurepods.popups.shared.model.PopupChipModel$Shown$$ExternalSyntheticLambda0
                r12 = 0
                r13.<init>()
            L10:
                r5 = r13
                r12 = r16 & 32
                if (r12 == 0) goto L1b
                com.android.systemui.statusbar.featurepods.popups.shared.model.PopupChipModel$Shown$$ExternalSyntheticLambda0 r14 = new com.android.systemui.statusbar.featurepods.popups.shared.model.PopupChipModel$Shown$$ExternalSyntheticLambda0
                r12 = 1
                r14.<init>()
            L1b:
                r6 = r14
                r12 = r16 & 64
                if (r12 == 0) goto L28
                com.android.systemui.statusbar.featurepods.popups.shared.model.HoverBehavior$None r12 = com.android.systemui.statusbar.featurepods.popups.shared.model.HoverBehavior.None.INSTANCE
                r7 = r12
            L23:
                r0 = r8
                r1 = r9
                r2 = r10
                r3 = r11
                goto L2a
            L28:
                r7 = r15
                goto L23
            L2a:
                r0.<init>(r1, r2, r3, r4, r5, r6, r7)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.featurepods.popups.shared.model.PopupChipModel.Shown.<init>(com.android.systemui.statusbar.featurepods.popups.shared.model.PopupChipId, com.android.systemui.common.shared.model.Icon, java.lang.String, boolean, kotlin.jvm.functions.Function0, kotlin.jvm.functions.Function0, com.android.systemui.statusbar.featurepods.popups.shared.model.HoverBehavior, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Shown)) {
                return false;
            }
            Shown shown = (Shown) obj;
            return Intrinsics.areEqual(this.chipId, shown.chipId) && Intrinsics.areEqual(this.icon, shown.icon) && Intrinsics.areEqual(this.chipText, shown.chipText) && this.isPopupShown == shown.isPopupShown && Intrinsics.areEqual(this.showPopup, shown.showPopup) && Intrinsics.areEqual(this.hidePopup, shown.hidePopup) && Intrinsics.areEqual(this.hoverBehavior, shown.hoverBehavior);
        }

        public final int hashCode() {
            return this.hoverBehavior.hashCode() + ((this.hidePopup.hashCode() + ((this.showPopup.hashCode() + TransitionData$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m((this.icon.hashCode() + (this.chipId.hashCode() * 31)) * 31, 31, this.chipText), 31, this.isPopupShown)) * 31)) * 31);
        }

        public final String toString() {
            return "Shown(chipId=" + this.chipId + ", icon=" + this.icon + ", chipText=" + this.chipText + ", isPopupShown=" + this.isPopupShown + ", showPopup=" + this.showPopup + ", hidePopup=" + this.hidePopup + ", hoverBehavior=" + this.hoverBehavior + ")";
        }

        public Shown(PopupChipId popupChipId, Icon icon, String str, boolean z, Function0 function0, Function0 function02, HoverBehavior hoverBehavior) {
            super(null);
            this.chipId = popupChipId;
            this.icon = icon;
            this.chipText = str;
            this.isPopupShown = z;
            this.showPopup = function0;
            this.hidePopup = function02;
            this.hoverBehavior = hoverBehavior;
            Objects.toString(popupChipId);
        }
    }
}
