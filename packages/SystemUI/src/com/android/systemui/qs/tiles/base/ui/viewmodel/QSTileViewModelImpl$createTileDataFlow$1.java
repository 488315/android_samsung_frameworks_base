package com.android.systemui.qs.tiles.base.ui.viewmodel;

import android.os.UserHandle;
import com.android.internal.logging.UiEventLogger;
import com.android.settingslib.RestrictedLockUtils;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.qs.QSEvent;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.tiles.base.domain.interactor.DisabledByPolicyInteractor;
import com.android.systemui.qs.tiles.base.domain.interactor.DisabledByPolicyInteractorImpl;
import com.android.systemui.qs.tiles.base.domain.interactor.QSTileDataInteractor;
import com.android.systemui.qs.tiles.base.domain.model.DataUpdateTrigger;
import com.android.systemui.qs.tiles.base.domain.model.QSTileInput;
import com.android.systemui.qs.tiles.base.shared.logging.QSTileLogger;
import com.android.systemui.qs.tiles.base.shared.logging.QSTileLogger$$ExternalSyntheticLambda0;
import com.android.systemui.qs.tiles.base.shared.logging.QSTileLogger$$ExternalSyntheticLambda1;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfig;
import com.android.systemui.qs.tiles.base.shared.model.QSTilePolicy;
import com.android.systemui.qs.tiles.base.shared.model.QSTileState;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUserAction;
import com.android.systemui.util.kotlin.FlowKt;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.text.StringsKt___StringsKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.flow.CancellableFlow;
import kotlinx.coroutines.flow.CancellableFlowImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharingStarted;

