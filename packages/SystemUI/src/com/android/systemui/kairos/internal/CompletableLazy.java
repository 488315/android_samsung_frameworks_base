package com.android.systemui.kairos.internal;

import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import kotlin.Lazy;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class CompletableLazy implements Lazy {
    public Object _value;
    public final String name;

    /* JADX WARN: Multi-variable type inference failed */
    public CompletableLazy() {
        this(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
    }

    @Override // kotlin.Lazy
    public final Object getValue() {
        Object obj = this._value;
        if (obj != NoValue.INSTANCE) {
            return obj;
        }
        throw new IllegalStateException(TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder("CompletableLazy("), this.name, ") accessed before initialized").toString());
    }

    @Override // kotlin.Lazy
    public final boolean isInitialized() {
        return this._value != NoValue.INSTANCE;
    }

    public final void setValue(Object obj) {
        if (this._value != NoValue.INSTANCE) {
            throw new IllegalStateException("CompletableLazy value already set");
        }
        this._value = obj;
    }

    public CompletableLazy(Object obj, String str) {
        this._value = obj;
        this.name = str;
    }

    public /* synthetic */ CompletableLazy(Object obj, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? NoValue.INSTANCE : obj, (i & 2) != 0 ? null : str);
    }
}
