package com.android.wm.shell.windowdecor.viewholder;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
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
import com.android.systemui.R;
import com.android.wm.shell.desktopmode.DesktopModeUiEventLogger;
import com.android.wm.shell.windowdecor.MaximizeButtonView;
import com.android.wm.shell.windowdecor.common.ButtonBackgroundDrawableUtilsKt;
import com.android.wm.shell.windowdecor.common.DecorThemeUtil;
import com.android.wm.shell.windowdecor.common.DrawableInsets;
import com.android.wm.shell.windowdecor.common.Theme;
import com.android.wm.shell.windowdecor.extension.TaskInfoKt;
import com.android.wm.shell.windowdecor.viewholder.WindowDecorationViewHolder;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.NoWhenBranchMatchedException;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class AppHeaderViewHolder extends WindowDecorationViewHolder {
    public final String a11yAnnounceTextMaximize;
    public final String a11yAnnounceTextRestore;
    public String a11yTextMaximize;
    public String a11yTextRestore;
    public final DrawableInsets appChipDrawableInsets;
    public final ImageView appIconImageView;
    public final TextView appNameTextView;
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
        public final boolean inFullImmersiveState;
        public final boolean isCaptionVisible;
        public final boolean isTaskMaximized;
        public final ActivityManager.RunningTaskInfo taskInfo;

        public HeaderData(ActivityManager.RunningTaskInfo runningTaskInfo, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
            this.taskInfo = runningTaskInfo;
            this.isTaskMaximized = z;
            this.inFullImmersiveState = z2;
            this.hasGlobalFocus = z3;
            this.enableMaximizeLongClick = z4;
            this.isCaptionVisible = z5;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof HeaderData)) {
                return false;
            }
            HeaderData headerData = (HeaderData) obj;
            return Intrinsics.areEqual(this.taskInfo, headerData.taskInfo) && this.isTaskMaximized == headerData.isTaskMaximized && this.inFullImmersiveState == headerData.inFullImmersiveState && this.hasGlobalFocus == headerData.hasGlobalFocus && this.enableMaximizeLongClick == headerData.enableMaximizeLongClick && this.isCaptionVisible == headerData.isCaptionVisible;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.isCaptionVisible) + TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(this.taskInfo.hashCode() * 31, 31, this.isTaskMaximized), 31, this.inFullImmersiveState), 31, this.hasGlobalFocus), 31, this.enableMaximizeLongClick);
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
            return MoveResult$$ExternalSyntheticOutline0.m(sb, this.isCaptionVisible, ")");
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
                    return -653789275;
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

    public AppHeaderViewHolder(View view, View.OnTouchListener onTouchListener, View.OnClickListener onClickListener, View.OnLongClickListener onLongClickListener, View.OnGenericMotionListener onGenericMotionListener, final Function0 function0, final Function0 function02, final Function0 function03, Function0 function04, DesktopModeUiEventLogger desktopModeUiEventLogger) {
        super(view);
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
        View requireViewById3 = view.requireViewById(R.id.open_menu_button);
        this.openMenuButton = requireViewById3;
        ImageButton imageButton = (ImageButton) view.requireViewById(R.id.close_window);
        this.closeWindowButton = imageButton;
        this.expandMenuButton = (ImageButton) view.requireViewById(R.id.expand_menu_button);
        MaximizeButtonView maximizeButtonView = (MaximizeButtonView) view.requireViewById(R.id.maximize_button_view);
        this.maximizeButtonView = maximizeButtonView;
        ImageButton imageButton2 = (ImageButton) view.requireViewById(R.id.maximize_window);
        this.maximizeWindowButton = imageButton2;
        ImageButton imageButton3 = (ImageButton) view.requireViewById(R.id.minimize_window);
        this.minimizeWindowButton = imageButton3;
        this.appNameTextView = (TextView) view.requireViewById(R.id.application_name);
        this.appIconImageView = (ImageView) view.requireViewById(R.id.application_icon);
        this.a11yAnnounceTextMaximize = this.context.getString(R.string.app_header_talkback_action_maximize_button_text);
        this.a11yAnnounceTextRestore = this.context.getString(R.string.app_header_talkback_action_restore_button_text);
        requireViewById.setOnTouchListener(onTouchListener);
        requireViewById2.setOnTouchListener(onTouchListener);
        requireViewById3.setOnClickListener(onClickListener);
        requireViewById3.setOnTouchListener(onTouchListener);
        imageButton.setOnClickListener(onClickListener);
        imageButton2.setOnClickListener(onClickListener);
        imageButton2.setOnTouchListener(onTouchListener);
        imageButton2.setOnGenericMotionListener(onGenericMotionListener);
        imageButton2.setOnLongClickListener(onLongClickListener);
        imageButton.setOnTouchListener(onTouchListener);
        imageButton3.setOnClickListener(onClickListener);
        imageButton3.setOnTouchListener(onTouchListener);
        maximizeButtonView.onHoverAnimationFinishedListener = function04;
        final AccessibilityNodeInfo.AccessibilityAction accessibilityAction = new AccessibilityNodeInfo.AccessibilityAction(R.id.action_snap_left, this.context.getString(R.string.desktop_mode_a11y_action_snap_left));
        final AccessibilityNodeInfo.AccessibilityAction accessibilityAction2 = new AccessibilityNodeInfo.AccessibilityAction(R.id.action_snap_right, this.context.getString(R.string.desktop_mode_a11y_action_snap_right));
        final AccessibilityNodeInfo.AccessibilityAction accessibilityAction3 = new AccessibilityNodeInfo.AccessibilityAction(R.id.action_maximize_restore, this.context.getString(R.string.desktop_mode_a11y_action_maximize_restore));
        requireViewById2.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.wm.shell.windowdecor.viewholder.AppHeaderViewHolder.1
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
                    AppHeaderViewHolder appHeaderViewHolder = this;
                    DesktopModeUiEventLogger desktopModeUiEventLogger2 = appHeaderViewHolder.desktopModeUiEventLogger;
                    ActivityManager.RunningTaskInfo runningTaskInfo = appHeaderViewHolder.currentTaskInfo;
                    desktopModeUiEventLogger2.log(runningTaskInfo != null ? runningTaskInfo : null, DesktopModeUiEventLogger.DesktopUiEventEnum.A11Y_ACTION_RESIZE_LEFT);
                    function0.invoke();
                } else if (i == R.id.action_snap_right) {
                    AppHeaderViewHolder appHeaderViewHolder2 = this;
                    DesktopModeUiEventLogger desktopModeUiEventLogger3 = appHeaderViewHolder2.desktopModeUiEventLogger;
                    ActivityManager.RunningTaskInfo runningTaskInfo2 = appHeaderViewHolder2.currentTaskInfo;
                    desktopModeUiEventLogger3.log(runningTaskInfo2 != null ? runningTaskInfo2 : null, DesktopModeUiEventLogger.DesktopUiEventEnum.A11Y_ACTION_RESIZE_RIGHT);
                    function02.invoke();
                } else if (i == R.id.action_maximize_restore) {
                    AppHeaderViewHolder appHeaderViewHolder3 = this;
                    DesktopModeUiEventLogger desktopModeUiEventLogger4 = appHeaderViewHolder3.desktopModeUiEventLogger;
                    ActivityManager.RunningTaskInfo runningTaskInfo3 = appHeaderViewHolder3.currentTaskInfo;
                    desktopModeUiEventLogger4.log(runningTaskInfo3 != null ? runningTaskInfo3 : null, DesktopModeUiEventLogger.DesktopUiEventEnum.A11Y_ACTION_MAXIMIZE_RESTORE);
                    function03.invoke();
                }
                return super.performAccessibilityAction(view2, i, bundle);
            }
        });
        imageButton2.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.wm.shell.windowdecor.viewholder.AppHeaderViewHolder.2
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
                    AppHeaderViewHolder appHeaderViewHolder = this;
                    DesktopModeUiEventLogger desktopModeUiEventLogger2 = appHeaderViewHolder.desktopModeUiEventLogger;
                    ActivityManager.RunningTaskInfo runningTaskInfo = appHeaderViewHolder.currentTaskInfo;
                    desktopModeUiEventLogger2.log(runningTaskInfo != null ? runningTaskInfo : null, DesktopModeUiEventLogger.DesktopUiEventEnum.A11Y_APP_WINDOW_MAXIMIZE_RESTORE_BUTTON);
                    view2.performClick();
                } else if (i == R.id.action_snap_left) {
                    AppHeaderViewHolder appHeaderViewHolder2 = this;
                    DesktopModeUiEventLogger desktopModeUiEventLogger3 = appHeaderViewHolder2.desktopModeUiEventLogger;
                    ActivityManager.RunningTaskInfo runningTaskInfo2 = appHeaderViewHolder2.currentTaskInfo;
                    desktopModeUiEventLogger3.log(runningTaskInfo2 != null ? runningTaskInfo2 : null, DesktopModeUiEventLogger.DesktopUiEventEnum.A11Y_ACTION_RESIZE_LEFT);
                    function0.invoke();
                } else if (i == R.id.action_snap_right) {
                    AppHeaderViewHolder appHeaderViewHolder3 = this;
                    DesktopModeUiEventLogger desktopModeUiEventLogger4 = appHeaderViewHolder3.desktopModeUiEventLogger;
                    ActivityManager.RunningTaskInfo runningTaskInfo3 = appHeaderViewHolder3.currentTaskInfo;
                    desktopModeUiEventLogger4.log(runningTaskInfo3 != null ? runningTaskInfo3 : null, DesktopModeUiEventLogger.DesktopUiEventEnum.A11Y_ACTION_RESIZE_RIGHT);
                    function02.invoke();
                } else if (i == R.id.action_maximize_restore) {
                    AppHeaderViewHolder appHeaderViewHolder4 = this;
                    DesktopModeUiEventLogger desktopModeUiEventLogger5 = appHeaderViewHolder4.desktopModeUiEventLogger;
                    ActivityManager.RunningTaskInfo runningTaskInfo4 = appHeaderViewHolder4.currentTaskInfo;
                    desktopModeUiEventLogger5.log(runningTaskInfo4 != null ? runningTaskInfo4 : null, DesktopModeUiEventLogger.DesktopUiEventEnum.A11Y_ACTION_MAXIMIZE_RESTORE);
                    function03.invoke();
                }
                return super.performAccessibilityAction(view2, i, bundle);
            }
        });
        imageButton.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.wm.shell.windowdecor.viewholder.AppHeaderViewHolder.3
            @Override // android.view.View.AccessibilityDelegate
            public final boolean performAccessibilityAction(View view2, int i, Bundle bundle) {
                if (i == AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK.getId()) {
                    AppHeaderViewHolder appHeaderViewHolder = AppHeaderViewHolder.this;
                    DesktopModeUiEventLogger desktopModeUiEventLogger2 = appHeaderViewHolder.desktopModeUiEventLogger;
                    ActivityManager.RunningTaskInfo runningTaskInfo = appHeaderViewHolder.currentTaskInfo;
                    if (runningTaskInfo == null) {
                        runningTaskInfo = null;
                    }
                    desktopModeUiEventLogger2.log(runningTaskInfo, DesktopModeUiEventLogger.DesktopUiEventEnum.A11Y_APP_WINDOW_CLOSE_BUTTON);
                }
                return super.performAccessibilityAction(view2, i, bundle);
            }
        });
        imageButton3.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.wm.shell.windowdecor.viewholder.AppHeaderViewHolder.4
            @Override // android.view.View.AccessibilityDelegate
            public final boolean performAccessibilityAction(View view2, int i, Bundle bundle) {
                if (i == AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK.getId()) {
                    AppHeaderViewHolder appHeaderViewHolder = AppHeaderViewHolder.this;
                    DesktopModeUiEventLogger desktopModeUiEventLogger2 = appHeaderViewHolder.desktopModeUiEventLogger;
                    ActivityManager.RunningTaskInfo runningTaskInfo = appHeaderViewHolder.currentTaskInfo;
                    if (runningTaskInfo == null) {
                        runningTaskInfo = null;
                    }
                    desktopModeUiEventLogger2.log(runningTaskInfo, DesktopModeUiEventLogger.DesktopUiEventEnum.A11Y_APP_WINDOW_MINIMIZE_BUTTON);
                }
                return super.performAccessibilityAction(view2, i, bundle);
            }
        });
        AccessibilityNodeInfoCompat.AccessibilityActionCompat accessibilityActionCompat = AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK;
        ViewCompat.replaceAccessibilityAction(requireViewById3, accessibilityActionCompat, this.context.getString(R.string.app_handle_chip_accessibility_announce), null);
        ViewCompat.replaceAccessibilityAction(imageButton3, accessibilityActionCompat, this.context.getString(R.string.app_header_talkback_action_minimize_button_text), null);
        ViewCompat.replaceAccessibilityAction(imageButton, accessibilityActionCompat, this.context.getString(R.string.app_header_talkback_action_close_button_text), null);
    }

    public final void bindData(HeaderData headerData) {
        int color;
        Header.Type type;
        HeaderStyle.Background opaque;
        HeaderStyle.Foreground foreground;
        ActivityManager.RunningTaskInfo runningTaskInfo = headerData.taskInfo;
        this.currentTaskInfo = runningTaskInfo;
        boolean isTrue = DesktopModeFlags.ENABLE_THEMED_APP_HEADERS.isTrue();
        boolean z = headerData.hasGlobalFocus;
        boolean z2 = headerData.isCaptionVisible;
        int i = 166;
        if (!isTrue) {
            if (DesktopModeFlags.ENABLE_DESKTOP_APP_HANDLE_ANIMATION.isTrue()) {
                this.captionView.setVisibility(z2 ? 0 : 8);
            }
            View view = this.captionView;
            if (TaskInfoKt.isTransparentCaptionBarAppearance(runningTaskInfo)) {
                color = 0;
            } else {
                color = this.context.obtainStyledAttributes(null, new int[]{isDarkMode() ? !z ? android.R.color.sliding_tab_text_color_shadow : android.R.color.surface_header_light : !z ? android.R.color.surface_dark : android.R.color.secondary_text_nofocus}, 0, 0).getColor(0, 0);
            }
            view.setBackgroundColor(color);
            Context context = this.context;
            boolean isTransparentCaptionBarAppearance = TaskInfoKt.isTransparentCaptionBarAppearance(runningTaskInfo);
            int i2 = android.R.color.search_url_text;
            if ((!isTransparentCaptionBarAppearance || !TaskInfoKt.isLightCaptionBarAppearance(runningTaskInfo)) && ((TaskInfoKt.isTransparentCaptionBarAppearance(runningTaskInfo) && !TaskInfoKt.isLightCaptionBarAppearance(runningTaskInfo)) || isDarkMode())) {
                i2 = 17171178;
            }
            int color2 = context.getColor(i2);
            if (isDarkMode() && !z) {
                i = 140;
            } else if (isDarkMode() || z) {
                i = 255;
            }
            if (i != 255) {
                color2 = Color.argb(i, Color.red(color2), Color.green(color2), Color.blue(color2));
            }
            int alpha = Color.alpha(color2);
            this.closeWindowButton.setImageTintList(ColorStateList.valueOf(color2));
            this.maximizeWindowButton.setImageTintList(ColorStateList.valueOf(color2));
            this.minimizeWindowButton.setImageTintList(ColorStateList.valueOf(color2));
            this.expandMenuButton.setImageTintList(ColorStateList.valueOf(color2));
            this.appNameTextView.setVisibility(!TaskInfoKt.isTransparentCaptionBarAppearance(runningTaskInfo) ? 0 : 8);
            this.appNameTextView.setTextColor(color2);
            this.appIconImageView.setImageAlpha(alpha);
            this.maximizeWindowButton.setImageAlpha(alpha);
            this.minimizeWindowButton.setImageAlpha(alpha);
            this.closeWindowButton.setImageAlpha(alpha);
            this.expandMenuButton.setImageAlpha(alpha);
            TypedArray obtainStyledAttributes = this.context.obtainStyledAttributes(null, new int[]{android.R.attr.selectableItemBackground, android.R.attr.selectableItemBackgroundBorderless}, 0, 0);
            this.openMenuButton.setBackground(obtainStyledAttributes.getDrawable(0));
            this.maximizeWindowButton.setBackground(obtainStyledAttributes.getDrawable(1));
            this.closeWindowButton.setBackground(obtainStyledAttributes.getDrawable(1));
            this.minimizeWindowButton.setBackground(obtainStyledAttributes.getDrawable(1));
            obtainStyledAttributes.recycle();
            MaximizeButtonView maximizeButtonView = this.maximizeButtonView;
            boolean isDarkMode = isDarkMode();
            int i3 = MaximizeButtonView.$r8$clinit;
            maximizeButtonView.setAnimationTints(isDarkMode, null, null, null);
            this.minimizeWindowButton.setVisibility(!DesktopModeFlags.ENABLE_MINIMIZE_BUTTON.isTrue() ? 8 : 0);
            return;
        }
        Header header = new Header(TaskInfoKt.isTransparentCaptionBarAppearance(runningTaskInfo) ? Header.Type.CUSTOM : Header.Type.DEFAULT, this.decorThemeUtil.getAppTheme(runningTaskInfo), z, TaskInfoKt.isLightCaptionBarAppearance(runningTaskInfo));
        int[] iArr = WhenMappings.$EnumSwitchMapping$2;
        Header.Type type2 = header.type;
        int i4 = iArr[type2.ordinal()];
        Theme theme = header.appTheme;
        boolean z3 = header.isFocused;
        if (i4 == 1) {
            int i5 = WhenMappings.$EnumSwitchMapping$1[theme.ordinal()];
            if (i5 == 1) {
                type = type2;
                opaque = z3 ? new HeaderStyle.Background.Opaque(ColorKt.m467toArgb8_81llA(this.lightColors.secondaryContainer)) : new HeaderStyle.Background.Opaque(ColorKt.m467toArgb8_81llA(this.lightColors.surfaceContainerLow));
            } else {
                if (i5 != 2) {
                    throw new NoWhenBranchMatchedException();
                }
                if (z3) {
                    type = type2;
                    opaque = new HeaderStyle.Background.Opaque(ColorKt.m467toArgb8_81llA(this.darkColors.surfaceContainerHigh));
                } else {
                    type = type2;
                    opaque = new HeaderStyle.Background.Opaque(ColorKt.m467toArgb8_81llA(this.darkColors.surfaceDim));
                }
            }
        } else {
            if (i4 != 2) {
                throw new NoWhenBranchMatchedException();
            }
            opaque = HeaderStyle.Background.Transparent.INSTANCE;
            type = type2;
        }
        int i6 = iArr[type.ordinal()];
        if (i6 == 1) {
            int i7 = WhenMappings.$EnumSwitchMapping$1[theme.ordinal()];
            if (i7 == 1) {
                foreground = z3 ? new HeaderStyle.Foreground(ColorKt.m467toArgb8_81llA(this.lightColors.onSecondaryContainer), 255) : new HeaderStyle.Foreground(ColorKt.m467toArgb8_81llA(this.lightColors.onSecondaryContainer), 166);
            } else {
                if (i7 != 2) {
                    throw new NoWhenBranchMatchedException();
                }
                foreground = z3 ? new HeaderStyle.Foreground(ColorKt.m467toArgb8_81llA(this.darkColors.onSurface), 255) : new HeaderStyle.Foreground(ColorKt.m467toArgb8_81llA(this.darkColors.onSurface), 140);
            }
        } else {
            if (i6 != 2) {
                throw new NoWhenBranchMatchedException();
            }
            boolean z4 = header.isAppearanceCaptionLight;
            if (z4 && z3) {
                foreground = new HeaderStyle.Foreground(ColorKt.m467toArgb8_81llA(this.lightColors.onSecondaryContainer), 255);
            } else if (z4 && !z3) {
                foreground = new HeaderStyle.Foreground(ColorKt.m467toArgb8_81llA(this.lightColors.onSecondaryContainer), 166);
            } else if (!z4 && z3) {
                foreground = new HeaderStyle.Foreground(ColorKt.m467toArgb8_81llA(this.darkColors.onSurface), 255);
            } else {
                if (z4 || z3) {
                    throw new IllegalStateException(("No other combination expected header=" + header).toString());
                }
                foreground = new HeaderStyle.Foreground(ColorKt.m467toArgb8_81llA(this.darkColors.onSurface), 140);
            }
        }
        HeaderStyle headerStyle = new HeaderStyle(opaque, foreground);
        if (DesktopModeFlags.ENABLE_DESKTOP_APP_HANDLE_ANIMATION.isTrue()) {
            this.captionView.setVisibility(z2 ? 0 : 8);
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
        int i8 = foreground2.color;
        ColorStateList valueOf = ColorStateList.valueOf(i8);
        int i9 = foreground2.opacity;
        ColorStateList withAlpha = valueOf.withAlpha(i9);
        View view2 = this.openMenuButton;
        view2.setBackground(ButtonBackgroundDrawableUtilsKt.createBackgroundDrawable(i8, this.headerButtonsRippleRadius, this.appChipDrawableInsets));
        this.expandMenuButton.setImageTintList(withAlpha);
        TextView textView = this.appNameTextView;
        textView.setVisibility(type == Header.Type.DEFAULT ? 0 : 8);
        textView.setTextColor(withAlpha);
        this.appIconImageView.setImageAlpha(i9);
        view2.setDefaultFocusHighlightEnabled(false);
        ImageButton imageButton = this.minimizeWindowButton;
        imageButton.setImageTintList(withAlpha);
        imageButton.setBackground(ButtonBackgroundDrawableUtilsKt.createBackgroundDrawable(i8, this.headerButtonsRippleRadius, this.minimizeDrawableInsets));
        this.minimizeWindowButton.setVisibility(!DesktopModeFlags.ENABLE_MINIMIZE_BUTTON.isTrue() ? 8 : 0);
        MaximizeButtonView maximizeButtonView2 = this.maximizeButtonView;
        maximizeButtonView2.setAnimationTints(theme == Theme.DARK, withAlpha, Integer.valueOf(i8), ButtonBackgroundDrawableUtilsKt.createBackgroundDrawable(i8, this.headerButtonsRippleRadius, this.maximizeDrawableInsets));
        int i10 = ((DesktopModeFlags.ENABLE_FULLY_IMMERSIVE_IN_DESKTOP.isTrue() && headerData.inFullImmersiveState) || headerData.isTaskMaximized) ? R.drawable.decor_desktop_mode_immersive_or_maximize_exit_button_dark : R.drawable.decor_desktop_mode_maximize_button_dark;
        maximizeButtonView2.maximizeWindow.setImageResource(i10);
        if (i10 == R.drawable.decor_desktop_mode_immersive_or_maximize_exit_button_dark) {
            this.sizeToggleDirection = SizeToggleDirection.RESTORE;
            ViewCompat.replaceAccessibilityAction(this.maximizeWindowButton, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK, this.a11yAnnounceTextRestore, null);
        } else if (i10 == R.drawable.decor_desktop_mode_maximize_button_dark) {
            this.sizeToggleDirection = SizeToggleDirection.MAXIMIZE;
            ViewCompat.replaceAccessibilityAction(this.maximizeWindowButton, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK, this.a11yAnnounceTextMaximize, null);
        }
        updateMaximizeButtonContentDescription();
        ImageButton imageButton2 = this.closeWindowButton;
        imageButton2.setImageTintList(withAlpha);
        imageButton2.setBackground(ButtonBackgroundDrawableUtilsKt.createBackgroundDrawable(i8, this.headerButtonsRippleRadius, this.closeDrawableInsets));
        boolean z5 = headerData.enableMaximizeLongClick;
        if (!z5) {
            this.maximizeButtonView.cancelHoverAnimation();
        }
        this.maximizeButtonView.hoverDisabled = true ^ z5;
        this.maximizeWindowButton.setOnLongClickListener(z5 ? this.onLongClickListener : null);
    }

    @Override // java.lang.AutoCloseable
    public final void close() {
        this.maximizeWindowButton.cancelLongPress();
    }

    public final boolean isDarkMode() {
        return (this.context.getResources().getConfiguration().uiMode & 48) == 32;
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0022, code lost:
    
        if (r4 == null) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0025, code lost:
    
        r2 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0031, code lost:
    
        r1.setContentDescription(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0034, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x002f, code lost:
    
        if (r4 == null) goto L23;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateMaximizeButtonContentDescription() {
        /*
            r4 = this;
            java.lang.String r0 = r4.a11yTextRestore
            if (r0 == 0) goto L34
            java.lang.String r0 = r4.a11yTextMaximize
            if (r0 == 0) goto L34
            com.android.wm.shell.windowdecor.viewholder.AppHeaderViewHolder$SizeToggleDirection r0 = r4.sizeToggleDirection
            if (r0 == 0) goto L34
            android.widget.ImageButton r1 = r4.maximizeWindowButton
            r2 = 0
            if (r0 != 0) goto L12
            r0 = r2
        L12:
            int[] r3 = com.android.wm.shell.windowdecor.viewholder.AppHeaderViewHolder.WhenMappings.$EnumSwitchMapping$0
            int r0 = r0.ordinal()
            r0 = r3[r0]
            r3 = 1
            if (r0 == r3) goto L2d
            r3 = 2
            if (r0 != r3) goto L27
            java.lang.String r4 = r4.a11yTextRestore
            if (r4 != 0) goto L25
            goto L31
        L25:
            r2 = r4
            goto L31
        L27:
            kotlin.NoWhenBranchMatchedException r4 = new kotlin.NoWhenBranchMatchedException
            r4.<init>()
            throw r4
        L2d:
            java.lang.String r4 = r4.a11yTextMaximize
            if (r4 != 0) goto L25
        L31:
            r1.setContentDescription(r2)
        L34:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.windowdecor.viewholder.AppHeaderViewHolder.updateMaximizeButtonContentDescription():void");
    }

    @Override // com.android.wm.shell.windowdecor.viewholder.WindowDecorationViewHolder
    public final void onHandleMenuClosed() {
    }

    @Override // com.android.wm.shell.windowdecor.viewholder.WindowDecorationViewHolder
    public final void onHandleMenuOpened() {
    }
}
