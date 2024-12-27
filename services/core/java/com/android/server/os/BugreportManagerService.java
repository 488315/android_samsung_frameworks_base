package com.android.server.os;

import android.content.Context;
import android.os.Environment;
import android.util.ArraySet;
import android.util.AtomicFile;
import com.android.server.SystemConfig;
import com.android.server.SystemService;
import com.android.server.os.BugreportManagerServiceImpl;
import com.android.server.os.BugreportManagerServiceImpl.Injector.RoleManagerWrapper;
import java.io.File;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class BugreportManagerService extends SystemService {
    public BugreportManagerService(Context context) {
        super(context);
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        Context context = getContext();
        ArraySet arraySet = SystemConfig.getInstance().mBugreportWhitelistedPackages;
        AtomicFile atomicFile = new AtomicFile(new File(new File(Environment.getDataDirectory(), "system"), "bugreport-mapping.xml"));
        BugreportManagerServiceImpl.Injector injector = new BugreportManagerServiceImpl.Injector();
        injector.mContext = context;
        injector.mAllowlistedPackages = arraySet;
        injector.mMappingFile = atomicFile;
        injector.mRoleManagerWrapper = injector.new RoleManagerWrapper();
        publishBinderService("bugreport", new BugreportManagerServiceImpl(injector));
    }
}
