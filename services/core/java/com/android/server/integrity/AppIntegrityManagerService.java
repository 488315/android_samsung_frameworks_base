package com.android.server.integrity;

import android.content.Context;
import android.content.pm.PackageManagerInternal;
import android.os.HandlerThread;
import com.android.server.KnoxCaptureInputFilter$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.integrity.engine.RuleEvaluationEngine;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AppIntegrityManagerService extends SystemService {
    public final Context mContext;

    public AppIntegrityManagerService(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        RuleEvaluationEngine ruleEvaluationEngine;
        Context context = this.mContext;
        Set set = AppIntegrityManagerServiceImpl.PACKAGE_INSTALLER;
        HandlerThread m = KnoxCaptureInputFilter$$ExternalSyntheticOutline0.m("AppIntegrityManagerServiceHandler");
        PackageManagerInternal packageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        AppIntegrityManagerServiceImpl$$ExternalSyntheticLambda0 appIntegrityManagerServiceImpl$$ExternalSyntheticLambda0 = new AppIntegrityManagerServiceImpl$$ExternalSyntheticLambda0();
        synchronized (RuleEvaluationEngine.class) {
            ruleEvaluationEngine = new RuleEvaluationEngine(IntegrityFileManager.getInstance());
        }
        publishBinderService("app_integrity", new AppIntegrityManagerServiceImpl(context, packageManagerInternal, appIntegrityManagerServiceImpl$$ExternalSyntheticLambda0, ruleEvaluationEngine, IntegrityFileManager.getInstance(), m.getThreadHandler()));
    }
}
