package com.android.systemui.kairos.internal;

import kotlin.Lazy;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface DeferScope {
    void deferAction(Function0 function0);

    Lazy deferAsync(Function0 function0);
}
