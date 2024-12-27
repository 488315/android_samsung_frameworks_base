package com.android.server.soundtrigger;

import android.util.Slog;

import com.android.server.utils.EventLogger;

import java.util.UUID;

public final class SoundTriggerEvent$ServiceEvent extends EventLogger.Event {
    public final /* synthetic */ int $r8$classId = 0;
    public final Object mErrorString;
    public final String mPackageName;
    public final Object mType;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class Type {
        public static final /* synthetic */ Type[] $VALUES;
        public static final Type ATTACH;
        public static final Type DETACH;
        public static final Type LIST_MODULE;

        static {
            Type type = new Type("ATTACH", 0);
            ATTACH = type;
            Type type2 = new Type("LIST_MODULE", 1);
            LIST_MODULE = type2;
            Type type3 = new Type("DETACH", 2);
            DETACH = type3;
            $VALUES = new Type[] {type, type2, type3};
        }

        public static Type valueOf(String str) {
            return (Type) Enum.valueOf(Type.class, str);
        }

        public static Type[] values() {
            return (Type[]) $VALUES.clone();
        }
    }

    public SoundTriggerEvent$ServiceEvent(Type type, String str, String str2) {
        this.mType = type;
        this.mPackageName = str;
        this.mErrorString = str2;
    }

    public SoundTriggerEvent$ServiceEvent(
            SoundTriggerEvent$SessionEvent$Type soundTriggerEvent$SessionEvent$Type,
            UUID uuid,
            String str) {
        this.mErrorString = soundTriggerEvent$SessionEvent$Type;
        this.mType = uuid;
        this.mPackageName = str;
    }

    @Override // com.android.server.utils.EventLogger.Event
    public final String eventToString() {
        switch (this.$r8$classId) {
            case 0:
                StringBuilder sb =
                        new StringBuilder(String.format("%-12s", ((Type) this.mType).name()));
                String str = (String) this.mErrorString;
                if (str != null) {
                    sb.append(" ERROR: ");
                    sb.append(str);
                }
                String str2 = this.mPackageName;
                if (str2 != null) {
                    sb.append(" for: ");
                    sb.append(str2);
                }
                return sb.toString();
            default:
                StringBuilder sb2 =
                        new StringBuilder(
                                String.format(
                                        "%-25s",
                                        ((SoundTriggerEvent$SessionEvent$Type) this.mErrorString)
                                                .name()));
                String str3 = this.mPackageName;
                if (str3 != null) {
                    sb2.append(" ERROR: ");
                    sb2.append(str3);
                }
                if (((UUID) this.mType) != null) {
                    sb2.append(" for: ");
                    sb2.append((UUID) this.mType);
                }
                return sb2.toString();
        }
    }

    @Override // com.android.server.utils.EventLogger.Event
    public final EventLogger.Event printLog(int i, String str) {
        if (i == 0) {
            Slog.i(str, eventToString());
        } else if (i == 1) {
            Slog.e(str, eventToString());
        } else if (i != 2) {
            Slog.v(str, eventToString());
        } else {
            Slog.w(str, eventToString());
        }
        return this;
    }
}
