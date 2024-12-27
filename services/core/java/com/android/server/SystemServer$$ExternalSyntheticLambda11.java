package com.android.server;

import android.content.Context;
import android.os.IBinder;
import android.os.IServiceCreator;

import com.samsung.android.ssdid.SemSsdidManagerService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class SystemServer$$ExternalSyntheticLambda11
        implements IServiceCreator {
    public final IBinder createService(Context context) {
        return new SemSsdidManagerService(context);
    }
}
