package com.android.wm.shell.windowdecor;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Space;
import android.window.DesktopModeFlags;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.android.systemui.R;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.desktopmode.DesktopModeUiEventLogger;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.windowdecor.additionalviewcontainer.AdditionalViewContainer;
import com.android.wm.shell.windowdecor.common.DecorThemeUtil;
import com.android.wm.shell.windowdecor.common.DesktopMenuPositionUtilityKt;
import com.android.wm.shell.windowdecor.common.DrawableInsets;
import com.android.wm.shell.windowdecor.common.WindowDecorTaskResourceLoader;
import com.android.wm.shell.windowdecor.extension.TaskInfoKt;
import com.android.wm.shell.windowdecor.policy.PopupButtonPolicy;
import com.android.wm.shell.windowdecor.viewholder.MultiTaskingHandleViewHolder;
import com.samsung.android.rune.CoreRune;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.lang.reflect.InvocationTargetException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;

/* loaded from: classes3.dex */
public final class HandleMenu {
    public static final Companion Companion = new Companion(null);
    public final CoroutineScope bgScope;
    public PopupButtonPolicy buttonPolicy;
    public final int captionHeight;
    public final int captionWidth;
    public final Context context;
    public final DesktopModeUiEventLogger desktopModeUiEventLogger;
    public final DisplayController displayController;
    public final Point globalMenuPosition;
    public final PointF handleMenuPosition;
    public HandleMenuView handleMenuView;
    public AdditionalViewContainer handleMenuViewContainer;
    public final boolean isBrowserApp;
    public final boolean isDesktopModeSupportedOnDisplay;
    public final int layoutResId;
    public StandaloneCoroutine loadAppInfoJob;
    public final CoroutineDispatcher mainDispatcher;
    public final int marginHandleMenuHeaderTop;
    public final int marginHandleMenuStart;
    public final int marginMenuStart;
    public final int marginMenuTop;
    public final int menuHeight;
    public final int menuWidth;
    public final Intent openInAppOrBrowserIntent;
    public final DesktopModeWindowDecoration parentDecor;
    public final boolean shouldShowChangeAspectRatioButton;
    public final boolean shouldShowDesktopModeButton;
    public final boolean shouldShowManageWindowsButton;
    public final boolean shouldShowNewWindowButton;
    public final boolean shouldShowRestartButton;
    public final boolean shouldShowRestartNotification;
    public final boolean shouldShowWindowingPill;
    public final SplitScreenController splitScreenController;
    public final ActivityManager.RunningTaskInfo taskInfo;
    public final WindowDecorTaskResourceLoader taskResourceLoader;
    public final WindowManagerWrapper windowManagerWrapper;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public HandleMenu(CoroutineDispatcher coroutineDispatcher, CoroutineScope coroutineScope, DesktopModeWindowDecoration desktopModeWindowDecoration, WindowManagerWrapper windowManagerWrapper, WindowDecorTaskResourceLoader windowDecorTaskResourceLoader, int i, SplitScreenController splitScreenController, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, Intent intent, DesktopModeUiEventLogger desktopModeUiEventLogger, int i2, int i3, int i4, int i5, DisplayController displayController, boolean z8, boolean z9) {
        this.mainDispatcher = coroutineDispatcher;
        this.bgScope = coroutineScope;
        this.parentDecor = desktopModeWindowDecoration;
        this.windowManagerWrapper = windowManagerWrapper;
        this.taskResourceLoader = windowDecorTaskResourceLoader;
        this.layoutResId = i;
        this.splitScreenController = splitScreenController;
        this.shouldShowWindowingPill = z;
        this.shouldShowNewWindowButton = z2;
        this.shouldShowManageWindowsButton = z3;
        this.shouldShowChangeAspectRatioButton = z4;
        this.shouldShowDesktopModeButton = z5;
        this.shouldShowRestartButton = z6;
        this.isBrowserApp = z7;
        this.openInAppOrBrowserIntent = intent;
        this.desktopModeUiEventLogger = desktopModeUiEventLogger;
        this.captionWidth = i2;
        this.captionHeight = i3;
        this.displayController = displayController;
        this.isDesktopModeSupportedOnDisplay = z8;
        this.shouldShowRestartNotification = z9;
        this.context = desktopModeWindowDecoration.mDecorWindowContext;
        this.taskInfo = desktopModeWindowDecoration.mTaskInfo;
        int iLoadDimensionPixelSize = loadDimensionPixelSize(R.dimen.desktop_mode_handle_menu_pill_spacing_margin);
        this.menuWidth = loadDimensionPixelSize(R.dimen.desktop_mode_handle_menu_width);
        int iLoadDimensionPixelSize2 = loadDimensionPixelSize(R.dimen.desktop_mode_handle_menu_height);
        int iLoadDimensionPixelSize3 = (z ? iLoadDimensionPixelSize2 : (iLoadDimensionPixelSize2 - loadDimensionPixelSize(R.dimen.desktop_mode_handle_menu_windowing_pill_height)) - iLoadDimensionPixelSize) - loadDimensionPixelSize(R.dimen.desktop_mode_handle_menu_screenshot_height);
        iLoadDimensionPixelSize3 = z2 ? iLoadDimensionPixelSize3 : iLoadDimensionPixelSize3 - loadDimensionPixelSize(R.dimen.desktop_mode_handle_menu_new_window_height);
        iLoadDimensionPixelSize3 = z3 ? iLoadDimensionPixelSize3 : iLoadDimensionPixelSize3 - loadDimensionPixelSize(R.dimen.desktop_mode_handle_menu_manage_windows_height);
        iLoadDimensionPixelSize3 = z4 ? iLoadDimensionPixelSize3 : iLoadDimensionPixelSize3 - loadDimensionPixelSize(R.dimen.desktop_mode_handle_menu_change_aspect_ratio_height);
        iLoadDimensionPixelSize3 = z6 ? iLoadDimensionPixelSize3 : iLoadDimensionPixelSize3 - loadDimensionPixelSize(R.dimen.desktop_mode_handle_menu_restart_button_height);
        if (!z2 && !z3 && !z4 && !z6) {
            iLoadDimensionPixelSize3 -= iLoadDimensionPixelSize;
        }
        this.menuHeight = intent == null ? (iLoadDimensionPixelSize3 - loadDimensionPixelSize(R.dimen.desktop_mode_handle_menu_open_in_browser_pill_height)) - iLoadDimensionPixelSize : iLoadDimensionPixelSize3;
        this.marginMenuTop = loadDimensionPixelSize(R.dimen.desktop_mode_handle_menu_margin_top);
        this.marginMenuStart = loadDimensionPixelSize(R.dimen.desktop_mode_handle_menu_margin_start);
        this.handleMenuPosition = new PointF();
        this.globalMenuPosition = new Point();
        this.marginHandleMenuHeaderTop = loadDimensionPixelSize(R.dimen.mw_desktop_popup_header_margin_top);
        this.marginHandleMenuStart = loadDimensionPixelSize(R.dimen.mw_desktop_popup_margin_horizontal);
        if (CoreRune.MW_CAPTION_POPUP) {
            return;
        }
        updateHandleMenuPillPositions(i4, i5);
    }

