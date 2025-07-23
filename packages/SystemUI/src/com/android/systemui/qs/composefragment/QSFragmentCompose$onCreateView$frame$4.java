package com.android.systemui.qs.composefragment;

import com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final /* synthetic */ class QSFragmentCompose$onCreateView$frame$4 extends FunctionReferenceImpl implements Function0 {
    public QSFragmentCompose$onCreateView$frame$4(Object obj) {
        super(0, obj, QSFragmentComposeViewModel.class, "emitMotionEventForFalsingSwipeNested", "emitMotionEventForFalsingSwipeNested()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        ((QSFragmentComposeViewModel) this.receiver).falsingInteractor.manager.isFalseTouch(17);
        return Unit.INSTANCE;
    }
}
