package android.gesture;

import java.io.DataInputStream;
import java.io.IOException;

/* loaded from: classes.dex */
public class GesturePoint {
    public final long timestamp;

    /* renamed from: x */
    public final float f67x;

    /* renamed from: y */
    public final float f68y;

    public GesturePoint(float x, float y, long t) {
        this.f67x = x;
        this.f68y = y;
        this.timestamp = t;
    }

    static GesturePoint deserialize(DataInputStream in) throws IOException {
        float x = in.readFloat();
        float y = in.readFloat();
        long timeStamp = in.readLong();
        return new GesturePoint(x, y, timeStamp);
    }

    public Object clone() {
        return new GesturePoint(this.f67x, this.f68y, this.timestamp);
    }
}
