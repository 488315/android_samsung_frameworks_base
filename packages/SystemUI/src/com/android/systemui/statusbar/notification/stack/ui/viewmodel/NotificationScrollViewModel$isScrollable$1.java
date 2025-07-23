package com.android.systemui.statusbar.notification.stack.ui.viewmodel;

import com.android.compose.animation.scene.OverlayKey;
import com.android.compose.animation.scene.SceneKey;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class NotificationScrollViewModel$isScrollable$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ NotificationScrollViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NotificationScrollViewModel$isScrollable$1(NotificationScrollViewModel notificationScrollViewModel, Continuation continuation) {
        super(3, continuation);
        this.this$0 = notificationScrollViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        NotificationScrollViewModel$isScrollable$1 notificationScrollViewModel$isScrollable$1 = new NotificationScrollViewModel$isScrollable$1(this.this$0, (Continuation) obj3);
        notificationScrollViewModel$isScrollable$1.L$0 = (SceneKey) obj;
        notificationScrollViewModel$isScrollable$1.L$1 = (Set) obj2;
        return notificationScrollViewModel$isScrollable$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        boolean z;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        SceneKey sceneKey = (SceneKey) this.L$0;
        Set set = (Set) this.L$1;
        if (!NotificationScrollViewModel.access$showsNotifications(this.this$0, sceneKey)) {
            Set set2 = set;
            NotificationScrollViewModel notificationScrollViewModel = this.this$0;
            if (!(set2 instanceof Collection) || !set2.isEmpty()) {
                Iterator it = set2.iterator();
                while (it.hasNext()) {
                    if (NotificationScrollViewModel.access$showsNotifications(notificationScrollViewModel, (OverlayKey) it.next())) {
                    }
                }
            }
            z = false;
            return Boolean.valueOf(z);
        }
        z = true;
        return Boolean.valueOf(z);
    }
}
