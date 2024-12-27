package com.android.server.companion.devicepresence;

import android.os.Environment;
import android.util.AtomicFile;

import java.io.File;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class ObservableUuidStore$$ExternalSyntheticLambda3
        implements Function {
    public final /* synthetic */ int f$0;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return new AtomicFile(
                new File(
                        Environment.getDataSystemDeDirectory(this.f$0),
                        "observing_uuids_presence.xml"));
    }
}
