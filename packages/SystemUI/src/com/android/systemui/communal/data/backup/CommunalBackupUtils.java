package com.android.systemui.communal.data.backup;

import android.content.Context;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class CommunalBackupUtils {
    public final Context context;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public CommunalBackupUtils(Context context) {
        this.context = context;
    }
}
