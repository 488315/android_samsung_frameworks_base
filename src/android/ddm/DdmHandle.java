package android.ddm;

import java.nio.ByteBuffer;
import org.apache.harmony.dalvik.ddmc.ChunkHandler;

/* loaded from: classes.dex */
public abstract class DdmHandle extends ChunkHandler {
    public static String getString(ByteBuffer byteBuffer, int i) {
        char[] cArr = new char[i];
        for (int i2 = 0; i2 < i; i2++) {
            cArr[i2] = byteBuffer.getChar();
        }
        return new String(cArr);
    }

    public static void putString(ByteBuffer byteBuffer, String str) {
        int length = str.length();
        for (int i = 0; i < length; i++) {
            byteBuffer.putChar(str.charAt(i));
        }
    }
}
