package com.android.systemui.qs;

import android.app.IActivityManager;
import android.app.IForegroundServiceObserver;
import android.app.job.IUserVisibleJobObserver;
import android.app.job.JobScheduler;
import android.app.job.UserVisibleJobSummary;
import android.content.Context;
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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.compose.runtime.snapshots.SnapshotStateObserver$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.AdapterListUpdateCallback;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecurityContainer$UserSwitcherViewMode$2$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.qs.FgsManagerController;
import com.android.systemui.qs.FgsManagerControllerImpl;
import com.android.systemui.qs.FgsManagerControllerImpl.UserPackage;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.shade.domain.interactor.ShadeDialogContextInteractor;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.util.DeviceConfigProxy;
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
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                    FgsManagerControllerImpl.AppItemViewHolder.this.stopButton.setText(R.string.sec_fgs_manager_app_item_stop_button_stopped_label);
                    FgsManagerControllerImpl.AppListAdapter appListAdapter = this;
                    FgsManagerControllerImpl.AppItemViewHolder appItemViewHolder2 = FgsManagerControllerImpl.AppItemViewHolder.this;
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            long elapsedRealtime = this.systemClock.elapsedRealtime();
            long j = this.startTime;
            long j2 = elapsedRealtime - j;
            StringBuilder m = SnapshotStateObserver$$ExternalSyntheticOutline0.m("startTime=", j, " (time running = ");
            m.append(j2);
            m.append("ms)");
            printWriter.println(m.toString());
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
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                    PackageManager packageManager = FgsManagerControllerImpl.this.packageManager;
                    FgsManagerControllerImpl.UserPackage userPackage = this;
                    return Integer.valueOf(packageManager.getPackageUidAsUser(userPackage.packageName, userPackage.userId));
                }
            });
        }

        public final void dump(PrintWriter printWriter) {
            printWriter.println("UserPackage: [");
            ((IndentingPrintWriter) printWriter).increaseIndent();
            StringBuilder m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("userId="), this.userId, printWriter, "packageName=");
            m.append(this.packageName);
            printWriter.println(m.toString());
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

        /* JADX WARN: Removed duplicated region for block: B:29:0x006d  */
        /* JADX WARN: Removed duplicated region for block: B:33:0x0086  */
        /* JADX WARN: Removed duplicated region for block: B:36:? A[RETURN, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void updateUiControl() {
            /*
                r3 = this;
                com.android.systemui.qs.FgsManagerControllerImpl r0 = com.android.systemui.qs.FgsManagerControllerImpl.this
                android.app.IActivityManager r1 = r0.activityManager
                kotlin.Lazy r2 = r3.uid$delegate
                java.lang.Object r2 = r2.getValue()
                java.lang.Number r2 = (java.lang.Number) r2
                int r2 = r2.intValue()
                int r1 = r1.getBackgroundRestrictionExemptionReason(r2)
                r3.backgroundRestrictionExemptionReason = r1
                r2 = 10
                if (r1 == r2) goto L59
                r2 = 11
                if (r1 == r2) goto L59
                r2 = 51
                if (r1 == r2) goto L56
                r2 = 63
                if (r1 == r2) goto L56
                r2 = 65
                if (r1 == r2) goto L4c
                r2 = 300(0x12c, float:4.2E-43)
                if (r1 == r2) goto L59
                r2 = 318(0x13e, float:4.46E-43)
                if (r1 == r2) goto L59
                r2 = 320(0x140, float:4.48E-43)
                if (r1 == r2) goto L59
                r2 = 327(0x147, float:4.58E-43)
                if (r1 == r2) goto L59
                r2 = 350(0x15e, float:4.9E-43)
                if (r1 == r2) goto L56
                r2 = 55
                if (r1 == r2) goto L59
                r2 = 56
                if (r1 == r2) goto L59
                switch(r1) {
                    case 322: goto L59;
                    case 323: goto L59;
                    case 324: goto L59;
                    default: goto L49;
                }
            L49:
                com.android.systemui.qs.FgsManagerControllerImpl$UIControl r1 = com.android.systemui.qs.FgsManagerControllerImpl.UIControl.NORMAL
                goto L5b
            L4c:
                boolean r1 = r0.showStopBtnForUserAllowlistedApps
                if (r1 == 0) goto L53
                com.android.systemui.qs.FgsManagerControllerImpl$UIControl r1 = com.android.systemui.qs.FgsManagerControllerImpl.UIControl.NORMAL
                goto L5b
            L53:
                com.android.systemui.qs.FgsManagerControllerImpl$UIControl r1 = com.android.systemui.qs.FgsManagerControllerImpl.UIControl.HIDE_BUTTON
                goto L5b
            L56:
                com.android.systemui.qs.FgsManagerControllerImpl$UIControl r1 = com.android.systemui.qs.FgsManagerControllerImpl.UIControl.HIDE_ENTRY
                goto L5b
            L59:
                com.android.systemui.qs.FgsManagerControllerImpl$UIControl r1 = com.android.systemui.qs.FgsManagerControllerImpl.UIControl.HIDE_BUTTON
            L5b:
                r3.uiControl = r1
                kotlin.Lazy r1 = r0.stoppableApps$delegate
                java.lang.Object r1 = r1.getValue()
                java.lang.String[] r1 = (java.lang.String[]) r1
                java.lang.String r2 = r3.packageName
                boolean r1 = kotlin.collections.ArraysKt___ArraysKt.contains(r1, r2)
                if (r1 != 0) goto L7b
                kotlin.Lazy r1 = r0.vendorStoppableApps$delegate
                java.lang.Object r1 = r1.getValue()
                java.lang.String[] r1 = (java.lang.String[]) r1
                boolean r1 = kotlin.collections.ArraysKt___ArraysKt.contains(r1, r2)
                if (r1 == 0) goto L7f
            L7b:
                com.android.systemui.qs.FgsManagerControllerImpl$UIControl r1 = com.android.systemui.qs.FgsManagerControllerImpl.UIControl.NORMAL
                r3.uiControl = r1
            L7f:
                r1 = 1
                r3.uiControlInitialized = r1
                com.android.systemui.qs.SecFgsManagerController r0 = r0.secFgsManagerController
                if (r0 == 0) goto La2
                int r3 = r3.backgroundRestrictionExemptionReason
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                java.lang.String r1 = "updateUiControl["
                r0.<init>(r1)
                r0.append(r2)
                java.lang.String r1 = "]: "
                r0.append(r1)
                r0.append(r3)
                java.lang.String r3 = r0.toString()
                com.android.systemui.qs.SecFgsManagerController.log(r3)
            La2:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.FgsManagerControllerImpl.UserPackage.updateUiControl():void");
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                        Object obj = linkedHashMap.get(userPackage);
                        if (obj == null) {
                            obj = new StartTimeAndIdentifiers(fgsManagerControllerImpl.systemClock);
                            linkedHashMap.put(userPackage, obj);
                        }
                        ((StartTimeAndIdentifiers) obj).jobSummaries.add(userVisibleJobSummary);
                    } else {
                        StartTimeAndIdentifiers startTimeAndIdentifiers = (StartTimeAndIdentifiers) ((LinkedHashMap) fgsManagerControllerImpl.runningTaskIdentifiers).get(userPackage);
                        if (startTimeAndIdentifiers != null) {
                            startTimeAndIdentifiers.jobSummaries.remove(userVisibleJobSummary);
                            if (startTimeAndIdentifiers.fgsTokens.isEmpty() && startTimeAndIdentifiers.jobSummaries.isEmpty()) {
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
    public FgsManagerControllerImpl(Resources resources, Executor executor, Executor executor2, SystemClock systemClock, IActivityManager iActivityManager, JobScheduler jobScheduler, PackageManager packageManager, UserTracker userTracker, DeviceConfigProxy deviceConfigProxy, DialogTransitionAnimator dialogTransitionAnimator, BroadcastDispatcher broadcastDispatcher, DumpManager dumpManager, SystemUIDialog.Factory factory, ShadeDialogContextInteractor shadeDialogContextInteractor) {
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
        this.secFgsManagerController = new SecFgsManagerController(new FgsManagerControllerImpl$$ExternalSyntheticLambda2(this, 0), new FgsManagerControllerImpl$$ExternalSyntheticLambda0(this, 2), new FgsManagerControllerImpl$secFgsManagerController$3(this));
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
        Set<UserPackage> keySet = ((LinkedHashMap) this.runningTaskIdentifiers).keySet();
        int i = 0;
        if ((keySet instanceof Collection) && keySet.isEmpty()) {
            return 0;
        }
        for (UserPackage userPackage : keySet) {
            if (userPackage.getUiControl() != UIControl.HIDE_BUTTON && this.currentProfileIds.contains(Integer.valueOf(userPackage.userId)) && (i = i + 1) < 0) {
                CollectionsKt__CollectionsKt.throwCountOverflow();
                throw null;
            }
        }
        return i;
    }

    public final int getNumVisiblePackagesLocked() {
        Set<UserPackage> keySet = ((LinkedHashMap) this.runningTaskIdentifiers).keySet();
        int i = 0;
        if ((keySet instanceof Collection) && keySet.isEmpty()) {
            return 0;
        }
        for (UserPackage userPackage : keySet) {
            if (userPackage.getUiControl() != UIControl.HIDE_ENTRY && this.currentProfileIds.contains(Integer.valueOf(userPackage.userId)) && (i = i + 1) < 0) {
                CollectionsKt__CollectionsKt.throwCountOverflow();
                throw null;
            }
        }
        return i;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0064 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void showDialog$2() {
        /*
            Method dump skipped, instructions count: 468
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.FgsManagerControllerImpl.showDialog$2():void");
    }

    public final void updateAppItemsLocked(final boolean z) {
        if (this.dialog == null) {
            this.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.qs.FgsManagerControllerImpl$updateAppItemsLocked$1
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
        this.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.qs.FgsManagerControllerImpl$updateAppItemsLocked$2
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
                        ((FgsManagerControllerImpl.UserPackage) ((Map.Entry) it.next()).getKey()).updateUiControl();
                    }
                }
                Set keySet = map2.keySet();
                ArrayList arrayList = new ArrayList();
                for (Object obj : keySet) {
                    FgsManagerControllerImpl.UserPackage userPackage = (FgsManagerControllerImpl.UserPackage) obj;
                    if (set2.contains(Integer.valueOf(userPackage.userId)) && userPackage.getUiControl() != FgsManagerControllerImpl.UIControl.HIDE_ENTRY) {
                        arrayList.add(obj);
                    }
                }
                Set keySet2 = fgsManagerControllerImpl.runningApps.keySet();
                ArrayList arrayList2 = new ArrayList();
                for (Object obj2 : keySet2) {
                    if (!map2.containsKey((FgsManagerControllerImpl.UserPackage) obj2)) {
                        arrayList2.add(obj2);
                    }
                }
                int size = arrayList.size();
                int i2 = 0;
                int i3 = 0;
                while (i3 < size) {
                    int i4 = i3 + 1;
                    FgsManagerControllerImpl.UserPackage userPackage2 = (FgsManagerControllerImpl.UserPackage) arrayList.get(i3);
                    PackageManager packageManager = fgsManagerControllerImpl.packageManager;
                    String str = userPackage2.packageName;
                    int i5 = userPackage2.userId;
                    ApplicationInfo applicationInfoAsUser = packageManager.getApplicationInfoAsUser(str, i2, i5);
                    ArrayMap arrayMap = fgsManagerControllerImpl.runningApps;
                    Object obj3 = map2.get(userPackage2);
                    obj3.getClass();
                    long longValue = ((Number) obj3).longValue();
                    FgsManagerControllerImpl.UIControl uiControl = userPackage2.getUiControl();
                    CharSequence applicationLabel = fgsManagerControllerImpl.packageManager.getApplicationLabel(applicationInfoAsUser);
                    PackageManager packageManager2 = fgsManagerControllerImpl.packageManager;
                    arrayMap.put(userPackage2, new FgsManagerControllerImpl.RunningApp(userPackage2.userId, userPackage2.packageName, longValue, uiControl, applicationLabel, packageManager2.getUserBadgedIcon(packageManager2.getApplicationIcon(applicationInfoAsUser), UserHandle.of(i5))));
                    Object obj4 = fgsManagerControllerImpl.runningApps.get(userPackage2);
                    obj4.getClass();
                    fgsManagerControllerImpl.backgroundExecutor.execute(new FgsManagerControllerImpl$logEvent$1(fgsManagerControllerImpl, userPackage2.packageName, userPackage2.userId, 1, fgsManagerControllerImpl.systemClock.elapsedRealtime(), ((FgsManagerControllerImpl.RunningApp) obj4).timeStarted, false));
                    i3 = i4;
                    i2 = 0;
                }
                int size2 = arrayList2.size();
                int i6 = 0;
                while (i6 < size2) {
                    Object obj5 = arrayList2.get(i6);
                    i6++;
                    FgsManagerControllerImpl.UserPackage userPackage3 = (FgsManagerControllerImpl.UserPackage) obj5;
                    Object obj6 = fgsManagerControllerImpl.runningApps.get(userPackage3);
                    obj6.getClass();
                    FgsManagerControllerImpl.RunningApp runningApp = (FgsManagerControllerImpl.RunningApp) obj6;
                    FgsManagerControllerImpl.RunningApp runningApp2 = new FgsManagerControllerImpl.RunningApp(runningApp.userId, runningApp.packageName, runningApp.timeStarted, runningApp.uiControl);
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
                        FgsManagerControllerImpl fgsManagerControllerImpl2 = FgsManagerControllerImpl.this;
                        FgsManagerControllerImpl.AppListAdapter appListAdapter = fgsManagerControllerImpl2.appListAdapter;
                        final List sortedWith = CollectionsKt___CollectionsKt.sortedWith(CollectionsKt___CollectionsKt.toList(fgsManagerControllerImpl2.runningApps.values()), new Comparator() { // from class: com.android.systemui.qs.FgsManagerControllerImpl$updateAppItems$4$run$$inlined$sortedByDescending$1
                            @Override // java.util.Comparator
                            public final int compare(Object obj7, Object obj8) {
                                return ComparisonsKt__ComparisonsKt.compareValues(Long.valueOf(((FgsManagerControllerImpl.RunningApp) obj8).timeStarted), Long.valueOf(((FgsManagerControllerImpl.RunningApp) obj7).timeStarted));
                            }
                        });
                        appListAdapter.getClass();
                        final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
                        ref$ObjectRef.element = appListAdapter.data;
                        appListAdapter.data = sortedWith;
                        SecFgsManagerController secFgsManagerController = FgsManagerControllerImpl.this.secFgsManagerController;
                        if (secFgsManagerController != null) {
                            boolean z3 = sortedWith.size() == 0;
                            SecFgsManagerController.log("updateNoItemTextView: " + z3);
                            TextView textView = secFgsManagerController.noItemTextView;
                            if (textView != null) {
                                textView.setVisibility(z3 ? 0 : 8);
                            }
                        }
                        DiffUtil.calculateDiff(new DiffUtil.Callback() { // from class: com.android.systemui.qs.FgsManagerControllerImpl$AppListAdapter$setData$2
                            @Override // androidx.recyclerview.widget.DiffUtil.Callback
                            public final boolean areContentsTheSame(int i7, int i8) {
                                return ((FgsManagerControllerImpl.RunningApp) ((List) ref$ObjectRef.element).get(i7)).stopped == ((FgsManagerControllerImpl.RunningApp) sortedWith.get(i8)).stopped;
                            }

                            @Override // androidx.recyclerview.widget.DiffUtil.Callback
                            public final boolean areItemsTheSame(int i7, int i8) {
                                return Intrinsics.areEqual(((List) ref$ObjectRef.element).get(i7), sortedWith.get(i8));
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
                        SecFgsManagerController secFgsManagerController2 = FgsManagerControllerImpl.this.secFgsManagerController;
                        if (secFgsManagerController2 == null || (systemUIDialog = (SystemUIDialog) secFgsManagerController2.dialog.invoke()) == null) {
                            return;
                        }
                        if (systemUIDialog.isShowing()) {
                            systemUIDialog = null;
                        }
                        if (systemUIDialog != null) {
                            SecFgsManagerController.log("show dialog");
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
                        FgsManagerController.OnNumberOfPackagesChangedListener.this.onNumberOfPackagesChanged(numVisiblePackagesLocked);
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            StringBuilder m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("userId="), this.userId, printWriter, "packageName=");
            m.append(this.packageName);
            printWriter.println(m.toString());
            long elapsedRealtime = systemClock.elapsedRealtime();
            long j = this.timeStarted;
            StringBuilder m2 = SnapshotStateObserver$$ExternalSyntheticOutline0.m("timeStarted=", j, " (time since start = ");
            m2.append(elapsedRealtime - j);
            m2.append("ms)");
            printWriter.println(m2.toString());
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
