package com.android.systemui.reardisplay;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.haptics.slider.HapticSlider;
import com.android.systemui.haptics.slider.HapticSliderPlugin;
import com.android.systemui.haptics.slider.HapticSliderViewBinder;
import com.android.systemui.haptics.slider.SeekableSliderTrackerConfig;
import com.android.systemui.haptics.slider.SliderHapticFeedbackConfig;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.util.time.SystemClock;
import com.google.android.msdl.domain.MSDLPlayer;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

/* loaded from: classes2.dex */
public final class RearDisplayInnerDialogDelegate implements SystemUIDialog.Delegate {
    public final MSDLPlayer msdlPlayer;
    public final Runnable onCanceledRunnable;
    public final Context rearDisplayContext;
    public final SliderHapticFeedbackConfig sliderHapticFeedbackConfig = new SliderHapticFeedbackConfig(1.0f, 1.0f, 0.0f, 0.2f, 0.25f, 0.0f, 0.05f, 5, 200.0f, 0, 1.0f, 0.05f, 1.1235955f, 0.0f, null, NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT, null);
    public final SeekableSliderTrackerConfig sliderTrackerConfig = new SeekableSliderTrackerConfig(100, 0.02f, 0.01f, 0.99f);
    public final SystemClock systemClock;
    public final SystemUIDialog.Factory systemUIDialogFactory;
    public final boolean touchExplorationEnabled;
    public final VibratorHelper vibratorHelper;

    public interface Factory {
        RearDisplayInnerDialogDelegate create(Context context, Runnable runnable, boolean z);
    }

    public final class SeekBarListener implements SeekBar.OnSeekBarChangeListener {
        public final HapticSliderPlugin hapticSliderPlugin;
        public int lastProgress;
        public final Runnable onCanceledRunnable;
        public int secondLastProgress;

        public SeekBarListener(HapticSliderPlugin hapticSliderPlugin, Runnable runnable) {
            this.hapticSliderPlugin = hapticSliderPlugin;
            this.onCanceledRunnable = runnable;
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            this.hapticSliderPlugin.onProgressChanged(i, z);
            if (i == 100 && this.lastProgress != 0) {
                this.onCanceledRunnable.run();
            }
            this.secondLastProgress = this.lastProgress;
            this.lastProgress = i;
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onStartTrackingTouch(SeekBar seekBar) {
            HapticSliderPlugin hapticSliderPlugin = this.hapticSliderPlugin;
            if (hapticSliderPlugin.isTracking()) {
                hapticSliderPlugin.sliderEventProducer.onStartTracking(true);
            }
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onStopTrackingTouch(SeekBar seekBar) {
            HapticSliderPlugin hapticSliderPlugin = this.hapticSliderPlugin;
            if (hapticSliderPlugin.isTracking()) {
                hapticSliderPlugin.sliderEventProducer.onStopTracking(true);
            }
            if (this.lastProgress < 100 || this.secondLastProgress == 0) {
                this.lastProgress = 0;
                this.secondLastProgress = 0;
                if (seekBar != null) {
                    seekBar.setProgress(0);
                }
            }
        }
    }

    public RearDisplayInnerDialogDelegate(SystemUIDialog.Factory factory, Context context, boolean z, VibratorHelper vibratorHelper, MSDLPlayer mSDLPlayer, SystemClock systemClock, Runnable runnable) {
        this.systemUIDialogFactory = factory;
        this.rearDisplayContext = context;
        this.touchExplorationEnabled = z;
        this.vibratorHelper = vibratorHelper;
        this.msdlPlayer = mSDLPlayer;
        this.systemClock = systemClock;
        this.onCanceledRunnable = runnable;
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog.Delegate
    public final SystemUIDialog createDialog() {
        Context context = this.rearDisplayContext;
        SystemUIDialog.Factory factory = this.systemUIDialogFactory;
        factory.getClass();
        int i = SystemUIDialog.$r8$clinit;
        return factory.create(this, context, R.style.Theme_SystemUI_Dialog, false);
    }

    @Override // com.android.systemui.statusbar.phone.DialogDelegate
    public final void onCreate(Dialog dialog, Bundle bundle) {
        SystemUIDialog systemUIDialog = (SystemUIDialog) dialog;
        systemUIDialog.setContentView(R.layout.activity_rear_display_enabled);
        systemUIDialog.setCanceledOnTouchOutside(false);
        Button button = (Button) systemUIDialog.requireViewById(R.id.cancel_button);
        boolean z = this.touchExplorationEnabled;
        if (z) {
            button.setVisibility(0);
            button.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.reardisplay.RearDisplayInnerDialogDelegate$onCreate$1$1$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.this$0.onCanceledRunnable.run();
                }
            });
        }
        TextView textView = (TextView) systemUIDialog.requireViewById(R.id.seekbar_instructions);
        if (z) {
            textView.setVisibility(8);
        }
        SeekBar seekBar = (SeekBar) systemUIDialog.requireViewById(R.id.seekbar);
        if (z) {
            seekBar.setVisibility(8);
            return;
        }
        seekBar.getClass();
        final HapticSliderPlugin hapticSliderPlugin = new HapticSliderPlugin(this.vibratorHelper, this.msdlPlayer, this.systemClock, new HapticSlider.SeekBar(seekBar), this.sliderHapticFeedbackConfig, this.sliderTrackerConfig);
        HapticSliderViewBinder.bind(seekBar, hapticSliderPlugin);
        seekBar.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.reardisplay.RearDisplayInnerDialogDelegate$onCreate$1$3$1
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                hapticSliderPlugin.onTouchEvent(motionEvent);
                return false;
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBarListener(hapticSliderPlugin, this.onCanceledRunnable));
    }
}
