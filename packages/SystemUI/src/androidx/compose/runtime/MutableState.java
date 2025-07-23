package androidx.compose.runtime;

import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface MutableState<T> extends State<T> {
    Object component1();

    Function1 component2();

    void setValue(Object obj);
}
