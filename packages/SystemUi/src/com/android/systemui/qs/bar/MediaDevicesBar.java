package com.android.systemui.qs.bar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.keyguard.AbstractC0731x5bb8a836;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.FontSizeUtils;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.controls.controller.CustomDeviceControlsController;
import com.android.systemui.controls.controller.CustomDeviceControlsControllerImpl;
import com.android.systemui.controls.controller.CustomDeviceControlsControllerImpl$removeCallback$1;
import com.android.systemui.controls.dagger.ControlsComponent;
import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.controls.management.ControlsListingControllerImpl;
import com.android.systemui.media.MediaOutputHelper;
import com.android.systemui.media.MediaType;
import com.android.systemui.qs.QSBackupRestoreManager;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.ConfigurationState;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.TouchDelegateUtil;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MediaDevicesBar extends BarItemImpl implements TunerService.Tunable {
    public static final Uri EMERGENCY_MODE_URI = Settings.System.getUriFor("emergency_mode");
    public boolean mBrightnessBarOnTop;
    public final CustomDeviceControlsController mCustomDeviceControlsController;
    public LinearLayout mDeviceTouchArea;
    public TextView mDevicesTitleText;
    public boolean mIsAllowedOnPanel;
    public boolean mIsAllowedOnTop;
    public final ConfigurationState mLastConfigurationState;
    public final MediaOutputHelper mMediaOutputHelper;
    public TextView mMediaTitleText;
    public LinearLayout mMediaTouchArea;
    public int mOrientation;
    public final QSBackupRestoreManager mQSBackupRestoreManager;
    public final SecQSPanelResourcePicker mResourcePicker;
    public final SettingsHelper mSettingsHelper;
    public final MediaDevicesBar$$ExternalSyntheticLambda0 mSettingsListener;
    public final TunerService mTunerService;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.qs.bar.MediaDevicesBar$$ExternalSyntheticLambda0, com.android.systemui.util.SettingsHelper$OnChangedCallback] */
    public MediaDevicesBar(Context context, SettingsHelper settingsHelper, TunerService tunerService, CustomDeviceControlsController customDeviceControlsController, MediaOutputHelper mediaOutputHelper, QSBackupRestoreManager qSBackupRestoreManager) {
        super(context);
        this.mLastConfigurationState = new ConfigurationState(Arrays.asList(ConfigurationState.ConfigurationField.ORIENTATION, ConfigurationState.ConfigurationField.SCREEN_HEIGHT_DP, ConfigurationState.ConfigurationField.DISPLAY_DEVICE_TYPE));
        ?? r0 = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.qs.bar.MediaDevicesBar$$ExternalSyntheticLambda0
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                MediaDevicesBar.this.updateBarVisibility();
            }
        };
        this.mSettingsListener = r0;
        this.mContext = context;
        this.mSettingsHelper = settingsHelper;
        this.mTunerService = tunerService;
        this.mResourcePicker = (SecQSPanelResourcePicker) Dependency.get(SecQSPanelResourcePicker.class);
        this.mCustomDeviceControlsController = customDeviceControlsController;
        this.mQSBackupRestoreManager = qSBackupRestoreManager;
        settingsHelper.registerCallback(r0, EMERGENCY_MODE_URI);
        tunerService.addTunable(this, "qspanel_media_quickcontrol_bar_available");
        tunerService.addTunable(this, "qspanel_media_quickcontrol_bar_available_on_top");
        tunerService.addTunable(this, "brightness_on_top");
        this.mIsAllowedOnPanel = tunerService.getValue(1, "qspanel_media_quickcontrol_bar_available") != 0;
        this.mIsAllowedOnTop = tunerService.getValue(-1, "qspanel_media_quickcontrol_bar_available_on_top") != 0;
        this.mBrightnessBarOnTop = tunerService.getValue(1, "brightness_on_top") != 0;
        if (BasicRune.CONTROLS_SAMSUNG_STYLE) {
            Objects.requireNonNull(customDeviceControlsController);
            MediaDevicesBar$$ExternalSyntheticLambda1 mediaDevicesBar$$ExternalSyntheticLambda1 = new MediaDevicesBar$$ExternalSyntheticLambda1(customDeviceControlsController);
            final CustomDeviceControlsControllerImpl customDeviceControlsControllerImpl = (CustomDeviceControlsControllerImpl) customDeviceControlsController;
            ControlsComponent controlsComponent = customDeviceControlsControllerImpl.controlsComponent;
            if (controlsComponent.featureEnabled) {
                Log.i("CustomDeviceControlsControllerImpl", "setCallback()");
                Log.i("CustomDeviceControlsControllerImpl", "removeCallback()");
                customDeviceControlsControllerImpl.callback = null;
                customDeviceControlsControllerImpl.controlsComponent.getControlsListingController().ifPresent(new CustomDeviceControlsControllerImpl$removeCallback$1(customDeviceControlsControllerImpl));
                customDeviceControlsControllerImpl.callback = mediaDevicesBar$$ExternalSyntheticLambda1;
                if (customDeviceControlsControllerImpl.secureSettings.getInt("controls_enabled", 1) == 0) {
                    Log.i("CustomDeviceControlsControllerImpl", "fireControlsUpdate()");
                    MediaDevicesBar$$ExternalSyntheticLambda1 mediaDevicesBar$$ExternalSyntheticLambda12 = customDeviceControlsControllerImpl.callback;
                    if (mediaDevicesBar$$ExternalSyntheticLambda12 != null) {
                        CustomDeviceControlsControllerImpl customDeviceControlsControllerImpl2 = (CustomDeviceControlsControllerImpl) mediaDevicesBar$$ExternalSyntheticLambda12.f$0;
                        customDeviceControlsControllerImpl2.getClass();
                        Log.i("CustomDeviceControlsControllerImpl", "removeCallback()");
                        customDeviceControlsControllerImpl2.callback = null;
                        customDeviceControlsControllerImpl2.controlsComponent.getControlsListingController().ifPresent(new CustomDeviceControlsControllerImpl$removeCallback$1(customDeviceControlsControllerImpl2));
                    }
                } else {
                    controlsComponent.getControlsListingController().ifPresent(new Consumer() { // from class: com.android.systemui.controls.controller.CustomDeviceControlsControllerImpl$setCallback$1
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ((ControlsListingControllerImpl) ((ControlsListingController) obj)).addCallback(CustomDeviceControlsControllerImpl.this.listingCallback);
                        }
                    });
                }
            } else {
                Log.i("CustomDeviceControlsControllerImpl", "setCallback(): canceled");
            }
        }
        this.mMediaOutputHelper = mediaOutputHelper;
        qSBackupRestoreManager.addCallback("MediaDevices", new QSBackupRestoreManager.Callback() { // from class: com.android.systemui.qs.bar.MediaDevicesBar.1
            @Override // com.android.systemui.qs.QSBackupRestoreManager.Callback
            public final boolean isValidDB() {
                boolean z = true;
                int value = MediaDevicesBar.this.mTunerService.getValue(1, "qspanel_media_quickcontrol_bar_available");
                if (value != 0 && value != 1) {
                    z = false;
                }
                AbstractC0731x5bb8a836.m72m("showMediaDevices : ", value, " valid : ", z, "MediaDevices");
                return z;
            }

            @Override // com.android.systemui.qs.QSBackupRestoreManager.Callback
            public final String onBackup(boolean z) {
                StringBuilder sb = new StringBuilder("TAG::show_media_divices::");
                MediaDevicesBar mediaDevicesBar = MediaDevicesBar.this;
                if (z) {
                    String value = mediaDevicesBar.mTunerService.getValue("qspanel_media_quickcontrol_bar_available");
                    if (value == null) {
                        value = "1";
                    }
                    sb.append(value);
                } else {
                    mediaDevicesBar.getClass();
                }
                Log.d("MediaDevices", "builder : " + ((Object) sb));
                return sb.toString();
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r2v3 */
            /* JADX WARN: Type inference failed for: r2v4, types: [boolean, int] */
            /* JADX WARN: Type inference failed for: r2v5 */
            @Override // com.android.systemui.qs.QSBackupRestoreManager.Callback
            public final void onRestore(String str) {
                MediaDevicesBar mediaDevicesBar = MediaDevicesBar.this;
                mediaDevicesBar.getClass();
                String[] split = str.split("::");
                if (split[0].equals("show_media_divices")) {
                    ?? r2 = Integer.parseInt(split[1]) == 1 ? 1 : 0;
                    StringBuilder m49m = RowView$$ExternalSyntheticOutline0.m49m("showMediaDevices : ", r2, "   Integer.parseInt(sp[1]) : ");
                    m49m.append(Integer.parseInt(split[1]));
                    Log.d("MediaDevices", m49m.toString());
                    TunerService tunerService2 = mediaDevicesBar.mTunerService;
                    if (r2 != (tunerService2.getValue(1, "qspanel_media_quickcontrol_bar_available") != 0)) {
                        tunerService2.setValue((int) r2, "qspanel_media_quickcontrol_bar_available");
                    }
                }
            }
        });
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void destroy() {
        this.mCallback = null;
        this.mSettingsHelper.unregisterCallback(this.mSettingsListener);
        this.mTunerService.removeTunable(this);
        LinkedHashMap linkedHashMap = this.mQSBackupRestoreManager.mQSBnRMap;
        if (linkedHashMap.keySet().contains("MediaDevices")) {
            linkedHashMap.remove("MediaDevices");
        }
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final int getBarHeight() {
        int dimensionPixelSize;
        if (!this.mShowing) {
            return 0;
        }
        boolean z = this.mIsAllowedOnTop;
        SecQSPanelResourcePicker secQSPanelResourcePicker = this.mResourcePicker;
        if (z && this.mBrightnessBarOnTop) {
            if (this.mLastConfigurationState.getValue(ConfigurationState.ConfigurationField.ORIENTATION) == 2 && !QpRune.QUICK_TABLET) {
                Context context = this.mContext;
                secQSPanelResourcePicker.getClass();
                dimensionPixelSize = SecQSPanelResourcePicker.getBrightnessBarHeight(context);
                Context context2 = this.mContext;
                secQSPanelResourcePicker.getClass();
                return SecQSPanelResourcePicker.getQuickQSCommonBottomMargin(context2) + dimensionPixelSize;
            }
        }
        Context context3 = this.mContext;
        secQSPanelResourcePicker.getClass();
        dimensionPixelSize = context3.getResources().getDimensionPixelSize(R.dimen.qspanel_media_quickcontrol_height);
        Context context22 = this.mContext;
        secQSPanelResourcePicker.getClass();
        return SecQSPanelResourcePicker.getQuickQSCommonBottomMargin(context22) + dimensionPixelSize;
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final int getBarLayout() {
        return R.layout.qspanel_media_devices_bar_layout;
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void inflateViews(ViewGroup viewGroup) {
        final int i = 0;
        View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.qspanel_media_devices_bar_layout, viewGroup, false);
        this.mBarRootView = inflate;
        this.mMediaTouchArea = (LinearLayout) inflate.findViewById(R.id.media_touch_area);
        this.mDeviceTouchArea = (LinearLayout) this.mBarRootView.findViewById(R.id.device_touch_area);
        this.mMediaTitleText = (TextView) this.mBarRootView.findViewById(R.id.media_title);
        this.mDevicesTitleText = (TextView) this.mBarRootView.findViewById(R.id.quickcontrol_title);
        this.mMediaTitleText.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.qs.bar.MediaDevicesBar$$ExternalSyntheticLambda2
            public final /* synthetic */ MediaDevicesBar f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i) {
                    case 0:
                        MediaDevicesBar mediaDevicesBar = this.f$0;
                        mediaDevicesBar.getClass();
                        SystemUIAnalytics.sendRunstoneEventLog(SystemUIAnalytics.sCurrentScreenID, "QPPE1006", "QUICK_PANEL_LAYOUT");
                        Log.d(mediaDevicesBar.TAG, "startMediaActivity()");
                        mediaDevicesBar.mMediaOutputHelper.show(mediaDevicesBar.mContext, false, MediaType.QS, null, mediaDevicesBar.mMediaTitleText, null);
                        break;
                    default:
                        MediaDevicesBar mediaDevicesBar2 = this.f$0;
                        mediaDevicesBar2.getClass();
                        SystemUIAnalytics.sendRunstoneEventLog(SystemUIAnalytics.sCurrentScreenID, "QPPE1005", "QUICK_PANEL_LAYOUT");
                        if (BasicRune.CONTROLS_SAMSUNG_STYLE) {
                            ((CustomDeviceControlsControllerImpl) mediaDevicesBar2.mCustomDeviceControlsController).start();
                            break;
                        }
                        break;
                }
            }
        });
        final int i2 = 1;
        this.mDevicesTitleText.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.qs.bar.MediaDevicesBar$$ExternalSyntheticLambda2
            public final /* synthetic */ MediaDevicesBar f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i2) {
                    case 0:
                        MediaDevicesBar mediaDevicesBar = this.f$0;
                        mediaDevicesBar.getClass();
                        SystemUIAnalytics.sendRunstoneEventLog(SystemUIAnalytics.sCurrentScreenID, "QPPE1006", "QUICK_PANEL_LAYOUT");
                        Log.d(mediaDevicesBar.TAG, "startMediaActivity()");
                        mediaDevicesBar.mMediaOutputHelper.show(mediaDevicesBar.mContext, false, MediaType.QS, null, mediaDevicesBar.mMediaTitleText, null);
                        break;
                    default:
                        MediaDevicesBar mediaDevicesBar2 = this.f$0;
                        mediaDevicesBar2.getClass();
                        SystemUIAnalytics.sendRunstoneEventLog(SystemUIAnalytics.sCurrentScreenID, "QPPE1005", "QUICK_PANEL_LAYOUT");
                        if (BasicRune.CONTROLS_SAMSUNG_STYLE) {
                            ((CustomDeviceControlsControllerImpl) mediaDevicesBar2.mCustomDeviceControlsController).start();
                            break;
                        }
                        break;
                }
            }
        });
        updateBarVisibility();
        updateHeightMargins();
        TouchDelegateUtil touchDelegateUtil = TouchDelegateUtil.INSTANCE;
        LinearLayout linearLayout = this.mMediaTouchArea;
        TextView textView = this.mMediaTitleText;
        touchDelegateUtil.getClass();
        TouchDelegateUtil.expandTouchAreaAsParent(linearLayout, textView);
        TouchDelegateUtil.expandTouchAreaAsParent(this.mDeviceTouchArea, this.mDevicesTitleText);
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final boolean isAvailable() {
        return DeviceState.isMediaQuickControlBarAvailable(this.mContext);
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void onConfigChanged(Configuration configuration) {
        if (this.mBarRootView == null) {
            return;
        }
        int i = this.mContext.getResources().getConfiguration().orientation;
        ConfigurationState configurationState = this.mLastConfigurationState;
        if (configurationState.needToUpdate(configuration) || this.mOrientation != i) {
            this.mOrientation = i;
            updateBarVisibility();
            configurationState.update(configuration);
            updateHeightMargins();
        }
    }

    @Override // com.android.systemui.tuner.TunerService.Tunable
    public final void onTuningChanged(String str, String str2) {
        Log.d(this.TAG, FontProvider$$ExternalSyntheticOutline0.m32m("onTuningChanged(): key = ", str, ", newValue = ", str2));
        if (str2 == null) {
            return;
        }
        if ("qspanel_media_quickcontrol_bar_available".equals(str)) {
            this.mIsAllowedOnPanel = Integer.parseInt(str2) != 0;
        } else if ("qspanel_media_quickcontrol_bar_available_on_top".equals(str)) {
            this.mIsAllowedOnTop = Integer.parseInt(str2) != 0;
        } else if ("brightness_on_top".equals(str)) {
            this.mBrightnessBarOnTop = Integer.parseInt(str2) != 0;
        }
        updateHeightMargins();
        updateBarVisibility();
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void onUiModeChanged() {
        if (this.mBarRootView == null) {
            return;
        }
        updateResources();
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void setUnderneathQqs(boolean z) {
        this.mIsUnderneathQqs = z;
        updateHeightMargins();
    }

    public final RippleDrawable updateBackground() {
        Context context = this.mContext;
        this.mResourcePicker.getClass();
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.qspanel_media_quickcontrol_height);
        ColorStateList valueOf = ColorStateList.valueOf(this.mContext.getColor(R.color.notification_panel_theme_ripple_color));
        GradientDrawable gradientDrawable = new GradientDrawable();
        if (QpRune.QUICK_TABLET) {
            gradientDrawable.setColor(this.mContext.getResources().getColor(R.color.sec_media_devices_tablet_button_bg_fill_color));
        } else {
            gradientDrawable.setColor(this.mContext.getResources().getColor(R.color.sec_media_devices_button_bg_fill_color));
        }
        float f = dimensionPixelSize;
        gradientDrawable.setCornerRadius(f);
        float[] fArr = new float[8];
        Arrays.fill(fArr, f);
        ShapeDrawable shapeDrawable = new ShapeDrawable(new RoundRectShape(fArr, null, null));
        shapeDrawable.getPaint().setColor(-1);
        return new RippleDrawable(valueOf, gradientDrawable, shapeDrawable);
    }

    public final void updateBarVisibility() {
        boolean z = false;
        boolean z2 = this.mSettingsHelper.isEmergencyMode();
        if (this.mIsAllowedOnPanel && !z2 && (!this.mIsOnCollapsedState || this.mIsAllowedOnTop)) {
            z = true;
        }
        showBar(z);
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void updateHeightMargins() {
        if (this.mBarRootView == null) {
            return;
        }
        Context context = this.mContext;
        this.mResourcePicker.getClass();
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.qspanel_media_quickcontrol_height);
        Resources resources = this.mContext.getResources();
        boolean z = QpRune.QUICK_TABLET;
        int dimensionPixelSize2 = z ? resources.getDimensionPixelSize(R.dimen.qspanel_media_device_touch_line_between_padding_tablet) : resources.getDimensionPixelSize(R.dimen.qspanel_media_device_touch_line_between_padding);
        if (this.mIsAllowedOnTop && this.mBrightnessBarOnTop) {
            if (this.mLastConfigurationState.getValue(ConfigurationState.ConfigurationField.ORIENTATION) == 2 && !z) {
                dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.brightness_slider_height);
                dimensionPixelSize2 = this.mContext.getResources().getDimensionPixelSize(R.dimen.media_device_bar_between_padding_with_brightness_bar);
            }
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, dimensionPixelSize);
        layoutParams.bottomMargin = SecQSPanelResourcePicker.getQuickQSCommonBottomMargin(this.mContext);
        this.mBarRootView.setLayoutParams(layoutParams);
        this.mDeviceTouchArea.setPaddingRelative(0, 0, dimensionPixelSize2, 0);
        this.mMediaTouchArea.setPaddingRelative(dimensionPixelSize2, 0, 0, 0);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-1, dimensionPixelSize);
        this.mDevicesTitleText.setLayoutParams(layoutParams2);
        this.mMediaTitleText.setLayoutParams(layoutParams2);
        updateResources();
    }

    public final void updateResources() {
        int color = this.mContext.getColor(R.color.sec_media_devices_buttons_text_color);
        TextView textView = this.mMediaTitleText;
        if (textView != null) {
            textView.setTextColor(color);
            this.mMediaTitleText.setBackground(updateBackground());
            FontSizeUtils.updateFontSize(this.mMediaTitleText, R.dimen.qspanel_media_quickcontrol_text_size, 0.8f, 1.3f);
        }
        TextView textView2 = this.mDevicesTitleText;
        if (textView2 != null) {
            textView2.setTextColor(color);
            this.mDevicesTitleText.setBackground(updateBackground());
            FontSizeUtils.updateFontSize(this.mDevicesTitleText, R.dimen.qspanel_media_quickcontrol_text_size, 0.8f, 1.3f);
        }
    }
}
