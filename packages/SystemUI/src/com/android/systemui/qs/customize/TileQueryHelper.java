package com.android.systemui.qs.customize;

import android.content.Context;
import android.util.ArraySet;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.settings.UserTracker;
import java.util.ArrayList;
import java.util.concurrent.Executor;

public final class TileQueryHelper {
    public final Executor mBgExecutor;
    public final Executor mMainExecutor;

    public final class TileInfo {
        public final boolean isSystem;
        public final String spec;
        public final QSTile.State state;

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
