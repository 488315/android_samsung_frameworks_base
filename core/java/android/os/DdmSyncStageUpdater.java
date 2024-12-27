package android.os;

import android.util.Slog;

import org.apache.harmony.dalvik.ddmc.Chunk;
import org.apache.harmony.dalvik.ddmc.ChunkHandler;
import org.apache.harmony.dalvik.ddmc.DdmServer;

import java.nio.ByteBuffer;

public class DdmSyncStageUpdater {
    private static final int CHUNK_STAGE = ChunkHandler.type("STAG");
    private static final String TAG = "DdmSyncStageUpdater";

    public synchronized void next(DdmSyncState.Stage stage) {
        try {
            DdmSyncState.next(stage);
            ByteBuffer data = ByteBuffer.allocate(4);
            data.putInt(stage.toInt());
            Chunk stagChunk = new Chunk(CHUNK_STAGE, data);
            DdmServer.sendChunk(stagChunk);
        } catch (Exception e) {
            Slog.w(TAG, "Unable to go to next stage" + stage, e);
        }
    }
}
