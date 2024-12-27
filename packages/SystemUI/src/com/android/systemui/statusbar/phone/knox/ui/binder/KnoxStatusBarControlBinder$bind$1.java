package com.android.systemui.statusbar.phone.knox.ui.binder;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.systemui.R;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.statusbar.phone.knox.domain.model.KnoxStatusBarCustomTextModel;
import com.android.systemui.statusbar.phone.knox.ui.view.KnoxStatusBarTextView;
import com.android.systemui.statusbar.phone.knox.ui.viewmodel.KnoxStatusBarControlViewModel;
import com.android.systemui.statusbar.phone.knox.ui.viewmodel.KnoxStatusBarViewControl;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
final class KnoxStatusBarControlBinder$bind$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ KnoxStatusBarViewControl $knoxStatusBarViewControl;
    final /* synthetic */ KnoxStatusBarControlViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.statusbar.phone.knox.ui.binder.KnoxStatusBarControlBinder$bind$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ KnoxStatusBarViewControl $knoxStatusBarViewControl;
        final /* synthetic */ KnoxStatusBarControlViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        /* renamed from: com.android.systemui.statusbar.phone.knox.ui.binder.KnoxStatusBarControlBinder$bind$1$1$1, reason: invalid class name and collision with other inner class name */
        final class C02021 extends SuspendLambda implements Function2 {
            final /* synthetic */ KnoxStatusBarViewControl $knoxStatusBarViewControl;
            final /* synthetic */ KnoxStatusBarControlViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C02021(KnoxStatusBarControlViewModel knoxStatusBarControlViewModel, KnoxStatusBarViewControl knoxStatusBarViewControl, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = knoxStatusBarControlViewModel;
                this.$knoxStatusBarViewControl = knoxStatusBarViewControl;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C02021(this.$viewModel, this.$knoxStatusBarViewControl, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C02021) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    final KnoxStatusBarControlViewModel knoxStatusBarControlViewModel = this.$viewModel;
                    ReadonlyStateFlow readonlyStateFlow = knoxStatusBarControlViewModel.statusBarHidden;
                    final KnoxStatusBarViewControl knoxStatusBarViewControl = this.$knoxStatusBarViewControl;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.phone.knox.ui.binder.KnoxStatusBarControlBinder.bind.1.1.1.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            Boolean bool = (Boolean) obj2;
                            boolean booleanValue = bool.booleanValue();
                            Function1 function1 = KnoxStatusBarControlViewModel.this.setHidden;
                            if (function1 != null) {
                                function1.invoke(bool);
                            } else {
                                KnoxStatusBarViewControl knoxStatusBarViewControl2 = knoxStatusBarViewControl;
                                knoxStatusBarViewControl2.setHiddenByKnox(booleanValue);
                                View statusBarView = knoxStatusBarViewControl2.getStatusBarView();
                                if (booleanValue) {
                                    statusBarView.setVisibility(statusBarView.getVisibility());
                                } else {
                                    statusBarView.setVisibility(0);
                                }
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (readonlyStateFlow.$$delegate_0.collect(flowCollector, this) == coroutineSingletons) {
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

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        /* renamed from: com.android.systemui.statusbar.phone.knox.ui.binder.KnoxStatusBarControlBinder$bind$1$1$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            final /* synthetic */ KnoxStatusBarViewControl $knoxStatusBarViewControl;
            final /* synthetic */ KnoxStatusBarControlViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(KnoxStatusBarControlViewModel knoxStatusBarControlViewModel, KnoxStatusBarViewControl knoxStatusBarViewControl, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = knoxStatusBarControlViewModel;
                this.$knoxStatusBarViewControl = knoxStatusBarViewControl;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass2(this.$viewModel, this.$knoxStatusBarViewControl, continuation);
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
                    final KnoxStatusBarControlViewModel knoxStatusBarControlViewModel = this.$viewModel;
                    ReadonlyStateFlow readonlyStateFlow = knoxStatusBarControlViewModel.knoxStatusBarCustomText;
                    final KnoxStatusBarViewControl knoxStatusBarViewControl = this.$knoxStatusBarViewControl;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.phone.knox.ui.binder.KnoxStatusBarControlBinder.bind.1.1.2.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            KnoxStatusBarCustomTextModel knoxStatusBarCustomTextModel = (KnoxStatusBarCustomTextModel) obj2;
                            KnoxStatusBarControlViewModel knoxStatusBarControlViewModel2 = KnoxStatusBarControlViewModel.this;
                            knoxStatusBarControlViewModel2.getClass();
                            KnoxStatusBarTextView knoxStatusBarTextView = (KnoxStatusBarTextView) knoxStatusBarViewControl.getStatusBarView().findViewById(R.id.knox_custom_text);
                            if (knoxStatusBarTextView != null) {
                                String str = knoxStatusBarCustomTextModel.customText;
                                DarkIconDispatcher darkIconDispatcher = knoxStatusBarControlViewModel2.darkIconDispatcher;
                                if (str == null || str.length() == 0) {
                                    darkIconDispatcher.removeDarkReceiver(knoxStatusBarTextView);
                                    knoxStatusBarTextView.setVisibility(8);
                                    knoxStatusBarTextView.setSelected(false);
                                } else {
                                    darkIconDispatcher.addDarkReceiver(knoxStatusBarTextView);
                                    knoxStatusBarTextView.setVisibility(0);
                                    knoxStatusBarTextView.setText(str);
                                    knoxStatusBarTextView.setTextSize(1, knoxStatusBarCustomTextModel.textSize);
                                    knoxStatusBarTextView.setTypeface(null, knoxStatusBarCustomTextModel.textStyle);
                                    int i2 = knoxStatusBarCustomTextModel.width;
                                    if (i2 > 0) {
                                        knoxStatusBarTextView.setHorizontallyScrolling(true);
                                        knoxStatusBarTextView.setSingleLine();
                                        knoxStatusBarTextView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                                        knoxStatusBarTextView.setMarqueeRepeatLimit(10);
                                        knoxStatusBarTextView.setSelected(true);
                                    } else {
                                        knoxStatusBarTextView.setHorizontallyScrolling(false);
                                        knoxStatusBarTextView.setSingleLine(false);
                                        knoxStatusBarTextView.setEllipsize(null);
                                        i2 = 108;
                                    }
                                    int i3 = (int) (i2 * knoxStatusBarTextView.getContext().getResources().getDisplayMetrics().density);
                                    Log.d("KnoxStatusBarTextView", "Scroll width=" + i3);
                                    knoxStatusBarTextView.setMaxWidth(i3);
                                    knoxStatusBarTextView.setVisibility(0);
                                }
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (readonlyStateFlow.$$delegate_0.collect(flowCollector, this) == coroutineSingletons) {
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

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        /* renamed from: com.android.systemui.statusbar.phone.knox.ui.binder.KnoxStatusBarControlBinder$bind$1$1$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            final /* synthetic */ KnoxStatusBarViewControl $knoxStatusBarViewControl;
            final /* synthetic */ KnoxStatusBarControlViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass3(KnoxStatusBarControlViewModel knoxStatusBarControlViewModel, KnoxStatusBarViewControl knoxStatusBarViewControl, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = knoxStatusBarControlViewModel;
                this.$knoxStatusBarViewControl = knoxStatusBarViewControl;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass3(this.$viewModel, this.$knoxStatusBarViewControl, continuation);
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
                    final KnoxStatusBarControlViewModel knoxStatusBarControlViewModel = this.$viewModel;
                    ReadonlyStateFlow readonlyStateFlow = knoxStatusBarControlViewModel.statusBarIconsEnabled;
                    final KnoxStatusBarViewControl knoxStatusBarViewControl = this.$knoxStatusBarViewControl;
                    FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.phone.knox.ui.binder.KnoxStatusBarControlBinder.bind.1.1.3.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            boolean booleanValue = ((Boolean) obj2).booleanValue();
                            KnoxStatusBarControlViewModel.this.getClass();
                            View requireViewById = knoxStatusBarViewControl.getStatusBarView().requireViewById(R.id.system_icons);
                            if (booleanValue) {
                                requireViewById.animate().cancel();
                                requireViewById.setAlpha(1.0f);
                                requireViewById.setVisibility(0);
                            } else {
                                requireViewById.animate().cancel();
                                requireViewById.setAlpha(0.0f);
                                requireViewById.setVisibility(4);
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    this.label = 1;
                    if (readonlyStateFlow.$$delegate_0.collect(flowCollector, this) == coroutineSingletons) {
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
        public AnonymousClass1(KnoxStatusBarControlViewModel knoxStatusBarControlViewModel, KnoxStatusBarViewControl knoxStatusBarViewControl, Continuation continuation) {
            super(2, continuation);
            this.$viewModel = knoxStatusBarControlViewModel;
            this.$knoxStatusBarViewControl = knoxStatusBarViewControl;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$knoxStatusBarViewControl, continuation);
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
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            BuildersKt.launch$default(coroutineScope, null, null, new C02021(this.$viewModel, this.$knoxStatusBarViewControl, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(this.$viewModel, this.$knoxStatusBarViewControl, null), 3);
            BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass3(this.$viewModel, this.$knoxStatusBarViewControl, null), 3);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KnoxStatusBarControlBinder$bind$1(KnoxStatusBarControlViewModel knoxStatusBarControlViewModel, KnoxStatusBarViewControl knoxStatusBarViewControl, Continuation continuation) {
        super(3, continuation);
        this.$viewModel = knoxStatusBarControlViewModel;
        this.$knoxStatusBarViewControl = knoxStatusBarViewControl;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        KnoxStatusBarControlBinder$bind$1 knoxStatusBarControlBinder$bind$1 = new KnoxStatusBarControlBinder$bind$1(this.$viewModel, this.$knoxStatusBarViewControl, (Continuation) obj3);
        knoxStatusBarControlBinder$bind$1.L$0 = (LifecycleOwner) obj;
        return knoxStatusBarControlBinder$bind$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.STARTED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$knoxStatusBarViewControl, null);
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
