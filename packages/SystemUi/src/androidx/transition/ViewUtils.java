package androidx.transition;

import android.graphics.Rect;
import android.util.Property;
import android.view.View;
import androidx.core.view.ViewCompat;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ViewUtils {
    public static final ViewUtilsApi29 IMPL = new ViewUtilsApi29();
    public static final C05531 TRANSITION_ALPHA = new Property(Float.class, "translationAlpha") { // from class: androidx.transition.ViewUtils.1
        @Override // android.util.Property
        public final Object get(Object obj) {
            ViewUtils.IMPL.getClass();
            return Float.valueOf(((View) obj).getTransitionAlpha());
        }

        @Override // android.util.Property
        public final void set(Object obj, Object obj2) {
            ViewUtils.setTransitionAlpha((View) obj, ((Float) obj2).floatValue());
        }
    };
    public static final C05542 CLIP_BOUNDS = new Property(Rect.class, "clipBounds") { // from class: androidx.transition.ViewUtils.2
        @Override // android.util.Property
        public final Object get(Object obj) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            return ViewCompat.Api18Impl.getClipBounds((View) obj);
        }

        @Override // android.util.Property
        public final void set(Object obj, Object obj2) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api18Impl.setClipBounds((View) obj, (Rect) obj2);
        }
    };

    private ViewUtils() {
    }

    public static void setLeftTopRightBottom(View view, int i, int i2, int i3, int i4) {
        IMPL.getClass();
        view.setLeftTopRightBottom(i, i2, i3, i4);
    }

    public static void setTransitionAlpha(View view, float f) {
        IMPL.getClass();
        view.setTransitionAlpha(f);
    }

    public static void setTransitionVisibility(View view, int i) {
        IMPL.getClass();
        view.setTransitionVisibility(i);
    }
}
