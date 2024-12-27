package com.android.server.stats.bootstrap;

import android.content.Context;
import android.os.IStatsBootstrapAtomService;
import android.os.StatsBootstrapAtom;
import android.os.StatsBootstrapAtomValue;
import android.util.Slog;
import android.util.StatsEvent;
import android.util.StatsLog;

import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.SystemService;
import com.android.server.VaultKeeperService$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class StatsBootstrapAtomService extends IStatsBootstrapAtomService.Stub {

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Lifecycle extends SystemService {
        public Lifecycle(Context context) {
            super(context);
        }

        @Override // com.android.server.SystemService
        public final void onStart() {
            try {
                publishBinderService("statsbootstrap", new StatsBootstrapAtomService());
            } catch (Exception e) {
                Slog.e("StatsBootstrapAtomService", "Failed to publishBinderService", e);
            }
        }
    }

    public final void reportBootstrapAtom(StatsBootstrapAtom statsBootstrapAtom) {
        int i = statsBootstrapAtom.atomId;
        if (i < 1 || i >= 10000) {
            NandswapManager$$ExternalSyntheticOutline0.m(
                    new StringBuilder("Atom ID "),
                    statsBootstrapAtom.atomId,
                    " is not a valid atom ID",
                    "StatsBootstrapAtomService");
            return;
        }
        StatsEvent.Builder atomId = StatsEvent.newBuilder().setAtomId(statsBootstrapAtom.atomId);
        for (StatsBootstrapAtomValue statsBootstrapAtomValue : statsBootstrapAtom.values) {
            int tag = statsBootstrapAtomValue.getTag();
            if (tag == 0) {
                atomId.writeBoolean(statsBootstrapAtomValue.getBoolValue());
            } else if (tag == 1) {
                atomId.writeInt(statsBootstrapAtomValue.getIntValue());
            } else if (tag == 2) {
                atomId.writeLong(statsBootstrapAtomValue.getLongValue());
            } else if (tag == 3) {
                atomId.writeFloat(statsBootstrapAtomValue.getFloatValue());
            } else if (tag == 4) {
                atomId.writeString(statsBootstrapAtomValue.getStringValue());
            } else {
                if (tag != 5) {
                    StringBuilder sb = new StringBuilder("Unexpected value type ");
                    sb.append(statsBootstrapAtomValue.getTag());
                    sb.append(" when logging atom ");
                    VaultKeeperService$$ExternalSyntheticOutline0.m(
                            sb, statsBootstrapAtom.atomId, "StatsBootstrapAtomService");
                    return;
                }
                atomId.writeByteArray(statsBootstrapAtomValue.getBytesValue());
            }
        }
        StatsLog.write(atomId.usePooledBuffer().build());
    }
}
