package com.android.server.location.contexthub;

import android.content.Intent;
import android.hardware.location.IContextHubClientCallback;

import java.util.function.Consumer;
import java.util.function.Supplier;

public final /* synthetic */ class ContextHubClientManager$$ExternalSyntheticLambda3
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ long f$0;

    public /* synthetic */ ContextHubClientManager$$ExternalSyntheticLambda3(long j, int i) {
        this.$r8$classId = i;
        this.f$0 = j;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                final long j = this.f$0;
                final ContextHubClientBroker contextHubClientBroker = (ContextHubClientBroker) obj;
                contextHubClientBroker.getClass();
                final int i = 1;
                contextHubClientBroker.invokeCallback(
                        new ContextHubClientBroker
                                .CallbackConsumer() { // from class:
                                                      // com.android.server.location.contexthub.ContextHubClientBroker$$ExternalSyntheticLambda11
                            @Override // com.android.server.location.contexthub.ContextHubClientBroker.CallbackConsumer
                            public final void accept(
                                    IContextHubClientCallback iContextHubClientCallback) {
                                switch (i) {
                                    case 0:
                                        iContextHubClientCallback.onNanoAppLoaded(j);
                                        break;
                                    default:
                                        iContextHubClientCallback.onNanoAppUnloaded(j);
                                        break;
                                }
                            }
                        });
                Supplier supplier =
                        new Supplier() { // from class:
                                         // com.android.server.location.contexthub.ContextHubClientBroker$$ExternalSyntheticLambda12
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                switch (i) {
                                    case 0:
                                        ContextHubClientBroker contextHubClientBroker2 =
                                                contextHubClientBroker;
                                        long j2 = j;
                                        Intent createIntent =
                                                contextHubClientBroker2.createIntent(0);
                                        createIntent.putExtra(
                                                "android.hardware.location.extra.NANOAPP_ID", j2);
                                        return createIntent;
                                    default:
                                        ContextHubClientBroker contextHubClientBroker3 =
                                                contextHubClientBroker;
                                        long j3 = j;
                                        Intent createIntent2 =
                                                contextHubClientBroker3.createIntent(1);
                                        createIntent2.putExtra(
                                                "android.hardware.location.extra.NANOAPP_ID", j3);
                                        return createIntent2;
                                }
                            }
                        };
                synchronized (contextHubClientBroker) {
                    contextHubClientBroker.sendPendingIntent(supplier, j, null);
                }
                return;
            default:
                final long j2 = this.f$0;
                final ContextHubClientBroker contextHubClientBroker2 = (ContextHubClientBroker) obj;
                contextHubClientBroker2.checkNanoappPermsAsync();
                final int i2 = 0;
                contextHubClientBroker2.invokeCallback(
                        new ContextHubClientBroker
                                .CallbackConsumer() { // from class:
                                                      // com.android.server.location.contexthub.ContextHubClientBroker$$ExternalSyntheticLambda11
                            @Override // com.android.server.location.contexthub.ContextHubClientBroker.CallbackConsumer
                            public final void accept(
                                    IContextHubClientCallback iContextHubClientCallback) {
                                switch (i2) {
                                    case 0:
                                        iContextHubClientCallback.onNanoAppLoaded(j2);
                                        break;
                                    default:
                                        iContextHubClientCallback.onNanoAppUnloaded(j2);
                                        break;
                                }
                            }
                        });
                Supplier supplier2 =
                        new Supplier() { // from class:
                                         // com.android.server.location.contexthub.ContextHubClientBroker$$ExternalSyntheticLambda12
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                switch (i2) {
                                    case 0:
                                        ContextHubClientBroker contextHubClientBroker22 =
                                                contextHubClientBroker2;
                                        long j22 = j2;
                                        Intent createIntent =
                                                contextHubClientBroker22.createIntent(0);
                                        createIntent.putExtra(
                                                "android.hardware.location.extra.NANOAPP_ID", j22);
                                        return createIntent;
                                    default:
                                        ContextHubClientBroker contextHubClientBroker3 =
                                                contextHubClientBroker2;
                                        long j3 = j2;
                                        Intent createIntent2 =
                                                contextHubClientBroker3.createIntent(1);
                                        createIntent2.putExtra(
                                                "android.hardware.location.extra.NANOAPP_ID", j3);
                                        return createIntent2;
                                }
                            }
                        };
                synchronized (contextHubClientBroker2) {
                    contextHubClientBroker2.sendPendingIntent(supplier2, j2, null);
                }
                return;
        }
    }
}
