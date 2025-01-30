package com.android.systemui.statusbar.notification.logging;

import android.R;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.android.systemui.statusbar.notification.logging.NotificationMemoryViewWalker;
import com.android.systemui.util.ConvenienceExtensionsKt;
import java.util.HashSet;
import java.util.Iterator;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.sequences.SequenceBuilderIterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationMemoryViewWalker {
    public static final NotificationMemoryViewWalker INSTANCE = new NotificationMemoryViewWalker();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* JADX WARN: Removed duplicated region for block: B:25:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00c9  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x008c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void computeViewHierarchyUse(ViewGroup viewGroup, UsageBuilder usageBuilder, HashSet hashSet) {
        ImageView imageView;
        Drawable drawable;
        int i;
        Bitmap bitmap;
        Iterator it = ConvenienceExtensionsKt.getChildren(viewGroup).iterator();
        while (true) {
            SequenceBuilderIterator sequenceBuilderIterator = (SequenceBuilderIterator) it;
            if (!sequenceBuilderIterator.hasNext()) {
                return;
            }
            View view = (View) sequenceBuilderIterator.next();
            if (view instanceof ViewGroup) {
                computeViewHierarchyUse((ViewGroup) view, usageBuilder, hashSet);
            } else if ((view instanceof ImageView) && (drawable = (imageView = (ImageView) view).getDrawable()) != null) {
                int identityHashCode = System.identityHashCode(drawable);
                if (!hashSet.contains(Integer.valueOf(identityHashCode))) {
                    boolean z = drawable instanceof BitmapDrawable;
                    boolean z2 = false;
                    if (z && (bitmap = ((BitmapDrawable) drawable).getBitmap()) != null) {
                        int identityHashCode2 = System.identityHashCode(bitmap);
                        if (!hashSet.contains(Integer.valueOf(identityHashCode2))) {
                            hashSet.add(Integer.valueOf(identityHashCode2));
                            i = bitmap.getAllocationByteCount();
                            switch (imageView.getId()) {
                                case R.id.icon:
                                case R.id.costsMoney:
                                case R.id.mediaProjection:
                                    usageBuilder.smallIcon += i;
                                    break;
                                case R.id.alignBounds:
                                case R.id.feedbackVisual:
                                case R.id.firstStrongLtr:
                                case R.id.retailDemo:
                                case R.id.shortcut:
                                    usageBuilder.systemIcons += i;
                                    break;
                                case R.id.breadcrumb_section:
                                    usageBuilder.style += i;
                                    break;
                                case R.id.snooze_button:
                                    usageBuilder.largeIcon += i;
                                    break;
                                default:
                                    if (Log.isLoggable("NotificationMemory", 3)) {
                                        AbstractC0000x2c234b15.m3m("Custom view: ", view.getId() == -1 ? "no-id" : view.getResources().getResourceName(view.getId()), "NotificationMemory");
                                    }
                                    usageBuilder.customViews += i;
                                    break;
                            }
                            if (z) {
                                Bitmap bitmap2 = ((BitmapDrawable) drawable).getBitmap();
                                if ((bitmap2 != null ? bitmap2.getConfig() : null) != Bitmap.Config.HARDWARE) {
                                    z2 = true;
                                }
                            }
                            if (z2) {
                                usageBuilder.softwareBitmaps += i;
                            }
                            hashSet.add(Integer.valueOf(identityHashCode));
                        }
                    }
                    i = 0;
                    switch (imageView.getId()) {
                        case R.id.icon:
                        case R.id.costsMoney:
                        case R.id.mediaProjection:
                            break;
                        case R.id.alignBounds:
                        case R.id.feedbackVisual:
                        case R.id.firstStrongLtr:
                        case R.id.retailDemo:
                        case R.id.shortcut:
                            break;
                        case R.id.breadcrumb_section:
                            break;
                        case R.id.snooze_button:
                            break;
                    }
                    if (z) {
                    }
                    if (z2) {
                    }
                    hashSet.add(Integer.valueOf(identityHashCode));
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
}
