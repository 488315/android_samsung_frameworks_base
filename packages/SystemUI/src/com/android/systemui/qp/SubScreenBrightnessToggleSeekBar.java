package com.android.systemui.qp;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.SeekBar;
import android.widget.Toast;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean onTouchEvent(android.view.MotionEvent r6) {
        /*
            r5 = this;
            boolean r0 = r5.mIsDragging
            r1 = 1
            if (r0 != 0) goto L11
            boolean r0 = r5.mIsDetailViewTouched
            if (r0 == 0) goto L11
            int r0 = r6.getAction()
            if (r0 != 0) goto L11
            goto La0
        L11:
            boolean r0 = r5.mHighBrightnessModeEnter
            r2 = 0
            if (r0 == 0) goto L60
            com.android.systemui.Dependency r0 = com.android.systemui.Dependency.sDependency
            java.lang.Class<com.android.systemui.qp.util.SubscreenUtil> r3 = com.android.systemui.qp.util.SubscreenUtil.class
            java.lang.Object r0 = r0.getDependencyInner(r3)
            com.android.systemui.qp.util.SubscreenUtil r0 = (com.android.systemui.qp.util.SubscreenUtil) r0
            r0.getClass()
            boolean r0 = com.android.systemui.QpRune.QUICK_SUBSCREEN_PANEL
            if (r0 == 0) goto L60
            com.android.systemui.Dependency r0 = com.android.systemui.Dependency.sDependency
            java.lang.Class<com.android.systemui.util.SettingsHelper> r3 = com.android.systemui.util.SettingsHelper.class
            java.lang.Object r0 = r0.getDependencyInner(r3)
            com.android.systemui.util.SettingsHelper r0 = (com.android.systemui.util.SettingsHelper) r0
            int r0 = r0.getSubscreenBrightnessMode()
            if (r0 == 0) goto L60
            int r0 = r6.getAction()
            if (r0 != 0) goto L60
            java.lang.String r0 = "SubScreenBrightnessToggleSeekBar"
            java.lang.String r3 = "showHighBrightnessModeToast()"
            android.util.Log.d(r0, r3)
            android.widget.Toast r0 = r5.mHighBrightnessModeToast
            if (r0 != 0) goto L58
            android.content.Context r0 = r5.mContext
            r3 = 2131956210(0x7f1311f2, float:1.954897E38)
            java.lang.String r3 = r0.getString(r3)
            android.widget.Toast r0 = android.widget.Toast.makeText(r0, r3, r2)
            r5.mHighBrightnessModeToast = r0
        L58:
            android.widget.Toast r0 = r5.mHighBrightnessModeToast
            r0.show()
            r0 = 0
            r5.mHighBrightnessModeToast = r0
        L60:
            float r0 = r6.getX()
            float r3 = r6.getY()
            int r4 = r6.getAction()
            if (r4 == 0) goto La6
            if (r4 == r1) goto La1
            r2 = 2
            if (r4 == r2) goto L77
            r0 = 3
            if (r4 == r0) goto La1
            goto La0
        L77:
            boolean r2 = r5.mIsHorizontalGesture
            if (r2 == 0) goto L7c
            goto La1
        L7c:
            float r2 = r5.mInitialTouchY
            float r3 = r3 - r2
            float r2 = r5.mInitialTouchX
            float r0 = r0 - r2
            float r2 = java.lang.Math.abs(r3)
            float r0 = java.lang.Math.abs(r0)
            int r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r2 <= 0) goto La0
            android.content.Context r2 = r5.mContext
            android.view.ViewConfiguration r2 = android.view.ViewConfiguration.get(r2)
            int r2 = r2.getScaledTouchSlop()
            float r2 = (float) r2
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 <= 0) goto La0
            r5.mIsHorizontalGesture = r1
            goto La1
        La0:
            return r1
        La1:
            boolean r5 = super.onTouchEvent(r6)
            return r5
        La6:
            r5.mInitialTouchX = r0
            r5.mInitialTouchY = r3
            r5.mIsHorizontalGesture = r2
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qp.SubScreenBrightnessToggleSeekBar.onTouchEvent(android.view.MotionEvent):boolean");
    }
}
