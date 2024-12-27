package com.android.systemui.keyguard.data.quickaffordance;

import com.android.systemui.CoreStartable;
import com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository;
import com.android.systemui.settings.UserFileManager;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.RingerModeTracker;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class MuteQuickAffordanceCoreStartable implements CoreStartable {
    public final CoroutineDispatcher backgroundDispatcher;
    public final CoroutineScope coroutineScope;
    public final KeyguardQuickAffordanceRepository keyguardQuickAffordanceRepository;
    public final MuteQuickAffordanceCoreStartable$observer$1 observer = new MuteQuickAffordanceCoreStartable$observer$1(this);
    public final RingerModeTracker ringerModeTracker;
    public final UserFileManager userFileManager;
    public final UserTracker userTracker;

    public MuteQuickAffordanceCoreStartable(UserTracker userTracker, RingerModeTracker ringerModeTracker, UserFileManager userFileManager, KeyguardQuickAffordanceRepository keyguardQuickAffordanceRepository, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher) {
        this.userTracker = userTracker;
        this.ringerModeTracker = ringerModeTracker;
        this.userFileManager = userFileManager;
        this.keyguardQuickAffordanceRepository = keyguardQuickAffordanceRepository;
        this.coroutineScope = coroutineScope;
        this.backgroundDispatcher = coroutineDispatcher;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        final ReadonlyStateFlow readonlyStateFlow = this.keyguardQuickAffordanceRepository.selections;
        FlowKt.launchIn(new Flow() { // from class: com.android.systemui.keyguard.data.quickaffordance.MuteQuickAffordanceCoreStartable$start$$inlined$map$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.keyguard.data.quickaffordance.MuteQuickAffordanceCoreStartable$start$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ MuteQuickAffordanceCoreStartable this$0;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.keyguard.data.quickaffordance.MuteQuickAffordanceCoreStartable$start$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, MuteQuickAffordanceCoreStartable muteQuickAffordanceCoreStartable) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = muteQuickAffordanceCoreStartable;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x0030  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
                    /*
                        r6 = this;
                        boolean r0 = r8 instanceof com.android.systemui.keyguard.data.quickaffordance.MuteQuickAffordanceCoreStartable$start$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r8
                        com.android.systemui.keyguard.data.quickaffordance.MuteQuickAffordanceCoreStartable$start$$inlined$map$1$2$1 r0 = (com.android.systemui.keyguard.data.quickaffordance.MuteQuickAffordanceCoreStartable$start$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.data.quickaffordance.MuteQuickAffordanceCoreStartable$start$$inlined$map$1$2$1 r0 = new com.android.systemui.keyguard.data.quickaffordance.MuteQuickAffordanceCoreStartable$start$$inlined$map$1$2$1
                        r0.<init>(r8)
                    L18:
                        java.lang.Object r8 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L30
                        if (r2 != r3) goto L28
                        kotlin.ResultKt.throwOnFailure(r8)
                        goto Lab
                    L28:
                        java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                        java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                        r6.<init>(r7)
                        throw r6
                    L30:
                        kotlin.ResultKt.throwOnFailure(r8)
                        java.util.Map r7 = (java.util.Map) r7
                        java.util.Collection r7 = r7.values()
                        java.lang.Iterable r7 = (java.lang.Iterable) r7
                        boolean r8 = r7 instanceof java.util.Collection
                        com.android.systemui.keyguard.data.quickaffordance.MuteQuickAffordanceCoreStartable r2 = r6.this$0
                        if (r8 == 0) goto L4b
                        r8 = r7
                        java.util.Collection r8 = (java.util.Collection) r8
                        boolean r8 = r8.isEmpty()
                        if (r8 == 0) goto L4b
                        goto L93
                    L4b:
                        java.util.Iterator r7 = r7.iterator()
                    L4f:
                        boolean r8 = r7.hasNext()
                        if (r8 == 0) goto L93
                        java.lang.Object r8 = r7.next()
                        java.util.List r8 = (java.util.List) r8
                        java.lang.Iterable r8 = (java.lang.Iterable) r8
                        boolean r4 = r8 instanceof java.util.Collection
                        if (r4 == 0) goto L6b
                        r4 = r8
                        java.util.Collection r4 = (java.util.Collection) r4
                        boolean r4 = r4.isEmpty()
                        if (r4 == 0) goto L6b
                        goto L4f
                    L6b:
                        java.util.Iterator r8 = r8.iterator()
                    L6f:
                        boolean r4 = r8.hasNext()
                        if (r4 == 0) goto L4f
                        java.lang.Object r4 = r8.next()
                        com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig r4 = (com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig) r4
                        java.lang.String r4 = r4.getKey()
                        java.lang.String r5 = "mute"
                        boolean r4 = kotlin.jvm.internal.Intrinsics.areEqual(r4, r5)
                        if (r4 == 0) goto L6f
                        com.android.systemui.util.RingerModeTracker r7 = r2.ringerModeTracker
                        androidx.lifecycle.LiveData r7 = r7.getRingerModeInternal()
                        com.android.systemui.keyguard.data.quickaffordance.MuteQuickAffordanceCoreStartable$observer$1 r8 = r2.observer
                        r7.observeForever(r8)
                        goto L9e
                    L93:
                        com.android.systemui.util.RingerModeTracker r7 = r2.ringerModeTracker
                        androidx.lifecycle.LiveData r7 = r7.getRingerModeInternal()
                        com.android.systemui.keyguard.data.quickaffordance.MuteQuickAffordanceCoreStartable$observer$1 r8 = r2.observer
                        r7.removeObserver(r8)
                    L9e:
                        kotlin.Unit r7 = kotlin.Unit.INSTANCE
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r6 = r6.$this_unsafeFlow
                        java.lang.Object r6 = r6.emit(r7, r0)
                        if (r6 != r1) goto Lab
                        return r1
                    Lab:
                        kotlin.Unit r6 = kotlin.Unit.INSTANCE
                        return r6
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.data.quickaffordance.MuteQuickAffordanceCoreStartable$start$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, this.coroutineScope);
    }
}
