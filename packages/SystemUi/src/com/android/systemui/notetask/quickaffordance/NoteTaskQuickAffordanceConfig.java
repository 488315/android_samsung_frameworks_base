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
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import dagger.Lazy;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.notetask.quickaffordance.NoteTaskQuickAffordanceConfig$lockScreenState$2$1", m277f = "NoteTaskQuickAffordanceConfig.kt", m278l = {}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.notetask.quickaffordance.NoteTaskQuickAffordanceConfig$lockScreenState$2$1 */
        final class C18971 extends SuspendLambda implements Function5 {
            /* synthetic */ boolean Z$0;
            /* synthetic */ boolean Z$1;
            /* synthetic */ boolean Z$2;
            /* synthetic */ boolean Z$3;
            int label;
            final /* synthetic */ NoteTaskQuickAffordanceConfig this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C18971(NoteTaskQuickAffordanceConfig noteTaskQuickAffordanceConfig, Continuation<? super C18971> continuation) {
                super(5, continuation);
                this.this$0 = noteTaskQuickAffordanceConfig;
            }

            @Override // kotlin.jvm.functions.Function5
            public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
                boolean booleanValue = ((Boolean) obj).booleanValue();
                boolean booleanValue2 = ((Boolean) obj2).booleanValue();
                boolean booleanValue3 = ((Boolean) obj3).booleanValue();
                boolean booleanValue4 = ((Boolean) obj4).booleanValue();
                C18971 c18971 = new C18971(this.this$0, (Continuation) obj5);
                c18971.Z$0 = booleanValue;
                c18971.Z$1 = booleanValue2;
                c18971.Z$2 = booleanValue3;
                c18971.Z$3 = booleanValue4;
                return c18971.invokeSuspend(Unit.INSTANCE);
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
                if (Build.IS_DEBUGGABLE) {
                    KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("lockScreenState:isUserUnlocked=", z, noteTaskQuickAffordanceConfig.getClass().getSimpleName());
                }
                NoteTaskQuickAffordanceConfig noteTaskQuickAffordanceConfig2 = this.this$0;
                if (Build.IS_DEBUGGABLE) {
                    KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("lockScreenState:isStylusEverUsed=", z2, noteTaskQuickAffordanceConfig2.getClass().getSimpleName());
                }
                NoteTaskQuickAffordanceConfig noteTaskQuickAffordanceConfig3 = this.this$0;
                if (Build.IS_DEBUGGABLE) {
                    KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("lockScreenState:isConfigSelected=", z3, noteTaskQuickAffordanceConfig3.getClass().getSimpleName());
                }
                NoteTaskQuickAffordanceConfig noteTaskQuickAffordanceConfig4 = this.this$0;
                if (Build.IS_DEBUGGABLE) {
                    KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("lockScreenState:isDefaultNotesAppSet=", z4, noteTaskQuickAffordanceConfig4.getClass().getSimpleName());
                }
                if (!this.this$0.isEnabled || !z || !z4 || (!z3 && !z2)) {
                    return KeyguardQuickAffordanceConfig.LockScreenState.Hidden.INSTANCE;
                }
                return new KeyguardQuickAffordanceConfig.LockScreenState.Visible(new Icon.Resource(this.this$0.pickerIconResourceId, new ContentDescription.Resource(this.this$0.pickerNameResourceId)), null, 2, null);
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.notetask.quickaffordance.NoteTaskQuickAffordanceConfig$lockScreenState$2$2", m277f = "NoteTaskQuickAffordanceConfig.kt", m278l = {}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.notetask.quickaffordance.NoteTaskQuickAffordanceConfig$lockScreenState$2$2 */
        final class C18982 extends SuspendLambda implements Function2 {
            /* synthetic */ Object L$0;
            int label;
            final /* synthetic */ NoteTaskQuickAffordanceConfig this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C18982(NoteTaskQuickAffordanceConfig noteTaskQuickAffordanceConfig, Continuation<? super C18982> continuation) {
                super(2, continuation);
                this.this$0 = noteTaskQuickAffordanceConfig;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C18982 c18982 = new C18982(this.this$0, continuation);
                c18982.L$0 = obj;
                return c18982;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C18982) create((KeyguardQuickAffordanceConfig.LockScreenState) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
            final String str = NoteTaskQuickAffordanceConfig.this.key;
            final ReadonlyStateFlow readonlyStateFlow = keyguardQuickAffordanceRepository.selections;
            Flow flow = new Flow() { // from class: com.android.systemui.notetask.quickaffordance.NoteTaskQuickAffordanceConfigKt$createConfigSelectedFlow$$inlined$map$1

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                /* renamed from: com.android.systemui.notetask.quickaffordance.NoteTaskQuickAffordanceConfigKt$createConfigSelectedFlow$$inlined$map$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ String $key$inlined;
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                    @DebugMetadata(m276c = "com.android.systemui.notetask.quickaffordance.NoteTaskQuickAffordanceConfigKt$createConfigSelectedFlow$$inlined$map$1$2", m277f = "NoteTaskQuickAffordanceConfig.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
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
                            this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
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
                                    List flatten = CollectionsKt__IterablesKt.flatten(((Map) obj).values());
                                    boolean z = false;
                                    if (!flatten.isEmpty()) {
                                        Iterator it = flatten.iterator();
                                        while (true) {
                                            if (!it.hasNext()) {
                                                break;
                                            }
                                            if (Intrinsics.areEqual(((KeyguardQuickAffordanceConfig) it.next()).getKey(), this.$key$inlined)) {
                                                z = true;
                                                break;
                                            }
                                        }
                                    }
                                    Boolean valueOf = Boolean.valueOf(z);
                                    anonymousClass1.label = 1;
                                    if (this.$this_unsafeFlow.emit(valueOf, anonymousClass1) == coroutineSingletons) {
                                        return coroutineSingletons;
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
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, str), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            };
            NoteTaskQuickAffordanceConfig noteTaskQuickAffordanceConfig = NoteTaskQuickAffordanceConfig.this;
            CallbackFlowBuilder callbackFlow = FlowKt.callbackFlow(new NoteTaskQuickAffordanceConfigKt$createStylusEverUsedFlow$1(noteTaskQuickAffordanceConfig.context, noteTaskQuickAffordanceConfig.stylusManager, null));
            NoteTaskQuickAffordanceConfig noteTaskQuickAffordanceConfig2 = NoteTaskQuickAffordanceConfig.this;
            CallbackFlowBuilder callbackFlow2 = FlowKt.callbackFlow(new NoteTaskQuickAffordanceConfigKt$createUserUnlockedFlow$1(noteTaskQuickAffordanceConfig2.userManager, noteTaskQuickAffordanceConfig2.keyguardMonitor, null));
            NoteTaskQuickAffordanceConfig noteTaskQuickAffordanceConfig3 = NoteTaskQuickAffordanceConfig.this;
            return new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.combine(callbackFlow2, callbackFlow, flow, FlowKt.callbackFlow(new NoteTaskQuickAffordanceConfigKt$createNotesRoleFlow$1(noteTaskQuickAffordanceConfig3.roleManager, noteTaskQuickAffordanceConfig3.backgroundExecutor, noteTaskQuickAffordanceConfig3.noteTaskInfoResolver, noteTaskQuickAffordanceConfig3.controller, null)), new C18971(NoteTaskQuickAffordanceConfig.this, null)), new C18982(NoteTaskQuickAffordanceConfig.this, null));
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
        Context context = this.context;
        String string = context.getString(R.string.notes_app_quick_affordance_unavailable_explanation);
        String string2 = context.getString(R.string.keyguard_affordance_enablement_dialog_notes_app_action);
        Intent intent = new Intent("com.android.systemui.action.MANAGE_NOTES_ROLE_FROM_QUICK_AFFORDANCE");
        intent.setPackage(context.getPackageName());
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
