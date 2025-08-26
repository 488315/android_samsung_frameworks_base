package com.android.systemui.accessibility.data.repository;

import android.view.accessibility.AccessibilityManager;
import com.android.app.tracing.FlowTracing;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: classes.dex */
public final class AccessibilityRepositoryImpl implements AccessibilityRepository {
    public final Flow isEnabled;
    public final Flow isTouchExplorationEnabled;
    public final AccessibilityManager manager;

    public AccessibilityRepositoryImpl(AccessibilityManager accessibilityManager) {
        this.manager = accessibilityManager;
        FlowTracing flowTracing = FlowTracing.INSTANCE;
        AccessibilityRepositoryImpl$isTouchExplorationEnabled$1 accessibilityRepositoryImpl$isTouchExplorationEnabled$1 = new AccessibilityRepositoryImpl$isTouchExplorationEnabled$1(this, null);
        flowTracing.getClass();
        this.isTouchExplorationEnabled = FlowKt.distinctUntilChanged(FlowTracing.tracedConflatedCallbackFlow("AccessibilityRepository", accessibilityRepositoryImpl$isTouchExplorationEnabled$1));
        this.isEnabled = FlowKt.distinctUntilChanged(FlowTracing.tracedConflatedCallbackFlow("AccessibilityRepository", new AccessibilityRepositoryImpl$isEnabled$1(this, null)));
    }

    /* renamed from: getRecommendedTimeout-UqaQ4Hc, reason: not valid java name */
    public final long m1003getRecommendedTimeoutUqaQ4Hc(int i, long j) {
        Duration.Companion companion = Duration.Companion;
        return DurationKt.toDuration(this.manager.getRecommendedTimeoutMillis((int) Duration.m3457getInWholeMillisecondsimpl(j), i), DurationUnit.MILLISECONDS);
    }
}
