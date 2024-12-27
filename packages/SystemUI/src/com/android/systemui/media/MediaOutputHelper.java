package com.android.systemui.media;

import android.graphics.Rect;
import android.view.View;
import com.android.systemui.qs.SecQSDetailController;
import com.android.systemui.qs.tiles.detail.MediaOutputDetailAdapter;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class MediaOutputHelper {
    public final MediaOutputDetailAdapter mediaOutputDetailAdapter;
    public View mediaRootView;
    public final SecQSDetailController qsDetailController;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public MediaOutputHelper(SecMediaHost secMediaHost, SecQSDetailController secQSDetailController, MediaOutputDetailAdapter mediaOutputDetailAdapter) {
        this.qsDetailController = secQSDetailController;
        this.mediaOutputDetailAdapter = mediaOutputDetailAdapter;
        mediaOutputDetailAdapter.callback = new MediaOutputDetailAdapter.Callback() { // from class: com.android.systemui.media.MediaOutputHelper.1
            @Override // com.android.systemui.qs.tiles.detail.MediaOutputDetailAdapter.Callback
            public final void onDismissRequested() {
                MediaOutputHelper.this.qsDetailController.closeDetail();
            }

            @Override // com.android.systemui.qs.tiles.detail.MediaOutputDetailAdapter.Callback
            public final void showAdapter() {
                MediaOutputHelper mediaOutputHelper = MediaOutputHelper.this;
                mediaOutputHelper.qsDetailController.showTargetDetail(mediaOutputHelper.mediaOutputDetailAdapter);
            }
        };
    }

    public final void showDetail() {
        View view;
        SecQSDetailController secQSDetailController = this.qsDetailController;
        boolean qsExpanded = secQSDetailController.getQsExpanded();
        MediaOutputDetailAdapter mediaOutputDetailAdapter = this.mediaOutputDetailAdapter;
        if (qsExpanded && (view = this.mediaRootView) != null) {
            Rect rect = new Rect();
            view.getGlobalVisibleRect(rect);
            rect.right = view.getMeasuredWidth() + rect.left;
            rect.bottom = view.getMeasuredHeight() + rect.top;
            mediaOutputDetailAdapter.fromRect = rect;
        }
        secQSDetailController.showTargetDetail(mediaOutputDetailAdapter);
    }
}
