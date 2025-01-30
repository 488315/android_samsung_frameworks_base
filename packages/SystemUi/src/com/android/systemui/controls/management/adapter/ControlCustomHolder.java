package com.android.systemui.controls.management.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.controls.CustomControlInterface;
import com.android.systemui.controls.p005ui.util.ControlsUtil;
import com.android.systemui.controls.util.ControlsRuneWrapper;
import com.android.systemui.controls.util.ControlsRuneWrapperImpl;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ControlCustomHolder extends ControlCommonCustomHolder {
    public LottieAnimationView animationView;
    public final ControlsRuneWrapper controlsRuneWrapper;
    public final ControlsUtil controlsUtil;
    public final TextView subtitle;
    public final View view;

    public ControlCustomHolder(View view, int i, ControlsUtil controlsUtil, ControlsRuneWrapper controlsRuneWrapper, Function2 function2) {
        super(view, i, R.id.right_top_viewstub, controlsUtil, controlsRuneWrapper, function2);
        this.view = view;
        this.controlsUtil = controlsUtil;
        this.controlsRuneWrapper = controlsRuneWrapper;
        TextView textView = (TextView) this.itemView.requireViewById(R.id.subtitle);
        ControlsUtil.Companion.getClass();
        ControlsUtil.Companion.updateFontSize(textView, R.dimen.control_custom_text_size, 1.1f);
        this.subtitle = textView;
        if (BasicRune.CONTROLS_SAMSUNG_STYLE_FOLD) {
            Context context = this.itemView.getContext();
            controlsUtil.getClass();
            if (ControlsUtil.isFoldDelta(context)) {
                textView.setTextSize(0, view.getResources().getDimension(R.dimen.control_custom_text_size_fold));
            }
        }
    }

    @Override // com.android.systemui.controls.management.adapter.ControlCommonCustomHolder
    public final void resetForReuse() {
        ImageView imageView;
        LottieAnimationView lottieAnimationView;
        super.resetForReuse();
        ImageView imageView2 = this.icon;
        imageView2.setVisibility(0);
        imageView2.setBackground(null);
        imageView2.setAlpha(1.0f);
        if (BasicRune.CONTROLS_LOTTIE_ICON_ANIMATION && (lottieAnimationView = this.animationView) != null) {
            lottieAnimationView.setVisibility(8);
            lottieAnimationView.cancelAnimation();
        }
        ((ControlsRuneWrapperImpl) this.controlsRuneWrapper).getClass();
        if (!BasicRune.CONTROLS_OVERLAY_CUSTOM_ICON || (imageView = this.overlayCustomIcon) == null) {
            return;
        }
        imageView.setVisibility(8);
        imageView.setImageDrawable(null);
    }

    @Override // com.android.systemui.controls.management.adapter.ControlCommonCustomHolder
    public final void setContentDescription(CheckBox checkBox, TextView textView) {
        textView.setImportantForAccessibility(2);
        TextView textView2 = this.subtitle;
        textView2.setImportantForAccessibility(2);
        checkBox.setContentDescription(((Object) textView2.getText()) + " " + ((Object) textView.getText()));
    }

    @Override // com.android.systemui.controls.management.adapter.ControlCommonCustomHolder
    public final void setSubtitleText(CharSequence charSequence) {
        this.subtitle.setText(charSequence);
    }

    @Override // com.android.systemui.controls.management.adapter.ControlCommonCustomHolder
    public final void updateLottieIcon(CustomControlInterface customControlInterface) {
        Context context = this.itemView.getContext();
        ImageView imageView = this.icon;
        View view = this.view;
        LottieAnimationView lottieAnimationView = this.animationView;
        String customIconAnimationJson = customControlInterface.getCustomIconAnimationJson();
        String customIconAnimationJsonCache = customControlInterface.getCustomIconAnimationJsonCache();
        int customIconAnimationStartFrame = customControlInterface.getCustomIconAnimationStartFrame();
        int customIconAnimationEndFrame = customControlInterface.getCustomIconAnimationEndFrame();
        int customIconAnimationRepeatCount = customControlInterface.getCustomIconAnimationRepeatCount();
        this.controlsUtil.getClass();
        this.animationView = ControlsUtil.updateLottieIcon(context, imageView, view, lottieAnimationView, customIconAnimationJson, customIconAnimationJsonCache, customIconAnimationStartFrame, customIconAnimationEndFrame, customIconAnimationRepeatCount);
    }

    public static /* synthetic */ void getAnimationView$annotations() {
    }
}
