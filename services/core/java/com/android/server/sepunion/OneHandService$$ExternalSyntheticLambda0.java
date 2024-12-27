package com.android.server.sepunion;

import android.provider.Settings;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class OneHandService$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ OneHandService$$ExternalSyntheticLambda0(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                OneHandService oneHandService = (OneHandService) obj;
                if (Settings.System.getIntForUser(
                                oneHandService.mContext.getContentResolver(),
                                "any_screen_running",
                                0,
                                -2)
                        == 1) {
                    Settings.System.putIntForUser(
                            oneHandService.mContext.getContentResolver(),
                            "any_screen_running",
                            0,
                            -2);
                    break;
                }
                break;
            default:
                OneHandService.m867$$Nest$mstartGestureService(OneHandService.this);
                break;
        }
    }
}
