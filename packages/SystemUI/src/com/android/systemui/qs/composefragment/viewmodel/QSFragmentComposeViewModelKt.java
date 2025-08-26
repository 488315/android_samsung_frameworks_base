package com.android.systemui.qs.composefragment.viewmodel;

import com.android.systemui.media.controls.ui.view.MediaHost;
import com.android.systemui.util.animation.DisappearParameters;

/* loaded from: classes2.dex */
public abstract class QSFragmentComposeViewModelKt {
    public static final void access$applyDisappearParameters(MediaHost mediaHost, boolean z) {
        DisappearParameters disappearParameters = mediaHost.state.disappearParameters;
        disappearParameters.setFadeStartPosition(0.95f);
        disappearParameters.setDisappearStart(0.0f);
        if (z) {
            disappearParameters.getDisappearSize().set(0.0f, 0.4f);
            disappearParameters.getGonePivot().set(1.0f, 0.0f);
            disappearParameters.getContentTranslationFraction().set(0.25f, 1.0f);
            disappearParameters.setDisappearEnd(0.6f);
            return;
        }
        disappearParameters.getDisappearSize().set(1.0f, 0.0f);
        disappearParameters.getGonePivot().set(0.0f, 0.0f);
        disappearParameters.getContentTranslationFraction().set(0.0f, 1.0f);
        disappearParameters.setDisappearEnd(0.95f);
    }
}
