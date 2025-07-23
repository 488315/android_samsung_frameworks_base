package com.android.systemui.settings.multisim.ui.binder;

import android.util.Log;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleKt;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.systemui.qs.bar.MultiSIMPreferredSlotBar;
import com.android.systemui.settings.multisim.ui.view.MultiSIMPreferredSlotView;
import com.android.systemui.settings.multisim.ui.viewmodel.Bar;
import com.android.systemui.settings.multisim.ui.viewmodel.Button;
import com.android.systemui.settings.multisim.ui.viewmodel.MultiSIMViewModel;
import com.android.systemui.settings.multisim.ui.viewmodel.MultiSIMViewModelImpl;
import com.android.systemui.settings.multisim.ui.viewmodel.SlotsView;
import java.util.ArrayList;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class MultiSIMPreferredBarViewBinder$bindClone$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ Bar $bar;
    final /* synthetic */ SlotsView $view;
    final /* synthetic */ MultiSIMViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.settings.multisim.ui.binder.MultiSIMPreferredBarViewBinder$bindClone$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ LifecycleOwner $$this$repeatWhenAttached;
        final /* synthetic */ Bar $bar;
        final /* synthetic */ MultiSIMViewModel $viewModel;
        int label;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.settings.multisim.ui.binder.MultiSIMPreferredBarViewBinder$bindClone$1$1$1, reason: invalid class name and collision with other inner class name */
        final class C02901 extends SuspendLambda implements Function2 {
            final /* synthetic */ Bar $bar;
            final /* synthetic */ MultiSIMViewModel $viewModel;
            private /* synthetic */ Object L$0;
            int label;

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.settings.multisim.ui.binder.MultiSIMPreferredBarViewBinder$bindClone$1$1$1$1, reason: invalid class name and collision with other inner class name */
            final class C02911 extends SuspendLambda implements Function2 {
                final /* synthetic */ Bar $bar;
                final /* synthetic */ MultiSIMViewModel $viewModel;
                int label;

                /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                /* renamed from: com.android.systemui.settings.multisim.ui.binder.MultiSIMPreferredBarViewBinder$bindClone$1$1$1$1$1, reason: invalid class name and collision with other inner class name */
                final class C02921 extends SuspendLambda implements Function2 {
                    final /* synthetic */ Bar $bar;
                    int label;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public C02921(Bar bar, Continuation continuation) {
                        super(2, continuation);
                        this.$bar = bar;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation create(Object obj, Continuation continuation) {
                        return new C02921(this.$bar, continuation);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        return ((C02921) create((Unit) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        if (this.label != 0) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj);
                        Log.d(MultiSIMPreferredBarViewBinder.TAG, "CloneBar updated");
                        ((MultiSIMPreferredSlotBar) this.$bar).updateBarVisibilities();
                        return Unit.INSTANCE;
                    }
                }

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C02911(MultiSIMViewModel multiSIMViewModel, Bar bar, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = multiSIMViewModel;
                    this.$bar = bar;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C02911(this.$viewModel, this.$bar, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C02911) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = ((MultiSIMViewModelImpl) this.$viewModel).barUpdateEvents;
                        C02921 c02921 = new C02921(this.$bar, null);
                        this.label = 1;
                        if (FlowKt.collectLatest(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, c02921, this) == coroutineSingletons) {
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
            public C02901(MultiSIMViewModel multiSIMViewModel, Bar bar, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = multiSIMViewModel;
                this.$bar = bar;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C02901 c02901 = new C02901(this.$viewModel, this.$bar, continuation);
                c02901.L$0 = obj;
                return c02901;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C02901) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                BuildersKt.launch$default((CoroutineScope) this.L$0, null, null, new C02911(this.$viewModel, this.$bar, null), 3);
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(LifecycleOwner lifecycleOwner, MultiSIMViewModel multiSIMViewModel, Bar bar, Continuation continuation) {
            super(2, continuation);
            this.$$this$repeatWhenAttached = lifecycleOwner;
            this.$viewModel = multiSIMViewModel;
            this.$bar = bar;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$$this$repeatWhenAttached, this.$viewModel, this.$bar, continuation);
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
                LifecycleOwner lifecycleOwner = this.$$this$repeatWhenAttached;
                Lifecycle.State state = Lifecycle.State.CREATED;
                C02901 c02901 = new C02901(this.$viewModel, this.$bar, null);
                this.label = 1;
                if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, c02901, this) == coroutineSingletons) {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.settings.multisim.ui.binder.MultiSIMPreferredBarViewBinder$bindClone$1$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ LifecycleOwner $$this$repeatWhenAttached;
        final /* synthetic */ SlotsView $view;
        final /* synthetic */ MultiSIMViewModel $viewModel;
        int label;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.settings.multisim.ui.binder.MultiSIMPreferredBarViewBinder$bindClone$1$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ SlotsView $view;
            final /* synthetic */ MultiSIMViewModel $viewModel;
            private /* synthetic */ Object L$0;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(SlotsView slotsView, MultiSIMViewModel multiSIMViewModel, Continuation continuation) {
                super(2, continuation);
                this.$view = slotsView;
                this.$viewModel = multiSIMViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$view, this.$viewModel, continuation);
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
                        MultiSIMPreferredSlotView multiSIMPreferredSlotView = (MultiSIMPreferredSlotView) this.$view;
                        multiSIMPreferredSlotView.getClass();
                        ArrayList arrayList = new ArrayList(multiSIMPreferredSlotView.mSlotButtons);
                        int size = arrayList.size();
                        int i2 = 0;
                        int i3 = 0;
                        while (i3 < size) {
                            Object obj2 = arrayList.get(i3);
                            i3++;
                            MultiSIMPreferredBarViewBinder.access$bindButton(MultiSIMPreferredBarViewBinder.INSTANCE, coroutineScope, this.$viewModel, (Button) obj2);
                        }
                        String str = MultiSIMPreferredBarViewBinder.TAG;
                        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
                        int size2 = arrayList.size();
                        while (i2 < size2) {
                            Object obj3 = arrayList.get(i2);
                            i2++;
                            arrayList2.add(((MultiSIMPreferredSlotView.PrefferedSlotButton) ((Button) obj3)).mType);
                        }
                        Log.d(str, "bind " + arrayList2);
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
                    Log.d(MultiSIMPreferredBarViewBinder.TAG, "unbind");
                    throw th;
                }
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(LifecycleOwner lifecycleOwner, SlotsView slotsView, MultiSIMViewModel multiSIMViewModel, Continuation continuation) {
            super(2, continuation);
            this.$$this$repeatWhenAttached = lifecycleOwner;
            this.$view = slotsView;
            this.$viewModel = multiSIMViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.$$this$repeatWhenAttached, this.$view, this.$viewModel, continuation);
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
                LifecycleOwner lifecycleOwner = this.$$this$repeatWhenAttached;
                Lifecycle.State state = Lifecycle.State.STARTED;
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$view, this.$viewModel, null);
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
    public MultiSIMPreferredBarViewBinder$bindClone$1(MultiSIMViewModel multiSIMViewModel, Bar bar, SlotsView slotsView, Continuation continuation) {
        super(3, continuation);
        this.$viewModel = multiSIMViewModel;
        this.$bar = bar;
        this.$view = slotsView;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        MultiSIMPreferredBarViewBinder$bindClone$1 multiSIMPreferredBarViewBinder$bindClone$1 = new MultiSIMPreferredBarViewBinder$bindClone$1(this.$viewModel, this.$bar, this.$view, (Continuation) obj3);
        multiSIMPreferredBarViewBinder$bindClone$1.L$0 = (LifecycleOwner) obj;
        return multiSIMPreferredBarViewBinder$bindClone$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
        BuildersKt.launch$default(LifecycleOwnerKt.getLifecycleScope(lifecycleOwner), null, null, new AnonymousClass1(lifecycleOwner, this.$viewModel, this.$bar, null), 3);
        BuildersKt.launch$default(LifecycleKt.getCoroutineScope(lifecycleOwner.getLifecycle()), null, null, new AnonymousClass2(lifecycleOwner, this.$view, this.$viewModel, null), 3);
        return Unit.INSTANCE;
    }
}
