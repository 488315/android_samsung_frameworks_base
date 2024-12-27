package com.android.systemui.notetask;

import android.app.role.RoleManager;
import android.os.Build;
import android.os.Trace;
import android.os.UserHandle;
import com.android.app.tracing.TraceProxy_platformKt;
import com.android.app.tracing.coroutines.TraceContextElementKt;
import com.android.app.tracing.coroutines.TraceData;
import com.android.systemui.log.DebugLogger;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class NoteTaskController$launchUpdateNoteTaskAsUser$$inlined$launch$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ String $spanName;
    final /* synthetic */ UserHandle $user$inlined;
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    boolean Z$0;
    int label;
    final /* synthetic */ NoteTaskController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NoteTaskController$launchUpdateNoteTaskAsUser$$inlined$launch$1(String str, Continuation continuation, NoteTaskController noteTaskController, UserHandle userHandle) {
        super(2, continuation);
        this.$spanName = str;
        this.this$0 = noteTaskController;
        this.$user$inlined = userHandle;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        NoteTaskController$launchUpdateNoteTaskAsUser$$inlined$launch$1 noteTaskController$launchUpdateNoteTaskAsUser$$inlined$launch$1 = new NoteTaskController$launchUpdateNoteTaskAsUser$$inlined$launch$1(this.$spanName, continuation, this.this$0, this.$user$inlined);
        noteTaskController$launchUpdateNoteTaskAsUser$$inlined$launch$1.L$0 = obj;
        return noteTaskController$launchUpdateNoteTaskAsUser$$inlined$launch$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((NoteTaskController$launchUpdateNoteTaskAsUser$$inlined$launch$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
        String str = this.$spanName;
        TraceData traceData = (TraceData) TraceContextElementKt.traceThreadLocal.get();
        boolean isEnabled = Trace.isEnabled();
        if (traceData == null && !isEnabled) {
            str = "<none>";
        }
        if (traceData != null) {
            traceData.beginSpan(str);
        }
        boolean z = false;
        int nextInt = isEnabled ? ThreadLocalRandom.current().nextInt() : 0;
        if (isEnabled) {
            TraceProxy_platformKt.asyncTraceForTrackBegin(nextInt, "Coroutines", str);
        }
        try {
            if (this.this$0.userManager.isUserUnlocked(this.$user$inlined)) {
                NoteTaskRoleManagerExt noteTaskRoleManagerExt = NoteTaskRoleManagerExt.INSTANCE;
                RoleManager roleManager = this.this$0.roleManager;
                UserHandle userHandle = this.$user$inlined;
                noteTaskRoleManagerExt.getClass();
                String str2 = (String) CollectionsKt___CollectionsKt.firstOrNull(roleManager.getRoleHoldersAsUser("android.app.role.NOTES", userHandle));
                if (this.this$0.isEnabled && str2 != null && str2.length() != 0) {
                    z = true;
                }
                this.this$0.setNoteTaskShortcutEnabled(z, this.$user$inlined);
                if (z) {
                    this.this$0.shortcutManager.enableShortcuts(Collections.singletonList("note_task_shortcut_id"));
                    NoteTaskController noteTaskController = this.this$0;
                    this.this$0.shortcutManager.updateShortcuts(Collections.singletonList(NoteTaskRoleManagerExt.createNoteShortcutInfoAsUser(noteTaskController.roleManager, noteTaskController.context, this.$user$inlined)));
                } else {
                    this.this$0.shortcutManager.disableShortcuts(Collections.singletonList("note_task_shortcut_id"));
                }
            } else {
                int i = DebugLogger.$r8$clinit;
                boolean z2 = Build.IS_DEBUGGABLE;
                Reflection.getOrCreateKotlinClass(coroutineScope.getClass()).getSimpleName();
            }
            Unit unit = Unit.INSTANCE;
            if (isEnabled) {
                TraceProxy_platformKt.asyncTraceForTrackEnd(nextInt, "Coroutines");
            }
            if (traceData != null) {
                traceData.endSpan();
            }
            return Unit.INSTANCE;
        } catch (Throwable th) {
            if (isEnabled) {
                TraceProxy_platformKt.asyncTraceForTrackEnd(nextInt, "Coroutines");
            }
            if (traceData != null) {
                traceData.endSpan();
            }
            throw th;
        }
    }
}
