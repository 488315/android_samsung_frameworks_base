package android.app;

import android.content.Context;
import android.graphics.RectF;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class WallpaperColorOverrideAreas {
    public static final int DISPLAY_TYPE_PHONE = 0;
    public static final int DISPLAY_TYPE_SUB = 2;
    public static final int DISPLAY_TYPE_TABLET = 1;
    public static final int DISPLAY_TYPE_VIRTUAL = 4;
    public static final int DISPLAY_TYPE_WATCHFACE = 3;
    private static final String FIRST_DELIMITER = ";";
    public static final String KEY_CUSTOM_WALLPAPER_COLOR_AREAS_HOME = "custom_wallpaper_color_areas_home";
    public static final String KEY_CUSTOM_WALLPAPER_COLOR_AREAS_LOCK = "custom_wallpaper_color_areas_lock";
    public static final int ROTATION_270 = 2;
    public static final int ROTATION_90 = 1;
    public static final int ROTATION_ALL = 4;
    public static final int ROTATION_LANDSCAPE = 3;
    public static final int ROTATION_PORTRAIT = 0;
    private static final String SECOND_DELIMITER = "-";
    public static final String TAG = "WallpaperColorOverrideAreas";
    private static final String THIRD_DELIMITER = ":";
    private HashMap<String, RectF> mAreaMap;
    private Context mContext;
    private String mSettingsKey;
    private int mWhich;

    @Retention(RetentionPolicy.SOURCE)
    public @interface DISPLAY_TYPE {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ROTATION {
    }

    public WallpaperColorOverrideAreas(Context context, int i) {
        this(context, i, null);
    }

    public WallpaperColorOverrideAreas(Context context, int i, WallpaperColorOverrideAreas wallpaperColorOverrideAreas) {
        this.mAreaMap = new HashMap<>();
        this.mContext = context;
        this.mWhich = i;
        bindSettingsKey();
        if (wallpaperColorOverrideAreas != null) {
            wallpaperColorOverrideAreas.fill(this.mAreaMap);
            Log.i(TAG, "Init with base info. Copied = " + this);
        }
    }

    private void bindSettingsKey() {
        int i = this.mWhich;
        if ((i & 2) == 2) {
            this.mSettingsKey = KEY_CUSTOM_WALLPAPER_COLOR_AREAS_LOCK;
        } else if ((i & 1) == 1) {
            this.mSettingsKey = KEY_CUSTOM_WALLPAPER_COLOR_AREAS_HOME;
        } else {
            this.mSettingsKey = KEY_CUSTOM_WALLPAPER_COLOR_AREAS_LOCK;
        }
    }

    public void add(int i, int i2, long j, RectF rectF) {
        if (i2 == 3) {
            this.mAreaMap.put(combineKey(i, 1, j), new RectF(rectF));
            this.mAreaMap.put(combineKey(i, 2, j), new RectF(rectF));
        } else if (i2 == 4) {
            this.mAreaMap.put(combineKey(i, 0, j), new RectF(rectF));
            this.mAreaMap.put(combineKey(i, 1, j), new RectF(rectF));
            this.mAreaMap.put(combineKey(i, 2, j), new RectF(rectF));
            this.mAreaMap.put(combineKey(i, 3, j), new RectF(rectF));
        }
        this.mAreaMap.put(combineKey(i, i2, j), new RectF(rectF));
    }

    public void remove(int i, int i2, long j) {
        if (i2 == 3) {
            this.mAreaMap.remove(combineKey(i, 1, j));
            this.mAreaMap.remove(combineKey(i, 2, j));
        } else if (i2 == 4) {
            this.mAreaMap.remove(combineKey(i, 0, j));
            this.mAreaMap.remove(combineKey(i, 1, j));
            this.mAreaMap.remove(combineKey(i, 2, j));
            this.mAreaMap.remove(combineKey(i, 3, j));
        }
        this.mAreaMap.remove(combineKey(i, i2, j));
    }

    public RectF get(int i, int i2, long j) {
        return this.mAreaMap.get(combineKey(i, i2, j));
    }

    private String combineKey(int i, int i2, long j) {
        return i + ":" + i2 + ":" + j;
    }

    private String combineValue(RectF rectF) {
        return rectF.left + ":" + rectF.top + ":" + rectF.right + ":" + rectF.bottom;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, RectF> entry : this.mAreaMap.entrySet()) {
            if (entry != null) {
                sb.append(entry.getKey());
                sb.append("-");
                sb.append(combineValue(entry.getValue()));
                sb.append(";");
            }
        }
        return sb.toString();
    }

    private void parse(String str) {
        Log.i(TAG, "Parsing color area : " + str);
        if (TextUtils.isEmpty(str)) {
            return;
        }
        for (String str2 : str.split(";")) {
            if (!TextUtils.isEmpty(str2)) {
                String[] strArrSplit = str2.split("-");
                if (strArrSplit.length == 2 && !TextUtils.isEmpty(strArrSplit[0]) && !TextUtils.isEmpty(strArrSplit[1])) {
                    String[] strArrSplit2 = strArrSplit[1].split(":");
                    if (strArrSplit2.length == 4) {
                        try {
                            this.mAreaMap.put(strArrSplit[0], new RectF(Float.parseFloat(strArrSplit2[0]), Float.parseFloat(strArrSplit2[1]), Float.parseFloat(strArrSplit2[2]), Float.parseFloat(strArrSplit2[3])));
                        } catch (RuntimeException unused) {
                            Log.e(TAG, "Cannot parsing area rect : " + strArrSplit[1]);
                        }
                    }
                }
            }
        }
    }

    public void load() {
        parse(Settings.System.getString(this.mContext.getContentResolver(), this.mSettingsKey));
    }

    public void store() {
        Settings.System.putString(this.mContext.getContentResolver(), this.mSettingsKey, toString());
    }

    private void fill(HashMap<String, RectF> map) {
        if (map == null) {
            return;
        }
        for (Map.Entry<String, RectF> entry : this.mAreaMap.entrySet()) {
            if (entry != null) {
                map.put(entry.getKey(), new RectF(entry.getValue()));
            }
        }
    }
}
