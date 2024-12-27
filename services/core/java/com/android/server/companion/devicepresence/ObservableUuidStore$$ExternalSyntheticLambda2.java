package com.android.server.companion.devicepresence;

import java.util.List;

public final /* synthetic */ class ObservableUuidStore$$ExternalSyntheticLambda2
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ObservableUuidStore f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ List f$2;

    public /* synthetic */ ObservableUuidStore$$ExternalSyntheticLambda2(
            ObservableUuidStore observableUuidStore, int i, List list, int i2) {
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
