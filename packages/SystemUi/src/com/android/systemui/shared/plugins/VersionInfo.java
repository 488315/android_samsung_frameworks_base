package com.android.systemui.shared.plugins;

import android.util.ArrayMap;
import com.android.systemui.plugins.annotations.Dependencies;
import com.android.systemui.plugins.annotations.DependsOn;
import com.android.systemui.plugins.annotations.ProvidesInterface;
import com.android.systemui.plugins.annotations.Requirements;
import com.android.systemui.plugins.annotations.Requires;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class VersionInfo {
    public Class mDefault;
    public final ArrayMap mVersions = new ArrayMap();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Version {
        public final boolean mRequired;
        public final int mVersion;

        public Version(int i, boolean z) {
            this.mVersion = i;
            this.mRequired = z;
        }
    }

    public final void addClass(Class cls, boolean z) {
        ArrayMap arrayMap = this.mVersions;
        if (arrayMap.containsKey(cls)) {
            return;
        }
        ProvidesInterface providesInterface = (ProvidesInterface) cls.getDeclaredAnnotation(ProvidesInterface.class);
        if (providesInterface != null) {
            arrayMap.put(cls, new Version(providesInterface.version(), true));
        }
        Requires requires = (Requires) cls.getDeclaredAnnotation(Requires.class);
        if (requires != null) {
            arrayMap.put(requires.target(), new Version(requires.version(), z));
        }
        Requirements requirements = (Requirements) cls.getDeclaredAnnotation(Requirements.class);
        if (requirements != null) {
            for (Requires requires2 : requirements.value()) {
                arrayMap.put(requires2.target(), new Version(requires2.version(), z));
            }
        }
        DependsOn dependsOn = (DependsOn) cls.getDeclaredAnnotation(DependsOn.class);
        if (dependsOn != null) {
            addClass(dependsOn.target(), true);
        }
        Dependencies dependencies = (Dependencies) cls.getDeclaredAnnotation(Dependencies.class);
        if (dependencies != null) {
            for (DependsOn dependsOn2 : dependencies.value()) {
                addClass(dependsOn2.target(), true);
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class InvalidVersionException extends RuntimeException {
        private int mActual;
        private int mExpected;
        private final boolean mTooNew;

        public InvalidVersionException(String str, boolean z) {
            super(str);
            this.mTooNew = z;
        }

        public final boolean isTooNew() {
            return this.mTooNew;
        }

        public InvalidVersionException(Class<?> cls, boolean z, int i, int i2) {
            super(cls.getSimpleName() + " expected version " + i + " but had " + i2);
            this.mTooNew = z;
            this.mExpected = i;
            this.mActual = i2;
        }
    }
}
