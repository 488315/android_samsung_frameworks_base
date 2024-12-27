package com.android.server.biometrics.sensors.fingerprint.aidl;

import android.hardware.fingerprint.Fingerprint;
import com.android.server.biometrics.sensors.EnumerateConsumer;
import com.android.server.biometrics.sensors.RemovalConsumer;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AidlResponseHandler$$ExternalSyntheticLambda10 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Fingerprint f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ AidlResponseHandler$$ExternalSyntheticLambda10(Fingerprint fingerprint, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = fingerprint;
        this.f$1 = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((RemovalConsumer) obj).onRemoved(this.f$0, this.f$1);
                break;
            default:
                ((EnumerateConsumer) obj).onEnumerationResult(this.f$0, this.f$1);
                break;
        }
    }
}
