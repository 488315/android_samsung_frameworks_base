package com.android.systemui.statusbar.notification.stack.ui.viewmodel;

import com.android.systemui.statusbar.data.repository.RemoteInputRepositoryImpl;
import com.android.systemui.statusbar.domain.interactor.RemoteInputInteractor;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final /* synthetic */ class NotificationScrollViewModel$remoteInputRowBottomBoundConsumer$1 extends FunctionReferenceImpl implements Function1 {
    public NotificationScrollViewModel$remoteInputRowBottomBoundConsumer$1(Object obj) {
        super(1, obj, RemoteInputInteractor.class, "setRemoteInputRowBottomBound", "setRemoteInputRowBottomBound(Ljava/lang/Float;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        ((RemoteInputRepositoryImpl) ((RemoteInputInteractor) this.receiver).remoteInputRepository).remoteInputRowBottomBound.setValue((Float) obj);
        return Unit.INSTANCE;
    }
}
