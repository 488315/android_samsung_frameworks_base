package com.android.systemui.shade.display;

import android.view.Display;
import com.android.systemui.display.data.repository.DisplayRepository;
import com.android.systemui.display.data.repository.DisplayRepositoryImpl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import kotlin.ResultKt;
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

/* loaded from: classes3.dex */
public final class AnyExternalShadeDisplayPolicy implements ShadeDisplayPolicy {
    public static final Set ALLOWED_DISPLAY_TYPES;
    public final ReadonlyStateFlow displayId;

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    Integer num;
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
                        ArrayList arrayList = new ArrayList();
                        for (Object obj3 : (Set) obj) {
                            Display display = (Display) obj3;
                            if (display.getDisplayId() != 0 && AnyExternalShadeDisplayPolicy.ALLOWED_DISPLAY_TYPES.contains(new Integer(display.getType()))) {
                                arrayList.add(obj3);
                            }
                        }
                        Iterator it = arrayList.iterator();
                        if (it.hasNext()) {
                            Integer num2 = new Integer(((Display) it.next()).getDisplayId());
                            while (it.hasNext()) {
                                Integer num3 = new Integer(((Display) it.next()).getDisplayId());
                                if (num2.compareTo(num3) > 0) {
                                    num2 = num3;
                                }
                            }
                            num = num2;
                        } else {
                            num = null;
                        }
                        Integer num4 = new Integer(num != null ? num.intValue() : 0);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(num4, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = displays.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
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
