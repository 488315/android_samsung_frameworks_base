package com.android.systemui.display.data.repository;

import com.android.app.displaylib.PerDisplayRepository;
import com.android.systemui.dump.DumpManager;

/* loaded from: classes2.dex */
public final class PerDisplayRepoDumpHelper implements PerDisplayRepository.InitCallback {
    public final DumpManager dumpManager;

    public PerDisplayRepoDumpHelper(DumpManager dumpManager) {
        this.dumpManager = dumpManager;
    }
}
