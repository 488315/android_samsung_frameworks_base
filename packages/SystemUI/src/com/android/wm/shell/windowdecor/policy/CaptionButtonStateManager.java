package com.android.wm.shell.windowdecor.policy;

import android.app.ActivityManager;
import android.app.UiModeManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.RippleDrawable;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.graphics.ColorUtils;
import com.android.systemui.R;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.windowdecor.CaptionGlobalState;
import com.android.wm.shell.windowdecor.widget.CaptionButton;
import com.samsung.android.knox.ex.peripheral.PeripheralConstants;
import com.samsung.android.multiwindow.MultiWindowCoreState;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;

/* loaded from: classes3.dex */
public class CaptionButtonStateManager {
    public CaptionButton mCloseButton;
    public final Context mContext;
    public final DisplayController mDisplayController;
    public CaptionButton mFreeformButton;
    public final boolean mIsExternalDisplayConnected;
    public boolean mIsNightMode;
    public final boolean mIsPopupView;
    public final boolean mIsSplitStashed;
    public CaptionButton mSplitButton;
    public ActivityManager.RunningTaskInfo mTaskInfo;
    public CaptionButton mToggleFreeformButton;
    public CaptionButton mToggleImmersiveButton;
    public boolean mIsTopDownSplit = true;
    public boolean mIsFreeformMaximized = false;
    public boolean mInFullImmersiveState = false;

    public CaptionButtonStateManager(ActivityManager.RunningTaskInfo runningTaskInfo, Context context, DisplayController displayController, boolean z, boolean z2) {
        this.mTaskInfo = runningTaskInfo;
        this.mContext = context;
        this.mDisplayController = displayController;
        this.mIsPopupView = z;
        DesktopStateImpl.Companion.getClass();
        this.mIsExternalDisplayConnected = DesktopStateImpl.desktopExternalDisplayId != -1;
        this.mIsNightMode = isNightMode();
        this.mIsSplitStashed = z2;
    }

    public final ColorStateList getButtonColor() {
        boolean z = CaptionGlobalState.COLOR_THEME_ENABLED;
        boolean z2 = this.mIsPopupView;
        if (z) {
            return getThemeColorStateList(z2);
        }
        if (z2) {
            return getButtonFillColor(this.mIsNightMode);
        }
        return this.mContext.getResources().getColorStateList(this.mIsNightMode ? R.color.mw_caption_button_color_dark : R.color.mw_caption_button_color_light, null);
    }

    public final ColorStateList getButtonFillColor(boolean z) {
        return this.mContext.getResources().getColorStateList(z ? R.color.mw_caption_button_icon_color_dark : R.color.mw_caption_button_icon_color_light, null);
    }

