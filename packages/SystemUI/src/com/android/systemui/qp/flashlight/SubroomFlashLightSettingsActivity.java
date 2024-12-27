package com.android.systemui.qp.flashlight;

import android.R;
import android.app.Activity;
import android.content.SharedPreferences;
import android.hardware.devicestate.DeviceState;
import android.hardware.devicestate.DeviceStateManager;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.qp.SubscreenQSControllerContract$FlashLightView;
import com.android.systemui.qp.flashlight.SubroomFlashLightTurnOffView;
import com.android.systemui.qp.flashlight.SubroomFlashLightTurnOnView;
import com.android.systemui.qp.util.SubscreenUtil;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.statusbar.policy.FlashlightController;
import com.android.systemui.statusbar.policy.FlashlightControllerImpl;
import com.android.systemui.statusbar.policy.SecFlashlightControllerImpl;
import com.android.systemui.util.SystemUIAnalytics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public class SubroomFlashLightSettingsActivity extends Activity implements SubroomFlashLightTurnOffView.ClickListener, SubroomFlashLightTurnOnView.TurnOnClickListener, SubscreenQSControllerContract$FlashLightView {
    public static final /* synthetic */ int $r8$clinit = 0;
    public AccessibilityManager mAccessibilityManager;
    public RelativeLayout mBackButton;
    public SubroomFlashLightSettingsActivity mContext;
    public DeviceStateManager mDeviceStateManager;
    public TextView mFlashLightHelpText;
    public SubroomFlashLightButtonSettingsView mFlashLightHelpView;
    public SubscreenFlashLightController mFlashLightPresentationController;
    public SubroomFlashLightTurnOffView mFlashLightTurnOff;
    public SubroomFlashLightTurnOnView mFlashLightTurnOn;
    public FlashlightController mFlashlightController;
    public WindowManager.LayoutParams mLp;
    public SecFlashlightControllerImpl mSecFlashlightController;
    public SharedPreferences mShowHelpViewTextPrefs;
    public FrameLayout mSubRoomPresentationView;
    public SubroomFlashLightUtil mSubroomFlashLightUtil;
    public int mSubScreen = 0;
    public int mHelpViewTextCounter = 0;
    public boolean mLongPress = false;
    public int mActivityState = 0;
    public boolean mIsFlexMode = false;
    public long mLastEvent = 0;
    public final AnonymousClass1 mDeviceStateCallback = new DeviceStateManager.DeviceStateCallback() { // from class: com.android.systemui.qp.flashlight.SubroomFlashLightSettingsActivity.1
        public final void onDeviceStateChanged(DeviceState deviceState) {
            int identifier = deviceState.getIdentifier();
            SubroomFlashLightSettingsActivity subroomFlashLightSettingsActivity = SubroomFlashLightSettingsActivity.this;
            subroomFlashLightSettingsActivity.mIsFlexMode = identifier == 1;
            if (subroomFlashLightSettingsActivity.mSubRoomPresentationView == null) {
                return;
            }
            ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("updateRoundedCorners ,flexmode:"), subroomFlashLightSettingsActivity.mIsFlexMode, "SubroomFlashLightSettingsActivity");
            if (subroomFlashLightSettingsActivity.mIsFlexMode) {
                subroomFlashLightSettingsActivity.mSubRoomPresentationView.semSetRoundedCorners(3, subroomFlashLightSettingsActivity.mContext.getResources().getDimensionPixelSize(17105820));
                subroomFlashLightSettingsActivity.mSubRoomPresentationView.semSetRoundedCornerColor(3, subroomFlashLightSettingsActivity.mContext.getColor(R.color.black));
            } else {
                subroomFlashLightSettingsActivity.mSubRoomPresentationView.semSetRoundedCorners(0);
            }
            subroomFlashLightSettingsActivity.mSubRoomPresentationView.invalidate();
        }
    };

    public final void finishFlashLightActivity() {
        Log.d("SubroomFlashLightSettingsActivity", "finishFlashLightActivity");
        finish();
    }

    public final int getActivityState() {
        RecyclerView$$ExternalSyntheticOutline0.m(this.mActivityState, "SubroomFlashLightSettingsActivity", new StringBuilder("getActivityState: "));
        return this.mActivityState;
    }

    public final boolean isValidKey(int i, KeyEvent keyEvent) {
        return !this.mAccessibilityManager.isTouchExplorationEnabled() && (i == 25 || i == 24) && keyEvent.getEventTime() - this.mLastEvent > 70;
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened) {
            finishFlashLightActivity();
        }
        Log.d("SubroomFlashLightSettingsActivity", "onCreate: ");
        setContentView(com.android.systemui.R.layout.subscreen_flashlight_presentation);
        this.mContext = this;
        this.mSubroomFlashLightUtil = new SubroomFlashLightUtil(this);
        this.mSubRoomPresentationView = (FrameLayout) findViewById(com.android.systemui.R.id.subroom_presentation_view);
        DeviceStateManager deviceStateManager = (DeviceStateManager) this.mContext.getSystemService(DeviceStateManager.class);
        this.mDeviceStateManager = deviceStateManager;
        deviceStateManager.registerCallback(this.mContext.getMainExecutor(), this.mDeviceStateCallback);
        View decorView = getWindow().getDecorView();
        if (decorView != null) {
            SecQSPanelResourcePicker secQSPanelResourcePicker = (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
            SubroomFlashLightSettingsActivity subroomFlashLightSettingsActivity = this.mContext;
            secQSPanelResourcePicker.getClass();
            decorView.setSystemUiVisibility((subroomFlashLightSettingsActivity.getResources().getConfiguration().uiMode & 48) == 32 ? 1792 : 1808);
        }
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        this.mLp = attributes;
        if (attributes != null) {
            attributes.setTitle("SubroomFlashLightSettingsActivity");
            this.mLp.semSetScreenDimDuration(0L);
            if (!QpRune.QUICK_SUBSCREEN_PANEL) {
                this.mLp.semSetScreenTimeout(10000L);
            }
            WindowManager.LayoutParams layoutParams = this.mLp;
            layoutParams.privateFlags |= 16;
            layoutParams.alpha = 1.0f;
            getWindow().setAttributes(this.mLp);
        }
        getWindow().setFlags(1024, 1024);
        setShowWhenLocked(true);
        this.mFlashLightPresentationController = SubscreenFlashLightController.getInstance(this);
        FlashlightController flashlightController = (FlashlightController) Dependency.sDependency.getDependencyInner(FlashlightController.class);
        this.mFlashlightController = flashlightController;
        this.mSecFlashlightController = ((FlashlightControllerImpl) flashlightController).mSecFlashlightController;
        this.mFlashLightPresentationController.mFlashLightPresentationView = this;
        this.mSubRoomPresentationView = (FrameLayout) findViewById(com.android.systemui.R.id.subroom_presentation_view);
        this.mSubScreen = 0;
        this.mAccessibilityManager = (AccessibilityManager) getSystemService(AccessibilityManager.class);
        this.mShowHelpViewTextPrefs = getSharedPreferences("ShowDifferentHelpViewText", 0);
        SubroomFlashLightUtil subroomFlashLightUtil = this.mSubroomFlashLightUtil;
        this.mFlashLightHelpView = subroomFlashLightUtil.mFlashLightHelpView;
        SubroomFlashLightTurnOnView subroomFlashLightTurnOnView = subroomFlashLightUtil.mFlashLightTurnOn;
        this.mFlashLightTurnOn = subroomFlashLightTurnOnView;
        this.mBackButton = subroomFlashLightUtil.mBackButton;
        this.mFlashLightHelpText = subroomFlashLightUtil.mFlashLightHelpText;
        SubroomFlashLightTurnOffView subroomFlashLightTurnOffView = subroomFlashLightUtil.mFlashLightTurnOff;
        this.mFlashLightTurnOff = subroomFlashLightTurnOffView;
        subroomFlashLightTurnOffView.mListener = this;
        subroomFlashLightTurnOnView.mListener = this;
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        Log.d("SubroomFlashLightSettingsActivity", "onDestroy: ");
        runOnUiThread(new Runnable() { // from class: com.android.systemui.qp.flashlight.SubroomFlashLightSettingsActivity.2
            @Override // java.lang.Runnable
            public final void run() {
                SubroomFlashLightSettingsActivity subroomFlashLightSettingsActivity = SubroomFlashLightSettingsActivity.this;
                SubroomFlashLightTurnOnView subroomFlashLightTurnOnView = subroomFlashLightSettingsActivity.mFlashLightTurnOn;
                if (subroomFlashLightTurnOnView != null) {
                    subroomFlashLightTurnOnView.setVisibility(8);
                }
                SubroomFlashLightTurnOffView subroomFlashLightTurnOffView = subroomFlashLightSettingsActivity.mFlashLightTurnOff;
                if (subroomFlashLightTurnOffView != null) {
                    subroomFlashLightTurnOffView.setVisibility(8);
                }
                SubroomFlashLightButtonSettingsView subroomFlashLightButtonSettingsView = subroomFlashLightSettingsActivity.mFlashLightHelpView;
                if (subroomFlashLightButtonSettingsView != null) {
                    subroomFlashLightButtonSettingsView.setVisibility(8);
                }
                FrameLayout frameLayout = subroomFlashLightSettingsActivity.mSubRoomPresentationView;
                if (frameLayout != null) {
                    frameLayout.setVisibility(8);
                }
                subroomFlashLightSettingsActivity.mSubroomFlashLightUtil = null;
            }
        });
        this.mFlashLightPresentationController = null;
        this.mSubScreen = 0;
        this.mActivityState = 0;
        this.mDeviceStateManager.unregisterCallback(this.mDeviceStateCallback);
        RecyclerView$$ExternalSyntheticOutline0.m(this.mActivityState, "SubroomFlashLightSettingsActivity", new StringBuilder("onDestroy: "));
        super.onDestroy();
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (!isValidKey(i, keyEvent)) {
            return super.onKeyDown(i, keyEvent);
        }
        Log.d("SubroomFlashLightSettingsActivity", "onKeyDown VOLUME!!");
        keyEvent.startTracking();
        return true;
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public final boolean onKeyLongPress(int i, KeyEvent keyEvent) {
        if (!isValidKey(i, keyEvent)) {
            return super.onKeyLongPress(i, keyEvent);
        }
        Log.d("SubroomFlashLightSettingsActivity", "LONG PRESS!");
        this.mLongPress = true;
        onVolumeKeyEvent(i, keyEvent);
        return true;
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public final boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (!isValidKey(i, keyEvent)) {
            return super.onKeyUp(i, keyEvent);
        }
        if (this.mLongPress) {
            this.mLongPress = false;
            return true;
        }
        Log.d("SubroomFlashLightSettingsActivity", "SHORT PRESS");
        onVolumeKeyEvent(i, keyEvent);
        return true;
    }

    @Override // android.app.Activity
    public final void onPause() {
        this.mActivityState = 2;
        RecyclerView$$ExternalSyntheticOutline0.m(this.mActivityState, "SubroomFlashLightSettingsActivity", new StringBuilder("onPause: "));
        super.onPause();
    }

    @Override // android.app.Activity
    public final void onResume() {
        super.onResume();
        this.mActivityState = 1;
        RecyclerView$$ExternalSyntheticOutline0.m(this.mActivityState, "SubroomFlashLightSettingsActivity", new StringBuilder("onResume: "));
    }

    @Override // android.app.Activity
    public final void onStart() {
        super.onStart();
        this.mActivityState = 1;
        RecyclerView$$ExternalSyntheticOutline0.m(this.mActivityState, "SubroomFlashLightSettingsActivity", new StringBuilder("onStart: "));
        if (this.mSubScreen != 0) {
            return;
        }
        if (((FlashlightControllerImpl) this.mFlashlightController).isEnabled()) {
            showTurnOffView();
        } else if (this.mAccessibilityManager.isTouchExplorationEnabled()) {
            Log.d("SubroomFlashLightSettingsActivity", "showTurnOnView: ");
            this.mSubScreen = 4;
            updateWindowFlag(true);
            this.mSubRoomPresentationView.setVisibility(0);
            this.mBackButton.setVisibility(0);
            this.mFlashLightTurnOn.setVisibility(0);
            this.mFlashLightTurnOff.setVisibility(8);
            final int i = 0;
            this.mBackButton.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.qp.flashlight.SubroomFlashLightSettingsActivity$$ExternalSyntheticLambda0
                public final /* synthetic */ SubroomFlashLightSettingsActivity f$0;

                {
                    this.f$0 = this;
                }

                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    int i2 = i;
                    SubroomFlashLightSettingsActivity subroomFlashLightSettingsActivity = this.f$0;
                    switch (i2) {
                        case 0:
                            int i3 = SubroomFlashLightSettingsActivity.$r8$clinit;
                            subroomFlashLightSettingsActivity.finishFlashLightActivity();
                            break;
                        default:
                            int i4 = SubroomFlashLightSettingsActivity.$r8$clinit;
                            subroomFlashLightSettingsActivity.finishFlashLightActivity();
                            break;
                    }
                }
            });
        } else {
            Log.d("SubroomFlashLightSettingsActivity", "showHelpView: ");
            updateWindowFlag(false);
            this.mSubScreen = 4;
            this.mSubRoomPresentationView.setVisibility(0);
            this.mBackButton.setVisibility(0);
            TextView textView = this.mFlashLightHelpText;
            int i2 = this.mShowHelpViewTextPrefs.getInt("helpViewTextCount", 0);
            this.mHelpViewTextCounter = i2;
            textView.setText(i2 < 3 ? com.android.systemui.R.string.subscreen_flashlight_help_text : com.android.systemui.R.string.subscreen_flashlight_help_after_three_times_text);
            this.mFlashLightHelpView.setVisibility(0);
            this.mFlashLightHelpText.setMovementMethod(new ScrollingMovementMethod());
            this.mFlashLightTurnOff.setVisibility(8);
            final int i3 = 1;
            this.mBackButton.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.qp.flashlight.SubroomFlashLightSettingsActivity$$ExternalSyntheticLambda0
                public final /* synthetic */ SubroomFlashLightSettingsActivity f$0;

                {
                    this.f$0 = this;
                }

                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    int i22 = i3;
                    SubroomFlashLightSettingsActivity subroomFlashLightSettingsActivity = this.f$0;
                    switch (i22) {
                        case 0:
                            int i32 = SubroomFlashLightSettingsActivity.$r8$clinit;
                            subroomFlashLightSettingsActivity.finishFlashLightActivity();
                            break;
                        default:
                            int i4 = SubroomFlashLightSettingsActivity.$r8$clinit;
                            subroomFlashLightSettingsActivity.finishFlashLightActivity();
                            break;
                    }
                }
            });
        }
        SubscreenUtil subscreenUtil = (SubscreenUtil) Dependency.sDependency.getDependencyInner(SubscreenUtil.class);
        String string = getResources().getString(com.android.systemui.R.string.qs_detail_flashlight_title);
        subscreenUtil.getClass();
        SubscreenUtil.sendAnnouncementEvent(this, string);
        if (QpRune.QUICK_SUBSCREEN_PANEL) {
            SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_QP_FLASHLIGHT_COVER);
        } else {
            SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_FLASHLIGHT_COVER);
        }
    }

    @Override // android.app.Activity
    public final void onStop() {
        this.mActivityState = 2;
        RecyclerView$$ExternalSyntheticOutline0.m(this.mActivityState, "SubroomFlashLightSettingsActivity", new StringBuilder("onStop: "));
        super.onStop();
    }

    public final void onVolumeKeyEvent(int i, KeyEvent keyEvent) {
        RecyclerView$$ExternalSyntheticOutline0.m(this.mSubScreen, "SubroomFlashLightSettingsActivity", new StringBuilder("onVolumeKeyEvent: mSubScreen: "));
        if (this.mAccessibilityManager.isTouchExplorationEnabled()) {
            Log.d("SubroomFlashLightSettingsActivity", "onVolumeKeyEvent: Access Restrcicted by Talkback");
            return;
        }
        if (i == 25 || i == 24) {
            if (!((FlashlightControllerImpl) this.mFlashlightController).isAvailable()) {
                this.mSecFlashlightController.showUnavailableMessage();
                return;
            }
            this.mLastEvent = keyEvent.getEventTime();
            int i2 = this.mSubScreen;
            if (i2 != 4) {
                if (i2 == 5) {
                    ((FlashlightControllerImpl) this.mFlashlightController).setFlashlight(false);
                    finishFlashLightActivity();
                    return;
                }
                return;
            }
            showTurnOffView();
            ((FlashlightControllerImpl) this.mFlashlightController).setFlashlight(true);
            int i3 = this.mShowHelpViewTextPrefs.getInt("helpViewTextCount", 0);
            this.mHelpViewTextCounter = i3;
            if (i3 < 3) {
                SharedPreferences.Editor edit = this.mShowHelpViewTextPrefs.edit();
                int i4 = this.mHelpViewTextCounter + 1;
                this.mHelpViewTextCounter = i4;
                edit.putInt("helpViewTextCount", i4);
                edit.commit();
            }
        }
    }

    public final void showTurnOffView() {
        Log.d("SubroomFlashLightSettingsActivity", "showTurnOffView: ");
        this.mSubScreen = 5;
        updateWindowFlag(true);
        this.mFlashLightTurnOn.setVisibility(8);
        this.mSubRoomPresentationView.setVisibility(0);
        this.mBackButton.setVisibility(8);
        this.mFlashLightHelpView.setVisibility(8);
        this.mFlashLightTurnOff.setVisibility(0);
    }

    public final void updateWindowFlag(boolean z) {
        WindowManager.LayoutParams layoutParams = this.mLp;
        if (layoutParams != null) {
            if (z) {
                layoutParams.flags |= 2097280;
            } else {
                layoutParams.flags &= -2097281;
            }
            getWindow().setAttributes(this.mLp);
        }
    }
}
