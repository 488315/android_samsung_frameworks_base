package com.android.systemui.statusbar.notification.logging;

import android.R;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.android.systemui.util.ConvenienceExtensionsKt;
import java.util.HashSet;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotificationMemoryViewWalker {
    public static final NotificationMemoryViewWalker INSTANCE = new NotificationMemoryViewWalker();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class UsageBuilder {
        public int customViews;
        public int largeIcon;
        public int smallIcon;
        public int softwareBitmaps;
        public int style;
        public int systemIcons;
    }

    private NotificationMemoryViewWalker() {
    }

    public static void computeViewHierarchyUse(ViewGroup viewGroup, UsageBuilder usageBuilder, HashSet hashSet) {
        ImageView imageView;
        Drawable drawable;
        Bitmap bitmap;
        for (View view : ConvenienceExtensionsKt.getChildren(viewGroup)) {
            if (view instanceof ViewGroup) {
                computeViewHierarchyUse((ViewGroup) view, usageBuilder, hashSet);
            } else {
                view.getClass();
                if ((view instanceof ImageView) && (drawable = (imageView = (ImageView) view).getDrawable()) != null) {
                    int identityHashCode = System.identityHashCode(drawable);
                    if (!hashSet.contains(Integer.valueOf(identityHashCode))) {
                        boolean z = drawable instanceof BitmapDrawable;
                        int i = 0;
                        if (z && (bitmap = ((BitmapDrawable) drawable).getBitmap()) != null) {
                            int identityHashCode2 = System.identityHashCode(bitmap);
                            if (!hashSet.contains(Integer.valueOf(identityHashCode2))) {
                                hashSet.add(Integer.valueOf(identityHashCode2));
                                i = bitmap.getAllocationByteCount();
                            }
                        }
                        switch (imageView.getId()) {
                            case R.id.icon:
                            case R.id.dvorak:
                            case R.id.multipleChoice:
                                usageBuilder.smallIcon += i;
                                break;
                            case R.id.autofill_dataset_icon:
                            case R.id.floating:
                            case R.id.game:
                            case R.id.sequentially:
                            case R.id.stateUnspecified:
                                usageBuilder.systemIcons += i;
                                break;
                            case R.id.checked:
                                usageBuilder.style += i;
                                break;
                            case R.id.tag_top_animator:
                                usageBuilder.largeIcon += i;
                                break;
                            default:
                                if (Log.isLoggable("NotificationMemory", 3)) {
                                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("Custom view: ", view.getId() == -1 ? "no-id" : view.getResources().getResourceName(view.getId()), "NotificationMemory");
                                }
                                usageBuilder.customViews += i;
                                break;
                        }
                        if (z) {
                            Bitmap bitmap2 = ((BitmapDrawable) drawable).getBitmap();
                            if ((bitmap2 != null ? bitmap2.getConfig() : null) != Bitmap.Config.HARDWARE) {
                                usageBuilder.softwareBitmaps += i;
                            }
                        }
                        hashSet.add(Integer.valueOf(identityHashCode));
                    }
                }
            }
        }
    }

    public static NotificationViewUsage getViewUsage(ViewType viewType, View[] viewArr, HashSet hashSet) {
        Lazy lazy = LazyKt__LazyJVMKt.lazy(new NotificationMemoryViewWalker$$ExternalSyntheticLambda0());
        int length = viewArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            View view = viewArr[i];
            ViewGroup viewGroup = view instanceof ViewGroup ? (ViewGroup) view : null;
            if (viewGroup != null) {
                UsageBuilder usageBuilder = (UsageBuilder) lazy.getValue();
                INSTANCE.getClass();
                computeViewHierarchyUse(viewGroup, usageBuilder, hashSet);
            }
            i++;
        }
        if (!lazy.isInitialized()) {
            return null;
        }
        UsageBuilder usageBuilder2 = (UsageBuilder) lazy.getValue();
        return new NotificationViewUsage(viewType, usageBuilder2.smallIcon, usageBuilder2.largeIcon, usageBuilder2.systemIcons, usageBuilder2.style, usageBuilder2.customViews, usageBuilder2.softwareBitmaps);
    }

    public static /* synthetic */ NotificationViewUsage getViewUsage$default(NotificationMemoryViewWalker notificationMemoryViewWalker, ViewType viewType, View[] viewArr) {
        HashSet hashSet = new HashSet();
        notificationMemoryViewWalker.getClass();
        return getViewUsage(viewType, viewArr, hashSet);
    }
}
