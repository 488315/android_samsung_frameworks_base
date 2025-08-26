package com.android.systemui.communal.ui.compose;

import android.content.ComponentName;
import com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel;
import java.util.Map;
import kotlin.Unit;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes2.dex */
final /* synthetic */ class ContentListStateKt$rememberContentListState$1$4 extends FunctionReferenceImpl implements Function5 {
    public ContentListStateKt$rememberContentListState$1$4(Object obj) {
        super(5, obj, BaseCommunalViewModel.class, "onResizeWidget", "onResizeWidget(IILjava/util/Map;Landroid/content/ComponentName;I)V", 0);
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        int iIntValue = ((Number) obj5).intValue();
        ((BaseCommunalViewModel) this.receiver).onResizeWidget(((Number) obj).intValue(), ((Number) obj2).intValue(), (Map) obj3, (ComponentName) obj4, iIntValue);
        return Unit.INSTANCE;
    }
}
