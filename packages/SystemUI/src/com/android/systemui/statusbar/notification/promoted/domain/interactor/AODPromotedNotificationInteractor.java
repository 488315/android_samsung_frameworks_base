package com.android.systemui.statusbar.notification.promoted.domain.interactor;

import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.statusbar.notification.promoted.shared.model.PromotedNotificationContentModel;
import com.android.systemui.statusbar.policy.domain.interactor.SensitiveNotificationProtectionInteractor;
import com.android.systemui.util.kotlin.FlowDumperImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.DistinctFlowImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* loaded from: classes3.dex */
public final class AODPromotedNotificationInteractor extends FlowDumperImpl {
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 canShowPrivateNotificationContent;
    public final DistinctFlowImpl content;
    public final Flow isPresent;

    public AODPromotedNotificationInteractor(PromotedNotificationsInteractor promotedNotificationsInteractor, KeyguardInteractor keyguardInteractor, SensitiveNotificationProtectionInteractor sensitiveNotificationProtectionInteractor, DumpManager dumpManager) {
        super(dumpManager, null, 2, null);
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(keyguardInteractor.isKeyguardDismissible, sensitiveNotificationProtectionInteractor.isSensitiveStateActive, new AODPromotedNotificationInteractor$canShowPrivateNotificationContent$1(null));
        this.canShowPrivateNotificationContent = flowKt__ZipKt$combine$$inlined$unsafeFlow$1;
        final DistinctFlowImpl distinctFlowImplDistinctUntilChanged = FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(promotedNotificationsInteractor.aodPromotedNotification, flowKt__ZipKt$combine$$inlined$unsafeFlow$1, new AODPromotedNotificationInteractor$content$1(null)), new AODPromotedNotificationInteractor$$ExternalSyntheticLambda0());
        this.content = distinctFlowImplDistinctUntilChanged;
        this.isPresent = dumpWhileCollecting(new Flow() { // from class: com.android.systemui.statusbar.notification.promoted.domain.interactor.AODPromotedNotificationInteractor$special$$inlined$map$1

            /* renamed from: com.android.systemui.statusbar.notification.promoted.domain.interactor.AODPromotedNotificationInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.notification.promoted.domain.interactor.AODPromotedNotificationInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        Boolean boolValueOf = Boolean.valueOf(((PromotedNotificationContentModel) obj) != null);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = distinctFlowImplDistinctUntilChanged.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, "isPresent");
    }
}
