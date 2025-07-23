package com.google.dexmaker;

import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.google.dexmaker.dx.rop.cst.CstMethodRef;
import com.google.dexmaker.dx.rop.cst.CstNat;
import com.google.dexmaker.dx.rop.cst.CstString;
import java.util.Arrays;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class MethodId {
    public final TypeId declaringType;
    public final String name;
    public final TypeList parameters;
    public final TypeId returnType;

    public MethodId(TypeId typeId, TypeId typeId2, String str, TypeList typeList) {
        if (typeId == null || typeId2 == null || str == null || typeList == null) {
            throw null;
        }
        this.declaringType = typeId;
        this.returnType = typeId2;
        this.name = str;
        this.parameters = typeList;
        CstString cstString = new CstString(str);
        StringBuilder sb = new StringBuilder("(");
        for (TypeId typeId3 : typeList.types) {
            sb.append(typeId3.name);
        }
        sb.append(")");
        sb.append(this.returnType.name);
        new CstMethodRef(typeId.constant, new CstNat(cstString, new CstString(sb.toString())));
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof MethodId)) {
            return false;
        }
        MethodId methodId = (MethodId) obj;
        return methodId.declaringType.equals(this.declaringType) && methodId.name.equals(this.name) && methodId.parameters.equals(this.parameters) && methodId.returnType.equals(this.returnType);
    }

    public final int hashCode() {
        return this.returnType.name.hashCode() + ((Arrays.hashCode(this.parameters.types) + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(527, 31, this.declaringType.name), 31, this.name)) * 31);
    }

    public final String toString() {
        return this.declaringType + "." + this.name + "(" + this.parameters + ")";
    }
}
