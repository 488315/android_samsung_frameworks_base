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

/* loaded from: classes3.dex */
public final class NotificationMemoryViewWalker {
    public static final NotificationMemoryViewWalker INSTANCE = new NotificationMemoryViewWalker();

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
                    int iIdentityHashCode = System.identityHashCode(drawable);
                    if (!hashSet.contains(Integer.valueOf(iIdentityHashCode))) {
                        boolean z = drawable instanceof BitmapDrawable;
                        int allocationByteCount = 0;
                        if (z && (bitmap = ((BitmapDrawable) drawable).getBitmap()) != null) {
                            int iIdentityHashCode2 = System.identityHashCode(bitmap);
                            if (!hashSet.contains(Integer.valueOf(iIdentityHashCode2))) {
                                hashSet.add(Integer.valueOf(iIdentityHashCode2));
                                allocationByteCount = bitmap.getAllocationByteCount();
                            }
                        }
                        switch (imageView.getId()) {
                            case R.id.icon:
                            case R.id.dvorak:
                            case R.id.multipleChoice:
                                usageBuilder.smallIcon += allocationByteCount;
                                break;
                            case R.id.autofill_dataset_icon:
                            case R.id.floating:
                            case R.id.game:
                            case R.id.serial_number:
                            case R.id.stateVisible:
                                usageBuilder.systemIcons += allocationByteCount;
                                break;
                            case R.id.checked:
                                usageBuilder.style += allocationByteCount;
                                break;
                            case R.id.tag_top_override:
                                usageBuilder.largeIcon += allocationByteCount;
                                break;
                            default:
                                if (Log.isLoggable("NotificationMemory", 3)) {
                                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("Custom view: ", view.getId() == -1 ? "no-id" : view.getResources().getResourceName(view.getId()), "NotificationMemory");
                                }
                                usageBuilder.customViews += allocationByteCount;
                                break;
                        }
                        if (z) {
                            Bitmap bitmap2 = ((BitmapDrawable) drawable).getBitmap();
                            if ((bitmap2 != null ? bitmap2.getConfig() : null) != Bitmap.Config.HARDWARE) {
                                usageBuilder.softwareBitmaps += allocationByteCount;
                            }
                        }
                        hashSet.add(Integer.valueOf(iIdentityHashCode));
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
