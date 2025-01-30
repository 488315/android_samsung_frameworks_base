package androidx.lifecycle;

import androidx.lifecycle.Lifecycle;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.MainCoroutineDispatcher;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.scheduling.DefaultScheduler;
import kotlinx.coroutines.sync.Mutex;
import kotlinx.coroutines.sync.MutexImpl;
import kotlinx.coroutines.sync.MutexKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "androidx.lifecycle.RepeatOnLifecycleKt$repeatOnLifecycle$3", m277f = "RepeatOnLifecycle.kt", m278l = {84}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
final class RepeatOnLifecycleKt$repeatOnLifecycle$3 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function2 $block;
    final /* synthetic */ Lifecycle.State $state;
    final /* synthetic */ Lifecycle $this_repeatOnLifecycle;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @DebugMetadata(m276c = "androidx.lifecycle.RepeatOnLifecycleKt$repeatOnLifecycle$3$1", m277f = "RepeatOnLifecycle.kt", m278l = {166}, m279m = "invokeSuspend")
    /* renamed from: androidx.lifecycle.RepeatOnLifecycleKt$repeatOnLifecycle$3$1 */
    final class C03061 extends SuspendLambda implements Function2 {
        final /* synthetic */ CoroutineScope $$this$coroutineScope;
        final /* synthetic */ Function2 $block;
        final /* synthetic */ Lifecycle.State $state;
        final /* synthetic */ Lifecycle $this_repeatOnLifecycle;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C03061(Lifecycle lifecycle, Lifecycle.State state, CoroutineScope coroutineScope, Function2 function2, Continuation<? super C03061> continuation) {
            super(2, continuation);
            this.$this_repeatOnLifecycle = lifecycle;
            this.$state = state;
            this.$$this$coroutineScope = coroutineScope;
            this.$block = function2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new C03061(this.$this_repeatOnLifecycle, this.$state, this.$$this$coroutineScope, this.$block, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C03061) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:20:0x00e5  */
        /* JADX WARN: Removed duplicated region for block: B:23:0x00ee  */
        /* JADX WARN: Removed duplicated region for block: B:45:0x00c2 A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:46:0x00c3  */
        /* JADX WARN: Type inference failed for: r15v0, types: [T, androidx.lifecycle.LifecycleObserver, androidx.lifecycle.RepeatOnLifecycleKt$repeatOnLifecycle$3$1$1$1] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            Ref$ObjectRef ref$ObjectRef;
            Ref$ObjectRef ref$ObjectRef2;
            Lifecycle.Event event;
            Lifecycle.Event event2;
            Job job;
            LifecycleEventObserver lifecycleEventObserver;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                if (this.$this_repeatOnLifecycle.getCurrentState() == Lifecycle.State.DESTROYED) {
                    return Unit.INSTANCE;
                }
                final Ref$ObjectRef ref$ObjectRef3 = new Ref$ObjectRef();
                Ref$ObjectRef ref$ObjectRef4 = new Ref$ObjectRef();
                try {
                    Lifecycle.State state = this.$state;
                    Lifecycle lifecycle = this.$this_repeatOnLifecycle;
                    final CoroutineScope coroutineScope = this.$$this$coroutineScope;
                    final Function2 function2 = this.$block;
                    this.L$0 = ref$ObjectRef3;
                    this.L$1 = ref$ObjectRef4;
                    this.L$2 = state;
                    this.L$3 = lifecycle;
                    this.L$4 = coroutineScope;
                    this.L$5 = function2;
                    this.label = 1;
                    final CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(this), 1);
                    cancellableContinuationImpl.initCancellability();
                    Lifecycle.Event event3 = Lifecycle.Event.ON_CREATE;
                    int[] iArr = Lifecycle.AbstractC03001.$SwitchMap$androidx$lifecycle$Lifecycle$State;
                    int i2 = iArr[state.ordinal()];
                    Lifecycle.Event event4 = i2 != 1 ? i2 != 2 ? i2 != 3 ? null : Lifecycle.Event.ON_RESUME : Lifecycle.Event.ON_START : Lifecycle.Event.ON_CREATE;
                    int i3 = iArr[state.ordinal()];
                    if (i3 == 1) {
                        event = Lifecycle.Event.ON_DESTROY;
                    } else if (i3 == 2) {
                        event = Lifecycle.Event.ON_STOP;
                    } else if (i3 != 3) {
                        event2 = null;
                        Symbol symbol = MutexKt.UNLOCK_FAIL;
                        final MutexImpl mutexImpl = new MutexImpl(false);
                        final Lifecycle.Event event5 = event4;
                        final Lifecycle.Event event6 = event2;
                        ?? r15 = new LifecycleEventObserver() { // from class: androidx.lifecycle.RepeatOnLifecycleKt$repeatOnLifecycle$3$1$1$1

                            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                            @DebugMetadata(m276c = "androidx.lifecycle.RepeatOnLifecycleKt$repeatOnLifecycle$3$1$1$1$1", m277f = "RepeatOnLifecycle.kt", m278l = {171, 110}, m279m = "invokeSuspend")
                            /* renamed from: androidx.lifecycle.RepeatOnLifecycleKt$repeatOnLifecycle$3$1$1$1$1 */
                            final class C03071 extends SuspendLambda implements Function2 {
                                final /* synthetic */ Function2 $block;
                                final /* synthetic */ Mutex $mutex;
                                Object L$0;
                                Object L$1;
                                int label;

                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                public C03071(Mutex mutex, Function2 function2, Continuation<? super C03071> continuation) {
                                    super(2, continuation);
                                    this.$mutex = mutex;
                                    this.$block = function2;
                                }

                                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                public final Continuation create(Object obj, Continuation continuation) {
                                    return new C03071(this.$mutex, this.$block, continuation);
                                }

                                @Override // kotlin.jvm.functions.Function2
                                public final Object invoke(Object obj, Object obj2) {
                                    return ((C03071) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                                }

                                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                public final Object invokeSuspend(Object obj) {
                                    Function2 function2;
                                    Object obj2;
                                    Throwable th;
                                    Object obj3;
                                    Object obj4;
                                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                    int i = this.label;
                                    try {
                                        if (i == 0) {
                                            ResultKt.throwOnFailure(obj);
                                            Mutex mutex = this.$mutex;
                                            function2 = this.$block;
                                            this.L$0 = mutex;
                                            this.L$1 = function2;
                                            this.label = 1;
                                            MutexImpl mutexImpl = (MutexImpl) mutex;
                                            Object lock = mutexImpl.lock(this);
                                            obj2 = mutexImpl;
                                            if (lock == coroutineSingletons) {
                                                return coroutineSingletons;
                                            }
                                        } else {
                                            if (i != 1) {
                                                if (i != 2) {
                                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                                }
                                                obj3 = (Mutex) this.L$0;
                                                try {
                                                    ResultKt.throwOnFailure(obj);
                                                    Unit unit = Unit.INSTANCE;
                                                    ((MutexImpl) obj3).unlock(null);
                                                    return Unit.INSTANCE;
                                                } catch (Throwable th2) {
                                                    th = th2;
                                                    obj4 = obj3;
                                                    ((MutexImpl) obj4).unlock(null);
                                                    throw th;
                                                }
                                            }
                                            function2 = (Function2) this.L$1;
                                            Object obj5 = (Mutex) this.L$0;
                                            ResultKt.throwOnFailure(obj);
                                            obj2 = obj5;
                                        }
                                        RepeatOnLifecycleKt$repeatOnLifecycle$3$1$1$1$1$1$1 repeatOnLifecycleKt$repeatOnLifecycle$3$1$1$1$1$1$1 = new RepeatOnLifecycleKt$repeatOnLifecycle$3$1$1$1$1$1$1(function2, null);
                                        this.L$0 = obj2;
                                        this.L$1 = null;
                                        this.label = 2;
                                        if (CoroutineScopeKt.coroutineScope(repeatOnLifecycleKt$repeatOnLifecycle$3$1$1$1$1$1$1, this) == coroutineSingletons) {
                                            return coroutineSingletons;
                                        }
                                        obj3 = obj2;
                                        Unit unit2 = Unit.INSTANCE;
                                        ((MutexImpl) obj3).unlock(null);
                                        return Unit.INSTANCE;
                                    } catch (Throwable th3) {
                                        th = th3;
                                        obj4 = obj2;
                                        ((MutexImpl) obj4).unlock(null);
                                        throw th;
                                    }
                                }
                            }

                            /* JADX WARN: Type inference failed for: r3v4, types: [T, kotlinx.coroutines.StandaloneCoroutine] */
                            @Override // androidx.lifecycle.LifecycleEventObserver
                            public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event7) {
                                Lifecycle.Event event8 = Lifecycle.Event.this;
                                Ref$ObjectRef ref$ObjectRef5 = ref$ObjectRef3;
                                if (event7 == event8) {
                                    ref$ObjectRef5.element = BuildersKt.launch$default(coroutineScope, null, null, new C03071(mutexImpl, function2, null), 3);
                                    return;
                                }
                                if (event7 == event6) {
                                    Job job2 = (Job) ref$ObjectRef5.element;
                                    if (job2 != null) {
                                        job2.cancel(null);
                                    }
                                    ref$ObjectRef5.element = null;
                                }
                                if (event7 == Lifecycle.Event.ON_DESTROY) {
                                    int i4 = Result.$r8$clinit;
                                    ((CancellableContinuationImpl) cancellableContinuationImpl).resumeWith(Unit.INSTANCE);
                                }
                            }
                        };
                        ref$ObjectRef4.element = r15;
                        lifecycle.addObserver(r15);
                        if (cancellableContinuationImpl.getResult() != coroutineSingletons) {
                            return coroutineSingletons;
                        }
                        ref$ObjectRef = ref$ObjectRef3;
                        ref$ObjectRef2 = ref$ObjectRef4;
                    } else {
                        event = Lifecycle.Event.ON_PAUSE;
                    }
                    event2 = event;
                    Symbol symbol2 = MutexKt.UNLOCK_FAIL;
                    final Mutex mutexImpl2 = new MutexImpl(false);
                    final Lifecycle.Event event52 = event4;
                    final Lifecycle.Event event62 = event2;
                    ?? r152 = new LifecycleEventObserver() { // from class: androidx.lifecycle.RepeatOnLifecycleKt$repeatOnLifecycle$3$1$1$1

                        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                        @DebugMetadata(m276c = "androidx.lifecycle.RepeatOnLifecycleKt$repeatOnLifecycle$3$1$1$1$1", m277f = "RepeatOnLifecycle.kt", m278l = {171, 110}, m279m = "invokeSuspend")
                        /* renamed from: androidx.lifecycle.RepeatOnLifecycleKt$repeatOnLifecycle$3$1$1$1$1 */
                        final class C03071 extends SuspendLambda implements Function2 {
                            final /* synthetic */ Function2 $block;
                            final /* synthetic */ Mutex $mutex;
                            Object L$0;
                            Object L$1;
                            int label;

                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            public C03071(Mutex mutex, Function2 function2, Continuation<? super C03071> continuation) {
                                super(2, continuation);
                                this.$mutex = mutex;
                                this.$block = function2;
                            }

                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                            public final Continuation create(Object obj, Continuation continuation) {
                                return new C03071(this.$mutex, this.$block, continuation);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                return ((C03071) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                            }

                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                            public final Object invokeSuspend(Object obj) {
                                Function2 function2;
                                Object obj2;
                                Throwable th;
                                Object obj3;
                                Object obj4;
                                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                int i = this.label;
                                try {
                                    if (i == 0) {
                                        ResultKt.throwOnFailure(obj);
                                        Mutex mutex = this.$mutex;
                                        function2 = this.$block;
                                        this.L$0 = mutex;
                                        this.L$1 = function2;
                                        this.label = 1;
                                        MutexImpl mutexImpl = (MutexImpl) mutex;
                                        Object lock = mutexImpl.lock(this);
                                        obj2 = mutexImpl;
                                        if (lock == coroutineSingletons) {
                                            return coroutineSingletons;
                                        }
                                    } else {
                                        if (i != 1) {
                                            if (i != 2) {
                                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                            }
                                            obj3 = (Mutex) this.L$0;
                                            try {
                                                ResultKt.throwOnFailure(obj);
                                                Unit unit2 = Unit.INSTANCE;
                                                ((MutexImpl) obj3).unlock(null);
                                                return Unit.INSTANCE;
                                            } catch (Throwable th2) {
                                                th = th2;
                                                obj4 = obj3;
                                                ((MutexImpl) obj4).unlock(null);
                                                throw th;
                                            }
                                        }
                                        function2 = (Function2) this.L$1;
                                        Object obj5 = (Mutex) this.L$0;
                                        ResultKt.throwOnFailure(obj);
                                        obj2 = obj5;
                                    }
                                    RepeatOnLifecycleKt$repeatOnLifecycle$3$1$1$1$1$1$1 repeatOnLifecycleKt$repeatOnLifecycle$3$1$1$1$1$1$1 = new RepeatOnLifecycleKt$repeatOnLifecycle$3$1$1$1$1$1$1(function2, null);
                                    this.L$0 = obj2;
                                    this.L$1 = null;
                                    this.label = 2;
                                    if (CoroutineScopeKt.coroutineScope(repeatOnLifecycleKt$repeatOnLifecycle$3$1$1$1$1$1$1, this) == coroutineSingletons) {
                                        return coroutineSingletons;
                                    }
                                    obj3 = obj2;
                                    Unit unit22 = Unit.INSTANCE;
                                    ((MutexImpl) obj3).unlock(null);
                                    return Unit.INSTANCE;
                                } catch (Throwable th3) {
                                    th = th3;
                                    obj4 = obj2;
                                    ((MutexImpl) obj4).unlock(null);
                                    throw th;
                                }
                            }
                        }

                        /* JADX WARN: Type inference failed for: r3v4, types: [T, kotlinx.coroutines.StandaloneCoroutine] */
                        @Override // androidx.lifecycle.LifecycleEventObserver
                        public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event7) {
                            Lifecycle.Event event8 = Lifecycle.Event.this;
                            Ref$ObjectRef ref$ObjectRef5 = ref$ObjectRef3;
                            if (event7 == event8) {
                                ref$ObjectRef5.element = BuildersKt.launch$default(coroutineScope, null, null, new C03071(mutexImpl2, function2, null), 3);
                                return;
                            }
                            if (event7 == event62) {
                                Job job2 = (Job) ref$ObjectRef5.element;
                                if (job2 != null) {
                                    job2.cancel(null);
                                }
                                ref$ObjectRef5.element = null;
                            }
                            if (event7 == Lifecycle.Event.ON_DESTROY) {
                                int i4 = Result.$r8$clinit;
                                ((CancellableContinuationImpl) cancellableContinuationImpl).resumeWith(Unit.INSTANCE);
                            }
                        }
                    };
                    ref$ObjectRef4.element = r152;
                    lifecycle.addObserver(r152);
                    if (cancellableContinuationImpl.getResult() != coroutineSingletons) {
                    }
                } catch (Throwable th) {
                    th = th;
                    ref$ObjectRef = ref$ObjectRef3;
                    ref$ObjectRef2 = ref$ObjectRef4;
                    job = (Job) ref$ObjectRef.element;
                    if (job != null) {
                    }
                    lifecycleEventObserver = (LifecycleEventObserver) ref$ObjectRef2.element;
                    if (lifecycleEventObserver != null) {
                    }
                    throw th;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ref$ObjectRef2 = (Ref$ObjectRef) this.L$1;
                ref$ObjectRef = (Ref$ObjectRef) this.L$0;
                try {
                    ResultKt.throwOnFailure(obj);
                } catch (Throwable th2) {
                    th = th2;
                    job = (Job) ref$ObjectRef.element;
                    if (job != null) {
                        job.cancel(null);
                    }
                    lifecycleEventObserver = (LifecycleEventObserver) ref$ObjectRef2.element;
                    if (lifecycleEventObserver != null) {
                        this.$this_repeatOnLifecycle.removeObserver(lifecycleEventObserver);
                    }
                    throw th;
                }
            }
            Job job2 = (Job) ref$ObjectRef.element;
            if (job2 != null) {
                job2.cancel(null);
            }
            LifecycleEventObserver lifecycleEventObserver2 = (LifecycleEventObserver) ref$ObjectRef2.element;
            if (lifecycleEventObserver2 != null) {
                this.$this_repeatOnLifecycle.removeObserver(lifecycleEventObserver2);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RepeatOnLifecycleKt$repeatOnLifecycle$3(Lifecycle lifecycle, Lifecycle.State state, Function2 function2, Continuation<? super RepeatOnLifecycleKt$repeatOnLifecycle$3> continuation) {
        super(2, continuation);
        this.$this_repeatOnLifecycle = lifecycle;
        this.$state = state;
        this.$block = function2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        RepeatOnLifecycleKt$repeatOnLifecycle$3 repeatOnLifecycleKt$repeatOnLifecycle$3 = new RepeatOnLifecycleKt$repeatOnLifecycle$3(this.$this_repeatOnLifecycle, this.$state, this.$block, continuation);
        repeatOnLifecycleKt$repeatOnLifecycle$3.L$0 = obj;
        return repeatOnLifecycleKt$repeatOnLifecycle$3;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((RepeatOnLifecycleKt$repeatOnLifecycle$3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            DefaultScheduler defaultScheduler = Dispatchers.Default;
            MainCoroutineDispatcher immediate = MainDispatcherLoader.dispatcher.getImmediate();
            C03061 c03061 = new C03061(this.$this_repeatOnLifecycle, this.$state, coroutineScope, this.$block, null);
            this.label = 1;
            if (BuildersKt.withContext(immediate, c03061, this) == coroutineSingletons) {
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
