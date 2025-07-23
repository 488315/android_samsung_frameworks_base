package com.android.wm.shell.desktopmode.persistence;

import android.content.Context;
import android.window.DesktopModeFlags;
import com.android.internal.protolog.ProtoLog;
import com.android.wm.shell.desktopmode.DesktopDisplayEventHandler$$ExternalSyntheticOutline0;
import com.android.wm.shell.desktopmode.DesktopUserRepositories;
import com.android.wm.shell.desktopmode.persistence.DesktopRepositoryInitializer;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.desktopmode.DesktopConfig;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.SpreadBuilder;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DesktopRepositoryInitializerImpl implements DesktopRepositoryInitializer {
    public final StateFlowImpl _isInitialized;
    public final Set addedDisplayIdsBeforeInitialized;
    public DesktopRepositoryInitializer.DeskActivationFactory deskActivationFactory;
    public DesktopRepositoryInitializer.DeskRecreationFactory deskRecreationFactory = new DefaultDeskRecreationFactory();
    public final DesktopConfig desktopConfig;
    public final StateFlowImpl isInitialized;
    public final CoroutineScope mainCoroutineScope;
    public final DesktopPersistentRepository persistentRepository;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class DefaultDeskActivationFactory implements DesktopRepositoryInitializer.DeskActivationFactory {
        @Override // com.android.wm.shell.desktopmode.persistence.DesktopRepositoryInitializer.DeskActivationFactory
        public final Object activeDesk(int i, Continuation continuation) {
            return Unit.INSTANCE;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class DefaultDeskRecreationFactory implements DesktopRepositoryInitializer.DeskRecreationFactory {
        @Override // com.android.wm.shell.desktopmode.persistence.DesktopRepositoryInitializer.DeskRecreationFactory
        public final Object recreateDesk(int i, int i2, int i3, Continuation continuation) {
            return new Integer(i3);
        }
    }

    static {
        new Companion(null);
    }

    public DesktopRepositoryInitializerImpl(Context context, DesktopPersistentRepository desktopPersistentRepository, CoroutineScope coroutineScope, DesktopConfig desktopConfig) {
        this.persistentRepository = desktopPersistentRepository;
        this.mainCoroutineScope = coroutineScope;
        this.desktopConfig = desktopConfig;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this._isInitialized = MutableStateFlow;
        this.isInitialized = MutableStateFlow;
        this.deskActivationFactory = new DefaultDeskActivationFactory();
        this.addedDisplayIdsBeforeInitialized = new LinkedHashSet();
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x00a7, code lost:
    
        if (r6 == false) goto L31;
     */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00ad  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:27:0x008f -> B:10:0x003c). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$getDesksToRestore(com.android.wm.shell.desktopmode.persistence.DesktopRepositoryInitializerImpl r9, com.android.wm.shell.desktopmode.persistence.DesktopRepositoryState r10, int r11, kotlin.coroutines.jvm.internal.ContinuationImpl r12) {
        /*
            r9.getClass()
            boolean r0 = r12 instanceof com.android.wm.shell.desktopmode.persistence.DesktopRepositoryInitializerImpl$getDesksToRestore$1
            if (r0 == 0) goto L16
            r0 = r12
            com.android.wm.shell.desktopmode.persistence.DesktopRepositoryInitializerImpl$getDesksToRestore$1 r0 = (com.android.wm.shell.desktopmode.persistence.DesktopRepositoryInitializerImpl$getDesksToRestore$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            com.android.wm.shell.desktopmode.persistence.DesktopRepositoryInitializerImpl$getDesksToRestore$1 r0 = new com.android.wm.shell.desktopmode.persistence.DesktopRepositoryInitializerImpl$getDesksToRestore$1
            r0.<init>(r9, r12)
        L1b:
            java.lang.Object r12 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L47
            if (r2 != r3) goto L3f
            int r9 = r0.I$1
            int r10 = r0.I$0
            java.lang.Object r11 = r0.L$2
            java.util.Iterator r11 = (java.util.Iterator) r11
            java.lang.Object r2 = r0.L$1
            java.util.Collection r2 = (java.util.Collection) r2
            java.lang.Object r4 = r0.L$0
            com.android.wm.shell.desktopmode.persistence.DesktopRepositoryInitializerImpl r4 = (com.android.wm.shell.desktopmode.persistence.DesktopRepositoryInitializerImpl) r4
            kotlin.ResultKt.throwOnFailure(r12)
            r8 = r0
            r0 = r9
            r9 = r4
        L3c:
            r4 = r2
            r2 = r8
            goto L93
        L3f:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L47:
            kotlin.ResultKt.throwOnFailure(r12)
            android.window.DesktopExperienceFlags r12 = android.window.DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND
            boolean r12 = r12.isTrue()
            r12 = r12 ^ r3
            java.util.Map r10 = r10.getDesktopMap()
            java.util.Set r10 = r10.keySet()
            java.lang.Iterable r10 = (java.lang.Iterable) r10
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            java.util.Iterator r10 = r10.iterator()
            r8 = r11
            r11 = r10
            r10 = r8
        L67:
            boolean r4 = r11.hasNext()
            if (r4 == 0) goto Lb4
            java.lang.Object r4 = r11.next()
            java.lang.Integer r4 = (java.lang.Integer) r4
            com.android.wm.shell.desktopmode.persistence.DesktopPersistentRepository r5 = r9.persistentRepository
            r4.getClass()
            int r4 = r4.intValue()
            r0.L$0 = r9
            r0.L$1 = r2
            r0.L$2 = r11
            r0.I$0 = r10
            r0.I$1 = r12
            r0.label = r3
            java.lang.Object r4 = r5.readDesktop(r10, r4, r0)
            if (r4 != r1) goto L8f
            return r1
        L8f:
            r8 = r0
            r0 = r12
            r12 = r4
            goto L3c
        L93:
            com.android.wm.shell.desktopmode.persistence.Desktop r12 = (com.android.wm.shell.desktopmode.persistence.Desktop) r12
            r5 = 0
            if (r12 == 0) goto Laa
            int r6 = r12.getDesktopId()
            int r7 = r12.getDisplayId()
            if (r6 != r7) goto La4
            r6 = r3
            goto La5
        La4:
            r6 = 0
        La5:
            if (r0 == 0) goto Lab
            if (r6 == 0) goto Laa
            goto Lab
        Laa:
            r12 = r5
        Lab:
            if (r12 == 0) goto Lb0
            r4.add(r12)
        Lb0:
            r12 = r0
            r0 = r2
            r2 = r4
            goto L67
        Lb4:
            java.util.List r2 = (java.util.List) r2
            java.lang.Iterable r2 = (java.lang.Iterable) r2
            java.util.Set r9 = kotlin.collections.CollectionsKt___CollectionsKt.toSet(r2)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.desktopmode.persistence.DesktopRepositoryInitializerImpl.access$getDesksToRestore(com.android.wm.shell.desktopmode.persistence.DesktopRepositoryInitializerImpl, com.android.wm.shell.desktopmode.persistence.DesktopRepositoryState, int, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public static final void access$logV(DesktopRepositoryInitializerImpl desktopRepositoryInitializerImpl, String str, Object... objArr) {
        desktopRepositoryInitializerImpl.getClass();
        ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
        String concat = "%s: ".concat(str);
        SpreadBuilder m = DesktopDisplayEventHandler$$ExternalSyntheticOutline0.m(2, "DesktopRepositoryInitializerImpl", objArr);
        ProtoLog.v(shellProtoLogGroup, concat, m.list.toArray(new Object[m.list.size()]));
    }

    public static final void access$logW(DesktopRepositoryInitializerImpl desktopRepositoryInitializerImpl, Object... objArr) {
        desktopRepositoryInitializerImpl.getClass();
        ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
        SpreadBuilder m = DesktopDisplayEventHandler$$ExternalSyntheticOutline0.m(2, "DesktopRepositoryInitializerImpl", objArr);
        ProtoLog.w(shellProtoLogGroup, "%s: Could not re-create desk=%d from display=%d in displayId=%d", m.list.toArray(new Object[m.list.size()]));
    }

    public final void initialize(DesktopUserRepositories desktopUserRepositories) {
        if (DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_PERSISTENCE.isTrue()) {
            BuildersKt.launch$default(this.mainCoroutineScope, null, null, new DesktopRepositoryInitializerImpl$initialize$1(this, desktopUserRepositories, null), 3);
        } else {
            this._isInitialized.updateState(null, Boolean.TRUE);
        }
    }
}
