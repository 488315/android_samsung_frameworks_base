package com.android.systemui.qs.tiles.base.viewmodel;

import com.android.systemui.qs.tiles.impl.custom.di.CustomTileComponent;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

final /* synthetic */ class QSTileViewModelFactory$Component$create$2 extends FunctionReferenceImpl implements Function0 {
    public QSTileViewModelFactory$Component$create$2(Object obj) {
        super(0, obj, CustomTileComponent.class, "dataInteractor", "dataInteractor()Lcom/android/systemui/qs/tiles/base/interactor/QSTileDataInteractor;", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        return ((CustomTileComponent) this.receiver).dataInteractor();
    }
}
