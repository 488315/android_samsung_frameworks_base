package androidx.arch.core.internal;

import androidx.arch.core.internal.SafeIterableMap;
import java.util.HashMap;

/* loaded from: classes.dex */
public class FastSafeIterableMap extends SafeIterableMap {
    public final HashMap mHashMap = new HashMap();

    @Override // androidx.arch.core.internal.SafeIterableMap
    public final SafeIterableMap.Entry get(Object obj) {
        return (SafeIterableMap.Entry) this.mHashMap.get(obj);
    }

    @Override // androidx.arch.core.internal.SafeIterableMap
    public final Object putIfAbsent(Object obj, Object obj2) {
        SafeIterableMap.Entry entry = get(obj);
        if (entry != null) {
            return entry.mValue;
        }
        HashMap map = this.mHashMap;
        SafeIterableMap.Entry entry2 = new SafeIterableMap.Entry(obj, obj2);
        this.mSize++;
        SafeIterableMap.Entry entry3 = this.mEnd;
        if (entry3 == null) {
            this.mStart = entry2;
            this.mEnd = entry2;
        } else {
            entry3.mNext = entry2;
            entry2.mPrevious = entry3;
            this.mEnd = entry2;
        }
        map.put(obj, entry2);
        return null;
    }

    @Override // androidx.arch.core.internal.SafeIterableMap
    public final Object remove(Object obj) {
        Object objRemove = super.remove(obj);
        this.mHashMap.remove(obj);
        return objRemove;
    }
}
