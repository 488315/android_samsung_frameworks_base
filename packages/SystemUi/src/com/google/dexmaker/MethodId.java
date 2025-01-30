package com.google.dexmaker;

import androidx.picker.model.AppInfo$$ExternalSyntheticOutline0;
import com.google.dexmaker.dx.rop.cst.CstMethodRef;
import com.google.dexmaker.dx.rop.cst.CstNat;
import com.google.dexmaker.dx.rop.cst.CstString;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
        if (obj instanceof MethodId) {
            MethodId methodId = (MethodId) obj;
            if (methodId.declaringType.equals(this.declaringType) && methodId.name.equals(this.name) && methodId.parameters.equals(this.parameters) && methodId.returnType.equals(this.returnType)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return this.returnType.hashCode() + ((this.parameters.hashCode() + AppInfo$$ExternalSyntheticOutline0.m41m(this.name, (this.declaringType.hashCode() + 527) * 31, 31)) * 31);
    }

    public final String toString() {
        return this.declaringType + "." + this.name + "(" + this.parameters + ")";
    }
}
