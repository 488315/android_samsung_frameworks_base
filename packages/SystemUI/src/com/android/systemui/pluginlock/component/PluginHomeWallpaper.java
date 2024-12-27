package com.android.systemui.pluginlock.component;

import android.app.SemWallpaperColors;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ComposerImpl$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecPatternView$$ExternalSyntheticOutline0;
import com.android.systemui.LsRune;
import com.android.systemui.pluginlock.utils.BitmapUtils;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public class PluginHomeWallpaper {
    private static final String PREFIX_FIRST_FILE = "wallpaper_0";
    private static final String TAG = "PluginHomeWallpaper";
    private static int sScreenType;
    private final Context mContext;
    private final Map<Integer, WallpaperData> mWallpaperDataList;
    private PluginWallpaperCallback mWallpaperUpdateCallback;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    class WallpaperData {
        private SemWallpaperColors mHints;
        private String mIntelligentCrops;
        private String mPath;
        private Rect mRect;
        private int mType;
        private Uri mUri;

        public /* synthetic */ WallpaperData(int i) {
            this();
        }

        public SemWallpaperColors getHints() {
            return this.mHints;
        }

        public String getIntelligentCrops() {
            return this.mIntelligentCrops;
        }

        public String getPath() {
            return this.mPath;
        }

        public Rect getRect() {
            return this.mRect;
        }

        public int getType() {
            return this.mType;
        }

        public Uri getUri() {
            return this.mUri;
        }

        public boolean hasData() {
            return (this.mType == -2 || (this.mPath == null && this.mUri == null)) ? false : true;
        }

        public void resetAll() {
            this.mType = -2;
            this.mPath = null;
            this.mUri = null;
            this.mHints = null;
            this.mRect = null;
        }

        public void resetData() {
            this.mPath = null;
            this.mUri = null;
            this.mHints = null;
            this.mRect = null;
        }

        public void setHints(SemWallpaperColors semWallpaperColors) {
            this.mHints = semWallpaperColors;
        }

        public void setIntelligentCrops(String str) {
            this.mIntelligentCrops = str;
        }

        public void setPath(String str) {
            this.mPath = str;
            this.mUri = null;
            this.mRect = getRect(str);
        }

        public void setType(int i) {
            this.mType = i;
        }

        public void setUri(Uri uri) {
            this.mPath = null;
            this.mUri = uri;
            this.mRect = null;
        }

        private WallpaperData() {
            this.mType = -2;
            this.mPath = null;
            this.mIntelligentCrops = null;
            this.mUri = null;
            this.mHints = null;
            this.mRect = null;
        }

        private Rect getRect(String str) {
            FileInputStream fileInputStream;
            String m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str.replaceFirst("[.^][^.]+$", ""), "_rect.txt");
            Rect rect = null;
            try {
                fileInputStream = new FileInputStream(m);
            } catch (FileNotFoundException unused) {
                Log.w(PluginHomeWallpaper.TAG, "getRect, " + m + " is not available");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                try {
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    try {
                        rect = Rect.unflattenFromString(bufferedReader.readLine());
                        bufferedReader.close();
                        inputStreamReader.close();
                        fileInputStream.close();
                        Log.d(PluginHomeWallpaper.TAG, "getRect, rectPath: " + m + ", rect: " + rect);
                        return rect;
                    } finally {
                    }
                } finally {
                }
            } catch (Throwable th) {
                try {
                    fileInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
    }

    public PluginHomeWallpaper(Context context) {
        HashMap hashMap = new HashMap();
        this.mWallpaperDataList = hashMap;
        this.mContext = context;
        int i = 0;
        hashMap.put(0, new WallpaperData(i));
        if (LsRune.WALLPAPER_SUB_WATCHFACE || LsRune.WALLPAPER_VIRTUAL_DISPLAY) {
            hashMap.put(1, new WallpaperData(i));
        }
    }

    public static void setScreenTypeChanged(int i) {
        KeyguardSecPatternView$$ExternalSyntheticOutline0.m(i, "setScreenTypeChanged() type: ", TAG);
        sScreenType = i;
    }

    public void clearWallpaper() {
        try {
            this.mWallpaperDataList.get(0).resetAll();
            if (!LsRune.WALLPAPER_SUB_WATCHFACE && !LsRune.WALLPAPER_VIRTUAL_DISPLAY) {
                return;
            }
            this.mWallpaperDataList.get(1).resetAll();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public PluginWallpaperCallback getCallback() {
        return this.mWallpaperUpdateCallback;
    }

    public int getCurrentScreen() {
        return sScreenType;
    }

    public String getIntelligentCrops(int i) {
        WallpaperData wallpaperData = this.mWallpaperDataList.get(Integer.valueOf(getKey(i)));
        if (wallpaperData != null) {
            return wallpaperData.getIntelligentCrops();
        }
        return null;
    }

    public int getKey(int i) {
        return (i == 1 || i == 0) ? i : ((i & 16) == 16 || (i & 32) == 32) ? 1 : 0;
    }

    public Bitmap getWallpaperBitmap(int i) {
        int key = getKey(i);
        WallpaperData wallpaperData = this.mWallpaperDataList.get(Integer.valueOf(key));
        if (wallpaperData != null) {
            Uri uri = wallpaperData.getUri();
            String path = wallpaperData.getPath();
            boolean z = getIntelligentCrops(i) != null;
            Log.d(TAG, "getWallpaperBitmap() path:" + path + ", hasICrops = " + z);
            if (path != null) {
                return BitmapUtils.getBitmapFromPath(this.mContext, path, !z, key == 1);
            }
            if (uri != null) {
                return BitmapUtils.getBitmapFromUri(this.mContext, uri, !z, key == 1);
            }
            Log.w(TAG, "getWallpaperBitmap() no available data");
        }
        return null;
    }

    public String getWallpaperPath(int i) {
        WallpaperData wallpaperData = this.mWallpaperDataList.get(Integer.valueOf(getKey(i)));
        if (wallpaperData != null) {
            return wallpaperData.getPath();
        }
        return null;
    }

    public Rect getWallpaperRect(int i) {
        WallpaperData wallpaperData = this.mWallpaperDataList.get(Integer.valueOf(getKey(i)));
        if (wallpaperData != null) {
            return wallpaperData.getRect();
        }
        return null;
    }

    public int getWallpaperType(int i) {
        WallpaperData wallpaperData = this.mWallpaperDataList.get(Integer.valueOf(getKey(i)));
        if (wallpaperData != null) {
            return wallpaperData.getType();
        }
        Log.w(TAG, "getWallpaperType: WallpaperData is null for screen [" + i + "]");
        return -2;
    }

    public void resetWallpaper(int i) {
        try {
            this.mWallpaperDataList.get(Integer.valueOf(getKey(i))).resetAll();
            Log.d(TAG, "resetWallpaper()");
            PluginWallpaperCallback pluginWallpaperCallback = this.mWallpaperUpdateCallback;
            if (pluginWallpaperCallback != null) {
                pluginWallpaperCallback.onWallpaperHintUpdate(null);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void setWallpaper(int i, int i2, int i3, String str) {
        setWallpaper(i, i2, i3, str, null);
    }

    public void setWallpaperHints(SemWallpaperColors semWallpaperColors) {
        WallpaperData wallpaperData = this.mWallpaperDataList.get(Integer.valueOf(getKey(sScreenType)));
        if (wallpaperData != null) {
            wallpaperData.setHints(semWallpaperColors);
        }
    }

    public void setWallpaperUpdateCallback(PluginWallpaperCallback pluginWallpaperCallback) {
        this.mWallpaperUpdateCallback = pluginWallpaperCallback;
    }

    public void updateHint() {
        if (this.mWallpaperUpdateCallback != null) {
            int key = getKey(sScreenType);
            ListPopupWindow$$ExternalSyntheticOutline0.m(key, "updateHint() onWallpaperHintUpdate will be called: ", TAG);
            WallpaperData wallpaperData = this.mWallpaperDataList.get(Integer.valueOf(key));
            if (wallpaperData != null) {
                this.mWallpaperUpdateCallback.onWallpaperHintUpdate(wallpaperData.getHints());
            }
        }
    }

    public void setWallpaper(int i, int i2, int i3, String str, String str2) {
        boolean contains;
        StringBuilder m = RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(i2, i3, "setWallpaper() wallpaperType:", ", sourceType:", ", source:");
        ComposerImpl$$ExternalSyntheticOutline0.m(m, str, ", screen:", i, ", iCrops = ");
        ExifInterface$$ExternalSyntheticOutline0.m(m, str2, TAG);
        WallpaperData wallpaperData = this.mWallpaperDataList.get(Integer.valueOf(getKey(i)));
        boolean z = false;
        z = false;
        if (wallpaperData == null) {
            wallpaperData = new WallpaperData(z ? 1 : 0);
            this.mWallpaperDataList.put(Integer.valueOf(getKey(i)), wallpaperData);
        }
        boolean hasData = wallpaperData.hasData();
        wallpaperData.setType(i2);
        wallpaperData.setIntelligentCrops(str2);
        if (i3 == 0) {
            wallpaperData.setPath(str);
            contains = str.contains(PREFIX_FIRST_FILE);
        } else if (i3 != 2) {
            Log.w(TAG, "setWallpaper() unsupported type!");
            return;
        } else {
            wallpaperData.setUri(Uri.parse(str));
            contains = false;
        }
        Log.d(TAG, "setWallpaper() mWallpaperUpdateCallback:" + this.mWallpaperUpdateCallback);
        PluginWallpaperCallback pluginWallpaperCallback = this.mWallpaperUpdateCallback;
        if (pluginWallpaperCallback != null) {
            if (!hasData && contains) {
                z = true;
            }
            pluginWallpaperCallback.onWallpaperUpdate(z);
        }
    }
}
