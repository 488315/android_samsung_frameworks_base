package com.android.keyguard.clock;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.internal.colorextraction.ColorExtractor;
import com.android.systemui.R;
import com.android.systemui.colorextraction.SysuiColorExtractor;
import com.android.systemui.plugins.ClockPlugin;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.TimeZone;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DefaultClockController implements ClockPlugin {
    public final SysuiColorExtractor mColorExtractor;
    public final LayoutInflater mLayoutInflater;
    public final ViewPreviewer mRenderer = new ViewPreviewer();
    public final Resources mResources;
    public TextView mTextDate;
    public TextView mTextTime;
    public View mView;

    public DefaultClockController(Resources resources, LayoutInflater layoutInflater, SysuiColorExtractor sysuiColorExtractor) {
        this.mResources = resources;
        this.mLayoutInflater = layoutInflater;
        this.mColorExtractor = sysuiColorExtractor;
    }

    @Override // com.android.systemui.plugins.ClockPlugin
    public final View getBigClockView() {
        if (this.mView == null) {
            View inflate = this.mLayoutInflater.inflate(R.layout.default_clock_preview, (ViewGroup) null);
            this.mView = inflate;
            this.mTextTime = (TextView) inflate.findViewById(R.id.time);
            this.mTextDate = (TextView) this.mView.findViewById(R.id.date);
        }
        return this.mView;
    }

    @Override // com.android.systemui.plugins.ClockPlugin
    public final String getName() {
        return "default";
    }

    @Override // com.android.systemui.plugins.ClockPlugin
    public final int getPreferredY(int i) {
        return i / 2;
    }

    @Override // com.android.systemui.plugins.ClockPlugin
    public final Bitmap getPreview(int i, int i2) {
        View bigClockView = getBigClockView();
        setTextColor(-1);
        ColorExtractor.GradientColors colors = this.mColorExtractor.getColors(2);
        colors.supportsDarkText();
        colors.getColorPalette();
        ViewPreviewer viewPreviewer = this.mRenderer;
        viewPreviewer.getClass();
        if (bigClockView == null) {
            return null;
        }
        FutureTask futureTask = new FutureTask(new Callable() { // from class: com.android.keyguard.clock.ViewPreviewer.1
            public final /* synthetic */ int val$height;
            public final /* synthetic */ View val$view;
            public final /* synthetic */ int val$width;

            public CallableC08581(int i3, int i22, View bigClockView2) {
                r2 = i3;
                r3 = i22;
                r4 = bigClockView2;
            }

            @Override // java.util.concurrent.Callable
            public final Object call() {
                Bitmap.Config config = Bitmap.Config.ARGB_8888;
                int i3 = r2;
                int i4 = r3;
                Bitmap createBitmap = Bitmap.createBitmap(i3, i4, config);
                Canvas canvas = new Canvas(createBitmap);
                canvas.drawColor(EmergencyPhoneWidget.BG_COLOR);
                ViewPreviewer.this.getClass();
                View view = r4;
                ViewPreviewer.dispatchVisibilityAggregated(view, true);
                view.measure(View.MeasureSpec.makeMeasureSpec(i3, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS), View.MeasureSpec.makeMeasureSpec(i4, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS));
                view.layout(0, 0, i3, i4);
                view.draw(canvas);
                return createBitmap;
            }
        });
        if (Looper.myLooper() == Looper.getMainLooper()) {
            futureTask.run();
        } else {
            viewPreviewer.mMainHandler.post(futureTask);
        }
        try {
            return (Bitmap) futureTask.get();
        } catch (Exception e) {
            Log.e("ViewPreviewer", "Error completing task", e);
            return null;
        }
    }

    @Override // com.android.systemui.plugins.ClockPlugin
    public final Bitmap getThumbnail() {
        return BitmapFactory.decodeResource(this.mResources, R.drawable.default_thumbnail);
    }

    @Override // com.android.systemui.plugins.ClockPlugin
    public final String getTitle() {
        return this.mResources.getString(R.string.clock_title_default);
    }

    @Override // com.android.systemui.plugins.ClockPlugin
    public final View getView() {
        return null;
    }

    @Override // com.android.systemui.plugins.ClockPlugin
    public final void onDestroyView() {
        this.mView = null;
        this.mTextTime = null;
        this.mTextDate = null;
    }

    @Override // com.android.systemui.plugins.ClockPlugin
    public final void setTextColor(int i) {
        this.mTextTime.setTextColor(i);
        this.mTextDate.setTextColor(i);
    }

    @Override // com.android.systemui.plugins.ClockPlugin
    public final boolean shouldShowStatusArea() {
        return true;
    }

    @Override // com.android.systemui.plugins.ClockPlugin
    public final void onTimeZoneChanged(TimeZone timeZone) {
    }

    @Override // com.android.systemui.plugins.ClockPlugin
    public final void setDarkAmount(float f) {
    }

    @Override // com.android.systemui.plugins.ClockPlugin
    public final void setStyle(Paint.Style style) {
    }

    @Override // com.android.systemui.plugins.ClockPlugin
    public final void onTimeTick() {
    }

    @Override // com.android.systemui.plugins.ClockPlugin
    public final void setColorPalette(boolean z, int[] iArr) {
    }
}
