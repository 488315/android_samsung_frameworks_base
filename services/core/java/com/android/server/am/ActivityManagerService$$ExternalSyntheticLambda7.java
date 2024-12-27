package com.android.server.am;

import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.os.RemoteException;
import android.util.Slog;

import com.android.internal.os.ProcessCpuTracker;

import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class ActivityManagerService$$ExternalSyntheticLambda7
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ ActivityManagerService$$ExternalSyntheticLambda7(
            int i, String[] strArr, ArrayList arrayList, int i2) {
        this.$r8$classId = i2;
        this.f$2 = i;
        this.f$0 = strArr;
        this.f$1 = arrayList;
    }

    public /* synthetic */ ActivityManagerService$$ExternalSyntheticLambda7(
            StringBuilder sb, IPackageManager iPackageManager, int i) {
        this.$r8$classId = 0;
        this.f$0 = sb;
        this.f$1 = iPackageManager;
        this.f$2 = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        String str;
        String str2;
        switch (this.$r8$classId) {
            case 0:
                StringBuilder sb = (StringBuilder) this.f$0;
                IPackageManager iPackageManager = (IPackageManager) this.f$1;
                int i = this.f$2;
                String str3 = (String) obj;
                sb.append("Package: ");
                sb.append(str3);
                try {
                    PackageInfo packageInfo = iPackageManager.getPackageInfo(str3, 0L, i);
                    if (packageInfo != null) {
                        sb.append(" v");
                        sb.append(packageInfo.getLongVersionCode());
                        if (packageInfo.versionName != null) {
                            sb.append(" (");
                            sb.append(packageInfo.versionName);
                            sb.append(")");
                        }
                    }
                } catch (RemoteException e) {
                    Slog.e("ActivityManager", "Error getting package info: " + str3, e);
                }
                sb.append("\n");
                break;
            case 1:
                int i2 = this.f$2;
                String[] strArr = (String[]) this.f$0;
                ArrayList arrayList = (ArrayList) this.f$1;
                ProcessCpuTracker.Stats stats = (ProcessCpuTracker.Stats) obj;
                if (stats.pid == i2 || ((str = stats.baseName) != null && str.equals(strArr[0]))) {
                    arrayList.add(stats);
                    break;
                }
                break;
            default:
                int i3 = this.f$2;
                String[] strArr2 = (String[]) this.f$0;
                ArrayList arrayList2 = (ArrayList) this.f$1;
                ProcessCpuTracker.Stats stats2 = (ProcessCpuTracker.Stats) obj;
                if (stats2.pid == i3
                        || ((str2 = stats2.baseName) != null && str2.equals(strArr2[0]))) {
                    arrayList2.add(stats2);
                    break;
                }
                break;
        }
    }
}
