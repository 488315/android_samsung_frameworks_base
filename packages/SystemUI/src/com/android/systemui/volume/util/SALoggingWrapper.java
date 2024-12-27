package com.android.systemui.volume.util;

import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.util.SystemUIAnalytics;
import kotlin.enums.EnumEntriesKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class SALoggingWrapper {

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Event {
        public static final /* synthetic */ Event[] $VALUES;
        public static final Event EXPAND;
        public static final Event FINE_CONTROL_ACCESSIBILITY;
        public static final Event FINE_CONTROL_BIXBY;
        public static final Event FINE_CONTROL_MEDIA;
        public static final Event FINE_CONTROL_NOTIFICATION;
        public static final Event FINE_CONTROL_RINGTONE;
        public static final Event FINE_CONTROL_SYSTEM;
        public static final Event MEDIA_DEFAULT_OFF;
        public static final Event MEDIA_DEFAULT_ON;
        public static final Event SAFETY_CANCEL;
        public static final Event SAFETY_OK;
        public static final Event SHRINK;
        public static final Event SUB_VOLUME_PANEL_FINE_CONTROL;
        public static final Event SUB_VOLUME_PANEL_SHOW;
        public static final Event VOLUME_LIMITER_CANCEL;
        public static final Event VOLUME_LIMITER_SETTING;

        static {
            Event event = new Event("EXPAND", 0);
            EXPAND = event;
            Event event2 = new Event("SHRINK", 1);
            SHRINK = event2;
            Event event3 = new Event("MEDIA_DEFAULT_ON", 2);
            MEDIA_DEFAULT_ON = event3;
            Event event4 = new Event("MEDIA_DEFAULT_OFF", 3);
            MEDIA_DEFAULT_OFF = event4;
            Event event5 = new Event("SAFETY_CANCEL", 4);
            SAFETY_CANCEL = event5;
            Event event6 = new Event("SAFETY_OK", 5);
            SAFETY_OK = event6;
            Event event7 = new Event("VOLUME_LIMITER_SETTING", 6);
            VOLUME_LIMITER_SETTING = event7;
            Event event8 = new Event("VOLUME_LIMITER_CANCEL", 7);
            VOLUME_LIMITER_CANCEL = event8;
            Event event9 = new Event("FINE_CONTROL_RINGTONE", 8);
            FINE_CONTROL_RINGTONE = event9;
            Event event10 = new Event("FINE_CONTROL_NOTIFICATION", 9);
            FINE_CONTROL_NOTIFICATION = event10;
            Event event11 = new Event("FINE_CONTROL_MEDIA", 10);
            FINE_CONTROL_MEDIA = event11;
            Event event12 = new Event("FINE_CONTROL_SYSTEM", 11);
            FINE_CONTROL_SYSTEM = event12;
            Event event13 = new Event("FINE_CONTROL_BIXBY", 12);
            FINE_CONTROL_BIXBY = event13;
            Event event14 = new Event("FINE_CONTROL_ACCESSIBILITY", 13);
            FINE_CONTROL_ACCESSIBILITY = event14;
            Event event15 = new Event("SUB_VOLUME_PANEL_SHOW", 14);
            SUB_VOLUME_PANEL_SHOW = event15;
            Event event16 = new Event("SUB_VOLUME_PANEL_FINE_CONTROL", 15);
            SUB_VOLUME_PANEL_FINE_CONTROL = event16;
            Event[] eventArr = {event, event2, event3, event4, event5, event6, event7, event8, event9, event10, event11, event12, event13, event14, event15, event16};
            $VALUES = eventArr;
            EnumEntriesKt.enumEntries(eventArr);
        }

        private Event(String str, int i) {
        }

        public static Event valueOf(String str) {
            return (Event) Enum.valueOf(Event.class, str);
        }

        public static Event[] values() {
            return (Event[]) $VALUES.clone();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Event.values().length];
            try {
                iArr[Event.SUB_VOLUME_PANEL_SHOW.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Event.EXPAND.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[Event.SHRINK.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[Event.MEDIA_DEFAULT_OFF.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[Event.MEDIA_DEFAULT_ON.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[Event.SAFETY_CANCEL.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[Event.SAFETY_OK.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[Event.VOLUME_LIMITER_CANCEL.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[Event.VOLUME_LIMITER_SETTING.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[Event.FINE_CONTROL_RINGTONE.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr[Event.FINE_CONTROL_NOTIFICATION.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr[Event.FINE_CONTROL_MEDIA.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                iArr[Event.FINE_CONTROL_SYSTEM.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                iArr[Event.FINE_CONTROL_BIXBY.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                iArr[Event.FINE_CONTROL_ACCESSIBILITY.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                iArr[Event.SUB_VOLUME_PANEL_FINE_CONTROL.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static void sendEventLog(Event event) {
        switch (WhenMappings.$EnumSwitchMapping$0[event.ordinal()]) {
            case 1:
                if (BasicRune.VOLUME_SUB_DISPLAY_VOLUME_DIALOG && !((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened) {
                    SystemUIAnalytics.sendEventLog(SystemUIAnalytics.SID_SUBSCREEN_NORMAL, SystemUIAnalytics.EID_VOLUME_PANEL_COVER_SHOW);
                    break;
                }
                break;
            case 2:
                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.SID_VOLUME_PANEL_OPENED, SystemUIAnalytics.EID_VOLUME_PANEL_EXPAND);
                break;
            case 3:
                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.SID_VOLUME_PANEL_OPENED, SystemUIAnalytics.EID_VOLUME_PANEL_SHRINK);
                break;
            case 4:
                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.SID_VOLUME_PANEL_OPENED, SystemUIAnalytics.EID_VOLUME_PANEL_MEDIA_SWITCH, 2L);
                break;
            case 5:
                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.SID_VOLUME_PANEL_OPENED, SystemUIAnalytics.EID_VOLUME_PANEL_MEDIA_SWITCH, 1L);
                break;
            case 6:
                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.SID_VOLUME_PANEL_OPENED, SystemUIAnalytics.EID_VOLUME_PANEL_EAR_SHOCK_CANCEL);
                break;
            case 7:
                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.SID_VOLUME_PANEL_OPENED, SystemUIAnalytics.EID_VOLUME_PANEL_EAR_SHOCK_OK);
                break;
            case 8:
                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.SID_VOLUME_PANEL_OPENED, SystemUIAnalytics.EID_VOLUME_PANEL_LIMITER_CANCEL);
                break;
            case 9:
                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.SID_VOLUME_PANEL_OPENED, SystemUIAnalytics.EID_VOLUME_PANEL_LIMITER_SETTING);
                break;
            case 10:
                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.SID_VOLUME_PANEL_OPENED, SystemUIAnalytics.EID_VOLUME_PANEL_FINE_VOLUME_RINGTONE);
                break;
            case 11:
                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.SID_VOLUME_PANEL_OPENED, SystemUIAnalytics.EID_VOLUME_PANEL_FINE_VOLUME_NOTIFICATION);
                break;
            case 12:
                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.SID_VOLUME_PANEL_OPENED, SystemUIAnalytics.EID_VOLUME_PANEL_FINE_VOLUME_MEDIA);
                break;
            case 13:
                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.SID_VOLUME_PANEL_OPENED, SystemUIAnalytics.EID_VOLUME_PANEL_FINE_VOLUME_SYSTEM);
                break;
            case 14:
                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.SID_VOLUME_PANEL_OPENED, SystemUIAnalytics.EID_VOLUME_PANEL_FINE_VOLUME_BIXBY);
                break;
            case 15:
                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.SID_VOLUME_PANEL_OPENED, SystemUIAnalytics.EID_VOLUME_PANEL_FINE_VOLUME_ACCESSIBILITY);
                break;
            case 16:
                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.SID_SUBSCREEN_NORMAL, SystemUIAnalytics.EID_VOLUME_PANEL_COVER_FINE_CONTROL);
                break;
        }
    }
}
