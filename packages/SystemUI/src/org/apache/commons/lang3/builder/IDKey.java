package org.apache.commons.lang3.builder;

/* loaded from: classes4.dex */
public final class IDKey {
    public final int id;
    public final Object value;

    public IDKey(Object obj) {
        this.id = System.identityHashCode(obj);
        this.value = obj;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof IDKey)) {
            return false;
        }
        IDKey iDKey = (IDKey) obj;
        return this.id == iDKey.id && this.value == iDKey.value;
    }

    public final int hashCode() {
        return this.id;
    }
}
