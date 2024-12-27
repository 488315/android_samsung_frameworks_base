package com.android.systemui.qs.tiles.base.viewmodel;

import com.android.systemui.qs.tiles.impl.custom.di.CustomTileComponent;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final /* synthetic */ class QSTileViewModelFactory$Component$create$1 extends FunctionReferenceImpl implements Function0 {
    public QSTileViewModelFactory$Component$create$1(Object obj) {
        super(0, obj, CustomTileComponent.class, "userActionInteractor", "userActionInteractor()Lcom/android/systemui/qs/tiles/base/interactor/QSTileUserActionInteractor;", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        return ((CustomTileComponent) this.receiver).userActionInteractor();
    }
}
