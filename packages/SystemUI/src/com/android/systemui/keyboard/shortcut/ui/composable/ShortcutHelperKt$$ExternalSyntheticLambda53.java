package com.android.systemui.keyboard.shortcut.ui.composable;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.RecomposeScopeImplKt;
import kotlin.Function;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
public final /* synthetic */ class ShortcutHelperKt$$ExternalSyntheticLambda53 implements Function2 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Function f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ ShortcutHelperKt$$ExternalSyntheticLambda53(Function function, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = function;
        this.f$1 = i;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        int i = this.$r8$classId;
        Composer composer = (Composer) obj;
        ((Integer) obj2).intValue();
        switch (i) {
            case 0:
                ShortcutHelperKt.CustomizeButton((Function0) this.f$0, composer, RecomposeScopeImplKt.updateChangedFlags(this.f$1 | 1));
                break;
            case 1:
                ShortcutHelperKt.ResetButton((Function0) this.f$0, composer, RecomposeScopeImplKt.updateChangedFlags(this.f$1 | 1));
                break;
            case 2:
                ShortcutHelperKt.DoneButton((Function0) this.f$0, composer, RecomposeScopeImplKt.updateChangedFlags(this.f$1 | 1));
                break;
            default:
                ShortcutHelperKt.ShortcutsSearchBar((Function1) this.f$0, composer, RecomposeScopeImplKt.updateChangedFlags(this.f$1 | 1));
                break;
        }
        return Unit.INSTANCE;
    }
}
