package android.ddm;

import org.apache.harmony.dalvik.ddmc.Chunk;
import org.apache.harmony.dalvik.ddmc.ChunkHandler;
import org.apache.harmony.dalvik.ddmc.DdmServer;

/* loaded from: classes.dex */
public class DdmHandleHeap extends DdmHandle {
    public static final int CHUNK_HPGC = ChunkHandler.type("HPGC");
    private static DdmHandleHeap mInstance = new DdmHandleHeap();

    public void onConnected() {
    }

    public void onDisconnected() {
    }

    private DdmHandleHeap() {
    }

    public static void register() {
        DdmServer.registerHandler(CHUNK_HPGC, mInstance);
    }

    public Chunk handleChunk(Chunk chunk) {
        int i = chunk.type;
        if (i == CHUNK_HPGC) {
            return handleHPGC(chunk);
        }
        throw new RuntimeException("Unknown packet " + name(i));
    }

    private Chunk handleHPGC(Chunk chunk) {
        Runtime.getRuntime().gc();
        return null;
    }
}
