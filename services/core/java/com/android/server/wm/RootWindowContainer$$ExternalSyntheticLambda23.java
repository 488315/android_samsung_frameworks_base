package com.android.server.wm;

import com.android.internal.util.ToBooleanFunction;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class RootWindowContainer$$ExternalSyntheticLambda23
        implements ToBooleanFunction {
    public final /* synthetic */ int f$0;
    public final /* synthetic */ boolean[] f$1;

    public /* synthetic */ RootWindowContainer$$ExternalSyntheticLambda23(int i, boolean[] zArr) {
        this.f$0 = i;
        this.f$1 = zArr;
    }

    public final boolean apply(Object obj) {
        int i = this.f$0;
        boolean[] zArr = this.f$1;
        WindowState windowState = (WindowState) obj;
        if (windowState.mOwnerUid == i && windowState.isVisible()) {
            zArr[0] = true;
        }
        if (windowState.mAttrs.type == 2040) {
            return zArr[0] && windowState.mOwnerUid != i;
        }
        return false;
    }
}
