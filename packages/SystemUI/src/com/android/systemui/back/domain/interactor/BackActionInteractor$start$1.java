package com.android.systemui.back.domain.interactor;

import android.view.ViewRootImpl;
import android.window.WindowOnBackInvokedDispatcher;
import com.android.systemui.scene.ui.view.WindowRootView;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

final class BackActionInteractor$start$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ BackActionInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BackActionInteractor$start$1(BackActionInteractor backActionInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = backActionInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new BackActionInteractor$start$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BackActionInteractor$start$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final BackActionInteractor backActionInteractor = this.this$0;
            ReadonlyStateFlow readonlyStateFlow = backActionInteractor.windowRootViewVisibilityInteractor.isLockscreenOrShadeVisibleAndInteractive;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.back.domain.interactor.BackActionInteractor$start$1.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    ViewRootImpl viewRootImpl;
                    ViewRootImpl viewRootImpl2;
                    boolean booleanValue = ((Boolean) obj2).booleanValue();
                    WindowOnBackInvokedDispatcher windowOnBackInvokedDispatcher = null;
                    BackActionInteractor backActionInteractor2 = BackActionInteractor.this;
                    if (booleanValue) {
                        if (!backActionInteractor2.isCallbackRegistered) {
                            WindowRootView windowRootView = ((NotificationShadeWindowControllerImpl) backActionInteractor2.notificationShadeWindowController).mWindowRootView;
                            if (windowRootView != null && (viewRootImpl2 = windowRootView.getViewRootImpl()) != null) {
                                windowOnBackInvokedDispatcher = viewRootImpl2.getOnBackInvokedDispatcher();
                            }
                            if (windowOnBackInvokedDispatcher != null) {
                                windowOnBackInvokedDispatcher.registerOnBackInvokedCallback(0, backActionInteractor2.callback);
                                backActionInteractor2.isCallbackRegistered = true;
                            }
                        }
                    } else if (backActionInteractor2.isCallbackRegistered) {
                        WindowRootView windowRootView2 = ((NotificationShadeWindowControllerImpl) backActionInteractor2.notificationShadeWindowController).mWindowRootView;
                        if (windowRootView2 != null && (viewRootImpl = windowRootView2.getViewRootImpl()) != null) {
                            windowOnBackInvokedDispatcher = viewRootImpl.getOnBackInvokedDispatcher();
                        }
                        if (windowOnBackInvokedDispatcher != null) {
                            windowOnBackInvokedDispatcher.unregisterOnBackInvokedCallback(backActionInteractor2.callback);
                            backActionInteractor2.isCallbackRegistered = false;
                        }
                    }
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (readonlyStateFlow.$$delegate_0.collect(flowCollector, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }
}
