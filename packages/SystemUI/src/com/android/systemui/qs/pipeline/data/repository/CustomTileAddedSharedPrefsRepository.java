package com.android.systemui.qs.pipeline.data.repository;

import android.content.ComponentName;
import com.android.systemui.settings.UserFileManager;
import com.android.systemui.settings.UserFileManagerImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class CustomTileAddedSharedPrefsRepository implements CustomTileAddedRepository {
    public final UserFileManager userFileManager;

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

    public CustomTileAddedSharedPrefsRepository(UserFileManager userFileManager) {
        this.userFileManager = userFileManager;
    }

    @Override // com.android.systemui.qs.pipeline.data.repository.CustomTileAddedRepository
    public final boolean isTileAdded(int i, ComponentName componentName) {
        return ((UserFileManagerImpl) this.userFileManager).getSharedPreferences$1(i, "tiles_prefs").getBoolean(componentName.flattenToString(), false);
    }

    @Override // com.android.systemui.qs.pipeline.data.repository.CustomTileAddedRepository
    public final void setTileAdded(ComponentName componentName, boolean z, int i) {
        ((UserFileManagerImpl) this.userFileManager).getSharedPreferences$1(i, "tiles_prefs").edit().putBoolean(componentName.flattenToString(), z).apply();
    }
}
