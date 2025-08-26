package com.android.wm.shell.common.pip;

import android.content.Context;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes3.dex */
final /* synthetic */ class PipAppOpsListener$mTopPipActivityInfoSupplier$1 extends FunctionReferenceImpl implements Function1 {
    public PipAppOpsListener$mTopPipActivityInfoSupplier$1(Object obj) {
        super(1, obj, PipUtils.class, "getTopPipActivity", "getTopPipActivity(Landroid/content/Context;)Landroid/util/Pair;", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        return PipUtils.getTopPipActivity((Context) obj);
    }
}
