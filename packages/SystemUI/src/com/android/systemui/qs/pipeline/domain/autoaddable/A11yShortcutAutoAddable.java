package com.android.systemui.qs.pipeline.domain.autoaddable;

import android.content.ComponentName;
import com.android.systemui.accessibility.data.repository.AccessibilityQsShortcutsRepository;
import com.android.systemui.qs.pipeline.domain.model.AutoAddTracking;
import com.android.systemui.qs.pipeline.domain.model.AutoAddable;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;

public final class A11yShortcutAutoAddable implements AutoAddable {
    public final AccessibilityQsShortcutsRepository a11yQsShortcutsRepository;
    public final AutoAddTracking.Always autoAddTracking;
    public final CoroutineDispatcher bgDispatcher;
    public final ComponentName componentName;
    public final String description;
    public final TileSpec spec;

    public A11yShortcutAutoAddable(AccessibilityQsShortcutsRepository accessibilityQsShortcutsRepository, CoroutineDispatcher coroutineDispatcher, TileSpec tileSpec, ComponentName componentName) {
        this.spec = tileSpec;
        this.componentName = componentName;
        this.description = "A11yShortcutAutoAddableSetting: " + tileSpec + ":" + componentName + " (" + AutoAddTracking.Always.INSTANCE + ")";
    }

    public final boolean equals(Object obj) {
        if (obj instanceof A11yShortcutAutoAddable) {
            A11yShortcutAutoAddable a11yShortcutAutoAddable = (A11yShortcutAutoAddable) obj;
            if (Intrinsics.areEqual(this.spec, a11yShortcutAutoAddable.spec) && Intrinsics.areEqual(this.componentName, a11yShortcutAutoAddable.componentName)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.systemui.qs.pipeline.domain.model.AutoAddable
    public final String getDescription() {
        return this.description;
    }

    public final int hashCode() {
        return Objects.hash(this.spec, this.componentName);
    }

    public final String toString() {
        return this.description;
    }
}
