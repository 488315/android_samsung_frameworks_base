package com.android.server.apphibernation;

import android.util.proto.ProtoInputStream;
import android.util.proto.ProtoOutputStream;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public interface ProtoReadWriter {
    Object readFromProto(ProtoInputStream protoInputStream);

    void writeToProto(ProtoOutputStream protoOutputStream, Object obj);
}
