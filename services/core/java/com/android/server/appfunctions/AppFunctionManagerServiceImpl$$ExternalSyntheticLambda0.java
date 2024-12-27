package com.android.server.appfunctions;

import android.app.appfunctions.IAppFunctionService;
import android.os.IBinder;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AppFunctionManagerServiceImpl$$ExternalSyntheticLambda0 implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return IAppFunctionService.Stub.asInterface((IBinder) obj);
            default:
                return new Object();
        }
    }
}
