package gov.nist.core;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class NameValueList implements Serializable, Cloneable, Map<String, NameValue> {
    private static final long serialVersionUID = -6998271876574260243L;
    private Map<String, NameValue> hmap;
    private String separator;

    public NameValueList() {
        this.separator = ";";
        this.hmap = new LinkedHashMap();
    }

    @Override // java.util.Map
    public final void clear() {
        this.hmap.clear();
    }

    public final Object clone() {
        NameValueList nameValueList = new NameValueList();
        nameValueList.separator = this.separator;
        Iterator<NameValue> it = this.hmap.values().iterator();
        while (it.hasNext()) {
            nameValueList.set((NameValue) it.next().clone());
        }
        return nameValueList;
    }

    @Override // java.util.Map
    public final boolean containsKey(Object obj) {
        return this.hmap.containsKey(obj.toString().toLowerCase());
    }

    @Override // java.util.Map
    public final boolean containsValue(Object obj) {
        return this.hmap.containsValue(obj);
    }

    public final void delete(String str) {
        String lowerCase = str.toLowerCase();
        if (this.hmap.containsKey(lowerCase)) {
            this.hmap.remove(lowerCase);
        }
    }

    public final String encode() {
        return encode(new StringBuffer()).toString();
    }

    @Override // java.util.Map
    public final Set<Map.Entry<String, NameValue>> entrySet() {
        return this.hmap.entrySet();
    }

    @Override // java.util.Map
    public final boolean equals(Object obj) {
        if (obj == null || !obj.getClass().equals(getClass())) {
            return false;
        }
        NameValueList nameValueList = (NameValueList) obj;
        if (this.hmap.size() != nameValueList.hmap.size()) {
            return false;
        }
        for (String str : this.hmap.keySet()) {
            NameValue nameValue = getNameValue(str);
            NameValue nameValue2 = nameValueList.hmap.get(str);
            if (nameValue2 == null || !nameValue2.equals(nameValue)) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.Map
    public final NameValue get(Object obj) {
        return this.hmap.get(obj.toString().toLowerCase());
    }

    public final NameValue getNameValue(String str) {
        return this.hmap.get(str.toLowerCase());
    }

    public final Iterator getNames() {
        return this.hmap.keySet().iterator();
    }

    public final Object getValue(String str) {
        NameValue nameValue = getNameValue(str.toLowerCase());
        if (nameValue != null) {
            return nameValue.getValueAsObject();
        }
        return null;
    }

    @Override // java.util.Map
    public final int hashCode() {
        return this.hmap.keySet().hashCode();
    }

    @Override // java.util.Map
    public final boolean isEmpty() {
        return this.hmap.isEmpty();
    }

    @Override // java.util.Map
    public final Set<String> keySet() {
        return this.hmap.keySet();
    }

    @Override // java.util.Map
    public final NameValue put(String str, NameValue nameValue) {
        return this.hmap.put(str, nameValue);
    }

    @Override // java.util.Map
    public final void putAll(Map<? extends String, ? extends NameValue> map) {
        this.hmap.putAll(map);
    }

    @Override // java.util.Map
    public final NameValue remove(Object obj) {
        return this.hmap.remove(obj.toString().toLowerCase());
    }

    public final void set(NameValue nameValue) {
        this.hmap.put(nameValue.getName().toLowerCase(), nameValue);
    }

    public final void setSeparator(String str) {
        this.separator = str;
    }

    @Override // java.util.Map
    public final int size() {
        return this.hmap.size();
    }

    public final String toString() {
        return encode();
    }

    @Override // java.util.Map
    public final Collection<NameValue> values() {
        return this.hmap.values();
    }

    public final StringBuffer encode(StringBuffer stringBuffer) {
        if (!this.hmap.isEmpty()) {
            Iterator<NameValue> it = this.hmap.values().iterator();
            if (it.hasNext()) {
                while (true) {
                    NameValue next = it.next();
                    if (next instanceof GenericObject) {
                        next.encode(stringBuffer);
                    } else {
                        stringBuffer.append(next.toString());
                    }
                    if (!it.hasNext()) {
                        break;
                    }
                    stringBuffer.append(this.separator);
                }
            }
        }
        return stringBuffer;
    }

    public final void set(Object obj, String str) {
        this.hmap.put(str.toLowerCase(), new NameValue(str, obj));
    }

    public NameValueList(boolean z) {
        this.separator = ";";
        if (z) {
            this.hmap = new ConcurrentHashMap();
        } else {
            this.hmap = new LinkedHashMap();
        }
    }
}
