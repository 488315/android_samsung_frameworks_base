package com.android.systemui.statusbar.chips.casttootherdevice.domain.interactor;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.mediarouter.data.repository.MediaRouterRepository;
import com.android.systemui.mediarouter.data.repository.MediaRouterRepositoryImpl;
import com.android.systemui.statusbar.chips.StatusBarChipLogTags;
import com.android.systemui.statusbar.chips.casttootherdevice.domain.model.MediaRouterCastModel;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class MediaRouterChipInteractor {
    public static final String TAG;
    public final ReadonlyStateFlow activeCastDevice;
    public final LogBuffer logger;
    public final ReadonlyStateFlow mediaRouterCastingState;
    public final MediaRouterRepository mediaRouterRepository;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        StatusBarChipLogTags.INSTANCE.getClass();
        TAG = StringsKt__StringsKt.padEnd(20, "MediaRouter");
    }

    public MediaRouterChipInteractor(CoroutineScope coroutineScope, MediaRouterRepository mediaRouterRepository, LogBuffer logBuffer) {
        this.mediaRouterRepository = mediaRouterRepository;
        this.logger = logBuffer;
        final ReadonlyStateFlow readonlyStateFlow = ((MediaRouterRepositoryImpl) mediaRouterRepository).castDevices;
        Flow flow = new Flow() { // from class: com.android.systemui.statusbar.chips.casttootherdevice.domain.interactor.MediaRouterChipInteractor$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.chips.casttootherdevice.domain.interactor.MediaRouterChipInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.statusbar.chips.casttootherdevice.domain.interactor.MediaRouterChipInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.chips.casttootherdevice.domain.interactor.MediaRouterChipInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.chips.casttootherdevice.domain.interactor.MediaRouterChipInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.chips.casttootherdevice.domain.interactor.MediaRouterChipInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.chips.casttootherdevice.domain.interactor.MediaRouterChipInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.chips.casttootherdevice.domain.interactor.MediaRouterChipInteractor$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L58
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.util.List r5 = (java.util.List) r5
                        java.lang.Iterable r5 = (java.lang.Iterable) r5
                        java.util.Iterator r5 = r5.iterator()
                    L3a:
                        boolean r6 = r5.hasNext()
                        if (r6 == 0) goto L4c
                        java.lang.Object r6 = r5.next()
                        r2 = r6
                        com.android.systemui.statusbar.policy.CastDevice r2 = (com.android.systemui.statusbar.policy.CastDevice) r2
                        boolean r2 = r2.isCasting
                        if (r2 == 0) goto L3a
                        goto L4d
                    L4c:
                        r6 = 0
                    L4d:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r6, r0)
                        if (r4 != r1) goto L58
                        return r1
                    L58:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.chips.casttootherdevice.domain.interactor.MediaRouterChipInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion companion = SharingStarted.Companion;
        final ReadonlyStateFlow stateIn = FlowKt.stateIn(flow, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), null);
        this.activeCastDevice = stateIn;
        this.mediaRouterCastingState = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.statusbar.chips.casttootherdevice.domain.interactor.MediaRouterChipInteractor$special$$inlined$map$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.chips.casttootherdevice.domain.interactor.MediaRouterChipInteractor$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ MediaRouterChipInteractor this$0;

                /* renamed from: com.android.systemui.statusbar.chips.casttootherdevice.domain.interactor.MediaRouterChipInteractor$special$$inlined$map$2$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, MediaRouterChipInteractor mediaRouterChipInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = mediaRouterChipInteractor;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r8, kotlin.coroutines.Continuation r9) {
                    /*
                        r7 = this;
                        boolean r0 = r9 instanceof com.android.systemui.statusbar.chips.casttootherdevice.domain.interactor.MediaRouterChipInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r9
                        com.android.systemui.statusbar.chips.casttootherdevice.domain.interactor.MediaRouterChipInteractor$special$$inlined$map$2$2$1 r0 = (com.android.systemui.statusbar.chips.casttootherdevice.domain.interactor.MediaRouterChipInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.chips.casttootherdevice.domain.interactor.MediaRouterChipInteractor$special$$inlined$map$2$2$1 r0 = new com.android.systemui.statusbar.chips.casttootherdevice.domain.interactor.MediaRouterChipInteractor$special$$inlined$map$2$2$1
                        r0.<init>(r9)
                    L18:
                        java.lang.Object r9 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r9)
                        goto L71
                    L27:
                        java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                        java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
                        r7.<init>(r8)
                        throw r7
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r9)
                        com.android.systemui.statusbar.policy.CastDevice r8 = (com.android.systemui.statusbar.policy.CastDevice) r8
                        r9 = 0
                        com.android.systemui.statusbar.chips.casttootherdevice.domain.interactor.MediaRouterChipInteractor r2 = r7.this$0
                        if (r8 == 0) goto L55
                        com.android.systemui.log.LogBuffer r2 = r2.logger
                        java.lang.String r4 = com.android.systemui.statusbar.chips.casttootherdevice.domain.interactor.MediaRouterChipInteractor.TAG
                        com.android.systemui.log.core.LogLevel r5 = com.android.systemui.log.core.LogLevel.INFO
                        com.android.systemui.statusbar.chips.casttootherdevice.domain.interactor.MediaRouterChipInteractor$mediaRouterCastingState$1$2 r6 = com.android.systemui.statusbar.chips.casttootherdevice.domain.interactor.MediaRouterChipInteractor$mediaRouterCastingState$1$2.INSTANCE
                        com.android.systemui.log.core.LogMessage r9 = r2.obtain(r4, r5, r6, r9)
                        r4 = r9
                        com.android.systemui.log.LogMessageImpl r4 = (com.android.systemui.log.LogMessageImpl) r4
                        java.lang.String r8 = r8.name
                        r4.str1 = r8
                        r2.commit(r9)
                        com.android.systemui.statusbar.chips.casttootherdevice.domain.model.MediaRouterCastModel$Casting r9 = new com.android.systemui.statusbar.chips.casttootherdevice.domain.model.MediaRouterCastModel$Casting
                        r9.<init>(r8)
                        goto L66
                    L55:
                        com.android.systemui.log.LogBuffer r8 = r2.logger
                        java.lang.String r2 = com.android.systemui.statusbar.chips.casttootherdevice.domain.interactor.MediaRouterChipInteractor.TAG
                        com.android.systemui.log.core.LogLevel r4 = com.android.systemui.log.core.LogLevel.INFO
                        com.android.systemui.statusbar.chips.casttootherdevice.domain.interactor.MediaRouterChipInteractor$mediaRouterCastingState$1$4 r5 = com.android.systemui.statusbar.chips.casttootherdevice.domain.interactor.MediaRouterChipInteractor$mediaRouterCastingState$1$4.INSTANCE
                        com.android.systemui.log.core.LogMessage r9 = r8.obtain(r2, r4, r5, r9)
                        r8.commit(r9)
                        com.android.systemui.statusbar.chips.casttootherdevice.domain.model.MediaRouterCastModel$DoingNothing r9 = com.android.systemui.statusbar.chips.casttootherdevice.domain.model.MediaRouterCastModel.DoingNothing.INSTANCE
                    L66:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r7 = r7.$this_unsafeFlow
                        java.lang.Object r7 = r7.emit(r9, r0)
                        if (r7 != r1) goto L71
                        return r1
                    L71:
                        kotlin.Unit r7 = kotlin.Unit.INSTANCE
                        return r7
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.chips.casttootherdevice.domain.interactor.MediaRouterChipInteractor$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), MediaRouterCastModel.DoingNothing.INSTANCE);
    }
}
