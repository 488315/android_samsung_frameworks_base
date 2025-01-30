package com.android.systemui.statusbar.pipeline.shared.ui.view;

import android.content.res.ColorStateList;
import android.widget.ImageView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.systemui.statusbar.StatusBarIconView;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.shared.ui.view.SingleBindableStatusBarIconView$Companion$withDefaultBinding$1", m277f = "SingleBindableStatusBarIconView.kt", m278l = {99}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
final class SingleBindableStatusBarIconView$Companion$withDefaultBinding$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ Function3 $block;
    final /* synthetic */ MutableStateFlow $decorTint;
    final /* synthetic */ MutableStateFlow $iconTint;
    final /* synthetic */ Ref$BooleanRef $isCollecting;
    final /* synthetic */ SingleBindableStatusBarIconView $view;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.shared.ui.view.SingleBindableStatusBarIconView$Companion$withDefaultBinding$1$1", m277f = "SingleBindableStatusBarIconView.kt", m278l = {102}, m279m = "invokeSuspend")
    /* renamed from: com.android.systemui.statusbar.pipeline.shared.ui.view.SingleBindableStatusBarIconView$Companion$withDefaultBinding$1$1 */
    final class C33501 extends SuspendLambda implements Function2 {
        final /* synthetic */ LifecycleOwner $$this$repeatWhenAttached;
        final /* synthetic */ MutableStateFlow $decorTint;
        final /* synthetic */ MutableStateFlow $iconTint;
        final /* synthetic */ Ref$BooleanRef $isCollecting;
        final /* synthetic */ SingleBindableStatusBarIconView $view;
        int label;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.shared.ui.view.SingleBindableStatusBarIconView$Companion$withDefaultBinding$1$1$1", m277f = "SingleBindableStatusBarIconView.kt", m278l = {143}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.statusbar.pipeline.shared.ui.view.SingleBindableStatusBarIconView$Companion$withDefaultBinding$1$1$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ MutableStateFlow $decorTint;
            final /* synthetic */ MutableStateFlow $iconTint;
            final /* synthetic */ Ref$BooleanRef $isCollecting;
            final /* synthetic */ SingleBindableStatusBarIconView $view;
            private /* synthetic */ Object L$0;
            int label;

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.shared.ui.view.SingleBindableStatusBarIconView$Companion$withDefaultBinding$1$1$1$1", m277f = "SingleBindableStatusBarIconView.kt", m278l = {131}, m279m = "invokeSuspend")
            /* renamed from: com.android.systemui.statusbar.pipeline.shared.ui.view.SingleBindableStatusBarIconView$Companion$withDefaultBinding$1$1$1$1, reason: invalid class name and collision with other inner class name */
            final class C48861 extends SuspendLambda implements Function2 {
                final /* synthetic */ MutableStateFlow $iconTint;
                final /* synthetic */ SingleBindableStatusBarIconView $view;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C48861(MutableStateFlow mutableStateFlow, SingleBindableStatusBarIconView singleBindableStatusBarIconView, Continuation<? super C48861> continuation) {
                    super(2, continuation);
                    this.$iconTint = mutableStateFlow;
                    this.$view = singleBindableStatusBarIconView;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C48861(this.$iconTint, this.$view, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C48861) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        MutableStateFlow mutableStateFlow = this.$iconTint;
                        final SingleBindableStatusBarIconView singleBindableStatusBarIconView = this.$view;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.pipeline.shared.ui.view.SingleBindableStatusBarIconView.Companion.withDefaultBinding.1.1.1.1.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                int intValue = ((Number) obj2).intValue();
                                ColorStateList valueOf = ColorStateList.valueOf(intValue);
                                SingleBindableStatusBarIconView singleBindableStatusBarIconView2 = SingleBindableStatusBarIconView.this;
                                ImageView imageView = singleBindableStatusBarIconView2.iconView;
                                if (imageView == null) {
                                    imageView = null;
                                }
                                imageView.setImageTintList(valueOf);
                                StatusBarIconView statusBarIconView = singleBindableStatusBarIconView2.dotView;
                                (statusBarIconView != null ? statusBarIconView : null).setDecorColor(intValue);
                                return Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        if (((StateFlowImpl) mutableStateFlow).collect(flowCollector, this) == coroutineSingletons) {
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

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.shared.ui.view.SingleBindableStatusBarIconView$Companion$withDefaultBinding$1$1$1$2", m277f = "SingleBindableStatusBarIconView.kt", m278l = {139}, m279m = "invokeSuspend")
            /* renamed from: com.android.systemui.statusbar.pipeline.shared.ui.view.SingleBindableStatusBarIconView$Companion$withDefaultBinding$1$1$1$2, reason: invalid class name */
            final class AnonymousClass2 extends SuspendLambda implements Function2 {
                final /* synthetic */ MutableStateFlow $decorTint;
                final /* synthetic */ SingleBindableStatusBarIconView $view;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass2(MutableStateFlow mutableStateFlow, SingleBindableStatusBarIconView singleBindableStatusBarIconView, Continuation<? super AnonymousClass2> continuation) {
                    super(2, continuation);
                    this.$decorTint = mutableStateFlow;
                    this.$view = singleBindableStatusBarIconView;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass2(this.$decorTint, this.$view, continuation);
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
                        MutableStateFlow mutableStateFlow = this.$decorTint;
                        final SingleBindableStatusBarIconView singleBindableStatusBarIconView = this.$view;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.pipeline.shared.ui.view.SingleBindableStatusBarIconView.Companion.withDefaultBinding.1.1.1.2.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                int intValue = ((Number) obj2).intValue();
                                StatusBarIconView statusBarIconView = SingleBindableStatusBarIconView.this.dotView;
                                if (statusBarIconView == null) {
                                    statusBarIconView = null;
                                }
                                statusBarIconView.setDecorColor(intValue);
                                return Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        if (((StateFlowImpl) mutableStateFlow).collect(flowCollector, this) == coroutineSingletons) {
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

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(Ref$BooleanRef ref$BooleanRef, MutableStateFlow mutableStateFlow, SingleBindableStatusBarIconView singleBindableStatusBarIconView, MutableStateFlow mutableStateFlow2, Continuation<? super AnonymousClass1> continuation) {
                super(2, continuation);
                this.$isCollecting = ref$BooleanRef;
                this.$iconTint = mutableStateFlow;
                this.$view = singleBindableStatusBarIconView;
                this.$decorTint = mutableStateFlow2;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$isCollecting, this.$iconTint, this.$view, this.$decorTint, continuation);
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
                try {
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                        BuildersKt.launch$default(coroutineScope, null, null, new C48861(this.$iconTint, this.$view, null), 3);
                        BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(this.$decorTint, this.$view, null), 3);
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
                } catch (Throwable th) {
                    this.$isCollecting.element = false;
                    throw th;
                }
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C33501(LifecycleOwner lifecycleOwner, Ref$BooleanRef ref$BooleanRef, MutableStateFlow mutableStateFlow, SingleBindableStatusBarIconView singleBindableStatusBarIconView, MutableStateFlow mutableStateFlow2, Continuation<? super C33501> continuation) {
            super(2, continuation);
            this.$$this$repeatWhenAttached = lifecycleOwner;
            this.$isCollecting = ref$BooleanRef;
            this.$iconTint = mutableStateFlow;
            this.$view = singleBindableStatusBarIconView;
            this.$decorTint = mutableStateFlow2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new C33501(this.$$this$repeatWhenAttached, this.$isCollecting, this.$iconTint, this.$view, this.$decorTint, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C33501) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                LifecycleOwner lifecycleOwner = this.$$this$repeatWhenAttached;
                Lifecycle.State state = Lifecycle.State.STARTED;
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$isCollecting, this.$iconTint, this.$view, this.$decorTint, null);
                this.label = 1;
                if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, anonymousClass1, this) == coroutineSingletons) {
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
    public SingleBindableStatusBarIconView$Companion$withDefaultBinding$1(Function3 function3, SingleBindableStatusBarIconView singleBindableStatusBarIconView, Ref$BooleanRef ref$BooleanRef, MutableStateFlow mutableStateFlow, MutableStateFlow mutableStateFlow2, Continuation<? super SingleBindableStatusBarIconView$Companion$withDefaultBinding$1> continuation) {
        super(3, continuation);
        this.$block = function3;
        this.$view = singleBindableStatusBarIconView;
        this.$isCollecting = ref$BooleanRef;
        this.$iconTint = mutableStateFlow;
        this.$decorTint = mutableStateFlow2;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        SingleBindableStatusBarIconView$Companion$withDefaultBinding$1 singleBindableStatusBarIconView$Companion$withDefaultBinding$1 = new SingleBindableStatusBarIconView$Companion$withDefaultBinding$1(this.$block, this.$view, this.$isCollecting, this.$iconTint, this.$decorTint, (Continuation) obj3);
        singleBindableStatusBarIconView$Companion$withDefaultBinding$1.L$0 = (LifecycleOwner) obj;
        return singleBindableStatusBarIconView$Companion$withDefaultBinding$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        LifecycleOwner lifecycleOwner;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner2 = (LifecycleOwner) this.L$0;
            Function3 function3 = this.$block;
            SingleBindableStatusBarIconView singleBindableStatusBarIconView = this.$view;
            this.L$0 = lifecycleOwner2;
            this.label = 1;
            if (function3.invoke(lifecycleOwner2, singleBindableStatusBarIconView, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
            lifecycleOwner = lifecycleOwner2;
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            LifecycleOwner lifecycleOwner3 = (LifecycleOwner) this.L$0;
            ResultKt.throwOnFailure(obj);
            lifecycleOwner = lifecycleOwner3;
        }
        BuildersKt.launch$default(LifecycleOwnerKt.getLifecycleScope(lifecycleOwner), null, null, new C33501(lifecycleOwner, this.$isCollecting, this.$iconTint, this.$view, this.$decorTint, null), 3);
        return Unit.INSTANCE;
    }
}
