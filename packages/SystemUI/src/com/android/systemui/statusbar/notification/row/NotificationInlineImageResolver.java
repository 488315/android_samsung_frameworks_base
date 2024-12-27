package com.android.systemui.statusbar.notification.row;

import android.R;
import android.app.ActivityManager;
import android.app.Notification;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import com.android.internal.widget.ImageResolver;
import com.android.internal.widget.LocalImageResolver;
import com.android.internal.widget.MessagingMessage;
import com.android.systemui.statusbar.notification.row.NotificationInlineImageCache;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

public final class NotificationInlineImageResolver implements ImageResolver {
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
        return this.mContext.getResources().getDimensionPixelSize(ActivityManager.isLowRamDeviceStatic() ? R.dimen.seekbar_track_progress_height_material : R.dimen.seekbar_track_background_height_material);
    }

    public int getMaxImageWidth() {
        return this.mContext.getResources().getDimensionPixelSize(ActivityManager.isLowRamDeviceStatic() ? R.dimen.select_dialog_padding_start_material : R.dimen.select_dialog_drawable_padding_start_material);
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

    public final void preloadImages(Notification notification2) {
        if (hasCache()) {
            HashSet hashSet = new HashSet();
            Bundle bundle = notification2.extras;
            if (bundle != null) {
                Parcelable[] parcelableArray = bundle.getParcelableArray("android.messages");
                List<Notification.MessagingStyle.Message> messagesFromBundleArray = parcelableArray == null ? null : Notification.MessagingStyle.Message.getMessagesFromBundleArray(parcelableArray);
                if (messagesFromBundleArray != null) {
                    for (Notification.MessagingStyle.Message message : messagesFromBundleArray) {
                        if (MessagingMessage.hasImage(message)) {
                            hashSet.add(message.getDataUri());
                        }
                    }
                }
                Parcelable[] parcelableArray2 = bundle.getParcelableArray("android.messages.historic");
                List<Notification.MessagingStyle.Message> messagesFromBundleArray2 = parcelableArray2 != null ? Notification.MessagingStyle.Message.getMessagesFromBundleArray(parcelableArray2) : null;
                if (messagesFromBundleArray2 != null) {
                    for (Notification.MessagingStyle.Message message2 : messagesFromBundleArray2) {
                        if (MessagingMessage.hasImage(message2)) {
                            hashSet.add(message2.getDataUri());
                        }
                    }
                }
                this.mWantedUriSet = hashSet;
            }
            this.mWantedUriSet.forEach(new Consumer() { // from class: com.android.systemui.statusbar.notification.row.NotificationInlineImageResolver$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    NotificationInlineImageResolver notificationInlineImageResolver = NotificationInlineImageResolver.this;
                    Uri uri = (Uri) obj;
                    if (((NotificationInlineImageCache) notificationInlineImageResolver.mImageCache).mCache.containsKey(uri)) {
                        return;
                    }
                    NotificationInlineImageCache notificationInlineImageCache = (NotificationInlineImageCache) notificationInlineImageResolver.mImageCache;
                    notificationInlineImageCache.getClass();
                    NotificationInlineImageCache.PreloadImageTask preloadImageTask = new NotificationInlineImageCache.PreloadImageTask(notificationInlineImageCache.mResolver);
                    preloadImageTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, uri);
                    notificationInlineImageCache.mCache.put(uri, preloadImageTask);
                }
            });
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
