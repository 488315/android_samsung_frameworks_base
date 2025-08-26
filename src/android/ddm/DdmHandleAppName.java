package android.ddm;

import java.nio.ByteBuffer;
import org.apache.harmony.dalvik.ddmc.Chunk;
import org.apache.harmony.dalvik.ddmc.ChunkHandler;
import org.apache.harmony.dalvik.ddmc.DdmServer;

/* loaded from: classes.dex */
public class DdmHandleAppName extends DdmHandle {
    public static final int CHUNK_APNM = ChunkHandler.type("APNM");
    private static DdmHandleAppName mInstance = new DdmHandleAppName();
    private static volatile Names sNames;

    public static void register() {
    }

    public Chunk handleChunk(Chunk chunk) {
        return null;
    }

    public void onConnected() {
    }

    public void onDisconnected() {
    }

    static {
        String str = "";
        sNames = new Names(str, str);
    }

    private DdmHandleAppName() {
    }

    public static void setAppName(String str, int i) {
        setAppName(str, str, i);
    }

    public static void setAppName(String str, String str2, int i) {
        if (str == null || str.isEmpty() || str2 == null || str2.isEmpty()) {
            return;
        }
        sNames = new Names(str, str2);
        sendAPNM(str, str2, i);
    }

    public static Names getNames() {
        return sNames;
    }

    private static void sendAPNM(String str, String str2, int i) {
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate((str.length() * 2) + 12 + (str2.length() * 2));
        byteBufferAllocate.order(ChunkHandler.CHUNK_ORDER);
        byteBufferAllocate.putInt(str.length());
        putString(byteBufferAllocate, str);
        byteBufferAllocate.putInt(i);
        byteBufferAllocate.putInt(str2.length());
        putString(byteBufferAllocate, str2);
        DdmServer.sendChunk(new Chunk(CHUNK_APNM, byteBufferAllocate));
    }

    static final class Names {
        private final String mAppName;
        private final String mPkgName;

        private Names(String str, String str2) {
            this.mAppName = str;
            this.mPkgName = str2;
        }

        public String getAppName() {
            return this.mAppName;
        }

        public String getPkgName() {
            return this.mPkgName;
        }
    }
}
