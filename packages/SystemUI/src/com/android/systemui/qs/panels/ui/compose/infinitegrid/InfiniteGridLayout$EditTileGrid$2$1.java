package com.android.systemui.qs.panels.ui.compose.infinitegrid;

import com.android.systemui.qs.panels.ui.viewmodel.DynamicIconTilesViewModel;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes2.dex */
final /* synthetic */ class InfiniteGridLayout$EditTileGrid$2$1 extends FunctionReferenceImpl implements Function2 {
    public InfiniteGridLayout$EditTileGrid$2$1(Object obj) {
        super(2, obj, DynamicIconTilesViewModel.class, "resize", "resize(Lcom/android/systemui/qs/pipeline/shared/TileSpec;Z)V", 0);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        boolean zBooleanValue = ((Boolean) obj2).booleanValue();
        ((DynamicIconTilesViewModel) this.receiver).resize((TileSpec) obj, zBooleanValue);
        return Unit.INSTANCE;
    }
}
