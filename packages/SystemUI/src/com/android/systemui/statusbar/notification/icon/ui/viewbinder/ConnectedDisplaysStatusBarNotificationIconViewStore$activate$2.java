package com.android.systemui.statusbar.notification.icon.ui.viewbinder;

import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.core.StatusBarConnectedDisplays;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class ConnectedDisplaysStatusBarNotificationIconViewStore$activate$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ ConnectedDisplaysStatusBarNotificationIconViewStore this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConnectedDisplaysStatusBarNotificationIconViewStore$activate$2(ConnectedDisplaysStatusBarNotificationIconViewStore connectedDisplaysStatusBarNotificationIconViewStore, Continuation continuation) {
        super(2, continuation);
        this.this$0 = connectedDisplaysStatusBarNotificationIconViewStore;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ConnectedDisplaysStatusBarNotificationIconViewStore$activate$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        ((ConnectedDisplaysStatusBarNotificationIconViewStore$activate$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        throw null;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i != 0) {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            throw new KotlinNothingValueException();
        }
        ResultKt.throwOnFailure(obj);
        ConnectedDisplaysStatusBarNotificationIconViewStore connectedDisplaysStatusBarNotificationIconViewStore = this.this$0;
        connectedDisplaysStatusBarNotificationIconViewStore.notifPipeline.addCollectionListener(connectedDisplaysStatusBarNotificationIconViewStore.notifCollectionListener);
        connectedDisplaysStatusBarNotificationIconViewStore.iconManager.getClass();
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i2 = StatusBarConnectedDisplays.$r8$clinit;
        throw new IllegalStateException("New code path not supported when com.android.systemui.shared.status_bar_connected_displays is disabled.");
    }
}
