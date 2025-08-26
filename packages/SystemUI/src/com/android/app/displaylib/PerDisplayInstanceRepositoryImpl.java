package com.android.app.displaylib;

import android.os.Trace;
import android.util.Log;
import android.view.Display;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import com.android.app.displaylib.PerDisplayRepository;
import com.android.app.tracing.TraceUtilsKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.display.data.repository.PerDisplayRepoDumpHelper;
import com.android.systemui.dump.DumpableFromToString;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* loaded from: classes.dex */
public final class PerDisplayInstanceRepositoryImpl implements PerDisplayRepository {
    public final ReadonlyStateFlow allowedDisplays;
    public final String debugName;
    public final DisplayRepository displayRepository;
    public final PerDisplayRepository.InitCallback initCallback;
    public final PerDisplayInstanceProvider instanceProvider;
    public final ConcurrentHashMap perDisplayInstances;

    /* renamed from: com.android.app.displaylib.PerDisplayInstanceRepositoryImpl$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return PerDisplayInstanceRepositoryImpl.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                PerDisplayInstanceRepositoryImpl perDisplayInstanceRepositoryImpl = PerDisplayInstanceRepositoryImpl.this;
                this.label = 1;
                PerDisplayRepoDumpHelper perDisplayRepoDumpHelper = (PerDisplayRepoDumpHelper) perDisplayInstanceRepositoryImpl.initCallback;
                perDisplayRepoDumpHelper.getClass();
                perDisplayRepoDumpHelper.dumpManager.registerNormalDumpable("PerDisplayRepository-" + perDisplayInstanceRepositoryImpl.debugName, new DumpableFromToString(perDisplayInstanceRepositoryImpl));
                Object objCollectLatest = FlowKt.collectLatest(perDisplayInstanceRepositoryImpl.allowedDisplays, new PerDisplayInstanceRepositoryImpl$start$2(perDisplayInstanceRepositoryImpl, null), this);
                if (objCollectLatest != obj2) {
                    objCollectLatest = Unit.INSTANCE;
                }
                if (objCollectLatest == obj2) {
                    return obj2;
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

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public interface Factory {
        PerDisplayInstanceRepositoryImpl create(String str, PerDisplayInstanceProvider perDisplayInstanceProvider, DisplayInstanceLifecycleManager displayInstanceLifecycleManager);
    }

    static {
        new Companion(null);
    }

    public PerDisplayInstanceRepositoryImpl(String str, PerDisplayInstanceProvider perDisplayInstanceProvider, DisplayInstanceLifecycleManager displayInstanceLifecycleManager, CoroutineScope coroutineScope, DisplayRepository displayRepository, PerDisplayRepository.InitCallback initCallback) {
        this.debugName = str;
        this.instanceProvider = perDisplayInstanceProvider;
        this.displayRepository = displayRepository;
        this.initCallback = initCallback;
        this.perDisplayInstances = new ConcurrentHashMap();
        this.allowedDisplays = FlowKt.stateIn(displayInstanceLifecycleManager == null ? displayRepository.getDisplayIds() : new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(displayInstanceLifecycleManager.getDisplayIds(), displayRepository.getDisplayIds(), new PerDisplayInstanceRepositoryImpl$allowedDisplays$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), Collections.singleton(0));
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass1(null), 6);
    }

    @Override // com.android.app.displaylib.PerDisplayRepository
    public final Object get(int i) {
        Display display = this.displayRepository.getDisplay(i);
        String str = this.debugName;
        if (display == null) {
            Log.e("PerDisplayInstanceRepo", ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m(i, "<", str, ": Display with id ", " doesn't exist."));
            return null;
        }
        if (!((Set) this.allowedDisplays.$$delegate_0.getValue()).contains(Integer.valueOf(i))) {
            Log.e("PerDisplayInstanceRepo", ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m(i, "<", str, ": Display with id ", " exists but it's not allowed by lifecycle manager."));
            return null;
        }
        ConcurrentHashMap concurrentHashMap = this.perDisplayInstances;
        Integer numValueOf = Integer.valueOf(i);
        final Function1 function1 = new Function1() { // from class: com.android.app.displaylib.PerDisplayInstanceRepositoryImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                Integer num = (Integer) obj;
                PerDisplayInstanceRepositoryImpl perDisplayInstanceRepositoryImpl = this.f$0;
                Log.d("PerDisplayInstanceRepo", "<" + perDisplayInstanceRepositoryImpl.debugName + "> creating instance for displayId=" + num + ", as it wasn't available.");
                boolean zIsEnabled = Trace.isEnabled();
                String str2 = perDisplayInstanceRepositoryImpl.debugName;
                if (zIsEnabled) {
                    TraceUtilsKt.beginSlice("creating instance of " + str2 + " for displayId=" + num);
                }
                try {
                    Object objCreateInstance = perDisplayInstanceRepositoryImpl.instanceProvider.createInstance(num.intValue());
                    if (objCreateInstance == null) {
                        Log.e("PerDisplayInstanceRepo", "<" + str2 + "> returning null because createInstance(" + num + ") returned null.");
                    }
                    return objCreateInstance;
                } finally {
                    if (zIsEnabled) {
                        TraceUtilsKt.endSlice();
                    }
                }
            }
        };
        return concurrentHashMap.computeIfAbsent(numValueOf, new Function() { // from class: com.android.app.displaylib.PerDisplayInstanceRepositoryImpl$sam$java_util_function_Function$0
            @Override // java.util.function.Function
            public final /* synthetic */ Object apply(Object obj) {
                return function1.mo781invoke(obj);
            }
        });
    }

    public final String toString() {
        return "PerDisplayInstanceRepositoryImpl(debugName='" + this.debugName + "', instances=" + this.perDisplayInstances + ")";
    }

    public /* synthetic */ PerDisplayInstanceRepositoryImpl(String str, PerDisplayInstanceProvider perDisplayInstanceProvider, DisplayInstanceLifecycleManager displayInstanceLifecycleManager, CoroutineScope coroutineScope, DisplayRepository displayRepository, PerDisplayRepository.InitCallback initCallback, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, perDisplayInstanceProvider, (i & 4) != 0 ? null : displayInstanceLifecycleManager, coroutineScope, displayRepository, initCallback);
    }
}
