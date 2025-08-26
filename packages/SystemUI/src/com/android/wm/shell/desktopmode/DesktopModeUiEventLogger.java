package com.android.wm.shell.desktopmode;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import com.android.internal.logging.InstanceIdSequence;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.protolog.ProtoLog;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.sec.ims.volte2.data.VolteConstants;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.SpreadBuilder;

/* loaded from: classes3.dex */
public final class DesktopModeUiEventLogger {
    public final PackageManager packageManager;
    public final UiEventLogger uiEventLogger;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class DesktopUiEventEnum implements UiEventLogger.UiEventEnum {
        public static final /* synthetic */ DesktopUiEventEnum[] $VALUES;
        public static final DesktopUiEventEnum A11Y_ACTION_MAXIMIZE_RESTORE;
        public static final DesktopUiEventEnum A11Y_ACTION_RESIZE_LEFT;
        public static final DesktopUiEventEnum A11Y_ACTION_RESIZE_RIGHT;
        public static final DesktopUiEventEnum A11Y_APP_HANDLE_MENU_DESKTOP_VIEW;
        public static final DesktopUiEventEnum A11Y_APP_HANDLE_MENU_FULLSCREEN;
        public static final DesktopUiEventEnum A11Y_APP_HANDLE_MENU_OPENED;
        public static final DesktopUiEventEnum A11Y_APP_HANDLE_MENU_SPLIT_SCREEN;
        public static final DesktopUiEventEnum A11Y_APP_WINDOW_CLOSE_BUTTON;
        public static final DesktopUiEventEnum A11Y_APP_WINDOW_MAXIMIZE_RESTORE_BUTTON;
        public static final DesktopUiEventEnum A11Y_APP_WINDOW_MINIMIZE_BUTTON;
        public static final DesktopUiEventEnum A11Y_MAXIMIZE_MENU_MAXIMIZE;
        public static final DesktopUiEventEnum A11Y_MAXIMIZE_MENU_RESIZE_LEFT;
        public static final DesktopUiEventEnum A11Y_MAXIMIZE_MENU_RESIZE_RIGHT;
        public static final DesktopUiEventEnum APP_HANDLE_EDUCATION_TOOLTIP_CLICKED = null;
        public static final DesktopUiEventEnum APP_HANDLE_EDUCATION_TOOLTIP_DISMISSED = null;
        public static final DesktopUiEventEnum APP_HANDLE_EDUCATION_TOOLTIP_SHOWN = null;
        public static final DesktopUiEventEnum DESKTOP_WINDOW_APP_HANDLE_DRAG_TO_DESKTOP_MODE;
        public static final DesktopUiEventEnum DESKTOP_WINDOW_APP_HANDLE_DRAG_TO_FULL_SCREEN;
        public static final DesktopUiEventEnum DESKTOP_WINDOW_APP_HANDLE_DRAG_TO_SPLIT_SCREEN;
        public static final DesktopUiEventEnum DESKTOP_WINDOW_APP_HANDLE_MENU_TAP_TO_DESKTOP_MODE;
        public static final DesktopUiEventEnum DESKTOP_WINDOW_APP_HANDLE_MENU_TAP_TO_FULL_SCREEN;
        public static final DesktopUiEventEnum DESKTOP_WINDOW_APP_HANDLE_MENU_TAP_TO_SPLIT_SCREEN;
        public static final DesktopUiEventEnum DESKTOP_WINDOW_APP_HANDLE_TAP;
        public static final DesktopUiEventEnum DESKTOP_WINDOW_APP_HEADER_DRAG_TO_FULL_SCREEN;
        public static final DesktopUiEventEnum DESKTOP_WINDOW_APP_HEADER_DRAG_TO_TILE_TO_LEFT;
        public static final DesktopUiEventEnum DESKTOP_WINDOW_APP_HEADER_DRAG_TO_TILE_TO_RIGHT;
        public static final DesktopUiEventEnum DESKTOP_WINDOW_HEADER_DOUBLE_TAP_TO_MAXIMIZE;
        public static final DesktopUiEventEnum DESKTOP_WINDOW_HEADER_DOUBLE_TAP_TO_RESTORE;
        public static final DesktopUiEventEnum DESKTOP_WINDOW_HEADER_TAP_TO_REFOCUS;
        public static final DesktopUiEventEnum DESKTOP_WINDOW_MAXIMIZE_BUTTON_MENU_TAP_TO_IMMERSIVE;
        public static final DesktopUiEventEnum DESKTOP_WINDOW_MAXIMIZE_BUTTON_MENU_TAP_TO_MAXIMIZE;
        public static final DesktopUiEventEnum DESKTOP_WINDOW_MAXIMIZE_BUTTON_MENU_TAP_TO_RESTORE;
        public static final DesktopUiEventEnum DESKTOP_WINDOW_MAXIMIZE_BUTTON_MENU_TAP_TO_TILE_TO_LEFT;
        public static final DesktopUiEventEnum DESKTOP_WINDOW_MAXIMIZE_BUTTON_MENU_TAP_TO_TILE_TO_RIGHT;
        public static final DesktopUiEventEnum DESKTOP_WINDOW_MAXIMIZE_BUTTON_REVEAL_MENU;
        public static final DesktopUiEventEnum DESKTOP_WINDOW_MAXIMIZE_BUTTON_TAP;
        public static final DesktopUiEventEnum DESKTOP_WINDOW_MOVE_BY_HEADER_DRAG;
        public static final DesktopUiEventEnum DESKTOP_WINDOW_MULTI_INSTANCE_MANAGE_WINDOWS_ICON_CLICK;
        public static final DesktopUiEventEnum DESKTOP_WINDOW_MULTI_INSTANCE_NEW_WINDOW_CLICK;
        public static final DesktopUiEventEnum DESKTOP_WINDOW_RESTORE_BUTTON_TAP;
        public static final DesktopUiEventEnum ENTER_DESKTOP_MODE_EDUCATION_TOOLTIP_CLICKED = null;
        public static final DesktopUiEventEnum ENTER_DESKTOP_MODE_EDUCATION_TOOLTIP_DISMISSED = null;
        public static final DesktopUiEventEnum ENTER_DESKTOP_MODE_EDUCATION_TOOLTIP_SHOWN = null;
        public static final DesktopUiEventEnum EXIT_DESKTOP_MODE_EDUCATION_TOOLTIP_CLICKED = null;
        public static final DesktopUiEventEnum EXIT_DESKTOP_MODE_EDUCATION_TOOLTIP_DISMISSED = null;
        public static final DesktopUiEventEnum EXIT_DESKTOP_MODE_EDUCATION_TOOLTIP_SHOWN = null;
        private final int mId;

        static {
            DesktopUiEventEnum desktopUiEventEnum = new DesktopUiEventEnum("DESKTOP_WINDOW_EDGE_DRAG_RESIZE", 0, 1721);
            DesktopUiEventEnum desktopUiEventEnum2 = new DesktopUiEventEnum("DESKTOP_WINDOW_CORNER_DRAG_RESIZE", 1, 1722);
            DesktopUiEventEnum desktopUiEventEnum3 = new DesktopUiEventEnum("DESKTOP_WINDOW_MAXIMIZE_BUTTON_TAP", 2, 1723);
            DESKTOP_WINDOW_MAXIMIZE_BUTTON_TAP = desktopUiEventEnum3;
            DesktopUiEventEnum desktopUiEventEnum4 = new DesktopUiEventEnum("DESKTOP_WINDOW_RESTORE_BUTTON_TAP", 3, 2017);
            DESKTOP_WINDOW_RESTORE_BUTTON_TAP = desktopUiEventEnum4;
            DesktopUiEventEnum desktopUiEventEnum5 = new DesktopUiEventEnum("DESKTOP_WINDOW_HEADER_DOUBLE_TAP_TO_MAXIMIZE", 4, 1724);
            DESKTOP_WINDOW_HEADER_DOUBLE_TAP_TO_MAXIMIZE = desktopUiEventEnum5;
            DesktopUiEventEnum desktopUiEventEnum6 = new DesktopUiEventEnum("DESKTOP_WINDOW_HEADER_DOUBLE_TAP_TO_RESTORE", 5, 2018);
            DESKTOP_WINDOW_HEADER_DOUBLE_TAP_TO_RESTORE = desktopUiEventEnum6;
            DesktopUiEventEnum desktopUiEventEnum7 = new DesktopUiEventEnum("DESKTOP_WINDOW_APP_HANDLE_TAP", 6, 1998);
            DESKTOP_WINDOW_APP_HANDLE_TAP = desktopUiEventEnum7;
            DesktopUiEventEnum desktopUiEventEnum8 = new DesktopUiEventEnum("DESKTOP_WINDOW_APP_HANDLE_MENU_TAP_TO_DESKTOP_MODE", 7, 1999);
            DESKTOP_WINDOW_APP_HANDLE_MENU_TAP_TO_DESKTOP_MODE = desktopUiEventEnum8;
            DesktopUiEventEnum desktopUiEventEnum9 = new DesktopUiEventEnum("DESKTOP_WINDOW_APP_HANDLE_MENU_TAP_TO_SPLIT_SCREEN", 8, 2000);
            DESKTOP_WINDOW_APP_HANDLE_MENU_TAP_TO_SPLIT_SCREEN = desktopUiEventEnum9;
            DesktopUiEventEnum desktopUiEventEnum10 = new DesktopUiEventEnum("DESKTOP_WINDOW_APP_HANDLE_MENU_TAP_TO_FULL_SCREEN", 9, VolteConstants.ErrorCode.CALL_FORBIDDEN);
            DESKTOP_WINDOW_APP_HANDLE_MENU_TAP_TO_FULL_SCREEN = desktopUiEventEnum10;
            DesktopUiEventEnum desktopUiEventEnum11 = new DesktopUiEventEnum("DESKTOP_WINDOW_APP_HANDLE_DRAG_TO_DESKTOP_MODE", 10, VolteConstants.ErrorCode.MAKECALL_REG_FAILURE_TIMER_F);
            DESKTOP_WINDOW_APP_HANDLE_DRAG_TO_DESKTOP_MODE = desktopUiEventEnum11;
            DesktopUiEventEnum desktopUiEventEnum12 = new DesktopUiEventEnum("DESKTOP_WINDOW_APP_HANDLE_DRAG_TO_SPLIT_SCREEN", 11, VolteConstants.ErrorCode.MAKECALL_REG_FAILURE_REG_403);
            DESKTOP_WINDOW_APP_HANDLE_DRAG_TO_SPLIT_SCREEN = desktopUiEventEnum12;
            DesktopUiEventEnum desktopUiEventEnum13 = new DesktopUiEventEnum("DESKTOP_WINDOW_APP_HANDLE_DRAG_TO_FULL_SCREEN", 12, VolteConstants.ErrorCode.MAKECALL_REG_FAILURE_REG_423);
            DESKTOP_WINDOW_APP_HANDLE_DRAG_TO_FULL_SCREEN = desktopUiEventEnum13;
            DesktopUiEventEnum desktopUiEventEnum14 = new DesktopUiEventEnum("DESKTOP_WINDOW_APP_HEADER_DRAG_TO_FULL_SCREEN", 13, VolteConstants.ErrorCode.MAKECALL_REG_FAILURE_GENERAL);
            DESKTOP_WINDOW_APP_HEADER_DRAG_TO_FULL_SCREEN = desktopUiEventEnum14;
            DesktopUiEventEnum desktopUiEventEnum15 = new DesktopUiEventEnum("DESKTOP_WINDOW_APP_HEADER_DRAG_TO_TILE_TO_LEFT", 14, 2006);
            DESKTOP_WINDOW_APP_HEADER_DRAG_TO_TILE_TO_LEFT = desktopUiEventEnum15;
            DesktopUiEventEnum desktopUiEventEnum16 = new DesktopUiEventEnum("DESKTOP_WINDOW_APP_HEADER_DRAG_TO_TILE_TO_RIGHT", 15, 2007);
            DESKTOP_WINDOW_APP_HEADER_DRAG_TO_TILE_TO_RIGHT = desktopUiEventEnum16;
            DesktopUiEventEnum desktopUiEventEnum17 = new DesktopUiEventEnum("DESKTOP_WINDOW_MAXIMIZE_BUTTON_REVEAL_MENU", 16, 2015);
            DESKTOP_WINDOW_MAXIMIZE_BUTTON_REVEAL_MENU = desktopUiEventEnum17;
            DesktopUiEventEnum desktopUiEventEnum18 = new DesktopUiEventEnum("DESKTOP_WINDOW_MAXIMIZE_BUTTON_MENU_TAP_TO_MAXIMIZE", 17, 2009);
            DESKTOP_WINDOW_MAXIMIZE_BUTTON_MENU_TAP_TO_MAXIMIZE = desktopUiEventEnum18;
            DesktopUiEventEnum desktopUiEventEnum19 = new DesktopUiEventEnum("DESKTOP_WINDOW_MAXIMIZE_BUTTON_MENU_TAP_TO_IMMERSIVE", 18, 2010);
            DESKTOP_WINDOW_MAXIMIZE_BUTTON_MENU_TAP_TO_IMMERSIVE = desktopUiEventEnum19;
            DesktopUiEventEnum desktopUiEventEnum20 = new DesktopUiEventEnum("DESKTOP_WINDOW_MAXIMIZE_BUTTON_MENU_TAP_TO_RESTORE", 19, 2011);
            DESKTOP_WINDOW_MAXIMIZE_BUTTON_MENU_TAP_TO_RESTORE = desktopUiEventEnum20;
            DesktopUiEventEnum desktopUiEventEnum21 = new DesktopUiEventEnum("DESKTOP_WINDOW_MAXIMIZE_BUTTON_MENU_TAP_TO_TILE_TO_LEFT", 20, 2012);
            DESKTOP_WINDOW_MAXIMIZE_BUTTON_MENU_TAP_TO_TILE_TO_LEFT = desktopUiEventEnum21;
            DesktopUiEventEnum desktopUiEventEnum22 = new DesktopUiEventEnum("DESKTOP_WINDOW_MAXIMIZE_BUTTON_MENU_TAP_TO_TILE_TO_RIGHT", 21, 2013);
            DESKTOP_WINDOW_MAXIMIZE_BUTTON_MENU_TAP_TO_TILE_TO_RIGHT = desktopUiEventEnum22;
            DesktopUiEventEnum desktopUiEventEnum23 = new DesktopUiEventEnum("DESKTOP_WINDOW_MOVE_BY_HEADER_DRAG", 22, 2021);
            DESKTOP_WINDOW_MOVE_BY_HEADER_DRAG = desktopUiEventEnum23;
            DesktopUiEventEnum desktopUiEventEnum24 = new DesktopUiEventEnum("DESKTOP_WINDOW_HEADER_TAP_TO_REFOCUS", 23, 2022);
            DESKTOP_WINDOW_HEADER_TAP_TO_REFOCUS = desktopUiEventEnum24;
            DesktopUiEventEnum desktopUiEventEnum25 = new DesktopUiEventEnum("DESKTOP_WINDOW_MULTI_INSTANCE_NEW_WINDOW_CLICK", 24, 2069);
            DESKTOP_WINDOW_MULTI_INSTANCE_NEW_WINDOW_CLICK = desktopUiEventEnum25;
            DesktopUiEventEnum desktopUiEventEnum26 = new DesktopUiEventEnum("DESKTOP_WINDOW_MULTI_INSTANCE_MANAGE_WINDOWS_ICON_CLICK", 25, 2070);
            DESKTOP_WINDOW_MULTI_INSTANCE_MANAGE_WINDOWS_ICON_CLICK = desktopUiEventEnum26;
            DesktopUiEventEnum desktopUiEventEnum27 = new DesktopUiEventEnum("APP_HANDLE_EDUCATION_TOOLTIP_SHOWN", 26, 2097);
            DesktopUiEventEnum desktopUiEventEnum28 = new DesktopUiEventEnum("APP_HANDLE_EDUCATION_TOOLTIP_CLICKED", 27, 2098);
            DesktopUiEventEnum desktopUiEventEnum29 = new DesktopUiEventEnum("APP_HANDLE_EDUCATION_TOOLTIP_DISMISSED", 28, 2099);
            DesktopUiEventEnum desktopUiEventEnum30 = new DesktopUiEventEnum("ENTER_DESKTOP_MODE_EDUCATION_TOOLTIP_SHOWN", 29, 2100);
            DesktopUiEventEnum desktopUiEventEnum31 = new DesktopUiEventEnum("ENTER_DESKTOP_MODE_EDUCATION_TOOLTIP_CLICKED", 30, VolteConstants.ErrorCode.CALL_NOT_ACCEPTABLE_DIVERT);
            DesktopUiEventEnum desktopUiEventEnum32 = new DesktopUiEventEnum("ENTER_DESKTOP_MODE_EDUCATION_TOOLTIP_DISMISSED", 31, VolteConstants.ErrorCode.NETWORK_UNREACHABLE);
            DesktopUiEventEnum desktopUiEventEnum33 = new DesktopUiEventEnum("EXIT_DESKTOP_MODE_EDUCATION_TOOLTIP_SHOWN", 32, 2103);
            DesktopUiEventEnum desktopUiEventEnum34 = new DesktopUiEventEnum("EXIT_DESKTOP_MODE_EDUCATION_TOOLTIP_CLICKED", 33, 2104);
            DesktopUiEventEnum desktopUiEventEnum35 = new DesktopUiEventEnum("EXIT_DESKTOP_MODE_EDUCATION_TOOLTIP_DISMISSED", 34, 2105);
            DesktopUiEventEnum desktopUiEventEnum36 = new DesktopUiEventEnum("A11Y_APP_HANDLE_MENU_OPENED", 35, 2156);
            A11Y_APP_HANDLE_MENU_OPENED = desktopUiEventEnum36;
            DesktopUiEventEnum desktopUiEventEnum37 = new DesktopUiEventEnum("A11Y_SYSTEM_ACTION_APP_HANDLE_MENU", 36, 2157);
            DesktopUiEventEnum desktopUiEventEnum38 = new DesktopUiEventEnum("A11Y_APP_HANDLE_MENU_DESKTOP_VIEW", 37, 2158);
            A11Y_APP_HANDLE_MENU_DESKTOP_VIEW = desktopUiEventEnum38;
            DesktopUiEventEnum desktopUiEventEnum39 = new DesktopUiEventEnum("A11Y_APP_HANDLE_MENU_FULLSCREEN", 38, 2159);
            A11Y_APP_HANDLE_MENU_FULLSCREEN = desktopUiEventEnum39;
            DesktopUiEventEnum desktopUiEventEnum40 = new DesktopUiEventEnum("A11Y_APP_HANDLE_MENU_SPLIT_SCREEN", 39, 2160);
            A11Y_APP_HANDLE_MENU_SPLIT_SCREEN = desktopUiEventEnum40;
            DesktopUiEventEnum desktopUiEventEnum41 = new DesktopUiEventEnum("A11Y_APP_WINDOW_MAXIMIZE_RESTORE_BUTTON", 40, 2161);
            A11Y_APP_WINDOW_MAXIMIZE_RESTORE_BUTTON = desktopUiEventEnum41;
            DesktopUiEventEnum desktopUiEventEnum42 = new DesktopUiEventEnum("A11Y_APP_WINDOW_MINIMIZE_BUTTON", 41, 2162);
            A11Y_APP_WINDOW_MINIMIZE_BUTTON = desktopUiEventEnum42;
            DesktopUiEventEnum desktopUiEventEnum43 = new DesktopUiEventEnum("A11Y_APP_WINDOW_CLOSE_BUTTON", 42, 2163);
            A11Y_APP_WINDOW_CLOSE_BUTTON = desktopUiEventEnum43;
            DesktopUiEventEnum desktopUiEventEnum44 = new DesktopUiEventEnum("A11Y_MAXIMIZE_MENU_MAXIMIZE", 43, 2164);
            A11Y_MAXIMIZE_MENU_MAXIMIZE = desktopUiEventEnum44;
            DesktopUiEventEnum desktopUiEventEnum45 = new DesktopUiEventEnum("A11Y_MAXIMIZE_MENU_RESIZE_LEFT", 44, 2165);
            A11Y_MAXIMIZE_MENU_RESIZE_LEFT = desktopUiEventEnum45;
            DesktopUiEventEnum desktopUiEventEnum46 = new DesktopUiEventEnum("A11Y_MAXIMIZE_MENU_RESIZE_RIGHT", 45, 2166);
            A11Y_MAXIMIZE_MENU_RESIZE_RIGHT = desktopUiEventEnum46;
            DesktopUiEventEnum desktopUiEventEnum47 = new DesktopUiEventEnum("A11Y_ACTION_MAXIMIZE_RESTORE", 46, 2167);
            A11Y_ACTION_MAXIMIZE_RESTORE = desktopUiEventEnum47;
            DesktopUiEventEnum desktopUiEventEnum48 = new DesktopUiEventEnum("A11Y_ACTION_RESIZE_LEFT", 47, 2168);
            A11Y_ACTION_RESIZE_LEFT = desktopUiEventEnum48;
            DesktopUiEventEnum desktopUiEventEnum49 = new DesktopUiEventEnum("A11Y_ACTION_RESIZE_RIGHT", 48, 2169);
            A11Y_ACTION_RESIZE_RIGHT = desktopUiEventEnum49;
            DesktopUiEventEnum[] desktopUiEventEnumArr = {desktopUiEventEnum, desktopUiEventEnum2, desktopUiEventEnum3, desktopUiEventEnum4, desktopUiEventEnum5, desktopUiEventEnum6, desktopUiEventEnum7, desktopUiEventEnum8, desktopUiEventEnum9, desktopUiEventEnum10, desktopUiEventEnum11, desktopUiEventEnum12, desktopUiEventEnum13, desktopUiEventEnum14, desktopUiEventEnum15, desktopUiEventEnum16, desktopUiEventEnum17, desktopUiEventEnum18, desktopUiEventEnum19, desktopUiEventEnum20, desktopUiEventEnum21, desktopUiEventEnum22, desktopUiEventEnum23, desktopUiEventEnum24, desktopUiEventEnum25, desktopUiEventEnum26, desktopUiEventEnum27, desktopUiEventEnum28, desktopUiEventEnum29, desktopUiEventEnum30, desktopUiEventEnum31, desktopUiEventEnum32, desktopUiEventEnum33, desktopUiEventEnum34, desktopUiEventEnum35, desktopUiEventEnum36, desktopUiEventEnum37, desktopUiEventEnum38, desktopUiEventEnum39, desktopUiEventEnum40, desktopUiEventEnum41, desktopUiEventEnum42, desktopUiEventEnum43, desktopUiEventEnum44, desktopUiEventEnum45, desktopUiEventEnum46, desktopUiEventEnum47, desktopUiEventEnum48, desktopUiEventEnum49};
            $VALUES = desktopUiEventEnumArr;
            EnumEntriesKt.enumEntries(desktopUiEventEnumArr);
        }

        private DesktopUiEventEnum(String str, int i, int i2) {
            this.mId = i2;
        }

        public static DesktopUiEventEnum valueOf(String str) {
            return (DesktopUiEventEnum) Enum.valueOf(DesktopUiEventEnum.class, str);
        }

        public static DesktopUiEventEnum[] values() {
            return (DesktopUiEventEnum[]) $VALUES.clone();
        }

        public final int getId() {
            return this.mId;
        }
    }

