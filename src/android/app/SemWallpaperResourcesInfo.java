package android.app;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.samsung.android.wallpaper.Rune;
import com.samsung.android.wallpaper.utils.SemWallpaperProperties;
import com.samsung.android.wallpaper.utils.WhichChecker;
import java.io.BufferedReader;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class SemWallpaperResourcesInfo {
    protected static final boolean DEBUG = !Rune.isShipBuild();
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

        Item() {
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("type=" + this.type);
            sb.append(", which=" + this.which);
            sb.append(", index=" + this.index);
            sb.append(", default=" + this.isDefault);
            sb.append(", file=" + this.fileName);
            int i = this.type;
            if (i == 7) {
                sb.append(", extra=" + this.typeParams);
            } else if (i == 8) {
                sb.append(", frame=" + this.videoFrameInfo);
                sb.append(", isBlackFrame=" + this.isBlackFirstFrame);
            }
            sb.append(", bespoke=" + this.isBespoke);
            sb.append(", cmf=");
            Iterator<String> it = this.cmfInfo.iterator();
            while (it.hasNext()) {
                sb.append(it.next() + " ");
            }
            return sb.toString();
        }
    }

    static class TypeParams {
        public Bundle mExtras = new Bundle();
        public String mServiceClassName;
        public String mServicePkgName;

        TypeParams() {
        }

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

        private boolean isPhone(int i) {
            return ((i & 8) == 8 || (i & 16) == 16) ? false : true;
        }

        ResourceData() {
        }

        public void addItem(Item item) {
            ArrayList<Item> arrayList = this.mItemsMap.get(Integer.valueOf(item.type));
            if (arrayList == null) {
                arrayList = new ArrayList<>();
                this.mItemsMap.put(Integer.valueOf(item.type), arrayList);
            }
            arrayList.add(item);
        }

        public void setDefaultWallpaperType(int i, int i2) {
            this.mDefaultTypeMap.put(Integer.valueOf(i), Integer.valueOf(i2));
        }

        public void setDefaultMultipackStyle(int i, String str) {
            this.mDefaultMultipackStyle.put(Integer.valueOf(i), str);
        }

        public void addKnownColor(String str) {
            this.mKnownColorCode.add(str);
        }

        public void addKnownColors(ArrayList<String> arrayList) {
            if (arrayList == null) {
                return;
            }
            Iterator<String> it = arrayList.iterator();
            while (it.hasNext()) {
                addKnownColor(it.next());
            }
        }

        public void addBespokeCode(String str) {
            this.mBespokeCode.add(str);
        }

        public boolean isKnownColorCode(String str) {
            if (TextUtils.isEmpty(str)) {
                return false;
            }
            return this.mKnownColorCode.contains(str);
        }

        public boolean isSupportCMF() {
            return this.mIsSupportCMF;
        }

        public int getDefaultWallpaperType(int i, String str) {
            Item firstExactlyMatchedItemFromAllTypes = (this.mIsSupportCMF && isBespokeCode(str)) ? getFirstExactlyMatchedItemFromAllTypes(i, str) : null;
            return firstExactlyMatchedItemFromAllTypes != null ? firstExactlyMatchedItemFromAllTypes.type : getDefaultWallpaperType(i);
        }

        private int getDefaultWallpaperType(int i) {
            if (WhichChecker.isModeAbsent(i)) {
                Log.w(SemWallpaperResourcesInfo.TAG, "getDefaultWallpaperType: mode is missing. which=" + i, new IllegalArgumentException());
            }
            return this.mDefaultTypeMap.getOrDefault(Integer.valueOf(i), 0).intValue();
        }

        public String getDefaultMultipackStyle(int i) {
            if (isPhone(i)) {
                i |= 4;
            }
            return this.mDefaultMultipackStyle.get(Integer.valueOf(i));
        }

        public Item getDefaultWallpaperItem(int i, String str, int i2) {
            if (WhichChecker.isModeAbsent(i)) {
                Log.w(SemWallpaperResourcesInfo.TAG, "getDefaultWallpaperItem: mode is missing. which=" + i, new IllegalArgumentException());
            }
            ArrayList<Item> arrayList = this.mItemsMap.get(Integer.valueOf(i2));
            Item itemChooseDefaultWallpaperItem = (arrayList == null || arrayList.isEmpty()) ? null : chooseDefaultWallpaperItem(i, str, arrayList);
            if (itemChooseDefaultWallpaperItem == null) {
                Log.w(SemWallpaperResourcesInfo.TAG, "getDefaultWallpaperItem: nothing matched. which=" + i);
                return null;
            }
            if (WhichChecker.isSystemAndLock(itemChooseDefaultWallpaperItem.which) && WhichChecker.isLock(i)) {
                Log.i(SemWallpaperResourcesInfo.TAG, "getDefaultWallpaperItem: paired lock. which=" + i + ", matched=[" + itemChooseDefaultWallpaperItem + NavigationBarInflaterView.SIZE_MOD_END);
                return null;
            }
            if (SemWallpaperResourcesInfo.DEBUG) {
                Log.i(SemWallpaperResourcesInfo.TAG, "getDefaultWallpaperItem: which=" + i + ", colorCode=" + str + ", matched=[" + itemChooseDefaultWallpaperItem + NavigationBarInflaterView.SIZE_MOD_END);
            }
            return itemChooseDefaultWallpaperItem;
        }

        private Item chooseDefaultWallpaperItem(int i, String str, ArrayList<Item> arrayList) {
            Item firstExactlyMatchedItem;
            if (arrayList.isEmpty()) {
                Log.w(SemWallpaperResourcesInfo.TAG, "getDefaultWallpaperItem: empty item array");
                return null;
            }
            if (this.mIsSupportCMF && !TextUtils.isEmpty(str) && (firstExactlyMatchedItem = getFirstExactlyMatchedItem(i, str, arrayList)) != null) {
                if (SemWallpaperResourcesInfo.DEBUG) {
                    Log.i(SemWallpaperResourcesInfo.TAG, "getDefaultWallpaperItem: which & color matched. item=" + firstExactlyMatchedItem);
                }
                return firstExactlyMatchedItem;
            }
            boolean zIsBespokeCode = isBespokeCode(str);
            Iterator<Item> it = arrayList.iterator();
            Item item = null;
            while (it.hasNext()) {
                Item next = it.next();
                if ((next.which & i) == i && (zIsBespokeCode || !next.isBespoke)) {
                    if (item == null) {
                        item = next;
                    }
                    if (next.isDefault) {
                        if (SemWallpaperResourcesInfo.DEBUG) {
                            Log.i(SemWallpaperResourcesInfo.TAG, "getDefaultWallpaperItem: which & default matched. item=" + next);
                        }
                        return next;
                    }
                }
            }
            if (item != null) {
                if (SemWallpaperResourcesInfo.DEBUG) {
                    Log.i(SemWallpaperResourcesInfo.TAG, "getDefaultWallpaperItem: which matched. use first item. item=" + item);
                }
                return item;
            }
            Iterator<Item> it2 = arrayList.iterator();
            Item item2 = null;
            while (it2.hasNext()) {
                Item next2 = it2.next();
                if (zIsBespokeCode || !next2.isBespoke) {
                    if (item2 == null) {
                        item2 = next2;
                    }
                    if (next2.isDefault) {
                        if (SemWallpaperResourcesInfo.DEBUG) {
                            Log.i(SemWallpaperResourcesInfo.TAG, "getDefaultWallpaperItem: default matched. item=" + next2);
                        }
                        return next2;
                    }
                }
            }
            if (item2 != null) {
                if (SemWallpaperResourcesInfo.DEBUG) {
                    Log.i(SemWallpaperResourcesInfo.TAG, "getDefaultWallpaperItem: type matched. use first item. item=" + item2);
                }
                return item2;
            }
            Log.w(SemWallpaperResourcesInfo.TAG, "getDefaultWallpaperItem: could not find matched item. which=" + i + ", deviceColor=" + str);
            return null;
        }

        private Item getFirstExactlyMatchedItemFromAllTypes(int i, String str) {
            ArrayList arrayList = new ArrayList(this.mItemsMap.keySet());
            int iIndexOf = arrayList.indexOf(8);
            if (iIndexOf >= 0) {
                arrayList.remove(iIndexOf);
                arrayList.add(0, 8);
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                Integer num = (Integer) it.next();
                num.intValue();
                Item firstExactlyMatchedItem = getFirstExactlyMatchedItem(i, str, this.mItemsMap.get(num));
                if (firstExactlyMatchedItem != null) {
                    return firstExactlyMatchedItem;
                }
            }
            return null;
        }

        private Item getFirstExactlyMatchedItem(int i, String str, ArrayList<Item> arrayList) {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            Iterator<Item> it = arrayList.iterator();
            while (it.hasNext()) {
                Item next = it.next();
                if (isDefaultResource(next, i, str)) {
                    return next;
                }
            }
            return null;
        }

        public Item getVideoItemByFilename(String str) {
            if (TextUtils.isEmpty(str)) {
                Log.e(SemWallpaperResourcesInfo.TAG, "getVideoItemByFilename: fileName is null");
                return null;
            }
            ArrayList<Item> arrayList = this.mItemsMap.get(8);
            if (arrayList == null || arrayList.isEmpty()) {
                Log.i(SemWallpaperResourcesInfo.TAG, "getVideoItemByFilename: video item array is empty");
                return null;
            }
            Iterator<Item> it = arrayList.iterator();
            while (it.hasNext()) {
                Item next = it.next();
                if (str.equals(next.fileName)) {
                    return next;
                }
            }
            return null;
        }

        public void finalizeInternalState() {
            sortAscending();
            this.mIsSupportCMF = determineSupportsCmf();
        }

        public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            HashMap<Integer, Integer> map = this.mDefaultTypeMap;
            if (map == null || map.size() <= 0) {
                return;
            }
            for (Map.Entry<Integer, Integer> entry : this.mDefaultTypeMap.entrySet()) {
                printWriter.println(String.format("\t[%5d: %5d]", entry.getKey(), entry.getValue()));
            }
        }

        private void sortAscending() {
            Comparator<Item> comparator = new Comparator<Item>(this) { // from class: android.app.SemWallpaperResourcesInfo.ResourceData.1
                @Override // java.util.Comparator
                public int compare(Item item, Item item2) {
                    return item.index.compareTo(item2.index);
                }
            };
            for (ArrayList<Item> arrayList : this.mItemsMap.values()) {
                if (arrayList.size() > 1) {
                    Collections.sort(arrayList, comparator);
                }
            }
        }

        private boolean determineSupportsCmf() {
            Iterator<ArrayList<Item>> it = this.mItemsMap.values().iterator();
            while (it.hasNext()) {
                Iterator<Item> it2 = it.next().iterator();
                while (it2.hasNext()) {
                    if (isValidCode(it2.next().cmfInfo)) {
                        return true;
                    }
                }
            }
            return false;
        }

        private boolean isBespokeCode(String str) {
            if (TextUtils.isEmpty(str)) {
                return false;
            }
            Iterator<String> it = this.mBespokeCode.iterator();
            while (it.hasNext()) {
                if (it.next().equals(str)) {
                    return true;
                }
            }
            return false;
        }

        private boolean isValidCode(ArrayList<String> arrayList) {
            Iterator<String> it = arrayList.iterator();
            while (it.hasNext()) {
                if (!TextUtils.isEmpty(it.next())) {
                    return true;
                }
            }
            return false;
        }

        private boolean isDefaultResource(Item item, int i, String str) {
            if ((item.which & i) != i) {
                return false;
            }
            Iterator<String> it = item.cmfInfo.iterator();
            while (it.hasNext()) {
                String next = it.next();
                if (!TextUtils.isEmpty(next) && next.equals(str)) {
                    return true;
                }
            }
            return false;
        }
    }

    static class ResourceParser {
        private static final int DEX_SCREEN = 10;
        private static final int MAIN_SCREEN = 0;
        private static final int SUB_SCREEN = 1;
        private static final int WALLPAPER_TYPE_PRELOADED_LIVE = 10;
        private Context mContext;

        public ResourceParser(Context context) {
            this.mContext = context;
        }

        public ResourceData parseJson(String str) throws IOException {
            ResourceData resourceData = new ResourceData();
            int identifier = this.mContext.getResources().getIdentifier("resources_info", "raw", str);
            StringWriter stringWriter = new StringWriter();
            char[] cArr = new char[1024];
            try {
                InputStream inputStreamOpenRawResource = this.mContext.getResources().openRawResource(identifier);
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStreamOpenRawResource, "UTF-8"));
                    while (true) {
                        int i = bufferedReader.read(cArr);
                        if (i == -1) {
                            break;
                        }
                        stringWriter.write(cArr, 0, i);
                    }
                    if (inputStreamOpenRawResource != null) {
                        inputStreamOpenRawResource.close();
                    }
                    String string = stringWriter.toString();
                    try {
                        JSONObject jSONObject = new JSONObject(string);
                        parseWallpaperList(jSONObject, "phone", resourceData);
                        parseWallpaperList(jSONObject, "dex", resourceData);
                        parseTypes(jSONObject, resourceData);
                        parseBespoke(jSONObject, resourceData);
                        resourceData.finalizeInternalState();
                        return resourceData;
                    } catch (JSONException e) {
                        Log.e(SemWallpaperResourcesInfo.TAG, "parseJson: e=" + e, e);
                        Log.e(SemWallpaperResourcesInfo.TAG, "parseJson: " + string);
                        return new ResourceData();
                    }
                } finally {
                }
            } catch (Exception e2) {
                Log.i(SemWallpaperResourcesInfo.TAG, "parseJson: e=" + e2, e2);
                return resourceData;
            }
        }

        private void parseWallpaperList(JSONObject jSONObject, String str, ResourceData resourceData) throws JSONException {
            int i = -1;
            try {
                JSONArray jSONArray = jSONObject.getJSONArray(str);
                int length = jSONArray.length();
                int iIntValue = -1;
                for (int i2 = 0; i2 < length; i2++) {
                    try {
                        Item item = new Item();
                        JSONObject jSONObject2 = jSONArray.getJSONObject(i2);
                        item.isDefault = jSONObject2.getBoolean("isDefault");
                        item.index = Integer.valueOf(jSONObject2.getInt("index"));
                        item.type = jSONObject2.getInt("type");
                        if (item.type == 10) {
                            item.type = 7;
                        }
                        item.which = determineModeEnsuredWhich(jSONObject2.optInt("which", -1), jSONObject2.optInt("screen", -1));
                        item.isBespoke = jSONObject2.optBoolean("isBespoke", false);
                        item.fileName = jSONObject2.optString("filename", null);
                        item.videoFrameInfo = jSONObject2.optInt("frame_no", -1);
                        item.isBlackFirstFrame = jSONObject2.optBoolean("isBlackFirstFrame", false);
                        try {
                            parseCmfInfo(jSONObject2.getJSONArray("cmf_info"), item);
                        } catch (JSONException e) {
                            Log.w(SemWallpaperResourcesInfo.TAG, "parseWallpaperListTag: " + e.getMessage());
                        }
                        resourceData.addKnownColors(item.cmfInfo);
                        parseTypeParams(jSONObject2.optJSONObject("type_params"), item);
                        resourceData.addItem(item);
                        iIntValue = item.index.intValue();
                    } catch (JSONException e2) {
                        e = e2;
                        i = iIntValue;
                        Log.e(SemWallpaperResourcesInfo.TAG, "parseWallpaperListTag: last parse success item : section=" + str + ", index=" + i);
                        throw e;
                    }
                }
            } catch (JSONException e3) {
                e = e3;
            }
        }

        private void parseTypes(JSONObject jSONObject, ResourceData resourceData) throws JSONException {
            JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("types");
            if (jSONArrayOptJSONArray == null) {
                return;
            }
            for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
                JSONObject jSONObject2 = jSONArrayOptJSONArray.getJSONObject(i);
                int iDetermineModeEnsuredWhich = determineModeEnsuredWhich(jSONObject2.getInt("which"), jSONObject2.getInt("screen"));
                int i2 = jSONObject2.getInt("type");
                if (i2 == 10) {
                    i2 = 7;
                }
                resourceData.setDefaultWallpaperType(iDetermineModeEnsuredWhich, i2);
                if (i2 == 3) {
                    resourceData.setDefaultMultipackStyle(iDetermineModeEnsuredWhich, jSONObject2.getString("style"));
                }
            }
        }

        private static void parseBespoke(JSONObject jSONObject, ResourceData resourceData) throws JSONException {
            JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("bespoke");
            if (jSONArrayOptJSONArray != null) {
                for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
                    String lowerCase = ((String) jSONArrayOptJSONArray.get(i)).toLowerCase();
                    if (!TextUtils.isEmpty(lowerCase)) {
                        resourceData.addBespokeCode(lowerCase);
                        resourceData.addKnownColor(lowerCase);
                    }
                }
            }
        }

        private void parseTypeParams(JSONObject jSONObject, Item item) throws JSONException {
            if (jSONObject == null || item == null) {
                return;
            }
            TypeParams typeParams = item.typeParams;
            typeParams.mServicePkgName = jSONObject.optString("service_package_name", null);
            typeParams.mServiceClassName = jSONObject.optString("service_class_name", null);
            String strOptString = jSONObject.optString("content_type", null);
            if (!TextUtils.isEmpty(strOptString)) {
                typeParams.mExtras.putString("contentType", strOptString);
            }
            JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("service_settings");
            if (jSONObjectOptJSONObject != null) {
                typeParams.mExtras.putBundle(SemWallpaperProperties.KEY_SERVICE_SETTINGS, convertJsonObjectToBundle(jSONObjectOptJSONObject));
            }
        }

        private void parseCmfInfo(JSONArray jSONArray, Item item) {
            if (jSONArray == null || item == null) {
                return;
            }
            int length = jSONArray.length();
            for (int i = 0; i < length; i++) {
                String refinedColorCode = getRefinedColorCode(jSONArray.optString(i, null));
                if (TextUtils.isEmpty(refinedColorCode)) {
                    Log.w(SemWallpaperResourcesInfo.TAG, "parseCmfInfo: empty cmf detected. wp item index=" + item.index);
                } else {
                    item.cmfInfo.add(refinedColorCode);
                }
            }
        }

        private Bundle convertJsonObjectToBundle(JSONObject jSONObject) throws JSONException {
            Bundle bundle = new Bundle();
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String string = itKeys.next().toString();
                try {
                    Object obj = jSONObject.get(string);
                    if (obj instanceof String) {
                        bundle.putString(string, (String) obj);
                    } else if (obj instanceof Integer) {
                        bundle.putInt(string, ((Integer) obj).intValue());
                    } else if (obj instanceof Boolean) {
                        bundle.putBoolean(string, ((Boolean) obj).booleanValue());
                    } else if (obj instanceof Double) {
                        bundle.putDouble(string, ((Double) obj).doubleValue());
                    } else if (obj instanceof JSONObject) {
                        bundle.putBundle(string, convertJsonObjectToBundle((JSONObject) obj));
                    }
                } catch (JSONException unused) {
                    Log.e(SemWallpaperResourcesInfo.TAG, "convertJsonObjectToBundle: failed to get value. key=" + string);
                }
            }
            return bundle;
        }

        private String getRefinedColorCode(String str) {
            if (str == null) {
                return null;
            }
            return str.toLowerCase().trim();
        }

        private int determineModeEnsuredWhich(int i, int i2) {
            if (!WhichChecker.isModeAbsent(i)) {
                return i;
            }
            if (i2 == 0) {
                return WhichChecker.getType(i) | 4;
            }
            if (i2 == 1) {
                return WhichChecker.getType(i) | 16;
            }
            if (i2 == 10) {
                return WhichChecker.getType(i) | 8;
            }
            Log.w(SemWallpaperResourcesInfo.TAG, "determineModeEnsuredWhich: screen is missing. which=" + i + ", screen=" + i2);
            return i | 4;
        }
    }

    public SemWallpaperResourcesInfo(Context context) {
        try {
            Context contextCreatePackageContext = context.createPackageContext(WALLPAPER_PACKAGE, 0);
            this.mResPkgContext = contextCreatePackageContext;
            if (contextCreatePackageContext != null) {
                this.mResource = new ResourceParser(this.mResPkgContext).parseJson(WALLPAPER_PACKAGE);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "init: e=" + e);
        }
        if (this.mResource == null) {
            this.mResource = new ResourceData();
        }
        Context applicationContext = context.getApplicationContext();
        this.mContext = applicationContext;
        if (applicationContext == null) {
            String packageName = context.getPackageName();
            Log.w(TAG, "init: failed to get app context. context=" + context + NavigationBarInflaterView.KEY_CODE_START + packageName + "), resPkgContext=" + this.mResPkgContext);
            try {
                this.mContext = context.createPackageContext(packageName, 0);
            } catch (PackageManager.NameNotFoundException unused) {
            }
            if (this.mContext == null) {
                Context context2 = this.mResPkgContext;
                this.mContext = context2 != null ? context2 : context;
            }
            Log.w(TAG, "init: mContext=" + this.mContext);
        }
    }

    public int getDefaultWallpaperType(int i, String str) {
        int defaultWallpaperType = this.mResource.getDefaultWallpaperType(getModeEnsuredWhich(i), str);
        Log.i(TAG, "getDefaultWallpaperType: which = " + i + " , type = " + defaultWallpaperType);
        return defaultWallpaperType;
    }

    public InputStream getDefaultImageWallpaper(int i) {
        if (this.mResPkgContext == null) {
            Log.i(TAG, "getDefaultImageWallpaper: the resource context is not available");
            return null;
        }
        String defaultImageFileName = getDefaultImageFileName(i);
        Log.i(TAG, "getDefaultImageWallpaper: resourceName = " + defaultImageFileName);
        if (TextUtils.isEmpty(defaultImageFileName)) {
            return null;
        }
        int identifier = this.mResPkgContext.getResources().getIdentifier(defaultImageFileName.substring(0, defaultImageFileName.lastIndexOf(46)), "drawable", WALLPAPER_PACKAGE);
        Log.i(TAG, "getDefaultImageWallpaper: wallpaperResId = " + identifier);
        if (identifier <= 0) {
            return null;
        }
        return this.mResPkgContext.getResources().openRawResource(identifier);
    }

    public String getDefaultImageFileName(int i) {
        Item defaultWallpaperItem = getDefaultWallpaperItem(i, 0, true);
        if (defaultWallpaperItem == null) {
            return null;
        }
        return defaultWallpaperItem.fileName;
    }

    public String getDefaultVideoWallpaperFileName(int i) {
        Item defaultWallpaperItem = getDefaultWallpaperItem(i, 8, true);
        String str = defaultWallpaperItem != null ? defaultWallpaperItem.fileName : null;
        Log.i(TAG, "getDefaultVideoWallpaperFileName: " + str);
        return str;
    }

    public int getDefaultVideoFrameInfo(String str) {
        Item videoItemByFilename = this.mResource.getVideoItemByFilename(str);
        if (videoItemByFilename == null) {
            return 0;
        }
        return videoItemByFilename.videoFrameInfo;
    }

    public boolean isBlackFirstFrame(String str) {
        Item videoItemByFilename = this.mResource.getVideoItemByFilename(str);
        if (videoItemByFilename == null) {
            return false;
        }
        return videoItemByFilename.isBlackFirstFrame;
    }

    public ComponentName getDefaultLiveWallpaperComponentName(int i) {
        Item defaultWallpaperItem = getDefaultWallpaperItem(i, 7, false);
        if (defaultWallpaperItem == null) {
            Log.w(TAG, "getDefaultLiveWallpaperComponentName: no matched item" + i);
            return null;
        }
        if (defaultWallpaperItem.typeParams == null || TextUtils.isEmpty(defaultWallpaperItem.typeParams.mServicePkgName) || TextUtils.isEmpty(defaultWallpaperItem.typeParams.mServiceClassName)) {
            Log.w(TAG, "getDefaultLiveWallpaperComponentName: empty component name. which=" + i);
            return null;
        }
        return new ComponentName(defaultWallpaperItem.typeParams.mServicePkgName, defaultWallpaperItem.typeParams.mServiceClassName);
    }

    public Bundle getDefaultLiveWallpaperExtras(int i) {
        Item defaultWallpaperItem = getDefaultWallpaperItem(i, 7, false);
        if (defaultWallpaperItem == null) {
            Log.w(TAG, "getDefaultLiveWallpaperExtras: no matched item. which=" + i);
            return null;
        }
        TypeParams typeParams = defaultWallpaperItem.typeParams;
        if (typeParams == null || typeParams.mExtras.isEmpty()) {
            return null;
        }
        return new Bundle(typeParams.mExtras);
    }

    public String getDefaultMultipackStyle(int i) {
        return this.mResource.getDefaultMultipackStyle(i);
    }

    public boolean isKnownColorCode(String str) {
        String refinedColorCode = getRefinedColorCode(str);
        boolean zIsKnownColorCode = this.mResource.isKnownColorCode(refinedColorCode);
        Log.d(TAG, "isKnownColorCode: code = " + refinedColorCode + ", isKnown = " + zIsKnownColorCode);
        return zIsKnownColorCode;
    }

    public boolean isSupportCMF() {
        return this.mResource.isSupportCMF();
    }

    public boolean isDefaultVideo(int i) {
        return getDefaultWallpaperType(i, getDeviceColorCode()) == 8;
    }

    public boolean isDefaultMultipack(int i) {
        return getDefaultWallpaperType(i, getDeviceColorCode()) == 3;
    }

    public boolean isDefaultWallpaperPaired(int i, int i2) {
        Item defaultWallpaperItem = getDefaultWallpaperItem(WhichChecker.getMode(i) | 1, i2, false);
        if (defaultWallpaperItem == null) {
            return false;
        }
        return WhichChecker.isSystemAndLock(defaultWallpaperItem.which);
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println("[Default wallpaper type from json]");
        this.mResource.dump(fileDescriptor, printWriter, strArr);
    }

    private Item getDefaultWallpaperItem(int i, int i2, boolean z) {
        int modeEnsuredWhich = getModeEnsuredWhich(i);
        String deviceColorCode = getDeviceColorCode();
        Item defaultWallpaperItem = this.mResource.getDefaultWallpaperItem(modeEnsuredWhich, deviceColorCode, i2);
        if (defaultWallpaperItem == null && z && WhichChecker.isLock(modeEnsuredWhich)) {
            Item defaultWallpaperItem2 = this.mResource.getDefaultWallpaperItem(WhichChecker.getMode(modeEnsuredWhich) | 1, deviceColorCode, i2);
            if (defaultWallpaperItem2 != null && WhichChecker.isSystemAndLock(defaultWallpaperItem2.which)) {
                return defaultWallpaperItem2;
            }
        }
        return defaultWallpaperItem;
    }

    private int getModeEnsuredWhich(int i) {
        if (!WhichChecker.isModeAbsent(i)) {
            return i;
        }
        Log.w(TAG, "getModeEnsuredWhich: mode is missing. which=" + i, new IllegalArgumentException());
        return i | 4;
    }

    private String getDeviceColorCode() {
        String deviceColor = WallpaperManager.getDeviceColor(this.mContext);
        if (deviceColor == null) {
            return null;
        }
        return getRefinedColorCode(deviceColor);
    }

    private String getRefinedColorCode(String str) {
        if (str == null) {
            return null;
        }
        return str.toLowerCase().trim();
    }
}
