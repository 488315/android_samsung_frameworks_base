package com.android.internal.org.bouncycastle.operator;

import java.io.OutputStream;

/* loaded from: classes5.dex */
public interface AADProcessor {
    OutputStream getAADStream();

    byte[] getMAC();
}
