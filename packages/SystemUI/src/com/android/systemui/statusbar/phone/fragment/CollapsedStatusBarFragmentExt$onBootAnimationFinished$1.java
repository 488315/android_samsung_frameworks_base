package com.android.systemui.statusbar.phone.fragment;

import android.app.ActivityManager;
import android.content.ComponentName;
import com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepository;
import com.android.systemui.statusbar.data.repository.StatusBarModePerDisplayRepositoryImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import java.util.List;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class CollapsedStatusBarFragmentExt$onBootAnimationFinished$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ CollapsedStatusBarFragmentExt this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CollapsedStatusBarFragmentExt$onBootAnimationFinished$1(CollapsedStatusBarFragmentExt collapsedStatusBarFragmentExt, Continuation continuation) {
        super(2, continuation);
        this.this$0 = collapsedStatusBarFragmentExt;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CollapsedStatusBarFragmentExt$onBootAnimationFinished$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CollapsedStatusBarFragmentExt$onBootAnimationFinished$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ReadonlyStateFlow readonlyStateFlow = ((StatusBarModePerDisplayRepositoryImpl) ((StatusBarModePerDisplayRepository) this.this$0.statusBarModeRepository.getDefaultDisplay())).isInFullscreenMode;
            final CollapsedStatusBarFragmentExt collapsedStatusBarFragmentExt = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragmentExt$onBootAnimationFinished$1.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    String str;
                    boolean booleanValue = ((Boolean) obj2).booleanValue();
                    CollapsedStatusBarFragmentExt collapsedStatusBarFragmentExt2 = CollapsedStatusBarFragmentExt.this;
                    float floatValue = ((Number) collapsedStatusBarFragmentExt2.secPanelExpansionStateInteractor.shadeFraction.getValue()).floatValue();
                    if (collapsedStatusBarFragmentExt2.lastFullscreenFlagState != booleanValue && (!((KeyguardStateControllerImpl) ((KeyguardStateController) collapsedStatusBarFragmentExt2.keyguardStateControllerLazy.get())).mShowing || floatValue <= 0.0f)) {
                        if (booleanValue) {
                            try {
                                List<ActivityManager.RunningTaskInfo> runningTasks = collapsedStatusBarFragmentExt2.activityManager.getRunningTasks(1);
                                List<ActivityManager.RunningTaskInfo> list = runningTasks;
                                if (list != null && !list.isEmpty()) {
                                    ComponentName componentName = runningTasks.get(0).topActivity;
                                    if (componentName == null || (str = componentName.getPackageName()) == null) {
                                        str = "";
                                    }
                                    collapsedStatusBarFragmentExt2.lastFullscreenPkgName = str;
                                }
                                collapsedStatusBarFragmentExt2.printStatusBarInfoLog("fullscreen");
                            } catch (Exception unused) {
                                collapsedStatusBarFragmentExt2.lastFullscreenPkgName = "";
                            }
                        } else {
                            collapsedStatusBarFragmentExt2.lastFullscreenPkgName = "";
                        }
                        collapsedStatusBarFragmentExt2.lastFullscreenFlagState = booleanValue;
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
