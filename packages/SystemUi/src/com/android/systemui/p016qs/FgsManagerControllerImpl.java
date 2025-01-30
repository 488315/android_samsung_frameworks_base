package com.android.systemui.p016qs;

import android.app.IActivityManager;
import android.app.IForegroundServiceObserver;
import android.app.job.IUserVisibleJobObserver;
import android.app.job.JobScheduler;
import android.app.job.UserVisibleJobSummary;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.text.format.DateUtils;
import android.util.ArrayMap;
import android.util.IndentingPrintWriter;
import android.util.StatsEvent;
import android.util.StatsLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.picker.model.AppInfo$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.AdapterListUpdateCallback;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.app.motiontool.TraceMetadata$$ExternalSyntheticOutline0;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyboard.KeyboardUI$$ExternalSyntheticOutline0;
import com.android.systemui.p016qs.FgsManagerController;
import com.android.systemui.p016qs.FgsManagerControllerImpl;
import com.android.systemui.p016qs.footer.data.repository.C2178xdf89411;
import com.android.systemui.qs.FgsManagerControllerImpl.UserPackage;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.util.DeviceConfigProxy;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.util.time.SystemClockImpl;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Supplier;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class FgsManagerControllerImpl implements Dumpable, FgsManagerController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final StateFlowImpl _showFooterDot;
    public final IActivityManager activityManager;
    public final AppListAdapter appListAdapter;
    public final Executor backgroundExecutor;
    public final BroadcastDispatcher broadcastDispatcher;
    public final Context context;
    public final Set currentProfileIds;
    public final DeviceConfigProxy deviceConfigProxy;
    public SystemUIDialog dialog;
    public final DumpManager dumpManager;
    public final ForegroundServiceObserver foregroundServiceObserver;
    public boolean informJobSchedulerOfPendingAppStop;
    public boolean initialized;
    public final JobScheduler jobScheduler;
    public int lastNumberOfVisiblePackages;
    public final Object lock;
    public final Executor mainExecutor;
    public boolean newChangesSinceDialogWasDismissed;
    public final Set onDialogDismissedListeners;
    public final Set onNumberOfPackagesChangedListeners;
    public final PackageManager packageManager;
    public final ArrayMap runningApps;
    public final Map runningTaskIdentifiers;
    public final SecFgsManagerControllerImpl secFgsManagerController;
    public final ReadonlyStateFlow showFooterDot;
    public boolean showStopBtnForUserAllowlistedApps;
    public boolean showUserVisibleJobs;
    public final SystemClock systemClock;
    public final UserTracker userTracker;
    public final FgsManagerControllerImpl$userTrackerCallback$1 userTrackerCallback;
    public final UserVisibleJobObserver userVisibleJobObserver;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AppItemViewHolder extends RecyclerView.ViewHolder {
        public final TextView appLabelView;
        public final TextView durationView;
        public final ImageView iconView;
        public final Button stopButton;

        public AppItemViewHolder(View view) {
            super(view);
            this.appLabelView = (TextView) view.requireViewById(R.id.fgs_manager_app_item_label);
            this.durationView = (TextView) view.requireViewById(R.id.fgs_manager_app_item_duration);
            this.iconView = (ImageView) view.requireViewById(R.id.fgs_manager_app_item_icon);
            this.stopButton = (Button) view.requireViewById(R.id.fgs_manager_app_item_stop_button);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AppListAdapter extends RecyclerView.Adapter {
        public final Object lock = new Object();
        public List data = EmptyList.INSTANCE;

        public AppListAdapter() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final int getItemCount() {
            return this.data.size();
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r7v1, types: [T, java.lang.Object] */
        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            ?? r7;
            final AppItemViewHolder appItemViewHolder = (AppItemViewHolder) viewHolder;
            final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
            synchronized (this.lock) {
                r7 = this.data.get(i);
                ref$ObjectRef.element = r7;
                Unit unit = Unit.INSTANCE;
            }
            final FgsManagerControllerImpl fgsManagerControllerImpl = FgsManagerControllerImpl.this;
            appItemViewHolder.iconView.setImageDrawable(((RunningApp) r7).icon);
            appItemViewHolder.appLabelView.setText(((RunningApp) ref$ObjectRef.element).appLabel);
            TextView textView = appItemViewHolder.durationView;
            ((SystemClockImpl) fgsManagerControllerImpl.systemClock).getClass();
            textView.setText(DateUtils.formatDuration(Math.max(android.os.SystemClock.elapsedRealtime() - ((RunningApp) ref$ObjectRef.element).timeStarted, 60000L), 10));
            appItemViewHolder.stopButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.FgsManagerControllerImpl$AppListAdapter$onBindViewHolder$2$1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    FgsManagerControllerImpl.AppItemViewHolder.this.stopButton.setText(R.string.sec_fgs_manager_app_item_stop_button_stopped_label);
                    FgsManagerControllerImpl.AppItemViewHolder appItemViewHolder2 = FgsManagerControllerImpl.AppItemViewHolder.this;
                    appItemViewHolder2.stopButton.setContentDescription(((Object) appItemViewHolder2.appLabelView.getText()) + ", " + ((Object) FgsManagerControllerImpl.AppItemViewHolder.this.stopButton.getText()));
                    FgsManagerControllerImpl fgsManagerControllerImpl2 = fgsManagerControllerImpl;
                    T t = ref$ObjectRef.element;
                    int i2 = ((FgsManagerControllerImpl.RunningApp) t).userId;
                    String str = ((FgsManagerControllerImpl.RunningApp) t).packageName;
                    long j = ((FgsManagerControllerImpl.RunningApp) t).timeStarted;
                    int i3 = FgsManagerControllerImpl.$r8$clinit;
                    fgsManagerControllerImpl2.logEvent(true, str, i2, j);
                    fgsManagerControllerImpl2.new UserPackage(i2, str);
                    if (fgsManagerControllerImpl2.showUserVisibleJobs || fgsManagerControllerImpl2.informJobSchedulerOfPendingAppStop) {
                        fgsManagerControllerImpl2.jobScheduler.notePendingUserRequestedAppStop(str, i2, "task manager");
                    }
                    fgsManagerControllerImpl2.activityManager.stopAppForUser(str, i2);
                }
            });
            if (((RunningApp) ref$ObjectRef.element).uiControl == UIControl.HIDE_BUTTON) {
                appItemViewHolder.stopButton.setVisibility(4);
            }
            if (((RunningApp) ref$ObjectRef.element).stopped) {
                appItemViewHolder.stopButton.setEnabled(false);
                appItemViewHolder.stopButton.setText(R.string.sec_fgs_manager_app_item_stop_button_stopped_label);
                appItemViewHolder.durationView.setVisibility(4);
            } else {
                appItemViewHolder.stopButton.setEnabled(true);
                appItemViewHolder.stopButton.setText(R.string.sec_fgs_manager_app_item_stop_button_label);
                appItemViewHolder.durationView.setVisibility(0);
            }
            appItemViewHolder.stopButton.setContentDescription(((Object) appItemViewHolder.appLabelView.getText()) + ", " + ((Object) appItemViewHolder.stopButton.getText()));
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final RecyclerView.ViewHolder onCreateViewHolder(RecyclerView recyclerView, int i) {
            return new AppItemViewHolder(LayoutInflater.from(recyclerView.getContext()).inflate(R.layout.sec_fgs_manager_app_item, (ViewGroup) recyclerView, false));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ForegroundServiceObserver extends IForegroundServiceObserver.Stub {
        public ForegroundServiceObserver() {
        }

        public final void onForegroundStateChanged(IBinder iBinder, String str, int i, boolean z) {
            FgsManagerControllerImpl fgsManagerControllerImpl = FgsManagerControllerImpl.this;
            synchronized (fgsManagerControllerImpl.lock) {
                boolean z2 = false;
                fgsManagerControllerImpl.secFgsManagerController.log("onForegroundStateChanged: [dialog:" + (fgsManagerControllerImpl.dialog != null) + "]:[isForeground:" + z + "]:[packageName:" + str + "]:[token:" + iBinder + "]:[userId:" + i + "]");
                UserPackage userPackage = fgsManagerControllerImpl.new UserPackage(i, str);
                if (z) {
                    LinkedHashMap linkedHashMap = (LinkedHashMap) fgsManagerControllerImpl.runningTaskIdentifiers;
                    Object obj = linkedHashMap.get(userPackage);
                    if (obj == null) {
                        obj = new StartTimeAndIdentifiers(fgsManagerControllerImpl.systemClock);
                        linkedHashMap.put(userPackage, obj);
                    }
                    ((StartTimeAndIdentifiers) obj).fgsTokens.add(iBinder);
                } else {
                    StartTimeAndIdentifiers startTimeAndIdentifiers = (StartTimeAndIdentifiers) ((LinkedHashMap) fgsManagerControllerImpl.runningTaskIdentifiers).get(userPackage);
                    if (startTimeAndIdentifiers != null) {
                        startTimeAndIdentifiers.fgsTokens.remove(iBinder);
                        if (startTimeAndIdentifiers.fgsTokens.isEmpty() && startTimeAndIdentifiers.jobSummaries.isEmpty()) {
                            z2 = true;
                        }
                    }
                    if (z2) {
                        fgsManagerControllerImpl.runningTaskIdentifiers.remove(userPackage);
                    }
                }
                fgsManagerControllerImpl.updateNumberOfVisibleRunningPackagesLocked();
                FgsManagerControllerImpl.updateAppItemsLocked$default(fgsManagerControllerImpl);
                Unit unit = Unit.INSTANCE;
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class StartTimeAndIdentifiers {
        public final SystemClock systemClock;
        public final long startTime = android.os.SystemClock.elapsedRealtime();
        public final Set fgsTokens = new LinkedHashSet();
        public final Set jobSummaries = new LinkedHashSet();

        public StartTimeAndIdentifiers(SystemClock systemClock) {
            this.systemClock = systemClock;
        }

        public final void dump(PrintWriter printWriter) {
            printWriter.println("StartTimeAndIdentifiers: [");
            ((IndentingPrintWriter) printWriter).increaseIndent();
            ((SystemClockImpl) this.systemClock).getClass();
            long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            long j = this.startTime;
            long j2 = elapsedRealtime - j;
            StringBuilder m17m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m17m("startTime=", j, " (time running = ");
            m17m.append(j2);
            m17m.append("ms)");
            printWriter.println(m17m.toString());
            printWriter.println("fgs tokens: [");
            ((IndentingPrintWriter) printWriter).increaseIndent();
            Iterator it = this.fgsTokens.iterator();
            while (it.hasNext()) {
                printWriter.println(String.valueOf((IBinder) it.next()));
            }
            ((IndentingPrintWriter) printWriter).decreaseIndent();
            printWriter.println("job summaries: [");
            ((IndentingPrintWriter) printWriter).increaseIndent();
            Iterator it2 = this.jobSummaries.iterator();
            while (it2.hasNext()) {
                printWriter.println(String.valueOf((UserVisibleJobSummary) it2.next()));
            }
            ((IndentingPrintWriter) printWriter).decreaseIndent();
            printWriter.println("]");
            ((IndentingPrintWriter) printWriter).decreaseIndent();
            printWriter.println("]");
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof StartTimeAndIdentifiers) && Intrinsics.areEqual(this.systemClock, ((StartTimeAndIdentifiers) obj).systemClock);
        }

        public final int hashCode() {
            return this.systemClock.hashCode();
        }

        public final String toString() {
            return "StartTimeAndIdentifiers(systemClock=" + this.systemClock + ")";
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    enum UIControl {
        NORMAL,
        HIDE_BUTTON,
        HIDE_ENTRY
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class UserPackage {
        public final String packageName;
        public boolean uiControlInitialized;
        public final Lazy uid$delegate;
        public final int userId;
        public int backgroundRestrictionExemptionReason = -1;
        public UIControl uiControl = UIControl.NORMAL;

        public UserPackage(int i, String str) {
            this.userId = i;
            this.packageName = str;
            this.uid$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.FgsManagerControllerImpl$UserPackage$uid$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    PackageManager packageManager = FgsManagerControllerImpl.this.packageManager;
                    FgsManagerControllerImpl.UserPackage userPackage = this;
                    return Integer.valueOf(packageManager.getPackageUidAsUser(userPackage.packageName, userPackage.userId));
                }
            });
        }

        public final void dump(PrintWriter printWriter) {
            printWriter.println("UserPackage: [");
            ((IndentingPrintWriter) printWriter).increaseIndent();
            KeyboardUI$$ExternalSyntheticOutline0.m134m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m77m(new StringBuilder("userId="), this.userId, printWriter, "packageName="), this.packageName, printWriter);
            if (!this.uiControlInitialized) {
                updateUiControl();
            }
            printWriter.println("uiControl=" + this.uiControl + " (reason=" + this.backgroundRestrictionExemptionReason + ")");
            ((IndentingPrintWriter) printWriter).decreaseIndent();
            printWriter.println("]");
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof UserPackage)) {
                return false;
            }
            UserPackage userPackage = (UserPackage) obj;
            return Intrinsics.areEqual(userPackage.packageName, this.packageName) && userPackage.userId == this.userId;
        }

        public final int hashCode() {
            return Objects.hash(Integer.valueOf(this.userId), this.packageName);
        }

        public final void updateUiControl() {
            UIControl uIControl;
            FgsManagerControllerImpl fgsManagerControllerImpl = FgsManagerControllerImpl.this;
            int backgroundRestrictionExemptionReason = fgsManagerControllerImpl.activityManager.getBackgroundRestrictionExemptionReason(((Number) this.uid$delegate.getValue()).intValue());
            this.backgroundRestrictionExemptionReason = backgroundRestrictionExemptionReason;
            if (backgroundRestrictionExemptionReason != 10 && backgroundRestrictionExemptionReason != 11) {
                if (backgroundRestrictionExemptionReason != 51 && backgroundRestrictionExemptionReason != 63) {
                    if (backgroundRestrictionExemptionReason == 65) {
                        uIControl = fgsManagerControllerImpl.showStopBtnForUserAllowlistedApps ? UIControl.NORMAL : UIControl.HIDE_BUTTON;
                    } else if (backgroundRestrictionExemptionReason != 300 && backgroundRestrictionExemptionReason != 318 && backgroundRestrictionExemptionReason != 320 && backgroundRestrictionExemptionReason != 327) {
                        if (backgroundRestrictionExemptionReason != 350) {
                            if (backgroundRestrictionExemptionReason != 55 && backgroundRestrictionExemptionReason != 56) {
                                switch (backgroundRestrictionExemptionReason) {
                                    case 322:
                                    case 323:
                                    case 324:
                                        break;
                                    default:
                                        uIControl = UIControl.NORMAL;
                                        break;
                                }
                            }
                        }
                    }
                    this.uiControl = uIControl;
                    this.uiControlInitialized = true;
                    fgsManagerControllerImpl.secFgsManagerController.log("updateUiControl[" + this.packageName + "]: " + backgroundRestrictionExemptionReason);
                }
                uIControl = UIControl.HIDE_ENTRY;
                this.uiControl = uIControl;
                this.uiControlInitialized = true;
                fgsManagerControllerImpl.secFgsManagerController.log("updateUiControl[" + this.packageName + "]: " + backgroundRestrictionExemptionReason);
            }
            uIControl = UIControl.HIDE_BUTTON;
            this.uiControl = uIControl;
            this.uiControlInitialized = true;
            fgsManagerControllerImpl.secFgsManagerController.log("updateUiControl[" + this.packageName + "]: " + backgroundRestrictionExemptionReason);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class UserVisibleJobObserver extends IUserVisibleJobObserver.Stub {
        public UserVisibleJobObserver() {
        }

        public final void onUserVisibleJobStateChanged(UserVisibleJobSummary userVisibleJobSummary, boolean z) {
            FgsManagerControllerImpl fgsManagerControllerImpl = FgsManagerControllerImpl.this;
            synchronized (fgsManagerControllerImpl.lock) {
                UserPackage userPackage = fgsManagerControllerImpl.new UserPackage(UserHandle.getUserId(userVisibleJobSummary.getCallingUid()), userVisibleJobSummary.getCallingPackageName());
                if (z) {
                    LinkedHashMap linkedHashMap = (LinkedHashMap) fgsManagerControllerImpl.runningTaskIdentifiers;
                    Object obj = linkedHashMap.get(userPackage);
                    if (obj == null) {
                        obj = new StartTimeAndIdentifiers(fgsManagerControllerImpl.systemClock);
                        linkedHashMap.put(userPackage, obj);
                    }
                    ((StartTimeAndIdentifiers) obj).jobSummaries.add(userVisibleJobSummary);
                } else {
                    StartTimeAndIdentifiers startTimeAndIdentifiers = (StartTimeAndIdentifiers) ((LinkedHashMap) fgsManagerControllerImpl.runningTaskIdentifiers).get(userPackage);
                    boolean z2 = false;
                    if (startTimeAndIdentifiers != null) {
                        startTimeAndIdentifiers.jobSummaries.remove(userVisibleJobSummary);
                        if (startTimeAndIdentifiers.fgsTokens.isEmpty() && startTimeAndIdentifiers.jobSummaries.isEmpty()) {
                            z2 = true;
                        }
                    }
                    if (z2) {
                        fgsManagerControllerImpl.runningTaskIdentifiers.remove(userPackage);
                    }
                }
                fgsManagerControllerImpl.updateNumberOfVisibleRunningPackagesLocked();
                FgsManagerControllerImpl.updateAppItemsLocked$default(fgsManagerControllerImpl);
                Unit unit = Unit.INSTANCE;
            }
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r1v10, types: [com.android.systemui.qs.FgsManagerControllerImpl$userTrackerCallback$1] */
    public FgsManagerControllerImpl(Context context, Executor executor, Executor executor2, SystemClock systemClock, IActivityManager iActivityManager, JobScheduler jobScheduler, PackageManager packageManager, UserTracker userTracker, DeviceConfigProxy deviceConfigProxy, DialogLaunchAnimator dialogLaunchAnimator, BroadcastDispatcher broadcastDispatcher, DumpManager dumpManager) {
        this.context = context;
        this.mainExecutor = executor;
        this.backgroundExecutor = executor2;
        this.systemClock = systemClock;
        this.activityManager = iActivityManager;
        this.jobScheduler = jobScheduler;
        this.packageManager = packageManager;
        this.userTracker = userTracker;
        this.deviceConfigProxy = deviceConfigProxy;
        this.broadcastDispatcher = broadcastDispatcher;
        this.dumpManager = dumpManager;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this._showFooterDot = MutableStateFlow;
        this.showFooterDot = FlowKt.asStateFlow(MutableStateFlow);
        this.showUserVisibleJobs = true;
        this.informJobSchedulerOfPendingAppStop = true;
        this.lock = new Object();
        this.currentProfileIds = new LinkedHashSet();
        this.runningTaskIdentifiers = new LinkedHashMap();
        this.appListAdapter = new AppListAdapter();
        this.runningApps = new ArrayMap();
        this.userTrackerCallback = new UserTracker.Callback() { // from class: com.android.systemui.qs.FgsManagerControllerImpl$userTrackerCallback$1
            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onProfilesChanged(List list) {
                FgsManagerControllerImpl fgsManagerControllerImpl = FgsManagerControllerImpl.this;
                synchronized (fgsManagerControllerImpl.lock) {
                    fgsManagerControllerImpl.currentProfileIds.clear();
                    Set set = fgsManagerControllerImpl.currentProfileIds;
                    ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        arrayList.add(Integer.valueOf(((UserInfo) it.next()).id));
                    }
                    set.addAll(arrayList);
                    fgsManagerControllerImpl.lastNumberOfVisiblePackages = 0;
                    fgsManagerControllerImpl.updateNumberOfVisibleRunningPackagesLocked();
                    Unit unit = Unit.INSTANCE;
                }
            }

            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanged(int i, Context context2) {
            }
        };
        this.foregroundServiceObserver = new ForegroundServiceObserver();
        this.userVisibleJobObserver = new UserVisibleJobObserver();
        this.secFgsManagerController = new SecFgsManagerControllerImpl(new Consumer() { // from class: com.android.systemui.qs.FgsManagerControllerImpl$secFgsManagerController$1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                FgsManagerControllerImpl.this.dialog = (SystemUIDialog) obj;
            }
        }, new Supplier() { // from class: com.android.systemui.qs.FgsManagerControllerImpl$secFgsManagerController$2
            @Override // java.util.function.Supplier
            public final Object get() {
                return FgsManagerControllerImpl.this.dialog;
            }
        }, new Runnable() { // from class: com.android.systemui.qs.FgsManagerControllerImpl$secFgsManagerController$3
            @Override // java.lang.Runnable
            public final void run() {
                final FgsManagerControllerImpl fgsManagerControllerImpl = FgsManagerControllerImpl.this;
                synchronized (fgsManagerControllerImpl.lock) {
                    if (fgsManagerControllerImpl.initialized) {
                        return;
                    }
                    fgsManagerControllerImpl.deviceConfigProxy.getClass();
                    fgsManagerControllerImpl.showUserVisibleJobs = DeviceConfig.getBoolean("systemui", "task_manager_show_user_visible_jobs", true);
                    fgsManagerControllerImpl.deviceConfigProxy.getClass();
                    fgsManagerControllerImpl.informJobSchedulerOfPendingAppStop = DeviceConfig.getBoolean("systemui", "task_manager_inform_job_scheduler_of_pending_app_stop", true);
                    try {
                        fgsManagerControllerImpl.activityManager.registerForegroundServiceObserver(fgsManagerControllerImpl.foregroundServiceObserver);
                        if (fgsManagerControllerImpl.showUserVisibleJobs) {
                            fgsManagerControllerImpl.jobScheduler.registerUserVisibleJobObserver(fgsManagerControllerImpl.userVisibleJobObserver);
                        }
                    } catch (RemoteException e) {
                        e.rethrowFromSystemServer();
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
                    DeviceConfigProxy deviceConfigProxy2 = fgsManagerControllerImpl.deviceConfigProxy;
                    Executor executor3 = fgsManagerControllerImpl.backgroundExecutor;
                    DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener = new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.systemui.qs.FgsManagerControllerImpl$init$1$2
                        public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                            StateFlowImpl stateFlowImpl = FgsManagerControllerImpl.this._showFooterDot;
                            stateFlowImpl.setValue(Boolean.valueOf(properties.getBoolean("task_manager_show_footer_dot", ((Boolean) stateFlowImpl.getValue()).booleanValue())));
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
                                        for (Map.Entry entry : ((LinkedHashMap) fgsManagerControllerImpl4.runningTaskIdentifiers).entrySet()) {
                                            FgsManagerControllerImpl.UserPackage userPackage = (FgsManagerControllerImpl.UserPackage) entry.getKey();
                                            FgsManagerControllerImpl.StartTimeAndIdentifiers startTimeAndIdentifiers = (FgsManagerControllerImpl.StartTimeAndIdentifiers) entry.getValue();
                                            if (!startTimeAndIdentifiers.fgsTokens.isEmpty()) {
                                                startTimeAndIdentifiers.jobSummaries.clear();
                                            } else {
                                                fgsManagerControllerImpl4.runningTaskIdentifiers.remove(userPackage);
                                            }
                                        }
                                        fgsManagerControllerImpl4.updateNumberOfVisibleRunningPackagesLocked();
                                        FgsManagerControllerImpl.updateAppItemsLocked$default(fgsManagerControllerImpl4);
                                        Unit unit = Unit.INSTANCE;
                                    }
                                }
                            }
                            FgsManagerControllerImpl fgsManagerControllerImpl5 = FgsManagerControllerImpl.this;
                            fgsManagerControllerImpl5.informJobSchedulerOfPendingAppStop = properties.getBoolean("show_stop_button_for_user_allowlisted_apps", fgsManagerControllerImpl5.informJobSchedulerOfPendingAppStop);
                        }
                    };
                    deviceConfigProxy2.getClass();
                    DeviceConfig.addOnPropertiesChangedListener("systemui", executor3, onPropertiesChangedListener);
                    StateFlowImpl stateFlowImpl = fgsManagerControllerImpl._showFooterDot;
                    fgsManagerControllerImpl.deviceConfigProxy.getClass();
                    stateFlowImpl.setValue(Boolean.valueOf(DeviceConfig.getBoolean("systemui", "task_manager_show_footer_dot", false)));
                    fgsManagerControllerImpl.deviceConfigProxy.getClass();
                    fgsManagerControllerImpl.showStopBtnForUserAllowlistedApps = DeviceConfig.getBoolean("systemui", "show_stop_button_for_user_allowlisted_apps", true);
                    fgsManagerControllerImpl.dumpManager.registerDumpable(fgsManagerControllerImpl);
                    BroadcastDispatcher.registerReceiver$default(fgsManagerControllerImpl.broadcastDispatcher, new BroadcastReceiver() { // from class: com.android.systemui.qs.FgsManagerControllerImpl$init$1$3
                        @Override // android.content.BroadcastReceiver
                        public final void onReceive(Context context2, Intent intent) {
                            if (Intrinsics.areEqual(intent.getAction(), "android.intent.action.SHOW_FOREGROUND_SERVICE_MANAGER")) {
                                FgsManagerControllerImpl.this.showDialog();
                            }
                        }
                    }, new IntentFilter("android.intent.action.SHOW_FOREGROUND_SERVICE_MANAGER"), fgsManagerControllerImpl.mainExecutor, null, 4, null, 40);
                    fgsManagerControllerImpl.initialized = true;
                    Unit unit = Unit.INSTANCE;
                }
            }
        });
        this.onNumberOfPackagesChangedListeners = new LinkedHashSet();
        this.onDialogDismissedListeners = new LinkedHashSet();
    }

    public static void updateAppItemsLocked$default(final FgsManagerControllerImpl fgsManagerControllerImpl) {
        SystemUIDialog systemUIDialog = fgsManagerControllerImpl.dialog;
        Executor executor = fgsManagerControllerImpl.backgroundExecutor;
        if (systemUIDialog == null) {
            executor.execute(new Runnable() { // from class: com.android.systemui.qs.FgsManagerControllerImpl$updateAppItemsLocked$1
                @Override // java.lang.Runnable
                public final void run() {
                    FgsManagerControllerImpl.this.runningApps.clear();
                }
            });
            return;
        }
        Map map = fgsManagerControllerImpl.runningTaskIdentifiers;
        final LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt__MapsJVMKt.mapCapacity(map.size()));
        for (Map.Entry entry : ((LinkedHashMap) map).entrySet()) {
            linkedHashMap.put(entry.getKey(), Long.valueOf(((StartTimeAndIdentifiers) entry.getValue()).startTime));
        }
        final Set set = CollectionsKt___CollectionsKt.toSet(fgsManagerControllerImpl.currentProfileIds);
        final boolean z = false;
        executor.execute(new Runnable() { // from class: com.android.systemui.qs.FgsManagerControllerImpl$updateAppItemsLocked$2
            @Override // java.lang.Runnable
            public final void run() {
                final FgsManagerControllerImpl fgsManagerControllerImpl2 = FgsManagerControllerImpl.this;
                Map map2 = linkedHashMap;
                Set set2 = set;
                boolean z2 = z;
                int i = FgsManagerControllerImpl.$r8$clinit;
                fgsManagerControllerImpl2.getClass();
                if (z2) {
                    Iterator it = map2.entrySet().iterator();
                    while (it.hasNext()) {
                        ((FgsManagerControllerImpl.UserPackage) ((Map.Entry) it.next()).getKey()).updateUiControl();
                    }
                }
                Set keySet = map2.keySet();
                ArrayList arrayList = new ArrayList();
                Iterator it2 = keySet.iterator();
                while (true) {
                    boolean z3 = false;
                    if (!it2.hasNext()) {
                        break;
                    }
                    Object next = it2.next();
                    FgsManagerControllerImpl.UserPackage userPackage = (FgsManagerControllerImpl.UserPackage) next;
                    if (set2.contains(Integer.valueOf(userPackage.userId))) {
                        if (!userPackage.uiControlInitialized) {
                            userPackage.updateUiControl();
                        }
                        if (userPackage.uiControl != FgsManagerControllerImpl.UIControl.HIDE_ENTRY) {
                            z3 = true;
                        }
                    }
                    if (z3) {
                        arrayList.add(next);
                    }
                }
                ArrayMap arrayMap = fgsManagerControllerImpl2.runningApps;
                Set keySet2 = arrayMap.keySet();
                ArrayList arrayList2 = new ArrayList();
                for (Object obj : keySet2) {
                    if (!map2.containsKey((FgsManagerControllerImpl.UserPackage) obj)) {
                        arrayList2.add(obj);
                    }
                }
                Iterator it3 = arrayList.iterator();
                while (it3.hasNext()) {
                    FgsManagerControllerImpl.UserPackage userPackage2 = (FgsManagerControllerImpl.UserPackage) it3.next();
                    String str = userPackage2.packageName;
                    PackageManager packageManager = fgsManagerControllerImpl2.packageManager;
                    int i2 = userPackage2.userId;
                    ApplicationInfo applicationInfoAsUser = packageManager.getApplicationInfoAsUser(str, 0, i2);
                    int i3 = userPackage2.userId;
                    String str2 = userPackage2.packageName;
                    Object obj2 = map2.get(userPackage2);
                    Intrinsics.checkNotNull(obj2);
                    long longValue = ((Number) obj2).longValue();
                    if (!userPackage2.uiControlInitialized) {
                        userPackage2.updateUiControl();
                    }
                    arrayMap.put(userPackage2, new FgsManagerControllerImpl.RunningApp(i3, str2, longValue, userPackage2.uiControl, packageManager.getApplicationLabel(applicationInfoAsUser), packageManager.getUserBadgedIcon(packageManager.getApplicationIcon(applicationInfoAsUser), UserHandle.of(i2))));
                    String str3 = userPackage2.packageName;
                    int i4 = userPackage2.userId;
                    Object obj3 = arrayMap.get(userPackage2);
                    Intrinsics.checkNotNull(obj3);
                    fgsManagerControllerImpl2.logEvent(false, str3, i4, ((FgsManagerControllerImpl.RunningApp) obj3).timeStarted);
                }
                Iterator it4 = arrayList2.iterator();
                while (it4.hasNext()) {
                    FgsManagerControllerImpl.UserPackage userPackage3 = (FgsManagerControllerImpl.UserPackage) it4.next();
                    Object obj4 = arrayMap.get(userPackage3);
                    Intrinsics.checkNotNull(obj4);
                    FgsManagerControllerImpl.RunningApp runningApp = (FgsManagerControllerImpl.RunningApp) obj4;
                    FgsManagerControllerImpl.RunningApp runningApp2 = new FgsManagerControllerImpl.RunningApp(runningApp.userId, runningApp.packageName, runningApp.timeStarted, runningApp.uiControl);
                    runningApp2.stopped = true;
                    runningApp2.appLabel = runningApp.appLabel;
                    runningApp2.icon = runningApp.icon;
                    arrayMap.put(userPackage3, runningApp2);
                }
                fgsManagerControllerImpl2.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.qs.FgsManagerControllerImpl$updateAppItems$4
                    /* JADX WARN: Type inference failed for: r3v0, types: [T, java.util.List] */
                    @Override // java.lang.Runnable
                    public final void run() {
                        FgsManagerControllerImpl fgsManagerControllerImpl3 = FgsManagerControllerImpl.this;
                        FgsManagerControllerImpl.AppListAdapter appListAdapter = fgsManagerControllerImpl3.appListAdapter;
                        final List sortedWith = CollectionsKt___CollectionsKt.sortedWith(CollectionsKt___CollectionsKt.toList(fgsManagerControllerImpl3.runningApps.values()), new Comparator() { // from class: com.android.systemui.qs.FgsManagerControllerImpl$updateAppItems$4$run$$inlined$sortedByDescending$1
                            @Override // java.util.Comparator
                            public final int compare(Object obj5, Object obj6) {
                                return ComparisonsKt__ComparisonsKt.compareValues(Long.valueOf(((FgsManagerControllerImpl.RunningApp) obj6).timeStarted), Long.valueOf(((FgsManagerControllerImpl.RunningApp) obj5).timeStarted));
                            }
                        });
                        appListAdapter.getClass();
                        final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
                        ref$ObjectRef.element = appListAdapter.data;
                        appListAdapter.data = sortedWith;
                        SecFgsManagerControllerImpl secFgsManagerControllerImpl = FgsManagerControllerImpl.this.secFgsManagerController;
                        boolean z4 = appListAdapter.getItemCount() == 0;
                        secFgsManagerControllerImpl.getClass();
                        secFgsManagerControllerImpl.log("updateNoItemTextView: " + z4);
                        TextView textView = secFgsManagerControllerImpl.noItemTextView;
                        if (textView != null) {
                            textView.setVisibility(z4 ? 0 : 8);
                        }
                        DiffUtil.calculateDiff(new DiffUtil.Callback() { // from class: com.android.systemui.qs.FgsManagerControllerImpl$AppListAdapter$setData$1
                            @Override // androidx.recyclerview.widget.DiffUtil.Callback
                            public final boolean areContentsTheSame(int i5, int i6) {
                                return ((FgsManagerControllerImpl.RunningApp) ((List) ref$ObjectRef.element).get(i5)).stopped == ((FgsManagerControllerImpl.RunningApp) sortedWith.get(i6)).stopped;
                            }

                            @Override // androidx.recyclerview.widget.DiffUtil.Callback
                            public final boolean areItemsTheSame(int i5, int i6) {
                                return Intrinsics.areEqual(((List) ref$ObjectRef.element).get(i5), sortedWith.get(i6));
                            }

                            @Override // androidx.recyclerview.widget.DiffUtil.Callback
                            public final int getNewListSize() {
                                return sortedWith.size();
                            }

                            @Override // androidx.recyclerview.widget.DiffUtil.Callback
                            public final int getOldListSize() {
                                return ((List) ref$ObjectRef.element).size();
                            }
                        }).dispatchUpdatesTo(new AdapterListUpdateCallback(appListAdapter));
                        SecFgsManagerControllerImpl secFgsManagerControllerImpl2 = FgsManagerControllerImpl.this.secFgsManagerController;
                        SystemUIDialog systemUIDialog2 = (SystemUIDialog) secFgsManagerControllerImpl2.dialogSupplier.get();
                        if (systemUIDialog2 != null) {
                            if (systemUIDialog2.isShowing()) {
                                systemUIDialog2 = null;
                            }
                            if (systemUIDialog2 != null) {
                                secFgsManagerControllerImpl2.log("show dialog");
                                systemUIDialog2.show();
                                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.sCurrentScreenID, "5153");
                            }
                        }
                    }
                });
            }
        });
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        PrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter);
        synchronized (this.lock) {
            indentingPrintWriter.println("current user profiles = " + this.currentProfileIds);
            indentingPrintWriter.println("newChangesSinceDialogWasShown=" + this.newChangesSinceDialogWasDismissed);
            indentingPrintWriter.println("Running task identifiers: [");
            indentingPrintWriter.increaseIndent();
            for (Map.Entry entry : ((LinkedHashMap) this.runningTaskIdentifiers).entrySet()) {
                UserPackage userPackage = (UserPackage) entry.getKey();
                StartTimeAndIdentifiers startTimeAndIdentifiers = (StartTimeAndIdentifiers) entry.getValue();
                indentingPrintWriter.println("{");
                indentingPrintWriter.increaseIndent();
                userPackage.dump(indentingPrintWriter);
                startTimeAndIdentifiers.dump(indentingPrintWriter);
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println("}");
            }
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println("]");
            indentingPrintWriter.println("Loaded package UI info: [");
            indentingPrintWriter.increaseIndent();
            for (Map.Entry entry2 : this.runningApps.entrySet()) {
                UserPackage userPackage2 = (UserPackage) entry2.getKey();
                RunningApp runningApp = (RunningApp) entry2.getValue();
                indentingPrintWriter.println("{");
                indentingPrintWriter.increaseIndent();
                userPackage2.dump(indentingPrintWriter);
                runningApp.dump(indentingPrintWriter);
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println("}");
            }
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println("]");
            Unit unit = Unit.INSTANCE;
        }
    }

    public final int getNumRunningPackages() {
        int numVisiblePackagesLocked;
        synchronized (this.lock) {
            numVisiblePackagesLocked = getNumVisiblePackagesLocked();
            this.secFgsManagerController.log("numRunningPackages: " + numVisiblePackagesLocked);
        }
        return numVisiblePackagesLocked;
    }

    public final int getNumVisibleButtonsLocked() {
        Set<UserPackage> keySet = ((LinkedHashMap) this.runningTaskIdentifiers).keySet();
        if ((keySet instanceof Collection) && keySet.isEmpty()) {
            return 0;
        }
        int i = 0;
        for (UserPackage userPackage : keySet) {
            if (!userPackage.uiControlInitialized) {
                userPackage.updateUiControl();
            }
            if ((userPackage.uiControl != UIControl.HIDE_BUTTON && this.currentProfileIds.contains(Integer.valueOf(userPackage.userId))) && (i = i + 1) < 0) {
                throw new ArithmeticException("Count overflow has happened.");
            }
        }
        return i;
    }

    public final int getNumVisiblePackagesLocked() {
        Set<UserPackage> keySet = ((LinkedHashMap) this.runningTaskIdentifiers).keySet();
        if ((keySet instanceof Collection) && keySet.isEmpty()) {
            return 0;
        }
        int i = 0;
        for (UserPackage userPackage : keySet) {
            if (!userPackage.uiControlInitialized) {
                userPackage.updateUiControl();
            }
            if ((userPackage.uiControl != UIControl.HIDE_ENTRY && this.currentProfileIds.contains(Integer.valueOf(userPackage.userId))) && (i = i + 1) < 0) {
                throw new ArithmeticException("Count overflow has happened.");
            }
        }
        return i;
    }

    public final void logEvent(final boolean z, final String str, final int i, final long j) {
        ((SystemClockImpl) this.systemClock).getClass();
        final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        final int i2 = z ? 2 : 1;
        this.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.qs.FgsManagerControllerImpl$logEvent$1
            @Override // java.lang.Runnable
            public final void run() {
                int packageUidAsUser = FgsManagerControllerImpl.this.packageManager.getPackageUidAsUser(str, i);
                int i3 = i2;
                long j2 = elapsedRealtime - j;
                StatsEvent.Builder newBuilder = StatsEvent.newBuilder();
                newBuilder.setAtomId(450);
                newBuilder.writeInt(packageUidAsUser);
                newBuilder.addBooleanAnnotation((byte) 1, true);
                newBuilder.writeInt(i3);
                newBuilder.writeLong(j2);
                newBuilder.usePooledBuffer();
                StatsLog.write(newBuilder.build());
                SecFgsManagerControllerImpl secFgsManagerControllerImpl = FgsManagerControllerImpl.this.secFgsManagerController;
                boolean z2 = z;
                String str2 = str;
                long j3 = elapsedRealtime - j;
                secFgsManagerControllerImpl.getClass();
                secFgsManagerControllerImpl.log("saLog[stopped:" + z2 + "]: [uid:" + packageUidAsUser + "]:[packageName:" + str2 + "]:[duration:" + j3 + "]");
                if (z2) {
                    SystemUIAnalytics.sendEventLog(SystemUIAnalytics.sCurrentScreenID, "5138", str2, j3);
                }
            }
        });
    }

    public final void showDialog() {
        WindowManager.LayoutParams attributes;
        final SecFgsManagerControllerImpl secFgsManagerControllerImpl = this.secFgsManagerController;
        Consumer consumer = new Consumer() { // from class: com.android.systemui.qs.FgsManagerControllerImpl$showDialog$1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((RecyclerView) obj).setAdapter(FgsManagerControllerImpl.this.appListAdapter);
            }
        };
        Executor executor = this.backgroundExecutor;
        Context context = this.context;
        final Object obj = this.lock;
        final Consumer consumer2 = new Consumer() { // from class: com.android.systemui.qs.FgsManagerControllerImpl$showDialog$2
            @Override // java.util.function.Consumer
            public final void accept(Object obj2) {
                FgsManagerControllerImpl.this.newChangesSinceDialogWasDismissed = ((Boolean) obj2).booleanValue();
            }
        };
        final Runnable runnable = new Runnable() { // from class: com.android.systemui.qs.FgsManagerControllerImpl$showDialog$3
            @Override // java.lang.Runnable
            public final void run() {
                FgsManagerControllerImpl fgsManagerControllerImpl = FgsManagerControllerImpl.this;
                for (final FgsManagerController.OnDialogDismissedListener onDialogDismissedListener : fgsManagerControllerImpl.onDialogDismissedListeners) {
                    fgsManagerControllerImpl.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.qs.FgsManagerControllerImpl$showDialog$3$1$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            ChannelExt.trySendWithFailureLogging$default(ChannelExt.INSTANCE, ((C2178xdf89411) FgsManagerController.OnDialogDismissedListener.this).$$this$conflatedCallbackFlow, Unit.INSTANCE, "ForegroundServicesRepositoryImpl");
                        }
                    });
                }
            }
        };
        final Runnable runnable2 = new Runnable() { // from class: com.android.systemui.qs.FgsManagerControllerImpl$showDialog$4
            @Override // java.lang.Runnable
            public final void run() {
                FgsManagerControllerImpl.updateAppItemsLocked$default(FgsManagerControllerImpl.this);
            }
        };
        Runnable runnable3 = new Runnable() { // from class: com.android.systemui.qs.FgsManagerControllerImpl$showDialog$5
            @Override // java.lang.Runnable
            public final void run() {
                Iterator it = ((LinkedHashMap) FgsManagerControllerImpl.this.runningTaskIdentifiers).keySet().iterator();
                while (it.hasNext()) {
                    ((FgsManagerControllerImpl.UserPackage) it.next()).updateUiControl();
                }
            }
        };
        if (secFgsManagerControllerImpl.dialogSupplier.get() != null) {
            return;
        }
        secFgsManagerControllerImpl.log("setup dialog");
        synchronized (obj) {
            runnable3.run();
            Unit unit = Unit.INSTANCE;
        }
        SystemUIDialog systemUIDialog = new SystemUIDialog(context, 2132018528);
        systemUIDialog.setTitle(R.string.sec_fgs_manager_dialog_title);
        systemUIDialog.setMessage(R.string.sec_fgs_manager_dialog_message);
        systemUIDialog.setNeutralButton(R.string.sec_quick_settings_done, null);
        if (QpRune.QUICK_TABLET) {
            Window window = systemUIDialog.getWindow();
            if (window != null) {
                window.setGravity(8388659);
            }
            Window window2 = systemUIDialog.getWindow();
            if (window2 != null && (attributes = window2.getAttributes()) != null) {
                int dimensionPixelSize = systemUIDialog.getContext().getResources().getDimensionPixelSize(R.dimen.sec_fgs_side_margin_tablet);
                attributes.x = dimensionPixelSize;
                attributes.y = dimensionPixelSize;
            }
        } else {
            Window window3 = systemUIDialog.getWindow();
            if (window3 != null) {
                window3.setGravity(81);
            }
        }
        View inflate = LayoutInflater.from(systemUIDialog.getContext()).inflate(R.layout.sec_fgs_manager_container, (ViewGroup) systemUIDialog.getListView(), false);
        Context context2 = inflate.getContext();
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.sec_fgs_manager_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context2));
        consumer.accept(recyclerView);
        systemUIDialog.setView(inflate);
        secFgsManagerControllerImpl.noItemTextView = (TextView) inflate.findViewById(R.id.sec_fgs_manager_no_item_text_view);
        secFgsManagerControllerImpl.dialogConsumer.accept(systemUIDialog);
        systemUIDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.qs.SecFgsManagerControllerImpl$setOnDismissListener$1
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                consumer2.accept(Boolean.FALSE);
                Object obj2 = obj;
                SecFgsManagerControllerImpl secFgsManagerControllerImpl2 = secFgsManagerControllerImpl;
                Runnable runnable4 = runnable2;
                synchronized (obj2) {
                    secFgsManagerControllerImpl2.dialogConsumer.accept(null);
                    secFgsManagerControllerImpl2.noItemTextView = null;
                    runnable4.run();
                    Unit unit2 = Unit.INSTANCE;
                }
                runnable.run();
                secFgsManagerControllerImpl.log("dismiss dialog");
            }
        });
        executor.execute(new Runnable() { // from class: com.android.systemui.qs.SecFgsManagerControllerImpl$setupDialog$3
            @Override // java.lang.Runnable
            public final void run() {
                Object obj2 = obj;
                Runnable runnable4 = runnable2;
                synchronized (obj2) {
                    runnable4.run();
                    Unit unit2 = Unit.INSTANCE;
                }
            }
        });
    }

    public final void updateNumberOfVisibleRunningPackagesLocked() {
        final int numVisiblePackagesLocked = getNumVisiblePackagesLocked();
        if (numVisiblePackagesLocked != this.lastNumberOfVisiblePackages) {
            this.lastNumberOfVisiblePackages = numVisiblePackagesLocked;
            this.newChangesSinceDialogWasDismissed = true;
            for (final FgsManagerController.OnNumberOfPackagesChangedListener onNumberOfPackagesChangedListener : this.onNumberOfPackagesChangedListeners) {
                this.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.qs.FgsManagerControllerImpl$updateNumberOfVisibleRunningPackagesLocked$1$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        FgsManagerController.OnNumberOfPackagesChangedListener.this.onNumberOfPackagesChanged(numVisiblePackagesLocked);
                    }
                });
            }
            this.secFgsManagerController.log("updateNumberOfVisibleRunningPackagesLocked: " + numVisiblePackagesLocked);
        }
    }

    @Override // com.android.systemui.p016qs.FgsManagerController
    public final int visibleButtonsCount() {
        int numVisibleButtonsLocked;
        synchronized (this.lock) {
            numVisibleButtonsLocked = getNumVisibleButtonsLocked();
        }
        return numVisibleButtonsLocked;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class RunningApp {
        public CharSequence appLabel;
        public Drawable icon;
        public final String packageName;
        public boolean stopped;
        public final long timeStarted;
        public final UIControl uiControl;
        public final int userId;

        public RunningApp(int i, String str, long j, UIControl uIControl) {
            this.userId = i;
            this.packageName = str;
            this.timeStarted = j;
            this.uiControl = uIControl;
            this.appLabel = "";
        }

        public final void dump(PrintWriter printWriter) {
            printWriter.println("RunningApp: [");
            ((IndentingPrintWriter) printWriter).increaseIndent();
            StringBuilder m77m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m77m(new StringBuilder("userId="), this.userId, printWriter, "packageName=");
            m77m.append(this.packageName);
            printWriter.println(m77m.toString());
            long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            long j = this.timeStarted;
            long j2 = elapsedRealtime - j;
            StringBuilder m17m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m17m("timeStarted=", j, " (time since start = ");
            m17m.append(j2);
            m17m.append("ms)");
            printWriter.println(m17m.toString());
            printWriter.println("uiControl=" + this.uiControl);
            printWriter.println("appLabel=" + ((Object) this.appLabel));
            printWriter.println("icon=" + this.icon);
            ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("stopped=", this.stopped, printWriter);
            ((IndentingPrintWriter) printWriter).decreaseIndent();
            printWriter.println("]");
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof RunningApp)) {
                return false;
            }
            RunningApp runningApp = (RunningApp) obj;
            return this.userId == runningApp.userId && Intrinsics.areEqual(this.packageName, runningApp.packageName) && this.timeStarted == runningApp.timeStarted && this.uiControl == runningApp.uiControl;
        }

        public final int hashCode() {
            return this.uiControl.hashCode() + TraceMetadata$$ExternalSyntheticOutline0.m51m(this.timeStarted, AppInfo$$ExternalSyntheticOutline0.m41m(this.packageName, Integer.hashCode(this.userId) * 31, 31), 31);
        }

        public final String toString() {
            return "RunningApp(userId=" + this.userId + ", packageName=" + this.packageName + ", timeStarted=" + this.timeStarted + ", uiControl=" + this.uiControl + ")";
        }

        public RunningApp(int i, String str, long j, UIControl uIControl, CharSequence charSequence, Drawable drawable) {
            this(i, str, j, uIControl);
            this.appLabel = charSequence;
            this.icon = drawable;
        }
    }
}
