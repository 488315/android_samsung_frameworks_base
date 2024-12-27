package com.android.server.wm;

import android.content.pm.ApplicationInfo;

import java.util.ArrayList;
import java.util.function.Consumer;

public final /* synthetic */ class RootWindowContainer$$ExternalSyntheticLambda8
        implements Consumer {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ int f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ RootWindowContainer$$ExternalSyntheticLambda8(
            int i, String str, ApplicationInfo applicationInfo) {
        this.f$0 = i;
        this.f$1 = str;
        this.f$2 = applicationInfo;
    }

    public /* synthetic */ RootWindowContainer$$ExternalSyntheticLambda8(
            int i, String str, ArrayList arrayList) {
        this.f$1 = str;
        this.f$2 = arrayList;
        this.f$0 = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                int i = this.f$0;
                String str = this.f$1;
                ApplicationInfo applicationInfo = (ApplicationInfo) this.f$2;
                ActivityRecord activityRecord = (ActivityRecord) obj;
                if (activityRecord.mUserId == i && str.equals(activityRecord.packageName)) {
                    activityRecord.info.applicationInfo = applicationInfo;
                    break;
                }
                break;
            default:
                String str2 = this.f$1;
                ArrayList arrayList = (ArrayList) this.f$2;
                int i2 = this.f$0;
                WindowState windowState = (WindowState) obj;
                if (str2 == null) {
                    if (System.identityHashCode(windowState) == i2) {
                        arrayList.add(windowState);
                        break;
                    }
                } else if (windowState.mAttrs.getTitle().toString().contains(str2)) {
                    arrayList.add(windowState);
                    break;
                }
                break;
        }
    }
}
