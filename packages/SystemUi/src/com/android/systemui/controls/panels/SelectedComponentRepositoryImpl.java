package com.android.systemui.controls.panels;

import android.content.ComponentName;
import android.content.SharedPreferences;
import com.android.systemui.controls.panels.SelectedComponentRepository;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.settings.UserFileManager;
import com.android.systemui.settings.UserFileManagerImpl;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SelectedComponentRepositoryImpl implements SelectedComponentRepository {
    public final FeatureFlags featureFlags;
    public final UserFileManager userFileManager;
    public final UserTracker userTracker;

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

    public SelectedComponentRepositoryImpl(UserFileManager userFileManager, UserTracker userTracker, FeatureFlags featureFlags) {
        this.userFileManager = userFileManager;
        this.userTracker = userTracker;
        this.featureFlags = featureFlags;
    }

    public final void setSelectedComponent(SelectedComponentRepository.SelectedComponent selectedComponent) {
        SharedPreferences.Editor edit = ((UserFileManagerImpl) this.userFileManager).getSharedPreferences(((UserTrackerImpl) this.userTracker).getUserId(), "controls_prefs").edit();
        ComponentName componentName = selectedComponent.componentName;
        edit.putString("controls_component", componentName != null ? componentName.flattenToString() : null).putString("controls_structure", selectedComponent.name).putBoolean("controls_is_panel", selectedComponent.isPanel).apply();
    }
}
