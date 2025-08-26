package androidx.compose.runtime.collection;

import androidx.collection.MutableObjectList;
import androidx.collection.MutableScatterMap;
import androidx.collection.ObjectListKt;
import androidx.compose.runtime.MovableContent;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.collections.ArraysKt___ArraysJvmKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.jvm.internal.markers.KMutableList;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt___RangesKt;

/* loaded from: classes.dex */
public final class MultiValueMap<K, V> {
    public final MutableScatterMap map;

    private /* synthetic */ MultiValueMap(MutableScatterMap mutableScatterMap) {
        this.map = mutableScatterMap;
    }

    /* renamed from: add-impl, reason: not valid java name */
    public static final void m342addimpl(MutableScatterMap mutableScatterMap, Object obj, Object obj2) {
        int iFindInsertIndex = mutableScatterMap.findInsertIndex(obj);
        boolean z = iFindInsertIndex < 0;
        Object obj3 = z ? null : mutableScatterMap.values[iFindInsertIndex];
        if ((obj3 instanceof List) && (obj3 instanceof KMappedMarker)) {
            boolean z2 = obj3 instanceof KMutableList;
        }
        if (obj3 != null) {
            if (obj3 instanceof MutableObjectList) {
                MutableObjectList mutableObjectList = (MutableObjectList) obj3;
                mutableObjectList.add(obj2);
                obj2 = mutableObjectList;
            } else {
                Object[] objArr = ObjectListKt.EmptyArray;
                MutableObjectList mutableObjectList2 = new MutableObjectList(2);
                mutableObjectList2.add(obj3);
                mutableObjectList2.add(obj2);
                obj2 = mutableObjectList2;
            }
        }
        if (!z) {
            mutableScatterMap.values[iFindInsertIndex] = obj2;
            return;
        }
        int i = ~iFindInsertIndex;
        mutableScatterMap.keys[i] = obj;
        mutableScatterMap.values[i] = obj2;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ MultiValueMap m343boximpl(MutableScatterMap mutableScatterMap) {
        return new MultiValueMap(mutableScatterMap);
    }

    /* renamed from: constructor-impl$default, reason: not valid java name */
    public static MutableScatterMap m344constructorimpl$default() {
        return new MutableScatterMap(0, 1, null);
    }

    /* renamed from: removeLast-impl, reason: not valid java name */
    public static final Object m345removeLastimpl(MutableScatterMap mutableScatterMap, MovableContent movableContent) {
        Object obj = mutableScatterMap.get(movableContent);
        if (obj == null) {
            return null;
        }
        if (!(obj instanceof MutableObjectList)) {
            mutableScatterMap.remove(movableContent);
            return obj;
        }
        MutableObjectList mutableObjectList = (MutableObjectList) obj;
        if (mutableObjectList.isEmpty()) {
            throw new NoSuchElementException("List is empty.");
        }
        int i = mutableObjectList._size - 1;
        Object obj2 = mutableObjectList.get(i);
        mutableObjectList.removeAt(i);
        if (mutableObjectList.isEmpty()) {
            mutableScatterMap.remove(movableContent);
        }
        if (mutableObjectList._size == 1) {
            mutableScatterMap.set(movableContent, mutableObjectList.first());
        }
        return obj2;
    }

    /* renamed from: removeValueIf-impl, reason: not valid java name */
    public static final void m346removeValueIfimpl(MutableScatterMap mutableScatterMap, MovableContent movableContent, Function1 function1) {
        Object obj = mutableScatterMap.get(movableContent);
        if (obj != null) {
            if (!(obj instanceof MutableObjectList)) {
                if (((Boolean) function1.mo781invoke(obj)).booleanValue()) {
                    mutableScatterMap.remove(movableContent);
                    return;
                }
                return;
            }
            MutableObjectList mutableObjectList = (MutableObjectList) obj;
            int i = mutableObjectList._size;
            Object[] objArr = mutableObjectList.content;
            int i2 = 0;
            IntRange intRangeUntil = RangesKt___RangesKt.until(0, i);
            int i3 = intRangeUntil.first;
            int i4 = intRangeUntil.last;
            if (i3 <= i4) {
                while (true) {
                    objArr[i3 - i2] = objArr[i3];
                    if (((Boolean) function1.mo781invoke(objArr[i3])).booleanValue()) {
                        i2++;
                    }
                    if (i3 == i4) {
                        break;
                    } else {
                        i3++;
                    }
                }
            }
            Arrays.fill(objArr, i - i2, i, (Object) null);
            mutableObjectList._size -= i2;
            if (mutableObjectList.isEmpty()) {
                mutableScatterMap.remove(movableContent);
            }
            if (mutableObjectList._size == 0) {
                mutableScatterMap.set(movableContent, mutableObjectList.first());
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x007b  */
    /* renamed from: values-impl, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final MutableObjectList m347valuesimpl(MutableScatterMap mutableScatterMap) {
        if (mutableScatterMap.isEmpty()) {
            return ObjectListKt.EmptyObjectList;
        }
        MutableObjectList mutableObjectList = new MutableObjectList(0, 1, null);
        Object[] objArr = mutableScatterMap.values;
        long[] jArr = mutableScatterMap.metadata;
        int length = jArr.length - 2;
        if (length >= 0) {
            int i = 0;
            while (true) {
                long j = jArr[i];
                if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i2 = 8 - ((~(i - length)) >>> 31);
                    for (int i3 = 0; i3 < i2; i3++) {
                        if ((255 & j) < 128) {
                            Object obj = objArr[(i << 3) + i3];
                            if (obj instanceof MutableObjectList) {
                                MutableObjectList mutableObjectList2 = (MutableObjectList) obj;
                                if (!mutableObjectList2.isEmpty()) {
                                    int i4 = mutableObjectList._size + mutableObjectList2._size;
                                    Object[] objArr2 = mutableObjectList.content;
                                    if (objArr2.length < i4) {
                                        mutableObjectList.resizeStorage(i4, objArr2);
                                    }
                                    ArraysKt___ArraysJvmKt.copyInto(mutableObjectList2.content, mutableObjectList.content, mutableObjectList._size, 0, mutableObjectList2._size);
                                    mutableObjectList._size += mutableObjectList2._size;
                                }
                            } else {
                                mutableObjectList.add(obj);
                            }
                        }
                        j >>= 8;
                    }
                    if (i2 != 8) {
                        break;
                    }
                    if (i == length) {
                        break;
                    }
                    i++;
                }
            }
        }
        return mutableObjectList;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof MultiValueMap) {
            return Intrinsics.areEqual(this.map, ((MultiValueMap) obj).map);
        }
        return false;
    }

    public final int hashCode() {
        return this.map.hashCode();
    }

    public final String toString() {
        return "MultiValueMap(map=" + this.map + ')';
    }
}
