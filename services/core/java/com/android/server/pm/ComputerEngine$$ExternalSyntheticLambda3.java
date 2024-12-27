package com.android.server.pm;

import android.content.Context;

import com.samsung.android.server.pm.nal.GetAppListHelper;

public final /* synthetic */ class ComputerEngine$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ComputerEngine f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ ComputerEngine$$ExternalSyntheticLambda3(
            ComputerEngine computerEngine, int i, int i2, int i3) {
        this.$r8$classId = i3;
        this.f$0 = computerEngine;
        this.f$1 = i;
        this.f$2 = i2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ComputerEngine computerEngine = this.f$0;
                int i = this.f$1;
                int i2 = this.f$2;
                GetAppListHelper getAppListHelper = computerEngine.mGetAppListHelper;
                Context context = computerEngine.mContext;
                getAppListHelper.getClass();
                GetAppListHelper.requestGetAppListPermIfNeeded(context, i, i2);
                break;
            default:
                ComputerEngine computerEngine2 = this.f$0;
                int i3 = this.f$1;
                int i4 = this.f$2;
                GetAppListHelper getAppListHelper2 = computerEngine2.mGetAppListHelper;
                Context context2 = computerEngine2.mContext;
                getAppListHelper2.getClass();
                GetAppListHelper.requestGetAppListPermIfNeeded(context2, i3, i4);
                break;
        }
    }
}
