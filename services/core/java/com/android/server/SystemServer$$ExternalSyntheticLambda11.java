package com.android.server;

import android.content.Context;
import android.os.IBinder;
import android.os.IServiceCreator;

import com.samsung.android.ssdid.SemSsdidManagerService;

public final /* synthetic */ class SystemServer$$ExternalSyntheticLambda11
        implements IServiceCreator {
    public final IBinder createService(Context context) {
        return new SemSsdidManagerService(context);
    }
}
