package android.ddm;

import org.apache.harmony.dalvik.ddmc.Chunk;
import org.apache.harmony.dalvik.ddmc.ChunkHandler;
import org.apache.harmony.dalvik.ddmc.DdmServer;

/* loaded from: classes.dex */
public class DdmHandleExit extends DdmHandle {
    public static final int CHUNK_EXIT = ChunkHandler.type("EXIT");
    private static DdmHandleExit mInstance = new DdmHandleExit();

    public void onConnected() {
    }

    public void onDisconnected() {
    }

    private DdmHandleExit() {
    }

    public static void register() {
        DdmServer.registerHandler(CHUNK_EXIT, mInstance);
    }

    public Chunk handleChunk(Chunk chunk) {
        Runtime.getRuntime().halt(wrapChunk(chunk).getInt());
        return null;
    }
}
