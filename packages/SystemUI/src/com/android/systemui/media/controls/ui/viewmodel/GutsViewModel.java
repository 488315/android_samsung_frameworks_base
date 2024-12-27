package com.android.systemui.media.controls.ui.viewmodel;

import android.graphics.drawable.Drawable;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class GutsViewModel {
    public final int accentPrimaryColor;
    public final Drawable cancelTextBackground;
    public final CharSequence gutsText;
    public final boolean isDismissEnabled;
    public final Function0 onDismissClicked;
    public final Function0 onSettingsClicked;
    public final int surfaceColor;
    public final int textPrimaryColor;

    public GutsViewModel(CharSequence charSequence, int i, int i2, int i3, boolean z, Function0 function0, Drawable drawable, Function0 function02) {
        this.gutsText = charSequence;
        this.textPrimaryColor = i;
        this.accentPrimaryColor = i2;
        this.surfaceColor = i3;
        this.isDismissEnabled = z;
        this.onDismissClicked = function0;
        this.cancelTextBackground = drawable;
        this.onSettingsClicked = function02;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof GutsViewModel)) {
            return false;
        }
        GutsViewModel gutsViewModel = (GutsViewModel) obj;
        return Intrinsics.areEqual(this.gutsText, gutsViewModel.gutsText) && this.textPrimaryColor == gutsViewModel.textPrimaryColor && this.accentPrimaryColor == gutsViewModel.accentPrimaryColor && this.surfaceColor == gutsViewModel.surfaceColor && this.isDismissEnabled == gutsViewModel.isDismissEnabled && Intrinsics.areEqual(this.onDismissClicked, gutsViewModel.onDismissClicked) && Intrinsics.areEqual(this.cancelTextBackground, gutsViewModel.cancelTextBackground) && Intrinsics.areEqual(this.onSettingsClicked, gutsViewModel.onSettingsClicked);
    }

    public final int hashCode() {
        int hashCode = (this.onDismissClicked.hashCode() + TransitionData$$ExternalSyntheticOutline0.m(KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.surfaceColor, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.accentPrimaryColor, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.textPrimaryColor, this.gutsText.hashCode() * 31, 31), 31), 31), 31, this.isDismissEnabled)) * 31;
        Drawable drawable = this.cancelTextBackground;
        return this.onSettingsClicked.hashCode() + ((hashCode + (drawable == null ? 0 : drawable.hashCode())) * 31);
    }

    public final String toString() {
        CharSequence charSequence = this.gutsText;
        return "GutsViewModel(gutsText=" + ((Object) charSequence) + ", textPrimaryColor=" + this.textPrimaryColor + ", accentPrimaryColor=" + this.accentPrimaryColor + ", surfaceColor=" + this.surfaceColor + ", isDismissEnabled=" + this.isDismissEnabled + ", onDismissClicked=" + this.onDismissClicked + ", cancelTextBackground=" + this.cancelTextBackground + ", onSettingsClicked=" + this.onSettingsClicked + ")";
    }

    public /* synthetic */ GutsViewModel(CharSequence charSequence, int i, int i2, int i3, boolean z, Function0 function0, Drawable drawable, Function0 function02, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this(charSequence, i, i2, i3, (i4 & 16) != 0 ? true : z, function0, drawable, function02);
    }
}
