package com.android.systemui.qs.ui.viewmodel;

import com.android.compose.animation.scene.Back;
import com.android.compose.animation.scene.OverlayKey;
import com.android.compose.animation.scene.Swipe;
import com.android.compose.animation.scene.UserActionResult;
import com.android.systemui.qs.panels.ui.viewmodel.EditModeViewModel;
import com.android.systemui.scene.shared.model.Overlays;
import com.android.systemui.scene.ui.viewmodel.SceneContainerArea;
import com.android.systemui.scene.ui.viewmodel.UserActionsViewModel;
import com.android.systemui.scene.ui.viewmodel.UserActionsViewModel$$ExternalSyntheticLambda0;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.builders.MapBuilder;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes2.dex */
public final class QuickSettingsShadeOverlayActionsViewModel extends UserActionsViewModel {
    public final EditModeViewModel editModeViewModel;

    public interface Factory {
        QuickSettingsShadeOverlayActionsViewModel create();
    }

    public QuickSettingsShadeOverlayActionsViewModel(EditModeViewModel editModeViewModel) {
        this.editModeViewModel = editModeViewModel;
    }

    @Override // com.android.systemui.scene.ui.viewmodel.UserActionsViewModel
    public final Object hydrateActions(final UserActionsViewModel$$ExternalSyntheticLambda0 userActionsViewModel$$ExternalSyntheticLambda0, Continuation continuation) {
        final ReadonlyStateFlow readonlyStateFlow = this.editModeViewModel.isEditing;
        Object objCollect = new Flow() { // from class: com.android.systemui.qs.ui.viewmodel.QuickSettingsShadeOverlayActionsViewModel$hydrateActions$$inlined$map$1

            /* renamed from: com.android.systemui.qs.ui.viewmodel.QuickSettingsShadeOverlayActionsViewModel$hydrateActions$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.qs.ui.viewmodel.QuickSettingsShadeOverlayActionsViewModel$hydrateActions$$inlined$map$1$2$1, reason: invalid class name */
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
                        boolean zBooleanValue = ((Boolean) obj).booleanValue();
                        MapBuilder mapBuilder = new MapBuilder();
                        Swipe.Companion companion = Swipe.Companion;
                        companion.getClass();
                        Swipe swipe = Swipe.Up;
                        OverlayKey overlayKey = Overlays.QuickSettingsShade;
                        mapBuilder.put(swipe, new UserActionResult.HideOverlay(overlayKey, null, false, 6, null));
                        if (!zBooleanValue) {
                            mapBuilder.put(Back.INSTANCE, new UserActionResult.HideOverlay(overlayKey, null, false, 6, null));
                        }
                        mapBuilder.put(Swipe.Companion.m928DownloWS4t8$default(companion, 0, SceneContainerArea.TopEdgeStartHalf.INSTANCE, 3), new UserActionResult.ReplaceByOverlay(Overlays.NotificationsShade, null, false, 6, null));
                        MapBuilder mapBuilderBuild = mapBuilder.build();
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(mapBuilderBuild, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect2 = readonlyStateFlow.collect(new AnonymousClass2(flowCollector), continuation2);
                return objCollect2 == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect2 : Unit.INSTANCE;
            }
        }.collect(new FlowCollector() { // from class: com.android.systemui.qs.ui.viewmodel.QuickSettingsShadeOverlayActionsViewModel.hydrateActions.3
            @Override // kotlinx.coroutines.flow.FlowCollector
            public final Object emit(Object obj, Continuation continuation2) {
                userActionsViewModel$$ExternalSyntheticLambda0.mo781invoke((Map) obj);
                return Unit.INSTANCE;
            }
        }, continuation);
        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
    }
}
