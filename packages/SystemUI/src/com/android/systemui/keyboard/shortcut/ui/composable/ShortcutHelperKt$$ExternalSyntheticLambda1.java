package com.android.systemui.keyboard.shortcut.ui.composable;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutCommand;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
public final /* synthetic */ class ShortcutHelperKt$$ExternalSyntheticLambda1 implements Function2 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ShortcutHelperKt$$ExternalSyntheticLambda1(Object obj, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        int i = this.$r8$classId;
        Composer composer = (Composer) obj;
        ((Integer) obj2).getClass();
        switch (i) {
            case 0:
                ShortcutHelperKt.SubCategoryTitle((String) this.f$0, composer, RecomposeScopeImplKt.updateChangedFlags(1));
                break;
            case 1:
                ShortcutHelperKt.ShortcutCommand((ShortcutCommand) this.f$0, composer, RecomposeScopeImplKt.updateChangedFlags(1));
                break;
            default:
                ShortcutHelperKt.ShortcutKeyContainer((ComposableLambdaImpl) this.f$0, composer, RecomposeScopeImplKt.updateChangedFlags(7));
                break;
        }
        return Unit.INSTANCE;
    }
}
