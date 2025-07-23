package com.android.systemui.navigationbar.gestural.domain;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.navigationbar.gestural.data.respository.GestureRepository;
import com.android.systemui.navigationbar.gestural.data.respository.GestureRepositoryImpl;
import com.android.systemui.navigationbar.gestural.domain.TaskMatcher;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.shared.system.TaskStackChangeListeners;
import com.android.systemui.util.kotlin.FlowKt$emitOnStart$1;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.collections.EmptySet;
import kotlin.coroutines.CoroutineContext;
import kotlin.enums.EnumEntriesKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class GestureInteractor {
    public final StateFlowImpl _localGestureBlockedMatchers;
    public final ActivityManagerWrapper activityManagerWrapper;
    public final CoroutineContext backgroundCoroutineContext;
    public final GestureRepository gestureRepository;
    public final CoroutineScope scope;
    public final TaskStackChangeListeners taskStackChangeListeners;
    public final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 topActivityBlocked;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Scope {
        public static final /* synthetic */ Scope[] $VALUES;
        public static final Scope Global;
        public static final Scope Local;

        static {
            Scope scope = new Scope("Local", 0);
            Local = scope;
            Scope scope2 = new Scope("Global", 1);
            Global = scope2;
            Scope[] scopeArr = {scope, scope2};
            $VALUES = scopeArr;
            EnumEntriesKt.enumEntries(scopeArr);
        }

        private Scope(String str, int i) {
        }

        public static Scope valueOf(String str) {
            return (Scope) Enum.valueOf(Scope.class, str);
        }

        public static Scope[] values() {
            return (Scope[]) $VALUES.clone();
        }
    }

    public GestureInteractor(GestureRepository gestureRepository, CoroutineDispatcher coroutineDispatcher, CoroutineContext coroutineContext, CoroutineScope coroutineScope, ActivityManagerWrapper activityManagerWrapper, TaskStackChangeListeners taskStackChangeListeners) {
        this.gestureRepository = gestureRepository;
        this.backgroundCoroutineContext = coroutineContext;
        this.scope = coroutineScope;
        this.activityManagerWrapper = activityManagerWrapper;
        this.taskStackChangeListeners = taskStackChangeListeners;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(EmptySet.INSTANCE);
        this._localGestureBlockedMatchers = MutableStateFlow;
        this.topActivityBlocked = FlowKt.combine(FlowKt.distinctUntilChanged(FlowKt.mapLatest(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new FlowKt$emitOnStart$1(null), FlowKt.flowOn(FlowConflatedKt.conflatedCallbackFlow(new GestureInteractor$_topActivity$1(this, null)), coroutineDispatcher)), new GestureInteractor$_topActivity$2(this, null))), ((GestureRepositoryImpl) gestureRepository)._gestureBlockedMatchers, FlowKt.asStateFlow(MutableStateFlow), new GestureInteractor$topActivityBlocked$1(null));
    }

    public final void addGestureBlockedMatcher(TaskMatcher taskMatcher, Scope scope) {
        CoroutineTracingKt.launchTraced$default(this.scope, null, null, new GestureInteractor$addGestureBlockedMatcher$1(scope, this, taskMatcher, null), 7);
    }

    public final void removeGestureBlockedMatcher(TaskMatcher.TopActivityType topActivityType, Scope scope) {
        CoroutineTracingKt.launchTraced$default(this.scope, null, null, new GestureInteractor$removeGestureBlockedMatcher$1(scope, this, topActivityType, null), 7);
    }
}
