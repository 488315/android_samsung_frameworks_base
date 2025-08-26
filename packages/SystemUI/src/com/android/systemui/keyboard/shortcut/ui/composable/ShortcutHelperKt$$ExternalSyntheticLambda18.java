package com.android.systemui.keyboard.shortcut.ui.composable;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.RecomposeScopeImplKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
public final /* synthetic */ class ShortcutHelperKt$$ExternalSyntheticLambda18 implements Function2 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Function0 f$0;

    public /* synthetic */ ShortcutHelperKt$$ExternalSyntheticLambda18(int i, int i2, Function0 function0) {
        this.$r8$classId = i2;
        this.f$0 = function0;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        int i = this.$r8$classId;
        Composer composer = (Composer) obj;
        ((Integer) obj2).getClass();
        switch (i) {
            case 0:
                ShortcutHelperKt.AddShortcutButton(this.f$0, composer, RecomposeScopeImplKt.updateChangedFlags(1));
                break;
            default:
                ShortcutHelperKt.DeleteShortcutButton(this.f$0, composer, RecomposeScopeImplKt.updateChangedFlags(1));
                break;
        }
        return Unit.INSTANCE;
    }
}
