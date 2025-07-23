package com.android.systemui.keyboard.shortcut.ui.composable;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.RecomposeScopeImplKt;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutKey;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class ShortcutCustomizerKt$$ExternalSyntheticLambda22 implements Function2 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ShortcutKey.Icon.ResIdIcon f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ ShortcutCustomizerKt$$ExternalSyntheticLambda22(ShortcutKey.Icon.ResIdIcon resIdIcon, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = resIdIcon;
        this.f$1 = i;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        int i = this.$r8$classId;
        Composer composer = (Composer) obj;
        ((Integer) obj2).intValue();
        switch (i) {
            case 0:
                ShortcutCustomizerKt.ActionKeyContainer(this.f$0, composer, RecomposeScopeImplKt.updateChangedFlags(this.f$1 | 1));
                break;
            default:
                ShortcutCustomizerKt.ActionKeyIcon(this.f$0, composer, RecomposeScopeImplKt.updateChangedFlags(this.f$1 | 1));
                break;
        }
        return Unit.INSTANCE;
    }
}
