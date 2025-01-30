package com.android.server.backup.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/* loaded from: classes.dex */
public interface DataStreamCodec {
    Object deserialize(DataInputStream dataInputStream);

    void serialize(Object obj, DataOutputStream dataOutputStream);
}
