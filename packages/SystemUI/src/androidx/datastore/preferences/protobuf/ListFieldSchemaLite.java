package androidx.datastore.preferences.protobuf;

import androidx.datastore.preferences.protobuf.Internal;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ListFieldSchemaLite implements ListFieldSchema {
    public final Internal.ProtobufList mutableListAt(long j, Object obj) {
        Internal.ProtobufList protobufList = (Internal.ProtobufList) UnsafeUtil.getObject(j, obj);
        if (((AbstractProtobufList) protobufList).isMutable) {
            return protobufList;
        }
        int size = protobufList.size();
        Internal.ProtobufList mutableCopyWithCapacity = protobufList.mutableCopyWithCapacity(size == 0 ? 10 : size * 2);
        UnsafeUtil.putObject(j, obj, mutableCopyWithCapacity);
        return mutableCopyWithCapacity;
    }
}
