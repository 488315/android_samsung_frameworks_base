package com.android.systemui.biometrics;

import com.android.systemui.Dumpable;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public interface AuthDialog extends Dumpable {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class LayoutParams {
        public final int mMediumHeight;
        public final int mMediumWidth;

        public LayoutParams(int i, int i2) {
            this.mMediumWidth = i;
            this.mMediumHeight = i2;
        }
    }
}
