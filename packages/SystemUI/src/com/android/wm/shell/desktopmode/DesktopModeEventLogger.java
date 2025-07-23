package com.android.wm.shell.desktopmode;

import android.app.ActivityManager;
import android.util.EventLog;
import android.util.Size;
import android.view.MotionEvent;
import android.window.DesktopModeFlags;
import com.android.internal.protolog.ProtoLog;
import com.android.internal.util.FrameworkStatsLog;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DesktopModeEventLogger {
    public static final Companion Companion = new Companion(null);
    public final Random random = new SecureRandom();
    public final AtomicInteger currentSessionId = new AtomicInteger(0);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {

        /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
        /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class EnterReason {
            public static final /* synthetic */ EnterReason[] $VALUES;
            public static final EnterReason APP_FREEFORM_INTENT;
            public static final EnterReason APP_FROM_OVERVIEW;
            public static final EnterReason APP_HANDLE_DRAG;
            public static final EnterReason APP_HANDLE_MENU_BUTTON;
            public static final EnterReason KEYBOARD_SHORTCUT_ENTER;
            public static final EnterReason OVERVIEW;
            public static final EnterReason SCREEN_ON;
            public static final EnterReason UNKNOWN_ENTER;
            private final int reason;

            static {
                EnterReason enterReason = new EnterReason("UNKNOWN_ENTER", 0, 0);
                UNKNOWN_ENTER = enterReason;
                EnterReason enterReason2 = new EnterReason("OVERVIEW", 1, 1);
                OVERVIEW = enterReason2;
                EnterReason enterReason3 = new EnterReason("APP_HANDLE_DRAG", 2, 2);
                APP_HANDLE_DRAG = enterReason3;
                EnterReason enterReason4 = new EnterReason("APP_HANDLE_MENU_BUTTON", 3, 3);
                APP_HANDLE_MENU_BUTTON = enterReason4;
                EnterReason enterReason5 = new EnterReason("APP_FREEFORM_INTENT", 4, 4);
                APP_FREEFORM_INTENT = enterReason5;
                EnterReason enterReason6 = new EnterReason("KEYBOARD_SHORTCUT_ENTER", 5, 5);
                KEYBOARD_SHORTCUT_ENTER = enterReason6;
                EnterReason enterReason7 = new EnterReason("SCREEN_ON", 6, 6);
                SCREEN_ON = enterReason7;
                EnterReason enterReason8 = new EnterReason("APP_FROM_OVERVIEW", 7, 7);
                APP_FROM_OVERVIEW = enterReason8;
                EnterReason[] enterReasonArr = {enterReason, enterReason2, enterReason3, enterReason4, enterReason5, enterReason6, enterReason7, enterReason8};
                $VALUES = enterReasonArr;
                EnumEntriesKt.enumEntries(enterReasonArr);
            }

            private EnterReason(String str, int i, int i2) {
                this.reason = i2;
            }

            public static EnterReason valueOf(String str) {
                return (EnterReason) Enum.valueOf(EnterReason.class, str);
            }

            public static EnterReason[] values() {
                return (EnterReason[]) $VALUES.clone();
            }

            public final int getReason() {
                return this.reason;
            }
        }

        /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
        /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class ExitReason {
            public static final /* synthetic */ ExitReason[] $VALUES;
            public static final ExitReason APP_HANDLE_MENU_BUTTON_EXIT;
            public static final ExitReason DRAG_TO_EXIT;
            public static final ExitReason KEYBOARD_SHORTCUT_EXIT;
            public static final ExitReason RETURN_HOME_OR_OVERVIEW;
            public static final ExitReason SCREEN_OFF;
            public static final ExitReason TASK_FINISHED;
            public static final ExitReason TASK_MINIMIZED;
            public static final ExitReason TASK_MOVED_TO_BACK;
            public static final ExitReason UNKNOWN_EXIT;
            private final int reason;

            static {
                ExitReason exitReason = new ExitReason("UNKNOWN_EXIT", 0, 0);
                UNKNOWN_EXIT = exitReason;
                ExitReason exitReason2 = new ExitReason("DRAG_TO_EXIT", 1, 1);
                DRAG_TO_EXIT = exitReason2;
                ExitReason exitReason3 = new ExitReason("APP_HANDLE_MENU_BUTTON_EXIT", 2, 2);
                APP_HANDLE_MENU_BUTTON_EXIT = exitReason3;
                ExitReason exitReason4 = new ExitReason("KEYBOARD_SHORTCUT_EXIT", 3, 3);
                KEYBOARD_SHORTCUT_EXIT = exitReason4;
                ExitReason exitReason5 = new ExitReason("RETURN_HOME_OR_OVERVIEW", 4, 4);
                RETURN_HOME_OR_OVERVIEW = exitReason5;
                ExitReason exitReason6 = new ExitReason("TASK_FINISHED", 5, 5);
                TASK_FINISHED = exitReason6;
                ExitReason exitReason7 = new ExitReason("SCREEN_OFF", 6, 6);
                SCREEN_OFF = exitReason7;
                ExitReason exitReason8 = new ExitReason("TASK_MINIMIZED", 7, 7);
                TASK_MINIMIZED = exitReason8;
                ExitReason exitReason9 = new ExitReason("TASK_MOVED_TO_BACK", 8, 8);
                TASK_MOVED_TO_BACK = exitReason9;
                ExitReason[] exitReasonArr = {exitReason, exitReason2, exitReason3, exitReason4, exitReason5, exitReason6, exitReason7, exitReason8, exitReason9};
                $VALUES = exitReasonArr;
                EnumEntriesKt.enumEntries(exitReasonArr);
            }

            private ExitReason(String str, int i, int i2) {
                this.reason = i2;
            }

            public static ExitReason valueOf(String str) {
                return (ExitReason) Enum.valueOf(ExitReason.class, str);
            }

            public static ExitReason[] values() {
                return (ExitReason[]) $VALUES.clone();
            }

            public final int getReason() {
                return this.reason;
            }
        }

        /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
        /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class FocusReason {
            public static final /* synthetic */ FocusReason[] $VALUES;
            public static final FocusReason UNKNOWN;
            private final int reason;

            static {
                FocusReason focusReason = new FocusReason("UNKNOWN", 0, 1);
                UNKNOWN = focusReason;
                FocusReason[] focusReasonArr = {focusReason};
                $VALUES = focusReasonArr;
                EnumEntriesKt.enumEntries(focusReasonArr);
            }

            private FocusReason(String str, int i, int i2) {
                this.reason = i2;
            }

            public static FocusReason valueOf(String str) {
                return (FocusReason) Enum.valueOf(FocusReason.class, str);
            }

            public static FocusReason[] values() {
                return (FocusReason[]) $VALUES.clone();
            }

            public final int getReason() {
                return this.reason;
            }
        }

        /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
        /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class InputMethod {
            public static final /* synthetic */ InputMethod[] $VALUES;
            public static final InputMethod KEYBOARD;
            public static final InputMethod MOUSE;
            public static final InputMethod STYLUS;
            public static final InputMethod TOUCH;
            public static final InputMethod TOUCHPAD;
            public static final InputMethod UNKNOWN_INPUT_METHOD;
            private final int method;

            static {
                InputMethod inputMethod = new InputMethod("UNKNOWN_INPUT_METHOD", 0, 0);
                UNKNOWN_INPUT_METHOD = inputMethod;
                InputMethod inputMethod2 = new InputMethod("TOUCH", 1, 1);
                TOUCH = inputMethod2;
                InputMethod inputMethod3 = new InputMethod("STYLUS", 2, 2);
                STYLUS = inputMethod3;
                InputMethod inputMethod4 = new InputMethod("MOUSE", 3, 3);
                MOUSE = inputMethod4;
                InputMethod inputMethod5 = new InputMethod("TOUCHPAD", 4, 4);
                TOUCHPAD = inputMethod5;
                InputMethod inputMethod6 = new InputMethod("KEYBOARD", 5, 5);
                KEYBOARD = inputMethod6;
                InputMethod[] inputMethodArr = {inputMethod, inputMethod2, inputMethod3, inputMethod4, inputMethod5, inputMethod6};
                $VALUES = inputMethodArr;
                EnumEntriesKt.enumEntries(inputMethodArr);
            }

            private InputMethod(String str, int i, int i2) {
                this.method = i2;
            }

            public static InputMethod valueOf(String str) {
                return (InputMethod) Enum.valueOf(InputMethod.class, str);
            }

            public static InputMethod[] values() {
                return (InputMethod[]) $VALUES.clone();
            }

            public final int getMethod() {
                return this.method;
            }
        }

        /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
        /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class MinimizeReason {
            public static final /* synthetic */ MinimizeReason[] $VALUES;
            public static final MinimizeReason HOME_ACTION;
            public static final MinimizeReason KEY_GESTURE;
            public static final MinimizeReason MINIMIZE_BUTTON;
            public static final MinimizeReason TASK_LIMIT;
            public static final MinimizeReason TASK_TO_BACK;
            private final int reason;

            static {
                MinimizeReason minimizeReason = new MinimizeReason("TASK_LIMIT", 0, 1);
                TASK_LIMIT = minimizeReason;
                MinimizeReason minimizeReason2 = new MinimizeReason("MINIMIZE_BUTTON", 1, 2);
                MINIMIZE_BUTTON = minimizeReason2;
                MinimizeReason minimizeReason3 = new MinimizeReason("KEY_GESTURE", 2, 3);
                KEY_GESTURE = minimizeReason3;
                MinimizeReason minimizeReason4 = new MinimizeReason("HOME_ACTION", 3, 4);
                HOME_ACTION = minimizeReason4;
                MinimizeReason minimizeReason5 = new MinimizeReason("TASK_TO_BACK", 4, 5);
                TASK_TO_BACK = minimizeReason5;
                MinimizeReason[] minimizeReasonArr = {minimizeReason, minimizeReason2, minimizeReason3, minimizeReason4, minimizeReason5};
                $VALUES = minimizeReasonArr;
                EnumEntriesKt.enumEntries(minimizeReasonArr);
            }

            private MinimizeReason(String str, int i, int i2) {
                this.reason = i2;
            }

            public static MinimizeReason valueOf(String str) {
                return (MinimizeReason) Enum.valueOf(MinimizeReason.class, str);
            }

            public static MinimizeReason[] values() {
                return (MinimizeReason[]) $VALUES.clone();
            }

            public final int getReason() {
                return this.reason;
            }
        }

        /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
        /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class ResizeTrigger {
            public static final /* synthetic */ ResizeTrigger[] $VALUES;
            public static final ResizeTrigger CORNER;
            public static final ResizeTrigger DOUBLE_TAP_APP_HEADER;
            public static final ResizeTrigger DRAG_LEFT;
            public static final ResizeTrigger DRAG_RIGHT;
            public static final ResizeTrigger DRAG_TO_TOP_RESIZE_TRIGGER;
            public static final ResizeTrigger EDGE;
            public static final ResizeTrigger MAXIMIZE_BUTTON;
            public static final ResizeTrigger MAXIMIZE_MENU;
            public static final ResizeTrigger SNAP_LEFT_MENU;
            public static final ResizeTrigger SNAP_RIGHT_MENU;
            public static final ResizeTrigger TILING_DIVIDER;
            public static final ResizeTrigger UNKNOWN_RESIZE_TRIGGER;
            private final int trigger;

            static {
                ResizeTrigger resizeTrigger = new ResizeTrigger("UNKNOWN_RESIZE_TRIGGER", 0, 0);
                UNKNOWN_RESIZE_TRIGGER = resizeTrigger;
                ResizeTrigger resizeTrigger2 = new ResizeTrigger("CORNER", 1, 1);
                CORNER = resizeTrigger2;
                ResizeTrigger resizeTrigger3 = new ResizeTrigger("EDGE", 2, 2);
                EDGE = resizeTrigger3;
                ResizeTrigger resizeTrigger4 = new ResizeTrigger("TILING_DIVIDER", 3, 3);
                TILING_DIVIDER = resizeTrigger4;
                ResizeTrigger resizeTrigger5 = new ResizeTrigger("MAXIMIZE_BUTTON", 4, 4);
                MAXIMIZE_BUTTON = resizeTrigger5;
                ResizeTrigger resizeTrigger6 = new ResizeTrigger("DOUBLE_TAP_APP_HEADER", 5, 5);
                DOUBLE_TAP_APP_HEADER = resizeTrigger6;
                ResizeTrigger resizeTrigger7 = new ResizeTrigger("DRAG_LEFT", 6, 6);
                DRAG_LEFT = resizeTrigger7;
                ResizeTrigger resizeTrigger8 = new ResizeTrigger("DRAG_RIGHT", 7, 7);
                DRAG_RIGHT = resizeTrigger8;
                ResizeTrigger resizeTrigger9 = new ResizeTrigger("SNAP_LEFT_MENU", 8, 8);
                SNAP_LEFT_MENU = resizeTrigger9;
                ResizeTrigger resizeTrigger10 = new ResizeTrigger("SNAP_RIGHT_MENU", 9, 9);
                SNAP_RIGHT_MENU = resizeTrigger10;
                ResizeTrigger resizeTrigger11 = new ResizeTrigger("MAXIMIZE_MENU", 10, 10);
                MAXIMIZE_MENU = resizeTrigger11;
                ResizeTrigger resizeTrigger12 = new ResizeTrigger("DRAG_TO_TOP_RESIZE_TRIGGER", 11, 11);
                DRAG_TO_TOP_RESIZE_TRIGGER = resizeTrigger12;
                ResizeTrigger[] resizeTriggerArr = {resizeTrigger, resizeTrigger2, resizeTrigger3, resizeTrigger4, resizeTrigger5, resizeTrigger6, resizeTrigger7, resizeTrigger8, resizeTrigger9, resizeTrigger10, resizeTrigger11, resizeTrigger12};
                $VALUES = resizeTriggerArr;
                EnumEntriesKt.enumEntries(resizeTriggerArr);
            }

            private ResizeTrigger(String str, int i, int i2) {
                this.trigger = i2;
            }

            public static ResizeTrigger valueOf(String str) {
                return (ResizeTrigger) Enum.valueOf(ResizeTrigger.class, str);
            }

            public static ResizeTrigger[] values() {
                return (ResizeTrigger[]) $VALUES.clone();
            }

            public final int getTrigger() {
                return this.trigger;
            }
        }

        /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
        /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class UnminimizeReason {
            public static final /* synthetic */ UnminimizeReason[] $VALUES;
            public static final UnminimizeReason ALT_TAB;
            public static final UnminimizeReason APP_HANDLE_MENU_BUTTON;
            public static final UnminimizeReason TASKBAR_MANAGE_WINDOW;
            public static final UnminimizeReason TASKBAR_TAP;
            public static final UnminimizeReason TASK_LAUNCH;
            public static final UnminimizeReason UNKNOWN;
            private final int reason;

            static {
                UnminimizeReason unminimizeReason = new UnminimizeReason("UNKNOWN", 0, 1);
                UNKNOWN = unminimizeReason;
                UnminimizeReason unminimizeReason2 = new UnminimizeReason("TASKBAR_TAP", 1, 2);
                TASKBAR_TAP = unminimizeReason2;
                UnminimizeReason unminimizeReason3 = new UnminimizeReason("ALT_TAB", 2, 3);
                ALT_TAB = unminimizeReason3;
                UnminimizeReason unminimizeReason4 = new UnminimizeReason("TASK_LAUNCH", 3, 4);
                TASK_LAUNCH = unminimizeReason4;
                UnminimizeReason unminimizeReason5 = new UnminimizeReason("APP_HANDLE_MENU_BUTTON", 4, 5);
                APP_HANDLE_MENU_BUTTON = unminimizeReason5;
                UnminimizeReason unminimizeReason6 = new UnminimizeReason("TASKBAR_MANAGE_WINDOW", 5, 6);
                TASKBAR_MANAGE_WINDOW = unminimizeReason6;
                UnminimizeReason[] unminimizeReasonArr = {unminimizeReason, unminimizeReason2, unminimizeReason3, unminimizeReason4, unminimizeReason5, unminimizeReason6};
                $VALUES = unminimizeReasonArr;
                EnumEntriesKt.enumEntries(unminimizeReasonArr);
            }

            private UnminimizeReason(String str, int i, int i2) {
                this.reason = i2;
            }

            public static UnminimizeReason valueOf(String str) {
                return (UnminimizeReason) Enum.valueOf(UnminimizeReason.class, str);
            }

            public static UnminimizeReason[] values() {
                return (UnminimizeReason[]) $VALUES.clone();
            }

            public final int getReason() {
                return this.reason;
            }
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static InputMethod getInputMethodFromMotionEvent(MotionEvent motionEvent) {
            if (motionEvent == null) {
                return InputMethod.UNKNOWN_INPUT_METHOD;
            }
            int toolType = motionEvent.getToolType(motionEvent.findPointerIndex(motionEvent.getPointerId(0)));
            return toolType == 2 ? InputMethod.STYLUS : toolType == 3 ? InputMethod.MOUSE : (toolType == 1 && motionEvent.getSource() == 8194) ? InputMethod.TOUCHPAD : (toolType == 1 && motionEvent.getSource() == 4098) ? InputMethod.TOUCH : InputMethod.UNKNOWN_INPUT_METHOD;
        }

        private Companion() {
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class TaskSizeUpdate {
            public final Integer displayArea;
            public final InputMethod inputMethod;
            public final int instanceId;
            public final ResizeTrigger resizeTrigger;
            public final int taskHeight;
            public final int taskWidth;
            public final int uid;

            public TaskSizeUpdate(ResizeTrigger resizeTrigger, InputMethod inputMethod, int i, int i2, int i3, int i4, Integer num) {
                this.resizeTrigger = resizeTrigger;
                this.inputMethod = inputMethod;
                this.instanceId = i;
                this.uid = i2;
                this.taskHeight = i3;
                this.taskWidth = i4;
                this.displayArea = num;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof TaskSizeUpdate)) {
                    return false;
                }
                TaskSizeUpdate taskSizeUpdate = (TaskSizeUpdate) obj;
                return this.resizeTrigger == taskSizeUpdate.resizeTrigger && this.inputMethod == taskSizeUpdate.inputMethod && this.instanceId == taskSizeUpdate.instanceId && this.uid == taskSizeUpdate.uid && this.taskHeight == taskSizeUpdate.taskHeight && this.taskWidth == taskSizeUpdate.taskWidth && Intrinsics.areEqual(this.displayArea, taskSizeUpdate.displayArea);
            }

            public final int hashCode() {
                ResizeTrigger resizeTrigger = this.resizeTrigger;
                int hashCode = (resizeTrigger == null ? 0 : resizeTrigger.hashCode()) * 31;
                InputMethod inputMethod = this.inputMethod;
                int m = ReorderTile$$ExternalSyntheticOutline0.m(this.taskWidth, ReorderTile$$ExternalSyntheticOutline0.m(this.taskHeight, ReorderTile$$ExternalSyntheticOutline0.m(this.uid, ReorderTile$$ExternalSyntheticOutline0.m(this.instanceId, (hashCode + (inputMethod == null ? 0 : inputMethod.hashCode())) * 31, 31), 31), 31), 31);
                Integer num = this.displayArea;
                return m + (num != null ? num.hashCode() : 0);
            }

            public final String toString() {
                return "TaskSizeUpdate(resizeTrigger=" + this.resizeTrigger + ", inputMethod=" + this.inputMethod + ", instanceId=" + this.instanceId + ", uid=" + this.uid + ", taskHeight=" + this.taskHeight + ", taskWidth=" + this.taskWidth + ", displayArea=" + this.displayArea + ")";
            }

            public /* synthetic */ TaskSizeUpdate(ResizeTrigger resizeTrigger, InputMethod inputMethod, int i, int i2, int i3, int i4, Integer num, int i5, DefaultConstructorMarker defaultConstructorMarker) {
                this((i5 & 1) != 0 ? null : resizeTrigger, (i5 & 2) != 0 ? null : inputMethod, i, i2, i3, i4, num);
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class TaskUpdate {
            public final FocusReason focusReason;
            public final int instanceId;
            public final MinimizeReason minimizeReason;
            public final int taskHeight;
            public final int taskWidth;
            public final int taskX;
            public final int taskY;
            public final int uid;
            public final UnminimizeReason unminimizeReason;
            public final int visibleTaskCount;

            public TaskUpdate(int i, int i2, int i3, int i4, int i5, int i6, MinimizeReason minimizeReason, UnminimizeReason unminimizeReason, int i7, FocusReason focusReason) {
                this.instanceId = i;
                this.uid = i2;
                this.taskHeight = i3;
                this.taskWidth = i4;
                this.taskX = i5;
                this.taskY = i6;
                this.minimizeReason = minimizeReason;
                this.unminimizeReason = unminimizeReason;
                this.visibleTaskCount = i7;
                this.focusReason = focusReason;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof TaskUpdate)) {
                    return false;
                }
                TaskUpdate taskUpdate = (TaskUpdate) obj;
                return this.instanceId == taskUpdate.instanceId && this.uid == taskUpdate.uid && this.taskHeight == taskUpdate.taskHeight && this.taskWidth == taskUpdate.taskWidth && this.taskX == taskUpdate.taskX && this.taskY == taskUpdate.taskY && this.minimizeReason == taskUpdate.minimizeReason && this.unminimizeReason == taskUpdate.unminimizeReason && this.visibleTaskCount == taskUpdate.visibleTaskCount && this.focusReason == taskUpdate.focusReason;
            }

            public final int hashCode() {
                int m = ReorderTile$$ExternalSyntheticOutline0.m(this.taskY, ReorderTile$$ExternalSyntheticOutline0.m(this.taskX, ReorderTile$$ExternalSyntheticOutline0.m(this.taskWidth, ReorderTile$$ExternalSyntheticOutline0.m(this.taskHeight, ReorderTile$$ExternalSyntheticOutline0.m(this.uid, Integer.hashCode(this.instanceId) * 31, 31), 31), 31), 31), 31);
                MinimizeReason minimizeReason = this.minimizeReason;
                int hashCode = (m + (minimizeReason == null ? 0 : minimizeReason.hashCode())) * 31;
                UnminimizeReason unminimizeReason = this.unminimizeReason;
                int m2 = ReorderTile$$ExternalSyntheticOutline0.m(this.visibleTaskCount, (hashCode + (unminimizeReason == null ? 0 : unminimizeReason.hashCode())) * 31, 31);
                FocusReason focusReason = this.focusReason;
                return m2 + (focusReason != null ? focusReason.hashCode() : 0);
            }

            public final String toString() {
                return "TaskUpdate(instanceId=" + this.instanceId + ", uid=" + this.uid + ", taskHeight=" + this.taskHeight + ", taskWidth=" + this.taskWidth + ", taskX=" + this.taskX + ", taskY=" + this.taskY + ", minimizeReason=" + this.minimizeReason + ", unminimizeReason=" + this.unminimizeReason + ", visibleTaskCount=" + this.visibleTaskCount + ", focusReason=" + this.focusReason + ")";
            }

            public /* synthetic */ TaskUpdate(int i, int i2, int i3, int i4, int i5, int i6, MinimizeReason minimizeReason, UnminimizeReason unminimizeReason, int i7, FocusReason focusReason, int i8, DefaultConstructorMarker defaultConstructorMarker) {
                this(i, i2, i3, i4, i5, i6, (i8 & 64) != 0 ? null : minimizeReason, (i8 & 128) != 0 ? null : unminimizeReason, i7, (i8 & 512) != 0 ? null : focusReason);
            }
        }

        public static /* synthetic */ void getNO_SESSION_ID$annotations() {
        }

        public static /* synthetic */ void getUNSET_FOCUS_REASON$annotations() {
        }

        public static /* synthetic */ void getUNSET_MINIMIZE_REASON$annotations() {
        }

        public static /* synthetic */ void getUNSET_UNMINIMIZE_REASON$annotations() {
        }
    }

    public static Companion.TaskSizeUpdate createTaskSizeUpdate(Companion.ResizeTrigger resizeTrigger, Companion.InputMethod inputMethod, ActivityManager.RunningTaskInfo runningTaskInfo, Integer num, Integer num2, DisplayController displayController, Size size) {
        Integer num3;
        DisplayLayout displayLayout;
        runningTaskInfo.configuration.windowConfiguration.getBounds();
        int intValue = num2.intValue();
        int intValue2 = num.intValue();
        if (size != null) {
            num3 = Integer.valueOf(size.getWidth() * size.getHeight());
        } else {
            num3 = null;
            if (displayController != null && (displayLayout = displayController.getDisplayLayout(runningTaskInfo.displayId)) != null) {
                num3 = Integer.valueOf(displayLayout.mHeight * displayLayout.mWidth);
            }
        }
        return new Companion.TaskSizeUpdate(resizeTrigger, inputMethod, runningTaskInfo.taskId, runningTaskInfo.effectiveUid, intValue, intValue2, num3);
    }

    public static void logTaskSizeUpdated(int i, int i2, Companion.TaskSizeUpdate taskSizeUpdate) {
        Companion.ResizeTrigger resizeTrigger = taskSizeUpdate.resizeTrigger;
        if (resizeTrigger == null) {
            resizeTrigger = Companion.ResizeTrigger.UNKNOWN_RESIZE_TRIGGER;
        }
        int trigger = resizeTrigger.getTrigger();
        Companion.InputMethod inputMethod = taskSizeUpdate.inputMethod;
        if (inputMethod == null) {
            inputMethod = Companion.InputMethod.UNKNOWN_INPUT_METHOD;
        }
        int method = inputMethod.getMethod();
        Integer num = taskSizeUpdate.displayArea;
        FrameworkStatsLog.write(935, trigger, i, method, i2, taskSizeUpdate.instanceId, taskSizeUpdate.uid, taskSizeUpdate.taskHeight, taskSizeUpdate.taskWidth, num != null ? num.intValue() : -1);
    }

    public static void logTaskUpdate(int i, int i2, Companion.TaskUpdate taskUpdate) {
        Companion.MinimizeReason minimizeReason = taskUpdate.minimizeReason;
        int reason = minimizeReason != null ? minimizeReason.getReason() : 0;
        Companion.UnminimizeReason unminimizeReason = taskUpdate.unminimizeReason;
        int reason2 = unminimizeReason != null ? unminimizeReason.getReason() : 0;
        Companion.FocusReason focusReason = taskUpdate.focusReason;
        FrameworkStatsLog.write(819, i, taskUpdate.instanceId, taskUpdate.uid, taskUpdate.taskHeight, taskUpdate.taskWidth, taskUpdate.taskX, taskUpdate.taskY, i2, reason, reason2, taskUpdate.visibleTaskCount, focusReason != null ? focusReason.getReason() : 0);
        EventLog.writeEvent(38502, Integer.valueOf(i), Integer.valueOf(taskUpdate.instanceId), Integer.valueOf(taskUpdate.uid), Integer.valueOf(taskUpdate.taskHeight), Integer.valueOf(taskUpdate.taskWidth), Integer.valueOf(taskUpdate.taskX), Integer.valueOf(taskUpdate.taskY), Integer.valueOf(i2), Integer.valueOf(minimizeReason != null ? minimizeReason.getReason() : 0), Integer.valueOf(unminimizeReason != null ? unminimizeReason.getReason() : 0), Integer.valueOf(taskUpdate.visibleTaskCount), Integer.valueOf(focusReason != null ? focusReason.getReason() : 0));
    }

    public final void logTaskInfoChanged(Companion.TaskUpdate taskUpdate) {
        int i = this.currentSessionId.get();
        if (i == 0) {
            ProtoLog.w(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, "DesktopModeLogger: No session id found for logging task info changed", new Object[0]);
        } else {
            ProtoLog.v(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, "DesktopModeLogger: Logging task info changed, session: %s taskId: %s", new Object[]{Integer.valueOf(i), Integer.valueOf(taskUpdate.instanceId)});
            logTaskUpdate(3, i, taskUpdate);
        }
    }

    public final void logTaskResizingEnded(Companion.ResizeTrigger resizeTrigger, Companion.InputMethod inputMethod, ActivityManager.RunningTaskInfo runningTaskInfo, Integer num, Integer num2, DisplayController displayController, Size size) {
        if (DesktopModeFlags.ENABLE_RESIZING_METRICS.isTrue()) {
            int i = this.currentSessionId.get();
            if (i == 0) {
                ProtoLog.w(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, "DesktopModeLogger: No session id found for logging end of task resizing", new Object[0]);
                return;
            }
            Companion.TaskSizeUpdate createTaskSizeUpdate = createTaskSizeUpdate(resizeTrigger, inputMethod, runningTaskInfo, num, num2, displayController, size);
            ProtoLog.v(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, "DesktopModeLogger: Logging task resize is ending, session: %s, taskSizeUpdate: %s", new Object[]{Integer.valueOf(i), createTaskSizeUpdate});
            logTaskSizeUpdated(2, i, createTaskSizeUpdate);
        }
    }

    public final void logTaskResizingStarted(Companion.ResizeTrigger resizeTrigger, Companion.InputMethod inputMethod, ActivityManager.RunningTaskInfo runningTaskInfo, Integer num, Integer num2, DisplayController displayController, Size size) {
        if (DesktopModeFlags.ENABLE_RESIZING_METRICS.isTrue()) {
            int i = this.currentSessionId.get();
            if (i == 0) {
                ProtoLog.w(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, "DesktopModeLogger: No session id found for logging start of task resizing", new Object[0]);
                return;
            }
            Companion.TaskSizeUpdate createTaskSizeUpdate = createTaskSizeUpdate(resizeTrigger, inputMethod, runningTaskInfo, num, num2, displayController, size);
            ProtoLog.v(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, "DesktopModeLogger: Logging task resize is starting, session: %s, taskSizeUpdate: %s", new Object[]{Integer.valueOf(i), createTaskSizeUpdate});
            logTaskSizeUpdated(1, i, createTaskSizeUpdate);
        }
    }

    public static /* synthetic */ void getCurrentSessionId$annotations() {
    }
}
