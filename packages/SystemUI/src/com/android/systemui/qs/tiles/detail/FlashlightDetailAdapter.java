package com.android.systemui.qs.tiles.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.DetailAdapter;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.tiles.FlashlightTile;
import com.android.systemui.statusbar.policy.FlashlightController;
import com.android.systemui.statusbar.policy.SecFlashlightControllerImpl;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.qs.tiles.detail.FlashlightDetailAdapter$2, reason: invalid class name */
    public class AnonymousClass2 implements Runnable {
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

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.qs.tiles.detail.FlashlightDetailAdapter$3] */
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

    /* JADX WARN: Removed duplicated region for block: B:11:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    @Override // com.android.systemui.plugins.qs.DetailAdapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setToggleState(boolean r6) {
        /*
            r5 = this;
            com.android.systemui.Dependency r0 = com.android.systemui.Dependency.sDependency
            java.lang.Class<com.android.systemui.knox.KnoxStateMonitor> r1 = com.android.systemui.knox.KnoxStateMonitor.class
            java.lang.Object r0 = r0.getDependencyInner(r1)
            com.android.systemui.knox.KnoxStateMonitor r0 = (com.android.systemui.knox.KnoxStateMonitor) r0
            com.android.systemui.knox.KnoxStateMonitorImpl r0 = (com.android.systemui.knox.KnoxStateMonitorImpl) r0
            com.android.systemui.knox.EdmMonitor r0 = r0.mEdmMonitor
            if (r0 == 0) goto L1a
            com.android.systemui.knox.KnoxStateMonitorImpl r1 = r0.knoxStateMonitor
            android.content.Context r1 = r1.mContext
            boolean r0 = r0.mSettingsChangesAllowed
            if (r0 != 0) goto L1a
            r0 = 1
            goto L1b
        L1a:
            r0 = 0
        L1b:
            java.lang.String r1 = "FlashlightDetailAdapter"
            com.android.systemui.qs.tiles.FlashlightTile r2 = r5.mFlashlightTile
            if (r0 == 0) goto L36
            r2.showItPolicyToast()
            java.lang.String r6 = "setToggleState blocked"
            android.util.Log.d(r1, r6)
            java.lang.Boolean r5 = r5.getToggleState()
            boolean r5 = r5.booleanValue()
            r2.fireToggleStateChanged(r5)
            return
        L36:
            boolean r0 = r5.mIsLowBattery
            if (r0 == 0) goto L52
            android.content.Context r6 = r5.mContext
            r0 = 2131953558(0x7f130796, float:1.954359E38)
            java.lang.String r6 = r6.getString(r0)
            r2.showWarningMessage(r6)
            java.lang.Boolean r5 = r5.getToggleState()
            boolean r5 = r5.booleanValue()
            r2.fireToggleStateChanged(r5)
            return
        L52:
            java.lang.String r0 = com.android.systemui.util.SystemUIAnalytics.getCurrentScreenID()
            java.lang.String r2 = "flashlight"
            java.lang.String r3 = "QPDE1008"
            java.lang.String r4 = "location"
            com.android.systemui.util.SystemUIAnalytics.sendEventCDLog(r0, r3, r4, r2)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r2 = "setToggleState "
            r0.<init>(r2)
            r0.append(r6)
            java.lang.String r0 = r0.toString()
            android.util.Log.d(r1, r0)
            com.android.systemui.statusbar.policy.FlashlightController r0 = r5.mFlashlightController
            com.android.systemui.statusbar.policy.FlashlightControllerImpl r0 = (com.android.systemui.statusbar.policy.FlashlightControllerImpl) r0
            r0.setFlashlight(r6)
            com.android.systemui.qs.tiles.detail.FlashlightDetailAdapter$2 r0 = new com.android.systemui.qs.tiles.detail.FlashlightDetailAdapter$2
            r0.<init>(r6)
            android.os.Handler r5 = r5.mUiHandler
            r5.post(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.detail.FlashlightDetailAdapter.setToggleState(boolean):void");
    }
}
