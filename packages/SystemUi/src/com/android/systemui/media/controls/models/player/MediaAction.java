package com.android.systemui.media.controls.models.player;

import android.graphics.drawable.Drawable;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaAction {
    public final Runnable action;
    public final Drawable background;
    public final CharSequence contentDescription;
    public final Drawable icon;
    public final Integer rebindId;

    public MediaAction(Drawable drawable, Runnable runnable, CharSequence charSequence, Drawable drawable2, Integer num) {
        this.icon = drawable;
        this.action = runnable;
        this.contentDescription = charSequence;
        this.background = drawable2;
        this.rebindId = num;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaAction)) {
            return false;
        }
        MediaAction mediaAction = (MediaAction) obj;
        return Intrinsics.areEqual(this.icon, mediaAction.icon) && Intrinsics.areEqual(this.action, mediaAction.action) && Intrinsics.areEqual(this.contentDescription, mediaAction.contentDescription) && Intrinsics.areEqual(this.background, mediaAction.background) && Intrinsics.areEqual(this.rebindId, mediaAction.rebindId);
    }

    public final int hashCode() {
        Drawable drawable = this.icon;
        int hashCode = (drawable == null ? 0 : drawable.hashCode()) * 31;
        Runnable runnable = this.action;
        int hashCode2 = (hashCode + (runnable == null ? 0 : runnable.hashCode())) * 31;
        CharSequence charSequence = this.contentDescription;
        int hashCode3 = (hashCode2 + (charSequence == null ? 0 : charSequence.hashCode())) * 31;
        Drawable drawable2 = this.background;
        int hashCode4 = (hashCode3 + (drawable2 == null ? 0 : drawable2.hashCode())) * 31;
        Integer num = this.rebindId;
        return hashCode4 + (num != null ? num.hashCode() : 0);
    }

    public final String toString() {
        return "MediaAction(icon=" + this.icon + ", action=" + this.action + ", contentDescription=" + ((Object) this.contentDescription) + ", background=" + this.background + ", rebindId=" + this.rebindId + ")";
    }

    public /* synthetic */ MediaAction(Drawable drawable, Runnable runnable, CharSequence charSequence, Drawable drawable2, Integer num, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(drawable, runnable, charSequence, drawable2, (i & 16) != 0 ? null : num);
    }
}
