package android.ddm;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.p009os.Debug;
import android.p009os.Process;
import android.p009os.UserHandle;
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
  private static final String[] FRAMEWORK_FEATURES = {"opengl-tracing", "view-hierarchy"};

  private DdmHandleHello() {}

  public static void register() {
    DdmServer.registerHandler(CHUNK_HELO, mInstance);
    DdmServer.registerHandler(CHUNK_FEAT, mInstance);
  }

  public void onConnected() {}

  public void onDisconnected() {}

  public Chunk handleChunk(Chunk request) {
    int type = request.type;
    if (type == CHUNK_HELO) {
      return handleHELO(request);
    }
    if (type == CHUNK_FEAT) {
      return handleFEAT(request);
    }
    throw new RuntimeException("Unknown packet " + name(type));
  }

  private Chunk handleHELO(Chunk chunk) {
    wrapChunk(chunk).getInt();
    String str =
        System.getProperty("java.vm.name", "?") + " v" + System.getProperty("java.vm.version", "?");
    DdmHandleAppName.Names names = DdmHandleAppName.getNames();
    String appName = names.getAppName();
    String pkgName = names.getPkgName();
    VMRuntime runtime = VMRuntime.getRuntime();
    String str2 = runtime.is64Bit() ? "64-bit" : "32-bit";
    String vmInstructionSet = runtime.vmInstructionSet();
    if (vmInstructionSet != null && vmInstructionSet.length() > 0) {
      str2 = str2 + " (" + vmInstructionSet + NavigationBarInflaterView.KEY_CODE_END;
    }
    String str3 = "CheckJNI=" + (runtime.isCheckJniEnabled() ? "true" : "false");
    boolean isNativeDebuggable = runtime.isNativeDebuggable();
    ByteBuffer allocate =
        ByteBuffer.allocate(
            (str.length() * 2)
                + 32
                + (appName.length() * 2)
                + (str2.length() * 2)
                + (str3.length() * 2)
                + 1
                + (pkgName.length() * 2));
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
    allocate.putInt(str3.length());
    putString(allocate, str3);
    allocate.put(isNativeDebuggable ? (byte) 1 : (byte) 0);
    allocate.putInt(pkgName.length());
    putString(allocate, pkgName);
    Chunk chunk2 = new Chunk(CHUNK_HELO, allocate);
    if (Debug.waitingForDebugger()) {
      sendWAIT(0);
    }
    return chunk2;
  }

  private Chunk handleFEAT(Chunk request) {
    String[] vmFeatures = Debug.getVmFeatureList();
    int size = ((vmFeatures.length + FRAMEWORK_FEATURES.length) * 4) + 4;
    for (int i = vmFeatures.length - 1; i >= 0; i--) {
      size += vmFeatures[i].length() * 2;
    }
    for (int i2 = FRAMEWORK_FEATURES.length - 1; i2 >= 0; i2--) {
      size += FRAMEWORK_FEATURES[i2].length() * 2;
    }
    ByteBuffer out = ByteBuffer.allocate(size);
    out.order(ChunkHandler.CHUNK_ORDER);
    out.putInt(vmFeatures.length + FRAMEWORK_FEATURES.length);
    for (int i3 = vmFeatures.length - 1; i3 >= 0; i3--) {
      out.putInt(vmFeatures[i3].length());
      putString(out, vmFeatures[i3]);
    }
    for (int i4 = FRAMEWORK_FEATURES.length - 1; i4 >= 0; i4--) {
      String[] strArr = FRAMEWORK_FEATURES;
      out.putInt(strArr[i4].length());
      putString(out, strArr[i4]);
    }
    return new Chunk(CHUNK_FEAT, out);
  }

  public static void sendWAIT(int reason) {
    byte[] data = {(byte) reason};
    Chunk waitChunk = new Chunk(CHUNK_WAIT, data, 0, 1);
    DdmServer.sendChunk(waitChunk);
  }
}
