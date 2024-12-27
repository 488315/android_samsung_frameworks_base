package com.android.systemui.media.controls.ui.viewmodel;

import android.graphics.drawable.Drawable;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import com.android.systemui.controls.controller.ControlInfo$$ExternalSyntheticOutline0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class MediaRecViewModel {
    public final Drawable albumIcon;
    public final Drawable appIcon;
    public final CharSequence contentDescription;
    public final Function2 onClicked;
    public final int progress;
    public final int progressColor;
    public final CharSequence subtitle;
    public final int subtitleColor;
    public final CharSequence title;
    public final int titleColor;

    public MediaRecViewModel(CharSequence charSequence, CharSequence charSequence2, int i, CharSequence charSequence3, int i2, int i3, int i4, Drawable drawable, Drawable drawable2, Function2 function2) {
        this.contentDescription = charSequence;
        this.title = charSequence2;
        this.titleColor = i;
        this.subtitle = charSequence3;
        this.subtitleColor = i2;
        this.progress = i3;
        this.progressColor = i4;
        this.albumIcon = drawable;
        this.appIcon = drawable2;
        this.onClicked = function2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaRecViewModel)) {
            return false;
        }
        MediaRecViewModel mediaRecViewModel = (MediaRecViewModel) obj;
        return Intrinsics.areEqual(this.contentDescription, mediaRecViewModel.contentDescription) && Intrinsics.areEqual(this.title, mediaRecViewModel.title) && this.titleColor == mediaRecViewModel.titleColor && Intrinsics.areEqual(this.subtitle, mediaRecViewModel.subtitle) && this.subtitleColor == mediaRecViewModel.subtitleColor && this.progress == mediaRecViewModel.progress && this.progressColor == mediaRecViewModel.progressColor && Intrinsics.areEqual(this.albumIcon, mediaRecViewModel.albumIcon) && Intrinsics.areEqual(this.appIcon, mediaRecViewModel.appIcon) && Intrinsics.areEqual(this.onClicked, mediaRecViewModel.onClicked);
    }

    public final int hashCode() {
        int m = KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.progressColor, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.progress, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.subtitleColor, ControlInfo$$ExternalSyntheticOutline0.m(KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.titleColor, ControlInfo$$ExternalSyntheticOutline0.m(this.contentDescription.hashCode() * 31, 31, this.title), 31), 31, this.subtitle), 31), 31), 31);
        Drawable drawable = this.albumIcon;
        int hashCode = (m + (drawable == null ? 0 : drawable.hashCode())) * 31;
        Drawable drawable2 = this.appIcon;
        return this.onClicked.hashCode() + ((hashCode + (drawable2 != null ? drawable2.hashCode() : 0)) * 31);
    }

    public final String toString() {
        CharSequence charSequence = this.contentDescription;
        CharSequence charSequence2 = this.title;
        CharSequence charSequence3 = this.subtitle;
        return "MediaRecViewModel(contentDescription=" + ((Object) charSequence) + ", title=" + ((Object) charSequence2) + ", titleColor=" + this.titleColor + ", subtitle=" + ((Object) charSequence3) + ", subtitleColor=" + this.subtitleColor + ", progress=" + this.progress + ", progressColor=" + this.progressColor + ", albumIcon=" + this.albumIcon + ", appIcon=" + this.appIcon + ", onClicked=" + this.onClicked + ")";
    }

    public /* synthetic */ MediaRecViewModel(CharSequence charSequence, CharSequence charSequence2, int i, CharSequence charSequence3, int i2, int i3, int i4, Drawable drawable, Drawable drawable2, Function2 function2, int i5, DefaultConstructorMarker defaultConstructorMarker) {
        this(charSequence, (i5 & 2) != 0 ? "" : charSequence2, i, (i5 & 8) != 0 ? "" : charSequence3, i2, (i5 & 32) != 0 ? 0 : i3, i4, (i5 & 128) != 0 ? null : drawable, (i5 & 256) != 0 ? null : drawable2, function2);
    }
}
