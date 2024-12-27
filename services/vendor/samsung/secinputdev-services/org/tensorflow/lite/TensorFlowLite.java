package org.tensorflow.lite;

public final class TensorFlowLite {
    private static final String ALTERNATE_LIBNAME = "tensorflowlite_jni_stable";
    private static final String LIBNAME = "tensorflowlite_jni.secinput.samsung";
    private static final Throwable LOAD_LIBRARY_EXCEPTION;
    private static volatile boolean isInit = false;

    private static native void nativeDoNothing();

    private static native String nativeRuntimeVersion();

    private static native String nativeSchemaVersion();

    static {
        Throwable loadLibraryException = null;
        try {
            System.loadLibrary(LIBNAME);
        } catch (UnsatisfiedLinkError e1) {
            try {
                System.loadLibrary(ALTERNATE_LIBNAME);
            } catch (UnsatisfiedLinkError e) {
                loadLibraryException = e1;
            }
        }
        LOAD_LIBRARY_EXCEPTION = loadLibraryException;
    }

    private TensorFlowLite() {}

    @Deprecated
    public static String version() {
        return schemaVersion();
    }

    public static String runtimeVersion() {
        init();
        return nativeRuntimeVersion();
    }

    public static String schemaVersion() {
        init();
        return nativeSchemaVersion();
    }

    public static void init() {
        if (isInit) {
            return;
        }
        try {
            nativeDoNothing();
            isInit = true;
        } catch (UnsatisfiedLinkError e) {
            Throwable exceptionToLog = LOAD_LIBRARY_EXCEPTION != null ? LOAD_LIBRARY_EXCEPTION : e;
            throw new UnsatisfiedLinkError(
                    "Failed to load native TensorFlow Lite methods. Check that the correct native"
                        + " libraries are present, and, if using a custom native library, have been"
                        + " properly loaded via System.loadLibrary():\n"
                        + "  "
                            + exceptionToLog);
        }
    }
}
