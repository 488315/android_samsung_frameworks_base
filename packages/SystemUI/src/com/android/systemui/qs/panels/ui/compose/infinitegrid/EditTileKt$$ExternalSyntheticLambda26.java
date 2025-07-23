package com.android.systemui.qs.panels.ui.compose.infinitegrid;

import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.GraphicsLayerScope;
import androidx.compose.ui.graphics.ReusableGraphicsLayerScope;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class EditTileKt$$ExternalSyntheticLambda26 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Function0 f$0;

    public /* synthetic */ EditTileKt$$ExternalSyntheticLambda26(int i, Function0 function0) {
        this.$r8$classId = i;
        this.f$0 = function0;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                DrawScope.m539drawRectnJ9OG0$default((DrawScope) obj, ((Color) this.f$0.invoke()).value, 0L, 0L, 0.0f, null, null, 0, 126);
                break;
            default:
                ((ReusableGraphicsLayerScope) ((GraphicsLayerScope) obj)).setAlpha(((Number) this.f$0.invoke()).floatValue());
                break;
        }
        return Unit.INSTANCE;
    }
}
