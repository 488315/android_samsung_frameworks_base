package com.android.systemui.volume.panel.component.bottombar.ui;

import com.android.systemui.volume.panel.component.bottombar.ui.viewmodel.BottomBarViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

final /* synthetic */ class BottomBarComponent$Content$1$2 extends FunctionReferenceImpl implements Function0 {
    public BottomBarComponent$Content$1$2(Object obj) {
        super(0, obj, BottomBarViewModel.class, "onDoneClicked", "onDoneClicked()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        ((BottomBarViewModel) this.receiver).volumePanelViewModel.volumePanelGlobalStateInteractor.setVisible(false);
        return Unit.INSTANCE;
    }
}
