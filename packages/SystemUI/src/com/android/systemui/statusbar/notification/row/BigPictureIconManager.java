package com.android.systemui.statusbar.notification.row;

import android.R;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.ImageDecoder;
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
import com.android.systemui.util.Assert;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import java.io.PrintWriter;
import kotlin.Pair;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;

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

    public abstract class DrawableState {
        public final Icon icon;

        public final class Empty extends DrawableState {
            public static final Empty INSTANCE = new Empty();

            /* JADX WARN: Multi-variable type inference failed */
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

        public final class Initial extends DrawableState {
            public static final Initial INSTANCE = new Initial();

            /* JADX WARN: Multi-variable type inference failed */
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
        this.maxWidth = context.getResources().getDimensionPixelSize(ActivityManager.isLowRamDeviceStatic() ? R.dimen.timepicker_selector_stroke : R.dimen.timepicker_selector_radius);
        this.maxHeight = context.getResources().getDimensionPixelSize(ActivityManager.isLowRamDeviceStatic() ? R.dimen.timepicker_selector_dot_radius : R.dimen.timepicker_radial_picker_top_margin);
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
        Drawable drawableLoadDrawableSync = ImageLoader.loadDrawableSync(icon, context, i, i2, 0);
        if (drawableLoadDrawableSync == null) {
            return null;
        }
        DrawableState drawableState = this.displayedState;
        if (drawableState instanceof DrawableState.PlaceHolder) {
            Size size = ((DrawableState.PlaceHolder) drawableState).drawableSize;
            int width = size.getWidth();
            int height = size.getHeight();
            Size size2 = new Size(drawableLoadDrawableSync.getIntrinsicWidth(), drawableLoadDrawableSync.getIntrinsicHeight());
            int width2 = size2.getWidth();
            int height2 = size2.getHeight();
            if (width != width2 || height != height2) {
                StringBuilder sbM = MutableObjectList$$ExternalSyntheticOutline0.m(width, height, "Mismatch in dimensions, when replacing PlaceHolder ", " X ", " with Drawable ");
                sbM.append(width2);
                sbM.append(" X ");
                sbM.append(height2);
                sbM.append(".");
                Log.e("BigPicImageLoader", sbM.toString());
            }
        }
        return new Pair(drawableLoadDrawableSync, new DrawableState.FullImage(icon, new Size(drawableLoadDrawableSync.getIntrinsicWidth(), drawableLoadDrawableSync.getIntrinsicHeight())));
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00ef  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Runnable updateIcon(NotificationDrawableConsumer notificationDrawableConsumer, Icon icon) {
        Size size;
        this.drawableConsumer = notificationDrawableConsumer;
        StandaloneCoroutine standaloneCoroutine = this.lastLoadingJob;
        final Pair pairLoadImageSync = null;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
        if (icon != null) {
            Integer numValueOf = icon != null ? Integer.valueOf(icon.getType()) : null;
            boolean z = true;
            if ((numValueOf == null || numValueOf.intValue() != 1) && ((numValueOf == null || numValueOf.intValue() != 5) && ((numValueOf == null || numValueOf.intValue() != 3) && numValueOf != null))) {
                z = false;
            }
            if (z) {
                pairLoadImageSync = loadImageSync(icon);
            } else {
                ImageLoader imageLoader = this.imageLoader;
                Context context = this.context;
                imageLoader.getClass();
                int type = icon.getType();
                if (type == 4 || type == 6) {
                    ImageDecoder.Source sourceCreateSource = ImageDecoder.createSource(context.getContentResolver(), icon.getUri());
                    try {
                        size = ImageDecoder.decodeHeader(sourceCreateSource).getSize();
                    } catch (Exception e) {
                        Log.w("ImageLoader", "Failed to load source " + sourceCreateSource, e);
                    }
                    if (size != null) {
                        int i = this.maxWidth;
                        int i2 = this.maxHeight;
                        if (size.getWidth() > i || size.getHeight() > i2) {
                            float fMin = Math.min(i <= 0 ? 1.0f : i / size.getWidth(), i2 <= 0 ? 1.0f : i2 / size.getHeight());
                            if (fMin < 1.0f) {
                                size = new Size((int) (size.getWidth() * fMin), (int) (size.getHeight() * fMin));
                            }
                        }
                        PlaceHolderDrawable placeHolderDrawable = new PlaceHolderDrawable(size.getWidth(), size.getHeight());
                        pairLoadImageSync = new Pair(placeHolderDrawable, new DrawableState.PlaceHolder(icon, new Size(placeHolderDrawable.getIntrinsicWidth(), placeHolderDrawable.getIntrinsicHeight())));
                    }
                    if (pairLoadImageSync == null) {
                        pairLoadImageSync = loadImageSync(icon);
                    }
                } else {
                    size = null;
                    if (size != null) {
                    }
                    if (pairLoadImageSync == null) {
                    }
                }
            }
        }
        return new Runnable() { // from class: com.android.systemui.statusbar.notification.row.BigPictureIconManager.updateIcon.1
            @Override // java.lang.Runnable
            public final void run() {
                DrawableState drawableState;
                BigPictureIconManager bigPictureIconManager = BigPictureIconManager.this;
                Pair pair = pairLoadImageSync;
                bigPictureIconManager.getClass();
                Assert.isMainThread();
                NotificationDrawableConsumer notificationDrawableConsumer2 = bigPictureIconManager.drawableConsumer;
                if (notificationDrawableConsumer2 != null) {
                    notificationDrawableConsumer2.setImageDrawable(pair != null ? (Drawable) pair.getFirst() : null);
                }
                if (pair == null || (drawableState = (DrawableState) pair.getSecond()) == null) {
                    drawableState = DrawableState.Empty.INSTANCE;
                }
                bigPictureIconManager.displayedState = drawableState;
            }
        };
    }
}
