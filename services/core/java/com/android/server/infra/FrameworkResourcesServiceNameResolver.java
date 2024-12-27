package com.android.server.infra;

import android.R;
import android.content.Context;
import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class FrameworkResourcesServiceNameResolver extends ServiceNameBaseResolver {
    public final int mArrayResourceId;
    public final int mStringResourceId;

    public FrameworkResourcesServiceNameResolver(Context context) {
        super(context, true);
        this.mStringResourceId = -1;
        this.mArrayResourceId = R.array.config_serialPorts;
    }

    public FrameworkResourcesServiceNameResolver(Context context, int i) {
        super(context, false);
        this.mStringResourceId = i;
        this.mArrayResourceId = -1;
    }

    @Override // com.android.server.infra.ServiceNameBaseResolver
    public final void dumpShort(PrintWriter printWriter) {
        synchronized (this.mLock) {
            printWriter.print("FrameworkResourcesServiceNamer: resId=");
            printWriter.print(this.mStringResourceId);
            printWriter.print(", numberTemps=");
            printWriter.print(this.mTemporaryServiceNamesList.size());
            printWriter.print(", enabledDefaults=");
            printWriter.print(this.mDefaultServicesDisabled.size());
        }
    }

    @Override // com.android.server.infra.ServiceNameBaseResolver
    public final String readServiceName(int i) {
        return this.mContext.getResources().getString(this.mStringResourceId);
    }

    @Override // com.android.server.infra.ServiceNameBaseResolver
    public final String[] readServiceNameList(int i) {
        return this.mContext.getResources().getStringArray(this.mArrayResourceId);
    }
}
