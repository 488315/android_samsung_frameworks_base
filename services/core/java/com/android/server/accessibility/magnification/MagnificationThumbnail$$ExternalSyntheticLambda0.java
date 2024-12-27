package com.android.server.accessibility.magnification;

import android.R;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class MagnificationThumbnail$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MagnificationThumbnail f$0;

    public /* synthetic */ MagnificationThumbnail$$ExternalSyntheticLambda0(
            MagnificationThumbnail magnificationThumbnail, int i) {
        this.$r8$classId = i;
        this.f$0 = magnificationThumbnail;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        MagnificationThumbnail magnificationThumbnail = this.f$0;
        switch (i) {
            case 0:
                if (magnificationThumbnail.mVisible) {
                    magnificationThumbnail.animateThumbnail(false);
                    break;
                }
                break;
            case 1:
                if (magnificationThumbnail.mVisible) {
                    if (magnificationThumbnail.mThumbnailLayout.getParent() != null) {
                        magnificationThumbnail.mWindowManager.removeView(
                                magnificationThumbnail.mThumbnailLayout);
                    }
                    magnificationThumbnail.mVisible = false;
                    break;
                }
                break;
            case 2:
                FrameLayout frameLayout =
                        (FrameLayout)
                                LayoutInflater.from(magnificationThumbnail.mContext)
                                        .inflate(17367467, (ViewGroup) null);
                magnificationThumbnail.mThumbnailLayout = frameLayout;
                magnificationThumbnail.mThumbnailView =
                        frameLayout.findViewById(R.id.accessibility_performAction_description);
                break;
            default:
                if (magnificationThumbnail.mThumbnailLayout.getParent() != null) {
                    magnificationThumbnail.mWindowManager.updateViewLayout(
                            magnificationThumbnail.mThumbnailLayout,
                            magnificationThumbnail.mBackgroundParams);
                    break;
                }
                break;
        }
    }
}
