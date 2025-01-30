package com.google.protobuf;

import com.google.protobuf.Internal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class ListFieldSchema {
    public static final ListFieldSchemaFull FULL_INSTANCE;
    public static final ListFieldSchemaLite LITE_INSTANCE;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ListFieldSchemaFull extends ListFieldSchema {
        public static final Class UNMODIFIABLE_LIST_CLASS = Collections.unmodifiableList(Collections.emptyList()).getClass();

        private ListFieldSchemaFull() {
            super();
        }

        @Override // com.google.protobuf.ListFieldSchema
        public final void makeImmutableListAt(long j, Object obj) {
            Object unmodifiableList;
            List list = (List) UnsafeUtil.getObject(j, obj);
            if (list instanceof LazyStringList) {
                unmodifiableList = ((LazyStringList) list).getUnmodifiableView();
            } else {
                if (UNMODIFIABLE_LIST_CLASS.isAssignableFrom(list.getClass())) {
                    return;
                }
                if ((list instanceof PrimitiveNonBoxingCollection) && (list instanceof Internal.ProtobufList)) {
                    AbstractProtobufList abstractProtobufList = (AbstractProtobufList) ((Internal.ProtobufList) list);
                    if (abstractProtobufList.isMutable) {
                        abstractProtobufList.isMutable = false;
                        return;
                    }
                    return;
                }
                unmodifiableList = Collections.unmodifiableList(list);
            }
            UnsafeUtil.putObject(j, obj, unmodifiableList);
        }

        @Override // com.google.protobuf.ListFieldSchema
        public final void mergeListsAt(long j, Object obj, Object obj2) {
            List list = (List) UnsafeUtil.getObject(j, obj2);
            List mutableListAt = mutableListAt(list.size(), j, obj);
            int size = mutableListAt.size();
            int size2 = list.size();
            if (size > 0 && size2 > 0) {
                mutableListAt.addAll(list);
            }
            if (size > 0) {
                list = mutableListAt;
            }
            UnsafeUtil.putObject(j, obj, list);
        }

        @Override // com.google.protobuf.ListFieldSchema
        public final List mutableListAt(long j, Object obj) {
            return mutableListAt(10, j, obj);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static List mutableListAt(int i, long j, Object obj) {
            LazyStringArrayList lazyStringArrayList;
            List list = (List) UnsafeUtil.getObject(j, obj);
            if (list.isEmpty()) {
                List lazyStringArrayList2 = list instanceof LazyStringList ? new LazyStringArrayList(i) : ((list instanceof PrimitiveNonBoxingCollection) && (list instanceof Internal.ProtobufList)) ? ((Internal.ProtobufList) list).mutableCopyWithCapacity(i) : new ArrayList(i);
                UnsafeUtil.putObject(j, obj, lazyStringArrayList2);
                return lazyStringArrayList2;
            }
            if (UNMODIFIABLE_LIST_CLASS.isAssignableFrom(list.getClass())) {
                ArrayList arrayList = new ArrayList(list.size() + i);
                arrayList.addAll(list);
                UnsafeUtil.putObject(j, obj, arrayList);
                lazyStringArrayList = arrayList;
            } else {
                if (!(list instanceof UnmodifiableLazyStringList)) {
                    if (!(list instanceof PrimitiveNonBoxingCollection) || !(list instanceof Internal.ProtobufList)) {
                        return list;
                    }
                    Internal.ProtobufList protobufList = (Internal.ProtobufList) list;
                    if (((AbstractProtobufList) protobufList).isMutable) {
                        return list;
                    }
                    Internal.ProtobufList mutableCopyWithCapacity = protobufList.mutableCopyWithCapacity(list.size() + i);
                    UnsafeUtil.putObject(j, obj, mutableCopyWithCapacity);
                    return mutableCopyWithCapacity;
                }
                LazyStringArrayList lazyStringArrayList3 = new LazyStringArrayList(list.size() + i);
                lazyStringArrayList3.addAll((UnmodifiableLazyStringList) list);
                UnsafeUtil.putObject(j, obj, lazyStringArrayList3);
                lazyStringArrayList = lazyStringArrayList3;
            }
            return lazyStringArrayList;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ListFieldSchemaLite extends ListFieldSchema {
        private ListFieldSchemaLite() {
            super();
        }

        @Override // com.google.protobuf.ListFieldSchema
        public final void makeImmutableListAt(long j, Object obj) {
            ((AbstractProtobufList) ((Internal.ProtobufList) UnsafeUtil.getObject(j, obj))).isMutable = false;
        }

        @Override // com.google.protobuf.ListFieldSchema
        public final void mergeListsAt(long j, Object obj, Object obj2) {
            Internal.ProtobufList protobufList = (Internal.ProtobufList) UnsafeUtil.getObject(j, obj);
            Internal.ProtobufList protobufList2 = (Internal.ProtobufList) UnsafeUtil.getObject(j, obj2);
            int size = protobufList.size();
            int size2 = protobufList2.size();
            if (size > 0 && size2 > 0) {
                if (!((AbstractProtobufList) protobufList).isMutable) {
                    protobufList = protobufList.mutableCopyWithCapacity(size2 + size);
                }
                protobufList.addAll(protobufList2);
            }
            if (size > 0) {
                protobufList2 = protobufList;
            }
            UnsafeUtil.putObject(j, obj, protobufList2);
        }

        @Override // com.google.protobuf.ListFieldSchema
        public final List mutableListAt(long j, Object obj) {
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

    static {
        FULL_INSTANCE = new ListFieldSchemaFull();
        LITE_INSTANCE = new ListFieldSchemaLite();
    }

    private ListFieldSchema() {
    }

    public abstract void makeImmutableListAt(long j, Object obj);

    public abstract void mergeListsAt(long j, Object obj, Object obj2);

    public abstract List mutableListAt(long j, Object obj);
}
