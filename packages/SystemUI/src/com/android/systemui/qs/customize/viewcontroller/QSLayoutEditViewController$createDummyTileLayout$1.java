package com.android.systemui.qs.customize.viewcontroller;

import android.content.res.Resources;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes2.dex */
final /* synthetic */ class QSLayoutEditViewController$createDummyTileLayout$1 extends FunctionReferenceImpl implements Function1 {
    public QSLayoutEditViewController$createDummyTileLayout$1(Object obj) {
        super(1, obj, QSLayoutEditViewController.class, "updateRecyclerViewHeight", "updateRecyclerViewHeight(Ljava/lang/Integer;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) throws Resources.NotFoundException {
        QSLayoutEditViewController qSLayoutEditViewController = (QSLayoutEditViewController) this.receiver;
        int i = QSLayoutEditViewController.$r8$clinit;
        qSLayoutEditViewController.updateRecyclerViewHeight((Integer) obj);
        return Unit.INSTANCE;
    }
}
