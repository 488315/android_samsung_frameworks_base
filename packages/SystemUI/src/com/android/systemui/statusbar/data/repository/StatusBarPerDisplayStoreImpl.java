package com.android.systemui.statusbar.data.repository;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.display.data.repository.DisplayRepository;
import com.android.systemui.display.data.repository.DisplayRepositoryImpl;
import com.android.systemui.display.data.repository.PerDisplayStoreImpl;
import com.android.systemui.util.kotlin.FlowKt;
import java.util.Iterator;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.SetsKt___SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes3.dex */
public abstract class StatusBarPerDisplayStoreImpl extends PerDisplayStoreImpl {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CoroutineScope backgroundApplicationScope;
    public final DisplayRepository displayRepository;

    /* renamed from: com.android.systemui.statusbar.data.repository.StatusBarPerDisplayStoreImpl$start$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        /* renamed from: com.android.systemui.statusbar.data.repository.StatusBarPerDisplayStoreImpl$start$1$1, reason: invalid class name and collision with other inner class name */
        final class C04901 extends SuspendLambda implements Function3 {
            /* synthetic */ Object L$0;
            /* synthetic */ Object L$1;
            int label;

            public C04901(Continuation continuation) {
                super(3, continuation);
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                C04901 c04901 = new C04901((Continuation) obj3);
                c04901.L$0 = (Set) obj;
                c04901.L$1 = (Set) obj2;
                return c04901.invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return SetsKt___SetsKt.minus((Set) this.L$0, (Iterable) this.L$1);
            }
        }

        /* renamed from: com.android.systemui.statusbar.data.repository.StatusBarPerDisplayStoreImpl$start$1$2, reason: invalid class name */
        public final class AnonymousClass2 implements FlowCollector {
            public final /* synthetic */ StatusBarPerDisplayStoreImpl this$0;

            public AnonymousClass2(StatusBarPerDisplayStoreImpl statusBarPerDisplayStoreImpl) {
                this.this$0 = statusBarPerDisplayStoreImpl;
            }

            /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
            @Override // kotlinx.coroutines.flow.FlowCollector
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object emit(Set set, Continuation continuation) {
                StatusBarPerDisplayStoreImpl$start$1$2$emit$1 statusBarPerDisplayStoreImpl$start$1$2$emit$1;
                StatusBarPerDisplayStoreImpl statusBarPerDisplayStoreImpl;
                Iterator it;
                if (continuation instanceof StatusBarPerDisplayStoreImpl$start$1$2$emit$1) {
                    statusBarPerDisplayStoreImpl$start$1$2$emit$1 = (StatusBarPerDisplayStoreImpl$start$1$2$emit$1) continuation;
                    int i = statusBarPerDisplayStoreImpl$start$1$2$emit$1.label;
                    if ((i & Integer.MIN_VALUE) != 0) {
                        statusBarPerDisplayStoreImpl$start$1$2$emit$1.label = i - Integer.MIN_VALUE;
                    } else {
                        statusBarPerDisplayStoreImpl$start$1$2$emit$1 = new StatusBarPerDisplayStoreImpl$start$1$2$emit$1(this, continuation);
                    }
                }
                Object obj = statusBarPerDisplayStoreImpl$start$1$2$emit$1.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i2 = statusBarPerDisplayStoreImpl$start$1$2$emit$1.label;
                if (i2 == 0) {
                    ResultKt.throwOnFailure(obj);
                    Iterator it2 = set.iterator();
                    statusBarPerDisplayStoreImpl = this.this$0;
                    it = it2;
                } else {
                    if (i2 != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    it = (Iterator) statusBarPerDisplayStoreImpl$start$1$2$emit$1.L$1;
                    statusBarPerDisplayStoreImpl = (StatusBarPerDisplayStoreImpl) statusBarPerDisplayStoreImpl$start$1$2$emit$1.L$0;
                    ResultKt.throwOnFailure(obj);
                }
                while (it.hasNext()) {
                    int iIntValue = ((Number) it.next()).intValue();
                    int i3 = StatusBarPerDisplayStoreImpl.$r8$clinit;
                    Object objRemove = statusBarPerDisplayStoreImpl.perDisplayInstances.remove(new Integer(iIntValue));
                    if (objRemove != null) {
                        statusBarPerDisplayStoreImpl$start$1$2$emit$1.L$0 = statusBarPerDisplayStoreImpl;
                        statusBarPerDisplayStoreImpl$start$1$2$emit$1.L$1 = it;
                        statusBarPerDisplayStoreImpl$start$1$2$emit$1.label = 1;
                        if (statusBarPerDisplayStoreImpl.onDisplayRemovalAction(objRemove) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    }
                }
                return Unit.INSTANCE;
            }
        }

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return StatusBarPerDisplayStoreImpl.this.new AnonymousClass1(continuation);
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
                Flow flowPairwiseBy = FlowKt.pairwiseBy(((DisplayRepositoryImpl) StatusBarPerDisplayStoreImpl.this.displayRepository).displaysWithDecorationsRepositoryImpl.getDisplayIdsWithSystemDecorations(), new C04901(null));
                AnonymousClass2 anonymousClass2 = new AnonymousClass2(StatusBarPerDisplayStoreImpl.this);
                this.label = 1;
                if (flowPairwiseBy.collect(anonymousClass2, this) == coroutineSingletons) {
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

    public StatusBarPerDisplayStoreImpl(CoroutineScope coroutineScope, DisplayRepository displayRepository) {
        super(coroutineScope, displayRepository);
        this.backgroundApplicationScope = coroutineScope;
        this.displayRepository = displayRepository;
    }

    @Override // com.android.systemui.display.data.repository.PerDisplayStoreImpl, com.android.systemui.CoreStartable
    public final void start() {
        getInstanceClass().getSimpleName();
        CoroutineTracingKt.launchTraced$default(this.backgroundApplicationScope, null, null, new AnonymousClass1(null), 6);
    }
}
