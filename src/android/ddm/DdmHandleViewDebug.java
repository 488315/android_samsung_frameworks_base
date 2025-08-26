package android.ddm;

import android.util.Log;
import android.view.View;
import android.view.ViewDebug;
import android.view.WindowManagerGlobal;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import org.apache.harmony.dalvik.ddmc.Chunk;
import org.apache.harmony.dalvik.ddmc.ChunkHandler;
import org.apache.harmony.dalvik.ddmc.DdmServer;

/* loaded from: classes.dex */
public class DdmHandleViewDebug extends DdmHandle {
    private static final int ERR_EXCEPTION = -3;
    private static final int ERR_INVALID_OP = -1;
    private static final int ERR_INVALID_PARAM = -2;
    private static final String TAG = "DdmViewDebug";
    private static final int VUOP_CAPTURE_VIEW = 1;
    private static final int VUOP_DUMP_DISPLAYLIST = 2;
    private static final int VUOP_INVOKE_VIEW_METHOD = 4;
    private static final int VUOP_PROFILE_VIEW = 3;
    private static final int VUOP_SET_LAYOUT_PARAMETER = 5;
    private static final int VURT_CAPTURE_LAYERS = 2;
    private static final int VURT_DUMP_HIERARCHY = 1;
    private static final int VURT_DUMP_THEME = 3;
    private static final int CHUNK_VULW = ChunkHandler.type("VULW");
    private static final int CHUNK_VURT = ChunkHandler.type("VURT");
    private static final int CHUNK_VUOP = ChunkHandler.type("VUOP");
    private static final DdmHandleViewDebug sInstance = new DdmHandleViewDebug();

    public void onConnected() {
    }

    public void onDisconnected() {
    }

    private DdmHandleViewDebug() {
    }

    public static void register() {
        int i = CHUNK_VULW;
        DdmHandleViewDebug ddmHandleViewDebug = sInstance;
        DdmServer.registerHandler(i, ddmHandleViewDebug);
        DdmServer.registerHandler(CHUNK_VURT, ddmHandleViewDebug);
        DdmServer.registerHandler(CHUNK_VUOP, ddmHandleViewDebug);
    }

    public Chunk handleChunk(Chunk chunk) {
        int i = chunk.type;
        if (i == CHUNK_VULW) {
            return listWindows();
        }
        ByteBuffer byteBufferWrapChunk = wrapChunk(chunk);
        int i2 = byteBufferWrapChunk.getInt();
        View rootView = getRootView(byteBufferWrapChunk);
        if (rootView == null) {
            return createFailChunk(-2, "Invalid View Root");
        }
        if (i == CHUNK_VURT) {
            if (i2 == 1) {
                return dumpHierarchy(rootView, byteBufferWrapChunk);
            }
            if (i2 == 2) {
                return captureLayers(rootView);
            }
            if (i2 == 3) {
                return dumpTheme(rootView);
            }
            return createFailChunk(-1, "Unknown view root operation: " + i2);
        }
        View targetView = getTargetView(rootView, byteBufferWrapChunk);
        if (targetView == null) {
            return createFailChunk(-2, "Invalid target view");
        }
        if (i != CHUNK_VUOP) {
            throw new RuntimeException("Unknown packet " + name(i));
        }
        if (i2 == 1) {
            return captureView(rootView, targetView);
        }
        if (i2 == 2) {
            return dumpDisplayLists(rootView, targetView);
        }
        if (i2 == 3) {
            return profileView(rootView, targetView);
        }
        if (i2 == 4) {
            return invokeViewMethod(rootView, targetView, byteBufferWrapChunk);
        }
        if (i2 == 5) {
            return setLayoutParameter(rootView, targetView, byteBufferWrapChunk);
        }
        return createFailChunk(-1, "Unknown view operation: " + i2);
    }

    private Chunk listWindows() {
        String[] viewRootNames = WindowManagerGlobal.getInstance().getViewRootNames();
        int length = 4;
        for (String str : viewRootNames) {
            length = length + 4 + (str.length() * 2);
        }
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(length);
        byteBufferAllocate.order(ChunkHandler.CHUNK_ORDER);
        byteBufferAllocate.putInt(viewRootNames.length);
        for (String str2 : viewRootNames) {
            byteBufferAllocate.putInt(str2.length());
            putString(byteBufferAllocate, str2);
        }
        return new Chunk(CHUNK_VULW, byteBufferAllocate);
    }

    private View getRootView(ByteBuffer byteBuffer) {
        try {
            return WindowManagerGlobal.getInstance().getRootView(getString(byteBuffer, byteBuffer.getInt()));
        } catch (BufferUnderflowException unused) {
            return null;
        }
    }

