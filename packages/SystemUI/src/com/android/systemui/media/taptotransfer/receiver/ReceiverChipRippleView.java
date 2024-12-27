package com.android.systemui.media.taptotransfer.receiver;

import android.content.Context;
import android.util.AttributeSet;
import com.android.systemui.surfaceeffects.ripple.RippleShader;
import com.android.systemui.surfaceeffects.ripple.RippleView;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class ReceiverChipRippleView extends RippleView {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean isStarted;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public ReceiverChipRippleView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setupShader(RippleShader.RippleShape.CIRCLE);
        RippleShader rippleShader = this.rippleShader;
        RippleShader rippleShader2 = rippleShader != null ? rippleShader : null;
        RippleShader.FadeParams fadeParams = rippleShader2.baseRingFadeParams;
        fadeParams.fadeOutStart = 1.0f;
        fadeParams.fadeOutEnd = 1.0f;
        RippleShader.FadeParams fadeParams2 = rippleShader2.centerFillFadeParams;
        fadeParams2.fadeInStart = 0.0f;
        fadeParams2.fadeInEnd = 0.0f;
        fadeParams2.fadeOutStart = 1.0f;
        fadeParams2.fadeOutEnd = 1.0f;
        (rippleShader == null ? null : rippleShader).setFloatUniform("in_sparkle_strength", 0.0f);
        this.isStarted = false;
    }

    public static final RippleShader access$getRippleShader(ReceiverChipRippleView receiverChipRippleView) {
        RippleShader rippleShader = receiverChipRippleView.rippleShader;
        if (rippleShader != null) {
            return rippleShader;
        }
        return null;
    }
}
