package com.android.systemui.util.drawable;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedImageDrawable;
import android.graphics.drawable.AnimatedRotateDrawable;
import android.graphics.drawable.AnimatedStateListDrawable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DrawableSize {
    public static final Companion Companion = new Companion(null);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static boolean isSimpleBitmap(Drawable drawable) {
            if (drawable.isStateful()) {
                return false;
            }
            return !((drawable instanceof Animatable) || (drawable instanceof Animatable2) || (drawable instanceof AnimatedImageDrawable) || (drawable instanceof AnimatedRotateDrawable) || (drawable instanceof AnimatedStateListDrawable) || (drawable instanceof AnimatedVectorDrawable));
        }
    }
}
