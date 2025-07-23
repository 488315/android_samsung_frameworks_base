package androidx.compose.ui.modifier;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ModifierLocal<T> {
    public final Function0 defaultFactory;

    public /* synthetic */ ModifierLocal(Function0 function0, DefaultConstructorMarker defaultConstructorMarker) {
        this(function0);
    }

    private ModifierLocal(Function0 function0) {
        this.defaultFactory = function0;
    }
}
