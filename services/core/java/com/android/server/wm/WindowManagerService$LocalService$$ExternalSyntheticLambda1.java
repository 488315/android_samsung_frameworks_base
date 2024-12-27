package com.android.server.wm;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class WindowManagerService$LocalService$$ExternalSyntheticLambda1
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ String f$0;

    public /* synthetic */ WindowManagerService$LocalService$$ExternalSyntheticLambda1(
            String str, int i) {
        this.$r8$classId = i;
        this.f$0 = str;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        String str = this.f$0;
        DisplayContent displayContent = (DisplayContent) obj;
        switch (i) {
            case 0:
                RefreshRatePolicy refreshRatePolicy =
                        displayContent.mDisplayPolicy.mRefreshRatePolicy;
                refreshRatePolicy.mFixedRefreshRatePackages.remove(str);
                refreshRatePolicy.mWmService.requestTraversal();
                break;
            default:
                RefreshRatePolicy refreshRatePolicy2 =
                        displayContent.mDisplayPolicy.mRefreshRatePolicy;
                refreshRatePolicy2.mNonHighRefreshRatePackages.mPackages.remove(str);
                refreshRatePolicy2.mWmService.requestTraversal();
                break;
        }
    }
}
