package com.android.systemui.navigationbar.gestural.domain;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.navigationbar.gestural.data.respository.GestureRepository;
import com.android.systemui.navigationbar.gestural.data.respository.GestureRepositoryImpl;
import com.android.systemui.navigationbar.gestural.domain.TaskMatcher;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.shared.system.TaskStackChangeListeners;
import com.android.systemui.util.kotlin.FlowKt;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import java.util.Set;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptySet;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

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

    /* renamed from: com.android.systemui.navigationbar.gestural.domain.GestureInteractor$addGestureBlockedMatcher$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ Scope $gestureScope;
        final /* synthetic */ TaskMatcher $matcher;
        int label;
        final /* synthetic */ GestureInteractor this$0;

        /* renamed from: com.android.systemui.navigationbar.gestural.domain.GestureInteractor$addGestureBlockedMatcher$1$WhenMappings */
        public abstract /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[Scope.values().length];
                try {
                    iArr[Scope.Local.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[Scope.Global.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Scope scope, GestureInteractor gestureInteractor, TaskMatcher taskMatcher, Continuation continuation) {
            super(2, continuation);
            this.$gestureScope = scope;
            this.this$0 = gestureInteractor;
            this.$matcher = taskMatcher;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$gestureScope, this.this$0, this.$matcher, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:13:0x0037, code lost:
        
            if (((com.android.systemui.navigationbar.gestural.data.respository.GestureRepositoryImpl) r5).addGestureBlockedMatcher(r1, r4) == r0) goto L19;
         */
        /* JADX WARN: Code restructure failed: missing block: B:18:0x005b, code lost:
        
            if (kotlin.Unit.INSTANCE == r0) goto L19;
         */
        /* JADX WARN: Code restructure failed: missing block: B:19:0x005d, code lost:
        
            return r0;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                int i2 = WhenMappings.$EnumSwitchMapping$0[this.$gestureScope.ordinal()];
                if (i2 == 1) {
                    StateFlowImpl stateFlowImpl = this.this$0._localGestureBlockedMatchers;
                    Set mutableSet = CollectionsKt___CollectionsKt.toMutableSet((Iterable) stateFlowImpl.getValue());
                    mutableSet.add(this.$matcher);
                    this.label = 1;
                    stateFlowImpl.updateState(null, mutableSet);
                } else {
                    if (i2 != 2) {
                        throw new NoWhenBranchMatchedException();
                    }
                    GestureRepository gestureRepository = this.this$0.gestureRepository;
                    TaskMatcher taskMatcher = this.$matcher;
                    this.label = 2;
                }
            } else {
                if (i != 1 && i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.navigationbar.gestural.domain.GestureInteractor$removeGestureBlockedMatcher$1, reason: invalid class name and case insensitive filesystem */
    final class C09691 extends SuspendLambda implements Function2 {
        final /* synthetic */ Scope $gestureScope;
        final /* synthetic */ TaskMatcher $matcher;
        int label;
        final /* synthetic */ GestureInteractor this$0;

        /* renamed from: com.android.systemui.navigationbar.gestural.domain.GestureInteractor$removeGestureBlockedMatcher$1$WhenMappings */
        public abstract /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[Scope.values().length];
                try {
                    iArr[Scope.Local.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[Scope.Global.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09691(Scope scope, GestureInteractor gestureInteractor, TaskMatcher taskMatcher, Continuation continuation) {
            super(2, continuation);
            this.$gestureScope = scope;
            this.this$0 = gestureInteractor;
            this.$matcher = taskMatcher;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new C09691(this.$gestureScope, this.this$0, this.$matcher, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C09691) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:13:0x0037, code lost:
        
            if (((com.android.systemui.navigationbar.gestural.data.respository.GestureRepositoryImpl) r5).removeGestureBlockedMatcher(r1, r4) == r0) goto L19;
         */
        /* JADX WARN: Code restructure failed: missing block: B:18:0x005b, code lost:
        
            if (kotlin.Unit.INSTANCE == r0) goto L19;
         */
        /* JADX WARN: Code restructure failed: missing block: B:19:0x005d, code lost:
        
            return r0;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                int i2 = WhenMappings.$EnumSwitchMapping$0[this.$gestureScope.ordinal()];
                if (i2 == 1) {
                    StateFlowImpl stateFlowImpl = this.this$0._localGestureBlockedMatchers;
                    Set mutableSet = CollectionsKt___CollectionsKt.toMutableSet((Iterable) stateFlowImpl.getValue());
                    mutableSet.remove(this.$matcher);
                    this.label = 1;
                    stateFlowImpl.updateState(null, mutableSet);
                } else {
                    if (i2 != 2) {
                        throw new NoWhenBranchMatchedException();
                    }
                    GestureRepository gestureRepository = this.this$0.gestureRepository;
                    TaskMatcher taskMatcher = this.$matcher;
                    this.label = 2;
                }
            } else {
                if (i != 1 && i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    public GestureInteractor(GestureRepository gestureRepository, CoroutineDispatcher coroutineDispatcher, CoroutineContext coroutineContext, CoroutineScope coroutineScope, ActivityManagerWrapper activityManagerWrapper, TaskStackChangeListeners taskStackChangeListeners) {
        this.gestureRepository = gestureRepository;
        this.backgroundCoroutineContext = coroutineContext;
        this.scope = coroutineScope;
        this.activityManagerWrapper = activityManagerWrapper;
        this.taskStackChangeListeners = taskStackChangeListeners;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(EmptySet.INSTANCE);
        this._localGestureBlockedMatchers = stateFlowImplMutableStateFlow;
        this.topActivityBlocked = FlowKt.combine(FlowKt.distinctUntilChanged(FlowKt.mapLatest(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new FlowKt.AnonymousClass1(null), kotlinx.coroutines.flow.FlowKt.flowOn(FlowConflatedKt.conflatedCallbackFlow(new GestureInteractor$_topActivity$1(this, null)), coroutineDispatcher)), new GestureInteractor$_topActivity$2(this, null))), ((GestureRepositoryImpl) gestureRepository)._gestureBlockedMatchers, kotlinx.coroutines.flow.FlowKt.asStateFlow(stateFlowImplMutableStateFlow), new GestureInteractor$topActivityBlocked$1(null));
    }

    public final void addGestureBlockedMatcher(TaskMatcher taskMatcher, Scope scope) {
        CoroutineTracingKt.launchTraced$default(this.scope, null, null, new AnonymousClass1(scope, this, taskMatcher, null), 7);
    }

    public final void removeGestureBlockedMatcher(TaskMatcher.TopActivityType topActivityType, Scope scope) {
        CoroutineTracingKt.launchTraced$default(this.scope, null, null, new C09691(scope, this, topActivityType, null), 7);
    }
}
