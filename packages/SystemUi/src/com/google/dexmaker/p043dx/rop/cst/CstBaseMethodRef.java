package com.google.dexmaker.p043dx.rop.cst;

import com.google.dexmaker.p043dx.rop.type.Prototype;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class CstBaseMethodRef extends CstMemberRef {
    public final Prototype prototype;

    public CstBaseMethodRef(CstType cstType, CstNat cstNat) {
        super(cstType, cstNat);
        this.prototype = Prototype.intern(this.nat.descriptor.string);
    }

    @Override // com.google.dexmaker.p043dx.rop.cst.CstMemberRef, com.google.dexmaker.p043dx.rop.cst.Constant
    public final int compareTo0(Constant constant) {
        int compareTo0 = super.compareTo0(constant);
        return compareTo0 != 0 ? compareTo0 : this.prototype.compareTo(((CstBaseMethodRef) constant).prototype);
    }
}
