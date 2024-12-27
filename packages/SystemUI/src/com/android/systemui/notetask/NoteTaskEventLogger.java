package com.android.systemui.notetask;

import com.android.internal.logging.UiEventLogger;
import kotlin.NoWhenBranchMatchedException;
import kotlin.enums.EnumEntriesKt;

public final class NoteTaskEventLogger {
    public final UiEventLogger uiEventLogger;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class NoteTaskUiEvent implements UiEventLogger.UiEventEnum {
        public static final /* synthetic */ NoteTaskUiEvent[] $VALUES;
        public static final NoteTaskUiEvent NOTE_CLOSED_VIA_STYLUS_TAIL_BUTTON;
        public static final NoteTaskUiEvent NOTE_CLOSED_VIA_STYLUS_TAIL_BUTTON_LOCKED;
        public static final NoteTaskUiEvent NOTE_OPENED_VIA_KEYGUARD_QUICK_AFFORDANCE;
        public static final NoteTaskUiEvent NOTE_OPENED_VIA_SHORTCUT;
        public static final NoteTaskUiEvent NOTE_OPENED_VIA_STYLUS_TAIL_BUTTON;
        public static final NoteTaskUiEvent NOTE_OPENED_VIA_STYLUS_TAIL_BUTTON_LOCKED;
        private final int _id;

        static {
            NoteTaskUiEvent noteTaskUiEvent = new NoteTaskUiEvent("NOTE_OPENED_VIA_KEYGUARD_QUICK_AFFORDANCE", 0, 1294);
            NOTE_OPENED_VIA_KEYGUARD_QUICK_AFFORDANCE = noteTaskUiEvent;
            NoteTaskUiEvent noteTaskUiEvent2 = new NoteTaskUiEvent("NOTE_OPENED_VIA_STYLUS_TAIL_BUTTON", 1, 1295);
            NOTE_OPENED_VIA_STYLUS_TAIL_BUTTON = noteTaskUiEvent2;
            NoteTaskUiEvent noteTaskUiEvent3 = new NoteTaskUiEvent("NOTE_OPENED_VIA_STYLUS_TAIL_BUTTON_LOCKED", 2, 1296);
            NOTE_OPENED_VIA_STYLUS_TAIL_BUTTON_LOCKED = noteTaskUiEvent3;
            NoteTaskUiEvent noteTaskUiEvent4 = new NoteTaskUiEvent("NOTE_OPENED_VIA_SHORTCUT", 3, 1297);
            NOTE_OPENED_VIA_SHORTCUT = noteTaskUiEvent4;
            NoteTaskUiEvent noteTaskUiEvent5 = new NoteTaskUiEvent("NOTE_CLOSED_VIA_STYLUS_TAIL_BUTTON", 4, 1311);
            NOTE_CLOSED_VIA_STYLUS_TAIL_BUTTON = noteTaskUiEvent5;
            NoteTaskUiEvent noteTaskUiEvent6 = new NoteTaskUiEvent("NOTE_CLOSED_VIA_STYLUS_TAIL_BUTTON_LOCKED", 5, 1312);
            NOTE_CLOSED_VIA_STYLUS_TAIL_BUTTON_LOCKED = noteTaskUiEvent6;
            NoteTaskUiEvent[] noteTaskUiEventArr = {noteTaskUiEvent, noteTaskUiEvent2, noteTaskUiEvent3, noteTaskUiEvent4, noteTaskUiEvent5, noteTaskUiEvent6};
            $VALUES = noteTaskUiEventArr;
            EnumEntriesKt.enumEntries(noteTaskUiEventArr);
        }

        private NoteTaskUiEvent(String str, int i, int i2) {
            this._id = i2;
        }

        public static NoteTaskUiEvent valueOf(String str) {
            return (NoteTaskUiEvent) Enum.valueOf(NoteTaskUiEvent.class, str);
        }

        public static NoteTaskUiEvent[] values() {
            return (NoteTaskUiEvent[]) $VALUES.clone();
        }

        public final int getId() {
            return this._id;
        }
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[NoteTaskEntryPoint.values().length];
            try {
                iArr[NoteTaskEntryPoint.TAIL_BUTTON.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[NoteTaskEntryPoint.WIDGET_PICKER_SHORTCUT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[NoteTaskEntryPoint.WIDGET_PICKER_SHORTCUT_IN_MULTI_WINDOW_MODE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[NoteTaskEntryPoint.QUICK_AFFORDANCE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[NoteTaskEntryPoint.APP_CLIPS.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[NoteTaskEntryPoint.KEYBOARD_SHORTCUT.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public NoteTaskEventLogger(UiEventLogger uiEventLogger) {
        this.uiEventLogger = uiEventLogger;
    }

    public final void logNoteTaskClosed(NoteTaskInfo noteTaskInfo) {
        NoteTaskEntryPoint noteTaskEntryPoint = noteTaskInfo.entryPoint;
        switch (noteTaskEntryPoint == null ? -1 : WhenMappings.$EnumSwitchMapping$0[noteTaskEntryPoint.ordinal()]) {
            case -1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                return;
            case 0:
            default:
                throw new NoWhenBranchMatchedException();
            case 1:
                this.uiEventLogger.log(noteTaskInfo.isKeyguardLocked ? NoteTaskUiEvent.NOTE_CLOSED_VIA_STYLUS_TAIL_BUTTON_LOCKED : NoteTaskUiEvent.NOTE_CLOSED_VIA_STYLUS_TAIL_BUTTON, noteTaskInfo.uid, noteTaskInfo.packageName);
                return;
        }
    }

    public final void logNoteTaskOpened(NoteTaskInfo noteTaskInfo) {
        NoteTaskUiEvent noteTaskUiEvent;
        NoteTaskEntryPoint noteTaskEntryPoint = noteTaskInfo.entryPoint;
        switch (noteTaskEntryPoint == null ? -1 : WhenMappings.$EnumSwitchMapping$0[noteTaskEntryPoint.ordinal()]) {
            case -1:
            case 5:
            case 6:
                return;
            case 0:
            default:
                throw new NoWhenBranchMatchedException();
            case 1:
                if (!noteTaskInfo.isKeyguardLocked) {
                    noteTaskUiEvent = NoteTaskUiEvent.NOTE_OPENED_VIA_STYLUS_TAIL_BUTTON;
                    break;
                } else {
                    noteTaskUiEvent = NoteTaskUiEvent.NOTE_OPENED_VIA_STYLUS_TAIL_BUTTON_LOCKED;
                    break;
                }
            case 2:
            case 3:
                noteTaskUiEvent = NoteTaskUiEvent.NOTE_OPENED_VIA_SHORTCUT;
                break;
            case 4:
                noteTaskUiEvent = NoteTaskUiEvent.NOTE_OPENED_VIA_KEYGUARD_QUICK_AFFORDANCE;
                break;
        }
        this.uiEventLogger.log(noteTaskUiEvent, noteTaskInfo.uid, noteTaskInfo.packageName);
    }
}
