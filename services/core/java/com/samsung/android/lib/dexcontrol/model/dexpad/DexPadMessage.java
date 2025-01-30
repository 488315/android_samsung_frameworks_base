package com.samsung.android.lib.dexcontrol.model.dexpad;

/* loaded from: classes2.dex */
public abstract class DexPadMessage {
    public static byte[] getBytes(byte b, byte b2) {
        return new byte[]{b, b2};
    }

    public static byte[] getBytes(byte b, byte b2, int i) {
        return new byte[]{b, b2, (byte) i};
    }
}
