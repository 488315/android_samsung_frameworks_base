package androidx.compose.ui.graphics;

import android.graphics.Path;
import android.graphics.RectF;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.geometry.RoundRect;
import androidx.compose.ui.graphics.AndroidPath_androidKt;
import kotlin.NoWhenBranchMatchedException;
import kotlin.enums.EnumEntriesKt;

/* loaded from: classes.dex */
public interface Path {

    public final class Companion {
        public static final /* synthetic */ int $r8$clinit = 0;

        static {
            new Companion();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class Direction {
        public static final /* synthetic */ Direction[] $VALUES;
        public static final Direction Clockwise;
        public static final Direction CounterClockwise;

        static {
            Direction direction = new Direction("CounterClockwise", 0);
            CounterClockwise = direction;
            Direction direction2 = new Direction("Clockwise", 1);
            Clockwise = direction2;
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

    static {
        int i = Companion.$r8$clinit;
    }

    /* renamed from: addPath-Uv8p0NA$default, reason: not valid java name */
    static void m493addPathUv8p0NA$default(Path path, Path path2) {
        Offset.Companion.getClass();
        android.graphics.Path path3 = ((AndroidPath) path).internalPath;
        if (!(path2 instanceof AndroidPath)) {
            throw new UnsupportedOperationException("Unable to obtain android.graphics.Path");
        }
        path3.addPath(((AndroidPath) path2).internalPath, Float.intBitsToFloat((int) 0), Float.intBitsToFloat((int) 0));
    }

    static void addRect$default(Path path, Rect rect) {
        Path.Direction direction;
        Direction direction2 = Direction.CounterClockwise;
        AndroidPath androidPath = (AndroidPath) path;
        boolean zIsNaN = Float.isNaN(rect.left);
        float f = rect.bottom;
        float f2 = rect.right;
        float f3 = rect.top;
        if (zIsNaN || Float.isNaN(f3) || Float.isNaN(f2) || Float.isNaN(f)) {
            AndroidPath_androidKt.throwIllegalStateException("Invalid rectangle, make sure no value is NaN");
        }
        if (androidPath.rectF == null) {
            androidPath.rectF = new RectF();
        }
        RectF rectF = androidPath.rectF;
        rectF.getClass();
        rectF.set(rect.left, f3, f2, f);
        android.graphics.Path path2 = androidPath.internalPath;
        RectF rectF2 = androidPath.rectF;
        rectF2.getClass();
        int i = AndroidPath_androidKt.WhenMappings.$EnumSwitchMapping$0[direction2.ordinal()];
        if (i == 1) {
            direction = Path.Direction.CCW;
        } else {
            if (i != 2) {
                throw new NoWhenBranchMatchedException();
            }
            direction = Path.Direction.CW;
        }
        path2.addRect(rectF2, direction);
    }

    static void addRoundRect$default(Path path, RoundRect roundRect) {
        Path.Direction direction;
        Direction direction2 = Direction.CounterClockwise;
        AndroidPath androidPath = (AndroidPath) path;
        if (androidPath.rectF == null) {
            androidPath.rectF = new RectF();
        }
        RectF rectF = androidPath.rectF;
        rectF.getClass();
        rectF.set(roundRect.left, roundRect.top, roundRect.right, roundRect.bottom);
        if (androidPath.radii == null) {
            androidPath.radii = new float[8];
        }
        float[] fArr = androidPath.radii;
        fArr.getClass();
        long j = roundRect.topLeftCornerRadius;
        fArr[0] = Float.intBitsToFloat((int) (j >> 32));
        fArr[1] = Float.intBitsToFloat((int) (j & 4294967295L));
        long j2 = roundRect.topRightCornerRadius;
        fArr[2] = Float.intBitsToFloat((int) (j2 >> 32));
        fArr[3] = Float.intBitsToFloat((int) (j2 & 4294967295L));
        long j3 = roundRect.bottomRightCornerRadius;
        fArr[4] = Float.intBitsToFloat((int) (j3 >> 32));
        fArr[5] = Float.intBitsToFloat((int) (j3 & 4294967295L));
        long j4 = roundRect.bottomLeftCornerRadius;
        fArr[6] = Float.intBitsToFloat((int) (j4 >> 32));
        fArr[7] = Float.intBitsToFloat((int) (j4 & 4294967295L));
        android.graphics.Path path2 = androidPath.internalPath;
        RectF rectF2 = androidPath.rectF;
        rectF2.getClass();
        float[] fArr2 = androidPath.radii;
        fArr2.getClass();
        int i = AndroidPath_androidKt.WhenMappings.$EnumSwitchMapping$0[direction2.ordinal()];
        if (i == 1) {
            direction = Path.Direction.CCW;
        } else {
            if (i != 2) {
                throw new NoWhenBranchMatchedException();
            }
            direction = Path.Direction.CW;
        }
        path2.addRoundRect(rectF2, fArr2, direction);
    }
}
