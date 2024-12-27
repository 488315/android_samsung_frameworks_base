package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.Flags;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combineTransform$$inlined$combineTransformUnsafe$FlowKt__ZipKt$4;
import kotlinx.coroutines.flow.SafeFlow;

public final class AodAlphaViewModel {
    public final SafeFlow alpha;

    public AodAlphaViewModel(KeyguardTransitionInteractor keyguardTransitionInteractor, GoneToAodTransitionViewModel goneToAodTransitionViewModel, GoneToDozingTransitionViewModel goneToDozingTransitionViewModel, KeyguardInteractor keyguardInteractor, SceneInteractor sceneInteractor) {
        Flags.sceneContainer();
        FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new AodAlphaViewModel$alpha$5(null), goneToAodTransitionViewModel.enterFromTopAnimationAlpha);
        FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$12 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new AodAlphaViewModel$alpha$6(null), goneToDozingTransitionViewModel.lockscreenAlpha);
        FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$13 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new AodAlphaViewModel$alpha$7(null), keyguardInteractor.keyguardAlpha);
        this.alpha = new SafeFlow(new FlowKt__ZipKt$combineTransform$$inlined$combineTransformUnsafe$FlowKt__ZipKt$4(new Flow[]{keyguardTransitionInteractor.transitions, flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1, flowKt__EmittersKt$onStart$$inlined$unsafeFlow$12, flowKt__EmittersKt$onStart$$inlined$unsafeFlow$13}, null, new AodAlphaViewModel$alpha$8(null)));
    }
}
