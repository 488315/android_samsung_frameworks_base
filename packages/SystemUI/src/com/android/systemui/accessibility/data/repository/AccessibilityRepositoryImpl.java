package com.android.systemui.accessibility.data.repository;

import android.view.accessibility.AccessibilityManager;
import com.android.app.tracing.FlowTracing;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    public final long m1001getRecommendedTimeoutUqaQ4Hc(int i, long j) {
        Duration.Companion companion = Duration.Companion;
        return DurationKt.toDuration(this.manager.getRecommendedTimeoutMillis((int) Duration.m3437getInWholeMillisecondsimpl(j), i), DurationUnit.MILLISECONDS);
    }
}
