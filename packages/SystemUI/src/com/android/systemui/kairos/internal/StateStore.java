package com.android.systemui.kairos.internal;

import kotlin.Pair;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public abstract class StateStore {
    public /* synthetic */ StateStore(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract Pair getCurrentWithEpoch(EvalScope evalScope);

    private StateStore() {
    }
}
