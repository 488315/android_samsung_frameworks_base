package com.android.wm.shell.compatui.api;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class CompatUISpec {
    public final CompatUILayout layout;
    public final CompatUILifecyclePredicates lifecycle;
    public final Function1 log;
    public final String name;

    public CompatUISpec(Function1 function1, String str, CompatUILifecyclePredicates compatUILifecyclePredicates, CompatUILayout compatUILayout) {
        this.log = function1;
        this.name = str;
        this.lifecycle = compatUILifecyclePredicates;
        this.layout = compatUILayout;
    }

    public /* synthetic */ CompatUISpec(Function1 function1, String str, CompatUILifecyclePredicates compatUILifecyclePredicates, CompatUILayout compatUILayout, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new CompatUISpec$$ExternalSyntheticLambda0() : function1, str, compatUILifecyclePredicates, compatUILayout);
    }
}
