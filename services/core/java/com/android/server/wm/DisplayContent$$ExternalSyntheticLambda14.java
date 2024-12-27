package com.android.server.wm;

import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class DisplayContent$$ExternalSyntheticLambda14 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;

    public /* synthetic */ DisplayContent$$ExternalSyntheticLambda14(int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        int i2 = this.f$0;
        switch (i) {
            case 0:
                if (((Task) obj).getRootTask().mTaskId == i2) {}
                break;
            case 1:
                WindowState windowState = (WindowState) obj;
                if (windowState.mOwnerUid != i2 || !windowState.isFocused()) {}
                break;
            default:
                WindowState windowState2 = (WindowState) obj;
                if (windowState2.mAttrs.type != 2005
                        || windowState2.mOwnerUid != i2
                        || windowState2.mPermanentlyHidden
                        || windowState2.mWindowRemovalAllowed) {}
                break;
        }
        return false;
    }
}
