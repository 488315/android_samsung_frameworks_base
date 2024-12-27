package android.hardware.face;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes2.dex */
public final class FaceEnrollStages {
    public static final int ENROLLING_MOVEMENT_1 = 4;
    public static final int ENROLLING_MOVEMENT_2 = 5;
    public static final int ENROLLMENT_ENROLLING_MOVEMENT_DOWN = 102;
    public static final int ENROLLMENT_ENROLLING_MOVEMENT_FRONT = 100;
    public static final int ENROLLMENT_ENROLLING_MOVEMENT_LEFT = 103;
    public static final int ENROLLMENT_ENROLLING_MOVEMENT_RIGHT = 104;
    public static final int ENROLLMENT_ENROLLING_MOVEMENT_UP = 101;
    public static final int ENROLLMENT_FINISHED = 6;
    public static final int ENROLLMENT_WAITING_FOR_FACE_DOWN = 112;
    public static final int ENROLLMENT_WAITING_FOR_FACE_FRONT = 110;
    public static final int ENROLLMENT_WAITING_FOR_FACE_LEFT = 113;
    public static final int ENROLLMENT_WAITING_FOR_FACE_RIGHT = 114;
    public static final int ENROLLMENT_WAITING_FOR_FACE_UP = 111;
    public static final int FIRST_FRAME_RECEIVED = 1;
    public static final int HOLD_STILL_IN_CENTER = 3;
    public static final int UNKNOWN = 0;
    public static final int WAITING_FOR_CENTERING = 2;

    @Retention(RetentionPolicy.SOURCE)
    public @interface FaceEnrollStage {
    }

    private FaceEnrollStages() {
    }
}
