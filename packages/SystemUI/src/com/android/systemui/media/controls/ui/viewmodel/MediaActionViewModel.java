package com.android.systemui.media.controls.ui.viewmodel;

import android.graphics.drawable.Drawable;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class MediaActionViewModel {
    public final Drawable background;
    public final Integer buttonId;
    public final CharSequence contentDescription;
    public final Drawable icon;
    public final boolean isEnabled;
    public final boolean isVisibleWhenScrubbing;
    public final int notVisibleValue;
    public final Function1 onClicked;
    public final Integer rebindId;
    public final boolean showInCollapsed;

    public MediaActionViewModel(Drawable drawable, CharSequence charSequence, Drawable drawable2, boolean z, int i, boolean z2, Integer num, Integer num2, boolean z3, Function1 function1) {
        this.icon = drawable;
        this.contentDescription = charSequence;
        this.background = drawable2;
        this.isVisibleWhenScrubbing = z;
        this.notVisibleValue = i;
        this.showInCollapsed = z2;
        this.rebindId = num;
        this.buttonId = num2;
        this.isEnabled = z3;
        this.onClicked = function1;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaActionViewModel)) {
            return false;
        }
        MediaActionViewModel mediaActionViewModel = (MediaActionViewModel) obj;
        return Intrinsics.areEqual(this.icon, mediaActionViewModel.icon) && Intrinsics.areEqual(this.contentDescription, mediaActionViewModel.contentDescription) && Intrinsics.areEqual(this.background, mediaActionViewModel.background) && this.isVisibleWhenScrubbing == mediaActionViewModel.isVisibleWhenScrubbing && this.notVisibleValue == mediaActionViewModel.notVisibleValue && this.showInCollapsed == mediaActionViewModel.showInCollapsed && Intrinsics.areEqual(this.rebindId, mediaActionViewModel.rebindId) && Intrinsics.areEqual(this.buttonId, mediaActionViewModel.buttonId) && this.isEnabled == mediaActionViewModel.isEnabled && Intrinsics.areEqual(this.onClicked, mediaActionViewModel.onClicked);
    }

    public final int hashCode() {
        Drawable drawable = this.icon;
        int hashCode = (drawable == null ? 0 : drawable.hashCode()) * 31;
        CharSequence charSequence = this.contentDescription;
        int hashCode2 = (hashCode + (charSequence == null ? 0 : charSequence.hashCode())) * 31;
        Drawable drawable2 = this.background;
        int m = TransitionData$$ExternalSyntheticOutline0.m(KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.notVisibleValue, TransitionData$$ExternalSyntheticOutline0.m((hashCode2 + (drawable2 == null ? 0 : drawable2.hashCode())) * 31, 31, this.isVisibleWhenScrubbing), 31), 31, this.showInCollapsed);
        Integer num = this.rebindId;
        int hashCode3 = (m + (num == null ? 0 : num.hashCode())) * 31;
        Integer num2 = this.buttonId;
        return this.onClicked.hashCode() + TransitionData$$ExternalSyntheticOutline0.m((hashCode3 + (num2 != null ? num2.hashCode() : 0)) * 31, 31, this.isEnabled);
    }

    public final String toString() {
        Drawable drawable = this.icon;
        CharSequence charSequence = this.contentDescription;
        return "MediaActionViewModel(icon=" + drawable + ", contentDescription=" + ((Object) charSequence) + ", background=" + this.background + ", isVisibleWhenScrubbing=" + this.isVisibleWhenScrubbing + ", notVisibleValue=" + this.notVisibleValue + ", showInCollapsed=" + this.showInCollapsed + ", rebindId=" + this.rebindId + ", buttonId=" + this.buttonId + ", isEnabled=" + this.isEnabled + ", onClicked=" + this.onClicked + ")";
    }

    public /* synthetic */ MediaActionViewModel(Drawable drawable, CharSequence charSequence, Drawable drawable2, boolean z, int i, boolean z2, Integer num, Integer num2, boolean z3, Function1 function1, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(drawable, charSequence, drawable2, (i2 & 8) != 0 ? true : z, (i2 & 16) != 0 ? 8 : i, z2, (i2 & 64) != 0 ? null : num, (i2 & 128) != 0 ? null : num2, z3, function1);
    }
}
