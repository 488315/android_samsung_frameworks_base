package com.android.systemui.accessibility.data.repository;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.ComponentName;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes.dex */
final class AccessibilityQsShortcutsRepositoryImpl$getAccessibilityTileServices$2$a11yServiceTileServices$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ AccessibilityQsShortcutsRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AccessibilityQsShortcutsRepositoryImpl$getAccessibilityTileServices$2$a11yServiceTileServices$1(AccessibilityQsShortcutsRepositoryImpl accessibilityQsShortcutsRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = accessibilityQsShortcutsRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AccessibilityQsShortcutsRepositoryImpl$getAccessibilityTileServices$2$a11yServiceTileServices$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AccessibilityQsShortcutsRepositoryImpl$getAccessibilityTileServices$2$a11yServiceTileServices$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        ComponentName componentName;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        List<AccessibilityServiceInfo> installedAccessibilityServiceList = this.this$0.manager.getInstalledAccessibilityServiceList();
        ArrayList arrayList = new ArrayList();
        for (AccessibilityServiceInfo accessibilityServiceInfo : installedAccessibilityServiceList) {
            String str = accessibilityServiceInfo.getResolveInfo().serviceInfo.packageName;
            String tileServiceName = accessibilityServiceInfo.getTileServiceName();
            if (TextUtils.isEmpty(tileServiceName)) {
                componentName = null;
            } else {
                tileServiceName.getClass();
                componentName = new ComponentName(str, tileServiceName);
            }
            if (componentName != null) {
                arrayList.add(componentName);
            }
        }
        return CollectionsKt___CollectionsKt.toSet(arrayList);
    }
}
