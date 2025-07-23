package com.android.internal.org.bouncycastle.math.ec;

import com.android.internal.org.bouncycastle.math.ec.ECCurve;
import com.android.internal.org.bouncycastle.math.ec.ECPoint;
import java.math.BigInteger;

/* loaded from: classes5.dex */
public class WTauNafMultiplier extends AbstractECMultiplier {
    static final String PRECOMP_NAME = "bc_wtnaf";

    @Override // com.android.internal.org.bouncycastle.math.ec.AbstractECMultiplier
    protected ECPoint multiplyPositive(ECPoint eCPoint, BigInteger bigInteger) {
        if (!(eCPoint instanceof ECPoint.AbstractF2m)) {
            throw new IllegalArgumentException("Only ECPoint.AbstractF2m can be used in WTauNafMultiplier");
        }
        ECPoint.AbstractF2m abstractF2m = (ECPoint.AbstractF2m) eCPoint;
        ECCurve.AbstractF2m abstractF2m2 = (ECCurve.AbstractF2m) abstractF2m.getCurve();
        byte byteValue = abstractF2m2.getA().toBigInteger().byteValue();
        byte mu = Tnaf.getMu(byteValue);
        return multiplyWTnaf(abstractF2m, Tnaf.partModReduction(abstractF2m2, bigInteger, byteValue, mu, (byte) 10), byteValue, mu);
    }

    private ECPoint.AbstractF2m multiplyWTnaf(ECPoint.AbstractF2m abstractF2m, ZTauElement zTauElement, byte b, byte b2) {
        return multiplyFromWTnaf(abstractF2m, Tnaf.tauAdicWNaf(b2, zTauElement, 4, Tnaf.getTw(b2, 4).intValue(), b == 0 ? Tnaf.alpha0 : Tnaf.alpha1));
    }

    private static ECPoint.AbstractF2m multiplyFromWTnaf(final ECPoint.AbstractF2m abstractF2m, byte[] bArr) {
        ECCurve.AbstractF2m abstractF2m2 = (ECCurve.AbstractF2m) abstractF2m.getCurve();
        final byte byteValue = abstractF2m2.getA().toBigInteger().byteValue();
        ECPoint.AbstractF2m[] preComp = ((WTauNafPreCompInfo) abstractF2m2.precompute(abstractF2m, PRECOMP_NAME, new PreCompCallback() { // from class: com.android.internal.org.bouncycastle.math.ec.WTauNafMultiplier.1
            @Override // com.android.internal.org.bouncycastle.math.ec.PreCompCallback
            public PreCompInfo precompute(PreCompInfo preCompInfo) {
                if (preCompInfo instanceof WTauNafPreCompInfo) {
                    return preCompInfo;
                }
                WTauNafPreCompInfo wTauNafPreCompInfo = new WTauNafPreCompInfo();
                wTauNafPreCompInfo.setPreComp(Tnaf.getPreComp(ECPoint.AbstractF2m.this, byteValue));
                return wTauNafPreCompInfo;
            }
        })).getPreComp();
        ECPoint.AbstractF2m[] abstractF2mArr = new ECPoint.AbstractF2m[preComp.length];
        for (int i = 0; i < preComp.length; i++) {
            abstractF2mArr[i] = (ECPoint.AbstractF2m) preComp[i].negate();
        }
        ECPoint.AbstractF2m abstractF2m3 = (ECPoint.AbstractF2m) abstractF2m.getCurve().getInfinity();
        int i2 = 0;
        for (int length = bArr.length - 1; length >= 0; length--) {
            i2++;
            byte b = bArr[length];
            if (b != 0) {
                abstractF2m3 = (ECPoint.AbstractF2m) abstractF2m3.tauPow(i2).add(b > 0 ? preComp[b >>> 1] : abstractF2mArr[(-b) >>> 1]);
                i2 = 0;
            }
        }
        return i2 > 0 ? abstractF2m3.tauPow(i2) : abstractF2m3;
    }
}
