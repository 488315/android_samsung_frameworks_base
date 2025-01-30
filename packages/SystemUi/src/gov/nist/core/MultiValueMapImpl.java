package gov.nist.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class MultiValueMapImpl<V> implements Map, Serializable, Cloneable {
    private static final long serialVersionUID = 4275505380960964605L;
    private HashMap<String, ArrayList<V>> map = new HashMap<>();

    @Override // java.util.Map
    public final void clear() {
        Iterator<Map.Entry<String, ArrayList<V>>> it = this.map.entrySet().iterator();
        while (it.hasNext()) {
            it.next().getValue().clear();
        }
        this.map.clear();
    }

    public final Object clone() {
        MultiValueMapImpl multiValueMapImpl = new MultiValueMapImpl();
        multiValueMapImpl.map = (HashMap) this.map.clone();
        return multiValueMapImpl;
    }

    @Override // java.util.Map
    public final boolean containsKey(Object obj) {
        return this.map.containsKey(obj);
    }

    @Override // java.util.Map
    public final boolean containsValue(Object obj) {
        Set<Map.Entry<String, ArrayList<V>>> entrySet = this.map.entrySet();
        if (entrySet == null) {
            return false;
        }
        Iterator<Map.Entry<String, ArrayList<V>>> it = entrySet.iterator();
        while (it.hasNext()) {
            if (it.next().getValue().contains(obj)) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.Map
    public final Set entrySet() {
        return this.map.entrySet();
    }

    @Override // java.util.Map
    public final boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override // java.util.Map
    public final Set keySet() {
        return this.map.keySet();
    }

    @Override // java.util.Map
    public final Object put(Object obj, Object obj2) {
        return this.map.put((String) obj, (ArrayList) ((List) obj2));
    }

    @Override // java.util.Map
    public final void putAll(Map map) {
        for (String str : map.keySet()) {
            ArrayList<V> arrayList = new ArrayList<>();
            arrayList.addAll((Collection) map.get(str));
            this.map.put(str, arrayList);
        }
    }

    @Override // java.util.Map
    public final Object remove(Object obj) {
        return this.map.remove(obj);
    }

    @Override // java.util.Map
    public final int size() {
        return this.map.size();
    }

    @Override // java.util.Map
    public final Collection values() {
        ArrayList arrayList = new ArrayList(this.map.size());
        Iterator<Map.Entry<String, ArrayList<V>>> it = this.map.entrySet().iterator();
        while (it.hasNext()) {
            for (Object obj : it.next().getValue().toArray()) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    @Override // java.util.Map
    public final List get(Object obj) {
        return this.map.get(obj);
    }

    public final void put(Object obj, String str) {
        ArrayList<V> arrayList = this.map.get(str);
        if (arrayList == null) {
            arrayList = new ArrayList<>(10);
            this.map.put(str, arrayList);
        }
        arrayList.add(obj);
    }
}
