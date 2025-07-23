package android.gesture;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class GestureStroke {
    static final float TOUCH_TOLERANCE = 3.0f;
    public final RectF boundingBox;
    public final float length;
    private Path mCachedPath;
    public final float[] points;
    private final long[] timestamps;

    public GestureStroke(ArrayList<GesturePoint> arrayList) {
        int i;
        int size = arrayList.size();
        float[] fArr = new float[size * 2];
        long[] jArr = new long[size];
        RectF rectF = null;
        int i2 = 0;
        int i3 = 0;
        float f = 0.0f;
        while (i2 < size) {
            GesturePoint gesturePoint = arrayList.get(i2);
            int i4 = i2 * 2;
            fArr[i4] = gesturePoint.x;
            fArr[i4 + 1] = gesturePoint.y;
            jArr[i3] = gesturePoint.timestamp;
            if (rectF == null) {
                rectF = new RectF();
                rectF.top = gesturePoint.y;
                rectF.left = gesturePoint.x;
                rectF.right = gesturePoint.x;
                rectF.bottom = gesturePoint.y;
                i = i2;
                f = 0.0f;
            } else {
                int i5 = (i2 - 1) * 2;
                i = i2;
                float hypot = (float) (f + Math.hypot(gesturePoint.x - fArr[i5], gesturePoint.y - fArr[i5 + 1]));
                rectF.union(gesturePoint.x, gesturePoint.y);
                f = hypot;
            }
            i3++;
            i2 = i + 1;
        }
        this.timestamps = jArr;
        this.points = fArr;
        this.boundingBox = rectF;
        this.length = f;
    }

    private GestureStroke(RectF rectF, float f, float[] fArr, long[] jArr) {
        this.boundingBox = new RectF(rectF.left, rectF.top, rectF.right, rectF.bottom);
        this.length = f;
        this.points = (float[]) fArr.clone();
        this.timestamps = (long[]) jArr.clone();
    }

    public Object clone() {
        return new GestureStroke(this.boundingBox, this.length, this.points, this.timestamps);
    }

    void draw(Canvas canvas, Paint paint) {
        if (this.mCachedPath == null) {
            makePath();
        }
        canvas.drawPath(this.mCachedPath, paint);
    }

    public Path getPath() {
        if (this.mCachedPath == null) {
            makePath();
        }
        return this.mCachedPath;
    }

    private void makePath() {
        float[] fArr = this.points;
        int length = fArr.length;
        Path path = null;
        float f = 0.0f;
        float f2 = 0.0f;
        for (int i = 0; i < length; i += 2) {
            float f3 = fArr[i];
            float f4 = fArr[i + 1];
            if (path == null) {
                path = new Path();
                path.moveTo(f3, f4);
            } else {
                float abs = Math.abs(f3 - f);
                float abs2 = Math.abs(f4 - f2);
                if (abs >= 3.0f || abs2 >= 3.0f) {
                    path.quadTo(f, f2, (f3 + f) / 2.0f, (f4 + f2) / 2.0f);
                }
            }
            f = f3;
            f2 = f4;
        }
        this.mCachedPath = path;
    }

    public Path toPath(float f, float f2, int i) {
        float[] temporalSampling = GestureUtils.temporalSampling(this, i);
        RectF rectF = this.boundingBox;
        GestureUtils.translate(temporalSampling, -rectF.left, -rectF.top);
        float width = f / rectF.width();
        float height = f2 / rectF.height();
        if (width > height) {
            width = height;
        }
        GestureUtils.scale(temporalSampling, width, width);
        int length = temporalSampling.length;
        float f3 = 0.0f;
        Path path = null;
        float f4 = 0.0f;
        for (int i2 = 0; i2 < length; i2 += 2) {
            float f5 = temporalSampling[i2];
            float f6 = temporalSampling[i2 + 1];
            if (path == null) {
                Path path2 = new Path();
                path2.moveTo(f5, f6);
                path = path2;
            } else {
                float abs = Math.abs(f5 - f3);
                float abs2 = Math.abs(f6 - f4);
                if (abs >= 3.0f || abs2 >= 3.0f) {
                    path.quadTo(f3, f4, (f5 + f3) / 2.0f, (f6 + f4) / 2.0f);
                }
            }
            f3 = f5;
            f4 = f6;
        }
        return path;
    }

    void serialize(DataOutputStream dataOutputStream) throws IOException {
        float[] fArr = this.points;
        long[] jArr = this.timestamps;
        int length = fArr.length;
        dataOutputStream.writeInt(length / 2);
        for (int i = 0; i < length; i += 2) {
            dataOutputStream.writeFloat(fArr[i]);
            dataOutputStream.writeFloat(fArr[i + 1]);
            dataOutputStream.writeLong(jArr[i / 2]);
        }
    }

    static GestureStroke deserialize(DataInputStream dataInputStream) throws IOException {
        int readInt = dataInputStream.readInt();
        ArrayList arrayList = new ArrayList(readInt);
        for (int i = 0; i < readInt; i++) {
            arrayList.add(GesturePoint.deserialize(dataInputStream));
        }
        return new GestureStroke(arrayList);
    }

    public void clearPath() {
        Path path = this.mCachedPath;
        if (path != null) {
            path.rewind();
        }
    }

    public OrientedBoundingBox computeOrientedBoundingBox() {
        return GestureUtils.computeOrientedBoundingBox(this.points);
    }
}
