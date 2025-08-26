package com.google.dexmaker.dx.rop.cst;

/* loaded from: classes4.dex */
public final class CstNat extends Constant {
    public final CstString descriptor;
    public final CstString name;

    static {
        new CstNat(new CstString("TYPE"), new CstString("Ljava/lang/Class;"));
    }

    public CstNat(CstString cstString, CstString cstString2) {
        if (cstString == null) {
            throw new NullPointerException("name == null");
        }
        if (cstString2 == null) {
            throw new NullPointerException("descriptor == null");
        }
        this.name = cstString;
        this.descriptor = cstString2;
    }

    @Override // com.google.dexmaker.dx.rop.cst.Constant
    public final int compareTo0(Constant constant) {
        CstNat cstNat = (CstNat) constant;
        int iCompareTo = this.name.compareTo((Constant) cstNat.name);
        return iCompareTo != 0 ? iCompareTo : this.descriptor.compareTo((Constant) cstNat.descriptor);
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof CstNat)) {
            return false;
        }
        CstNat cstNat = (CstNat) obj;
        return this.name.equals(cstNat.name) && this.descriptor.equals(cstNat.descriptor);
    }

    public final int hashCode() {
        return this.descriptor.string.hashCode() ^ (this.name.string.hashCode() * 31);
    }

    @Override // com.google.dexmaker.dx.util.ToHuman
    public final String toHuman() {
        return this.name.toHuman() + ':' + this.descriptor.toHuman();
    }

    public final String toString() {
        return "nat{" + toHuman() + '}';
    }
}
