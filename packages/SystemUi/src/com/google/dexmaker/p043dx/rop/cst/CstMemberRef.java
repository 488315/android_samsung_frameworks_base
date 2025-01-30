package com.google.dexmaker.p043dx.rop.cst;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class CstMemberRef extends TypedConstant {
    public final CstType definingClass;
    public final CstNat nat;

    public CstMemberRef(CstType cstType, CstNat cstNat) {
        if (cstType == null) {
            throw new NullPointerException("definingClass == null");
        }
        if (cstNat == null) {
            throw new NullPointerException("nat == null");
        }
        this.definingClass = cstType;
        this.nat = cstNat;
    }

    @Override // com.google.dexmaker.p043dx.rop.cst.Constant
    public int compareTo0(Constant constant) {
        CstMemberRef cstMemberRef = (CstMemberRef) constant;
        int compareTo = this.definingClass.compareTo((Constant) cstMemberRef.definingClass);
        return compareTo != 0 ? compareTo : this.nat.name.compareTo((Constant) cstMemberRef.nat.name);
    }

    public final boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CstMemberRef cstMemberRef = (CstMemberRef) obj;
        return this.definingClass.equals(cstMemberRef.definingClass) && this.nat.equals(cstMemberRef.nat);
    }

    public final int hashCode() {
        return this.nat.hashCode() ^ (this.definingClass.hashCode() * 31);
    }

    @Override // com.google.dexmaker.p043dx.util.ToHuman
    public final String toHuman() {
        return this.definingClass.toHuman() + '.' + this.nat.toHuman();
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("method{");
        typeName();
        sb.append(toHuman());
        sb.append('}');
        return sb.toString();
    }
}
