package androidx.compose.animation.graphics.vector;

import androidx.compose.animation.core.Transition;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.State;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
