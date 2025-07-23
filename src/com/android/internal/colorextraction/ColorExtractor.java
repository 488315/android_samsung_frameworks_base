package com.android.internal.colorextraction;

import android.app.WallpaperColors;
import android.app.WallpaperManager;
import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.AsyncTask;
import android.util.SparseArray;
import com.android.internal.colorextraction.types.ExtractionType;
import com.android.internal.colorextraction.types.Tonal;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/* loaded from: classes5.dex */
public class ColorExtractor implements WallpaperManager.OnColorsChangedListener {
    private static final boolean DEBUG = false;
    private static final String TAG = "ColorExtractor";
    public static final int TYPE_DARK = 1;
    public static final int TYPE_EXTRA_DARK = 2;
    public static final int TYPE_NORMAL = 0;
    private static final int[] sGradientTypes = {0, 1, 2};
    private final Context mContext;
    private final ExtractionType mExtractionType;
    protected final SparseArray<GradientColors[]> mGradientColors;
    protected WallpaperColors mLockColors;
    private final ArrayList<WeakReference<OnColorsChangedListener>> mOnColorsChangedListeners;
    protected WallpaperColors mSystemColors;

    public interface OnColorsChangedListener {
        void onColorsChanged(ColorExtractor colorExtractor, int i);
    }

    public ColorExtractor(Context context) {
        this(context, new Tonal(context), true, (WallpaperManager) context.getSystemService(WallpaperManager.class));
    }

    public ColorExtractor(Context context, ExtractionType extractionType, boolean z, WallpaperManager wallpaperManager) {
        this.mContext = context;
        this.mExtractionType = extractionType;
        this.mGradientColors = new SparseArray<>();
        int[] iArr = {2, 1};
        for (int i = 0; i < 2; i++) {
            int i2 = iArr[i];
            int[] iArr2 = sGradientTypes;
            GradientColors[] gradientColorsArr = new GradientColors[iArr2.length];
            this.mGradientColors.append(i2, gradientColorsArr);
            for (int i3 : iArr2) {
                gradientColorsArr[i3] = new GradientColors();
            }
        }
        this.mOnColorsChangedListeners = new ArrayList<>();
        if (wallpaperManager.isWallpaperSupported()) {
            wallpaperManager.addOnColorsChangedListener(this, null);
            initExtractColors(wallpaperManager, z);
        }
    }

