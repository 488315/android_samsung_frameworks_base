package com.android.server.wm;

import android.app.ActivityOptions;
import android.app.BackgroundStartPrivileges;
import android.app.IApplicationThread;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.hardware.display.DisplayManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Trace;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Slog;
import android.util.SparseArray;
import android.view.RemoteAnimationAdapter;
import android.window.RemoteTransition;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.DualAppManagerService;
import com.android.server.am.PendingIntentRecord;
import com.android.server.wm.ActivityMetricsLogger;
import com.android.server.wm.ActivityStarter;
import com.android.server.wm.ActivityTaskManagerService;
import com.android.server.uri.NeededUriGrants;
import com.android.server.uri.UriGrantsManagerInternal;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.List;
import java.util.function.IntFunction;

/* loaded from: classes3.dex */
public class ActivityStartController {
    public final BackgroundActivityStartController mBalController;
    public boolean mCheckedForSetup;
    public final ActivityStarter.Factory mFactory;
    public final Handler mHandler;
    public boolean mInExecution;
    public ActivityRecord mLastHomeActivityStartRecord;
    public int mLastHomeActivityStartResult;
    public ActivityStarter mLastStarter;
    public final PendingRemoteAnimationRegistry mPendingRemoteAnimationRegistry;
    public final ActivityTaskManagerService mService;
    public final ActivityTaskSupervisor mSupervisor;
    public ActivityRecord[] tmpOutRecord;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public ActivityStartController(ActivityTaskManagerService activityTaskManagerService) {
        this(activityTaskManagerService, r0, new ActivityStarter.DefaultFactory(activityTaskManagerService, r0, new ActivityStartInterceptor(activityTaskManagerService, r0)));
        ActivityTaskSupervisor activityTaskSupervisor = activityTaskManagerService.mTaskSupervisor;
    }

    public ActivityStartController(ActivityTaskManagerService activityTaskManagerService, ActivityTaskSupervisor activityTaskSupervisor, ActivityStarter.Factory factory) {
        this.tmpOutRecord = new ActivityRecord[1];
        this.mCheckedForSetup = false;
        this.mInExecution = false;
        this.mService = activityTaskManagerService;
        this.mSupervisor = activityTaskSupervisor;
        this.mHandler = new Handler(activityTaskManagerService.f1734mH.getLooper(), null, true);
        this.mFactory = factory;
        factory.setController(this);
        this.mPendingRemoteAnimationRegistry = new PendingRemoteAnimationRegistry(activityTaskManagerService.mGlobalLock, activityTaskManagerService.f1734mH);
        this.mBalController = new BackgroundActivityStartController(activityTaskManagerService, activityTaskSupervisor);
    }

    public ActivityStarter obtainStarter(Intent intent, String str) {
        return this.mFactory.obtain().setIntent(intent).setReason(str);
    }

    public void onExecutionStarted() {
        this.mInExecution = true;
    }

    public boolean isInExecution() {
        return this.mInExecution;
    }

    public void onExecutionComplete(ActivityStarter activityStarter) {
        this.mInExecution = false;
        if (this.mLastStarter == null) {
            this.mLastStarter = this.mFactory.obtain();
        }
        this.mLastStarter.set(activityStarter);
        this.mFactory.recycle(activityStarter);
    }

    public void postStartActivityProcessingForLastStarter(ActivityRecord activityRecord, int i, Task task) {
        ActivityStarter activityStarter = this.mLastStarter;
        if (activityStarter == null) {
            return;
        }
        activityStarter.postStartActivityProcessing(activityRecord, i, task);
    }

    public void startHomeActivity(Intent intent, ActivityInfo activityInfo, String str, TaskDisplayArea taskDisplayArea) {
        ActivityOptions makeBasic = ActivityOptions.makeBasic();
        makeBasic.setLaunchWindowingMode(1);
        if (!ActivityRecord.isResolverActivity(activityInfo.name)) {
            makeBasic.setLaunchActivityType(2);
        }
        makeBasic.setLaunchDisplayId(taskDisplayArea.getDisplayId());
        makeBasic.setLaunchTaskDisplayArea(taskDisplayArea.mRemoteToken.toWindowContainerToken());
        this.mSupervisor.beginDeferResume();
        try {
            Task orCreateRootHomeTask = taskDisplayArea.getOrCreateRootHomeTask(true);
            this.mSupervisor.endDeferResume();
            this.mLastHomeActivityStartResult = obtainStarter(intent, "startHomeActivity: " + str).setOutActivity(this.tmpOutRecord).setCallingUid(0).setActivityInfo(activityInfo).setActivityOptions(makeBasic.toBundle()).execute();
            this.mLastHomeActivityStartRecord = this.tmpOutRecord[0];
            if (orCreateRootHomeTask.mInResumeTopActivity) {
                this.mSupervisor.scheduleResumeTopActivities();
            }
        } catch (Throwable th) {
            this.mSupervisor.endDeferResume();
            throw th;
        }
    }

