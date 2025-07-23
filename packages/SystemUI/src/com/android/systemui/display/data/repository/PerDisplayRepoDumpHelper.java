package com.android.systemui.display.data.repository;

import com.android.app.displaylib.PerDisplayRepository;
import com.android.systemui.dump.DumpManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class PerDisplayRepoDumpHelper implements PerDisplayRepository.InitCallback {
    public final DumpManager dumpManager;

    public PerDisplayRepoDumpHelper(DumpManager dumpManager) {
        this.dumpManager = dumpManager;
    }
}
