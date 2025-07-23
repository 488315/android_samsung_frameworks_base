package com.android.systemui.accessibility.data.repository;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class AccessibilityRepositoryImpl$isEnabled$1$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AccessibilityRepositoryImpl f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ AccessibilityRepositoryImpl$isEnabled$1$$ExternalSyntheticLambda0(AccessibilityRepositoryImpl accessibilityRepositoryImpl, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = accessibilityRepositoryImpl;
        this.f$1 = obj;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.manager.removeAccessibilityStateChangeListener((AccessibilityRepositoryKt$sam$android_view_accessibility_AccessibilityManager_AccessibilityStateChangeListener$0) this.f$1);
                break;
            default:
                this.f$0.manager.removeTouchExplorationStateChangeListener((AccessibilityRepositoryKt$sam$android_view_accessibility_AccessibilityManager_TouchExplorationStateChangeListener$0) this.f$1);
                break;
        }
        return Unit.INSTANCE;
    }
}
