package com.android.systemui.keyboard.shortcut.ui.composable;

import androidx.compose.runtime.State;
import androidx.compose.ui.graphics.GraphicsLayerScope;
import androidx.compose.ui.graphics.ReusableGraphicsLayerScope;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import com.android.systemui.keyboard.shortcut.shared.model.Shortcut;
import com.android.systemui.keyboard.shortcut.ui.model.ShortcutCategoryUi;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class ShortcutHelperKt$$ExternalSyntheticLambda4 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ShortcutHelperKt$$ExternalSyntheticLambda4(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                SemanticsPropertiesKt.setContentDescription((SemanticsPropertyReceiver) obj, ((Shortcut) this.f$0).contentDescription);
                break;
            case 1:
                ((Function1) this.f$0).mo779invoke(((ShortcutCategoryUi) obj).type);
                break;
            default:
                ((ReusableGraphicsLayerScope) ((GraphicsLayerScope) obj)).setRotationZ(((Number) ((State) this.f$0).getValue()).floatValue());
                break;
        }
        return Unit.INSTANCE;
    }
}
