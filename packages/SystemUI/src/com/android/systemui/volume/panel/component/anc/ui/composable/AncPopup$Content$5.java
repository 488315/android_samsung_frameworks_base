package com.android.systemui.volume.panel.component.anc.ui.composable;

import com.android.systemui.volume.panel.component.anc.ui.viewmodel.AncViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final /* synthetic */ class AncPopup$Content$5 extends FunctionReferenceImpl implements Function1 {
    public AncPopup$Content$5(Object obj) {
        super(1, obj, AncViewModel.class, "onPopupSliceWidthChanged", "onPopupSliceWidthChanged(I)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        ((AncViewModel) this.receiver).interactor.popupSliceWidth.updateState(null, Integer.valueOf(((Number) obj).intValue()));
        return Unit.INSTANCE;
    }
}
