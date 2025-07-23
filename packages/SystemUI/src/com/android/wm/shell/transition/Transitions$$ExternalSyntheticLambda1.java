package com.android.wm.shell.transition;

import android.R;
import android.content.ContentResolver;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Pair;
import android.view.WindowManager;
import android.window.RemoteTransition;
import android.window.TransitionInfo;
import android.window.TransitionMetrics;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelAdapter$$ExternalSyntheticOutline0;
import com.android.systemui.util.SettingsHelper;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.sysui.ShellCommandHandler;
import com.android.wm.shell.taskview.TaskViewTransitions;
import com.android.wm.shell.transition.Transitions;
import com.android.wm.shell.transition.Transitions.SettingsObserver;
import com.samsung.android.knox.zt.config.securelog.SignalSeverity;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class Transitions$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ Transitions$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                final Transitions transitions = (Transitions) obj;
                boolean z = Transitions.ENABLE_SHELL_TRANSITIONS;
                ShellTaskOrganizer shellTaskOrganizer = transitions.mOrganizer;
                if (z) {
                    shellTaskOrganizer.shareTransactionQueue();
                }
                transitions.mShellController.addExternalInterface("com.android.wm.shell.shared.IShellTransitions", new Supplier() { // from class: com.android.wm.shell.transition.Transitions$$ExternalSyntheticLambda5
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        Transitions transitions2 = Transitions.this;
                        boolean z2 = Transitions.DEBUG_START_TRANSITION;
                        transitions2.getClass();
                        return new Transitions.IShellTransitionsImpl(transitions2);
                    }
                }, transitions);
                ContentResolver contentResolver = transitions.mContext.getContentResolver();
                float fixScale = WindowManager.fixScale(Settings.Global.getFloat(transitions.mContext.getContentResolver(), SettingsHelper.INDEX_TRANSITION_ANIMATION_SCALE, transitions.mContext.getResources().getFloat(R.dimen.config_resActivitySnapshotScale)));
                transitions.mTransitionAnimationScaleSetting = fixScale;
                for (int size = transitions.mHandlers.size() - 1; size >= 0; size--) {
                    ((Transitions.TransitionHandler) transitions.mHandlers.get(size)).setAnimScaleSetting(fixScale);
                }
                contentResolver.registerContentObserver(Settings.Global.getUriFor(SettingsHelper.INDEX_TRANSITION_ANIMATION_SCALE), false, transitions.new SettingsObserver());
                if (z) {
                    transitions.mIsRegistered = true;
                    try {
                        shellTaskOrganizer.registerTransitionPlayer(transitions.mPlayerImpl);
                        TransitionMetrics.getInstance();
                    } catch (RuntimeException e) {
                        transitions.mIsRegistered = false;
                        throw e;
                    }
                }
                ShellCommandHandler shellCommandHandler = transitions.mShellCommandHandler;
                shellCommandHandler.addCommandCallback("transitions", transitions, transitions);
                shellCommandHandler.addDumpCallback(new BiConsumer() { // from class: com.android.wm.shell.transition.Transitions$$ExternalSyntheticLambda6
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj2, Object obj3) {
                        Transitions transitions2 = Transitions.this;
                        final PrintWriter printWriter = (PrintWriter) obj2;
                        String str = (String) obj3;
                        boolean z2 = Transitions.DEBUG_START_TRANSITION;
                        printWriter.println(str + "ShellTransitions");
                        String str2 = str + "  ";
                        QSTileViewModelAdapter$$ExternalSyntheticOutline0.m(printWriter, str, "Handlers (ordered by priority):");
                        for (int size2 = transitions2.mHandlers.size() - 1; size2 >= 0; size2--) {
                            Transitions.TransitionHandler transitionHandler = (Transitions.TransitionHandler) transitions2.mHandlers.get(size2);
                            printWriter.print(str2);
                            printWriter.print(transitionHandler.getClass().getSimpleName());
                            printWriter.println(" (" + Integer.toHexString(System.identityHashCode(transitionHandler)) + ")");
                            if (transitionHandler instanceof TaskViewTransitions) {
                                TaskViewTransitions taskViewTransitions = (TaskViewTransitions) transitionHandler;
                                final String m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str2, "  ");
                                StringBuilder m2 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(m, "mTaskViews=");
                                m2.append(taskViewTransitions.mTaskViews);
                                printWriter.println(m2.toString());
                                printWriter.println(m + "mPending=" + taskViewTransitions.mPending);
                                StringBuilder sb = new StringBuilder();
                                sb.append(m);
                                sb.append("mLogHistory");
                                printWriter.println(sb.toString());
                                taskViewTransitions.mLogHistory.forEach(new Consumer() { // from class: com.android.wm.shell.taskview.TaskViewTransitions$$ExternalSyntheticLambda2
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj4) {
                                        QSTileViewModelAdapter$$ExternalSyntheticOutline0.m(printWriter, m, (String) obj4);
                                    }
                                });
                                printWriter.println();
                            }
                        }
                        RemoteTransitionHandler remoteTransitionHandler = transitions2.mRemoteTransitionHandler;
                        remoteTransitionHandler.getClass();
                        String str3 = str + "  ";
                        QSTileViewModelAdapter$$ExternalSyntheticOutline0.m(printWriter, str, "Registered Remotes:");
                        if (remoteTransitionHandler.mFilters.isEmpty()) {
                            QSTileViewModelAdapter$$ExternalSyntheticOutline0.m(printWriter, str3, SignalSeverity.NONE);
                        } else {
                            ArrayList arrayList = remoteTransitionHandler.mFilters;
                            int size3 = arrayList.size();
                            int i2 = 0;
                            while (i2 < size3) {
                                Object obj4 = arrayList.get(i2);
                                i2++;
                                RemoteTransitionHandler.dumpRemote(printWriter, str3, (RemoteTransition) ((Pair) obj4).second);
                            }
                        }
                        QSTileViewModelAdapter$$ExternalSyntheticOutline0.m(printWriter, str, "Registered Takeover Remotes:");
                        if (remoteTransitionHandler.mTakeoverFilters.isEmpty()) {
                            QSTileViewModelAdapter$$ExternalSyntheticOutline0.m(printWriter, str3, SignalSeverity.NONE);
                        } else {
                            ArrayList arrayList2 = remoteTransitionHandler.mTakeoverFilters;
                            int size4 = arrayList2.size();
                            int i3 = 0;
                            while (i3 < size4) {
                                Object obj5 = arrayList2.get(i3);
                                i3++;
                                RemoteTransitionHandler.dumpRemote(printWriter, str3, (RemoteTransition) ((Pair) obj5).second);
                            }
                        }
                        QSTileViewModelAdapter$$ExternalSyntheticOutline0.m(printWriter, str, "Observers:");
                        ArrayList arrayList3 = transitions2.mObservers;
                        int size5 = arrayList3.size();
                        int i4 = 0;
                        while (i4 < size5) {
                            Object obj6 = arrayList3.get(i4);
                            i4++;
                            printWriter.print(str2);
                            printWriter.println(((Transitions.TransitionObserver) obj6).getClass().getSimpleName());
                        }
                        QSTileViewModelAdapter$$ExternalSyntheticOutline0.m(printWriter, str, "Pending Transitions:");
                        ArrayList arrayList4 = transitions2.mPendingTransitions;
                        int size6 = arrayList4.size();
                        int i5 = 0;
                        while (true) {
                            String str4 = null;
                            if (i5 >= size6) {
                                break;
                            }
                            Object obj7 = arrayList4.get(i5);
                            i5++;
                            Transitions.ActiveTransition activeTransition = (Transitions.ActiveTransition) obj7;
                            printWriter.print(str2 + "token=");
                            printWriter.println(activeTransition.mToken);
                            printWriter.print(str2 + "id=");
                            TransitionInfo transitionInfo = activeTransition.mInfo;
                            printWriter.println(transitionInfo != null ? transitionInfo.getDebugId() : -1);
                            printWriter.print(str2 + "handler=");
                            Transitions.TransitionHandler transitionHandler2 = activeTransition.mHandler;
                            if (transitionHandler2 != null) {
                                str4 = transitionHandler2.getClass().getSimpleName();
                            }
                            printWriter.println(str4);
                        }
                        if (transitions2.mPendingTransitions.isEmpty()) {
                            QSTileViewModelAdapter$$ExternalSyntheticOutline0.m(printWriter, str2, SignalSeverity.NONE);
                        }
                        QSTileViewModelAdapter$$ExternalSyntheticOutline0.m(printWriter, str, "Ready-during-sync Transitions:");
                        ArrayList arrayList5 = transitions2.mReadyDuringSync;
                        int size7 = arrayList5.size();
                        int i6 = 0;
                        while (i6 < size7) {
                            Object obj8 = arrayList5.get(i6);
                            i6++;
                            Transitions.ActiveTransition activeTransition2 = (Transitions.ActiveTransition) obj8;
                            printWriter.print(str2 + "token=");
                            printWriter.println(activeTransition2.mToken);
                            printWriter.print(str2 + "id=");
                            TransitionInfo transitionInfo2 = activeTransition2.mInfo;
                            printWriter.println(transitionInfo2 != null ? transitionInfo2.getDebugId() : -1);
                            printWriter.print(str2 + "handler=");
                            Transitions.TransitionHandler transitionHandler3 = activeTransition2.mHandler;
                            printWriter.println(transitionHandler3 != null ? transitionHandler3.getClass().getSimpleName() : null);
                        }
                        if (transitions2.mReadyDuringSync.isEmpty()) {
                            QSTileViewModelAdapter$$ExternalSyntheticOutline0.m(printWriter, str2, SignalSeverity.NONE);
                        }
                        QSTileViewModelAdapter$$ExternalSyntheticOutline0.m(printWriter, str, "Tracks:");
                        for (int i7 = 0; i7 < transitions2.mTracks.size(); i7++) {
                            Transitions.ActiveTransition activeTransition3 = ((Transitions.Track) transitions2.mTracks.get(i7)).mActiveTransition;
                            printWriter.println(str2 + "Track #" + i7);
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(str2);
                            sb2.append("active=");
                            printWriter.print(sb2.toString());
                            printWriter.println(activeTransition3);
                            if (activeTransition3 != null) {
                                printWriter.print(str2 + "hander=");
                                printWriter.println(activeTransition3.mHandler);
                            }
                        }
                    }
                }, transitions);
                return;
            default:
                Transitions transitions2 = Transitions.this;
                float f = transitions2.mTransitionAnimationScaleSetting;
                for (int size2 = transitions2.mHandlers.size() - 1; size2 >= 0; size2--) {
                    ((Transitions.TransitionHandler) transitions2.mHandlers.get(size2)).setAnimScaleSetting(f);
                }
                return;
        }
    }
}
