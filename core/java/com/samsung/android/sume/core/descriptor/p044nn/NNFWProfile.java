package com.samsung.android.sume.core.descriptor.p044nn;

import com.samsung.android.sume.core.types.HwUnit;
import com.samsung.android.sume.core.types.p045nn.NNFW;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/* loaded from: classes4.dex */
public class NNFWProfile implements Serializable {

    /* renamed from: fw */
    private final NNFW f3054fw;

    /* renamed from: hw */
    private final HwUnit f3055hw;
    private int units;

    public NNFWProfile(NNFW fw, HwUnit hw, int units) {
        this.f3054fw = fw;
        this.f3055hw = hw;
        this.units = units;
    }

    public NNFW getFw() {
        return this.f3054fw;
    }

    public HwUnit getHw() {
        return this.f3055hw;
    }

    public int getUnits() {
        return this.units;
    }

    public List<NNFWProfile> flatten() {
        int copies = this.units;
        this.units = 1;
        return Collections.nCopies(copies, this);
    }
}
