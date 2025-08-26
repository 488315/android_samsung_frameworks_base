package android.graphics;

import android.util.proto.ProtoOutputStream;

/* loaded from: classes.dex */
public final class GraphicsProtos {
    private GraphicsProtos() {
    }

    public static void dumpPointProto(Point point, ProtoOutputStream protoOutputStream, long j) {
        long jStart = protoOutputStream.start(j);
        protoOutputStream.write(1120986464257L, point.x);
        protoOutputStream.write(1120986464258L, point.y);
        protoOutputStream.end(jStart);
    }
}
