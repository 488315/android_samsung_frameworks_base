package com.android.systemui.basic.util;

import com.android.systemui.globalactions.features.CoverSupportStrategy$$ExternalSyntheticLambda1;
import com.android.systemui.util.CoverUtil;
import com.samsung.android.cover.CoverState;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class CoverUtilWrapper {
    public CoverSupportStrategy$$ExternalSyntheticLambda1 mActionBeforeSecureConfirm;
    public CoverState mCoverState;
    public final CoverUtilWrapper$$ExternalSyntheticLambda0 mCoverStateChangedListener;
    public final CoverUtil mCoverUtil;
    public final Map mListeners = new HashMap();

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.basic.util.CoverUtilWrapper$$ExternalSyntheticLambda0, com.android.systemui.util.CoverUtil$CoverStateChangedListener] */
    public CoverUtilWrapper(CoverUtil coverUtil) {
        this.mCoverUtil = coverUtil;
        ?? r0 = new CoverUtil.CoverStateChangedListener() { // from class: com.android.systemui.basic.util.CoverUtilWrapper$$ExternalSyntheticLambda0
            @Override // com.android.systemui.util.CoverUtil.CoverStateChangedListener
            public final void onUpdateCoverState(CoverState coverState) {
                CoverSupportStrategy$$ExternalSyntheticLambda1 coverSupportStrategy$$ExternalSyntheticLambda1;
                CoverUtilWrapper coverUtilWrapper = CoverUtilWrapper.this;
                if (((HashMap) coverUtilWrapper.mListeners).isEmpty()) {
                    return;
                }
                coverUtilWrapper.mCoverState = coverState;
                boolean switchState = coverState.getSwitchState();
                final boolean z = !switchState;
                final int type = coverState.getType();
                if (switchState && ((coverUtilWrapper.mCoverState.getType() == 15 || coverUtilWrapper.mCoverState.getType() == 16 || coverUtilWrapper.mCoverState.getType() == 8) && (coverSupportStrategy$$ExternalSyntheticLambda1 = coverUtilWrapper.mActionBeforeSecureConfirm) != null)) {
                    coverSupportStrategy$$ExternalSyntheticLambda1.run();
                    coverUtilWrapper.mActionBeforeSecureConfirm = null;
                }
                ((HashMap) coverUtilWrapper.mListeners).forEach(new BiConsumer() { // from class: com.android.systemui.basic.util.CoverUtilWrapper$$ExternalSyntheticLambda1
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        ((BiConsumer) obj2).accept(Boolean.valueOf(z), Integer.valueOf(type));
                    }
                });
            }
        };
        this.mCoverStateChangedListener = r0;
        coverUtil.addListener(r0);
    }
}
