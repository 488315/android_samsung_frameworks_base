package com.android.systemui.deviceentry.domain.interactor;

import com.android.systemui.authentication.domain.interactor.AuthenticationInteractor;
import com.android.systemui.bouncer.domain.interactor.AlternateBouncerInteractor;
import com.android.systemui.deviceentry.data.repository.DeviceEntryRepository;
import com.android.systemui.deviceentry.data.repository.DeviceEntryRepositoryImpl;
import com.android.systemui.deviceentry.shared.model.DeviceUnlockStatus;
import com.android.systemui.keyguard.DismissCallbackRegistry;
import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.scene.domain.interactor.SceneBackInteractor;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L45
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.deviceentry.shared.model.DeviceUnlockStatus r5 = (com.android.systemui.deviceentry.shared.model.DeviceUnlockStatus) r5
                        boolean r5 = r5.isUnlocked
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L45
                        return r1
                    L45:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), Boolean.valueOf(((DeviceUnlockStatus) deviceUnlockedInteractor.deviceUnlockStatus.$$delegate_0.getValue()).isUnlocked));
        final StateFlow stateFlow = sceneInteractor.currentScene;
        Flow buffer$default = FlowKt.buffer$default(FlowKt.mapLatest(new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$filter$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$filter$1$2$1 r0 = (com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$filter$1$2$1 r0 = new com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$filter$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L50
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        r6 = r5
                        com.android.compose.animation.scene.SceneKey r6 = (com.android.compose.animation.scene.SceneKey) r6
                        com.android.compose.animation.scene.SceneKey r2 = com.android.systemui.scene.shared.model.Scenes.Gone
                        boolean r2 = kotlin.jvm.internal.Intrinsics.areEqual(r6, r2)
                        if (r2 != 0) goto L45
                        com.android.compose.animation.scene.SceneKey r2 = com.android.systemui.scene.shared.model.Scenes.Lockscreen
                        boolean r6 = kotlin.jvm.internal.Intrinsics.areEqual(r6, r2)
                        if (r6 == 0) goto L50
                    L45:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L50
                        return r1
                    L50:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, new DeviceEntryInteractor$isDeviceEnteredDirectly$2(this, null)), -1, 2);
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        Boolean bool = Boolean.FALSE;
        ReadonlyStateFlow stateIn = FlowKt.stateIn(buffer$default, coroutineScope, startedEagerly, bool);
        this.isDeviceEnteredDirectly = stateIn;
        final ReadonlyStateFlow readonlyStateFlow2 = sceneBackInteractor.backStack;
        final Flow pairwise = com.android.systemui.util.kotlin.FlowKt.pairwise(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$map$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$map$2$2$1 r0 = (com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$map$2$2$1 r0 = new com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$map$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L72
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.scene.data.model.SceneStack r5 = (com.android.systemui.scene.data.model.SceneStack) r5
                        com.android.systemui.scene.data.model.SceneStackKt$asIterable$$inlined$Iterable$1 r6 = new com.android.systemui.scene.data.model.SceneStackKt$asIterable$$inlined$Iterable$1
                        r6.<init>(r5)
                        boolean r5 = r6 instanceof java.util.List
                        r2 = 0
                        if (r5 == 0) goto L4c
                        java.util.List r6 = (java.util.List) r6
                        boolean r5 = r6.isEmpty()
                        if (r5 == 0) goto L47
                        goto L67
                    L47:
                        java.lang.Object r2 = androidx.preference.PreferenceGroupAdapter$$ExternalSyntheticOutline0.m(r3, r6)
                        goto L67
                    L4c:
                        java.util.Iterator r5 = r6.iterator()
                        boolean r6 = r5.hasNext()
                        if (r6 != 0) goto L57
                        goto L67
                    L57:
                        java.lang.Object r6 = r5.next()
                        r2 = r6
                    L5c:
                        boolean r6 = r5.hasNext()
                        if (r6 == 0) goto L67
                        java.lang.Object r2 = r5.next()
                        goto L5c
                    L67:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r2, r0)
                        if (r4 != r1) goto L72
                        return r1
                    L72:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), null);
        this.isDeviceEntered = FlowKt.stateIn(DiffableKt.logDiffsForTable((Flow) new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(stateIn, new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$map$3

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$map$3.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$map$3$2$1 r0 = (com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$map$3.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$map$3$2$1 r0 = new com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$map$3$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L62
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.util.kotlin.WithPrev r5 = (com.android.systemui.util.kotlin.WithPrev) r5
                        java.lang.Object r6 = r5.component1()
                        com.android.compose.animation.scene.SceneKey r6 = (com.android.compose.animation.scene.SceneKey) r6
                        java.lang.Object r5 = r5.component2()
                        com.android.compose.animation.scene.SceneKey r5 = (com.android.compose.animation.scene.SceneKey) r5
                        com.android.compose.animation.scene.SceneKey r2 = com.android.systemui.scene.shared.model.Scenes.Lockscreen
                        boolean r6 = kotlin.jvm.internal.Intrinsics.areEqual(r6, r2)
                        if (r6 == 0) goto L52
                        com.android.compose.animation.scene.SceneKey r6 = com.android.systemui.scene.shared.model.Scenes.Gone
                        boolean r5 = kotlin.jvm.internal.Intrinsics.areEqual(r5, r6)
                        if (r5 == 0) goto L52
                        r5 = r3
                        goto L53
                    L52:
                        r5 = 0
                    L53:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L62
                        return r1
                    L62:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$special$$inlined$map$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
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
                        Flow logDiffsForTable = DiffableKt.logDiffsForTable((Flow) FlowKt.combine(new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$canSwipeToEnter_delegate$lambda$6$$inlined$map$1

                            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

                                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                                @Override // kotlinx.coroutines.flow.FlowCollector
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                                */
                                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                                    /*
                                        r4 = this;
                                        boolean r0 = r6 instanceof com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$canSwipeToEnter_delegate$lambda$6$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                        if (r0 == 0) goto L13
                                        r0 = r6
                                        com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$canSwipeToEnter_delegate$lambda$6$$inlined$map$1$2$1 r0 = (com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$canSwipeToEnter_delegate$lambda$6$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                        int r1 = r0.label
                                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                        r3 = r1 & r2
                                        if (r3 == 0) goto L13
                                        int r1 = r1 - r2
                                        r0.label = r1
                                        goto L18
                                    L13:
                                        com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$canSwipeToEnter_delegate$lambda$6$$inlined$map$1$2$1 r0 = new com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$canSwipeToEnter_delegate$lambda$6$$inlined$map$1$2$1
                                        r0.<init>(r6)
                                    L18:
                                        java.lang.Object r6 = r0.result
                                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                        int r2 = r0.label
                                        r3 = 1
                                        if (r2 == 0) goto L2f
                                        if (r2 != r3) goto L27
                                        kotlin.ResultKt.throwOnFailure(r6)
                                        goto L49
                                    L27:
                                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                        r4.<init>(r5)
                                        throw r4
                                    L2f:
                                        kotlin.ResultKt.throwOnFailure(r6)
                                        com.android.systemui.authentication.shared.model.AuthenticationMethodModel r5 = (com.android.systemui.authentication.shared.model.AuthenticationMethodModel) r5
                                        com.android.systemui.authentication.shared.model.AuthenticationMethodModel$None r6 = com.android.systemui.authentication.shared.model.AuthenticationMethodModel.None.INSTANCE
                                        boolean r5 = kotlin.jvm.internal.Intrinsics.areEqual(r5, r6)
                                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                                        r0.label = r3
                                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                        java.lang.Object r4 = r4.emit(r5, r0)
                                        if (r4 != r1) goto L49
                                        return r1
                                    L49:
                                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                        return r4
                                    */
                                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$canSwipeToEnter_delegate$lambda$6$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                                }
                            }

                            @Override // kotlinx.coroutines.flow.Flow
                            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                            }
                        }, (Flow) deviceEntryInteractor2.isLockscreenEnabled$delegate.getValue(), deviceEntryInteractor2.deviceUnlockedInteractor.deviceUnlockStatus, deviceEntryInteractor2.isDeviceEntered, new DeviceEntryInteractor$canSwipeToEnter$2$2(null)), deviceEntryInteractor2.tableLogBuffer, "", "canSwipeToEnter", false);
                        SharingStarted.Companion.getClass();
                        return FlowKt.stateIn(logDiffsForTable, deviceEntryInteractor2.applicationScope, SharingStarted.Companion.Eagerly, null);
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
                        Flow logDiffsForTable = DiffableKt.logDiffsForTable((Flow) FlowKt.combine(new Flow() { // from class: com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$canSwipeToEnter_delegate$lambda$6$$inlined$map$1

                            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

                                @Override // kotlinx.coroutines.flow.FlowCollector
                                public final Object emit(Object obj, Continuation continuation) {
                                    /*
                                        this = this;
                                        boolean r0 = r6 instanceof com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$canSwipeToEnter_delegate$lambda$6$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                        if (r0 == 0) goto L13
                                        r0 = r6
                                        com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$canSwipeToEnter_delegate$lambda$6$$inlined$map$1$2$1 r0 = (com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$canSwipeToEnter_delegate$lambda$6$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                        int r1 = r0.label
                                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                        r3 = r1 & r2
                                        if (r3 == 0) goto L13
                                        int r1 = r1 - r2
                                        r0.label = r1
                                        goto L18
                                    L13:
                                        com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$canSwipeToEnter_delegate$lambda$6$$inlined$map$1$2$1 r0 = new com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$canSwipeToEnter_delegate$lambda$6$$inlined$map$1$2$1
                                        r0.<init>(r6)
                                    L18:
                                        java.lang.Object r6 = r0.result
                                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                        int r2 = r0.label
                                        r3 = 1
                                        if (r2 == 0) goto L2f
                                        if (r2 != r3) goto L27
                                        kotlin.ResultKt.throwOnFailure(r6)
                                        goto L49
                                    L27:
                                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                        r4.<init>(r5)
                                        throw r4
                                    L2f:
                                        kotlin.ResultKt.throwOnFailure(r6)
                                        com.android.systemui.authentication.shared.model.AuthenticationMethodModel r5 = (com.android.systemui.authentication.shared.model.AuthenticationMethodModel) r5
                                        com.android.systemui.authentication.shared.model.AuthenticationMethodModel$None r6 = com.android.systemui.authentication.shared.model.AuthenticationMethodModel.None.INSTANCE
                                        boolean r5 = kotlin.jvm.internal.Intrinsics.areEqual(r5, r6)
                                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                                        r0.label = r3
                                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                        java.lang.Object r4 = r4.emit(r5, r0)
                                        if (r4 != r1) goto L49
                                        return r1
                                    L49:
                                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                        return r4
                                    */
                                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$canSwipeToEnter_delegate$lambda$6$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                                }
                            }

                            @Override // kotlinx.coroutines.flow.Flow
                            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                            }
                        }, (Flow) deviceEntryInteractor2.isLockscreenEnabled$delegate.getValue(), deviceEntryInteractor2.deviceUnlockedInteractor.deviceUnlockStatus, deviceEntryInteractor2.isDeviceEntered, new DeviceEntryInteractor$canSwipeToEnter$2$2(null)), deviceEntryInteractor2.tableLogBuffer, "", "canSwipeToEnter", false);
                        SharingStarted.Companion.getClass();
                        return FlowKt.stateIn(logDiffsForTable, deviceEntryInteractor2.applicationScope, SharingStarted.Companion.Eagerly, null);
                }
            }
        });
        this.isBypassEnabled = ((DeviceEntryRepositoryImpl) deviceEntryRepository).isBypassEnabled;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0051, code lost:
    
        if (((com.android.systemui.authentication.shared.model.AuthenticationMethodModel) r5).isSecure == false) goto L22;
     */
    /* JADX WARN: Removed duplicated region for block: B:18:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object isAuthenticationRequired(kotlin.coroutines.jvm.internal.ContinuationImpl r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$isAuthenticationRequired$1
            if (r0 == 0) goto L13
            r0 = r5
            com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$isAuthenticationRequired$1 r0 = (com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$isAuthenticationRequired$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$isAuthenticationRequired$1 r0 = new com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor$isAuthenticationRequired$1
            r0.<init>(r4, r5)
        L18:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 != r3) goto L27
            kotlin.ResultKt.throwOnFailure(r5)
            goto L4d
        L27:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2f:
            kotlin.ResultKt.throwOnFailure(r5)
            com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor r5 = r4.deviceUnlockedInteractor
            kotlinx.coroutines.flow.ReadonlyStateFlow r5 = r5.deviceUnlockStatus
            kotlinx.coroutines.flow.StateFlow r5 = r5.$$delegate_0
            java.lang.Object r5 = r5.getValue()
            com.android.systemui.deviceentry.shared.model.DeviceUnlockStatus r5 = (com.android.systemui.deviceentry.shared.model.DeviceUnlockStatus) r5
            boolean r5 = r5.isUnlocked
            if (r5 != 0) goto L54
            r0.label = r3
            com.android.systemui.authentication.domain.interactor.AuthenticationInteractor r4 = r4.authenticationInteractor
            java.lang.Object r5 = r4.getAuthenticationMethod(r0)
            if (r5 != r1) goto L4d
            return r1
        L4d:
            com.android.systemui.authentication.shared.model.AuthenticationMethodModel r5 = (com.android.systemui.authentication.shared.model.AuthenticationMethodModel) r5
            boolean r4 = r5.isSecure
            if (r4 == 0) goto L54
            goto L55
        L54:
            r3 = 0
        L55:
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r3)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor.isAuthenticationRequired(kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
