package com.samsung.android.sume.core.descriptor.nn;

import com.samsung.android.sume.core.types.HwUnit;
import com.samsung.android.sume.core.types.nn.NNFW;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/* loaded from: classes6.dex */
public class NNFWProfile implements Serializable {
    private final NNFW fw;
    private final HwUnit hw;
    private int units;

    public NNFWProfile(NNFW fw, HwUnit hw, int units) {
        this.fw = fw;
        this.hw = hw;
        this.units = units;
    }

    public NNFW getFw() {
        return this.fw;
    }

    public HwUnit getHw() {
        return this.hw;
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
