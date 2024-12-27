package com.android.server.pm;

import android.content.Context;
import android.content.pm.CrossProfileAppsInternal;
import com.android.server.SystemService;
import com.android.server.pm.CrossProfileAppsServiceImpl;

public final class CrossProfileAppsService extends SystemService {
    public final CrossProfileAppsServiceImpl mServiceImpl;

    public CrossProfileAppsService(Context context) {
        super(context);
        this.mServiceImpl = new CrossProfileAppsServiceImpl(context, new CrossProfileAppsServiceImpl.InjectorImpl(context));
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        ?? r1 = this.mServiceImpl;
        publishBinderService("crossprofileapps", r1);
        publishLocalService(CrossProfileAppsInternal.class, r1.mLocalService);
    }
}
