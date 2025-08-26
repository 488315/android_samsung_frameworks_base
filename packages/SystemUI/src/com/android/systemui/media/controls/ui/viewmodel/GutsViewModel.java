package com.android.systemui.media.controls.ui.viewmodel;

import android.graphics.drawable.Drawable;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class GutsViewModel {
    public final Drawable cancelTextBackground;
    public final CharSequence gutsText;
    public final boolean isDismissEnabled;
    public final Function0 onDismissClicked;
    public final Function0 onSettingsClicked;

    public GutsViewModel(CharSequence charSequence, boolean z, Function0 function0, Drawable drawable, Function0 function02) {
        this.gutsText = charSequence;
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
        return Intrinsics.areEqual(this.gutsText, gutsViewModel.gutsText) && this.isDismissEnabled == gutsViewModel.isDismissEnabled && Intrinsics.areEqual(this.onDismissClicked, gutsViewModel.onDismissClicked) && Intrinsics.areEqual(this.cancelTextBackground, gutsViewModel.cancelTextBackground) && Intrinsics.areEqual(this.onSettingsClicked, gutsViewModel.onSettingsClicked);
    }

    public final int hashCode() {
        int iHashCode = (this.onDismissClicked.hashCode() + TransitionData$$ExternalSyntheticOutline0.m(this.gutsText.hashCode() * 31, 31, this.isDismissEnabled)) * 31;
        Drawable drawable = this.cancelTextBackground;
        return this.onSettingsClicked.hashCode() + ((iHashCode + (drawable == null ? 0 : drawable.hashCode())) * 31);
    }

    public final String toString() {
        CharSequence charSequence = this.gutsText;
        return "GutsViewModel(gutsText=" + ((Object) charSequence) + ", isDismissEnabled=" + this.isDismissEnabled + ", onDismissClicked=" + this.onDismissClicked + ", cancelTextBackground=" + this.cancelTextBackground + ", onSettingsClicked=" + this.onSettingsClicked + ")";
    }

    public /* synthetic */ GutsViewModel(CharSequence charSequence, boolean z, Function0 function0, Drawable drawable, Function0 function02, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(charSequence, (i & 2) != 0 ? true : z, function0, drawable, function02);
    }
}
