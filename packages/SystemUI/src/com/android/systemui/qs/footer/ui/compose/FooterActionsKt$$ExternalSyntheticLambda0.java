package com.android.systemui.qs.footer.ui.compose;

import androidx.compose.runtime.MutableState;
import androidx.compose.ui.graphics.GraphicsLayerScope;
import androidx.compose.ui.graphics.ReusableGraphicsLayerScope;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class FooterActionsKt$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ FooterActionsKt$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                SemanticsPropertiesKt.setContentDescription((SemanticsPropertyReceiver) obj, (String) this.f$0);
                break;
            case 1:
                SemanticsPropertiesKt.setContentDescription((SemanticsPropertyReceiver) obj, (String) this.f$0);
                break;
            default:
                ((ReusableGraphicsLayerScope) ((GraphicsLayerScope) obj)).setAlpha(((Number) ((MutableState) this.f$0).getValue()).floatValue());
                break;
        }
        return Unit.INSTANCE;
    }
}
