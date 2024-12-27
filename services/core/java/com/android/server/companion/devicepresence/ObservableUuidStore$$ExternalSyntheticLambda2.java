package com.android.server.companion.devicepresence;

import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class ObservableUuidStore$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ObservableUuidStore f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ List f$2;

    public /* synthetic */ ObservableUuidStore$$ExternalSyntheticLambda2(ObservableUuidStore observableUuidStore, int i, List list, int i2) {
        this.$r8$classId = i2;
        this.f$0 = observableUuidStore;
        this.f$1 = i;
        this.f$2 = list;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.writeObservableUuidToStore(this.f$1, this.f$2);
                break;
            default:
                this.f$0.writeObservableUuidToStore(this.f$1, this.f$2);
                break;
        }
    }
}
