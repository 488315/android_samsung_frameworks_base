package android.app;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Bundle;
import android.os.Debug;
import android.text.TextUtils;
import android.util.Log;

import com.samsung.android.wallpaper.utils.SemWallpaperProperties;
import com.samsung.android.wallpaper.utils.WhichChecker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileDescriptor;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class SemWallpaperResourcesInfo {
    protected static final boolean DEBUG = Debug.semIsProductDev();
    protected static final String TAG = "WallpaperResourcesInfo";
    private static final String WALLPAPER_PACKAGE = "com.samsung.android.wallpaper.res";
    private Context mContext;
    private Context mResPkgContext;
    private ResourceData mResource;
    private int mVersion = 1;

    static class Item {
        public Integer index = -1;
        public int which = -1;
        public String fileName = null;
        public int type = -1;
        public TypeParams typeParams = new TypeParams();
        public int videoFrameInfo = -1;
        public boolean isBlackFirstFrame = false;
        public boolean isDefault = false;
        public boolean isBespoke = false;
        public ArrayList<String> cmfInfo = new ArrayList<>();

        Item() {}

        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("type=" + this.type);
            builder.append(", which=" + this.which);
            builder.append(", index=" + this.index);
            builder.append(", default=" + this.isDefault);
            builder.append(", file=" + this.fileName);
            switch (this.type) {
                case 7:
                    builder.append(", extra=" + this.typeParams);
                    break;
                case 8:
                    builder.append(", frame=" + this.videoFrameInfo);
                    builder.append(", isBlackFrame=" + this.isBlackFirstFrame);
                    break;
            }
            builder.append(", bespoke=" + this.isBespoke);
            builder.append(", cmf=");
            Iterator<String> it = this.cmfInfo.iterator();
            while (it.hasNext()) {
                String cmf = it.next();
                builder.append(cmf + " ");
            }
            return builder.toString();
        }
    }

    static class TypeParams {
        public Bundle mExtras = new Bundle();
        public String mServiceClassName;
        public String mServicePkgName;

        TypeParams() {}

        public String toString() {
            return this.mServicePkgName + "/" + this.mServiceClassName;
        }
    }

    static class ResourceData {
        private boolean mIsSupportCMF;
        private final HashMap<Integer, ArrayList<Item>> mItemsMap = new HashMap<>();
        private final ArrayList<String> mBespokeCode = new ArrayList<>();
        private final HashMap<Integer, Integer> mDefaultTypeMap = new HashMap<>();
        private final HashMap<Integer, String> mDefaultMultipackStyle = new HashMap<>();
        private final HashSet<String> mKnownColorCode = new HashSet<>();

        ResourceData() {}

        public void addItem(Item item) {
            ArrayList<Item> itemArray = this.mItemsMap.get(Integer.valueOf(item.type));
            if (itemArray == null) {
                itemArray = new ArrayList<>();
                this.mItemsMap.put(Integer.valueOf(item.type), itemArray);
            }
            itemArray.add(item);
        }

        public void setDefaultWallpaperType(int which, int type) {
            this.mDefaultTypeMap.put(Integer.valueOf(which), Integer.valueOf(type));
        }

        public void setDefaultMultipackStyle(int which, String style) {
            this.mDefaultMultipackStyle.put(Integer.valueOf(which), style);
        }

        public void addKnownColor(String colorCode) {
            this.mKnownColorCode.add(colorCode);
        }

        public void addKnownColors(ArrayList<String> cmfArray) {
            if (cmfArray == null) {
                return;
            }
            Iterator<String> it = cmfArray.iterator();
            while (it.hasNext()) {
                String colorCode = it.next();
                addKnownColor(colorCode);
            }
        }

        public void addBespokeCode(String colorCode) {
            this.mBespokeCode.add(colorCode);
        }

        public boolean isKnownColorCode(String colorCode) {
            if (TextUtils.isEmpty(colorCode)) {
                return false;
            }
            return this.mKnownColorCode.contains(colorCode);
        }

        public boolean isSupportCMF() {
            return this.mIsSupportCMF;
        }

        public int getDefaultWallpaperType(int which, String colorCode) {
            Item matchedItem = null;
            boolean isBespokeDevice = isBespokeCode(colorCode);
            if (this.mIsSupportCMF && isBespokeDevice) {
                matchedItem = getFirstExactlyMatchedItemFromAllTypes(which, colorCode);
            }
            return matchedItem != null ? matchedItem.type : getDefaultWallpaperType(which);
        }

        private int getDefaultWallpaperType(int which) {
            if (WhichChecker.isModeAbsent(which)) {
                Log.w(
                        SemWallpaperResourcesInfo.TAG,
                        "getDefaultWallpaperType: mode is missing. which=" + which,
                        new IllegalArgumentException());
            }
            return this.mDefaultTypeMap.getOrDefault(Integer.valueOf(which), 0).intValue();
        }

        public String getDefaultMultipackStyle(int which) {
            if (isPhone(which)) {
                which |= 4;
            }
            return this.mDefaultMultipackStyle.get(Integer.valueOf(which));
        }

        public Item getDefaultWallpaperItem(int which, String deviceColorCode, int wallpaperType) {
            if (WhichChecker.isModeAbsent(which)) {
                Log.w(
                        SemWallpaperResourcesInfo.TAG,
                        "getDefaultWallpaperItem: mode is missing. which=" + which,
                        new IllegalArgumentException());
            }
            Item matchedItem = null;
            ArrayList<Item> candidateItems = this.mItemsMap.get(Integer.valueOf(wallpaperType));
            if (candidateItems != null && !candidateItems.isEmpty()) {
                matchedItem = chooseDefaultWallpaperItem(which, deviceColorCode, candidateItems);
            }
            if (matchedItem == null) {
                Log.w(
                        SemWallpaperResourcesInfo.TAG,
                        "getDefaultWallpaperItem: nothing matched. which=" + which);
                return null;
            }
            if (WhichChecker.isSystemAndLock(matchedItem.which) && WhichChecker.isLock(which)) {
                Log.i(
                        SemWallpaperResourcesInfo.TAG,
                        "getDefaultWallpaperItem: paired lock. which="
                                + which
                                + ", matched=["
                                + matchedItem
                                + NavigationBarInflaterView.SIZE_MOD_END);
                return null;
            }
            if (SemWallpaperResourcesInfo.DEBUG) {
                Log.i(
                        SemWallpaperResourcesInfo.TAG,
                        "getDefaultWallpaperItem: which="
                                + which
                                + ", colorCode="
                                + deviceColorCode
                                + ", matched=["
                                + matchedItem
                                + NavigationBarInflaterView.SIZE_MOD_END);
            }
            return matchedItem;
        }

        private Item chooseDefaultWallpaperItem(
                int which, String deviceColorCode, ArrayList<Item> candidateItems) {
            Item item;
            if (candidateItems.isEmpty()) {
                Log.w(SemWallpaperResourcesInfo.TAG, "getDefaultWallpaperItem: empty item array");
                return null;
            }
            if (this.mIsSupportCMF
                    && !TextUtils.isEmpty(deviceColorCode)
                    && (item = getFirstExactlyMatchedItem(which, deviceColorCode, candidateItems))
                            != null) {
                if (SemWallpaperResourcesInfo.DEBUG) {
                    Log.i(
                            SemWallpaperResourcesInfo.TAG,
                            "getDefaultWallpaperItem: which & color matched. item=" + item);
                }
                return item;
            }
            boolean isBespokeDevice = isBespokeCode(deviceColorCode);
            Item firstItem = null;
            Iterator<Item> it = candidateItems.iterator();
            while (it.hasNext()) {
                Item item2 = it.next();
                int itemWhich = item2.which;
                if ((which & itemWhich) == which && (isBespokeDevice || !item2.isBespoke)) {
                    if (firstItem == null) {
                        firstItem = item2;
                    }
                    if (item2.isDefault) {
                        if (SemWallpaperResourcesInfo.DEBUG) {
                            Log.i(
                                    SemWallpaperResourcesInfo.TAG,
                                    "getDefaultWallpaperItem: which & default matched. item="
                                            + item2);
                        }
                        return item2;
                    }
                }
            }
            if (firstItem != null) {
                if (SemWallpaperResourcesInfo.DEBUG) {
                    Log.i(
                            SemWallpaperResourcesInfo.TAG,
                            "getDefaultWallpaperItem: which matched. use first item. item="
                                    + firstItem);
                }
                return firstItem;
            }
            Item firstItem2 = null;
            Iterator<Item> it2 = candidateItems.iterator();
            while (it2.hasNext()) {
                Item item3 = it2.next();
                if (isBespokeDevice || !item3.isBespoke) {
                    if (firstItem2 == null) {
                        firstItem2 = item3;
                    }
                    if (item3.isDefault) {
                        if (SemWallpaperResourcesInfo.DEBUG) {
                            Log.i(
                                    SemWallpaperResourcesInfo.TAG,
                                    "getDefaultWallpaperItem: default matched. item=" + item3);
                        }
                        return item3;
                    }
                }
            }
            if (firstItem2 != null) {
                if (SemWallpaperResourcesInfo.DEBUG) {
                    Log.i(
                            SemWallpaperResourcesInfo.TAG,
                            "getDefaultWallpaperItem: type matched. use first item. item="
                                    + firstItem2);
                }
                return firstItem2;
            }
            Log.w(
                    SemWallpaperResourcesInfo.TAG,
                    "getDefaultWallpaperItem: could not find matched item. which="
                            + which
                            + ", deviceColor="
                            + deviceColorCode);
            return null;
        }

        private Item getFirstExactlyMatchedItemFromAllTypes(int which, String colorCode) {
            ArrayList<Integer> keySet = new ArrayList<>(this.mItemsMap.keySet());
            int videoTypeIndex = keySet.indexOf(8);
            if (videoTypeIndex >= 0) {
                keySet.remove(videoTypeIndex);
                keySet.add(0, 8);
            }
            Iterator<Integer> it = keySet.iterator();
            while (it.hasNext()) {
                int key = it.next().intValue();
                ArrayList<Item> candidateItems = this.mItemsMap.get(Integer.valueOf(key));
                Item item = getFirstExactlyMatchedItem(which, colorCode, candidateItems);
                if (item != null) {
                    return item;
                }
            }
            return null;
        }

        private Item getFirstExactlyMatchedItem(
                int which, String colorCode, ArrayList<Item> candidateItems) {
            if (TextUtils.isEmpty(colorCode)) {
                return null;
            }
            Iterator<Item> it = candidateItems.iterator();
            while (it.hasNext()) {
                Item item = it.next();
                if (isDefaultResource(item, which, colorCode)) {
                    return item;
                }
            }
            return null;
        }

        public Item getVideoItemByFilename(String filename) {
            if (TextUtils.isEmpty(filename)) {
                Log.e(SemWallpaperResourcesInfo.TAG, "getVideoItemByFilename: fileName is null");
                return null;
            }
            ArrayList<Item> itemArray = this.mItemsMap.get(8);
            if (itemArray == null || itemArray.isEmpty()) {
                Log.i(
                        SemWallpaperResourcesInfo.TAG,
                        "getVideoItemByFilename: video item array is empty");
                return null;
            }
            Iterator<Item> it = itemArray.iterator();
            while (it.hasNext()) {
                Item item = it.next();
                if (filename.equals(item.fileName)) {
                    return item;
                }
            }
            return null;
        }

        public void finalizeInternalState() {
            sortAscending();
            this.mIsSupportCMF = determineSupportsCmf();
        }

        public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
            if (this.mDefaultTypeMap != null && this.mDefaultTypeMap.size() > 0) {
                for (Map.Entry<Integer, Integer> entry : this.mDefaultTypeMap.entrySet()) {
                    pw.println(String.format("\t[%5d: %5d]", entry.getKey(), entry.getValue()));
                }
            }
        }

        private void sortAscending() {
            Comparator ascending = new Comparator<Item>() { // from class:
                        // android.app.SemWallpaperResourcesInfo.ResourceData.1
                        @Override // java.util.Comparator
                        public int compare(Item r1, Item r2) {
                            return r1.index.compareTo(r2.index);
                        }
                    };
            for (ArrayList<Item> itemArray : this.mItemsMap.values()) {
                if (itemArray.size() > 1) {
                    Collections.sort(itemArray, ascending);
                }
            }
        }

        private boolean determineSupportsCmf() {
            for (ArrayList<Item> itemArray : this.mItemsMap.values()) {
                Iterator<Item> it = itemArray.iterator();
                while (it.hasNext()) {
                    Item item = it.next();
                    if (isValidCode(item.cmfInfo)) {
                        return true;
                    }
                }
            }
            return false;
        }

        private boolean isBespokeCode(String colorCode) {
            if (TextUtils.isEmpty(colorCode)) {
                return false;
            }
            Iterator<String> it = this.mBespokeCode.iterator();
            while (it.hasNext()) {
                String code = it.next();
                if (code.equals(colorCode)) {
                    return true;
                }
            }
            return false;
        }

        private boolean isValidCode(ArrayList<String> list) {
            Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                String code = it.next();
                if (!TextUtils.isEmpty(code)) {
                    return true;
                }
            }
            return false;
        }

        private boolean isPhone(int which) {
            return ((which & 8) == 8 || (which & 16) == 16) ? false : true;
        }

        private boolean isDefaultResource(Item item, int which, String colorCode) {
            if ((item.which & which) == which) {
                Iterator<String> it = item.cmfInfo.iterator();
                while (it.hasNext()) {
                    String cmfInfo = it.next();
                    if (!TextUtils.isEmpty(cmfInfo) && cmfInfo.equals(colorCode)) {
                        return true;
                    }
                }
                return false;
            }
            return false;
        }
    }

    static class ResourceParser {
        private static final int MAIN_SCREEN = 0;
        private static final int SUB_SCREEN = 1;
        private static final int WALLPAPER_TYPE_PRELOADED_LIVE = 10;
        private Context mContext;

        public ResourceParser(Context context) {
            this.mContext = context;
        }

        public ResourceData parseJson(String wallpaperResPkgName) {
            JSONException jSONException;
            JSONObject jsonRoot;
            JSONArray jsonArray;
            int itemCount;
            int i;
            String str;
            ResourceParser resourceParser = this;
            ResourceData result = new ResourceData();
            int resId =
                    resourceParser
                            .mContext
                            .getResources()
                            .getIdentifier("resources_info", "raw", wallpaperResPkgName);
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            try {
                InputStream is = resourceParser.mContext.getResources().openRawResource(resId);
                try {
                    Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    while (true) {
                        int n = reader.read(buffer);
                        if (n == -1) {
                            break;
                        }
                        try {
                            writer.write(buffer, 0, n);
                        } catch (Throwable th) {
                            jSONException = th;
                            if (is == null) {
                                throw jSONException;
                            }
                            try {
                                try {
                                    is.close();
                                    throw jSONException;
                                } catch (Exception e) {
                                    e = e;
                                    Log.i(SemWallpaperResourcesInfo.TAG, "parseJson: e=" + e, e);
                                    return result;
                                }
                            } catch (Throwable th2) {
                                jSONException.addSuppressed(th2);
                                throw jSONException;
                            }
                        }
                    }
                    if (is != null) {
                        try {
                            is.close();
                        } catch (Exception e2) {
                            e = e2;
                            Log.i(SemWallpaperResourcesInfo.TAG, "parseJson: e=" + e, e);
                            return result;
                        }
                    }
                    String jsonData = writer.toString();
                    int lastParseSuccessItemIndex = -1;
                    try {
                        jsonRoot = new JSONObject(jsonData);
                        jsonArray = jsonRoot.getJSONArray("phone");
                        itemCount = jsonArray.length();
                        i = 0;
                    } catch (JSONException e3) {
                        e = e3;
                    }
                    while (true) {
                        int resId2 = resId;
                        str = "screen";
                        if (i >= itemCount) {
                            break;
                        }
                        try {
                            Item item = new Item();
                            JSONObject jsonItem = jsonArray.getJSONObject(i);
                            Writer writer2 = writer;
                            try {
                                char[] buffer2 = buffer;
                                try {
                                    item.isDefault = jsonItem.getBoolean("isDefault");
                                    item.index = Integer.valueOf(jsonItem.getInt("index"));
                                    item.type = jsonItem.getInt("type");
                                    if (item.type == 10) {
                                        item.type = 7;
                                    }
                                    int which = jsonItem.optInt("which", -1);
                                    int screen = jsonItem.optInt("screen", -1);
                                    item.which =
                                            resourceParser.determineModeEnsuredWhich(which, screen);
                                    item.isBespoke = jsonItem.optBoolean("isBespoke", false);
                                    item.fileName = jsonItem.optString("filename", null);
                                    item.videoFrameInfo = jsonItem.optInt("frame_no", -1);
                                    item.isBlackFirstFrame =
                                            jsonItem.optBoolean("isBlackFirstFrame", false);
                                    resourceParser.parseCmfInfo(
                                            jsonItem.getJSONArray("cmf_info"), item);
                                    result.addKnownColors(item.cmfInfo);
                                    resourceParser.parseTypeParams(
                                            jsonItem.optJSONObject("type_params"), item);
                                    result.addItem(item);
                                    lastParseSuccessItemIndex = item.index.intValue();
                                    i++;
                                    resId = resId2;
                                    writer = writer2;
                                    buffer = buffer2;
                                } catch (JSONException e4) {
                                    e = e4;
                                }
                            } catch (JSONException e5) {
                                e = e5;
                            }
                        } catch (JSONException e6) {
                            e = e6;
                        }
                        e = e4;
                        Log.e(SemWallpaperResourcesInfo.TAG, "parseJson: e=" + e, e);
                        Log.e(
                                SemWallpaperResourcesInfo.TAG,
                                "parseJson: last parse success item index="
                                        + lastParseSuccessItemIndex);
                        Log.e(SemWallpaperResourcesInfo.TAG, "parseJson: " + jsonData);
                        return new ResourceData();
                    }
                    JSONArray jsonArray2 = jsonRoot.optJSONArray("types");
                    if (jsonArray2 != null) {
                        int i2 = 0;
                        while (i2 < jsonArray2.length()) {
                            JSONObject object = jsonArray2.getJSONObject(i2);
                            int screen2 = object.getInt(str);
                            int which2 =
                                    resourceParser.determineModeEnsuredWhich(
                                            object.getInt("which"), screen2);
                            String str2 = str;
                            int type = object.getInt("type");
                            if (type == 10) {
                                type = 7;
                            }
                            result.setDefaultWallpaperType(which2, type);
                            if (type == 3) {
                                String style = object.getString("style");
                                result.setDefaultMultipackStyle(which2, style);
                            }
                            i2++;
                            resourceParser = this;
                            str = str2;
                        }
                    }
                    JSONArray bespokeArray = jsonRoot.optJSONArray("bespoke");
                    if (bespokeArray != null) {
                        for (int i3 = 0; i3 < bespokeArray.length(); i3++) {
                            String colorCode = ((String) bespokeArray.get(i3)).toLowerCase();
                            if (!TextUtils.isEmpty(colorCode)) {
                                result.addBespokeCode(colorCode);
                                result.addKnownColor(colorCode);
                            }
                        }
                    }
                    result.finalizeInternalState();
                    return result;
                } catch (Throwable e7) {
                    jSONException = e7;
                }
            } catch (Exception e8) {
                e = e8;
            }
        }

        private void parseTypeParams(JSONObject jsonTypeParams, Item outItem) {
            if (jsonTypeParams == null || outItem == null) {
                return;
            }
            TypeParams typeParams = outItem.typeParams;
            typeParams.mServicePkgName = jsonTypeParams.optString("service_package_name", null);
            typeParams.mServiceClassName = jsonTypeParams.optString("service_class_name", null);
            String contentType = jsonTypeParams.optString("content_type", null);
            if (!TextUtils.isEmpty(contentType)) {
                typeParams.mExtras.putString("contentType", contentType);
            }
            JSONObject svcSettingsObj = jsonTypeParams.optJSONObject("service_settings");
            if (svcSettingsObj != null) {
                Bundle svcSettingBundle = convertJsonObjectToBundle(svcSettingsObj);
                typeParams.mExtras.putBundle(
                        SemWallpaperProperties.KEY_SERVICE_SETTINGS, svcSettingBundle);
            }
        }

        private void parseCmfInfo(JSONArray cmfArray, Item outItem) {
            if (cmfArray == null || outItem == null) {
                return;
            }
            int cmfCount = cmfArray.length();
            for (int j = 0; j < cmfCount; j++) {
                String colorCode = getRefinedColorCode(cmfArray.optString(j, null));
                if (TextUtils.isEmpty(colorCode)) {
                    Log.w(
                            SemWallpaperResourcesInfo.TAG,
                            "parseCmfInfo: empty cmf detected. wp item index=" + outItem.index);
                } else {
                    outItem.cmfInfo.add(colorCode);
                }
            }
        }

        private Bundle convertJsonObjectToBundle(JSONObject jsonObject) {
            Bundle bundle = new Bundle();
            Iterator iterator = jsonObject.keys();
            while (iterator.hasNext()) {
                String key = iterator.next().toString();
                try {
                    Object value = jsonObject.get(key);
                    if (value instanceof String) {
                        bundle.putString(key, (String) value);
                    } else if (value instanceof Integer) {
                        bundle.putInt(key, ((Integer) value).intValue());
                    } else if (value instanceof Boolean) {
                        bundle.putBoolean(key, ((Boolean) value).booleanValue());
                    } else if (value instanceof Double) {
                        bundle.putDouble(key, ((Double) value).doubleValue());
                    } else if (value instanceof JSONObject) {
                        bundle.putBundle(key, convertJsonObjectToBundle((JSONObject) value));
                    }
                } catch (JSONException e) {
                    Log.e(
                            SemWallpaperResourcesInfo.TAG,
                            "convertJsonObjectToBundle: failed to get value. key=" + key);
                }
            }
            return bundle;
        }

        private String getRefinedColorCode(String colorCode) {
            if (colorCode == null) {
                return null;
            }
            return colorCode.toLowerCase().trim();
        }

        private int determineModeEnsuredWhich(int which, int screen) {
            if (screen == 0) {
                return WhichChecker.getType(which) | 4;
            }
            if (screen == 1) {
                return WhichChecker.getType(which) | 16;
            }
            if (!WhichChecker.isModeAbsent(which)) {
                return which;
            }
            Log.w(
                    SemWallpaperResourcesInfo.TAG,
                    "determineModeEnsuredWhich: screen is missing. which="
                            + which
                            + ", screen="
                            + screen);
            return which | 4;
        }
    }

    public SemWallpaperResourcesInfo(Context context) {
        try {
            this.mResPkgContext = context.createPackageContext(WALLPAPER_PACKAGE, 0);
            if (this.mResPkgContext != null) {
                this.mResource =
                        new ResourceParser(this.mResPkgContext).parseJson(WALLPAPER_PACKAGE);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "init: e=" + e);
        }
        if (this.mResource == null) {
            this.mResource = new ResourceData();
        }
        this.mContext = context.getApplicationContext();
        if (this.mContext == null) {
            String pkgName = context.getPackageName();
            Log.w(
                    TAG,
                    "init: failed to get app context. context="
                            + context
                            + NavigationBarInflaterView.KEY_CODE_START
                            + pkgName
                            + "), resPkgContext="
                            + this.mResPkgContext);
            try {
                this.mContext = context.createPackageContext(pkgName, 0);
            } catch (PackageManager.NameNotFoundException e2) {
            }
            if (this.mContext == null) {
                this.mContext = this.mResPkgContext != null ? this.mResPkgContext : context;
            }
            Log.w(TAG, "init: mContext=" + this.mContext);
        }
    }

    public int getDefaultWallpaperType(int which, String colorCode) {
        int modeEnsuredWhich = getModeEnsuredWhich(which);
        int type = this.mResource.getDefaultWallpaperType(modeEnsuredWhich, colorCode);
        Log.i(TAG, "getDefaultWallpaperType: which = " + which + " , type = " + type);
        return type;
    }

    public InputStream getDefaultImageWallpaper(int which) {
        if (this.mResPkgContext == null) {
            Log.i(TAG, "getDefaultImageWallpaper: the resource context is not available");
            return null;
        }
        String resourceName = getDefaultImageFileName(which);
        Log.i(TAG, "getDefaultImageWallpaper: resourceName = " + resourceName);
        if (TextUtils.isEmpty(resourceName)) {
            return null;
        }
        int wallpaperResId =
                this.mResPkgContext
                        .getResources()
                        .getIdentifier(
                                resourceName.substring(0, resourceName.lastIndexOf(46)),
                                "drawable",
                                WALLPAPER_PACKAGE);
        Log.i(TAG, "getDefaultImageWallpaper: wallpaperResId = " + wallpaperResId);
        if (wallpaperResId <= 0) {
            return null;
        }
        InputStream inputStream =
                this.mResPkgContext.getResources().openRawResource(wallpaperResId);
        return inputStream;
    }

    public String getDefaultImageFileName(int which) {
        Item defaultResource = getDefaultWallpaperItem(which, 0);
        if (defaultResource == null) {
            return null;
        }
        return defaultResource.fileName;
    }

    public String getDefaultVideoWallpaperFileName(int which) {
        String videoFileName = null;
        Item defaultResource = getDefaultWallpaperItem(which, 8);
        if (defaultResource != null) {
            videoFileName = defaultResource.fileName;
        }
        Log.i(TAG, "getDefaultVideoWallpaperFileName: " + videoFileName);
        return videoFileName;
    }

    public int getDefaultVideoFrameInfo(String fileName) {
        Item videoItem = this.mResource.getVideoItemByFilename(fileName);
        if (videoItem == null) {
            return 0;
        }
        return videoItem.videoFrameInfo;
    }

    public boolean isBlackFirstFrame(String fileName) {
        Item videoItem = this.mResource.getVideoItemByFilename(fileName);
        if (videoItem == null) {
            return false;
        }
        return videoItem.isBlackFirstFrame;
    }

    public ComponentName getDefaultLiveWallpaperComponentName(int which) {
        Item item = getDefaultWallpaperItem(which, 7);
        if (item == null) {
            Log.w(TAG, "getDefaultLiveWallpaperComponentName: no matched item" + which);
            return null;
        }
        if (item.typeParams == null
                || TextUtils.isEmpty(item.typeParams.mServicePkgName)
                || TextUtils.isEmpty(item.typeParams.mServiceClassName)) {
            Log.w(
                    TAG,
                    "getDefaultLiveWallpaperComponentName: empty component name. which=" + which);
            return null;
        }
        return new ComponentName(
                item.typeParams.mServicePkgName, item.typeParams.mServiceClassName);
    }

    public Bundle getDefaultLiveWallpaperExtras(int which) {
        Item item = getDefaultWallpaperItem(which, 7);
        if (item == null) {
            Log.w(TAG, "getDefaultLiveWallpaperExtras: no matched item. which=" + which);
            return null;
        }
        TypeParams typeParams = item.typeParams;
        if (typeParams == null || typeParams.mExtras.isEmpty()) {
            return null;
        }
        return new Bundle(typeParams.mExtras);
    }

    public String getDefaultMultipackStyle(int which) {
        return this.mResource.getDefaultMultipackStyle(which);
    }

    public boolean isKnownColorCode(String colorCode) {
        String colorCode2 = getRefinedColorCode(colorCode);
        boolean isKnown = this.mResource.isKnownColorCode(colorCode2);
        Log.d(TAG, "isKnownColorCode: code = " + colorCode2 + ", isKnown = " + isKnown);
        return isKnown;
    }

    public boolean isSupportCMF() {
        return this.mResource.isSupportCMF();
    }

    public boolean isDefaultVideo(int which) {
        String deviceColorCode = getDeviceColorCode();
        return getDefaultWallpaperType(which, deviceColorCode) == 8;
    }

    public boolean isDefaultMultipack(int which) {
        String deviceColorCode = getDeviceColorCode();
        return getDefaultWallpaperType(which, deviceColorCode) == 3;
    }

    public boolean isDefaultWallpaperPaired(int modeOrWhich, int wallpaperType) {
        int mode = WhichChecker.getMode(modeOrWhich);
        Item item = getDefaultWallpaperItem(mode | 1, wallpaperType);
        if (item == null) {
            return false;
        }
        return WhichChecker.isSystemAndLock(item.which);
    }

    public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        pw.println("[Default wallpaper type from json]");
        this.mResource.dump(fd, pw, args);
    }

    private Item getDefaultWallpaperItem(int which, int wallpaperType) {
        int which2 = getModeEnsuredWhich(which);
        String deviceColorCode = getDeviceColorCode();
        return this.mResource.getDefaultWallpaperItem(which2, deviceColorCode, wallpaperType);
    }

    private int getModeEnsuredWhich(int which) {
        if (WhichChecker.isModeAbsent(which)) {
            Log.w(
                    TAG,
                    "getModeEnsuredWhich: mode is missing. which=" + which,
                    new IllegalArgumentException());
            return which | 4;
        }
        return which;
    }

    private String getDeviceColorCode() {
        String colorCode = WallpaperManager.getDeviceColor(this.mContext);
        if (colorCode == null) {
            return null;
        }
        return getRefinedColorCode(colorCode);
    }

    private String getRefinedColorCode(String colorCode) {
        if (colorCode == null) {
            return null;
        }
        return colorCode.toLowerCase().trim();
    }
}
