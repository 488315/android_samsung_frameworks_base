package com.android.systemui.scene.domain.interactor;

import com.android.compose.animation.scene.ContentKey;
import com.android.systemui.scene.shared.model.Overlays;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.statusbar.disableflags.domain.interactor.DisableFlagsInteractor;
import com.android.systemui.statusbar.disableflags.shared.model.DisableFlagsModel;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DisabledContentInteractor {
    public final DisableFlagsInteractor disableFlagsInteractor;

    public DisabledContentInteractor(DisableFlagsInteractor disableFlagsInteractor) {
        this.disableFlagsInteractor = disableFlagsInteractor;
    }

    public static boolean isDisabled$default(DisabledContentInteractor disabledContentInteractor, ContentKey contentKey) {
        DisableFlagsModel disableFlagsModel = (DisableFlagsModel) disabledContentInteractor.disableFlagsInteractor.disableFlags.$$delegate_0.getValue();
        disabledContentInteractor.getClass();
        if (Intrinsics.areEqual(contentKey, Scenes.Shade) || Intrinsics.areEqual(contentKey, Overlays.NotificationsShade)) {
            if ((disableFlagsModel.disable2 & 4) != 0) {
                return true;
            }
        } else if ((Intrinsics.areEqual(contentKey, Scenes.QuickSettings) || Intrinsics.areEqual(contentKey, Overlays.QuickSettingsShade)) && !disableFlagsModel.isQuickSettingsEnabled()) {
            return true;
        }
        return false;
    }
}
