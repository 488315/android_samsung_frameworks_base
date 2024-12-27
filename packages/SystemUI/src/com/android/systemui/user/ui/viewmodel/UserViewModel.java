package com.android.systemui.user.ui.viewmodel;

import android.graphics.drawable.Drawable;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import com.android.systemui.common.shared.model.Text;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class UserViewModel {
    public final float alpha;
    public final Drawable image;
    public final boolean isSelectionMarkerVisible;
    public final Text name;
    public final Function0 onClicked;
    public final int viewKey;

    public UserViewModel(int i, Text text, Drawable drawable, boolean z, float f, Function0 function0) {
        this.viewKey = i;
        this.name = text;
        this.image = drawable;
        this.isSelectionMarkerVisible = z;
        this.alpha = f;
        this.onClicked = function0;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof UserViewModel)) {
            return false;
        }
        UserViewModel userViewModel = (UserViewModel) obj;
        return this.viewKey == userViewModel.viewKey && Intrinsics.areEqual(this.name, userViewModel.name) && Intrinsics.areEqual(this.image, userViewModel.image) && this.isSelectionMarkerVisible == userViewModel.isSelectionMarkerVisible && Float.compare(this.alpha, userViewModel.alpha) == 0 && Intrinsics.areEqual(this.onClicked, userViewModel.onClicked);
    }

    public final int hashCode() {
        int m = FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.alpha, TransitionData$$ExternalSyntheticOutline0.m((this.image.hashCode() + ((this.name.hashCode() + (Integer.hashCode(this.viewKey) * 31)) * 31)) * 31, 31, this.isSelectionMarkerVisible), 31);
        Function0 function0 = this.onClicked;
        return m + (function0 == null ? 0 : function0.hashCode());
    }

    public final String toString() {
        return "UserViewModel(viewKey=" + this.viewKey + ", name=" + this.name + ", image=" + this.image + ", isSelectionMarkerVisible=" + this.isSelectionMarkerVisible + ", alpha=" + this.alpha + ", onClicked=" + this.onClicked + ")";
    }
}
