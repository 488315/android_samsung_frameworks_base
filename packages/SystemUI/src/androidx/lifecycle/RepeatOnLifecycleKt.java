package androidx.lifecycle;

import androidx.lifecycle.Lifecycle;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.android.HandlerContext;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;
import kotlinx.coroutines.sync.Mutex;
import kotlinx.coroutines.sync.MutexImpl;
import kotlinx.coroutines.sync.MutexKt;

/* loaded from: classes.dex */
public abstract class RepeatOnLifecycleKt {

    /* renamed from: androidx.lifecycle.RepeatOnLifecycleKt$repeatOnLifecycle$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        final /* synthetic */ Function2 $block;
        final /* synthetic */ Lifecycle.State $state;
        final /* synthetic */ Lifecycle $this_repeatOnLifecycle;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: androidx.lifecycle.RepeatOnLifecycleKt$repeatOnLifecycle$3$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
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
            public AnonymousClass1(Lifecycle lifecycle, Lifecycle.State state, CoroutineScope coroutineScope, Function2 function2, Continuation continuation) {
                super(2, continuation);
                this.$this_repeatOnLifecycle = lifecycle;
                this.$state = state;
                this.$$this$coroutineScope = coroutineScope;
                this.$block = function2;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.$this_repeatOnLifecycle, this.$state, this.$$this$coroutineScope, this.$block, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Removed duplicated region for block: B:36:0x00bc A[RETURN] */
            /* JADX WARN: Removed duplicated region for block: B:37:0x00bd  */
            /* JADX WARN: Removed duplicated region for block: B:40:0x00c4  */
            /* JADX WARN: Removed duplicated region for block: B:43:0x00cd  */
            /* JADX WARN: Removed duplicated region for block: B:50:0x00de  */
            /* JADX WARN: Removed duplicated region for block: B:53:0x00e7  */
            /* JADX WARN: Removed duplicated region for block: B:59:? A[SYNTHETIC] */
            /* JADX WARN: Type inference failed for: r4v3, types: [T, androidx.lifecycle.LifecycleObserver, androidx.lifecycle.RepeatOnLifecycleKt$repeatOnLifecycle$3$1$1$1] */
            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object invokeSuspend(Object obj) throws Throwable {
                Ref$ObjectRef ref$ObjectRef;
                Throwable th;
                Ref$ObjectRef ref$ObjectRef2;
                Lifecycle.Event event;
                final Lifecycle.Event event2;
                Job job;
                LifecycleEventObserver lifecycleEventObserver;
                Job job2;
                LifecycleEventObserver lifecycleEventObserver2;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    if (this.$this_repeatOnLifecycle.getCurrentState() == Lifecycle.State.DESTROYED) {
                        return Unit.INSTANCE;
                    }
                    final Ref$ObjectRef ref$ObjectRef3 = new Ref$ObjectRef();
                    ref$ObjectRef = new Ref$ObjectRef();
                    try {
                        Lifecycle.State state = this.$state;
                        Lifecycle lifecycle = this.$this_repeatOnLifecycle;
                        final CoroutineScope coroutineScope = this.$$this$coroutineScope;
                        final Function2 function2 = this.$block;
                        this.L$0 = ref$ObjectRef3;
                        this.L$1 = ref$ObjectRef;
                        this.L$2 = state;
                        this.L$3 = lifecycle;
                        this.L$4 = coroutineScope;
                        this.L$5 = function2;
                        this.label = 1;
                        final CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(this), 1);
                        cancellableContinuationImpl.initCancellability();
                        Lifecycle.Event.Companion.getClass();
                        int[] iArr = Lifecycle.Event.Companion.WhenMappings.$EnumSwitchMapping$0;
                        int i2 = iArr[state.ordinal()];
                        final Lifecycle.Event event3 = i2 != 1 ? i2 != 2 ? i2 != 3 ? null : Lifecycle.Event.ON_RESUME : Lifecycle.Event.ON_START : Lifecycle.Event.ON_CREATE;
                        int i3 = iArr[state.ordinal()];
                        if (i3 == 1) {
                            event = Lifecycle.Event.ON_DESTROY;
                        } else if (i3 == 2) {
                            event = Lifecycle.Event.ON_STOP;
                        } else {
                            if (i3 != 3) {
                                event2 = null;
                                final MutexImpl mutexImplMutex$default = MutexKt.Mutex$default();
                                ?? r4 = new LifecycleEventObserver() { // from class: androidx.lifecycle.RepeatOnLifecycleKt$repeatOnLifecycle$3$1$1$1

                                    /* renamed from: androidx.lifecycle.RepeatOnLifecycleKt$repeatOnLifecycle$3$1$1$1$1, reason: invalid class name */
                                    final class AnonymousClass1 extends SuspendLambda implements Function2 {
                                        final /* synthetic */ Function2 $block;
                                        final /* synthetic */ Mutex $mutex;
                                        Object L$0;
                                        Object L$1;
                                        int label;

                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        public AnonymousClass1(Mutex mutex, Function2 function2, Continuation continuation) {
                                            super(2, continuation);
                                            this.$mutex = mutex;
                                            this.$block = function2;
                                        }

                                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                        public final Continuation create(Object obj, Continuation continuation) {
                                            return new AnonymousClass1(this.$mutex, this.$block, continuation);
                                        }

                                        @Override // kotlin.jvm.functions.Function2
                                        public final Object invoke(Object obj, Object obj2) {
                                            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                                        }

                                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                        public final Object invokeSuspend(Object obj) throws Throwable {
                                            Mutex mutex;
                                            Function2 function2;
                                            Throwable th;
                                            Mutex mutex2;
                                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                            int i = this.label;
                                            try {
                                                if (i == 0) {
                                                    ResultKt.throwOnFailure(obj);
                                                    mutex = this.$mutex;
                                                    function2 = this.$block;
                                                    this.L$0 = mutex;
                                                    this.L$1 = function2;
                                                    this.label = 1;
                                                    if (mutex.lock(this) != coroutineSingletons) {
                                                    }
                                                    return coroutineSingletons;
                                                }
                                                if (i != 1) {
                                                    if (i != 2) {
                                                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                                    }
                                                    mutex2 = (Mutex) this.L$0;
                                                    try {
                                                        ResultKt.throwOnFailure(obj);
                                                        Unit unit = Unit.INSTANCE;
                                                        mutex2.unlock(null);
                                                        return Unit.INSTANCE;
                                                    } catch (Throwable th2) {
                                                        th = th2;
                                                        mutex2.unlock(null);
                                                        throw th;
                                                    }
                                                }
                                                function2 = (Function2) this.L$1;
                                                Mutex mutex3 = (Mutex) this.L$0;
                                                ResultKt.throwOnFailure(obj);
                                                mutex = mutex3;
                                                RepeatOnLifecycleKt$repeatOnLifecycle$3$1$1$1$1$1$1 repeatOnLifecycleKt$repeatOnLifecycle$3$1$1$1$1$1$1 = new RepeatOnLifecycleKt$repeatOnLifecycle$3$1$1$1$1$1$1(function2, null);
                                                this.L$0 = mutex;
                                                this.L$1 = null;
                                                this.label = 2;
                                                if (CoroutineScopeKt.coroutineScope(repeatOnLifecycleKt$repeatOnLifecycle$3$1$1$1$1$1$1, this) != coroutineSingletons) {
                                                    mutex2 = mutex;
                                                    Unit unit2 = Unit.INSTANCE;
                                                    mutex2.unlock(null);
                                                    return Unit.INSTANCE;
                                                }
                                                return coroutineSingletons;
                                            } catch (Throwable th3) {
                                                Mutex mutex4 = mutex;
                                                th = th3;
                                                mutex2 = mutex4;
                                                mutex2.unlock(null);
                                                throw th;
                                            }
                                        }
                                    }

                                    /* JADX WARN: Type inference failed for: r3v3, types: [T, kotlinx.coroutines.StandaloneCoroutine] */
                                    @Override // androidx.lifecycle.LifecycleEventObserver
                                    public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event4) {
                                        Lifecycle.Event event5 = event3;
                                        Ref$ObjectRef ref$ObjectRef4 = ref$ObjectRef3;
                                        if (event4 == event5) {
                                            ref$ObjectRef4.element = BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(mutexImplMutex$default, function2, null), 3);
                                            return;
                                        }
                                        if (event4 == event2) {
                                            Job job3 = (Job) ref$ObjectRef4.element;
                                            if (job3 != null) {
                                                job3.cancel(null);
                                            }
                                            ref$ObjectRef4.element = null;
                                        }
                                        if (event4 == Lifecycle.Event.ON_DESTROY) {
                                            int i4 = Result.$r8$clinit;
                                            cancellableContinuationImpl.resumeWith(Unit.INSTANCE);
                                        }
                                    }
                                };
                                ref$ObjectRef.element = r4;
                                lifecycle.addObserver(r4);
                                if (cancellableContinuationImpl.getResult() != coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                                ref$ObjectRef2 = ref$ObjectRef3;
                                job2 = (Job) ref$ObjectRef2.element;
                                if (job2 != null) {
                                }
                                lifecycleEventObserver2 = (LifecycleEventObserver) ref$ObjectRef.element;
                                if (lifecycleEventObserver2 != null) {
                                }
                                return Unit.INSTANCE;
                            }
                            event = Lifecycle.Event.ON_PAUSE;
                        }
                        event2 = event;
                        final Mutex mutexImplMutex$default2 = MutexKt.Mutex$default();
                        ?? r42 = new LifecycleEventObserver() { // from class: androidx.lifecycle.RepeatOnLifecycleKt$repeatOnLifecycle$3$1$1$1

                            /* renamed from: androidx.lifecycle.RepeatOnLifecycleKt$repeatOnLifecycle$3$1$1$1$1, reason: invalid class name */
                            final class AnonymousClass1 extends SuspendLambda implements Function2 {
                                final /* synthetic */ Function2 $block;
                                final /* synthetic */ Mutex $mutex;
                                Object L$0;
                                Object L$1;
                                int label;

                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                public AnonymousClass1(Mutex mutex, Function2 function2, Continuation continuation) {
                                    super(2, continuation);
                                    this.$mutex = mutex;
                                    this.$block = function2;
                                }

                                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                public final Continuation create(Object obj, Continuation continuation) {
                                    return new AnonymousClass1(this.$mutex, this.$block, continuation);
                                }

                                @Override // kotlin.jvm.functions.Function2
                                public final Object invoke(Object obj, Object obj2) {
                                    return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                                }

                                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                public final Object invokeSuspend(Object obj) throws Throwable {
                                    Mutex mutex;
                                    Function2 function2;
                                    Throwable th;
                                    Mutex mutex2;
                                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                    int i = this.label;
                                    try {
                                        if (i == 0) {
                                            ResultKt.throwOnFailure(obj);
                                            mutex = this.$mutex;
                                            function2 = this.$block;
                                            this.L$0 = mutex;
                                            this.L$1 = function2;
                                            this.label = 1;
                                            if (mutex.lock(this) != coroutineSingletons) {
                                            }
                                            return coroutineSingletons;
                                        }
                                        if (i != 1) {
                                            if (i != 2) {
                                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                            }
                                            mutex2 = (Mutex) this.L$0;
                                            try {
                                                ResultKt.throwOnFailure(obj);
                                                Unit unit2 = Unit.INSTANCE;
                                                mutex2.unlock(null);
                                                return Unit.INSTANCE;
                                            } catch (Throwable th2) {
                                                th = th2;
                                                mutex2.unlock(null);
                                                throw th;
                                            }
                                        }
                                        function2 = (Function2) this.L$1;
                                        Mutex mutex3 = (Mutex) this.L$0;
                                        ResultKt.throwOnFailure(obj);
                                        mutex = mutex3;
                                        RepeatOnLifecycleKt$repeatOnLifecycle$3$1$1$1$1$1$1 repeatOnLifecycleKt$repeatOnLifecycle$3$1$1$1$1$1$1 = new RepeatOnLifecycleKt$repeatOnLifecycle$3$1$1$1$1$1$1(function2, null);
                                        this.L$0 = mutex;
                                        this.L$1 = null;
                                        this.label = 2;
                                        if (CoroutineScopeKt.coroutineScope(repeatOnLifecycleKt$repeatOnLifecycle$3$1$1$1$1$1$1, this) != coroutineSingletons) {
                                            mutex2 = mutex;
                                            Unit unit22 = Unit.INSTANCE;
                                            mutex2.unlock(null);
                                            return Unit.INSTANCE;
                                        }
                                        return coroutineSingletons;
                                    } catch (Throwable th3) {
                                        Mutex mutex4 = mutex;
                                        th = th3;
                                        mutex2 = mutex4;
                                        mutex2.unlock(null);
                                        throw th;
                                    }
                                }
                            }

                            /* JADX WARN: Type inference failed for: r3v3, types: [T, kotlinx.coroutines.StandaloneCoroutine] */
                            @Override // androidx.lifecycle.LifecycleEventObserver
                            public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event4) {
                                Lifecycle.Event event5 = event3;
                                Ref$ObjectRef ref$ObjectRef4 = ref$ObjectRef3;
                                if (event4 == event5) {
                                    ref$ObjectRef4.element = BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(mutexImplMutex$default2, function2, null), 3);
                                    return;
                                }
                                if (event4 == event2) {
                                    Job job3 = (Job) ref$ObjectRef4.element;
                                    if (job3 != null) {
                                        job3.cancel(null);
                                    }
                                    ref$ObjectRef4.element = null;
                                }
                                if (event4 == Lifecycle.Event.ON_DESTROY) {
                                    int i4 = Result.$r8$clinit;
                                    cancellableContinuationImpl.resumeWith(Unit.INSTANCE);
                                }
                            }
                        };
                        ref$ObjectRef.element = r42;
                        lifecycle.addObserver(r42);
                        if (cancellableContinuationImpl.getResult() != coroutineSingletons) {
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        ref$ObjectRef2 = ref$ObjectRef3;
                        job = (Job) ref$ObjectRef2.element;
                        if (job != null) {
                            job.cancel(null);
                        }
                        lifecycleEventObserver = (LifecycleEventObserver) ref$ObjectRef.element;
                        if (lifecycleEventObserver != null) {
                            throw th;
                        }
                        this.$this_repeatOnLifecycle.removeObserver(lifecycleEventObserver);
                        throw th;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ref$ObjectRef = (Ref$ObjectRef) this.L$1;
                    ref$ObjectRef2 = (Ref$ObjectRef) this.L$0;
                    try {
                        ResultKt.throwOnFailure(obj);
                        job2 = (Job) ref$ObjectRef2.element;
                        if (job2 != null) {
                            job2.cancel(null);
                        }
                        lifecycleEventObserver2 = (LifecycleEventObserver) ref$ObjectRef.element;
                        if (lifecycleEventObserver2 != null) {
                            this.$this_repeatOnLifecycle.removeObserver(lifecycleEventObserver2);
                        }
                        return Unit.INSTANCE;
                    } catch (Throwable th3) {
                        th = th3;
                        job = (Job) ref$ObjectRef2.element;
                        if (job != null) {
                        }
                        lifecycleEventObserver = (LifecycleEventObserver) ref$ObjectRef.element;
                        if (lifecycleEventObserver != null) {
                        }
                    }
                }
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(Lifecycle lifecycle, Lifecycle.State state, Function2 function2, Continuation continuation) {
            super(2, continuation);
            this.$this_repeatOnLifecycle = lifecycle;
            this.$state = state;
            this.$block = function2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass3 anonymousClass3 = new AnonymousClass3(this.$this_repeatOnLifecycle, this.$state, this.$block, continuation);
            anonymousClass3.L$0 = obj;
            return anonymousClass3;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                DefaultScheduler defaultScheduler = Dispatchers.Default;
                HandlerContext handlerContext = MainDispatcherLoader.dispatcher.immediate;
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$this_repeatOnLifecycle, this.$state, coroutineScope, this.$block, null);
                this.label = 1;
                if (BuildersKt.withContext(handlerContext, anonymousClass1, this) == coroutineSingletons) {
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

    public static final Object repeatOnLifecycle(Lifecycle lifecycle, Lifecycle.State state, Function2 function2, Continuation continuation) {
        if (state == Lifecycle.State.INITIALIZED) {
            throw new IllegalArgumentException("repeatOnLifecycle cannot start work with the INITIALIZED lifecycle state.");
        }
        if (lifecycle.getCurrentState() == Lifecycle.State.DESTROYED) {
            return Unit.INSTANCE;
        }
        Object objCoroutineScope = CoroutineScopeKt.coroutineScope(new AnonymousClass3(lifecycle, state, function2, null), continuation);
        return objCoroutineScope == CoroutineSingletons.COROUTINE_SUSPENDED ? objCoroutineScope : Unit.INSTANCE;
    }

    public static final Object repeatOnLifecycle(LifecycleOwner lifecycleOwner, Lifecycle.State state, Function2 function2, Continuation continuation) {
        Object objRepeatOnLifecycle = repeatOnLifecycle(lifecycleOwner.getLifecycle(), state, function2, continuation);
        return objRepeatOnLifecycle == CoroutineSingletons.COROUTINE_SUSPENDED ? objRepeatOnLifecycle : Unit.INSTANCE;
    }
}
