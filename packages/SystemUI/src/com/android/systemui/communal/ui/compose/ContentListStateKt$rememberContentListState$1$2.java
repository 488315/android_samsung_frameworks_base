package com.android.systemui.communal.ui.compose;

import android.content.ComponentName;
import com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes2.dex */
final /* synthetic */ class ContentListStateKt$rememberContentListState$1$2 extends FunctionReferenceImpl implements Function4 {
    public ContentListStateKt$rememberContentListState$1$2(Object obj) {
        super(4, obj, BaseCommunalViewModel.class, "onDeleteWidget", "onDeleteWidget(ILjava/lang/String;Landroid/content/ComponentName;I)V", 0);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        int iIntValue = ((Number) obj4).intValue();
        ((BaseCommunalViewModel) this.receiver).onDeleteWidget(((Number) obj).intValue(), (String) obj2, (ComponentName) obj3, iIntValue);
        return Unit.INSTANCE;
    }
}