    private View getTargetView(View view, ByteBuffer byteBuffer) {
        try {
            return ViewDebug.findView(view, getString(byteBuffer, byteBuffer.getInt()));
        } catch (BufferUnderflowException unused) {
            return null;
        }
    }

    private Chunk dumpHierarchy(View view, ByteBuffer byteBuffer) {
        int length = 1;
        boolean z = byteBuffer.getInt() > 0;
        boolean z2 = byteBuffer.getInt() > 0;
        boolean z3 = byteBuffer.hasRemaining() && byteBuffer.getInt() > 0;
        long jCurrentTimeMillis = System.currentTimeMillis();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(2097152);
        try {
            if (z3) {
                ViewDebug.dumpv2(view, byteArrayOutputStream);
            } else {
                ViewDebug.dump(view, z, z2, byteArrayOutputStream);
            }
            Log.d(TAG, "Time to obtain view hierarchy (ms): " + (System.currentTimeMillis() - jCurrentTimeMillis));
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            int i = CHUNK_VURT;
            length = byteArray.length;
            return new Chunk(i, byteArray, 0, length);
        } catch (IOException | InterruptedException e) {
            return createFailChunk(length, "Unexpected error while obtaining view hierarchy: " + e.getMessage());
        }
    }

    private Chunk captureLayers(View view) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            try {
                ViewDebug.captureLayers(view, dataOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                return new Chunk(CHUNK_VURT, byteArray, 0, byteArray.length);
            } finally {
                try {
                    dataOutputStream.close();
                } catch (IOException unused) {
                }
            }
        } catch (IOException e) {
            Chunk chunkCreateFailChunk = createFailChunk(1, "Unexpected error while obtaining view hierarchy: " + e.getMessage());
            try {
                dataOutputStream.close();
            } catch (IOException unused2) {
            }
            return chunkCreateFailChunk;
        }
    }

    private Chunk dumpTheme(View view) throws Throwable {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
        try {
            ViewDebug.dumpTheme(view, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            return new Chunk(CHUNK_VURT, byteArray, 0, byteArray.length);
        } catch (IOException e) {
            return createFailChunk(1, "Unexpected error while dumping the theme: " + e.getMessage());
        }
    }

    private Chunk captureView(View view, View view2) throws Throwable {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
        try {
            ViewDebug.capture(view, byteArrayOutputStream, view2);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            return new Chunk(CHUNK_VUOP, byteArray, 0, byteArray.length);
        } catch (IOException e) {
            return createFailChunk(1, "Unexpected error while capturing view: " + e.getMessage());
        }
    }

    private Chunk dumpDisplayLists(final View view, final View view2) {
        view.post(new Runnable(this) { // from class: android.ddm.DdmHandleViewDebug.1
            @Override // java.lang.Runnable
            public void run() {
                ViewDebug.outputDisplayList(view, view2);
            }
        });
        return null;
    }

    private Chunk invokeViewMethod(View view, View view2, ByteBuffer byteBuffer) {
        try {
            byte[] bArrInvokeViewMethod = ViewDebug.invokeViewMethod(view2, getString(byteBuffer, byteBuffer.getInt()), byteBuffer);
            return new Chunk(CHUNK_VUOP, bArrInvokeViewMethod, 0, bArrInvokeViewMethod.length);
        } catch (ViewDebug.ViewMethodInvocationSerializationException e) {
            return createFailChunk(-2, e.getMessage());
        } catch (Exception e2) {
            return createFailChunk(-3, e2.getMessage());
        }
    }

    private Chunk setLayoutParameter(View view, View view2, ByteBuffer byteBuffer) {
        String string = getString(byteBuffer, byteBuffer.getInt());
        try {
            ViewDebug.setLayoutParameter(view2, string, byteBuffer.getInt());
            return null;
        } catch (Exception e) {
            Log.e(TAG, "Exception setting layout parameter: " + e);
            return createFailChunk(-3, "Error accessing field " + string + ":" + e.getMessage());
        }
    }

    private Chunk profileView(View view, View view2) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(32768);
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(byteArrayOutputStream), 32768);
        try {
            try {
                ViewDebug.profileViewAndChildren(view2, bufferedWriter);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                return new Chunk(CHUNK_VUOP, byteArray, 0, byteArray.length);
            } catch (IOException e) {
                Chunk chunkCreateFailChunk = createFailChunk(1, "Unexpected error while profiling view: " + e.getMessage());
                try {
                    bufferedWriter.close();
                } catch (IOException unused) {
                }
                return chunkCreateFailChunk;
            }
        } finally {
            try {
                bufferedWriter.close();
            } catch (IOException unused2) {
            }
        }
    }
}
