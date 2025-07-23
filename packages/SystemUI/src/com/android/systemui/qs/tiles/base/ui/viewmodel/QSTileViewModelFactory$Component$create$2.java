package com.android.systemui.qs.tiles.base.ui.viewmodel;

import com.android.systemui.qs.tiles.impl.custom.ui.model.CustomTileComponent;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final /* synthetic */ class QSTileViewModelFactory$Component$create$2 extends FunctionReferenceImpl implements Function0 {
    public QSTileViewModelFactory$Component$create$2(Object obj) {
        super(0, obj, CustomTileComponent.class, "dataInteractor", "dataInteractor()Lcom/android/systemui/qs/tiles/base/domain/interactor/QSTileDataInteractor;", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        return ((CustomTileComponent) this.receiver).dataInteractor();
    }
}
