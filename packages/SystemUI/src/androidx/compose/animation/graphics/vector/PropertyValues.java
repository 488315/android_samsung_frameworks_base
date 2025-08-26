package androidx.compose.animation.graphics.vector;

import androidx.compose.animation.core.Transition;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.State;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public abstract class PropertyValues<T> {
    public final List timestamps;

    public /* synthetic */ PropertyValues(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract State createState(Transition transition, String str, int i, Composer composer, int i2);

    private PropertyValues() {
        this.timestamps = new ArrayList();
    }
}
