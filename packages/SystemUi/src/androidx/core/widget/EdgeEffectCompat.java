package androidx.core.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EdgeEffect;
import androidx.reflect.view.SeslViewRuneReflector;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class EdgeEffectCompat {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Api21Impl {
        private Api21Impl() {
        }

        public static void onPull(EdgeEffect edgeEffect, float f, float f2) {
            edgeEffect.onPull(f, f2);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Api31Impl {
        private Api31Impl() {
        }

        public static EdgeEffect create(Context context, AttributeSet attributeSet) {
            try {
                return new EdgeEffect(context, attributeSet);
            } catch (Throwable unused) {
                return new EdgeEffect(context);
            }
        }

        public static float getDistance(EdgeEffect edgeEffect) {
            try {
                return edgeEffect.getDistance();
            } catch (Throwable unused) {
                return 0.0f;
            }
        }

        public static float onPullDistance(EdgeEffect edgeEffect, float f, float f2) {
            try {
                return edgeEffect.onPullDistance(f, f2);
            } catch (Throwable unused) {
                edgeEffect.onPull(f, f2);
                return 0.0f;
            }
        }
    }

    @Deprecated
    public EdgeEffectCompat(Context context) {
        new EdgeEffect(context);
    }

    public static float getDistance(EdgeEffect edgeEffect) {
        if (SeslViewRuneReflector.isEdgeEffectStretchType()) {
            return Api31Impl.getDistance(edgeEffect);
        }
        return 0.0f;
    }

    public static float onPullDistance(EdgeEffect edgeEffect, float f, float f2) {
        if (SeslViewRuneReflector.isEdgeEffectStretchType()) {
            return Api31Impl.onPullDistance(edgeEffect, f, f2);
        }
        Api21Impl.onPull(edgeEffect, f, f2);
        return f;
    }
}
