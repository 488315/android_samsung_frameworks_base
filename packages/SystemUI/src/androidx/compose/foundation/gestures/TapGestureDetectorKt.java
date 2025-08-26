package androidx.compose.foundation.gestures;

import androidx.compose.foundation.ComposeFoundationFlags;
import androidx.compose.foundation.gestures.LongPressResult;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.pointer.AwaitPointerEventScope;
import androidx.compose.ui.input.pointer.PointerEvent;
import androidx.compose.ui.input.pointer.PointerEventKt;
import androidx.compose.ui.input.pointer.PointerEventPass;
import androidx.compose.ui.input.pointer.PointerEventTimeoutCancellationException;
import androidx.compose.ui.input.pointer.PointerInputChange;
import androidx.compose.ui.input.pointer.PointerInputScope;
import androidx.compose.ui.input.pointer.SuspendingPointerInputModifierNodeImpl;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.BaseContinuationImpl;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.StandaloneCoroutine;

/* loaded from: classes.dex */
public abstract class TapGestureDetectorKt {
    public static final Function3 NoPressGesture = new TapGestureDetectorKt$NoPressGesture$1(null);

    /* renamed from: androidx.compose.foundation.gestures.TapGestureDetectorKt$awaitFirstDown$2, reason: invalid class name */
    final class AnonymousClass2 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        boolean Z$0;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass2(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return TapGestureDetectorKt.awaitFirstDown(null, false, null, this);
        }
    }

    /* renamed from: androidx.compose.foundation.gestures.TapGestureDetectorKt$detectTapAndPress$2, reason: invalid class name and case insensitive filesystem */
    final class C07002 extends SuspendLambda implements Function2 {
        final /* synthetic */ Function3 $onPress;
        final /* synthetic */ Function1 $onTap;
        final /* synthetic */ PressGestureScopeImpl $pressScope;
        final /* synthetic */ PointerInputScope $this_detectTapAndPress;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: androidx.compose.foundation.gestures.TapGestureDetectorKt$detectTapAndPress$2$1, reason: invalid class name */
        final class AnonymousClass1 extends RestrictedSuspendLambda implements Function2 {
            final /* synthetic */ CoroutineScope $$this$coroutineScope;
            final /* synthetic */ Function3 $onPress;
            final /* synthetic */ Function1 $onTap;
            final /* synthetic */ PressGestureScopeImpl $pressScope;
            private /* synthetic */ Object L$0;
            Object L$1;
            int label;

            /* renamed from: androidx.compose.foundation.gestures.TapGestureDetectorKt$detectTapAndPress$2$1$1, reason: invalid class name and collision with other inner class name */
            final class C00091 extends SuspendLambda implements Function2 {
                final /* synthetic */ PointerInputChange $down;
                final /* synthetic */ Function3 $onPress;
                final /* synthetic */ PressGestureScopeImpl $pressScope;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C00091(Function3 function3, PressGestureScopeImpl pressGestureScopeImpl, PointerInputChange pointerInputChange, Continuation continuation) {
                    super(2, continuation);
                    this.$onPress = function3;
                    this.$pressScope = pressGestureScopeImpl;
                    this.$down = pointerInputChange;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C00091(this.$onPress, this.$pressScope, this.$down, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C00091) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        Function3 function3 = this.$onPress;
                        PressGestureScopeImpl pressGestureScopeImpl = this.$pressScope;
                        Offset offsetM395boximpl = Offset.m395boximpl(this.$down.position);
                        this.label = 1;
                        if (function3.invoke(pressGestureScopeImpl, offsetM395boximpl, this) == coroutineSingletons) {
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

            /* renamed from: androidx.compose.foundation.gestures.TapGestureDetectorKt$detectTapAndPress$2$1$2, reason: invalid class name and collision with other inner class name */
            final class C00102 extends SuspendLambda implements Function2 {
                final /* synthetic */ PressGestureScopeImpl $pressScope;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C00102(PressGestureScopeImpl pressGestureScopeImpl, Continuation continuation) {
                    super(2, continuation);
                    this.$pressScope = pressGestureScopeImpl;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C00102(this.$pressScope, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C00102) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    this.$pressScope.cancel();
                    return Unit.INSTANCE;
                }
            }

            /* renamed from: androidx.compose.foundation.gestures.TapGestureDetectorKt$detectTapAndPress$2$1$3, reason: invalid class name */
            final class AnonymousClass3 extends SuspendLambda implements Function2 {
                final /* synthetic */ PressGestureScopeImpl $pressScope;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass3(PressGestureScopeImpl pressGestureScopeImpl, Continuation continuation) {
                    super(2, continuation);
                    this.$pressScope = pressGestureScopeImpl;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass3(this.$pressScope, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((AnonymousClass3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    this.$pressScope.release();
                    return Unit.INSTANCE;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(CoroutineScope coroutineScope, Function3 function3, Function1 function1, PressGestureScopeImpl pressGestureScopeImpl, Continuation continuation) {
                super(2, continuation);
                this.$$this$coroutineScope = coroutineScope;
                this.$onPress = function3;
                this.$onTap = function1;
                this.$pressScope = pressGestureScopeImpl;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$$this$coroutineScope, this.$onPress, this.$onTap, this.$pressScope, continuation);
                anonymousClass1.L$0 = obj;
                return anonymousClass1;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass1) create((AwaitPointerEventScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            /* JADX WARN: Removed duplicated region for block: B:27:0x0085  */
            /* JADX WARN: Removed duplicated region for block: B:28:0x0092  */
            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object invokeSuspend(Object obj) {
                Job jobLaunch$default;
                AwaitPointerEventScope awaitPointerEventScope;
                Job job;
                PointerInputChange pointerInputChange;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    AwaitPointerEventScope awaitPointerEventScope2 = (AwaitPointerEventScope) this.L$0;
                    CoroutineScope coroutineScope = this.$$this$coroutineScope;
                    Function3 function3 = TapGestureDetectorKt.NoPressGesture;
                    jobLaunch$default = BuildersKt.launch$default(coroutineScope, null, ComposeFoundationFlags.isDetectTapGesturesImmediateCoroutineDispatchEnabled ? CoroutineStart.UNDISPATCHED : CoroutineStart.DEFAULT, new TapGestureDetectorKt$detectTapAndPress$2$1$resetJob$1(this.$pressScope, null), 1);
                    this.L$0 = awaitPointerEventScope2;
                    this.L$1 = jobLaunch$default;
                    this.label = 1;
                    Object objAwaitFirstDown$default = TapGestureDetectorKt.awaitFirstDown$default(awaitPointerEventScope2, null, this, 3);
                    if (objAwaitFirstDown$default != coroutineSingletons) {
                        awaitPointerEventScope = awaitPointerEventScope2;
                        obj = objAwaitFirstDown$default;
                    }
                    return coroutineSingletons;
                }
                if (i != 1) {
                    if (i != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    job = (Job) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    pointerInputChange = (PointerInputChange) obj;
                    if (pointerInputChange != null) {
                        TapGestureDetectorKt.launchAwaitingReset$default(this.$$this$coroutineScope, job, new C00102(this.$pressScope, null));
                    } else {
                        pointerInputChange.consume();
                        TapGestureDetectorKt.launchAwaitingReset$default(this.$$this$coroutineScope, job, new AnonymousClass3(this.$pressScope, null));
                        Function1 function1 = this.$onTap;
                        if (function1 != null) {
                            function1.mo781invoke(Offset.m395boximpl(pointerInputChange.position));
                        }
                    }
                    return Unit.INSTANCE;
                }
                jobLaunch$default = (Job) this.L$1;
                awaitPointerEventScope = (AwaitPointerEventScope) this.L$0;
                ResultKt.throwOnFailure(obj);
                PointerInputChange pointerInputChange2 = (PointerInputChange) obj;
                pointerInputChange2.consume();
                Function3 function32 = this.$onPress;
                if (function32 != TapGestureDetectorKt.NoPressGesture) {
                    TapGestureDetectorKt.launchAwaitingReset$default(this.$$this$coroutineScope, jobLaunch$default, new C00091(function32, this.$pressScope, pointerInputChange2, null));
                }
                this.L$0 = jobLaunch$default;
                this.L$1 = null;
                this.label = 2;
                obj = TapGestureDetectorKt.waitForUpOrCancellation(awaitPointerEventScope, PointerEventPass.Main, this);
                if (obj != coroutineSingletons) {
                    job = jobLaunch$default;
                    pointerInputChange = (PointerInputChange) obj;
                    if (pointerInputChange != null) {
                    }
                    return Unit.INSTANCE;
                }
                return coroutineSingletons;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C07002(PointerInputScope pointerInputScope, Function3 function3, Function1 function1, PressGestureScopeImpl pressGestureScopeImpl, Continuation continuation) {
            super(2, continuation);
            this.$this_detectTapAndPress = pointerInputScope;
            this.$onPress = function3;
            this.$onTap = function1;
            this.$pressScope = pressGestureScopeImpl;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C07002 c07002 = new C07002(this.$this_detectTapAndPress, this.$onPress, this.$onTap, this.$pressScope, continuation);
            c07002.L$0 = obj;
            return c07002;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C07002) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                PointerInputScope pointerInputScope = this.$this_detectTapAndPress;
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(coroutineScope, this.$onPress, this.$onTap, this.$pressScope, null);
                this.label = 1;
                if (ForEachGestureKt.awaitEachGesture(pointerInputScope, anonymousClass1, this) == coroutineSingletons) {
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

    /* renamed from: androidx.compose.foundation.gestures.TapGestureDetectorKt$detectTapGestures$2, reason: invalid class name and case insensitive filesystem */
    final class C07012 extends SuspendLambda implements Function2 {
        final /* synthetic */ Function1 $onDoubleTap;
        final /* synthetic */ Function1 $onLongPress;
        final /* synthetic */ Function3 $onPress;
        final /* synthetic */ Function1 $onTap;
        final /* synthetic */ PointerInputScope $this_detectTapGestures;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: androidx.compose.foundation.gestures.TapGestureDetectorKt$detectTapGestures$2$1, reason: invalid class name */
        final class AnonymousClass1 extends RestrictedSuspendLambda implements Function2 {
            final /* synthetic */ CoroutineScope $$this$coroutineScope;
            final /* synthetic */ Function1 $onDoubleTap;
            final /* synthetic */ Function1 $onLongPress;
            final /* synthetic */ Function3 $onPress;
            final /* synthetic */ Function1 $onTap;
            final /* synthetic */ PressGestureScopeImpl $pressScope;
            private /* synthetic */ Object L$0;
            Object L$1;
            Object L$2;
            Object L$3;
            int label;

            /* renamed from: androidx.compose.foundation.gestures.TapGestureDetectorKt$detectTapGestures$2$1$1, reason: invalid class name and collision with other inner class name */
            final class C00111 extends SuspendLambda implements Function2 {
                final /* synthetic */ PointerInputChange $down;
                final /* synthetic */ Function3 $onPress;
                final /* synthetic */ PressGestureScopeImpl $pressScope;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C00111(Function3 function3, PressGestureScopeImpl pressGestureScopeImpl, PointerInputChange pointerInputChange, Continuation continuation) {
                    super(2, continuation);
                    this.$onPress = function3;
                    this.$pressScope = pressGestureScopeImpl;
                    this.$down = pointerInputChange;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C00111(this.$onPress, this.$pressScope, this.$down, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C00111) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        Function3 function3 = this.$onPress;
                        PressGestureScopeImpl pressGestureScopeImpl = this.$pressScope;
                        Offset offsetM395boximpl = Offset.m395boximpl(this.$down.position);
                        this.label = 1;
                        if (function3.invoke(pressGestureScopeImpl, offsetM395boximpl, this) == coroutineSingletons) {
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

            /* renamed from: androidx.compose.foundation.gestures.TapGestureDetectorKt$detectTapGestures$2$1$2, reason: invalid class name and collision with other inner class name */
            final class C00122 extends SuspendLambda implements Function2 {
                final /* synthetic */ PressGestureScopeImpl $pressScope;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C00122(PressGestureScopeImpl pressGestureScopeImpl, Continuation continuation) {
                    super(2, continuation);
                    this.$pressScope = pressGestureScopeImpl;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C00122(this.$pressScope, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C00122) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    this.$pressScope.release();
                    return Unit.INSTANCE;
                }
            }

            /* renamed from: androidx.compose.foundation.gestures.TapGestureDetectorKt$detectTapGestures$2$1$3, reason: invalid class name */
            final class AnonymousClass3 extends SuspendLambda implements Function2 {
                final /* synthetic */ PressGestureScopeImpl $pressScope;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass3(PressGestureScopeImpl pressGestureScopeImpl, Continuation continuation) {
                    super(2, continuation);
                    this.$pressScope = pressGestureScopeImpl;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass3(this.$pressScope, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((AnonymousClass3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    this.$pressScope.cancel();
                    return Unit.INSTANCE;
                }
            }

            /* renamed from: androidx.compose.foundation.gestures.TapGestureDetectorKt$detectTapGestures$2$1$4, reason: invalid class name */
            final class AnonymousClass4 extends SuspendLambda implements Function2 {
                final /* synthetic */ PressGestureScopeImpl $pressScope;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass4(PressGestureScopeImpl pressGestureScopeImpl, Continuation continuation) {
                    super(2, continuation);
                    this.$pressScope = pressGestureScopeImpl;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass4(this.$pressScope, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((AnonymousClass4) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    this.$pressScope.release();
                    return Unit.INSTANCE;
                }
            }

            /* renamed from: androidx.compose.foundation.gestures.TapGestureDetectorKt$detectTapGestures$2$1$5, reason: invalid class name */
            final class AnonymousClass5 extends SuspendLambda implements Function2 {
                final /* synthetic */ Job $cancelOrReleaseJob;
                final /* synthetic */ PressGestureScopeImpl $pressScope;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass5(Job job, PressGestureScopeImpl pressGestureScopeImpl, Continuation continuation) {
                    super(2, continuation);
                    this.$cancelOrReleaseJob = job;
                    this.$pressScope = pressGestureScopeImpl;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass5(this.$cancelOrReleaseJob, this.$pressScope, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((AnonymousClass5) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                /* JADX WARN: Code restructure failed: missing block: B:14:0x0032, code lost:
                
                    if (r5.reset(r4) == r0) goto L15;
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
                        Job job = this.$cancelOrReleaseJob;
                        this.label = 1;
                        if (job.join(this) != coroutineSingletons) {
                        }
                        return coroutineSingletons;
                    }
                    if (i != 1) {
                        if (i != 2) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj);
                        return Unit.INSTANCE;
                    }
                    ResultKt.throwOnFailure(obj);
                    PressGestureScopeImpl pressGestureScopeImpl = this.$pressScope;
                    this.label = 2;
                }
            }

            /* renamed from: androidx.compose.foundation.gestures.TapGestureDetectorKt$detectTapGestures$2$1$6, reason: invalid class name */
            final class AnonymousClass6 extends SuspendLambda implements Function2 {
                final /* synthetic */ Function3 $onPress;
                final /* synthetic */ PressGestureScopeImpl $pressScope;
                final /* synthetic */ PointerInputChange $secondDown;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass6(Function3 function3, PressGestureScopeImpl pressGestureScopeImpl, PointerInputChange pointerInputChange, Continuation continuation) {
                    super(2, continuation);
                    this.$onPress = function3;
                    this.$pressScope = pressGestureScopeImpl;
                    this.$secondDown = pointerInputChange;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass6(this.$onPress, this.$pressScope, this.$secondDown, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((AnonymousClass6) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        Function3 function3 = this.$onPress;
                        PressGestureScopeImpl pressGestureScopeImpl = this.$pressScope;
                        Offset offsetM395boximpl = Offset.m395boximpl(this.$secondDown.position);
                        this.label = 1;
                        if (function3.invoke(pressGestureScopeImpl, offsetM395boximpl, this) == coroutineSingletons) {
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

            /* renamed from: androidx.compose.foundation.gestures.TapGestureDetectorKt$detectTapGestures$2$1$7, reason: invalid class name */
            final class AnonymousClass7 extends SuspendLambda implements Function2 {
                final /* synthetic */ PressGestureScopeImpl $pressScope;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass7(PressGestureScopeImpl pressGestureScopeImpl, Continuation continuation) {
                    super(2, continuation);
                    this.$pressScope = pressGestureScopeImpl;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass7(this.$pressScope, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((AnonymousClass7) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    this.$pressScope.release();
                    return Unit.INSTANCE;
                }
            }

            /* renamed from: androidx.compose.foundation.gestures.TapGestureDetectorKt$detectTapGestures$2$1$8, reason: invalid class name */
            final class AnonymousClass8 extends SuspendLambda implements Function2 {
                final /* synthetic */ PressGestureScopeImpl $pressScope;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass8(PressGestureScopeImpl pressGestureScopeImpl, Continuation continuation) {
                    super(2, continuation);
                    this.$pressScope = pressGestureScopeImpl;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass8(this.$pressScope, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((AnonymousClass8) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    this.$pressScope.cancel();
                    return Unit.INSTANCE;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(CoroutineScope coroutineScope, Function3 function3, Function1 function1, Function1 function12, Function1 function13, PressGestureScopeImpl pressGestureScopeImpl, Continuation continuation) {
                super(2, continuation);
                this.$$this$coroutineScope = coroutineScope;
                this.$onPress = function3;
                this.$onLongPress = function1;
                this.$onDoubleTap = function12;
                this.$onTap = function13;
                this.$pressScope = pressGestureScopeImpl;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$$this$coroutineScope, this.$onPress, this.$onLongPress, this.$onDoubleTap, this.$onTap, this.$pressScope, continuation);
                anonymousClass1.L$0 = obj;
                return anonymousClass1;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass1) create((AwaitPointerEventScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            /* JADX WARN: Removed duplicated region for block: B:103:0x0249  */
            /* JADX WARN: Removed duplicated region for block: B:104:0x0264  */
            /* JADX WARN: Removed duplicated region for block: B:21:0x00a3  */
            /* JADX WARN: Removed duplicated region for block: B:22:0x00a6  */
            /* JADX WARN: Removed duplicated region for block: B:25:0x00b9  */
            /* JADX WARN: Removed duplicated region for block: B:28:0x00c9  */
            /* JADX WARN: Removed duplicated region for block: B:33:0x00de  */
            /* JADX WARN: Removed duplicated region for block: B:39:0x00fd  */
            /* JADX WARN: Removed duplicated region for block: B:45:0x0129  */
            /* JADX WARN: Removed duplicated region for block: B:53:0x013a  */
            /* JADX WARN: Removed duplicated region for block: B:54:0x0148  */
            /* JADX WARN: Removed duplicated region for block: B:56:0x015a  */
            /* JADX WARN: Removed duplicated region for block: B:67:0x0193  */
            /* JADX WARN: Removed duplicated region for block: B:70:0x01a2  */
            /* JADX WARN: Removed duplicated region for block: B:90:0x0209  */
            /* JADX WARN: Removed duplicated region for block: B:96:0x0237  */
            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object invokeSuspend(Object obj) {
                AwaitPointerEventScope awaitPointerEventScope;
                Job jobLaunch$default;
                Function3 function3;
                PointerInputChange pointerInputChange;
                AwaitPointerEventScope awaitPointerEventScope2;
                PointerInputChange pointerInputChange2;
                Job jobLaunchAwaitingReset$default;
                PointerInputChange pointerInputChange3;
                AwaitPointerEventScope awaitPointerEventScope3;
                LongPressResult longPressResult;
                Job job;
                PointerInputChange pointerInputChange4;
                Job jobLaunch$default2;
                PointerInputChange pointerInputChange5;
                PointerInputChange pointerInputChange6;
                PointerInputChange pointerInputChange7;
                PointerInputChange pointerInputChange8;
                LongPressResult longPressResult2;
                Job job2;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                switch (this.label) {
                    case 0:
                        ResultKt.throwOnFailure(obj);
                        AwaitPointerEventScope awaitPointerEventScope4 = (AwaitPointerEventScope) this.L$0;
                        this.L$0 = awaitPointerEventScope4;
                        this.label = 1;
                        Object objAwaitFirstDown$default = TapGestureDetectorKt.awaitFirstDown$default(awaitPointerEventScope4, null, this, 3);
                        if (objAwaitFirstDown$default != coroutineSingletons) {
                            awaitPointerEventScope = awaitPointerEventScope4;
                            obj = objAwaitFirstDown$default;
                            PointerInputChange pointerInputChange9 = (PointerInputChange) obj;
                            pointerInputChange9.consume();
                            CoroutineScope coroutineScope = this.$$this$coroutineScope;
                            Function3 function32 = TapGestureDetectorKt.NoPressGesture;
                            jobLaunch$default = BuildersKt.launch$default(coroutineScope, null, !ComposeFoundationFlags.isDetectTapGesturesImmediateCoroutineDispatchEnabled ? CoroutineStart.UNDISPATCHED : CoroutineStart.DEFAULT, new TapGestureDetectorKt$detectTapGestures$2$1$resetJob$1(this.$pressScope, null), 1);
                            function3 = this.$onPress;
                            if (function3 != TapGestureDetectorKt.NoPressGesture) {
                                TapGestureDetectorKt.launchAwaitingReset$default(this.$$this$coroutineScope, jobLaunch$default, new C00111(function3, this.$pressScope, pointerInputChange9, null));
                            }
                            if (this.$onLongPress != null) {
                                this.L$0 = awaitPointerEventScope;
                                this.L$1 = jobLaunch$default;
                                this.label = 2;
                                obj = TapGestureDetectorKt.waitForUpOrCancellation(awaitPointerEventScope, PointerEventPass.Main, this);
                                if (obj != coroutineSingletons) {
                                    awaitPointerEventScope2 = awaitPointerEventScope;
                                    pointerInputChange2 = (PointerInputChange) obj;
                                    if (pointerInputChange2 == null) {
                                        jobLaunchAwaitingReset$default = TapGestureDetectorKt.launchAwaitingReset$default(this.$$this$coroutineScope, jobLaunch$default, new AnonymousClass3(this.$pressScope, null));
                                    } else {
                                        pointerInputChange2.consume();
                                        jobLaunchAwaitingReset$default = TapGestureDetectorKt.launchAwaitingReset$default(this.$$this$coroutineScope, jobLaunch$default, new AnonymousClass4(this.$pressScope, null));
                                    }
                                    if (pointerInputChange2 != null) {
                                        if (this.$onDoubleTap == null) {
                                            Function1 function1 = this.$onTap;
                                            if (function1 != null) {
                                                function1.mo781invoke(Offset.m395boximpl(pointerInputChange2.position));
                                            }
                                        } else {
                                            this.L$0 = awaitPointerEventScope2;
                                            this.L$1 = pointerInputChange2;
                                            this.L$2 = jobLaunchAwaitingReset$default;
                                            this.label = 5;
                                            Object objWithTimeoutOrNull = awaitPointerEventScope2.withTimeoutOrNull(awaitPointerEventScope2.getViewConfiguration().getDoubleTapTimeoutMillis(), new TapGestureDetectorKt$awaitSecondDown$2(pointerInputChange2, null), this);
                                            if (objWithTimeoutOrNull != coroutineSingletons) {
                                                AwaitPointerEventScope awaitPointerEventScope5 = awaitPointerEventScope2;
                                                pointerInputChange3 = pointerInputChange2;
                                                obj = objWithTimeoutOrNull;
                                                awaitPointerEventScope3 = awaitPointerEventScope5;
                                                pointerInputChange4 = (PointerInputChange) obj;
                                                if (pointerInputChange4 != null) {
                                                    Function1 function12 = this.$onTap;
                                                    if (function12 != null) {
                                                        function12.mo781invoke(Offset.m395boximpl(pointerInputChange3.position));
                                                    }
                                                } else {
                                                    CoroutineScope coroutineScope2 = this.$$this$coroutineScope;
                                                    Function3 function33 = TapGestureDetectorKt.NoPressGesture;
                                                    jobLaunch$default2 = BuildersKt.launch$default(coroutineScope2, null, ComposeFoundationFlags.isDetectTapGesturesImmediateCoroutineDispatchEnabled ? CoroutineStart.UNDISPATCHED : CoroutineStart.DEFAULT, new AnonymousClass5(jobLaunchAwaitingReset$default, this.$pressScope, null), 1);
                                                    Function3 function34 = this.$onPress;
                                                    if (function34 != TapGestureDetectorKt.NoPressGesture) {
                                                        TapGestureDetectorKt.launchAwaitingReset$default(this.$$this$coroutineScope, jobLaunch$default2, new AnonymousClass6(function34, this.$pressScope, pointerInputChange4, null));
                                                    }
                                                    if (this.$onLongPress == null) {
                                                        this.L$0 = jobLaunch$default2;
                                                        this.L$1 = pointerInputChange3;
                                                        this.L$2 = null;
                                                        this.label = 6;
                                                        obj = TapGestureDetectorKt.waitForUpOrCancellation(awaitPointerEventScope3, PointerEventPass.Main, this);
                                                        if (obj != coroutineSingletons) {
                                                            pointerInputChange7 = pointerInputChange3;
                                                            pointerInputChange8 = (PointerInputChange) obj;
                                                            if (pointerInputChange8 != null) {
                                                                pointerInputChange8.consume();
                                                                TapGestureDetectorKt.launchAwaitingReset$default(this.$$this$coroutineScope, jobLaunch$default2, new AnonymousClass7(this.$pressScope, null));
                                                                this.$onDoubleTap.mo781invoke(Offset.m395boximpl(pointerInputChange8.position));
                                                            } else {
                                                                TapGestureDetectorKt.launchAwaitingReset$default(this.$$this$coroutineScope, jobLaunch$default2, new AnonymousClass8(this.$pressScope, null));
                                                                Function1 function13 = this.$onTap;
                                                                if (function13 != null) {
                                                                    function13.mo781invoke(Offset.m395boximpl(pointerInputChange7.position));
                                                                }
                                                            }
                                                        }
                                                    } else {
                                                        this.L$0 = awaitPointerEventScope3;
                                                        this.L$1 = jobLaunch$default2;
                                                        this.L$2 = pointerInputChange3;
                                                        this.L$3 = pointerInputChange4;
                                                        this.label = 7;
                                                        Object objWaitForLongPress = TapGestureDetectorKt.waitForLongPress(awaitPointerEventScope3, PointerEventPass.Main, this);
                                                        if (objWaitForLongPress != coroutineSingletons) {
                                                            pointerInputChange5 = pointerInputChange3;
                                                            pointerInputChange6 = pointerInputChange4;
                                                            obj = objWaitForLongPress;
                                                            PointerInputChange pointerInputChange10 = pointerInputChange5;
                                                            longPressResult2 = (LongPressResult) obj;
                                                            if (Intrinsics.areEqual(longPressResult2, LongPressResult.Success.INSTANCE)) {
                                                                if (longPressResult2 instanceof LongPressResult.Released) {
                                                                    pointerInputChange8 = ((LongPressResult.Released) longPressResult2).finalUpChange;
                                                                    pointerInputChange7 = pointerInputChange10;
                                                                } else {
                                                                    if (!(longPressResult2 instanceof LongPressResult.Canceled)) {
                                                                        throw new NoWhenBranchMatchedException();
                                                                    }
                                                                    pointerInputChange7 = pointerInputChange10;
                                                                    pointerInputChange8 = null;
                                                                }
                                                                if (pointerInputChange8 != null) {
                                                                }
                                                            } else {
                                                                this.$onLongPress.mo781invoke(Offset.m395boximpl(pointerInputChange6.position));
                                                                this.L$0 = jobLaunch$default2;
                                                                this.L$1 = null;
                                                                this.L$2 = null;
                                                                this.L$3 = null;
                                                                this.label = 8;
                                                                if (TapGestureDetectorKt.access$consumeUntilUp(awaitPointerEventScope3, this) != coroutineSingletons) {
                                                                    job2 = jobLaunch$default2;
                                                                    TapGestureDetectorKt.launchAwaitingReset$default(this.$$this$coroutineScope, job2, new TapGestureDetectorKt$detectTapGestures$2$1$secondUp$1(this.$pressScope, null));
                                                                    return Unit.INSTANCE;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    return Unit.INSTANCE;
                                }
                            } else {
                                this.L$0 = awaitPointerEventScope;
                                this.L$1 = pointerInputChange9;
                                this.L$2 = jobLaunch$default;
                                this.label = 3;
                                Object objWaitForLongPress2 = TapGestureDetectorKt.waitForLongPress(awaitPointerEventScope, PointerEventPass.Main, this);
                                if (objWaitForLongPress2 != coroutineSingletons) {
                                    pointerInputChange = pointerInputChange9;
                                    obj = objWaitForLongPress2;
                                    longPressResult = (LongPressResult) obj;
                                    if (Intrinsics.areEqual(longPressResult, LongPressResult.Success.INSTANCE)) {
                                        if (longPressResult instanceof LongPressResult.Released) {
                                            pointerInputChange2 = ((LongPressResult.Released) longPressResult).finalUpChange;
                                        } else {
                                            if (!(longPressResult instanceof LongPressResult.Canceled)) {
                                                throw new NoWhenBranchMatchedException();
                                            }
                                            pointerInputChange2 = null;
                                        }
                                        awaitPointerEventScope2 = awaitPointerEventScope;
                                        if (pointerInputChange2 == null) {
                                        }
                                        if (pointerInputChange2 != null) {
                                        }
                                        return Unit.INSTANCE;
                                    }
                                    this.$onLongPress.mo781invoke(Offset.m395boximpl(pointerInputChange.position));
                                    this.L$0 = jobLaunch$default;
                                    this.L$1 = null;
                                    this.L$2 = null;
                                    this.label = 4;
                                    if (TapGestureDetectorKt.access$consumeUntilUp(awaitPointerEventScope, this) != coroutineSingletons) {
                                        job = jobLaunch$default;
                                        TapGestureDetectorKt.launchAwaitingReset$default(this.$$this$coroutineScope, job, new C00122(this.$pressScope, null));
                                        return Unit.INSTANCE;
                                    }
                                }
                            }
                        }
                        return coroutineSingletons;
                    case 1:
                        AwaitPointerEventScope awaitPointerEventScope6 = (AwaitPointerEventScope) this.L$0;
                        ResultKt.throwOnFailure(obj);
                        awaitPointerEventScope = awaitPointerEventScope6;
                        PointerInputChange pointerInputChange92 = (PointerInputChange) obj;
                        pointerInputChange92.consume();
                        CoroutineScope coroutineScope3 = this.$$this$coroutineScope;
                        Function3 function322 = TapGestureDetectorKt.NoPressGesture;
                        jobLaunch$default = BuildersKt.launch$default(coroutineScope3, null, !ComposeFoundationFlags.isDetectTapGesturesImmediateCoroutineDispatchEnabled ? CoroutineStart.UNDISPATCHED : CoroutineStart.DEFAULT, new TapGestureDetectorKt$detectTapGestures$2$1$resetJob$1(this.$pressScope, null), 1);
                        function3 = this.$onPress;
                        if (function3 != TapGestureDetectorKt.NoPressGesture) {
                        }
                        if (this.$onLongPress != null) {
                        }
                        return coroutineSingletons;
                    case 2:
                        jobLaunch$default = (Job) this.L$1;
                        awaitPointerEventScope2 = (AwaitPointerEventScope) this.L$0;
                        ResultKt.throwOnFailure(obj);
                        pointerInputChange2 = (PointerInputChange) obj;
                        if (pointerInputChange2 == null) {
                        }
                        if (pointerInputChange2 != null) {
                        }
                        return Unit.INSTANCE;
                    case 3:
                        jobLaunch$default = (Job) this.L$2;
                        pointerInputChange = (PointerInputChange) this.L$1;
                        awaitPointerEventScope = (AwaitPointerEventScope) this.L$0;
                        ResultKt.throwOnFailure(obj);
                        longPressResult = (LongPressResult) obj;
                        if (Intrinsics.areEqual(longPressResult, LongPressResult.Success.INSTANCE)) {
                        }
                        break;
                    case 4:
                        job = (Job) this.L$0;
                        ResultKt.throwOnFailure(obj);
                        TapGestureDetectorKt.launchAwaitingReset$default(this.$$this$coroutineScope, job, new C00122(this.$pressScope, null));
                        return Unit.INSTANCE;
                    case 5:
                        jobLaunchAwaitingReset$default = (Job) this.L$2;
                        pointerInputChange3 = (PointerInputChange) this.L$1;
                        awaitPointerEventScope3 = (AwaitPointerEventScope) this.L$0;
                        ResultKt.throwOnFailure(obj);
                        pointerInputChange4 = (PointerInputChange) obj;
                        if (pointerInputChange4 != null) {
                        }
                        break;
                    case 6:
                        pointerInputChange7 = (PointerInputChange) this.L$1;
                        jobLaunch$default2 = (Job) this.L$0;
                        ResultKt.throwOnFailure(obj);
                        pointerInputChange8 = (PointerInputChange) obj;
                        if (pointerInputChange8 != null) {
                        }
                        return Unit.INSTANCE;
                    case 7:
                        PointerInputChange pointerInputChange11 = (PointerInputChange) this.L$3;
                        PointerInputChange pointerInputChange12 = (PointerInputChange) this.L$2;
                        Job job3 = (Job) this.L$1;
                        awaitPointerEventScope3 = (AwaitPointerEventScope) this.L$0;
                        ResultKt.throwOnFailure(obj);
                        pointerInputChange5 = pointerInputChange12;
                        pointerInputChange6 = pointerInputChange11;
                        jobLaunch$default2 = job3;
                        PointerInputChange pointerInputChange102 = pointerInputChange5;
                        longPressResult2 = (LongPressResult) obj;
                        if (Intrinsics.areEqual(longPressResult2, LongPressResult.Success.INSTANCE)) {
                        }
                        break;
                    case 8:
                        job2 = (Job) this.L$0;
                        ResultKt.throwOnFailure(obj);
                        TapGestureDetectorKt.launchAwaitingReset$default(this.$$this$coroutineScope, job2, new TapGestureDetectorKt$detectTapGestures$2$1$secondUp$1(this.$pressScope, null));
                        return Unit.INSTANCE;
                    default:
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C07012(PointerInputScope pointerInputScope, Function3 function3, Function1 function1, Function1 function12, Function1 function13, Continuation continuation) {
            super(2, continuation);
            this.$this_detectTapGestures = pointerInputScope;
            this.$onPress = function3;
            this.$onLongPress = function1;
            this.$onDoubleTap = function12;
            this.$onTap = function13;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C07012 c07012 = new C07012(this.$this_detectTapGestures, this.$onPress, this.$onLongPress, this.$onDoubleTap, this.$onTap, continuation);
            c07012.L$0 = obj;
            return c07012;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C07012) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                PressGestureScopeImpl pressGestureScopeImpl = new PressGestureScopeImpl(this.$this_detectTapGestures);
                PointerInputScope pointerInputScope = this.$this_detectTapGestures;
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(coroutineScope, this.$onPress, this.$onLongPress, this.$onDoubleTap, this.$onTap, pressGestureScopeImpl, null);
                this.label = 1;
                if (ForEachGestureKt.awaitEachGesture(pointerInputScope, anonymousClass1, this) == coroutineSingletons) {
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

    /* renamed from: androidx.compose.foundation.gestures.TapGestureDetectorKt$waitForLongPress$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
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
            return TapGestureDetectorKt.waitForLongPress(null, null, this);
        }
    }

    /* renamed from: androidx.compose.foundation.gestures.TapGestureDetectorKt$waitForLongPress$2, reason: invalid class name and case insensitive filesystem */
    final class C07022 extends RestrictedSuspendLambda implements Function2 {
        final /* synthetic */ PointerEventPass $pass;
        final /* synthetic */ Ref$ObjectRef<LongPressResult> $result;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C07022(PointerEventPass pointerEventPass, Ref$ObjectRef<LongPressResult> ref$ObjectRef, Continuation continuation) {
            super(2, continuation);
            this.$pass = pointerEventPass;
            this.$result = ref$ObjectRef;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C07022 c07022 = new C07022(this.$pass, this.$result, continuation);
            c07022.L$0 = obj;
            return c07022;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C07022) create((AwaitPointerEventScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:19:0x0058, code lost:
        
            r5 = r14.getMotionEvent$ui_release();
         */
        /* JADX WARN: Code restructure failed: missing block: B:20:0x005c, code lost:
        
            if (r5 == null) goto L22;
         */
        /* JADX WARN: Code restructure failed: missing block: B:21:0x005e, code lost:
        
            r5 = r5.getClassification();
         */
        /* JADX WARN: Code restructure failed: missing block: B:22:0x0063, code lost:
        
            r5 = 0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:23:0x0064, code lost:
        
            if (r5 != 2) goto L25;
         */
        /* JADX WARN: Code restructure failed: missing block: B:24:0x0066, code lost:
        
            r5 = true;
         */
        /* JADX WARN: Code restructure failed: missing block: B:25:0x0068, code lost:
        
            r5 = false;
         */
        /* JADX WARN: Code restructure failed: missing block: B:26:0x0069, code lost:
        
            if (r5 == false) goto L28;
         */
        /* JADX WARN: Code restructure failed: missing block: B:27:0x006b, code lost:
        
            r13.$result.element = androidx.compose.foundation.gestures.LongPressResult.Success.INSTANCE;
         */
        /* JADX WARN: Code restructure failed: missing block: B:28:0x0073, code lost:
        
            r14 = r14.changes;
            r5 = r14.size();
            r6 = 0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:29:0x007d, code lost:
        
            if (r6 >= r5) goto L56;
         */
        /* JADX WARN: Code restructure failed: missing block: B:30:0x007f, code lost:
        
            r7 = (androidx.compose.ui.input.pointer.PointerInputChange) r14.get(r6);
         */
        /* JADX WARN: Code restructure failed: missing block: B:31:0x0089, code lost:
        
            if (r7.isConsumed() != false) goto L57;
         */
        /* JADX WARN: Code restructure failed: missing block: B:33:0x0097, code lost:
        
            if (androidx.compose.ui.input.pointer.PointerEventKt.m591isOutOfBoundsjwHxaWs(r7, r1.mo587getSizeYbymL2g(), r1.mo586getExtendedTouchPaddingNHjbRc()) == false) goto L35;
         */
        /* JADX WARN: Code restructure failed: missing block: B:35:0x009a, code lost:
        
            r6 = r6 + 1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:36:0x009d, code lost:
        
            r13.$result.element = androidx.compose.foundation.gestures.LongPressResult.Canceled.INSTANCE;
         */
        /* JADX WARN: Code restructure failed: missing block: B:37:0x00a4, code lost:
        
            r14 = androidx.compose.ui.input.pointer.PointerEventPass.Final;
            r13.L$0 = r1;
            r13.label = 2;
            r14 = r1.awaitPointerEvent(r14, r13);
         */
        /* JADX WARN: Code restructure failed: missing block: B:38:0x00ae, code lost:
        
            if (r14 != r0) goto L40;
         */
        /* JADX WARN: Code restructure failed: missing block: B:44:0x00cb, code lost:
        
            r13.$result.element = androidx.compose.foundation.gestures.LongPressResult.Canceled.INSTANCE;
         */
        /* JADX WARN: Code restructure failed: missing block: B:50:0x00ef, code lost:
        
            return kotlin.Unit.INSTANCE;
         */
        /* JADX WARN: Removed duplicated region for block: B:14:0x003b  */
        /* JADX WARN: Removed duplicated region for block: B:17:0x004c  */
        /* JADX WARN: Removed duplicated region for block: B:53:0x00dc A[SYNTHETIC] */
        /* JADX WARN: Type inference failed for: r0v1, types: [T, androidx.compose.foundation.gestures.LongPressResult$Released] */
        /* JADX WARN: Type inference failed for: r14v11, types: [T, androidx.compose.foundation.gestures.LongPressResult$Canceled] */
        /* JADX WARN: Type inference failed for: r14v12, types: [T, androidx.compose.foundation.gestures.LongPressResult$Success] */
        /* JADX WARN: Type inference failed for: r14v19, types: [T, androidx.compose.foundation.gestures.LongPressResult$Canceled] */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:38:0x00ae -> B:40:0x00b1). Please report as a decompilation issue!!! */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            AwaitPointerEventScope awaitPointerEventScope;
            AwaitPointerEventScope awaitPointerEventScope2;
            int size;
            int i;
            Object objAwaitPointerEvent;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                awaitPointerEventScope = (AwaitPointerEventScope) this.L$0;
                PointerEventPass pointerEventPass = this.$pass;
                this.L$0 = awaitPointerEventScope;
                this.label = 1;
                objAwaitPointerEvent = awaitPointerEventScope.awaitPointerEvent(pointerEventPass, this);
                if (objAwaitPointerEvent != coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            if (i2 == 1) {
                awaitPointerEventScope2 = (AwaitPointerEventScope) this.L$0;
                ResultKt.throwOnFailure(obj);
                PointerEvent pointerEvent = (PointerEvent) obj;
                List list = pointerEvent.changes;
                size = list.size();
                i = 0;
                while (true) {
                    if (i >= size) {
                    }
                    i++;
                }
                return coroutineSingletons;
            }
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            awaitPointerEventScope2 = (AwaitPointerEventScope) this.L$0;
            ResultKt.throwOnFailure(obj);
            List list2 = ((PointerEvent) obj).changes;
            int size2 = list2.size();
            for (int i3 = 0; i3 < size2; i3++) {
                if (((PointerInputChange) list2.get(i3)).isConsumed()) {
                    break;
                }
            }
            awaitPointerEventScope = awaitPointerEventScope2;
            PointerEventPass pointerEventPass2 = this.$pass;
            this.L$0 = awaitPointerEventScope;
            this.label = 1;
            objAwaitPointerEvent = awaitPointerEventScope.awaitPointerEvent(pointerEventPass2, this);
            if (objAwaitPointerEvent != coroutineSingletons) {
                awaitPointerEventScope2 = awaitPointerEventScope;
                obj = objAwaitPointerEvent;
                PointerEvent pointerEvent2 = (PointerEvent) obj;
                List list3 = pointerEvent2.changes;
                size = list3.size();
                i = 0;
                while (true) {
                    if (i >= size) {
                        this.$result.element = new LongPressResult.Released((PointerInputChange) pointerEvent2.changes.get(0));
                        break;
                    }
                    if (!PointerEventKt.changedToUp((PointerInputChange) list3.get(i))) {
                        break;
                    }
                    i++;
                }
            }
            return coroutineSingletons;
        }
    }

    /* renamed from: androidx.compose.foundation.gestures.TapGestureDetectorKt$waitForUpOrCancellation$2, reason: invalid class name and case insensitive filesystem */
    final class C07032 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public C07032(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return TapGestureDetectorKt.waitForUpOrCancellation(null, null, this);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0040 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0050 A[LOOP:0: B:19:0x004e->B:20:0x0050, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:16:0x003e -> B:18:0x0041). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$consumeUntilUp(AwaitPointerEventScope awaitPointerEventScope, BaseContinuationImpl baseContinuationImpl) {
        TapGestureDetectorKt$consumeUntilUp$1 tapGestureDetectorKt$consumeUntilUp$1;
        int size;
        int i;
        int i2;
        int size2;
        if (baseContinuationImpl instanceof TapGestureDetectorKt$consumeUntilUp$1) {
            tapGestureDetectorKt$consumeUntilUp$1 = (TapGestureDetectorKt$consumeUntilUp$1) baseContinuationImpl;
            int i3 = tapGestureDetectorKt$consumeUntilUp$1.label;
            if ((i3 & Integer.MIN_VALUE) != 0) {
                tapGestureDetectorKt$consumeUntilUp$1.label = i3 - Integer.MIN_VALUE;
            } else {
                tapGestureDetectorKt$consumeUntilUp$1 = new TapGestureDetectorKt$consumeUntilUp$1(baseContinuationImpl);
            }
        }
        Object objAwaitPointerEvent = tapGestureDetectorKt$consumeUntilUp$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i4 = tapGestureDetectorKt$consumeUntilUp$1.label;
        if (i4 == 0) {
            ResultKt.throwOnFailure(objAwaitPointerEvent);
            tapGestureDetectorKt$consumeUntilUp$1.L$0 = awaitPointerEventScope;
            tapGestureDetectorKt$consumeUntilUp$1.label = 1;
            objAwaitPointerEvent = ((SuspendingPointerInputModifierNodeImpl.PointerEventHandlerCoroutine) awaitPointerEventScope).awaitPointerEvent(PointerEventPass.Main, tapGestureDetectorKt$consumeUntilUp$1);
            if (objAwaitPointerEvent == coroutineSingletons) {
            }
            PointerEvent pointerEvent = (PointerEvent) objAwaitPointerEvent;
            List list = pointerEvent.changes;
            size = list.size();
            i = 0;
            while (i2 < size) {
            }
            List list2 = pointerEvent.changes;
            size2 = list2.size();
            while (i < size2) {
            }
            return Unit.INSTANCE;
        }
        if (i4 != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        awaitPointerEventScope = (AwaitPointerEventScope) tapGestureDetectorKt$consumeUntilUp$1.L$0;
        ResultKt.throwOnFailure(objAwaitPointerEvent);
        PointerEvent pointerEvent2 = (PointerEvent) objAwaitPointerEvent;
        List list3 = pointerEvent2.changes;
        size = list3.size();
        i = 0;
        for (i2 = 0; i2 < size; i2++) {
            ((PointerInputChange) list3.get(i2)).consume();
        }
        List list22 = pointerEvent2.changes;
        size2 = list22.size();
        while (i < size2) {
            if (((PointerInputChange) list22.get(i)).pressed) {
                tapGestureDetectorKt$consumeUntilUp$1.L$0 = awaitPointerEventScope;
                tapGestureDetectorKt$consumeUntilUp$1.label = 1;
                objAwaitPointerEvent = ((SuspendingPointerInputModifierNodeImpl.PointerEventHandlerCoroutine) awaitPointerEventScope).awaitPointerEvent(PointerEventPass.Main, tapGestureDetectorKt$consumeUntilUp$1);
                if (objAwaitPointerEvent == coroutineSingletons) {
                    return coroutineSingletons;
                }
                PointerEvent pointerEvent22 = (PointerEvent) objAwaitPointerEvent;
                List list32 = pointerEvent22.changes;
                size = list32.size();
                i = 0;
                while (i2 < size) {
                }
                List list222 = pointerEvent22.changes;
                size2 = list222.size();
                while (i < size2) {
                }
            } else {
                i++;
            }
        }
        return Unit.INSTANCE;
    }

    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    /* JADX WARN: Removed duplicated region for block: B:17:0x004e A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x005e  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0079  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0076 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:16:0x004c -> B:18:0x004f). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final java.lang.Object awaitFirstDown(androidx.compose.ui.input.pointer.AwaitPointerEventScope r9, boolean r10, androidx.compose.ui.input.pointer.PointerEventPass r11, kotlin.coroutines.Continuation r12) {
        /*
            boolean r0 = r12 instanceof androidx.compose.foundation.gestures.TapGestureDetectorKt.AnonymousClass2
            if (r0 == 0) goto L13
            r0 = r12
            androidx.compose.foundation.gestures.TapGestureDetectorKt$awaitFirstDown$2 r0 = (androidx.compose.foundation.gestures.TapGestureDetectorKt.AnonymousClass2) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.compose.foundation.gestures.TapGestureDetectorKt$awaitFirstDown$2 r0 = new androidx.compose.foundation.gestures.TapGestureDetectorKt$awaitFirstDown$2
            r0.<init>(r12)
        L18:
            java.lang.Object r12 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L3d
            if (r2 != r3) goto L35
            boolean r9 = r0.Z$0
            java.lang.Object r10 = r0.L$1
            androidx.compose.ui.input.pointer.PointerEventPass r10 = (androidx.compose.ui.input.pointer.PointerEventPass) r10
            java.lang.Object r11 = r0.L$0
            androidx.compose.ui.input.pointer.AwaitPointerEventScope r11 = (androidx.compose.ui.input.pointer.AwaitPointerEventScope) r11
            kotlin.ResultKt.throwOnFailure(r12)
            r8 = r10
            r10 = r9
            r9 = r11
            r11 = r8
            goto L4f
        L35:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L3d:
            kotlin.ResultKt.throwOnFailure(r12)
        L40:
            r0.L$0 = r9
            r0.L$1 = r11
            r0.Z$0 = r10
            r0.label = r3
            java.lang.Object r12 = r9.awaitPointerEvent(r11, r0)
            if (r12 != r1) goto L4f
            return r1
        L4f:
            androidx.compose.ui.input.pointer.PointerEvent r12 = (androidx.compose.ui.input.pointer.PointerEvent) r12
            java.util.List r2 = r12.changes
            r4 = r2
            java.util.Collection r4 = (java.util.Collection) r4
            int r4 = r4.size()
            r5 = 0
            r6 = r5
        L5c:
            if (r6 >= r4) goto L76
            java.lang.Object r7 = r2.get(r6)
            androidx.compose.ui.input.pointer.PointerInputChange r7 = (androidx.compose.ui.input.pointer.PointerInputChange) r7
            if (r10 == 0) goto L6b
            boolean r7 = androidx.compose.ui.input.pointer.PointerEventKt.changedToDown(r7)
            goto L6f
        L6b:
            boolean r7 = androidx.compose.ui.input.pointer.PointerEventKt.changedToDownIgnoreConsumed(r7)
        L6f:
            if (r7 != 0) goto L73
            r2 = r5
            goto L77
        L73:
            int r6 = r6 + 1
            goto L5c
        L76:
            r2 = r3
        L77:
            if (r2 == 0) goto L40
            java.util.List r9 = r12.changes
            java.lang.Object r9 = r9.get(r5)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.TapGestureDetectorKt.awaitFirstDown(androidx.compose.ui.input.pointer.AwaitPointerEventScope, boolean, androidx.compose.ui.input.pointer.PointerEventPass, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public static /* synthetic */ Object awaitFirstDown$default(AwaitPointerEventScope awaitPointerEventScope, PointerEventPass pointerEventPass, Continuation continuation, int i) {
        boolean z = (i & 1) != 0;
        if ((i & 2) != 0) {
            pointerEventPass = PointerEventPass.Main;
        }
        return awaitFirstDown(awaitPointerEventScope, z, pointerEventPass, continuation);
    }

    public static final Object detectTapAndPress(PointerInputScope pointerInputScope, Function3 function3, Function1 function1, Continuation continuation) {
        Object objCoroutineScope = CoroutineScopeKt.coroutineScope(new C07002(pointerInputScope, function3, function1, new PressGestureScopeImpl(pointerInputScope), null), continuation);
        return objCoroutineScope == CoroutineSingletons.COROUTINE_SUSPENDED ? objCoroutineScope : Unit.INSTANCE;
    }

    public static final Object detectTapGestures(PointerInputScope pointerInputScope, Function3 function3, Function1 function1, Function1 function12, Function1 function13, Continuation continuation) {
        Object objCoroutineScope = CoroutineScopeKt.coroutineScope(new C07012(pointerInputScope, function3, function12, function1, function13, null), continuation);
        return objCoroutineScope == CoroutineSingletons.COROUTINE_SUSPENDED ? objCoroutineScope : Unit.INSTANCE;
    }

    public static /* synthetic */ Object detectTapGestures$default(PointerInputScope pointerInputScope, Function1 function1, Function1 function12, Function3 function3, Function1 function13, Continuation continuation, int i) {
        Function1 function14;
        Function3 function32;
        Function1 function15;
        if ((i & 1) != 0) {
            function1 = null;
        }
        if ((i & 2) != 0) {
            function12 = null;
        }
        if ((i & 4) != 0) {
            function3 = NoPressGesture;
        }
        if ((i & 8) != 0) {
            Function1 function16 = function12;
            function14 = function1;
            function32 = function3;
            function15 = function16;
            function13 = null;
        } else {
            Function1 function17 = function12;
            function14 = function1;
            function32 = function3;
            function15 = function17;
        }
        return detectTapGestures(pointerInputScope, function32, function14, function15, function13, continuation);
    }

    public static StandaloneCoroutine launchAwaitingReset$default(CoroutineScope coroutineScope, Job job, Function2 function2) {
        return BuildersKt.launch$default(coroutineScope, null, ComposeFoundationFlags.isDetectTapGesturesImmediateCoroutineDispatchEnabled ? CoroutineStart.UNDISPATCHED : CoroutineStart.DEFAULT, new TapGestureDetectorKt$launchAwaitingReset$1(job, function2, null), 1);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Type inference failed for: r2v1, types: [T, androidx.compose.foundation.gestures.LongPressResult$Canceled] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object waitForLongPress(AwaitPointerEventScope awaitPointerEventScope, PointerEventPass pointerEventPass, BaseContinuationImpl baseContinuationImpl) {
        AnonymousClass1 anonymousClass1;
        Ref$ObjectRef ref$ObjectRef;
        if (baseContinuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) baseContinuationImpl;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(baseContinuationImpl);
            }
        }
        Object obj = anonymousClass1.result;
        Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        try {
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                Ref$ObjectRef ref$ObjectRef2 = new Ref$ObjectRef();
                ref$ObjectRef2.element = LongPressResult.Canceled.INSTANCE;
                long longPressTimeoutMillis = awaitPointerEventScope.getViewConfiguration().getLongPressTimeoutMillis();
                Function2 c07022 = new C07022(pointerEventPass, ref$ObjectRef2, null);
                anonymousClass1.L$0 = ref$ObjectRef2;
                anonymousClass1.label = 1;
                if (awaitPointerEventScope.withTimeout(longPressTimeoutMillis, c07022, anonymousClass1) == obj2) {
                    return obj2;
                }
                ref$ObjectRef = ref$ObjectRef2;
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ref$ObjectRef = (Ref$ObjectRef) anonymousClass1.L$0;
                ResultKt.throwOnFailure(obj);
            }
            return ref$ObjectRef.element;
        } catch (PointerEventTimeoutCancellationException unused) {
            return LongPressResult.Success.INSTANCE;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:35:0x00ae, code lost:
    
        if (r15 == r1) goto L36;
     */
    /* JADX WARN: Removed duplicated region for block: B:21:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:35:0x00ae -> B:13:0x0032). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object waitForUpOrCancellation(AwaitPointerEventScope awaitPointerEventScope, PointerEventPass pointerEventPass, BaseContinuationImpl baseContinuationImpl) {
        C07032 c07032;
        AwaitPointerEventScope awaitPointerEventScope2;
        PointerEventPass pointerEventPass2;
        int size;
        int i;
        if (baseContinuationImpl instanceof C07032) {
            c07032 = (C07032) baseContinuationImpl;
            int i2 = c07032.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                c07032.label = i2 - Integer.MIN_VALUE;
            } else {
                c07032 = new C07032(baseContinuationImpl);
            }
        }
        Object objAwaitPointerEvent = c07032.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i3 = c07032.label;
        if (i3 == 0) {
            ResultKt.throwOnFailure(objAwaitPointerEvent);
            c07032.L$0 = awaitPointerEventScope;
            c07032.L$1 = pointerEventPass;
            c07032.label = 1;
            objAwaitPointerEvent = awaitPointerEventScope.awaitPointerEvent(pointerEventPass, c07032);
            if (objAwaitPointerEvent != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i3 == 1) {
            pointerEventPass2 = (PointerEventPass) c07032.L$1;
            awaitPointerEventScope2 = (AwaitPointerEventScope) c07032.L$0;
            ResultKt.throwOnFailure(objAwaitPointerEvent);
            PointerEvent pointerEvent = (PointerEvent) objAwaitPointerEvent;
            List list = pointerEvent.changes;
            size = list.size();
            while (i < size) {
            }
            return pointerEvent.changes.get(0);
        }
        if (i3 != 2) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        pointerEventPass2 = (PointerEventPass) c07032.L$1;
        awaitPointerEventScope2 = (AwaitPointerEventScope) c07032.L$0;
        ResultKt.throwOnFailure(objAwaitPointerEvent);
        AwaitPointerEventScope awaitPointerEventScope3 = awaitPointerEventScope2;
        pointerEventPass = pointerEventPass2;
        awaitPointerEventScope = awaitPointerEventScope3;
        List list2 = ((PointerEvent) objAwaitPointerEvent).changes;
        int size2 = list2.size();
        for (int i4 = 0; i4 < size2; i4++) {
            if (((PointerInputChange) list2.get(i4)).isConsumed()) {
                return null;
            }
        }
        c07032.L$0 = awaitPointerEventScope;
        c07032.L$1 = pointerEventPass;
        c07032.label = 1;
        objAwaitPointerEvent = awaitPointerEventScope.awaitPointerEvent(pointerEventPass, c07032);
        if (objAwaitPointerEvent != coroutineSingletons) {
            PointerEventPass pointerEventPass3 = pointerEventPass;
            awaitPointerEventScope2 = awaitPointerEventScope;
            pointerEventPass2 = pointerEventPass3;
            PointerEvent pointerEvent2 = (PointerEvent) objAwaitPointerEvent;
            List list3 = pointerEvent2.changes;
            size = list3.size();
            for (i = 0; i < size; i++) {
                if (!PointerEventKt.changedToUp((PointerInputChange) list3.get(i))) {
                    List list4 = pointerEvent2.changes;
                    int size3 = list4.size();
                    for (int i5 = 0; i5 < size3; i5++) {
                        PointerInputChange pointerInputChange = (PointerInputChange) list4.get(i5);
                        if (pointerInputChange.isConsumed() || PointerEventKt.m591isOutOfBoundsjwHxaWs(pointerInputChange, awaitPointerEventScope2.mo587getSizeYbymL2g(), awaitPointerEventScope2.mo586getExtendedTouchPaddingNHjbRc())) {
                            return null;
                        }
                    }
                    PointerEventPass pointerEventPass4 = PointerEventPass.Final;
                    c07032.L$0 = awaitPointerEventScope2;
                    c07032.L$1 = pointerEventPass2;
                    c07032.label = 2;
                    objAwaitPointerEvent = awaitPointerEventScope2.awaitPointerEvent(pointerEventPass4, c07032);
                }
            }
            return pointerEvent2.changes.get(0);
        }
        return coroutineSingletons;
    }
}
