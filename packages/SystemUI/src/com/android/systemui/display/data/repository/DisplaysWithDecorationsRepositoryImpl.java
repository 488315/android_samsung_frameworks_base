package com.android.systemui.display.data.repository;

import android.view.IWindowManager;
import com.android.systemui.display.data.repository.DisplaysWithDecorationsRepositoryImpl;
import com.android.systemui.statusbar.CommandQueue;
import java.util.ArrayList;
import java.util.Set;
import kotlin.ResultKt;
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

/* loaded from: classes2.dex */
public final class DisplaysWithDecorationsRepositoryImpl implements DisplaysWithDecorationsRepository {
    public final CommandQueue commandQueue;
    public final CallbackFlowBuilder decorationEvents = FlowKt.callbackFlow(new DisplaysWithDecorationsRepositoryImpl$decorationEvents$1(this, null));
    public final ReadonlyStateFlow displayIdsWithSystemDecorations;
    public final Set initialDisplayIdsWithDecorations;
    public final IWindowManager windowManager;

    public abstract class Event {
        public final int displayId;

        public final class Add extends Event {
            public Add(int i) {
                super(i, null);
            }
        }

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
                        DisplaysWithDecorationsRepositoryImpl.Event.Remove remove = new DisplaysWithDecorationsRepositoryImpl.Event.Remove(((Number) obj).intValue());
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(remove, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = displayRemovalEvent.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }), new DisplaysWithDecorationsRepositoryImpl$displayIdsWithSystemDecorations$2(null))), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), set);
    }

    @Override // com.android.systemui.display.data.repository.DisplaysWithDecorationsRepository
    public final StateFlow getDisplayIdsWithSystemDecorations() {
        return this.displayIdsWithSystemDecorations;
    }
}
