package com.android.systemui.keyboard.shortcut.ui.composable;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.Modifier;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class ShortcutCustomizerKt$$ExternalSyntheticLambda1 implements Function2 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Modifier f$0;
    public final /* synthetic */ Function0 f$1;
    public final /* synthetic */ Function0 f$2;
    public final /* synthetic */ int f$3;

    public /* synthetic */ ShortcutCustomizerKt$$ExternalSyntheticLambda1(Modifier modifier, Function0 function0, Function0 function02, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = modifier;
        this.f$1 = function0;
        this.f$2 = function02;
        this.f$3 = i;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        int i = this.$r8$classId;
        Composer composer = (Composer) obj;
        ((Integer) obj2).intValue();
        switch (i) {
            case 0:
                ShortcutCustomizerKt.DeleteShortcutDialog(this.f$0, this.f$1, this.f$2, composer, RecomposeScopeImplKt.updateChangedFlags(this.f$3 | 1));
                break;
            default:
                ShortcutCustomizerKt.ResetShortcutDialog(this.f$0, this.f$1, this.f$2, composer, RecomposeScopeImplKt.updateChangedFlags(this.f$3 | 1));
                break;
        }
        return Unit.INSTANCE;
    }
}