    private void initExtractColors(WallpaperManager wallpaperManager, boolean z) {
        if (z) {
            this.mSystemColors = wallpaperManager.getWallpaperColors(1);
            this.mLockColors = wallpaperManager.getWallpaperColors(2);
            extractWallpaperColors();
            return;
        }
        new LoadWallpaperColors().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, wallpaperManager);
    }

    private class LoadWallpaperColors extends AsyncTask<WallpaperManager, Void, Void> {
        private WallpaperColors mLockColors;
        private WallpaperColors mSystemColors;

        private LoadWallpaperColors() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Void doInBackground(WallpaperManager... wallpaperManagerArr) {
            this.mSystemColors = wallpaperManagerArr[0].getWallpaperColors(1);
            this.mLockColors = wallpaperManagerArr[0].getWallpaperColors(2);
            return null;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Void r2) {
            ColorExtractor.this.mSystemColors = this.mSystemColors;
            ColorExtractor.this.mLockColors = this.mLockColors;
            ColorExtractor.this.extractWallpaperColors();
            ColorExtractor.this.triggerColorsChanged(3);
        }
    }

    protected void extractWallpaperColors() {
        GradientColors[] gradientColorsArr = this.mGradientColors.get(1);
        GradientColors[] gradientColorsArr2 = this.mGradientColors.get(2);
        extractInto(this.mSystemColors, gradientColorsArr[0], gradientColorsArr[1], gradientColorsArr[2]);
        extractInto(this.mLockColors, gradientColorsArr2[0], gradientColorsArr2[1], gradientColorsArr2[2]);
    }

    public GradientColors getColors(int i) {
        return getColors(i, 1);
    }

    public GradientColors getColors(int i, int i2) {
        if (i2 != 0 && i2 != 1 && i2 != 2) {
            throw new IllegalArgumentException("type should be TYPE_NORMAL, TYPE_DARK or TYPE_EXTRA_DARK");
        }
        if (i != 2 && i != 1) {
            throw new IllegalArgumentException("which should be FLAG_SYSTEM or FLAG_NORMAL");
        }
        return this.mGradientColors.get(i)[i2];
    }

    public WallpaperColors getWallpaperColors(int i) {
        if (i == 2) {
            return this.mLockColors;
        }
        if (i == 1) {
            return this.mSystemColors;
        }
        throw new IllegalArgumentException("Invalid value for which: " + i);
    }

    @Override // android.app.WallpaperManager.OnColorsChangedListener
    public void onColorsChanged(WallpaperColors wallpaperColors, int i) {
        boolean z;
        boolean z2 = true;
        if ((i & 2) != 0) {
            this.mLockColors = wallpaperColors;
            GradientColors[] gradientColorsArr = this.mGradientColors.get(2);
            extractInto(wallpaperColors, gradientColorsArr[0], gradientColorsArr[1], gradientColorsArr[2]);
            z = true;
        } else {
            z = false;
        }
        if ((i & 1) != 0) {
            this.mSystemColors = wallpaperColors;
            GradientColors[] gradientColorsArr2 = this.mGradientColors.get(1);
            extractInto(wallpaperColors, gradientColorsArr2[0], gradientColorsArr2[1], gradientColorsArr2[2]);
        } else {
            z2 = z;
        }
        if (z2) {
            triggerColorsChanged(i);
        }
    }

    protected void triggerColorsChanged(int i) {
        ArrayList arrayList = new ArrayList(this.mOnColorsChangedListeners);
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            WeakReference weakReference = (WeakReference) arrayList.get(i2);
            OnColorsChangedListener onColorsChangedListener = (OnColorsChangedListener) weakReference.get();
            if (onColorsChangedListener == null) {
                this.mOnColorsChangedListeners.remove(weakReference);
            } else {
                onColorsChangedListener.onColorsChanged(this, i);
            }
        }
    }

    private void extractInto(WallpaperColors wallpaperColors, GradientColors gradientColors, GradientColors gradientColors2, GradientColors gradientColors3) {
        this.mExtractionType.extractInto(wallpaperColors, gradientColors, gradientColors2, gradientColors3);
    }

    public void destroy() {
        WallpaperManager wallpaperManager = (WallpaperManager) this.mContext.getSystemService(WallpaperManager.class);
        if (wallpaperManager != null) {
            wallpaperManager.removeOnColorsChangedListener(this);
        }
    }

    public void addOnColorsChangedListener(OnColorsChangedListener onColorsChangedListener) {
        this.mOnColorsChangedListeners.add(new WeakReference<>(onColorsChangedListener));
    }

    public void removeOnColorsChangedListener(OnColorsChangedListener onColorsChangedListener) {
        ArrayList arrayList = new ArrayList(this.mOnColorsChangedListeners);
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            WeakReference weakReference = (WeakReference) arrayList.get(i);
            if (weakReference.get() == onColorsChangedListener) {
                this.mOnColorsChangedListeners.remove(weakReference);
                return;
            }
        }
    }

    public static class GradientColors {
        private int[] mColorPalette;
        private int mMainColor;
        private int mSecondaryColor;
        private boolean mSupportsDarkText;

        public void setMainColor(int i) {
            this.mMainColor = i;
        }

        public void setSecondaryColor(int i) {
            this.mSecondaryColor = i;
        }

        public void setColorPalette(int[] iArr) {
            this.mColorPalette = iArr;
        }

        public void setSupportsDarkText(boolean z) {
            this.mSupportsDarkText = z;
        }

        public void set(GradientColors gradientColors) {
            this.mMainColor = gradientColors.mMainColor;
            this.mSecondaryColor = gradientColors.mSecondaryColor;
            this.mColorPalette = gradientColors.mColorPalette;
            this.mSupportsDarkText = gradientColors.mSupportsDarkText;
        }

        public int getMainColor() {
            return this.mMainColor;
        }

        public int getSecondaryColor() {
            return this.mSecondaryColor;
        }

        public int[] getColorPalette() {
            return this.mColorPalette;
        }

        public boolean supportsDarkText() {
            return this.mSupportsDarkText;
        }

        public boolean equals(Object obj) {
            if (obj != null && obj.getClass() == getClass()) {
                GradientColors gradientColors = (GradientColors) obj;
                if (gradientColors.mMainColor == this.mMainColor && gradientColors.mSecondaryColor == this.mSecondaryColor && gradientColors.mSupportsDarkText == this.mSupportsDarkText) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return (((this.mMainColor * 31) + this.mSecondaryColor) * 31) + (!this.mSupportsDarkText ? 1 : 0);
        }

        public String toString() {
            return "GradientColors(" + Integer.toHexString(this.mMainColor) + ", " + Integer.toHexString(this.mSecondaryColor) + NavigationBarInflaterView.KEY_CODE_END;
        }
    }
}
