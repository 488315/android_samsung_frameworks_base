package com.android.systemui.biometrics;

import com.android.systemui.Dumpable;

public interface AuthDialog extends Dumpable {

    public final class LayoutParams {
        public final int mMediumHeight;
        public final int mMediumWidth;

        public LayoutParams(int i, int i2) {
            this.mMediumWidth = i;
            this.mMediumHeight = i2;
        }
    }
}
