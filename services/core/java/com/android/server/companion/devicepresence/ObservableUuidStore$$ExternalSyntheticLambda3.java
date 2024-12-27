package com.android.server.companion.devicepresence;

import android.os.Environment;
import android.util.AtomicFile;

import java.io.File;
import java.util.function.Function;

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
