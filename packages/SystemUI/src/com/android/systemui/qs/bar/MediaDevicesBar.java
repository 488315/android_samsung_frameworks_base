package com.android.systemui.qs.bar;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.graphics.drawable.SeslRecoilDrawable;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecSecurityContainerController$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.FontSizeUtils;
import com.android.systemui.R;
import com.android.systemui.controls.controller.SecDeviceControlsController;
import com.android.systemui.controls.controller.SecDeviceControlsControllerImpl;
import com.android.systemui.controls.controller.SecDeviceControlsControllerImpl$$ExternalSyntheticLambda0;
import com.android.systemui.controls.controller.SecDeviceControlsControllerImpl$sam$java_util_function_Consumer$0;
import com.android.systemui.controls.dagger.ControlsComponent;
import com.android.systemui.media.MediaOutputHelper;
import com.android.systemui.qs.QSBackupRestoreManager;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.panelresource.SecQSPanelResourceCommon;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.ConfigurationState;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.Arrays;

/* loaded from: classes2.dex */
public class MediaDevicesBar extends BarItemImpl implements TunerService.Tunable {
    public static final Uri EMERGENCY_MODE_URI = Settings.System.getUriFor(SettingsHelper.INDEX_EMERGENCY_MODE);
    public boolean mBrightnessBarOnTop;
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
    public final SecDeviceControlsController mSecDeviceControlsController;
    private final SettingsHelper mSettingsHelper;
    private final SettingsHelper.OnChangedCallback mSettingsListener;
    public final TunerService mTunerService;

    /* renamed from: com.android.systemui.qs.bar.MediaDevicesBar$1, reason: invalid class name */
    public class AnonymousClass1 {
        public AnonymousClass1() {
        }
    }

