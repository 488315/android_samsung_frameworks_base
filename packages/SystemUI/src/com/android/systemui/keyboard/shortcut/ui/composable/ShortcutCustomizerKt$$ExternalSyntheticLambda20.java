package com.android.systemui.keyboard.shortcut.ui.composable;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.RecomposeScopeImplKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
public final /* synthetic */ class ShortcutCustomizerKt$$ExternalSyntheticLambda20 implements Function2 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        int i = this.$r8$classId;
        Composer composer = (Composer) obj;
        ((Integer) obj2).getClass();
        switch (i) {
            case 0:
                ShortcutCustomizerKt.PressKeyPrompt(RecomposeScopeImplKt.updateChangedFlags(1), composer);
                break;
            case 1:
                ShortcutCustomizerKt.PlusIconContainer(RecomposeScopeImplKt.updateChangedFlags(1), composer);
                break;
            case 2:
                ShortcutCustomizerKt.ShortcutKeySeparator(RecomposeScopeImplKt.updateChangedFlags(1), composer);
                break;
            default:
                ShortcutCustomizerKt.ActionKeyText(RecomposeScopeImplKt.updateChangedFlags(1), composer);
                break;
        }
        return Unit.INSTANCE;
    }
}
