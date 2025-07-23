package com.android.systemui.keyboard.shortcut.ui.composable;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.RecomposeScopeImplKt;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutSubCategory;
import com.android.systemui.keyboard.shortcut.ui.model.ShortcutCategoryUi;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class ShortcutHelperKt$$ExternalSyntheticLambda2 implements Function2 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ String f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ ShortcutHelperKt$$ExternalSyntheticLambda2(Object obj, int i, int i2, String str) {
        this.$r8$classId = i2;
        this.f$0 = str;
        this.f$1 = obj;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        int i = this.$r8$classId;
        Composer composer = (Composer) obj;
        ((Integer) obj2).getClass();
        switch (i) {
            case 0:
                ShortcutHelperKt.ShortcutSubCategorySinglePane(this.f$0, (ShortcutSubCategory) this.f$1, composer, RecomposeScopeImplKt.updateChangedFlags(1));
                break;
            default:
                ShortcutHelperKt.ShortcutCategoryDetailsSinglePane(this.f$0, (ShortcutCategoryUi) this.f$1, composer, RecomposeScopeImplKt.updateChangedFlags(1));
                break;
        }
        return Unit.INSTANCE;
    }
}
