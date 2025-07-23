package android.gesture;

import java.io.DataInputStream;
import java.io.IOException;

/* loaded from: classes.dex */
public class GesturePoint {
    public final long timestamp;
    public final float x;
    public final float y;

    public GesturePoint(float f, float f2, long j) {
        this.x = f;
        this.y = f2;
        this.timestamp = j;
    }

    static GesturePoint deserialize(DataInputStream dataInputStream) throws IOException {
        return new GesturePoint(dataInputStream.readFloat(), dataInputStream.readFloat(), dataInputStream.readLong());
    }

    public Object clone() {
        return new GesturePoint(this.x, this.y, this.timestamp);
    }
}
