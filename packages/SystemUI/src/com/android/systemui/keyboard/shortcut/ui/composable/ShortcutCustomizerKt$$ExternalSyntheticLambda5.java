package com.android.systemui.keyboard.shortcut.ui.composable;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.RecomposeScopeImplKt;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutKey;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class ShortcutCustomizerKt$$ExternalSyntheticLambda5 implements Function2 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ShortcutCustomizerKt$$ExternalSyntheticLambda5(Object obj, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        switch (this.$r8$classId) {
            case 0:
                ((Integer) obj2).getClass();
                int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                ShortcutCustomizerKt.ErrorMessageContainer((String) this.f$0, (Composer) obj, updateChangedFlags);
                break;
            case 1:
                ((Integer) obj2).getClass();
                ShortcutCustomizerKt.PressedKeysTextContainer((List) this.f$0, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(1));
                break;
            case 2:
                ((Integer) obj2).getClass();
                int updateChangedFlags2 = RecomposeScopeImplKt.updateChangedFlags(1);
                ShortcutCustomizerKt.ShortcutTextKey((ShortcutKey.Text) this.f$0, (Composer) obj, updateChangedFlags2);
                break;
            default:
                ((Integer) obj2).getClass();
                int updateChangedFlags3 = RecomposeScopeImplKt.updateChangedFlags(1);
                ShortcutCustomizerKt.ShortcutIconKey((ShortcutKey.Icon) this.f$0, (Composer) obj, updateChangedFlags3);
                break;
        }
        return Unit.INSTANCE;
    }
}
