package com.samsung.systemui.splugins;

import android.util.ArrayMap;
import com.samsung.systemui.splugins.SVersionInfo;
import com.samsung.systemui.splugins.annotations.Dependencies;
import com.samsung.systemui.splugins.annotations.DependsOn;
import com.samsung.systemui.splugins.annotations.ProvidesInterface;
import com.samsung.systemui.splugins.annotations.Requirements;
import com.samsung.systemui.splugins.annotations.Requires;
import java.util.function.BiConsumer;

/* loaded from: classes4.dex */
public class SVersionInfo {
    private Class<?> mDefault;
    private final ArrayMap<Class<?>, Version> mVersions = new ArrayMap<>();

    class Version {
        private final boolean mRequired;
        private final int mVersion;

        public Version(int i, boolean z) {
            this.mVersion = i;
            this.mRequired = z;
        }

        public int getMajorVersion() {
            return this.mVersion / 1000;
        }
    }

    private Version createVersion(Class<?> cls) {
        ProvidesInterface providesInterface = (ProvidesInterface) cls.getDeclaredAnnotation(ProvidesInterface.class);
        if (providesInterface != null) {
            return new Version(providesInterface.version(), false);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkVersion$0(ArrayMap arrayMap, Class cls, Version version) {
        Version versionCreateVersion = (Version) arrayMap.remove(cls);
        if (versionCreateVersion == null) {
            versionCreateVersion = createVersion(cls);
        }
        if (versionCreateVersion == null) {
            throw new InvalidVersionException(cls.getSimpleName().concat(" does not provide an interface"), false);
        }
        if (versionCreateVersion.getMajorVersion() <= version.getMajorVersion()) {
            return;
        }
        throw new InvalidVersionException(cls.getSimpleName() + " is not a supporting version. expected " + versionCreateVersion.mVersion + " but " + version.mVersion, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$checkVersion$1(Class cls, Version version) {
        if (version.mRequired) {
            throw new InvalidVersionException("Missing required dependency ".concat(cls.getSimpleName()), false);
        }
    }

    public SVersionInfo addClass(Class<?> cls) {
        if (this.mDefault == null) {
            this.mDefault = cls;
        }
        addClass(cls, false);
        return this;
    }

    public void checkVersion(SVersionInfo sVersionInfo) throws InvalidVersionException {
        final ArrayMap arrayMap = new ArrayMap(this.mVersions);
        sVersionInfo.mVersions.forEach(new BiConsumer() { // from class: com.samsung.systemui.splugins.SVersionInfo$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.lambda$checkVersion$0(arrayMap, (Class) obj, (SVersionInfo.Version) obj2);
            }
        });
        arrayMap.forEach(new SVersionInfo$$ExternalSyntheticLambda1());
    }

    public int getDefaultVersion() {
        return this.mVersions.get(this.mDefault).mVersion;
    }

    public <T> boolean hasClass(Class<T> cls) {
        return this.mVersions.containsKey(cls);
    }

    public boolean hasVersionInfo() {
        return !this.mVersions.isEmpty();
    }

    public class InvalidVersionException extends RuntimeException {
        private final boolean mTooNew;

        public InvalidVersionException(String str, boolean z) {
            super(str);
            this.mTooNew = z;
        }

        public boolean isTooNew() {
            return this.mTooNew;
        }

        public InvalidVersionException(Class<?> cls, boolean z, int i, int i2) {
            super(cls.getSimpleName() + " expected version " + i + " but had " + i2);
            this.mTooNew = z;
        }
    }

    private void addClass(Class<?> cls, boolean z) {
        if (this.mVersions.containsKey(cls)) {
            return;
        }
        ProvidesInterface providesInterface = (ProvidesInterface) cls.getDeclaredAnnotation(ProvidesInterface.class);
        if (providesInterface != null) {
            this.mVersions.put(cls, new Version(providesInterface.version(), true));
        }
        Requires requires = (Requires) cls.getDeclaredAnnotation(Requires.class);
        if (requires != null) {
            this.mVersions.put(requires.target(), new Version(requires.version(), z));
        }
        Requirements requirements = (Requirements) cls.getDeclaredAnnotation(Requirements.class);
        if (requirements != null) {
            for (Requires requires2 : requirements.value()) {
                this.mVersions.put(requires2.target(), new Version(requires2.version(), z));
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
}
