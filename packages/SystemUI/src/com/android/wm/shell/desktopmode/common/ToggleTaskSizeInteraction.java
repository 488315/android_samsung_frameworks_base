package com.android.wm.shell.desktopmode.common;

import android.graphics.Rect;
import com.android.wm.shell.desktopmode.DesktopModeEventLogger;
import com.android.wm.shell.desktopmode.DesktopModeUiEventLogger;
import kotlin.NoWhenBranchMatchedException;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ToggleTaskSizeInteraction {
    public final Rect animationStartBounds;
    public final Integer cujTracing;
    public final Direction direction;
    public final DesktopModeEventLogger.Companion.InputMethod inputMethod;
    public final String jankTag;
    public final DesktopModeEventLogger.Companion.ResizeTrigger resizeTrigger;
    public final Source source;
    public final DesktopModeUiEventLogger.DesktopUiEventEnum uiEvent;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class AmbiguousSource {
        public static final /* synthetic */ AmbiguousSource[] $VALUES;
        public static final AmbiguousSource DOUBLE_TAP;
        public static final AmbiguousSource HEADER_BUTTON;
        public static final AmbiguousSource MAXIMIZE_MENU;

        static {
            AmbiguousSource ambiguousSource = new AmbiguousSource("HEADER_BUTTON", 0);
            HEADER_BUTTON = ambiguousSource;
            AmbiguousSource ambiguousSource2 = new AmbiguousSource("MAXIMIZE_MENU", 1);
            MAXIMIZE_MENU = ambiguousSource2;
            AmbiguousSource ambiguousSource3 = new AmbiguousSource("DOUBLE_TAP", 2);
            DOUBLE_TAP = ambiguousSource3;
            AmbiguousSource[] ambiguousSourceArr = {ambiguousSource, ambiguousSource2, ambiguousSource3};
            $VALUES = ambiguousSourceArr;
            EnumEntriesKt.enumEntries(ambiguousSourceArr);
        }

        private AmbiguousSource(String str, int i) {
        }

        public static AmbiguousSource valueOf(String str) {
            return (AmbiguousSource) Enum.valueOf(AmbiguousSource.class, str);
        }

        public static AmbiguousSource[] values() {
            return (AmbiguousSource[]) $VALUES.clone();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Direction {
        public static final /* synthetic */ Direction[] $VALUES;
        public static final Direction MAXIMIZE;
        public static final Direction RESTORE;

        static {
            Direction direction = new Direction("MAXIMIZE", 0);
            MAXIMIZE = direction;
            Direction direction2 = new Direction("RESTORE", 1);
            RESTORE = direction2;
            Direction[] directionArr = {direction, direction2};
            $VALUES = directionArr;
            EnumEntriesKt.enumEntries(directionArr);
        }

        private Direction(String str, int i) {
        }

        public static Direction valueOf(String str) {
            return (Direction) Enum.valueOf(Direction.class, str);
        }

        public static Direction[] values() {
            return (Direction[]) $VALUES.clone();
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Source {
        public static final /* synthetic */ Source[] $VALUES;
        public static final Source DOUBLE_TAP_TO_MAXIMIZE;
        public static final Source DOUBLE_TAP_TO_RESTORE;
        public static final Source HEADER_BUTTON_TO_MAXIMIZE;
        public static final Source HEADER_BUTTON_TO_RESTORE;
        public static final Source HEADER_DRAG_TO_TOP;
        public static final Source KEYBOARD_SHORTCUT;
        public static final Source MAXIMIZE_MENU_TO_MAXIMIZE;
        public static final Source MAXIMIZE_MENU_TO_RESTORE;

        static {
            Source source = new Source("HEADER_BUTTON_TO_MAXIMIZE", 0);
            HEADER_BUTTON_TO_MAXIMIZE = source;
            Source source2 = new Source("HEADER_BUTTON_TO_RESTORE", 1);
            HEADER_BUTTON_TO_RESTORE = source2;
            Source source3 = new Source("KEYBOARD_SHORTCUT", 2);
            KEYBOARD_SHORTCUT = source3;
            Source source4 = new Source("HEADER_DRAG_TO_TOP", 3);
            HEADER_DRAG_TO_TOP = source4;
            Source source5 = new Source("MAXIMIZE_MENU_TO_MAXIMIZE", 4);
            MAXIMIZE_MENU_TO_MAXIMIZE = source5;
            Source source6 = new Source("MAXIMIZE_MENU_TO_RESTORE", 5);
            MAXIMIZE_MENU_TO_RESTORE = source6;
            Source source7 = new Source("DOUBLE_TAP_TO_MAXIMIZE", 6);
            DOUBLE_TAP_TO_MAXIMIZE = source7;
            Source source8 = new Source("DOUBLE_TAP_TO_RESTORE", 7);
            DOUBLE_TAP_TO_RESTORE = source8;
            Source[] sourceArr = {source, source2, source3, source4, source5, source6, source7, source8};
            $VALUES = sourceArr;
            EnumEntriesKt.enumEntries(sourceArr);
        }

        private Source(String str, int i) {
        }

        public static Source valueOf(String str) {
            return (Source) Enum.valueOf(Source.class, str);
        }

        public static Source[] values() {
            return (Source[]) $VALUES.clone();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Source.values().length];
            try {
                iArr[Source.HEADER_BUTTON_TO_MAXIMIZE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Source.HEADER_BUTTON_TO_RESTORE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[Source.KEYBOARD_SHORTCUT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[Source.HEADER_DRAG_TO_TOP.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[Source.MAXIMIZE_MENU_TO_MAXIMIZE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[Source.MAXIMIZE_MENU_TO_RESTORE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[Source.DOUBLE_TAP_TO_MAXIMIZE.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[Source.DOUBLE_TAP_TO_RESTORE.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public ToggleTaskSizeInteraction(Direction direction, Source source, DesktopModeEventLogger.Companion.InputMethod inputMethod) {
        this(direction, source, inputMethod, null, 8, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ToggleTaskSizeInteraction)) {
            return false;
        }
        ToggleTaskSizeInteraction toggleTaskSizeInteraction = (ToggleTaskSizeInteraction) obj;
        return this.direction == toggleTaskSizeInteraction.direction && this.source == toggleTaskSizeInteraction.source && this.inputMethod == toggleTaskSizeInteraction.inputMethod && Intrinsics.areEqual(this.animationStartBounds, toggleTaskSizeInteraction.animationStartBounds);
    }

    public final int hashCode() {
        int hashCode = (this.inputMethod.hashCode() + ((this.source.hashCode() + (this.direction.hashCode() * 31)) * 31)) * 31;
        Rect rect = this.animationStartBounds;
        return hashCode + (rect == null ? 0 : rect.hashCode());
    }

    public final String toString() {
        return "ToggleTaskSizeInteraction(direction=" + this.direction + ", source=" + this.source + ", inputMethod=" + this.inputMethod + ", animationStartBounds=" + this.animationStartBounds + ")";
    }

    public ToggleTaskSizeInteraction(Direction direction, Source source, DesktopModeEventLogger.Companion.InputMethod inputMethod, Rect rect) {
        DesktopModeUiEventLogger.DesktopUiEventEnum desktopUiEventEnum;
        DesktopModeEventLogger.Companion.ResizeTrigger resizeTrigger;
        this.direction = direction;
        this.source = source;
        this.inputMethod = inputMethod;
        this.animationStartBounds = rect;
        int[] iArr = WhenMappings.$EnumSwitchMapping$0;
        String str = "caption_bar_button";
        Integer num = null;
        switch (iArr[source.ordinal()]) {
            case 1:
            case 2:
                break;
            case 3:
            case 4:
                str = null;
                break;
            case 5:
            case 6:
                str = "maximize_menu";
                break;
            case 7:
            case 8:
                str = "double_tap";
                break;
            default:
                throw new NoWhenBranchMatchedException();
        }
        this.jankTag = str;
        switch (iArr[source.ordinal()]) {
            case 1:
                desktopUiEventEnum = DesktopModeUiEventLogger.DesktopUiEventEnum.DESKTOP_WINDOW_MAXIMIZE_BUTTON_TAP;
                break;
            case 2:
                desktopUiEventEnum = DesktopModeUiEventLogger.DesktopUiEventEnum.DESKTOP_WINDOW_RESTORE_BUTTON_TAP;
                break;
            case 3:
            case 4:
                desktopUiEventEnum = null;
                break;
            case 5:
                desktopUiEventEnum = DesktopModeUiEventLogger.DesktopUiEventEnum.DESKTOP_WINDOW_MAXIMIZE_BUTTON_MENU_TAP_TO_MAXIMIZE;
                break;
            case 6:
                desktopUiEventEnum = DesktopModeUiEventLogger.DesktopUiEventEnum.DESKTOP_WINDOW_MAXIMIZE_BUTTON_MENU_TAP_TO_RESTORE;
                break;
            case 7:
                desktopUiEventEnum = DesktopModeUiEventLogger.DesktopUiEventEnum.DESKTOP_WINDOW_HEADER_DOUBLE_TAP_TO_MAXIMIZE;
                break;
            case 8:
                desktopUiEventEnum = DesktopModeUiEventLogger.DesktopUiEventEnum.DESKTOP_WINDOW_HEADER_DOUBLE_TAP_TO_RESTORE;
                break;
            default:
                throw new NoWhenBranchMatchedException();
        }
        this.uiEvent = desktopUiEventEnum;
        switch (iArr[source.ordinal()]) {
            case 1:
                resizeTrigger = DesktopModeEventLogger.Companion.ResizeTrigger.MAXIMIZE_BUTTON;
                break;
            case 2:
                resizeTrigger = DesktopModeEventLogger.Companion.ResizeTrigger.MAXIMIZE_BUTTON;
                break;
            case 3:
                resizeTrigger = DesktopModeEventLogger.Companion.ResizeTrigger.UNKNOWN_RESIZE_TRIGGER;
                break;
            case 4:
                resizeTrigger = DesktopModeEventLogger.Companion.ResizeTrigger.DRAG_TO_TOP_RESIZE_TRIGGER;
                break;
            case 5:
                resizeTrigger = DesktopModeEventLogger.Companion.ResizeTrigger.MAXIMIZE_MENU;
                break;
            case 6:
                resizeTrigger = DesktopModeEventLogger.Companion.ResizeTrigger.MAXIMIZE_MENU;
                break;
            case 7:
                resizeTrigger = DesktopModeEventLogger.Companion.ResizeTrigger.DOUBLE_TAP_APP_HEADER;
                break;
            case 8:
                resizeTrigger = DesktopModeEventLogger.Companion.ResizeTrigger.DOUBLE_TAP_APP_HEADER;
                break;
            default:
                throw new NoWhenBranchMatchedException();
        }
        this.resizeTrigger = resizeTrigger;
        switch (iArr[source.ordinal()]) {
            case 1:
                num = 104;
                break;
            case 2:
                num = 119;
                break;
            case 3:
            case 4:
                break;
            case 5:
                num = 104;
                break;
            case 6:
                num = 119;
                break;
            case 7:
                num = 104;
                break;
            case 8:
                num = 119;
                break;
            default:
                throw new NoWhenBranchMatchedException();
        }
        this.cujTracing = num;
    }

    public /* synthetic */ ToggleTaskSizeInteraction(Direction direction, Source source, DesktopModeEventLogger.Companion.InputMethod inputMethod, Rect rect, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(direction, source, inputMethod, (i & 8) != 0 ? null : rect);
    }

    public ToggleTaskSizeInteraction(boolean z, Source source, DesktopModeEventLogger.Companion.InputMethod inputMethod) {
        this(z ? Direction.RESTORE : Direction.MAXIMIZE, source, inputMethod, null, 8, null);
    }
}
