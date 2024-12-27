package com.android.systemui.settings.brightness;

import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorPrivacyManager;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.CompoundButton;
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
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.qs.DetailAdapter;
import com.android.systemui.qs.SecQSSwitchPreference;
import com.android.systemui.settings.brightness.BrightnessController;
import com.android.systemui.settings.brightness.BrightnessController.AnonymousClass10;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SystemUIAnalytics;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.function.Function;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference0Impl;

public final class BrightnessDetailAdapter implements DetailAdapter {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Lazy activityStarter$delegate;
    public SecQSSwitchPreference autoBrightnessContainer;
    public final BrightnessDetailAdapter$autoBrightnessDelegate$1 autoBrightnessDelegate;
    public TextView autoBrightnessSummary;
    public SwitchCompat autoBrightnessSwitch;
    public BrightnessController brightnessController;
    public BrightnessObserver brightnessObserver;
    public final Context context;
    public RestrictedLockUtils.EnforcedAdmin enforcedAdmin;
    public final BrightnessController.Factory factory;
    public final QuickBarBrightnessExtraBrightness quickBarBrightnessExtraBrightness;
    public final QuickBrightnessSeadView quickBrightnessSeadView;
    public final KnoxStateMonitor quickKnox;
    public final QuickSALog quickSALog;
    public final Lazy sensorPrivacyManager$delegate;

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

