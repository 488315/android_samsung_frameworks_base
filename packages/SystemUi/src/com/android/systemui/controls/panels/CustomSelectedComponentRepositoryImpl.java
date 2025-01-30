package com.android.systemui.controls.panels;

import android.content.ComponentName;
import android.content.SharedPreferences;
import com.android.systemui.controls.panels.CustomSelectedComponentRepository;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CustomSelectedComponentRepositoryImpl implements CustomSelectedComponentRepository {
    public final SharedPreferences sharedPreferences;

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

    public CustomSelectedComponentRepositoryImpl(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public final CustomSelectedComponentRepository.CustomSelectedComponent getSelectedComponent() {
        SharedPreferences sharedPreferences = this.sharedPreferences;
        String string = sharedPreferences.getString("controls_custom_component", null);
        if (string == null) {
            return null;
        }
        String string2 = sharedPreferences.getString("controls_custom_structure", "");
        Intrinsics.checkNotNull(string2);
        return new CustomSelectedComponentRepository.CustomSelectedComponent(string2, ComponentName.unflattenFromString(string), sharedPreferences.getBoolean("controls_custom_is_panel", false));
    }

    public final void setSelectedComponent(CustomSelectedComponentRepository.CustomSelectedComponent customSelectedComponent) {
        SharedPreferences.Editor edit = this.sharedPreferences.edit();
        ComponentName componentName = customSelectedComponent.componentName;
        edit.putString("controls_custom_component", componentName != null ? componentName.flattenToString() : null).putString("controls_custom_structure", customSelectedComponent.name).putBoolean("controls_custom_is_panel", customSelectedComponent.isPanel).apply();
    }
}
