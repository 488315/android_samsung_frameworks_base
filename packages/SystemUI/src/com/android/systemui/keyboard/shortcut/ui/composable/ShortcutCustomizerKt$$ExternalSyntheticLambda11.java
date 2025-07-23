package com.android.systemui.keyboard.shortcut.ui.composable;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Modifier;
import java.util.List;
import kotlin.Function;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class ShortcutCustomizerKt$$ExternalSyntheticLambda11 implements Function2 {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ boolean f$0;
    public final /* synthetic */ Function f$1;
    public final /* synthetic */ Object f$2;
    public final /* synthetic */ String f$3;
    public final /* synthetic */ Function f$4;
    public final /* synthetic */ Object f$5;
    public final /* synthetic */ int f$6;

    public /* synthetic */ ShortcutCustomizerKt$$ExternalSyntheticLambda11(ComposableLambdaImpl composableLambdaImpl, ComposableLambdaImpl composableLambdaImpl2, ComposableLambdaImpl composableLambdaImpl3, boolean z, Modifier modifier, String str, int i) {
        this.f$1 = composableLambdaImpl;
        this.f$2 = composableLambdaImpl2;
        this.f$4 = composableLambdaImpl3;
        this.f$0 = z;
        this.f$5 = modifier;
        this.f$3 = str;
        this.f$6 = i;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        switch (this.$r8$classId) {
            case 0:
                ((Integer) obj2).intValue();
                ShortcutCustomizerKt.SelectedKeyCombinationContainer(this.f$0, (Function1) this.f$1, (List) this.f$2, this.f$3, (Function0) this.f$4, (Function0) this.f$5, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(this.f$6 | 1));
                break;
            default:
                ((Integer) obj2).getClass();
                int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(this.f$6 | 1);
                ComposableLambdaImpl composableLambdaImpl = (ComposableLambdaImpl) this.f$4;
                Modifier modifier = (Modifier) this.f$5;
                String str = this.f$3;
                ShortcutCustomizerKt.OutlinedInputField((ComposableLambdaImpl) this.f$1, (ComposableLambdaImpl) this.f$2, composableLambdaImpl, this.f$0, modifier, str, (Composer) obj, updateChangedFlags);
                break;
        }
        return Unit.INSTANCE;
    }

    public /* synthetic */ ShortcutCustomizerKt$$ExternalSyntheticLambda11(boolean z, Function1 function1, List list, String str, Function0 function0, Function0 function02, int i) {
        this.f$0 = z;
        this.f$1 = function1;
        this.f$2 = list;
        this.f$3 = str;
        this.f$4 = function0;
        this.f$5 = function02;
        this.f$6 = i;
    }
}
