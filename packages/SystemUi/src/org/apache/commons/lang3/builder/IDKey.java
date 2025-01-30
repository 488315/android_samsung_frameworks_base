package org.apache.commons.lang3.builder;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class IDKey {

    /* renamed from: id */
    public final int f674id;
    public final Object value;

    public IDKey(Object obj) {
        this.f674id = System.identityHashCode(obj);
        this.value = obj;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof IDKey)) {
            return false;
        }
        IDKey iDKey = (IDKey) obj;
        return this.f674id == iDKey.f674id && this.value == iDKey.value;
    }

    public final int hashCode() {
        return this.f674id;
    }
}
