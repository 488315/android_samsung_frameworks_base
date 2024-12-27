package com.android.systemui.qs.external;

import android.content.Context;
import android.content.SharedPreferences;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class CustomTileStatePersisterImpl implements CustomTileStatePersister {
    public final SharedPreferences sharedPreferences;

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