    public void startSetupActivity() {
        Bundle bundle;
        if (this.mCheckedForSetup) {
            return;
        }
        ContentResolver contentResolver = this.mService.mContext.getContentResolver();
        if (this.mService.mFactoryTest == 1 || Settings.Global.getInt(contentResolver, "device_provisioned", 0) == 0) {
            return;
        }
        this.mCheckedForSetup = true;
        Intent intent = new Intent("android.intent.action.UPGRADE_SETUP");
        List<ResolveInfo> queryIntentActivities = this.mService.mContext.getPackageManager().queryIntentActivities(intent, 1049728);
        if (queryIntentActivities.isEmpty()) {
            return;
        }
        ResolveInfo resolveInfo = queryIntentActivities.get(0);
        Bundle bundle2 = resolveInfo.activityInfo.metaData;
        String string = bundle2 != null ? bundle2.getString("android.SETUP_VERSION") : null;
        if (string == null && (bundle = resolveInfo.activityInfo.applicationInfo.metaData) != null) {
            string = bundle.getString("android.SETUP_VERSION");
        }
        String stringForUser = Settings.Secure.getStringForUser(contentResolver, "last_setup_shown", contentResolver.getUserId());
        if (string == null || string.equals(stringForUser)) {
            return;
        }
        intent.setFlags(268435456);
        ActivityInfo activityInfo = resolveInfo.activityInfo;
        intent.setComponent(new ComponentName(activityInfo.packageName, activityInfo.name));
        obtainStarter(intent, "startSetupActivity").setCallingUid(0).setActivityInfo(resolveInfo.activityInfo).execute();
    }

    public int checkTargetUser(int i, boolean z, int i2, int i3, String str) {
        if (z) {
            return this.mService.handleIncomingUser(i2, i3, i, str);
        }
        this.mService.mAmInternal.ensureNotSpecialUser(i);
        return i;
    }

    public static boolean isExternalOrNoComponentIntent(String str, Intent intent) {
        if (intent.getComponent() == null) {
            return true;
        }
        return !str.equals(intent.getComponent().getPackageName());
    }

    public ActivityOptions adjustOptions(SafeActivityOptions safeActivityOptions, int i) {
        ActivityOptions options;
        if (safeActivityOptions == null) {
            options = ActivityOptions.makeBasic();
        } else {
            options = safeActivityOptions.getOptions(this.mSupervisor);
        }
        options.setLaunchDisplayId(i);
        return options;
    }

    public static ActivityOptions adjustOptions(Bundle bundle, int i) {
        ActivityOptions fromBundle;
        if (bundle == null) {
            fromBundle = ActivityOptions.makeBasic();
        } else {
            fromBundle = ActivityOptions.fromBundle(bundle);
        }
        fromBundle.setLaunchDisplayId(i);
        return fromBundle;
    }

