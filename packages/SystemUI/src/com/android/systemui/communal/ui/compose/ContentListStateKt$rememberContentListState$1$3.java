package com.android.systemui.communal.ui.compose;

import com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel;
import java.util.Map;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final /* synthetic */ class ContentListStateKt$rememberContentListState$1$3 extends FunctionReferenceImpl implements Function1 {
    public ContentListStateKt$rememberContentListState$1$3(Object obj) {
        super(1, obj, BaseCommunalViewModel.class, "onReorderWidgets", "onReorderWidgets(Ljava/util/Map;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        ((BaseCommunalViewModel) this.receiver).onReorderWidgets((Map) obj);
        return Unit.INSTANCE;
    }
}
