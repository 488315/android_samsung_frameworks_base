package com.android.systemui.screenshot.ui.viewmodel;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ActionButtonViewModel {
    public static final Companion Companion = new Companion(null);
    public static int nextId;
    public final ActionButtonAppearance appearance;
    public final int id;
    public final Function0 onClicked;
    public final boolean showDuringEntrance;
    public final boolean visible;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public static ActionButtonViewModel withNextId(ActionButtonAppearance actionButtonAppearance, Function0 function0) {
            int i = ActionButtonViewModel.nextId;
            ActionButtonViewModel.nextId = i + 1;
            return new ActionButtonViewModel(actionButtonAppearance, i, true, true, function0);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public ActionButtonViewModel(ActionButtonAppearance actionButtonAppearance, int i, boolean z, boolean z2, Function0 function0) {
        this.appearance = actionButtonAppearance;
        this.id = i;
        this.visible = z;
        this.showDuringEntrance = z2;
        this.onClicked = function0;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ActionButtonViewModel)) {
            return false;
        }
        ActionButtonViewModel actionButtonViewModel = (ActionButtonViewModel) obj;
        return Intrinsics.areEqual(this.appearance, actionButtonViewModel.appearance) && this.id == actionButtonViewModel.id && this.visible == actionButtonViewModel.visible && this.showDuringEntrance == actionButtonViewModel.showDuringEntrance && Intrinsics.areEqual(this.onClicked, actionButtonViewModel.onClicked);
    }

    public final int hashCode() {
        int m = TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.id, this.appearance.hashCode() * 31, 31), 31, this.visible), 31, this.showDuringEntrance);
        Function0 function0 = this.onClicked;
        return m + (function0 == null ? 0 : function0.hashCode());
    }

    public final String toString() {
        return "ActionButtonViewModel(appearance=" + this.appearance + ", id=" + this.id + ", visible=" + this.visible + ", showDuringEntrance=" + this.showDuringEntrance + ", onClicked=" + this.onClicked + ")";
    }
}
