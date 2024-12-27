package com.android.server.location.contexthub;

import android.content.Intent;

import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class ContextHubClientBroker$$ExternalSyntheticLambda3
        implements Supplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ContextHubClientBroker f$0;
    public final /* synthetic */ long f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ ContextHubClientBroker$$ExternalSyntheticLambda3(
            ContextHubClientBroker contextHubClientBroker, long j, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = contextHubClientBroker;
        this.f$1 = j;
        this.f$2 = i;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                ContextHubClientBroker contextHubClientBroker = this.f$0;
                long j = this.f$1;
                int i = this.f$2;
                Intent createIntent = contextHubClientBroker.createIntent(7);
                createIntent.putExtra("android.hardware.location.extra.NANOAPP_ID", j);
                return createIntent.putExtra(
                        "android.hardware.location.extra.CLIENT_AUTHORIZATION_STATE", i);
            default:
                ContextHubClientBroker contextHubClientBroker2 = this.f$0;
                long j2 = this.f$1;
                int i2 = this.f$2;
                Intent createIntent2 = contextHubClientBroker2.createIntent(4);
                createIntent2.putExtra("android.hardware.location.extra.NANOAPP_ID", j2);
                return createIntent2.putExtra(
                        "android.hardware.location.extra.NANOAPP_ABORT_CODE", i2);
        }
    }
}
