package com.android.server.biometrics;

import android.content.Context;
import android.os.Environment;

import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public final /* synthetic */ class SemBioAnalyticsManager$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ SemBioAnalyticsManager$$ExternalSyntheticLambda1(
            int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                SemBioAnalyticsManager semBioAnalyticsManager = (SemBioAnalyticsManager) this.f$0;
                Context context = (Context) this.f$1;
                semBioAnalyticsManager.mContext = context;
                semBioAnalyticsManager.mDqaMgr = new SemBioAnalyticsManager.DQAManager(context);
                Iterator it = semBioAnalyticsManager.mPendingRequestBeforeBootComplete.iterator();
                while (it.hasNext()) {
                    semBioAnalyticsManager.fpHandleData(
                            (SemBioAnalyticsManager.EventData) it.next());
                }
                semBioAnalyticsManager.mPendingRequestBeforeBootComplete.clear();
                semBioAnalyticsManager.mPendingRequestBeforeBootComplete = null;
                break;
            default:
                String str = (String) this.f$0;
                String str2 = (String) this.f$1;
                if (SemBioAnalyticsManager.DEBUG) {
                    DualAppManagerService$$ExternalSyntheticOutline0.m(
                            "DQAManager.writeDqaData: ", str, "BiometricService.AM");
                }
                File file = new File(Environment.getUserSystemDirectory(0), str2);
                if (str != null) {
                    Utils.writeFile(file, str.getBytes(StandardCharsets.UTF_8));
                    break;
                } else {
                    file.delete();
                    break;
                }
        }
    }
}
