package com.android.server.am;

import android.util.SparseArray;
import com.android.internal.os.ProcessCpuTracker;
import com.android.server.am.KillPolicyManager;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class KillPolicyManager$PsiFile$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ KillPolicyManager$PsiFile$$ExternalSyntheticLambda0(KillPolicyManager.ProcessMemoryUsageInfo processMemoryUsageInfo, SparseArray sparseArray) {
        this.f$0 = processMemoryUsageInfo;
        this.f$1 = sparseArray;
    }

    public /* synthetic */ KillPolicyManager$PsiFile$$ExternalSyntheticLambda0(KillPolicyManager.PsiFile psiFile) {
        KillPolicyManager.PsiDataType psiDataType = KillPolicyManager.PsiDataType.AVG10;
        this.f$0 = psiFile;
        this.f$1 = psiDataType;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                KillPolicyManager.PsiFile psiFile = (KillPolicyManager.PsiFile) this.f$0;
                KillPolicyManager.PsiDataType psiDataType = (KillPolicyManager.PsiDataType) this.f$1;
                String str = (String) obj;
                psiFile.getClass();
                boolean startsWith = str.startsWith("some");
                KillPolicyManager.PsiDataType psiDataType2 = KillPolicyManager.PsiDataType.TOTAL;
                KillPolicyManager.PsiDataType psiDataType3 = KillPolicyManager.PsiDataType.AVG10;
                if (!startsWith) {
                    if (str.startsWith("full")) {
                        if (psiDataType != psiDataType3) {
                            if (psiDataType == psiDataType2) {
                                Long.parseLong(KillPolicyManager.PsiFile.getItem(4, str, "total"));
                                break;
                            }
                        } else {
                            Double.parseDouble(KillPolicyManager.PsiFile.getItem(1, str, "avg10"));
                            break;
                        }
                    }
                } else if (psiDataType != psiDataType3) {
                    if (psiDataType == psiDataType2) {
                        Long.parseLong(KillPolicyManager.PsiFile.getItem(4, str, "total"));
                        break;
                    }
                } else {
                    psiFile.mSomeAvg10 = Double.parseDouble(KillPolicyManager.PsiFile.getItem(1, str, "avg10"));
                    break;
                }
                break;
            default:
                KillPolicyManager.ProcessMemoryUsageInfo processMemoryUsageInfo = (KillPolicyManager.ProcessMemoryUsageInfo) this.f$0;
                SparseArray sparseArray = (SparseArray) this.f$1;
                ProcessCpuTracker.Stats stats = (ProcessCpuTracker.Stats) obj;
                processMemoryUsageInfo.getClass();
                if (stats.vsize > 0 && sparseArray.indexOfKey(stats.pid) < 0) {
                    processMemoryUsageInfo.getProcDumpMemInfoInternal(stats.pid, 0, -1, stats.name);
                    break;
                }
                break;
        }
    }
}
