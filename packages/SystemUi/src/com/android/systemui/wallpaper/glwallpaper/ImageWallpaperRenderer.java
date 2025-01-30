package com.android.systemui.wallpaper.glwallpaper;

import android.app.ActivityManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.os.PowerManager;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.text.TextUtils;
import android.util.Log;
import android.util.Size;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.wallpaper.CoverWallpaper;
import com.android.systemui.wallpaper.CoverWallpaperController;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.wallpaper.colors.SystemWallpaperColors;
import com.android.systemui.wallpaper.effect.ColorDecorFilterHelper;
import com.android.systemui.wallpaper.effect.HighlightFilterHelper;
import com.android.systemui.wallpaper.log.WallpaperLogger;
import com.android.systemui.wallpaper.log.WallpaperLoggerImpl;
import com.android.systemui.wallpaper.utils.WhichChecker;
import java.nio.Buffer;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ImageWallpaperRenderer {
    public String mColorDecorFilterData;
    public final Context mContext;
    public int mCurDensityDpi;
    public int mDeviceDisplayType;
    public final int mDisplayId;
    public int mHighlightFilterAmount;
    public final ImageSmartCropper mImageSmartCropper;
    public boolean mIsNightModeOn;
    public final boolean mIsVirtualDisplay;
    public final WallpaperLogger mLoggerWrapper;
    public Consumer mOnBitmapUpdated;
    public int mOrientation;
    public final PowerManager mPm;
    public final ImageGLProgram mProgram;
    public int mSmartCropYOffset;
    public final SystemWallpaperColors mSystemWallpaperColors;
    public final WallpaperTexture mTexture;
    public final ImageGLWallpaper mWallpaper;
    public final WallpaperManager mWallpaperManager;
    public final Rect mSurfaceSize = new Rect();
    public boolean mIsSmartCropAllowed = true;
    public final float mYOffset = 0.5f;
    public int mLidState = -1;
    public boolean mIsFolded = false;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class WallpaperTexture {
        public Bitmap mBitmap;
        public final CoverWallpaper mCoverWallpaper;
        public int mCurrentUserId;
        public final Rect mDimensions;
        public int mDisplayId;
        public boolean mIsVirtualDisplay;
        public final AtomicInteger mRefCount;
        public Bitmap mSubBitmap;
        public final WallpaperManager mWallpaperManager;
        public boolean mWcgContent;

        public /* synthetic */ WallpaperTexture(WallpaperManager wallpaperManager, CoverWallpaper coverWallpaper, int i) {
            this(wallpaperManager, coverWallpaper);
        }

        public static String getHash(Bitmap bitmap) {
            return bitmap != null ? Integer.toHexString(bitmap.hashCode()) : "null";
        }

        public final Bitmap loadBitmap(int i) {
            int i2;
            int i3;
            Bitmap bitmapAsUser;
            WallpaperManager wallpaperManager = this.mWallpaperManager;
            if (wallpaperManager == null) {
                return null;
            }
            int i4 = this.mDisplayId;
            if (i4 == 2) {
                i2 = 9;
            } else if (!LsRune.WALLPAPER_SUB_DISPLAY_MODE) {
                i2 = (LsRune.COVER_VIRTUAL_DISPLAY && this.mIsVirtualDisplay) ? 33 : 1;
            } else if (LsRune.WALLPAPER_SUB_WATCHFACE) {
                if (i4 != 1) {
                    i3 = 5;
                    TooltipPopup$$ExternalSyntheticOutline0.m13m(AbstractC0000x2c234b15.m1m("loadBitmap: lidState = ", i, " display id = "), this.mDisplayId, "ImageWallpaperRenderer");
                    i2 = i3;
                }
                i3 = 17;
                TooltipPopup$$ExternalSyntheticOutline0.m13m(AbstractC0000x2c234b15.m1m("loadBitmap: lidState = ", i, " display id = "), this.mDisplayId, "ImageWallpaperRenderer");
                i2 = i3;
            } else {
                if (i != 0) {
                    i3 = 1;
                    TooltipPopup$$ExternalSyntheticOutline0.m13m(AbstractC0000x2c234b15.m1m("loadBitmap: lidState = ", i, " display id = "), this.mDisplayId, "ImageWallpaperRenderer");
                    i2 = i3;
                }
                i3 = 17;
                TooltipPopup$$ExternalSyntheticOutline0.m13m(AbstractC0000x2c234b15.m1m("loadBitmap: lidState = ", i, " display id = "), this.mDisplayId, "ImageWallpaperRenderer");
                i2 = i3;
            }
            if (LsRune.WALLPAPER_PLAY_GIF) {
                if (((i2 & 16) == 16) || ((i2 & 32) == 32)) {
                    CoverWallpaperController coverWallpaperController = (CoverWallpaperController) this.mCoverWallpaper;
                    if (coverWallpaperController.isCoverWallpaperRequired()) {
                        Log.i("ImageWallpaperRenderer", "loadCachedBitmapByWhich: from cover wallpaper controller");
                        return coverWallpaperController.getWallpaperBitmap(true);
                    }
                    if (wallpaperManager.semGetWallpaperType(i2) == 3) {
                        Log.i("ImageWallpaperRenderer", "loadCachedBitmapByWhich: Just return null in case of custom pack.");
                        return null;
                    }
                }
            }
            if (!LsRune.WALLPAPER_CACHED_WALLPAPER) {
                bitmapAsUser = wallpaperManager.getBitmapAsUser(this.mCurrentUserId, false, i2, false);
            } else if (WallpaperUtils.isCachedWallpaperAvailable(i2)) {
                Log.i("ImageWallpaperRenderer", "loadCachedBitmapByWhich: get cached bitmap " + i2);
                bitmapAsUser = WallpaperUtils.getCachedWallpaper(i2);
            } else {
                SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("loadCachedBitmapByWhich: from wallpaper manager ", i2, "ImageWallpaperRenderer");
                bitmapAsUser = wallpaperManager.getBitmapAsUser(this.mCurrentUserId, false, i2, false);
                WallpaperUtils.clearCachedWallpaper(i2);
                WallpaperUtils.setCachedWallpaper(bitmapAsUser, i2);
            }
            return bitmapAsUser;
        }

        public final String toString() {
            return "{" + getHash(this.mBitmap) + ", " + getHash(this.mSubBitmap) + ", " + this.mRefCount.get() + "}";
        }

        public final void use(Consumer consumer) {
            Bitmap bitmap;
            this.mRefCount.incrementAndGet();
            synchronized (this.mRefCount) {
                int lidState = this.mWallpaperManager.getLidState();
                bitmap = lidState == 0 ? this.mSubBitmap : this.mBitmap;
                if (bitmap == null) {
                    bitmap = loadBitmap(lidState);
                    if (lidState == 0) {
                        this.mSubBitmap = bitmap;
                    } else {
                        this.mBitmap = bitmap;
                    }
                    if (LsRune.WALLPAPER_CACHED_WALLPAPER) {
                        this.mWcgContent = this.mWallpaperManager.wallpaperSupportsWcg(bitmap);
                    } else {
                        this.mWcgContent = this.mWallpaperManager.wallpaperSupportsWcg(1);
                    }
                    this.mWallpaperManager.forgetLoadedWallpaper();
                    if (bitmap != null) {
                        Log.i("ImageWallpaperRenderer", "Load bitmap w: " + bitmap.getWidth() + " , h : " + bitmap.getHeight());
                        this.mDimensions.set(0, 0, bitmap.getWidth(), bitmap.getHeight());
                    } else {
                        Log.w("ImageWallpaperRenderer", "Can't get bitmap");
                    }
                }
            }
            if (consumer != null) {
                consumer.accept(bitmap);
            }
            synchronized (this.mRefCount) {
                int decrementAndGet = this.mRefCount.decrementAndGet();
                if (decrementAndGet == 0 && bitmap != null) {
                    if (!LsRune.WALLPAPER_CACHED_WALLPAPER) {
                        Log.e("ImageWallpaperRenderer", "WallpaperTexture: release 0x" + getHash(this.mBitmap) + " , " + getHash(this.mSubBitmap) + ", refCount=" + decrementAndGet);
                        Bitmap bitmap2 = this.mBitmap;
                        if (bitmap2 != null) {
                            bitmap2.recycle();
                        }
                        Bitmap bitmap3 = this.mSubBitmap;
                        if (bitmap3 != null) {
                            bitmap3.recycle();
                        }
                    }
                    this.mBitmap = null;
                    this.mSubBitmap = null;
                }
            }
        }

        private WallpaperTexture(WallpaperManager wallpaperManager, CoverWallpaper coverWallpaper) {
            this.mWallpaperManager = wallpaperManager;
            this.mRefCount = new AtomicInteger();
            this.mDimensions = new Rect();
            this.mCoverWallpaper = coverWallpaper;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ImageWallpaperRenderer(Context context, int i, WallpaperLogger wallpaperLogger, SystemWallpaperColors systemWallpaperColors, CoverWallpaper coverWallpaper) {
        this.mOrientation = -1;
        Object[] objArr = 0;
        this.mCurDensityDpi = 0;
        this.mIsNightModeOn = false;
        this.mDeviceDisplayType = -1;
        this.mHighlightFilterAmount = -1;
        WallpaperManager wallpaperManager = (WallpaperManager) context.getSystemService(WallpaperManager.class);
        if (wallpaperManager == null) {
            Log.w("ImageWallpaperRenderer", "WallpaperManager not available");
        }
        this.mSystemWallpaperColors = systemWallpaperColors;
        WallpaperTexture wallpaperTexture = new WallpaperTexture(wallpaperManager, coverWallpaper, objArr == true ? 1 : 0);
        this.mTexture = wallpaperTexture;
        ImageGLProgram imageGLProgram = new ImageGLProgram(context);
        this.mProgram = imageGLProgram;
        this.mWallpaper = new ImageGLWallpaper(imageGLProgram);
        this.mContext = context;
        this.mLoggerWrapper = wallpaperLogger;
        wallpaperTexture.mCurrentUserId = ActivityManager.getCurrentUser();
        this.mDisplayId = i;
        wallpaperTexture.mDisplayId = i;
        if (LsRune.COVER_VIRTUAL_DISPLAY) {
            boolean isVirtualWallpaperDisplay = WallpaperManager.isVirtualWallpaperDisplay(context, i);
            this.mIsVirtualDisplay = isVirtualWallpaperDisplay;
            wallpaperTexture.mIsVirtualDisplay = isVirtualWallpaperDisplay;
        }
        boolean z = LsRune.WALLPAPER_CACHED_WALLPAPER;
        if (z) {
            if ((i == 2) == false && z) {
                boolean isCachedWallpaperAvailable = WallpaperUtils.isCachedWallpaperAvailable(1);
                WallpaperManager wallpaperManager2 = wallpaperTexture.mWallpaperManager;
                if (isCachedWallpaperAvailable) {
                    Log.i("ImageWallpaperRenderer", " Already exist in cache :  main ");
                } else {
                    Bitmap bitmapAsUser = wallpaperManager2.getBitmapAsUser(wallpaperTexture.mCurrentUserId, false, 5, false);
                    if (bitmapAsUser != null) {
                        Log.i("ImageWallpaperRenderer", "Load main bitmap save in cache " + bitmapAsUser.getWidth() + "  , " + bitmapAsUser.getHeight());
                        WallpaperUtils.clearCachedWallpaper(1);
                        WallpaperUtils.setCachedWallpaper(bitmapAsUser, 1);
                    }
                }
                if (LsRune.WALLPAPER_SUB_DISPLAY_MODE) {
                    if (WallpaperUtils.isCachedWallpaperAvailable(17)) {
                        Log.i("ImageWallpaperRenderer", " Already exist in cache :  sub");
                    } else {
                        Bitmap bitmapAsUser2 = wallpaperManager2.getBitmapAsUser(wallpaperTexture.mCurrentUserId, false, 17, false);
                        if (bitmapAsUser2 != null) {
                            Log.i("ImageWallpaperRenderer", "Load sub bitmap save in cache " + bitmapAsUser2.getWidth() + "  , " + bitmapAsUser2.getHeight());
                            WallpaperUtils.clearCachedWallpaper(17);
                            WallpaperUtils.setCachedWallpaper(bitmapAsUser2, 17);
                        }
                    }
                }
            }
        }
        this.mImageSmartCropper = new ImageSmartCropper(context, i);
        this.mWallpaperManager = wallpaperManager;
        this.mCurDensityDpi = context.getResources().getConfiguration().densityDpi;
        this.mOrientation = context.getResources().getConfiguration().orientation;
        this.mSmartCropYOffset = -1000000;
        WallpaperUtils.clearCachedSmartCroppedRect(getCurrentWhich());
        if (LsRune.WALLPAPER_SUB_DISPLAY_MODE) {
            int lidState = wallpaperManager.getLidState();
            this.mPm = (PowerManager) context.getSystemService("power");
            ((WallpaperLoggerImpl) wallpaperLogger).log("ImageWallpaperRenderer", " initial lid state : " + showLidState(lidState) + " , " + context.getResources().getConfiguration().semDisplayDeviceType);
            int i2 = context.getResources().getConfiguration().semDisplayDeviceType;
            this.mDeviceDisplayType = i2;
            if (i2 == 5 && lidState != 0) {
                Log.i("ImageWallpaperRenderer", " flex mode ".concat(showLidState(0)));
                lidState = 0;
            }
            setLidState(lidState);
        }
        this.mIsNightModeOn = (context.getResources().getConfiguration().uiMode & 32) != 0;
        int convertDisplayIdToMode = WhichChecker.convertDisplayIdToMode(i, context);
        if (convertDisplayIdToMode >= 0) {
            String filterData = ColorDecorFilterHelper.getFilterData(convertDisplayIdToMode | 1, context, context.getUserId());
            if (!TextUtils.isEmpty(filterData)) {
                this.mColorDecorFilterData = filterData;
            }
        }
        if (!TextUtils.isEmpty(this.mColorDecorFilterData)) {
            return;
        }
        this.mHighlightFilterAmount = 60;
    }

    public static String showLidState(int i) {
        return LsRune.WALLPAPER_SUB_DISPLAY_MODE ? i == 1 ? "LID_OPEN" : i == 0 ? "LID_CLOSED" : "LID_UNKNOWN" : "LID_UNKNOWN";
    }

    public final int getCurrentWhich() {
        if (this.mDisplayId == 2) {
            return 9;
        }
        if (LsRune.WALLPAPER_SUB_DISPLAY_MODE && this.mWallpaperManager.getLidState() == 0) {
            return 17;
        }
        return (LsRune.WALLPAPER_VIRTUAL_DISPLAY && this.mIsVirtualDisplay) ? 33 : 1;
    }

    public final void onSurfaceCreated() {
        StringBuilder sb = new StringBuilder(" onSurfaceCreated ");
        sb.append(this.mDisplayId);
        sb.append(", colorDecor = ");
        sb.append(!TextUtils.isEmpty(this.mColorDecorFilterData));
        sb.append(", highlightAmount = ");
        TooltipPopup$$ExternalSyntheticOutline0.m13m(sb, this.mHighlightFilterAmount, "ImageWallpaperRenderer");
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        ImageGLProgram imageGLProgram = this.mProgram;
        String shaderResource = imageGLProgram.getShaderResource(R.raw.image_wallpaper_vertex_shader);
        String shaderResource2 = imageGLProgram.getShaderResource(R.raw.image_wallpaper_fragment_shader);
        int shaderHandle = ImageGLProgram.getShaderHandle(35633, shaderResource);
        int shaderHandle2 = ImageGLProgram.getShaderHandle(35632, shaderResource2);
        int glCreateProgram = GLES20.glCreateProgram();
        if (glCreateProgram == 0) {
            Log.d("ImageGLProgram", "Can not create OpenGL ES program");
            glCreateProgram = 0;
        } else {
            GLES20.glAttachShader(glCreateProgram, shaderHandle);
            GLES20.glAttachShader(glCreateProgram, shaderHandle2);
            GLES20.glLinkProgram(glCreateProgram);
        }
        imageGLProgram.mProgramHandle = glCreateProgram;
        GLES20.glUseProgram(glCreateProgram);
        this.mTexture.use(new Consumer() { // from class: com.android.systemui.wallpaper.glwallpaper.ImageWallpaperRenderer$$ExternalSyntheticLambda0
            /* JADX WARN: Removed duplicated region for block: B:18:0x0109  */
            @Override // java.util.function.Consumer
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void accept(Object obj) {
                Bitmap bitmap;
                boolean z;
                Bitmap createFilteredBitmap;
                ImageWallpaperRenderer imageWallpaperRenderer = ImageWallpaperRenderer.this;
                Bitmap bitmap2 = (Bitmap) obj;
                if (bitmap2 == null) {
                    imageWallpaperRenderer.getClass();
                    Log.w("ImageWallpaperRenderer", "reload texture failed!");
                } else {
                    Consumer consumer = imageWallpaperRenderer.mOnBitmapUpdated;
                    if (consumer != null) {
                        consumer.accept(bitmap2);
                    }
                }
                if (bitmap2 != null) {
                    if (!TextUtils.isEmpty(imageWallpaperRenderer.mColorDecorFilterData)) {
                        createFilteredBitmap = ColorDecorFilterHelper.createFilteredBitmap(bitmap2, imageWallpaperRenderer.mColorDecorFilterData);
                    } else {
                        int i = imageWallpaperRenderer.mHighlightFilterAmount;
                        if (i >= 0) {
                            createFilteredBitmap = HighlightFilterHelper.createFilteredBitmap(bitmap2, i);
                        }
                    }
                    bitmap = createFilteredBitmap;
                    z = true;
                    ImageGLWallpaper imageGLWallpaper = imageWallpaperRenderer.mWallpaper;
                    ImageGLProgram imageGLProgram2 = imageGLWallpaper.mProgram;
                    imageGLWallpaper.mAttrPosition = GLES20.glGetAttribLocation(imageGLProgram2.mProgramHandle, "aPosition");
                    imageGLWallpaper.mVertexBuffer.position(0);
                    GLES20.glVertexAttribPointer(imageGLWallpaper.mAttrPosition, 2, 5126, false, 0, (Buffer) imageGLWallpaper.mVertexBuffer);
                    GLES20.glEnableVertexAttribArray(imageGLWallpaper.mAttrPosition);
                    imageGLWallpaper.mAttrTextureCoordinates = GLES20.glGetAttribLocation(imageGLProgram2.mProgramHandle, "aTextureCoordinates");
                    imageGLWallpaper.mTextureBuffer.position(0);
                    GLES20.glVertexAttribPointer(imageGLWallpaper.mAttrTextureCoordinates, 2, 5126, false, 0, (Buffer) imageGLWallpaper.mTextureBuffer);
                    GLES20.glEnableVertexAttribArray(imageGLWallpaper.mAttrTextureCoordinates);
                    GLES20.glEnable(3042);
                    GLES20.glBlendFunc(770, 771);
                    imageGLWallpaper.mUniTexture = GLES20.glGetUniformLocation(imageGLProgram2.mProgramHandle, "uTexture");
                    imageGLWallpaper.mUniNightFilter = GLES20.glGetUniformLocation(imageGLProgram2.mProgramHandle, "uNightFilter");
                    imageGLWallpaper.mUniFilterColor = GLES20.glGetUniformLocation(imageGLProgram2.mProgramHandle, "uFilterColor");
                    int[] iArr = new int[1];
                    if (bitmap != null || bitmap.isRecycled()) {
                        Log.w("ImageGLWallpaper", "setupTexture: invalid bitmap");
                    } else {
                        GLES20.glGenTextures(1, iArr, 0);
                        int i2 = iArr[0];
                        if (i2 == 0) {
                            Log.w("ImageGLWallpaper", "setupTexture: glGenTextures() failed");
                        } else {
                            try {
                                GLES20.glBindTexture(3553, i2);
                                GLUtils.texImage2D(3553, 0, bitmap, 0);
                                GLES20.glTexParameteri(3553, 10241, 9729);
                                GLES20.glTexParameteri(3553, 10240, 9729);
                                imageGLWallpaper.mTextureId = iArr[0];
                            } catch (IllegalArgumentException e) {
                                Log.w("ImageGLWallpaper", "Failed uploading texture: " + e.getLocalizedMessage());
                            }
                        }
                    }
                    if (bitmap != null) {
                        boolean z2 = (imageWallpaperRenderer.mDisplayId == 2) || imageWallpaperRenderer.mIsVirtualDisplay;
                        ImageSmartCropper imageSmartCropper = imageWallpaperRenderer.mImageSmartCropper;
                        if (imageSmartCropper != null && !z2) {
                            imageSmartCropper.updateSmartCropRectIfNeeded(bitmap, imageWallpaperRenderer.getCurrentWhich());
                            Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
                            Rect rect2 = imageSmartCropper.mCropResult;
                            WallpaperManager wallpaperManager = imageWallpaperRenderer.mWallpaperManager;
                            if (rect2 == null) {
                                wallpaperManager.semSetSmartCropRect(1, rect, rect);
                            } else {
                                wallpaperManager.semSetSmartCropRect(1, rect, rect2);
                            }
                        }
                    }
                    if (z || bitmap == null || bitmap.isRecycled()) {
                        return;
                    }
                    bitmap.recycle();
                    return;
                }
                bitmap = bitmap2;
                z = false;
                ImageGLWallpaper imageGLWallpaper2 = imageWallpaperRenderer.mWallpaper;
                ImageGLProgram imageGLProgram22 = imageGLWallpaper2.mProgram;
                imageGLWallpaper2.mAttrPosition = GLES20.glGetAttribLocation(imageGLProgram22.mProgramHandle, "aPosition");
                imageGLWallpaper2.mVertexBuffer.position(0);
                GLES20.glVertexAttribPointer(imageGLWallpaper2.mAttrPosition, 2, 5126, false, 0, (Buffer) imageGLWallpaper2.mVertexBuffer);
                GLES20.glEnableVertexAttribArray(imageGLWallpaper2.mAttrPosition);
                imageGLWallpaper2.mAttrTextureCoordinates = GLES20.glGetAttribLocation(imageGLProgram22.mProgramHandle, "aTextureCoordinates");
                imageGLWallpaper2.mTextureBuffer.position(0);
                GLES20.glVertexAttribPointer(imageGLWallpaper2.mAttrTextureCoordinates, 2, 5126, false, 0, (Buffer) imageGLWallpaper2.mTextureBuffer);
                GLES20.glEnableVertexAttribArray(imageGLWallpaper2.mAttrTextureCoordinates);
                GLES20.glEnable(3042);
                GLES20.glBlendFunc(770, 771);
                imageGLWallpaper2.mUniTexture = GLES20.glGetUniformLocation(imageGLProgram22.mProgramHandle, "uTexture");
                imageGLWallpaper2.mUniNightFilter = GLES20.glGetUniformLocation(imageGLProgram22.mProgramHandle, "uNightFilter");
                imageGLWallpaper2.mUniFilterColor = GLES20.glGetUniformLocation(imageGLProgram22.mProgramHandle, "uFilterColor");
                int[] iArr2 = new int[1];
                if (bitmap != null) {
                }
                Log.w("ImageGLWallpaper", "setupTexture: invalid bitmap");
                if (bitmap != null) {
                }
                if (z) {
                }
            }
        });
    }

    public final Size reportSurfaceSize() {
        WallpaperTexture wallpaperTexture = this.mTexture;
        wallpaperTexture.use(null);
        Rect rect = wallpaperTexture.mDimensions;
        Rect rect2 = this.mSurfaceSize;
        rect2.set(rect);
        return new Size(rect2.width(), rect2.height());
    }

    public final void setLidState(int i) {
        boolean z = LsRune.WALLPAPER_SUB_DISPLAY_MODE;
        if (z) {
            this.mLidState = i;
            ImageSmartCropper imageSmartCropper = this.mImageSmartCropper;
            if (imageSmartCropper == null || !z) {
                return;
            }
            imageSmartCropper.mLidState = i;
        }
    }
}
