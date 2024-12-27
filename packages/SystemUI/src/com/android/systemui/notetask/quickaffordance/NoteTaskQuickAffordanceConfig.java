package com.android.systemui.notetask.quickaffordance;

import android.app.role.RoleManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.R;
import com.android.systemui.animation.Expandable;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository;
import com.android.systemui.notetask.NoteTaskController;
import com.android.systemui.notetask.NoteTaskEntryPoint;
import com.android.systemui.notetask.NoteTaskInfoResolver;
import com.android.systemui.stylus.StylusManager;
import dagger.Lazy;
import java.util.concurrent.Executor;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.CallbackFlowBuilder;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NoteTaskQuickAffordanceConfig implements KeyguardQuickAffordanceConfig {
    public final Executor backgroundExecutor;
    public final Context context;
    public final NoteTaskController controller;
    public final boolean isEnabled;
    public final KeyguardUpdateMonitor keyguardMonitor;
    public final Lazy lazyRepository;
    public final NoteTaskInfoResolver noteTaskInfoResolver;
    public final RoleManager roleManager;
    public final StylusManager stylusManager;
    public final UserManager userManager;
    public final String key = "create_note";
    public final int pickerNameResourceId = R.string.note_task_button_label;
    public final int pickerIconResourceId = R.drawable.ic_note_task_shortcut_keyguard;
    public final kotlin.Lazy lockScreenState$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.notetask.quickaffordance.NoteTaskQuickAffordanceConfig$lockScreenState$2

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        /* renamed from: com.android.systemui.notetask.quickaffordance.NoteTaskQuickAffordanceConfig$lockScreenState$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function5 {
            /* synthetic */ boolean Z$0;
            /* synthetic */ boolean Z$1;
            /* synthetic */ boolean Z$2;
            /* synthetic */ boolean Z$3;
            int label;
            final /* synthetic */ NoteTaskQuickAffordanceConfig this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(NoteTaskQuickAffordanceConfig noteTaskQuickAffordanceConfig, Continuation continuation) {
                super(5, continuation);
                this.this$0 = noteTaskQuickAffordanceConfig;
            }

            @Override // kotlin.jvm.functions.Function5
            public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
                boolean booleanValue = ((Boolean) obj).booleanValue();
                boolean booleanValue2 = ((Boolean) obj2).booleanValue();
                boolean booleanValue3 = ((Boolean) obj3).booleanValue();
                boolean booleanValue4 = ((Boolean) obj4).booleanValue();
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, (Continuation) obj5);
                anonymousClass1.Z$0 = booleanValue;
                anonymousClass1.Z$1 = booleanValue2;
                anonymousClass1.Z$2 = booleanValue3;
                anonymousClass1.Z$3 = booleanValue4;
                return anonymousClass1.invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                boolean z = this.Z$0;
                boolean z2 = this.Z$1;
                boolean z3 = this.Z$2;
                boolean z4 = this.Z$3;
                NoteTaskQuickAffordanceConfig noteTaskQuickAffordanceConfig = this.this$0;
                boolean z5 = Build.IS_DEBUGGABLE;
                if (z5) {
                    KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("lockScreenState:isUserUnlocked=", noteTaskQuickAffordanceConfig.getClass().getSimpleName(), z);
                }
                NoteTaskQuickAffordanceConfig noteTaskQuickAffordanceConfig2 = this.this$0;
                if (z5) {
                    KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("lockScreenState:isStylusEverUsed=", noteTaskQuickAffordanceConfig2.getClass().getSimpleName(), z2);
                }
                NoteTaskQuickAffordanceConfig noteTaskQuickAffordanceConfig3 = this.this$0;
                if (z5) {
                    KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("lockScreenState:isConfigSelected=", noteTaskQuickAffordanceConfig3.getClass().getSimpleName(), z3);
                }
                NoteTaskQuickAffordanceConfig noteTaskQuickAffordanceConfig4 = this.this$0;
                if (z5) {
                    KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("lockScreenState:isDefaultNotesAppSet=", noteTaskQuickAffordanceConfig4.getClass().getSimpleName(), z4);
                }
                if (!this.this$0.isEnabled || !z || !z4 || !z3 || !z2) {
                    return KeyguardQuickAffordanceConfig.LockScreenState.Hidden.INSTANCE;
                }
                return new KeyguardQuickAffordanceConfig.LockScreenState.Visible(new Icon.Resource(this.this$0.pickerIconResourceId, new ContentDescription.Resource(this.this$0.pickerNameResourceId)), null, 2, null);
            }
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        /* renamed from: com.android.systemui.notetask.quickaffordance.NoteTaskQuickAffordanceConfig$lockScreenState$2$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ NoteTaskQuickAffordanceConfig this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(NoteTaskQuickAffordanceConfig noteTaskQuickAffordanceConfig, Continuation continuation) {
                super(2, continuation);
                this.this$0 = noteTaskQuickAffordanceConfig;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.this$0, continuation);
                anonymousClass2.L$0 = obj;
                return anonymousClass2;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass2) create((KeyguardQuickAffordanceConfig.LockScreenState) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                KeyguardQuickAffordanceConfig.LockScreenState lockScreenState = (KeyguardQuickAffordanceConfig.LockScreenState) this.L$0;
                NoteTaskQuickAffordanceConfig noteTaskQuickAffordanceConfig = this.this$0;
                if (Build.IS_DEBUGGABLE) {
                    Log.d(noteTaskQuickAffordanceConfig.getClass().getSimpleName(), "lockScreenState=" + lockScreenState);
                }
                return Unit.INSTANCE;
            }
        }

        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            KeyguardQuickAffordanceRepository keyguardQuickAffordanceRepository = (KeyguardQuickAffordanceRepository) NoteTaskQuickAffordanceConfig.this.lazyRepository.get();
            Intrinsics.checkNotNull(keyguardQuickAffordanceRepository);
            final String str = NoteTaskQuickAffordanceConfig.this.key;
            final ReadonlyStateFlow readonlyStateFlow = keyguardQuickAffordanceRepository.selections;
            Flow flow = new Flow() { // from class: com.android.systemui.notetask.quickaffordance.NoteTaskQuickAffordanceConfigKt$createConfigSelectedFlow$$inlined$map$1

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.notetask.quickaffordance.NoteTaskQuickAffordanceConfigKt$createConfigSelectedFlow$$inlined$map$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ String $key$inlined;
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                    /* renamed from: com.android.systemui.notetask.quickaffordance.NoteTaskQuickAffordanceConfigKt$createConfigSelectedFlow$$inlined$map$1$2$1, reason: invalid class name */
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
                            this.label |= Integer.MIN_VALUE;
                            return AnonymousClass2.this.emit(null, this);
                        }
                    }

                    public AnonymousClass2(FlowCollector flowCollector, String str) {
                        this.$this_unsafeFlow = flowCollector;
                        this.$key$inlined = str;
                    }

                    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                        /*
                            r5 = this;
                            boolean r0 = r7 instanceof com.android.systemui.notetask.quickaffordance.NoteTaskQuickAffordanceConfigKt$createConfigSelectedFlow$$inlined$map$1.AnonymousClass2.AnonymousClass1
                            if (r0 == 0) goto L13
                            r0 = r7
                            com.android.systemui.notetask.quickaffordance.NoteTaskQuickAffordanceConfigKt$createConfigSelectedFlow$$inlined$map$1$2$1 r0 = (com.android.systemui.notetask.quickaffordance.NoteTaskQuickAffordanceConfigKt$createConfigSelectedFlow$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r3 = r1 & r2
                            if (r3 == 0) goto L13
                            int r1 = r1 - r2
                            r0.label = r1
                            goto L18
                        L13:
                            com.android.systemui.notetask.quickaffordance.NoteTaskQuickAffordanceConfigKt$createConfigSelectedFlow$$inlined$map$1$2$1 r0 = new com.android.systemui.notetask.quickaffordance.NoteTaskQuickAffordanceConfigKt$createConfigSelectedFlow$$inlined$map$1$2$1
                            r0.<init>(r7)
                        L18:
                            java.lang.Object r7 = r0.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r2 = r0.label
                            r3 = 1
                            if (r2 == 0) goto L2f
                            if (r2 != r3) goto L27
                            kotlin.ResultKt.throwOnFailure(r7)
                            goto L72
                        L27:
                            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                            r5.<init>(r6)
                            throw r5
                        L2f:
                            kotlin.ResultKt.throwOnFailure(r7)
                            java.util.Map r6 = (java.util.Map) r6
                            java.util.Collection r6 = r6.values()
                            java.lang.Iterable r6 = (java.lang.Iterable) r6
                            java.util.List r6 = kotlin.collections.CollectionsKt__IterablesKt.flatten(r6)
                            boolean r7 = r6.isEmpty()
                            r2 = 0
                            if (r7 == 0) goto L46
                            goto L63
                        L46:
                            java.util.Iterator r6 = r6.iterator()
                        L4a:
                            boolean r7 = r6.hasNext()
                            if (r7 == 0) goto L63
                            java.lang.Object r7 = r6.next()
                            com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig r7 = (com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig) r7
                            java.lang.String r7 = r7.getKey()
                            java.lang.String r4 = r5.$key$inlined
                            boolean r7 = kotlin.jvm.internal.Intrinsics.areEqual(r7, r4)
                            if (r7 == 0) goto L4a
                            r2 = r3
                        L63:
                            java.lang.Boolean r6 = java.lang.Boolean.valueOf(r2)
                            r0.label = r3
                            kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                            java.lang.Object r5 = r5.emit(r6, r0)
                            if (r5 != r1) goto L72
                            return r1
                        L72:
                            kotlin.Unit r5 = kotlin.Unit.INSTANCE
                            return r5
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.notetask.quickaffordance.NoteTaskQuickAffordanceConfigKt$createConfigSelectedFlow$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, str), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            };
            NoteTaskQuickAffordanceConfig noteTaskQuickAffordanceConfig = NoteTaskQuickAffordanceConfig.this;
            CallbackFlowBuilder callbackFlow = FlowKt.callbackFlow(new NoteTaskQuickAffordanceConfigKt$createStylusEverUsedFlow$1(noteTaskQuickAffordanceConfig.context, noteTaskQuickAffordanceConfig.stylusManager, null));
            NoteTaskQuickAffordanceConfig noteTaskQuickAffordanceConfig2 = NoteTaskQuickAffordanceConfig.this;
            CallbackFlowBuilder callbackFlow2 = FlowKt.callbackFlow(new NoteTaskQuickAffordanceConfigKt$createUserUnlockedFlow$1(noteTaskQuickAffordanceConfig2.userManager, noteTaskQuickAffordanceConfig2.keyguardMonitor, null));
            NoteTaskQuickAffordanceConfig noteTaskQuickAffordanceConfig3 = NoteTaskQuickAffordanceConfig.this;
            return new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.combine(callbackFlow2, callbackFlow, flow, FlowKt.callbackFlow(new NoteTaskQuickAffordanceConfigKt$createNotesRoleFlow$1(noteTaskQuickAffordanceConfig3.roleManager, noteTaskQuickAffordanceConfig3.backgroundExecutor, noteTaskQuickAffordanceConfig3.noteTaskInfoResolver, noteTaskQuickAffordanceConfig3.controller, null)), new AnonymousClass1(NoteTaskQuickAffordanceConfig.this, null)), new AnonymousClass2(NoteTaskQuickAffordanceConfig.this, null));
        }
    });

    public NoteTaskQuickAffordanceConfig(Context context, NoteTaskController noteTaskController, NoteTaskInfoResolver noteTaskInfoResolver, StylusManager stylusManager, RoleManager roleManager, KeyguardUpdateMonitor keyguardUpdateMonitor, UserManager userManager, Lazy lazy, boolean z, Executor executor) {
        this.context = context;
        this.controller = noteTaskController;
        this.noteTaskInfoResolver = noteTaskInfoResolver;
        this.stylusManager = stylusManager;
        this.roleManager = roleManager;
        this.keyguardMonitor = keyguardUpdateMonitor;
        this.userManager = userManager;
        this.lazyRepository = lazy;
        this.isEnabled = z;
        this.backgroundExecutor = executor;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final String getKey() {
        return this.key;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final Flow getLockScreenState() {
        return (Flow) this.lockScreenState$delegate.getValue();
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final int getPickerIconResourceId() {
        return this.pickerIconResourceId;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final Object getPickerScreenState(Continuation continuation) {
        NoteTaskEntryPoint noteTaskEntryPoint = NoteTaskEntryPoint.QUICK_AFFORDANCE;
        UserHandle userForHandlingNotesTaking = this.controller.getUserForHandlingNotesTaking(noteTaskEntryPoint);
        NoteTaskInfoResolver.Companion companion = NoteTaskInfoResolver.Companion;
        boolean z = this.noteTaskInfoResolver.resolveInfo(noteTaskEntryPoint, false, userForHandlingNotesTaking) != null;
        boolean z2 = this.isEnabled;
        if (z2 && z) {
            return new KeyguardQuickAffordanceConfig.PickerScreenState.Default(null, 1, null);
        }
        if (!z2) {
            return KeyguardQuickAffordanceConfig.PickerScreenState.UnavailableOnDevice.INSTANCE;
        }
        String string = this.context.getString(R.string.notes_app_quick_affordance_unavailable_explanation);
        String string2 = this.context.getString(R.string.keyguard_affordance_enablement_dialog_notes_app_action);
        Intent intent = new Intent("com.android.systemui.action.MANAGE_NOTES_ROLE_FROM_QUICK_AFFORDANCE");
        intent.setPackage(this.context.getPackageName());
        Unit unit = Unit.INSTANCE;
        return new KeyguardQuickAffordanceConfig.PickerScreenState.Disabled(string, string2, intent);
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final KeyguardQuickAffordanceConfig.OnTriggeredResult onTriggered(Expandable expandable) {
        NoteTaskEntryPoint noteTaskEntryPoint = NoteTaskEntryPoint.QUICK_AFFORDANCE;
        NoteTaskController noteTaskController = this.controller;
        if (noteTaskController.isEnabled) {
            noteTaskController.showNoteTaskAsUser(noteTaskEntryPoint, noteTaskController.getUserForHandlingNotesTaking(noteTaskEntryPoint));
        }
        return KeyguardQuickAffordanceConfig.OnTriggeredResult.Handled.INSTANCE;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final String pickerName() {
        return this.context.getString(this.pickerNameResourceId);
    }
}
