package com.android.systemui.wallpaper.theme.view;

import android.content.Context;
import android.content.res.Resources;
import android.widget.ImageView;
import java.util.LinkedList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class FrameImageView extends ImageView {
    public Resources mApkResources;
    public int[] mImageSetIds;
    public final LinkedList mQueue;

    public FrameImageView(Context context) {
        super(context);
        this.mQueue = new LinkedList();
    }

    @Override // android.widget.ImageView, android.view.View
    public final void onDetachedFromWindow() {
        this.mQueue.clear();
        super.onDetachedFromWindow();
    }

    @Override // android.widget.ImageView
    public final void setImageResource(int i) {
        try {
            setImageDrawable(this.mApkResources.getDrawable(i));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        } catch (OutOfMemoryError e3) {
            e3.printStackTrace();
        }
    }
}
