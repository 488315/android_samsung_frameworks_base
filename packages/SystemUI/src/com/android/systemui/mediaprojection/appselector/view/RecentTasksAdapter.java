package com.android.systemui.mediaprojection.appselector.view;

import android.R;
import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.UserHandle;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.View;
import android.view.ViewGroup;
import android.window.RemoteTransition;
import androidx.recyclerview.widget.RecyclerView;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.keyguard.KeyguardSecurityContainer$UserSwitcherViewMode$2$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorActivity;
import com.android.systemui.mediaprojection.appselector.data.RecentTask;
import com.android.systemui.mediaprojection.appselector.view.RecentTaskViewHolder;
import com.android.systemui.mediaprojection.appselector.view.RecentTasksAdapter;
import com.android.wm.shell.shared.split.SplitBounds;
import com.android.wm.shell.splitscreen.SplitScreen;
import com.android.wm.shell.splitscreen.SplitScreenController;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.StandaloneCoroutine;

/* loaded from: classes2.dex */
public final class RecentTasksAdapter extends RecyclerView.Adapter {
    public final List items;
    public final RecentTaskClickListener listener;
    public final RecentTaskViewHolder.Factory viewHolderFactory;

    public interface Factory {
        RecentTasksAdapter create(List list, RecentTaskClickListener recentTaskClickListener);
    }

    public interface RecentTaskClickListener {
    }

