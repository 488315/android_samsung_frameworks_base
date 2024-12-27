package com.android.server.wm;

import android.os.IBinder;

import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class WindowManagerService$$ExternalSyntheticLambda0
        implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ WindowManagerService$$ExternalSyntheticLambda0(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                IBinder iBinder = (IBinder) obj2;
                int i2 = WindowManagerService.MY_PID;
                if (((ActivityRecord) obj).mLaunchCookie == iBinder) {
                    break;
                }
                break;
            default:
                WindowState windowState = (WindowState) obj;
                int i3 = WindowManagerService.MY_PID;
                if (windowState == ((DisplayContent) obj2).mInputMethodWindow) {
                    break;
                }
                break;
        }
        return true;
    }
}
