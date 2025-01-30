package com.android.systemui.qp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class SubscreenBrightnessTouchEnhancerView extends FrameLayout {
    public ImageView mBrightnessDetailContainerView;
    public final Context mContext;
    public SubScreenBrightnessToggleSeekBar mSlider;
    public boolean mTouchDownDetailView;

    public SubscreenBrightnessTouchEnhancerView(Context context) {
        this(context, null);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action != 0) {
            if (action != 1) {
                if (action == 2) {
                    if (isTouched(this.mBrightnessDetailContainerView, motionEvent.getRawX(), motionEvent.getRawY()) && this.mTouchDownDetailView) {
                        return true;
                    }
                    this.mSlider.mIsDragging = true;
                    if (this.mTouchDownDetailView) {
                        ImageView imageView = this.mBrightnessDetailContainerView;
                        if (imageView != null) {
                            imageView.setClickable(false);
                            this.mBrightnessDetailContainerView.setEnabled(false);
                        }
                        this.mSlider.mIsDetailViewTouched = true ^ this.mTouchDownDetailView;
                    }
                }
            } else {
                if (!this.mSlider.mIsDragging && this.mTouchDownDetailView && isTouched(this.mBrightnessDetailContainerView, motionEvent.getRawX(), motionEvent.getRawY())) {
                    ImageView imageView2 = this.mBrightnessDetailContainerView;
                    if (imageView2 != null) {
                        imageView2.setClickable(true);
                        this.mBrightnessDetailContainerView.setEnabled(true);
                        this.mBrightnessDetailContainerView.performClick();
                    }
                    this.mTouchDownDetailView = false;
                    SubScreenBrightnessToggleSeekBar subScreenBrightnessToggleSeekBar = this.mSlider;
                    subScreenBrightnessToggleSeekBar.mIsDetailViewTouched = false;
                    subScreenBrightnessToggleSeekBar.mIsDragging = false;
                    return true;
                }
                this.mTouchDownDetailView = false;
                SubScreenBrightnessToggleSeekBar subScreenBrightnessToggleSeekBar2 = this.mSlider;
                subScreenBrightnessToggleSeekBar2.mIsDetailViewTouched = false;
                subScreenBrightnessToggleSeekBar2.mIsDragging = false;
            }
        } else {
            if (!isTouched(this.mSlider, motionEvent.getRawX(), motionEvent.getRawY()) && !isTouched(this.mBrightnessDetailContainerView, motionEvent.getRawX(), motionEvent.getRawY())) {
                return true;
            }
            if (isTouched(this.mBrightnessDetailContainerView, motionEvent.getRawX(), motionEvent.getRawY())) {
                this.mTouchDownDetailView = true;
                this.mSlider.mIsDetailViewTouched = true;
            }
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public final boolean isTouched(View view, float f, float f2) {
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        if (MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m11m(this.mContext) == 1) {
            if (f >= iArr[0] || f <= r4 - view.getWidth()) {
                return false;
            }
            int i = iArr[1];
            return f2 < ((float) i) && f2 > ((float) (i - view.getHeight()));
        }
        if (f <= iArr[0] || f >= view.getWidth() + r4) {
            return false;
        }
        int i2 = iArr[1];
        return f2 > ((float) i2) && f2 < ((float) (view.getHeight() + i2));
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mSlider = (SubScreenBrightnessToggleSeekBar) findViewById(R.id.subroom_brightness_seekbar);
        this.mBrightnessDetailContainerView = (ImageView) findViewById(R.id.brightness_panel_more_icon);
    }

    public SubscreenBrightnessTouchEnhancerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
    }
}
