package com.android.systemui.qs.customize.viewcontroller;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final /* synthetic */ class QSLayoutEditViewController$createDummyTileLayout$1 extends FunctionReferenceImpl implements Function1 {
    public QSLayoutEditViewController$createDummyTileLayout$1(Object obj) {
        super(1, obj, QSLayoutEditViewController.class, "updateRecyclerViewHeight", "updateRecyclerViewHeight(Ljava/lang/Integer;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        QSLayoutEditViewController qSLayoutEditViewController = (QSLayoutEditViewController) this.receiver;
        int i = QSLayoutEditViewController.$r8$clinit;
        qSLayoutEditViewController.updateRecyclerViewHeight((Integer) obj);
        return Unit.INSTANCE;
    }
}
