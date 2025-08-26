package com.android.systemui.kairos;

import com.android.systemui.kairos.internal.Init;

/* loaded from: classes2.dex */
public final class IncrementalInit extends Incremental {
    public final Init init;

    public IncrementalInit(Init init) {
        super(null);
        this.init = init;
    }

    @Override // com.android.systemui.kairos.State
    public final Init getInit$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos() {
        return this.init;
    }
}
