package com.android.systemui.qs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.UserInfo;
import android.os.RemoteException;
import android.provider.DeviceConfig;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.qs.FgsManagerControllerImpl;
import com.android.systemui.settings.UserTrackerImpl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final /* synthetic */ class FgsManagerControllerImpl$secFgsManagerController$3 extends FunctionReferenceImpl implements Function0 {
    public FgsManagerControllerImpl$secFgsManagerController$3(Object obj) {
        super(0, obj, FgsManagerControllerImpl.class, "init", "init()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        final FgsManagerControllerImpl fgsManagerControllerImpl = (FgsManagerControllerImpl) this.receiver;
        synchronized (fgsManagerControllerImpl.lock) {
            if (!fgsManagerControllerImpl.initialized) {
                fgsManagerControllerImpl.showUserVisibleJobs = fgsManagerControllerImpl.deviceConfigProxy.getBoolean("systemui", "task_manager_show_user_visible_jobs", true);
                fgsManagerControllerImpl.informJobSchedulerOfPendingAppStop = fgsManagerControllerImpl.deviceConfigProxy.getBoolean("systemui", "task_manager_inform_job_scheduler_of_pending_app_stop", true);
                try {
                    fgsManagerControllerImpl.activityManager.registerForegroundServiceObserver(fgsManagerControllerImpl.foregroundServiceObserver);
                    if (fgsManagerControllerImpl.showUserVisibleJobs) {
                        fgsManagerControllerImpl.jobScheduler.registerUserVisibleJobObserver(fgsManagerControllerImpl.userVisibleJobObserver);
                    }
                } catch (RemoteException e) {
                    e.rethrowFromSystemServer().getClass();
                }
                ((UserTrackerImpl) fgsManagerControllerImpl.userTracker).addCallback(fgsManagerControllerImpl.userTrackerCallback, fgsManagerControllerImpl.backgroundExecutor);
                Set set = fgsManagerControllerImpl.currentProfileIds;
                List userProfiles = ((UserTrackerImpl) fgsManagerControllerImpl.userTracker).getUserProfiles();
                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(userProfiles, 10));
                Iterator it = userProfiles.iterator();
                while (it.hasNext()) {
                    arrayList.add(Integer.valueOf(((UserInfo) it.next()).id));
                }
                set.addAll(arrayList);
                fgsManagerControllerImpl.deviceConfigProxy.addOnPropertiesChangedListener("systemui", fgsManagerControllerImpl.backgroundExecutor, new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.systemui.qs.FgsManagerControllerImpl$init$1$2
                    public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                        StateFlowImpl stateFlowImpl = FgsManagerControllerImpl.this._showFooterDot;
                        stateFlowImpl.updateState(null, Boolean.valueOf(properties.getBoolean("task_manager_show_footer_dot", ((Boolean) stateFlowImpl.getValue()).booleanValue())));
                        FgsManagerControllerImpl fgsManagerControllerImpl2 = FgsManagerControllerImpl.this;
                        fgsManagerControllerImpl2.showStopBtnForUserAllowlistedApps = properties.getBoolean("show_stop_button_for_user_allowlisted_apps", fgsManagerControllerImpl2.showStopBtnForUserAllowlistedApps);
                        FgsManagerControllerImpl fgsManagerControllerImpl3 = FgsManagerControllerImpl.this;
                        boolean z = fgsManagerControllerImpl3.showUserVisibleJobs;
                        fgsManagerControllerImpl3.showUserVisibleJobs = properties.getBoolean("task_manager_show_user_visible_jobs", z);
                        FgsManagerControllerImpl fgsManagerControllerImpl4 = FgsManagerControllerImpl.this;
                        boolean z2 = fgsManagerControllerImpl4.showUserVisibleJobs;
                        if (z2 != z) {
                            if (z2) {
                                fgsManagerControllerImpl4.jobScheduler.registerUserVisibleJobObserver(fgsManagerControllerImpl4.userVisibleJobObserver);
                            } else {
                                fgsManagerControllerImpl4.jobScheduler.unregisterUserVisibleJobObserver(fgsManagerControllerImpl4.userVisibleJobObserver);
                                synchronized (fgsManagerControllerImpl4.lock) {
                                    try {
                                        for (Map.Entry entry : ((LinkedHashMap) fgsManagerControllerImpl4.runningTaskIdentifiers).entrySet()) {
                                            FgsManagerControllerImpl.UserPackage userPackage = (FgsManagerControllerImpl.UserPackage) entry.getKey();
                                            FgsManagerControllerImpl.StartTimeAndIdentifiers startTimeAndIdentifiers = (FgsManagerControllerImpl.StartTimeAndIdentifiers) entry.getValue();
                                            if (startTimeAndIdentifiers.fgsTokens.isEmpty()) {
                                                fgsManagerControllerImpl4.runningTaskIdentifiers.remove(userPackage);
                                            } else {
                                                startTimeAndIdentifiers.jobSummaries.clear();
                                            }
                                        }
                                        fgsManagerControllerImpl4.updateNumberOfVisibleRunningPackagesLocked();
                                        fgsManagerControllerImpl4.updateAppItemsLocked(false);
                                        Unit unit = Unit.INSTANCE;
                                    } catch (Throwable th) {
                                        throw th;
                                    }
                                }
                            }
                        }
                        FgsManagerControllerImpl fgsManagerControllerImpl5 = FgsManagerControllerImpl.this;
                        fgsManagerControllerImpl5.informJobSchedulerOfPendingAppStop = properties.getBoolean("show_stop_button_for_user_allowlisted_apps", fgsManagerControllerImpl5.informJobSchedulerOfPendingAppStop);
                    }
                });
                fgsManagerControllerImpl._showFooterDot.updateState(null, Boolean.valueOf(fgsManagerControllerImpl.deviceConfigProxy.getBoolean("systemui", "task_manager_show_footer_dot", false)));
                fgsManagerControllerImpl.showStopBtnForUserAllowlistedApps = fgsManagerControllerImpl.deviceConfigProxy.getBoolean("systemui", "show_stop_button_for_user_allowlisted_apps", true);
                fgsManagerControllerImpl.dumpManager.registerDumpable(fgsManagerControllerImpl);
                BroadcastDispatcher.registerReceiver$default(fgsManagerControllerImpl.broadcastDispatcher, new BroadcastReceiver() { // from class: com.android.systemui.qs.FgsManagerControllerImpl$init$1$3
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        if (Intrinsics.areEqual(intent.getAction(), "android.intent.action.SHOW_FOREGROUND_SERVICE_MANAGER")) {
                            FgsManagerControllerImpl.this.showDialog$2();
                        }
                    }
                }, new IntentFilter("android.intent.action.SHOW_FOREGROUND_SERVICE_MANAGER"), fgsManagerControllerImpl.mainExecutor, null, 4, null, 40);
                fgsManagerControllerImpl.initialized = true;
                Unit unit = Unit.INSTANCE;
            }
        }
        return Unit.INSTANCE;
    }
}