    public static boolean isRtl(Context context) {
        return MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m(context) == 1;
    }

    public static boolean pointInView(View view, float f, float f2) {
        return view != null && ((float) view.getLeft()) <= f && ((float) view.getRight()) >= f && ((float) view.getTop()) <= f2 && ((float) view.getBottom()) >= f2;
    }

    public final void checkMotionEvent(MotionEvent motionEvent) {
        if (!DesktopModeFlags.ENABLE_HANDLE_INPUT_FIX.isTrue() || this.taskInfo.isFreeform()) {
            PointF pointF = new PointF(motionEvent.getX() - this.handleMenuPosition.x, motionEvent.getY() - this.handleMenuPosition.y);
            HandleMenuView handleMenuView = this.handleMenuView;
            if (handleMenuView != null) {
                float f = pointF.x;
                float f2 = pointF.y;
                boolean z = false;
                HandleMenuImageButton handleMenuImageButton = handleMenuView.collapseMenuButton;
                boolean z2 = handleMenuImageButton != null && ((float) handleMenuImageButton.getLeft()) <= f && ((float) handleMenuImageButton.getRight()) >= f && ((float) handleMenuImageButton.getTop()) <= f2 && ((float) handleMenuImageButton.getBottom()) >= f2;
                int actionMasked = motionEvent.getActionMasked();
                handleMenuImageButton.setHovered(z2 && actionMasked != 1);
                if (z2 && actionMasked == 0) {
                    z = true;
                }
                handleMenuImageButton.setPressed(z);
                if (actionMasked == 1 && z2) {
                    handleMenuImageButton.performClick();
                }
            }
        }
    }