    public final int startActivityInPackage(int i, int i2, int i3, String str, String str2, Intent intent, String str3, IBinder iBinder, String str4, int i4, int i5, SafeActivityOptions safeActivityOptions, int i6, Task task, String str5, boolean z, PendingIntentRecord pendingIntentRecord, BackgroundStartPrivileges backgroundStartPrivileges) {
        SafeActivityOptions safeActivityOptions2;
        SafeActivityOptions safeActivityOptions3;
        String str6;
        String str7;
        Intent intent2;
        String str8;
        ActivityTaskManagerService activityTaskManagerService = this.mService;
        if ((activityTaskManagerService.mSIOPLevel >= 6 || activityTaskManagerService.mBatteryOverheatLevel > 0) && !activityTaskManagerService.isPossibleToStart(intent)) {
            Handler handler = this.mHandler;
            ActivityTaskManagerService.HandlerC2878H handlerC2878H = this.mService.f1734mH;
            this.mHandler.sendMessage(handler.obtainMessage(5));
            return -102;
        }
        int checkTargetUser = checkTargetUser(i6, z, i2, i3, str5);
        if (!CoreRune.SYSFW_APP_SPEG || str == null) {
            safeActivityOptions2 = safeActivityOptions;
        } else {
            DisplayManager displayManager = (DisplayManager) this.mService.mContext.getSystemService(DisplayManager.class);
            int hiddenDisplayId = displayManager.getHiddenDisplayId(str);
            if (hiddenDisplayId != -1) {
                if (isExternalOrNoComponentIntent(str, intent)) {
                    Slog.w("SPEG", "Application " + str + " is trying to start an extrnal intent: " + intent);
                    return 102;
                }
                safeActivityOptions3 = new SafeActivityOptions(adjustOptions(safeActivityOptions, hiddenDisplayId));
                Slog.d("SPEG", "Reuse hidden display #" + hiddenDisplayId + " for " + intent);
                if (SemDualAppManager.isDualAppId(SemDualAppManager.getDualAppProfileId()) || intent == null || intent.getComponent() == null || (!("com.tencent.mm".equals(intent.getComponent().getPackageName()) || "com.tencent.mobileqq".equals(intent.getComponent().getPackageName())) || i3 == 1000)) {
                    str6 = str3;
                    str7 = str5;
                    intent2 = intent;
                } else {
                    int userId = UserHandle.getUserId(i3);
                    intent2 = DualAppManagerService.startDAChooserActivity(intent, userId, checkTargetUser, str, i3);
                    if (intent2 != null) {
                        str6 = null;
                    } else {
                        str6 = str3;
                        intent2 = intent;
                    }
                    if (SemDualAppManager.isDualAppId(userId)) {
                        if (intent2.getComponent() != null) {
                            str8 = intent2.getComponent().getPackageName();
                        } else {
                            str8 = intent2.getPackage();
                        }
                        if (str != null && str8 != null && !str.equals(str8) && DualAppManagerService.shouldForwardToOwner(str8)) {
                            checkTargetUser = 0;
                        }
                    }
                    str7 = str5;
                }
                return obtainStarter(intent2, str7).setCallingUid(i).setRealCallingPid(i2).setRealCallingUid(i3).setCallingPackage(str).setCallingFeatureId(str2).setResolvedType(str6).setResultTo(iBinder).setResultWho(str4).setRequestCode(i4).setStartFlags(i5).setActivityOptions(safeActivityOptions3).setUserId(checkTargetUser).setInTask(task).setOriginatingPendingIntent(pendingIntentRecord).setBackgroundStartPrivileges(backgroundStartPrivileges).execute();
            }
            safeActivityOptions2 = safeActivityOptions;
            if (displayManager.getHiddenDisplayId(i3) != -1) {
                Slog.w("SPEG", "Application " + str + XmlUtils.STRING_ARRAY_SEPARATOR + i + " is trying to startActivityInPackage under another uid " + i3);
                return 102;
            }
        }
        safeActivityOptions3 = safeActivityOptions2;
        if (SemDualAppManager.isDualAppId(SemDualAppManager.getDualAppProfileId())) {
        }
        str6 = str3;
        str7 = str5;
        intent2 = intent;
        return obtainStarter(intent2, str7).setCallingUid(i).setRealCallingPid(i2).setRealCallingUid(i3).setCallingPackage(str).setCallingFeatureId(str2).setResolvedType(str6).setResultTo(iBinder).setResultWho(str4).setRequestCode(i4).setStartFlags(i5).setActivityOptions(safeActivityOptions3).setUserId(checkTargetUser).setInTask(task).setOriginatingPendingIntent(pendingIntentRecord).setBackgroundStartPrivileges(backgroundStartPrivileges).execute();
    }

    public final int startActivitiesInPackage(int i, String str, String str2, Intent[] intentArr, String[] strArr, IBinder iBinder, SafeActivityOptions safeActivityOptions, int i2, boolean z, PendingIntentRecord pendingIntentRecord, BackgroundStartPrivileges backgroundStartPrivileges) {
        return startActivitiesInPackage(i, 0, -1, str, str2, intentArr, strArr, iBinder, safeActivityOptions, i2, z, pendingIntentRecord, backgroundStartPrivileges);
    }

