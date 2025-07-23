package com.google.dexmaker.dx.rop.cst;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
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

    @Override // com.google.dexmaker.dx.rop.cst.Constant
    public int compareTo0(Constant constant) {
        CstMemberRef cstMemberRef = (CstMemberRef) constant;
        int compareTo = this.definingClass.compareTo((Constant) cstMemberRef.definingClass);
        return compareTo != 0 ? compareTo : this.nat.name.compareTo((Constant) cstMemberRef.nat.name);
    }

    public final boolean equals(Object obj) {
        if (obj != null && getClass() == obj.getClass()) {
            CstMemberRef cstMemberRef = (CstMemberRef) obj;
            if (this.definingClass.equals(cstMemberRef.definingClass) && this.nat.equals(cstMemberRef.nat)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return this.nat.hashCode() ^ (this.definingClass.hashCode() * 31);
    }

    @Override // com.google.dexmaker.dx.util.ToHuman
    public final String toHuman() {
        return this.definingClass.type.toHuman() + '.' + this.nat.toHuman();
    }

    public final String toString() {
        return "method{" + toHuman() + '}';
    }
}
