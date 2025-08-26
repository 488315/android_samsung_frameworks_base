package android.ddm;

import android.ddm.DdmHandleAppName;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.DdmSyncState;
import android.os.Debug;
import android.os.Process;
import android.os.UserHandle;
import dalvik.system.VMRuntime;
import java.nio.ByteBuffer;
import org.apache.harmony.dalvik.ddmc.Chunk;
import org.apache.harmony.dalvik.ddmc.ChunkHandler;
import org.apache.harmony.dalvik.ddmc.DdmServer;

/* loaded from: classes.dex */
public class DdmHandleHello extends DdmHandle {
    private static final int CLIENT_PROTOCOL_VERSION = 1;
    public static final int CHUNK_HELO = ChunkHandler.type("HELO");
    public static final int CHUNK_WAIT = ChunkHandler.type("WAIT");
    public static final int CHUNK_FEAT = ChunkHandler.type("FEAT");
    private static DdmHandleHello mInstance = new DdmHandleHello();

    public void onConnected() {
    }

    public void onDisconnected() {
    }

    private DdmHandleHello() {
    }

    public static void register() {
        DdmServer.registerHandler(CHUNK_HELO, mInstance);
        DdmServer.registerHandler(CHUNK_FEAT, mInstance);
    }

    public Chunk handleChunk(Chunk chunk) {
        int i = chunk.type;
        if (i == CHUNK_HELO) {
            return handleHELO(chunk);
        }
        if (i == CHUNK_FEAT) {
            return handleFEAT(chunk);
        }
        throw new RuntimeException("Unknown packet " + name(i));
    }

    private Chunk handleHELO(Chunk chunk) {
        wrapChunk(chunk).getInt();
        String str = System.getProperty("java.vm.name", "?") + " v" + System.getProperty("java.vm.version", "?");
        DdmHandleAppName.Names names = DdmHandleAppName.getNames();
        String appName = names.getAppName();
        String pkgName = names.getPkgName();
        VMRuntime runtime = VMRuntime.getRuntime();
        String str2 = runtime.is64Bit() ? "64-bit" : "32-bit";
        String strVmInstructionSet = runtime.vmInstructionSet();
        if (strVmInstructionSet != null && strVmInstructionSet.length() > 0) {
            str2 = str2 + " (" + strVmInstructionSet + NavigationBarInflaterView.KEY_CODE_END;
        }
        String strConcat = "CheckJNI=".concat(runtime.isCheckJniEnabled() ? "true" : "false");
        boolean zIsNativeDebuggable = runtime.isNativeDebuggable();
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate((str.length() * 2) + 32 + (appName.length() * 2) + (str2.length() * 2) + (strConcat.length() * 2) + 1 + (pkgName.length() * 2) + 4);
        byteBufferAllocate.order(ChunkHandler.CHUNK_ORDER);
        byteBufferAllocate.putInt(1);
        byteBufferAllocate.putInt(Process.myPid());
        byteBufferAllocate.putInt(str.length());
        byteBufferAllocate.putInt(appName.length());
        putString(byteBufferAllocate, str);
        putString(byteBufferAllocate, appName);
        byteBufferAllocate.putInt(UserHandle.myUserId());
        byteBufferAllocate.putInt(str2.length());
        putString(byteBufferAllocate, str2);
        byteBufferAllocate.putInt(strConcat.length());
        putString(byteBufferAllocate, strConcat);
        byteBufferAllocate.put(zIsNativeDebuggable ? (byte) 1 : (byte) 0);
        byteBufferAllocate.putInt(pkgName.length());
        putString(byteBufferAllocate, pkgName);
        byteBufferAllocate.putInt(DdmSyncState.getStage().toInt());
        Chunk chunk2 = new Chunk(CHUNK_HELO, byteBufferAllocate);
        if (Debug.waitingForDebugger()) {
            sendWAIT(0);
        }
        return chunk2;
    }

    private Chunk handleFEAT(Chunk chunk) {
        String[] vmFeatureList = Debug.getVmFeatureList();
        String[] featureList = Debug.getFeatureList();
        int length = ((vmFeatureList.length + featureList.length) * 4) + 4;
        for (int length2 = vmFeatureList.length - 1; length2 >= 0; length2--) {
            length += vmFeatureList[length2].length() * 2;
        }
        for (int length3 = featureList.length - 1; length3 >= 0; length3--) {
            length += featureList[length3].length() * 2;
        }
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(length);
        byteBufferAllocate.order(ChunkHandler.CHUNK_ORDER);
        byteBufferAllocate.putInt(vmFeatureList.length + featureList.length);
        for (int length4 = vmFeatureList.length - 1; length4 >= 0; length4--) {
            byteBufferAllocate.putInt(vmFeatureList[length4].length());
            putString(byteBufferAllocate, vmFeatureList[length4]);
        }
        for (int length5 = featureList.length - 1; length5 >= 0; length5--) {
            byteBufferAllocate.putInt(featureList[length5].length());
            putString(byteBufferAllocate, featureList[length5]);
        }
        return new Chunk(CHUNK_FEAT, byteBufferAllocate);
    }

    public static void sendWAIT(int i) {
        DdmServer.sendChunk(new Chunk(CHUNK_WAIT, new byte[]{(byte) i}, 0, 1));
    }
}
