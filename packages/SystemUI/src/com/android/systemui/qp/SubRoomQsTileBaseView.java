package com.android.systemui.qp;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.keyguard.ClockEventController$zenModeCallback$1$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.tileimpl.QSIconViewImpl;
import com.android.systemui.qs.tileimpl.SecQSTileBaseView;

public final class SubRoomQsTileBaseView extends SecQSTileBaseView {
    public int mCircleColor;
    public int mColorActive;
    public int mColorDisabled;
    public int mColorInactive;
    public final QSIconViewImpl mQSIconViewImpl;
    public RippleDrawable mRipple;
    public QSTile.State mState;
    public Drawable mTileBackground;

    public SubRoomQsTileBaseView(Context context, QSIconViewImpl qSIconViewImpl, boolean z) {
        super(context, z, qSIconViewImpl);
        int dimension = (int) context.getResources().getDimension(R.dimen.subscreen_qs_tile_icon_size);
        this.mIconFrame.removeView(this.mBg);
        this.mIconFrame.removeView(this.mIcon);
        this.mIconFrame.addView(this.mBg, new FrameLayout.LayoutParams(dimension, dimension, 17));
        this.mIconFrame.addView(this.mIcon, new FrameLayout.LayoutParams(dimension, dimension, 17));
        this.mIconFrame.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
        Drawable newTileBackground = newTileBackground();
        this.mTileBackground = newTileBackground;
        if (newTileBackground instanceof RippleDrawable) {
            ((RippleDrawable) newTileBackground).setColor(ColorStateList.valueOf(context.getColor(R.color.sec_qs_ripple_background)));
            setRipple((RippleDrawable) this.mTileBackground);
        }
        this.mQSIconViewImpl = new QSIconViewImpl(context);
        this.mColorActive = ((LinearLayout) this).mContext.getColor(R.color.subscreen_qs_tile_round_background_on);
        this.mColorDisabled = ((LinearLayout) this).mContext.getColor(R.color.subscreen_qs_tile_round_background_off);
        this.mColorInactive = ((LinearLayout) this).mContext.getColor(R.color.subscreen_qs_tile_round_background_dim);
        updateSubscreenTileStroke();
        this.mIcon.setBackground(this.mTileBackground);
        this.mIcon.setFocusable(true);
        setFocusable(false);
        this.mIconFrame.setFocusable(false);
    }

    @Override // com.android.systemui.qs.tileimpl.SecQSTileBaseView
    public final Drawable getTileBackground() {
        return this.mTileBackground;
    }

    @Override // com.android.systemui.qs.tileimpl.SecQSTileBaseView
    public final void handleStateChanged(QSTile.State state) {
        int i;
        String str;
        super.handleStateChanged(state);
        this.mState = state;
        int i2 = state.state;
        if (i2 == 0) {
            i = this.mColorInactive;
        } else if (i2 == 1) {
            i = this.mColorDisabled;
        } else if (i2 != 2) {
            ClockEventController$zenModeCallback$1$$ExternalSyntheticOutline0.m(i2, "Invalid state ", "SubRoomQsTileBaseView");
            i = 0;
        } else {
            i = this.mColorActive;
        }
        if (i != this.mCircleColor) {
            this.mQSIconViewImpl.setTint(this.mBg, i);
            this.mCircleColor = i;
        }
        Drawable newTileBackground = newTileBackground();
        this.mTileBackground = newTileBackground;
        if (newTileBackground instanceof RippleDrawable) {
            ((RippleDrawable) newTileBackground).setColor(ColorStateList.valueOf(((LinearLayout) this).mContext.getColor(R.color.sec_qs_ripple_background)));
            setRipple((RippleDrawable) this.mTileBackground);
        }
        updateSubscreenTileStroke();
        ((ImageView) this.mIcon.mIcon).setColorFilter(((LinearLayout) this).mContext.getColor(state.state == 2 ? R.color.subscreen_qs_tile_icon_on_color : R.color.subscreen_qs_tile_icon_off_color), PorterDuff.Mode.SRC_IN);
        CharSequence charSequence = state.label;
        if (charSequence != null) {
            StringBuilder sb = new StringBuilder(charSequence.length());
            sb.append(state.label);
            str = sb.toString();
        } else {
            str = null;
        }
        if (state.contentDescription != null) {
            str = ((Object) state.contentDescription) + ",";
        } else if (str != null) {
            str = str + "," + getResources().getString(state.state == 2 ? R.string.accessibility_desc_on : R.string.accessibility_desc_off) + ",";
        }
        this.mIconFrame.setContentDescription(str);
    }

    @Override // com.android.systemui.qs.tileimpl.SecQSTileBaseView, android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mColorActive = ((LinearLayout) this).mContext.getColor(R.color.subscreen_qs_tile_round_background_on);
        this.mColorDisabled = ((LinearLayout) this).mContext.getColor(R.color.subscreen_qs_tile_round_background_off);
        this.mColorInactive = ((LinearLayout) this).mContext.getColor(R.color.subscreen_qs_tile_round_background_dim);
        QSTile.State state = this.mState;
        if (state != null) {
            handleStateChanged(state);
        }
    }

    public final void setRipple(RippleDrawable rippleDrawable) {
        this.mRipple = rippleDrawable;
        if (getWidth() != 0) {
            int measuredWidth = this.mIcon.getMeasuredWidth() / 2;
            int measuredHeight = this.mIcon.getMeasuredHeight() / 2;
            int height = (int) (this.mIcon.getHeight() * 0.8f);
            this.mRipple.setHotspotBounds(measuredWidth - height, measuredHeight - height, measuredWidth + height, measuredHeight + height);
        }
    }

    public final void updateSubscreenTileStroke() {
        Log.d("SubRoomQsTileBaseView", "updateSubscreenTileStroke");
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(1);
        gradientDrawable.setStroke(getContext().getResources().getDimensionPixelSize(R.dimen.subscreen_tile_stroke_width), getContext().getColor(R.color.subscreen_tile_stroke_color));
        this.mBg.setBackground(gradientDrawable);
    }
}
