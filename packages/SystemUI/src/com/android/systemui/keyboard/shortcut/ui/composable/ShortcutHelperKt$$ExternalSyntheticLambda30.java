package com.android.systemui.keyboard.shortcut.ui.composable;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.Modifier;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutCategoryType;
import com.android.systemui.keyboard.shortcut.ui.model.ShortcutsUiState;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class ShortcutHelperKt$$ExternalSyntheticLambda30 implements Function2 {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Function1 f$2;
    public final /* synthetic */ Function1 f$3;
    public final /* synthetic */ Modifier f$4;
    public final /* synthetic */ Function0 f$5;
    public final /* synthetic */ Object f$6;
    public final /* synthetic */ int f$7;

    public /* synthetic */ ShortcutHelperKt$$ExternalSyntheticLambda30(ShortcutsUiState.Active active, ShortcutHelperKt$ShortcutHelper$1 shortcutHelperKt$ShortcutHelper$1, Function1 function1, Function1 function12, Modifier modifier, Function0 function0, Function1 function13, int i) {
        this.f$0 = active;
        this.f$1 = shortcutHelperKt$ShortcutHelper$1;
        this.f$2 = function1;
        this.f$3 = function12;
        this.f$4 = modifier;
        this.f$5 = function0;
        this.f$6 = function13;
        this.f$7 = i;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        switch (this.$r8$classId) {
            case 0:
                ((Integer) obj2).getClass();
                int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(this.f$7 | 1);
                Function0 function0 = this.f$5;
                Function1 function1 = (Function1) this.f$6;
                ShortcutHelperKt.ActiveShortcutHelper((ShortcutsUiState.Active) this.f$0, (ShortcutHelperKt$ShortcutHelper$1) this.f$1, this.f$2, this.f$3, this.f$4, function0, function1, (Composer) obj, updateChangedFlags);
                break;
            default:
                ((Integer) obj2).getClass();
                int updateChangedFlags2 = RecomposeScopeImplKt.updateChangedFlags(this.f$7 | 1);
                Function0 function02 = this.f$5;
                Modifier modifier = this.f$4;
                ShortcutHelperKt.ShortcutHelperSinglePane((String) this.f$0, this.f$2, (List) this.f$1, (ShortcutCategoryType) this.f$6, this.f$3, function02, modifier, (Composer) obj, updateChangedFlags2);
                break;
        }
        return Unit.INSTANCE;
    }

    public /* synthetic */ ShortcutHelperKt$$ExternalSyntheticLambda30(String str, Function1 function1, List list, ShortcutCategoryType shortcutCategoryType, Function1 function12, Function0 function0, Modifier modifier, int i) {
        this.f$0 = str;
        this.f$2 = function1;
        this.f$1 = list;
        this.f$6 = shortcutCategoryType;
        this.f$3 = function12;
        this.f$5 = function0;
        this.f$4 = modifier;
        this.f$7 = i;
    }
}
