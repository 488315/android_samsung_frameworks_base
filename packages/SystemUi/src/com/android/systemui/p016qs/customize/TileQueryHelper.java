package com.android.systemui.p016qs.customize;

import android.content.Context;
import android.util.ArraySet;
import com.android.systemui.plugins.p013qs.QSTile;
import com.android.systemui.settings.UserTracker;
import java.util.ArrayList;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TileQueryHelper {
    public final Executor mBgExecutor;
    public final Executor mMainExecutor;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class TileInfo {
        public final boolean isSystem;
        public final String spec;
        public final QSTile.State state;

        public TileInfo() {
        }

        public TileInfo(String str, QSTile.State state, boolean z) {
            this.spec = str;
            this.state = state;
            this.isSystem = z;
        }
    }

    public TileQueryHelper(Context context, UserTracker userTracker, Executor executor, Executor executor2) {
        new ArrayList();
        new ArraySet();
        this.mMainExecutor = executor;
        this.mBgExecutor = executor2;
    }
}
