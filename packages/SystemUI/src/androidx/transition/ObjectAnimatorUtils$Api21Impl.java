package androidx.transition;

import android.animation.ObjectAnimator;
import android.animation.TypeConverter;
import android.graphics.Path;
import android.util.Property;

/* loaded from: classes.dex */
public class ObjectAnimatorUtils$Api21Impl {
    private ObjectAnimatorUtils$Api21Impl() {
    }

    public static <T, V> ObjectAnimator ofObject(T t, Property<T, V> property, Path path) {
        return ObjectAnimator.ofObject(t, property, (TypeConverter) null, path);
    }
}
