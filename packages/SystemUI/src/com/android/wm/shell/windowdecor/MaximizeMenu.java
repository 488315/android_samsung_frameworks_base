package com.android.wm.shell.windowdecor;

import android.animation.AnimatorSet;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.util.StateSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import androidx.compose.material3.ColorScheme;
import androidx.compose.ui.graphics.ColorKt;
import com.android.systemui.R;
import com.android.wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.desktopmode.DesktopModeUiEventLogger;
import com.android.wm.shell.windowdecor.additionalviewcontainer.AdditionalViewHostViewContainer;
import com.android.wm.shell.windowdecor.common.DecorThemeUtil;
import com.android.wm.shell.windowdecor.common.ThemeUtilsKt;
import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.function.Supplier;
import kotlin.NoWhenBranchMatchedException;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class MaximizeMenu {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final float cornerRadius;
    public final Context decorWindowContext;
    public final DesktopModeUiEventLogger desktopModeUiEventLogger;
    public final DisplayController displayController;
    public SurfaceControl leash;
    public AdditionalViewHostViewContainer maximizeMenu;
    public MaximizeMenuView maximizeMenuView;
    public final int menuPadding;
    public Point menuPosition;
    public final Function2 positionSupplier;
    public final RootTaskDisplayAreaOrganizer rootTdaOrganizer;
    public final SyncTransactionQueue syncQueue;
    public final ActivityManager.RunningTaskInfo taskInfo;
    public final Supplier transactionSupplier;
    public SurfaceControlViewHost viewHost;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class MaximizeMenuView {
        public final View container;
        public final DecorThemeUtil decorThemeUtil;
        public final DesktopModeUiEventLogger desktopModeUiEventLogger;
        public final int fillRadius;
        public final Rect hoverTempRect;
        public final Rect immersiveFillPaddingRect;
        public final Button immersiveToggleButton;
        public final TextView immersiveToggleButtonText;
        public final View immersiveToggleContainer;
        public final Rect maximizeFillPaddingRect;
        public final Rect maximizeRestoreFillPaddingRect;
        public AnimatorSet menuAnimatorSet;
        public final int menuPadding;
        public DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 onImmersiveOrRestoreClickListener;
        public DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda8 onLeftSnapClickListener;
        public DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda8 onMaximizeClickListener;
        public DesktopModeWindowDecoration$$ExternalSyntheticLambda12 onMenuHoverListener;
        public DesktopModeWindowDecoration$$ExternalSyntheticLambda6 onOutsideTouchListener;
        public DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda8 onRightSnapClickListener;
        public final int outlineRadius;
        public final int outlineStroke;
        public final View overlay;
        public final ViewGroup rootView;
        public final Button sizeToggleButton;
        public final TextView sizeToggleButtonText;
        public final View sizeToggleContainer;
        public final SizeToggleDirection sizeToggleDirection;
        public final View snapButtonsLayout;
        public final View snapContainer;
        public final Button snapLeftButton;
        public final Button snapRightButton;
        public final TextView snapWindowText;
        public MenuStyle style;
        public ActivityManager.RunningTaskInfo taskInfo;

        public abstract class ImmersiveConfig {

            public final class Hidden extends ImmersiveConfig {
                public static final Hidden INSTANCE = new Hidden();

                private Hidden() {
                    super(null);
                }

                public final boolean equals(Object obj) {
                    return this == obj || (obj instanceof Hidden);
                }

                public final int hashCode() {
                    return -654586471;
                }

                public final String toString() {
                    return "Hidden";
                }
            }

            public final class Visible extends ImmersiveConfig {
                public final ImmersiveToggleDirection direction;

                public Visible(ImmersiveToggleDirection immersiveToggleDirection) {
                    super(null);
                    this.direction = immersiveToggleDirection;
                }

                public final boolean equals(Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    return (obj instanceof Visible) && this.direction == ((Visible) obj).direction;
                }

                public final int hashCode() {
                    return this.direction.hashCode();
                }

                public final String toString() {
                    return "Visible(direction=" + this.direction + ")";
                }
            }

            public /* synthetic */ ImmersiveConfig(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private ImmersiveConfig() {
            }
        }

        /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
        /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
        public final class ImmersiveToggleDirection {
            public static final /* synthetic */ ImmersiveToggleDirection[] $VALUES;
            public static final ImmersiveToggleDirection ENTER;
            public static final ImmersiveToggleDirection EXIT;

            static {
                ImmersiveToggleDirection immersiveToggleDirection = new ImmersiveToggleDirection("ENTER", 0);
                ENTER = immersiveToggleDirection;
                ImmersiveToggleDirection immersiveToggleDirection2 = new ImmersiveToggleDirection("EXIT", 1);
                EXIT = immersiveToggleDirection2;
                ImmersiveToggleDirection[] immersiveToggleDirectionArr = {immersiveToggleDirection, immersiveToggleDirection2};
                $VALUES = immersiveToggleDirectionArr;
                EnumEntriesKt.enumEntries(immersiveToggleDirectionArr);
            }

            private ImmersiveToggleDirection(String str, int i) {
            }

            public static ImmersiveToggleDirection valueOf(String str) {
                return (ImmersiveToggleDirection) Enum.valueOf(ImmersiveToggleDirection.class, str);
            }

            public static ImmersiveToggleDirection[] values() {
                return (ImmersiveToggleDirection[]) $VALUES.clone();
            }
        }

        public final class MenuStyle {
            public final int backgroundColor;
            public final ImmersiveOption immersiveOption;
            public final MaximizeOption maximizeOption;
            public final SnapOptions snapOptions;
            public final int textColor;

            public final class ImmersiveOption {
                public final StateListDrawable drawable;

                public ImmersiveOption(StateListDrawable stateListDrawable) {
                    this.drawable = stateListDrawable;
                }

                public final boolean equals(Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    return (obj instanceof ImmersiveOption) && Intrinsics.areEqual(this.drawable, ((ImmersiveOption) obj).drawable);
                }

                public final int hashCode() {
                    return this.drawable.hashCode();
                }

                public final String toString() {
                    return "ImmersiveOption(drawable=" + this.drawable + ")";
                }
            }

            public final class MaximizeOption {
                public final StateListDrawable drawable;

                public MaximizeOption(StateListDrawable stateListDrawable) {
                    this.drawable = stateListDrawable;
                }

                public final boolean equals(Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    return (obj instanceof MaximizeOption) && Intrinsics.areEqual(this.drawable, ((MaximizeOption) obj).drawable);
                }

                public final int hashCode() {
                    return this.drawable.hashCode();
                }

                public final String toString() {
                    return "MaximizeOption(drawable=" + this.drawable + ")";
                }
            }

            public final class SnapOptions {
                public final int activeBackgroundColor;
                public final int activeSnapSideColor;
                public final int activeStrokeColor;
                public final int inactiveBackgroundColor;
                public final int inactiveSnapSideColor;
                public final int inactiveStrokeColor;
                public final int semiActiveSnapSideColor;

                public SnapOptions(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
                    this.inactiveSnapSideColor = i;
                    this.semiActiveSnapSideColor = i2;
                    this.activeSnapSideColor = i3;
                    this.inactiveStrokeColor = i4;
                    this.activeStrokeColor = i5;
                    this.inactiveBackgroundColor = i6;
                    this.activeBackgroundColor = i7;
                }

                public final boolean equals(Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    if (!(obj instanceof SnapOptions)) {
                        return false;
                    }
                    SnapOptions snapOptions = (SnapOptions) obj;
                    return this.inactiveSnapSideColor == snapOptions.inactiveSnapSideColor && this.semiActiveSnapSideColor == snapOptions.semiActiveSnapSideColor && this.activeSnapSideColor == snapOptions.activeSnapSideColor && this.inactiveStrokeColor == snapOptions.inactiveStrokeColor && this.activeStrokeColor == snapOptions.activeStrokeColor && this.inactiveBackgroundColor == snapOptions.inactiveBackgroundColor && this.activeBackgroundColor == snapOptions.activeBackgroundColor;
                }

                public final int hashCode() {
                    return Integer.hashCode(this.activeBackgroundColor) + ReorderTile$$ExternalSyntheticOutline0.m(this.inactiveBackgroundColor, ReorderTile$$ExternalSyntheticOutline0.m(this.activeStrokeColor, ReorderTile$$ExternalSyntheticOutline0.m(this.inactiveStrokeColor, ReorderTile$$ExternalSyntheticOutline0.m(this.activeSnapSideColor, ReorderTile$$ExternalSyntheticOutline0.m(this.semiActiveSnapSideColor, Integer.hashCode(this.inactiveSnapSideColor) * 31, 31), 31), 31), 31), 31);
                }

                public final String toString() {
                    StringBuilder sb = new StringBuilder("SnapOptions(inactiveSnapSideColor=");
                    sb.append(this.inactiveSnapSideColor);
                    sb.append(", semiActiveSnapSideColor=");
                    sb.append(this.semiActiveSnapSideColor);
                    sb.append(", activeSnapSideColor=");
                    sb.append(this.activeSnapSideColor);
                    sb.append(", inactiveStrokeColor=");
                    sb.append(this.inactiveStrokeColor);
                    sb.append(", activeStrokeColor=");
                    sb.append(this.activeStrokeColor);
                    sb.append(", inactiveBackgroundColor=");
                    sb.append(this.inactiveBackgroundColor);
                    sb.append(", activeBackgroundColor=");
                    return ReorderTile$$ExternalSyntheticOutline0.m(this.activeBackgroundColor, ")", sb);
                }
            }

            public MenuStyle(int i, int i2, MaximizeOption maximizeOption, ImmersiveOption immersiveOption, SnapOptions snapOptions) {
                this.backgroundColor = i;
                this.textColor = i2;
                this.maximizeOption = maximizeOption;
                this.immersiveOption = immersiveOption;
                this.snapOptions = snapOptions;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof MenuStyle)) {
                    return false;
                }
                MenuStyle menuStyle = (MenuStyle) obj;
                return this.backgroundColor == menuStyle.backgroundColor && this.textColor == menuStyle.textColor && Intrinsics.areEqual(this.maximizeOption, menuStyle.maximizeOption) && Intrinsics.areEqual(this.immersiveOption, menuStyle.immersiveOption) && Intrinsics.areEqual(this.snapOptions, menuStyle.snapOptions);
            }

            public final int hashCode() {
                return this.snapOptions.hashCode() + ((this.immersiveOption.drawable.hashCode() + ((this.maximizeOption.drawable.hashCode() + ReorderTile$$ExternalSyntheticOutline0.m(this.textColor, Integer.hashCode(this.backgroundColor) * 31, 31)) * 31)) * 31);
            }

            public final String toString() {
                return "MenuStyle(backgroundColor=" + this.backgroundColor + ", textColor=" + this.textColor + ", maximizeOption=" + this.maximizeOption + ", immersiveOption=" + this.immersiveOption + ", snapOptions=" + this.snapOptions + ")";
            }
        }

        /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
        /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
        public final class SizeToggleDirection {
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

        /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
        /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
        public final class SnapToHalfSelection {
            public static final /* synthetic */ SnapToHalfSelection[] $VALUES;
            public static final SnapToHalfSelection LEFT;
            public static final SnapToHalfSelection NONE;
            public static final SnapToHalfSelection RIGHT;

            static {
                SnapToHalfSelection snapToHalfSelection = new SnapToHalfSelection(PeripheralBarcodeConstants.Symbology.Type.TYPE_NONE, 0);
                NONE = snapToHalfSelection;
                SnapToHalfSelection snapToHalfSelection2 = new SnapToHalfSelection("LEFT", 1);
                LEFT = snapToHalfSelection2;
                SnapToHalfSelection snapToHalfSelection3 = new SnapToHalfSelection("RIGHT", 2);
                RIGHT = snapToHalfSelection3;
                SnapToHalfSelection[] snapToHalfSelectionArr = {snapToHalfSelection, snapToHalfSelection2, snapToHalfSelection3};
                $VALUES = snapToHalfSelectionArr;
                EnumEntriesKt.enumEntries(snapToHalfSelectionArr);
            }

            private SnapToHalfSelection(String str, int i) {
            }

            public static SnapToHalfSelection valueOf(String str) {
                return (SnapToHalfSelection) Enum.valueOf(SnapToHalfSelection.class, str);
            }

            public static SnapToHalfSelection[] values() {
                return (SnapToHalfSelection[]) $VALUES.clone();
            }
        }

        public abstract /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;
            public static final /* synthetic */ int[] $EnumSwitchMapping$1;
            public static final /* synthetic */ int[] $EnumSwitchMapping$2;

            static {
                int[] iArr = new int[ImmersiveToggleDirection.values().length];
                try {
                    iArr[ImmersiveToggleDirection.ENTER.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[ImmersiveToggleDirection.EXIT.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                $EnumSwitchMapping$0 = iArr;
                int[] iArr2 = new int[SnapToHalfSelection.values().length];
                try {
                    iArr2[SnapToHalfSelection.NONE.ordinal()] = 1;
                } catch (NoSuchFieldError unused3) {
                }
                try {
                    iArr2[SnapToHalfSelection.LEFT.ordinal()] = 2;
                } catch (NoSuchFieldError unused4) {
                }
                try {
                    iArr2[SnapToHalfSelection.RIGHT.ordinal()] = 3;
                } catch (NoSuchFieldError unused5) {
                }
                $EnumSwitchMapping$1 = iArr2;
                int[] iArr3 = new int[SizeToggleDirection.values().length];
                try {
                    iArr3[SizeToggleDirection.MAXIMIZE.ordinal()] = 1;
                } catch (NoSuchFieldError unused6) {
                }
                try {
                    iArr3[SizeToggleDirection.RESTORE.ordinal()] = 2;
                } catch (NoSuchFieldError unused7) {
                }
                $EnumSwitchMapping$2 = iArr3;
            }
        }

        public MaximizeMenuView(final Context context, DesktopModeUiEventLogger desktopModeUiEventLogger, SizeToggleDirection sizeToggleDirection, ImmersiveConfig immersiveConfig, boolean z, int i) throws Resources.NotFoundException {
            TextView textView;
            int i2;
            this.desktopModeUiEventLogger = desktopModeUiEventLogger;
            this.sizeToggleDirection = sizeToggleDirection;
            this.menuPadding = i;
            ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.desktop_mode_window_decor_maximize_menu, (ViewGroup) null);
            this.rootView = viewGroup;
            this.container = viewGroup.requireViewById(R.id.container);
            View viewRequireViewById = viewGroup.requireViewById(R.id.maximize_menu_overlay);
            this.overlay = viewRequireViewById;
            View viewRequireViewById2 = viewGroup.requireViewById(R.id.maximize_menu_immersive_toggle_container);
            this.immersiveToggleContainer = viewRequireViewById2;
            TextView textView2 = (TextView) viewGroup.requireViewById(R.id.maximize_menu_immersive_toggle_button_text);
            this.immersiveToggleButtonText = textView2;
            Button button = (Button) viewGroup.requireViewById(R.id.maximize_menu_immersive_toggle_button);
            this.immersiveToggleButton = button;
            View viewRequireViewById3 = viewGroup.requireViewById(R.id.maximize_menu_size_toggle_container);
            this.sizeToggleContainer = viewRequireViewById3;
            TextView textView3 = (TextView) viewGroup.requireViewById(R.id.maximize_menu_size_toggle_button_text);
            this.sizeToggleButtonText = textView3;
            Button button2 = (Button) viewGroup.requireViewById(R.id.maximize_menu_size_toggle_button);
            this.sizeToggleButton = button2;
            View viewRequireViewById4 = viewGroup.requireViewById(R.id.maximize_menu_snap_container);
            this.snapContainer = viewRequireViewById4;
            this.snapWindowText = (TextView) viewGroup.requireViewById(R.id.maximize_menu_snap_window_text);
            this.snapButtonsLayout = viewGroup.requireViewById(R.id.maximize_menu_snap_menu_layout);
            boolean z2 = MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m(context) == 1;
            Button button3 = z2 ? (Button) viewGroup.requireViewById(R.id.maximize_menu_snap_left_button) : (Button) viewGroup.requireViewById(R.id.maximize_menu_snap_right_button);
            this.snapRightButton = button3;
            Button button4 = z2 ? (Button) viewGroup.requireViewById(R.id.maximize_menu_snap_right_button) : (Button) viewGroup.requireViewById(R.id.maximize_menu_snap_left_button);
            this.snapLeftButton = button4;
            this.decorThemeUtil = new DecorThemeUtil(context);
            this.outlineRadius = context.getResources().getDimensionPixelSize(R.dimen.desktop_mode_maximize_menu_buttons_outline_radius);
            this.outlineStroke = context.getResources().getDimensionPixelSize(R.dimen.desktop_mode_maximize_menu_buttons_outline_stroke);
            this.fillRadius = context.getResources().getDimensionPixelSize(R.dimen.desktop_mode_maximize_menu_buttons_fill_radius);
            int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.desktop_mode_maximize_menu_immersive_button_fill_padding);
            int dimensionPixelSize2 = context.getResources().getDimensionPixelSize(R.dimen.desktop_mode_maximize_menu_snap_and_maximize_buttons_fill_padding);
            int dimensionPixelSize3 = context.getResources().getDimensionPixelSize(R.dimen.desktop_mode_maximize_menu_restore_button_fill_vertical_padding);
            int dimensionPixelSize4 = context.getResources().getDimensionPixelSize(R.dimen.desktop_mode_maximize_menu_restore_button_fill_horizontal_padding);
            this.maximizeFillPaddingRect = new Rect(dimensionPixelSize2, dimensionPixelSize2, dimensionPixelSize2, dimensionPixelSize2);
            this.maximizeRestoreFillPaddingRect = new Rect(dimensionPixelSize4, dimensionPixelSize3, dimensionPixelSize4, dimensionPixelSize3);
            this.immersiveFillPaddingRect = new Rect(dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize);
            this.hoverTempRect = new Rect();
            viewRequireViewById.setOnHoverListener(new View.OnHoverListener() { // from class: com.android.wm.shell.windowdecor.MaximizeMenu.MaximizeMenuView.1
                @Override // android.view.View.OnHoverListener
                public final boolean onHover(View view, MotionEvent motionEvent) {
                    DesktopModeWindowDecoration$$ExternalSyntheticLambda12 desktopModeWindowDecoration$$ExternalSyntheticLambda12;
                    int action = motionEvent.getAction();
                    if (action == 9) {
                        DesktopModeWindowDecoration$$ExternalSyntheticLambda12 desktopModeWindowDecoration$$ExternalSyntheticLambda122 = MaximizeMenuView.this.onMenuHoverListener;
                        if (desktopModeWindowDecoration$$ExternalSyntheticLambda122 != null) {
                            desktopModeWindowDecoration$$ExternalSyntheticLambda122.mo781invoke(Boolean.TRUE);
                        }
                    } else if (action == 10 && (desktopModeWindowDecoration$$ExternalSyntheticLambda12 = MaximizeMenuView.this.onMenuHoverListener) != null) {
                        desktopModeWindowDecoration$$ExternalSyntheticLambda12.mo781invoke(Boolean.FALSE);
                    }
                    MaximizeMenuView maximizeMenuView = MaximizeMenuView.this;
                    Rect rect = maximizeMenuView.hoverTempRect;
                    maximizeMenuView.snapButtonsLayout.getDrawingRect(rect);
                    maximizeMenuView.rootView.offsetDescendantRectToMyCoords(maximizeMenuView.snapButtonsLayout, rect);
                    if (motionEvent.getAction() != 9 && motionEvent.getAction() != 7) {
                        return false;
                    }
                    if (!rect.contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
                        MaximizeMenuView.this.updateSplitSnapSelection(SnapToHalfSelection.NONE);
                        return false;
                    }
                    if (motionEvent.getX() < rect.centerX()) {
                        MaximizeMenuView.this.updateSplitSnapSelection(SnapToHalfSelection.LEFT);
                        return false;
                    }
                    MaximizeMenuView.this.updateSplitSnapSelection(SnapToHalfSelection.RIGHT);
                    return false;
                }
            });
            viewRequireViewById2.setVisibility(immersiveConfig instanceof ImmersiveConfig.Hidden ? 8 : 0);
            viewRequireViewById3.setVisibility(0);
            viewRequireViewById4.setVisibility(z ? 0 : 8);
            button.setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.windowdecor.MaximizeMenu.MaximizeMenuView.2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) throws IllegalAccessException, Resources.NotFoundException, IllegalArgumentException, InvocationTargetException {
                    DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 desktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 = MaximizeMenuView.this.onImmersiveOrRestoreClickListener;
                    if (desktopModeWindowDecorViewModel$$ExternalSyntheticLambda9 != null) {
                        desktopModeWindowDecorViewModel$$ExternalSyntheticLambda9.invoke();
                    }
                }
            });
            button2.setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.windowdecor.MaximizeMenu.MaximizeMenuView.3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda8 desktopModeWindowDecorViewModel$$ExternalSyntheticLambda8 = MaximizeMenuView.this.onMaximizeClickListener;
                    if (desktopModeWindowDecorViewModel$$ExternalSyntheticLambda8 != null) {
                        desktopModeWindowDecorViewModel$$ExternalSyntheticLambda8.invoke();
                    }
                }
            });
            button3.setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.windowdecor.MaximizeMenu.MaximizeMenuView.4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda8 desktopModeWindowDecorViewModel$$ExternalSyntheticLambda8 = MaximizeMenuView.this.onRightSnapClickListener;
                    if (desktopModeWindowDecorViewModel$$ExternalSyntheticLambda8 != null) {
                        desktopModeWindowDecorViewModel$$ExternalSyntheticLambda8.invoke();
                    }
                }
            });
            button4.setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.windowdecor.MaximizeMenu.MaximizeMenuView.5
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda8 desktopModeWindowDecorViewModel$$ExternalSyntheticLambda8 = MaximizeMenuView.this.onLeftSnapClickListener;
                    if (desktopModeWindowDecorViewModel$$ExternalSyntheticLambda8 != null) {
                        desktopModeWindowDecorViewModel$$ExternalSyntheticLambda8.invoke();
                    }
                }
            });
            viewGroup.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.wm.shell.windowdecor.MaximizeMenu.MaximizeMenuView.6
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getActionMasked() != 4) {
                        return true;
                    }
                    DesktopModeWindowDecoration$$ExternalSyntheticLambda6 desktopModeWindowDecoration$$ExternalSyntheticLambda6 = MaximizeMenuView.this.onOutsideTouchListener;
                    if (desktopModeWindowDecoration$$ExternalSyntheticLambda6 == null) {
                        return false;
                    }
                    desktopModeWindowDecoration$$ExternalSyntheticLambda6.invoke();
                    return false;
                }
            });
            button2.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.wm.shell.windowdecor.MaximizeMenu.MaximizeMenuView.7
                @Override // android.view.View.AccessibilityDelegate
                public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                    super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                    accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK.getId(), context.getString(R.string.maximize_menu_talkback_action_maximize_restore_text)));
                    view.setClickable(true);
                }

                @Override // android.view.View.AccessibilityDelegate
                public final boolean performAccessibilityAction(View view, int i3, Bundle bundle) {
                    if (i3 == AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK.getId()) {
                        MaximizeMenuView maximizeMenuView = this;
                        DesktopModeUiEventLogger desktopModeUiEventLogger2 = maximizeMenuView.desktopModeUiEventLogger;
                        ActivityManager.RunningTaskInfo runningTaskInfo = maximizeMenuView.taskInfo;
                        if (runningTaskInfo == null) {
                            runningTaskInfo = null;
                        }
                        desktopModeUiEventLogger2.log(runningTaskInfo, DesktopModeUiEventLogger.DesktopUiEventEnum.A11Y_MAXIMIZE_MENU_MAXIMIZE);
                        DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda8 desktopModeWindowDecorViewModel$$ExternalSyntheticLambda8 = this.onMaximizeClickListener;
                        if (desktopModeWindowDecorViewModel$$ExternalSyntheticLambda8 != null) {
                            desktopModeWindowDecorViewModel$$ExternalSyntheticLambda8.invoke();
                        }
                    }
                    return super.performAccessibilityAction(view, i3, bundle);
                }
            });
            button4.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.wm.shell.windowdecor.MaximizeMenu.MaximizeMenuView.8
                @Override // android.view.View.AccessibilityDelegate
                public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                    super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                    accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK.getId(), context.getString(R.string.maximize_menu_talkback_action_snap_left_text)));
                    view.setClickable(true);
                }

                @Override // android.view.View.AccessibilityDelegate
                public final boolean performAccessibilityAction(View view, int i3, Bundle bundle) {
                    if (i3 == AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK.getId()) {
                        MaximizeMenuView maximizeMenuView = this;
                        DesktopModeUiEventLogger desktopModeUiEventLogger2 = maximizeMenuView.desktopModeUiEventLogger;
                        ActivityManager.RunningTaskInfo runningTaskInfo = maximizeMenuView.taskInfo;
                        if (runningTaskInfo == null) {
                            runningTaskInfo = null;
                        }
                        desktopModeUiEventLogger2.log(runningTaskInfo, DesktopModeUiEventLogger.DesktopUiEventEnum.A11Y_MAXIMIZE_MENU_RESIZE_LEFT);
                        DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda8 desktopModeWindowDecorViewModel$$ExternalSyntheticLambda8 = this.onLeftSnapClickListener;
                        if (desktopModeWindowDecorViewModel$$ExternalSyntheticLambda8 != null) {
                            desktopModeWindowDecorViewModel$$ExternalSyntheticLambda8.invoke();
                        }
                    }
                    return super.performAccessibilityAction(view, i3, bundle);
                }
            });
            button3.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.wm.shell.windowdecor.MaximizeMenu.MaximizeMenuView.9
                @Override // android.view.View.AccessibilityDelegate
                public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                    super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                    accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK.getId(), context.getString(R.string.maximize_menu_talkback_action_snap_right_text)));
                    view.setClickable(true);
                }

                @Override // android.view.View.AccessibilityDelegate
                public final boolean performAccessibilityAction(View view, int i3, Bundle bundle) {
                    if (i3 == AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK.getId()) {
                        MaximizeMenuView maximizeMenuView = this;
                        DesktopModeUiEventLogger desktopModeUiEventLogger2 = maximizeMenuView.desktopModeUiEventLogger;
                        ActivityManager.RunningTaskInfo runningTaskInfo = maximizeMenuView.taskInfo;
                        if (runningTaskInfo == null) {
                            runningTaskInfo = null;
                        }
                        desktopModeUiEventLogger2.log(runningTaskInfo, DesktopModeUiEventLogger.DesktopUiEventEnum.A11Y_MAXIMIZE_MENU_RESIZE_RIGHT);
                        DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda8 desktopModeWindowDecorViewModel$$ExternalSyntheticLambda8 = this.onRightSnapClickListener;
                        if (desktopModeWindowDecorViewModel$$ExternalSyntheticLambda8 != null) {
                            desktopModeWindowDecorViewModel$$ExternalSyntheticLambda8.invoke();
                        }
                    }
                    return super.performAccessibilityAction(view, i3, bundle);
                }
            });
            CharSequence text = context.getResources().getText(sizeToggleDirection == SizeToggleDirection.RESTORE ? R.string.desktop_mode_maximize_menu_restore_button_text : R.string.desktop_mode_maximize_menu_maximize_button_text);
            button2.setContentDescription(text);
            textView3.setText(text);
            if (immersiveConfig instanceof ImmersiveConfig.Visible) {
                int i3 = WhenMappings.$EnumSwitchMapping$0[((ImmersiveConfig.Visible) immersiveConfig).direction.ordinal()];
                if (i3 == 1) {
                    i2 = R.string.desktop_mode_maximize_menu_immersive_button_text;
                } else {
                    if (i3 != 2) {
                        throw new NoWhenBranchMatchedException();
                    }
                    i2 = R.string.desktop_mode_maximize_menu_immersive_restore_button_text;
                }
                CharSequence text2 = context.getResources().getText(i2);
                button.setContentDescription(text2);
                textView = textView2;
                textView.setText(text2);
            } else {
                textView = textView2;
            }
            button2.setLayerType(1, null);
            textView3.setLayerType(1, null);
            button.setLayerType(1, null);
            textView.setLayerType(1, null);
        }

        public final void activateSnapOption(boolean z) {
            View view = this.snapButtonsLayout;
            view.setBackgroundResource(R.drawable.desktop_mode_maximize_menu_layout_background_on_hover);
            GradientDrawable gradientDrawable = (GradientDrawable) view.getBackground();
            MenuStyle menuStyle = this.style;
            if (menuStyle == null) {
                menuStyle = null;
            }
            gradientDrawable.setColor(menuStyle.snapOptions.activeBackgroundColor);
            MenuStyle menuStyle2 = this.style;
            if (menuStyle2 == null) {
                menuStyle2 = null;
            }
            gradientDrawable.setStroke(this.outlineStroke, menuStyle2.snapOptions.activeStrokeColor);
            if (z) {
                Drawable background = this.snapLeftButton.getBackground();
                MenuStyle menuStyle3 = this.style;
                if (menuStyle3 == null) {
                    menuStyle3 = null;
                }
                background.setTint(menuStyle3.snapOptions.activeSnapSideColor);
                Drawable background2 = this.snapRightButton.getBackground();
                MenuStyle menuStyle4 = this.style;
                background2.setTint((menuStyle4 != null ? menuStyle4 : null).snapOptions.semiActiveSnapSideColor);
                return;
            }
            Drawable background3 = this.snapRightButton.getBackground();
            MenuStyle menuStyle5 = this.style;
            if (menuStyle5 == null) {
                menuStyle5 = null;
            }
            background3.setTint(menuStyle5.snapOptions.activeSnapSideColor);
            Drawable background4 = this.snapLeftButton.getBackground();
            MenuStyle menuStyle6 = this.style;
            background4.setTint((menuStyle6 != null ? menuStyle6 : null).snapOptions.semiActiveSnapSideColor);
        }

        public final LayerDrawable createMaximizeOrImmersiveButtonDrawable(int i, int i2, int i3, Integer num, Rect rect) {
            int i4;
            ArrayList arrayList = new ArrayList();
            ShapeDrawable shapeDrawable = new ShapeDrawable();
            float[] fArr = new float[8];
            int i5 = 0;
            while (true) {
                i4 = this.outlineRadius;
                if (i5 >= 8) {
                    break;
                }
                fArr[i5] = i4;
                i5++;
            }
            shapeDrawable.setShape(new RoundRectShape(fArr, null, null));
            shapeDrawable.getPaint().setColor(i);
            shapeDrawable.getPaint().setStyle(Paint.Style.FILL);
            arrayList.add(shapeDrawable);
            if (num != null) {
                int iIntValue = num.intValue();
                ShapeDrawable shapeDrawable2 = new ShapeDrawable();
                float[] fArr2 = new float[8];
                for (int i6 = 0; i6 < 8; i6++) {
                    fArr2[i6] = i4;
                }
                shapeDrawable2.setShape(new RoundRectShape(fArr2, null, null));
                shapeDrawable2.getPaint().setColor(iIntValue);
                shapeDrawable2.getPaint().setStyle(Paint.Style.FILL);
                arrayList.add(shapeDrawable2);
            }
            ShapeDrawable shapeDrawable3 = new ShapeDrawable();
            float[] fArr3 = new float[8];
            for (int i7 = 0; i7 < 8; i7++) {
                fArr3[i7] = i4;
            }
            shapeDrawable3.setShape(new RoundRectShape(fArr3, null, null));
            shapeDrawable3.getPaint().setColor(i3);
            shapeDrawable3.getPaint().setStyle(Paint.Style.FILL);
            arrayList.add(shapeDrawable3);
            ShapeDrawable shapeDrawable4 = new ShapeDrawable();
            float[] fArr4 = new float[8];
            for (int i8 = 0; i8 < 8; i8++) {
                fArr4[i8] = this.fillRadius;
            }
            shapeDrawable4.setShape(new RoundRectShape(fArr4, null, null));
            shapeDrawable4.getPaint().setColor(i2);
            shapeDrawable4.getPaint().setStyle(Paint.Style.FILL);
            arrayList.add(shapeDrawable4);
            LayerDrawable layerDrawable = new LayerDrawable((Drawable[]) arrayList.toArray(new Drawable[0]));
            int numberOfLayers = layerDrawable.getNumberOfLayers();
            if (numberOfLayers == 3) {
                int i9 = this.outlineStroke;
                layerDrawable.setLayerInset(1, i9, i9, i9, i9);
                layerDrawable.setLayerInset(2, rect.left, rect.top, rect.right, rect.bottom);
                return layerDrawable;
            }
            if (numberOfLayers != 4) {
                throw new IllegalStateException(("Unexpected number of layers: " + layerDrawable.getNumberOfLayers()).toString());
            }
            int[] iArr = {1, 2};
            for (int i10 = 0; i10 < 2; i10++) {
                int i11 = iArr[i10];
                int i12 = this.outlineStroke;
                layerDrawable.setLayerInset(i11, i12, i12, i12, i12);
            }
            layerDrawable.setLayerInset(3, rect.left, rect.top, rect.right, rect.bottom);
            return layerDrawable;
        }

        public final StateListDrawable createMaximizeOrImmersiveDrawable(int i, ColorScheme colorScheme, Rect rect) {
            long j = colorScheme.primary;
            int iM469toArgb8_81llA = ColorKt.m469toArgb8_81llA(j);
            LayerDrawable layerDrawableCreateMaximizeOrImmersiveButtonDrawable = createMaximizeOrImmersiveButtonDrawable(iM469toArgb8_81llA, iM469toArgb8_81llA, ThemeUtilsKt.withAlpha(ColorKt.m469toArgb8_81llA(j), 31), Integer.valueOf(i), rect);
            StateListDrawable stateListDrawable = new StateListDrawable();
            stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, layerDrawableCreateMaximizeOrImmersiveButtonDrawable);
            stateListDrawable.addState(new int[]{android.R.attr.state_focused}, layerDrawableCreateMaximizeOrImmersiveButtonDrawable);
            stateListDrawable.addState(new int[]{android.R.attr.state_selected}, layerDrawableCreateMaximizeOrImmersiveButtonDrawable);
            stateListDrawable.addState(new int[]{android.R.attr.state_hovered}, layerDrawableCreateMaximizeOrImmersiveButtonDrawable);
            int[] iArr = StateSet.WILD_CARD;
            long j2 = colorScheme.outlineVariant;
            stateListDrawable.addState(iArr, createMaximizeOrImmersiveButtonDrawable(ThemeUtilsKt.withAlpha(ColorKt.m469toArgb8_81llA(j2), 153), ColorKt.m469toArgb8_81llA(j2), ColorKt.m469toArgb8_81llA(colorScheme.surfaceContainerLow), null, rect));
            return stateListDrawable;
        }

        public final int measureHeight() {
            this.rootView.measure(0, 0);
            return this.rootView.getMeasuredHeight();
        }

        public final void updateSplitSnapSelection(SnapToHalfSelection snapToHalfSelection) {
            int i = WhenMappings.$EnumSwitchMapping$1[snapToHalfSelection.ordinal()];
            if (i != 1) {
                if (i == 2) {
                    activateSnapOption(true);
                    return;
                } else {
                    if (i != 3) {
                        throw new NoWhenBranchMatchedException();
                    }
                    activateSnapOption(false);
                    return;
                }
            }
            int[][] iArr = {new int[]{android.R.attr.state_pressed}, new int[]{android.R.attr.state_focused}, new int[]{android.R.attr.state_selected}, new int[0]};
            MenuStyle menuStyle = this.style;
            int i2 = (menuStyle == null ? null : menuStyle).snapOptions.activeSnapSideColor;
            int i3 = (menuStyle == null ? null : menuStyle).snapOptions.activeSnapSideColor;
            int i4 = (menuStyle == null ? null : menuStyle).snapOptions.activeSnapSideColor;
            if (menuStyle == null) {
                menuStyle = null;
            }
            ColorStateList colorStateList = new ColorStateList(iArr, new int[]{i2, i3, i4, menuStyle.snapOptions.inactiveSnapSideColor});
            Drawable background = this.snapLeftButton.getBackground();
            if (background != null) {
                background.setTintList(colorStateList);
            }
            Drawable background2 = this.snapRightButton.getBackground();
            if (background2 != null) {
                background2.setTintList(colorStateList);
            }
            View view = this.snapButtonsLayout;
            view.setBackgroundResource(R.drawable.desktop_mode_maximize_menu_layout_background);
            GradientDrawable gradientDrawable = (GradientDrawable) view.getBackground();
            MenuStyle menuStyle2 = this.style;
            if (menuStyle2 == null) {
                menuStyle2 = null;
            }
            gradientDrawable.setColor(menuStyle2.snapOptions.inactiveBackgroundColor);
            MenuStyle menuStyle3 = this.style;
            gradientDrawable.setStroke(this.outlineStroke, (menuStyle3 != null ? menuStyle3 : null).snapOptions.inactiveStrokeColor);
        }
    }

    static {
        new Companion(null);
    }

    public MaximizeMenu(SyncTransactionQueue syncTransactionQueue, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer, DisplayController displayController, ActivityManager.RunningTaskInfo runningTaskInfo, Context context, Function2 function2, Supplier<SurfaceControl.Transaction> supplier, DesktopModeUiEventLogger desktopModeUiEventLogger) {
        this.syncQueue = syncTransactionQueue;
        this.rootTdaOrganizer = rootTaskDisplayAreaOrganizer;
        this.displayController = displayController;
        this.taskInfo = runningTaskInfo;
        this.decorWindowContext = context;
        this.positionSupplier = function2;
        this.transactionSupplier = supplier;
        this.desktopModeUiEventLogger = desktopModeUiEventLogger;
        this.cornerRadius = context.getResources().getDimensionPixelSize(R.dimen.desktop_mode_maximize_menu_corner_radius);
        this.menuPadding = context.getResources().getDimensionPixelSize(R.dimen.desktop_mode_menu_padding);
    }

    public /* synthetic */ MaximizeMenu(SyncTransactionQueue syncTransactionQueue, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer, DisplayController displayController, ActivityManager.RunningTaskInfo runningTaskInfo, Context context, Function2 function2, Supplier supplier, DesktopModeUiEventLogger desktopModeUiEventLogger, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(syncTransactionQueue, rootTaskDisplayAreaOrganizer, displayController, runningTaskInfo, context, function2, (i & 64) != 0 ? new Supplier() { // from class: com.android.wm.shell.windowdecor.MaximizeMenu.1
            @Override // java.util.function.Supplier
            public final Object get() {
                return new SurfaceControl.Transaction();
            }
        } : supplier, desktopModeUiEventLogger);
    }
}
