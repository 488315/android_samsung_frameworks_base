package com.android.p038wm.shell.windowdecor;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.util.SparseArray;
import android.view.View;
import com.android.p038wm.shell.windowdecor.MultitaskingWindowDecorViewModel;
import com.android.systemui.R;
import com.samsung.android.knox.p045ex.peripheral.PeripheralConstants;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class WindowMenuPresenter {
    public float mAlpha;
    public final SparseArray mButtons = new SparseArray();
    public Context mContext;
    public final boolean mIsDexEnabled;
    public boolean mIsDisplayAdded;
    public final boolean mIsNewDexMode;
    public boolean mIsNightMode;
    public boolean mIsSplitTopDown;
    public final MultitaskingWindowDecorViewModel.CaptionTouchEventListener mListener;
    public final View mRootView;
    public WindowDecorSlider mSlider;
    public ActivityManager.RunningTaskInfo mTaskInfo;
    public final int mWindowingMode;

    public WindowMenuPresenter(Context context, ActivityManager.RunningTaskInfo runningTaskInfo, int i, MultitaskingWindowDecorViewModel.CaptionTouchEventListener captionTouchEventListener, View view, float f, boolean z) {
        this.mContext = context;
        this.mTaskInfo = runningTaskInfo;
        this.mListener = captionTouchEventListener;
        this.mWindowingMode = i;
        this.mRootView = view;
        this.mIsNightMode = MultiWindowUtils.isNightMode(runningTaskInfo);
        this.mIsDexEnabled = runningTaskInfo.configuration.isDexMode() || runningTaskInfo.configuration.isNewDexMode();
        boolean isNewDexMode = runningTaskInfo.configuration.isNewDexMode();
        this.mIsNewDexMode = isNewDexMode;
        this.mAlpha = f;
        this.mIsSplitTopDown = !isNewDexMode;
        this.mIsDisplayAdded = z;
    }

    public static boolean isButtonVisible(int i, int i2, boolean z, boolean z2) {
        if (i == R.id.maximize_window) {
            return i2 != 1;
        }
        if (i == R.id.opacity_window) {
            return CoreRune.MW_CAPTION_SHELL_SUPPORT_WINDOW_OPACITY;
        }
        if (i == R.id.rotate_window) {
            return CoreRune.MD_DEX_COMPAT_CAPTION_SHELL && z;
        }
        if (i == R.id.move_display_window) {
            return CoreRune.MW_CAPTION_SHELL_MOVE_DISPLAY && z2;
        }
        return true;
    }

    public final void changePinButtonIconBackground(boolean z) {
        WindowMenuItemView windowMenuItemView = (WindowMenuItemView) this.mRootView.findViewById(R.id.window_pin_window);
        if (windowMenuItemView != null) {
            windowMenuItemView.mShowIconBackground = z;
            windowMenuItemView.setContentDescription(z ? this.mContext.getString(R.string.sec_decor_button_text_window_unpin) : this.mContext.getString(R.string.sec_decor_button_text_window_pin));
        }
    }

    public ColorStateList getButtonTintColor() {
        if (!CaptionGlobalState.COLOR_THEME_ENABLED) {
            if (!(this instanceof WindowMenuCaptionPresenter)) {
                return this.mContext.getResources().getColorStateList(this.mIsNightMode ? R.color.sec_decor_icon_color_dark : R.color.sec_decor_icon_color_light, null);
            }
            return this.mContext.getResources().getColorStateList(this.mIsNightMode ? R.color.sec_decor_button_color_dark : R.color.sec_decor_button_color_light, null);
        }
        if (this.mIsNightMode != this.mContext.getResources().getConfiguration().isNightModeActive()) {
            Configuration configuration = new Configuration(this.mContext.getResources().getConfiguration());
            configuration.uiMode = ((this.mIsNightMode ? 32 : 16) & 48) | (configuration.uiMode & (-49));
            this.mContext = this.mContext.createConfigurationContext(configuration);
        }
        return this.mContext.getResources().getColorStateList(17171332, null);
    }

    public final void setDividerColor(WindowMenuDivider windowMenuDivider) {
        windowMenuDivider.setBackgroundTintList(this.mContext.getResources().getColorStateList(this.mIsNightMode ? R.color.sec_decor_icon_color_dark : R.color.sec_decor_icon_color_light, null));
        windowMenuDivider.setAlpha(this.mIsNightMode ? 0.12f : 0.2f);
    }

    public final void setSplitButtonDrawable(WindowMenuItemView windowMenuItemView, int i) {
        Configuration configuration = this.mTaskInfo.getConfiguration();
        boolean z = true;
        boolean z2 = false;
        boolean z3 = CoreRune.IS_TABLET_DEVICE && CoreRune.MW_MULTI_SPLIT;
        if ((configuration.windowConfiguration.getWindowingMode() != 1 || !z3) && (i & PeripheralConstants.ErrorCode.ERROR_PLUGIN_CUSTOM_BASE) != 0) {
            if (z3 && (i & 1) == 0) {
                z = false;
            }
            z2 = z;
        }
        if (this.mIsSplitTopDown != z2) {
            this.mIsSplitTopDown = z2;
            windowMenuItemView.setImageDrawable(this.mContext.getDrawable(z2 ? R.drawable.sec_decor_button_split_topbottom : R.drawable.sec_decor_button_split_leftright));
        }
    }

    public final void setupOpacitySlider() {
        View view = this.mRootView;
        WindowDecorSlider windowDecorSlider = (WindowDecorSlider) view.findViewById(R.id.slider);
        this.mSlider = windowDecorSlider;
        if (windowDecorSlider == null) {
            return;
        }
        View findViewById = view.findViewById(R.id.button_container);
        View findViewById2 = view.findViewById(R.id.slider_container);
        windowDecorSlider.getClass();
        if (findViewById != null && findViewById2 != null) {
            windowDecorSlider.mButtonContainer = findViewById;
            windowDecorSlider.mSliderContainer = findViewById2;
            windowDecorSlider.mAnimatable = true;
        }
        this.mSlider.setOnSeekBarChangeListener(this.mListener);
    }
}
