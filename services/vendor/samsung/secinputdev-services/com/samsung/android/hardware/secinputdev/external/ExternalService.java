package com.samsung.android.hardware.secinputdev.external;

import android.content.Context;
import android.os.Handler;

/* loaded from: classes.dex */
public abstract class ExternalService {
    protected final Context context;
    protected final Handler handler;
    protected final SemInputExternal.IServiceListener listener;

    public abstract String register() throws Exception;

    public ExternalService(
            Context context, SemInputExternal.IServiceListener listener, Handler handler) {
        this.context = context;
        this.listener = listener;
        this.handler = handler;
    }
}
