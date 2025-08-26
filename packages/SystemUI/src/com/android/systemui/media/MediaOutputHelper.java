package com.android.systemui.media;

import android.content.res.Resources;
import android.graphics.Rect;
import android.view.View;
import com.android.systemui.qs.SecQSDetailController;
import com.android.systemui.qs.tiles.detail.MediaOutputDetailAdapter;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class MediaOutputHelper {
    public final MediaOutputDetailAdapter mediaOutputDetailAdapter;
    public View mediaRootView;
    public final SecQSDetailController qsDetailController;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
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
            public final void onDismissRequested() throws Resources.NotFoundException {
                MediaOutputHelper.this.qsDetailController.closeDetail();
            }
        };
    }

    public final void showDetail() throws Resources.NotFoundException {
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
