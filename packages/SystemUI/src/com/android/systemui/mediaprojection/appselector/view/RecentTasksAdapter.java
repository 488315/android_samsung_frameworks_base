package com.android.systemui.mediaprojection.appselector.view;

import android.R;
import android.app.ActivityOptions;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.View;
import android.view.ViewGroup;
import android.window.RemoteTransition;
import androidx.mediarouter.app.MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.logging.InstanceId;
import com.android.systemui.Flags;
import com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorActivity;
import com.android.systemui.mediaprojection.appselector.data.RecentTask;
import com.android.systemui.mediaprojection.appselector.view.RecentTaskViewHolder;
import com.android.systemui.mediaprojection.appselector.view.RecentTasksAdapter;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.splitscreen.SplitScreen;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.util.SplitBounds;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.Job;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class RecentTasksAdapter extends RecyclerView.Adapter {
    public final List items;
    public final RecentTaskClickListener listener;
    public final RecentTaskViewHolder.Factory viewHolderFactory;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Factory {
        RecentTasksAdapter create(List list, RecentTaskClickListener recentTaskClickListener);
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        final Function1 function1 = new Function1() { // from class: com.android.systemui.mediaprojection.appselector.view.RecentTasksAdapter$onBindViewHolder$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                ActivityOptions makeScaleUpAnimation;
                RecentTasksAdapter.RecentTaskClickListener recentTaskClickListener = RecentTasksAdapter.this.listener;
                RecentTask recentTask2 = recentTask;
                View view = recentTaskViewHolder.itemView;
                final MediaProjectionRecentsViewController mediaProjectionRecentsViewController = (MediaProjectionRecentsViewController) recentTaskClickListener;
                mediaProjectionRecentsViewController.getClass();
                final ActivityOptions.LaunchCookie launchCookie = new ActivityOptions.LaunchCookie();
                Flags.FEATURE_FLAGS.getClass();
                boolean z = recentTask2.isForegroundTask;
                SplitBounds splitBounds = recentTask2.splitBounds;
                if (z) {
                    makeScaleUpAnimation = ActivityOptions.makeCustomTaskAnimation(view.getContext(), 0, R.anim.slide_in_up, null, null, null);
                    Intrinsics.checkNotNull(makeScaleUpAnimation);
                } else if (!mediaProjectionRecentsViewController.splitScreen.isPresent() || splitBounds == null) {
                    makeScaleUpAnimation = ActivityOptions.makeScaleUpAnimation(view, 0, 0, view.getWidth(), view.getHeight());
                    Intrinsics.checkNotNull(makeScaleUpAnimation);
                } else {
                    makeScaleUpAnimation = ActivityOptions.makeBasic();
                    Intrinsics.checkNotNull(makeScaleUpAnimation);
                }
                makeScaleUpAnimation.setPendingIntentBackgroundActivityStartMode(1);
                makeScaleUpAnimation.setLaunchDisplayId(recentTask2.displayId);
                makeScaleUpAnimation.setLaunchCookie(launchCookie);
                final int i2 = recentTask2.taskId;
                Function0 function0 = new Function0() { // from class: com.android.systemui.mediaprojection.appselector.view.MediaProjectionRecentsViewController$onRecentAppClicked$handleResult$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        ((MediaProjectionAppSelectorActivity) MediaProjectionRecentsViewController.this.resultHandler).returnSelectedApp(launchCookie, i2);
                        return Unit.INSTANCE;
                    }
                };
                if (!mediaProjectionRecentsViewController.splitScreen.isPresent() || splitBounds == null || z) {
                    mediaProjectionRecentsViewController.activityTaskManager.startActivityFromRecents(i2, makeScaleUpAnimation.toBundle());
                    function0.invoke();
                } else {
                    Intrinsics.checkNotNull(splitBounds);
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
                    final Bundle bundle = makeScaleUpAnimation.toBundle();
                    final int i7 = splitBounds.snapPosition;
                    final SplitScreenController.SplitScreenImpl splitScreenImpl = (SplitScreenController.SplitScreenImpl) splitScreen;
                    ((HandlerExecutor) SplitScreenController.this.mMainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.splitscreen.SplitScreenController$SplitScreenImpl$$ExternalSyntheticLambda1
                        public final /* synthetic */ Bundle f$4 = null;
                        public final /* synthetic */ InstanceId f$8 = null;

                        @Override // java.lang.Runnable
                        public final void run() {
                            SplitScreenController.SplitScreenImpl splitScreenImpl2 = SplitScreenController.SplitScreenImpl.this;
                            SplitScreenController.this.mStageCoordinator.startTasks(i2, bundle, i5, this.f$4, -1, null, i6, i7, 0.5f, 0, 0.5f, remoteTransition, this.f$8, -1, null);
                        }
                    });
                }
                return Unit.INSTANCE;
            }
        };
        recentTaskViewHolder.taskViewSizeProvider.listeners.add(recentTaskViewHolder);
        Job job = recentTaskViewHolder.job;
        if (job != null) {
            job.cancel(null);
        }
        recentTaskViewHolder.job = BuildersKt.launch$default(recentTaskViewHolder.scope, null, null, new RecentTaskViewHolder$bind$1(recentTask, recentTaskViewHolder, null), 3);
        recentTaskViewHolder.root.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.mediaprojection.appselector.view.RecentTaskViewHolder$sam$android_view_View_OnClickListener$0
            @Override // android.view.View.OnClickListener
            public final /* synthetic */ void onClick(View view) {
                Function1.this.invoke(view);
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(int i, ViewGroup viewGroup) {
        return this.viewHolderFactory.create((ViewGroup) MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(viewGroup, com.android.systemui.R.layout.media_projection_task_item, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onViewRecycled(RecyclerView.ViewHolder viewHolder) {
        RecentTaskViewHolder recentTaskViewHolder = (RecentTaskViewHolder) viewHolder;
        recentTaskViewHolder.taskViewSizeProvider.listeners.remove(recentTaskViewHolder);
        recentTaskViewHolder.iconView.setImageDrawable(null);
        recentTaskViewHolder.thumbnailView.bindTask(null, null);
        Job job = recentTaskViewHolder.job;
        if (job != null) {
            job.cancel(null);
        }
        recentTaskViewHolder.job = null;
    }
}
