package android.media.audiofx;

import android.app.ActivityThread;
import android.util.Log;
import java.util.UUID;

/* loaded from: classes2.dex */
public class StreamDefaultEffect extends DefaultEffect {
    private static final String TAG = "StreamDefaultEffect-JAVA";

    private final native void native_release(int i);

    private final native int native_setup(String str, String str2, int i, int i2, String str3, int[] iArr);

    static {
        System.loadLibrary("audioeffect_jni");
    }

    public StreamDefaultEffect(UUID uuid, UUID uuid2, int i, int i2) {
        int[] iArr = new int[1];
        int iNative_setup = native_setup(uuid.toString(), uuid2.toString(), i, i2, ActivityThread.currentOpPackageName(), iArr);
        if (iNative_setup != 0) {
            Log.e(TAG, "Error code " + iNative_setup + " when initializing StreamDefaultEffect");
            if (iNative_setup == -5) {
                throw new UnsupportedOperationException("Effect library not loaded");
            }
            if (iNative_setup == -4) {
                throw new IllegalArgumentException("Stream usage, type uuid, or implementation uuid not supported.");
            }
            throw new RuntimeException("Cannot initialize effect engine for type: " + uuid + " Error: " + iNative_setup);
        }
        this.mId = iArr[0];
    }

    public void release() {
        native_release(this.mId);
    }

    protected void finalize() {
        release();
    }
}
