package com.android.systemui.decor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class RoundedCornerDecorProviderImplKt {
    /* JADX WARN: Removed duplicated region for block: B:11:0x0024 A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0027 A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0029 A[ORIG_RETURN, RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final int access$toLayoutGravity(int i, int i2) {
        if (i2 == 0) {
            if (i == 0) {
                return 3;
            }
            if (i != 1) {
                if (i != 2) {
                }
            }
        }
        if (i2 == 1) {
            if (i != 0) {
                if (i == 1) {
                    return 3;
                }
                if (i != 2) {
                    return 5;
                }
            }
        }
        if (i2 != 3) {
            if (i != 0) {
                if (i != 1) {
                    return i != 2 ? 48 : 3;
                }
            }
            return 5;
        }
        if (i != 0) {
            if (i != 1) {
                return i != 2 ? 3 : 80;
            }
            return 5;
        }
    }
}
