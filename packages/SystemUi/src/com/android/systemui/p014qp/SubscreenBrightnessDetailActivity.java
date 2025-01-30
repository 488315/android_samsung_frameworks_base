package com.android.systemui.p014qp;

import android.R;
import android.app.Activity;
import android.content.ContentResolver;
import android.database.ContentObserver;
import android.hardware.devicestate.DeviceStateManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.p014qp.util.SubscreenToolTipWindow;
import com.android.systemui.p014qp.util.SubscreenUtil;
import com.android.systemui.p016qs.SecQSPanelResourcePicker;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class SubscreenBrightnessDetailActivity extends Activity implements WakefulnessLifecycle.Observer {
    public static final Uri BRIGHTNESS_MODE_URI = Settings.System.getUriFor("sub_screen_brightness_mode");
    public LinearLayout mAutoBrightnessContainer;
    public SwitchCompat mAutoBrightnessSwitch;
    public ImageView mBackButton;
    public BrightnessModeObserver mBrightnessObserver;
    public SubroomBrightnessSettingsView mBrightnessView;
    public SubscreenBrightnessDetailActivity mContext;
    public DeviceStateManager mDeviceStateManager;
    public SettingsHelper mSettingsHelper;
    public LinearLayout mSubBrightnessDetail;
    public SubscreenBrightnessController mSubscreenBrightnessController;
    public WakefulnessLifecycle mWakefulnessLifeCycle;
    public boolean mIsFlexMode = false;
    public final C20021 mDeviceStateCallback = new DeviceStateManager.DeviceStateCallback() { // from class: com.android.systemui.qp.SubscreenBrightnessDetailActivity.1
        public final void onStateChanged(int i) {
            SubscreenBrightnessDetailActivity subscreenBrightnessDetailActivity = SubscreenBrightnessDetailActivity.this;
            subscreenBrightnessDetailActivity.mIsFlexMode = i == 1;
            if (subscreenBrightnessDetailActivity.mSubBrightnessDetail == null) {
                return;
            }
            ActionBarContextView$$ExternalSyntheticOutline0.m9m(new StringBuilder("updateRoundedCorners ,flexmode:"), subscreenBrightnessDetailActivity.mIsFlexMode, "SubscreenBrightnessDetailActivity");
            if (subscreenBrightnessDetailActivity.mIsFlexMode) {
                subscreenBrightnessDetailActivity.mSubBrightnessDetail.semSetRoundedCorners(3, subscreenBrightnessDetailActivity.mContext.getResources().getDimensionPixelSize(R.dimen.text_view_end_margin));
                subscreenBrightnessDetailActivity.mSubBrightnessDetail.semSetRoundedCornerColor(3, subscreenBrightnessDetailActivity.mContext.getColor(R.color.black));
            } else {
                subscreenBrightnessDetailActivity.mSubBrightnessDetail.semSetRoundedCorners(0);
            }
            subscreenBrightnessDetailActivity.mSubBrightnessDetail.invalidate();
        }
    };
    public final C20065 mDisplayListener = new DisplayLifecycle.Observer() { // from class: com.android.systemui.qp.SubscreenBrightnessDetailActivity.5
        @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
        public final void onFolderStateChanged(boolean z) {
            if (z) {
                Uri uri = SubscreenBrightnessDetailActivity.BRIGHTNESS_MODE_URI;
                SubscreenBrightnessDetailActivity.this.finish();
            }
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class BrightnessModeObserver extends ContentObserver {
        public final ContentResolver mCr;

        public BrightnessModeObserver(Handler handler) {
            super(handler);
            this.mCr = SubscreenBrightnessDetailActivity.this.mContext.getContentResolver();
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            if (z) {
                return;
            }
            SubscreenBrightnessDetailActivity.this.mAutoBrightnessSwitch.setChecked(Settings.System.getIntForUser(SubscreenBrightnessDetailActivity.this.mContext.getContentResolver(), "sub_screen_brightness_mode", 1, -2) != 0);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SwitchDelegate extends AccessibilityDelegateCompat {
        public final SwitchCompat mSwitch;

        public /* synthetic */ SwitchDelegate(SubscreenBrightnessDetailActivity subscreenBrightnessDetailActivity, SwitchCompat switchCompat, int i) {
            this(switchCompat);
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat.mInfo);
            accessibilityNodeInfoCompat.setClassName(Switch.class.getName());
            accessibilityNodeInfoCompat.setCheckable(false);
            boolean isChecked = this.mSwitch.isChecked();
            SubscreenBrightnessDetailActivity subscreenBrightnessDetailActivity = SubscreenBrightnessDetailActivity.this;
            accessibilityNodeInfoCompat.setText(subscreenBrightnessDetailActivity.mContext.getString(com.android.systemui.R.string.sec_brightness_auto_brightness_title) + "," + (isChecked ? subscreenBrightnessDetailActivity.mContext.getString(com.android.systemui.R.string.switch_bar_on) : subscreenBrightnessDetailActivity.mContext.getString(com.android.systemui.R.string.switch_bar_off)));
        }

        private SwitchDelegate(SwitchCompat switchCompat) {
            this.mSwitch = switchCompat;
        }
    }

    /* renamed from: -$$Nest$msetBrightness, reason: not valid java name */
    public static void m1616$$Nest$msetBrightness(SubscreenBrightnessDetailActivity subscreenBrightnessDetailActivity, Boolean bool, Boolean bool2) {
        boolean z;
        subscreenBrightnessDetailActivity.getClass();
        int i = 0;
        if (((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).isBrightnessBlocked()) {
            Log.d("SubscreenBrightnessDetailActivity", "Auto brightness options are not available by KnoxStateMonitor.");
            z = false;
        } else {
            z = true;
        }
        if (z) {
            bool = bool2;
        }
        boolean booleanValue = bool.booleanValue();
        if (QpRune.QUICK_BAR_BRIGHTNESS_PERSONAL_CONTROL) {
            Settings.Secure.putIntForUser(subscreenBrightnessDetailActivity.mContext.getContentResolver(), booleanValue ? "brightness_pms_marker_screen" : "sub_screen_brightness", Settings.System.getIntForUser(subscreenBrightnessDetailActivity.mContext.getContentResolver(), booleanValue ? "sub_screen_brightness" : "brightness_pms_marker_screen", 100, -2), -2);
        }
        boolean equalsIgnoreCase = "factory".equalsIgnoreCase(SystemProperties.get("ro.factory.factory_binary", "Unknown"));
        if (booleanValue && !equalsIgnoreCase) {
            i = 1;
        }
        Settings.System.putIntForUser(subscreenBrightnessDetailActivity.mSettingsHelper.mContext.getContentResolver(), "sub_screen_brightness_mode", i, -2);
        subscreenBrightnessDetailActivity.mAutoBrightnessSwitch.setChecked(bool.booleanValue());
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(com.android.systemui.R.layout.subscreen_brightness_detail);
        this.mContext = this;
        ((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).addObserver(this.mDisplayListener);
        this.mSettingsHelper = (SettingsHelper) Dependency.get(SettingsHelper.class);
        this.mSubBrightnessDetail = (LinearLayout) findViewById(com.android.systemui.R.id.sub_brightness_detail);
        DeviceStateManager deviceStateManager = (DeviceStateManager) this.mContext.getSystemService(DeviceStateManager.class);
        this.mDeviceStateManager = deviceStateManager;
        deviceStateManager.registerCallback(this.mContext.getMainExecutor(), this.mDeviceStateCallback);
        View decorView = getWindow().getDecorView();
        if (decorView != null) {
            SecQSPanelResourcePicker secQSPanelResourcePicker = (SecQSPanelResourcePicker) Dependency.get(SecQSPanelResourcePicker.class);
            SubscreenBrightnessDetailActivity subscreenBrightnessDetailActivity = this.mContext;
            secQSPanelResourcePicker.getClass();
            decorView.setSystemUiVisibility(!SecQSPanelResourcePicker.isNightMode(subscreenBrightnessDetailActivity) ? 1808 : 1792);
        }
        getWindow().setFlags(1024, 1024);
        setShowWhenLocked(true);
        final SubscreenToolTipWindow subscreenToolTipWindow = new SubscreenToolTipWindow(this.mContext, com.android.systemui.R.string.subscreen_close_button);
        this.mBackButton = (ImageView) findViewById(com.android.systemui.R.id.subroom_back_button);
        this.mAutoBrightnessSwitch = (SwitchCompat) findViewById(com.android.systemui.R.id.title_switch);
        int i = this.mContext.getResources().getConfiguration().uiMode;
        this.mBrightnessView = (SubroomBrightnessSettingsView) findViewById(com.android.systemui.R.id.subroom_brightness_settings);
        this.mAutoBrightnessContainer = (LinearLayout) findViewById(com.android.systemui.R.id.subscreen_adaptive_switch_container);
        LinearLayout linearLayout = (LinearLayout) findViewById(com.android.systemui.R.id.subroom_brightness_container);
        this.mBackButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qp.SubscreenBrightnessDetailActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SubscreenBrightnessDetailActivity subscreenBrightnessDetailActivity2 = SubscreenBrightnessDetailActivity.this;
                Uri uri = SubscreenBrightnessDetailActivity.BRIGHTNESS_MODE_URI;
                subscreenBrightnessDetailActivity2.finish();
            }
        });
        this.mBackButton.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.systemui.qp.SubscreenBrightnessDetailActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                SubscreenToolTipWindow subscreenToolTipWindow2 = SubscreenToolTipWindow.this;
                Uri uri = SubscreenBrightnessDetailActivity.BRIGHTNESS_MODE_URI;
                PopupWindow popupWindow = subscreenToolTipWindow2.mTipWindow;
                if (!(popupWindow != null && popupWindow.isShowing())) {
                    subscreenToolTipWindow2.showToolTip(view);
                }
                return true;
            }
        });
        SubscreenBrightnessDetailActivity subscreenBrightnessDetailActivity2 = this.mContext;
        boolean z = subscreenBrightnessDetailActivity2.getResources().getBoolean(R.bool.config_cecSystemAudioModeMutingEnabled_allowed);
        if (QpRune.QUICK_SETTINGS_SUBSCREEN && z) {
            this.mAutoBrightnessContainer.setVisibility(0);
            String string = subscreenBrightnessDetailActivity2.getString(com.android.systemui.R.string.sec_brightness_auto_brightness_title);
            TextView textView = (TextView) this.mAutoBrightnessContainer.findViewById(com.android.systemui.R.id.title);
            textView.setText(string);
            SubscreenUtil.setLabelTextSize(com.android.systemui.R.dimen.brightness_lbl_text_size, (TextView) findViewById(com.android.systemui.R.id.brightness_title));
            SubscreenUtil.setLabelTextSize(com.android.systemui.R.dimen.subscreen_brightness_level_text_size, (TextView) findViewById(com.android.systemui.R.id.level_text));
            SubscreenUtil.setLabelTextSize(com.android.systemui.R.dimen.subscreen_brightness_level_text_size, textView);
            this.mAutoBrightnessSwitch.setChecked(this.mSettingsHelper.mItemLists.get("sub_screen_brightness_mode").getIntValue() != 0);
            this.mAutoBrightnessContainer.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qp.SubscreenBrightnessDetailActivity.2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SubscreenBrightnessDetailActivity subscreenBrightnessDetailActivity3;
                    int i2;
                    boolean isChecked = SubscreenBrightnessDetailActivity.this.mAutoBrightnessSwitch.isChecked();
                    SubscreenBrightnessDetailActivity.m1616$$Nest$msetBrightness(SubscreenBrightnessDetailActivity.this, Boolean.valueOf(isChecked), Boolean.valueOf(!isChecked));
                    if (SubscreenBrightnessDetailActivity.this.mAutoBrightnessSwitch.isChecked()) {
                        subscreenBrightnessDetailActivity3 = SubscreenBrightnessDetailActivity.this.mContext;
                        i2 = com.android.systemui.R.string.switch_bar_on;
                    } else {
                        subscreenBrightnessDetailActivity3 = SubscreenBrightnessDetailActivity.this.mContext;
                        i2 = com.android.systemui.R.string.switch_bar_off;
                    }
                    String string2 = subscreenBrightnessDetailActivity3.getString(i2);
                    SubscreenBrightnessDetailActivity.this.mAutoBrightnessContainer.announceForAccessibility(SubscreenBrightnessDetailActivity.this.mContext.getResources().getString(com.android.systemui.R.string.sec_brightness_auto_brightness_title) + " " + string2);
                }
            });
            this.mAutoBrightnessContainer.setOnTouchListener(new View.OnTouchListener(this) { // from class: com.android.systemui.qp.SubscreenBrightnessDetailActivity.3
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    return false;
                }
            });
            this.mAutoBrightnessSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.android.systemui.qp.SubscreenBrightnessDetailActivity.4
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                    SubscreenBrightnessDetailActivity.m1616$$Nest$msetBrightness(SubscreenBrightnessDetailActivity.this, Boolean.valueOf(!z2), Boolean.valueOf(z2));
                    if (QpRune.QUICK_PANEL_SUBSCREEN) {
                        SystemUIAnalytics.sendEventLog(z2 ? 1L : 0L, SystemUIAnalytics.sCurrentScreenID, "QPDS2027");
                    }
                }
            });
        } else {
            this.mAutoBrightnessContainer.setVisibility(8);
        }
        SubscreenUtil.applyRotation(this.mContext, this.mBackButton);
        SubscreenUtil.applyRotation(this.mContext, linearLayout);
        ViewCompat.setAccessibilityDelegate(this.mAutoBrightnessContainer, new SwitchDelegate(this, this.mAutoBrightnessSwitch, r4));
        if (this.mBrightnessView == null) {
            Log.d("SubscreenBrightnessDetailActivity", "onCreate() mBrightnessView is null");
            return;
        }
        SubscreenUtil subscreenUtil = (SubscreenUtil) Dependency.get(SubscreenUtil.class);
        String string2 = getResources().getString(com.android.systemui.R.string.sub_brightness_label);
        subscreenUtil.getClass();
        SubscreenUtil.sendAnnouncementEvent(this, string2);
        SubscreenBrightnessController subscreenBrightnessController = new SubscreenBrightnessController(this.mContext, this.mBrightnessView);
        this.mSubscreenBrightnessController = subscreenBrightnessController;
        subscreenBrightnessController.init();
        SubscreenBrightnessController subscreenBrightnessController2 = this.mSubscreenBrightnessController;
        subscreenBrightnessController2.getClass();
        subscreenBrightnessController2.mDetailActivity = true;
        this.mWakefulnessLifeCycle = (WakefulnessLifecycle) Dependency.get(WakefulnessLifecycle.class);
        BrightnessModeObserver brightnessModeObserver = new BrightnessModeObserver(new Handler());
        this.mBrightnessObserver = brightnessModeObserver;
        brightnessModeObserver.mCr.unregisterContentObserver(brightnessModeObserver);
        brightnessModeObserver.mCr.registerContentObserver(BRIGHTNESS_MODE_URI, false, brightnessModeObserver);
        WakefulnessLifecycle wakefulnessLifecycle = this.mWakefulnessLifeCycle;
        if (wakefulnessLifecycle != null) {
            wakefulnessLifecycle.addObserver(this);
        }
        if (QpRune.QUICK_PANEL_SUBSCREEN) {
            SystemUIAnalytics.sendEventLog(SystemUIAnalytics.sCurrentScreenID, "QPBE2023");
        }
        getWindow().getAttributes().semSetScreenTimeout(((SubscreenUtil) Dependency.get(SubscreenUtil.class)).mSubScreenQuickPanelWindowController != null ? r6.mSubScreenQSEventHandler.getScreenTimeOut() : 0);
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        WakefulnessLifecycle wakefulnessLifecycle = this.mWakefulnessLifeCycle;
        if (wakefulnessLifecycle != null) {
            wakefulnessLifecycle.removeObserver(this);
            this.mWakefulnessLifeCycle = null;
        }
        BrightnessModeObserver brightnessModeObserver = this.mBrightnessObserver;
        brightnessModeObserver.mCr.unregisterContentObserver(brightnessModeObserver);
        SubscreenBrightnessController subscreenBrightnessController = this.mSubscreenBrightnessController;
        subscreenBrightnessController.getClass();
        subscreenBrightnessController.mDetailActivity = false;
        this.mDeviceStateManager.unregisterCallback(this.mDeviceStateCallback);
        super.onDestroy();
    }

    @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
    public final void onStartedGoingToSleep() {
        Log.d("SubscreenBrightnessDetailActivity", "onStartedGoingToSleep");
        finish();
    }
}
