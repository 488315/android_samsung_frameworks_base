package com.android.systemui.kairos.internal;

import kotlin.Lazy;
import kotlin.jvm.functions.Function0;

/* loaded from: classes2.dex */
public interface DeferScope {
    void deferAction(Function0 function0);

    Lazy deferAsync(Function0 function0);
}
