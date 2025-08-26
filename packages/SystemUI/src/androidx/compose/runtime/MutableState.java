package androidx.compose.runtime;

import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public interface MutableState<T> extends State<T> {
    Object component1();

    Function1 component2();

    void setValue(Object obj);
}
