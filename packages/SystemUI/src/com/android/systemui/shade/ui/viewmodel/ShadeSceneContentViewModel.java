package com.android.systemui.shade.ui.viewmodel;

import com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.media.controls.domain.pipeline.interactor.MediaCarouselInteractor;
import com.android.systemui.qs.FooterActionsController;
import com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModel;
import com.android.systemui.qs.ui.adapter.QSSceneAdapter;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.settings.brightness.ui.viewModel.BrightnessMirrorViewModel;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractorImpl;
import com.android.systemui.shade.ui.viewmodel.ShadeHeaderViewModel;
import com.android.systemui.statusbar.disableflags.domain.interactor.DisableFlagsInteractor;
import com.android.systemui.statusbar.disableflags.shared.model.DisableFlagsModel;
import com.android.systemui.unfold.domain.interactor.UnfoldTransitionInteractor;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes3.dex */
public final class ShadeSceneContentViewModel extends ExclusiveActivatable {
    public final StateFlowImpl _isEmptySpaceClickable;
    public final StateFlowImpl _isQsEnabled;
    public final BrightnessMirrorViewModel.Factory brightnessMirrorViewModelFactory;
    public final DeviceEntryInteractor deviceEntryInteractor;
    public final DisableFlagsInteractor disableFlagsInteractor;
    public final FooterActionsController footerActionsController;
    public final AtomicBoolean footerActionsControllerInitialized;
    public final FooterActionsViewModel.Factory footerActionsViewModelFactory;
    public final ReadonlyStateFlow isEmptySpaceClickable;
    public final ReadonlyStateFlow isMediaVisible;
    public final ReadonlyStateFlow isQsEnabled;
    public final QSSceneAdapter qsSceneAdapter;
    public final SceneInteractor sceneInteractor;
    public final ShadeHeaderViewModel.Factory shadeHeaderViewModelFactory;
    public final ReadonlyStateFlow shadeMode;
    public final UnfoldTransitionInteractor unfoldTransitionInteractor;

    public interface Factory {
    }

    /* renamed from: com.android.systemui.shade.ui.viewmodel.ShadeSceneContentViewModel$onActivated$1, reason: invalid class name */
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
            return ShadeSceneContentViewModel.this.onActivated(this);
        }
    }

    /* renamed from: com.android.systemui.shade.ui.viewmodel.ShadeSceneContentViewModel$onActivated$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.shade.ui.viewmodel.ShadeSceneContentViewModel$onActivated$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            /* synthetic */ boolean Z$0;
            int label;
            final /* synthetic */ ShadeSceneContentViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(ShadeSceneContentViewModel shadeSceneContentViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = shadeSceneContentViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, continuation);
                anonymousClass1.Z$0 = ((Boolean) obj).booleanValue();
                return anonymousClass1;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Boolean bool = (Boolean) obj;
                bool.booleanValue();
                return ((AnonymousClass1) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                this.this$0._isEmptySpaceClickable.updateState(null, Boolean.valueOf(!this.Z$0));
                return Unit.INSTANCE;
            }
        }

        /* renamed from: com.android.systemui.shade.ui.viewmodel.ShadeSceneContentViewModel$onActivated$2$3, reason: invalid class name */
        final class AnonymousClass3 extends SuspendLambda implements Function2 {
            /* synthetic */ boolean Z$0;
            int label;
            final /* synthetic */ ShadeSceneContentViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass3(ShadeSceneContentViewModel shadeSceneContentViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = shadeSceneContentViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                AnonymousClass3 anonymousClass3 = new AnonymousClass3(this.this$0, continuation);
                anonymousClass3.Z$0 = ((Boolean) obj).booleanValue();
                return anonymousClass3;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Boolean bool = (Boolean) obj;
                bool.booleanValue();
                return ((AnonymousClass3) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                this.this$0._isQsEnabled.updateState(null, Boolean.valueOf(this.Z$0));
                return Unit.INSTANCE;
            }
        }

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = ShadeSceneContentViewModel.this.new AnonymousClass2(continuation);
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
                ShadeSceneContentViewModel shadeSceneContentViewModel = ShadeSceneContentViewModel.this;
                FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(shadeSceneContentViewModel.deviceEntryInteractor.isDeviceEntered, new AnonymousClass1(shadeSceneContentViewModel, null)), coroutineScope);
                final ReadonlyStateFlow readonlyStateFlow = ShadeSceneContentViewModel.this.disableFlagsInteractor.disableFlags;
                FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new Flow() { // from class: com.android.systemui.shade.ui.viewmodel.ShadeSceneContentViewModel$onActivated$2$invokeSuspend$$inlined$map$1

                    /* renamed from: com.android.systemui.shade.ui.viewmodel.ShadeSceneContentViewModel$onActivated$2$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* renamed from: com.android.systemui.shade.ui.viewmodel.ShadeSceneContentViewModel$onActivated$2$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
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
                                Boolean boolValueOf = Boolean.valueOf(((DisableFlagsModel) obj).isQuickSettingsEnabled());
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
                }, new AnonymousClass3(ShadeSceneContentViewModel.this, null)), coroutineScope);
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

    public ShadeSceneContentViewModel(QSSceneAdapter qSSceneAdapter, ShadeHeaderViewModel.Factory factory, BrightnessMirrorViewModel.Factory factory2, MediaCarouselInteractor mediaCarouselInteractor, ShadeModeInteractor shadeModeInteractor, DisableFlagsInteractor disableFlagsInteractor, FooterActionsViewModel.Factory factory3, FooterActionsController footerActionsController, UnfoldTransitionInteractor unfoldTransitionInteractor, DeviceEntryInteractor deviceEntryInteractor, SceneInteractor sceneInteractor) {
        this.qsSceneAdapter = qSSceneAdapter;
        this.shadeHeaderViewModelFactory = factory;
        this.brightnessMirrorViewModelFactory = factory2;
        this.disableFlagsInteractor = disableFlagsInteractor;
        this.footerActionsViewModelFactory = factory3;
        this.footerActionsController = footerActionsController;
        this.unfoldTransitionInteractor = unfoldTransitionInteractor;
        this.deviceEntryInteractor = deviceEntryInteractor;
        this.sceneInteractor = sceneInteractor;
        this.shadeMode = ((ShadeModeInteractorImpl) shadeModeInteractor).shadeMode;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.valueOf(!((Boolean) deviceEntryInteractor.isDeviceEntered.$$delegate_0.getValue()).booleanValue()));
        this._isEmptySpaceClickable = stateFlowImplMutableStateFlow;
        this.isEmptySpaceClickable = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
        this.isMediaVisible = mediaCarouselInteractor.hasActiveMediaOrRecommendation;
        StateFlowImpl stateFlowImplMutableStateFlow2 = StateFlowKt.MutableStateFlow(Boolean.valueOf(!((DisableFlagsModel) disableFlagsInteractor.disableFlags.$$delegate_0.getValue()).isQuickSettingsEnabled()));
        this._isQsEnabled = stateFlowImplMutableStateFlow2;
        this.isQsEnabled = FlowKt.asStateFlow(stateFlowImplMutableStateFlow2);
        this.footerActionsControllerInitialized = new AtomicBoolean(false);
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
