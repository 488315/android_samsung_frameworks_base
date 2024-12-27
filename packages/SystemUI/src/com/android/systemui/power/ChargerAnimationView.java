package com.android.systemui.power;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.android.systemui.FontSizeUtils;
import com.android.systemui.R;

public class ChargerAnimationView extends RelativeLayout {
    public AnimatorSet mAlphaAnimatorSet;
    public ChargerAnimationListener mAnimationListener;
    public boolean mAnimationPlaying;
    public LinearLayout mBackGroundView;
    public TextView mBatteryLevelTextView;
    public LinearLayout mBatteryTextContainer;
    public LottieAnimationView mChargerAnimationView;
    public RelativeLayout mChargerContainer;
    public ImageView mChargingIconView;
    public ImageView mCircleBackgroundView;
    public final Context mContext;
    public int mCurrentBatteryLevel;
    public DozeChargingHelper mDozeChargingHelper;
    public ObjectAnimator mFadeInAnimation;
    public boolean mIsSubscreenOff;
    public boolean mNeedFullScreenBlur;
    public TextView mPercentTextView;
    public TextView mPercentTextViewRtl;
    public int mSuperFastChargingType;

    public ChargerAnimationView(Context context) {
        this(context, null);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mBackGroundView = (LinearLayout) findViewById(R.id.backgroundArea);
        this.mBatteryTextContainer = (LinearLayout) findViewById(R.id.charging_content_container);
        this.mChargerAnimationView = (LottieAnimationView) findViewById(R.id.charger_animation_view);
        this.mChargerContainer = (RelativeLayout) findViewById(R.id.charger_animation_container);
        this.mBatteryLevelTextView = (TextView) findViewById(R.id.battery_level);
        this.mPercentTextView = (TextView) findViewById(R.id.battery_level_unit);
        this.mPercentTextViewRtl = (TextView) findViewById(R.id.battery_level_unit_rtl);
        this.mChargingIconView = (ImageView) findViewById(R.id.charging_icon);
        this.mCircleBackgroundView = (ImageView) findViewById(R.id.charger_animation_background_blur_view);
        this.mAlphaAnimatorSet = new AnimatorSet();
        setBatteryLevelText();
    }

    public final void setBatteryLevelText() {
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.charger_anim_text_margin_start);
        FontSizeUtils.updateFontSize(this.mBatteryLevelTextView, R.dimen.charger_anim_battery_text_size);
        this.mBatteryLevelTextView.setText(String.format("%d", Integer.valueOf(this.mCurrentBatteryLevel)));
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mBatteryTextContainer.getLayoutParams();
        if (ChargerAnimationUtil.checkExceptionalLanguage()) {
            marginLayoutParams.setMarginEnd(dimensionPixelSize);
            this.mPercentTextViewRtl.setText("%");
            this.mPercentTextViewRtl.setVisibility(0);
            this.mPercentTextView.setVisibility(8);
        } else {
            marginLayoutParams.setMarginStart(dimensionPixelSize);
            this.mPercentTextView.setText("%");
            this.mPercentTextView.setVisibility(0);
            this.mPercentTextViewRtl.setVisibility(8);
        }
        this.mBatteryTextContainer.setLayoutParams(marginLayoutParams);
    }

    public ChargerAnimationView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ChargerAnimationView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mNeedFullScreenBlur = false;
        this.mAnimationPlaying = false;
        this.mCurrentBatteryLevel = 0;
        this.mIsSubscreenOff = false;
        this.mDozeChargingHelper = null;
        this.mContext = context;
    }
}
