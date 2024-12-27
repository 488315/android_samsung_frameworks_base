package com.android.server.permission.jarjar.kotlin.collections;

import com.android.server.accessibility.magnification.WindowMagnificationGestureHandler$$ExternalSyntheticOutline0;
import com.android.server.permission.jarjar.kotlin.jvm.functions.Function1;
import com.android.server.permission.jarjar.kotlin.jvm.internal.Lambda;

final class CollectionsKt___CollectionsKt$elementAt$1 extends Lambda implements Function1 {
    final /* synthetic */ int $index;

    public CollectionsKt___CollectionsKt$elementAt$1(int i) {
        this.$index = i;
    }

    @Override // com.android.server.permission.jarjar.kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        ((Number) obj).intValue();
        throw new IndexOutOfBoundsException(
                WindowMagnificationGestureHandler$$ExternalSyntheticOutline0.m(
                        new StringBuilder("Collection doesn't contain element at index "),
                        this.$index,
                        '.'));
    }
}
