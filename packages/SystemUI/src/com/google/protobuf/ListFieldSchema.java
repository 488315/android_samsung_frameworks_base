package com.google.protobuf;

import com.google.protobuf.Internal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class ListFieldSchema {
    public static final ListFieldSchemaFull FULL_INSTANCE;
    public static final ListFieldSchemaLite LITE_INSTANCE;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ListFieldSchemaFull extends ListFieldSchema {
        public static final Class UNMODIFIABLE_LIST_CLASS = Collections.unmodifiableList(Collections.EMPTY_LIST).getClass();

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

        public static List mutableListAt(int i, long j, Object obj) {
            List list = (List) UnsafeUtil.getObject(j, obj);
            if (list.isEmpty()) {
                List lazyStringArrayList = list instanceof LazyStringList ? new LazyStringArrayList(i) : ((list instanceof PrimitiveNonBoxingCollection) && (list instanceof Internal.ProtobufList)) ? ((Internal.ProtobufList) list).mutableCopyWithCapacity(i) : new ArrayList(i);
                UnsafeUtil.putObject(j, obj, lazyStringArrayList);
                return lazyStringArrayList;
            }
            if (UNMODIFIABLE_LIST_CLASS.isAssignableFrom(list.getClass())) {
                ArrayList arrayList = new ArrayList(list.size() + i);
                arrayList.addAll(list);
                UnsafeUtil.putObject(j, obj, arrayList);
                return arrayList;
            }
            if (list instanceof UnmodifiableLazyStringList) {
                LazyStringArrayList lazyStringArrayList2 = new LazyStringArrayList(list.size() + i);
                lazyStringArrayList2.addAll((UnmodifiableLazyStringList) list);
                UnsafeUtil.putObject(j, obj, lazyStringArrayList2);
                return lazyStringArrayList2;
            }
            if ((list instanceof PrimitiveNonBoxingCollection) && (list instanceof Internal.ProtobufList)) {
                Internal.ProtobufList protobufList = (Internal.ProtobufList) list;
                if (!((AbstractProtobufList) protobufList).isMutable) {
                    Internal.ProtobufList mutableCopyWithCapacity = protobufList.mutableCopyWithCapacity(list.size() + i);
                    UnsafeUtil.putObject(j, obj, mutableCopyWithCapacity);
                    return mutableCopyWithCapacity;
                }
            }
            return list;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public abstract void makeImmutableListAt(long j, Object obj);

    public abstract void mergeListsAt(long j, Object obj, Object obj2);

    public abstract List mutableListAt(long j, Object obj);

    private ListFieldSchema() {
    }
}
