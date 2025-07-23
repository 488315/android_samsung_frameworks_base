package com.android.systemui.statusbar.notification.row;

import android.R;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.util.Dumpable;
import android.util.Log;
import android.util.Size;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import com.android.internal.widget.NotificationDrawableConsumer;
import com.android.internal.widget.NotificationIconManager;
import com.android.systemui.broadcast.ActionReceiver$$ExternalSyntheticOutline0;
import com.android.systemui.graphics.ImageLoader;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import java.io.PrintWriter;
import kotlin.Pair;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class BigPictureIconManager implements NotificationIconManager, Dumpable {
    public final CoroutineDispatcher bgDispatcher;
    public final Context context;
    public DrawableState displayedState = DrawableState.Initial.INSTANCE;
    public NotificationDrawableConsumer drawableConsumer;
    public final ImageLoader imageLoader;
    public final StandaloneCoroutine lastLoadingJob;
    public final CoroutineDispatcher mainDispatcher;
    public final int maxHeight;
    public final int maxWidth;
    public final CoroutineScope scope;
    public final BigPictureStatsManager statsManager;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class DrawableState {
        public final Icon icon;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Empty extends DrawableState {
            public static final Empty INSTANCE = new Empty();

            private Empty() {
                super(null, 0 == true ? 1 : 0);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof Empty);
            }

            public final int hashCode() {
                return -356686193;
            }

            public final String toString() {
                return "Empty";
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class FullImage extends DrawableState {
            public final Size drawableSize;
            public final Icon icon;

            public FullImage(Icon icon, Size size) {
                super(icon, null);
                this.icon = icon;
                this.drawableSize = size;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof FullImage)) {
                    return false;
                }
                FullImage fullImage = (FullImage) obj;
                return Intrinsics.areEqual(this.icon, fullImage.icon) && Intrinsics.areEqual(this.drawableSize, fullImage.drawableSize);
            }

            public final int hashCode() {
                return this.drawableSize.hashCode() + (this.icon.hashCode() * 31);
            }

            public final String toString() {
                return "FullImage(icon=" + this.icon + ", drawableSize=" + this.drawableSize + ")";
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Initial extends DrawableState {
            public static final Initial INSTANCE = new Initial();

            private Initial() {
                super(null, 0 == true ? 1 : 0);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof Initial);
            }

            public final int hashCode() {
                return 99151878;
            }

            public final String toString() {
                return "Initial";
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class PlaceHolder extends DrawableState {
            public final Size drawableSize;
            public final Icon icon;

            public PlaceHolder(Icon icon, Size size) {
                super(icon, null);
                this.icon = icon;
                this.drawableSize = size;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof PlaceHolder)) {
                    return false;
                }
                PlaceHolder placeHolder = (PlaceHolder) obj;
                return Intrinsics.areEqual(this.icon, placeHolder.icon) && Intrinsics.areEqual(this.drawableSize, placeHolder.drawableSize);
            }

            public final int hashCode() {
                return this.drawableSize.hashCode() + (this.icon.hashCode() * 31);
            }

            public final String toString() {
                return "PlaceHolder(icon=" + this.icon + ", drawableSize=" + this.drawableSize + ")";
            }
        }

        public /* synthetic */ DrawableState(Icon icon, DefaultConstructorMarker defaultConstructorMarker) {
            this(icon);
        }

        private DrawableState(Icon icon) {
            this.icon = icon;
        }
    }

    public BigPictureIconManager(Context context, ImageLoader imageLoader, BigPictureStatsManager bigPictureStatsManager, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2) {
        this.context = context;
        this.imageLoader = imageLoader;
        this.statsManager = bigPictureStatsManager;
        this.scope = coroutineScope;
        this.mainDispatcher = coroutineDispatcher;
        this.bgDispatcher = coroutineDispatcher2;
        this.maxWidth = context.getResources().getDimensionPixelSize(ActivityManager.isLowRamDeviceStatic() ? R.dimen.timepicker_selector_radius : R.dimen.timepicker_selector_dot_radius);
        this.maxHeight = context.getResources().getDimensionPixelSize(ActivityManager.isLowRamDeviceStatic() ? R.dimen.timepicker_radial_picker_top_margin : R.dimen.timepicker_radial_picker_horizontal_margin);
    }

    @Override // android.util.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        DrawableState drawableState = this.displayedState;
        boolean z = this.drawableConsumer != null;
        StringBuilder sb = new StringBuilder("{ state:");
        sb.append(drawableState);
        sb.append(", hasConsumer:");
        sb.append(z);
        sb.append(", viewShown:");
        ActionReceiver$$ExternalSyntheticOutline0.m(printWriter, "BigPictureIconManager ", MoveResult$$ExternalSyntheticOutline0.m(sb, false, "}"));
    }

    public final Pair loadImageSync(Icon icon) {
        ImageLoader imageLoader = this.imageLoader;
        Context context = this.context;
        int i = this.maxWidth;
        int i2 = this.maxHeight;
        ImageLoader.Companion companion = ImageLoader.Companion;
        imageLoader.getClass();
        Drawable loadDrawableSync = ImageLoader.loadDrawableSync(icon, context, i, i2, 0);
        if (loadDrawableSync == null) {
            return null;
        }
        DrawableState drawableState = this.displayedState;
        if (drawableState instanceof DrawableState.PlaceHolder) {
            Size size = ((DrawableState.PlaceHolder) drawableState).drawableSize;
            int width = size.getWidth();
            int height = size.getHeight();
            Size size2 = new Size(loadDrawableSync.getIntrinsicWidth(), loadDrawableSync.getIntrinsicHeight());
            int width2 = size2.getWidth();
            int height2 = size2.getHeight();
            if (width != width2 || height != height2) {
                StringBuilder m = MutableObjectList$$ExternalSyntheticOutline0.m(width, height, "Mismatch in dimensions, when replacing PlaceHolder ", " X ", " with Drawable ");
                m.append(width2);
                m.append(" X ");
                m.append(height2);
                m.append(".");
                Log.e("BigPicImageLoader", m.toString());
            }
        }
        return new Pair(loadDrawableSync, new DrawableState.FullImage(icon, new Size(loadDrawableSync.getIntrinsicWidth(), loadDrawableSync.getIntrinsicHeight())));
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00ef  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Runnable updateIcon(com.android.internal.widget.NotificationDrawableConsumer r5, android.graphics.drawable.Icon r6) {
        /*
            Method dump skipped, instructions count: 250
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.row.BigPictureIconManager.updateIcon(com.android.internal.widget.NotificationDrawableConsumer, android.graphics.drawable.Icon):java.lang.Runnable");
    }
}
