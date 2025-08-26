package com.android.systemui.qs.ui.viewmodel;

import androidx.compose.runtime.State;
import com.android.systemui.brightness.ui.viewmodel.BrightnessSliderViewModel;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.lifecycle.Hydrator;
import com.android.systemui.media.controls.domain.pipeline.interactor.MediaCarouselInteractor;
import com.android.systemui.media.controls.ui.controller.MediaCarouselController;
import com.android.systemui.media.controls.ui.view.MediaHost;
import com.android.systemui.qs.panels.ui.viewmodel.DetailsViewModel;
import com.android.systemui.qs.panels.ui.viewmodel.EditModeViewModel;
import com.android.systemui.qs.panels.ui.viewmodel.TileGridViewModel;
import com.android.systemui.qs.panels.ui.viewmodel.toolbar.ToolbarViewModel;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractorImpl;
import com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes2.dex */
public final class QuickSettingsContainerViewModel extends ExclusiveActivatable {
    public final BrightnessSliderViewModel brightnessSliderViewModel;
    public final DetailsViewModel detailsViewModel;
    public final EditModeViewModel editModeViewModel;
    public final Float expansion;
    public final Hydrator hydrator;
    public final MediaCarouselController mediaCarouselController;
    public final MediaHost mediaHost;
    public final ShadeHeaderViewModel shadeHeaderViewModel;
    public final State showHeader$delegate;
    public final State showMedia$delegate;
    public final TileGridViewModel tileGridViewModel;
    public final ToolbarViewModel toolbarViewModel;

    public interface Factory {
        QuickSettingsContainerViewModel create(boolean z, Float f);
    }

    /* renamed from: com.android.systemui.qs.ui.viewmodel.QuickSettingsContainerViewModel$onActivated$1, reason: invalid class name */
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
            return QuickSettingsContainerViewModel.this.onActivated(this);
        }
    }

    /* renamed from: com.android.systemui.qs.ui.viewmodel.QuickSettingsContainerViewModel$onActivated$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.qs.ui.viewmodel.QuickSettingsContainerViewModel$onActivated$2$2, reason: invalid class name and collision with other inner class name */
        final class C04262 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ QuickSettingsContainerViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C04262(QuickSettingsContainerViewModel quickSettingsContainerViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = quickSettingsContainerViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C04262(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C04262) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    Hydrator hydrator = this.this$0.hydrator;
                    this.label = 1;
                    if (hydrator.activate(this) == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.qs.ui.viewmodel.QuickSettingsContainerViewModel$onActivated$2$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ QuickSettingsContainerViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass3(QuickSettingsContainerViewModel quickSettingsContainerViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = quickSettingsContainerViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass3(this.this$0, continuation);
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
                    BrightnessSliderViewModel brightnessSliderViewModel = this.this$0.brightnessSliderViewModel;
                    this.label = 1;
                    if (brightnessSliderViewModel.activate(this) == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.qs.ui.viewmodel.QuickSettingsContainerViewModel$onActivated$2$4, reason: invalid class name */
        final class AnonymousClass4 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ QuickSettingsContainerViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass4(QuickSettingsContainerViewModel quickSettingsContainerViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = quickSettingsContainerViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass4(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass4) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    ToolbarViewModel toolbarViewModel = this.this$0.toolbarViewModel;
                    this.label = 1;
                    if (toolbarViewModel.activate(this) == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.qs.ui.viewmodel.QuickSettingsContainerViewModel$onActivated$2$5, reason: invalid class name */
        final class AnonymousClass5 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ QuickSettingsContainerViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass5(QuickSettingsContainerViewModel quickSettingsContainerViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = quickSettingsContainerViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass5(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass5) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    ShadeHeaderViewModel shadeHeaderViewModel = this.this$0.shadeHeaderViewModel;
                    this.label = 1;
                    if (shadeHeaderViewModel.activate(this) == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.qs.ui.viewmodel.QuickSettingsContainerViewModel$onActivated$2$6, reason: invalid class name */
        final class AnonymousClass6 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ QuickSettingsContainerViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass6(QuickSettingsContainerViewModel quickSettingsContainerViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = quickSettingsContainerViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass6(this.this$0, continuation);
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
                    TileGridViewModel tileGridViewModel = this.this$0.tileGridViewModel;
                    this.label = 1;
                    if (tileGridViewModel.activate(this) == coroutineSingletons) {
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

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = QuickSettingsContainerViewModel.this.new AnonymousClass2(continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
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
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                QuickSettingsContainerViewModel quickSettingsContainerViewModel = QuickSettingsContainerViewModel.this;
                Float f = quickSettingsContainerViewModel.expansion;
                if (f != null) {
                    quickSettingsContainerViewModel.mediaHost.setExpansion(f.floatValue());
                }
                BuildersKt.launch$default(coroutineScope, null, null, new C04262(QuickSettingsContainerViewModel.this, null), 3);
                BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass3(QuickSettingsContainerViewModel.this, null), 3);
                BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass4(QuickSettingsContainerViewModel.this, null), 3);
                BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass5(QuickSettingsContainerViewModel.this, null), 3);
                BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass6(QuickSettingsContainerViewModel.this, null), 3);
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
        }
    }

    public QuickSettingsContainerViewModel(BrightnessSliderViewModel.Factory factory, ShadeHeaderViewModel.Factory factory2, TileGridViewModel.Factory factory3, boolean z, Float f, EditModeViewModel editModeViewModel, DetailsViewModel detailsViewModel, ToolbarViewModel.Factory factory4, ShadeModeInteractor shadeModeInteractor, MediaCarouselInteractor mediaCarouselInteractor, MediaCarouselController mediaCarouselController, MediaHost mediaHost) {
        this.expansion = f;
        this.editModeViewModel = editModeViewModel;
        this.detailsViewModel = detailsViewModel;
        this.mediaCarouselController = mediaCarouselController;
        this.mediaHost = mediaHost;
        Hydrator hydrator = new Hydrator("QuickSettingsContainerViewModel.hydrator", null, 2, null);
        this.hydrator = hydrator;
        this.brightnessSliderViewModel = factory.create(z);
        this.toolbarViewModel = factory4.create();
        this.shadeHeaderViewModel = factory2.create();
        this.tileGridViewModel = factory3.create();
        Boolean boolValueOf = Boolean.valueOf(!((Boolean) r9.isShadeLayoutWide.$$delegate_0.getValue()).booleanValue());
        final ReadonlyStateFlow readonlyStateFlow = ((ShadeModeInteractorImpl) shadeModeInteractor).isShadeLayoutWide;
        this.showHeader$delegate = hydrator.hydratedStateOf("showHeader", boolValueOf, new Flow() { // from class: com.android.systemui.qs.ui.viewmodel.QuickSettingsContainerViewModel$special$$inlined$map$1

            /* renamed from: com.android.systemui.qs.ui.viewmodel.QuickSettingsContainerViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.qs.ui.viewmodel.QuickSettingsContainerViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
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
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        Boolean boolValueOf = Boolean.valueOf(!((Boolean) obj).booleanValue());
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        this.showMedia$delegate = hydrator.hydratedStateOf(mediaCarouselInteractor.hasAnyMediaOrRecommendation, "showMedia");
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object onActivated(Continuation continuation) {
        AnonymousClass1 anonymousClass1;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuation);
            }
        }
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(null);
            anonymousClass1.label = 1;
            if (CoroutineScopeKt.coroutineScope(anonymousClass2, anonymousClass1) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }
}