    public final RippleDrawable getRippleDrawable(Context context, View view) throws Resources.NotFoundException {
        RippleDrawable rippleDrawable = (RippleDrawable) context.getDrawable(R.drawable.mw_caption_button_ripple);
        if (rippleDrawable != null) {
            Resources resources = context.getResources();
            int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.mw_caption_button_ripple_inset);
            int dimensionPixelSize2 = this.mIsPopupView ? dimensionPixelSize : resources.getDimensionPixelSize(R.dimen.mw_caption_button_ripple_inset_vertical);
            rippleDrawable.setLayerInset(0, dimensionPixelSize, dimensionPixelSize2, dimensionPixelSize, dimensionPixelSize2);
            int paddingStart = view.getPaddingStart();
            int paddingEnd = view.getPaddingEnd();
            if (paddingStart > paddingEnd) {
                rippleDrawable.setLayerInsetStart(0, (paddingStart - paddingEnd) + dimensionPixelSize);
                return rippleDrawable;
            }
            if (paddingEnd > paddingStart) {
                rippleDrawable.setLayerInsetEnd(0, (paddingEnd - paddingStart) + dimensionPixelSize);
            }
        }
        return rippleDrawable;
    }

    public final ColorStateList getThemeColorStateList(boolean z) throws Resources.NotFoundException {
        Context contextCreateConfigurationContext;
        if (this.mIsNightMode != this.mContext.getResources().getConfiguration().isNightModeActive()) {
            Configuration configuration = new Configuration(this.mContext.getResources().getConfiguration());
            configuration.uiMode = ((this.mIsNightMode ? 32 : 16) & 48) | (configuration.uiMode & (-49));
            contextCreateConfigurationContext = this.mContext.createConfigurationContext(configuration);
        } else {
            contextCreateConfigurationContext = null;
        }
        ColorStateList colorStateList = (contextCreateConfigurationContext == null ? this.mContext.getResources() : contextCreateConfigurationContext.getResources()).getColorStateList(17171431, null);
        if (z) {
            return colorStateList;
        }
        int defaultColor = colorStateList.getDefaultColor();
        return new ColorStateList(new int[][]{new int[]{R.attr.state_task_focused}, new int[0]}, new int[]{defaultColor, ColorUtils.setAlphaComponent(defaultColor, 102)});
    }

    public final boolean isNightMode() {
        UiModeManager uiModeManager = (UiModeManager) this.mContext.getSystemService("uimode");
        ComponentName componentName = this.mTaskInfo.realActivity;
        if (componentName != null) {
            return this.mTaskInfo.configuration.isNightModeActive() || (uiModeManager.getPackageNightMode(componentName.getPackageName()) == 32);
        }
        return false;
    }

    public void setupCaptionButtonState(Context context, ViewGroup viewGroup, View.OnTouchListener onTouchListener, View.OnClickListener onClickListener) {
        this.mToggleFreeformButton = (CaptionButton) viewGroup.findViewById(R.id.toggle_freeform_window);
        this.mSplitButton = (CaptionButton) viewGroup.findViewById(R.id.split_window);
        this.mFreeformButton = (CaptionButton) viewGroup.findViewById(R.id.freeform_window);
        this.mToggleImmersiveButton = (CaptionButton) viewGroup.findViewById(R.id.toggle_immersive_window);
        CaptionButton captionButton = (CaptionButton) viewGroup.findViewById(R.id.close_window);
        this.mCloseButton = captionButton;
        if (captionButton != null) {
            captionButton.setImageDrawable(this.mContext.getDrawable(this.mIsNightMode ? R.drawable.mw_caption_button_close_dark : R.drawable.mw_caption_button_close_light));
        }
        ColorStateList buttonColor = getButtonColor();
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt instanceof CaptionButton) {
                CaptionButton captionButton2 = (CaptionButton) childAt;
                captionButton2.setOnTouchListener(onTouchListener);
                captionButton2.setOnClickListener(onClickListener);
                captionButton2.setBackground(getRippleDrawable(context, captionButton2));
                captionButton2.setImageTintList(buttonColor);
            }
        }
    }

    public final void setupSplitButtonImage(ActivityManager.RunningTaskInfo runningTaskInfo) {
        if (this.mSplitButton == null) {
            return;
        }
        int multiSplitFlags = MultiWindowManager.getInstance().getMultiSplitFlags();
        Configuration configuration = runningTaskInfo.configuration;
        boolean z = CoreRune.IS_TABLET_DEVICE && CoreRune.MW_MULTI_SPLIT;
        boolean z2 = ((configuration.windowConfiguration.getWindowingMode() == 1 && z) || (multiSplitFlags & PeripheralConstants.ErrorCode.ERROR_PLUGIN_CUSTOM_BASE) == 0 || (z && (multiSplitFlags & 1) == 0)) ? false : true;
        if (z2 != this.mIsTopDownSplit) {
            this.mIsTopDownSplit = z2;
            this.mSplitButton.setImageDrawable(this.mContext.getDrawable(z2 ? R.drawable.mw_caption_button_split_top_bottom : R.drawable.mw_caption_button_split_left_right));
        }
        boolean z3 = MultiWindowUtils.isSplitEnabled(MultiWindowManager.getInstance().getMultiSplitFlags()) && runningTaskInfo.resizeMode != 10 && runningTaskInfo.supportsMultiWindow && MultiWindowCoreState.MW_ENABLED && !this.mIsSplitStashed;
        if (this.mSplitButton.isEnabled() != z3) {
            this.mSplitButton.setEnabled(z3);
        }
    }
}
