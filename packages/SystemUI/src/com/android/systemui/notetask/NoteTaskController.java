package com.android.systemui.notetask;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.app.role.RoleManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutManager;
import android.content.pm.UserInfo;
import android.os.Build;
import android.os.Process;
import android.os.UserHandle;
import android.os.UserManager;
import android.widget.Toast;
import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.log.DebugLogger;
import com.android.systemui.notetask.NoteTaskControllerUpdateService;
import com.android.systemui.notetask.shortcut.CreateNoteTaskShortcutActivity;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.util.settings.SecureSettings;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

public final class NoteTaskController {
    public static final String TAG;
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
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        String simpleName = Reflection.getOrCreateKotlinClass(NoteTaskController.class).getSimpleName();
        if (simpleName == null) {
            simpleName = "";
        }
        TAG = simpleName;
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

    /* JADX WARN: Can't wrap try/catch for region: R(9:0|1|(2:3|(6:5|6|7|(1:(1:(6:11|12|13|14|15|16)(2:19|20))(1:21))(2:64|(2:66|67)(2:68|(1:71)(1:70)))|22|(2:24|25)(2:26|(2:28|29)(2:30|(2:36|(2:38|39)(3:40|41|(7:43|(1:45)(1:49)|(2:47|48)|13|14|15|16)(5:50|(4:52|53|(2:55|(4:57|(1:59)|60|(1:62)))|63)|14|15|16)))(2:34|35)))))|73|6|7|(0)(0)|22|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x01af, code lost:
    
        r1 = com.android.systemui.log.DebugLogger.$r8$clinit;
        r1 = android.os.Build.IS_DEBUGGABLE;
        kotlin.jvm.internal.Reflection.getOrCreateKotlinClass(r0.getClass()).getSimpleName();
     */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0088  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0054  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x002b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$awaitShowNoteTaskAsUser(com.android.systemui.notetask.NoteTaskController r15, com.android.systemui.notetask.NoteTaskEntryPoint r16, android.os.UserHandle r17, kotlin.coroutines.Continuation r18) {
        /*
            Method dump skipped, instructions count: 458
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.notetask.NoteTaskController.access$awaitShowNoteTaskAsUser(com.android.systemui.notetask.NoteTaskController, com.android.systemui.notetask.NoteTaskEntryPoint, android.os.UserHandle, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final UserHandle getCurrentRunningUser$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        return Process.myUserHandle();
    }

    public final UserHandle getUserForHandlingNotesTaking(NoteTaskEntryPoint noteTaskEntryPoint) {
        Object obj;
        if (noteTaskEntryPoint == NoteTaskEntryPoint.TAIL_BUTTON) {
            SecureSettings secureSettings = this.secureSettings;
            int identifier = ((UserTrackerImpl) secureSettings.getUserTracker()).getUserHandle().getIdentifier();
            return UserHandle.of(secureSettings.getIntForUser("default_note_task_profile", identifier, identifier));
        }
        boolean isOrganizationOwnedDeviceWithManagedProfile = this.devicePolicyManager.isOrganizationOwnedDeviceWithManagedProfile();
        UserTracker userTracker = this.userTracker;
        if (!isOrganizationOwnedDeviceWithManagedProfile || noteTaskEntryPoint != NoteTaskEntryPoint.QUICK_AFFORDANCE) {
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
        UserHandle userHandle = userInfo != null ? userInfo.getUserHandle() : null;
        return userHandle == null ? ((UserTrackerImpl) userTracker).getUserHandle() : userHandle;
    }

    public final void launchUpdateNoteTaskAsUser(UserHandle userHandle) {
        BuildersKt.launch$default(this.applicationScope, this.bgCoroutineContext, null, new NoteTaskController$launchUpdateNoteTaskAsUser$$inlined$launch$1(ComponentActivity$1$$ExternalSyntheticOutline0.m(new StringBuilder(), TAG, "#launchUpdateNoteTaskAsUser"), null, this, userHandle), 2);
    }

    public final void setNoteTaskShortcutEnabled(boolean z, UserHandle userHandle) {
        if (!this.userManager.isUserUnlocked(userHandle)) {
            int i = DebugLogger.$r8$clinit;
            boolean z2 = Build.IS_DEBUGGABLE;
            Reflection.getOrCreateKotlinClass(NoteTaskController.class).getSimpleName();
        } else {
            this.context.createContextAsUser(userHandle, 0).getPackageManager().setComponentEnabledSetting(new ComponentName(this.context, (Class<?>) CreateNoteTaskShortcutActivity.class), z ? 1 : 2, 1);
            int i2 = DebugLogger.$r8$clinit;
            boolean z3 = Build.IS_DEBUGGABLE;
            Reflection.getOrCreateKotlinClass(NoteTaskController.class).getSimpleName();
        }
    }

    public final void showNoDefaultNotesAppToast() {
        Toast.makeText(this.context, R.string.set_default_notes_app_toast_content, 0).show();
    }

    public final void showNoteTaskAsUser(NoteTaskEntryPoint noteTaskEntryPoint, UserHandle userHandle) {
        if (this.isEnabled) {
            String m = ComponentActivity$1$$ExternalSyntheticOutline0.m(new StringBuilder(), TAG, "#showNoteTaskAsUser");
            BuildersKt.launch$default(this.applicationScope, EmptyCoroutineContext.INSTANCE, null, new NoteTaskController$showNoteTaskAsUser$$inlined$launch$default$1(m, null, this, noteTaskEntryPoint, userHandle), 2);
        }
    }

    public final void updateNoteTaskAsUser(UserHandle userHandle) {
        if (!this.userManager.isUserUnlocked(userHandle)) {
            int i = DebugLogger.$r8$clinit;
            boolean z = Build.IS_DEBUGGABLE;
            Reflection.getOrCreateKotlinClass(NoteTaskController.class).getSimpleName();
        } else {
            if (userHandle.equals(getCurrentRunningUser$frameworks__base__packages__SystemUI__android_common__SystemUI_core())) {
                launchUpdateNoteTaskAsUser(userHandle);
                return;
            }
            NoteTaskControllerUpdateService.Companion companion = NoteTaskControllerUpdateService.Companion;
            Context context = this.context;
            companion.getClass();
            try {
                this.context.startServiceAsUser(new Intent(context, (Class<?>) NoteTaskControllerUpdateService.class), userHandle);
            } catch (SecurityException unused) {
                int i2 = DebugLogger.$r8$clinit;
                boolean z2 = Build.IS_DEBUGGABLE;
                Reflection.getOrCreateKotlinClass(NoteTaskController.class).getSimpleName();
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
