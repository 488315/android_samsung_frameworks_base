package com.android.server.apphibernation;

import android.util.Slog;
import android.util.proto.ProtoInputStream;
import android.util.proto.ProtoOutputStream;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class GlobalLevelHibernationProto implements ProtoReadWriter {
    @Override // com.android.server.apphibernation.ProtoReadWriter
    public final Object readFromProto(ProtoInputStream protoInputStream) {
        ArrayList arrayList = new ArrayList();
        while (protoInputStream.nextField() != -1) {
            if (protoInputStream.getFieldNumber() == 1) {
                GlobalLevelState globalLevelState = new GlobalLevelState();
                long start = protoInputStream.start(2246267895809L);
                while (protoInputStream.nextField() != -1) {
                    int fieldNumber = protoInputStream.getFieldNumber();
                    if (fieldNumber == 1) {
                        globalLevelState.packageName = protoInputStream.readString(1138166333441L);
                    } else if (fieldNumber == 2) {
                        globalLevelState.hibernated = protoInputStream.readBoolean(1133871366146L);
                    } else if (fieldNumber != 3) {
                        Slog.w("GlobalLevelHibernationProtoReadWriter", "Undefined field in proto: " + protoInputStream.getFieldNumber());
                    } else {
                        globalLevelState.savedByte = protoInputStream.readLong(1112396529667L);
                    }
                }
                protoInputStream.end(start);
                arrayList.add(globalLevelState);
            }
        }
        return arrayList;
    }

    @Override // com.android.server.apphibernation.ProtoReadWriter
    public final void writeToProto(ProtoOutputStream protoOutputStream, Object obj) {
        List list = (List) obj;
        int size = list.size();
        for (int i = 0; i < size; i++) {
            long start = protoOutputStream.start(2246267895809L);
            GlobalLevelState globalLevelState = (GlobalLevelState) list.get(i);
            protoOutputStream.write(1138166333441L, globalLevelState.packageName);
            protoOutputStream.write(1133871366146L, globalLevelState.hibernated);
            protoOutputStream.write(1112396529667L, globalLevelState.savedByte);
            protoOutputStream.end(start);
        }
    }
}
