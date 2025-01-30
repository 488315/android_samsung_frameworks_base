package com.android.systemui.media.controls.models.player;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaButton {
    public final MediaAction custom0;
    public final MediaAction custom1;
    public final MediaAction nextOrCustom;
    public final MediaAction playOrPause;
    public final MediaAction prevOrCustom;
    public final boolean reserveNext;
    public final boolean reservePrev;

    public MediaButton() {
        this(null, null, null, null, null, false, false, 127, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaButton)) {
            return false;
        }
        MediaButton mediaButton = (MediaButton) obj;
        return Intrinsics.areEqual(this.playOrPause, mediaButton.playOrPause) && Intrinsics.areEqual(this.nextOrCustom, mediaButton.nextOrCustom) && Intrinsics.areEqual(this.prevOrCustom, mediaButton.prevOrCustom) && Intrinsics.areEqual(this.custom0, mediaButton.custom0) && Intrinsics.areEqual(this.custom1, mediaButton.custom1) && this.reserveNext == mediaButton.reserveNext && this.reservePrev == mediaButton.reservePrev;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        MediaAction mediaAction = this.playOrPause;
        int hashCode = (mediaAction == null ? 0 : mediaAction.hashCode()) * 31;
        MediaAction mediaAction2 = this.nextOrCustom;
        int hashCode2 = (hashCode + (mediaAction2 == null ? 0 : mediaAction2.hashCode())) * 31;
        MediaAction mediaAction3 = this.prevOrCustom;
        int hashCode3 = (hashCode2 + (mediaAction3 == null ? 0 : mediaAction3.hashCode())) * 31;
        MediaAction mediaAction4 = this.custom0;
        int hashCode4 = (hashCode3 + (mediaAction4 == null ? 0 : mediaAction4.hashCode())) * 31;
        MediaAction mediaAction5 = this.custom1;
        int hashCode5 = (hashCode4 + (mediaAction5 != null ? mediaAction5.hashCode() : 0)) * 31;
        boolean z = this.reserveNext;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        int i2 = (hashCode5 + i) * 31;
        boolean z2 = this.reservePrev;
        return i2 + (z2 ? 1 : z2 ? 1 : 0);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("MediaButton(playOrPause=");
        sb.append(this.playOrPause);
        sb.append(", nextOrCustom=");
        sb.append(this.nextOrCustom);
        sb.append(", prevOrCustom=");
        sb.append(this.prevOrCustom);
        sb.append(", custom0=");
        sb.append(this.custom0);
        sb.append(", custom1=");
        sb.append(this.custom1);
        sb.append(", reserveNext=");
        sb.append(this.reserveNext);
        sb.append(", reservePrev=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(sb, this.reservePrev, ")");
    }

    public MediaButton(MediaAction mediaAction, MediaAction mediaAction2, MediaAction mediaAction3, MediaAction mediaAction4, MediaAction mediaAction5, boolean z, boolean z2) {
        this.playOrPause = mediaAction;
        this.nextOrCustom = mediaAction2;
        this.prevOrCustom = mediaAction3;
        this.custom0 = mediaAction4;
        this.custom1 = mediaAction5;
        this.reserveNext = z;
        this.reservePrev = z2;
    }

    public /* synthetic */ MediaButton(MediaAction mediaAction, MediaAction mediaAction2, MediaAction mediaAction3, MediaAction mediaAction4, MediaAction mediaAction5, boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : mediaAction, (i & 2) != 0 ? null : mediaAction2, (i & 4) != 0 ? null : mediaAction3, (i & 8) != 0 ? null : mediaAction4, (i & 16) != 0 ? null : mediaAction5, (i & 32) != 0 ? false : z, (i & 64) != 0 ? false : z2);
    }
}
