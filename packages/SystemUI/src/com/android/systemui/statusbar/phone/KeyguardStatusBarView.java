package com.android.systemui.statusbar.phone;

import android.R;
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
import com.android.systemui.Dependency;
import com.android.systemui.battery.BatteryMeterView;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.statusbar.core.NewStatusBarIcons;
import com.android.systemui.statusbar.phone.knox.ui.viewmodel.KnoxStatusBarViewControl;
import com.android.systemui.statusbar.phone.nio.KeyguardStatusBarNioLayoutModel;
import com.android.systemui.statusbar.phone.nio.KeyguardStatusBarNioLayoutRepository;
import com.android.systemui.statusbar.phone.ui.TintedIconManager;
import com.android.systemui.statusbar.phone.userswitcher.StatusBarUserSwitcherContainer;
import com.android.systemui.statusbar.policy.NetspeedView;
import java.util.ArrayList;
import java.util.Objects;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes3.dex */
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
    public boolean mIsUserSwitcherEnabled;
    public KeyguardStatusBarWallpaperHelper mKeyguardStatusBarWallpaperHelper;
    public boolean mKeyguardUserAvatarEnabled;
    public ImageView mMultiUserAvatar;
    public String mMultiUserName;
    public NetspeedView mNetspeedView;
    public WindowInsets mPreviousInsets;
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
        this.mDarkChange = StateFlowKt.MutableStateFlow(SysuiDarkIconDispatcher$DarkChange.EMPTY);
        this.mPreviousInsets = null;
        Insets.of(0, 0, 0, 0);
        this.mClipRect = new Rect(0, 0, 0, 0);
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
        getResources();
        this.mShowPercentAvailable = getContext().getResources().getBoolean(R.bool.config_bg_prompt_abusive_apps_to_bg_restricted);
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        loadDimens();
        ViewGroup viewGroup = this.mStatusIconArea;
        viewGroup.setPaddingRelative(viewGroup.getPaddingStart(), getResources().getDimensionPixelSize(com.android.systemui.R.dimen.status_bar_padding_top), this.mStatusIconArea.getPaddingEnd(), this.mStatusIconArea.getPaddingBottom());
    }

    @Override // android.view.View
    public final void onFinishInflate() throws Resources.NotFoundException {
        super.onFinishInflate();
        this.mSystemIconsContainer = findViewById(com.android.systemui.R.id.system_icons_container);
        this.mSystemIcons = findViewById(com.android.systemui.R.id.system_icons);
        this.mMultiUserAvatar = (ImageView) findViewById(com.android.systemui.R.id.multi_user_avatar);
        this.mCarrierLabel = (TextView) findViewById(com.android.systemui.R.id.keyguard_carrier_text);
        BatteryMeterView batteryMeterView = (BatteryMeterView) this.mSystemIconsContainer.findViewById(com.android.systemui.R.id.battery);
        this.mBatteryView = batteryMeterView;
        int i = NewStatusBarIcons.$r8$clinit;
        if (batteryMeterView != null) {
            batteryMeterView.setTag("KeyguardStatusBarView");
        }
        this.mCutoutSpace = findViewById(com.android.systemui.R.id.cutout_space_view);
        this.mStatusIconArea = (ViewGroup) findViewById(com.android.systemui.R.id.status_icon_area);
        if (BasicRune.STATUS_REAL_TIME_NETWORK_SPEED) {
            ViewGroup viewGroup = (ViewGroup) findViewById(com.android.systemui.R.id.system_icons);
            NetspeedView netspeedView = (NetspeedView) LayoutInflater.from(((RelativeLayout) this).mContext).inflate(com.android.systemui.R.layout.samsung_status_bar_network_speed_view, (ViewGroup) null);
            this.mNetspeedView = netspeedView;
            if (BasicRune.STATUS_LAYOUT_SIDELING_CUTOUT) {
                netspeedView.mInStatusBar = true;
            }
            viewGroup.addView(netspeedView, 0);
        }
        this.mUserSwitcherContainer = (StatusBarUserSwitcherContainer) findViewById(com.android.systemui.R.id.user_switcher_container);
        ((RelativeLayout) this).mContext.getResources().getBoolean(com.android.systemui.R.bool.config_enablePrivacyDot);
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

    public final void onOverlayChanged(float f) throws Resources.NotFoundException {
        this.mCarrierLabel.setTextAppearance(Utils.getThemeAttr(R.attr.textAppearanceSmall, ((RelativeLayout) this).mContext));
        this.mCarrierLabel.setTextAppearance(com.android.systemui.R.style.TextAppearance_Keyguard_Status_SamsungCarriers);
        this.mCarrierLabel.setTextSize(0, getResources().getDimensionPixelSize(com.android.systemui.R.dimen.status_bar_clock_size) * f);
        BatteryMeterView batteryMeterView = this.mBatteryView;
        if (batteryMeterView != null) {
            batteryMeterView.updateShowPercent();
        }
        TextView textView = (TextView) this.mUserSwitcherContainer.findViewById(com.android.systemui.R.id.current_user_name);
        if (textView != null) {
            textView.setTextAppearance(com.android.systemui.R.style.TextAppearance_StatusBar_UserChip);
        }
    }

    public final void onThemeChanged(TintedIconManager tintedIconManager) {
        Context context;
        BatteryMeterView batteryMeterView = this.mBatteryView;
        if (batteryMeterView != null && (context = ((RelativeLayout) this).mContext) != null) {
            batteryMeterView.mDualToneHandler.setColorsFromContext(context);
        }
        updateIconsAndTextColors(tintedIconManager);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0042  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setAlpha(float f) {
        KeyguardStatusBarViewExt keyguardStatusBarViewExt = this.mExt;
        if (Float.compare(keyguardStatusBarViewExt.alpha, f) != 0) {
            float f2 = keyguardStatusBarViewExt.alpha;
            keyguardStatusBarViewExt.alpha = f;
            KeyguardStatusBarNioLayoutRepository keyguardStatusBarNioLayoutRepository = (KeyguardStatusBarNioLayoutRepository) Dependency.sDependency.getDependencyInner(KeyguardStatusBarNioLayoutRepository.class);
            final float f3 = keyguardStatusBarViewExt.alpha;
            final KeyguardStatusBarNioLayoutModel keyguardStatusBarNioLayoutModel = keyguardStatusBarNioLayoutRepository.nioLayoutModel;
            keyguardStatusBarNioLayoutModel.updateValues(new Runnable() { // from class: com.android.systemui.statusbar.phone.nio.KeyguardStatusBarNioLayoutRepository$updateKeyguardStatusBarViewAlpha$1$1
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardStatusBarNioLayoutModel keyguardStatusBarNioLayoutModel2 = keyguardStatusBarNioLayoutModel;
                    float f4 = f3;
                    if (keyguardStatusBarNioLayoutModel2.keyguardStatusBarViewAlpha == f4) {
                        return;
                    }
                    keyguardStatusBarNioLayoutModel2.keyguardStatusBarViewAlpha = f4;
                    keyguardStatusBarNioLayoutModel2.isUpdatedModel = true;
                }
            });
            double d = f2;
            if (Double.compare(d, 0.0d) == 0 || Double.compare(d, 1.0d) == 0) {
                keyguardStatusBarViewExt.printStatusLog();
            } else {
                double d2 = f;
                if (Double.compare(d2, 0.0d) == 0 || Double.compare(d2, 1.0d) == 0) {
                }
            }
        }
        super.setAlpha(f);
    }

    @Override // android.view.View
    public final void setVisibility(int i) throws Resources.NotFoundException {
        KeyguardStatusBarViewExt keyguardStatusBarViewExt = this.mExt;
        if (keyguardStatusBarViewExt.visibility != i) {
            keyguardStatusBarViewExt.visibility = i;
            KeyguardStatusBarNioLayoutRepository keyguardStatusBarNioLayoutRepository = (KeyguardStatusBarNioLayoutRepository) Dependency.sDependency.getDependencyInner(KeyguardStatusBarNioLayoutRepository.class);
            final int i2 = keyguardStatusBarViewExt.visibility;
            final KeyguardStatusBarNioLayoutModel keyguardStatusBarNioLayoutModel = keyguardStatusBarNioLayoutRepository.nioLayoutModel;
            keyguardStatusBarNioLayoutModel.updateValues(new Runnable() { // from class: com.android.systemui.statusbar.phone.nio.KeyguardStatusBarNioLayoutRepository$updateKeyguardStatusBarViewVisibility$1$1
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardStatusBarNioLayoutModel keyguardStatusBarNioLayoutModel2 = keyguardStatusBarNioLayoutModel;
                    int i3 = i2;
                    if (keyguardStatusBarNioLayoutModel2.keyguardStatusBarViewVisibility != i3) {
                        keyguardStatusBarNioLayoutModel2.keyguardStatusBarViewVisibility = i3;
                        keyguardStatusBarNioLayoutModel2.isUpdatedModel = true;
                    }
                }
            });
            keyguardStatusBarViewExt.printStatusLog();
        }
        super.setVisibility(i);
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
        int colorAttrDefaultColor = Utils.getColorAttrDefaultColor(((RelativeLayout) this).mContext, com.android.systemui.R.attr.wallpaperTextColor, 0);
        double dLuminance = Color.luminance(colorAttrDefaultColor);
        int colorStateListDefaultColor = Utils.getColorStateListDefaultColor(dLuminance < 0.5d ? com.android.systemui.R.color.dark_mode_icon_color_single_tone : com.android.systemui.R.color.light_mode_icon_color_single_tone, ((RelativeLayout) this).mContext);
        int i = dLuminance < 0.5d ? -301989889 : -16777216;
        float f = colorAttrDefaultColor == -1 ? 0.0f : 1.0f;
        KeyguardStatusBarWallpaperHelper keyguardStatusBarWallpaperHelper = this.mKeyguardStatusBarWallpaperHelper;
        if (keyguardStatusBarWallpaperHelper != null) {
            colorStateListDefaultColor = keyguardStatusBarWallpaperHelper.fontColorFromWallPaper;
            int i2 = ((double) Color.luminance(colorStateListDefaultColor)) < 0.5d ? -301989889 : -16777216;
            f = this.mKeyguardStatusBarWallpaperHelper.intensity;
            i = i2;
        }
        this.mCarrierLabel.setTextColor(colorStateListDefaultColor);
        TextView textView = (TextView) this.mUserSwitcherContainer.findViewById(com.android.systemui.R.id.current_user_name);
        if (textView != null) {
            textView.setTextColor(Utils.getColorStateListDefaultColor(com.android.systemui.R.color.light_mode_icon_color_single_tone, ((RelativeLayout) this).mContext));
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
        this.mDarkChange.updateState(null, new SysuiDarkIconDispatcher$DarkChange(this.mEmptyTintRect, f, colorStateListDefaultColor));
        ArrayList<Rect> arrayList = this.mEmptyTintRect;
        KeyEvent.Callback callbackFindViewById = findViewById(com.android.systemui.R.id.battery);
        if (callbackFindViewById instanceof DarkIconDispatcher.DarkReceiver) {
            ((DarkIconDispatcher.DarkReceiver) callbackFindViewById).onDarkChanged(arrayList, f, colorStateListDefaultColor);
        }
        ArrayList<Rect> arrayList2 = this.mEmptyTintRect;
        KeyEvent.Callback callbackFindViewById2 = findViewById(com.android.systemui.R.id.clock);
        if (callbackFindViewById2 instanceof DarkIconDispatcher.DarkReceiver) {
            ((DarkIconDispatcher.DarkReceiver) callbackFindViewById2).onDarkChanged(arrayList2, f, colorStateListDefaultColor);
        }
        if (!BasicRune.STATUS_REAL_TIME_NETWORK_SPEED || (netspeedView = this.mNetspeedView) == null) {
            return;
        }
        netspeedView.onDarkChanged(this.mEmptyTintRect, f, colorStateListDefaultColor);
    }

    public final void updateVisibilities() throws Resources.NotFoundException {
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
        if (this.mMultiUserAvatar.getParent() != this.mStatusIconArea) {
            if (this.mMultiUserAvatar.getParent() != null) {
                getOverlay().remove(this.mMultiUserAvatar);
            }
            this.mStatusIconArea.addView(this.mMultiUserAvatar, 0);
        } else {
            this.mMultiUserAvatar.getParent();
        }
        if (!this.mIsUserSwitcherEnabled || !BasicRune.STATUS_LAYOUT_MUM_ICON || this.mUserCount <= 1 || this.isMultiUserAvatarHidden) {
            this.mMultiUserAvatar.setVisibility(8);
        } else {
            this.mMultiUserAvatar.setVisibility(0);
        }
        BatteryMeterView batteryMeterView = this.mBatteryView;
        if (batteryMeterView != null) {
            if (this.mBatteryCharging && this.mShowPercentAvailable) {
                i = 1;
            }
            batteryMeterView.setPercentShowMode(i);
        }
    }

    public final WindowInsets updateWindowInsets(WindowInsets windowInsets) {
        if (!Objects.equals(this.mPreviousInsets, windowInsets)) {
            this.mPreviousInsets = new WindowInsets(windowInsets);
        }
        return super.onApplyWindowInsets(windowInsets);
    }

    @Override // com.android.systemui.statusbar.phone.knox.ui.viewmodel.KnoxStatusBarViewControl
    public final void setHiddenByKnox(boolean z) {
    }

    @Override // com.android.systemui.statusbar.phone.knox.ui.viewmodel.KnoxStatusBarViewControl
    public final View getStatusBarView() {
        return this;
    }
}
