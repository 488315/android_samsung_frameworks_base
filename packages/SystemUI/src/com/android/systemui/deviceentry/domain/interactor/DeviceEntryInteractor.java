package com.android.systemui.deviceentry.domain.interactor;

import androidx.preference.PreferenceGroupAdapter$$ExternalSyntheticOutline0;
import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.authentication.domain.interactor.AuthenticationInteractor;
import com.android.systemui.authentication.shared.model.AuthenticationMethodModel;
import com.android.systemui.bouncer.domain.interactor.AlternateBouncerInteractor;
import com.android.systemui.deviceentry.data.repository.DeviceEntryRepository;
import com.android.systemui.deviceentry.data.repository.DeviceEntryRepositoryImpl;
import com.android.systemui.deviceentry.shared.model.DeviceUnlockStatus;
import com.android.systemui.keyguard.DismissCallbackRegistry;
import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.scene.data.model.SceneStack;
import com.android.systemui.scene.data.model.SceneStackKt$asIterable$$inlined$Iterable$1;
import com.android.systemui.scene.domain.interactor.SceneBackInteractor;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.util.kotlin.WithPrev;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes2.dex */
public final class DeviceEntryInteractor {
    public final AlternateBouncerInteractor alternateBouncerInteractor;
    public final CoroutineScope applicationScope;
    public final AuthenticationInteractor authenticationInteractor;
    public final Lazy canSwipeToEnter$delegate;
    public final DeviceUnlockedInteractor deviceUnlockedInteractor;
    public final DismissCallbackRegistry dismissCallbackRegistry;
    public final ReadonlyStateFlow isBypassEnabled;
    public final ReadonlyStateFlow isDeviceEntered;
    public final ReadonlyStateFlow isDeviceEnteredDirectly;
    public final Lazy isLockscreenEnabled$delegate;
    public final ReadonlyStateFlow isUnlocked;
    public final DeviceEntryRepository repository;
    public final SceneInteractor sceneInteractor;
    public final TableLogBuffer tableLogBuffer;