    public final int startActivitiesInPackage(int i, int i2, int i3, String str, String str2, Intent[] intentArr, String[] strArr, IBinder iBinder, SafeActivityOptions safeActivityOptions, int i4, boolean z, PendingIntentRecord pendingIntentRecord, BackgroundStartPrivileges backgroundStartPrivileges) {
        return startActivities(null, i, i2, i3, str, str2, intentArr, strArr, iBinder, safeActivityOptions, checkTargetUser(i4, z, Binder.getCallingPid(), Binder.getCallingUid(), "startActivityInPackage"), "startActivityInPackage", pendingIntentRecord, backgroundStartPrivileges);
    }

    /* JADX WARN: Code restructure failed: missing block: B:107:0x01f0, code lost:
    
        r0 = r0 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:108:0x01f2, code lost:
    
        if (r0 >= r5) goto L139;
     */
    /* JADX WARN: Code restructure failed: missing block: B:109:0x01f4, code lost:
    
        r28.mFactory.recycle(r25[r0]);
     */
    /* JADX WARN: Code restructure failed: missing block: B:110:0x01fb, code lost:
    
        r0 = r0 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:113:0x01fe, code lost:
    
        r0 = r28.mService.mWindowManager.mStartingSurfaceController;
     */
    /* JADX WARN: Code restructure failed: missing block: B:114:0x0204, code lost:
    
        if (r38 == null) goto L80;
     */
    /* JADX WARN: Code restructure failed: missing block: B:115:0x0206, code lost:
    
        r20 = r38.getOriginalOptions();
     */
    /* JADX WARN: Code restructure failed: missing block: B:116:0x020a, code lost:
    
        r0.endDeferAddStartingWindow(r20);
     */
    /* JADX WARN: Code restructure failed: missing block: B:119:0x021e, code lost:
    
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        android.os.Binder.restoreCallingIdentity(r18);
     */
    /* JADX WARN: Code restructure failed: missing block: B:120:0x0224, code lost:
    
        return r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x0225, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:124:0x0234, code lost:
    
        throw r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x028c, code lost:
    
        r0 = r28.mService.mWindowManager.mStartingSurfaceController;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x0292, code lost:
    
        if (r38 == null) goto L112;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x0294, code lost:
    
        r20 = r38.getOriginalOptions();
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x0298, code lost:
    
        r0.endDeferAddStartingWindow(r20);
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x02ac, code lost:
    
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x02b2, code lost:
    
        return 0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int startActivities(IApplicationThread iApplicationThread, int i, int i2, int i3, String str, String str2, Intent[] intentArr, String[] strArr, IBinder iBinder, SafeActivityOptions safeActivityOptions, int i4, String str3, PendingIntentRecord pendingIntentRecord, BackgroundStartPrivileges backgroundStartPrivileges) {
        int i5;
        int i6;
        int i7;
        int i8;
        Intent intent;
        NeededUriGrants checkGrantUriPermissionFromIntent;
        String str4;
        Intent[] intentArr2;
        SafeActivityOptions safeActivityOptions2;
        if (intentArr == null) {
            throw new NullPointerException("intents is null");
        }
        if (strArr == null) {
            throw new NullPointerException("resolvedTypes is null");
        }
        if (intentArr.length != strArr.length) {
            throw new IllegalArgumentException("intents are length different than resolvedTypes");
        }
        int callingPid = i2 != 0 ? i2 : Binder.getCallingPid();
        int i9 = i3;
        if (i9 == -1) {
            i9 = Binder.getCallingUid();
        }
        if (i >= 0) {
            i5 = i;
            i6 = -1;
        } else if (iApplicationThread == null) {
            i6 = callingPid;
            i5 = i9;
        } else {
            i5 = -1;
            i6 = -1;
        }
        int computeResolveFilterUid = ActivityStarter.computeResolveFilterUid(i5, i9, -10000);
        SparseArray sparseArray = new SparseArray();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        SafeActivityOptions selectiveCloneLaunchOptions = safeActivityOptions != null ? safeActivityOptions.selectiveCloneLaunchOptions(true) : null;
        try {
            Intent[] intentArr3 = (Intent[]) ArrayUtils.filterNotNull(intentArr, new IntFunction() { // from class: com.android.server.wm.ActivityStartController$$ExternalSyntheticLambda0
                @Override // java.util.function.IntFunction
                public final Object apply(int i10) {
                    Intent[] lambda$startActivities$0;
                    lambda$startActivities$0 = ActivityStartController.lambda$startActivities$0(i10);
                    return lambda$startActivities$0;
                }
            });
            int length = intentArr3.length;
            ActivityStarter[] activityStarterArr = new ActivityStarter[length];
            int i10 = 0;
            while (i10 < intentArr3.length) {
                Intent intent2 = intentArr3[i10];
                if (intent2.hasFileDescriptors()) {
                    throw new IllegalArgumentException("File descriptors passed in Intent");
                }
                int i11 = computeResolveFilterUid;
                boolean z = intent2.getComponent() != null;
                Intent intent3 = new Intent(intent2);
                ActivityStarter[] activityStarterArr2 = activityStarterArr;
                int i12 = length;
                SparseArray sparseArray2 = sparseArray;
                int i13 = i6;
                ActivityInfo activityInfoForUser = this.mService.mAmInternal.getActivityInfoForUser(this.mSupervisor.resolveActivity(intent3, strArr[i10], 0, null, i4, i11, i13), i4);
                if (activityInfoForUser != null) {
                    try {
                        UriGrantsManagerInternal uriGrantsManagerInternal = this.mSupervisor.mService.mUgmInternal;
                        ApplicationInfo applicationInfo = activityInfoForUser.applicationInfo;
                        i8 = i11;
                        intent = intent3;
                        checkGrantUriPermissionFromIntent = uriGrantsManagerInternal.checkGrantUriPermissionFromIntent(intent, i8, applicationInfo.packageName, UserHandle.getUserId(applicationInfo.uid));
                        ApplicationInfo applicationInfo2 = activityInfoForUser.applicationInfo;
                        if ((applicationInfo2.privateFlags & 2) != 0) {
                            throw new IllegalArgumentException("FLAG_CANT_SAVE_STATE not supported here");
                        }
                        sparseArray2.put(applicationInfo2.uid, applicationInfo2.packageName);
                    } catch (SecurityException unused) {
                        Slog.d("ActivityTaskManager", "Not allowed to start activity since no uri permission.");
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return -96;
                    }
                } else {
                    i8 = i11;
                    intent = intent3;
                    checkGrantUriPermissionFromIntent = null;
                }
                boolean z2 = i10 == intentArr3.length - 1;
                if (z2) {
                    str4 = str3;
                    intentArr2 = intentArr3;
                    safeActivityOptions2 = safeActivityOptions;
                } else {
                    str4 = str3;
                    intentArr2 = intentArr3;
                    safeActivityOptions2 = selectiveCloneLaunchOptions;
                }
                activityStarterArr2[i10] = obtainStarter(intent, str4).setIntentGrants(checkGrantUriPermissionFromIntent).setCaller(iApplicationThread).setResolvedType(strArr[i10]).setActivityInfo(activityInfoForUser).setRequestCode(-1).setCallingPid(i13).setCallingUid(i5).setCallingPackage(str).setCallingFeatureId(str2).setRealCallingPid(callingPid).setRealCallingUid(i9).setActivityOptions(safeActivityOptions2).setComponentSpecified(z).setAllowPendingRemoteAnimationRegistryLookup(z2).setOriginatingPendingIntent(pendingIntentRecord).setBackgroundStartPrivileges(backgroundStartPrivileges);
                i10++;
                intentArr3 = intentArr2;
                sparseArray = sparseArray2;
                computeResolveFilterUid = i8;
                activityStarterArr = activityStarterArr2;
                length = i12;
                i6 = i13;
            }
            ActivityStarter[] activityStarterArr3 = activityStarterArr;
            int i14 = length;
            SparseArray sparseArray3 = sparseArray;
            int i15 = computeResolveFilterUid;
            if (sparseArray3.size() > 1) {
                StringBuilder sb = new StringBuilder("startActivities: different apps [");
                int size = sparseArray3.size();
                int i16 = 0;
                while (i16 < size) {
                    sb.append((String) sparseArray3.valueAt(i16));
                    sb.append(i16 == size + (-1) ? "]" : ", ");
                    i16++;
                }
                sb.append(" from ");
                sb.append(str);
                Slog.wtf("ActivityTaskManager", sb.toString());
                i7 = 1;
            } else {
                i7 = 1;
            }
            ActivityRecord[] activityRecordArr = new ActivityRecord[i7];
            WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    this.mService.deferWindowLayout();
                    this.mService.mWindowManager.mStartingSurfaceController.beginDeferAddStartingWindow();
                    IBinder iBinder2 = iBinder;
                    int i17 = 0;
                    while (true) {
                        int i18 = i14;
                        if (i17 >= i18) {
                            try {
                                break;
                            } finally {
                            }
                        }
                        try {
                            int execute = activityStarterArr3[i17].setResultTo(iBinder2).setOutActivity(activityRecordArr).execute();
                            if (execute < 0) {
                                break;
                            }
                            ActivityRecord activityRecord = activityRecordArr[0];
                            if (activityRecord == null || activityRecord.getUid() != i15) {
                                if (i17 < i18 - 1) {
                                    activityStarterArr3[i17 + 1].getIntent().addFlags(268435456);
                                }
                                iBinder2 = iBinder;
                            } else {
                                iBinder2 = activityRecord.token;
                            }
                            i17++;
                            i14 = i18;
                        } catch (Throwable th) {
                            try {
                                this.mService.mWindowManager.mStartingSurfaceController.endDeferAddStartingWindow(safeActivityOptions != null ? safeActivityOptions.getOriginalOptions() : null);
                                throw th;
                            } finally {
                            }
                        }
                    }
                } catch (Throwable th2) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th2;
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static /* synthetic */ Intent[] lambda$startActivities$0(int i) {
        return new Intent[i];
    }

