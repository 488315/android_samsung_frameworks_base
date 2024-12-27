package com.android.server.pm;

import android.content.Context;
import android.content.pm.CrossProfileAppsInternal;
import com.android.server.SystemService;
import com.android.server.pm.CrossProfileAppsServiceImpl;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class CrossProfileAppsService extends SystemService {
    public final CrossProfileAppsServiceImpl mServiceImpl;

    public CrossProfileAppsService(Context context) {
        super(context);
        this.mServiceImpl = new CrossProfileAppsServiceImpl(context, new CrossProfileAppsServiceImpl.InjectorImpl(context));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [android.os.IBinder, com.android.server.pm.CrossProfileAppsServiceImpl] */
    @Override // com.android.server.SystemService
    public final void onStart() {
        ?? r1 = this.mServiceImpl;
        publishBinderService("crossprofileapps", r1);
        publishLocalService(CrossProfileAppsInternal.class, r1.mLocalService);
    }
}
