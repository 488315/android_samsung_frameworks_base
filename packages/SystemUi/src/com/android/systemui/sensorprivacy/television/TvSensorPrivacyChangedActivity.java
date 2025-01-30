package com.android.systemui.sensorprivacy.television;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Animatable;
import android.hardware.SensorPrivacyManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.tv.TvBottomSheetActivity;
import com.android.systemui.statusbar.policy.IndividualSensorPrivacyController;
import com.android.systemui.statusbar.policy.IndividualSensorPrivacyControllerImpl;
import com.android.systemui.util.settings.GlobalSettings;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class TvSensorPrivacyChangedActivity extends TvBottomSheetActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public Button mCancelButton;
    public TextView mContent;
    public final GlobalSettings mGlobalSettings;
    public ImageView mIcon;
    public Button mPositiveButton;
    public ImageView mSecondIcon;
    public int mSensor = -1;
    public TvSensorPrivacyChangedActivity$$ExternalSyntheticLambda0 mSensorPrivacyCallback;
    public final IndividualSensorPrivacyController mSensorPrivacyController;
    public TextView mTitle;

    public TvSensorPrivacyChangedActivity(IndividualSensorPrivacyController individualSensorPrivacyController, GlobalSettings globalSettings) {
        this.mSensorPrivacyController = individualSensorPrivacyController;
        this.mGlobalSettings = globalSettings;
    }

    /* JADX WARN: Type inference failed for: r3v8, types: [com.android.systemui.sensorprivacy.television.TvSensorPrivacyChangedActivity$$ExternalSyntheticLambda0] */
    @Override // com.android.systemui.tv.TvBottomSheetActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addSystemFlags(524288);
        if (getIntent().getBooleanExtra(SensorPrivacyManager.EXTRA_ALL_SENSORS, false)) {
            this.mSensor = Integer.MAX_VALUE;
        } else {
            this.mSensor = getIntent().getIntExtra(SensorPrivacyManager.EXTRA_SENSOR, -1);
        }
        int intExtra = getIntent().getIntExtra(SensorPrivacyManager.EXTRA_TOGGLE_TYPE, -1);
        if (this.mSensor == -1 || intExtra == -1) {
            finish();
            return;
        }
        if (intExtra == 1) {
            finish();
            return;
        }
        this.mSensorPrivacyCallback = new IndividualSensorPrivacyController.Callback() { // from class: com.android.systemui.sensorprivacy.television.TvSensorPrivacyChangedActivity$$ExternalSyntheticLambda0
            @Override // com.android.systemui.statusbar.policy.IndividualSensorPrivacyController.Callback
            public final void onSensorBlockedChanged(int i, boolean z) {
                int i2 = TvSensorPrivacyChangedActivity.$r8$clinit;
                TvSensorPrivacyChangedActivity.this.updateUI();
            }
        };
        this.mTitle = (TextView) findViewById(R.id.bottom_sheet_title);
        this.mContent = (TextView) findViewById(R.id.bottom_sheet_body);
        this.mIcon = (ImageView) findViewById(R.id.bottom_sheet_icon);
        this.mSecondIcon = (ImageView) findViewById(R.id.bottom_sheet_second_icon);
        this.mPositiveButton = (Button) findViewById(R.id.bottom_sheet_positive_button);
        Button button = (Button) findViewById(R.id.bottom_sheet_negative_button);
        this.mCancelButton = button;
        button.setText(android.R.string.cancel);
        this.mCancelButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.sensorprivacy.television.TvSensorPrivacyChangedActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TvSensorPrivacyChangedActivity tvSensorPrivacyChangedActivity = TvSensorPrivacyChangedActivity.this;
                int i = TvSensorPrivacyChangedActivity.$r8$clinit;
                tvSensorPrivacyChangedActivity.finish();
            }
        });
        updateUI();
    }

    @Override // android.app.Activity
    public final void onPause() {
        ((IndividualSensorPrivacyControllerImpl) this.mSensorPrivacyController).removeCallback(this.mSensorPrivacyCallback);
        super.onPause();
    }

    @Override // android.app.Activity
    public final void onResume() {
        super.onResume();
        updateUI();
        ((IndividualSensorPrivacyControllerImpl) this.mSensorPrivacyController).addCallback(this.mSensorPrivacyCallback);
    }

    public final void updateUI() {
        boolean z = getResources().getBoolean(R.bool.config_unblockHwSensorIconEnableTint);
        Resources resources = getResources();
        if (z) {
            ColorStateList colorStateList = resources.getColorStateList(R.color.bottom_sheet_icon_color, getTheme());
            this.mIcon.setImageTintList(colorStateList);
            this.mSecondIcon.setImageTintList(colorStateList);
        } else {
            this.mIcon.setImageTintList(null);
            this.mSecondIcon.setImageTintList(null);
        }
        this.mIcon.invalidate();
        this.mSecondIcon.invalidate();
        Resources resources2 = getResources();
        int dimensionPixelSize = resources2.getDimensionPixelSize(R.dimen.unblock_hw_sensor_icon_width);
        int dimensionPixelSize2 = resources2.getDimensionPixelSize(R.dimen.unblock_hw_sensor_icon_height);
        this.mIcon.getLayoutParams().width = dimensionPixelSize;
        this.mIcon.getLayoutParams().height = dimensionPixelSize2;
        this.mIcon.invalidate();
        this.mSecondIcon.getLayoutParams().width = dimensionPixelSize;
        this.mSecondIcon.getLayoutParams().height = dimensionPixelSize2;
        this.mSecondIcon.invalidate();
        int i = this.mSensor;
        IndividualSensorPrivacyController individualSensorPrivacyController = this.mSensorPrivacyController;
        if (i != 2) {
            if (((IndividualSensorPrivacyControllerImpl) individualSensorPrivacyController).isSensorBlockedByHardwareToggle(1)) {
                this.mTitle.setText(R.string.sensor_privacy_mic_turned_off_dialog_title);
                if (this.mGlobalSettings.getInt("receive_explicit_user_interaction_audio_enabled", 1) == 1) {
                    this.mContent.setText(R.string.sensor_privacy_mic_blocked_with_exception_dialog_content);
                } else {
                    this.mContent.setText(R.string.sensor_privacy_mic_blocked_no_exception_dialog_content);
                }
                this.mIcon.setImageResource(R.drawable.unblock_hw_sensor_microphone);
                this.mSecondIcon.setVisibility(8);
            } else {
                this.mTitle.setText(R.string.sensor_privacy_mic_turned_on_dialog_title);
                this.mContent.setText(R.string.sensor_privacy_mic_unblocked_dialog_content);
                this.mIcon.setImageResource(android.R.drawable.ic_menu_play_clip);
                this.mSecondIcon.setVisibility(8);
            }
        } else if (((IndividualSensorPrivacyControllerImpl) individualSensorPrivacyController).isSensorBlockedByHardwareToggle(2)) {
            this.mTitle.setText(R.string.sensor_privacy_camera_turned_off_dialog_title);
            this.mContent.setText(R.string.sensor_privacy_camera_blocked_dialog_content);
            this.mIcon.setImageResource(R.drawable.unblock_hw_sensor_camera);
            this.mSecondIcon.setVisibility(8);
        } else {
            this.mTitle.setText(R.string.sensor_privacy_camera_turned_on_dialog_title);
            this.mContent.setText(R.string.sensor_privacy_camera_unblocked_dialog_content);
            this.mIcon.setImageResource(android.R.drawable.ic_bt_hearing_aid);
            this.mSecondIcon.setVisibility(8);
        }
        Object drawable = this.mIcon.getDrawable();
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }
        this.mPositiveButton.setVisibility(8);
        this.mCancelButton.setText(android.R.string.ok);
    }
}
