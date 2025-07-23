package android.media.audiofx;

import android.app.ActivityThread;
import android.util.Log;
import java.util.UUID;

/* loaded from: classes2.dex */
public class SourceDefaultEffect extends DefaultEffect {
    private static final String TAG = "SourceDefaultEffect-JAVA";

    private final native void native_release(int i);

    private final native int native_setup(String str, String str2, int i, int i2, String str3, int[] iArr);

    static {
        System.loadLibrary("audioeffect_jni");
    }

    public SourceDefaultEffect(UUID uuid, UUID uuid2, int i, int i2) {
        int[] iArr = new int[1];
        int native_setup = native_setup(uuid.toString(), uuid2.toString(), i, i2, ActivityThread.currentOpPackageName(), iArr);
        if (native_setup != 0) {
            Log.e(TAG, "Error code " + native_setup + " when initializing SourceDefaultEffect");
            if (native_setup == -5) {
                throw new UnsupportedOperationException("Effect library not loaded");
            }
            if (native_setup == -4) {
                throw new IllegalArgumentException("Source, type uuid, or implementation uuid not supported.");
            }
            throw new RuntimeException("Cannot initialize effect engine for type: " + uuid + " Error: " + native_setup);
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
