package com.android.systemui.p014qp;

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
import com.android.systemui.p014qp.util.SubscreenUtil;
import com.android.systemui.util.SettingsHelper;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class SubScreenBrightnessToggleSeekBar extends SeekBar {
    public static boolean mHighBrightnessModeEnter = false;
    public final Context mContext;
    public Toast mHighBrightnessModeToast;
    public float mInitialTouchX;
    public float mInitialTouchY;
    public boolean mIsDetailViewTouched;
    public boolean mIsDragging;
    public boolean mIsHorizontalGesture;

    public SubScreenBrightnessToggleSeekBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIsHorizontalGesture = false;
        this.mContext = context;
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x007d, code lost:
    
        if (r4 != 3) goto L42;
     */
    @Override // android.widget.AbsSeekBar, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.mIsDragging && this.mIsDetailViewTouched && motionEvent.getAction() == 0) {
            return true;
        }
        boolean z = false;
        if (mHighBrightnessModeEnter) {
            ((SubscreenUtil) Dependency.get(SubscreenUtil.class)).getClass();
            if ((QpRune.QUICK_PANEL_SUBSCREEN && ((SettingsHelper) Dependency.get(SettingsHelper.class)).mItemLists.get("sub_screen_brightness_mode").getIntValue() != 0) && motionEvent.getAction() == 0) {
                Log.d("SubScreenBrightnessToggleSeekBar", "showHighBrightnessModeToast()");
                if (this.mHighBrightnessModeToast == null) {
                    Context context = this.mContext;
                    this.mHighBrightnessModeToast = Toast.makeText(context, context.getString(R.string.sec_brightness_slider_hbm_text), 0);
                }
                this.mHighBrightnessModeToast.show();
                this.mHighBrightnessModeToast = null;
            }
        }
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        int action = motionEvent.getAction();
        if (action != 0) {
            if (action != 1) {
                if (action == 2) {
                    if (!this.mIsHorizontalGesture) {
                        float f = y - this.mInitialTouchY;
                        float f2 = x - this.mInitialTouchX;
                        float abs = Math.abs(f);
                        float abs2 = Math.abs(f2);
                        if (abs2 > abs && abs2 > ViewConfiguration.get(this.mContext).getScaledTouchSlop()) {
                            this.mIsHorizontalGesture = true;
                        }
                    }
                }
            }
            z = true;
        } else {
            this.mInitialTouchX = x;
            this.mInitialTouchY = y;
            this.mIsHorizontalGesture = false;
        }
        if (z) {
            return super.onTouchEvent(motionEvent);
        }
        return true;
    }
}
