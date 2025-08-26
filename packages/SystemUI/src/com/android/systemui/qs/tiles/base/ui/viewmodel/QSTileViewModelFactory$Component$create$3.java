package com.android.systemui.qs.tiles.base.ui.viewmodel;

import com.android.systemui.qs.tiles.impl.custom.ui.model.CustomTileComponent;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes2.dex */
final /* synthetic */ class QSTileViewModelFactory$Component$create$3 extends FunctionReferenceImpl implements Function0 {
    public QSTileViewModelFactory$Component$create$3(Object obj) {
        super(0, obj, CustomTileComponent.class, "dataToStateMapper", "dataToStateMapper()Lcom/android/systemui/qs/tiles/base/ui/model/QSTileDataToStateMapper;", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        return ((CustomTileComponent) this.receiver).dataToStateMapper();
    }
}
