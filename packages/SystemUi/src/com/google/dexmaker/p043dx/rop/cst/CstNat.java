package com.google.dexmaker.p043dx.rop.cst;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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

    @Override // com.google.dexmaker.p043dx.rop.cst.Constant
    public final int compareTo0(Constant constant) {
        CstNat cstNat = (CstNat) constant;
        int compareTo = this.name.compareTo((Constant) cstNat.name);
        return compareTo != 0 ? compareTo : this.descriptor.compareTo((Constant) cstNat.descriptor);
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof CstNat)) {
            return false;
        }
        CstNat cstNat = (CstNat) obj;
        return this.name.equals(cstNat.name) && this.descriptor.equals(cstNat.descriptor);
    }

    public final int hashCode() {
        return this.descriptor.hashCode() ^ (this.name.hashCode() * 31);
    }

    @Override // com.google.dexmaker.p043dx.util.ToHuman
    public final String toHuman() {
        return this.name.toHuman() + ':' + this.descriptor.toHuman();
    }

    public final String toString() {
        return "nat{" + toHuman() + '}';
    }
}
