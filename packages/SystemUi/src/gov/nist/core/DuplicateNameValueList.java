package gov.nist.core;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class DuplicateNameValueList implements Serializable, Cloneable {
    private static final long serialVersionUID = -5611332957903796952L;
    private MultiValueMapImpl<NameValue> nameValueMap = new MultiValueMapImpl<>();
    private String separator = ";";

    public final Object clone() {
        DuplicateNameValueList duplicateNameValueList = new DuplicateNameValueList();
        duplicateNameValueList.separator = this.separator;
        Iterator it = this.nameValueMap.values().iterator();
        while (it.hasNext()) {
            duplicateNameValueList.set((NameValue) ((NameValue) it.next()).clone());
        }
        return duplicateNameValueList;
    }

    public final String encode() {
        StringBuffer stringBuffer = new StringBuffer();
        if (!this.nameValueMap.isEmpty()) {
            Iterator it = this.nameValueMap.values().iterator();
            if (it.hasNext()) {
                while (true) {
                    Object next = it.next();
                    if (next instanceof GenericObject) {
                        ((GenericObject) next).encode(stringBuffer);
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
        return stringBuffer.toString();
    }

    public final boolean equals(Object obj) {
        if (obj == null || !obj.getClass().equals(getClass())) {
            return false;
        }
        DuplicateNameValueList duplicateNameValueList = (DuplicateNameValueList) obj;
        if (this.nameValueMap.size() != duplicateNameValueList.nameValueMap.size()) {
            return false;
        }
        for (String str : this.nameValueMap.keySet()) {
            List list = this.nameValueMap.get((Object) str.toLowerCase());
            List list2 = duplicateNameValueList.nameValueMap.get((Object) str);
            if (list2 == null || !list2.equals(list)) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        return this.nameValueMap.keySet().hashCode();
    }

    public final boolean isEmpty() {
        return this.nameValueMap.isEmpty();
    }

    public final void set(NameValue nameValue) {
        this.nameValueMap.put((Object) nameValue, nameValue.getName().toLowerCase());
    }

    public final String toString() {
        return encode();
    }
}
