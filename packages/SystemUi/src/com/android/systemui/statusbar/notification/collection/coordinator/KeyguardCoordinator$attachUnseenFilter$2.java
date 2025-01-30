package com.android.systemui.statusbar.notification.collection.coordinator;

import android.provider.Settings;
import com.android.systemui.util.settings.SecureSettingsImpl;
import com.android.systemui.util.settings.SettingsProxyExt;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$attachUnseenFilter$2", m277f = "KeyguardCoordinator.kt", m278l = {113}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
final class KeyguardCoordinator$attachUnseenFilter$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ KeyguardCoordinator this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardCoordinator$attachUnseenFilter$2(KeyguardCoordinator keyguardCoordinator, Continuation<? super KeyguardCoordinator$attachUnseenFilter$2> continuation) {
        super(2, continuation);
        this.this$0 = keyguardCoordinator;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new KeyguardCoordinator$attachUnseenFilter$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardCoordinator$attachUnseenFilter$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final KeyguardCoordinator keyguardCoordinator = this.this$0;
            this.label = 1;
            int i2 = KeyguardCoordinator.$r8$clinit;
            keyguardCoordinator.getClass();
            SettingsProxyExt.INSTANCE.getClass();
            final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new KeyguardCoordinator$invalidateWhenUnseenSettingChanges$2(null), SettingsProxyExt.observerFlow(keyguardCoordinator.secureSettings, -1, "lock_screen_show_only_unseen_notifications"));
            Object collect = FlowKt.buffer$default(FlowKt.flowOn(new Flow() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$invalidateWhenUnseenSettingChanges$$inlined$map$1

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$invalidateWhenUnseenSettingChanges$$inlined$map$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;
                    public final /* synthetic */ KeyguardCoordinator this$0;

                    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                    @DebugMetadata(m276c = "com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$invalidateWhenUnseenSettingChanges$$inlined$map$1$2", m277f = "KeyguardCoordinator.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                    /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$invalidateWhenUnseenSettingChanges$$inlined$map$1$2$1, reason: invalid class name */
                    public final class AnonymousClass1 extends ContinuationImpl {
                        Object L$0;
                        int label;
                        /* synthetic */ Object result;

                        public AnonymousClass1(Continuation continuation) {
                            super(continuation);
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Object invokeSuspend(Object obj) {
                            this.result = obj;
                            this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                            return AnonymousClass2.this.emit(null, this);
                        }
                    }

                    public AnonymousClass2(FlowCollector flowCollector, KeyguardCoordinator keyguardCoordinator) {
                        this.$this_unsafeFlow = flowCollector;
                        this.this$0 = keyguardCoordinator;
                    }

                    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object emit(Object obj, Continuation continuation) {
                        AnonymousClass1 anonymousClass1;
                        int i;
                        if (continuation instanceof AnonymousClass1) {
                            anonymousClass1 = (AnonymousClass1) continuation;
                            int i2 = anonymousClass1.label;
                            if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                                anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                                Object obj2 = anonymousClass1.result;
                                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                i = anonymousClass1.label;
                                if (i != 0) {
                                    ResultKt.throwOnFailure(obj2);
                                    try {
                                        Boolean valueOf = Boolean.valueOf(Integer.parseInt(((SecureSettingsImpl) this.this$0.secureSettings).getStringForUser(-2, "lock_screen_show_only_unseen_notifications")) == 1);
                                        anonymousClass1.label = 1;
                                        if (this.$this_unsafeFlow.emit(valueOf, anonymousClass1) == coroutineSingletons) {
                                            return coroutineSingletons;
                                        }
                                    } catch (NumberFormatException unused) {
                                        throw new Settings.SettingNotFoundException("lock_screen_show_only_unseen_notifications");
                                    }
                                } else {
                                    if (i != 1) {
                                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                    }
                                    ResultKt.throwOnFailure(obj2);
                                }
                                return Unit.INSTANCE;
                            }
                        }
                        anonymousClass1 = new AnonymousClass1(continuation);
                        Object obj22 = anonymousClass1.result;
                        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                        i = anonymousClass1.label;
                        if (i != 0) {
                        }
                        return Unit.INSTANCE;
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                    Object collect2 = Flow.this.collect(new AnonymousClass2(flowCollector, keyguardCoordinator), continuation);
                    return collect2 == CoroutineSingletons.COROUTINE_SUSPENDED ? collect2 : Unit.INSTANCE;
                }
            }, keyguardCoordinator.bgDispatcher), -1).collect(new FlowCollector() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator$invalidateWhenUnseenSettingChanges$4
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    boolean booleanValue = ((Boolean) obj2).booleanValue();
                    KeyguardCoordinator keyguardCoordinator2 = KeyguardCoordinator.this;
                    if (booleanValue != keyguardCoordinator2.unseenFilterEnabled) {
                        keyguardCoordinator2.unseenFilterEnabled = booleanValue;
                        keyguardCoordinator2.unseenNotifFilter.invalidateList("unseen setting changed");
                    }
                    return Unit.INSTANCE;
                }
            }, this);
            if (collect != coroutineSingletons) {
                collect = Unit.INSTANCE;
            }
            if (collect == coroutineSingletons) {
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
