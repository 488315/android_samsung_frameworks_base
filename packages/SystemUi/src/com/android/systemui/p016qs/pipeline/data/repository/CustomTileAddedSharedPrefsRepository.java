package com.android.systemui.p016qs.pipeline.data.repository;

import android.content.ComponentName;
import com.android.systemui.settings.UserFileManager;
import com.android.systemui.settings.UserFileManagerImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CustomTileAddedSharedPrefsRepository implements CustomTileAddedRepository {
    public final UserFileManager userFileManager;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    @Override // com.android.systemui.p016qs.pipeline.data.repository.CustomTileAddedRepository
    public final boolean isTileAdded(int i, ComponentName componentName) {
        return ((UserFileManagerImpl) this.userFileManager).getSharedPreferences(i, "tiles_prefs").getBoolean(componentName.flattenToString(), false);
    }

    @Override // com.android.systemui.p016qs.pipeline.data.repository.CustomTileAddedRepository
    public final void setTileAdded(ComponentName componentName, boolean z, int i) {
        ((UserFileManagerImpl) this.userFileManager).getSharedPreferences(i, "tiles_prefs").edit().putBoolean(componentName.flattenToString(), z).apply();
    }
}
