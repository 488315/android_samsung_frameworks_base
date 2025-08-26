package com.android.systemui.util.kotlin;

import kotlin.coroutines.Continuation;

/* loaded from: classes3.dex */
public interface Producer<T> {
    Object get(Continuation continuation);
}
