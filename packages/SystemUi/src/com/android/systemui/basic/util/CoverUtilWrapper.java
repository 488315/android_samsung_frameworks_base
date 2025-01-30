package com.android.systemui.basic.util;

import com.android.systemui.util.CoverUtil;
import com.samsung.android.cover.CoverState;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CoverUtilWrapper {
    public Runnable mActionBeforeSecureConfirm;
    public CoverState mCoverState;
    public final CoverUtil mCoverUtil;
    public final Map mListeners = new HashMap();

    public CoverUtilWrapper(CoverUtil coverUtil) {
        this.mCoverUtil = coverUtil;
        coverUtil.mCoverStateChangedListeners.add(new CoverUtilWrapper$$ExternalSyntheticLambda0(this));
    }
}
