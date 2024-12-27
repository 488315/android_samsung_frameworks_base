package com.android.systemui.controls.management.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.controls.SecControlInterface;
import com.android.systemui.controls.util.ControlsUtil;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SecControlHolder extends SecControlCommonHolder {
    public LottieAnimationView animationView;
    public final ControlsUtil controlsUtil;
    public final TextView subtitle;
    public final View view;

    public SecControlHolder(View view, int i, ControlsUtil controlsUtil, Function2 function2) {
        super(view, i, R.id.right_top_viewstub, controlsUtil, function2);
        this.view = view;
        this.controlsUtil = controlsUtil;
        TextView textView = (TextView) this.itemView.requireViewById(R.id.subtitle);
        ControlsUtil.Companion.getClass();
        ControlsUtil.Companion.updateFontSize(textView, R.dimen.sec_control_text_size, 1.1f);
        this.subtitle = textView;
        if (BasicRune.CONTROLS_SAMSUNG_STYLE_FOLD) {
            Context context = this.itemView.getContext();
            controlsUtil.getClass();
            if (ControlsUtil.isFoldDelta(context)) {
                textView.setTextSize(0, view.getResources().getDimension(R.dimen.control_text_size_fold));
            }
        }
    }

    @Override // com.android.systemui.controls.management.adapter.SecControlCommonHolder
    public final void resetForReuse() {
        super.resetForReuse();
        ImageView imageView = this.icon;
        imageView.setVisibility(0);
        imageView.setBackground(null);
        imageView.setAlpha(1.0f);
        LottieAnimationView lottieAnimationView = this.animationView;
        if (lottieAnimationView != null) {
            lottieAnimationView.setVisibility(8);
            lottieAnimationView.cancelAnimation();
        }
        ImageView imageView2 = this.overlayCustomIcon;
        if (imageView2 != null) {
            imageView2.setVisibility(8);
            imageView2.setImageDrawable(null);
        }
    }

    @Override // com.android.systemui.controls.management.adapter.SecControlCommonHolder
    public final void setContentDescription(CheckBox checkBox, TextView textView) {
        textView.setImportantForAccessibility(2);
        this.subtitle.setImportantForAccessibility(2);
        checkBox.setContentDescription(((Object) this.subtitle.getText()) + " " + ((Object) textView.getText()));
    }

    @Override // com.android.systemui.controls.management.adapter.SecControlCommonHolder
    public final void setSubtitleText(CharSequence charSequence) {
        this.subtitle.setText(charSequence);
    }

    @Override // com.android.systemui.controls.management.adapter.SecControlCommonHolder
    public final void updateLottieIcon(SecControlInterface secControlInterface) {
        Context context = this.itemView.getContext();
        ImageView imageView = this.icon;
        View view = this.view;
        LottieAnimationView lottieAnimationView = this.animationView;
        String lottieIconAnimationJson = secControlInterface.getLottieIconAnimationJson();
        String lottieIconAnimationJsonCache = secControlInterface.getLottieIconAnimationJsonCache();
        int lottieIconAnimationStartFrame = secControlInterface.getLottieIconAnimationStartFrame();
        int lottieIconAnimationEndFrame = secControlInterface.getLottieIconAnimationEndFrame();
        int lottieIconAnimationRepeatCount = secControlInterface.getLottieIconAnimationRepeatCount();
        this.controlsUtil.getClass();
        this.animationView = ControlsUtil.updateLottieIcon(context, imageView, view, lottieAnimationView, lottieIconAnimationJson, lottieIconAnimationJsonCache, lottieIconAnimationStartFrame, lottieIconAnimationEndFrame, lottieIconAnimationRepeatCount);
    }

    public static /* synthetic */ void getAnimationView$annotations() {
    }
}
