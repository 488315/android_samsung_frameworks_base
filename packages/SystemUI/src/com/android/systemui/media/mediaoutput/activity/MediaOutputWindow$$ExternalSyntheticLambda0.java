package com.android.systemui.media.mediaoutput.activity;

import android.view.WindowInsets;
import android.view.WindowManager;
import com.android.systemui.R;
import com.android.systemui.media.mediaoutput.activity.MediaOutputWindow;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class MediaOutputWindow$$ExternalSyntheticLambda0 implements Function0 {
    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        MediaOutputWindow.Companion companion = MediaOutputWindow.Companion;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.width = -1;
        layoutParams.height = -1;
        layoutParams.type = 2038;
        layoutParams.flags = 0;
        layoutParams.format = -3;
        layoutParams.layoutInDisplayCutoutMode = 1;
        layoutParams.setFitInsetsTypes(layoutParams.getFitInsetsTypes() - WindowInsets.Type.systemBars());
        layoutParams.setTitle("MediaOutputWindow");
        layoutParams.windowAnimations = R.style.AnimationFade;
        return layoutParams;
    }
}
