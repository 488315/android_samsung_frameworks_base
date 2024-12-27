package com.android.server.power.shutdown;

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
