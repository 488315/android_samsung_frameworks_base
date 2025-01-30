package com.android.systemui.mediaprojection.appselector.view;

import android.app.IActivityTaskManager;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;
import com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorResultHandler;
import com.android.systemui.mediaprojection.appselector.view.RecentTasksAdapter;
import com.android.systemui.mediaprojection.appselector.view.TaskPreviewSizeProvider;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaProjectionRecentsViewController implements RecentTasksAdapter.RecentTaskClickListener, TaskPreviewSizeProvider.TaskPreviewSizeListener {
    public final IActivityTaskManager activityTaskManager;
    public List lastBoundData;
    public final RecentTasksAdapter.Factory recentTasksAdapterFactory;
    public final MediaProjectionAppSelectorResultHandler resultHandler;
    public final TaskPreviewSizeProvider taskViewSizeProvider;
    public Views views;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Views {
        public final View progress;
        public final View recentsContainer;
        public final RecyclerView recycler;
        public final ViewGroup root;

        public Views(ViewGroup viewGroup, View view, View view2, RecyclerView recyclerView) {
            this.root = viewGroup;
            this.recentsContainer = view;
            this.progress = view2;
            this.recycler = recyclerView;
        }
    }

    public MediaProjectionRecentsViewController(RecentTasksAdapter.Factory factory, TaskPreviewSizeProvider taskPreviewSizeProvider, IActivityTaskManager iActivityTaskManager, MediaProjectionAppSelectorResultHandler mediaProjectionAppSelectorResultHandler) {
        this.recentTasksAdapterFactory = factory;
        this.taskViewSizeProvider = taskPreviewSizeProvider;
        this.activityTaskManager = iActivityTaskManager;
        this.resultHandler = mediaProjectionAppSelectorResultHandler;
        taskPreviewSizeProvider.listeners.add(this);
    }

    public final void bind(List list) {
        Views views = this.views;
        if (views != null) {
            boolean isEmpty = list.isEmpty();
            ViewGroup viewGroup = views.root;
            if (isEmpty) {
                viewGroup.setVisibility(8);
                return;
            }
            views.progress.setVisibility(8);
            RecyclerView recyclerView = views.recycler;
            recyclerView.setVisibility(0);
            viewGroup.setVisibility(0);
            recyclerView.setAdapter(this.recentTasksAdapterFactory.create(list, this));
        }
        this.lastBoundData = list;
    }

    @Override // com.android.systemui.mediaprojection.appselector.view.TaskPreviewSizeProvider.TaskPreviewSizeListener
    public final void onTaskSizeChanged() {
        View view;
        Views views = this.views;
        if (views == null || (view = views.recentsContainer) == null) {
            return;
        }
        setTaskHeightSize(view);
    }

    public final void setTaskHeightSize(View view) {
        int dimensionPixelSize = (view.getContext().getResources().getDimensionPixelSize(R.dimen.media_projection_app_selector_task_icon_margin) * 2) + view.getContext().getResources().getDimensionPixelSize(R.dimen.media_projection_app_selector_task_icon_size) + this.taskViewSizeProvider.size.height();
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = dimensionPixelSize;
        view.setLayoutParams(layoutParams);
    }
}
