package com.android.systemui.statusbar.notification.row;

import android.R;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.ImageDecoder;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.util.Dumpable;
import android.util.Log;
import android.util.Size;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import com.android.internal.widget.NotificationDrawableConsumer;
import com.android.internal.widget.NotificationIconManager;
import com.android.systemui.biometrics.UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0;
import com.android.systemui.graphics.ImageLoader;
import java.io.PrintWriter;
import kotlin.Pair;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;

public final class BigPictureIconManager implements NotificationIconManager, Dumpable {
    public final CoroutineDispatcher bgDispatcher;
    public final Context context;
    public DrawableState displayedState = DrawableState.Initial.INSTANCE;
    public NotificationDrawableConsumer drawableConsumer;
    public final ImageLoader imageLoader;
    public final Job lastLoadingJob;
    public final CoroutineDispatcher mainDispatcher;
    public final int maxHeight;
    public final int maxWidth;
    public final CoroutineScope scope;
    public final BigPictureStatsManager statsManager;
    public final boolean viewShown;

    public abstract class DrawableState {
        public final Icon icon;

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
        }
    }

    public BigPictureIconManager(Context context, ImageLoader imageLoader, BigPictureStatsManager bigPictureStatsManager, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2) {
        this.context = context;
        this.imageLoader = imageLoader;
        this.maxWidth = context.getResources().getDimensionPixelSize(ActivityManager.isLowRamDeviceStatic() ? R.dimen.secondary_rounded_corner_radius_adjustment : R.dimen.secondary_rounded_corner_radius);
        this.maxHeight = context.getResources().getDimensionPixelSize(ActivityManager.isLowRamDeviceStatic() ? R.dimen.secondary_content_alpha_material_light : R.dimen.secondary_content_alpha_material_dark);
    }

    @Override // android.util.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        DrawableState drawableState = this.displayedState;
        boolean z = this.drawableConsumer != null;
        boolean z2 = this.viewShown;
        StringBuilder sb = new StringBuilder("{ state:");
        sb.append(drawableState);
        sb.append(", hasConsumer:");
        sb.append(z);
        sb.append(", viewShown:");
        UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0.m(printWriter, "BigPictureIconManager ", AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, z2, "}"));
    }

    public final Pair loadImageSync(Icon icon) {
        Drawable bitmapDrawable;
        Resources resources;
        ImageLoader imageLoader = this.imageLoader;
        Context context = this.context;
        int i = this.maxWidth;
        int i2 = this.maxHeight;
        ImageLoader.Companion companion = ImageLoader.Companion;
        imageLoader.getClass();
        int type = icon.getType();
        ImageLoader.Companion companion2 = ImageLoader.Companion;
        switch (type) {
            case 1:
                bitmapDrawable = new BitmapDrawable(context.getResources(), icon.getBitmap());
                break;
            case 2:
                companion2.getClass();
                if (icon.getType() == 2) {
                    resources = icon.getResources();
                    if (resources == null) {
                        String resPackage = icon.getResPackage();
                        if (resPackage.length() == 0 || context.getPackageName().equals(resPackage)) {
                            resources = context.getResources();
                        } else if ("android".equals(resPackage)) {
                            resources = Resources.getSystem();
                        } else {
                            PackageManager packageManager = context.getPackageManager();
                            try {
                                resources = packageManager.getResourcesForApplication(packageManager.getApplicationInfo(resPackage, 9216));
                            } catch (PackageManager.NameNotFoundException e) {
                                Log.w("ImageLoader", "Failed to resolve resource package", e);
                            }
                        }
                    }
                    if ((resources != null || (bitmapDrawable = ImageLoader.loadDrawableSync(ImageDecoder.createSource(resources, icon.getResId()), i, i2, 0)) == null) && (bitmapDrawable = icon.loadDrawable(context)) == null) {
                        Log.w("ImageLoader", "Failed to load drawable for " + icon);
                        bitmapDrawable = null;
                        break;
                    }
                }
                resources = null;
                if (resources != null) {
                }
                Log.w("ImageLoader", "Failed to load drawable for " + icon);
                bitmapDrawable = null;
                break;
            case 3:
                bitmapDrawable = ImageLoader.loadDrawableSync(ImageDecoder.createSource(icon.getDataBytes(), icon.getDataOffset(), icon.getDataLength()), i, i2, 0);
                break;
            case 4:
            case 6:
                bitmapDrawable = ImageLoader.loadDrawableSync(ImageDecoder.createSource(context.getContentResolver(), icon.getUri()), i, i2, 0);
                break;
            case 5:
                bitmapDrawable = new AdaptiveIconDrawable(null, new BitmapDrawable(context.getResources(), icon.getBitmap()));
                break;
            default:
                bitmapDrawable = icon.loadDrawable(context);
                if (bitmapDrawable == null) {
                    Log.w("ImageLoader", "Failed to load drawable for " + icon);
                    bitmapDrawable = null;
                    break;
                }
                break;
        }
        if (bitmapDrawable != null) {
            companion2.getClass();
            if (icon.hasTint()) {
                bitmapDrawable.mutate();
                bitmapDrawable.setTintList(icon.getTintList());
                bitmapDrawable.setTintBlendMode(icon.getTintBlendMode());
            }
        } else {
            bitmapDrawable = null;
        }
        if (bitmapDrawable == null) {
            return null;
        }
        DrawableState drawableState = this.displayedState;
        if (drawableState instanceof DrawableState.PlaceHolder) {
            Size size = ((DrawableState.PlaceHolder) drawableState).drawableSize;
            int width = size.getWidth();
            int height = size.getHeight();
            Size size2 = new Size(bitmapDrawable.getIntrinsicWidth(), bitmapDrawable.getIntrinsicHeight());
            int width2 = size2.getWidth();
            int height2 = size2.getHeight();
            if (width != width2 || height != height2) {
                StringBuilder m = RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(width, height, "Mismatch in dimensions, when replacing PlaceHolder ", " X ", " with Drawable ");
                m.append(width2);
                m.append(" X ");
                m.append(height2);
                m.append(".");
                Log.e("BigPicImageLoader", m.toString());
            }
        }
        return new Pair(bitmapDrawable, new DrawableState.FullImage(icon, new Size(bitmapDrawable.getIntrinsicWidth(), bitmapDrawable.getIntrinsicHeight())));
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Runnable updateIcon(com.android.internal.widget.NotificationDrawableConsumer r5, android.graphics.drawable.Icon r6) {
        /*
            Method dump skipped, instructions count: 258
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.row.BigPictureIconManager.updateIcon(com.android.internal.widget.NotificationDrawableConsumer, android.graphics.drawable.Icon):java.lang.Runnable");
    }
}
