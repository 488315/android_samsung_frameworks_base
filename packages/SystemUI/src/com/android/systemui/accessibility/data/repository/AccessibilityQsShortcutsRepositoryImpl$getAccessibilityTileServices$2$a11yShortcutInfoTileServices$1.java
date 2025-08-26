package com.android.systemui.accessibility.data.repository;

import android.accessibilityservice.AccessibilityShortcutInfo;
import android.content.ComponentName;
import android.content.Context;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityManager;
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
final class AccessibilityQsShortcutsRepositoryImpl$getAccessibilityTileServices$2$a11yShortcutInfoTileServices$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Context $context;
    int label;
    final /* synthetic */ AccessibilityQsShortcutsRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AccessibilityQsShortcutsRepositoryImpl$getAccessibilityTileServices$2$a11yShortcutInfoTileServices$1(AccessibilityQsShortcutsRepositoryImpl accessibilityQsShortcutsRepositoryImpl, Context context, Continuation continuation) {
        super(2, continuation);
        this.this$0 = accessibilityQsShortcutsRepositoryImpl;
        this.$context = context;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AccessibilityQsShortcutsRepositoryImpl$getAccessibilityTileServices$2$a11yShortcutInfoTileServices$1(this.this$0, this.$context, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AccessibilityQsShortcutsRepositoryImpl$getAccessibilityTileServices$2$a11yShortcutInfoTileServices$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        ComponentName componentName;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        AccessibilityManager accessibilityManager = this.this$0.manager;
        Context context = this.$context;
        List<AccessibilityShortcutInfo> installedAccessibilityShortcutListAsUser = accessibilityManager.getInstalledAccessibilityShortcutListAsUser(context, context.getUserId());
        ArrayList arrayList = new ArrayList();
        for (AccessibilityShortcutInfo accessibilityShortcutInfo : installedAccessibilityShortcutListAsUser) {
            String packageName = accessibilityShortcutInfo.getComponentName().getPackageName();
            String tileServiceName = accessibilityShortcutInfo.getTileServiceName();
            if (TextUtils.isEmpty(tileServiceName)) {
                componentName = null;
            } else {
                tileServiceName.getClass();
                componentName = new ComponentName(packageName, tileServiceName);
            }
            if (componentName != null) {
                arrayList.add(componentName);
            }
        }
        return CollectionsKt___CollectionsKt.toSet(arrayList);
    }
}
