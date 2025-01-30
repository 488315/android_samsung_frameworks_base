package com.android.systemui.accessibility.data.repository;

import android.view.accessibility.AccessibilityManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.accessibility.data.repository.AccessibilityRepositoryImpl$isTouchExplorationEnabled$1", m277f = "AccessibilityRepository.kt", m278l = {47}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
public final class AccessibilityRepositoryImpl$isTouchExplorationEnabled$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ AccessibilityManager $manager;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AccessibilityRepositoryImpl$isTouchExplorationEnabled$1(AccessibilityManager accessibilityManager, Continuation<? super AccessibilityRepositoryImpl$isTouchExplorationEnabled$1> continuation) {
        super(2, continuation);
        this.$manager = accessibilityManager;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        AccessibilityRepositoryImpl$isTouchExplorationEnabled$1 accessibilityRepositoryImpl$isTouchExplorationEnabled$1 = new AccessibilityRepositoryImpl$isTouchExplorationEnabled$1(this.$manager, continuation);
        accessibilityRepositoryImpl$isTouchExplorationEnabled$1.L$0 = obj;
        return accessibilityRepositoryImpl$isTouchExplorationEnabled$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AccessibilityRepositoryImpl$isTouchExplorationEnabled$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ProducerScope producerScope = (ProducerScope) this.L$0;
            final C1008x9c1f5fb1 c1008x9c1f5fb1 = new C1008x9c1f5fb1(producerScope);
            final AccessibilityManager.TouchExplorationStateChangeListener touchExplorationStateChangeListener = new AccessibilityManager.TouchExplorationStateChangeListener() { // from class: com.android.systemui.accessibility.data.repository.AccessibilityRepositoryImpl$sam$android_view_accessibility_AccessibilityManager_TouchExplorationStateChangeListener$0
                @Override // android.view.accessibility.AccessibilityManager.TouchExplorationStateChangeListener
                public final /* synthetic */ void onTouchExplorationStateChanged(boolean z) {
                    Function1.this.invoke(Boolean.valueOf(z));
                }
            };
            this.$manager.addTouchExplorationStateChangeListener(touchExplorationStateChangeListener);
            ((ChannelCoroutine) producerScope).mo2872trySendJP2dKIU(Boolean.valueOf(this.$manager.isTouchExplorationEnabled()));
            final AccessibilityManager accessibilityManager = this.$manager;
            Function0 function0 = new Function0() { // from class: com.android.systemui.accessibility.data.repository.AccessibilityRepositoryImpl$isTouchExplorationEnabled$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    accessibilityManager.removeTouchExplorationStateChangeListener(touchExplorationStateChangeListener);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
