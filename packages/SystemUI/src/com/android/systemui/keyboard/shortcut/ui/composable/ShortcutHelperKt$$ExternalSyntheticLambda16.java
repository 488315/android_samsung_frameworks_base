package com.android.systemui.keyboard.shortcut.ui.composable;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Shape;
import com.android.systemui.keyboard.shortcut.shared.model.Shortcut;
import com.android.systemui.keyboard.shortcut.ui.model.ShortcutCategoryUi;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
public final /* synthetic */ class ShortcutHelperKt$$ExternalSyntheticLambda16 implements Function2 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ boolean f$2;
    public final /* synthetic */ Function0 f$3;
    public final /* synthetic */ Object f$4;
    public final /* synthetic */ int f$5;

    public /* synthetic */ ShortcutHelperKt$$ExternalSyntheticLambda16(Object obj, Object obj2, boolean z, Function0 function0, Object obj3, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = obj;
        this.f$1 = obj2;
        this.f$2 = z;
        this.f$3 = function0;
        this.f$4 = obj3;
        this.f$5 = i;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        switch (this.$r8$classId) {
            case 0:
                ((Integer) obj2).getClass();
                int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(this.f$5 | 1);
                Function0 function0 = this.f$3;
                Function0 function02 = (Function0) this.f$4;
                ShortcutHelperKt.ShortcutKeyCombinations((Modifier) this.f$0, (Shortcut) this.f$1, this.f$2, function0, function02, (Composer) obj, iUpdateChangedFlags);
                break;
            default:
                ((Integer) obj2).intValue();
                ShortcutHelperKt.CategoryItemSinglePane((String) this.f$0, (ShortcutCategoryUi) this.f$1, this.f$2, this.f$3, (Shape) this.f$4, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(this.f$5 | 1));
                break;
        }
        return Unit.INSTANCE;
    }
}
