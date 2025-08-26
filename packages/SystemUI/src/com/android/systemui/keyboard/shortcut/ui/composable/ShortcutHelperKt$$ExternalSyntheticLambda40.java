package com.android.systemui.keyboard.shortcut.ui.composable;

import androidx.compose.runtime.MutableState;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutCustomizationRequestInfo;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutSubCategory;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final /* synthetic */ class ShortcutHelperKt$$ExternalSyntheticLambda40 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Function1 f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ ShortcutHelperKt$$ExternalSyntheticLambda40(Function1 function1, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = function1;
        this.f$1 = obj;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                String str = (String) obj;
                ((MutableState) this.f$1).setValue(str);
                this.f$0.mo781invoke(str);
                return Unit.INSTANCE;
            default:
                ShortcutCustomizationRequestInfo shortcutCustomizationRequestInfo = (ShortcutCustomizationRequestInfo) obj;
                boolean z = shortcutCustomizationRequestInfo instanceof ShortcutCustomizationRequestInfo.SingleShortcutCustomization.Add;
                Function1 function1 = this.f$0;
                ShortcutSubCategory shortcutSubCategory = (ShortcutSubCategory) this.f$1;
                if (z) {
                    function1.mo781invoke(ShortcutCustomizationRequestInfo.SingleShortcutCustomization.Add.copy$default((ShortcutCustomizationRequestInfo.SingleShortcutCustomization.Add) shortcutCustomizationRequestInfo, null, shortcutSubCategory.label, 11));
                } else if (shortcutCustomizationRequestInfo instanceof ShortcutCustomizationRequestInfo.SingleShortcutCustomization.Delete) {
                    function1.mo781invoke(ShortcutCustomizationRequestInfo.SingleShortcutCustomization.Delete.copy$default((ShortcutCustomizationRequestInfo.SingleShortcutCustomization.Delete) shortcutCustomizationRequestInfo, null, shortcutSubCategory.label, 27));
                } else {
                    if (!Intrinsics.areEqual(shortcutCustomizationRequestInfo, ShortcutCustomizationRequestInfo.Reset.INSTANCE)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    function1.mo781invoke(shortcutCustomizationRequestInfo);
                }
                return Unit.INSTANCE;
        }
    }
}
