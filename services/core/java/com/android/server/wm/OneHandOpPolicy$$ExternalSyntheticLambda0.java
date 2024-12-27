package com.android.server.wm;

import android.content.ComponentName;
import android.content.Intent;

public final /* synthetic */ class OneHandOpPolicy$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ OneHandOpPolicy f$0;

    public /* synthetic */ OneHandOpPolicy$$ExternalSyntheticLambda0(
            OneHandOpPolicy oneHandOpPolicy, int i) {
        this.$r8$classId = i;
        this.f$0 = oneHandOpPolicy;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        OneHandOpPolicy oneHandOpPolicy = this.f$0;
        switch (i) {
            case 0:
                if (oneHandOpPolicy.mIsOneHandOpEnabled
                        && oneHandOpPolicy.mOneHandOpMonitor.mWatcher == null) {
                    oneHandOpPolicy.startService(3);
                    break;
                }
                break;
            case 1:
                oneHandOpPolicy.startService(0);
                break;
            default:
                oneHandOpPolicy.getClass();
                try {
                    Intent intent = new Intent("com.samsung.action.EASYONEHAND_SERVICE");
                    intent.setComponent(
                            new ComponentName(
                                    "com.sec.android.easyonehand",
                                    "com.sec.android.easyonehand.EasyOneHandService"));
                    intent.putExtra("ForceHide", true);
                    oneHandOpPolicy.mContext.startService(intent);
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }
}
