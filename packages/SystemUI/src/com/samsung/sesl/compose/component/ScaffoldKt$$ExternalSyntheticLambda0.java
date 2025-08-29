package com.samsung.sesl.compose.component;

import androidx.compose.foundation.layout.WindowInsets;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Modifier;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;

/* loaded from: classes4.dex */
public final /* synthetic */ class ScaffoldKt$$ExternalSyntheticLambda0 implements Function2 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Modifier f$0;
    public final /* synthetic */ Function2 f$1;
    public final /* synthetic */ ComposableLambdaImpl f$10;
    public final /* synthetic */ int f$11;
    public final /* synthetic */ int f$13;
    public final /* synthetic */ ComposableLambdaImpl f$2;
    public final /* synthetic */ ComposableLambdaImpl f$3;
    public final /* synthetic */ ComposableLambdaImpl f$4;
    public final /* synthetic */ Function4 f$5;
    public final /* synthetic */ int f$6;
    public final /* synthetic */ long f$7;
    public final /* synthetic */ WindowInsets f$8;

    public /* synthetic */ ScaffoldKt$$ExternalSyntheticLambda0(Modifier modifier, Function2 function2, ComposableLambdaImpl composableLambdaImpl, ComposableLambdaImpl composableLambdaImpl2, ComposableLambdaImpl composableLambdaImpl3, Function4 function4, int i, long j, WindowInsets windowInsets, ComposableLambdaImpl composableLambdaImpl4, int i2, int i3, int i4) {
        this.$r8$classId = i4;
        this.f$0 = modifier;
        this.f$1 = function2;
        this.f$2 = composableLambdaImpl;
        this.f$3 = composableLambdaImpl2;
        this.f$4 = composableLambdaImpl3;
        this.f$5 = function4;
        this.f$6 = i;
        this.f$7 = j;
        this.f$8 = windowInsets;
        this.f$10 = composableLambdaImpl4;
        this.f$11 = i2;
        this.f$13 = i3;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        switch (this.$r8$classId) {
            case 0:
                ((Integer) obj2).getClass();
                int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(this.f$11 | 1);
                WindowInsets windowInsets = this.f$8;
                ComposableLambdaImpl composableLambdaImpl = this.f$10;
                int i = this.f$13;
                ScaffoldKt.m3340SeslScaffold5k0As8s(this.f$0, this.f$1, this.f$2, this.f$3, this.f$4, this.f$5, this.f$6, this.f$7, windowInsets, composableLambdaImpl, (Composer) obj, iUpdateChangedFlags, i);
                break;
            default:
                ((Integer) obj2).getClass();
                int iUpdateChangedFlags2 = RecomposeScopeImplKt.updateChangedFlags(this.f$11 | 1);
                int iUpdateChangedFlags3 = RecomposeScopeImplKt.updateChangedFlags(this.f$13);
                WindowInsets windowInsets2 = this.f$8;
                ComposableLambdaImpl composableLambdaImpl2 = this.f$10;
                ScaffoldKt.m3341SeslScaffoldImpl5k0As8s(this.f$0, this.f$1, this.f$2, this.f$3, this.f$4, this.f$5, this.f$6, this.f$7, windowInsets2, composableLambdaImpl2, (Composer) obj, iUpdateChangedFlags2, iUpdateChangedFlags3);
                break;
        }
        return Unit.INSTANCE;
    }
}
