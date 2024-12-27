package com.android.systemui.basic.util;

import com.android.systemui.util.CoverUtil;
import com.samsung.android.cover.CoverState;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public final class CoverUtilWrapper {
    public Runnable mActionBeforeSecureConfirm;
    public CoverState mCoverState;
    public final CoverUtilWrapper$$ExternalSyntheticLambda0 mCoverStateChangedListener;
    public final CoverUtil mCoverUtil;
    public final Map mListeners = new HashMap();

    public CoverUtilWrapper(CoverUtil coverUtil) {
        this.mCoverUtil = coverUtil;
        coverUtil.addListener(new CoverUtil.CoverStateChangedListener() { // from class: com.android.systemui.basic.util.CoverUtilWrapper$$ExternalSyntheticLambda0
            @Override // com.android.systemui.util.CoverUtil.CoverStateChangedListener
            public final void onUpdateCoverState(CoverState coverState) {
                Runnable runnable;
                CoverUtilWrapper coverUtilWrapper = CoverUtilWrapper.this;
                if (((HashMap) coverUtilWrapper.mListeners).isEmpty()) {
                    return;
                }
                coverUtilWrapper.mCoverState = coverState;
                final boolean z = !coverState.getSwitchState();
                final int type = coverState.getType();
                if (!z && ((coverUtilWrapper.mCoverState.getType() == 15 || coverUtilWrapper.mCoverState.getType() == 16 || coverUtilWrapper.mCoverState.getType() == 8) && (runnable = coverUtilWrapper.mActionBeforeSecureConfirm) != null)) {
                    runnable.run();
                    coverUtilWrapper.mActionBeforeSecureConfirm = null;
                }
                ((HashMap) coverUtilWrapper.mListeners).forEach(new BiConsumer() { // from class: com.android.systemui.basic.util.CoverUtilWrapper$$ExternalSyntheticLambda1
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((BiConsumer) obj2).accept(Boolean.valueOf(z), Integer.valueOf(type));
                    }
                });
            }
        });
    }
}
