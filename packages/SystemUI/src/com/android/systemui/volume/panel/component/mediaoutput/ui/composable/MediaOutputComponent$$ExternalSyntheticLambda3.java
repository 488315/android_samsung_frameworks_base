package com.android.systemui.volume.panel.component.mediaoutput.ui.composable;

import androidx.compose.animation.AnimatedContentKt;
import androidx.compose.animation.AnimatedContentTransitionScope;
import androidx.compose.animation.AnimatedContentTransitionScopeImpl;
import androidx.compose.animation.EnterExitTransitionKt;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.SnapSpec;
import com.android.systemui.volume.panel.component.mediaoutput.ui.viewmodel.DeviceIconViewModel;
import com.samsung.android.knox.license.KnoxEnterpriseLicenseManager;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class MediaOutputComponent$$ExternalSyntheticLambda3 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ MediaOutputComponent$$ExternalSyntheticLambda3(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return ((AnimatedContentTransitionScopeImpl) ((AnimatedContentTransitionScope) obj)).getTargetState() instanceof DeviceIconViewModel.IsPlaying ? AnimatedContentKt.togetherWith(EnterExitTransitionKt.m5scaleInL8ZKhE$default(AnimationSpecKt.tween$default(400, KnoxEnterpriseLicenseManager.ERROR_LICENSE_DEACTIVATED, null, 4), 0.9f, 4).plus(EnterExitTransitionKt.fadeIn$default(AnimationSpecKt.tween$default(400, KnoxEnterpriseLicenseManager.ERROR_LICENSE_DEACTIVATED, null, 4), 2)), EnterExitTransitionKt.fadeOut$default(AnimationSpecKt.snap$default(), 2)) : AnimatedContentKt.togetherWith(EnterExitTransitionKt.fadeIn$default(new SnapSpec(900), 2), EnterExitTransitionKt.m6scaleOutL8ZKhE$default(AnimationSpecKt.tween$default(400, 500, null, 4), 0.9f, 4).plus(EnterExitTransitionKt.fadeOut$default(AnimationSpecKt.tween$default(400, 500, null, 4), 2)));
            case 1:
                return ((DeviceIconViewModel) obj).getBackgroundColor();
            case 2:
                if (((AnimatedContentTransitionScopeImpl) ((AnimatedContentTransitionScope) obj)).getTargetState() instanceof DeviceIconViewModel.IsPlaying) {
                    return AnimatedContentKt.togetherWith(EnterExitTransitionKt.fadeIn$default(new SnapSpec(KnoxEnterpriseLicenseManager.ERROR_LICENSE_DEACTIVATED), 2), EnterExitTransitionKt.slideOutVertically(AnimationSpecKt.tween$default(400, 300, null, 4), new MediaOutputComponent$$ExternalSyntheticLambda3(4)).plus(EnterExitTransitionKt.fadeOut$default(AnimationSpecKt.tween$default(400, 300, null, 4), 2)));
                }
                return AnimatedContentKt.togetherWith(EnterExitTransitionKt.slideInVertically(new MediaOutputComponent$$ExternalSyntheticLambda3(4), AnimationSpecKt.tween$default(400, 900, null, 4)).plus(EnterExitTransitionKt.fadeIn$default(AnimationSpecKt.tween$default(400, 900, null, 4), 2)), EnterExitTransitionKt.fadeOut$default(AnimationSpecKt.tween$default(400, 500, null, 4), 2));
            case 3:
                return ((DeviceIconViewModel) obj).getIcon();
            default:
                Integer num = (Integer) obj;
                num.intValue();
                return num;
        }
    }
}
