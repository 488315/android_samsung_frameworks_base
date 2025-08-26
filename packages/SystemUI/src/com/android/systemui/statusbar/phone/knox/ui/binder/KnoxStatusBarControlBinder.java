package com.android.systemui.statusbar.phone.knox.ui.binder;

import android.content.res.Resources;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import androidx.viewpager.widget.ViewPager$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.statusbar.phone.KeyguardStatusBarViewController$$ExternalSyntheticLambda12;
import com.android.systemui.statusbar.phone.knox.domain.model.KnoxStatusBarCustomTextModel;
import com.android.systemui.statusbar.phone.knox.ui.view.KnoxStatusBarTextView;
import com.android.systemui.statusbar.phone.knox.ui.viewmodel.KnoxStatusBarControlViewModel;
import com.android.systemui.statusbar.phone.knox.ui.viewmodel.KnoxStatusBarViewControl;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes3.dex */
public final class KnoxStatusBarControlBinder {

    /* renamed from: com.android.systemui.statusbar.phone.knox.ui.binder.KnoxStatusBarControlBinder$bind$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function3 {
        final /* synthetic */ KnoxStatusBarViewControl $knoxStatusBarViewControl;
        final /* synthetic */ KnoxStatusBarControlViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.statusbar.phone.knox.ui.binder.KnoxStatusBarControlBinder$bind$1$1, reason: invalid class name and collision with other inner class name */
        final class C05321 extends SuspendLambda implements Function2 {
            final /* synthetic */ KnoxStatusBarViewControl $knoxStatusBarViewControl;
            final /* synthetic */ KnoxStatusBarControlViewModel $viewModel;
            private /* synthetic */ Object L$0;
            int label;

