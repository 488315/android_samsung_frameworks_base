package androidx.transition;

import android.graphics.Rect;
import android.util.Property;
import android.view.View;

/* loaded from: classes.dex */
public class ViewUtils {
    public static final ViewUtilsApi29 IMPL = new ViewUtilsApi29();
    public static final AnonymousClass1 TRANSITION_ALPHA = new Property(Float.class, "translationAlpha") { // from class: androidx.transition.ViewUtils.1
        @Override // android.util.Property
        public final Object get(Object obj) {
            ViewUtils.IMPL.getClass();
            return Float.valueOf(((View) obj).getTransitionAlpha());
        }

        @Override // android.util.Property
        public final void set(Object obj, Object obj2) {
            ViewUtils.setTransitionAlpha(((Float) obj2).floatValue(), (View) obj);
        }
    };

    /* JADX WARN: Type inference failed for: r0v1, types: [androidx.transition.ViewUtils$1] */
    static {
        new Property(Rect.class, "clipBounds") { // from class: androidx.transition.ViewUtils.2
            @Override // android.util.Property
            public final Object get(Object obj) {
                return ((View) obj).getClipBounds();
            }

            @Override // android.util.Property
            public final void set(Object obj, Object obj2) {
                ((View) obj).setClipBounds((Rect) obj2);
            }
        };
    }

    private ViewUtils() {
    }

    public static void setLeftTopRightBottom(View view, int i, int i2, int i3, int i4) {
        IMPL.getClass();
        view.setLeftTopRightBottom(i, i2, i3, i4);
    }

    public static void setTransitionAlpha(float f, View view) {
        IMPL.getClass();
        view.setTransitionAlpha(f);
    }

    public static void setTransitionVisibility(View view, int i) {
        IMPL.getClass();
        view.setTransitionVisibility(i);
    }
}
