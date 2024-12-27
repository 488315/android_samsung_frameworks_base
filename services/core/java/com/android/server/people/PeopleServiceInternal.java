package com.android.server.people;

import android.os.CancellationSignal;
import android.service.appprediction.IPredictionService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class PeopleServiceInternal extends IPredictionService.Stub {
    public abstract byte[] getBackupPayload(int i);

    public abstract void pruneDataForUser(int i, CancellationSignal cancellationSignal);

    public abstract void restore(int i, byte[] bArr);
}