    public final void closeMenuPopupImmediately() {
        MultiTaskingHandleViewHolder multiTaskingHandleViewHolderAsMultiTaskingAppHandle;
        AdditionalViewContainer additionalViewContainer = this.handleMenuViewContainer;
        if (additionalViewContainer != null) {
            additionalViewContainer.releaseView();
        }
        this.handleMenuViewContainer = null;
        if (!CoreRune.MW_CAPTION_HANDLE || (multiTaskingHandleViewHolderAsMultiTaskingAppHandle = DesktopModeWindowDecoration.asMultiTaskingAppHandle(this.parentDecor.mWindowDecorViewHolder)) == null) {
            return;
        }
        multiTaskingHandleViewHolderAsMultiTaskingAppHandle.handleTouchEnabled = true;
    }

    public final int loadDimensionPixelSize(int i) {
        if (i == 0) {
            return 0;
        }
        return this.context.getResources().getDimensionPixelSize(i);
    }

    /* JADX WARN: Removed duplicated region for block: B:58:0x0122  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateHandleMenuPillPositions(int i, int i2) {
        int iWidth;
        int i3;
        int paddingBottom;
        int i4;
        int iWidth2;
        int i5;
        if (!CoreRune.MW_CAPTION_POPUP) {
            Rect bounds = this.taskInfo.getConfiguration().windowConfiguration.getBounds();
            this.globalMenuPosition.set(DesktopMenuPositionUtilityKt.calculateMenuPosition(this.splitScreenController, this.taskInfo, this.marginMenuStart, this.marginMenuTop, i, i2, this.captionWidth, this.menuWidth, isRtl(this.context)));
            int i6 = this.layoutResId;
            int i7 = this.marginMenuTop;
            int i8 = this.menuWidth;
            if (i6 == R.layout.desktop_mode_app_header) {
                boolean zIsRtl = isRtl(this.context);
                iWidth = this.marginMenuStart;
                if (zIsRtl) {
                    iWidth = (bounds.width() - i8) - iWidth;
                }
            } else {
                if (DesktopModeFlags.ENABLE_HANDLE_INPUT_FIX.isTrue()) {
                    Point point = this.globalMenuPosition;
                    iWidth = point.x;
                    i3 = point.y;
                    this.handleMenuPosition.set(iWidth, i3);
                    return;
                }
                iWidth = (bounds.width() / 2) - (i8 / 2);
            }
            i3 = i2 + i7;
            this.handleMenuPosition.set(iWidth, i3);
            return;
        }
        Rect bounds2 = this.taskInfo.getConfiguration().windowConfiguration.getBounds();
        PopupButtonPolicy popupButtonPolicy = this.buttonPolicy;
        if (popupButtonPolicy == null) {
            popupButtonPolicy = null;
        }
        int i9 = popupButtonPolicy.mPopupWidth;
        DesktopModeWindowDecoration desktopModeWindowDecoration = this.parentDecor;
        boolean zIsDecorCaptionState$1 = desktopModeWindowDecoration.isDecorCaptionState$1();
        int i10 = this.marginHandleMenuHeaderTop;
        if (zIsDecorCaptionState$1) {
            i4 = i10;
        } else {
            if (this.taskInfo.isFreeform()) {
                paddingBottom = loadDimensionPixelSize(R.dimen.mw_handle_menu_freeform_top_margin);
            } else {
                int iLoadDimensionPixelSize = loadDimensionPixelSize(R.dimen.mw_handle_menu_top_margin);
                MultiTaskingHandleViewHolder multiTaskingHandleViewHolderAsMultiTaskingAppHandle = DesktopModeWindowDecoration.asMultiTaskingAppHandle(desktopModeWindowDecoration.mWindowDecorViewHolder);
                paddingBottom = iLoadDimensionPixelSize - (multiTaskingHandleViewHolderAsMultiTaskingAppHandle != null ? multiTaskingHandleViewHolderAsMultiTaskingAppHandle.captionHandle.getPaddingBottom() : 0);
            }
            i4 = paddingBottom;
        }
        this.globalMenuPosition.set(DesktopMenuPositionUtilityKt.calculateMenuPosition(this.splitScreenController, this.taskInfo, this.marginHandleMenuStart, i4, i, i2, this.captionWidth, i9, isRtl(this.context)));
        boolean zIsDecorCaptionState$12 = desktopModeWindowDecoration.isDecorCaptionState$1();
        int i11 = this.captionHeight;
        if (zIsDecorCaptionState$12) {
            if (desktopModeWindowDecoration.mInDesktopWindowing) {
                boolean zIsRtl2 = isRtl(this.context);
                iWidth = this.marginHandleMenuStart;
                if (zIsRtl2) {
                    iWidth = (bounds2.width() - i9) - iWidth;
                }
            } else {
                int iLoadDimensionPixelSize2 = loadDimensionPixelSize(R.dimen.mw_caption_button_width) * 3;
                if (isRtl(this.context)) {
                    iWidth = bounds2.width() < i9 + iLoadDimensionPixelSize2 ? bounds2.width() - i9 : iLoadDimensionPixelSize2;
                } else if (bounds2.width() >= i9 + iLoadDimensionPixelSize2) {
                    iWidth = (bounds2.width() - i9) - iLoadDimensionPixelSize2;
                }
            }
            i5 = i2 + i10 + i11;
        } else {
            boolean zIsFreeform = this.taskInfo.isFreeform();
            int i12 = this.captionWidth;
            if (zIsFreeform) {
                int iWidth3 = (bounds2.width() - i12) / 2;
                int iWidth4 = (bounds2.width() - i9) / 2;
                Rect rect = new Rect();
                DisplayLayout displayLayout = this.displayController.getDisplayLayout(this.taskInfo.displayId);
                if (displayLayout != null) {
                    rect.set(0, 0, displayLayout.mWidth, displayLayout.mHeight);
                }
                int i13 = bounds2.right - i9;
                if (i13 < 0) {
                    iWidth2 = bounds2.width() - i9;
                } else {
                    int i14 = bounds2.left;
                    int i15 = i14 + i9;
                    int i16 = rect.right;
                    if (i15 > i16) {
                        iWidth2 = 0;
                    } else {
                        int i17 = iWidth4 + i14;
                        int i18 = rect.left;
                        iWidth2 = i17 < i18 ? iWidth3 == i ? i18 - i14 : (i13 / 2) - i14 : i17 + i9 > i16 ? iWidth3 == i ? (i16 - i14) - i9 : ((i16 - i14) - i9) / 2 : ((i12 / 2) + i) - (i9 / 2);
                    }
                }
                i5 = i2 + i4 + i11 + (TaskInfoKt.isFullscreen(this.taskInfo) ? bounds2.top : 0);
                iWidth = iWidth2;
            }
        }
        AdditionalViewContainer additionalViewContainer = this.handleMenuViewContainer;
        View view = additionalViewContainer != null ? additionalViewContainer.getView() : null;
        if (view == null) {
            this.handleMenuPosition.set(iWidth, i5);
        } else {
            Rect rect2 = ((WindowManager.LayoutParams) view.getLayoutParams()).surfaceInsets;
            this.handleMenuPosition.set(iWidth - rect2.left, i5 - rect2.top);
        }
    }

    public final class HandleMenuView {
        public final HandleMenuAnimator animator;
        public final ImageView appIconView;
        public final View appInfoPill;
        public final MarqueedTextView appNameView;
        public final HandleMenuActionButton changeAspectRatioBtn;
        public final HandleMenuImageButton collapseMenuButton;
        public final Context context;
        public final DecorThemeUtil decorThemeUtil;
        public final ImageButton desktopBtn;
        public final Space desktopBtnSpace;
        public final DesktopModeUiEventLogger desktopModeUiEventLogger;
        public final ImageButton floatingBtn;
        public final Space floatingBtnSpace;
        public final ImageButton fullscreenBtn;
        public final int handleMenuCornerRadius;
        public final DrawableInsets iconButtonDrawableInsetsBase;
        public final DrawableInsets iconButtonDrawableInsetsLeft;
        public final DrawableInsets iconButtonDrawableInsetsRight;
        public final int iconButtonRippleRadius;
        public final boolean isBrowserApp;
        public final HandleMenuActionButton manageWindowBtn;
        public final View moreActionsPill;
        public final HandleMenuActionButton newWindowBtn;
        public DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 onChangeAspectRatioClickListener;
        public DesktopModeWindowDecoration$$ExternalSyntheticLambda6 onCloseMenuClickListener;
        public DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 onManageWindowsClickListener;
        public DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 onNewWindowClickListener;
        public DesktopModeWindowDecoration$$ExternalSyntheticLambda6 onOpenByDefaultClickListener;
        public HandleMenu$$ExternalSyntheticLambda2 onOpenInAppOrBrowserClickListener;
        public DesktopModeWindowDecoration$$ExternalSyntheticLambda6 onOutsideTouchListener;
        public DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 onRestartClickListener;
        public DesktopModeWindowDecoration$$ExternalSyntheticLambda6 onToDesktopClickListener;
        public DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 onToFloatClickListener;
        public DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 onToFullscreenClickListener;
        public DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 onToSplitScreenClickListener;
        public final ImageButton openByDefaultBtn;
        public final HandleMenuActionButton openInAppOrBrowserBtn;
        public final View openInAppOrBrowserPill;
        public final HandleMenuActionButton restartBtn;
        public final View rootView;
        public final HandleMenuActionButton screenshotBtn;
        public final boolean shouldShowBrowserPill;
        public final boolean shouldShowChangeAspectRatioButton;
        public final boolean shouldShowDesktopModeButton;
        public final boolean shouldShowManageWindowsButton;
        public final boolean shouldShowNewWindowButton;
        public final boolean shouldShowRestartButton;
        public final boolean shouldShowWindowingPill;
        public final ImageButton splitscreenBtn;
        public MenuStyle style;
        public ActivityManager.RunningTaskInfo taskInfo;
        public final View windowingPill;

        public final class MenuStyle {
            public final int backgroundColor;
            public final int textColor;
            public final ColorStateList windowingButtonColor;

            public MenuStyle(int i, int i2, ColorStateList colorStateList) {
                this.backgroundColor = i;
                this.textColor = i2;
                this.windowingButtonColor = colorStateList;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof MenuStyle)) {
                    return false;
                }
                MenuStyle menuStyle = (MenuStyle) obj;
                return this.backgroundColor == menuStyle.backgroundColor && this.textColor == menuStyle.textColor && Intrinsics.areEqual(this.windowingButtonColor, menuStyle.windowingButtonColor);
            }

            public final int hashCode() {
                return this.windowingButtonColor.hashCode() + ReorderTile$$ExternalSyntheticOutline0.m(this.textColor, Integer.hashCode(this.backgroundColor) * 31, 31);
            }

            public final String toString() {
                return "MenuStyle(backgroundColor=" + this.backgroundColor + ", textColor=" + this.textColor + ", windowingButtonColor=" + this.windowingButtonColor + ")";
            }
        }

        public HandleMenuView(Context context, DesktopModeUiEventLogger desktopModeUiEventLogger, int i, int i2, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8) throws Resources.NotFoundException {
            this.context = context;
            this.desktopModeUiEventLogger = desktopModeUiEventLogger;
            this.shouldShowWindowingPill = z;
            this.shouldShowBrowserPill = z2;
            this.shouldShowNewWindowButton = z3;
            this.shouldShowManageWindowsButton = z4;
            this.shouldShowChangeAspectRatioButton = z5;
            this.shouldShowDesktopModeButton = z6;
            this.shouldShowRestartButton = z7;
            this.isBrowserApp = z8;
            View viewInflate = LayoutInflater.from(context).inflate(R.layout.desktop_mode_window_decor_handle_menu, (ViewGroup) null);
            this.rootView = viewInflate;
            int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.desktop_mode_handle_menu_icon_button_ripple_inset_shift);
            int dimensionPixelSize2 = context.getResources().getDimensionPixelSize(R.dimen.desktop_mode_handle_menu_icon_button_ripple_inset_base);
            this.iconButtonRippleRadius = context.getResources().getDimensionPixelSize(R.dimen.desktop_mode_handle_menu_icon_button_ripple_radius);
            this.handleMenuCornerRadius = context.getResources().getDimensionPixelSize(R.dimen.desktop_mode_handle_menu_corner_radius);
            this.iconButtonDrawableInsetsBase = new DrawableInsets(dimensionPixelSize2, dimensionPixelSize2, dimensionPixelSize2, dimensionPixelSize2);
            this.iconButtonDrawableInsetsLeft = new DrawableInsets(dimensionPixelSize, dimensionPixelSize2, 0, dimensionPixelSize2);
            this.iconButtonDrawableInsetsRight = new DrawableInsets(0, dimensionPixelSize2, dimensionPixelSize, dimensionPixelSize2);
            View viewRequireViewById = viewInflate.requireViewById(R.id.app_info_pill);
            this.appInfoPill = viewRequireViewById;
            HandleMenuImageButton handleMenuImageButton = (HandleMenuImageButton) viewRequireViewById.requireViewById(R.id.collapse_menu_button);
            this.collapseMenuButton = handleMenuImageButton;
            this.appIconView = (ImageView) viewRequireViewById.requireViewById(R.id.application_icon);
            this.appNameView = (MarqueedTextView) viewRequireViewById.requireViewById(R.id.application_name);
            View viewRequireViewById2 = viewInflate.requireViewById(R.id.windowing_pill);
            this.windowingPill = viewRequireViewById2;
            ImageButton imageButton = (ImageButton) viewRequireViewById2.requireViewById(R.id.fullscreen_button);
            this.fullscreenBtn = imageButton;
            ImageButton imageButton2 = (ImageButton) viewRequireViewById2.requireViewById(R.id.split_screen_button);
            this.splitscreenBtn = imageButton2;
            ImageButton imageButton3 = (ImageButton) viewRequireViewById2.requireViewById(R.id.floating_button);
            this.floatingBtn = imageButton3;
            this.floatingBtnSpace = (Space) viewRequireViewById2.requireViewById(R.id.floating_button_space);
            ImageButton imageButton4 = (ImageButton) viewRequireViewById2.requireViewById(R.id.desktop_button);
            this.desktopBtn = imageButton4;
            this.desktopBtnSpace = (Space) viewRequireViewById2.requireViewById(R.id.desktop_button_space);
            View viewRequireViewById3 = viewInflate.requireViewById(R.id.more_actions_pill);
            this.moreActionsPill = viewRequireViewById3;
            this.screenshotBtn = (HandleMenuActionButton) viewRequireViewById3.requireViewById(R.id.screenshot_button);
            HandleMenuActionButton handleMenuActionButton = (HandleMenuActionButton) viewRequireViewById3.requireViewById(R.id.new_window_button);
            this.newWindowBtn = handleMenuActionButton;
            HandleMenuActionButton handleMenuActionButton2 = (HandleMenuActionButton) viewRequireViewById3.requireViewById(R.id.manage_windows_button);
            this.manageWindowBtn = handleMenuActionButton2;
            HandleMenuActionButton handleMenuActionButton3 = (HandleMenuActionButton) viewRequireViewById3.requireViewById(R.id.change_aspect_ratio_button);
            this.changeAspectRatioBtn = handleMenuActionButton3;
            HandleMenuActionButton handleMenuActionButton4 = (HandleMenuActionButton) viewRequireViewById3.requireViewById(R.id.handle_menu_restart_button);
            this.restartBtn = handleMenuActionButton4;
            View viewRequireViewById4 = viewInflate.requireViewById(R.id.open_in_app_or_browser_pill);
            this.openInAppOrBrowserPill = viewRequireViewById4;
            HandleMenuActionButton handleMenuActionButton5 = (HandleMenuActionButton) viewRequireViewById4.requireViewById(R.id.open_in_app_or_browser_button);
            this.openInAppOrBrowserBtn = handleMenuActionButton5;
            ImageButton imageButton5 = (ImageButton) viewRequireViewById4.requireViewById(R.id.open_by_default_button);
            this.openByDefaultBtn = imageButton5;
            this.decorThemeUtil = new DecorThemeUtil(context);
            this.animator = new HandleMenuAnimator(viewInflate, i, i2);
            imageButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.windowdecor.HandleMenu.HandleMenuView.1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws IllegalAccessException, Resources.NotFoundException, IllegalArgumentException, InvocationTargetException {
                    DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 desktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 = HandleMenuView.this.onToFullscreenClickListener;
                    if (desktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 != null) {
                        desktopModeWindowDecorViewModel$$ExternalSyntheticLambda9.invoke();
                    }
                }
            });
            imageButton2.setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.windowdecor.HandleMenu.HandleMenuView.2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws IllegalAccessException, Resources.NotFoundException, IllegalArgumentException, InvocationTargetException {
                    DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 desktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 = HandleMenuView.this.onToSplitScreenClickListener;
                    if (desktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 != null) {
                        desktopModeWindowDecorViewModel$$ExternalSyntheticLambda9.invoke();
                    }
                }
            });
            imageButton4.setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.windowdecor.HandleMenu.HandleMenuView.3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    DesktopModeWindowDecoration$$ExternalSyntheticLambda6 desktopModeWindowDecoration$$ExternalSyntheticLambda6 = HandleMenuView.this.onToDesktopClickListener;
                    if (desktopModeWindowDecoration$$ExternalSyntheticLambda6 != null) {
                        desktopModeWindowDecoration$$ExternalSyntheticLambda6.invoke();
                    }
                }
            });
            handleMenuActionButton5.setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.windowdecor.HandleMenu.HandleMenuView.4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    HandleMenu$$ExternalSyntheticLambda2 handleMenu$$ExternalSyntheticLambda2 = HandleMenuView.this.onOpenInAppOrBrowserClickListener;
                    if (handleMenu$$ExternalSyntheticLambda2 != null) {
                        handleMenu$$ExternalSyntheticLambda2.invoke();
                    }
                }
            });
            imageButton3.setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.windowdecor.HandleMenu.HandleMenuView.5
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws IllegalAccessException, Resources.NotFoundException, IllegalArgumentException, InvocationTargetException {
                    DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 desktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 = HandleMenuView.this.onToFloatClickListener;
                    if (desktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 != null) {
                        desktopModeWindowDecorViewModel$$ExternalSyntheticLambda9.invoke();
                    }
                }
            });
            imageButton5.setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.windowdecor.HandleMenu.HandleMenuView.6
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    DesktopModeWindowDecoration$$ExternalSyntheticLambda6 desktopModeWindowDecoration$$ExternalSyntheticLambda6 = HandleMenuView.this.onOpenByDefaultClickListener;
                    if (desktopModeWindowDecoration$$ExternalSyntheticLambda6 != null) {
                        desktopModeWindowDecoration$$ExternalSyntheticLambda6.invoke();
                    }
                }
            });
            handleMenuImageButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.windowdecor.HandleMenu.HandleMenuView.7
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    DesktopModeWindowDecoration$$ExternalSyntheticLambda6 desktopModeWindowDecoration$$ExternalSyntheticLambda6 = HandleMenuView.this.onCloseMenuClickListener;
                    if (desktopModeWindowDecoration$$ExternalSyntheticLambda6 != null) {
                        desktopModeWindowDecoration$$ExternalSyntheticLambda6.invoke();
                    }
                }
            });
            handleMenuActionButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.windowdecor.HandleMenu.HandleMenuView.8
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws IllegalAccessException, Resources.NotFoundException, IllegalArgumentException, InvocationTargetException {
                    DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 desktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 = HandleMenuView.this.onNewWindowClickListener;
                    if (desktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 != null) {
                        desktopModeWindowDecorViewModel$$ExternalSyntheticLambda9.invoke();
                    }
                }
            });
            handleMenuActionButton2.setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.windowdecor.HandleMenu.HandleMenuView.9
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws IllegalAccessException, Resources.NotFoundException, IllegalArgumentException, InvocationTargetException {
                    DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 desktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 = HandleMenuView.this.onManageWindowsClickListener;
                    if (desktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 != null) {
                        desktopModeWindowDecorViewModel$$ExternalSyntheticLambda9.invoke();
                    }
                }
            });
            handleMenuActionButton3.setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.windowdecor.HandleMenu.HandleMenuView.10
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws IllegalAccessException, Resources.NotFoundException, IllegalArgumentException, InvocationTargetException {
                    DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 desktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 = HandleMenuView.this.onChangeAspectRatioClickListener;
                    if (desktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 != null) {
                        desktopModeWindowDecorViewModel$$ExternalSyntheticLambda9.invoke();
                    }
                }
            });
            handleMenuActionButton4.setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.windowdecor.HandleMenu.HandleMenuView.11
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws IllegalAccessException, Resources.NotFoundException, IllegalArgumentException, InvocationTargetException {
                    DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 desktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 = HandleMenuView.this.onRestartClickListener;
                    if (desktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 != null) {
                        desktopModeWindowDecorViewModel$$ExternalSyntheticLambda9.invoke();
                    }
                }
            });
            viewInflate.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.wm.shell.windowdecor.HandleMenu.HandleMenuView.12
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getActionMasked() != 4) {
                        return true;
                    }
                    DesktopModeWindowDecoration$$ExternalSyntheticLambda6 desktopModeWindowDecoration$$ExternalSyntheticLambda6 = HandleMenuView.this.onOutsideTouchListener;
                    if (desktopModeWindowDecoration$$ExternalSyntheticLambda6 == null) {
                        return false;
                    }
                    desktopModeWindowDecoration$$ExternalSyntheticLambda6.invoke();
                    return false;
                }
            });
            imageButton4.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.wm.shell.windowdecor.HandleMenu.HandleMenuView.13
                @Override // android.view.View.AccessibilityDelegate
                public final boolean performAccessibilityAction(View view, int i3, Bundle bundle) {
                    if (i3 == AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK.getId()) {
                        HandleMenuView handleMenuView = HandleMenuView.this;
                        DesktopModeUiEventLogger desktopModeUiEventLogger2 = handleMenuView.desktopModeUiEventLogger;
                        ActivityManager.RunningTaskInfo runningTaskInfo = handleMenuView.taskInfo;
                        if (runningTaskInfo == null) {
                            runningTaskInfo = null;
                        }
                        desktopModeUiEventLogger2.log(runningTaskInfo, DesktopModeUiEventLogger.DesktopUiEventEnum.A11Y_APP_HANDLE_MENU_DESKTOP_VIEW);
                    }
                    return super.performAccessibilityAction(view, i3, bundle);
                }
            });
            imageButton.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.wm.shell.windowdecor.HandleMenu.HandleMenuView.14
                @Override // android.view.View.AccessibilityDelegate
                public final boolean performAccessibilityAction(View view, int i3, Bundle bundle) {
                    if (i3 == AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK.getId()) {
                        HandleMenuView handleMenuView = HandleMenuView.this;
                        DesktopModeUiEventLogger desktopModeUiEventLogger2 = handleMenuView.desktopModeUiEventLogger;
                        ActivityManager.RunningTaskInfo runningTaskInfo = handleMenuView.taskInfo;
                        if (runningTaskInfo == null) {
                            runningTaskInfo = null;
                        }
                        desktopModeUiEventLogger2.log(runningTaskInfo, DesktopModeUiEventLogger.DesktopUiEventEnum.A11Y_APP_HANDLE_MENU_FULLSCREEN);
                    }
                    return super.performAccessibilityAction(view, i3, bundle);
                }
            });
            imageButton2.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.wm.shell.windowdecor.HandleMenu.HandleMenuView.15
                @Override // android.view.View.AccessibilityDelegate
                public final boolean performAccessibilityAction(View view, int i3, Bundle bundle) {
                    if (i3 == AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK.getId()) {
                        HandleMenuView handleMenuView = HandleMenuView.this;
                        DesktopModeUiEventLogger desktopModeUiEventLogger2 = handleMenuView.desktopModeUiEventLogger;
                        ActivityManager.RunningTaskInfo runningTaskInfo = handleMenuView.taskInfo;
                        if (runningTaskInfo == null) {
                            runningTaskInfo = null;
                        }
                        desktopModeUiEventLogger2.log(runningTaskInfo, DesktopModeUiEventLogger.DesktopUiEventEnum.A11Y_APP_HANDLE_MENU_SPLIT_SCREEN);
                    }
                    return super.performAccessibilityAction(view, i3, bundle);
                }
            });
            AccessibilityNodeInfoCompat.AccessibilityActionCompat accessibilityActionCompat = AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK;
            ViewCompat.replaceAccessibilityAction(imageButton, accessibilityActionCompat, context.getString(R.string.app_handle_menu_accessibility_announce, context.getString(R.string.fullscreen_text)), null);
            ViewCompat.replaceAccessibilityAction(imageButton4, accessibilityActionCompat, context.getString(R.string.app_handle_menu_accessibility_announce, context.getString(R.string.desktop_text)), null);
            ViewCompat.replaceAccessibilityAction(imageButton2, accessibilityActionCompat, context.getString(R.string.app_handle_menu_accessibility_announce, context.getString(R.string.split_screen_text)), null);
        }

        public static /* synthetic */ void getAppIconView$annotations() {
        }

        public static /* synthetic */ void getAppNameView$annotations() {
        }
    }

    public static /* synthetic */ void getHandleMenuPosition$annotations() {
    }

    public static /* synthetic */ void getHandleMenuView$annotations() {
    }

    public static /* synthetic */ void getHandleMenuViewContainer$annotations() {
    }
}
