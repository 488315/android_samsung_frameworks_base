package com.android.systemui.mediaprojection.appselector.view;

import android.app.ActivityOptions;
import android.os.Binder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;
import com.android.systemui.media.MediaProjectionAppSelectorActivity;
import com.android.systemui.mediaprojection.appselector.data.RecentTask;
import com.android.systemui.mediaprojection.appselector.view.RecentTaskViewHolder;
import com.android.systemui.mediaprojection.appselector.view.RecentTasksAdapter;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.StandaloneCoroutine;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class RecentTasksAdapter extends RecyclerView.Adapter {
    public final List items;
    public final RecentTaskClickListener listener;
    public final RecentTaskViewHolder.Factory viewHolderFactory;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Factory {
        RecentTasksAdapter create(List list, RecentTaskClickListener recentTaskClickListener);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
                RecentTasksAdapter.RecentTaskClickListener recentTaskClickListener = RecentTasksAdapter.this.listener;
                RecentTask recentTask2 = recentTask;
                View view = recentTaskViewHolder.itemView;
                MediaProjectionRecentsViewController mediaProjectionRecentsViewController = (MediaProjectionRecentsViewController) recentTaskClickListener;
                mediaProjectionRecentsViewController.getClass();
                Binder binder = new Binder();
                ActivityOptions makeScaleUpAnimation = ActivityOptions.makeScaleUpAnimation(view, 0, 0, view.getWidth(), view.getHeight());
                makeScaleUpAnimation.setLaunchCookie(binder);
                mediaProjectionRecentsViewController.activityTaskManager.startActivityFromRecents(recentTask2.taskId, makeScaleUpAnimation.toBundle());
                ((MediaProjectionAppSelectorActivity) mediaProjectionRecentsViewController.resultHandler).returnSelectedApp(binder);
                return Unit.INSTANCE;
            }
        };
        recentTaskViewHolder.taskViewSizeProvider.listeners.add(recentTaskViewHolder);
        StandaloneCoroutine standaloneCoroutine = recentTaskViewHolder.job;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
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
    public final RecyclerView.ViewHolder onCreateViewHolder(RecyclerView recyclerView, int i) {
        return this.viewHolderFactory.create((ViewGroup) LayoutInflater.from(recyclerView.getContext()).inflate(R.layout.media_projection_task_item, (ViewGroup) recyclerView, false));
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
