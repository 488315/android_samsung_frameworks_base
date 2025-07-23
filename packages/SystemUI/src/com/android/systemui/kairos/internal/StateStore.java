package com.android.systemui.kairos.internal;

import kotlin.Pair;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class StateStore {
    public /* synthetic */ StateStore(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract Pair getCurrentWithEpoch(EvalScope evalScope);

    private StateStore() {
    }
}
