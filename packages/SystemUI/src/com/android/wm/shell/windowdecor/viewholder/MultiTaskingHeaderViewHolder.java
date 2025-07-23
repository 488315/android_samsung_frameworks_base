package com.android.wm.shell.windowdecor.viewholder;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.window.DesktopModeFlags;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.material3.ColorScheme;
import androidx.compose.material3.DynamicTonalPaletteKt;
import androidx.compose.ui.graphics.ColorKt;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import com.android.systemui.R;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.desktopmode.DesktopModeUiEventLogger;
import com.android.wm.shell.desktopmode.DesktopModeUtils;
import com.android.wm.shell.windowdecor.DesktopImmersiveCaptionAnimator;
import com.android.wm.shell.windowdecor.MaximizeButtonView;
import com.android.wm.shell.windowdecor.common.ButtonBackgroundDrawableUtilsKt;
import com.android.wm.shell.windowdecor.common.DecorThemeUtil;
import com.android.wm.shell.windowdecor.common.DrawableInsets;
import com.android.wm.shell.windowdecor.common.Theme;
import com.android.wm.shell.windowdecor.extension.TaskInfoKt;
import com.android.wm.shell.windowdecor.policy.CaptionButtonPolicy;
import com.android.wm.shell.windowdecor.policy.CaptionButtonStateManager;
import com.android.wm.shell.windowdecor.viewholder.WindowDecorationViewHolder;
import com.android.wm.shell.windowdecor.widget.CaptionButton;
import com.samsung.android.rune.CoreRune;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.NoWhenBranchMatchedException;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class MultiTaskingHeaderViewHolder extends WindowDecorationViewHolder {
    public final String a11yAnnounceTextMaximize;
    public final String a11yAnnounceTextRestore;
    public String a11yTextMaximize;
    public String a11yTextRestore;
    public final DrawableInsets appChipDrawableInsets;
    public final ImageView appIconImageView;
    public final TextView appNameTextView;
    public final CaptionButtonPolicy captionButtonPolicy;
    public final View captionHandle;
    public final View captionView;
    public final DrawableInsets closeDrawableInsets;
    public final ImageButton closeWindowButton;
    public ActivityManager.RunningTaskInfo currentTaskInfo;
    public final ColorScheme darkColors;
    public final DecorThemeUtil decorThemeUtil;
    public final DesktopModeUiEventLogger desktopModeUiEventLogger;
    public final ImageButton expandMenuButton;
    public final int headerButtonsRippleRadius;
    public final ColorScheme lightColors;
    public final MaximizeButtonView maximizeButtonView;
    public final DrawableInsets maximizeDrawableInsets;
    public final ImageButton maximizeWindowButton;
    public final DrawableInsets minimizeDrawableInsets;
    public final ImageButton minimizeWindowButton;
    public final View.OnLongClickListener onLongClickListener;
    public final View openMenuButton;
    public SizeToggleDirection sizeToggleDirection;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Factory {
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Header {
        public final Theme appTheme;
        public final boolean isAppearanceCaptionLight;
        public final boolean isFocused;
        public final Type type;

        /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
        /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Type {
            public static final /* synthetic */ Type[] $VALUES;
            public static final Type CUSTOM;
            public static final Type DEFAULT;

            static {
                Type type = new Type("DEFAULT", 0);
                DEFAULT = type;
                Type type2 = new Type("CUSTOM", 1);
                CUSTOM = type2;
                Type[] typeArr = {type, type2};
                $VALUES = typeArr;
                EnumEntriesKt.enumEntries(typeArr);
            }

            private Type(String str, int i) {
            }

            public static Type valueOf(String str) {
                return (Type) Enum.valueOf(Type.class, str);
            }

            public static Type[] values() {
                return (Type[]) $VALUES.clone();
            }
        }

        public Header(Type type, Theme theme, boolean z, boolean z2) {
            this.type = type;
            this.appTheme = theme;
            this.isFocused = z;
            this.isAppearanceCaptionLight = z2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Header)) {
                return false;
            }
            Header header = (Header) obj;
            return this.type == header.type && this.appTheme == header.appTheme && this.isFocused == header.isFocused && this.isAppearanceCaptionLight == header.isAppearanceCaptionLight;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.isAppearanceCaptionLight) + TransitionData$$ExternalSyntheticOutline0.m((this.appTheme.hashCode() + (this.type.hashCode() * 31)) * 31, 31, this.isFocused);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("Header(type=");
            sb.append(this.type);
            sb.append(", appTheme=");
            sb.append(this.appTheme);
            sb.append(", isFocused=");
            sb.append(this.isFocused);
            sb.append(", isAppearanceCaptionLight=");
            return MoveResult$$ExternalSyntheticOutline0.m(sb, this.isAppearanceCaptionLight, ")");
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class HeaderData extends WindowDecorationViewHolder.Data {
        public final boolean enableMaximizeLongClick;
        public final boolean hasGlobalFocus;
        public final int immersiveAnimHeight;
        public final boolean inFullImmersiveState;
        public final boolean isCaptionVisible;
        public final boolean isKeyguardShowing;
        public final boolean isStatusBarVisible;
        public final boolean isTaskMaximized;
        public final ActivityManager.RunningTaskInfo taskInfo;

        public HeaderData(ActivityManager.RunningTaskInfo runningTaskInfo, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, int i) {
            this.taskInfo = runningTaskInfo;
            this.isTaskMaximized = z;
            this.inFullImmersiveState = z2;
            this.hasGlobalFocus = z3;
            this.enableMaximizeLongClick = z4;
            this.isCaptionVisible = z5;
            this.isKeyguardShowing = z6;
            this.isStatusBarVisible = z7;
            this.immersiveAnimHeight = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof HeaderData)) {
                return false;
            }
            HeaderData headerData = (HeaderData) obj;
            return Intrinsics.areEqual(this.taskInfo, headerData.taskInfo) && this.isTaskMaximized == headerData.isTaskMaximized && this.inFullImmersiveState == headerData.inFullImmersiveState && this.hasGlobalFocus == headerData.hasGlobalFocus && this.enableMaximizeLongClick == headerData.enableMaximizeLongClick && this.isCaptionVisible == headerData.isCaptionVisible && this.isKeyguardShowing == headerData.isKeyguardShowing && this.isStatusBarVisible == headerData.isStatusBarVisible && this.immersiveAnimHeight == headerData.immersiveAnimHeight;
        }

        public final int hashCode() {
            return Integer.hashCode(this.immersiveAnimHeight) + TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(this.taskInfo.hashCode() * 31, 31, this.isTaskMaximized), 31, this.inFullImmersiveState), 31, this.hasGlobalFocus), 31, this.enableMaximizeLongClick), 31, this.isCaptionVisible), 31, this.isKeyguardShowing), 31, this.isStatusBarVisible);
        }

        public final String toString() {
            ActivityManager.RunningTaskInfo runningTaskInfo = this.taskInfo;
            StringBuilder sb = new StringBuilder("HeaderData(taskInfo=");
            sb.append(runningTaskInfo);
            sb.append(", isTaskMaximized=");
            sb.append(this.isTaskMaximized);
            sb.append(", inFullImmersiveState=");
            sb.append(this.inFullImmersiveState);
            sb.append(", hasGlobalFocus=");
            sb.append(this.hasGlobalFocus);
            sb.append(", enableMaximizeLongClick=");
            sb.append(this.enableMaximizeLongClick);
            sb.append(", isCaptionVisible=");
            sb.append(this.isCaptionVisible);
            sb.append(", isKeyguardShowing=");
            sb.append(this.isKeyguardShowing);
            sb.append(", isStatusBarVisible=");
            sb.append(this.isStatusBarVisible);
            sb.append(", immersiveAnimHeight=");
            return ReorderTile$$ExternalSyntheticOutline0.m(this.immersiveAnimHeight, ")", sb);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class HeaderStyle {
        public final Background background;
        public final Foreground foreground;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public abstract class Background {

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            public final class Opaque extends Background {
                public final int color;

                public Opaque(int i) {
                    super(null);
                    this.color = i;
                }

                public final boolean equals(Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    return (obj instanceof Opaque) && this.color == ((Opaque) obj).color;
                }

                public final int hashCode() {
                    return Integer.hashCode(this.color);
                }

                public final String toString() {
                    return ReorderTile$$ExternalSyntheticOutline0.m(this.color, ")", new StringBuilder("Opaque(color="));
                }
            }

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            public final class Transparent extends Background {
                public static final Transparent INSTANCE = new Transparent();

                private Transparent() {
                    super(null);
                }

                public final boolean equals(Object obj) {
                    return this == obj || (obj instanceof Transparent);
                }

                public final int hashCode() {
                    return -305021586;
                }

                public final String toString() {
                    return "Transparent";
                }
            }

            public /* synthetic */ Background(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Background() {
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Foreground {
            public final int color;
            public final int opacity;

            public Foreground(int i, int i2) {
                this.color = i;
                this.opacity = i2;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof Foreground)) {
                    return false;
                }
                Foreground foreground = (Foreground) obj;
                return this.color == foreground.color && this.opacity == foreground.opacity;
            }

            public final int hashCode() {
                return Integer.hashCode(this.opacity) + (Integer.hashCode(this.color) * 31);
            }

            public final String toString() {
                StringBuilder sb = new StringBuilder("Foreground(color=");
                sb.append(this.color);
                sb.append(", opacity=");
                return ReorderTile$$ExternalSyntheticOutline0.m(this.opacity, ")", sb);
            }
        }

        public HeaderStyle(Background background, Foreground foreground) {
            this.background = background;
            this.foreground = foreground;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof HeaderStyle)) {
                return false;
            }
            HeaderStyle headerStyle = (HeaderStyle) obj;
            return Intrinsics.areEqual(this.background, headerStyle.background) && Intrinsics.areEqual(this.foreground, headerStyle.foreground);
        }

        public final int hashCode() {
            return this.foreground.hashCode() + (this.background.hashCode() * 31);
        }

        public final String toString() {
            return "HeaderStyle(background=" + this.background + ", foreground=" + this.foreground + ")";
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class SizeToggleDirection {
        public static final /* synthetic */ SizeToggleDirection[] $VALUES;
        public static final SizeToggleDirection MAXIMIZE;
        public static final SizeToggleDirection RESTORE;

        static {
            SizeToggleDirection sizeToggleDirection = new SizeToggleDirection("MAXIMIZE", 0);
            MAXIMIZE = sizeToggleDirection;
            SizeToggleDirection sizeToggleDirection2 = new SizeToggleDirection("RESTORE", 1);
            RESTORE = sizeToggleDirection2;
            SizeToggleDirection[] sizeToggleDirectionArr = {sizeToggleDirection, sizeToggleDirection2};
            $VALUES = sizeToggleDirectionArr;
            EnumEntriesKt.enumEntries(sizeToggleDirectionArr);
        }

        private SizeToggleDirection(String str, int i) {
        }

        public static SizeToggleDirection valueOf(String str) {
            return (SizeToggleDirection) Enum.valueOf(SizeToggleDirection.class, str);
        }

        public static SizeToggleDirection[] values() {
            return (SizeToggleDirection[]) $VALUES.clone();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;
        public static final /* synthetic */ int[] $EnumSwitchMapping$2;

        static {
            int[] iArr = new int[SizeToggleDirection.values().length];
            try {
                iArr[SizeToggleDirection.MAXIMIZE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[SizeToggleDirection.RESTORE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[Theme.values().length];
            try {
                iArr2[Theme.LIGHT.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr2[Theme.DARK.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$1 = iArr2;
            int[] iArr3 = new int[Header.Type.values().length];
            try {
                iArr3[Header.Type.DEFAULT.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr3[Header.Type.CUSTOM.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            $EnumSwitchMapping$2 = iArr3;
        }
    }

    static {
        new Companion(null);
    }

    public MultiTaskingHeaderViewHolder(View view, View.OnTouchListener onTouchListener, View.OnClickListener onClickListener, View.OnLongClickListener onLongClickListener, View.OnGenericMotionListener onGenericMotionListener, final Function0 function0, final Function0 function02, final Function0 function03, Function0 function04, DesktopModeUiEventLogger desktopModeUiEventLogger, ActivityManager.RunningTaskInfo runningTaskInfo, DisplayController displayController, Handler handler) {
        super(view);
        final MultiTaskingHeaderViewHolder multiTaskingHeaderViewHolder;
        AccessibilityViewCommand accessibilityViewCommand;
        this.onLongClickListener = onLongClickListener;
        this.desktopModeUiEventLogger = desktopModeUiEventLogger;
        this.decorThemeUtil = new DecorThemeUtil(this.context);
        this.lightColors = DynamicTonalPaletteKt.dynamicLightColorScheme(this.context);
        this.darkColors = DynamicTonalPaletteKt.dynamicDarkColorScheme(this.context);
        this.headerButtonsRippleRadius = this.context.getResources().getDimensionPixelSize(R.dimen.desktop_mode_header_buttons_ripple_radius);
        this.appChipDrawableInsets = new DrawableInsets(this.context.getResources().getDimensionPixelSize(R.dimen.desktop_mode_header_app_chip_ripple_inset_vertical), 0, 2, (DefaultConstructorMarker) null);
        this.minimizeDrawableInsets = new DrawableInsets(this.context.getResources().getDimensionPixelSize(R.dimen.desktop_mode_header_minimize_ripple_inset_vertical), this.context.getResources().getDimensionPixelSize(R.dimen.desktop_mode_header_minimize_ripple_inset_horizontal));
        this.maximizeDrawableInsets = new DrawableInsets(this.context.getResources().getDimensionPixelSize(R.dimen.desktop_mode_header_maximize_ripple_inset_vertical), this.context.getResources().getDimensionPixelSize(R.dimen.desktop_mode_header_maximize_ripple_inset_horizontal));
        this.closeDrawableInsets = new DrawableInsets(this.context.getResources().getDimensionPixelSize(R.dimen.desktop_mode_header_close_ripple_inset_vertical), this.context.getResources().getDimensionPixelSize(R.dimen.desktop_mode_header_close_ripple_inset_horizontal));
        View requireViewById = view.requireViewById(R.id.desktop_mode_caption);
        this.captionView = requireViewById;
        View requireViewById2 = view.requireViewById(R.id.caption_handle);
        this.captionHandle = requireViewById2;
        View findViewById = view.findViewById(R.id.open_menu_button);
        this.openMenuButton = findViewById;
        ImageButton imageButton = (ImageButton) view.requireViewById(R.id.close_window);
        this.closeWindowButton = imageButton;
        this.expandMenuButton = (ImageButton) view.findViewById(R.id.expand_menu_button);
        MaximizeButtonView maximizeButtonView = (MaximizeButtonView) view.findViewById(R.id.maximize_button_view);
        this.maximizeButtonView = maximizeButtonView;
        ImageButton imageButton2 = (ImageButton) view.findViewById(R.id.maximize_window);
        this.maximizeWindowButton = imageButton2;
        ImageButton imageButton3 = (ImageButton) view.findViewById(R.id.minimize_window);
        this.minimizeWindowButton = imageButton3;
        this.appNameTextView = (TextView) view.findViewById(R.id.application_name);
        this.appIconImageView = (ImageView) view.findViewById(R.id.application_icon);
        this.a11yAnnounceTextMaximize = this.context.getString(R.string.app_header_talkback_action_maximize_button_text);
        this.a11yAnnounceTextRestore = this.context.getString(R.string.app_header_talkback_action_restore_button_text);
        CaptionButtonPolicy captionButtonPolicy = new CaptionButtonPolicy(runningTaskInfo, this.context, displayController, handler);
        this.captionButtonPolicy = captionButtonPolicy;
        if (CoreRune.MW_CAPTION) {
            captionButtonPolicy.setupRootView(this.context, requireViewById, onTouchListener, onClickListener);
            CaptionButton captionButton = captionButtonPolicy.mToggleFreeformButton;
            if (captionButton != null) {
                captionButton.setOnLongClickListener(onLongClickListener);
            }
            if (findViewById != null) {
                findViewById.setTooltipText(this.context.getString(R.string.sec_decor_button_text_more_options));
                return;
            }
            return;
        }
        requireViewById.setOnTouchListener(onTouchListener);
        requireViewById2.setOnTouchListener(onTouchListener);
        if (findViewById != null) {
            findViewById.setOnClickListener(onClickListener);
        }
        if (findViewById != null) {
            findViewById.setOnTouchListener(onTouchListener);
        }
        imageButton.setOnClickListener(onClickListener);
        if (imageButton2 != null) {
            imageButton2.setOnClickListener(onClickListener);
        }
        if (imageButton2 != null) {
            imageButton2.setOnTouchListener(onTouchListener);
        }
        if (imageButton2 != null) {
            imageButton2.setOnGenericMotionListener(onGenericMotionListener);
        }
        if (imageButton2 != null) {
            imageButton2.setOnLongClickListener(onLongClickListener);
        }
        imageButton.setOnTouchListener(onTouchListener);
        if (imageButton3 != null) {
            imageButton3.setOnClickListener(onClickListener);
        }
        if (imageButton3 != null) {
            imageButton3.setOnTouchListener(onTouchListener);
        }
        if (maximizeButtonView != null) {
            maximizeButtonView.onHoverAnimationFinishedListener = function04;
        }
        final AccessibilityNodeInfo.AccessibilityAction accessibilityAction = new AccessibilityNodeInfo.AccessibilityAction(R.id.action_snap_left, this.context.getString(R.string.desktop_mode_a11y_action_snap_left));
        final AccessibilityNodeInfo.AccessibilityAction accessibilityAction2 = new AccessibilityNodeInfo.AccessibilityAction(R.id.action_snap_right, this.context.getString(R.string.desktop_mode_a11y_action_snap_right));
        final AccessibilityNodeInfo.AccessibilityAction accessibilityAction3 = new AccessibilityNodeInfo.AccessibilityAction(R.id.action_maximize_restore, this.context.getString(R.string.desktop_mode_a11y_action_maximize_restore));
        requireViewById2.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.wm.shell.windowdecor.viewholder.MultiTaskingHeaderViewHolder.1
            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view2, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view2, accessibilityNodeInfo);
                accessibilityNodeInfo.addAction(accessibilityAction);
                accessibilityNodeInfo.addAction(accessibilityAction2);
                accessibilityNodeInfo.addAction(accessibilityAction3);
            }

            @Override // android.view.View.AccessibilityDelegate
            public final boolean performAccessibilityAction(View view2, int i, Bundle bundle) {
                if (i == R.id.action_snap_left) {
                    MultiTaskingHeaderViewHolder multiTaskingHeaderViewHolder2 = this;
                    DesktopModeUiEventLogger desktopModeUiEventLogger2 = multiTaskingHeaderViewHolder2.desktopModeUiEventLogger;
                    ActivityManager.RunningTaskInfo runningTaskInfo2 = multiTaskingHeaderViewHolder2.currentTaskInfo;
                    desktopModeUiEventLogger2.log(runningTaskInfo2 != null ? runningTaskInfo2 : null, DesktopModeUiEventLogger.DesktopUiEventEnum.A11Y_ACTION_RESIZE_LEFT);
                    function0.invoke();
                } else if (i == R.id.action_snap_right) {
                    MultiTaskingHeaderViewHolder multiTaskingHeaderViewHolder3 = this;
                    DesktopModeUiEventLogger desktopModeUiEventLogger3 = multiTaskingHeaderViewHolder3.desktopModeUiEventLogger;
                    ActivityManager.RunningTaskInfo runningTaskInfo3 = multiTaskingHeaderViewHolder3.currentTaskInfo;
                    desktopModeUiEventLogger3.log(runningTaskInfo3 != null ? runningTaskInfo3 : null, DesktopModeUiEventLogger.DesktopUiEventEnum.A11Y_ACTION_RESIZE_RIGHT);
                    function02.invoke();
                } else if (i == R.id.action_maximize_restore) {
                    MultiTaskingHeaderViewHolder multiTaskingHeaderViewHolder4 = this;
                    DesktopModeUiEventLogger desktopModeUiEventLogger4 = multiTaskingHeaderViewHolder4.desktopModeUiEventLogger;
                    ActivityManager.RunningTaskInfo runningTaskInfo4 = multiTaskingHeaderViewHolder4.currentTaskInfo;
                    desktopModeUiEventLogger4.log(runningTaskInfo4 != null ? runningTaskInfo4 : null, DesktopModeUiEventLogger.DesktopUiEventEnum.A11Y_ACTION_MAXIMIZE_RESTORE);
                    function03.invoke();
                }
                return super.performAccessibilityAction(view2, i, bundle);
            }
        });
        if (imageButton2 != null) {
            multiTaskingHeaderViewHolder = this;
            imageButton2.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.wm.shell.windowdecor.viewholder.MultiTaskingHeaderViewHolder.2
                @Override // android.view.View.AccessibilityDelegate
                public final void onInitializeAccessibilityNodeInfo(View view2, AccessibilityNodeInfo accessibilityNodeInfo) {
                    super.onInitializeAccessibilityNodeInfo(view2, accessibilityNodeInfo);
                    accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK);
                    accessibilityNodeInfo.addAction(accessibilityAction);
                    accessibilityNodeInfo.addAction(accessibilityAction2);
                    accessibilityNodeInfo.addAction(accessibilityAction3);
                    view2.setClickable(true);
                }

                @Override // android.view.View.AccessibilityDelegate
                public final boolean performAccessibilityAction(View view2, int i, Bundle bundle) {
                    if (i == AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK.getId()) {
                        MultiTaskingHeaderViewHolder multiTaskingHeaderViewHolder2 = multiTaskingHeaderViewHolder;
                        DesktopModeUiEventLogger desktopModeUiEventLogger2 = multiTaskingHeaderViewHolder2.desktopModeUiEventLogger;
                        ActivityManager.RunningTaskInfo runningTaskInfo2 = multiTaskingHeaderViewHolder2.currentTaskInfo;
                        desktopModeUiEventLogger2.log(runningTaskInfo2 != null ? runningTaskInfo2 : null, DesktopModeUiEventLogger.DesktopUiEventEnum.A11Y_APP_WINDOW_MAXIMIZE_RESTORE_BUTTON);
                        view2.performClick();
                    } else if (i == R.id.action_snap_left) {
                        MultiTaskingHeaderViewHolder multiTaskingHeaderViewHolder3 = multiTaskingHeaderViewHolder;
                        DesktopModeUiEventLogger desktopModeUiEventLogger3 = multiTaskingHeaderViewHolder3.desktopModeUiEventLogger;
                        ActivityManager.RunningTaskInfo runningTaskInfo3 = multiTaskingHeaderViewHolder3.currentTaskInfo;
                        desktopModeUiEventLogger3.log(runningTaskInfo3 != null ? runningTaskInfo3 : null, DesktopModeUiEventLogger.DesktopUiEventEnum.A11Y_ACTION_RESIZE_LEFT);
                        function0.invoke();
                    } else if (i == R.id.action_snap_right) {
                        MultiTaskingHeaderViewHolder multiTaskingHeaderViewHolder4 = multiTaskingHeaderViewHolder;
                        DesktopModeUiEventLogger desktopModeUiEventLogger4 = multiTaskingHeaderViewHolder4.desktopModeUiEventLogger;
                        ActivityManager.RunningTaskInfo runningTaskInfo4 = multiTaskingHeaderViewHolder4.currentTaskInfo;
                        desktopModeUiEventLogger4.log(runningTaskInfo4 != null ? runningTaskInfo4 : null, DesktopModeUiEventLogger.DesktopUiEventEnum.A11Y_ACTION_RESIZE_RIGHT);
                        function02.invoke();
                    } else if (i == R.id.action_maximize_restore) {
                        MultiTaskingHeaderViewHolder multiTaskingHeaderViewHolder5 = multiTaskingHeaderViewHolder;
                        DesktopModeUiEventLogger desktopModeUiEventLogger5 = multiTaskingHeaderViewHolder5.desktopModeUiEventLogger;
                        ActivityManager.RunningTaskInfo runningTaskInfo5 = multiTaskingHeaderViewHolder5.currentTaskInfo;
                        desktopModeUiEventLogger5.log(runningTaskInfo5 != null ? runningTaskInfo5 : null, DesktopModeUiEventLogger.DesktopUiEventEnum.A11Y_ACTION_MAXIMIZE_RESTORE);
                        function03.invoke();
                    }
                    return super.performAccessibilityAction(view2, i, bundle);
                }
            });
        } else {
            multiTaskingHeaderViewHolder = this;
        }
        imageButton.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.wm.shell.windowdecor.viewholder.MultiTaskingHeaderViewHolder.3
            @Override // android.view.View.AccessibilityDelegate
            public final boolean performAccessibilityAction(View view2, int i, Bundle bundle) {
                if (i == AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK.getId()) {
                    MultiTaskingHeaderViewHolder multiTaskingHeaderViewHolder2 = MultiTaskingHeaderViewHolder.this;
                    DesktopModeUiEventLogger desktopModeUiEventLogger2 = multiTaskingHeaderViewHolder2.desktopModeUiEventLogger;
                    ActivityManager.RunningTaskInfo runningTaskInfo2 = multiTaskingHeaderViewHolder2.currentTaskInfo;
                    if (runningTaskInfo2 == null) {
                        runningTaskInfo2 = null;
                    }
                    desktopModeUiEventLogger2.log(runningTaskInfo2, DesktopModeUiEventLogger.DesktopUiEventEnum.A11Y_APP_WINDOW_CLOSE_BUTTON);
                }
                return super.performAccessibilityAction(view2, i, bundle);
            }
        });
        if (imageButton3 != null) {
            imageButton3.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.wm.shell.windowdecor.viewholder.MultiTaskingHeaderViewHolder.4
                @Override // android.view.View.AccessibilityDelegate
                public final boolean performAccessibilityAction(View view2, int i, Bundle bundle) {
                    if (i == AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK.getId()) {
                        MultiTaskingHeaderViewHolder multiTaskingHeaderViewHolder2 = MultiTaskingHeaderViewHolder.this;
                        DesktopModeUiEventLogger desktopModeUiEventLogger2 = multiTaskingHeaderViewHolder2.desktopModeUiEventLogger;
                        ActivityManager.RunningTaskInfo runningTaskInfo2 = multiTaskingHeaderViewHolder2.currentTaskInfo;
                        if (runningTaskInfo2 == null) {
                            runningTaskInfo2 = null;
                        }
                        desktopModeUiEventLogger2.log(runningTaskInfo2, DesktopModeUiEventLogger.DesktopUiEventEnum.A11Y_APP_WINDOW_MINIMIZE_BUTTON);
                    }
                    return super.performAccessibilityAction(view2, i, bundle);
                }
            });
        }
        if (findViewById != null) {
            accessibilityViewCommand = null;
            ViewCompat.replaceAccessibilityAction(findViewById, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK, multiTaskingHeaderViewHolder.context.getString(R.string.app_handle_chip_accessibility_announce), null);
        } else {
            accessibilityViewCommand = null;
        }
        if (imageButton3 != null) {
            ViewCompat.replaceAccessibilityAction(imageButton3, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK, multiTaskingHeaderViewHolder.context.getString(R.string.app_header_talkback_action_minimize_button_text), accessibilityViewCommand);
        }
        ViewCompat.replaceAccessibilityAction(imageButton, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK, multiTaskingHeaderViewHolder.context.getString(R.string.app_header_talkback_action_close_button_text), accessibilityViewCommand);
    }

    public final void bindData(HeaderData headerData) {
        HeaderStyle.Background opaque;
        HeaderStyle.Foreground foreground;
        MaximizeButtonView maximizeButtonView;
        DesktopImmersiveCaptionAnimator desktopImmersiveCaptionAnimator;
        ActivityManager.RunningTaskInfo runningTaskInfo = headerData.taskInfo;
        this.currentTaskInfo = runningTaskInfo;
        boolean z = CoreRune.MW_CAPTION;
        boolean z2 = headerData.inFullImmersiveState;
        boolean z3 = headerData.hasGlobalFocus;
        if (z) {
            CaptionButtonPolicy captionButtonPolicy = this.captionButtonPolicy;
            View view = this.captionView;
            captionButtonPolicy.getClass();
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                ViewGroup viewGroup2 = (ViewGroup) viewGroup.findViewById(R.id.button_container);
                if (viewGroup2 == null) {
                    return;
                }
                captionButtonPolicy.mTaskInfo = runningTaskInfo;
                boolean isNightMode = captionButtonPolicy.isNightMode();
                if (captionButtonPolicy.mIsNightMode != isNightMode) {
                    captionButtonPolicy.mIsNightMode = isNightMode;
                    captionButtonPolicy.mCloseButton.setImageDrawable(((CaptionButtonStateManager) captionButtonPolicy).mContext.getDrawable(isNightMode ? R.drawable.mw_caption_button_close_dark : R.drawable.mw_caption_button_close_light));
                    ColorStateList buttonColor = captionButtonPolicy.getButtonColor();
                    for (int i = 0; i < viewGroup2.getChildCount(); i++) {
                        View childAt = viewGroup2.getChildAt(i);
                        if (childAt instanceof CaptionButton) {
                            ((CaptionButton) childAt).setImageTintList(buttonColor);
                        }
                    }
                }
                for (int i2 = 0; i2 < viewGroup2.getChildCount(); i2++) {
                    View childAt2 = viewGroup2.getChildAt(i2);
                    if (childAt2 instanceof CaptionButton) {
                        ((CaptionButton) childAt2).setTaskFocusState(z3);
                    }
                }
                boolean z4 = CoreRune.MW_CAPTION_DESKTOP;
                if (z4 && captionButtonPolicy.mIsInDesktopWindowing) {
                    ImageButton imageButton = captionButtonPolicy.mDesktopExpandButton;
                    if (imageButton != null) {
                        imageButton.setAlpha(z3 ? 1.0f : 0.4f);
                    }
                    ImageView imageView = captionButtonPolicy.mDesktopAppIcon;
                    if (imageView != null) {
                        imageView.setAlpha(z3 ? 1.0f : 0.4f);
                    }
                }
                captionButtonPolicy.setupSplitButtonImage(runningTaskInfo);
                captionButtonPolicy.setupFreeformButtonState(runningTaskInfo);
                if (z4) {
                    if (captionButtonPolicy.mToggleFreeformButton != null) {
                        boolean isTaskMaximized = DesktopModeUtils.isTaskMaximized(runningTaskInfo, captionButtonPolicy.mDisplayController);
                        if (((CaptionButtonStateManager) captionButtonPolicy).mInFullImmersiveState != z2) {
                            ((CaptionButtonStateManager) captionButtonPolicy).mInFullImmersiveState = z2;
                        }
                        if (z2 || isTaskMaximized) {
                            String string = ((CaptionButtonStateManager) captionButtonPolicy).mContext.getString(((CaptionButtonStateManager) captionButtonPolicy).mInFullImmersiveState ? R.string.sec_decor_button_text_exit_full_screen : R.string.sec_decor_button_text_restore_window);
                            captionButtonPolicy.mToggleFreeformButton.setImageDrawable(((CaptionButtonStateManager) captionButtonPolicy).mContext.getDrawable(R.drawable.mw_caption_button_restore));
                            captionButtonPolicy.mToggleFreeformButton.setContentDescription(string);
                            captionButtonPolicy.mToggleFreeformButton.setTooltipText(string);
                        } else {
                            String string2 = ((CaptionButtonStateManager) captionButtonPolicy).mContext.getString(R.string.sec_decor_button_text_maximize);
                            captionButtonPolicy.mToggleFreeformButton.setImageDrawable(((CaptionButtonStateManager) captionButtonPolicy).mContext.getDrawable(R.drawable.mw_caption_button_maximize));
                            captionButtonPolicy.mToggleFreeformButton.setContentDescription(string2);
                            captionButtonPolicy.mToggleFreeformButton.setTooltipText(string2);
                        }
                    }
                    CaptionButton captionButton = captionButtonPolicy.mMinimizeButton;
                    if (captionButton != null) {
                        captionButton.setEnabled(!z2);
                    }
                }
                boolean isTransparentCaptionBarAppearance = TaskInfoKt.isTransparentCaptionBarAppearance(runningTaskInfo);
                if (isTransparentCaptionBarAppearance != captionButtonPolicy.mIsCaptionTransparent) {
                    captionButtonPolicy.mIsCaptionTransparent = isTransparentCaptionBarAppearance;
                    viewGroup.setBackgroundColor(captionButtonPolicy.getBackgroundColor(captionButtonPolicy.mContext));
                }
                if (CoreRune.MW_CAPTION_DESKTOP_IMMERSIVE && captionButtonPolicy.mIsInDesktopWindowing) {
                    boolean z5 = captionButtonPolicy.mInFullImmersiveState;
                    boolean z6 = headerData.isStatusBarVisible;
                    if (z2 != z5) {
                        captionButtonPolicy.mInFullImmersiveState = z2;
                        viewGroup.setBackgroundColor(captionButtonPolicy.getBackgroundColor(captionButtonPolicy.mContext));
                        int color = !captionButtonPolicy.mInFullImmersiveState ? 0 : captionButtonPolicy.mContext.getResources().getColor(17171625, null);
                        ColorStateList buttonColor2 = !captionButtonPolicy.mInFullImmersiveState ? captionButtonPolicy.getButtonColor() : captionButtonPolicy.getButtonFillColor(true);
                        for (int i3 = 0; i3 < viewGroup.getChildCount(); i3++) {
                            viewGroup.getChildAt(i3).setBackgroundColor(color);
                        }
                        ViewGroup viewGroup3 = (ViewGroup) viewGroup.findViewById(R.id.button_container);
                        if (viewGroup3 != null) {
                            for (int i4 = 0; i4 < viewGroup3.getChildCount(); i4++) {
                                View childAt3 = viewGroup3.getChildAt(i4);
                                if (childAt3 instanceof CaptionButton) {
                                    CaptionButton captionButton2 = (CaptionButton) childAt3;
                                    captionButton2.setImageTintList(buttonColor2);
                                    if (!captionButton2.isEnabled()) {
                                        captionButton2.setAlpha(0.4f);
                                    }
                                }
                            }
                            ImageButton imageButton2 = captionButtonPolicy.mDesktopExpandButton;
                            if (imageButton2 != null) {
                                imageButton2.setImageTintList(buttonColor2);
                                ImageButton imageButton3 = captionButtonPolicy.mDesktopExpandButton;
                                imageButton3.setAlpha(imageButton3.isEnabled() ? 1.0f : 0.4f);
                            }
                            ImageView imageView2 = captionButtonPolicy.mDesktopAppIcon;
                            if (imageView2 != null) {
                                imageView2.setAlpha(imageView2.isEnabled() ? 1.0f : 0.4f);
                            }
                        }
                        if (captionButtonPolicy.mInFullImmersiveState) {
                            DesktopImmersiveCaptionAnimator desktopImmersiveCaptionAnimator2 = new DesktopImmersiveCaptionAnimator(runningTaskInfo, captionButtonPolicy.mHandler, viewGroup, headerData.immersiveAnimHeight);
                            captionButtonPolicy.mImmersiveAnimator = desktopImmersiveCaptionAnimator2;
                            captionButtonPolicy.mIsStatusBarVisible = z6;
                            desktopImmersiveCaptionAnimator2.setShownState(true);
                            desktopImmersiveCaptionAnimator2.mIsPaused = false;
                            desktopImmersiveCaptionAnimator2.hide();
                        } else {
                            captionButtonPolicy.mImmersiveAnimator.setPaused();
                            captionButtonPolicy.mImmersiveAnimator = null;
                        }
                    } else if (z5 && (desktopImmersiveCaptionAnimator = captionButtonPolicy.mImmersiveAnimator) != null && desktopImmersiveCaptionAnimator.mIsDefaultDisplay && z6 != captionButtonPolicy.mIsStatusBarVisible) {
                        captionButtonPolicy.mIsStatusBarVisible = z6;
                        if (z6) {
                            desktopImmersiveCaptionAnimator.show();
                        } else {
                            desktopImmersiveCaptionAnimator.hide();
                        }
                    }
                }
                if (CoreRune.MW_CAPTION_KEYGUARD) {
                    boolean z7 = captionButtonPolicy.mIsKeyguardShowing;
                    boolean z8 = headerData.isKeyguardShowing;
                    if (z7 != z8) {
                        captionButtonPolicy.mIsKeyguardShowing = z8;
                        for (int i5 = 0; i5 < viewGroup.getChildCount(); i5++) {
                            View childAt4 = viewGroup.getChildAt(i5);
                            if (childAt4 instanceof CaptionButton) {
                                CaptionButton captionButton3 = (CaptionButton) childAt4;
                                if (captionButton3.getId() != R.id.close_window) {
                                    captionButton3.setEnabled(!z8);
                                }
                            }
                        }
                        if (captionButtonPolicy.mIsInDesktopWindowing) {
                            View findViewById = viewGroup.findViewById(R.id.open_menu_button);
                            if (findViewById != null) {
                                findViewById.setEnabled(true ^ z8);
                            }
                            ImageButton imageButton4 = captionButtonPolicy.mDesktopExpandButton;
                            if (imageButton4 != null) {
                                imageButton4.setAlpha(!z8 ? 1.0f : 0.4f);
                            }
                            ImageView imageView3 = captionButtonPolicy.mDesktopAppIcon;
                            if (imageView3 != null) {
                                imageView3.setAlpha(z8 ? 0.4f : 1.0f);
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    return;
                }
                return;
            }
            return;
        }
        boolean isTrue = DesktopModeFlags.ENABLE_THEMED_APP_HEADERS.isTrue();
        boolean z9 = headerData.isCaptionVisible;
        if (!isTrue) {
            int i6 = 166;
            if (DesktopModeFlags.ENABLE_DESKTOP_APP_HANDLE_ANIMATION.isTrue()) {
                this.captionView.setVisibility(z9 ? 0 : 8);
            }
            this.captionView.setBackgroundColor(TaskInfoKt.isTransparentCaptionBarAppearance(runningTaskInfo) ? 0 : this.context.obtainStyledAttributes(null, new int[]{isDarkMode$1() ? !z3 ? android.R.color.sliding_tab_text_color_shadow : android.R.color.surface_header_light : !z3 ? android.R.color.surface_dark : android.R.color.secondary_text_nofocus}, 0, 0).getColor(0, 0));
            Context context = this.context;
            boolean isTransparentCaptionBarAppearance2 = TaskInfoKt.isTransparentCaptionBarAppearance(runningTaskInfo);
            int i7 = android.R.color.search_url_text;
            if ((!isTransparentCaptionBarAppearance2 || !TaskInfoKt.isLightCaptionBarAppearance(runningTaskInfo)) && ((TaskInfoKt.isTransparentCaptionBarAppearance(runningTaskInfo) && !TaskInfoKt.isLightCaptionBarAppearance(runningTaskInfo)) || isDarkMode$1())) {
                i7 = 17171178;
            }
            int color2 = context.getColor(i7);
            if (isDarkMode$1() && !z3) {
                i6 = 140;
            } else if (isDarkMode$1() || z3) {
                i6 = 255;
            }
            if (i6 != 255) {
                color2 = Color.argb(i6, Color.red(color2), Color.green(color2), Color.blue(color2));
            }
            int alpha = Color.alpha(color2);
            this.closeWindowButton.setImageTintList(ColorStateList.valueOf(color2));
            ImageButton imageButton5 = this.maximizeWindowButton;
            if (imageButton5 != null) {
                imageButton5.setImageTintList(ColorStateList.valueOf(color2));
            }
            ImageButton imageButton6 = this.minimizeWindowButton;
            if (imageButton6 != null) {
                imageButton6.setImageTintList(ColorStateList.valueOf(color2));
            }
            ImageButton imageButton7 = this.expandMenuButton;
            if (imageButton7 != null) {
                imageButton7.setImageTintList(ColorStateList.valueOf(color2));
            }
            TextView textView = this.appNameTextView;
            if (textView != null) {
                textView.setVisibility(!TaskInfoKt.isTransparentCaptionBarAppearance(runningTaskInfo) ? 0 : 8);
            }
            TextView textView2 = this.appNameTextView;
            if (textView2 != null) {
                textView2.setTextColor(color2);
            }
            ImageView imageView4 = this.appIconImageView;
            if (imageView4 != null) {
                imageView4.setImageAlpha(alpha);
            }
            ImageButton imageButton8 = this.maximizeWindowButton;
            if (imageButton8 != null) {
                imageButton8.setImageAlpha(alpha);
            }
            ImageButton imageButton9 = this.minimizeWindowButton;
            if (imageButton9 != null) {
                imageButton9.setImageAlpha(alpha);
            }
            this.closeWindowButton.setImageAlpha(alpha);
            ImageButton imageButton10 = this.expandMenuButton;
            if (imageButton10 != null) {
                imageButton10.setImageAlpha(alpha);
            }
            TypedArray obtainStyledAttributes = this.context.obtainStyledAttributes(null, new int[]{android.R.attr.selectableItemBackground, android.R.attr.selectableItemBackgroundBorderless}, 0, 0);
            View view2 = this.openMenuButton;
            if (view2 != null) {
                view2.setBackground(obtainStyledAttributes.getDrawable(0));
            }
            ImageButton imageButton11 = this.maximizeWindowButton;
            if (imageButton11 != null) {
                imageButton11.setBackground(obtainStyledAttributes.getDrawable(1));
            }
            this.closeWindowButton.setBackground(obtainStyledAttributes.getDrawable(1));
            ImageButton imageButton12 = this.minimizeWindowButton;
            if (imageButton12 != null) {
                imageButton12.setBackground(obtainStyledAttributes.getDrawable(1));
            }
            obtainStyledAttributes.recycle();
            MaximizeButtonView maximizeButtonView2 = this.maximizeButtonView;
            if (maximizeButtonView2 != null) {
                boolean isDarkMode$1 = isDarkMode$1();
                int i8 = MaximizeButtonView.$r8$clinit;
                maximizeButtonView2.setAnimationTints(isDarkMode$1, null, null, null);
            }
            ImageButton imageButton13 = this.minimizeWindowButton;
            if (imageButton13 != null) {
                imageButton13.setVisibility(!DesktopModeFlags.ENABLE_MINIMIZE_BUTTON.isTrue() ? 8 : 0);
                return;
            }
            return;
        }
        Header header = new Header(TaskInfoKt.isTransparentCaptionBarAppearance(runningTaskInfo) ? Header.Type.CUSTOM : Header.Type.DEFAULT, this.decorThemeUtil.getAppTheme(runningTaskInfo), z3, TaskInfoKt.isLightCaptionBarAppearance(runningTaskInfo));
        int[] iArr = WhenMappings.$EnumSwitchMapping$2;
        Header.Type type = header.type;
        int i9 = iArr[type.ordinal()];
        Theme theme = header.appTheme;
        boolean z10 = header.isFocused;
        if (i9 == 1) {
            int i10 = WhenMappings.$EnumSwitchMapping$1[theme.ordinal()];
            if (i10 == 1) {
                opaque = z10 ? new HeaderStyle.Background.Opaque(ColorKt.m467toArgb8_81llA(this.lightColors.secondaryContainer)) : new HeaderStyle.Background.Opaque(ColorKt.m467toArgb8_81llA(this.lightColors.surfaceContainerLow));
            } else {
                if (i10 != 2) {
                    throw new NoWhenBranchMatchedException();
                }
                opaque = z10 ? new HeaderStyle.Background.Opaque(ColorKt.m467toArgb8_81llA(this.darkColors.surfaceContainerHigh)) : new HeaderStyle.Background.Opaque(ColorKt.m467toArgb8_81llA(this.darkColors.surfaceDim));
            }
        } else {
            if (i9 != 2) {
                throw new NoWhenBranchMatchedException();
            }
            opaque = HeaderStyle.Background.Transparent.INSTANCE;
        }
        int i11 = iArr[type.ordinal()];
        if (i11 == 1) {
            int i12 = WhenMappings.$EnumSwitchMapping$1[theme.ordinal()];
            if (i12 == 1) {
                foreground = z10 ? new HeaderStyle.Foreground(ColorKt.m467toArgb8_81llA(this.lightColors.onSecondaryContainer), 255) : new HeaderStyle.Foreground(ColorKt.m467toArgb8_81llA(this.lightColors.onSecondaryContainer), 166);
            } else {
                if (i12 != 2) {
                    throw new NoWhenBranchMatchedException();
                }
                foreground = z10 ? new HeaderStyle.Foreground(ColorKt.m467toArgb8_81llA(this.darkColors.onSurface), 255) : new HeaderStyle.Foreground(ColorKt.m467toArgb8_81llA(this.darkColors.onSurface), 140);
            }
        } else {
            if (i11 != 2) {
                throw new NoWhenBranchMatchedException();
            }
            boolean z11 = header.isAppearanceCaptionLight;
            if (z11 && z10) {
                foreground = new HeaderStyle.Foreground(ColorKt.m467toArgb8_81llA(this.lightColors.onSecondaryContainer), 255);
            } else if (z11 && !z10) {
                foreground = new HeaderStyle.Foreground(ColorKt.m467toArgb8_81llA(this.lightColors.onSecondaryContainer), 166);
            } else if (!z11 && z10) {
                foreground = new HeaderStyle.Foreground(ColorKt.m467toArgb8_81llA(this.darkColors.onSurface), 255);
            } else {
                if (z11 || z10) {
                    throw new IllegalStateException(("No other combination expected header=" + header).toString());
                }
                foreground = new HeaderStyle.Foreground(ColorKt.m467toArgb8_81llA(this.darkColors.onSurface), 140);
            }
        }
        HeaderStyle headerStyle = new HeaderStyle(opaque, foreground);
        if (DesktopModeFlags.ENABLE_DESKTOP_APP_HANDLE_ANIMATION.isTrue()) {
            this.captionView.setVisibility(z9 ? 0 : 8);
        }
        HeaderStyle.Background background = headerStyle.background;
        if (background instanceof HeaderStyle.Background.Opaque) {
            this.captionView.setBackgroundColor(((HeaderStyle.Background.Opaque) background).color);
        } else {
            if (!Intrinsics.areEqual(background, HeaderStyle.Background.Transparent.INSTANCE)) {
                throw new NoWhenBranchMatchedException();
            }
            this.captionView.setBackgroundColor(0);
        }
        HeaderStyle.Foreground foreground2 = headerStyle.foreground;
        int i13 = foreground2.color;
        ColorStateList valueOf = ColorStateList.valueOf(i13);
        int i14 = foreground2.opacity;
        ColorStateList withAlpha = valueOf.withAlpha(i14);
        View view3 = this.openMenuButton;
        if (view3 != null) {
            view3.setBackground(ButtonBackgroundDrawableUtilsKt.createBackgroundDrawable(i13, this.headerButtonsRippleRadius, this.appChipDrawableInsets));
            ImageButton imageButton14 = this.expandMenuButton;
            if (imageButton14 != null) {
                imageButton14.setImageTintList(withAlpha);
            }
            TextView textView3 = this.appNameTextView;
            if (textView3 != null) {
                textView3.setVisibility(type == Header.Type.DEFAULT ? 0 : 8);
                textView3.setTextColor(withAlpha);
            }
            ImageView imageView5 = this.appIconImageView;
            if (imageView5 != null) {
                imageView5.setImageAlpha(i14);
            }
            view3.setDefaultFocusHighlightEnabled(false);
        }
        ImageButton imageButton15 = this.minimizeWindowButton;
        if (imageButton15 != null) {
            imageButton15.setImageTintList(withAlpha);
            imageButton15.setBackground(ButtonBackgroundDrawableUtilsKt.createBackgroundDrawable(i13, this.headerButtonsRippleRadius, this.minimizeDrawableInsets));
        }
        ImageButton imageButton16 = this.minimizeWindowButton;
        if (imageButton16 != null) {
            imageButton16.setVisibility(!DesktopModeFlags.ENABLE_MINIMIZE_BUTTON.isTrue() ? 8 : 0);
        }
        MaximizeButtonView maximizeButtonView3 = this.maximizeButtonView;
        if (maximizeButtonView3 != null) {
            maximizeButtonView3.setAnimationTints(theme == Theme.DARK, withAlpha, Integer.valueOf(i13), ButtonBackgroundDrawableUtilsKt.createBackgroundDrawable(i13, this.headerButtonsRippleRadius, this.maximizeDrawableInsets));
            int i15 = ((DesktopModeFlags.ENABLE_FULLY_IMMERSIVE_IN_DESKTOP.isTrue() && z2) || headerData.isTaskMaximized) ? R.drawable.decor_desktop_mode_immersive_or_maximize_exit_button_dark : R.drawable.decor_desktop_mode_maximize_button_dark;
            maximizeButtonView3.maximizeWindow.setImageResource(i15);
            if (i15 == R.drawable.decor_desktop_mode_immersive_or_maximize_exit_button_dark) {
                this.sizeToggleDirection = SizeToggleDirection.RESTORE;
                ImageButton imageButton17 = this.maximizeWindowButton;
                if (imageButton17 != null) {
                    ViewCompat.replaceAccessibilityAction(imageButton17, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK, this.a11yAnnounceTextRestore, null);
                }
            } else if (i15 == R.drawable.decor_desktop_mode_maximize_button_dark) {
                this.sizeToggleDirection = SizeToggleDirection.MAXIMIZE;
                ImageButton imageButton18 = this.maximizeWindowButton;
                if (imageButton18 != null) {
                    ViewCompat.replaceAccessibilityAction(imageButton18, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK, this.a11yAnnounceTextMaximize, null);
                }
            }
            updateMaximizeButtonContentDescription$1();
        }
        ImageButton imageButton19 = this.closeWindowButton;
        imageButton19.setImageTintList(withAlpha);
        imageButton19.setBackground(ButtonBackgroundDrawableUtilsKt.createBackgroundDrawable(i13, this.headerButtonsRippleRadius, this.closeDrawableInsets));
        boolean z12 = headerData.enableMaximizeLongClick;
        if (!z12 && (maximizeButtonView = this.maximizeButtonView) != null) {
            maximizeButtonView.cancelHoverAnimation();
        }
        MaximizeButtonView maximizeButtonView4 = this.maximizeButtonView;
        if (maximizeButtonView4 != null) {
            maximizeButtonView4.hoverDisabled = true ^ z12;
        }
        ImageButton imageButton20 = this.maximizeWindowButton;
        if (imageButton20 != null) {
            imageButton20.setOnLongClickListener(z12 ? this.onLongClickListener : null);
        }
    }

    @Override // java.lang.AutoCloseable
    public final void close() {
        ImageButton imageButton = this.maximizeWindowButton;
        if (imageButton != null) {
            imageButton.cancelLongPress();
        }
        if (CoreRune.MW_CAPTION_DESKTOP_IMMERSIVE) {
            this.captionView.getRootView().setVisibility(0);
        }
    }

    public final boolean isDarkMode$1() {
        return (this.context.getResources().getConfiguration().uiMode & 48) == 32;
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0024, code lost:
    
        if (r4 == null) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0027, code lost:
    
        r2 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0033, code lost:
    
        r1.setContentDescription(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0036, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0031, code lost:
    
        if (r4 == null) goto L25;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateMaximizeButtonContentDescription$1() {
        /*
            r4 = this;
            java.lang.String r0 = r4.a11yTextRestore
            if (r0 == 0) goto L36
            java.lang.String r0 = r4.a11yTextMaximize
            if (r0 == 0) goto L36
            com.android.wm.shell.windowdecor.viewholder.MultiTaskingHeaderViewHolder$SizeToggleDirection r0 = r4.sizeToggleDirection
            if (r0 == 0) goto L36
            android.widget.ImageButton r1 = r4.maximizeWindowButton
            if (r1 == 0) goto L36
            r2 = 0
            if (r0 != 0) goto L14
            r0 = r2
        L14:
            int[] r3 = com.android.wm.shell.windowdecor.viewholder.MultiTaskingHeaderViewHolder.WhenMappings.$EnumSwitchMapping$0
            int r0 = r0.ordinal()
            r0 = r3[r0]
            r3 = 1
            if (r0 == r3) goto L2f
            r3 = 2
            if (r0 != r3) goto L29
            java.lang.String r4 = r4.a11yTextRestore
            if (r4 != 0) goto L27
            goto L33
        L27:
            r2 = r4
            goto L33
        L29:
            kotlin.NoWhenBranchMatchedException r4 = new kotlin.NoWhenBranchMatchedException
            r4.<init>()
            throw r4
        L2f:
            java.lang.String r4 = r4.a11yTextMaximize
            if (r4 != 0) goto L27
        L33:
            r1.setContentDescription(r2)
        L36:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.windowdecor.viewholder.MultiTaskingHeaderViewHolder.updateMaximizeButtonContentDescription$1():void");
    }

    @Override // com.android.wm.shell.windowdecor.viewholder.WindowDecorationViewHolder
    public final void onHandleMenuClosed() {
    }

    @Override // com.android.wm.shell.windowdecor.viewholder.WindowDecorationViewHolder
    public final void onHandleMenuOpened() {
    }
}
