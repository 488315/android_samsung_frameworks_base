package android.view.inputmethod;

import android.util.proto.ProtoOutputStream;

public interface DumpableInputConnection {
    void dumpDebug(ProtoOutputStream protoOutputStream, long j);
}
