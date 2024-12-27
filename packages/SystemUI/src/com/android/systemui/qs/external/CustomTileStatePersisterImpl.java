package com.android.systemui.qs.external;

import android.content.Context;
import android.content.SharedPreferences;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class CustomTileStatePersisterImpl implements CustomTileStatePersister {
    public final SharedPreferences sharedPreferences;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    public CustomTileStatePersisterImpl(Context context) {
        this.sharedPreferences = context.getSharedPreferences("custom_tiles_state", 0);
    }
}
