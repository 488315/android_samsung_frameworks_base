package com.android.server.power.shutdown;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
final class AnimationStatus {
    public static final /* synthetic */ AnimationStatus[] $VALUES;
    public static final AnimationStatus FINISH;
    public static final AnimationStatus IDLE;
    public static final AnimationStatus LOAD;
    public static final AnimationStatus START;
    public static final AnimationStatus STOP;

    static {
        AnimationStatus animationStatus = new AnimationStatus("IDLE", 0);
        IDLE = animationStatus;
        AnimationStatus animationStatus2 = new AnimationStatus("LOAD", 1);
        LOAD = animationStatus2;
        AnimationStatus animationStatus3 = new AnimationStatus("START", 2);
        START = animationStatus3;
        AnimationStatus animationStatus4 = new AnimationStatus("STOP", 3);
        STOP = animationStatus4;
        AnimationStatus animationStatus5 = new AnimationStatus("FINISH", 4);
        FINISH = animationStatus5;
        $VALUES =
                new AnimationStatus[] {
                    animationStatus,
                    animationStatus2,
                    animationStatus3,
                    animationStatus4,
                    animationStatus5
                };
    }

    public static AnimationStatus valueOf(String str) {
        return (AnimationStatus) Enum.valueOf(AnimationStatus.class, str);
    }

    public static AnimationStatus[] values() {
        return (AnimationStatus[]) $VALUES.clone();
    }
}