    public int startActivityInTaskFragment(TaskFragment taskFragment, Intent intent, Bundle bundle, IBinder iBinder, int i, int i2, IBinder iBinder2) {
        ActivityRecord forTokenLocked = iBinder != null ? ActivityRecord.forTokenLocked(iBinder) : null;
        return obtainStarter(intent, "startActivityInTaskFragment").setActivityOptions(bundle).setInTaskFragment(taskFragment).setResultTo(iBinder).setRequestCode(-1).setCallingUid(i).setCallingPid(i2).setRealCallingUid(i).setRealCallingPid(i2).setUserId(forTokenLocked != null ? forTokenLocked.mUserId : this.mService.getCurrentUserId()).setErrorCallbackToken(iBinder2).execute();
    }

    public boolean startExistingRecentsIfPossible(Intent intent, ActivityOptions activityOptions) {
        try {
            Trace.traceBegin(32L, "startExistingRecents");
            if (!startExistingRecents(intent, activityOptions)) {
                Trace.traceEnd(32L);
                return false;
            }
            if (CoreRune.MW_SA_LOGGING && this.mService.mRootWindowContainer.getDefaultTaskDisplayArea().isSplitScreenModeActivated()) {
                CoreSaLogger.logForAdvanced("1005", "Tap 'Recent' button");
            }
            Trace.traceEnd(32L);
            return true;
        } catch (Throwable th) {
            Trace.traceEnd(32L);
            throw th;
        }
    }

