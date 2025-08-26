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
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.Executor;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

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
            NoteTaskQuickAffordanceConfig noteTaskQuickAffordanceConfig = this.f$0;
            KeyguardQuickAffordanceRepository keyguardQuickAffordanceRepository = (KeyguardQuickAffordanceRepository) noteTaskQuickAffordanceConfig.lazyRepository.get();
            keyguardQuickAffordanceRepository.getClass();
            final String str = "create_note";
            final ReadonlyStateFlow readonlyStateFlow = keyguardQuickAffordanceRepository.selections;
            Flow flow = new Flow() { // from class: com.android.systemui.notetask.quickaffordance.NoteTaskQuickAffordanceConfigKt$createConfigSelectedFlow$$inlined$map$1

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

                    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object emit(Object obj, Continuation continuation) {
                        AnonymousClass1 anonymousClass1;
                        if (continuation instanceof AnonymousClass1) {
                            anonymousClass1 = (AnonymousClass1) continuation;
                            int i = anonymousClass1.label;
                            if ((i & Integer.MIN_VALUE) != 0) {
                                anonymousClass1.label = i - Integer.MIN_VALUE;
                            } else {
                                anonymousClass1 = new AnonymousClass1(continuation);
                            }
                        }
                        Object obj2 = anonymousClass1.result;
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        int i2 = anonymousClass1.label;
                        if (i2 == 0) {
                            ResultKt.throwOnFailure(obj2);
                            ArrayList arrayList = (ArrayList) CollectionsKt__IterablesKt.flatten(((Map) obj).values());
                            boolean z = false;
                            if (!arrayList.isEmpty()) {
                                int size = arrayList.size();
                                int i3 = 0;
                                while (true) {
                                    if (i3 >= size) {
                                        break;
                                    }
                                    Object obj3 = arrayList.get(i3);
                                    i3++;
                                    if (Intrinsics.areEqual(((KeyguardQuickAffordanceConfig) obj3).getKey(), this.$key$inlined)) {
                                        z = true;
                                        break;
                                    }
                                }
                            }
                            Boolean boolValueOf = Boolean.valueOf(z);
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                        } else {
                            if (i2 != 1) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj2);
                        }
                        return Unit.INSTANCE;
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                    Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector, str), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
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
