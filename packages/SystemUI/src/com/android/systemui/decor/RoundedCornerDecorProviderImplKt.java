package com.android.systemui.decor;

/* loaded from: classes2.dex */
public abstract class RoundedCornerDecorProviderImplKt {
    public static final int access$toLayoutGravity(int i, int i2) {
        if (i2 != 0) {
            if (i2 != 1) {
                if (i2 != 3) {
                    if (i == 0) {
                        return 5;
                    }
                    if (i == 1) {
                        return 80;
                    }
                    if (i != 2) {
                        return 48;
                    }
                } else {
                    if (i == 0) {
                        return 48;
                    }
                    if (i == 1) {
                        return 5;
                    }
                    if (i == 2) {
                        return 80;
                    }
                }
            } else {
                if (i == 0) {
                    return 80;
                }
                if (i != 1) {
                    return i != 2 ? 5 : 48;
                }
            }
        } else if (i != 0) {
            if (i != 1) {
                return i != 2 ? 80 : 5;
            }
            return 48;
        }
        return 3;
    }
}
