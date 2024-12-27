package android.graphics;

import android.util.proto.ProtoOutputStream;

public final class GraphicsProtos {
    private GraphicsProtos() {}

    public static void dumpPointProto(
            Point point, ProtoOutputStream protoOutputStream, long fieldId) {
        long token = protoOutputStream.start(fieldId);
        protoOutputStream.write(1120986464257L, point.x);
        protoOutputStream.write(1120986464258L, point.y);
        protoOutputStream.end(token);
    }
}
