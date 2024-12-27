package com.android.systemui.settings.brightness;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.util.Log;
import com.android.systemui.R;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.util.function.Supplier;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SecBrightnessSliderView {
    public int dualSeekBarThreshold;
    public boolean highBrightnessModeEnter;
    public boolean isGradient;
    public final Supplier sliderSupplier;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public SecBrightnessSliderView(Supplier<ToggleSeekBar> supplier) {
        this.sliderSupplier = supplier;
        updateSliderResources();
    }

    public final ToggleSeekBar getSlider() {
        ToggleSeekBar toggleSeekBar = (ToggleSeekBar) this.sliderSupplier.get();
        if (toggleSeekBar == null) {
            Log.w("SecBrightnessSliderView", "slider: maybe BrightnessSliderView's onFinishInflate() is NOT called yet");
        }
        return toggleSeekBar;
    }

    public final void setDualSeekBarResources(boolean z) {
        if (((ToggleSeekBar) this.sliderSupplier.get()) == null || this.isGradient == z) {
            return;
        }
        this.isGradient = z;
        ToggleSeekBar slider = getSlider();
        Drawable progressDrawable = slider != null ? slider.getProgressDrawable() : null;
        LayerDrawable layerDrawable = progressDrawable instanceof LayerDrawable ? (LayerDrawable) progressDrawable : null;
        Object drawable = layerDrawable != null ? layerDrawable.getDrawable(1) : null;
        TransitionDrawable transitionDrawable = drawable instanceof TransitionDrawable ? (TransitionDrawable) drawable : null;
        if (transitionDrawable != null) {
            if (z) {
                transitionDrawable.startTransition(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend);
            } else {
                transitionDrawable.reverseTransition(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend);
            }
        }
    }

    public final void updateSliderResources() {
        ToggleSeekBar toggleSeekBar = (ToggleSeekBar) this.sliderSupplier.get();
        if (toggleSeekBar != null) {
            int floor = (int) Math.floor((toggleSeekBar.getMax() * toggleSeekBar.getContext().getResources().getInteger(R.integer.sec_brightness_slider_warning_percent)) / 100);
            this.dualSeekBarThreshold = floor;
            setDualSeekBarResources(floor <= toggleSeekBar.getProgress());
            Unit unit = Unit.INSTANCE;
        }
    }
}
