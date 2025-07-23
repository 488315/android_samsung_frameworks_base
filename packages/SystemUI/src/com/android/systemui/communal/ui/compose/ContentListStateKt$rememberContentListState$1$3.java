package com.android.systemui.communal.ui.compose;

import com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel;
import java.util.Map;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final /* synthetic */ class ContentListStateKt$rememberContentListState$1$3 extends FunctionReferenceImpl implements Function1 {
    public ContentListStateKt$rememberContentListState$1$3(Object obj) {
        super(1, obj, BaseCommunalViewModel.class, "onReorderWidgets", "onReorderWidgets(Ljava/util/Map;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        ((BaseCommunalViewModel) this.receiver).onReorderWidgets((Map) obj);
        return Unit.INSTANCE;
    }
}