            /* renamed from: com.android.systemui.statusbar.phone.knox.ui.binder.KnoxStatusBarControlBinder$bind$1$1$1, reason: invalid class name and collision with other inner class name */
            final class C05331 extends SuspendLambda implements Function2 {
                final /* synthetic */ KnoxStatusBarViewControl $knoxStatusBarViewControl;
                final /* synthetic */ KnoxStatusBarControlViewModel $viewModel;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public C05331(KnoxStatusBarControlViewModel knoxStatusBarControlViewModel, KnoxStatusBarViewControl knoxStatusBarViewControl, Continuation continuation) {
                    super(2, continuation);
                    this.$viewModel = knoxStatusBarControlViewModel;
                    this.$knoxStatusBarViewControl = knoxStatusBarViewControl;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C05331(this.$viewModel, this.$knoxStatusBarViewControl, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C05331) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                            public final Object emit(Object obj2, Continuation continuation) throws Resources.NotFoundException {
                                Boolean bool = (Boolean) obj2;
                                boolean zBooleanValue = bool.booleanValue();
                                KnoxStatusBarControlViewModel knoxStatusBarControlViewModel2 = knoxStatusBarControlViewModel;
                                knoxStatusBarControlViewModel2.getClass();
                                Log.d("KnoxStatusBarControlViewModel", "updateHiddenState(hidden:" + zBooleanValue);
                                KeyguardStatusBarViewController$$ExternalSyntheticLambda12 keyguardStatusBarViewController$$ExternalSyntheticLambda12 = knoxStatusBarControlViewModel2.setHidden;
                                if (keyguardStatusBarViewController$$ExternalSyntheticLambda12 != null) {
                                    keyguardStatusBarViewController$$ExternalSyntheticLambda12.mo781invoke(bool);
                                } else {
                                    KnoxStatusBarViewControl knoxStatusBarViewControl2 = knoxStatusBarViewControl;
                                    knoxStatusBarViewControl2.setHiddenByKnox(zBooleanValue);
                                    View statusBarView = knoxStatusBarViewControl2.getStatusBarView();
                                    if (zBooleanValue) {
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
                                int i2;
                                KnoxStatusBarCustomTextModel knoxStatusBarCustomTextModel = (KnoxStatusBarCustomTextModel) obj2;
                                KnoxStatusBarControlViewModel knoxStatusBarControlViewModel2 = knoxStatusBarControlViewModel;
                                knoxStatusBarControlViewModel2.getClass();
                                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("updateCustomText(text:", knoxStatusBarCustomTextModel.customText, "KnoxStatusBarControlViewModel");
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
                                        int i3 = knoxStatusBarCustomTextModel.textSize;
                                        knoxStatusBarTextView.setTextSize(1, i3);
                                        knoxStatusBarTextView.setTypeface(null, knoxStatusBarCustomTextModel.textStyle);
                                        int i4 = knoxStatusBarCustomTextModel.width;
                                        if (i4 > 0) {
                                            knoxStatusBarTextView.setHorizontallyScrolling(true);
                                            knoxStatusBarTextView.setSingleLine();
                                            knoxStatusBarTextView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                                            knoxStatusBarTextView.setMarqueeRepeatLimit(10);
                                            knoxStatusBarTextView.setSelected(true);
                                            i2 = i4;
                                        } else {
                                            knoxStatusBarTextView.setHorizontallyScrolling(false);
                                            knoxStatusBarTextView.setSingleLine(false);
                                            knoxStatusBarTextView.setEllipsize(null);
                                            i2 = 108;
                                        }
                                        int i5 = (int) (i2 * knoxStatusBarTextView.getContext().getResources().getDisplayMetrics().density);
                                        int visibility = knoxStatusBarTextView.getVisibility();
                                        StringBuilder sbM890m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m890m(i3, "setKnoxText(", str, ") size:", ", textWidth:");
                                        ViewPager$$ExternalSyntheticOutline0.m(sbM890m, i4, ", scrollWidth:", i5, ", visibility:");
                                        sbM890m.append(visibility);
                                        Log.d("KnoxStatusBarTextView", sbM890m.toString());
                                        knoxStatusBarTextView.setMaxWidth(i5);
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
                                boolean zBooleanValue = ((Boolean) obj2).booleanValue();
                                knoxStatusBarControlViewModel.getClass();
                                Log.d("KnoxStatusBarControlViewModel", "updateSystemIconEnabledState(enabled:" + zBooleanValue);
                                View viewRequireViewById = knoxStatusBarViewControl.getStatusBarView().requireViewById(R.id.system_icons);
                                if (zBooleanValue) {
                                    viewRequireViewById.animate().cancel();
                                    viewRequireViewById.setAlpha(1.0f);
                                    viewRequireViewById.setVisibility(0);
                                } else {
                                    viewRequireViewById.animate().cancel();
                                    viewRequireViewById.setAlpha(0.0f);
                                    viewRequireViewById.setVisibility(4);
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
            public C05321(KnoxStatusBarControlViewModel knoxStatusBarControlViewModel, KnoxStatusBarViewControl knoxStatusBarViewControl, Continuation continuation) {
                super(2, continuation);
                this.$viewModel = knoxStatusBarControlViewModel;
                this.$knoxStatusBarViewControl = knoxStatusBarViewControl;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C05321 c05321 = new C05321(this.$viewModel, this.$knoxStatusBarViewControl, continuation);
                c05321.L$0 = obj;
                return c05321;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C05321) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                BuildersKt.launch$default(coroutineScope, null, null, new C05331(this.$viewModel, this.$knoxStatusBarViewControl, null), 3);
                BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(this.$viewModel, this.$knoxStatusBarViewControl, null), 3);
                BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass3(this.$viewModel, this.$knoxStatusBarViewControl, null), 3);
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(KnoxStatusBarControlViewModel knoxStatusBarControlViewModel, KnoxStatusBarViewControl knoxStatusBarViewControl, Continuation continuation) {
            super(3, continuation);
            this.$viewModel = knoxStatusBarControlViewModel;
            this.$knoxStatusBarViewControl = knoxStatusBarViewControl;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$viewModel, this.$knoxStatusBarViewControl, (Continuation) obj3);
            anonymousClass1.L$0 = (LifecycleOwner) obj;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
                Lifecycle.State state = Lifecycle.State.STARTED;
                C05321 c05321 = new C05321(this.$viewModel, this.$knoxStatusBarViewControl, null);
                this.label = 1;
                if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, c05321, this) == coroutineSingletons) {
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

    static {
        new KnoxStatusBarControlBinder();
    }

    private KnoxStatusBarControlBinder() {
    }

    public static final void bind(KnoxStatusBarControlViewModel knoxStatusBarControlViewModel, KnoxStatusBarViewControl knoxStatusBarViewControl) {
        RepeatWhenAttachedKt.repeatWhenAttached(knoxStatusBarViewControl.getStatusBarView(), EmptyCoroutineContext.INSTANCE, new AnonymousClass1(knoxStatusBarControlViewModel, knoxStatusBarViewControl, null));
    }
}
