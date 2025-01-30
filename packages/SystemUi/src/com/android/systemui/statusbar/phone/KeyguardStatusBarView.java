package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Trace;
import android.util.AttributeSet;
import android.util.Pair;
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
import com.android.systemui.statusbar.phone.StatusBarIconController;
import com.android.systemui.statusbar.phone.knox.p024ui.viewmodel.KnoxStatusBarViewControl;
import com.android.systemui.statusbar.phone.userswitcher.StatusBarUserSwitcherContainer;
import com.android.systemui.statusbar.policy.NetspeedView;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class KeyguardStatusBarView extends RelativeLayout implements KnoxStatusBarViewControl {
    public boolean mBatteryCharging;
    public BatteryMeterView mBatteryView;
    public TextView mCarrierLabel;
    public final Rect mClipRect;
    public View mCutoutSpace;
    public final ArrayList mEmptyTintRect;
    public boolean mHiddenByDeX;
    public boolean mIsUserSwitcherEnabled;
    public KeyguardStatusBarWallpaperHelper mKeyguardStatusBarWallpaperHelper;
    public boolean mKeyguardUserAvatarEnabled;
    public boolean mKeyguardUserSwitcherEnabled;
    public ImageView mMultiUserAvatar;
    public String mMultiUserName;
    public NetspeedView mNetspeedView;
    public boolean mShowPercentAvailable;
    public ViewGroup mStatusIconArea;
    public View mSystemIconsContainer;
    public int mTopClipping;
    public int mUserCount;
    public StatusBarUserSwitcherContainer mUserSwitcherContainer;

    public KeyguardStatusBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mEmptyTintRect = new ArrayList();
        new Pair(0, 0);
        this.mClipRect = new Rect(0, 0, 0, 0);
        this.mHiddenByDeX = false;
        this.mUserCount = 0;
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
        this.mShowPercentAvailable = getContext().getResources().getBoolean(android.R.bool.config_batterySaverSupported);
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
        BatteryMeterView batteryMeterView = this.mBatteryView;
        SwitchableDoubleShadowTextView switchableDoubleShadowTextView = batteryMeterView.mBatteryPercentView;
        if (switchableDoubleShadowTextView != null) {
            batteryMeterView.removeView(switchableDoubleShadowTextView);
            batteryMeterView.mBatteryPercentView = null;
        }
        batteryMeterView.updateShowPercent();
        TextView textView = (TextView) this.mUserSwitcherContainer.findViewById(R.id.current_user_name);
        if (textView != null) {
            textView.setTextAppearance(themeAttr);
        }
    }

    public final void onThemeChanged(StatusBarIconController.TintedIconManager tintedIconManager) {
        BatteryMeterView batteryMeterView = this.mBatteryView;
        Context context = ((RelativeLayout) this).mContext;
        if (context == null) {
            batteryMeterView.getClass();
        } else {
            batteryMeterView.mDualToneHandler.setColorsFromContext(context);
        }
        updateIconsAndTextColors(tintedIconManager);
    }

    @Override // android.view.View
    public final void setVisibility(int i) {
        super.setVisibility((i == 8 || !this.mHiddenByDeX) ? i : 8);
        if (i == 0) {
            updateVisibilities();
            return;
        }
        this.mSystemIconsContainer.animate().cancel();
        this.mSystemIconsContainer.setTranslationX(0.0f);
        this.mMultiUserAvatar.animate().cancel();
        this.mMultiUserAvatar.setAlpha(1.0f);
    }

    public final void updateIconsAndTextColors(StatusBarIconController.TintedIconManager tintedIconManager) {
        NetspeedView netspeedView;
        int colorAttrDefaultColor = Utils.getColorAttrDefaultColor(R.attr.wallpaperTextColor, ((RelativeLayout) this).mContext, 0);
        int colorStateListDefaultColor = Utils.getColorStateListDefaultColor(((double) Color.luminance(colorAttrDefaultColor)) < 0.5d ? R.color.dark_mode_icon_color_single_tone : R.color.light_mode_icon_color_single_tone, ((RelativeLayout) this).mContext);
        float f = colorAttrDefaultColor == -1 ? 0.0f : 1.0f;
        KeyguardStatusBarWallpaperHelper keyguardStatusBarWallpaperHelper = this.mKeyguardStatusBarWallpaperHelper;
        if (keyguardStatusBarWallpaperHelper != null) {
            colorStateListDefaultColor = keyguardStatusBarWallpaperHelper.fontColorFromWallPaper;
            f = keyguardStatusBarWallpaperHelper.intensity;
        }
        this.mCarrierLabel.setTextColor(colorStateListDefaultColor);
        TextView textView = (TextView) this.mUserSwitcherContainer.findViewById(R.id.current_user_name);
        if (textView != null) {
            textView.setTextColor(Utils.getColorStateListDefaultColor(R.color.light_mode_icon_color_single_tone, ((RelativeLayout) this).mContext));
        }
        if (tintedIconManager != null) {
            tintedIconManager.setTint(colorStateListDefaultColor);
        }
        KeyguardStatusBarWallpaperHelper keyguardStatusBarWallpaperHelper2 = this.mKeyguardStatusBarWallpaperHelper;
        if (keyguardStatusBarWallpaperHelper2 != null) {
            this.mBatteryView.mIsGrayColor = keyguardStatusBarWallpaperHelper2.fontColorType == 2;
        }
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
        KeyguardStatusBarWallpaperHelper keyguardStatusBarWallpaperHelper3 = this.mKeyguardStatusBarWallpaperHelper;
        if (keyguardStatusBarWallpaperHelper3 != null) {
            netspeedView.mNeedGrayIcon = keyguardStatusBarWallpaperHelper3.fontColorType == 2;
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
            if (this.mIsUserSwitcherEnabled && BasicRune.STATUS_LAYOUT_MUM_ICON && this.mUserCount > 1) {
                this.mMultiUserAvatar.setVisibility(0);
            } else {
                this.mMultiUserAvatar.setVisibility(8);
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

    @Override // com.android.systemui.statusbar.phone.knox.p024ui.viewmodel.KnoxStatusBarViewControl
    public final void setHiddenByKnox(boolean z) {
    }

    @Override // com.android.systemui.statusbar.phone.knox.p024ui.viewmodel.KnoxStatusBarViewControl
    public final View getStatusBarView() {
        return this;
    }
}
