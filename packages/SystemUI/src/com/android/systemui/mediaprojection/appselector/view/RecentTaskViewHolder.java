package com.android.systemui.mediaprojection.appselector.view;

import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;
import com.android.systemui.mediaprojection.appselector.data.BadgedAppIconLoader;
import com.android.systemui.mediaprojection.appselector.data.RecentTaskLabelLoader;
import com.android.systemui.mediaprojection.appselector.data.RecentTaskThumbnailLoader;
import com.android.systemui.mediaprojection.appselector.view.TaskPreviewSizeProvider;
import com.android.systemui.statusbar.policy.ConfigurationController;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;

public final class RecentTaskViewHolder extends RecyclerView.ViewHolder implements ConfigurationController.ConfigurationListener, TaskPreviewSizeProvider.TaskPreviewSizeListener {
    public final BadgedAppIconLoader iconLoader;
    public final ImageView iconView;
    public Job job;
    public final RecentTaskLabelLoader labelLoader;
    public final ViewGroup root;
    public final CoroutineScope scope;
    public final TaskPreviewSizeProvider taskViewSizeProvider;
    public final RecentTaskThumbnailLoader thumbnailLoader;
    public final MediaProjectionTaskView thumbnailView;

    public interface Factory {
        RecentTaskViewHolder create(ViewGroup viewGroup);
    }

    public RecentTaskViewHolder(ViewGroup viewGroup, BadgedAppIconLoader badgedAppIconLoader, RecentTaskThumbnailLoader recentTaskThumbnailLoader, RecentTaskLabelLoader recentTaskLabelLoader, TaskPreviewSizeProvider taskPreviewSizeProvider, CoroutineScope coroutineScope) {
        super(viewGroup);
        this.root = viewGroup;
        this.iconLoader = badgedAppIconLoader;
        this.thumbnailLoader = recentTaskThumbnailLoader;
        this.labelLoader = recentTaskLabelLoader;
        this.taskViewSizeProvider = taskPreviewSizeProvider;
        this.scope = coroutineScope;
        MediaProjectionTaskView mediaProjectionTaskView = (MediaProjectionTaskView) viewGroup.requireViewById(R.id.task_thumbnail);
        this.thumbnailView = mediaProjectionTaskView;
        this.iconView = (ImageView) viewGroup.requireViewById(R.id.task_icon);
        ViewGroup.LayoutParams layoutParams = mediaProjectionTaskView.getLayoutParams();
        layoutParams.width = taskPreviewSizeProvider.size.width();
        layoutParams.height = taskPreviewSizeProvider.size.height();
        mediaProjectionTaskView.setLayoutParams(layoutParams);
    }

    @Override // com.android.systemui.mediaprojection.appselector.view.TaskPreviewSizeProvider.TaskPreviewSizeListener
    public final void onTaskSizeChanged() {
        MediaProjectionTaskView mediaProjectionTaskView = this.thumbnailView;
        ViewGroup.LayoutParams layoutParams = mediaProjectionTaskView.getLayoutParams();
        TaskPreviewSizeProvider taskPreviewSizeProvider = this.taskViewSizeProvider;
        layoutParams.width = taskPreviewSizeProvider.size.width();
        layoutParams.height = taskPreviewSizeProvider.size.height();
        mediaProjectionTaskView.setLayoutParams(layoutParams);
    }
}
