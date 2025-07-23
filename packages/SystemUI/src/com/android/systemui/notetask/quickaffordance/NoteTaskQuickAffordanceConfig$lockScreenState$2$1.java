package com.android.systemui.notetask.quickaffordance;

import android.os.Build;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class NoteTaskQuickAffordanceConfig$lockScreenState$2$1 extends SuspendLambda implements Function5 {
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    /* synthetic */ boolean Z$2;
    /* synthetic */ boolean Z$3;
    int label;
    final /* synthetic */ NoteTaskQuickAffordanceConfig this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NoteTaskQuickAffordanceConfig$lockScreenState$2$1(NoteTaskQuickAffordanceConfig noteTaskQuickAffordanceConfig, Continuation continuation) {
        super(5, continuation);
        this.this$0 = noteTaskQuickAffordanceConfig;
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        boolean booleanValue2 = ((Boolean) obj2).booleanValue();
        boolean booleanValue3 = ((Boolean) obj3).booleanValue();
        boolean booleanValue4 = ((Boolean) obj4).booleanValue();
        NoteTaskQuickAffordanceConfig$lockScreenState$2$1 noteTaskQuickAffordanceConfig$lockScreenState$2$1 = new NoteTaskQuickAffordanceConfig$lockScreenState$2$1(this.this$0, (Continuation) obj5);
        noteTaskQuickAffordanceConfig$lockScreenState$2$1.Z$0 = booleanValue;
        noteTaskQuickAffordanceConfig$lockScreenState$2$1.Z$1 = booleanValue2;
        noteTaskQuickAffordanceConfig$lockScreenState$2$1.Z$2 = booleanValue3;
        noteTaskQuickAffordanceConfig$lockScreenState$2$1.Z$3 = booleanValue4;
        return noteTaskQuickAffordanceConfig$lockScreenState$2$1.invokeSuspend(Unit.INSTANCE);
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
            EmergencyButtonController$$ExternalSyntheticOutline0.m("lockScreenState:isUserUnlocked=", noteTaskQuickAffordanceConfig.getClass().getSimpleName(), z);
        }
        NoteTaskQuickAffordanceConfig noteTaskQuickAffordanceConfig2 = this.this$0;
        if (z5) {
            EmergencyButtonController$$ExternalSyntheticOutline0.m("lockScreenState:isStylusEverUsed=", noteTaskQuickAffordanceConfig2.getClass().getSimpleName(), z2);
        }
        NoteTaskQuickAffordanceConfig noteTaskQuickAffordanceConfig3 = this.this$0;
        if (z5) {
            EmergencyButtonController$$ExternalSyntheticOutline0.m("lockScreenState:isConfigSelected=", noteTaskQuickAffordanceConfig3.getClass().getSimpleName(), z3);
        }
        NoteTaskQuickAffordanceConfig noteTaskQuickAffordanceConfig4 = this.this$0;
        if (z5) {
            EmergencyButtonController$$ExternalSyntheticOutline0.m("lockScreenState:isDefaultNotesAppSet=", noteTaskQuickAffordanceConfig4.getClass().getSimpleName(), z4);
        }
        boolean z6 = this.this$0.context.getResources().getBoolean(R.bool.custom_lockscreen_shortcuts_enabled);
        if (z6) {
            z2 = z3;
        }
        NoteTaskQuickAffordanceConfig noteTaskQuickAffordanceConfig5 = this.this$0;
        if (z5) {
            EmergencyButtonController$$ExternalSyntheticOutline0.m("lockScreenState:isCustomLockScreenShortcutEnabled=", noteTaskQuickAffordanceConfig5.getClass().getSimpleName(), z6);
        }
        NoteTaskQuickAffordanceConfig noteTaskQuickAffordanceConfig6 = this.this$0;
        if (z5) {
            EmergencyButtonController$$ExternalSyntheticOutline0.m("lockScreenState:isShortcutSelectedOrDefaultEnabled=", noteTaskQuickAffordanceConfig6.getClass().getSimpleName(), z2);
        }
        if (!this.this$0.isEnabled || !z || !z4 || !z2) {
            return KeyguardQuickAffordanceConfig.LockScreenState.Hidden.INSTANCE;
        }
        this.this$0.getClass();
        ContentDescription.Resource resource = new ContentDescription.Resource(R.string.note_task_button_label);
        this.this$0.getClass();
        return new KeyguardQuickAffordanceConfig.LockScreenState.Visible(new Icon.Resource(R.drawable.ic_note_task_shortcut_keyguard, resource), null, 2, null);
    }
}
