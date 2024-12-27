package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Insets;
import android.graphics.Rect;
import android.os.Trace;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowInsets;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.settingslib.Utils;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.battery.BatteryMeterView;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.statusbar.phone.SysuiDarkIconDispatcher;
import com.android.systemui.statusbar.phone.knox.ui.viewmodel.KnoxStatusBarViewControl;
import com.android.systemui.statusbar.phone.ui.TintedIconManager;
import com.android.systemui.statusbar.phone.userswitcher.StatusBarUserSwitcherContainer;
import com.android.systemui.statusbar.policy.NetspeedView;
import java.util.ArrayList;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class KeyguardStatusBarView extends RelativeLayout implements KnoxStatusBarViewControl {
    public boolean isMultiUserAvatarHidden;
    public boolean mBatteryCharging;
    public BatteryMeterView mBatteryView;
    public TextView mCarrierLabel;
    public final Rect mClipRect;
    public View mCutoutSpace;
    public final StateFlowImpl mDarkChange;
    public final ArrayList mEmptyTintRect;
    public final KeyguardStatusBarViewExt mExt;
    public boolean mHiddenByDeX;
    public boolean mIsUserSwitcherEnabled;
    public KeyguardStatusBarWallpaperHelper mKeyguardStatusBarWallpaperHelper;
    public boolean mKeyguardUserAvatarEnabled;
    public boolean mKeyguardUserSwitcherEnabled;
    public ImageView mMultiUserAvatar;
    public String mMultiUserName;
    public NetspeedView mNetspeedView;
    public int mNotifIconColor;
    public boolean mShowPercentAvailable;
    public ViewGroup mStatusIconArea;
    public View mSystemIcons;
    public View mSystemIconsContainer;
    public int mTopClipping;
    public int mUserCount;
    public StatusBarUserSwitcherContainer mUserSwitcherContainer;

    public KeyguardStatusBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mEmptyTintRect = new ArrayList();
        this.mDarkChange = StateFlowKt.MutableStateFlow(SysuiDarkIconDispatcher.DarkChange.EMPTY);
        Insets.of(0, 0, 0, 0);
        this.mClipRect = new Rect(0, 0, 0, 0);
        this.mHiddenByDeX = false;
        this.mUserCount = 0;
        this.isMultiUserAvatarHidden = false;
        this.mExt = new KeyguardStatusBarViewExt();
    }

    @Override // android.view.View
    public final boolean hasOverlappingRendering() {
        return false;
    }

    public boolean isKeyguardUserAvatarEnabled() {
        return this.mKeyguardUserAvatarEnabled;
    }

    public final void loadDimens() {
        Resources resources = getResources();
        resources.getDimensionPixelSize(R.dimen.system_icons_switcher_hidden_expanded_margin);
        resources.getDimensionPixelSize(R.dimen.status_bar_padding_end);
        resources.getDimensionPixelSize(R.dimen.ongoing_appops_dot_min_padding);
        getResources().getDimensionPixelSize(R.dimen.display_cutout_margin_consumption);
        this.mShowPercentAvailable = getContext().getResources().getBoolean(android.R.bool.config_bg_current_drain_event_duration_based_threshold_enabled);
        resources.getDimensionPixelSize(R.dimen.rounded_corner_content_padding);
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        loadDimens();
        ViewGroup viewGroup = this.mStatusIconArea;
        viewGroup.setPaddingRelative(viewGroup.getPaddingStart(), getResources().getDimensionPixelSize(R.dimen.status_bar_padding_top), this.mStatusIconArea.getPaddingEnd(), this.mStatusIconArea.getPaddingBottom());
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mSystemIconsContainer = findViewById(R.id.system_icons_container);
        this.mSystemIcons = findViewById(R.id.system_icons);
        this.mMultiUserAvatar = (ImageView) findViewById(R.id.multi_user_avatar);
        this.mCarrierLabel = (TextView) findViewById(R.id.keyguard_carrier_text);
        BatteryMeterView batteryMeterView = (BatteryMeterView) this.mSystemIconsContainer.findViewById(R.id.battery);
        this.mBatteryView = batteryMeterView;
        if (batteryMeterView != null) {
            batteryMeterView.setTag("KeyguardStatusBarView");
        }
        this.mCutoutSpace = findViewById(R.id.cutout_space_view);
        this.mStatusIconArea = (ViewGroup) findViewById(R.id.status_icon_area);
        if (BasicRune.STATUS_REAL_TIME_NETWORK_SPEED) {
            ViewGroup viewGroup = (ViewGroup) findViewById(R.id.system_icons);
            NetspeedView netspeedView = (NetspeedView) LayoutInflater.from(((RelativeLayout) this).mContext).inflate(R.layout.samsung_status_bar_network_speed_view, (ViewGroup) null);
            this.mNetspeedView = netspeedView;
            if (BasicRune.STATUS_LAYOUT_SIDELING_CUTOUT) {
                netspeedView.mInStatusBar = true;
            }
            viewGroup.addView(netspeedView, 0);
        }
        this.mUserSwitcherContainer = (StatusBarUserSwitcherContainer) findViewById(R.id.user_switcher_container);
        ((RelativeLayout) this).mContext.getResources().getBoolean(R.bool.config_enablePrivacyDot);
        loadDimens();
    }

    @Override // android.widget.RelativeLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mClipRect.set(0, this.mTopClipping, getWidth(), getHeight());
        setClipBounds(this.mClipRect);
    }

    @Override // android.widget.RelativeLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        Trace.beginSection("KeyguardStatusBarView#onMeasure");
        super.onMeasure(i, i2);
        Trace.endSection();
    }

    public final void onOverlayChanged() {
        int themeAttr = Utils.getThemeAttr(android.R.attr.textAppearanceSmall, ((RelativeLayout) this).mContext);
        this.mBatteryView.updateShowPercent();
        TextView textView = (TextView) this.mUserSwitcherContainer.findViewById(R.id.current_user_name);
        if (textView != null) {
            textView.setTextAppearance(themeAttr);
        }
    }

    public final void onThemeChanged(TintedIconManager tintedIconManager) {
        BatteryMeterView batteryMeterView = this.mBatteryView;
        Context context = ((RelativeLayout) this).mContext;
        if (context == null) {
            batteryMeterView.getClass();
        } else {
            batteryMeterView.mDualToneHandler.setColorsFromContext(context);
        }
        updateIconsAndTextColors(tintedIconManager);
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x002a, code lost:
    
        if (java.lang.Double.compare(r1, 1.0d) != 0) goto L13;
     */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setAlpha(float r8) {
        /*
            r7 = this;
            com.android.systemui.statusbar.phone.KeyguardStatusBarViewExt r0 = r7.mExt
            float r1 = r0.alpha
            int r1 = java.lang.Float.compare(r1, r8)
            if (r1 == 0) goto L2f
            float r1 = r0.alpha
            r0.alpha = r8
            double r1 = (double) r1
            r3 = 0
            int r5 = java.lang.Double.compare(r1, r3)
            if (r5 == 0) goto L2c
            r5 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            int r1 = java.lang.Double.compare(r1, r5)
            if (r1 == 0) goto L2c
            double r1 = (double) r8
            int r3 = java.lang.Double.compare(r1, r3)
            if (r3 == 0) goto L2c
            int r1 = java.lang.Double.compare(r1, r5)
            if (r1 != 0) goto L2f
        L2c:
            r0.printStatusLog()
        L2f:
            super.setAlpha(r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.KeyguardStatusBarView.setAlpha(float):void");
    }

    @Override // android.view.View
    public final void setVisibility(int i) {
        int i2 = (i == 8 || !this.mHiddenByDeX) ? i : 8;
        KeyguardStatusBarViewExt keyguardStatusBarViewExt = this.mExt;
        if (keyguardStatusBarViewExt.visibility != i2) {
            keyguardStatusBarViewExt.visibility = i2;
            keyguardStatusBarViewExt.printStatusLog();
        }
        super.setVisibility(i2);
        if (i == 0) {
            updateVisibilities();
            return;
        }
        this.mSystemIconsContainer.animate().cancel();
        this.mSystemIconsContainer.setTranslationX(0.0f);
        this.mMultiUserAvatar.animate().cancel();
        this.mMultiUserAvatar.setAlpha(1.0f);
    }

    public final void updateIconsAndTextColors(TintedIconManager tintedIconManager) {
        NetspeedView netspeedView;
        int colorAttrDefaultColor = Utils.getColorAttrDefaultColor(((RelativeLayout) this).mContext, R.attr.wallpaperTextColor, 0);
        double luminance = Color.luminance(colorAttrDefaultColor);
        int colorStateListDefaultColor = Utils.getColorStateListDefaultColor(luminance < 0.5d ? R.color.dark_mode_icon_color_single_tone : R.color.light_mode_icon_color_single_tone, ((RelativeLayout) this).mContext);
        int i = luminance < 0.5d ? -301989889 : -16777216;
        float f = colorAttrDefaultColor == -1 ? 0.0f : 1.0f;
        KeyguardStatusBarWallpaperHelper keyguardStatusBarWallpaperHelper = this.mKeyguardStatusBarWallpaperHelper;
        if (keyguardStatusBarWallpaperHelper != null) {
            colorStateListDefaultColor = keyguardStatusBarWallpaperHelper.fontColorFromWallPaper;
            f = keyguardStatusBarWallpaperHelper.intensity;
        }
        this.mCarrierLabel.setTextColor(colorStateListDefaultColor);
        this.mNotifIconColor = colorStateListDefaultColor;
        TextView textView = (TextView) this.mUserSwitcherContainer.findViewById(R.id.current_user_name);
        if (textView != null) {
            textView.setTextColor(Utils.getColorStateListDefaultColor(R.color.light_mode_icon_color_single_tone, ((RelativeLayout) this).mContext));
        }
        if (tintedIconManager != null) {
            tintedIconManager.setTint(colorStateListDefaultColor, i);
        }
        KeyguardStatusBarWallpaperHelper keyguardStatusBarWallpaperHelper2 = this.mKeyguardStatusBarWallpaperHelper;
        if (keyguardStatusBarWallpaperHelper2 != null) {
            BatteryMeterView batteryMeterView = this.mBatteryView;
            boolean z = keyguardStatusBarWallpaperHelper2.fontColorType == 2;
            batteryMeterView.mIsGrayColor = z;
            batteryMeterView.mSamsungDrawable.shouldShowGrayIcon = z;
        }
        this.mDarkChange.updateState(null, new SysuiDarkIconDispatcher.DarkChange(this.mEmptyTintRect, f, colorStateListDefaultColor));
        ArrayList<Rect> arrayList = this.mEmptyTintRect;
        KeyEvent.Callback findViewById = findViewById(R.id.battery);
        if (findViewById instanceof DarkIconDispatcher.DarkReceiver) {
            ((DarkIconDispatcher.DarkReceiver) findViewById).onDarkChanged(arrayList, f, colorStateListDefaultColor);
        }
        ArrayList<Rect> arrayList2 = this.mEmptyTintRect;
        KeyEvent.Callback findViewById2 = findViewById(R.id.clock);
        if (findViewById2 instanceof DarkIconDispatcher.DarkReceiver) {
            ((DarkIconDispatcher.DarkReceiver) findViewById2).onDarkChanged(arrayList2, f, colorStateListDefaultColor);
        }
        if (!BasicRune.STATUS_REAL_TIME_NETWORK_SPEED || (netspeedView = this.mNetspeedView) == null) {
            return;
        }
        netspeedView.onDarkChanged(this.mEmptyTintRect, f, colorStateListDefaultColor);
    }

    public final void updateVisibilities() {
        if (!this.mKeyguardUserAvatarEnabled) {
            ViewParent parent = this.mMultiUserAvatar.getParent();
            ViewGroup viewGroup = this.mStatusIconArea;
            if (parent == viewGroup) {
                viewGroup.removeView(this.mMultiUserAvatar);
                return;
            } else {
                if (this.mMultiUserAvatar.getParent() != null) {
                    getOverlay().remove(this.mMultiUserAvatar);
                    return;
                }
                return;
            }
        }
        int i = 0;
        if (this.mMultiUserAvatar.getParent() == this.mStatusIconArea || this.mKeyguardUserSwitcherEnabled) {
            ViewParent parent2 = this.mMultiUserAvatar.getParent();
            ViewGroup viewGroup2 = this.mStatusIconArea;
            if (parent2 == viewGroup2 && this.mKeyguardUserSwitcherEnabled) {
                viewGroup2.removeView(this.mMultiUserAvatar);
            }
        } else {
            if (this.mMultiUserAvatar.getParent() != null) {
                getOverlay().remove(this.mMultiUserAvatar);
            }
            this.mStatusIconArea.addView(this.mMultiUserAvatar, 0);
        }
        if (!this.mKeyguardUserSwitcherEnabled) {
            if (!this.mIsUserSwitcherEnabled || !BasicRune.STATUS_LAYOUT_MUM_ICON || this.mUserCount <= 1 || this.isMultiUserAvatarHidden) {
                this.mMultiUserAvatar.setVisibility(8);
            } else {
                this.mMultiUserAvatar.setVisibility(0);
            }
        }
        BatteryMeterView batteryMeterView = this.mBatteryView;
        if (this.mBatteryCharging && this.mShowPercentAvailable) {
            i = 1;
        }
        batteryMeterView.setPercentShowMode(i);
    }

    public final WindowInsets updateWindowInsets(WindowInsets windowInsets) {
        return super.onApplyWindowInsets(windowInsets);
    }

    @Override // com.android.systemui.statusbar.phone.knox.ui.viewmodel.KnoxStatusBarViewControl
    public final View getStatusBarView() {
        return this;
    }

    @Override // com.android.systemui.statusbar.phone.knox.ui.viewmodel.KnoxStatusBarViewControl
    public final void setHiddenByKnox(boolean z) {
    }
}
