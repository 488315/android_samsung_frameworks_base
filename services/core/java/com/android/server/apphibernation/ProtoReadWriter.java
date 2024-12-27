package com.android.server.apphibernation;

import android.util.proto.ProtoInputStream;
import android.util.proto.ProtoOutputStream;

public interface ProtoReadWriter {
    Object readFromProto(ProtoInputStream protoInputStream);

    void writeToProto(ProtoOutputStream protoOutputStream, Object obj);
}
