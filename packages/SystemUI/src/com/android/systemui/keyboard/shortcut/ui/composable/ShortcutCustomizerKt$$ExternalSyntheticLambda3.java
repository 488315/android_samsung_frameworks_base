package com.android.systemui.keyboard.shortcut.ui.composable;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.Modifier;
import com.android.systemui.keyboard.shortcut.ui.model.ShortcutCustomizationUiState;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class ShortcutCustomizerKt$$ExternalSyntheticLambda3 implements Function2 {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Modifier f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Object f$2;
    public final /* synthetic */ Function0 f$3;
    public final /* synthetic */ Function0 f$4;
    public final /* synthetic */ Object f$5;
    public final /* synthetic */ int f$6;

    public /* synthetic */ ShortcutCustomizerKt$$ExternalSyntheticLambda3(Modifier modifier, ShortcutCustomizationUiState.AddShortcutDialog addShortcutDialog, Function1 function1, Function0 function0, Function0 function02, Function0 function03, int i) {
        this.f$0 = modifier;
        this.f$1 = addShortcutDialog;
        this.f$2 = function1;
        this.f$3 = function0;
        this.f$4 = function02;
        this.f$5 = function03;
        this.f$6 = i;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        switch (this.$r8$classId) {
            case 0:
                ((Integer) obj2).intValue();
                ShortcutCustomizerKt.AddShortcutDialog(this.f$0, (ShortcutCustomizationUiState.AddShortcutDialog) this.f$1, (Function1) this.f$2, this.f$3, this.f$4, (Function0) this.f$5, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(this.f$6 | 1));
                break;
            default:
                ((Integer) obj2).intValue();
                ShortcutCustomizerKt.ConfirmationDialog(this.f$0, (String) this.f$1, (String) this.f$2, (String) this.f$5, this.f$3, this.f$4, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(this.f$6 | 1));
                break;
        }
        return Unit.INSTANCE;
    }

    public /* synthetic */ ShortcutCustomizerKt$$ExternalSyntheticLambda3(Modifier modifier, String str, String str2, String str3, Function0 function0, Function0 function02, int i) {
        this.f$0 = modifier;
        this.f$1 = str;
        this.f$2 = str2;
        this.f$5 = str3;
        this.f$3 = function0;
        this.f$4 = function02;
        this.f$6 = i;
    }
}
