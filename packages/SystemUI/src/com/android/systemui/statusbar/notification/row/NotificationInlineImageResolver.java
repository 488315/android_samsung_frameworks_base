package com.android.systemui.statusbar.notification.row;

import android.R;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import com.android.internal.widget.ImageResolver;
import com.android.internal.widget.LocalImageResolver;
import com.android.systemui.statusbar.notification.row.NotificationInlineImageCache;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* loaded from: classes3.dex */
public class NotificationInlineImageResolver implements ImageResolver {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context mContext;
    public final ImageCache mImageCache;
    protected int mMaxImageHeight;
    protected int mMaxImageWidth;
    public Set mWantedUriSet;

    public interface ImageCache {
    }

    public NotificationInlineImageResolver(Context context, ImageCache imageCache) {
        this.mContext = context;
        this.mImageCache = imageCache;
        if (imageCache != null) {
            ((NotificationInlineImageCache) imageCache).mResolver = this;
        }
        this.mMaxImageWidth = getMaxImageWidth();
        this.mMaxImageHeight = getMaxImageHeight();
    }

    public final void cancelRunningTasks() {
        if (hasCache()) {
            ((NotificationInlineImageCache) this.mImageCache).mCache.forEach(new NotificationInlineImageCache$$ExternalSyntheticLambda1());
        }
    }

    public Context getContext() {
        return this.mContext;
    }

    public int getMaxImageHeight() {
        return this.mContext.getResources().getDimensionPixelSize(ActivityManager.isLowRamDeviceStatic() ? R.dimen.tooltip_vertical_padding : R.dimen.tooltip_precise_anchor_threshold);
    }

    public int getMaxImageWidth() {
        return this.mContext.getResources().getDimensionPixelSize(ActivityManager.isLowRamDeviceStatic() ? R.dimen.tooltip_y_offset_touch : R.dimen.tooltip_y_offset_non_touch);
    }

    public final boolean hasCache() {
        return (this.mImageCache == null || ActivityManager.isLowRamDeviceStatic()) ? false : true;
    }

    public final Drawable loadImage(Uri uri) {
        return hasCache() ? loadImageFromCache(uri, 100L) : resolveImage(uri);
    }

    public final Drawable loadImageFromCache(Uri uri, long j) {
        if (!((NotificationInlineImageCache) this.mImageCache).mCache.containsKey(uri)) {
            NotificationInlineImageCache notificationInlineImageCache = (NotificationInlineImageCache) this.mImageCache;
            notificationInlineImageCache.getClass();
            NotificationInlineImageCache.PreloadImageTask preloadImageTask = new NotificationInlineImageCache.PreloadImageTask(notificationInlineImageCache.mResolver);
            preloadImageTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, uri);
            notificationInlineImageCache.mCache.put(uri, preloadImageTask);
        }
        NotificationInlineImageCache notificationInlineImageCache2 = (NotificationInlineImageCache) this.mImageCache;
        notificationInlineImageCache2.getClass();
        try {
            return (Drawable) ((NotificationInlineImageCache.PreloadImageTask) notificationInlineImageCache2.mCache.get(uri)).get(j, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | CancellationException | ExecutionException | TimeoutException e) {
            Log.d("NotificationInlineImageCache", "get: Failed get image from " + uri + " " + e);
            return null;
        }
    }

    public final Drawable resolveImage(Uri uri) {
        try {
            return LocalImageResolver.resolveImage(uri, this.mContext, this.mMaxImageWidth, this.mMaxImageHeight);
        } catch (Exception e) {
            Log.d("NotificationInlineImageResolver", "resolveImage: Can't load image from " + uri, e);
            return null;
        }
    }
}
