package com.android.compose.grid;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Modifier;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class GridsKt$$ExternalSyntheticLambda0 implements Function2 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;
    public final /* synthetic */ Modifier f$1;
    public final /* synthetic */ float f$2;
    public final /* synthetic */ float f$3;
    public final /* synthetic */ ComposableLambdaImpl f$4;
    public final /* synthetic */ int f$5;

    public /* synthetic */ GridsKt$$ExternalSyntheticLambda0(int i, Modifier modifier, float f, float f2, ComposableLambdaImpl composableLambdaImpl, int i2, int i3) {
        this.$r8$classId = i3;
        this.f$0 = i;
        this.f$1 = modifier;
        this.f$2 = f;
        this.f$3 = f2;
        this.f$4 = composableLambdaImpl;
        this.f$5 = i2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        switch (this.$r8$classId) {
            case 0:
                ((Integer) obj2).getClass();
                int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(this.f$5 | 1);
                ComposableLambdaImpl composableLambdaImpl = this.f$4;
                GridsKt.m937VerticalGridvz2T9sI(this.f$0, this.f$1, this.f$2, this.f$3, composableLambdaImpl, (Composer) obj, updateChangedFlags);
                break;
            default:
                ((Integer) obj2).getClass();
                int updateChangedFlags2 = RecomposeScopeImplKt.updateChangedFlags(this.f$5 | 1);
                ComposableLambdaImpl composableLambdaImpl2 = this.f$4;
                GridsKt.m936GridnSlTg7c(this.f$0, this.f$1, this.f$2, this.f$3, composableLambdaImpl2, (Composer) obj, updateChangedFlags2);
                break;
        }
        return Unit.INSTANCE;
    }
}
