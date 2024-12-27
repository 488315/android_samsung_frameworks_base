package com.android.server.ondeviceintelligence;

import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import com.android.internal.infra.AndroidFuture;

import java.io.IOException;
import java.util.function.BiConsumer;

public final /* synthetic */ class OnDeviceIntelligenceManagerService$1$$ExternalSyntheticLambda1
        implements BiConsumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ OnDeviceIntelligenceManagerService$1$$ExternalSyntheticLambda1(
            int i, Bundle bundle) {
        this.$r8$classId = i;
        this.f$0 = bundle;
    }

    public /* synthetic */ OnDeviceIntelligenceManagerService$1$$ExternalSyntheticLambda1(
            AndroidFuture androidFuture) {
        this.$r8$classId = 4;
        this.f$0 = androidFuture;
    }

    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        int i = this.$r8$classId;
        Object obj3 = this.f$0;
        switch (i) {
            case 0:
                BundleUtil.tryCloseResource((Bundle) obj3);
                return;
            case 1:
                BundleUtil.tryCloseResource((Bundle) obj3);
                return;
            case 2:
                BundleUtil.tryCloseResource((Bundle) obj3);
                return;
            case 3:
                BundleUtil.tryCloseResource((Bundle) obj3);
                return;
            default:
                AndroidFuture androidFuture = (AndroidFuture) obj3;
                ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) obj;
                Throwable th = (Throwable) obj2;
                try {
                    if (th != null) {
                        androidFuture.completeExceptionally(th);
                    } else {
                        BundleUtil.validatePfdReadOnly(parcelFileDescriptor);
                        androidFuture.complete(parcelFileDescriptor);
                    }
                    int i2 = OnDeviceIntelligenceManagerService.$r8$clinit;
                    if (parcelFileDescriptor != null) {
                        try {
                            parcelFileDescriptor.close();
                            return;
                        } catch (IOException e) {
                            Log.e(
                                    "OnDeviceIntelligenceManagerService",
                                    "Failed to close parcel file descriptor ",
                                    e);
                            return;
                        }
                    }
                    return;
                } catch (Throwable th2) {
                    int i3 = OnDeviceIntelligenceManagerService.$r8$clinit;
                    if (parcelFileDescriptor != null) {
                        try {
                            parcelFileDescriptor.close();
                        } catch (IOException e2) {
                            Log.e(
                                    "OnDeviceIntelligenceManagerService",
                                    "Failed to close parcel file descriptor ",
                                    e2);
                        }
                    }
                    throw th2;
                }
        }
    }
}