/* loaded from: classes2.dex */
final class QSTileViewModelImpl$createTileDataFlow$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ QSTileViewModelImpl this$0;

    /* renamed from: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$createTileDataFlow$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ FlowCollector $$this$transformLatest;
        final /* synthetic */ UserHandle $user;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ QSTileViewModelImpl this$0;

        /* renamed from: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$createTileDataFlow$1$1$1, reason: invalid class name and collision with other inner class name */
        final class C04231 extends SuspendLambda implements Function3 {
            /* synthetic */ Object L$0;
            int label;

            public C04231(Continuation continuation) {
                super(3, continuation);
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                C04231 c04231 = new C04231((Continuation) obj3);
                c04231.L$0 = obj;
                return c04231.invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return this.L$0;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(QSTileViewModelImpl qSTileViewModelImpl, UserHandle userHandle, FlowCollector flowCollector, Continuation continuation) {
            super(2, continuation);
            this.this$0 = qSTileViewModelImpl;
            this.$user = userHandle;
            this.$$this$transformLatest = flowCollector;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, this.$user, this.$$this$transformLatest, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                final QSTileViewModelImpl qSTileViewModelImpl = this.this$0;
                final UserHandle userHandle = this.$user;
                final SharedFlowImpl sharedFlowImpl = qSTileViewModelImpl.userInputs;
                final Flow flow = new Flow() { // from class: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$filterFalseActions$$inlined$filter$1

                    /* renamed from: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$filterFalseActions$$inlined$filter$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;
                        public final /* synthetic */ QSTileViewModelImpl this$0;

                        /* renamed from: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$filterFalseActions$$inlined$filter$1$2$1, reason: invalid class name */
                        public final class AnonymousClass1 extends ContinuationImpl {
                            Object L$0;
                            Object L$1;
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

                        public AnonymousClass2(FlowCollector flowCollector, QSTileViewModelImpl qSTileViewModelImpl) {
                            this.$this_unsafeFlow = flowCollector;
                            this.this$0 = qSTileViewModelImpl;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object emit(Object obj, Continuation continuation) {
                            AnonymousClass1 anonymousClass1;
                            boolean zIsFalseTap;
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
                                QSTileUserAction qSTileUserAction = (QSTileUserAction) obj;
                                boolean z = qSTileUserAction instanceof QSTileUserAction.Click;
                                QSTileViewModelImpl qSTileViewModelImpl = this.this$0;
                                if (z || (qSTileUserAction instanceof QSTileUserAction.ToggleClick)) {
                                    zIsFalseTap = qSTileViewModelImpl.falsingManager.isFalseTap(1);
                                } else {
                                    if (!(qSTileUserAction instanceof QSTileUserAction.LongClick)) {
                                        throw new NoWhenBranchMatchedException();
                                    }
                                    zIsFalseTap = qSTileViewModelImpl.falsingManager.isFalseLongTap(1);
                                }
                                if (zIsFalseTap) {
                                    QSTileLogger qSTileLogger = qSTileViewModelImpl.qsTileLogger;
                                    TileSpec tileSpec = qSTileViewModelImpl.config.tileSpec;
                                    LogBuffer logBuffer = qSTileLogger.getLogBuffer(tileSpec);
                                    LogMessage logMessageObtain = logBuffer.obtain(QSTileLogger.getLogTag(tileSpec), LogLevel.DEBUG, new QSTileLogger$$ExternalSyntheticLambda0(8), null);
                                    ((LogMessageImpl) logMessageObtain).str1 = QSTileLogger.toLogString(qSTileUserAction);
                                    logBuffer.commit(logMessageObtain);
                                }
                                if (!zIsFalseTap) {
                                    anonymousClass1.label = 1;
                                    if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                        return coroutineSingletons;
                                    }
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
                        Object objCollect = sharedFlowImpl.collect(new AnonymousClass2(flowCollector, qSTileViewModelImpl), continuation);
                        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                    }
                };
                final QSTilePolicy qSTilePolicy = qSTileViewModelImpl.config.policy;
                if (!(qSTilePolicy instanceof QSTilePolicy.NoRestrictions)) {
                    if (!(qSTilePolicy instanceof QSTilePolicy.Restricted)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    flow = new Flow() { // from class: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$filterByPolicy$lambda$8$$inlined$filter$1

                        /* renamed from: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$filterByPolicy$lambda$8$$inlined$filter$1$2, reason: invalid class name */
                        public final class AnonymousClass2 implements FlowCollector {
                            public final /* synthetic */ QSTilePolicy $policy$inlined;
                            public final /* synthetic */ FlowCollector $this_unsafeFlow;
                            public final /* synthetic */ UserHandle $user$inlined;
                            public final /* synthetic */ QSTileViewModelImpl this$0;

                            /* renamed from: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$filterByPolicy$lambda$8$$inlined$filter$1$2$1, reason: invalid class name */
                            public final class AnonymousClass1 extends ContinuationImpl {
                                Object L$0;
                                Object L$1;
                                Object L$2;
                                Object L$3;
                                Object L$4;
                                Object L$5;
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

                            public AnonymousClass2(FlowCollector flowCollector, QSTilePolicy qSTilePolicy, QSTileViewModelImpl qSTileViewModelImpl, UserHandle userHandle) {
                                this.$this_unsafeFlow = flowCollector;
                                this.$policy$inlined = qSTilePolicy;
                                this.this$0 = qSTileViewModelImpl;
                                this.$user$inlined = userHandle;
                            }

                            /* JADX WARN: Code restructure failed: missing block: B:44:0x0130, code lost:
                            
                                if (r9.emit(r1, r2) == r3) goto L45;
                             */
                            /* JADX WARN: Removed duplicated region for block: B:24:0x008b  */
                            /* JADX WARN: Removed duplicated region for block: B:30:0x00c8  */
                            /* JADX WARN: Removed duplicated region for block: B:31:0x00ca  */
                            /* JADX WARN: Removed duplicated region for block: B:35:0x00de  */
                            /* JADX WARN: Removed duplicated region for block: B:37:0x0108  */
                            /* JADX WARN: Removed duplicated region for block: B:38:0x010d  */
                            /* JADX WARN: Removed duplicated region for block: B:41:0x011b  */
                            /* JADX WARN: Removed duplicated region for block: B:43:0x011e  */
                            /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
                            /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:27:0x00af -> B:28:0x00b8). Please report as a decompilation issue!!! */
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final Object emit(Object obj, Continuation continuation) {
                                AnonymousClass1 anonymousClass1;
                                FlowCollector flowCollector;
                                Iterator it;
                                QSTileUserAction qSTileUserAction;
                                Object obj2;
                                int i;
                                AnonymousClass2 anonymousClass2 = this;
                                if (continuation instanceof AnonymousClass1) {
                                    anonymousClass1 = (AnonymousClass1) continuation;
                                    int i2 = anonymousClass1.label;
                                    if ((i2 & Integer.MIN_VALUE) != 0) {
                                        anonymousClass1.label = i2 - Integer.MIN_VALUE;
                                    } else {
                                        anonymousClass1 = anonymousClass2.new AnonymousClass1(continuation);
                                    }
                                }
                                Object obj3 = anonymousClass1.result;
                                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                int i3 = anonymousClass1.label;
                                int i4 = 1;
                                if (i3 != 0) {
                                    if (i3 != 1) {
                                        if (i3 != 2) {
                                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                        }
                                        ResultKt.throwOnFailure(obj3);
                                        return Unit.INSTANCE;
                                    }
                                    String str = (String) anonymousClass1.L$5;
                                    Iterator it2 = (Iterator) anonymousClass1.L$4;
                                    QSTileUserAction qSTileUserAction2 = (QSTileUserAction) anonymousClass1.L$3;
                                    FlowCollector flowCollector2 = (FlowCollector) anonymousClass1.L$2;
                                    Object obj4 = anonymousClass1.L$1;
                                    AnonymousClass2 anonymousClass22 = (AnonymousClass2) anonymousClass1.L$0;
                                    ResultKt.throwOnFailure(obj3);
                                    String str2 = str;
                                    anonymousClass2 = anonymousClass22;
                                    FlowCollector flowCollector3 = flowCollector2;
                                    QSTileUserAction qSTileUserAction3 = qSTileUserAction2;
                                    Iterator it3 = it2;
                                    AnonymousClass1 anonymousClass12 = anonymousClass1;
                                    Object obj5 = obj4;
                                    DisabledByPolicyInteractor.PolicyResult policyResult = (DisabledByPolicyInteractor.PolicyResult) obj3;
                                    DisabledByPolicyInteractorImpl disabledByPolicyInteractorImpl = (DisabledByPolicyInteractorImpl) anonymousClass2.this$0.disabledByPolicyInteractor;
                                    disabledByPolicyInteractorImpl.getClass();
                                    if (policyResult instanceof DisabledByPolicyInteractor.PolicyResult.TileEnabled) {
                                        i = 0;
                                    } else {
                                        if (!(policyResult instanceof DisabledByPolicyInteractor.PolicyResult.TileDisabled)) {
                                            throw new NoWhenBranchMatchedException();
                                        }
                                        disabledByPolicyInteractorImpl.activityStarter.postStartActivityDismissingKeyguard(RestrictedLockUtils.getShowAdminSupportDetailsIntent(((DisabledByPolicyInteractor.PolicyResult.TileDisabled) policyResult).admin), 0);
                                        i = i4;
                                    }
                                    if (i != 0) {
                                        QSTileViewModelImpl qSTileViewModelImpl = anonymousClass2.this$0;
                                        QSTileLogger qSTileLogger = qSTileViewModelImpl.qsTileLogger;
                                        TileSpec tileSpec = qSTileViewModelImpl.config.tileSpec;
                                        LogBuffer logBuffer = qSTileLogger.getLogBuffer(tileSpec);
                                        LogMessage logMessageObtain = logBuffer.obtain(QSTileLogger.getLogTag(tileSpec), LogLevel.DEBUG, new QSTileLogger$$ExternalSyntheticLambda1(str2, 1), null);
                                        ((LogMessageImpl) logMessageObtain).str1 = QSTileLogger.toLogString(qSTileUserAction3);
                                        logBuffer.commit(logMessageObtain);
                                    }
                                    if (i == 0) {
                                        obj2 = obj5;
                                        anonymousClass1 = anonymousClass12;
                                        flowCollector = flowCollector3;
                                        i4 = 0;
                                        if (i4 != 0) {
                                            anonymousClass1.L$0 = null;
                                            anonymousClass1.L$1 = null;
                                            anonymousClass1.L$2 = null;
                                            anonymousClass1.L$3 = null;
                                            anonymousClass1.L$4 = null;
                                            anonymousClass1.L$5 = null;
                                            anonymousClass1.label = 2;
                                        }
                                        return Unit.INSTANCE;
                                    }
                                    obj2 = obj5;
                                    anonymousClass1 = anonymousClass12;
                                    it = it3;
                                    qSTileUserAction = qSTileUserAction3;
                                    flowCollector = flowCollector3;
                                    i4 = 1;
                                    if (it.hasNext()) {
                                        i4 = 1;
                                        if (i4 != 0) {
                                        }
                                        return Unit.INSTANCE;
                                    }
                                    str2 = (String) it.next();
                                    DisabledByPolicyInteractor disabledByPolicyInteractor = anonymousClass2.this$0.disabledByPolicyInteractor;
                                    UserHandle userHandle = anonymousClass2.$user$inlined;
                                    anonymousClass1.L$0 = anonymousClass2;
                                    anonymousClass1.L$1 = obj2;
                                    anonymousClass1.L$2 = flowCollector;
                                    anonymousClass1.L$3 = qSTileUserAction;
                                    anonymousClass1.L$4 = it;
                                    anonymousClass1.L$5 = str2;
                                    anonymousClass1.label = i4;
                                    Object objIsDisabled = ((DisabledByPolicyInteractorImpl) disabledByPolicyInteractor).isDisabled(userHandle, str2, anonymousClass1);
                                    if (objIsDisabled != coroutineSingletons) {
                                        AnonymousClass1 anonymousClass13 = anonymousClass1;
                                        obj5 = obj2;
                                        obj3 = objIsDisabled;
                                        flowCollector3 = flowCollector;
                                        qSTileUserAction3 = qSTileUserAction;
                                        it3 = it;
                                        anonymousClass12 = anonymousClass13;
                                        DisabledByPolicyInteractor.PolicyResult policyResult2 = (DisabledByPolicyInteractor.PolicyResult) obj3;
                                        DisabledByPolicyInteractorImpl disabledByPolicyInteractorImpl2 = (DisabledByPolicyInteractorImpl) anonymousClass2.this$0.disabledByPolicyInteractor;
                                        disabledByPolicyInteractorImpl2.getClass();
                                        if (policyResult2 instanceof DisabledByPolicyInteractor.PolicyResult.TileEnabled) {
                                        }
                                        if (i != 0) {
                                        }
                                        if (i == 0) {
                                        }
                                    }
                                    return coroutineSingletons;
                                }
                                ResultKt.throwOnFailure(obj3);
                                QSTileUserAction qSTileUserAction4 = (QSTileUserAction) obj;
                                List list = ((QSTilePolicy.Restricted) anonymousClass2.$policy$inlined).userRestrictions;
                                boolean z = list instanceof Collection;
                                flowCollector = anonymousClass2.$this_unsafeFlow;
                                if (z && list.isEmpty()) {
                                    obj2 = obj;
                                    if (i4 != 0) {
                                    }
                                    return Unit.INSTANCE;
                                }
                                it = list.iterator();
                                qSTileUserAction = qSTileUserAction4;
                                obj2 = obj;
                                if (it.hasNext()) {
                                }
                            }
                        }

                        @Override // kotlinx.coroutines.flow.Flow
                        public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                            Object objCollect = flow.collect(new AnonymousClass2(flowCollector, qSTilePolicy, qSTileViewModelImpl, userHandle), continuation);
                            return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                        }
                    };
                }
                final Flow flowThrottle = FlowKt.throttle(flow, 200L, qSTileViewModelImpl.systemClock);
                Flow flowFlowOn = kotlinx.coroutines.flow.FlowKt.flowOn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new Flow() { // from class: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$userInputFlow$$inlined$mapNotNull$1

                    /* renamed from: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$userInputFlow$$inlined$mapNotNull$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;
                        public final /* synthetic */ UserHandle $user$inlined;
                        public final /* synthetic */ QSTileViewModelImpl this$0;

                        /* renamed from: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$userInputFlow$$inlined$mapNotNull$1$2$1, reason: invalid class name */
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

                        public AnonymousClass2(FlowCollector flowCollector, QSTileViewModelImpl qSTileViewModelImpl, UserHandle userHandle) {
                            this.$this_unsafeFlow = flowCollector;
                            this.this$0 = qSTileViewModelImpl;
                            this.$user$inlined = userHandle;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object emit(Object obj, Continuation continuation) {
                            AnonymousClass1 anonymousClass1;
                            Object objLastOrNull;
                            QSEvent qSEvent;
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
                                QSTileUserAction qSTileUserAction = (QSTileUserAction) obj;
                                QSTileViewModelImpl qSTileViewModelImpl = this.this$0;
                                QSTileState qSTileState = (QSTileState) CollectionsKt___CollectionsKt.lastOrNull(qSTileViewModelImpl.state.$$delegate_0.getReplayCache());
                                DataUpdateTrigger.UserInput userInput = null;
                                if (qSTileState != null && (objLastOrNull = CollectionsKt___CollectionsKt.lastOrNull(qSTileViewModelImpl.tileData.$$delegate_0.getReplayCache())) != null) {
                                    QSTileConfig qSTileConfig = qSTileViewModelImpl.config;
                                    TileSpec tileSpec = qSTileConfig.tileSpec;
                                    LogBuffer logBuffer = qSTileViewModelImpl.qsTileLogger.getLogBuffer(tileSpec);
                                    LogMessage logMessageObtain = logBuffer.obtain(QSTileLogger.getLogTag(tileSpec), LogLevel.DEBUG, new QSTileLogger$$ExternalSyntheticLambda0(6), null);
                                    LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                                    logMessageImpl.str1 = QSTileLogger.toLogString(qSTileUserAction);
                                    logMessageImpl.str2 = QSTileLogger.toLogString(qSTileState);
                                    logMessageImpl.str3 = StringsKt___StringsKt.take(50, String.valueOf(objLastOrNull));
                                    logBuffer.commit(logMessageObtain);
                                    UiEventLogger uiEventLogger = qSTileViewModelImpl.qsTileAnalytics.uiEventLogger;
                                    if (qSTileUserAction instanceof QSTileUserAction.Click) {
                                        qSEvent = QSEvent.QS_ACTION_CLICK;
                                    } else if (qSTileUserAction instanceof QSTileUserAction.ToggleClick) {
                                        qSEvent = QSEvent.QS_ACTION_SECONDARY_CLICK;
                                    } else {
                                        if (!(qSTileUserAction instanceof QSTileUserAction.LongClick)) {
                                            throw new NoWhenBranchMatchedException();
                                        }
                                        qSEvent = QSEvent.QS_ACTION_LONG_PRESS;
                                    }
                                    uiEventLogger.logWithInstanceId(qSEvent, 0, qSTileConfig.metricsSpec, qSTileConfig.instanceId);
                                    userInput = new DataUpdateTrigger.UserInput(new QSTileInput(this.$user$inlined, qSTileUserAction, objLastOrNull));
                                }
                                if (userInput != null) {
                                    anonymousClass1.label = 1;
                                    if (this.$this_unsafeFlow.emit(userInput, anonymousClass1) == coroutineSingletons) {
                                        return coroutineSingletons;
                                    }
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
                        Object objCollect = flowThrottle.collect(new AnonymousClass2(flowCollector, qSTileViewModelImpl, userHandle), continuation);
                        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                    }
                }, new QSTileViewModelImpl$userInputFlow$2(qSTileViewModelImpl, null)), qSTileViewModelImpl.backgroundDispatcher);
                final SharedFlowImpl sharedFlowImpl2 = this.this$0.forceUpdates;
                Flow flowFlowOn2 = kotlinx.coroutines.flow.FlowKt.flowOn(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new QSTileViewModelImpl$createTileDataFlow$1$1$updateTriggers$3(this.this$0, null), kotlinx.coroutines.flow.FlowKt.merge(flowFlowOn, new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new Flow() { // from class: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$createTileDataFlow$1$1$invokeSuspend$$inlined$map$1

                    /* renamed from: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$createTileDataFlow$1$1$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* renamed from: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl$createTileDataFlow$1$1$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
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
                                DataUpdateTrigger.ForceUpdate forceUpdate = DataUpdateTrigger.ForceUpdate.INSTANCE;
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(forceUpdate, anonymousClass1) == coroutineSingletons) {
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
                        Object objCollect = sharedFlowImpl2.collect(new AnonymousClass2(flowCollector), continuation);
                        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                    }
                }, new QSTileViewModelImpl$createTileDataFlow$1$1$updateTriggers$2(this.this$0, null)))), this.this$0.backgroundDispatcher);
                SharingStarted.Companion.getClass();
                ReadonlyStateFlow readonlyStateFlowStateIn = kotlinx.coroutines.flow.FlowKt.stateIn(flowFlowOn2, coroutineScope, SharingStarted.Companion.Eagerly, DataUpdateTrigger.InitialRequest.INSTANCE);
                Flow flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(((QSTileDataInteractor) this.this$0.tileDataInteractor.invoke()).tileData(this.$user, readonlyStateFlowStateIn), readonlyStateFlowStateIn, new C04231(null));
                if (!(flowKt__ZipKt$combine$$inlined$unsafeFlow$1 instanceof CancellableFlow)) {
                    flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new CancellableFlowImpl(flowKt__ZipKt$combine$$inlined$unsafeFlow$1);
                }
                Flow flowFlowOn3 = kotlinx.coroutines.flow.FlowKt.flowOn((CancellableFlow) flowKt__ZipKt$combine$$inlined$unsafeFlow$1, this.this$0.backgroundDispatcher);
                final FlowCollector flowCollector = this.$$this$transformLatest;
                FlowCollector flowCollector2 = new FlowCollector() { // from class: com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelImpl.createTileDataFlow.1.1.2
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        Object objEmit = flowCollector.emit(obj2, continuation);
                        return objEmit == CoroutineSingletons.COROUTINE_SUSPENDED ? objEmit : Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (flowFlowOn3.collect(flowCollector2, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public QSTileViewModelImpl$createTileDataFlow$1(QSTileViewModelImpl qSTileViewModelImpl, Continuation continuation) {
        super(3, continuation);
        this.this$0 = qSTileViewModelImpl;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        QSTileViewModelImpl$createTileDataFlow$1 qSTileViewModelImpl$createTileDataFlow$1 = new QSTileViewModelImpl$createTileDataFlow$1(this.this$0, (Continuation) obj3);
        qSTileViewModelImpl$createTileDataFlow$1.L$0 = (FlowCollector) obj;
        qSTileViewModelImpl$createTileDataFlow$1.L$1 = (UserHandle) obj2;
        return qSTileViewModelImpl$createTileDataFlow$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, (UserHandle) this.L$1, flowCollector, null);
            this.L$0 = null;
            this.label = 1;
            if (CoroutineScopeKt.coroutineScope(anonymousClass1, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
