package android.gesture;

import java.io.DataInputStream;
import java.io.IOException;

/* loaded from: classes.dex */
public class GesturePoint {
    public final long timestamp;
    public final float x;
    public final float y;

    public GesturePoint(float x, float y, long t) {
        this.x = x;
        this.y = y;
        this.timestamp = t;
    }

    static GesturePoint deserialize(DataInputStream in) throws IOException {
        float x = in.readFloat();
        float y = in.readFloat();
        long timeStamp = in.readLong();
        return new GesturePoint(x, y, timeStamp);
    }

    public Object clone() {
        return new GesturePoint(this.x, this.y, this.timestamp);
    }
}
