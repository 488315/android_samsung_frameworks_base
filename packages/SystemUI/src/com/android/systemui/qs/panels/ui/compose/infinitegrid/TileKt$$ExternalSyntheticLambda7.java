package com.android.systemui.qs.panels.ui.compose.infinitegrid;

import android.content.Context;
import androidx.compose.runtime.State;
import androidx.compose.ui.graphics.GraphicsLayerScope;
import androidx.compose.ui.graphics.ReusableGraphicsLayerScope;
import com.android.systemui.qs.panels.ui.viewmodel.IconProvider;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class TileKt$$ExternalSyntheticLambda7 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ State f$0;

    public /* synthetic */ TileKt$$ExternalSyntheticLambda7(State state, int i) {
        this.$r8$classId = i;
        this.f$0 = state;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((ReusableGraphicsLayerScope) ((GraphicsLayerScope) obj)).setAlpha(((Number) this.f$0.getValue()).floatValue());
                return Unit.INSTANCE;
            default:
                return TileKt.getTileIcon((Context) obj, (IconProvider) this.f$0.getValue());
        }
    }
}
