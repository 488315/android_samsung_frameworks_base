package com.samsung.android.os;

import java.io.FileOutputStream;
import java.io.IOException;

/* loaded from: classes6.dex */
public final class ReliableWrite {
    private static native int setReliableWriteNative(int i);

    public static void setReliableWrite(FileOutputStream outputStream) {
        try {
            setReliableWriteNative(outputStream.getFD().getInt$());
        } catch (IOException e) {
        }
    }
}
