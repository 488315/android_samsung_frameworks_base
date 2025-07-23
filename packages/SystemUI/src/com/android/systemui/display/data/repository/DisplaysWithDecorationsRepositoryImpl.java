package com.android.systemui.display.data.repository;

import android.view.IWindowManager;
import com.android.systemui.statusbar.CommandQueue;
import java.util.ArrayList;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.CallbackFlowBuilder;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$runningFold$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DisplaysWithDecorationsRepositoryImpl implements DisplaysWithDecorationsRepository {
    public final CommandQueue commandQueue;
    public final CallbackFlowBuilder decorationEvents = FlowKt.callbackFlow(new DisplaysWithDecorationsRepositoryImpl$decorationEvents$1(this, null));
    public final ReadonlyStateFlow displayIdsWithSystemDecorations;
    public final Set initialDisplayIdsWithDecorations;
    public final IWindowManager windowManager;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Event {
        public final int displayId;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Add extends Event {
            public Add(int i) {
                super(i, null);
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Remove extends Event {
            public Remove(int i) {
                super(i, null);
            }
        }

        public /* synthetic */ Event(int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(i);
        }

        private Event(int i) {
            this.displayId = i;
        }
    }

    public DisplaysWithDecorationsRepositoryImpl(CommandQueue commandQueue, IWindowManager iWindowManager, CoroutineScope coroutineScope, com.android.app.displaylib.DisplayRepository displayRepository) {
        this.commandQueue = commandQueue;
        this.windowManager = iWindowManager;
        Iterable iterable = (Iterable) displayRepository.getDisplayIds().getValue();
        ArrayList arrayList = new ArrayList();
        for (Object obj : iterable) {
            if (this.windowManager.shouldShowSystemDecors(((Number) obj).intValue())) {
                arrayList.add(obj);
            }
        }
        Set set = CollectionsKt___CollectionsKt.toSet(arrayList);
        this.initialDisplayIdsWithDecorations = set;
        CallbackFlowBuilder callbackFlowBuilder = this.decorationEvents;
        final Flow displayRemovalEvent = displayRepository.getDisplayRemovalEvent();
        this.displayIdsWithSystemDecorations = FlowKt.stateIn(FlowKt.distinctUntilChanged(new FlowKt__TransformKt$runningFold$$inlined$unsafeFlow$1(set, FlowKt.merge(callbackFlowBuilder, new Flow() { // from class: com.android.systemui.display.data.repository.DisplaysWithDecorationsRepositoryImpl$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.display.data.repository.DisplaysWithDecorationsRepositoryImpl$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.display.data.repository.DisplaysWithDecorationsRepositoryImpl$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.display.data.repository.DisplaysWithDecorationsRepositoryImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.display.data.repository.DisplaysWithDecorationsRepositoryImpl$special$$inlined$map$1$2$1 r0 = (com.android.systemui.display.data.repository.DisplaysWithDecorationsRepositoryImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.display.data.repository.DisplaysWithDecorationsRepositoryImpl$special$$inlined$map$1$2$1 r0 = new com.android.systemui.display.data.repository.DisplaysWithDecorationsRepositoryImpl$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L48
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Number r5 = (java.lang.Number) r5
                        int r5 = r5.intValue()
                        com.android.systemui.display.data.repository.DisplaysWithDecorationsRepositoryImpl$Event$Remove r6 = new com.android.systemui.display.data.repository.DisplaysWithDecorationsRepositoryImpl$Event$Remove
                        r6.<init>(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r6, r0)
                        if (r4 != r1) goto L48
                        return r1
                    L48:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.display.data.repository.DisplaysWithDecorationsRepositoryImpl$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), new DisplaysWithDecorationsRepositoryImpl$displayIdsWithSystemDecorations$2(null))), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), set);
    }

    @Override // com.android.systemui.display.data.repository.DisplaysWithDecorationsRepository
    public final StateFlow getDisplayIdsWithSystemDecorations() {
        return this.displayIdsWithSystemDecorations;
    }
}
