package com.android.systemui.notetask.quickaffordance;

import android.app.role.RoleManager;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.os.UserManager;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.R;
import com.android.systemui.animation.Expandable;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository;
import com.android.systemui.notetask.NoteTaskController;
import com.android.systemui.notetask.NoteTaskEntryPoint;
import com.android.systemui.notetask.NoteTaskInfoResolver;
import com.android.systemui.stylus.StylusManager;
import dagger.Lazy;
import java.util.concurrent.Executor;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class NoteTaskQuickAffordanceConfig implements KeyguardQuickAffordanceConfig {
    public final Executor backgroundExecutor;
    public final Context context;
    public final NoteTaskController controller;
    public final boolean isEnabled;
    public final KeyguardUpdateMonitor keyguardMonitor;
    public final Lazy lazyRepository;
    public final kotlin.Lazy lockScreenState$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.notetask.quickaffordance.NoteTaskQuickAffordanceConfig$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            NoteTaskQuickAffordanceConfig noteTaskQuickAffordanceConfig = NoteTaskQuickAffordanceConfig.this;
            KeyguardQuickAffordanceRepository keyguardQuickAffordanceRepository = (KeyguardQuickAffordanceRepository) noteTaskQuickAffordanceConfig.lazyRepository.get();
            keyguardQuickAffordanceRepository.getClass();
            final String str = "create_note";
            final ReadonlyStateFlow readonlyStateFlow = keyguardQuickAffordanceRepository.selections;
            Flow flow = new Flow() { // from class: com.android.systemui.notetask.quickaffordance.NoteTaskQuickAffordanceConfigKt$createConfigSelectedFlow$$inlined$map$1

                /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                /* renamed from: com.android.systemui.notetask.quickaffordance.NoteTaskQuickAffordanceConfigKt$createConfigSelectedFlow$$inlined$map$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ String $key$inlined;
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

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
                    public final java.lang.Object emit(java.lang.Object r8, kotlin.coroutines.Continuation r9) {
                        /*
                            r7 = this;
                            boolean r0 = r9 instanceof com.android.systemui.notetask.quickaffordance.NoteTaskQuickAffordanceConfigKt$createConfigSelectedFlow$$inlined$map$1.AnonymousClass2.AnonymousClass1
                            if (r0 == 0) goto L13
                            r0 = r9
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
                            r0.<init>(r9)
                        L18:
                            java.lang.Object r9 = r0.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r2 = r0.label
                            r3 = 1
                            if (r2 == 0) goto L2f
                            if (r2 != r3) goto L27
                            kotlin.ResultKt.throwOnFailure(r9)
                            goto L73
                        L27:
                            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
                            r7.<init>(r8)
                            throw r7
                        L2f:
                            kotlin.ResultKt.throwOnFailure(r9)
                            java.util.Map r8 = (java.util.Map) r8
                            java.util.Collection r8 = r8.values()
                            java.lang.Iterable r8 = (java.lang.Iterable) r8
                            java.util.List r8 = kotlin.collections.CollectionsKt__IterablesKt.flatten(r8)
                            java.util.ArrayList r8 = (java.util.ArrayList) r8
                            boolean r9 = r8.isEmpty()
                            r2 = 0
                            if (r9 == 0) goto L48
                            goto L64
                        L48:
                            int r9 = r8.size()
                            r4 = r2
                        L4d:
                            if (r4 >= r9) goto L64
                            java.lang.Object r5 = r8.get(r4)
                            int r4 = r4 + 1
                            com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig r5 = (com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig) r5
                            java.lang.String r5 = r5.getKey()
                            java.lang.String r6 = r7.$key$inlined
                            boolean r5 = kotlin.jvm.internal.Intrinsics.areEqual(r5, r6)
                            if (r5 == 0) goto L4d
                            r2 = r3
                        L64:
                            java.lang.Boolean r8 = java.lang.Boolean.valueOf(r2)
                            r0.label = r3
                            kotlinx.coroutines.flow.FlowCollector r7 = r7.$this_unsafeFlow
                            java.lang.Object r7 = r7.emit(r8, r0)
                            if (r7 != r1) goto L73
                            return r1
                        L73:
                            kotlin.Unit r7 = kotlin.Unit.INSTANCE
                            return r7
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
            return new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.combine(FlowKt.callbackFlow(new NoteTaskQuickAffordanceConfigKt$createUserUnlockedFlow$1(noteTaskQuickAffordanceConfig.userManager, noteTaskQuickAffordanceConfig.keyguardMonitor, null)), FlowKt.callbackFlow(new NoteTaskQuickAffordanceConfigKt$createStylusEverUsedFlow$1(noteTaskQuickAffordanceConfig.context, noteTaskQuickAffordanceConfig.stylusManager, null)), flow, FlowKt.callbackFlow(new NoteTaskQuickAffordanceConfigKt$createNotesRoleFlow$1(noteTaskQuickAffordanceConfig.roleManager, noteTaskQuickAffordanceConfig.backgroundExecutor, noteTaskQuickAffordanceConfig.noteTaskInfoResolver, noteTaskQuickAffordanceConfig.controller, null)), new NoteTaskQuickAffordanceConfig$lockScreenState$2$1(noteTaskQuickAffordanceConfig, null)), new NoteTaskQuickAffordanceConfig$lockScreenState$2$2(noteTaskQuickAffordanceConfig, null));
        }
    });
    public final NoteTaskInfoResolver noteTaskInfoResolver;
    public final RoleManager roleManager;
    public final StylusManager stylusManager;
    public final UserManager userManager;

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
        return "create_note";
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final Flow getLockScreenState() {
        return (Flow) this.lockScreenState$delegate.getValue();
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final int getPickerIconResourceId() {
        return R.drawable.ic_note_task_shortcut_keyguard;
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
        return new KeyguardQuickAffordanceConfig.OnTriggeredResult.Handled(true);
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig
    public final String pickerName() {
        return this.context.getString(R.string.note_task_button_label);
    }
}
