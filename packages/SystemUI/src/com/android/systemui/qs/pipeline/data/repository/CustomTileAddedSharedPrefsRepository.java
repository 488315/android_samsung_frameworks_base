package com.android.systemui.qs.pipeline.data.repository;

import android.content.ComponentName;
import com.android.systemui.settings.UserFileManager;
import com.android.systemui.settings.UserFileManagerImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CustomTileAddedSharedPrefsRepository implements CustomTileAddedRepository {
    public final UserFileManager userFileManager;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public CustomTileAddedSharedPrefsRepository(UserFileManager userFileManager) {
        this.userFileManager = userFileManager;
    }

    public final void setTileAdded(ComponentName componentName, boolean z, int i) {
        ((UserFileManagerImpl) this.userFileManager).getSharedPreferences$1(i, "tiles_prefs").edit().putBoolean(componentName.flattenToString(), z).apply();
    }
}
