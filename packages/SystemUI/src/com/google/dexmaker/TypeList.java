package com.google.dexmaker;

import com.google.dexmaker.dx.rop.type.StdTypeList;
import java.util.Arrays;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
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
