package com.android.systemui.statusbar.notification.row.ui.viewmodel;

import android.graphics.drawable.Drawable;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class SingleIcon extends ConversationAvatar {
    public final Drawable iconDrawable;

    public SingleIcon(Drawable drawable) {
        super(null);
        this.iconDrawable = drawable;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof SingleIcon) && Intrinsics.areEqual(this.iconDrawable, ((SingleIcon) obj).iconDrawable);
    }

    public final int hashCode() {
        Drawable drawable = this.iconDrawable;
        if (drawable == null) {
            return 0;
        }
        return drawable.hashCode();
    }

    public final String toString() {
        return "SingleIcon(iconDrawable=" + this.iconDrawable + ")";
    }
}