    public RecentTasksAdapter(List<RecentTask> list, RecentTaskClickListener recentTaskClickListener, RecentTaskViewHolder.Factory factory) {
        this.items = list;
        this.listener = recentTaskClickListener;
        this.viewHolderFactory = factory;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return this.items.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        final RecentTaskViewHolder recentTaskViewHolder = (RecentTaskViewHolder) viewHolder;
        final RecentTask recentTask = (RecentTask) this.items.get(i);
        final Function1 function1 = new Function1() { // from class: com.android.systemui.mediaprojection.appselector.view.RecentTasksAdapter$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                ActivityOptions activityOptionsMakeScaleUpAnimation;
                RecentTasksAdapter.RecentTaskClickListener recentTaskClickListener = this.f$0.listener;
                View view = recentTaskViewHolder.itemView;
                final MediaProjectionRecentsViewController mediaProjectionRecentsViewController = (MediaProjectionRecentsViewController) recentTaskClickListener;
                mediaProjectionRecentsViewController.getClass();
                final ActivityOptions.LaunchCookie launchCookie = new ActivityOptions.LaunchCookie();
                RecentTask recentTask2 = recentTask;
                boolean z = recentTask2.isForegroundTask;
                SplitBounds splitBounds = recentTask2.splitBounds;
                if (z) {
                    activityOptionsMakeScaleUpAnimation = ActivityOptions.makeCustomTaskAnimation(view.getContext(), 0, R.anim.task_close_enter, null, null, null);
                    activityOptionsMakeScaleUpAnimation.getClass();
                } else if (!mediaProjectionRecentsViewController.splitScreen.isPresent() || splitBounds == null) {
                    activityOptionsMakeScaleUpAnimation = ActivityOptions.makeScaleUpAnimation(view, 0, 0, view.getWidth(), view.getHeight());
                    activityOptionsMakeScaleUpAnimation.getClass();
                } else {
                    activityOptionsMakeScaleUpAnimation = ActivityOptions.makeBasic();
                    activityOptionsMakeScaleUpAnimation.getClass();
                }
                activityOptionsMakeScaleUpAnimation.setPendingIntentBackgroundActivityStartMode(1);
                activityOptionsMakeScaleUpAnimation.setLaunchDisplayId(recentTask2.displayId);
                activityOptionsMakeScaleUpAnimation.setLaunchCookie(launchCookie);
                final int i2 = recentTask2.taskId;
                Function0 function0 = new Function0() { // from class: com.android.systemui.mediaprojection.appselector.view.MediaProjectionRecentsViewController$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        ((MediaProjectionAppSelectorActivity) mediaProjectionRecentsViewController.resultHandler).returnSelectedApp(launchCookie, i2);
                        return Unit.INSTANCE;
                    }
                };
                if (BasicRune.POPUPUI_FOLDERBLE_TYPE_FLIP && !((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened) {
                    ComponentName componentName = recentTask2.baseIntentComponent;
                    String packageName = componentName != null ? componentName.getPackageName() : null;
                    Intent component = new Intent().setComponent(recentTask2.baseIntentComponent);
                    PendingIntent activity = PendingIntent.getActivity(mediaProjectionRecentsViewController.context, 0, component, 67108864);
                    if (!mediaProjectionRecentsViewController.activityTaskManager.isPackageEnabledForCoverLauncher(packageName, UserHandle.semGetMyUserId())) {
                        mediaProjectionRecentsViewController.pluginAODManager.showCoverToast(activity, component);
                    }
                }
                if (!mediaProjectionRecentsViewController.splitScreen.isPresent() || splitBounds == null || recentTask2.isForegroundTask) {
                    mediaProjectionRecentsViewController.activityTaskManager.startActivityFromRecents(i2, activityOptionsMakeScaleUpAnimation.toBundle());
                    function0.invoke();
                } else {
                    splitBounds.getClass();
                    int i3 = splitBounds.leftTopTaskId;
                    int i4 = i2 == i3 ? 1 : 0;
                    if (i4 != 0) {
                        i3 = splitBounds.rightBottomTaskId;
                    }
                    final int i5 = i3;
                    final int i6 = i4 ^ 1;
                    int[] locationOnScreen = view.getLocationOnScreen();
                    Display display = view.getContext().getDisplay();
                    DisplayInfo displayInfo = new DisplayInfo();
                    display.getDisplayInfo(displayInfo);
                    final RemoteTransition remoteTransition = new RemoteTransition(new RemoteRecentSplitTaskTransitionRunner(i2, i5, locationOnScreen, new Rect(0, 0, displayInfo.getNaturalWidth(), displayInfo.getNaturalHeight()), function0), view.getContext().getIApplicationThread(), "startSplitScreenTask");
                    SplitScreen splitScreen = (SplitScreen) mediaProjectionRecentsViewController.splitScreen.get();
                    final Bundle bundle = activityOptionsMakeScaleUpAnimation.toBundle();
                    final int i7 = splitBounds.snapPosition;
                    final SplitScreenController.SplitScreenImpl splitScreenImpl = (SplitScreenController.SplitScreenImpl) splitScreen;
                    SplitScreenController.this.mMainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$SplitScreenImpl$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            SplitScreenController.SplitScreenImpl splitScreenImpl2 = splitScreenImpl;
                            SplitScreenController.this.mStageCoordinator.startTasks(i2, bundle, i5, null, -1, null, i6, i7, 0.5f, 0, 0.5f, remoteTransition, null, -1, false, null);
                        }
                    });
                }
                return Unit.INSTANCE;
            }
        };
        recentTaskViewHolder.taskViewSizeProvider.listeners.add(recentTaskViewHolder);
        StandaloneCoroutine standaloneCoroutine = recentTaskViewHolder.job;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
        recentTaskViewHolder.job = CoroutineTracingKt.launchTraced$default(recentTaskViewHolder.scope, null, null, new RecentTaskViewHolder$bind$1(recentTask, recentTaskViewHolder, null), 7);
        recentTaskViewHolder.root.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.mediaprojection.appselector.view.RecentTaskViewHolder$sam$android_view_View_OnClickListener$0
            @Override // android.view.View.OnClickListener
            public final /* synthetic */ void onClick(View view) {
                function1.mo781invoke(view);
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return this.viewHolderFactory.create((ViewGroup) KeyguardSecurityContainer$UserSwitcherViewMode$2$$ExternalSyntheticOutline0.m(viewGroup, com.android.systemui.R.layout.media_projection_task_item, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onViewRecycled(RecyclerView.ViewHolder viewHolder) {
        RecentTaskViewHolder recentTaskViewHolder = (RecentTaskViewHolder) viewHolder;
        recentTaskViewHolder.taskViewSizeProvider.listeners.remove(recentTaskViewHolder);
        recentTaskViewHolder.iconView.setImageDrawable(null);
        recentTaskViewHolder.thumbnailView.bindTask(null, null);
        StandaloneCoroutine standaloneCoroutine = recentTaskViewHolder.job;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
        recentTaskViewHolder.job = null;
    }
}
