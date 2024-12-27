package com.android.systemui.communal.ui.compose;

import com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

final /* synthetic */ class ContentListStateKt$rememberContentListState$1$2 extends FunctionReferenceImpl implements Function1 {
    public ContentListStateKt$rememberContentListState$1$2(Object obj) {
        super(1, obj, BaseCommunalViewModel.class, "onDeleteWidget", "onDeleteWidget(I)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        ((BaseCommunalViewModel) this.receiver).onDeleteWidget(((Number) obj).intValue());
        return Unit.INSTANCE;
    }
}
