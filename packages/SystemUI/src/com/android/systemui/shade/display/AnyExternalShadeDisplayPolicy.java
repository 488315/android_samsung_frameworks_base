package com.android.systemui.shade.display;

import com.android.systemui.display.data.repository.DisplayRepository;
import com.android.systemui.display.data.repository.DisplayRepositoryImpl;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class AnyExternalShadeDisplayPolicy implements ShadeDisplayPolicy {
    public static final Set ALLOWED_DISPLAY_TYPES;
    public final ReadonlyStateFlow displayId;

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
        ALLOWED_DISPLAY_TYPES = ArraysKt___ArraysKt.toSet(new Integer[]{2, 4, 3});
    }

    public AnyExternalShadeDisplayPolicy(DisplayRepository displayRepository, CoroutineScope coroutineScope) {
        final StateFlow displays = ((DisplayRepositoryImpl) displayRepository).displayRepositoryFromLib.getDisplays();
        this.displayId = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.shade.display.AnyExternalShadeDisplayPolicy$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.shade.display.AnyExternalShadeDisplayPolicy$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.shade.display.AnyExternalShadeDisplayPolicy$special$$inlined$map$1$2$1, reason: invalid class name */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x0030  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r8, kotlin.coroutines.Continuation r9) {
                    /*
                        r7 = this;
                        boolean r0 = r9 instanceof com.android.systemui.shade.display.AnyExternalShadeDisplayPolicy$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r9
                        com.android.systemui.shade.display.AnyExternalShadeDisplayPolicy$special$$inlined$map$1$2$1 r0 = (com.android.systemui.shade.display.AnyExternalShadeDisplayPolicy$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.shade.display.AnyExternalShadeDisplayPolicy$special$$inlined$map$1$2$1 r0 = new com.android.systemui.shade.display.AnyExternalShadeDisplayPolicy$special$$inlined$map$1$2$1
                        r0.<init>(r9)
                    L18:
                        java.lang.Object r9 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L30
                        if (r2 != r3) goto L28
                        kotlin.ResultKt.throwOnFailure(r9)
                        goto Lb9
                    L28:
                        java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                        java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
                        r7.<init>(r8)
                        throw r7
                    L30:
                        kotlin.ResultKt.throwOnFailure(r9)
                        java.util.Set r8 = (java.util.Set) r8
                        java.lang.Iterable r8 = (java.lang.Iterable) r8
                        java.util.ArrayList r9 = new java.util.ArrayList
                        r9.<init>()
                        java.util.Iterator r8 = r8.iterator()
                    L40:
                        boolean r2 = r8.hasNext()
                        if (r2 == 0) goto L68
                        java.lang.Object r2 = r8.next()
                        r4 = r2
                        android.view.Display r4 = (android.view.Display) r4
                        int r5 = r4.getDisplayId()
                        if (r5 == 0) goto L40
                        java.util.Set r5 = com.android.systemui.shade.display.AnyExternalShadeDisplayPolicy.ALLOWED_DISPLAY_TYPES
                        int r4 = r4.getType()
                        java.lang.Integer r6 = new java.lang.Integer
                        r6.<init>(r4)
                        boolean r4 = r5.contains(r6)
                        if (r4 == 0) goto L40
                        r9.add(r2)
                        goto L40
                    L68:
                        java.util.Iterator r8 = r9.iterator()
                        boolean r9 = r8.hasNext()
                        if (r9 != 0) goto L74
                        r8 = 0
                        goto La1
                    L74:
                        java.lang.Object r9 = r8.next()
                        android.view.Display r9 = (android.view.Display) r9
                        int r9 = r9.getDisplayId()
                        java.lang.Integer r2 = new java.lang.Integer
                        r2.<init>(r9)
                    L83:
                        boolean r9 = r8.hasNext()
                        if (r9 == 0) goto La0
                        java.lang.Object r9 = r8.next()
                        android.view.Display r9 = (android.view.Display) r9
                        int r9 = r9.getDisplayId()
                        java.lang.Integer r4 = new java.lang.Integer
                        r4.<init>(r9)
                        int r9 = r2.compareTo(r4)
                        if (r9 <= 0) goto L83
                        r2 = r4
                        goto L83
                    La0:
                        r8 = r2
                    La1:
                        if (r8 == 0) goto La8
                        int r8 = r8.intValue()
                        goto La9
                    La8:
                        r8 = 0
                    La9:
                        java.lang.Integer r9 = new java.lang.Integer
                        r9.<init>(r8)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r7 = r7.$this_unsafeFlow
                        java.lang.Object r7 = r7.emit(r9, r0)
                        if (r7 != r1) goto Lb9
                        return r1
                    Lb9:
                        kotlin.Unit r7 = kotlin.Unit.INSTANCE
                        return r7
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.display.AnyExternalShadeDisplayPolicy$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), 0);
    }

    @Override // com.android.systemui.shade.display.ShadeDisplayPolicy
    public final StateFlow getDisplayId() {
        return this.displayId;
    }

    @Override // com.android.systemui.shade.display.ShadeDisplayPolicy
    public final String getName() {
        return "any_external_display";
    }
}
