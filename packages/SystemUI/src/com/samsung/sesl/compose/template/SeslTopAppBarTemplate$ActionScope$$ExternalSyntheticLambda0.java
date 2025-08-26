package com.samsung.sesl.compose.template;

import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Modifier;
import com.samsung.sesl.compose.template.SeslTopAppBarTemplate$ActionScope;
import com.samsung.sesl.compose.template.SeslTopAppBarTemplate$NavigationScope;
import kotlin.Function;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;

/* loaded from: classes4.dex */
public final /* synthetic */ class SeslTopAppBarTemplate$ActionScope$$ExternalSyntheticLambda0 implements Function2 {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ ComposableLambdaImpl f$1;
    public final /* synthetic */ Function f$2;
    public final /* synthetic */ Object f$3;
    public final /* synthetic */ Object f$4;
    public final /* synthetic */ int f$5;

    public /* synthetic */ SeslTopAppBarTemplate$ActionScope$$ExternalSyntheticLambda0(SeslTopAppBarTemplate$ActionScope seslTopAppBarTemplate$ActionScope, ComposableLambdaImpl composableLambdaImpl, ComposableLambdaImpl composableLambdaImpl2, ComposableLambdaImpl composableLambdaImpl3, Modifier modifier, int i) {
        this.f$0 = seslTopAppBarTemplate$ActionScope;
        this.f$1 = composableLambdaImpl;
        this.f$2 = composableLambdaImpl2;
        this.f$3 = composableLambdaImpl3;
        this.f$4 = modifier;
        this.f$5 = i;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Object obj3 = this.f$3;
        Function function = this.f$2;
        Object obj4 = this.f$0;
        Object obj5 = this.f$4;
        int i = this.f$5;
        switch (this.$r8$classId) {
            case 0:
                Composer composer = (Composer) obj;
                ((Integer) obj2).getClass();
                SeslTopAppBarTemplate$ActionScope.Companion companion = SeslTopAppBarTemplate$ActionScope.Companion;
                SeslTopAppBarTemplate$ActionScope seslTopAppBarTemplate$ActionScope = (SeslTopAppBarTemplate$ActionScope) obj4;
                ComposableLambdaImpl composableLambdaImpl = (ComposableLambdaImpl) function;
                seslTopAppBarTemplate$ActionScope.SeslTopAppBarActionLayout(this.f$1, composableLambdaImpl, (ComposableLambdaImpl) obj3, (Modifier) obj5, composer, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                break;
            default:
                Composer composer2 = (Composer) obj;
                ((Integer) obj2).getClass();
                SeslTopAppBarTemplate$NavigationScope.Companion companion2 = SeslTopAppBarTemplate$NavigationScope.Companion;
                SeslTopAppBarTemplate$NavigationScope seslTopAppBarTemplate$NavigationScope = (SeslTopAppBarTemplate$NavigationScope) obj4;
                Function0 function0 = (Function0) function;
                MutableInteractionSource mutableInteractionSource = (MutableInteractionSource) obj3;
                seslTopAppBarTemplate$NavigationScope.NavigationUp(function0, mutableInteractionSource, (String) obj5, this.f$1, composer2, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                break;
        }
        return Unit.INSTANCE;
    }

    public /* synthetic */ SeslTopAppBarTemplate$ActionScope$$ExternalSyntheticLambda0(SeslTopAppBarTemplate$NavigationScope seslTopAppBarTemplate$NavigationScope, Function0 function0, MutableInteractionSource mutableInteractionSource, String str, ComposableLambdaImpl composableLambdaImpl, int i) {
        this.f$0 = seslTopAppBarTemplate$NavigationScope;
        this.f$2 = function0;
        this.f$3 = mutableInteractionSource;
        this.f$4 = str;
        this.f$1 = composableLambdaImpl;
        this.f$5 = i;
    }
}
