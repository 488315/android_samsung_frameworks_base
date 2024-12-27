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
import com.android.systemui.statusbar.notification.logging.NotificationMemoryViewWalker;
import com.android.systemui.util.ConvenienceExtensionsKt;
import java.util.HashSet;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class NotificationMemoryViewWalker {
    public static final NotificationMemoryViewWalker INSTANCE = new NotificationMemoryViewWalker();

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
                Intrinsics.checkNotNull(view);
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
                            case R.id.dangerous:
                            case R.id.media_route_list:
                                usageBuilder.smallIcon += i;
                                break;
                            case R.id.alignMargins:
                            case R.id.fill_vertical:
                            case R.id.flagDefault:
                            case R.id.right_icon:
                            case R.id.silent:
                                usageBuilder.systemIcons += i;
                                break;
                            case R.id.button4:
                                usageBuilder.style += i;
                                break;
                            case R.id.splashscreen_branding_view:
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
        Lazy lazy = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.logging.NotificationMemoryViewWalker$getViewUsage$usageBuilder$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return new NotificationMemoryViewWalker.UsageBuilder();
            }
        });
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
