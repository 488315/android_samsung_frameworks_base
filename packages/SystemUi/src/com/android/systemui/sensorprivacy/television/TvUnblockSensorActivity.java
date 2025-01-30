package com.android.systemui.sensorprivacy.television;

import android.app.AppOpsManager;
import android.app.role.RoleManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.hardware.SensorPrivacyManager;
import android.os.Bundle;
import android.os.UserHandle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.systemui.R;
import com.android.systemui.tv.TvBottomSheetActivity;
import com.android.systemui.statusbar.policy.IndividualSensorPrivacyController;
import com.android.systemui.statusbar.policy.IndividualSensorPrivacyControllerImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class TvUnblockSensorActivity extends TvBottomSheetActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AppOpsManager mAppOpsManager;
    public Button mCancelButton;
    public TextView mContent;
    public ImageView mIcon;
    public Button mPositiveButton;
    public final RoleManager mRoleManager;
    public ImageView mSecondIcon;
    public int mSensor = -1;
    public TvUnblockSensorActivity$$ExternalSyntheticLambda1 mSensorPrivacyCallback;
    public final IndividualSensorPrivacyController mSensorPrivacyController;
    public TextView mTitle;

    public TvUnblockSensorActivity(IndividualSensorPrivacyController individualSensorPrivacyController, AppOpsManager appOpsManager, RoleManager roleManager) {
        this.mSensorPrivacyController = individualSensorPrivacyController;
        this.mAppOpsManager = appOpsManager;
        this.mRoleManager = roleManager;
    }

    /* JADX WARN: Type inference failed for: r3v7, types: [com.android.systemui.sensorprivacy.television.TvUnblockSensorActivity$$ExternalSyntheticLambda1] */
    @Override // com.android.systemui.tv.TvBottomSheetActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addSystemFlags(524288);
        if (getIntent().getBooleanExtra(SensorPrivacyManager.EXTRA_ALL_SENSORS, false)) {
            this.mSensor = Integer.MAX_VALUE;
        } else {
            this.mSensor = getIntent().getIntExtra(SensorPrivacyManager.EXTRA_SENSOR, -1);
        }
        if (this.mSensor == -1) {
            finish();
            return;
        }
        this.mSensorPrivacyCallback = new IndividualSensorPrivacyController.Callback() { // from class: com.android.systemui.sensorprivacy.television.TvUnblockSensorActivity$$ExternalSyntheticLambda1
            @Override // com.android.systemui.statusbar.policy.IndividualSensorPrivacyController.Callback
            public final void onSensorBlockedChanged(int i, boolean z) {
                TvUnblockSensorActivity tvUnblockSensorActivity = TvUnblockSensorActivity.this;
                int i2 = tvUnblockSensorActivity.mSensor;
                int i3 = R.string.sensor_privacy_mic_camera_unblocked_toast_content;
                if (i2 == Integer.MAX_VALUE) {
                    IndividualSensorPrivacyControllerImpl individualSensorPrivacyControllerImpl = (IndividualSensorPrivacyControllerImpl) tvUnblockSensorActivity.mSensorPrivacyController;
                    if (!individualSensorPrivacyControllerImpl.isSensorBlocked(2) && !individualSensorPrivacyControllerImpl.isSensorBlocked(1)) {
                        int i4 = tvUnblockSensorActivity.mSensor;
                        if (i4 == 1) {
                            i3 = R.string.sensor_privacy_mic_unblocked_toast_content;
                        } else if (i4 == 2) {
                            i3 = R.string.sensor_privacy_camera_unblocked_toast_content;
                        }
                        Toast.makeText(tvUnblockSensorActivity, i3, 0).show();
                        tvUnblockSensorActivity.finish();
                        return;
                    }
                }
                int i5 = tvUnblockSensorActivity.mSensor;
                if (i5 != i || z) {
                    tvUnblockSensorActivity.updateUI();
                    return;
                }
                if (i5 == 1) {
                    i3 = R.string.sensor_privacy_mic_unblocked_toast_content;
                } else if (i5 == 2) {
                    i3 = R.string.sensor_privacy_camera_unblocked_toast_content;
                }
                Toast.makeText(tvUnblockSensorActivity, i3, 0).show();
                tvUnblockSensorActivity.finish();
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
        this.mCancelButton.setOnClickListener(new TvUnblockSensorActivity$$ExternalSyntheticLambda0(this, 2));
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

    public final void setIconSize(int i, int i2) {
        Resources resources = getResources();
        int dimensionPixelSize = resources.getDimensionPixelSize(i);
        int dimensionPixelSize2 = resources.getDimensionPixelSize(i2);
        this.mIcon.getLayoutParams().width = dimensionPixelSize;
        this.mIcon.getLayoutParams().height = dimensionPixelSize2;
        this.mIcon.invalidate();
        this.mSecondIcon.getLayoutParams().width = dimensionPixelSize;
        this.mSecondIcon.getLayoutParams().height = dimensionPixelSize2;
        this.mSecondIcon.invalidate();
    }

    public final void setIconTint(boolean z) {
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
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0039  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateUI() {
        boolean z;
        boolean isSensorBlockedByHardwareToggle;
        String stringExtra = getIntent().getStringExtra("android.intent.extra.PACKAGE_NAME");
        boolean z2 = false;
        if (this.mRoleManager.getRoleHolders("android.app.role.ASSISTANT").contains(stringExtra)) {
            if (this.mAppOpsManager.checkOpNoThrow(121, UserHandle.myUserId(), stringExtra) != 0) {
                z = true;
                if (!z) {
                    setIconTint(true);
                    setIconSize(R.dimen.bottom_sheet_icon_size, R.dimen.bottom_sheet_icon_size);
                    this.mTitle.setText(R.string.sensor_privacy_start_use_mic_blocked_dialog_title);
                    this.mContent.setText(R.string.sensor_privacy_htt_blocked_dialog_content);
                    this.mIcon.setImageResource(android.R.drawable.pointer_grab_large_icon);
                    this.mSecondIcon.setVisibility(8);
                    this.mPositiveButton.setText(R.string.sensor_privacy_dialog_open_settings);
                    this.mPositiveButton.setOnClickListener(new TvUnblockSensorActivity$$ExternalSyntheticLambda0(this, 1));
                    return;
                }
                int i = this.mSensor;
                IndividualSensorPrivacyController individualSensorPrivacyController = this.mSensorPrivacyController;
                if (i == Integer.MAX_VALUE) {
                    IndividualSensorPrivacyControllerImpl individualSensorPrivacyControllerImpl = (IndividualSensorPrivacyControllerImpl) individualSensorPrivacyController;
                    isSensorBlockedByHardwareToggle = individualSensorPrivacyControllerImpl.isSensorBlockedByHardwareToggle(2) || individualSensorPrivacyControllerImpl.isSensorBlockedByHardwareToggle(1);
                } else {
                    isSensorBlockedByHardwareToggle = ((IndividualSensorPrivacyControllerImpl) individualSensorPrivacyController).isSensorBlockedByHardwareToggle(i);
                }
                if (!isSensorBlockedByHardwareToggle) {
                    setIconTint(true);
                    setIconSize(R.dimen.bottom_sheet_icon_size, R.dimen.bottom_sheet_icon_size);
                    int i2 = this.mSensor;
                    if (i2 == 1) {
                        this.mTitle.setText(R.string.sensor_privacy_start_use_mic_blocked_dialog_title);
                        this.mContent.setText(R.string.sensor_privacy_start_use_mic_dialog_content);
                        this.mIcon.setImageResource(android.R.drawable.pointer_grab_large_icon);
                        this.mSecondIcon.setVisibility(8);
                    } else if (i2 != 2) {
                        this.mTitle.setText(R.string.sensor_privacy_start_use_mic_camera_blocked_dialog_title);
                        this.mContent.setText(R.string.sensor_privacy_start_use_mic_camera_dialog_content);
                        this.mIcon.setImageResource(android.R.drawable.pointer_crosshair_vector);
                        this.mSecondIcon.setImageResource(android.R.drawable.pointer_grab_large_icon);
                    } else {
                        this.mTitle.setText(R.string.sensor_privacy_start_use_camera_blocked_dialog_title);
                        this.mContent.setText(R.string.sensor_privacy_start_use_camera_dialog_content);
                        this.mIcon.setImageResource(android.R.drawable.pointer_crosshair_vector);
                        this.mSecondIcon.setVisibility(8);
                    }
                    this.mPositiveButton.setText(17042771);
                    this.mPositiveButton.setOnClickListener(new TvUnblockSensorActivity$$ExternalSyntheticLambda0(this, 0));
                    return;
                }
                Resources resources = getResources();
                int i3 = this.mSensor;
                boolean z3 = (i3 == 1 || i3 == Integer.MAX_VALUE) && ((IndividualSensorPrivacyControllerImpl) individualSensorPrivacyController).isSensorBlockedByHardwareToggle(1);
                int i4 = this.mSensor;
                if ((i4 == 2 || i4 == Integer.MAX_VALUE) && ((IndividualSensorPrivacyControllerImpl) individualSensorPrivacyController).isSensorBlockedByHardwareToggle(2)) {
                    z2 = true;
                }
                setIconTint(resources.getBoolean(R.bool.config_unblockHwSensorIconEnableTint));
                setIconSize(R.dimen.unblock_hw_sensor_icon_width, R.dimen.unblock_hw_sensor_icon_height);
                if (z3 && z2) {
                    this.mTitle.setText(R.string.sensor_privacy_start_use_mic_camera_blocked_dialog_title);
                    this.mContent.setText(R.string.sensor_privacy_start_use_mic_camera_blocked_dialog_content);
                    this.mIcon.setImageResource(R.drawable.unblock_hw_sensor_all);
                    Drawable drawable = resources.getDrawable(R.drawable.unblock_hw_sensor_all_second, getTheme());
                    if (drawable == null) {
                        this.mSecondIcon.setVisibility(8);
                    } else {
                        this.mSecondIcon.setImageDrawable(drawable);
                    }
                } else if (z2) {
                    this.mTitle.setText(R.string.sensor_privacy_start_use_camera_blocked_dialog_title);
                    this.mContent.setText(R.string.sensor_privacy_start_use_camera_blocked_dialog_content);
                    this.mIcon.setImageResource(R.drawable.unblock_hw_sensor_camera);
                    this.mSecondIcon.setVisibility(8);
                } else if (z3) {
                    this.mTitle.setText(R.string.sensor_privacy_start_use_mic_blocked_dialog_title);
                    this.mContent.setText(R.string.sensor_privacy_start_use_mic_blocked_dialog_content);
                    this.mIcon.setImageResource(R.drawable.unblock_hw_sensor_microphone);
                    this.mSecondIcon.setVisibility(8);
                }
                Object drawable2 = this.mIcon.getDrawable();
                if (drawable2 instanceof Animatable) {
                    ((Animatable) drawable2).start();
                }
                this.mPositiveButton.setVisibility(8);
                this.mCancelButton.setText(android.R.string.ok);
                return;
            }
        }
        z = false;
        if (!z) {
        }
    }
}
