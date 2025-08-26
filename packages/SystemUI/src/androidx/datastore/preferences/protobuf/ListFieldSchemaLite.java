package androidx.datastore.preferences.protobuf;

import androidx.datastore.preferences.protobuf.Internal;

/* loaded from: classes.dex */
public final class ListFieldSchemaLite implements ListFieldSchema {
    public final Internal.ProtobufList mutableListAt(long j, Object obj) {
        Internal.ProtobufList protobufList = (Internal.ProtobufList) UnsafeUtil.getObject(j, obj);
        if (((AbstractProtobufList) protobufList).isMutable) {
            return protobufList;
        }
        int size = protobufList.size();
        Internal.ProtobufList protobufListMutableCopyWithCapacity = protobufList.mutableCopyWithCapacity(size == 0 ? 10 : size * 2);
        UnsafeUtil.putObject(j, obj, protobufListMutableCopyWithCapacity);
        return protobufListMutableCopyWithCapacity;
    }
}
