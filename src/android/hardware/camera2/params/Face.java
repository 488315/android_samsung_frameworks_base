package android.hardware.camera2.params;

import android.graphics.Point;
import android.graphics.Rect;

/* loaded from: classes2.dex */
public final class Face {
    public static final int ID_UNSUPPORTED = -1;
    public static final int SCORE_MAX = 100;
    public static final int SCORE_MIN = 1;
    private Rect mBounds;
    private int mId;
    private Point mLeftEye;
    private Point mMouth;
    private Point mRightEye;
    private int mScore;

    public Face(Rect rect, int i, int i2, Point point, Point point2, Point point3) {
        init(rect, i, i2, point, point2, point3);
    }

    public Face(Rect rect, int i) {
        init(rect, i, -1, null, null, null);
    }

    private void init(Rect rect, int i, int i2, Point point, Point point2, Point point3) {
        checkNotNull("bounds", rect);
        checkScore(i);
        checkId(i2);
        if (i2 == -1) {
            checkNull("leftEyePosition", point);
            checkNull("rightEyePosition", point2);
            checkNull("mouthPosition", point3);
        }
        checkFace(point, point2, point3);
        this.mBounds = rect;
        this.mScore = i;
        this.mId = i2;
        this.mLeftEye = point;
        this.mRightEye = point2;
        this.mMouth = point3;
    }

    public Rect getBounds() {
        return this.mBounds;
    }

    public int getScore() {
        return this.mScore;
    }

    public int getId() {
        return this.mId;
    }

    public Point getLeftEyePosition() {
        return this.mLeftEye;
    }

    public Point getRightEyePosition() {
        return this.mRightEye;
    }

    public Point getMouthPosition() {
        return this.mMouth;
    }

    public String toString() {
        return String.format("{ bounds: %s, score: %s, id: %d, leftEyePosition: %s, rightEyePosition: %s, mouthPosition: %s }", this.mBounds, Integer.valueOf(this.mScore), Integer.valueOf(this.mId), this.mLeftEye, this.mRightEye, this.mMouth);
    }

    private static void checkNotNull(String str, Object obj) {
        if (obj != null) {
            return;
        }
        throw new IllegalArgumentException(str + " was required, but it was null");
    }

    private static void checkNull(String str, Object obj) {
        if (obj == null) {
            return;
        }
        throw new IllegalArgumentException(str + " was required to be null, but it wasn't");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void checkScore(int i) {
        if (i < 1 || i > 100) {
            throw new IllegalArgumentException("Confidence out of range");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void checkId(int i) {
        if (i < 0 && i != -1) {
            throw new IllegalArgumentException("Id out of range");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void checkFace(Point point, Point point2, Point point3) {
        if (point == null && point2 == null && point3 == null) {
            return;
        }
        if (point == null || point2 == null || point3 == null) {
            throw new IllegalArgumentException("If any of leftEyePosition, rightEyePosition, or mouthPosition are non-null, all three must be non-null.");
        }
    }

    public static final class Builder {
        private static final long FIELD_BOUNDS = 2;
        private static final long FIELD_BUILT = 1;
        private static final long FIELD_ID = 8;
        private static final long FIELD_LEFT_EYE = 16;
        private static final long FIELD_MOUTH = 64;
        private static final String FIELD_NAME_BOUNDS = "bounds";
        private static final String FIELD_NAME_LEFT_EYE = "left eye";
        private static final String FIELD_NAME_MOUTH = "mouth";
        private static final String FIELD_NAME_RIGHT_EYE = "right eye";
        private static final String FIELD_NAME_SCORE = "score";
        private static final long FIELD_RIGHT_EYE = 32;
        private static final long FIELD_SCORE = 4;
        private Rect mBounds;
        private long mBuilderFieldsSet;
        private int mId;
        private Point mLeftEye;
        private Point mMouth;
        private Point mRightEye;
        private int mScore;

        public Builder() {
            this.mBuilderFieldsSet = 0L;
            this.mBounds = null;
            this.mScore = 0;
            this.mId = -1;
            this.mLeftEye = null;
            this.mRightEye = null;
            this.mMouth = null;
        }

        public Builder(Face face) {
            this.mBuilderFieldsSet = 0L;
            this.mBounds = null;
            this.mScore = 0;
            this.mId = -1;
            this.mLeftEye = null;
            this.mRightEye = null;
            this.mMouth = null;
            this.mBounds = face.mBounds;
            this.mScore = face.mScore;
            this.mId = face.mId;
            this.mLeftEye = face.mLeftEye;
            this.mRightEye = face.mRightEye;
            this.mMouth = face.mMouth;
        }

        public Builder setBounds(Rect rect) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mBounds = rect;
            return this;
        }

        public Builder setScore(int i) {
            checkNotUsed();
            Face.checkScore(i);
            this.mBuilderFieldsSet |= 4;
            this.mScore = i;
            return this;
        }

        public Builder setId(int i) {
            checkNotUsed();
            Face.checkId(i);
            this.mBuilderFieldsSet |= 8;
            this.mId = i;
            return this;
        }

        public Builder setLeftEyePosition(Point point) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 16;
            this.mLeftEye = point;
            return this;
        }

        public Builder setRightEyePosition(Point point) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 32;
            this.mRightEye = point;
            return this;
        }

        public Builder setMouthPosition(Point point) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 64;
            this.mMouth = point;
            return this;
        }

        public Face build() {
            checkNotUsed();
            checkFieldSet(2L, FIELD_NAME_BOUNDS);
            checkFieldSet(4L, "score");
            if (this.mId == -1) {
                checkIdUnsupportedThenNull(this.mLeftEye, FIELD_NAME_LEFT_EYE);
                checkIdUnsupportedThenNull(this.mRightEye, FIELD_NAME_RIGHT_EYE);
                checkIdUnsupportedThenNull(this.mMouth, FIELD_NAME_MOUTH);
            }
            Face.checkFace(this.mLeftEye, this.mRightEye, this.mMouth);
            this.mBuilderFieldsSet |= 1;
            return new Face(this.mBounds, this.mScore, this.mId, this.mLeftEye, this.mRightEye, this.mMouth);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 1) != 0) {
                throw new IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }

        private void checkFieldSet(long j, String str) {
            if ((this.mBuilderFieldsSet & j) != 0) {
                return;
            }
            throw new IllegalStateException("Field \"" + str + "\" must be set before building.");
        }

        private void checkIdUnsupportedThenNull(Object obj, String str) {
            if (obj == null) {
                return;
            }
            throw new IllegalArgumentException("Field \"" + str + "\" must be unset or null if id is ID_UNSUPPORTED.");
        }
    }
}