    public MediaDevicesBar(Context context, SettingsHelper settingsHelper, TunerService tunerService, SecDeviceControlsController secDeviceControlsController, MediaOutputHelper mediaOutputHelper, QSBackupRestoreManager qSBackupRestoreManager) {
        super(context);
        this.mLastConfigurationState = new ConfigurationState(Arrays.asList(ConfigurationState.ConfigurationField.ORIENTATION, ConfigurationState.ConfigurationField.SCREEN_HEIGHT_DP, ConfigurationState.ConfigurationField.DISPLAY_DEVICE_TYPE));
        SettingsHelper.OnChangedCallback onChangedCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.qs.bar.MediaDevicesBar$$ExternalSyntheticLambda0
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                Uri uri2 = MediaDevicesBar.EMERGENCY_MODE_URI;
                this.f$0.updateBarVisibility();
            }
        };
        this.mSettingsListener = onChangedCallback;
        this.mContext = context;
        this.mSettingsHelper = settingsHelper;
        this.mTunerService = tunerService;
        this.mResourcePicker = (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
        this.mSecDeviceControlsController = secDeviceControlsController;
        this.mQSBackupRestoreManager = qSBackupRestoreManager;
        settingsHelper.registerCallback(onChangedCallback, EMERGENCY_MODE_URI);
        tunerService.addTunable(this, "qspanel_media_quickcontrol_bar_available");
        tunerService.addTunable(this, "brightness_on_top");
        boolean z = tunerService.getValue(0, "qspanel_media_quickcontrol_bar_available") != 0;
        this.mIsAllowedOnPanel = z;
        this.mIsAllowedOnTop = z;
        this.mBrightnessBarOnTop = tunerService.getValue(1, "brightness_on_top") != 0;
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        SecDeviceControlsControllerImpl secDeviceControlsControllerImpl = (SecDeviceControlsControllerImpl) secDeviceControlsController;
        ControlsComponent controlsComponent = secDeviceControlsControllerImpl.controlsComponent;
        if (controlsComponent.featureEnabled) {
            secDeviceControlsControllerImpl.callback = null;
            controlsComponent.controlsListingController.ifPresent(new SecDeviceControlsControllerImpl$sam$java_util_function_Consumer$0(new SecDeviceControlsControllerImpl$$ExternalSyntheticLambda0(secDeviceControlsControllerImpl, 1)));
            secDeviceControlsControllerImpl.callback = anonymousClass1;
            if (secDeviceControlsControllerImpl.secureSettings.getInt("controls_enabled", 1) == 0) {
                Log.i("SecDeviceControlsControllerImpl", "fireControlsUpdate()");
                AnonymousClass1 anonymousClass12 = secDeviceControlsControllerImpl.callback;
                if (anonymousClass12 != null) {
                    SecDeviceControlsControllerImpl secDeviceControlsControllerImpl2 = (SecDeviceControlsControllerImpl) MediaDevicesBar.this.mSecDeviceControlsController;
                    secDeviceControlsControllerImpl2.callback = null;
                    secDeviceControlsControllerImpl2.controlsComponent.controlsListingController.ifPresent(new SecDeviceControlsControllerImpl$sam$java_util_function_Consumer$0(new SecDeviceControlsControllerImpl$$ExternalSyntheticLambda0(secDeviceControlsControllerImpl2, 1)));
                }
            } else {
                controlsComponent.controlsListingController.ifPresent(new SecDeviceControlsControllerImpl$sam$java_util_function_Consumer$0(new SecDeviceControlsControllerImpl$$ExternalSyntheticLambda0(secDeviceControlsControllerImpl, 0)));
            }
        }
        this.mMediaOutputHelper = mediaOutputHelper;
        qSBackupRestoreManager.addCallback("MediaDevices", new QSBackupRestoreManager.Callback() { // from class: com.android.systemui.qs.bar.MediaDevicesBar.2
            @Override // com.android.systemui.qs.QSBackupRestoreManager.Callback
            public final boolean isValidDB() {
                boolean z2 = true;
                int value = MediaDevicesBar.this.mTunerService.getValue(1, "qspanel_media_quickcontrol_bar_available");
                if (value != 0 && value != 1) {
                    z2 = false;
                }
                KeyguardSecSecurityContainerController$$ExternalSyntheticOutline0.m("showMediaDevices : ", value, " valid : ", z2, "MediaDevices");
                return z2;
            }

            @Override // com.android.systemui.qs.QSBackupRestoreManager.Callback
            public final String onBackup(boolean z2) {
                StringBuilder sb = new StringBuilder("TAG::show_media_divices::");
                MediaDevicesBar mediaDevicesBar = MediaDevicesBar.this;
                if (z2) {
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
                String[] strArrSplit = str.split("::");
                if (strArrSplit[0].equals("show_media_divices")) {
                    ?? r2 = Integer.parseInt(strArrSplit[1]) == 1 ? 1 : 0;
                    StringBuilder sbM = RowView$$ExternalSyntheticOutline0.m("showMediaDevices : ", "   Integer.parseInt(sp[1]) : ", r2);
                    sbM.append(Integer.parseInt(strArrSplit[1]));
                    Log.d("MediaDevices", sbM.toString());
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
        this.mQSBackupRestoreManager.removeCallback("MediaDevices");
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final int getBarHeight() {
        int iDp;
        if (!this.mShowing) {
            return 0;
        }
        boolean z = this.mIsAllowedOnTop;
        SecQSPanelResourcePicker secQSPanelResourcePicker = this.mResourcePicker;
        if (z && this.mBrightnessBarOnTop && shouldShowOneLineWithBrightnessBar()) {
            iDp = secQSPanelResourcePicker.getBrightnessBarHeight(this.mContext);
        } else {
            Context context = this.mContext;
            secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getClass();
            SecQSPanelResourceCommon.Companion.getClass();
            iDp = SecQSPanelResourceCommon.Companion.dp(R.dimen.qspanel_media_quickcontrol_height, context);
        }
        return secQSPanelResourcePicker.getQuickQSCommonBottomMargin(this.mContext) + iDp;
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final int getBarLayout() {
        return R.layout.qspanel_media_devices_bar_layout;
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void inflateViews(ViewGroup viewGroup) {
        View viewInflate = LayoutInflater.from(this.mContext).inflate(R.layout.qspanel_media_devices_bar_layout, viewGroup, false);
        this.mBarRootView = viewInflate;
        this.mMediaTouchArea = (LinearLayout) viewInflate.findViewById(R.id.media_touch_area);
        this.mDeviceTouchArea = (LinearLayout) this.mBarRootView.findViewById(R.id.device_touch_area);
        this.mMediaTitleText = (TextView) this.mBarRootView.findViewById(R.id.media_title);
        this.mDevicesTitleText = (TextView) this.mBarRootView.findViewById(R.id.quickcontrol_title);
        final int i = 0;
        this.mMediaTitleText.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.qs.bar.MediaDevicesBar$$ExternalSyntheticLambda1
            public final /* synthetic */ MediaDevicesBar f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws Resources.NotFoundException {
                int i2 = i;
                MediaDevicesBar mediaDevicesBar = this.f$0;
                switch (i2) {
                    case 0:
                        Uri uri = MediaDevicesBar.EMERGENCY_MODE_URI;
                        mediaDevicesBar.getClass();
                        SystemUIAnalytics.sendRunstoneEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_MEDIA_QUICKCONTROL_BAR_MEDIA, SystemUIAnalytics.RUNESTONE_LABEL_QP_LAYOUT);
                        Log.d(mediaDevicesBar.TAG, "startMediaActivity()");
                        mediaDevicesBar.mMediaOutputHelper.showDetail();
                        break;
                    default:
                        Uri uri2 = MediaDevicesBar.EMERGENCY_MODE_URI;
                        mediaDevicesBar.getClass();
                        SystemUIAnalytics.sendRunstoneEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_MEDIA_QUICKCONTROL_BAR_DEVICES, SystemUIAnalytics.RUNESTONE_LABEL_QP_LAYOUT);
                        ((SecDeviceControlsControllerImpl) mediaDevicesBar.mSecDeviceControlsController).start(null);
                        break;
                }
            }
        });
        final int i2 = 1;
        this.mDevicesTitleText.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.qs.bar.MediaDevicesBar$$ExternalSyntheticLambda1
            public final /* synthetic */ MediaDevicesBar f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws Resources.NotFoundException {
                int i22 = i2;
                MediaDevicesBar mediaDevicesBar = this.f$0;
                switch (i22) {
                    case 0:
                        Uri uri = MediaDevicesBar.EMERGENCY_MODE_URI;
                        mediaDevicesBar.getClass();
                        SystemUIAnalytics.sendRunstoneEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_MEDIA_QUICKCONTROL_BAR_MEDIA, SystemUIAnalytics.RUNESTONE_LABEL_QP_LAYOUT);
                        Log.d(mediaDevicesBar.TAG, "startMediaActivity()");
                        mediaDevicesBar.mMediaOutputHelper.showDetail();
                        break;
                    default:
                        Uri uri2 = MediaDevicesBar.EMERGENCY_MODE_URI;
                        mediaDevicesBar.getClass();
                        SystemUIAnalytics.sendRunstoneEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_MEDIA_QUICKCONTROL_BAR_DEVICES, SystemUIAnalytics.RUNESTONE_LABEL_QP_LAYOUT);
                        ((SecDeviceControlsControllerImpl) mediaDevicesBar.mSecDeviceControlsController).start(null);
                        break;
                }
            }
        });
        updateBarVisibility();
        updateHeightMargins();
        final int i3 = 0;
        this.mMediaTouchArea.setOnTouchListener(new View.OnTouchListener(this) { // from class: com.android.systemui.qs.bar.MediaDevicesBar$$ExternalSyntheticLambda3
            public final /* synthetic */ MediaDevicesBar f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                int i4 = i3;
                MediaDevicesBar mediaDevicesBar = this.f$0;
                switch (i4) {
                    case 0:
                        return mediaDevicesBar.mMediaTitleText.onTouchEvent(motionEvent);
                    default:
                        return mediaDevicesBar.mDevicesTitleText.onTouchEvent(motionEvent);
                }
            }
        });
        final int i4 = 1;
        this.mDeviceTouchArea.setOnTouchListener(new View.OnTouchListener(this) { // from class: com.android.systemui.qs.bar.MediaDevicesBar$$ExternalSyntheticLambda3
            public final /* synthetic */ MediaDevicesBar f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                int i42 = i4;
                MediaDevicesBar mediaDevicesBar = this.f$0;
                switch (i42) {
                    case 0:
                        return mediaDevicesBar.mMediaTitleText.onTouchEvent(motionEvent);
                    default:
                        return mediaDevicesBar.mDevicesTitleText.onTouchEvent(motionEvent);
                }
            }
        });
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
    public final void onTuningChanged(String str, String str2) throws Resources.NotFoundException {
        Log.d(this.TAG, AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("onTuningChanged(): key = ", str, ", newValue = ", str2));
        if (str2 == null) {
            return;
        }
        if ("qspanel_media_quickcontrol_bar_available".equals(str)) {
            boolean z = Integer.parseInt(str2) != 0;
            this.mIsAllowedOnPanel = z;
            this.mIsAllowedOnTop = z;
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
        updateResources$10();
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void setUnderneathQqs(boolean z) throws Resources.NotFoundException {
        this.mIsUnderneathQqs = z;
        updateHeightMargins();
    }

    public final boolean shouldShowOneLineWithBrightnessBar() {
        if (this.mContext.getResources().getConfiguration().orientation != 2) {
            return false;
        }
        SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor = this.mSecQsUiDisplayModeInteractor;
        return (secQsUiDisplayModeInteractor.isFoldWide() || secQsUiDisplayModeInteractor.isTablet()) ? false : true;
    }

    public final SeslRecoilDrawable updateBackground() {
        Context context = this.mContext;
        this.mResourcePicker.resourcePickHelper.getTargetPicker().getClass();
        SecQSPanelResourceCommon.Companion.getClass();
        int iDp = SecQSPanelResourceCommon.Companion.dp(R.dimen.qspanel_media_quickcontrol_height, context);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(this.mContext.getResources().getColor(this.mSecQsUiDisplayModeInteractor.isTablet() ? R.color.sec_media_devices_tablet_button_bg_fill_color : R.color.sec_media_devices_button_bg_fill_color));
        float f = iDp;
        gradientDrawable.setCornerRadius(f);
        GradientDrawable gradientDrawable2 = new GradientDrawable();
        gradientDrawable2.setCornerRadius(f);
        return new SeslRecoilDrawable(this.mContext.getColor(R.color.notification_panel_theme_ripple_color), new GradientDrawable[]{gradientDrawable}, gradientDrawable2);
    }

    public final void updateBarVisibility() {
        showBar(this.mIsAllowedOnPanel && !this.mSettingsHelper.isEmergencyMode() && (!this.mIsOnCollapsedState || this.mIsAllowedOnTop));
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void updateHeightMargins() throws Resources.NotFoundException {
        float f;
        int i;
        int dimensionPixelSize;
        if (this.mBarRootView == null) {
            return;
        }
        if (shouldShowOneLineWithBrightnessBar()) {
            f = 1.0f;
            i = 0;
        } else {
            f = 0.0f;
            i = -1;
        }
        Context context = this.mContext;
        SecQSPanelResourcePicker secQSPanelResourcePicker = this.mResourcePicker;
        secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getClass();
        SecQSPanelResourceCommon.Companion.getClass();
        int iDp = SecQSPanelResourceCommon.Companion.dp(R.dimen.qspanel_media_quickcontrol_height, context);
        int mediaDeviceBarTouchAreaBetweenPadding = secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getMediaDeviceBarTouchAreaBetweenPadding(this.mContext);
        if (this.mIsAllowedOnTop && this.mBrightnessBarOnTop && shouldShowOneLineWithBrightnessBar()) {
            iDp = this.mContext.getResources().getDimensionPixelSize(R.dimen.brightness_slider_height);
            mediaDeviceBarTouchAreaBetweenPadding = this.mContext.getResources().getDimensionPixelSize(R.dimen.media_device_bar_between_padding_with_brightness_bar);
            dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.bar_between_margin);
        } else {
            dimensionPixelSize = 0;
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(i, iDp, f);
        layoutParams.bottomMargin = secQSPanelResourcePicker.getQuickQSCommonBottomMargin(this.mContext);
        if (shouldShowOneLineWithBrightnessBar()) {
            layoutParams.setMarginStart(dimensionPixelSize);
        }
        this.mBarRootView.setLayoutParams(layoutParams);
        this.mDeviceTouchArea.setPaddingRelative(0, 0, mediaDeviceBarTouchAreaBetweenPadding, 0);
        this.mMediaTouchArea.setPaddingRelative(mediaDeviceBarTouchAreaBetweenPadding, 0, 0, 0);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(i, iDp, f);
        this.mDevicesTitleText.setLayoutParams(layoutParams2);
        this.mMediaTitleText.setLayoutParams(layoutParams2);
        updateResources$10();
    }

    public final void updateResources$10() throws Resources.NotFoundException {
        int color = this.mContext.getColor(R.color.sec_media_devices_buttons_text_color);
        TextView textView = this.mMediaTitleText;
        if (textView != null) {
            textView.setTextColor(color);
            this.mMediaTitleText.setBackground(updateBackground());
            FontSizeUtils.updateFontSize(this.mMediaTitleText, R.dimen.qspanel_media_quickcontrol_text_size, 1.0f, 1.3f);
        }
        TextView textView2 = this.mDevicesTitleText;
        if (textView2 != null) {
            textView2.setTextColor(color);
            this.mDevicesTitleText.setBackground(updateBackground());
            FontSizeUtils.updateFontSize(this.mDevicesTitleText, R.dimen.qspanel_media_quickcontrol_text_size, 1.0f, 1.3f);
        }
    }
}