    public final boolean startExistingRecents(Intent intent, ActivityOptions activityOptions) {
        ActivityRecord activityRecord;
        Task rootTask = this.mService.mRootWindowContainer.getDefaultTaskDisplayArea().getRootTask(0, this.mService.getRecentTasks().getRecentsComponent().equals(intent.getComponent()) ? 3 : 2);
        if (rootTask == null || (activityRecord = rootTask.topRunningActivity()) == null || activityRecord.isVisibleRequested() || !activityRecord.attachedToProcess() || !activityRecord.mActivityComponent.equals(intent.getComponent()) || !this.mService.isCallerRecents(activityRecord.getUid()) || activityRecord.mDisplayContent.isKeyguardLocked()) {
            return false;
        }
        if (isKidsModeRunning()) {
            Slog.d("ActivityTaskManager", "startExistingRecents blocked by kids mode");
            return false;
        }
        DisplayContent displayContent = activityRecord.getDisplayContent();
        if (displayContent != null) {
            displayContent.onExitingRecentsChanged(true);
        }
        this.mService.mRootWindowContainer.startPowerModeLaunchIfNeeded(true, activityRecord);
        ActivityMetricsLogger.LaunchingState notifyActivityLaunching = this.mSupervisor.getActivityMetricsLogger().notifyActivityLaunching(intent);
        Task task = activityRecord.getTask();
        this.mService.deferWindowLayout();
        try {
            TransitionController transitionController = activityRecord.mTransitionController;
            Transition collectingTransition = transitionController.getCollectingTransition();
            if (collectingTransition != null) {
                collectingTransition.setRemoteAnimationApp(activityRecord.app.getThread());
                transitionController.setTransientLaunch(activityRecord, TaskDisplayArea.getRootTaskAbove(rootTask));
            }
            task.moveToFront("startExistingRecents");
            task.mInResumeTopActivity = true;
            task.resumeTopActivity(null, activityOptions, true);
            this.mSupervisor.getActivityMetricsLogger().notifyActivityLaunched(notifyActivityLaunching, 2, false, activityRecord, activityOptions);
            return true;
        } finally {
            if (displayContent != null) {
                displayContent.onExitingRecentsChanged(false);
            }
            task.mInResumeTopActivity = false;
            this.mService.continueWindowLayout();
        }
    }

