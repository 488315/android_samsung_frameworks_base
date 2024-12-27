package com.android.systemui.wallpaper.theme.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import java.util.LinkedList;

public final class FrameImageView extends ImageView {
    public Resources mApkResources;
    public int[] mImageSetIds;
    public final LinkedList mQueue;
    public Bitmap mUsed;

    public FrameImageView(Context context) {
        super(context);
        this.mQueue = new LinkedList();
    }

    @Override // android.widget.ImageView, android.view.View
    public final void onDetachedFromWindow() {
        if (this.mUsed != null) {
            Log.i("ImageWallpaper_AnimationBuilder", "recycle bitmap : " + this.mUsed);
            this.mUsed.recycle();
        }
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
