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
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import android.widget.Toast;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.Bubbles;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.systemui.R;
import com.android.systemui.devicepolicy.DevicePolicyManagerExtKt;
import com.android.systemui.log.DebugLogger;
import com.android.systemui.notetask.NoteTaskLaunchMode;
import com.android.systemui.notetask.shortcut.CreateNoteTaskShortcutActivity;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shared.system.ActivityManagerKt;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NoteTaskController {
    public static final Companion Companion = new Companion(null);
    public static final List FORCE_WORK_NOTE_APPS_ENTRY_POINTS_ON_COPE_DEVICES;
    public static final String TAG;
    public final ActivityManager activityManager;
    public final Context context;
    public final DevicePolicyManager devicePolicyManager;
    public final NoteTaskEventLogger eventLogger;
    public final AtomicReference infoReference = new AtomicReference();
    public final boolean isEnabled;
    public final KeyguardManager keyguardManager;
    public final Optional optionalBubbles;
    public final NoteTaskInfoResolver resolver;
    public final RoleManager roleManager;
    public final ShortcutManager shortcutManager;
    public final UserManager userManager;
    public final UserTracker userTracker;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        String simpleName = Reflection.getOrCreateKotlinClass(NoteTaskController.class).getSimpleName();
        if (simpleName == null) {
            simpleName = "";
        }
        TAG = simpleName;
        FORCE_WORK_NOTE_APPS_ENTRY_POINTS_ON_COPE_DEVICES = CollectionsKt__CollectionsKt.listOf(NoteTaskEntryPoint.TAIL_BUTTON, NoteTaskEntryPoint.QUICK_AFFORDANCE);
    }

    public NoteTaskController(Context context, RoleManager roleManager, ShortcutManager shortcutManager, NoteTaskInfoResolver noteTaskInfoResolver, NoteTaskEventLogger noteTaskEventLogger, Optional<Bubbles> optional, UserManager userManager, KeyguardManager keyguardManager, ActivityManager activityManager, boolean z, DevicePolicyManager devicePolicyManager, UserTracker userTracker) {
        this.context = context;
        this.roleManager = roleManager;
        this.shortcutManager = shortcutManager;
        this.resolver = noteTaskInfoResolver;
        this.eventLogger = noteTaskEventLogger;
        this.optionalBubbles = optional;
        this.userManager = userManager;
        this.keyguardManager = keyguardManager;
        this.activityManager = activityManager;
        this.isEnabled = z;
        this.devicePolicyManager = devicePolicyManager;
        this.userTracker = userTracker;
    }

    public final UserHandle getUserForHandlingNotesTaking(NoteTaskEntryPoint noteTaskEntryPoint) {
        Object obj;
        boolean contains = FORCE_WORK_NOTE_APPS_ENTRY_POINTS_ON_COPE_DEVICES.contains(noteTaskEntryPoint);
        UserTracker userTracker = this.userTracker;
        if (!contains || !this.devicePolicyManager.isOrganizationOwnedDeviceWithManagedProfile()) {
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

    public final void setNoteTaskShortcutEnabled(boolean z, UserHandle userHandle) {
        if (!this.userManager.isUserUnlocked(userHandle)) {
            int i = DebugLogger.$r8$clinit;
            boolean z2 = Build.IS_DEBUGGABLE;
            Reflection.getOrCreateKotlinClass(NoteTaskController.class).getSimpleName();
            return;
        }
        Context context = this.context;
        ComponentName componentName = new ComponentName(context, (Class<?>) CreateNoteTaskShortcutActivity.class);
        int i2 = z ? 1 : 2;
        if (!Intrinsics.areEqual(userHandle, ((UserTrackerImpl) this.userTracker).getUserHandle())) {
            context = context.createContextAsUser(userHandle, 0);
        }
        context.getPackageManager().setComponentEnabledSetting(componentName, i2, 1);
        int i3 = DebugLogger.$r8$clinit;
        boolean z3 = Build.IS_DEBUGGABLE;
        Reflection.getOrCreateKotlinClass(NoteTaskController.class).getSimpleName();
    }

    public final void showNoDefaultNotesAppToast() {
        Toast.makeText(this.context, R.string.set_default_notes_app_toast_content, 0).show();
    }

    public final void showNoteTaskAsUser(NoteTaskEntryPoint noteTaskEntryPoint, final UserHandle userHandle) {
        Bubbles bubbles;
        if (this.isEnabled && (bubbles = (Bubbles) this.optionalBubbles.orElse(null)) != null && this.userManager.isUserUnlocked()) {
            boolean isKeyguardLocked = this.keyguardManager.isKeyguardLocked();
            if (isKeyguardLocked && DevicePolicyManagerExtKt.areKeyguardShortcutsDisabled$default(this.devicePolicyManager, userHandle.getIdentifier())) {
                int i = DebugLogger.$r8$clinit;
                boolean z = Build.IS_DEBUGGABLE;
                Reflection.getOrCreateKotlinClass(NoteTaskController.class).getSimpleName();
                return;
            }
            NoteTaskInfo resolveInfo = this.resolver.resolveInfo(noteTaskEntryPoint, isKeyguardLocked, userHandle);
            if (resolveInfo == null) {
                int i2 = DebugLogger.$r8$clinit;
                boolean z2 = Build.IS_DEBUGGABLE;
                Reflection.getOrCreateKotlinClass(NoteTaskController.class).getSimpleName();
                showNoDefaultNotesAppToast();
                return;
            }
            this.infoReference.set(resolveInfo);
            try {
                int i3 = DebugLogger.$r8$clinit;
                boolean z3 = Build.IS_DEBUGGABLE;
                Reflection.getOrCreateKotlinClass(NoteTaskController.class).getSimpleName();
                NoteTaskLaunchMode noteTaskLaunchMode = resolveInfo.launchMode;
                boolean z4 = noteTaskLaunchMode instanceof NoteTaskLaunchMode.AppBubble;
                Context context = this.context;
                if (z4) {
                    final Intent access$createNoteTaskIntent = NoteTaskControllerKt.access$createNoteTaskIntent(resolveInfo);
                    final Icon createWithResource = Icon.createWithResource(context, R.drawable.ic_note_task_shortcut_widget);
                    final BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) bubbles;
                    ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.bubbles.BubbleController$BubblesImpl$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            String str;
                            BubbleController.BubblesImpl bubblesImpl2 = BubbleController.BubblesImpl.this;
                            Intent intent = access$createNoteTaskIntent;
                            UserHandle userHandle2 = userHandle;
                            Icon icon = createWithResource;
                            BubbleController bubbleController = BubbleController.this;
                            bubbleController.getClass();
                            if (intent == null || intent.getPackage() == null) {
                                StringBuilder sb = new StringBuilder("App bubble failed to show, invalid intent: ");
                                sb.append(intent);
                                if (intent != null) {
                                    str = " with package: " + intent.getPackage();
                                } else {
                                    str = " ";
                                }
                                sb.append(str);
                                Log.w("Bubbles", sb.toString());
                                return;
                            }
                            String appBubbleKeyForApp = Bubble.getAppBubbleKeyForApp(intent.getPackage(), userHandle2);
                            if (BubbleController.isResizableActivity(intent, BubbleController.getPackageManagerForUser(userHandle2.getIdentifier(), bubbleController.mContext), appBubbleKeyForApp)) {
                                BubbleData bubbleData = bubbleController.mBubbleData;
                                Bubble bubbleInStackWithKey = bubbleData.getBubbleInStackWithKey(appBubbleKeyForApp);
                                if (bubbleInStackWithKey == null) {
                                    Bubble createAppBubble = Bubble.createAppBubble(intent, userHandle2, icon, bubbleController.mMainExecutor);
                                    createAppBubble.setShouldAutoExpand(true);
                                    bubbleController.inflateAndAdd(createAppBubble, true, false);
                                    return;
                                }
                                BubbleViewProvider bubbleViewProvider = bubbleData.mSelectedBubble;
                                if (!bubbleController.isStackExpanded()) {
                                    bubbleData.setSelectedBubble(bubbleInStackWithKey);
                                    bubbleData.setExpanded(true);
                                } else if (bubbleViewProvider == null || !appBubbleKeyForApp.equals(bubbleViewProvider.getKey())) {
                                    bubbleData.setSelectedBubble(bubbleInStackWithKey);
                                } else {
                                    bubbleController.collapseStack();
                                }
                            }
                        }
                    });
                    boolean z5 = Build.IS_DEBUGGABLE;
                    Reflection.getOrCreateKotlinClass(NoteTaskController.class).getSimpleName();
                } else if (noteTaskLaunchMode instanceof NoteTaskLaunchMode.Activity) {
                    boolean z6 = resolveInfo.isKeyguardLocked;
                    NoteTaskEventLogger noteTaskEventLogger = this.eventLogger;
                    if (z6) {
                        ActivityManagerKt activityManagerKt = ActivityManagerKt.INSTANCE;
                        ActivityManager activityManager = this.activityManager;
                        String str = resolveInfo.packageName;
                        activityManagerKt.getClass();
                        boolean z7 = true;
                        List<ActivityManager.RunningTaskInfo> runningTasks = activityManager.getRunningTasks(1);
                        if (!(!runningTasks.isEmpty()) || !Intrinsics.areEqual(str, runningTasks.get(0).topActivity.getPackageName())) {
                            z7 = false;
                        }
                        if (z7) {
                            Intent intent = new Intent("android.intent.action.MAIN");
                            intent.addCategory("android.intent.category.HOME");
                            intent.setFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                            context.startActivityAsUser(intent, userHandle);
                            noteTaskEventLogger.logNoteTaskClosed(resolveInfo);
                            boolean z8 = Build.IS_DEBUGGABLE;
                            Reflection.getOrCreateKotlinClass(NoteTaskController.class).getSimpleName();
                        }
                    }
                    context.startActivityAsUser(NoteTaskControllerKt.access$createNoteTaskIntent(resolveInfo), userHandle);
                    noteTaskEventLogger.logNoteTaskOpened(resolveInfo);
                    boolean z9 = Build.IS_DEBUGGABLE;
                    Reflection.getOrCreateKotlinClass(NoteTaskController.class).getSimpleName();
                }
                boolean z10 = Build.IS_DEBUGGABLE;
                Reflection.getOrCreateKotlinClass(NoteTaskController.class).getSimpleName();
            } catch (ActivityNotFoundException unused) {
                int i4 = DebugLogger.$r8$clinit;
                boolean z11 = Build.IS_DEBUGGABLE;
                Reflection.getOrCreateKotlinClass(NoteTaskController.class).getSimpleName();
            }
            boolean z12 = Build.IS_DEBUGGABLE;
            Reflection.getOrCreateKotlinClass(NoteTaskController.class).getSimpleName();
        }
    }

    public static /* synthetic */ void getInfoReference$annotations() {
    }
}
