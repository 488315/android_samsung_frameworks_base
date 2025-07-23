package com.android.systemui.qs.panels.ui.compose.infinitegrid;

import androidx.compose.runtime.State;
import androidx.compose.ui.graphics.GraphicsLayerScope;
import androidx.compose.ui.graphics.ReusableGraphicsLayerScope;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class EditTileKt$$ExternalSyntheticLambda3 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ State f$0;

    public /* synthetic */ EditTileKt$$ExternalSyntheticLambda3(State state, int i) {
        this.$r8$classId = i;
        this.f$0 = state;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                break;
            case 1:
                ((ReusableGraphicsLayerScope) ((GraphicsLayerScope) obj)).setAlpha(((Number) this.f$0.getValue()).floatValue());
                break;
            default:
                ((ReusableGraphicsLayerScope) ((GraphicsLayerScope) obj)).setAlpha(((Number) this.f$0.getValue()).floatValue());
                break;
        }
        return Unit.INSTANCE;
    }
}
