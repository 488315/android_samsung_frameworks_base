package com.samsung.sesl.compose.template;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Modifier;
import com.samsung.sesl.compose.template.SeslTopAppBarTemplate$TitleScope;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* loaded from: classes4.dex */
public final /* synthetic */ class SeslTopAppBarTemplate$TitleScope$$ExternalSyntheticLambda0 implements Function2 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SeslTopAppBarTemplate$TitleScope f$0;
    public final /* synthetic */ ComposableLambdaImpl f$1;
    public final /* synthetic */ Modifier f$2;
    public final /* synthetic */ int f$4;

    public /* synthetic */ SeslTopAppBarTemplate$TitleScope$$ExternalSyntheticLambda0(SeslTopAppBarTemplate$TitleScope seslTopAppBarTemplate$TitleScope, ComposableLambdaImpl composableLambdaImpl, Modifier modifier, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = seslTopAppBarTemplate$TitleScope;
        this.f$1 = composableLambdaImpl;
        this.f$2 = modifier;
        this.f$4 = i;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        SeslTopAppBarTemplate$TitleScope seslTopAppBarTemplate$TitleScope = this.f$0;
        Modifier modifier = this.f$2;
        ComposableLambdaImpl composableLambdaImpl = this.f$1;
        int i = this.f$4;
        int i2 = this.$r8$classId;
        Composer composer = (Composer) obj;
        ((Integer) obj2).getClass();
        switch (i2) {
            case 0:
                SeslTopAppBarTemplate$TitleScope.Companion companion = SeslTopAppBarTemplate$TitleScope.Companion;
                seslTopAppBarTemplate$TitleScope.Title(RecomposeScopeImplKt.updateChangedFlags(i | 1), composer, composableLambdaImpl, (Modifier.Companion) modifier);
                break;
            default:
                SeslTopAppBarTemplate$TitleScope.Companion companion2 = SeslTopAppBarTemplate$TitleScope.Companion;
                seslTopAppBarTemplate$TitleScope.SingleLine(RecomposeScopeImplKt.updateChangedFlags(i | 1), composer, composableLambdaImpl, modifier);
                break;
        }
        return Unit.INSTANCE;
    }
}
