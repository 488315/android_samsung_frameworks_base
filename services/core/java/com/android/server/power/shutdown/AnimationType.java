package com.android.server.power.shutdown;

final class AnimationType {
    public static final /* synthetic */ AnimationType[] $VALUES;
    public static final AnimationType MAIN;
    public static final AnimationType MAIN_LOOP;
    public static final AnimationType SUB;
    public static final AnimationType SUB_LOOP;

    static {
        AnimationType animationType = new AnimationType("MAIN", 0);
        MAIN = animationType;
        AnimationType animationType2 = new AnimationType("SUB", 1);
        SUB = animationType2;
        AnimationType animationType3 = new AnimationType("MAIN_LOOP", 2);
        MAIN_LOOP = animationType3;
        AnimationType animationType4 = new AnimationType("SUB_LOOP", 3);
        SUB_LOOP = animationType4;
        $VALUES =
                new AnimationType[] {animationType, animationType2, animationType3, animationType4};
    }

    public static AnimationType valueOf(String str) {
        return (AnimationType) Enum.valueOf(AnimationType.class, str);
    }

    public static AnimationType[] values() {
        return (AnimationType[]) $VALUES.clone();
    }
}
