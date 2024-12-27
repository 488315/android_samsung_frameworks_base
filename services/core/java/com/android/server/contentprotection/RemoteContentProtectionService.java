package com.android.server.contentprotection;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IInterface;
import com.android.internal.infra.ServiceConnector;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class RemoteContentProtectionService extends ServiceConnector.Impl {
    public static final /* synthetic */ int $r8$clinit = 0;
    private final long mAutoDisconnectTimeoutMs;
    private final ComponentName mComponentName;

    public RemoteContentProtectionService(Context context, ComponentName componentName, int i, boolean z, long j) {
        super(context, new Intent("android.service.contentcapture.ContentProtectionService").setComponent(componentName), z ? 4194304 : 0, i, new RemoteContentProtectionService$$ExternalSyntheticLambda0());
        this.mComponentName = componentName;
        this.mAutoDisconnectTimeoutMs = j;
    }

    public final long getAutoDisconnectTimeoutMs() {
        return this.mAutoDisconnectTimeoutMs;
    }

    public final void onServiceConnectionStatusChanged(IInterface iInterface, boolean z) {
        StringBuilder sb = new StringBuilder("Connection status for: ");
        sb.append(this.mComponentName);
        sb.append(" changed to: ");
        DeviceIdleController$$ExternalSyntheticOutline0.m(sb, z ? "connected" : "disconnected", "RemoteContentProtectionService");
    }
}
