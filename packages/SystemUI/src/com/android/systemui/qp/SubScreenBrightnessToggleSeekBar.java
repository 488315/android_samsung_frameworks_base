package com.android.systemui.qp;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.SeekBar;
import android.widget.Toast;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.qp.util.SubscreenUtil;
import com.android.systemui.util.SettingsHelper;

/* loaded from: classes2.dex */
public class SubScreenBrightnessToggleSeekBar extends SeekBar {
    public boolean mHighBrightnessModeEnter;
    public Toast mHighBrightnessModeToast;
    public float mInitialTouchX;
    public float mInitialTouchY;
    public boolean mIsDetailViewTouched;
    public boolean mIsDragging;
    public boolean mIsHorizontalGesture;

    public SubScreenBrightnessToggleSeekBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIsHorizontalGesture = false;
        this.mHighBrightnessModeEnter = false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x0074, code lost:
    
        if (r4 != 3) goto L37;
     */
    @Override // android.widget.AbsSeekBar, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mIsDragging || !this.mIsDetailViewTouched || motionEvent.getAction() != 0) {
            if (this.mHighBrightnessModeEnter) {
                ((SubscreenUtil) Dependency.sDependency.getDependencyInner(SubscreenUtil.class)).getClass();
                if (QpRune.QUICK_SUBSCREEN_PANEL && ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).getSubscreenBrightnessMode() != 0 && motionEvent.getAction() == 0) {
                    Log.d("SubScreenBrightnessToggleSeekBar", "showHighBrightnessModeToast()");
                    if (this.mHighBrightnessModeToast == null) {
                        Context context = ((SeekBar) this).mContext;
                        this.mHighBrightnessModeToast = Toast.makeText(context, context.getString(R.string.sec_brightness_slider_hbm_text), 0);
                    }
                    this.mHighBrightnessModeToast.show();
                    this.mHighBrightnessModeToast = null;
                }
            }
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            int action = motionEvent.getAction();
            if (action == 0) {
                this.mInitialTouchX = x;
                this.mInitialTouchY = y;
                this.mIsHorizontalGesture = false;
                return true;
            }
            if (action != 1) {
                if (action == 2) {
                    if (!this.mIsHorizontalGesture) {
                        float f = y - this.mInitialTouchY;
                        float f2 = x - this.mInitialTouchX;
                        float fAbs = Math.abs(f);
                        float fAbs2 = Math.abs(f2);
                        if (fAbs2 > fAbs && fAbs2 > ViewConfiguration.get(((SeekBar) this).mContext).getScaledTouchSlop()) {
                            this.mIsHorizontalGesture = true;
                        }
                    }
                }
            }
            return super.onTouchEvent(motionEvent);
        }
        return true;
    }
}
