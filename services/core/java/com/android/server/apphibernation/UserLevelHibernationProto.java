package com.android.server.apphibernation;

import android.util.Slog;
import android.util.proto.ProtoInputStream;
import android.util.proto.ProtoOutputStream;

import java.util.ArrayList;
import java.util.List;

public final class UserLevelHibernationProto implements ProtoReadWriter {
    @Override // com.android.server.apphibernation.ProtoReadWriter
    public final Object readFromProto(ProtoInputStream protoInputStream) {
        ArrayList arrayList = new ArrayList();
        while (protoInputStream.nextField() != -1) {
            if (protoInputStream.getFieldNumber() == 1) {
                UserLevelState userLevelState = new UserLevelState();
                long start = protoInputStream.start(2246267895809L);
                while (protoInputStream.nextField() != -1) {
                    int fieldNumber = protoInputStream.getFieldNumber();
                    if (fieldNumber == 1) {
                        userLevelState.packageName = protoInputStream.readString(1138166333441L);
                    } else if (fieldNumber != 2) {
                        Slog.w(
                                "UserLevelHibernationProtoReadWriter",
                                "Undefined field in proto: " + protoInputStream.getFieldNumber());
                    } else {
                        userLevelState.hibernated = protoInputStream.readBoolean(1133871366146L);
                    }
                }
                protoInputStream.end(start);
                arrayList.add(userLevelState);
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
            UserLevelState userLevelState = (UserLevelState) list.get(i);
            protoOutputStream.write(1138166333441L, userLevelState.packageName);
            protoOutputStream.write(1133871366146L, userLevelState.hibernated);
            protoOutputStream.end(start);
        }
    }
}
