package com.android.systemui.kairos.internal.store;

import java.util.Iterator;
import java.util.Map;
import kotlin.collections.AbstractSet;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ArrayMapK$entries$1 extends AbstractSet {
    public final /* synthetic */ ArrayMapK this$0;

    public ArrayMapK$entries$1(ArrayMapK arrayMapK) {
        this.this$0 = arrayMapK;
    }

    @Override // kotlin.collections.AbstractCollection, java.util.Collection, java.util.List
    public final /* bridge */ boolean contains(Object obj) {
        if (obj instanceof Map.Entry) {
            return super.contains((Map.Entry) obj);
        }
        return false;
    }

    @Override // kotlin.collections.AbstractCollection
    public final int getSize() {
        return this.this$0.unwrapped.size();
    }

    @Override // kotlin.collections.AbstractSet, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator iterator() {
        return this.this$0.unwrapped.iterator();
    }
}
