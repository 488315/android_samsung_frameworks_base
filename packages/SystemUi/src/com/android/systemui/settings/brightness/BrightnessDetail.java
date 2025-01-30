package com.android.systemui.settings.brightness;

import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorPrivacyManager;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.SwitchCompat;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.qs.SecQSPanelController;
import com.android.systemui.qs.SecQSSwitchPreference;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.qs.DetailAdapter;
import com.android.systemui.settings.brightness.BrightnessController;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SystemUIAnalytics;
import com.sec.ims.volte2.data.VolteConstants;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BrightnessDetail extends FrameLayout {
    public View divider;
    public LinearLayout mAutoBrightnessContainer;
    public final C23885 mAutoBrightnessDelegate;
    public TextView mAutoBrightnessSummary;
    public SwitchCompat mAutoBrightnessSwitch;
    public final SharedPreferences.Editor mBrightnessBarPrefEditor;
    public BrightnessController mBrightnessController;
    public final BrightnessController.Factory mBrightnessControllerFactory;
    public final C23841 mBrightnessDetailAdapter;
    public final Context mContext;
    public LinearLayout mExtraBrightnessContainer;
    public final C23896 mExtraBrightnessDelegate;
    public TextView mExtraBrightnessSummary;
    public SwitchCompat mExtraBrightnessSwitch;
    public final SecQSPanelController mQSPanelController;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class BrightnessObserver extends ContentObserver {
        public BrightnessObserver(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            if (BrightnessController.BRIGHTNESS_MODE_URI.equals(uri) || BrightnessController.SCREEN_DISPLAY_OUTDOOR_MODE_URI.equals(uri)) {
                BrightnessDetail brightnessDetail = BrightnessDetail.this;
                LinearLayout linearLayout = brightnessDetail.mAutoBrightnessContainer;
                if (linearLayout != null) {
                    linearLayout.setClickable(true);
                }
                SwitchCompat switchCompat = brightnessDetail.mAutoBrightnessSwitch;
                if (switchCompat != null) {
                    switchCompat.setEnabled(true);
                    brightnessDetail.mAutoBrightnessSwitch.setChecked(brightnessDetail.isSwitchChecked());
                }
            }
        }
    }

    /* renamed from: -$$Nest$msetExtraBrightnessLayoutVisibilityLogic, reason: not valid java name */
    public static void m1639$$Nest$msetExtraBrightnessLayoutVisibilityLogic(BrightnessDetail brightnessDetail, boolean z) {
        brightnessDetail.divider.setVisibility(z ? 8 : 0);
        brightnessDetail.mExtraBrightnessContainer.setVisibility(z ? 8 : 0);
    }

    /* renamed from: -$$Nest$msetExtraBrightnessLogic, reason: not valid java name */
    public static void m1640$$Nest$msetExtraBrightnessLogic(BrightnessDetail brightnessDetail, boolean z) {
        brightnessDetail.getClass();
        Log.secD("BrightenssDetail", "setExtraBrightness : " + (z ? 1 : 0));
        Settings.Secure.putIntForUser(brightnessDetail.mContext.getContentResolver(), "screen_extra_brightness", z ? 1 : 0, -2);
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.settings.brightness.BrightnessDetail$5] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.settings.brightness.BrightnessDetail$6] */
    public BrightnessDetail(Context context, SecQSPanelController secQSPanelController, BrightnessController.Factory factory) {
        super(context);
        this.mBrightnessDetailAdapter = new C23841();
        this.mAutoBrightnessDelegate = new View.AccessibilityDelegate() { // from class: com.android.systemui.settings.brightness.BrightnessDetail.5
            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                String string = BrightnessDetail.this.mAutoBrightnessSwitch.isChecked() ? BrightnessDetail.this.mContext.getString(R.string.switch_bar_on) : BrightnessDetail.this.mContext.getString(R.string.switch_bar_off);
                accessibilityNodeInfo.setContentDescription(((TextView) BrightnessDetail.this.mAutoBrightnessContainer.findViewById(R.id.title)).getText().toString() + ", " + string + ", Switch");
            }
        };
        this.mExtraBrightnessDelegate = new View.AccessibilityDelegate() { // from class: com.android.systemui.settings.brightness.BrightnessDetail.6
            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                String string = BrightnessDetail.this.mExtraBrightnessSwitch.isChecked() ? BrightnessDetail.this.mContext.getString(R.string.switch_bar_on) : BrightnessDetail.this.mContext.getString(R.string.switch_bar_off);
                accessibilityNodeInfo.setContentDescription(((TextView) BrightnessDetail.this.mExtraBrightnessContainer.findViewById(R.id.title)).getText().toString() + ", " + BrightnessDetail.this.mExtraBrightnessSummary.getText().toString() + ", " + string + ", Switch");
            }
        };
        this.mContext = context;
        this.mQSPanelController = secQSPanelController;
        this.mBrightnessControllerFactory = factory;
        SharedPreferences sharedPreferences = context.getSharedPreferences("quick_pref", 0);
        if (sharedPreferences != null) {
            boolean z = DeviceType.isLightSensorSupported(context) ? Settings.System.getIntForUser(context.getContentResolver(), "screen_brightness_mode", 0, -2) != 0 : Settings.System.getIntForUser(context.getContentResolver(), "display_outdoor_mode", 0, -2) != 0;
            SharedPreferences.Editor edit = sharedPreferences.edit();
            this.mBrightnessBarPrefEditor = edit;
            edit.putBoolean("QPDS1006", z);
            edit.commit();
        }
    }

    public final boolean isSwitchChecked() {
        return DeviceType.isLightSensorSupported(this.mContext) ? Settings.System.getIntForUser(this.mContext.getContentResolver(), "screen_brightness_mode", 0, -2) != 0 : Settings.System.getIntForUser(this.mContext.getContentResolver(), "display_outdoor_mode", 0, -2) != 0;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.settings.brightness.BrightnessDetail$1 */
    public final class C23841 implements DetailAdapter {
        public BrightnessDetailSliderView mBrightnessDetailSliderView;
        public BrightnessObserver mBrightnessObserver;
        public RestrictedLockUtils.EnforcedAdmin mEnforcedAdmin;

        /* renamed from: -$$Nest$msetBrightness, reason: not valid java name */
        public static void m1641$$Nest$msetBrightness(C23841 c23841, Boolean bool, Boolean bool2) {
            boolean z;
            c23841.getClass();
            if (((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).isBrightnessBlocked()) {
                Log.d("BrightenssDetail", "Auto brightness options are not available by KnoxStateMonitor.");
                z = false;
            } else {
                z = true;
            }
            BrightnessDetail brightnessDetail = BrightnessDetail.this;
            if (z) {
                c23841.setAutoBrightness(bool2.booleanValue());
                brightnessDetail.mAutoBrightnessSwitch.setChecked(bool2.booleanValue());
                if (QpRune.QUICK_BAR_EXTRA_BRIGHTNESS) {
                    BrightnessDetail.m1639$$Nest$msetExtraBrightnessLayoutVisibilityLogic(brightnessDetail, bool2.booleanValue());
                }
            } else {
                c23841.setAutoBrightness(bool.booleanValue());
                brightnessDetail.mAutoBrightnessSwitch.setChecked(bool.booleanValue());
                if (QpRune.QUICK_BAR_EXTRA_BRIGHTNESS) {
                    BrightnessDetail.m1639$$Nest$msetExtraBrightnessLayoutVisibilityLogic(brightnessDetail, bool.booleanValue());
                }
            }
            SystemUIAnalytics.sendEventLog(SystemUIAnalytics.sCurrentScreenID, "QPDE1006");
            brightnessDetail.mBrightnessBarPrefEditor.putBoolean("QPDS1006", bool2.booleanValue());
            brightnessDetail.mBrightnessBarPrefEditor.commit();
        }

        public C23841() {
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final View createDetailView(final Context context, View view, ViewGroup viewGroup) {
            final BrightnessDetail brightnessDetail = BrightnessDetail.this;
            View inflate = LayoutInflater.from(brightnessDetail.mContext).inflate(R.layout.sec_brightness_detail, viewGroup, false);
            ViewGroup viewGroup2 = (ViewGroup) inflate;
            brightnessDetail.mAutoBrightnessContainer = SecQSSwitchPreference.inflateSwitch(brightnessDetail.mContext, viewGroup2);
            DeviceType.isLightSensorSupported(context);
            ((TextView) brightnessDetail.mAutoBrightnessContainer.findViewById(R.id.title)).setText(DeviceType.isLightSensorSupported(context) ? context.getString(R.string.sec_brightness_auto_brightness_title) : context.getString(R.string.sec_brightness_outdoor_mode_title));
            viewGroup2.addView(brightnessDetail.mAutoBrightnessContainer);
            brightnessDetail.mAutoBrightnessSwitch = (SwitchCompat) brightnessDetail.mAutoBrightnessContainer.findViewById(R.id.title_switch);
            TextView textView = (TextView) brightnessDetail.mAutoBrightnessContainer.findViewById(R.id.title_summary);
            brightnessDetail.mAutoBrightnessSummary = textView;
            textView.setVisibility(8);
            if (QpRune.QUICK_TABLET) {
                int dimensionPixelSize = brightnessDetail.getResources().getDimensionPixelSize(R.dimen.sec_qs_detail_content_top_margin);
                LinearLayout linearLayout = brightnessDetail.mAutoBrightnessContainer;
                linearLayout.setPadding(linearLayout.getPaddingLeft(), dimensionPixelSize, brightnessDetail.mAutoBrightnessContainer.getPaddingRight(), brightnessDetail.mAutoBrightnessContainer.getPaddingBottom());
            }
            boolean z = QpRune.QUICK_BAR_EXTRA_BRIGHTNESS;
            if (z) {
                int dimensionPixelSize2 = brightnessDetail.mContext.getResources().getDimensionPixelSize(R.dimen.qspanel_layout_detail_divider_height);
                int dimensionPixelSize3 = brightnessDetail.mContext.getResources().getDimensionPixelSize(R.dimen.qspanel_layout_detail_divider_side_padding);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, dimensionPixelSize2);
                layoutParams.leftMargin = dimensionPixelSize3;
                layoutParams.rightMargin = dimensionPixelSize3;
                View view2 = new View(brightnessDetail.mContext);
                brightnessDetail.divider = view2;
                view2.setLayoutParams(layoutParams);
                brightnessDetail.divider.setBackgroundColor(brightnessDetail.mContext.getColor(R.color.qspanel_layout_brightness_detail_divider_background_color));
                viewGroup2.addView(brightnessDetail.divider);
                brightnessDetail.mExtraBrightnessContainer = SecQSSwitchPreference.inflateSwitch(brightnessDetail.mContext, viewGroup2);
                ((TextView) brightnessDetail.mExtraBrightnessContainer.findViewById(R.id.title)).setText(context.getString(R.string.sec_brightness_extra_brightness_title));
                brightnessDetail.mExtraBrightnessSwitch = (SwitchCompat) brightnessDetail.mExtraBrightnessContainer.findViewById(R.id.title_switch);
                String string = context.getString(R.string.sec_brightness_extrs_brightness_sub_title);
                TextView textView2 = (TextView) brightnessDetail.mExtraBrightnessContainer.findViewById(R.id.title_summary);
                brightnessDetail.mExtraBrightnessSummary = textView2;
                textView2.setText(string);
                brightnessDetail.mExtraBrightnessSummary.setVisibility(0);
                viewGroup2.addView(brightnessDetail.mExtraBrightnessContainer);
                brightnessDetail.mExtraBrightnessSwitch.setChecked(Settings.Secure.getIntForUser(brightnessDetail.mContext.getContentResolver(), "screen_extra_brightness", 0, -2) == 1);
            }
            brightnessDetail.mAutoBrightnessSwitch.setChecked(brightnessDetail.isSwitchChecked());
            brightnessDetail.mAutoBrightnessContainer.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.settings.brightness.BrightnessDetail.1.2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view3) {
                    boolean isChecked = BrightnessDetail.this.mAutoBrightnessSwitch.isChecked();
                    C23841.m1641$$Nest$msetBrightness(C23841.this, Boolean.valueOf(isChecked), Boolean.valueOf(!isChecked));
                }
            });
            brightnessDetail.mAutoBrightnessContainer.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.settings.brightness.BrightnessDetail.1.3
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view3, MotionEvent motionEvent) {
                    if (C23841.this.mEnforcedAdmin == null) {
                        return false;
                    }
                    Log.d("TAG", "DetailView.admin = " + C23841.this.mEnforcedAdmin + ", return.");
                    C23841 c23841 = C23841.this;
                    Log.d("BrightenssDetail", "showAdminSupportDetails.admin = " + c23841.mEnforcedAdmin);
                    Context context2 = BrightnessDetail.this.mContext;
                    ((ActivityStarter) Dependency.get(ActivityStarter.class)).postStartActivityDismissingKeyguard(RestrictedLockUtils.getShowAdminSupportDetailsIntent(c23841.mEnforcedAdmin), 0);
                    return true;
                }
            });
            brightnessDetail.mAutoBrightnessSwitch.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.settings.brightness.BrightnessDetail.1.4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view3) {
                    boolean isChecked = ((SwitchCompat) view3).isChecked();
                    C23841.m1641$$Nest$msetBrightness(C23841.this, Boolean.valueOf(!isChecked), Boolean.valueOf(isChecked));
                }
            });
            brightnessDetail.mAutoBrightnessSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.android.systemui.settings.brightness.BrightnessDetail.1.5
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                    C23841.m1641$$Nest$msetBrightness(C23841.this, Boolean.valueOf(!z2), Boolean.valueOf(z2));
                    BrightnessDetail brightnessDetail2 = BrightnessDetail.this;
                    brightnessDetail2.mAutoBrightnessSwitch.announceForAccessibility(brightnessDetail2.mContext.getString(z2 ? R.string.switch_bar_on : R.string.switch_bar_off));
                }
            });
            Log.d("BrightenssDetail", "isCameraLightSensorSupported");
            Sensor defaultSensor = ((SensorManager) context.getSystemService("sensor")).getDefaultSensor(65604);
            Log.d("BrightenssDetail", "isCameraLightSensorSupported" + defaultSensor);
            if (defaultSensor != null) {
                boolean isSensorPrivacyEnabled = ((SensorPrivacyManager) Dependency.get(SensorPrivacyManager.class)).isSensorPrivacyEnabled(2);
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("isBlocked ", isSensorPrivacyEnabled, "BrightenssDetail");
                boolean z2 = !isSensorPrivacyEnabled;
                brightnessDetail.mAutoBrightnessContainer.setClickable(z2);
                brightnessDetail.mAutoBrightnessContainer.setEnabled(z2);
                brightnessDetail.mAutoBrightnessSwitch.setClickable(z2);
                brightnessDetail.mAutoBrightnessSwitch.setEnabled(z2);
                brightnessDetail.mAutoBrightnessSummary.setVisibility(isSensorPrivacyEnabled ? 0 : 8);
                brightnessDetail.mAutoBrightnessSummary.setText(context.getString(R.string.sec_adaptive_brightness_disabled_sub_text));
                brightnessDetail.mAutoBrightnessContainer.setAlpha(isSensorPrivacyEnabled ? 0.4f : 1.0f);
            }
            if (z) {
                brightnessDetail.mExtraBrightnessContainer.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.settings.brightness.BrightnessDetail.2
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view3) {
                        boolean z3 = !BrightnessDetail.this.mExtraBrightnessSwitch.isChecked();
                        BrightnessDetail.this.mExtraBrightnessSwitch.setChecked(z3);
                        BrightnessDetail.m1640$$Nest$msetExtraBrightnessLogic(BrightnessDetail.this, z3);
                    }
                });
                brightnessDetail.mExtraBrightnessSwitch.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.settings.brightness.BrightnessDetail.3
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view3) {
                        boolean isChecked = ((SwitchCompat) view3).isChecked();
                        BrightnessDetail.this.mExtraBrightnessSwitch.setChecked(isChecked);
                        BrightnessDetail.m1640$$Nest$msetExtraBrightnessLogic(BrightnessDetail.this, isChecked);
                    }
                });
                brightnessDetail.mExtraBrightnessSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.android.systemui.settings.brightness.BrightnessDetail.4
                    @Override // android.widget.CompoundButton.OnCheckedChangeListener
                    public final void onCheckedChanged(CompoundButton compoundButton, boolean z3) {
                        BrightnessDetail.this.mExtraBrightnessSwitch.setChecked(z3);
                        BrightnessDetail.m1640$$Nest$msetExtraBrightnessLogic(BrightnessDetail.this, z3);
                        BrightnessDetail brightnessDetail2 = BrightnessDetail.this;
                        brightnessDetail2.mExtraBrightnessSwitch.announceForAccessibility(brightnessDetail2.mContext.getString(z3 ? R.string.switch_bar_on : R.string.switch_bar_off));
                    }
                });
                BrightnessDetail.m1639$$Nest$msetExtraBrightnessLayoutVisibilityLogic(brightnessDetail, brightnessDetail.mAutoBrightnessSwitch.isChecked());
            }
            inflate.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.systemui.settings.brightness.BrightnessDetail.1.1
                @Override // android.view.View.OnAttachStateChangeListener
                public final void onViewAttachedToWindow(View view3) {
                    BrightnessDetail brightnessDetail2 = BrightnessDetail.this;
                    BrightnessController.Factory factory = brightnessDetail2.mBrightnessControllerFactory;
                    ToggleSlider toggleSlider = (ToggleSlider) view3.findViewById(R.id.detail_slider);
                    factory.getClass();
                    brightnessDetail2.mBrightnessController = new BrightnessController(factory.mContext, toggleSlider, factory.mUserTracker, factory.mDisplayTracker, factory.mMainExecutor, factory.mBackgroundHandler);
                    BrightnessController brightnessController = BrightnessDetail.this.mBrightnessController;
                    brightnessController.mBackgroundHandler.post(brightnessController.mStartListeningRunnable);
                    C23841.this.mEnforcedAdmin = RestrictedLockUtilsInternal.checkIfRestrictionEnforced(context, "no_config_brightness", ActivityManager.semGetCurrentUser());
                    C23841.this.mBrightnessDetailSliderView = (BrightnessDetailSliderView) view3.findViewById(R.id.detail_slider);
                    C23841 c23841 = C23841.this;
                    BrightnessDetailSliderView brightnessDetailSliderView = c23841.mBrightnessDetailSliderView;
                    if (brightnessDetailSliderView != null && brightnessDetailSliderView.mSliderEnabled) {
                        BrightnessDetail.this.mBrightnessController.checkRestrictionAndSetEnabled();
                    }
                    C23841 c238412 = C23841.this;
                    SwitchCompat switchCompat = BrightnessDetail.this.mAutoBrightnessSwitch;
                    if (switchCompat != null) {
                        switchCompat.setEnabled(c238412.mEnforcedAdmin == null);
                    }
                    C23841 c238413 = C23841.this;
                    SwitchCompat switchCompat2 = BrightnessDetail.this.mExtraBrightnessSwitch;
                    if (switchCompat2 != null) {
                        switchCompat2.setEnabled(c238413.mEnforcedAdmin == null);
                    }
                    C23841.this.mBrightnessObserver = BrightnessDetail.this.new BrightnessObserver(new Handler());
                    BrightnessObserver brightnessObserver = C23841.this.mBrightnessObserver;
                    ContentResolver contentResolver = BrightnessDetail.this.mContext.getContentResolver();
                    contentResolver.unregisterContentObserver(brightnessObserver);
                    contentResolver.registerContentObserver(BrightnessController.BRIGHTNESS_MODE_URI, false, brightnessObserver, -1);
                    contentResolver.registerContentObserver(BrightnessController.SCREEN_DISPLAY_OUTDOOR_MODE_URI, false, brightnessObserver, -1);
                    BrightnessDetail brightnessDetail3 = BrightnessDetail.this;
                    LinearLayout linearLayout2 = brightnessDetail3.mAutoBrightnessContainer;
                    if (linearLayout2 != null) {
                        linearLayout2.setAccessibilityDelegate(brightnessDetail3.mAutoBrightnessDelegate);
                    }
                    BrightnessDetail brightnessDetail4 = BrightnessDetail.this;
                    LinearLayout linearLayout3 = brightnessDetail4.mExtraBrightnessContainer;
                    if (linearLayout3 != null) {
                        linearLayout3.setAccessibilityDelegate(brightnessDetail4.mExtraBrightnessDelegate);
                    }
                }

                @Override // android.view.View.OnAttachStateChangeListener
                public final void onViewDetachedFromWindow(View view3) {
                    BrightnessController brightnessController = BrightnessDetail.this.mBrightnessController;
                    brightnessController.mBackgroundHandler.post(brightnessController.mStopListeningRunnable);
                    brightnessController.mControlValueInitialized = false;
                    C23841 c23841 = C23841.this;
                    c23841.mEnforcedAdmin = null;
                    BrightnessObserver brightnessObserver = c23841.mBrightnessObserver;
                    BrightnessDetail.this.mContext.getContentResolver().unregisterContentObserver(brightnessObserver);
                    C23841.this.mBrightnessObserver = null;
                }
            });
            return inflate;
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final int getMetricsCategory() {
            return VolteConstants.ErrorCode.UT_RETRY_TO_CDMA_DIAL;
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final Intent getSettingsIntent() {
            return new Intent("android.settings.DISPLAY_SETTINGS");
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final CharSequence getTitle() {
            return BrightnessDetail.this.mContext.getResources().getString(R.string.sec_brightness_detail_title);
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final Boolean getToggleState() {
            return null;
        }

        public final void setAutoBrightness(boolean z) {
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("automatic = ", z, "BrightenssDetail");
            BrightnessDetail brightnessDetail = BrightnessDetail.this;
            if (brightnessDetail.mBrightnessController != null) {
                if (!DeviceType.isLightSensorSupported(brightnessDetail.mContext)) {
                    Settings.System.putIntForUser(brightnessDetail.mContext.getContentResolver(), "display_outdoor_mode", z ? 1 : 0, -2);
                    return;
                }
                if (QpRune.QUICK_BAR_BRIGHTNESS_PERSONAL_CONTROL) {
                    String str = z ? "screen_brightness" : "brightness_pms_marker_screen";
                    Settings.Secure.putIntForUser(brightnessDetail.mContext.getContentResolver(), z ? "brightness_pms_marker_screen" : "screen_brightness", Settings.System.getIntForUser(brightnessDetail.mContext.getContentResolver(), str, 100, -2), -2);
                }
                Settings.System.putIntForUser(brightnessDetail.mContext.getContentResolver(), "screen_brightness_mode", z ? 1 : 0, -2);
            }
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final void setToggleState(boolean z) {
        }
    }
}