    /* JADX WARN: Type inference failed for: r1v5, types: [com.android.systemui.settings.brightness.BrightnessDetailAdapter$autoBrightnessDelegate$1] */
    public BrightnessDetailAdapter(Context context, BrightnessController.Factory factory) {
        this.context = context;
        this.factory = factory;
        this.quickBarBrightnessExtraBrightness = QpRune.QUICK_BAR_BRIGHTNESS_EXTRA_BRIGHTNESS ? new QuickBarBrightnessExtraBrightness(context) : null;
        this.quickSALog = new QuickSALog(context);
        this.quickBrightnessSeadView = new QuickBrightnessSeadView();
        this.quickKnox = (KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class);
        this.autoBrightnessDelegate = new View.AccessibilityDelegate() { // from class: com.android.systemui.settings.brightness.BrightnessDetailAdapter$autoBrightnessDelegate$1
            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                String str;
                CharSequence text;
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                SecQSSwitchPreference secQSSwitchPreference = BrightnessDetailAdapter.this.autoBrightnessContainer;
                View findViewById = secQSSwitchPreference != null ? secQSSwitchPreference.findViewById(R.id.title) : null;
                TextView textView = findViewById instanceof TextView ? (TextView) findViewById : null;
                if (textView == null || (text = textView.getText()) == null || (str = text.toString()) == null) {
                    str = "";
                }
                SwitchCompat switchCompat = BrightnessDetailAdapter.this.autoBrightnessSwitch;
                accessibilityNodeInfo.setContentDescription(str + ", " + BrightnessDetailAdapter.this.context.getString(Intrinsics.areEqual(switchCompat != null ? Boolean.valueOf(switchCompat.isChecked()) : null, Boolean.TRUE) ? R.string.switch_bar_on : R.string.switch_bar_off) + ", Switch");
            }
        };
        this.activityStarter$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.settings.brightness.BrightnessDetailAdapter$activityStarter$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (ActivityStarter) Dependency.sDependency.getDependencyInner(ActivityStarter.class);
            }
        });
        this.sensorPrivacyManager$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.settings.brightness.BrightnessDetailAdapter$sensorPrivacyManager$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (SensorPrivacyManager) Dependency.sDependency.getDependencyInner(SensorPrivacyManager.class);
            }
        });
    }

    public static final View access$addDividerView(BrightnessDetailAdapter brightnessDetailAdapter, ViewGroup viewGroup) {
        brightnessDetailAdapter.getClass();
        View view = new View(brightnessDetailAdapter.context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, view.getContext().getResources().getDimensionPixelSize(R.dimen.qspanel_layout_detail_divider_height));
        int dimensionPixelSize = view.getContext().getResources().getDimensionPixelSize(R.dimen.qspanel_layout_detail_divider_side_padding);
        layoutParams.leftMargin = dimensionPixelSize;
        layoutParams.rightMargin = dimensionPixelSize;
        view.setLayoutParams(layoutParams);
        view.setBackgroundColor(view.getContext().getColor(R.color.qspanel_layout_brightness_detail_divider_background_color));
        viewGroup.addView(view);
        return view;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v1 */
    /* JADX WARN: Type inference failed for: r8v2, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r8v6 */
    public static final void access$setBrightness(BrightnessDetailAdapter brightnessDetailAdapter, boolean z, boolean z2) {
        ?? r8;
        KnoxStateMonitor knoxStateMonitor = brightnessDetailAdapter.quickKnox;
        if (Intrinsics.areEqual(knoxStateMonitor != null ? Boolean.valueOf(((KnoxStateMonitorImpl) knoxStateMonitor).isBrightnessBlocked()) : null, Boolean.TRUE)) {
            Log.d("BrightnessDetailAdapter", "Auto brightness options are not available by KnoxStateMonitor.");
            r8 = z;
        } else {
            r8 = z2;
        }
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("automatic = ", "BrightnessDetailAdapter", r8);
        Context context = brightnessDetailAdapter.context;
        if (DeviceType.isLightSensorSupported(context)) {
            if (QpRune.QUICK_BAR_BRIGHTNESS_PERSONAL_CONTROL) {
                Settings.Secure.putIntForUser(context.getContentResolver(), r8 == 0 ? "screen_brightness" : "brightness_pms_marker_screen", Settings.System.getIntForUser(context.getContentResolver(), r8 != 0 ? "screen_brightness" : "brightness_pms_marker_screen", 100, -2), -2);
            }
            Settings.System.putIntForUser(context.getContentResolver(), "screen_brightness_mode", r8, -2);
        } else {
            Settings.System.putIntForUser(context.getContentResolver(), "display_outdoor_mode", r8, -2);
        }
        SwitchCompat switchCompat = brightnessDetailAdapter.autoBrightnessSwitch;
        if (switchCompat != 0) {
            switchCompat.setChecked(r8);
        }
        QuickBarBrightnessExtraBrightness quickBarBrightnessExtraBrightness = brightnessDetailAdapter.quickBarBrightnessExtraBrightness;
        if (quickBarBrightnessExtraBrightness != null) {
            QuickBarBrightnessExtraBrightness quickBarBrightnessExtraBrightness2 = QpRune.QUICK_BAR_BRIGHTNESS_EXTRA_BRIGHTNESS ? quickBarBrightnessExtraBrightness : null;
            if (quickBarBrightnessExtraBrightness2 != null) {
                quickBarBrightnessExtraBrightness2.setExtraBrightnessLayoutVisibilityLogic(Boolean.valueOf((boolean) r8));
            }
        }
        QuickSALog quickSALog = brightnessDetailAdapter.quickSALog;
        if (quickSALog != null) {
            SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_BRIGHTNESS_DETAIL_ADAPTIVE_BRIGHTNESS);
            SharedPreferences.Editor editor = quickSALog.brightnessBarPrefEditor;
            if (editor != null) {
                editor.putBoolean(SystemUIAnalytics.STATUS_BRIGHTNESS_DETAIL_ADAPTIVE_BRIGHTNESS, z2);
                editor.commit();
            }
        }
    }

    @Override // com.android.systemui.plugins.qs.DetailAdapter
    public final View createDetailView(final Context context, View view, ViewGroup viewGroup) {
        if (context == null) {
            return new View(null);
        }
        View inflate = LayoutInflater.from(context).inflate(R.layout.sec_brightness_detail, viewGroup, false);
        if (inflate == null) {
            return new View(null);
        }
        ViewGroup viewGroup2 = (ViewGroup) inflate;
        this.autoBrightnessContainer = SecQSSwitchPreference.inflateSwitch(context, viewGroup2);
        DeviceType.isLightSensorSupported(context);
        int i = DeviceType.isLightSensorSupported(context) ? R.string.sec_brightness_auto_brightness_title : R.string.sec_brightness_outdoor_mode_title;
        SecQSSwitchPreference secQSSwitchPreference = this.autoBrightnessContainer;
        View findViewById = secQSSwitchPreference != null ? secQSSwitchPreference.findViewById(R.id.title) : null;
        TextView textView = findViewById instanceof TextView ? (TextView) findViewById : null;
        if (textView != null) {
            textView.setText(context.getString(i));
        }
        viewGroup2.addView(this.autoBrightnessContainer);
        SecQSSwitchPreference secQSSwitchPreference2 = this.autoBrightnessContainer;
        if (secQSSwitchPreference2 != null) {
            this.autoBrightnessSwitch = (SwitchCompat) secQSSwitchPreference2.findViewById(R.id.title_switch);
            TextView textView2 = (TextView) secQSSwitchPreference2.findViewById(R.id.title_summary);
            if (textView2 != null) {
                textView2.setVisibility(8);
            } else {
                textView2 = null;
            }
            this.autoBrightnessSummary = textView2;
            SecQSSwitchPreference secQSSwitchPreference3 = QpRune.QUICK_TABLET ? secQSSwitchPreference2 : null;
            if (secQSSwitchPreference3 != null) {
                secQSSwitchPreference3.setPadding(secQSSwitchPreference3.getPaddingLeft(), context.getResources().getDimensionPixelSize(R.dimen.sec_qs_detail_content_top_margin), secQSSwitchPreference3.getPaddingRight(), secQSSwitchPreference3.getPaddingBottom());
            }
            secQSSwitchPreference2.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.settings.brightness.BrightnessDetailAdapter$initBrightnessDetail$3$6
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    SwitchCompat switchCompat = BrightnessDetailAdapter.this.autoBrightnessSwitch;
                    boolean z = false;
                    if (switchCompat != null && switchCompat.isChecked()) {
                        z = true;
                    }
                    BrightnessDetailAdapter.access$setBrightness(BrightnessDetailAdapter.this, z, !z);
                }
            });
            secQSSwitchPreference2.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.settings.brightness.BrightnessDetailAdapter$initBrightnessDetail$3$7
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view2, MotionEvent motionEvent) {
                    BrightnessDetailAdapter brightnessDetailAdapter = BrightnessDetailAdapter.this;
                    RestrictedLockUtils.EnforcedAdmin enforcedAdmin = brightnessDetailAdapter.enforcedAdmin;
                    if (enforcedAdmin == null) {
                        return false;
                    }
                    Log.d("BrightnessDetailAdapter", "DetailView.admin = " + enforcedAdmin);
                    Log.d("BrightnessDetailAdapter", "showAdminSupportDetails.admin = " + brightnessDetailAdapter.enforcedAdmin);
                    ((ActivityStarter) brightnessDetailAdapter.activityStarter$delegate.getValue()).postStartActivityDismissingKeyguard(RestrictedLockUtils.getShowAdminSupportDetailsIntent(brightnessDetailAdapter.enforcedAdmin), 0);
                    return true;
                }
            });
        }
        final SwitchCompat switchCompat = this.autoBrightnessSwitch;
        if (switchCompat != null) {
            switchCompat.setChecked(isSwitchChecked());
            switchCompat.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.settings.brightness.BrightnessDetailAdapter$initBrightnessDetail$4$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    SwitchCompat switchCompat2 = view2 instanceof SwitchCompat ? (SwitchCompat) view2 : null;
                    boolean z = false;
                    if (switchCompat2 != null && switchCompat2.isChecked()) {
                        z = true;
                    }
                    BrightnessDetailAdapter.access$setBrightness(BrightnessDetailAdapter.this, !z, z);
                }
            });
            switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.android.systemui.settings.brightness.BrightnessDetailAdapter$initBrightnessDetail$4$2
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    BrightnessDetailAdapter.access$setBrightness(BrightnessDetailAdapter.this, !z, z);
                    switchCompat.announceForAccessibility(context.getString(z ? R.string.switch_bar_on : R.string.switch_bar_off));
                }
            });
        }
        Object systemService = context.getSystemService("sensor");
        SensorManager sensorManager = systemService instanceof SensorManager ? (SensorManager) systemService : null;
        if (sensorManager != null) {
            Sensor defaultSensor = sensorManager.getDefaultSensor(65604);
            Log.d("BrightnessDetailAdapter", "isCameraLightSensorSupported: " + defaultSensor);
            if (defaultSensor != null) {
                boolean z = !((SensorPrivacyManager) this.sensorPrivacyManager$delegate.getValue()).isSensorPrivacyEnabled(2);
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("initSensorPrivacy: isNonBlocked: ", "BrightnessDetailAdapter", z);
                SecQSSwitchPreference secQSSwitchPreference4 = this.autoBrightnessContainer;
                if (secQSSwitchPreference4 != null) {
                    secQSSwitchPreference4.setClickable(z);
                    secQSSwitchPreference4.setEnabled(z);
                    secQSSwitchPreference4.setAlpha(z ? 1.0f : 0.4f);
                }
                SwitchCompat switchCompat2 = this.autoBrightnessSwitch;
                if (switchCompat2 != null) {
                    switchCompat2.setClickable(z);
                    switchCompat2.setEnabled(z);
                }
                TextView textView3 = this.autoBrightnessSummary;
                if (textView3 != null) {
                    textView3.setVisibility(z ? 8 : 0);
                    textView3.setText(context.getString(R.string.sec_adaptive_brightness_disabled_sub_text));
                }
            }
        }
        QuickBarBrightnessExtraBrightness quickBarBrightnessExtraBrightness = this.quickBarBrightnessExtraBrightness;
        if (quickBarBrightnessExtraBrightness != null) {
            QuickBarBrightnessExtraBrightness quickBarBrightnessExtraBrightness2 = QpRune.QUICK_BAR_BRIGHTNESS_EXTRA_BRIGHTNESS ? quickBarBrightnessExtraBrightness : null;
            if (quickBarBrightnessExtraBrightness2 != null) {
                quickBarBrightnessExtraBrightness2.divider = access$addDividerView(new BrightnessDetailAdapter$initBrightnessDetail$6(this).this$0, viewGroup2);
                SecQSSwitchPreference inflateSwitch = SecQSSwitchPreference.inflateSwitch(context, viewGroup2);
                if (inflateSwitch != null) {
                    View findViewById2 = inflateSwitch.findViewById(R.id.title);
                    TextView textView4 = findViewById2 instanceof TextView ? (TextView) findViewById2 : null;
                    if (textView4 != null) {
                        textView4.setText(context.getString(R.string.sec_brightness_extra_brightness_title));
                    }
                    SwitchCompat switchCompat3 = (SwitchCompat) inflateSwitch.findViewById(R.id.title_switch);
                    if (switchCompat3 != null) {
                        quickBarBrightnessExtraBrightness2.extraBrightnessSwitch = switchCompat3;
                    }
                    TextView textView5 = (TextView) inflateSwitch.findViewById(R.id.title_summary);
                    if (textView5 != null) {
                        textView5.setText(context.getString(R.string.sec_brightness_extrs_brightness_sub_title));
                        textView5.setVisibility(0);
                    } else {
                        textView5 = null;
                    }
                    quickBarBrightnessExtraBrightness2.extraBrightnessSummary = textView5;
                    quickBarBrightnessExtraBrightness2.extraBrightnessContainer = inflateSwitch;
                    viewGroup2.addView(inflateSwitch);
                }
                SwitchCompat switchCompat4 = quickBarBrightnessExtraBrightness2.extraBrightnessSwitch;
                if (switchCompat4 != null) {
                    switchCompat4.setChecked(Settings.Secure.getIntForUser(context.getContentResolver(), "screen_extra_brightness", 0, -2) == 1);
                }
            }
        }
        final QuickBrightnessSeadView quickBrightnessSeadView = this.quickBrightnessSeadView;
        if (quickBrightnessSeadView != null) {
            new Function() { // from class: com.android.systemui.settings.brightness.BrightnessDetailAdapter$initBrightnessDetail$8
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return BrightnessDetailAdapter.access$addDividerView(BrightnessDetailAdapter.this, (ViewGroup) obj);
                }
            }.apply(viewGroup2);
            final SecQSSwitchPreference inflateSwitch2 = SecQSSwitchPreference.inflateSwitch(context, viewGroup2);
            if (inflateSwitch2 != null) {
                View findViewById3 = inflateSwitch2.findViewById(R.id.title);
                TextView textView6 = findViewById3 instanceof TextView ? (TextView) findViewById3 : null;
                if (textView6 != null) {
                    textView6.setText(context.getString(R.string.brightness_detail_sead_title));
                }
                TextView textView7 = (TextView) inflateSwitch2.findViewById(R.id.title_summary);
                if (textView7 != null) {
                    textView7.setText(context.getString(R.string.brightness_detail_sead_summary));
                    textView7.setVisibility(0);
                }
                SwitchCompat switchCompat5 = (SwitchCompat) inflateSwitch2.findViewById(R.id.title_switch);
                if (switchCompat5 != null) {
                    int i2 = QuickBrightnessSeadView.$r8$clinit;
                    switchCompat5.setChecked(Settings.System.getIntForUser(context.getContentResolver(), "ead_enabled", 0, -2) == 1);
                }
            } else {
                inflateSwitch2 = null;
            }
            if (inflateSwitch2 != null) {
                inflateSwitch2.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.settings.brightness.QuickBrightnessSeadView$initBrightnessDetail$1$1$1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        final QuickBrightnessSeadView quickBrightnessSeadView2 = QuickBrightnessSeadView.this;
                        SecQSSwitchPreference secQSSwitchPreference5 = inflateSwitch2;
                        final Context context2 = context;
                        int i3 = QuickBrightnessSeadView.$r8$clinit;
                        quickBrightnessSeadView2.getClass();
                        final SwitchCompat switchCompat6 = (SwitchCompat) secQSSwitchPreference5.findViewById(R.id.title_switch);
                        if (switchCompat6 != null) {
                            switchCompat6.setChecked(Settings.System.getIntForUser(context2.getContentResolver(), "ead_enabled", 0, -2) == 1);
                            switchCompat6.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.settings.brightness.QuickBrightnessSeadView$brightnessSeadSwitch$1$1
                                /* JADX WARN: Multi-variable type inference failed */
                                /* JADX WARN: Type inference failed for: r0v1 */
                                /* JADX WARN: Type inference failed for: r0v2, types: [boolean, int] */
                                /* JADX WARN: Type inference failed for: r0v3 */
                                /* JADX WARN: Type inference failed for: r0v4 */
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view3) {
                                    SwitchCompat switchCompat7 = view3 instanceof SwitchCompat ? (SwitchCompat) view3 : null;
                                    ?? r0 = 0;
                                    r0 = 0;
                                    if (switchCompat7 != null && switchCompat7.isChecked()) {
                                        r0 = 1;
                                    }
                                    SwitchCompat switchCompat8 = SwitchCompat.this;
                                    QuickBrightnessSeadView quickBrightnessSeadView3 = quickBrightnessSeadView2;
                                    Context context3 = context2;
                                    switchCompat8.setChecked(r0);
                                    int i4 = QuickBrightnessSeadView.$r8$clinit;
                                    quickBrightnessSeadView3.getClass();
                                    Settings.System.putIntForUser(context3.getContentResolver(), "ead_enabled", r0, -2);
                                }
                            });
                            switchCompat6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.android.systemui.settings.brightness.QuickBrightnessSeadView$brightnessSeadSwitch$1$2
                                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                                public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                                    SwitchCompat switchCompat7 = SwitchCompat.this;
                                    final QuickBrightnessSeadView quickBrightnessSeadView3 = quickBrightnessSeadView2;
                                    final Context context3 = context2;
                                    if (!z2) {
                                        int i4 = QuickBrightnessSeadView.$r8$clinit;
                                        quickBrightnessSeadView3.getClass();
                                    } else if (!quickBrightnessSeadView3.cameraInUse) {
                                        boolean z3 = Settings.System.getIntForUser(context3.getContentResolver(), "notified_ead_camera_use", 0, -2) == 1;
                                        quickBrightnessSeadView3.cameraInUse = z3;
                                        if (!z3) {
                                            if (quickBrightnessSeadView3.dialogCameraInUse == null) {
                                                SystemUIDialog systemUIDialog = new SystemUIDialog(context3, R.style.Theme_SystemUI_Dialog_Alert);
                                                systemUIDialog.setMessage(context3.getResources().getString(R.string.sec_ead_notice_camera_use));
                                                systemUIDialog.setPositiveButton(android.R.string.ok, null);
                                                systemUIDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.settings.brightness.QuickBrightnessSeadView$createDialog$1$1
                                                    @Override // android.content.DialogInterface.OnDismissListener
                                                    public final void onDismiss(DialogInterface dialogInterface) {
                                                        QuickBrightnessSeadView quickBrightnessSeadView4 = QuickBrightnessSeadView.this;
                                                        Context context4 = context3;
                                                        int i5 = QuickBrightnessSeadView.$r8$clinit;
                                                        quickBrightnessSeadView4.getClass();
                                                        Settings.System.putIntForUser(context4.getContentResolver(), "notified_ead_camera_use", 1, -2);
                                                        QuickBrightnessSeadView quickBrightnessSeadView5 = QuickBrightnessSeadView.this;
                                                        quickBrightnessSeadView5.dialogCameraInUse = null;
                                                        quickBrightnessSeadView5.cameraInUse = true;
                                                    }
                                                });
                                                quickBrightnessSeadView3.dialogCameraInUse = systemUIDialog;
                                                Unit unit = Unit.INSTANCE;
                                            }
                                            SystemUIDialog systemUIDialog2 = quickBrightnessSeadView3.dialogCameraInUse;
                                            if (systemUIDialog2 != null) {
                                                systemUIDialog2.show();
                                            }
                                        }
                                    }
                                    int i5 = QuickBrightnessSeadView.$r8$clinit;
                                    quickBrightnessSeadView3.getClass();
                                    Settings.System.putIntForUser(context3.getContentResolver(), "ead_enabled", z2 ? 1 : 0, -2);
                                    switchCompat7.setChecked(z2);
                                    SwitchCompat.this.announceForAccessibility(context2.getString(z2 ? R.string.switch_bar_on : R.string.switch_bar_off));
                                }
                            });
                        } else {
                            switchCompat6 = null;
                        }
                        if (switchCompat6 != null) {
                            QuickBrightnessSeadView quickBrightnessSeadView3 = QuickBrightnessSeadView.this;
                            Context context3 = context;
                            boolean z2 = !switchCompat6.isChecked();
                            switchCompat6.setChecked(z2);
                            quickBrightnessSeadView3.getClass();
                            Settings.System.putIntForUser(context3.getContentResolver(), "ead_enabled", z2 ? 1 : 0, -2);
                        }
                    }
                });
                viewGroup2.addView(inflateSwitch2);
            }
        }
        if (quickBarBrightnessExtraBrightness != null) {
            if (!QpRune.QUICK_BAR_BRIGHTNESS_EXTRA_BRIGHTNESS) {
                quickBarBrightnessExtraBrightness = null;
            }
            if (quickBarBrightnessExtraBrightness != null) {
                SwitchCompat switchCompat6 = this.autoBrightnessSwitch;
                quickBarBrightnessExtraBrightness.setExtraBrightnessLayoutClickListener();
                quickBarBrightnessExtraBrightness.setExtraBrightnessLayoutVisibilityLogic(switchCompat6 != null ? Boolean.valueOf(switchCompat6.isChecked()) : null);
            }
        }
        inflate.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.systemui.settings.brightness.BrightnessDetailAdapter$createDetailView$1$2
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view2) {
                SecQSSwitchPreference secQSSwitchPreference5;
                BrightnessDetailAdapter brightnessDetailAdapter = BrightnessDetailAdapter.this;
                BrightnessController create = brightnessDetailAdapter.factory.create((ToggleSlider) view2.findViewById(R.id.detail_slider));
                Handler handler = create.mBackgroundHandler;
                BrightnessController.AnonymousClass2 anonymousClass2 = create.mStartListeningRunnable;
                handler.removeCallbacks(anonymousClass2);
                handler.post(anonymousClass2);
                brightnessDetailAdapter.brightnessController = create;
                BrightnessDetailAdapter.this.enforcedAdmin = RestrictedLockUtilsInternal.checkIfRestrictionEnforced(context, "no_config_brightness", ActivityManager.semGetCurrentUser());
                BrightnessDetailSliderView brightnessDetailSliderView = (BrightnessDetailSliderView) view2.findViewById(R.id.detail_slider);
                if (brightnessDetailSliderView != null) {
                    if (!brightnessDetailSliderView.mSliderEnabled) {
                        brightnessDetailSliderView = null;
                    }
                    if (brightnessDetailSliderView != null) {
                        BrightnessController brightnessController = BrightnessDetailAdapter.this.brightnessController;
                        if (brightnessController == null) {
                            brightnessController = null;
                        }
                        brightnessController.getClass();
                        brightnessController.mBackgroundHandler.post(brightnessController.new AnonymousClass10());
                    }
                }
                BrightnessDetailAdapter brightnessDetailAdapter2 = BrightnessDetailAdapter.this;
                Context context2 = context;
                final BrightnessDetailAdapter brightnessDetailAdapter3 = BrightnessDetailAdapter.this;
                Function0 function0 = new Function0() { // from class: com.android.systemui.settings.brightness.BrightnessDetailAdapter$createDetailView$1$2$onViewAttachedToWindow$4
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return BrightnessDetailAdapter.this.autoBrightnessContainer;
                    }
                };
                final BrightnessDetailAdapter brightnessDetailAdapter4 = BrightnessDetailAdapter.this;
                Function0 function02 = new Function0() { // from class: com.android.systemui.settings.brightness.BrightnessDetailAdapter$createDetailView$1$2$onViewAttachedToWindow$5
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return BrightnessDetailAdapter.this.autoBrightnessSwitch;
                    }
                };
                final BrightnessDetailAdapter brightnessDetailAdapter5 = BrightnessDetailAdapter.this;
                BrightnessObserver brightnessObserver = new BrightnessObserver(context2, function0, function02, new PropertyReference0Impl(brightnessDetailAdapter5) { // from class: com.android.systemui.settings.brightness.BrightnessDetailAdapter$createDetailView$1$2$onViewAttachedToWindow$6
                    @Override // kotlin.jvm.internal.PropertyReference0Impl, kotlin.reflect.KProperty0
                    public final Object get() {
                        BrightnessDetailAdapter brightnessDetailAdapter6 = (BrightnessDetailAdapter) this.receiver;
                        int i3 = BrightnessDetailAdapter.$r8$clinit;
                        return Boolean.valueOf(brightnessDetailAdapter6.isSwitchChecked());
                    }
                });
                ContentResolver contentResolver = brightnessObserver.context.getContentResolver();
                if (contentResolver != null) {
                    contentResolver.unregisterContentObserver(brightnessObserver);
                    SecBrightnessController.Companion.getClass();
                    contentResolver.registerContentObserver(SecBrightnessController.BRIGHTNESS_MODE_URI, false, brightnessObserver, -1);
                    contentResolver.registerContentObserver(SecBrightnessController.SCREEN_DISPLAY_OUTDOOR_MODE_URI, false, brightnessObserver, -1);
                }
                brightnessDetailAdapter2.brightnessObserver = brightnessObserver;
                BrightnessDetailAdapter brightnessDetailAdapter6 = BrightnessDetailAdapter.this;
                SwitchCompat switchCompat7 = brightnessDetailAdapter6.autoBrightnessSwitch;
                if (switchCompat7 != null) {
                    switchCompat7.setEnabled(brightnessDetailAdapter6.enforcedAdmin == null);
                }
                BrightnessDetailAdapter brightnessDetailAdapter7 = BrightnessDetailAdapter.this;
                SecQSSwitchPreference secQSSwitchPreference6 = brightnessDetailAdapter7.autoBrightnessContainer;
                if (secQSSwitchPreference6 != null) {
                    secQSSwitchPreference6.setAccessibilityDelegate(brightnessDetailAdapter7.autoBrightnessDelegate);
                }
                QuickBarBrightnessExtraBrightness quickBarBrightnessExtraBrightness3 = BrightnessDetailAdapter.this.quickBarBrightnessExtraBrightness;
                if (quickBarBrightnessExtraBrightness3 != null) {
                    final QuickBarBrightnessExtraBrightness quickBarBrightnessExtraBrightness4 = QpRune.QUICK_BAR_BRIGHTNESS_EXTRA_BRIGHTNESS ? quickBarBrightnessExtraBrightness3 : null;
                    if (quickBarBrightnessExtraBrightness4 == null || (secQSSwitchPreference5 = quickBarBrightnessExtraBrightness4.extraBrightnessContainer) == null) {
                        return;
                    }
                    secQSSwitchPreference5.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.systemui.settings.brightness.QuickBarBrightnessExtraBrightness$onViewAttachedToWindow$1
                        @Override // android.view.View.AccessibilityDelegate
                        public final void onInitializeAccessibilityNodeInfo(View view3, AccessibilityNodeInfo accessibilityNodeInfo) {
                            String str;
                            CharSequence text;
                            String obj;
                            CharSequence text2;
                            super.onInitializeAccessibilityNodeInfo(view3, accessibilityNodeInfo);
                            SecQSSwitchPreference secQSSwitchPreference7 = QuickBarBrightnessExtraBrightness.this.extraBrightnessContainer;
                            View findViewById4 = secQSSwitchPreference7 != null ? secQSSwitchPreference7.findViewById(R.id.title) : null;
                            TextView textView8 = findViewById4 instanceof TextView ? (TextView) findViewById4 : null;
                            String str2 = "";
                            if (textView8 == null || (text2 = textView8.getText()) == null || (str = text2.toString()) == null) {
                                str = "";
                            }
                            TextView textView9 = QuickBarBrightnessExtraBrightness.this.extraBrightnessSummary;
                            if (textView9 != null && (text = textView9.getText()) != null && (obj = text.toString()) != null) {
                                str2 = obj;
                            }
                            SwitchCompat switchCompat8 = QuickBarBrightnessExtraBrightness.this.extraBrightnessSwitch;
                            accessibilityNodeInfo.setContentDescription(str + ", " + str2 + ", " + QuickBarBrightnessExtraBrightness.this.context.getString(Intrinsics.areEqual(switchCompat8 != null ? Boolean.valueOf(switchCompat8.isChecked()) : null, Boolean.TRUE) ? R.string.switch_bar_on : R.string.switch_bar_off) + ", Switch");
                        }
                    });
                }
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewDetachedFromWindow(View view2) {
                ContentResolver contentResolver;
                BrightnessController brightnessController = BrightnessDetailAdapter.this.brightnessController;
                if (brightnessController == null) {
                    brightnessController = null;
                }
                Handler handler = brightnessController.mBackgroundHandler;
                BrightnessController.AnonymousClass3 anonymousClass3 = brightnessController.mStopListeningRunnable;
                handler.removeCallbacks(anonymousClass3);
                handler.post(anonymousClass3);
                brightnessController.mControlValueInitialized = false;
                BrightnessDetailAdapter brightnessDetailAdapter = BrightnessDetailAdapter.this;
                brightnessDetailAdapter.enforcedAdmin = null;
                BrightnessObserver brightnessObserver = brightnessDetailAdapter.brightnessObserver;
                if (brightnessObserver != null && (contentResolver = brightnessObserver.context.getContentResolver()) != null) {
                    contentResolver.unregisterContentObserver(brightnessObserver);
                }
                BrightnessDetailAdapter.this.brightnessObserver = null;
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
        return this.context.getResources().getString(R.string.sec_brightness_detail_title);
    }

    @Override // com.android.systemui.plugins.qs.DetailAdapter
    public final /* bridge */ /* synthetic */ Boolean getToggleState() {
        return null;
    }

    public final boolean isSwitchChecked() {
        Context context = this.context;
        if (DeviceType.isLightSensorSupported(context)) {
            if (Settings.System.getIntForUser(context.getContentResolver(), "screen_brightness_mode", 0, -2) != 0) {
                return true;
            }
        } else if (Settings.System.getIntForUser(context.getContentResolver(), "display_outdoor_mode", 0, -2) != 0) {
            return true;
        }
        return false;
    }

    @Override // com.android.systemui.plugins.qs.DetailAdapter
    public final void setToggleState(boolean z) {
    }
}
