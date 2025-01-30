package com.android.systemui.p032tv;

import android.app.Activity;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import com.android.systemui.R;
import java.util.Collections;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class TvBottomSheetActivity extends Activity {
    public Drawable mBackgroundWithBlur;
    public Drawable mBackgroundWithoutBlur;
    public final TvBottomSheetActivity$$ExternalSyntheticLambda1 mBlurConsumer = new Consumer() { // from class: com.android.systemui.tv.TvBottomSheetActivity$$ExternalSyntheticLambda1
        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            TvBottomSheetActivity tvBottomSheetActivity = TvBottomSheetActivity.this;
            tvBottomSheetActivity.getWindow().setBackgroundDrawable(((Boolean) obj).booleanValue() ? tvBottomSheetActivity.mBackgroundWithBlur : tvBottomSheetActivity.mBackgroundWithoutBlur);
        }
    };

    @Override // android.app.Activity
    public final void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.tv_bottom_sheet_exit);
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        getWindowManager().addCrossWindowBlurEnabledListener(this.mBlurConsumer);
    }

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.tv_bottom_sheet);
        overridePendingTransition(R.anim.tv_bottom_sheet_enter, 0);
        this.mBackgroundWithBlur = getResources().getDrawable(R.drawable.bottom_sheet_background_with_blur);
        this.mBackgroundWithoutBlur = getResources().getDrawable(R.drawable.bottom_sheet_background);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int i = displayMetrics.widthPixels;
        int i2 = displayMetrics.heightPixels;
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.bottom_sheet_margin);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.width = i - (dimensionPixelSize * 2);
        attributes.height = -2;
        attributes.gravity = 81;
        attributes.horizontalMargin = 0.0f;
        attributes.verticalMargin = dimensionPixelSize / i2;
        attributes.format = -2;
        attributes.type = 2008;
        attributes.flags = attributes.flags | 128 | 16777216;
        getWindow().setAttributes(attributes);
        getWindow().setElevation(getWindow().getElevation() + 5.0f);
        getWindow().setBackgroundBlurRadius(getResources().getDimensionPixelSize(R.dimen.bottom_sheet_background_blur_radius));
        final View findViewById = findViewById(R.id.bottom_sheet);
        findViewById.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.tv.TvBottomSheetActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
                findViewById.setUnrestrictedPreferKeepClearRects(Collections.singletonList(new Rect(0, 0, i5 - i3, i6 - i4)));
            }
        });
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public final void onDetachedFromWindow() {
        getWindowManager().removeCrossWindowBlurEnabledListener(this.mBlurConsumer);
        super.onDetachedFromWindow();
    }
}
