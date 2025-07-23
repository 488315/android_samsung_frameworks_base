package com.android.systemui.qs.ui.viewmodel;

import com.android.systemui.qs.panels.ui.viewmodel.EditModeViewModel;
import com.android.systemui.scene.ui.viewmodel.UserActionsViewModel;
import com.android.systemui.scene.ui.viewmodel.UserActionsViewModel$$ExternalSyntheticLambda0;
import java.util.Map;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class QuickSettingsShadeOverlayActionsViewModel extends UserActionsViewModel {
    public final EditModeViewModel editModeViewModel;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        QuickSettingsShadeOverlayActionsViewModel create();
    }

    public QuickSettingsShadeOverlayActionsViewModel(EditModeViewModel editModeViewModel) {
        this.editModeViewModel = editModeViewModel;
    }

    @Override // com.android.systemui.scene.ui.viewmodel.UserActionsViewModel
    public final Object hydrateActions(final UserActionsViewModel$$ExternalSyntheticLambda0 userActionsViewModel$$ExternalSyntheticLambda0, Continuation continuation) {
        final ReadonlyStateFlow readonlyStateFlow = this.editModeViewModel.isEditing;
        Object collect = new Flow() { // from class: com.android.systemui.qs.ui.viewmodel.QuickSettingsShadeOverlayActionsViewModel$hydrateActions$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r13, kotlin.coroutines.Continuation r14) {
                    /*
                        r12 = this;
                        boolean r0 = r14 instanceof com.android.systemui.qs.ui.viewmodel.QuickSettingsShadeOverlayActionsViewModel$hydrateActions$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r14
                        com.android.systemui.qs.ui.viewmodel.QuickSettingsShadeOverlayActionsViewModel$hydrateActions$$inlined$map$1$2$1 r0 = (com.android.systemui.qs.ui.viewmodel.QuickSettingsShadeOverlayActionsViewModel$hydrateActions$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.qs.ui.viewmodel.QuickSettingsShadeOverlayActionsViewModel$hydrateActions$$inlined$map$1$2$1 r0 = new com.android.systemui.qs.ui.viewmodel.QuickSettingsShadeOverlayActionsViewModel$hydrateActions$$inlined$map$1$2$1
                        r0.<init>(r14)
                    L18:
                        java.lang.Object r14 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r14)
                        goto L88
                    L27:
                        java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
                        java.lang.String r13 = "call to 'resume' before 'invoke' with coroutine"
                        r12.<init>(r13)
                        throw r12
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r14)
                        java.lang.Boolean r13 = (java.lang.Boolean) r13
                        boolean r13 = r13.booleanValue()
                        kotlin.collections.builders.MapBuilder r14 = new kotlin.collections.builders.MapBuilder
                        r14.<init>()
                        com.android.compose.animation.scene.Swipe$Companion r2 = com.android.compose.animation.scene.Swipe.Companion
                        r2.getClass()
                        com.android.compose.animation.scene.Swipe r4 = com.android.compose.animation.scene.Swipe.Up
                        com.android.compose.animation.scene.UserActionResult$HideOverlay r5 = new com.android.compose.animation.scene.UserActionResult$HideOverlay
                        com.android.compose.animation.scene.OverlayKey r6 = com.android.systemui.scene.shared.model.Overlays.QuickSettingsShade
                        r7 = 0
                        r8 = 0
                        r9 = 6
                        r10 = 0
                        r5.<init>(r6, r7, r8, r9, r10)
                        r14.put(r4, r5)
                        if (r13 != 0) goto L63
                        com.android.compose.animation.scene.Back r13 = com.android.compose.animation.scene.Back.INSTANCE
                        r7 = r6
                        com.android.compose.animation.scene.UserActionResult$HideOverlay r6 = new com.android.compose.animation.scene.UserActionResult$HideOverlay
                        r8 = 0
                        r9 = 0
                        r10 = 6
                        r11 = 0
                        r6.<init>(r7, r8, r9, r10, r11)
                        r14.put(r13, r6)
                    L63:
                        com.android.systemui.scene.ui.viewmodel.SceneContainerArea$TopEdgeStartHalf r13 = com.android.systemui.scene.ui.viewmodel.SceneContainerArea.TopEdgeStartHalf.INSTANCE
                        r4 = 3
                        r5 = 0
                        com.android.compose.animation.scene.Swipe r13 = com.android.compose.animation.scene.Swipe.Companion.m926DownloWS4t8$default(r2, r5, r13, r4)
                        com.android.compose.animation.scene.UserActionResult$ReplaceByOverlay r4 = new com.android.compose.animation.scene.UserActionResult$ReplaceByOverlay
                        com.android.compose.animation.scene.OverlayKey r5 = com.android.systemui.scene.shared.model.Overlays.NotificationsShade
                        r6 = 0
                        r7 = 0
                        r8 = 6
                        r9 = 0
                        r4.<init>(r5, r6, r7, r8, r9)
                        r14.put(r13, r4)
                        kotlin.collections.builders.MapBuilder r13 = r14.build()
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r12 = r12.$this_unsafeFlow
                        java.lang.Object r12 = r12.emit(r13, r0)
                        if (r12 != r1) goto L88
                        return r1
                    L88:
                        kotlin.Unit r12 = kotlin.Unit.INSTANCE
                        return r12
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.ui.viewmodel.QuickSettingsShadeOverlayActionsViewModel$hydrateActions$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation2) {
                Object collect2 = Flow.this.collect(new AnonymousClass2(flowCollector), continuation2);
                return collect2 == CoroutineSingletons.COROUTINE_SUSPENDED ? collect2 : Unit.INSTANCE;
            }
        }.collect(new FlowCollector() { // from class: com.android.systemui.qs.ui.viewmodel.QuickSettingsShadeOverlayActionsViewModel$hydrateActions$3
            @Override // kotlinx.coroutines.flow.FlowCollector
            public final Object emit(Object obj, Continuation continuation2) {
                Function1.this.mo779invoke((Map) obj);
                return Unit.INSTANCE;
            }
        }, continuation);
        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
    }
}