    /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$isAuthenticationRequired$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return DeviceEntryInteractor.this.isAuthenticationRequired(this);
        }
    }

    public DeviceEntryInteractor(CoroutineScope coroutineScope, DeviceEntryRepository deviceEntryRepository, AuthenticationInteractor authenticationInteractor, SceneInteractor sceneInteractor, DeviceUnlockedInteractor deviceUnlockedInteractor, AlternateBouncerInteractor alternateBouncerInteractor, DismissCallbackRegistry dismissCallbackRegistry, SceneBackInteractor sceneBackInteractor, TableLogBuffer tableLogBuffer) {
        this.applicationScope = coroutineScope;
        this.repository = deviceEntryRepository;
        this.authenticationInteractor = authenticationInteractor;
        this.sceneInteractor = sceneInteractor;
        this.deviceUnlockedInteractor = deviceUnlockedInteractor;
        this.alternateBouncerInteractor = alternateBouncerInteractor;
        this.dismissCallbackRegistry = dismissCallbackRegistry;
        this.tableLogBuffer = tableLogBuffer;
        final ReadonlyStateFlow readonlyStateFlow = deviceUnlockedInteractor.deviceUnlockStatus;
        this.isUnlocked = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$map$1

            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        Boolean boolValueOf = Boolean.valueOf(((DeviceUnlockStatus) obj).isUnlocked);
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
                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), Boolean.valueOf(((DeviceUnlockStatus) deviceUnlockedInteractor.deviceUnlockStatus.$$delegate_0.getValue()).isUnlocked));
        final StateFlow stateFlow = sceneInteractor.currentScene;
        Flow flowBuffer$default = FlowKt.buffer$default(FlowKt.mapLatest(new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$filter$1

            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$filter$1$2$1, reason: invalid class name */
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
                        SceneKey sceneKey = (SceneKey) obj;
                        if (Intrinsics.areEqual(sceneKey, Scenes.Gone) || Intrinsics.areEqual(sceneKey, Scenes.Lockscreen)) {
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
                Object objCollect = stateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, new DeviceEntryInteractor$isDeviceEnteredDirectly$2(this, null)), -1, 2);
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        Boolean bool = Boolean.FALSE;
        ReadonlyStateFlow readonlyStateFlowStateIn = FlowKt.stateIn(flowBuffer$default, coroutineScope, startedEagerly, bool);
        this.isDeviceEnteredDirectly = readonlyStateFlowStateIn;
        final ReadonlyStateFlow readonlyStateFlow2 = sceneBackInteractor.backStack;
        final Flow flowPairwise = com.android.systemui.util.kotlin.FlowKt.pairwise(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$map$2

            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$map$2$2$1, reason: invalid class name */
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
                        Iterable sceneStackKt$asIterable$$inlined$Iterable$1 = new SceneStackKt$asIterable$$inlined$Iterable$1((SceneStack) obj);
                        Object next = null;
                        if (sceneStackKt$asIterable$$inlined$Iterable$1 instanceof List) {
                            List list = (List) sceneStackKt$asIterable$$inlined$Iterable$1;
                            if (!list.isEmpty()) {
                                next = PreferenceGroupAdapter$$ExternalSyntheticOutline0.m(1, list);
                            }
                        } else {
                            Iterator it = sceneStackKt$asIterable$$inlined$Iterable$1.iterator();
                            if (it.hasNext()) {
                                next = it.next();
                                while (it.hasNext()) {
                                    next = it.next();
                                }
                            }
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(next, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = readonlyStateFlow2.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }), null);
        this.isDeviceEntered = FlowKt.stateIn(DiffableKt.logDiffsForTable((Flow) new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(readonlyStateFlowStateIn, new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$map$3

            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$map$3$2$1, reason: invalid class name */
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
                        WithPrev withPrev = (WithPrev) obj;
                        Boolean boolValueOf = Boolean.valueOf(Intrinsics.areEqual((SceneKey) withPrev.component1(), Scenes.Lockscreen) && Intrinsics.areEqual((SceneKey) withPrev.component2(), Scenes.Gone));
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
                Object objCollect = flowPairwise.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, new DeviceEntryInteractor$isDeviceEntered$3(null)), tableLogBuffer, "", "isDeviceEntered", false), coroutineScope, startedEagerly, bool);
        final int i = 0;
        this.isLockscreenEnabled$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$$ExternalSyntheticLambda0
            public final /* synthetic */ DeviceEntryInteractor f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        DeviceEntryInteractor deviceEntryInteractor = this.f$0;
                        return new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new DeviceEntryInteractor$isLockscreenEnabled$2$1(deviceEntryInteractor, null), ((DeviceEntryRepositoryImpl) deviceEntryInteractor.repository).isLockscreenEnabled);
                    default:
                        DeviceEntryInteractor deviceEntryInteractor2 = this.f$0;
                        final Flow flow = deviceEntryInteractor2.authenticationInteractor.authenticationMethod;
                        Flow flowLogDiffsForTable = DiffableKt.logDiffsForTable((Flow) FlowKt.combine(new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$canSwipeToEnter_delegate$lambda$6$$inlined$map$1

                            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$canSwipeToEnter_delegate$lambda$6$$inlined$map$1$2, reason: invalid class name */
                            public final class AnonymousClass2 implements FlowCollector {
                                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                                /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$canSwipeToEnter_delegate$lambda$6$$inlined$map$1$2$1, reason: invalid class name */
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
                                        Boolean boolValueOf = Boolean.valueOf(Intrinsics.areEqual((AuthenticationMethodModel) obj, AuthenticationMethodModel.None.INSTANCE));
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
                                Object objCollect = flow.collect(new AnonymousClass2(flowCollector), continuation);
                                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                            }
                        }, (Flow) deviceEntryInteractor2.isLockscreenEnabled$delegate.getValue(), deviceEntryInteractor2.deviceUnlockedInteractor.deviceUnlockStatus, deviceEntryInteractor2.isDeviceEntered, new DeviceEntryInteractor$canSwipeToEnter$2$2(null)), deviceEntryInteractor2.tableLogBuffer, "", "canSwipeToEnter", false);
                        SharingStarted.Companion.getClass();
                        return FlowKt.stateIn(flowLogDiffsForTable, deviceEntryInteractor2.applicationScope, SharingStarted.Companion.Eagerly, null);
                }
            }
        });
        final int i2 = 1;
        this.canSwipeToEnter$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$$ExternalSyntheticLambda0
            public final /* synthetic */ DeviceEntryInteractor f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        DeviceEntryInteractor deviceEntryInteractor = this.f$0;
                        return new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new DeviceEntryInteractor$isLockscreenEnabled$2$1(deviceEntryInteractor, null), ((DeviceEntryRepositoryImpl) deviceEntryInteractor.repository).isLockscreenEnabled);
                    default:
                        DeviceEntryInteractor deviceEntryInteractor2 = this.f$0;
                        final Flow flow = deviceEntryInteractor2.authenticationInteractor.authenticationMethod;
                        Flow flowLogDiffsForTable = DiffableKt.logDiffsForTable((Flow) FlowKt.combine(new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$canSwipeToEnter_delegate$lambda$6$$inlined$map$1

                            /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$canSwipeToEnter_delegate$lambda$6$$inlined$map$1$2, reason: invalid class name */
                            public final class AnonymousClass2 implements FlowCollector {
                                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                                /* renamed from: com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$canSwipeToEnter_delegate$lambda$6$$inlined$map$1$2$1, reason: invalid class name */
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
                                        Boolean boolValueOf = Boolean.valueOf(Intrinsics.areEqual((AuthenticationMethodModel) obj, AuthenticationMethodModel.None.INSTANCE));
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
                                Object objCollect = flow.collect(new AnonymousClass2(flowCollector), continuation);
                                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                            }
                        }, (Flow) deviceEntryInteractor2.isLockscreenEnabled$delegate.getValue(), deviceEntryInteractor2.deviceUnlockedInteractor.deviceUnlockStatus, deviceEntryInteractor2.isDeviceEntered, new DeviceEntryInteractor$canSwipeToEnter$2$2(null)), deviceEntryInteractor2.tableLogBuffer, "", "canSwipeToEnter", false);
                        SharingStarted.Companion.getClass();
                        return FlowKt.stateIn(flowLogDiffsForTable, deviceEntryInteractor2.applicationScope, SharingStarted.Companion.Eagerly, null);
                }
            }
        });
        this.isBypassEnabled = ((DeviceEntryRepositoryImpl) deviceEntryRepository).isBypassEnabled;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object isAuthenticationRequired(ContinuationImpl continuationImpl) {
        AnonymousClass1 anonymousClass1;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object authenticationMethod = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(authenticationMethod);
            if (!((DeviceUnlockStatus) this.deviceUnlockedInteractor.deviceUnlockStatus.$$delegate_0.getValue()).isUnlocked) {
                anonymousClass1.label = 1;
                authenticationMethod = this.authenticationInteractor.getAuthenticationMethod(anonymousClass1);
                if (authenticationMethod == coroutineSingletons) {
                    return coroutineSingletons;
                }
            }
            return Boolean.valueOf(z);
        }
        if (i2 != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(authenticationMethod);
        boolean z = ((AuthenticationMethodModel) authenticationMethod).isSecure;
        return Boolean.valueOf(z);
    }
}
