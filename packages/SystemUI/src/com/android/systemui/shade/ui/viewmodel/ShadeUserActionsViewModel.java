package com.android.systemui.shade.ui.viewmodel;

import com.android.compose.animation.scene.SceneKey;
import com.android.compose.animation.scene.Swipe;
import com.android.compose.animation.scene.TransitionKey;
import com.android.compose.animation.scene.UserActionResult;
import com.android.systemui.qs.ui.adapter.QSSceneAdapter;
import com.android.systemui.qs.ui.adapter.QSSceneAdapterImpl;
import com.android.systemui.scene.domain.interactor.SceneBackInteractor;
import com.android.systemui.scene.domain.interactor.SceneBackInteractor$special$$inlined$map$1;
import com.android.systemui.scene.shared.model.SceneFamilies;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.scene.shared.model.TransitionKeys;
import com.android.systemui.scene.ui.viewmodel.UserActionsViewModel;
import com.android.systemui.scene.ui.viewmodel.UserActionsViewModel$$ExternalSyntheticLambda0;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractorImpl;
import com.android.systemui.shade.shared.model.ShadeMode;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.builders.MapBuilder;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes3.dex */
public final class ShadeUserActionsViewModel extends UserActionsViewModel {
    public final QSSceneAdapter qsSceneAdapter;
    public final SceneBackInteractor sceneBackInteractor;
    public final ShadeModeInteractor shadeModeInteractor;

    public interface Factory {
        ShadeUserActionsViewModel create();
    }

    /* renamed from: com.android.systemui.shade.ui.viewmodel.ShadeUserActionsViewModel$hydrateActions$4, reason: invalid class name */
    final class AnonymousClass4 extends SuspendLambda implements Function4 {
        /* synthetic */ Object L$0;
        /* synthetic */ Object L$1;
        /* synthetic */ boolean Z$0;
        int label;

        public AnonymousClass4(Continuation continuation) {
            super(4, continuation);
        }

        @Override // kotlin.jvm.functions.Function4
        public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
            boolean zBooleanValue = ((Boolean) obj2).booleanValue();
            AnonymousClass4 anonymousClass4 = new AnonymousClass4((Continuation) obj4);
            anonymousClass4.L$0 = (ShadeMode) obj;
            anonymousClass4.Z$0 = zBooleanValue;
            anonymousClass4.L$1 = (SceneKey) obj3;
            return anonymousClass4.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            ShadeMode shadeMode = (ShadeMode) this.L$0;
            boolean z = this.Z$0;
            SceneKey sceneKey = (SceneKey) this.L$1;
            MapBuilder mapBuilder = new MapBuilder();
            if (!z) {
                Swipe.Companion.getClass();
                Swipe swipe = Swipe.Up;
                UserActionResult.Companion companion = UserActionResult.Companion;
                TransitionKeys.INSTANCE.getClass();
                TransitionKey transitionKey = TransitionKeys.ToSplitShade;
                if (!(shadeMode instanceof ShadeMode.Split)) {
                    transitionKey = null;
                }
                mapBuilder.put(swipe, UserActionResult.Companion.invoke$default(companion, sceneKey, transitionKey, 4));
            }
            if (shadeMode instanceof ShadeMode.Single) {
                Swipe.Companion.getClass();
                mapBuilder.put(Swipe.Down, UserActionResult.Companion.invoke$default(UserActionResult.Companion, Scenes.QuickSettings, null, 6));
            }
            return mapBuilder.build();
        }
    }

    public ShadeUserActionsViewModel(QSSceneAdapter qSSceneAdapter, ShadeModeInteractor shadeModeInteractor, SceneBackInteractor sceneBackInteractor) {
        this.qsSceneAdapter = qSSceneAdapter;
        this.shadeModeInteractor = shadeModeInteractor;
        this.sceneBackInteractor = sceneBackInteractor;
    }

    @Override // com.android.systemui.scene.ui.viewmodel.UserActionsViewModel
    public final Object hydrateActions(final UserActionsViewModel$$ExternalSyntheticLambda0 userActionsViewModel$$ExternalSyntheticLambda0, Continuation continuation) {
        ReadonlyStateFlow readonlyStateFlow = ((ShadeModeInteractorImpl) this.shadeModeInteractor).shadeMode;
        ReadonlyStateFlow readonlyStateFlow2 = ((QSSceneAdapterImpl) this.qsSceneAdapter).isCustomizerShowing;
        final SceneBackInteractor$special$$inlined$map$1 sceneBackInteractor$special$$inlined$map$1 = this.sceneBackInteractor.backScene;
        final Flow flow = new Flow() { // from class: com.android.systemui.shade.ui.viewmodel.ShadeUserActionsViewModel$hydrateActions$$inlined$filter$1

            /* renamed from: com.android.systemui.shade.ui.viewmodel.ShadeUserActionsViewModel$hydrateActions$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.shade.ui.viewmodel.ShadeUserActionsViewModel$hydrateActions$$inlined$filter$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    Object L$1;
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
                        if (!Intrinsics.areEqual((SceneKey) obj, Scenes.Shade)) {
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
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
            public final Object collect(FlowCollector flowCollector, Continuation continuation2) {
                Object objCollect = sceneBackInteractor$special$$inlined$map$1.collect(new AnonymousClass2(flowCollector), continuation2);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        Object objCollect = FlowKt.combine(readonlyStateFlow, readonlyStateFlow2, new Flow() { // from class: com.android.systemui.shade.ui.viewmodel.ShadeUserActionsViewModel$hydrateActions$$inlined$map$1

            /* renamed from: com.android.systemui.shade.ui.viewmodel.ShadeUserActionsViewModel$hydrateActions$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.shade.ui.viewmodel.ShadeUserActionsViewModel$hydrateActions$$inlined$map$1$2$1, reason: invalid class name */
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
                        SceneKey sceneKey = (SceneKey) obj;
                        if (sceneKey == null) {
                            sceneKey = SceneFamilies.Home;
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(sceneKey, anonymousClass1) == coroutineSingletons) {
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
            public final Object collect(FlowCollector flowCollector, Continuation continuation2) {
                Object objCollect2 = flow.collect(new AnonymousClass2(flowCollector), continuation2);
                return objCollect2 == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect2 : Unit.INSTANCE;
            }
        }, new AnonymousClass4(null)).collect(new FlowCollector() { // from class: com.android.systemui.shade.ui.viewmodel.ShadeUserActionsViewModel.hydrateActions.5
            @Override // kotlinx.coroutines.flow.FlowCollector
            public final Object emit(Object obj, Continuation continuation2) {
                userActionsViewModel$$ExternalSyntheticLambda0.mo781invoke((Map) obj);
                return Unit.INSTANCE;
            }
        }, continuation);
        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
    }
}
