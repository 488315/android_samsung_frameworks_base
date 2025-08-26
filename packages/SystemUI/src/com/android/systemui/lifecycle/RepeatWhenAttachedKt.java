package com.android.systemui.lifecycle;

import android.view.View;
import androidx.lifecycle.LifecycleKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.app.tracing.coroutines.TraceContextElementKt;
import com.android.app.tracing.coroutines.TraceDataThreadLocal;
import com.android.systemui.util.Assert;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import com.android.systemui.utils.coroutines.flow.LatestConflatedKt;
import kotlin.KotlinNothingValueException;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.android.HandlerContext;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* loaded from: classes2.dex */
public abstract class RepeatWhenAttachedKt {
    public static final CoroutineContext MAIN_DISPATCHER_SINGLETON;

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[WindowLifecycleState.values().length];
            try {
                iArr[WindowLifecycleState.ATTACHED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[WindowLifecycleState.VISIBLE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[WindowLifecycleState.FOCUSED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* renamed from: com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatOnWindowLifecycle$1, reason: invalid class name */
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
            return RepeatWhenAttachedKt.repeatOnWindowLifecycle(null, null, null, this);
        }
    }

    /* renamed from: com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenAttached$1, reason: invalid class name and case insensitive filesystem */
    public final class C09181 implements DisposableHandle {
        public final /* synthetic */ Ref$ObjectRef $lifecycleOwner;
        public final /* synthetic */ RepeatWhenAttachedKt$repeatWhenAttached$onAttachListener$1 $onAttachListener;
        public final /* synthetic */ View $view;

        public C09181(Ref$ObjectRef<ViewLifecycleOwner> ref$ObjectRef, View view, RepeatWhenAttachedKt$repeatWhenAttached$onAttachListener$1 repeatWhenAttachedKt$repeatWhenAttached$onAttachListener$1) {
            this.$lifecycleOwner = ref$ObjectRef;
            this.$view = view;
            this.$onAttachListener = repeatWhenAttachedKt$repeatWhenAttached$onAttachListener$1;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // kotlinx.coroutines.DisposableHandle
        public final void dispose() {
            Assert.isMainThread();
            Ref$ObjectRef ref$ObjectRef = this.$lifecycleOwner;
            ViewLifecycleOwner viewLifecycleOwner = (ViewLifecycleOwner) ref$ObjectRef.element;
            if (viewLifecycleOwner != null) {
                viewLifecycleOwner.onDestroy();
            }
            ref$ObjectRef.element = null;
            this.$view.removeOnAttachStateChangeListener(this.$onAttachListener);
        }
    }

    /* renamed from: com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenAttachedToWindow$1, reason: invalid class name and case insensitive filesystem */
    final class C09191 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public C09191(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return RepeatWhenAttachedKt.repeatWhenAttachedToWindow(null, null, this);
        }
    }

    /* renamed from: com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenAttachedToWindow$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ Function2 $block;
        /* synthetic */ boolean Z$0;
        int label;

        /* renamed from: com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenAttachedToWindow$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ Function2 $block;
            private /* synthetic */ Object L$0;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(Function2 function2, Continuation continuation) {
                super(2, continuation);
                this.$block = function2;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$block, continuation);
                anonymousClass1.L$0 = obj;
                return anonymousClass1;
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
                    CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                    Function2 function2 = this.$block;
                    this.label = 1;
                    if (function2.invoke(coroutineScope, this) == coroutineSingletons) {
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

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(Function2 function2, Continuation continuation) {
            super(2, continuation);
            this.$block = function2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$block, continuation);
            anonymousClass2.Z$0 = ((Boolean) obj).booleanValue();
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            Boolean bool = (Boolean) obj;
            bool.booleanValue();
            return ((AnonymousClass2) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                if (this.Z$0) {
                    AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$block, null);
                    this.label = 1;
                    if (CoroutineScopeKt.coroutineScope(anonymousClass1, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
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

    /* renamed from: com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenWindowHasFocus$1, reason: invalid class name and case insensitive filesystem */
    final class C09201 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public C09201(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return RepeatWhenAttachedKt.repeatWhenWindowHasFocus(null, null, this);
        }
    }

    /* renamed from: com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenWindowHasFocus$2, reason: invalid class name and case insensitive filesystem */
    final class C09212 extends SuspendLambda implements Function2 {
        final /* synthetic */ Function2 $block;
        /* synthetic */ boolean Z$0;
        int label;

        /* renamed from: com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenWindowHasFocus$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ Function2 $block;
            private /* synthetic */ Object L$0;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(Function2 function2, Continuation continuation) {
                super(2, continuation);
                this.$block = function2;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$block, continuation);
                anonymousClass1.L$0 = obj;
                return anonymousClass1;
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
                    CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                    Function2 function2 = this.$block;
                    this.label = 1;
                    if (function2.invoke(coroutineScope, this) == coroutineSingletons) {
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

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09212(Function2 function2, Continuation continuation) {
            super(2, continuation);
            this.$block = function2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C09212 c09212 = new C09212(this.$block, continuation);
            c09212.Z$0 = ((Boolean) obj).booleanValue();
            return c09212;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            Boolean bool = (Boolean) obj;
            bool.booleanValue();
            return ((C09212) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                if (this.Z$0) {
                    AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$block, null);
                    this.label = 1;
                    if (CoroutineScopeKt.coroutineScope(anonymousClass1, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
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

    /* renamed from: com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenWindowIsVisible$1, reason: invalid class name and case insensitive filesystem */
    final class C09221 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public C09221(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return RepeatWhenAttachedKt.repeatWhenWindowIsVisible(null, null, this);
        }
    }

    /* renamed from: com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenWindowIsVisible$2, reason: invalid class name and case insensitive filesystem */
    final class C09232 extends SuspendLambda implements Function2 {
        final /* synthetic */ Function2 $block;
        /* synthetic */ boolean Z$0;
        int label;

        /* renamed from: com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenWindowIsVisible$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ Function2 $block;
            private /* synthetic */ Object L$0;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(Function2 function2, Continuation continuation) {
                super(2, continuation);
                this.$block = function2;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$block, continuation);
                anonymousClass1.L$0 = obj;
                return anonymousClass1;
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
                    CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                    Function2 function2 = this.$block;
                    this.label = 1;
                    if (function2.invoke(coroutineScope, this) == coroutineSingletons) {
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

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09232(Function2 function2, Continuation continuation) {
            super(2, continuation);
            this.$block = function2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C09232 c09232 = new C09232(this.$block, continuation);
            c09232.Z$0 = ((Boolean) obj).booleanValue();
            return c09232;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            Boolean bool = (Boolean) obj;
            bool.booleanValue();
            return ((C09232) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                if (this.Z$0) {
                    AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$block, null);
                    this.label = 1;
                    if (CoroutineScopeKt.coroutineScope(anonymousClass1, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
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
        DefaultScheduler defaultScheduler = Dispatchers.Default;
        HandlerContext handlerContext = MainDispatcherLoader.dispatcher;
        TraceDataThreadLocal traceDataThreadLocal = TraceContextElementKt.traceThreadLocal;
        EmptyCoroutineContext emptyCoroutineContext = EmptyCoroutineContext.INSTANCE;
        handlerContext.getClass();
        MAIN_DISPATCHER_SINGLETON = CoroutineContext.DefaultImpls.plus(handlerContext, emptyCoroutineContext);
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0054, code lost:
    
        if (repeatWhenWindowHasFocus(r6, r8, r0) == r1) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0069, code lost:
    
        if (repeatWhenWindowIsVisible(r6, r8, r0) == r1) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0078, code lost:
    
        if (repeatWhenAttachedToWindow(r6, r8, r0) == r1) goto L36;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final CoroutineSingletons repeatOnWindowLifecycle(View view, WindowLifecycleState windowLifecycleState, Function2 function2, ContinuationImpl continuationImpl) {
        AnonymousClass1 anonymousClass1;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 != 0) {
            if (i2 == 1) {
                ResultKt.throwOnFailure(obj);
                throw new KotlinNothingValueException();
            }
            if (i2 == 2) {
                ResultKt.throwOnFailure(obj);
                throw new KotlinNothingValueException();
            }
            if (i2 != 3) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            throw new KotlinNothingValueException();
        }
        ResultKt.throwOnFailure(obj);
        int i3 = WhenMappings.$EnumSwitchMapping$0[windowLifecycleState.ordinal()];
        if (i3 == 1) {
            anonymousClass1.label = 1;
        } else if (i3 == 2) {
            anonymousClass1.label = 2;
        } else {
            if (i3 != 3) {
                throw new NoWhenBranchMatchedException();
            }
            anonymousClass1.label = 3;
        }
        return coroutineSingletons;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [android.view.View$OnAttachStateChangeListener, com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenAttached$onAttachListener$1] */
    /* JADX WARN: Type inference failed for: r2v1, types: [T, com.android.systemui.lifecycle.ViewLifecycleOwner] */
    public static final C09181 repeatWhenAttached(final View view, CoroutineContext coroutineContext, final Function3 function3) {
        Assert.isMainThread();
        final CoroutineContext coroutineContextPlus = MAIN_DISPATCHER_SINGLETON.plus(coroutineContext);
        final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        ?? r1 = new View.OnAttachStateChangeListener() { // from class: com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenAttached$onAttachListener$1
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r2v1, types: [T, com.android.systemui.lifecycle.ViewLifecycleOwner] */
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view2) {
                Assert.isMainThread();
                ViewLifecycleOwner viewLifecycleOwner = (ViewLifecycleOwner) ref$ObjectRef.element;
                if (viewLifecycleOwner != null) {
                    viewLifecycleOwner.onDestroy();
                }
                Ref$ObjectRef ref$ObjectRef2 = ref$ObjectRef;
                View view3 = view;
                CoroutineContext coroutineContext2 = coroutineContextPlus;
                Function3 function32 = function3;
                CoroutineContext coroutineContext3 = RepeatWhenAttachedKt.MAIN_DISPATCHER_SINGLETON;
                ?? viewLifecycleOwner2 = new ViewLifecycleOwner(view3);
                viewLifecycleOwner2.onCreate();
                CoroutineTracingKt.launchTraced$default(LifecycleKt.getCoroutineScope(viewLifecycleOwner2.registry), coroutineContext2, null, new RepeatWhenAttachedKt$createLifecycleOwnerAndRun$1$1(function32, viewLifecycleOwner2, view3, null), 5);
                ref$ObjectRef2.element = viewLifecycleOwner2;
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewDetachedFromWindow(View view2) {
                ViewLifecycleOwner viewLifecycleOwner = (ViewLifecycleOwner) ref$ObjectRef.element;
                if (viewLifecycleOwner != null) {
                    viewLifecycleOwner.onDestroy();
                }
                ref$ObjectRef.element = null;
            }
        };
        view.addOnAttachStateChangeListener(r1);
        if (view.isAttachedToWindow()) {
            ?? viewLifecycleOwner = new ViewLifecycleOwner(view);
            viewLifecycleOwner.onCreate();
            CoroutineTracingKt.launchTraced$default(LifecycleKt.getCoroutineScope(viewLifecycleOwner.registry), coroutineContextPlus, null, new RepeatWhenAttachedKt$createLifecycleOwnerAndRun$1$1(function3, viewLifecycleOwner, view, null), 5);
            ref$ObjectRef.element = viewLifecycleOwner;
        }
        return new C09181(ref$ObjectRef, view, r1);
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x005a, code lost:
    
        if (kotlinx.coroutines.DelayKt.awaitCancellation(r0) == r1) goto L21;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final CoroutineSingletons repeatWhenAttachedToWindow(View view, Function2 function2, ContinuationImpl continuationImpl) {
        C09191 c09191;
        if (continuationImpl instanceof C09191) {
            c09191 = (C09191) continuationImpl;
            int i = c09191.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c09191.label = i - Integer.MIN_VALUE;
            } else {
                c09191 = new C09191(continuationImpl);
            }
        }
        Object obj = c09191.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c09191.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            Assert.isMainThread();
            Flow flowConflatedCallbackFlow = FlowConflatedKt.conflatedCallbackFlow(new RepeatWhenAttachedKt$isAttached$1(view, null));
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(function2, null);
            c09191.label = 1;
            if (FlowKt.collectLatest(flowConflatedCallbackFlow, anonymousClass2, c09191) != coroutineSingletons) {
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
        c09191.label = 2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0068, code lost:
    
        if (kotlinx.coroutines.DelayKt.awaitCancellation(r0) == r1) goto L21;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final CoroutineSingletons repeatWhenWindowHasFocus(View view, Function2 function2, ContinuationImpl continuationImpl) {
        C09201 c09201;
        if (continuationImpl instanceof C09201) {
            c09201 = (C09201) continuationImpl;
            int i = c09201.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c09201.label = i - Integer.MIN_VALUE;
            } else {
                c09201 = new C09201(continuationImpl);
            }
        }
        Object obj = c09201.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c09201.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            Assert.isMainThread();
            Flow flowFlatMapLatestConflated = LatestConflatedKt.flatMapLatestConflated(new RepeatWhenAttachedKt$special$$inlined$map$1(FlowConflatedKt.conflatedCallbackFlow(new RepeatWhenAttachedKt$isAttached$1(view, null)), view), new RepeatWhenAttachedKt$isWindowFocused$1(view, null));
            C09212 c09212 = new C09212(function2, null);
            c09201.label = 1;
            if (FlowKt.collectLatest(flowFlatMapLatestConflated, c09212, c09201) != coroutineSingletons) {
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
        c09201.label = 2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0068, code lost:
    
        if (kotlinx.coroutines.DelayKt.awaitCancellation(r0) == r1) goto L21;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final CoroutineSingletons repeatWhenWindowIsVisible(View view, Function2 function2, ContinuationImpl continuationImpl) {
        C09221 c09221;
        if (continuationImpl instanceof C09221) {
            c09221 = (C09221) continuationImpl;
            int i = c09221.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c09221.label = i - Integer.MIN_VALUE;
            } else {
                c09221 = new C09221(continuationImpl);
            }
        }
        Object obj = c09221.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c09221.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            Assert.isMainThread();
            Flow flowFlatMapLatestConflated = LatestConflatedKt.flatMapLatestConflated(new RepeatWhenAttachedKt$special$$inlined$map$1(FlowConflatedKt.conflatedCallbackFlow(new RepeatWhenAttachedKt$isAttached$1(view, null)), view), new RepeatWhenAttachedKt$isWindowVisible$1(view, null));
            C09232 c09232 = new C09232(function2, null);
            c09221.label = 1;
            if (FlowKt.collectLatest(flowFlatMapLatestConflated, c09232, c09221) != coroutineSingletons) {
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
        c09221.label = 2;
    }
}