    public final void doPendingActivityLaunches(PendingActivityLaunch pendingActivityLaunch, Task task, ActivityOptions activityOptions) {
        try {
            obtainStarter(null, "pendingActivityLaunch-for-process-kill").startResolvedActivity(pendingActivityLaunch.f1747r, pendingActivityLaunch.sourceRecord, null, null, pendingActivityLaunch.startFlags, activityOptions, task, null, 1, pendingActivityLaunch.intentGrants);
        } catch (Exception e) {
            Slog.e("ActivityTaskManager", "Exception during pending activity launch for process kill. pal=" + pendingActivityLaunch, e);
        }
    }

    public void registerRemoteAnimationForNextActivityStart(String str, RemoteAnimationAdapter remoteAnimationAdapter, IBinder iBinder) {
        this.mPendingRemoteAnimationRegistry.addPendingAnimation(str, remoteAnimationAdapter, iBinder);
    }

    public void registerRemoteTransitionForNextActivityStart(String str, RemoteAnimationAdapter remoteAnimationAdapter, IBinder iBinder, RemoteTransition remoteTransition) {
        this.mPendingRemoteAnimationRegistry.addPendingAnimation(str, remoteAnimationAdapter, iBinder, remoteTransition);
    }

    public PendingRemoteAnimationRegistry getPendingRemoteAnimationRegistry() {
        return this.mPendingRemoteAnimationRegistry;
    }

    public void dumpLastHomeActivityStartResult(PrintWriter printWriter, String str) {
        printWriter.print(str);
        printWriter.print("mLastHomeActivityStartResult=");
        printWriter.println(this.mLastHomeActivityStartResult);
    }

    public void dump(PrintWriter printWriter, String str, String str2) {
        boolean z;
        ActivityRecord activityRecord;
        boolean z2 = true;
        boolean z3 = false;
        boolean z4 = str2 != null;
        ActivityRecord activityRecord2 = this.mLastHomeActivityStartRecord;
        if (activityRecord2 == null || (z4 && !str2.equals(activityRecord2.packageName))) {
            z = false;
        } else {
            dumpLastHomeActivityStartResult(printWriter, str);
            printWriter.print(str);
            printWriter.println("mLastHomeActivityStartRecord:");
            this.mLastHomeActivityStartRecord.dump(printWriter, str + "  ", true);
            z = true;
        }
        ActivityStarter activityStarter = this.mLastStarter;
        if (activityStarter != null) {
            if (!z4 || activityStarter.relatedToPackage(str2) || ((activityRecord = this.mLastHomeActivityStartRecord) != null && str2.equals(activityRecord.packageName))) {
                z3 = true;
            }
            if (z3) {
                if (z) {
                    z2 = z;
                } else {
                    dumpLastHomeActivityStartResult(printWriter, str);
                }
                printWriter.print(str);
                printWriter.println("mLastStarter:");
                this.mLastStarter.dump(printWriter, str + "  ");
                if (z4) {
                    return;
                } else {
                    z = z2;
                }
            }
        }
        if (z) {
            return;
        }
        printWriter.print(str);
        printWriter.println("(nothing)");
    }

    public BackgroundActivityStartController getBackgroundActivityLaunchController() {
        return this.mBalController;
    }

    public final boolean isKidsModeRunning() {
        ResolveInfo resolveActivity;
        ActivityInfo activityInfo;
        String str;
        PackageManager packageManager = this.mService.mContext.getPackageManager();
        if (packageManager == null || (resolveActivity = packageManager.resolveActivity(new Intent("android.intent.action.MAIN").addCategory("android.intent.category.HOME"), 65536)) == null || (activityInfo = resolveActivity.activityInfo) == null || (str = activityInfo.name) == null) {
            return false;
        }
        return str.contains("com.sec.android.app.kidshome");
    }
}
