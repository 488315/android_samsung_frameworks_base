package com.android.systemui.notetask;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.app.role.RoleManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutManager;
import android.content.pm.UserInfo;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Process;
import android.os.UserHandle;
import android.os.UserManager;
import android.widget.Toast;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.R;
import com.android.systemui.devicepolicy.DevicePolicyManagerExtKt;
import com.android.systemui.log.DebugLogger;
import com.android.systemui.notetask.NoteTaskControllerUpdateService;
import com.android.systemui.notetask.NoteTaskLaunchMode;
import com.android.systemui.notetask.shortcut.CreateNoteTaskShortcutActivity;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shared.system.ActivityManagerKt;
import com.android.systemui.util.settings.SecureSettings;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
public final class NoteTaskController {
    public static final Companion Companion = new Companion(null);
    public static final String TAG = null;
    public final ActivityManager activityManager;
    public final CoroutineScope applicationScope;
    public final CoroutineContext bgCoroutineContext;
    public final Context context;
    public final DevicePolicyManager devicePolicyManager;
    public final NoteTaskEventLogger eventLogger;
    public final AtomicReference infoReference = new AtomicReference();
    public final boolean isEnabled;
    public final KeyguardManager keyguardManager;
    public final NoteTaskBubblesController noteTaskBubblesController;
    public final NoteTaskInfoResolver resolver;
    public final RoleManager roleManager;
    public final SecureSettings secureSettings;
    public final ShortcutManager shortcutManager;
    public final UserManager userManager;
    public final UserTracker userTracker;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.notetask.NoteTaskController$launchUpdateNoteTaskAsUser$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ UserHandle $user;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(UserHandle userHandle, Continuation continuation) {
            super(2, continuation);
            this.$user = userHandle;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = NoteTaskController.this.new AnonymousClass1(this.$user, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            if (!NoteTaskController.this.userManager.isUserUnlocked(this.$user)) {
                DebugLogger debugLogger = DebugLogger.INSTANCE;
                boolean z = Build.IS_DEBUGGABLE;
                Reflection.getOrCreateKotlinClass(coroutineScope.getClass()).getSimpleName();
                return Unit.INSTANCE;
            }
            NoteTaskRoleManagerExt noteTaskRoleManagerExt = NoteTaskRoleManagerExt.INSTANCE;
            RoleManager roleManager = NoteTaskController.this.roleManager;
            UserHandle userHandle = this.$user;
            noteTaskRoleManagerExt.getClass();
            String str = (String) CollectionsKt___CollectionsKt.firstOrNull(roleManager.getRoleHoldersAsUser("android.app.role.NOTES", userHandle));
            boolean z2 = (!NoteTaskController.this.isEnabled || str == null || str.length() == 0) ? false : true;
            NoteTaskController noteTaskController = NoteTaskController.this;
            UserHandle userHandle2 = this.$user;
            if (noteTaskController.userManager.isUserUnlocked(userHandle2)) {
                noteTaskController.context.createContextAsUser(userHandle2, 0).getPackageManager().setComponentEnabledSetting(new ComponentName(noteTaskController.context, (Class<?>) CreateNoteTaskShortcutActivity.class), z2 ? 1 : 2, 1);
                DebugLogger debugLogger2 = DebugLogger.INSTANCE;
                boolean z3 = Build.IS_DEBUGGABLE;
                Reflection.getOrCreateKotlinClass(NoteTaskController.class).getSimpleName();
            } else {
                DebugLogger debugLogger3 = DebugLogger.INSTANCE;
                boolean z4 = Build.IS_DEBUGGABLE;
                Reflection.getOrCreateKotlinClass(NoteTaskController.class).getSimpleName();
            }
            if (z2) {
                NoteTaskController.this.shortcutManager.enableShortcuts(Collections.singletonList("note_task_shortcut_id"));
                NoteTaskController noteTaskController2 = NoteTaskController.this;
                NoteTaskController.this.shortcutManager.updateShortcuts(Collections.singletonList(NoteTaskRoleManagerExt.createNoteShortcutInfoAsUser(noteTaskController2.roleManager, noteTaskController2.context, this.$user)));
            } else {
                NoteTaskController.this.shortcutManager.disableShortcuts(Collections.singletonList("note_task_shortcut_id"));
            }
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.notetask.NoteTaskController$showNoteTaskAsUser$1, reason: invalid class name and case insensitive filesystem */
    final class C09701 extends SuspendLambda implements Function2 {
        final /* synthetic */ NoteTaskEntryPoint $entryPoint;
        final /* synthetic */ UserHandle $user;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09701(NoteTaskEntryPoint noteTaskEntryPoint, UserHandle userHandle, Continuation continuation) {
            super(2, continuation);
            this.$entryPoint = noteTaskEntryPoint;
            this.$user = userHandle;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return NoteTaskController.this.new C09701(this.$entryPoint, this.$user, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C09701) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                NoteTaskController noteTaskController = NoteTaskController.this;
                NoteTaskEntryPoint noteTaskEntryPoint = this.$entryPoint;
                UserHandle userHandle = this.$user;
                this.label = 1;
                if (NoteTaskController.access$awaitShowNoteTaskAsUser(noteTaskController, noteTaskEntryPoint, userHandle, this) == coroutineSingletons) {
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

    static {
        Reflection.getOrCreateKotlinClass(NoteTaskController.class).getSimpleName();
    }

    public NoteTaskController(Context context, RoleManager roleManager, ShortcutManager shortcutManager, NoteTaskInfoResolver noteTaskInfoResolver, NoteTaskEventLogger noteTaskEventLogger, NoteTaskBubblesController noteTaskBubblesController, UserManager userManager, KeyguardManager keyguardManager, ActivityManager activityManager, boolean z, DevicePolicyManager devicePolicyManager, UserTracker userTracker, SecureSettings secureSettings, CoroutineScope coroutineScope, CoroutineContext coroutineContext) {
        this.context = context;
        this.roleManager = roleManager;
        this.shortcutManager = shortcutManager;
        this.resolver = noteTaskInfoResolver;
        this.eventLogger = noteTaskEventLogger;
        this.noteTaskBubblesController = noteTaskBubblesController;
        this.userManager = userManager;
        this.keyguardManager = keyguardManager;
        this.activityManager = activityManager;
        this.isEnabled = z;
        this.devicePolicyManager = devicePolicyManager;
        this.userTracker = userTracker;
        this.secureSettings = secureSettings;
        this.applicationScope = coroutineScope;
        this.bgCoroutineContext = coroutineContext;
    }

    /* JADX WARN: Code restructure failed: missing block: B:60:0x0144, code lost:
    
        if (r1 == r3) goto L61;
     */
    /* JADX WARN: Removed duplicated region for block: B:76:0x01ac A[Catch: ActivityNotFoundException -> 0x01cf, TryCatch #0 {ActivityNotFoundException -> 0x01cf, blocks: (B:13:0x0034, B:62:0x0147, B:77:0x01bf, B:45:0x00e7, B:54:0x0111, B:56:0x0115, B:59:0x0142, B:63:0x0157, B:65:0x015b, B:68:0x0161, B:70:0x0177, B:72:0x0181, B:73:0x0185, B:75:0x018b, B:76:0x01ac, B:78:0x01c9, B:79:0x01ce, B:48:0x00fb, B:51:0x0101), top: B:83:0x002a }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$awaitShowNoteTaskAsUser(NoteTaskController noteTaskController, NoteTaskEntryPoint noteTaskEntryPoint, UserHandle userHandle, ContinuationImpl continuationImpl) throws Throwable {
        NoteTaskController$awaitShowNoteTaskAsUser$1 noteTaskController$awaitShowNoteTaskAsUser$1;
        NoteTaskEntryPoint noteTaskEntryPoint2;
        Object objWithContext;
        UserHandle userHandle2;
        NoteTaskController noteTaskController2 = noteTaskController;
        noteTaskController2.getClass();
        if (continuationImpl instanceof NoteTaskController$awaitShowNoteTaskAsUser$1) {
            noteTaskController$awaitShowNoteTaskAsUser$1 = (NoteTaskController$awaitShowNoteTaskAsUser$1) continuationImpl;
            int i = noteTaskController$awaitShowNoteTaskAsUser$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                noteTaskController$awaitShowNoteTaskAsUser$1.label = i - Integer.MIN_VALUE;
            } else {
                noteTaskController$awaitShowNoteTaskAsUser$1 = new NoteTaskController$awaitShowNoteTaskAsUser$1(noteTaskController2, continuationImpl);
            }
        }
        Object obj = noteTaskController$awaitShowNoteTaskAsUser$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = noteTaskController$awaitShowNoteTaskAsUser$1.label;
        try {
        } catch (ActivityNotFoundException unused) {
            DebugLogger debugLogger = DebugLogger.INSTANCE;
            boolean z = Build.IS_DEBUGGABLE;
            Reflection.getOrCreateKotlinClass(noteTaskController2.getClass()).getSimpleName();
        }
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            if (!noteTaskController2.isEnabled) {
                return Unit.INSTANCE;
            }
            noteTaskController$awaitShowNoteTaskAsUser$1.L$0 = noteTaskController2;
            noteTaskEntryPoint2 = noteTaskEntryPoint;
            noteTaskController$awaitShowNoteTaskAsUser$1.L$1 = noteTaskEntryPoint2;
            noteTaskController$awaitShowNoteTaskAsUser$1.L$2 = userHandle;
            noteTaskController$awaitShowNoteTaskAsUser$1.label = 1;
            NoteTaskBubblesController noteTaskBubblesController = noteTaskController2.noteTaskBubblesController;
            noteTaskBubblesController.getClass();
            objWithContext = BuildersKt.withContext(noteTaskBubblesController.bgDispatcher, new NoteTaskBubblesController$areBubblesAvailable$2(noteTaskBubblesController, null), noteTaskController$awaitShowNoteTaskAsUser$1);
            if (objWithContext != coroutineSingletons) {
                userHandle2 = userHandle;
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            noteTaskController2 = (NoteTaskController) noteTaskController$awaitShowNoteTaskAsUser$1.L$0;
            ResultKt.throwOnFailure(obj);
            DebugLogger debugLogger2 = DebugLogger.INSTANCE;
            boolean z2 = Build.IS_DEBUGGABLE;
            Reflection.getOrCreateKotlinClass(noteTaskController2.getClass()).getSimpleName();
            boolean z3 = Build.IS_DEBUGGABLE;
            Reflection.getOrCreateKotlinClass(NoteTaskController.class).getSimpleName();
            boolean z4 = Build.IS_DEBUGGABLE;
            Reflection.getOrCreateKotlinClass(NoteTaskController.class).getSimpleName();
            return Unit.INSTANCE;
        }
        UserHandle userHandle3 = (UserHandle) noteTaskController$awaitShowNoteTaskAsUser$1.L$2;
        NoteTaskEntryPoint noteTaskEntryPoint3 = (NoteTaskEntryPoint) noteTaskController$awaitShowNoteTaskAsUser$1.L$1;
        NoteTaskController noteTaskController3 = (NoteTaskController) noteTaskController$awaitShowNoteTaskAsUser$1.L$0;
        ResultKt.throwOnFailure(obj);
        userHandle2 = userHandle3;
        noteTaskController2 = noteTaskController3;
        objWithContext = obj;
        noteTaskEntryPoint2 = noteTaskEntryPoint3;
        if (!((Boolean) objWithContext).booleanValue()) {
            DebugLogger debugLogger3 = DebugLogger.INSTANCE;
            boolean z5 = Build.IS_DEBUGGABLE;
            Reflection.getOrCreateKotlinClass(noteTaskController2.getClass()).getSimpleName();
            return Unit.INSTANCE;
        }
        if (!noteTaskController2.userManager.isUserUnlocked()) {
            return Unit.INSTANCE;
        }
        boolean zIsKeyguardLocked = noteTaskController2.keyguardManager.isKeyguardLocked();
        if (zIsKeyguardLocked && DevicePolicyManagerExtKt.areKeyguardShortcutsDisabled$default(noteTaskController2.devicePolicyManager, userHandle2.getIdentifier())) {
            DebugLogger debugLogger4 = DebugLogger.INSTANCE;
            boolean z6 = Build.IS_DEBUGGABLE;
            Reflection.getOrCreateKotlinClass(NoteTaskController.class).getSimpleName();
            return Unit.INSTANCE;
        }
        NoteTaskInfo noteTaskInfoResolveInfo = noteTaskController2.resolver.resolveInfo(noteTaskEntryPoint2, zIsKeyguardLocked, userHandle2);
        if (noteTaskInfoResolveInfo == null) {
            DebugLogger debugLogger5 = DebugLogger.INSTANCE;
            boolean z7 = Build.IS_DEBUGGABLE;
            Reflection.getOrCreateKotlinClass(NoteTaskController.class).getSimpleName();
            noteTaskController2.showNoDefaultNotesAppToast();
            return Unit.INSTANCE;
        }
        NoteTaskLaunchMode noteTaskLaunchMode = noteTaskInfoResolveInfo.launchMode;
        noteTaskController2.infoReference.set(noteTaskInfoResolveInfo);
        DebugLogger debugLogger6 = DebugLogger.INSTANCE;
        boolean z8 = Build.IS_DEBUGGABLE;
        Reflection.getOrCreateKotlinClass(NoteTaskController.class).getSimpleName();
        NoteTaskEntryPoint noteTaskEntryPoint4 = noteTaskInfoResolveInfo.entryPoint;
        boolean z9 = noteTaskEntryPoint4 == NoteTaskEntryPoint.TAIL_BUTTON || (noteTaskEntryPoint4 != NoteTaskEntryPoint.KEYBOARD_SHORTCUT && noteTaskController2.context.getResources().getInteger(R.integer.config_preferredNotesMode) == 1);
        if (!(noteTaskLaunchMode instanceof NoteTaskLaunchMode.AppBubble)) {
            if (!(noteTaskLaunchMode instanceof NoteTaskLaunchMode.Activity)) {
                throw new NoWhenBranchMatchedException();
            }
            boolean z10 = noteTaskInfoResolveInfo.isKeyguardLocked;
            NoteTaskEventLogger noteTaskEventLogger = noteTaskController2.eventLogger;
            if (z10) {
                ActivityManagerKt activityManagerKt = ActivityManagerKt.INSTANCE;
                ActivityManager activityManager = noteTaskController2.activityManager;
                String str = noteTaskInfoResolveInfo.packageName;
                activityManagerKt.getClass();
                List<ActivityManager.RunningTaskInfo> runningTasks = activityManager.getRunningTasks(1);
                if (runningTasks.isEmpty()) {
                    noteTaskController2.context.startActivityAsUser(NoteTaskControllerKt.access$createNoteTaskIntent(noteTaskInfoResolveInfo, z9), userHandle2);
                    noteTaskEventLogger.logNoteTaskOpened(noteTaskInfoResolveInfo);
                    Reflection.getOrCreateKotlinClass(NoteTaskController.class).getSimpleName();
                    boolean z32 = Build.IS_DEBUGGABLE;
                    Reflection.getOrCreateKotlinClass(NoteTaskController.class).getSimpleName();
                } else {
                    ComponentName componentName = runningTasks.get(0).topActivity;
                    if (Intrinsics.areEqual(str, componentName != null ? componentName.getPackageName() : null)) {
                        Intent intent = new Intent("android.intent.action.MAIN");
                        intent.addCategory("android.intent.category.HOME");
                        intent.setFlags(268435456);
                        noteTaskController2.context.startActivityAsUser(intent, userHandle2);
                        noteTaskEventLogger.logNoteTaskClosed(noteTaskInfoResolveInfo);
                        Reflection.getOrCreateKotlinClass(NoteTaskController.class).getSimpleName();
                    }
                    boolean z322 = Build.IS_DEBUGGABLE;
                    Reflection.getOrCreateKotlinClass(NoteTaskController.class).getSimpleName();
                }
            }
            boolean z42 = Build.IS_DEBUGGABLE;
            Reflection.getOrCreateKotlinClass(NoteTaskController.class).getSimpleName();
            return Unit.INSTANCE;
        }
        Intent intentAccess$createNoteTaskIntent = NoteTaskControllerKt.access$createNoteTaskIntent(noteTaskInfoResolveInfo, z9);
        Icon iconCreateWithResource = Icon.createWithResource(noteTaskController2.context, R.drawable.ic_note_task_shortcut_widget);
        NoteTaskBubblesController noteTaskBubblesController2 = noteTaskController2.noteTaskBubblesController;
        NoteTaskBubbleExpandBehavior noteTaskBubbleExpandBehavior = ((NoteTaskLaunchMode.AppBubble) noteTaskLaunchMode).bubbleExpandBehavior;
        noteTaskController$awaitShowNoteTaskAsUser$1.L$0 = noteTaskController2;
        noteTaskController$awaitShowNoteTaskAsUser$1.L$1 = null;
        noteTaskController$awaitShowNoteTaskAsUser$1.L$2 = null;
        noteTaskController$awaitShowNoteTaskAsUser$1.label = 2;
        noteTaskBubblesController2.getClass();
        Object objWithContext2 = BuildersKt.withContext(noteTaskBubblesController2.bgDispatcher, new NoteTaskBubblesController$showOrHideNoteBubble$2(noteTaskBubblesController2, intentAccess$createNoteTaskIntent, userHandle2, iconCreateWithResource, noteTaskBubbleExpandBehavior, null), noteTaskController$awaitShowNoteTaskAsUser$1);
        if (objWithContext2 != coroutineSingletons) {
            objWithContext2 = Unit.INSTANCE;
        }
    }

    public final UserHandle getCurrentRunningUser$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        return Process.myUserHandle();
    }

    public final UserHandle getUserForHandlingNotesTaking(NoteTaskEntryPoint noteTaskEntryPoint) {
        Object next;
        UserHandle userHandle;
        NoteTaskEntryPoint noteTaskEntryPoint2 = NoteTaskEntryPoint.TAIL_BUTTON;
        UserTracker userTracker = this.userTracker;
        if (noteTaskEntryPoint == noteTaskEntryPoint2) {
            int identifier = ((UserTrackerImpl) userTracker).getUserHandle().getIdentifier();
            return UserHandle.of(this.secureSettings.getIntForUser("default_note_task_profile", identifier, identifier));
        }
        if (!this.devicePolicyManager.isOrganizationOwnedDeviceWithManagedProfile() || noteTaskEntryPoint != NoteTaskEntryPoint.QUICK_AFFORDANCE) {
            return ((UserTrackerImpl) userTracker).getUserHandle();
        }
        Iterator it = ((UserTrackerImpl) userTracker).getUserProfiles().iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            if (this.userManager.isManagedProfile(((UserInfo) next).id)) {
                break;
            }
        }
        UserInfo userInfo = (UserInfo) next;
        return (userInfo == null || (userHandle = userInfo.getUserHandle()) == null) ? ((UserTrackerImpl) userTracker).getUserHandle() : userHandle;
    }

    public final void launchUpdateNoteTaskAsUser(UserHandle userHandle) {
        CoroutineTracingKt.launchTraced$default(this.applicationScope, this.bgCoroutineContext, null, new AnonymousClass1(userHandle, null), 4);
    }

    public final void showNoDefaultNotesAppToast() {
        Toast.makeText(this.context, R.string.set_default_notes_app_toast_content, 0).show();
    }

    public final void showNoteTaskAsUser(NoteTaskEntryPoint noteTaskEntryPoint, UserHandle userHandle) {
        if (this.isEnabled) {
            CoroutineTracingKt.launchTraced$default(this.applicationScope, null, null, new C09701(noteTaskEntryPoint, userHandle, null), 6);
        }
    }

    public final void updateNoteTaskAsUser(UserHandle userHandle) {
        if (!this.userManager.isUserUnlocked(userHandle)) {
            DebugLogger debugLogger = DebugLogger.INSTANCE;
            boolean z = Build.IS_DEBUGGABLE;
            Reflection.getOrCreateKotlinClass(NoteTaskController.class).getSimpleName();
        } else {
            if (Intrinsics.areEqual(userHandle, getCurrentRunningUser$frameworks__base__packages__SystemUI__android_common__SystemUI_core())) {
                launchUpdateNoteTaskAsUser(userHandle);
                return;
            }
            NoteTaskControllerUpdateService.Companion companion = NoteTaskControllerUpdateService.Companion;
            Context context = this.context;
            companion.getClass();
            try {
                this.context.startServiceAsUser(new Intent(context, (Class<?>) NoteTaskControllerUpdateService.class), userHandle);
            } catch (SecurityException unused) {
                DebugLogger debugLogger2 = DebugLogger.INSTANCE;
                boolean z2 = Build.IS_DEBUGGABLE;
                Reflection.getOrCreateKotlinClass(NoteTaskController.class).getSimpleName();
                Unit unit = Unit.INSTANCE;
            }
        }
    }

    public final void updateNoteTaskForCurrentUserAndManagedProfiles() {
        UserTrackerImpl userTrackerImpl = (UserTrackerImpl) this.userTracker;
        updateNoteTaskAsUser(userTrackerImpl.getUserHandle());
        for (UserInfo userInfo : userTrackerImpl.getUserProfiles()) {
            if (this.userManager.isManagedProfile(userInfo.id)) {
                updateNoteTaskAsUser(userInfo.getUserHandle());
            }
        }
    }

    public static /* synthetic */ void getInfoReference$annotations() {
    }
}
