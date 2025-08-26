package com.android.systemui.keyboard.shortcut.shared.model;

import android.graphics.drawable.Drawable;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public interface ShortcutKey {

    public interface Icon extends ShortcutKey {

        public final class DrawableIcon implements Icon {
            public final Drawable drawable;

            public DrawableIcon(Drawable drawable) {
                this.drawable = drawable;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                return (obj instanceof DrawableIcon) && Intrinsics.areEqual(this.drawable, ((DrawableIcon) obj).drawable);
            }

            public final int hashCode() {
                return this.drawable.hashCode();
            }

            public final String toString() {
                return "DrawableIcon(drawable=" + this.drawable + ")";
            }
        }

        public final class ResIdIcon implements Icon {
            public final int drawableResId;

            public ResIdIcon(int i) {
                this.drawableResId = i;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                return (obj instanceof ResIdIcon) && this.drawableResId == ((ResIdIcon) obj).drawableResId;
            }

            public final int hashCode() {
                return Integer.hashCode(this.drawableResId);
            }

            public final String toString() {
                return ReorderTile$$ExternalSyntheticOutline0.m(this.drawableResId, ")", new StringBuilder("ResIdIcon(drawableResId="));
            }
        }
    }

    public final class Text implements ShortcutKey {
        public final String value;

        public Text(String str) {
            this.value = str;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Text) && Intrinsics.areEqual(this.value, ((Text) obj).value);
        }

        public final int hashCode() {
            return this.value.hashCode();
        }

        public final String toString() {
            return TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder("Text(value="), this.value, ")");
        }
    }
}
