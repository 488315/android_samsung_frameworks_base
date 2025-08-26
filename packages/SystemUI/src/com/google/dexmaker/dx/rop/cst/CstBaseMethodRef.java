package com.google.dexmaker.dx.rop.cst;

import com.google.dexmaker.dx.rop.type.Prototype;

/* loaded from: classes4.dex */
public abstract class CstBaseMethodRef extends CstMemberRef {
    public final Prototype prototype;

    public CstBaseMethodRef(CstType cstType, CstNat cstNat) {
        super(cstType, cstNat);
        this.prototype = Prototype.intern(this.nat.descriptor.string);
    }

    @Override // com.google.dexmaker.dx.rop.cst.CstMemberRef, com.google.dexmaker.dx.rop.cst.Constant
    public final int compareTo0(Constant constant) {
        int iCompareTo0 = super.compareTo0(constant);
        return iCompareTo0 != 0 ? iCompareTo0 : this.prototype.compareTo(((CstBaseMethodRef) constant).prototype);
    }
}
