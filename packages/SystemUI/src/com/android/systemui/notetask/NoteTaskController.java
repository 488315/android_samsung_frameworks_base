package com.android.systemui.notetask;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.app.role.RoleManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutManager;
import android.content.pm.UserInfo;
import android.os.Build;
import android.os.Process;
import android.os.UserHandle;
import android.os.UserManager;
import android.widget.Toast;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.R;
import com.android.systemui.log.DebugLogger;
import com.android.systemui.notetask.NoteTaskControllerUpdateService;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.util.settings.SecureSettings;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
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

    /* JADX WARN: Can't wrap try/catch for region: R(9:0|1|(2:3|(6:5|6|7|(1:(1:(6:11|12|13|14|15|16)(2:18|19))(1:20))(2:73|(2:75|76)(3:77|(1:79)|49))|21|(2:23|24)(2:25|(2:27|28)(2:29|(2:35|(2:37|38)(6:39|40|(3:69|43|(2:45|(1:47)(1:50))(2:51|(7:53|54|(2:56|(4:58|(1:60)|61|(4:63|14|15|16)))|64|14|15|16)(2:65|66)))|42|43|(0)(0)))(2:33|34)))))|81|6|7|(0)(0)|21|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0144, code lost:
    
        if (r1 == r3) goto L61;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x01cf, code lost:
    
        r1 = com.android.systemui.log.DebugLogger.INSTANCE;
        r1 = android.os.Build.IS_DEBUGGABLE;
        kotlin.jvm.internal.Reflection.getOrCreateKotlinClass(r0.getClass()).getSimpleName();
     */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0088  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0115 A[Catch: ActivityNotFoundException -> 0x01cf, TryCatch #0 {ActivityNotFoundException -> 0x01cf, blocks: (B:12:0x0034, B:13:0x0147, B:14:0x01bf, B:40:0x00e7, B:43:0x0111, B:45:0x0115, B:50:0x0142, B:51:0x0157, B:53:0x015b, B:56:0x0161, B:58:0x0177, B:60:0x0181, B:61:0x0185, B:63:0x018b, B:64:0x01ac, B:65:0x01c9, B:66:0x01ce, B:67:0x00fb, B:70:0x0101), top: B:7:0x002a }] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0157 A[Catch: ActivityNotFoundException -> 0x01cf, TryCatch #0 {ActivityNotFoundException -> 0x01cf, blocks: (B:12:0x0034, B:13:0x0147, B:14:0x01bf, B:40:0x00e7, B:43:0x0111, B:45:0x0115, B:50:0x0142, B:51:0x0157, B:53:0x015b, B:56:0x0161, B:58:0x0177, B:60:0x0181, B:61:0x0185, B:63:0x018b, B:64:0x01ac, B:65:0x01c9, B:66:0x01ce, B:67:0x00fb, B:70:0x0101), top: B:7:0x002a }] */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0055  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x002c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$awaitShowNoteTaskAsUser(com.android.systemui.notetask.NoteTaskController r16, com.android.systemui.notetask.NoteTaskEntryPoint r17, android.os.UserHandle r18, kotlin.coroutines.jvm.internal.ContinuationImpl r19) {
        /*
            Method dump skipped, instructions count: 490
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.notetask.NoteTaskController.access$awaitShowNoteTaskAsUser(com.android.systemui.notetask.NoteTaskController, com.android.systemui.notetask.NoteTaskEntryPoint, android.os.UserHandle, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public final UserHandle getCurrentRunningUser$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        return Process.myUserHandle();
    }

    public final UserHandle getUserForHandlingNotesTaking(NoteTaskEntryPoint noteTaskEntryPoint) {
        Object obj;
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
                obj = null;
                break;
            }
            obj = it.next();
            if (this.userManager.isManagedProfile(((UserInfo) obj).id)) {
                break;
            }
        }
        UserInfo userInfo = (UserInfo) obj;
        return (userInfo == null || (userHandle = userInfo.getUserHandle()) == null) ? ((UserTrackerImpl) userTracker).getUserHandle() : userHandle;
    }

    public final void launchUpdateNoteTaskAsUser(UserHandle userHandle) {
        CoroutineTracingKt.launchTraced$default(this.applicationScope, this.bgCoroutineContext, null, new NoteTaskController$launchUpdateNoteTaskAsUser$1(this, userHandle, null), 4);
    }

    public final void showNoDefaultNotesAppToast() {
        Toast.makeText(this.context, R.string.set_default_notes_app_toast_content, 0).show();
    }

    public final void showNoteTaskAsUser(NoteTaskEntryPoint noteTaskEntryPoint, UserHandle userHandle) {
        if (this.isEnabled) {
            CoroutineTracingKt.launchTraced$default(this.applicationScope, null, null, new NoteTaskController$showNoteTaskAsUser$1(this, noteTaskEntryPoint, userHandle, null), 6);
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
