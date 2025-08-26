package androidx.compose.animation.graphics.vector;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public abstract class PropertyValuesHolder1D<T> extends PropertyValuesHolder<T> {
    public final String propertyName;

    public /* synthetic */ PropertyValuesHolder1D(String str, DefaultConstructorMarker defaultConstructorMarker) {
        this(str);
    }

    private PropertyValuesHolder1D(String str) {
        super(null);
        this.propertyName = str;
    }
}
