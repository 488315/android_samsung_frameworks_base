package com.android.server.contentprotection;

import android.os.IBinder;
import android.service.contentcapture.IContentProtectionService;

import java.util.function.Function;

public final /* synthetic */ class RemoteContentProtectionService$$ExternalSyntheticLambda0
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return IContentProtectionService.Stub.asInterface((IBinder) obj);
    }
}
