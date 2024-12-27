package com.android.server.wm;

import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class WindowManagerService$$ExternalSyntheticLambda5 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ArrayList f$0;

    public /* synthetic */ WindowManagerService$$ExternalSyntheticLambda5(ArrayList arrayList, int i) {
        this.$r8$classId = i;
        this.f$0 = arrayList;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        ArrayList arrayList = this.f$0;
        WindowState windowState = (WindowState) obj;
        switch (i) {
            case 0:
                int i2 = WindowManagerService.MY_PID;
                arrayList.add(windowState);
                break;
            case 1:
                int i3 = WindowManagerService.MY_PID;
                arrayList.add(windowState);
                break;
            case 2:
                int i4 = WindowManagerService.MY_PID;
                arrayList.add(windowState);
                break;
            default:
                int i5 = WindowManagerService.MY_PID;
                if (windowState.mActivityRecord == null && windowState.isVisibleNow()) {
                    arrayList.add(windowState);
                    break;
                }
                break;
        }
    }
}
