package com.android.systemui.qs;

import android.app.IActivityManager;
import android.app.IForegroundServiceObserver;
import android.app.job.IUserVisibleJobObserver;
import android.app.job.JobScheduler;
import android.app.job.UserVisibleJobSummary;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.os.UserHandle;
import android.text.format.DateUtils;
import android.util.ArrayMap;
import android.util.IndentingPrintWriter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.compose.runtime.snapshots.SnapshotStateObserver$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.AdapterListUpdateCallback;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecurityContainer$UserSwitcherViewMode$2$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.animation.DialogCuj;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.animation.Expandable;
import com.android.systemui.animation.TransitionAnimator;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.qs.FgsManagerController;
import com.android.systemui.qs.FgsManagerControllerImpl;
import com.android.systemui.qs.FgsManagerControllerImpl.UserPackage;
import com.android.systemui.qs.footer.data.repository.ForegroundServicesRepositoryImpl$hasNewChanges$1$dialogDismissedEvents$1$listener$1;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.shade.domain.interactor.ShadeDialogContextInteractor;
import com.android.systemui.shade.domain.interactor.ShadeDialogContextInteractorImpl;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.util.DeviceConfigProxy;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.time.SystemClock;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
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
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes2.dex */
public final class FgsManagerControllerImpl implements Dumpable, FgsManagerController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final StateFlowImpl _showFooterDot;
    public final IActivityManager activityManager;
    public final AppListAdapter appListAdapter;
    public final Executor backgroundExecutor;
    public final BroadcastDispatcher broadcastDispatcher;
    public final Set currentProfileIds;
    public final DeviceConfigProxy deviceConfigProxy;
    public SystemUIDialog dialog;
    public final DialogTransitionAnimator dialogTransitionAnimator;
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
    public final Resources resources;
    public final ArrayMap runningApps;
    public final Map runningTaskIdentifiers;
    public final SecFgsManagerController secFgsManagerController;
    public final ShadeDialogContextInteractor shadeDialogContextRepository;
    public final ReadonlyStateFlow showFooterDot;
    public boolean showStopBtnForUserAllowlistedApps;
    public boolean showUserVisibleJobs;
    public final Lazy stoppableApps$delegate;
    public final SystemClock systemClock;
    public final SystemUIDialog.Factory systemUIDialogFactory;
    public final UserTracker userTracker;
    public final FgsManagerControllerImpl$userTrackerCallback$1 userTrackerCallback;
    public final UserVisibleJobObserver userVisibleJobObserver;
    public final Lazy vendorStoppableApps$delegate;

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

    public final class AppListAdapter extends RecyclerView.Adapter {
        public final Object lock = new Object();
        public List data = EmptyList.INSTANCE;

        public AppListAdapter() {
        }

        public static void updateContentDescription(AppItemViewHolder appItemViewHolder) {
            appItemViewHolder.stopButton.setContentDescription(((Object) appItemViewHolder.appLabelView.getText()) + ", " + ((Object) appItemViewHolder.stopButton.getText()));
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final int getItemCount() {
            return this.data.size();
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r8v1, types: [T, java.lang.Object] */
        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            ?? r8;
            final AppItemViewHolder appItemViewHolder = (AppItemViewHolder) viewHolder;
            final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
            synchronized (this.lock) {
                r8 = this.data.get(i);
                ref$ObjectRef.element = r8;
                Unit unit = Unit.INSTANCE;
            }
            final FgsManagerControllerImpl fgsManagerControllerImpl = FgsManagerControllerImpl.this;
            appItemViewHolder.iconView.setImageDrawable(((RunningApp) r8).icon);
            appItemViewHolder.appLabelView.setText(((RunningApp) ref$ObjectRef.element).appLabel);
            appItemViewHolder.durationView.setText(DateUtils.formatDuration(Math.max(fgsManagerControllerImpl.systemClock.elapsedRealtime() - ((RunningApp) ref$ObjectRef.element).timeStarted, 60000L), 10));
            appItemViewHolder.stopButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.FgsManagerControllerImpl$AppListAdapter$onBindViewHolder$2$1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    appItemViewHolder.stopButton.setText(R.string.sec_fgs_manager_app_item_stop_button_stopped_label);
                    FgsManagerControllerImpl.AppListAdapter appListAdapter = this;
                    FgsManagerControllerImpl.AppItemViewHolder appItemViewHolder2 = appItemViewHolder;
                    appListAdapter.getClass();
                    FgsManagerControllerImpl.AppListAdapter.updateContentDescription(appItemViewHolder2);
                    FgsManagerControllerImpl fgsManagerControllerImpl2 = fgsManagerControllerImpl;
                    FgsManagerControllerImpl.RunningApp runningApp = (FgsManagerControllerImpl.RunningApp) ref$ObjectRef.element;
                    int i2 = runningApp.userId;
                    String str = runningApp.packageName;
                    long j = runningApp.timeStarted;
                    int i3 = FgsManagerControllerImpl.$r8$clinit;
                    fgsManagerControllerImpl2.backgroundExecutor.execute(new FgsManagerControllerImpl$logEvent$1(fgsManagerControllerImpl2, str, i2, 2, fgsManagerControllerImpl2.systemClock.elapsedRealtime(), j, true));
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
            updateContentDescription(appItemViewHolder);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new AppItemViewHolder(KeyguardSecurityContainer$UserSwitcherViewMode$2$$ExternalSyntheticOutline0.m(viewGroup, R.layout.sec_fgs_manager_app_item, viewGroup, false));
        }
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class ForegroundServiceObserver extends IForegroundServiceObserver.Stub {
        public ForegroundServiceObserver() {
        }

        public final void onForegroundStateChanged(IBinder iBinder, String str, int i, boolean z) {
            FgsManagerControllerImpl fgsManagerControllerImpl = FgsManagerControllerImpl.this;
            synchronized (fgsManagerControllerImpl.lock) {
                try {
                    if (fgsManagerControllerImpl.secFgsManagerController != null) {
                        SecFgsManagerController.log("onForegroundStateChanged: [dialog:" + (fgsManagerControllerImpl.dialog != null) + "]:[isForeground:" + z + "]:[packageName:" + str + "]:[token:" + iBinder + "]:[userId:" + i + "]");
                    }
                    UserPackage userPackage = fgsManagerControllerImpl.new UserPackage(i, str);
                    if (z) {
                        LinkedHashMap linkedHashMap = (LinkedHashMap) fgsManagerControllerImpl.runningTaskIdentifiers;
                        Object startTimeAndIdentifiers = linkedHashMap.get(userPackage);
                        if (startTimeAndIdentifiers == null) {
                            startTimeAndIdentifiers = new StartTimeAndIdentifiers(fgsManagerControllerImpl.systemClock);
                            linkedHashMap.put(userPackage, startTimeAndIdentifiers);
                        }
                        ((StartTimeAndIdentifiers) startTimeAndIdentifiers).fgsTokens.add(iBinder);
                    } else {
                        StartTimeAndIdentifiers startTimeAndIdentifiers2 = (StartTimeAndIdentifiers) ((LinkedHashMap) fgsManagerControllerImpl.runningTaskIdentifiers).get(userPackage);
                        if (startTimeAndIdentifiers2 != null) {
                            startTimeAndIdentifiers2.fgsTokens.remove(iBinder);
                            if (startTimeAndIdentifiers2.fgsTokens.isEmpty() && startTimeAndIdentifiers2.jobSummaries.isEmpty()) {
                                fgsManagerControllerImpl.runningTaskIdentifiers.remove(userPackage);
                            }
                        }
                    }
                    fgsManagerControllerImpl.updateNumberOfVisibleRunningPackagesLocked();
                    fgsManagerControllerImpl.updateAppItemsLocked(false);
                    Unit unit = Unit.INSTANCE;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final class StartTimeAndIdentifiers {
        public final Set fgsTokens = new LinkedHashSet();
        public final Set jobSummaries = new LinkedHashSet();
        public final long startTime;
        public final SystemClock systemClock;

        public StartTimeAndIdentifiers(SystemClock systemClock) {
            this.systemClock = systemClock;
            this.startTime = systemClock.elapsedRealtime();
        }

        public final void dump(PrintWriter printWriter) {
            printWriter.println("StartTimeAndIdentifiers: [");
            ((IndentingPrintWriter) printWriter).increaseIndent();
            long jElapsedRealtime = this.systemClock.elapsedRealtime();
            long j = this.startTime;
            long j2 = jElapsedRealtime - j;
            StringBuilder sbM = SnapshotStateObserver$$ExternalSyntheticOutline0.m("startTime=", j, " (time running = ");
            sbM.append(j2);
            sbM.append("ms)");
            printWriter.println(sbM.toString());
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

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    final class UIControl {
        public static final /* synthetic */ UIControl[] $VALUES;
        public static final UIControl HIDE_BUTTON;
        public static final UIControl HIDE_ENTRY;
        public static final UIControl NORMAL;

        static {
            UIControl uIControl = new UIControl("NORMAL", 0);
            NORMAL = uIControl;
            UIControl uIControl2 = new UIControl("HIDE_BUTTON", 1);
            HIDE_BUTTON = uIControl2;
            UIControl uIControl3 = new UIControl("HIDE_ENTRY", 2);
            HIDE_ENTRY = uIControl3;
            UIControl[] uIControlArr = {uIControl, uIControl2, uIControl3};
            $VALUES = uIControlArr;
            EnumEntriesKt.enumEntries(uIControlArr);
        }

        private UIControl(String str, int i) {
        }

        public static UIControl valueOf(String str) {
            return (UIControl) Enum.valueOf(UIControl.class, str);
        }

        public static UIControl[] values() {
            return (UIControl[]) $VALUES.clone();
        }
    }

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
            this.uid$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.FgsManagerControllerImpl$UserPackage$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    PackageManager packageManager = fgsManagerControllerImpl.packageManager;
                    FgsManagerControllerImpl.UserPackage userPackage = this;
                    return Integer.valueOf(packageManager.getPackageUidAsUser(userPackage.packageName, userPackage.userId));
                }
            });
        }

        public final void dump(PrintWriter printWriter) {
            printWriter.println("UserPackage: [");
            ((IndentingPrintWriter) printWriter).increaseIndent();
            StringBuilder sbM = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("userId="), this.userId, printWriter, "packageName=");
            sbM.append(this.packageName);
            printWriter.println(sbM.toString());
            printWriter.println("uiControl=" + getUiControl() + " (reason=" + this.backgroundRestrictionExemptionReason + ")");
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

        public final UIControl getUiControl() {
            if (!this.uiControlInitialized) {
                updateUiControl();
            }
            return this.uiControl;
        }

        public final int hashCode() {
            return Objects.hash(Integer.valueOf(this.userId), this.packageName);
        }

        /* JADX WARN: Removed duplicated region for block: B:32:0x0056  */
        /* JADX WARN: Removed duplicated region for block: B:33:0x0059  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void updateUiControl() {
            UIControl uIControl;
            FgsManagerControllerImpl fgsManagerControllerImpl = FgsManagerControllerImpl.this;
            int backgroundRestrictionExemptionReason = fgsManagerControllerImpl.activityManager.getBackgroundRestrictionExemptionReason(((Number) this.uid$delegate.getValue()).intValue());
            this.backgroundRestrictionExemptionReason = backgroundRestrictionExemptionReason;
            if (backgroundRestrictionExemptionReason == 10 || backgroundRestrictionExemptionReason == 11) {
                uIControl = UIControl.HIDE_BUTTON;
            } else if (backgroundRestrictionExemptionReason == 51 || backgroundRestrictionExemptionReason == 63) {
                uIControl = UIControl.HIDE_ENTRY;
            } else if (backgroundRestrictionExemptionReason == 65) {
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
            String[] strArr = (String[]) fgsManagerControllerImpl.stoppableApps$delegate.getValue();
            String str = this.packageName;
            if (ArraysKt___ArraysKt.contains(strArr, str) || ArraysKt___ArraysKt.contains((String[]) fgsManagerControllerImpl.vendorStoppableApps$delegate.getValue(), str)) {
                this.uiControl = UIControl.NORMAL;
            }
            this.uiControlInitialized = true;
            if (fgsManagerControllerImpl.secFgsManagerController != null) {
                SecFgsManagerController.log("updateUiControl[" + str + "]: " + this.backgroundRestrictionExemptionReason);
            }
        }
    }

    public final class UserVisibleJobObserver extends IUserVisibleJobObserver.Stub {
        public UserVisibleJobObserver() {
        }

        public final void onUserVisibleJobStateChanged(UserVisibleJobSummary userVisibleJobSummary, boolean z) {
            FgsManagerControllerImpl fgsManagerControllerImpl = FgsManagerControllerImpl.this;
            synchronized (fgsManagerControllerImpl.lock) {
                try {
                    UserPackage userPackage = fgsManagerControllerImpl.new UserPackage(UserHandle.getUserId(userVisibleJobSummary.getCallingUid()), userVisibleJobSummary.getCallingPackageName());
                    if (z) {
                        LinkedHashMap linkedHashMap = (LinkedHashMap) fgsManagerControllerImpl.runningTaskIdentifiers;
                        Object startTimeAndIdentifiers = linkedHashMap.get(userPackage);
                        if (startTimeAndIdentifiers == null) {
                            startTimeAndIdentifiers = new StartTimeAndIdentifiers(fgsManagerControllerImpl.systemClock);
                            linkedHashMap.put(userPackage, startTimeAndIdentifiers);
                        }
                        ((StartTimeAndIdentifiers) startTimeAndIdentifiers).jobSummaries.add(userVisibleJobSummary);
                    } else {
                        StartTimeAndIdentifiers startTimeAndIdentifiers2 = (StartTimeAndIdentifiers) ((LinkedHashMap) fgsManagerControllerImpl.runningTaskIdentifiers).get(userPackage);
                        if (startTimeAndIdentifiers2 != null) {
                            startTimeAndIdentifiers2.jobSummaries.remove(userVisibleJobSummary);
                            if (startTimeAndIdentifiers2.fgsTokens.isEmpty() && startTimeAndIdentifiers2.jobSummaries.isEmpty()) {
                                fgsManagerControllerImpl.runningTaskIdentifiers.remove(userPackage);
                            }
                        }
                    }
                    fgsManagerControllerImpl.updateNumberOfVisibleRunningPackagesLocked();
                    fgsManagerControllerImpl.updateAppItemsLocked(false);
                    Unit unit = Unit.INSTANCE;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r1v10, types: [com.android.systemui.qs.FgsManagerControllerImpl$userTrackerCallback$1] */
    public FgsManagerControllerImpl(Resources resources, Executor executor, Executor executor2, SystemClock systemClock, IActivityManager iActivityManager, JobScheduler jobScheduler, PackageManager packageManager, UserTracker userTracker, DeviceConfigProxy deviceConfigProxy, DialogTransitionAnimator dialogTransitionAnimator, BroadcastDispatcher broadcastDispatcher, DumpManager dumpManager, SystemUIDialog.Factory factory, ShadeDialogContextInteractor shadeDialogContextInteractor, ConfigurationInteractor configurationInteractor, CoroutineScope coroutineScope) {
        this.resources = resources;
        this.mainExecutor = executor;
        this.backgroundExecutor = executor2;
        this.systemClock = systemClock;
        this.activityManager = iActivityManager;
        this.jobScheduler = jobScheduler;
        this.packageManager = packageManager;
        this.userTracker = userTracker;
        this.deviceConfigProxy = deviceConfigProxy;
        this.dialogTransitionAnimator = dialogTransitionAnimator;
        this.broadcastDispatcher = broadcastDispatcher;
        this.dumpManager = dumpManager;
        this.systemUIDialogFactory = factory;
        this.shadeDialogContextRepository = shadeDialogContextInteractor;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this._showFooterDot = stateFlowImplMutableStateFlow;
        this.showFooterDot = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
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
                FgsManagerControllerImpl fgsManagerControllerImpl = this.this$0;
                synchronized (fgsManagerControllerImpl.lock) {
                    try {
                        fgsManagerControllerImpl.currentProfileIds.clear();
                        Set set = fgsManagerControllerImpl.currentProfileIds;
                        List list2 = list;
                        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
                        Iterator it = list2.iterator();
                        while (it.hasNext()) {
                            arrayList.add(Integer.valueOf(((UserInfo) it.next()).id));
                        }
                        set.addAll(arrayList);
                        fgsManagerControllerImpl.lastNumberOfVisiblePackages = 0;
                        fgsManagerControllerImpl.updateNumberOfVisibleRunningPackagesLocked();
                        Unit unit = Unit.INSTANCE;
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }

            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanged(int i, Context context) {
            }
        };
        this.foregroundServiceObserver = new ForegroundServiceObserver();
        this.userVisibleJobObserver = new UserVisibleJobObserver();
        this.stoppableApps$delegate = LazyKt__LazyJVMKt.lazy(new FgsManagerControllerImpl$$ExternalSyntheticLambda0(this, 0));
        this.vendorStoppableApps$delegate = LazyKt__LazyJVMKt.lazy(new FgsManagerControllerImpl$$ExternalSyntheticLambda0(this, 1));
        this.secFgsManagerController = new SecFgsManagerController(new FgsManagerControllerImpl$$ExternalSyntheticLambda2(this, 0), new FgsManagerControllerImpl$$ExternalSyntheticLambda0(this, 2), new FgsManagerControllerImpl$secFgsManagerController$3(this), configurationInteractor, coroutineScope);
        this.onNumberOfPackagesChangedListeners = new LinkedHashSet();
        this.onDialogDismissedListeners = new LinkedHashSet();
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        PrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter);
        synchronized (this.lock) {
            try {
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
                    runningApp.dump(indentingPrintWriter, this.systemClock);
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.println("}");
                }
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println("]");
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int getNumRunningPackages() {
        int numVisiblePackagesLocked;
        synchronized (this.lock) {
            numVisiblePackagesLocked = getNumVisiblePackagesLocked();
            if (this.secFgsManagerController != null) {
                SecFgsManagerController.log("numRunningPackages: " + numVisiblePackagesLocked);
            }
        }
        return numVisiblePackagesLocked;
    }

    public final int getNumVisibleButtonsLocked() {
        Set<UserPackage> setKeySet = ((LinkedHashMap) this.runningTaskIdentifiers).keySet();
        int i = 0;
        if ((setKeySet instanceof Collection) && setKeySet.isEmpty()) {
            return 0;
        }
        for (UserPackage userPackage : setKeySet) {
            if (userPackage.getUiControl() != UIControl.HIDE_BUTTON && this.currentProfileIds.contains(Integer.valueOf(userPackage.userId)) && (i = i + 1) < 0) {
                CollectionsKt__CollectionsKt.throwCountOverflow();
                throw null;
            }
        }
        return i;
    }

    public final int getNumVisiblePackagesLocked() {
        Set<UserPackage> setKeySet = ((LinkedHashMap) this.runningTaskIdentifiers).keySet();
        int i = 0;
        if ((setKeySet instanceof Collection) && setKeySet.isEmpty()) {
            return 0;
        }
        for (UserPackage userPackage : setKeySet) {
            if (userPackage.getUiControl() != UIControl.HIDE_ENTRY && this.currentProfileIds.contains(Integer.valueOf(userPackage.userId)) && (i = i + 1) < 0) {
                CollectionsKt__CollectionsKt.throwCountOverflow();
                throw null;
            }
        }
        return i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void showDialog$2() throws Resources.NotFoundException {
        final SecFgsManagerController secFgsManagerController = this.secFgsManagerController;
        RecyclerView recyclerView = null;
        Object[] objArr = 0;
        if (secFgsManagerController == null) {
            synchronized (this.lock) {
                try {
                    if (this.dialog == null) {
                        final SystemUIDialog systemUIDialogCreate = this.systemUIDialogFactory.create(((ShadeDialogContextInteractorImpl) this.shadeDialogContextRepository).getContext());
                        systemUIDialogCreate.setTitle(R.string.fgs_manager_dialog_title);
                        systemUIDialogCreate.setMessage(R.string.fgs_manager_dialog_message);
                        Context context = systemUIDialogCreate.getContext();
                        RecyclerView recyclerView2 = new RecyclerView(context);
                        recyclerView2.setLayoutManager(new LinearLayoutManager(context));
                        recyclerView2.setAdapter(this.appListAdapter);
                        systemUIDialogCreate.setView(recyclerView2, 0, context.getResources().getDimensionPixelSize(R.dimen.fgs_manager_list_top_spacing), 0, 0);
                        this.dialog = systemUIDialogCreate;
                        systemUIDialogCreate.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.qs.FgsManagerControllerImpl$showDialog$2$1
                            @Override // android.content.DialogInterface.OnDismissListener
                            public final void onDismiss(DialogInterface dialogInterface) {
                                FgsManagerControllerImpl fgsManagerControllerImpl = this.this$0;
                                fgsManagerControllerImpl.newChangesSinceDialogWasDismissed = false;
                                synchronized (fgsManagerControllerImpl.lock) {
                                    fgsManagerControllerImpl.dialog = null;
                                    fgsManagerControllerImpl.updateAppItemsLocked(false);
                                    Unit unit = Unit.INSTANCE;
                                }
                                FgsManagerControllerImpl fgsManagerControllerImpl2 = this.this$0;
                                for (final FgsManagerController.OnDialogDismissedListener onDialogDismissedListener : fgsManagerControllerImpl2.onDialogDismissedListeners) {
                                    fgsManagerControllerImpl2.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.qs.FgsManagerControllerImpl$showDialog$2$1$2$1
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            ForegroundServicesRepositoryImpl$hasNewChanges$1$dialogDismissedEvents$1$listener$1 foregroundServicesRepositoryImpl$hasNewChanges$1$dialogDismissedEvents$1$listener$1 = (ForegroundServicesRepositoryImpl$hasNewChanges$1$dialogDismissedEvents$1$listener$1) onDialogDismissedListener;
                                            foregroundServicesRepositoryImpl$hasNewChanges$1$dialogDismissedEvents$1$listener$1.getClass();
                                            ChannelExt.trySendWithFailureLogging$default(ChannelExt.INSTANCE, foregroundServicesRepositoryImpl$hasNewChanges$1$dialogDismissedEvents$1$listener$1.$$this$conflatedCallbackFlow, Unit.INSTANCE, "ForegroundServicesRepositoryImpl");
                                        }
                                    });
                                }
                            }
                        });
                        Executor executor = this.mainExecutor;
                        final Object[] objArr2 = objArr == true ? 1 : 0;
                        executor.execute(new Runnable() { // from class: com.android.systemui.qs.FgsManagerControllerImpl$showDialog$2$2
                            @Override // java.lang.Runnable
                            public final void run() {
                                Expandable expandable = objArr2;
                                DialogTransitionAnimator.Controller controllerDialogTransitionController = expandable != null ? expandable.dialogTransitionController(new DialogCuj(58, "active_background_apps")) : null;
                                if (controllerDialogTransitionController == null) {
                                    systemUIDialogCreate.show();
                                    return;
                                }
                                DialogTransitionAnimator dialogTransitionAnimator = this.dialogTransitionAnimator;
                                SystemUIDialog systemUIDialog = systemUIDialogCreate;
                                systemUIDialog.getClass();
                                TransitionAnimator.Timings timings = DialogTransitionAnimator.TIMINGS;
                                dialogTransitionAnimator.show(systemUIDialog, controllerDialogTransitionController, false);
                            }
                        });
                        updateAppItemsLocked(true);
                    }
                    Unit unit = Unit.INSTANCE;
                } catch (Throwable th) {
                    throw th;
                }
            }
            return;
        }
        Executor executor2 = this.backgroundExecutor;
        Context context2 = ((ShadeDialogContextInteractorImpl) this.shadeDialogContextRepository).getContext();
        final Object obj = this.lock;
        final FgsManagerControllerImpl$$ExternalSyntheticLambda2 fgsManagerControllerImpl$$ExternalSyntheticLambda2 = new FgsManagerControllerImpl$$ExternalSyntheticLambda2(this, 1);
        final FgsManagerControllerImpl$$ExternalSyntheticLambda0 fgsManagerControllerImpl$$ExternalSyntheticLambda0 = new FgsManagerControllerImpl$$ExternalSyntheticLambda0(this, 3);
        final FgsManagerControllerImpl$showDialog$1$4 fgsManagerControllerImpl$showDialog$1$4 = new FgsManagerControllerImpl$showDialog$1$4(this);
        if (secFgsManagerController.dialog.invoke() != null) {
            return;
        }
        SecFgsManagerController.log("setup dialog");
        Context displayContext = SecFgsManagerController.getDisplayContext(context2);
        synchronized (obj) {
            Iterator it = ((LinkedHashMap) this.runningTaskIdentifiers).keySet().iterator();
            while (it.hasNext()) {
                ((UserPackage) it.next()).updateUiControl();
            }
            Unit unit2 = Unit.INSTANCE;
            Unit unit3 = Unit.INSTANCE;
        }
        SystemUIDialog systemUIDialog = new SystemUIDialog(displayContext, R.style.Theme_SystemUI_Dialog_Alert);
        boolean zAreEqual = Intrinsics.areEqual(displayContext, context2);
        systemUIDialog.setTitle(R.string.sec_fgs_manager_dialog_title);
        systemUIDialog.setMessage(R.string.sec_fgs_manager_dialog_message);
        systemUIDialog.setButton(-3, R.string.sec_quick_settings_done, null, true);
        Window window = systemUIDialog.getWindow();
        if (window != null) {
            if (QpRune.QUICK_TABLET || ((SecQsUiDisplayModeInteractor) secFgsManagerController.qsUiDisplayModeInteractor$delegate.getValue()).isTablet() || !zAreEqual) {
                window.setGravity(8388659);
                secFgsManagerController.setMargin(window);
            } else {
                window.setGravity(81);
            }
        }
        View viewInflate = LayoutInflater.from(systemUIDialog.getContext()).inflate(R.layout.sec_fgs_manager_container, (ViewGroup) systemUIDialog.getListView(), false);
        viewInflate.getClass();
        Context context3 = viewInflate.getContext();
        RecyclerView recyclerView3 = (RecyclerView) viewInflate.findViewById(R.id.sec_fgs_manager_recycler_view);
        if (recyclerView3 != null) {
            recyclerView3.setLayoutManager(new LinearLayoutManager(context3));
            recyclerView = recyclerView3;
        }
        if (recyclerView != null) {
            recyclerView.setAdapter(this.appListAdapter);
            secFgsManagerController.recyclerView = recyclerView;
        }
        systemUIDialog.setView(viewInflate);
        secFgsManagerController.noItemTextView = (TextView) viewInflate.findViewById(R.id.sec_fgs_manager_no_item_text_view);
        secFgsManagerController.updateDialog.mo781invoke(systemUIDialog);
        systemUIDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.qs.SecFgsManagerController$setOnDismissListener$1
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                fgsManagerControllerImpl$$ExternalSyntheticLambda2.mo781invoke(Boolean.FALSE);
                Object obj2 = obj;
                SecFgsManagerController secFgsManagerController2 = secFgsManagerController;
                Function0 function0 = fgsManagerControllerImpl$showDialog$1$4;
                synchronized (obj2) {
                    try {
                        RecyclerView recyclerView4 = secFgsManagerController2.recyclerView;
                        if (recyclerView4 != null) {
                            recyclerView4.setAdapter(null);
                        }
                        secFgsManagerController2.updateDialog.mo781invoke(null);
                        secFgsManagerController2.noItemTextView = null;
                        function0.invoke();
                        Unit unit4 = Unit.INSTANCE;
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }
                fgsManagerControllerImpl$$ExternalSyntheticLambda0.invoke();
                secFgsManagerController.getClass();
                SecFgsManagerController.log("dismiss dialog");
            }
        });
        executor2.execute(new Runnable() { // from class: com.android.systemui.qs.SecFgsManagerController$setupDialog$3
            @Override // java.lang.Runnable
            public final void run() {
                Object obj2 = obj;
                Function0 function0 = fgsManagerControllerImpl$showDialog$1$4;
                synchronized (obj2) {
                    function0.invoke();
                    Unit unit4 = Unit.INSTANCE;
                }
            }
        });
    }

    public final void updateAppItemsLocked(final boolean z) {
        if (this.dialog == null) {
            this.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.qs.FgsManagerControllerImpl.updateAppItemsLocked.1
                @Override // java.lang.Runnable
                public final void run() {
                    FgsManagerControllerImpl.this.runningApps.clear();
                }
            });
            return;
        }
        Map map = this.runningTaskIdentifiers;
        final LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt__MapsJVMKt.mapCapacity(map.size()));
        for (Map.Entry entry : ((LinkedHashMap) map).entrySet()) {
            linkedHashMap.put(entry.getKey(), Long.valueOf(((StartTimeAndIdentifiers) entry.getValue()).startTime));
        }
        final Set set = CollectionsKt___CollectionsKt.toSet(this.currentProfileIds);
        this.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.qs.FgsManagerControllerImpl.updateAppItemsLocked.2
            @Override // java.lang.Runnable
            public final void run() {
                final FgsManagerControllerImpl fgsManagerControllerImpl = FgsManagerControllerImpl.this;
                Map map2 = linkedHashMap;
                Set set2 = set;
                boolean z2 = z;
                int i = FgsManagerControllerImpl.$r8$clinit;
                fgsManagerControllerImpl.getClass();
                if (z2) {
                    Iterator it = map2.entrySet().iterator();
                    while (it.hasNext()) {
                        ((UserPackage) ((Map.Entry) it.next()).getKey()).updateUiControl();
                    }
                }
                Set setKeySet = map2.keySet();
                ArrayList arrayList = new ArrayList();
                for (Object obj : setKeySet) {
                    UserPackage userPackage = (UserPackage) obj;
                    if (set2.contains(Integer.valueOf(userPackage.userId)) && userPackage.getUiControl() != UIControl.HIDE_ENTRY) {
                        arrayList.add(obj);
                    }
                }
                Set setKeySet2 = fgsManagerControllerImpl.runningApps.keySet();
                ArrayList arrayList2 = new ArrayList();
                for (Object obj2 : setKeySet2) {
                    if (!map2.containsKey((UserPackage) obj2)) {
                        arrayList2.add(obj2);
                    }
                }
                int size = arrayList.size();
                int i2 = 0;
                int i3 = 0;
                while (i3 < size) {
                    int i4 = i3 + 1;
                    UserPackage userPackage2 = (UserPackage) arrayList.get(i3);
                    PackageManager packageManager = fgsManagerControllerImpl.packageManager;
                    String str = userPackage2.packageName;
                    int i5 = userPackage2.userId;
                    ApplicationInfo applicationInfoAsUser = packageManager.getApplicationInfoAsUser(str, i2, i5);
                    ArrayMap arrayMap = fgsManagerControllerImpl.runningApps;
                    Object obj3 = map2.get(userPackage2);
                    obj3.getClass();
                    long jLongValue = ((Number) obj3).longValue();
                    UIControl uiControl = userPackage2.getUiControl();
                    CharSequence applicationLabel = fgsManagerControllerImpl.packageManager.getApplicationLabel(applicationInfoAsUser);
                    PackageManager packageManager2 = fgsManagerControllerImpl.packageManager;
                    arrayMap.put(userPackage2, new RunningApp(userPackage2.userId, userPackage2.packageName, jLongValue, uiControl, applicationLabel, packageManager2.getUserBadgedIcon(packageManager2.getApplicationIcon(applicationInfoAsUser), UserHandle.of(i5))));
                    Object obj4 = fgsManagerControllerImpl.runningApps.get(userPackage2);
                    obj4.getClass();
                    fgsManagerControllerImpl.backgroundExecutor.execute(new FgsManagerControllerImpl$logEvent$1(fgsManagerControllerImpl, userPackage2.packageName, userPackage2.userId, 1, fgsManagerControllerImpl.systemClock.elapsedRealtime(), ((RunningApp) obj4).timeStarted, false));
                    i3 = i4;
                    i2 = 0;
                }
                int size2 = arrayList2.size();
                int i6 = 0;
                while (i6 < size2) {
                    Object obj5 = arrayList2.get(i6);
                    i6++;
                    UserPackage userPackage3 = (UserPackage) obj5;
                    Object obj6 = fgsManagerControllerImpl.runningApps.get(userPackage3);
                    obj6.getClass();
                    RunningApp runningApp = (RunningApp) obj6;
                    RunningApp runningApp2 = new RunningApp(runningApp.userId, runningApp.packageName, runningApp.timeStarted, runningApp.uiControl);
                    runningApp2.stopped = true;
                    runningApp2.appLabel = runningApp.appLabel;
                    runningApp2.icon = runningApp.icon;
                    fgsManagerControllerImpl.runningApps.put(userPackage3, runningApp2);
                }
                fgsManagerControllerImpl.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.qs.FgsManagerControllerImpl$updateAppItems$4
                    /* JADX WARN: Type inference failed for: r3v0, types: [T, java.util.List] */
                    @Override // java.lang.Runnable
                    public final void run() {
                        SystemUIDialog systemUIDialog;
                        FgsManagerControllerImpl fgsManagerControllerImpl2 = fgsManagerControllerImpl;
                        FgsManagerControllerImpl.AppListAdapter appListAdapter = fgsManagerControllerImpl2.appListAdapter;
                        final List listSortedWith = CollectionsKt___CollectionsKt.sortedWith(CollectionsKt___CollectionsKt.toList(fgsManagerControllerImpl2.runningApps.values()), new Comparator() { // from class: com.android.systemui.qs.FgsManagerControllerImpl$updateAppItems$4$run$$inlined$sortedByDescending$1
                            @Override // java.util.Comparator
                            public final int compare(Object obj7, Object obj8) {
                                return ComparisonsKt__ComparisonsKt.compareValues(Long.valueOf(((FgsManagerControllerImpl.RunningApp) obj8).timeStarted), Long.valueOf(((FgsManagerControllerImpl.RunningApp) obj7).timeStarted));
                            }
                        });
                        appListAdapter.getClass();
                        final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
                        ref$ObjectRef.element = appListAdapter.data;
                        appListAdapter.data = listSortedWith;
                        SecFgsManagerController secFgsManagerController = FgsManagerControllerImpl.this.secFgsManagerController;
                        if (secFgsManagerController != null) {
                            boolean z3 = listSortedWith.size() == 0;
                            SecFgsManagerController.log("updateNoItemTextView: " + z3);
                            TextView textView = secFgsManagerController.noItemTextView;
                            if (textView != null) {
                                textView.setVisibility(z3 ? 0 : 8);
                            }
                        }
                        DiffUtil.calculateDiff(new DiffUtil.Callback() { // from class: com.android.systemui.qs.FgsManagerControllerImpl$AppListAdapter$setData$2
                            @Override // androidx.recyclerview.widget.DiffUtil.Callback
                            public final boolean areContentsTheSame(int i7, int i8) {
                                return ((FgsManagerControllerImpl.RunningApp) ((List) ref$ObjectRef.element).get(i7)).stopped == ((FgsManagerControllerImpl.RunningApp) listSortedWith.get(i8)).stopped;
                            }

                            @Override // androidx.recyclerview.widget.DiffUtil.Callback
                            public final boolean areItemsTheSame(int i7, int i8) {
                                return Intrinsics.areEqual(((List) ref$ObjectRef.element).get(i7), listSortedWith.get(i8));
                            }

                            @Override // androidx.recyclerview.widget.DiffUtil.Callback
                            public final int getNewListSize() {
                                return listSortedWith.size();
                            }

                            @Override // androidx.recyclerview.widget.DiffUtil.Callback
                            public final int getOldListSize() {
                                return ((List) ref$ObjectRef.element).size();
                            }
                        }).dispatchUpdatesTo(new AdapterListUpdateCallback(appListAdapter));
                        SecFgsManagerController secFgsManagerController2 = fgsManagerControllerImpl.secFgsManagerController;
                        if (secFgsManagerController2 == null || (systemUIDialog = (SystemUIDialog) secFgsManagerController2.dialog.invoke()) == null) {
                            return;
                        }
                        if (systemUIDialog.isShowing()) {
                            systemUIDialog = null;
                        }
                        if (systemUIDialog != null) {
                            SecFgsManagerController.log("show dialog");
                            secFgsManagerController2.foldState = (SecQsUiDisplayModeInteractor.FoldState) ((SecQsUiDisplayModeInteractor) secFgsManagerController2.qsUiDisplayModeInteractor$delegate.getValue()).getFoldState().getValue();
                            systemUIDialog.show();
                            SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_FGS_ACTIVE_APPS);
                        }
                    }
                });
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
                        onNumberOfPackagesChangedListener.onNumberOfPackagesChanged(numVisiblePackagesLocked);
                    }
                });
            }
            if (this.secFgsManagerController != null) {
                SecFgsManagerController.log("updateNumberOfVisibleRunningPackagesLocked: " + numVisiblePackagesLocked);
            }
        }
    }

    @Override // com.android.systemui.qs.FgsManagerController
    public final int visibleButtonsCount() {
        int numVisibleButtonsLocked;
        synchronized (this.lock) {
            numVisibleButtonsLocked = getNumVisibleButtonsLocked();
        }
        return numVisibleButtonsLocked;
    }

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

        public final void dump(PrintWriter printWriter, SystemClock systemClock) {
            printWriter.println("RunningApp: [");
            ((IndentingPrintWriter) printWriter).increaseIndent();
            StringBuilder sbM = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("userId="), this.userId, printWriter, "packageName=");
            sbM.append(this.packageName);
            printWriter.println(sbM.toString());
            long jElapsedRealtime = systemClock.elapsedRealtime();
            long j = this.timeStarted;
            StringBuilder sbM2 = SnapshotStateObserver$$ExternalSyntheticOutline0.m("timeStarted=", j, " (time since start = ");
            sbM2.append(jElapsedRealtime - j);
            sbM2.append("ms)");
            printWriter.println(sbM2.toString());
            printWriter.println("uiControl=" + this.uiControl);
            printWriter.println("appLabel=" + ((Object) this.appLabel));
            printWriter.println("icon=" + this.icon);
            ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "stopped=", this.stopped);
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
            return this.uiControl.hashCode() + MoveResult$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(Integer.hashCode(this.userId) * 31, 31, this.packageName), 31, this.timeStarted);
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
