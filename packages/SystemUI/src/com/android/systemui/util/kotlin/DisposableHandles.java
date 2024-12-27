package com.android.systemui.util.kotlin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlinx.coroutines.DisposableHandle;

public final class DisposableHandles implements DisposableHandle {
    public static final int $stable = 8;
    private final List<DisposableHandle> handles = new ArrayList();

    public final void add(DisposableHandle... disposableHandleArr) {
        CollectionsKt__MutableCollectionsKt.addAll(this.handles, disposableHandleArr);
    }

    @Override // kotlinx.coroutines.DisposableHandle
    public void dispose() {
        Iterator<T> it = this.handles.iterator();
        while (it.hasNext()) {
            ((DisposableHandle) it.next()).dispose();
        }
        this.handles.clear();
    }

    public final void plusAssign(DisposableHandle disposableHandle) {
        this.handles.add(disposableHandle);
    }

    public final void replaceAll(DisposableHandle... disposableHandleArr) {
        dispose();
        add((DisposableHandle[]) Arrays.copyOf(disposableHandleArr, disposableHandleArr.length));
    }

    public final void plusAssign(Iterable<? extends DisposableHandle> iterable) {
        CollectionsKt__MutableCollectionsKt.addAll(iterable, this.handles);
    }
}
