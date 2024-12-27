package com.android.server.soundtrigger;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SoundTriggerEvent$SessionEvent$Type {
    public static final /* synthetic */ SoundTriggerEvent$SessionEvent$Type[] $VALUES;
    public static final SoundTriggerEvent$SessionEvent$Type DELETE_MODEL;
    public static final SoundTriggerEvent$SessionEvent$Type DETACH;
    public static final SoundTriggerEvent$SessionEvent$Type GET_MODEL_STATE;
    public static final SoundTriggerEvent$SessionEvent$Type GET_MODULE_PROPERTIES;
    public static final SoundTriggerEvent$SessionEvent$Type LOAD_MODEL;
    public static final SoundTriggerEvent$SessionEvent$Type MODULE_DIED;
    public static final SoundTriggerEvent$SessionEvent$Type PAUSE;
    public static final SoundTriggerEvent$SessionEvent$Type PAUSE_FAILED;
    public static final SoundTriggerEvent$SessionEvent$Type RECOGNITION;
    public static final SoundTriggerEvent$SessionEvent$Type RESOURCES_AVAILABLE;
    public static final SoundTriggerEvent$SessionEvent$Type RESUME;
    public static final SoundTriggerEvent$SessionEvent$Type RESUME_FAILED;
    public static final SoundTriggerEvent$SessionEvent$Type SET_PARAMETER;
    public static final SoundTriggerEvent$SessionEvent$Type START_RECOGNITION;
    public static final SoundTriggerEvent$SessionEvent$Type START_RECOGNITION_SERVICE;
    public static final SoundTriggerEvent$SessionEvent$Type STOP_RECOGNITION;
    public static final SoundTriggerEvent$SessionEvent$Type STOP_RECOGNITION_SERVICE;
    public static final SoundTriggerEvent$SessionEvent$Type UNLOAD_MODEL;
    public static final SoundTriggerEvent$SessionEvent$Type UPDATE_MODEL;

    static {
        SoundTriggerEvent$SessionEvent$Type soundTriggerEvent$SessionEvent$Type = new SoundTriggerEvent$SessionEvent$Type("START_RECOGNITION", 0);
        START_RECOGNITION = soundTriggerEvent$SessionEvent$Type;
        SoundTriggerEvent$SessionEvent$Type soundTriggerEvent$SessionEvent$Type2 = new SoundTriggerEvent$SessionEvent$Type("STOP_RECOGNITION", 1);
        STOP_RECOGNITION = soundTriggerEvent$SessionEvent$Type2;
        SoundTriggerEvent$SessionEvent$Type soundTriggerEvent$SessionEvent$Type3 = new SoundTriggerEvent$SessionEvent$Type("LOAD_MODEL", 2);
        LOAD_MODEL = soundTriggerEvent$SessionEvent$Type3;
        SoundTriggerEvent$SessionEvent$Type soundTriggerEvent$SessionEvent$Type4 = new SoundTriggerEvent$SessionEvent$Type("UNLOAD_MODEL", 3);
        UNLOAD_MODEL = soundTriggerEvent$SessionEvent$Type4;
        SoundTriggerEvent$SessionEvent$Type soundTriggerEvent$SessionEvent$Type5 = new SoundTriggerEvent$SessionEvent$Type("UPDATE_MODEL", 4);
        UPDATE_MODEL = soundTriggerEvent$SessionEvent$Type5;
        SoundTriggerEvent$SessionEvent$Type soundTriggerEvent$SessionEvent$Type6 = new SoundTriggerEvent$SessionEvent$Type("DELETE_MODEL", 5);
        DELETE_MODEL = soundTriggerEvent$SessionEvent$Type6;
        SoundTriggerEvent$SessionEvent$Type soundTriggerEvent$SessionEvent$Type7 = new SoundTriggerEvent$SessionEvent$Type("START_RECOGNITION_SERVICE", 6);
        START_RECOGNITION_SERVICE = soundTriggerEvent$SessionEvent$Type7;
        SoundTriggerEvent$SessionEvent$Type soundTriggerEvent$SessionEvent$Type8 = new SoundTriggerEvent$SessionEvent$Type("STOP_RECOGNITION_SERVICE", 7);
        STOP_RECOGNITION_SERVICE = soundTriggerEvent$SessionEvent$Type8;
        SoundTriggerEvent$SessionEvent$Type soundTriggerEvent$SessionEvent$Type9 = new SoundTriggerEvent$SessionEvent$Type("GET_MODEL_STATE", 8);
        GET_MODEL_STATE = soundTriggerEvent$SessionEvent$Type9;
        SoundTriggerEvent$SessionEvent$Type soundTriggerEvent$SessionEvent$Type10 = new SoundTriggerEvent$SessionEvent$Type("SET_PARAMETER", 9);
        SET_PARAMETER = soundTriggerEvent$SessionEvent$Type10;
        SoundTriggerEvent$SessionEvent$Type soundTriggerEvent$SessionEvent$Type11 = new SoundTriggerEvent$SessionEvent$Type("GET_MODULE_PROPERTIES", 10);
        GET_MODULE_PROPERTIES = soundTriggerEvent$SessionEvent$Type11;
        SoundTriggerEvent$SessionEvent$Type soundTriggerEvent$SessionEvent$Type12 = new SoundTriggerEvent$SessionEvent$Type("DETACH", 11);
        DETACH = soundTriggerEvent$SessionEvent$Type12;
        SoundTriggerEvent$SessionEvent$Type soundTriggerEvent$SessionEvent$Type13 = new SoundTriggerEvent$SessionEvent$Type("RECOGNITION", 12);
        RECOGNITION = soundTriggerEvent$SessionEvent$Type13;
        SoundTriggerEvent$SessionEvent$Type soundTriggerEvent$SessionEvent$Type14 = new SoundTriggerEvent$SessionEvent$Type("RESUME", 13);
        RESUME = soundTriggerEvent$SessionEvent$Type14;
        SoundTriggerEvent$SessionEvent$Type soundTriggerEvent$SessionEvent$Type15 = new SoundTriggerEvent$SessionEvent$Type("RESUME_FAILED", 14);
        RESUME_FAILED = soundTriggerEvent$SessionEvent$Type15;
        SoundTriggerEvent$SessionEvent$Type soundTriggerEvent$SessionEvent$Type16 = new SoundTriggerEvent$SessionEvent$Type("PAUSE", 15);
        PAUSE = soundTriggerEvent$SessionEvent$Type16;
        SoundTriggerEvent$SessionEvent$Type soundTriggerEvent$SessionEvent$Type17 = new SoundTriggerEvent$SessionEvent$Type("PAUSE_FAILED", 16);
        PAUSE_FAILED = soundTriggerEvent$SessionEvent$Type17;
        SoundTriggerEvent$SessionEvent$Type soundTriggerEvent$SessionEvent$Type18 = new SoundTriggerEvent$SessionEvent$Type("RESOURCES_AVAILABLE", 17);
        RESOURCES_AVAILABLE = soundTriggerEvent$SessionEvent$Type18;
        SoundTriggerEvent$SessionEvent$Type soundTriggerEvent$SessionEvent$Type19 = new SoundTriggerEvent$SessionEvent$Type("MODULE_DIED", 18);
        MODULE_DIED = soundTriggerEvent$SessionEvent$Type19;
        $VALUES = new SoundTriggerEvent$SessionEvent$Type[]{soundTriggerEvent$SessionEvent$Type, soundTriggerEvent$SessionEvent$Type2, soundTriggerEvent$SessionEvent$Type3, soundTriggerEvent$SessionEvent$Type4, soundTriggerEvent$SessionEvent$Type5, soundTriggerEvent$SessionEvent$Type6, soundTriggerEvent$SessionEvent$Type7, soundTriggerEvent$SessionEvent$Type8, soundTriggerEvent$SessionEvent$Type9, soundTriggerEvent$SessionEvent$Type10, soundTriggerEvent$SessionEvent$Type11, soundTriggerEvent$SessionEvent$Type12, soundTriggerEvent$SessionEvent$Type13, soundTriggerEvent$SessionEvent$Type14, soundTriggerEvent$SessionEvent$Type15, soundTriggerEvent$SessionEvent$Type16, soundTriggerEvent$SessionEvent$Type17, soundTriggerEvent$SessionEvent$Type18, soundTriggerEvent$SessionEvent$Type19};
    }

    public static SoundTriggerEvent$SessionEvent$Type valueOf(String str) {
        return (SoundTriggerEvent$SessionEvent$Type) Enum.valueOf(SoundTriggerEvent$SessionEvent$Type.class, str);
    }

    public static SoundTriggerEvent$SessionEvent$Type[] values() {
        return (SoundTriggerEvent$SessionEvent$Type[]) $VALUES.clone();
    }
}
