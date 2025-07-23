package com.google.dexmaker.dx.rop.cst;

import com.google.dexmaker.dx.rop.type.Prototype;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class CstBaseMethodRef extends CstMemberRef {
    public final Prototype prototype;

    public CstBaseMethodRef(CstType cstType, CstNat cstNat) {
        super(cstType, cstNat);
        this.prototype = Prototype.intern(this.nat.descriptor.string);
    }

    @Override // com.google.dexmaker.dx.rop.cst.CstMemberRef, com.google.dexmaker.dx.rop.cst.Constant
    public final int compareTo0(Constant constant) {
        int compareTo0 = super.compareTo0(constant);
        return compareTo0 != 0 ? compareTo0 : this.prototype.compareTo(((CstBaseMethodRef) constant).prototype);
    }
}
