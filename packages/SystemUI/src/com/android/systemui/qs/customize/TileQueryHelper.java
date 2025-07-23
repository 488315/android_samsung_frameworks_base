package com.android.systemui.qs.customize;

import android.content.Context;
import android.util.ArraySet;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.settings.UserTracker;
import java.util.ArrayList;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class TileQueryHelper {
    public final Executor mBgExecutor;
    public final Executor mMainExecutor;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class TileInfo {
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
