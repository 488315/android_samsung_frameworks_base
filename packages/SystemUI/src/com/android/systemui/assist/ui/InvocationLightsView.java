package com.android.systemui.assist.ui;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.View;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import com.android.keyguard.ClockEventController$zenModeCallback$1$$ExternalSyntheticOutline0;
import com.android.settingslib.Utils;
import com.android.systemui.R;
import com.android.systemui.navigationbar.NavigationBarController;
import com.android.systemui.navigationbar.NavigationBarTransitions;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public class InvocationLightsView extends View implements NavigationBarTransitions.DarkIntensityListener {
    public final ArrayList mAssistInvocationLights;
    public final int mDarkColor;
    public final PerimeterPathGuide mGuide;
    public final int mLightColor;
    public NavigationBarController mNavigationBarController;
    public final Paint mPaint;
    public final Path mPath;
    public boolean mRegistered;
    public final int[] mScreenLocation;
    public final boolean mUseNavBarColor;
    public final int mViewHeight;

    public InvocationLightsView(Context context) {
        this(context, null);
    }

    @Override // com.android.systemui.navigationbar.NavigationBarTransitions.DarkIntensityListener
    public final void onDarkIntensity(float f) {
        updateDarkness(f);
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        getLocationOnScreen(this.mScreenLocation);
        int[] iArr = this.mScreenLocation;
        canvas.translate(-iArr[0], -iArr[1]);
        if (this.mUseNavBarColor) {
            Iterator it = this.mAssistInvocationLights.iterator();
            while (it.hasNext()) {
                renderLight((EdgeLight) it.next(), canvas);
            }
        } else {
            this.mPaint.setStrokeCap(Paint.Cap.ROUND);
            renderLight((EdgeLight) this.mAssistInvocationLights.get(0), canvas);
            renderLight((EdgeLight) this.mAssistInvocationLights.get(3), canvas);
            this.mPaint.setStrokeCap(Paint.Cap.BUTT);
            renderLight((EdgeLight) this.mAssistInvocationLights.get(1), canvas);
            renderLight((EdgeLight) this.mAssistInvocationLights.get(2), canvas);
        }
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        getLayoutParams().height = this.mViewHeight;
        requestLayout();
    }

    @Override // android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        int rotation = getContext().getDisplay().getRotation();
        PerimeterPathGuide perimeterPathGuide = this.mGuide;
        if (rotation != perimeterPathGuide.mRotation) {
            if (rotation != 0 && rotation != 1 && rotation != 2 && rotation != 3) {
                ClockEventController$zenModeCallback$1$$ExternalSyntheticOutline0.m(rotation, "Invalid rotation provided: ", "PerimeterPathGuide");
            } else {
                perimeterPathGuide.mRotation = rotation;
                perimeterPathGuide.computeRegions();
            }
        }
    }

    public final void renderLight(EdgeLight edgeLight, Canvas canvas) {
        float f = edgeLight.mLength;
        float f2 = 0.0f;
        if (f > 0.0f) {
            PerimeterPathGuide perimeterPathGuide = this.mGuide;
            Path path = this.mPath;
            float f3 = edgeLight.mStart;
            float f4 = f + f3;
            perimeterPathGuide.getClass();
            path.reset();
            float f5 = ((f3 % 1.0f) + 1.0f) % 1.0f;
            float f6 = ((f4 % 1.0f) + 1.0f) % 1.0f;
            if (f5 > f6) {
                perimeterPathGuide.strokeSegmentInternal(path, f5, 1.0f);
            } else {
                f2 = f5;
            }
            perimeterPathGuide.strokeSegmentInternal(path, f2, f6);
            this.mPaint.setColor(edgeLight.mColor);
            canvas.drawPath(this.mPath, this.mPaint);
        }
    }

    public final void setLight(float f, float f2, int i) {
        if (i < 0 || i >= 4) {
            RecordingInputConnection$$ExternalSyntheticOutline0.m(i, "invalid invocation light index: ", "InvocationLightsView");
        }
        ((EdgeLight) this.mAssistInvocationLights.get(i)).setEndpoints(f, f2);
    }

    public final void updateDarkness(float f) {
        if (this.mUseNavBarColor) {
            int intValue = ((Integer) ArgbEvaluator.getInstance().evaluate(f, Integer.valueOf(this.mLightColor), Integer.valueOf(this.mDarkColor))).intValue();
            Iterator it = this.mAssistInvocationLights.iterator();
            boolean z = true;
            while (it.hasNext()) {
                EdgeLight edgeLight = (EdgeLight) it.next();
                boolean z2 = edgeLight.mColor != intValue;
                edgeLight.mColor = intValue;
                z &= z2;
            }
            if (z) {
                invalidate();
            }
        }
    }

    public InvocationLightsView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public InvocationLightsView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public InvocationLightsView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        int i3;
        int i4;
        this.mAssistInvocationLights = new ArrayList();
        Paint paint = new Paint();
        this.mPaint = paint;
        this.mPath = new Path();
        this.mScreenLocation = new int[2];
        this.mRegistered = false;
        this.mUseNavBarColor = true;
        context.getDisplay().getRealMetrics(new DisplayMetrics());
        int ceil = (int) Math.ceil(r2.density * 3.0f);
        paint.setStrokeWidth(ceil);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.MITER);
        paint.setAntiAlias(true);
        Display display = context.getDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getRealMetrics(displayMetrics);
        int rotation = display.getRotation();
        if (rotation != 0 && rotation != 2) {
            i3 = displayMetrics.heightPixels;
        } else {
            i3 = displayMetrics.widthPixels;
        }
        int i5 = i3;
        Display display2 = context.getDisplay();
        DisplayMetrics displayMetrics2 = new DisplayMetrics();
        display2.getRealMetrics(displayMetrics2);
        int rotation2 = display2.getRotation();
        if (rotation2 != 0 && rotation2 != 2) {
            i4 = displayMetrics2.widthPixels;
        } else {
            i4 = displayMetrics2.heightPixels;
        }
        this.mGuide = new PerimeterPathGuide(context, new CircularCornerPathRenderer(context), ceil / 2, i5, i4);
        int max = Math.max(DisplayUtils.getInvocationCornerRadius(context, true), DisplayUtils.getInvocationCornerRadius(context, false));
        context.getDisplay().getRealMetrics(new DisplayMetrics());
        this.mViewHeight = Math.max(max, (int) Math.ceil(3.0f * r12.density));
        int themeAttr = Utils.getThemeAttr(R.attr.darkIconTheme, ((View) this).mContext);
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(((View) this).mContext, Utils.getThemeAttr(R.attr.lightIconTheme, ((View) this).mContext));
        ContextThemeWrapper contextThemeWrapper2 = new ContextThemeWrapper(((View) this).mContext, themeAttr);
        this.mLightColor = Utils.getColorAttrDefaultColor(contextThemeWrapper, R.attr.singleToneColor, 0);
        this.mDarkColor = Utils.getColorAttrDefaultColor(contextThemeWrapper2, R.attr.singleToneColor, 0);
        for (int i6 = 0; i6 < 4; i6++) {
            this.mAssistInvocationLights.add(new EdgeLight(0, 0.0f, 0.0f));
        }
    }
}
