package com.android.systemui.topwindoweffects.ui.compose;

import android.content.Context;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.platform.AbstractComposeView;
import com.android.systemui.compose.ComposeInitializer;
import com.android.systemui.topwindoweffects.ui.viewmodel.SqueezeEffectViewModel;
import kotlin.jvm.functions.Function0;

/* loaded from: classes3.dex */
public final class EffectsWindowRoot extends AbstractComposeView {
    public final Function0 onEffectFinished;
    public final SqueezeEffectViewModel.Factory viewModelFactory;

    public EffectsWindowRoot(Context context, Function0 function0, SqueezeEffectViewModel.Factory factory) {
        super(context, null, 0, 6, null);
        this.onEffectFinished = function0;
        this.viewModelFactory = factory;
    }

    @Override // androidx.compose.ui.platform.AbstractComposeView
    public final void Content(ComposerImpl composerImpl) {
        composerImpl.startReplaceGroup(-1607057601);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.topwindoweffects.ui.compose.EffectsWindowRoot.Content (EffectsWindowRoot.kt:43)");
        }
        SqueezeEffectKt.SqueezeEffect(this.viewModelFactory, this.onEffectFinished, null, composerImpl, 0);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
    }

    @Override // androidx.compose.ui.platform.AbstractComposeView, android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        ComposeInitializer.INSTANCE.getClass();
        ComposeInitializer.onAttachedToWindow(this);
        super.onAttachedToWindow();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ComposeInitializer.INSTANCE.getClass();
        ComposeInitializer.onDetachedFromWindow(this);
    }
}
