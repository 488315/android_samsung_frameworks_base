package com.android.systemui.statusbar.featurepods.popups.shared.model;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.statusbar.featurepods.popups.shared.model.HoverBehavior;
import java.util.Objects;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public abstract class PopupChipModel {

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

    public final class Shown extends PopupChipModel {
        public final PopupChipId chipId;
        public final String chipText;
        public final Function0 hidePopup;
        public final HoverBehavior hoverBehavior;
        public final Icon icon;
        public final boolean isPopupShown;
        public final Function0 showPopup;

        public /* synthetic */ Shown(PopupChipId popupChipId, Icon icon, String str, boolean z, Function0 function0, Function0 function02, HoverBehavior hoverBehavior, int i, DefaultConstructorMarker defaultConstructorMarker) {
            boolean z2 = (i & 8) != 0 ? false : z;
            if ((i & 16) != 0) {
                final int i2 = 0;
                function0 = new Function0() { // from class: com.android.systemui.statusbar.featurepods.popups.shared.model.PopupChipModel$Shown$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        switch (i2) {
                        }
                        return Unit.INSTANCE;
                    }
                };
            }
            Function0 function03 = function0;
            if ((i & 32) != 0) {
                final int i3 = 1;
                function02 = new Function0() { // from class: com.android.systemui.statusbar.featurepods.popups.shared.model.PopupChipModel$Shown$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        switch (i3) {
                        }
                        return Unit.INSTANCE;
                    }
                };
            }
            this(popupChipId, icon, str, z2, function03, function02, (i & 64) != 0 ? HoverBehavior.None.INSTANCE : hoverBehavior);
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
