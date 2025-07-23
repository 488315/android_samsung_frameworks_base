package com.android.internal.hidden_from_bootclasspath.android.net.http;

/* loaded from: classes5.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    @Override // com.android.internal.hidden_from_bootclasspath.android.net.http.FeatureFlags
    public boolean preloadHttpengineInZygote() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.net.http.FeatureFlags
    public boolean preloadHttpengineJavaImplClasses() {
        return true;
    }

    @Override // com.android.internal.hidden_from_bootclasspath.android.net.http.FeatureFlags
    public boolean preloadHttpengineSharedLibrary() {
        return true;
    }
}
