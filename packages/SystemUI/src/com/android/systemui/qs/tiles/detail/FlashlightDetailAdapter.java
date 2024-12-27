package com.android.systemui.qs.tiles.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.plugins.qs.DetailAdapter;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.tiles.FlashlightTile;
import com.android.systemui.statusbar.policy.FlashlightController;
import com.android.systemui.statusbar.policy.FlashlightControllerImpl;
import com.android.systemui.statusbar.policy.SecFlashlightControllerImpl;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;

public final class FlashlightDetailAdapter implements DetailAdapter {
    public final Context mContext;
    public final FlashlightController mFlashlightController;
    public final FlashlightTile mFlashlightTile;
    public final boolean mIsLowBattery;
    public final SecFlashlightControllerImpl mSecFlashlightController;
    private final SettingsHelper mSettingsHelper;
    public final QSTile.BooleanState mState;
    public final Handler mUiHandler;
    public SeekBar mSlider = null;
    public TextView mWarningTextView = null;
    public final AnonymousClass3 torchLevelChangedListener = new SeekBar.OnSeekBarChangeListener() { // from class: com.android.systemui.qs.tiles.detail.FlashlightDetailAdapter.3
        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            if (z) {
                int i2 = i + 1;
                FlashlightDetailAdapter.this.mSecFlashlightController.setFlashlightLevel(i2, false);
                FlashlightDetailAdapter.this.mSlider.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(41));
                TextView textView = FlashlightDetailAdapter.this.mWarningTextView;
                if (textView != null) {
                    textView.setVisibility(i < 3 ? 8 : 0);
                }
                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_FLASH_LIGHT_SLIDER, i);
                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), "QPDS1014", i2);
                if (FlashlightDetailAdapter.this.mSettingsHelper.isVoiceAssistantEnabled()) {
                    return;
                }
                FlashlightDetailAdapter.this.mSlider.setContentDescription(Integer.toString(i2));
            }
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onStopTrackingTouch(SeekBar seekBar) {
            FlashlightDetailAdapter.this.mSecFlashlightController.setFlashlightLevel(seekBar.getProgress() + 1, true);
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onStartTrackingTouch(SeekBar seekBar) {
        }
    };

    /* renamed from: com.android.systemui.qs.tiles.detail.FlashlightDetailAdapter$2, reason: invalid class name */
    public final class AnonymousClass2 implements Runnable {
        public final /* synthetic */ boolean val$state;

        public AnonymousClass2(boolean z) {
            this.val$state = z;
        }

        @Override // java.lang.Runnable
        public final void run() {
            FlashlightDetailAdapter flashlightDetailAdapter = FlashlightDetailAdapter.this;
            int i = flashlightDetailAdapter.mSecFlashlightController.mFlashlightLevel;
            SeekBar seekBar = flashlightDetailAdapter.mSlider;
            if (seekBar != null) {
                seekBar.setProgress(i);
                FlashlightDetailAdapter.this.mSlider.setEnabled(this.val$state);
                FlashlightDetailAdapter.this.mSlider.setAlpha(this.val$state ? 1.0f : 0.6f);
                if (!FlashlightDetailAdapter.this.mSettingsHelper.isVoiceAssistantEnabled()) {
                    FlashlightDetailAdapter.this.mSlider.setContentDescription(Integer.toString(i + 1));
                }
            }
            TextView textView = FlashlightDetailAdapter.this.mWarningTextView;
            if (textView != null) {
                textView.setVisibility((i < 3 || !this.val$state) ? 8 : 0);
            }
        }
    }

    public FlashlightDetailAdapter(FlashlightTile flashlightTile, FlashlightController flashlightController, SecFlashlightControllerImpl secFlashlightControllerImpl, Context context, QSTile.BooleanState booleanState, SettingsHelper settingsHelper, boolean z, Handler handler) {
        this.mIsLowBattery = false;
        this.mFlashlightTile = flashlightTile;
        this.mFlashlightController = flashlightController;
        this.mSecFlashlightController = secFlashlightControllerImpl;
        this.mContext = context;
        this.mState = booleanState;
        this.mSettingsHelper = settingsHelper;
        this.mIsLowBattery = z;
        this.mUiHandler = handler;
    }

    @Override // com.android.systemui.plugins.qs.DetailAdapter
    public final View createDetailView(Context context, View view, ViewGroup viewGroup) {
        View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.qs_detail_flashlight, viewGroup, false);
        this.mWarningTextView = (TextView) inflate.findViewById(R.id.text_warning);
        Context context2 = this.mContext;
        String string = context2.getString(R.string.quick_settings_flashlight_detail_warning, context2.getString(R.string.sec_quick_settings_flashlight_label));
        TextView textView = this.mWarningTextView;
        if (textView != null) {
            textView.setText(string);
        }
        SeekBar seekBar = (SeekBar) inflate.findViewById(R.id.flashlight_slider);
        this.mSlider = seekBar;
        seekBar.setOnSeekBarChangeListener(this.torchLevelChangedListener);
        this.mSlider.setMax(4);
        this.mSlider.setOnTouchListener(new View.OnTouchListener(this) { // from class: com.android.systemui.qs.tiles.detail.FlashlightDetailAdapter.1
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view2, MotionEvent motionEvent) {
                return false;
            }
        });
        this.mUiHandler.post(new AnonymousClass2(this.mState.value));
        return inflate;
    }

    @Override // com.android.systemui.plugins.qs.DetailAdapter
    public final int getMetricsCategory() {
        return 119;
    }

    @Override // com.android.systemui.plugins.qs.DetailAdapter
    public final Intent getSettingsIntent() {
        return null;
    }

    @Override // com.android.systemui.plugins.qs.DetailAdapter
    public final CharSequence getTitle() {
        return this.mContext.getString(R.string.qs_detail_flashlight_title);
    }

    @Override // com.android.systemui.plugins.qs.DetailAdapter
    public final Boolean getToggleState() {
        return Boolean.valueOf(this.mState.value);
    }

    @Override // com.android.systemui.plugins.qs.DetailAdapter
    public final void setToggleState(boolean z) {
        boolean isFlashlightTileBlocked = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).isFlashlightTileBlocked();
        FlashlightTile flashlightTile = this.mFlashlightTile;
        if (isFlashlightTileBlocked) {
            flashlightTile.showItPolicyToast();
            Log.d("FlashlightDetailAdapter", "setToggleState blocked");
            flashlightTile.fireToggleStateChanged(getToggleState().booleanValue());
        } else {
            if (this.mIsLowBattery) {
                flashlightTile.showWarningMessage(this.mContext.getString(R.string.flash_light_disabled_by_low_battery));
                flashlightTile.fireToggleStateChanged(getToggleState().booleanValue());
                return;
            }
            SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_DETAIL_SWITCH, "location", "flashlight");
            Log.d("FlashlightDetailAdapter", "setToggleState " + z);
            ((FlashlightControllerImpl) this.mFlashlightController).setFlashlight(z);
            this.mUiHandler.post(new AnonymousClass2(z));
        }
    }
}
