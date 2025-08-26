package com.android.systemui.wallpaper.provider;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.util.Size;
import com.android.systemui.Dependency;
import com.android.systemui.blur.domain.interactor.WallpaperScreenShotProvider$$ExternalSyntheticOutline0;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.CoverWallpaperController;
import com.android.systemui.wallpaper.engines.image.ImageSource;
import com.android.systemui.wallpaper.utils.IntelligentCropHelper;
import com.samsung.android.wallpaper.live.sdk.provider.LiveWallpaperProviderCallDispatcher;
import com.samsung.android.wallpaper.live.sdk.provider.ProviderCallResult;
import com.samsung.android.wallpaper.live.sdk.provider.call.GetBackgroundRegion$Params;
import com.samsung.android.wallpaper.live.sdk.provider.call.GetBackgroundRegion$Result;
import com.samsung.android.wallpaper.live.sdk.provider.call.GetScreenshot$Params;
import com.samsung.android.wallpaper.live.sdk.provider.call.GetScreenshot$Result;
import com.samsung.android.wallpaper.live.sdk.provider.call.GetThumbnail;
import com.samsung.android.wallpaper.live.sdk.utils.BitmapUtils;
import com.samsung.android.wallpaper.live.sdk.utils.DisplayUtils;
import com.samsung.android.wallpaper.live.sdk.utils.GraphicsUtils;
import com.samsung.android.wallpaper.stretch.utils.BgRegion;
import com.samsung.android.wallpaper.stretch.utils.SemRegion;
import com.samsung.android.wallpaper.utils.SemWallpaperProperties;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class ProviderCallDispatcher extends LiveWallpaperProviderCallDispatcher {
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v15, types: [com.samsung.android.wallpaper.live.sdk.provider.call.GetBackgroundRegion$Result] */
    /* JADX WARN: Type inference failed for: r11v32, types: [android.graphics.Region] */
    /* JADX WARN: Type inference failed for: r11v33 */
    /* JADX WARN: Type inference failed for: r11v34 */
    /* JADX WARN: Type inference failed for: r4v10, types: [android.graphics.Region, com.samsung.android.wallpaper.stretch.utils.SemRegion, java.lang.Object] */
    @Override // com.samsung.android.wallpaper.live.sdk.provider.LiveWallpaperProviderCallDispatcher
    public final GetBackgroundRegion$Result onGetBackgroundRegion(Context context, GetBackgroundRegion$Params getBackgroundRegion$Params) throws NoSuchMethodException, IOException, SecurityException {
        GetBackgroundRegion$Result getBackgroundRegion$Result;
        final ?? r11;
        BgRegionGenerator bgRegionGenerator = new BgRegionGenerator(context);
        WallpaperManager wallpaperManager = bgRegionGenerator.mWallMgr;
        int i = getBackgroundRegion$Params.sourceWhich;
        int iSemGetWallpaperType = wallpaperManager.semGetWallpaperType(i);
        if (iSemGetWallpaperType == 0 || iSemGetWallpaperType == -1) {
            Log.i("ImageWallpaper[BgRegionGenerator]", "getBgRegionForImageType : which=" + getBackgroundRegion$Params.which + ", srcWhich=" + i);
            Context context2 = bgRegionGenerator.mContext;
            int i2 = getBackgroundRegion$Params.userId;
            String stringProperty = new SemWallpaperProperties(context2, i, i2).getStringProperty("bgRegion");
            if (TextUtils.isEmpty(stringProperty)) {
                stringProperty = "bg_region";
            }
            ParcelFileDescriptor wallpaperAssetFile = bgRegionGenerator.mWallMgr.getWallpaperAssetFile(i, i2, stringProperty);
            if (wallpaperAssetFile == null) {
                Log.i("ImageWallpaper[BgRegionGenerator]", "getBgRegionForImageType: bg region file not exist");
                getBackgroundRegion$Result = null;
                r11 = getBackgroundRegion$Result;
            } else {
                try {
                    FileInputStream fileInputStream = new FileInputStream(wallpaperAssetFile.getFileDescriptor());
                    try {
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        byte[] bArr = new byte[1024];
                        while (true) {
                            int i3 = fileInputStream.read(bArr);
                            if (i3 <= 0) {
                                break;
                            }
                            byteArrayOutputStream.write(bArr, 0, i3);
                        }
                        BgRegion bgRegion = new BgRegion(byteArrayOutputStream.toByteArray());
                        byteArrayOutputStream.close();
                        fileInputStream.close();
                        final Point displaySize = DisplayUtils.getDisplaySize(bgRegionGenerator.mContext, i, getBackgroundRegion$Params.rotation);
                        if (displaySize == null || displaySize.x * displaySize.y <= 0) {
                            getBackgroundRegion$Result = null;
                            Log.e("ImageWallpaper[BgRegionGenerator]", "getBgRegionForImageType: display is not ready. size=" + displaySize);
                        } else {
                            Context context3 = bgRegionGenerator.mContext;
                            CoverWallpaperController coverWallpaperController = CoverWallpaperController.sInstance;
                            int i4 = getBackgroundRegion$Params.sourceWhich;
                            final ImageSource imageSource = new ImageSource(context3, coverWallpaperController, null, i4, getBackgroundRegion$Params.userId, DisplayUtils.getDisplayIdByWhich(i4, context3));
                            final Size[] sizeArr = {null};
                            final Rect[] rectArr = {null};
                            imageSource.useBitmap(new Consumer() { // from class: com.android.systemui.wallpaper.provider.BgRegionGenerator$$ExternalSyntheticLambda0
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    Rect nearestCropHint;
                                    ImageSource imageSource2 = imageSource;
                                    Point point = displaySize;
                                    Rect[] rectArr2 = rectArr;
                                    Size[] sizeArr2 = sizeArr;
                                    ImageSource.WallpaperImage wallpaperImage = (ImageSource.WallpaperImage) obj;
                                    if (imageSource2.isFixedOrientation(false, (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class))) {
                                        Log.i("ImageWallpaper[BgRegionGenerator]", "getBgRegionForImageType: not support fixed orientation wallpaper");
                                        return;
                                    }
                                    Bitmap bitmap = wallpaperImage.mBitmap;
                                    if (bitmap == null) {
                                        Log.e("ImageWallpaper[BgRegionGenerator]", "getBgRegionForImageType: failed to get wallpaper bitmap");
                                        return;
                                    }
                                    ArrayList arrayList = wallpaperImage.mCropRects;
                                    Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
                                    if (arrayList != null && !arrayList.isEmpty() && (nearestCropHint = IntelligentCropHelper.getNearestCropHint(point, arrayList)) != null) {
                                        rect = nearestCropHint;
                                    }
                                    int i5 = point.x;
                                    int i6 = point.y;
                                    int i7 = rect.left;
                                    int i8 = rect.top;
                                    Rect centerCropRect = GraphicsUtils.getCenterCropRect(rect.right - i7, rect.bottom - i8, i5, i6);
                                    centerCropRect.offset(i7, i8);
                                    rectArr2[0] = centerCropRect;
                                    sizeArr2[0] = new Size(bitmap.getWidth(), bitmap.getHeight());
                                }
                            });
                            if (rectArr[0] == null || sizeArr[0] == null) {
                                getBackgroundRegion$Result = null;
                                Log.e("ImageWallpaper[BgRegionGenerator]", "getBgRegionForImageType: incorrect cropRect or singleImageSize");
                            } else {
                                float fWidth = r9.width() / sizeArr[0].getWidth();
                                float fHeight = r9.height() / sizeArr[0].getHeight();
                                Log.i("ImageWallpaper[BgRegionGenerator]", "getBgRegionForImageType: scr=" + displaySize + ", img=" + sizeArr[0] + ", crop=" + rectArr[0] + ", bgBound=" + bgRegion.getBounds() + ", sx=" + fWidth + ", sy=" + fHeight);
                                Rect rect = new Rect();
                                Rect rect2 = rectArr[0];
                                getBackgroundRegion$Result = null;
                                RectF rectF = new RectF(((float) rect2.left) * fWidth, ((float) rect2.top) * fHeight, ((float) rect2.right) * fWidth, ((float) rect2.bottom) * fHeight);
                                float f = ((float) displaySize.x) / ((float) displaySize.y);
                                if (rectArr[0].width() / rectArr[0].height() > f) {
                                    float fWidth2 = (rectF.width() - (rectF.height() * f)) / 2.0f;
                                    if (fWidth2 > 0.0f) {
                                        rectF.left += fWidth2;
                                        rectF.right -= fWidth2;
                                    }
                                } else {
                                    float fHeight2 = (rectF.height() - (rectF.width() / f)) / 2.0f;
                                    if (fHeight2 > 0.0f) {
                                        rectF.top += fHeight2;
                                        rectF.bottom -= fHeight2;
                                    }
                                }
                                rectF.round(rect);
                                ?? semRegion = new SemRegion(bgRegion);
                                semRegion.crop(rect);
                                semRegion.translate(-rect.left, -rect.top);
                                float fWidth3 = displaySize.x / rectF.width();
                                float fHeight3 = displaySize.y / rectF.height();
                                if (!semRegion.isMutable && semRegion.equals(semRegion)) {
                                    throw new IllegalStateException("Cannot scale immutable region.");
                                }
                                Path boundaryPath = semRegion.getBoundaryPath();
                                Matrix matrix = new Matrix();
                                matrix.setScale(fWidth3, fHeight3);
                                boundaryPath.transform(matrix);
                                RectF rectF2 = new RectF();
                                boundaryPath.computeBounds(rectF2, true);
                                semRegion.set((int) rectF2.left, (int) rectF2.top, (int) rectF2.right, (int) rectF2.bottom);
                                semRegion.setPath(boundaryPath, semRegion);
                                Rect rect3 = new Rect(0, 0, displaySize.x, displaySize.y);
                                semRegion.crop(rect3);
                                Region region = new Region((Region) semRegion);
                                int i5 = rect3.left;
                                int i6 = rect3.right - 1;
                                int[] iArr = {i5, i6, i5, i6};
                                int i7 = rect3.top;
                                int i8 = rect3.bottom - 1;
                                int[] iArr2 = {i7, i7, i8, i8};
                                for (int i9 = 0; i9 < 4; i9++) {
                                    int i10 = iArr[i9];
                                    int i11 = iArr2[i9];
                                    region.op(i10, i11, i10 + 1, i11 + 1, Region.Op.UNION);
                                }
                                Log.i("ImageWallpaper[BgRegionGenerator]", "getBgRegionForImageType: display=" + displaySize + ", srcWhich=" + i + ", cropRect=" + rectArr[0] + ", region=" + region.getBounds());
                                r11 = region;
                            }
                        }
                        r11 = getBackgroundRegion$Result;
                    } finally {
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            getBackgroundRegion$Result = null;
            r11 = getBackgroundRegion$Result;
        }
        return (r11 == 0 || r11.isEmpty()) ? getBackgroundRegion$Result : new ProviderCallResult(r11) { // from class: com.samsung.android.wallpaper.live.sdk.provider.call.GetBackgroundRegion$Result
            public final Region mBackgroundRegion;

            {
                this.mBackgroundRegion = r11;
            }

            @Override // com.samsung.android.wallpaper.live.sdk.provider.ProviderCallResult
            public final Bundle toBundle() {
                Bundle bundle = new Bundle();
                Region region2 = this.mBackgroundRegion;
                if (region2 != null) {
                    bundle.putParcelable("background_region", region2);
                }
                if (bundle.isEmpty()) {
                    return null;
                }
                return bundle;
            }
        };
    }

    @Override // com.samsung.android.wallpaper.live.sdk.provider.LiveWallpaperProviderCallDispatcher
    public final GetScreenshot$Result onGetScreenshot(Context context, GetScreenshot$Params getScreenshot$Params) {
        Log.i("ImageWallpaper[ProviderCallDispatcher]", "onGetScreenshot");
        return super.onGetScreenshot(context, getScreenshot$Params);
    }

    @Override // com.samsung.android.wallpaper.live.sdk.provider.LiveWallpaperProviderCallDispatcher
    public final GetThumbnail.Result onGetThumbnail(Context context, GetThumbnail.Params params) {
        try {
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            final Bitmap bitmapGenerateThumbnail = new ThumbnailGenerator(context).generateThumbnail(params);
            ParcelFileDescriptor parcelFileDescriptorEncodeBitmapToPipe = bitmapGenerateThumbnail != null ? BitmapUtils.encodeBitmapToPipe(bitmapGenerateThumbnail, Bitmap.CompressFormat.JPEG, new Runnable() { // from class: com.android.systemui.wallpaper.provider.ProviderCallDispatcher$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    bitmapGenerateThumbnail.recycle();
                }
            }) : null;
            Log.i("ImageWallpaper[ProviderCallDispatcher]", "onGetThumbnail: wpId=" + params.wallpaperId + ", which=" + params.which + ", srcWhich=" + params.sourceWhich + ", rotation=" + params.rotation + ", size=" + BitmapUtils.getBitmapSizeString(bitmapGenerateThumbnail) + ", elapsed=" + (SystemClock.elapsedRealtime() - jElapsedRealtime));
            if (parcelFileDescriptorEncodeBitmapToPipe != null) {
                return new GetThumbnail.Result(parcelFileDescriptorEncodeBitmapToPipe);
            }
            return null;
        } catch (Exception e) {
            WallpaperScreenShotProvider$$ExternalSyntheticOutline0.m("onGetThumbnail: e=", e, "ImageWallpaper[ProviderCallDispatcher]", e);
            return null;
        }
    }
}
