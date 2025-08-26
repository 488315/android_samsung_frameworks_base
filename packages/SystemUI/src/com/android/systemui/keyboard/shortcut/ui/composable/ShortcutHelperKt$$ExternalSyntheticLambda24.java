package com.android.systemui.keyboard.shortcut.ui.composable;

import androidx.compose.foundation.layout.BoxScope;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.RecomposeScopeImplKt;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutKey;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
public final /* synthetic */ class ShortcutHelperKt$$ExternalSyntheticLambda24 implements Function2 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BoxScope f$0;
    public final /* synthetic */ ShortcutKey f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ ShortcutHelperKt$$ExternalSyntheticLambda24(BoxScope boxScope, ShortcutKey shortcutKey, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = boxScope;
        this.f$1 = shortcutKey;
        this.f$2 = i;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        int i = this.$r8$classId;
        Composer composer = (Composer) obj;
        ((Integer) obj2).intValue();
        switch (i) {
            case 0:
                ShortcutHelperKt.ShortcutTextKey(this.f$0, (ShortcutKey.Text) this.f$1, composer, RecomposeScopeImplKt.updateChangedFlags(this.f$2 | 1));
                break;
            default:
                ShortcutHelperKt.ShortcutIconKey(this.f$0, (ShortcutKey.Icon) this.f$1, composer, RecomposeScopeImplKt.updateChangedFlags(this.f$2 | 1));
                break;
        }
        return Unit.INSTANCE;
    }
}
