package com.android.systemui.display.data.repository;

import android.util.Log;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.CoreStartable;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes2.dex */
public abstract class PerDisplayStoreImpl implements PerDisplayStore, CoreStartable {
    public final CoroutineScope backgroundApplicationScope;
    public final DisplayRepository displayRepository;
    public final ConcurrentHashMap perDisplayInstances = new ConcurrentHashMap();

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.display.data.repository.PerDisplayStoreImpl$start$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return PerDisplayStoreImpl.this.new AnonymousClass1(continuation);
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
                Flow displayRemovalEvent = ((DisplayRepositoryImpl) PerDisplayStoreImpl.this.displayRepository).displayRepositoryFromLib.getDisplayRemovalEvent();
                final PerDisplayStoreImpl perDisplayStoreImpl = PerDisplayStoreImpl.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.display.data.repository.PerDisplayStoreImpl.start.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        Object objOnDisplayRemovalAction;
                        int iIntValue = ((Number) obj2).intValue();
                        PerDisplayStoreImpl perDisplayStoreImpl2 = perDisplayStoreImpl;
                        Object objRemove = perDisplayStoreImpl2.perDisplayInstances.remove(new Integer(iIntValue));
                        return (objRemove == null || (objOnDisplayRemovalAction = perDisplayStoreImpl2.onDisplayRemovalAction(objRemove)) != CoroutineSingletons.COROUTINE_SUSPENDED) ? Unit.INSTANCE : objOnDisplayRemovalAction;
                    }
                };
                this.label = 1;
                if (displayRemovalEvent.collect(flowCollector, this) == coroutineSingletons) {
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

    static {
        new Companion(null);
    }

    public PerDisplayStoreImpl(CoroutineScope coroutineScope, DisplayRepository displayRepository) {
        this.backgroundApplicationScope = coroutineScope;
        this.displayRepository = displayRepository;
    }

    public abstract Object createInstanceForDisplay(int i);

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println(this.perDisplayInstances);
    }

    @Override // com.android.systemui.display.data.repository.PerDisplayStore
    public final Object forDisplay(int i) {
        if (((DisplayRepositoryImpl) this.displayRepository).displayRepositoryFromLib.getDisplay(i) == null) {
            Log.e("PerDisplayStore", ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m(i, "<", getInstanceClass().getSimpleName(), ">: Display with id ", " doesn't exist."));
            return null;
        }
        synchronized (this.perDisplayInstances) {
            try {
                Object obj = this.perDisplayInstances.get(Integer.valueOf(i));
                if (obj != null) {
                    return obj;
                }
                Object objCreateInstanceForDisplay = createInstanceForDisplay(i);
                if (objCreateInstanceForDisplay == null) {
                    Log.e("PerDisplayStore", "<" + getInstanceClass().getSimpleName() + "> returning null because createInstanceForDisplay(" + i + ") returned null.");
                } else {
                    this.perDisplayInstances.put(Integer.valueOf(i), objCreateInstanceForDisplay);
                }
                return objCreateInstanceForDisplay;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.systemui.display.data.repository.PerDisplayStore
    public final Object getDefaultDisplay() {
        Object objForDisplay = forDisplay(0);
        objForDisplay.getClass();
        return objForDisplay;
    }

    public abstract Class getInstanceClass();

    public Object onDisplayRemovalAction(Object obj) {
        return Unit.INSTANCE;
    }

    @Override // com.android.systemui.CoreStartable
    public void start() {
        getInstanceClass().getSimpleName();
        CoroutineTracingKt.launchTraced$default(this.backgroundApplicationScope, null, null, new AnonymousClass1(null), 6);
    }
}
