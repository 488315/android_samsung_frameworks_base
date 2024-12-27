package com.android.systemui.controls.panels;

import android.content.ComponentName;
import android.content.SharedPreferences;
import android.os.UserHandle;
import com.android.systemui.controls.panels.SelectedComponentRepository;
import com.android.systemui.settings.UserFileManager;
import com.android.systemui.settings.UserFileManagerImpl;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.util.SystemUIAnalytics;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;

public final class SecSelectedComponentRepositoryImpl implements SecSelectedComponentRepository {
    public final CoroutineDispatcher bgDispatcher;
    public final UserFileManager userFileManager;
    public final UserTracker userTracker;

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

    public SecSelectedComponentRepositoryImpl(UserFileManager userFileManager, UserTracker userTracker, CoroutineDispatcher coroutineDispatcher) {
        this.userFileManager = userFileManager;
        this.userTracker = userTracker;
        this.bgDispatcher = coroutineDispatcher;
    }

    public final SelectedComponentRepository.SelectedComponent getSelectedComponent(UserHandle userHandle) {
        SharedPreferences sharedPreferences$1 = ((UserFileManagerImpl) this.userFileManager).getSharedPreferences$1(userHandle.equals(UserHandle.CURRENT) ? ((UserTrackerImpl) this.userTracker).getUserId() : userHandle.getIdentifier(), SystemUIAnalytics.CONTROL_PREF_NAME);
        String string = sharedPreferences$1.getString("controls_custom_component", null);
        if (string == null) {
            return null;
        }
        String string2 = sharedPreferences$1.getString("controls_custom_structure", "");
        Intrinsics.checkNotNull(string2);
        return new SelectedComponentRepository.SelectedComponent(string2, ComponentName.unflattenFromString(string), sharedPreferences$1.getBoolean("controls_custom_is_panel", false));
    }

    public final void removeSelectedComponent() {
        ((UserFileManagerImpl) this.userFileManager).getSharedPreferences$1(((UserTrackerImpl) this.userTracker).getUserId(), SystemUIAnalytics.CONTROL_PREF_NAME).edit().remove("controls_custom_component").remove("controls_custom_structure").remove("controls_custom_is_panel").apply();
    }

    public final void setSelectedComponent(SelectedComponentRepository.SelectedComponent selectedComponent) {
        SharedPreferences.Editor edit = ((UserFileManagerImpl) this.userFileManager).getSharedPreferences$1(((UserTrackerImpl) this.userTracker).getUserId(), SystemUIAnalytics.CONTROL_PREF_NAME).edit();
        ComponentName componentName = selectedComponent.componentName;
        edit.putString("controls_custom_component", componentName != null ? componentName.flattenToString() : null).putString("controls_custom_structure", selectedComponent.name).putBoolean("controls_custom_is_panel", selectedComponent.isPanel).apply();
    }
}
