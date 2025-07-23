package org.apache.commons.lang3.builder;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