    static {
        new Companion(null);
    }

    public DesktopModeUiEventLogger(UiEventLogger uiEventLogger, PackageManager packageManager) {
        this.uiEventLogger = uiEventLogger;
        this.packageManager = packageManager;
        new InstanceIdSequence(Integer.MAX_VALUE);
    }

    public static void logD(String str, Object... objArr) {
        ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
        String strConcat = "%s: ".concat(str);
        SpreadBuilder spreadBuilderM = DesktopDisplayEventHandler$$ExternalSyntheticOutline0.m(2, "DesktopModeUiEventLogger", objArr);
        ProtoLog.d(shellProtoLogGroup, strConcat, spreadBuilderM.list.toArray(new Object[spreadBuilderM.list.size()]));
    }

    public final void log(ActivityManager.RunningTaskInfo runningTaskInfo, DesktopUiEventEnum desktopUiEventEnum) {
        int i;
        ComponentName componentName = runningTaskInfo.baseActivity;
        String packageName = componentName != null ? componentName.getPackageName() : null;
        if (packageName == null) {
            logD("Skip logging due to null base activity", new Object[0]);
            return;
        }
        try {
            i = this.packageManager.getApplicationInfoAsUser(packageName, 0, runningTaskInfo.userId).uid;
        } catch (PackageManager.NameNotFoundException unused) {
            i = -1;
        }
        if (packageName.length() != 0 && i >= 0) {
            this.uiEventLogger.log(desktopUiEventEnum, i, packageName);
        } else {
            logD("Skip logging since package name is empty or bad uid", new Object[0]);
        }
    }
}
