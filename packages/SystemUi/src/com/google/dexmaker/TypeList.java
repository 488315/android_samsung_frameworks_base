package com.google.dexmaker;

import com.google.dexmaker.p043dx.rop.type.StdTypeList;
import java.util.Arrays;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TypeList {
    public final StdTypeList ropTypes;
    public final TypeId[] types;

    public TypeList(TypeId[] typeIdArr) {
        this.types = (TypeId[]) typeIdArr.clone();
        this.ropTypes = new StdTypeList(typeIdArr.length);
        for (int i = 0; i < typeIdArr.length; i++) {
            this.ropTypes.set(i, typeIdArr[i].ropType);
        }
    }

    public final boolean equals(Object obj) {
        return (obj instanceof TypeList) && Arrays.equals(((TypeList) obj).types, this.types);
    }

    public final int hashCode() {
        return Arrays.hashCode(this.types);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (true) {
            TypeId[] typeIdArr = this.types;
            if (i >= typeIdArr.length) {
                return sb.toString();
            }
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(typeIdArr[i]);
            i++;
        }
    }
}
