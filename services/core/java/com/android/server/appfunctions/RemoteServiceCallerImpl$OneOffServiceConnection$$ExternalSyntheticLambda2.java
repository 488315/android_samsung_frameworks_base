package com.android.server.appfunctions;

import android.app.appfunctions.AppFunctionException;
import android.util.Slog;

public final /* synthetic */
class RemoteServiceCallerImpl$OneOffServiceConnection$$ExternalSyntheticLambda2
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */
    RemoteServiceCallerImpl$OneOffServiceConnection$$ExternalSyntheticLambda2(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                RunAppFunctionServiceCallback runAppFunctionServiceCallback =
                        (RunAppFunctionServiceCallback) obj;
                runAppFunctionServiceCallback.getClass();
                Slog.e("RunAppFunctionServiceCallback", "Failed to connect to service");
                runAppFunctionServiceCallback.mSafeExecuteAppFunctionCallback.onError(
                        new AppFunctionException(3000, "Failed to connect to AppFunctionService"));
                break;
            default:
                ((RemoteServiceCallerImpl.OneOffServiceConnection) obj).safeUnbind();
                break;
        }
    }
}
