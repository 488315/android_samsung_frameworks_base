package com.android.wm.shell.desktopmode;

import android.window.RemoteTransition;
import android.window.WindowContainerTransaction;
import com.android.wm.shell.desktopmode.DesktopTasksController;
import com.android.wm.shell.shared.desktopmode.DesktopModeTransitionSource;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.SafeContinuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class DesktopTasksController$moveTaskToDefaultDeskAndActivate$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $displayId;
    final /* synthetic */ RemoteTransition $remoteTransition;
    final /* synthetic */ int $taskId;
    final /* synthetic */ DesktopModeTransitionSource $transitionSource;
    final /* synthetic */ WindowContainerTransaction $wct;
    int I$0;
    Object L$0;
    int label;
    final /* synthetic */ DesktopTasksController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DesktopTasksController$moveTaskToDefaultDeskAndActivate$1(DesktopTasksController desktopTasksController, int i, int i2, WindowContainerTransaction windowContainerTransaction, DesktopModeTransitionSource desktopModeTransitionSource, RemoteTransition remoteTransition, Continuation continuation) {
        super(2, continuation);
        this.this$0 = desktopTasksController;
        this.$taskId = i;
        this.$displayId = i2;
        this.$wct = windowContainerTransaction;
        this.$transitionSource = desktopModeTransitionSource;
        this.$remoteTransition = remoteTransition;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new DesktopTasksController$moveTaskToDefaultDeskAndActivate$1(this.this$0, this.$taskId, this.$displayId, this.$wct, this.$transitionSource, this.$remoteTransition, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DesktopTasksController$moveTaskToDefaultDeskAndActivate$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Type inference failed for: r5v0, types: [com.android.wm.shell.desktopmode.DesktopTasksController$createDeskSuspending$2$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object orThrow;
        DesktopTasksController desktopTasksController;
        int i;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = this.label;
        try {
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                DesktopTasksController desktopTasksController2 = this.this$0;
                int i3 = this.$taskId;
                int i4 = this.$displayId;
                this.L$0 = desktopTasksController2;
                this.I$0 = i3;
                this.label = 1;
                Integer defaultDeskId = desktopTasksController2.taskRepository.getDefaultDeskId(i4);
                if (defaultDeskId != null) {
                    orThrow = new Integer(defaultDeskId.intValue());
                } else {
                    int i5 = desktopTasksController2.userId;
                    final SafeContinuation safeContinuation = new SafeContinuation(IntrinsicsKt__IntrinsicsJvmKt.intercepted(this));
                    DesktopTasksController.createDesk$default(desktopTasksController2, i4, i5, false, new Function1() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$createDeskSuspending$2$1
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo779invoke(Object obj2) {
                            int intValue = ((Number) obj2).intValue();
                            int i6 = Result.$r8$clinit;
                            Continuation.this.resumeWith(Integer.valueOf(intValue));
                            return Unit.INSTANCE;
                        }
                    }, 24);
                    orThrow = safeContinuation.getOrThrow();
                }
                if (orThrow == coroutineSingletons) {
                    return coroutineSingletons;
                }
                desktopTasksController = desktopTasksController2;
                i = i3;
                obj = orThrow;
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                int i6 = this.I$0;
                DesktopTasksController desktopTasksController3 = (DesktopTasksController) this.L$0;
                ResultKt.throwOnFailure(obj);
                i = i6;
                desktopTasksController = desktopTasksController3;
            }
            DesktopTasksController.moveTaskToDesk$default(desktopTasksController, i, ((Number) obj).intValue(), this.$wct, this.$transitionSource, this.$remoteTransition, 32);
        } catch (Throwable th) {
            DesktopTasksController desktopTasksController4 = this.this$0;
            Object[] objArr = {th.getMessage()};
            DesktopTasksController.Companion companion = DesktopTasksController.Companion;
            desktopTasksController4.getClass();
            DesktopTasksController.logE("Failed to move task to default desk: %s", objArr);
        }
        return Unit.INSTANCE;
    }
}
