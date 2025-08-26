package android.ddm;

import android.os.Debug;
import android.util.Log;
import java.nio.ByteBuffer;
import org.apache.harmony.dalvik.ddmc.Chunk;
import org.apache.harmony.dalvik.ddmc.ChunkHandler;
import org.apache.harmony.dalvik.ddmc.DdmServer;

/* loaded from: classes.dex */
public class DdmHandleProfiling extends DdmHandle {
    private static final boolean DEBUG = false;
    public static final int CHUNK_MPRS = ChunkHandler.type("MPRS");
    public static final int CHUNK_MPRE = ChunkHandler.type("MPRE");
    public static final int CHUNK_MPSS = ChunkHandler.type("MPSS");
    public static final int CHUNK_MPSE = ChunkHandler.type("MPSE");
    public static final int CHUNK_MPRQ = ChunkHandler.type("MPRQ");
    public static final int CHUNK_SPSS = ChunkHandler.type("SPSS");
    public static final int CHUNK_SPSE = ChunkHandler.type("SPSE");
    private static DdmHandleProfiling mInstance = new DdmHandleProfiling();

    public void onConnected() {
    }

    public void onDisconnected() {
    }

    private DdmHandleProfiling() {
    }

    public static void register() {
        DdmServer.registerHandler(CHUNK_MPRS, mInstance);
        DdmServer.registerHandler(CHUNK_MPRE, mInstance);
        DdmServer.registerHandler(CHUNK_MPSS, mInstance);
        DdmServer.registerHandler(CHUNK_MPSE, mInstance);
        DdmServer.registerHandler(CHUNK_MPRQ, mInstance);
        DdmServer.registerHandler(CHUNK_SPSS, mInstance);
        DdmServer.registerHandler(CHUNK_SPSE, mInstance);
    }

    public Chunk handleChunk(Chunk chunk) {
        int i = chunk.type;
        if (i == CHUNK_MPRS) {
            return handleMPRS(chunk);
        }
        if (i == CHUNK_MPRE) {
            return handleMPRE(chunk);
        }
        if (i == CHUNK_MPSS) {
            return handleMPSS(chunk);
        }
        if (i == CHUNK_MPSE) {
            return handleMPSEOrSPSE(chunk, "Method");
        }
        if (i == CHUNK_MPRQ) {
            return handleMPRQ(chunk);
        }
        if (i == CHUNK_SPSS) {
            return handleSPSS(chunk);
        }
        if (i == CHUNK_SPSE) {
            return handleMPSEOrSPSE(chunk, "Sample");
        }
        throw new RuntimeException("Unknown packet " + name(i));
    }

    private Chunk handleMPRS(Chunk chunk) {
        ByteBuffer byteBufferWrapChunk = wrapChunk(chunk);
        try {
            Debug.startMethodTracing(getString(byteBufferWrapChunk, byteBufferWrapChunk.getInt()), byteBufferWrapChunk.getInt(), byteBufferWrapChunk.getInt());
            return null;
        } catch (RuntimeException e) {
            return createFailChunk(1, e.getMessage());
        }
    }

    private Chunk handleMPRE(Chunk chunk) {
        byte b;
        try {
            Debug.stopMethodTracing();
            b = 0;
        } catch (RuntimeException e) {
            Log.w("ddm-heap", "Method profiling end failed: " + e.getMessage());
            b = (byte) 1;
        }
        return new Chunk(CHUNK_MPRE, new byte[]{b}, 0, 1);
    }

    private Chunk handleMPSS(Chunk chunk) {
        ByteBuffer byteBufferWrapChunk = wrapChunk(chunk);
        try {
            Debug.startMethodTracingDdms(byteBufferWrapChunk.getInt(), byteBufferWrapChunk.getInt(), false, 0);
            return null;
        } catch (RuntimeException e) {
            return createFailChunk(1, e.getMessage());
        }
    }

    private Chunk handleMPSEOrSPSE(Chunk chunk, String str) {
        try {
            Debug.stopMethodTracing();
            return null;
        } catch (RuntimeException e) {
            Log.w("ddm-heap", str + " prof stream end failed: " + e.getMessage());
            return createFailChunk(1, e.getMessage());
        }
    }

    private Chunk handleMPRQ(Chunk chunk) {
        return new Chunk(CHUNK_MPRQ, new byte[]{(byte) Debug.getMethodTracingMode()}, 0, 1);
    }

    private Chunk handleSPSS(Chunk chunk) {
        ByteBuffer byteBufferWrapChunk = wrapChunk(chunk);
        try {
            Debug.startMethodTracingDdms(byteBufferWrapChunk.getInt(), byteBufferWrapChunk.getInt(), true, byteBufferWrapChunk.getInt());
            return null;
        } catch (RuntimeException e) {
            return createFailChunk(1, e.getMessage());
        }
    }
}
