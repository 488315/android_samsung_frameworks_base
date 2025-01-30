package android.app;

import android.content.Context;
import android.graphics.RectF;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import com.samsung.android.wallpaper.utils.WhichChecker;
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
    public static final String TAG = WallpaperColorOverrideAreas.class.getSimpleName();
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

    public WallpaperColorOverrideAreas(Context context, int which) {
        this(context, which, null);
    }

    public WallpaperColorOverrideAreas(Context context, int which, WallpaperColorOverrideAreas base) {
        this.mAreaMap = new HashMap<>();
        this.mContext = context;
        this.mWhich = which;
        bindSettingsKey();
        if (base != null) {
            base.fill(this.mAreaMap);
            Log.m98i(TAG, "Init with base info. Copied = " + this);
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

    public void add(int displayType, int rotation, long area, RectF bounds) {
        if (rotation == 3) {
            this.mAreaMap.put(combineKey(displayType, 1, area), new RectF(bounds));
            this.mAreaMap.put(combineKey(displayType, 2, area), new RectF(bounds));
        } else if (rotation == 4) {
            this.mAreaMap.put(combineKey(displayType, 0, area), new RectF(bounds));
            this.mAreaMap.put(combineKey(displayType, 1, area), new RectF(bounds));
            this.mAreaMap.put(combineKey(displayType, 2, area), new RectF(bounds));
            this.mAreaMap.put(combineKey(displayType, 3, area), new RectF(bounds));
        }
        this.mAreaMap.put(combineKey(displayType, rotation, area), new RectF(bounds));
    }

    public void remove(int displayType, int rotation, long area) {
        if (rotation == 3) {
            this.mAreaMap.remove(combineKey(displayType, 1, area));
            this.mAreaMap.remove(combineKey(displayType, 2, area));
        } else if (rotation == 4) {
            this.mAreaMap.remove(combineKey(displayType, 0, area));
            this.mAreaMap.remove(combineKey(displayType, 1, area));
            this.mAreaMap.remove(combineKey(displayType, 2, area));
            this.mAreaMap.remove(combineKey(displayType, 3, area));
        }
        this.mAreaMap.remove(combineKey(displayType, rotation, area));
    }

    public RectF get(int displayType, int rotation, long area) {
        return this.mAreaMap.get(combineKey(displayType, rotation, area));
    }

    private String combineKey(int displayType, int rotation, long area) {
        return displayType + ":" + rotation + ":" + area;
    }

    private String combineValue(RectF bounds) {
        return bounds.left + ":" + bounds.top + ":" + bounds.right + ":" + bounds.bottom;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, RectF> entry : this.mAreaMap.entrySet()) {
            if (entry != null) {
                builder.append(entry.getKey()).append("-").append(combineValue(entry.getValue())).append(";");
            }
        }
        return builder.toString();
    }

    private void parse(String settingsString) {
        Log.m98i(TAG, "Parsing color area : " + settingsString);
        if (TextUtils.isEmpty(settingsString)) {
            return;
        }
        String[] areaStringList = settingsString.split(";");
        for (String areaString : areaStringList) {
            if (!TextUtils.isEmpty(areaString)) {
                String[] keyValueList = areaString.split("-");
                if (keyValueList.length == 2 && !TextUtils.isEmpty(keyValueList[0]) && !TextUtils.isEmpty(keyValueList[1])) {
                    String[] valueList = keyValueList[1].split(":");
                    if (valueList.length == 4) {
                        try {
                            RectF areaRect = new RectF(Float.parseFloat(valueList[0]), Float.parseFloat(valueList[1]), Float.parseFloat(valueList[2]), Float.parseFloat(valueList[3]));
                            this.mAreaMap.put(keyValueList[0], areaRect);
                        } catch (RuntimeException e) {
                            Log.m96e(TAG, "Cannot parsing area rect : " + keyValueList[1]);
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
        String dataToStore = toString();
        Settings.System.putString(this.mContext.getContentResolver(), this.mSettingsKey, dataToStore);
        WallpaperManager wpMgr = WallpaperManager.getInstance(this.mContext);
        wpMgr.semSetWallpaperColorOverrideAreas(WhichChecker.getType(this.mWhich), this.mContext.getUserId(), dataToStore);
    }

    private void fill(HashMap<String, RectF> areaMap) {
        if (areaMap == null) {
            return;
        }
        for (Map.Entry<String, RectF> entry : this.mAreaMap.entrySet()) {
            if (entry != null) {
                areaMap.put(entry.getKey(), new RectF(entry.getValue()));
            }
        }
    }
}
