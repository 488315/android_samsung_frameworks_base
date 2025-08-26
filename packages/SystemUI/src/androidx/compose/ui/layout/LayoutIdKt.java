package androidx.compose.ui.layout;

import androidx.compose.ui.Modifier;

/* loaded from: classes.dex */
public abstract class LayoutIdKt {
    public static final Object getLayoutId(Measurable measurable) {
        Object parentData = measurable.getParentData();
        LayoutIdParentData layoutIdParentData = parentData instanceof LayoutIdParentData ? (LayoutIdParentData) parentData : null;
        if (layoutIdParentData != null) {
            return ((LayoutIdModifier) layoutIdParentData).layoutId;
        }
        return null;
    }

    public static final Modifier layoutId(Modifier modifier, Object obj) {
        return modifier.then(new LayoutIdElement(obj));
    }
}
