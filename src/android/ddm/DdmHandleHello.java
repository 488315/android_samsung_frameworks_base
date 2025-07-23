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
        String vmInstructionSet = runtime.vmInstructionSet();
        if (vmInstructionSet != null && vmInstructionSet.length() > 0) {
            str2 = str2 + " (" + vmInstructionSet + NavigationBarInflaterView.KEY_CODE_END;
        }
        String concat = "CheckJNI=".concat(runtime.isCheckJniEnabled() ? "true" : "false");
        boolean isNativeDebuggable = runtime.isNativeDebuggable();
        ByteBuffer allocate = ByteBuffer.allocate((str.length() * 2) + 32 + (appName.length() * 2) + (str2.length() * 2) + (concat.length() * 2) + 1 + (pkgName.length() * 2) + 4);
        allocate.order(ChunkHandler.CHUNK_ORDER);
        allocate.putInt(1);
        allocate.putInt(Process.myPid());
        allocate.putInt(str.length());
        allocate.putInt(appName.length());
        putString(allocate, str);
        putString(allocate, appName);
        allocate.putInt(UserHandle.myUserId());
        allocate.putInt(str2.length());
        putString(allocate, str2);
        allocate.putInt(concat.length());
        putString(allocate, concat);
        allocate.put(isNativeDebuggable ? (byte) 1 : (byte) 0);
        allocate.putInt(pkgName.length());
        putString(allocate, pkgName);
        allocate.putInt(DdmSyncState.getStage().toInt());
        Chunk chunk2 = new Chunk(CHUNK_HELO, allocate);
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
        ByteBuffer allocate = ByteBuffer.allocate(length);
        allocate.order(ChunkHandler.CHUNK_ORDER);
        allocate.putInt(vmFeatureList.length + featureList.length);
        for (int length4 = vmFeatureList.length - 1; length4 >= 0; length4--) {
            allocate.putInt(vmFeatureList[length4].length());
            putString(allocate, vmFeatureList[length4]);
        }
        for (int length5 = featureList.length - 1; length5 >= 0; length5--) {
            allocate.putInt(featureList[length5].length());
            putString(allocate, featureList[length5]);
        }
        return new Chunk(CHUNK_FEAT, allocate);
    }

    public static void sendWAIT(int i) {
        DdmServer.sendChunk(new Chunk(CHUNK_WAIT, new byte[]{(byte) i}, 0, 1));
    }
}
