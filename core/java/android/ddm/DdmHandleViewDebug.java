package android.ddm;

import android.util.Log;
import android.view.View;
import android.view.ViewDebug;
import android.view.WindowManagerGlobal;

import org.apache.harmony.dalvik.ddmc.Chunk;
import org.apache.harmony.dalvik.ddmc.ChunkHandler;
import org.apache.harmony.dalvik.ddmc.DdmServer;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

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

    private DdmHandleViewDebug() {}

    public static void register() {
        DdmServer.registerHandler(CHUNK_VULW, sInstance);
        DdmServer.registerHandler(CHUNK_VURT, sInstance);
        DdmServer.registerHandler(CHUNK_VUOP, sInstance);
    }

    public void onConnected() {}

    public void onDisconnected() {}

    public Chunk handleChunk(Chunk request) {
        int type = request.type;
        if (type == CHUNK_VULW) {
            return listWindows();
        }
        ByteBuffer in = wrapChunk(request);
        int op = in.getInt();
        View rootView = getRootView(in);
        if (rootView == null) {
            return createFailChunk(-2, "Invalid View Root");
        }
        if (type == CHUNK_VURT) {
            if (op == 1) {
                return dumpHierarchy(rootView, in);
            }
            if (op == 2) {
                return captureLayers(rootView);
            }
            if (op == 3) {
                return dumpTheme(rootView);
            }
            return createFailChunk(-1, "Unknown view root operation: " + op);
        }
        View targetView = getTargetView(rootView, in);
        if (targetView == null) {
            return createFailChunk(-2, "Invalid target view");
        }
        if (type == CHUNK_VUOP) {
            switch (op) {
                case 1:
                    return captureView(rootView, targetView);
                case 2:
                    return dumpDisplayLists(rootView, targetView);
                case 3:
                    return profileView(rootView, targetView);
                case 4:
                    return invokeViewMethod(rootView, targetView, in);
                case 5:
                    return setLayoutParameter(rootView, targetView, in);
                default:
                    return createFailChunk(-1, "Unknown view operation: " + op);
            }
        }
        throw new RuntimeException("Unknown packet " + name(type));
    }

    private Chunk listWindows() {
        String[] windowNames = WindowManagerGlobal.getInstance().getViewRootNames();
        int responseLength = 4;
        for (String str : windowNames) {
            responseLength = responseLength + 4 + (str.length() * 2);
        }
        ByteBuffer out = ByteBuffer.allocate(responseLength);
        out.order(ChunkHandler.CHUNK_ORDER);
        out.putInt(windowNames.length);
        for (String name : windowNames) {
            out.putInt(name.length());
            putString(out, name);
        }
        return new Chunk(CHUNK_VULW, out);
    }

    private View getRootView(ByteBuffer in) {
        try {
            int viewRootNameLength = in.getInt();
            String viewRootName = getString(in, viewRootNameLength);
            return WindowManagerGlobal.getInstance().getRootView(viewRootName);
        } catch (BufferUnderflowException e) {
            return null;
        }
    }

    private View getTargetView(View root, ByteBuffer in) {
        try {
            int viewLength = in.getInt();
            String viewName = getString(in, viewLength);
            return ViewDebug.findView(root, viewName);
        } catch (BufferUnderflowException e) {
            return null;
        }
    }

    private Chunk dumpHierarchy(View rootView, ByteBuffer in) {
        int i = 1;
        boolean skipChildren = in.getInt() > 0;
        boolean includeProperties = in.getInt() > 0;
        boolean v2 = in.hasRemaining() && in.getInt() > 0;
        long start = System.currentTimeMillis();
        ByteArrayOutputStream b = new ByteArrayOutputStream(2097152);
        try {
            if (v2) {
                ViewDebug.dumpv2(rootView, b);
            } else {
                ViewDebug.dump(rootView, skipChildren, includeProperties, b);
            }
            long end = System.currentTimeMillis();
            Log.d(TAG, "Time to obtain view hierarchy (ms): " + (end - start));
            i = b.toByteArray();
            return new Chunk(CHUNK_VURT, (byte[]) i, 0, i.length);
        } catch (IOException | InterruptedException e) {
            return createFailChunk(
                    i, "Unexpected error while obtaining view hierarchy: " + e.getMessage());
        }
    }

    private Chunk captureLayers(View rootView) {
        ByteArrayOutputStream b = new ByteArrayOutputStream(1024);
        DataOutputStream dos = new DataOutputStream(b);
        try {
            try {
                ViewDebug.captureLayers(rootView, dos);
                byte[] data = b.toByteArray();
                return new Chunk(CHUNK_VURT, data, 0, data.length);
            } catch (IOException e) {
                Chunk createFailChunk =
                        createFailChunk(
                                1,
                                "Unexpected error while obtaining view hierarchy: "
                                        + e.getMessage());
                try {
                    dos.close();
                } catch (IOException e2) {
                }
                return createFailChunk;
            }
        } finally {
            try {
                dos.close();
            } catch (IOException e3) {
            }
        }
    }

    private Chunk dumpTheme(View rootView) {
        ByteArrayOutputStream b = new ByteArrayOutputStream(1024);
        try {
            ViewDebug.dumpTheme(rootView, b);
            byte[] data = b.toByteArray();
            return new Chunk(CHUNK_VURT, data, 0, data.length);
        } catch (IOException e) {
            return createFailChunk(
                    1, "Unexpected error while dumping the theme: " + e.getMessage());
        }
    }

    private Chunk captureView(View rootView, View targetView) {
        ByteArrayOutputStream b = new ByteArrayOutputStream(1024);
        try {
            ViewDebug.capture(rootView, b, targetView);
            byte[] data = b.toByteArray();
            return new Chunk(CHUNK_VUOP, data, 0, data.length);
        } catch (IOException e) {
            return createFailChunk(1, "Unexpected error while capturing view: " + e.getMessage());
        }
    }

    private Chunk dumpDisplayLists(final View rootView, final View targetView) {
        rootView.post(
                new Runnable() { // from class: android.ddm.DdmHandleViewDebug.1
                    @Override // java.lang.Runnable
                    public void run() {
                        ViewDebug.outputDisplayList(rootView, targetView);
                    }
                });
        return null;
    }

    private Chunk invokeViewMethod(View rootView, View targetView, ByteBuffer in) {
        int l = in.getInt();
        String methodName = getString(in, l);
        try {
            byte[] returnValue = ViewDebug.invokeViewMethod(targetView, methodName, in);
            return new Chunk(CHUNK_VUOP, returnValue, 0, returnValue.length);
        } catch (ViewDebug.ViewMethodInvocationSerializationException e) {
            return createFailChunk(-2, e.getMessage());
        } catch (Exception e2) {
            return createFailChunk(-3, e2.getMessage());
        }
    }

    private Chunk setLayoutParameter(View rootView, View targetView, ByteBuffer in) {
        int l = in.getInt();
        String param = getString(in, l);
        int value = in.getInt();
        try {
            ViewDebug.setLayoutParameter(targetView, param, value);
            return null;
        } catch (Exception e) {
            Log.e(TAG, "Exception setting layout parameter: " + e);
            return createFailChunk(-3, "Error accessing field " + param + ":" + e.getMessage());
        }
    }

    private Chunk profileView(View rootView, View targetView) {
        ByteArrayOutputStream b = new ByteArrayOutputStream(32768);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(b), 32768);
        try {
            try {
                ViewDebug.profileViewAndChildren(targetView, bw);
                byte[] data = b.toByteArray();
                return new Chunk(CHUNK_VUOP, data, 0, data.length);
            } catch (IOException e) {
                Chunk createFailChunk =
                        createFailChunk(
                                1, "Unexpected error while profiling view: " + e.getMessage());
                try {
                    bw.close();
                } catch (IOException e2) {
                }
                return createFailChunk;
            }
        } finally {
            try {
                bw.close();
            } catch (IOException e3) {
            }
        }
    }
}
