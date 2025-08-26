package androidx.compose.runtime;

import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
public interface Composition {
    void dispose();

    boolean isDisposed();

    void setContent(Function2 function2);
}
