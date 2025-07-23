package com.android.systemui.people.ui.viewmodel;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.MutableStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final /* synthetic */ class PeopleViewModelKt$PeopleViewModel$2 extends FunctionReferenceImpl implements Function1 {
    final /* synthetic */ MutableStateFlow $appWidgetId;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PeopleViewModelKt$PeopleViewModel$2(MutableStateFlow mutableStateFlow) {
        super(1, Intrinsics.Kotlin.class, "onWidgetIdChanged", "PeopleViewModel$onWidgetIdChanged(Lkotlinx/coroutines/flow/MutableStateFlow;I)V", 0);
        this.$appWidgetId = mutableStateFlow;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        this.$appWidgetId.setValue(Integer.valueOf(((Number) obj).intValue()));
        return Unit.INSTANCE;
    }
}
