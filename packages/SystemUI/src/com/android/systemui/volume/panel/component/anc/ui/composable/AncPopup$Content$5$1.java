package com.android.systemui.volume.panel.component.anc.ui.composable;

import com.android.systemui.volume.panel.component.anc.ui.viewmodel.AncViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes3.dex */
final /* synthetic */ class AncPopup$Content$5$1 extends FunctionReferenceImpl implements Function1 {
    public AncPopup$Content$5$1(Object obj) {
        super(1, obj, AncViewModel.class, "onPopupSliceWidthChanged", "onPopupSliceWidthChanged(I)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        ((AncViewModel) this.receiver).interactor.popupSliceWidth.updateState(null, Integer.valueOf(((Number) obj).intValue()));
        return Unit.INSTANCE;
    }
}
