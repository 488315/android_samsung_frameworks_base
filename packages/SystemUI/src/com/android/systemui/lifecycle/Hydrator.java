package com.android.systemui.lifecycle;

import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.plugins.log.TableLogBufferBase;
import java.util.ArrayList;
import java.util.List;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes2.dex */
public final class Hydrator extends ExclusiveActivatable {
    public final List children;
    public final TableLogBuffer tableLogBuffer;
    public final String traceName;

    public final class NamedActivatable {
        public final Activatable activatable;
        public final String traceName;

        public NamedActivatable(String str, Activatable activatable) {
            this.traceName = str;
            this.activatable = activatable;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof NamedActivatable)) {
                return false;
            }
            NamedActivatable namedActivatable = (NamedActivatable) obj;
            return Intrinsics.areEqual(this.traceName, namedActivatable.traceName) && Intrinsics.areEqual(this.activatable, namedActivatable.activatable);
        }

        public final int hashCode() {
            String str = this.traceName;
            return this.activatable.hashCode() + ((str == null ? 0 : str.hashCode()) * 31);
        }

        public final String toString() {
            return "NamedActivatable(traceName=" + this.traceName + ", activatable=" + this.activatable + ")";
        }
    }

    /* renamed from: com.android.systemui.lifecycle.Hydrator$hydratedStateOf$3, reason: invalid class name */
    public final class AnonymousClass3 extends ExclusiveActivatable {
        public final /* synthetic */ MutableState $mutableState;
        public final /* synthetic */ Flow $source;
        public final /* synthetic */ String $traceName;
        public final /* synthetic */ Hydrator this$0;

        public AnonymousClass3(Flow flow, String str, MutableState<Object> mutableState, Hydrator hydrator) {
            this.$source = flow;
            this.$traceName = str;
            this.$mutableState = mutableState;
            this.this$0 = hydrator;
        }

        /* JADX WARN: Code restructure failed: missing block: B:20:0x0055, code lost:
        
            if (kotlinx.coroutines.DelayKt.awaitCancellation(r0) == r1) goto L21;
         */
        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
        @Override // com.android.systemui.lifecycle.ExclusiveActivatable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object onActivated(Continuation continuation) {
            Hydrator$hydratedStateOf$3$onActivated$1 hydrator$hydratedStateOf$3$onActivated$1;
            if (continuation instanceof Hydrator$hydratedStateOf$3$onActivated$1) {
                hydrator$hydratedStateOf$3$onActivated$1 = (Hydrator$hydratedStateOf$3$onActivated$1) continuation;
                int i = hydrator$hydratedStateOf$3$onActivated$1.label;
                if ((i & Integer.MIN_VALUE) != 0) {
                    hydrator$hydratedStateOf$3$onActivated$1.label = i - Integer.MIN_VALUE;
                } else {
                    hydrator$hydratedStateOf$3$onActivated$1 = new Hydrator$hydratedStateOf$3$onActivated$1(this, continuation);
                }
            }
            Object obj = hydrator$hydratedStateOf$3$onActivated$1.result;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i2 = hydrator$hydratedStateOf$3$onActivated$1.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                final Hydrator hydrator = this.this$0;
                final String str = this.$traceName;
                final MutableState mutableState = this.$mutableState;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.lifecycle.Hydrator$hydratedStateOf$3$onActivated$2
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation2) {
                        Hydrator hydrator2;
                        TableLogBuffer tableLogBuffer;
                        String str2 = str;
                        if (str2 != null && (tableLogBuffer = (hydrator2 = hydrator).tableLogBuffer) != null) {
                            TableLogBufferBase.DefaultImpls.logChange(tableLogBuffer, hydrator2.traceName, str2, obj2 != null ? obj2.toString() : null);
                        }
                        mutableState.setValue(obj2);
                        return Unit.INSTANCE;
                    }
                };
                hydrator$hydratedStateOf$3$onActivated$1.label = 1;
                if (this.$source.collect(flowCollector, hydrator$hydratedStateOf$3$onActivated$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            if (i2 != 1) {
                if (i2 != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                throw new KotlinNothingValueException();
            }
            ResultKt.throwOnFailure(obj);
            hydrator$hydratedStateOf$3$onActivated$1.label = 2;
        }
    }

    /* renamed from: com.android.systemui.lifecycle.Hydrator$onActivated$1, reason: invalid class name */
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
            return Hydrator.this.onActivated(this);
        }
    }

    /* renamed from: com.android.systemui.lifecycle.Hydrator$onActivated$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = Hydrator.this.new AnonymousClass2(continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                Hydrator hydrator = Hydrator.this;
                String str = hydrator.traceName;
                ArrayList arrayList = (ArrayList) hydrator.children;
                int size = arrayList.size();
                int i2 = 0;
                while (i2 < size) {
                    Object obj2 = arrayList.get(i2);
                    i2++;
                    NamedActivatable namedActivatable = (NamedActivatable) obj2;
                    if (namedActivatable.traceName != null) {
                        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new Hydrator$onActivated$2$1$1$1(namedActivatable, null), 6);
                    } else {
                        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new Hydrator$onActivated$2$1$1$2(namedActivatable, null), 7);
                    }
                }
                this.label = 1;
                if (DelayKt.awaitCancellation(this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }
    }

    public /* synthetic */ Hydrator(String str, TableLogBuffer tableLogBuffer, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? null : tableLogBuffer);
    }

    public final State hydratedStateOf(StateFlow stateFlow, String str) {
        return hydratedStateOf(str, stateFlow.getValue(), stateFlow);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object onActivated(Continuation continuation) {
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
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(null);
            anonymousClass1.label = 1;
            if (CoroutineScopeKt.coroutineScope(anonymousClass2, anonymousClass1) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }

    public Hydrator(String str, TableLogBuffer tableLogBuffer) {
        this.traceName = str;
        this.tableLogBuffer = tableLogBuffer;
        this.children = new ArrayList();
    }

    public final State hydratedStateOf(String str, Object obj, Flow flow) {
        if (isActive()) {
            throw new IllegalStateException("Cannot call hydratedStateOf after Hydrator is already active.");
        }
        MutableState mutableStateMutableStateOf$default = SnapshotStateKt.mutableStateOf$default(obj);
        TableLogBuffer tableLogBuffer = this.tableLogBuffer;
        if (tableLogBuffer != null) {
            tableLogBuffer.logChange(this.traceName, str, obj != null ? obj.toString() : null, true);
        }
        ((ArrayList) this.children).add(new NamedActivatable(str, new AnonymousClass3(flow, str, mutableStateMutableStateOf$default, this)));
        return mutableStateMutableStateOf$default;
    }
}
