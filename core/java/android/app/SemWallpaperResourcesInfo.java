package android.app;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.samsung.android.wallpaper.utils.SemWallpaperProperties;
import com.samsung.android.wallpaper.utils.WhichChecker;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class SemWallpaperResourcesInfo {
  private static final int MAIN_SCREEN = 0;
  private static final int SUB_SCREEN = 1;
  private static final String TAG = "WallpaperResourcesInfo";
  private static final String WALLPAPER_PACKAGE = "com.samsung.android.wallpaper.res";
  private static final int WALLPAPER_TYPE_PRELOADED_LIVE = 10;
  private String mColorCode;
  private Context mContext;
  private boolean mIsSupportCMF;
  private int mVersion = 1;
  private final ArrayList<Item> mImageItems = new ArrayList<>();
  private final ArrayList<Item> mVideoItems = new ArrayList<>();
  private final ArrayList<Item> mLiveItems = new ArrayList<>();
  private final ArrayList<String> mBespokeCode = new ArrayList<>();
  private final HashMap<Integer, Integer> mDefaultTypeMap = new HashMap<>();
  private final HashMap<Integer, String> mDefaultMultipackStyle = new HashMap<>();
  private final HashSet<String> mKnownColorCode = new HashSet<>();

  private static class TypeParams {
    public Bundle mExtras;
    public String mServiceClassName;
    public String mServicePkgName;

    private TypeParams() {
      this.mExtras = new Bundle();
    }
  }

  public SemWallpaperResourcesInfo(Context context) {
    this.mIsSupportCMF = false;
    try {
      Context createPackageContext = context.createPackageContext(WALLPAPER_PACKAGE, 0);
      this.mContext = createPackageContext;
      if (createPackageContext != null) {
        parseJson();
        ascendingSort();
      }
      this.mIsSupportCMF = checkCMF();
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
  }

  public int getDefaultWallpaperType(int which, String colorCode) {
    int screen = (which & 16) == 16 ? 1 : 0;
    if (!TextUtils.isEmpty(colorCode) && isBespokeCode(colorCode) && this.mBespokeCode.size() > 0) {
      Log.m98i(TAG, "getDefaultWallpaperType: colorCode = " + colorCode + " , screen = " + screen);
      try {
        Iterator<Item> it = this.mVideoItems.iterator();
        while (it.hasNext()) {
          Item item = it.next();
          if (isDefaultResource(item, which, screen, colorCode)) {
            Log.m98i(
                TAG,
                "getDefaultWallpaperType by color code: "
                    + item.fileName
                    + ", "
                    + item.index
                    + " , "
                    + item.cmfInfo);
            return 8;
          }
        }
        Iterator<Item> it2 = this.mImageItems.iterator();
        while (it2.hasNext()) {
          Item item2 = it2.next();
          if (isDefaultResource(item2, which, screen, colorCode)) {
            Log.m98i(
                TAG,
                "getDefaultWallpaperType by color code: "
                    + item2.fileName
                    + ", "
                    + item2.index
                    + " , "
                    + item2.cmfInfo);
            return 0;
          }
        }
        Iterator<Item> it3 = this.mLiveItems.iterator();
        while (it3.hasNext()) {
          Item item3 = it3.next();
          if (isDefaultResource(item3, which, screen, colorCode)) {
            Log.m98i(
                TAG,
                "getDefaultWallpaperType by color code: "
                    + item3.fileName
                    + ", "
                    + item3.index
                    + " , "
                    + item3.cmfInfo);
            return 7;
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    int type = getDefaultWallpaperType(which);
    if (type == 10) {
      type = 7;
    }
    Log.m98i(TAG, "getDefaultWallpaperType: which = " + which + " , type = " + type);
    return type;
  }

  private boolean isBespokeCode(String colorCode) {
    if (this.mBespokeCode.size() <= 0) {
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

  public InputStream getDefaultImageWallpaper(int which) {
    ArrayList<Item> arrayList = this.mImageItems;
    if (arrayList == null || arrayList.size() == 0) {
      Log.m98i(TAG, "getDefaultWallpaper: mItem is null");
      return null;
    }
    String resourceName = getDefaultImageFileName(which);
    Log.m98i(TAG, "getDefaultImageWallpaper: resourceName = " + resourceName);
    if (resourceName == null || resourceName.isEmpty()) {
      return null;
    }
    int wallpaperResId =
        this.mContext
            .getResources()
            .getIdentifier(
                resourceName.substring(0, resourceName.lastIndexOf(46)),
                "drawable",
                WALLPAPER_PACKAGE);
    Log.m98i(TAG, "getDefaultImageWallpaper: wallpaperResId = " + wallpaperResId);
    if (wallpaperResId <= 0) {
      return null;
    }
    InputStream inputStream = this.mContext.getResources().openRawResource(wallpaperResId);
    return inputStream;
  }

  public String getDefaultImageFileName(int which) {
    Item defaultResource = getDefaultImageResource(which);
    if (defaultResource == null) {
      if (!WhichChecker.isLock(which)) {
        return null;
      }
      Log.m102w(
          TAG,
          "getDefaultImageFileName: Failed to get default image resource for which["
              + which
              + "]. Getting default for system.");
      defaultResource = getDefaultImageResource(WhichChecker.getMode(which) | 1);
      if (defaultResource == null) {
        return null;
      }
    }
    String resourceName = defaultResource.fileName;
    return resourceName;
  }

  /* JADX WARN: Code restructure failed: missing block: B:34:0x006b, code lost:

     android.util.Log.m98i(android.app.SemWallpaperResourcesInfo.TAG, "getDefaultItem by color code: " + r7.fileName + ", " + r7.index + " , " + r7.cmfInfo);
  */
  /* JADX WARN: Code restructure failed: missing block: B:35:0x009d, code lost:

     r0 = r7;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public String getDefaultVideoWallpaperFileName(int which) {
    ArrayList<Item> arrayList = this.mVideoItems;
    if (arrayList == null || arrayList.size() == 0) {
      Log.m98i(TAG, "getDefaultVideoWallpaperFileName: mVideoItems is null");
      return null;
    }
    Item defaultResource = null;
    int screen = (which & 16) == 16 ? 1 : 0;
    if (this.mIsSupportCMF) {
      try {
        String deviceColor = WallpaperManager.getDeviceColor(this.mContext);
        this.mColorCode = deviceColor;
        if (!TextUtils.isEmpty(deviceColor)) {
          Log.m98i(TAG, "getDefaultItem: colorCode = " + this.mColorCode + " , screen = " + screen);
          Iterator<Item> it = this.mVideoItems.iterator();
          while (true) {
            if (!it.hasNext()) {
              break;
            }
            Item item = it.next();
            if (isDefaultResource(item, which, screen, this.mColorCode)) {
              break;
            }
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    if (defaultResource == null) {
      Iterator<Item> it2 = this.mVideoItems.iterator();
      while (true) {
        if (!it2.hasNext()) {
          break;
        }
        Item item2 = it2.next();
        if (item2.isDefault) {
          Log.m98i(
              TAG,
              "getDefaultItem by default attr: "
                  + item2.fileName
                  + ", "
                  + item2.index
                  + " , "
                  + item2.cmfInfo);
          defaultResource = item2;
          break;
        }
      }
    }
    if (defaultResource == null) {
      Item firstVideo = this.mVideoItems.get(0);
      Log.m98i(
          TAG,
          "getDefaultItem by first resource: "
              + firstVideo.fileName
              + ", "
              + firstVideo.index
              + " , "
              + firstVideo.cmfInfo);
      defaultResource = this.mVideoItems.get(0);
    }
    return defaultResource.fileName;
  }

  public int getDefaultVideoFrameInfo(String fileName) {
    if (TextUtils.isEmpty(fileName)) {
      Log.m96e(TAG, "getDefaultVideoFrameInfo: fileName is null");
      return 0;
    }
    ArrayList<Item> arrayList = this.mVideoItems;
    if (arrayList == null || arrayList.size() == 0) {
      Log.m98i(TAG, "getDefaultVideoFrameInfo: mVideoItems is null");
      return 0;
    }
    Iterator<Item> it = this.mVideoItems.iterator();
    while (it.hasNext()) {
      Item item = it.next();
      if (item.fileName != null && item.fileName.equals(fileName)) {
        return item.videoFrameInfo;
      }
    }
    return 0;
  }

  public boolean isBlackFirstFrame(String fileName) {
    if (TextUtils.isEmpty(fileName)) {
      Log.m96e(TAG, "isBlackFirstFrame: fileName is null");
      return false;
    }
    ArrayList<Item> arrayList = this.mVideoItems;
    if (arrayList == null || arrayList.size() == 0) {
      Log.m98i(TAG, "isBlackFirstFrame: mVideoItems is null");
      return false;
    }
    Iterator<Item> it = this.mVideoItems.iterator();
    while (it.hasNext()) {
      Item item = it.next();
      if (item.fileName != null && item.fileName.equals(fileName)) {
        return item.isBlackFirstFrame;
      }
    }
    return false;
  }

  public ComponentName getDefaultLiveWallpaperComponentName(int which) {
    Item item = getDefaultLiveWallpaperResource(which);
    if (item == null
        || TextUtils.isEmpty(item.typeParams.mServicePkgName)
        || TextUtils.isEmpty(item.typeParams.mServiceClassName)) {
      return null;
    }
    return new ComponentName(item.typeParams.mServicePkgName, item.typeParams.mServiceClassName);
  }

  public Bundle getDefaultLiveWallpaperExtras(int which) {
    TypeParams typeParams;
    Item item = getDefaultLiveWallpaperResource(which);
    if (item == null || (typeParams = item.typeParams) == null || typeParams.mExtras.isEmpty()) {
      return null;
    }
    return new Bundle(typeParams.mExtras);
  }

  public boolean isKnownColorCode(String colorCode) {
    if (TextUtils.isEmpty(colorCode)) {
      return false;
    }
    boolean isKnown = this.mKnownColorCode.contains(colorCode.toLowerCase());
    Log.m94d(TAG, "isKnownColorCode: code = " + colorCode + ", isKnown = " + isKnown);
    return isKnown;
  }

  /* JADX WARN: Code restructure failed: missing block: B:42:0x006e, code lost:

     android.util.Log.m98i(android.app.SemWallpaperResourcesInfo.TAG, "getDefaultLiveWallpaperResource by color code: " + r7.fileName + ", " + r7.index + " , " + r7.cmfInfo);
  */
  /* JADX WARN: Code restructure failed: missing block: B:43:0x00a0, code lost:

     r0 = r7;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  private Item getDefaultLiveWallpaperResource(int which) {
    if (this.mLiveItems.size() == 0) {
      Log.m98i(TAG, "getDefaultLiveWallpaperResource: mLiveItems is empty");
      return null;
    }
    Item defaultResource = null;
    if (this.mIsSupportCMF) {
      try {
        String deviceColor = WallpaperManager.getDeviceColor(this.mContext);
        this.mColorCode = deviceColor;
        if (!TextUtils.isEmpty(deviceColor)) {
          int screen = (which & 16) == 16 ? 1 : 0;
          Log.m98i(
              TAG,
              "getDefaultLiveWallpaperResource: colorCode = "
                  + this.mColorCode
                  + " , screen = "
                  + screen);
          Iterator<Item> it = this.mLiveItems.iterator();
          while (true) {
            if (!it.hasNext()) {
              break;
            }
            Item item = it.next();
            if (isDefaultResource(item, which, screen, this.mColorCode)) {
              break;
            }
          }
        }
      } catch (Exception e) {
        Log.m96e(TAG, "getDefaultLiveWallpaperResource: e=" + e);
      }
    }
    if (defaultResource == null) {
      Iterator<Item> it2 = this.mLiveItems.iterator();
      while (true) {
        if (!it2.hasNext()) {
          break;
        }
        Item item2 = it2.next();
        if (isWhichMatched(which, item2) && item2.isDefault) {
          Log.m98i(
              TAG,
              "getDefaultLiveWallpaperResource by default attr: "
                  + item2.fileName
                  + ", "
                  + item2.index
                  + " , "
                  + item2.cmfInfo);
          defaultResource = item2;
          break;
        }
      }
    }
    if (defaultResource == null) {
      Item firstItem = this.mLiveItems.get(0);
      if (isWhichMatched(which, firstItem)) {
        Log.m98i(
            TAG,
            "getDefaultLiveWallpaperResource by first resource: "
                + firstItem.fileName
                + ", "
                + firstItem.index
                + " , "
                + firstItem.cmfInfo);
        return firstItem;
      }
      return defaultResource;
    }
    return defaultResource;
  }

  private boolean isWhichMatched(int which, Item item) {
    int targetItemScreen = (which & 16) == 16 ? 1 : 0;
    int targetItemWhich = which & 3;
    return item.which == targetItemWhich && item.screen == targetItemScreen;
  }

  /* JADX WARN: Code restructure failed: missing block: B:53:0x006b, code lost:

     android.util.Log.m98i(android.app.SemWallpaperResourcesInfo.TAG, "getDefaultItem by color code: " + r7.fileName + ", " + r7.index + " , " + r7.cmfInfo);
  */
  /* JADX WARN: Code restructure failed: missing block: B:54:0x009d, code lost:

     r0 = r7;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  private Item getDefaultImageResource(int which) {
    ArrayList<Item> arrayList = this.mImageItems;
    if (arrayList == null || arrayList.size() == 0) {
      Log.m98i(TAG, "getDefaultItem: mImageItems is null");
      return null;
    }
    Item defaultResource = null;
    Item firstResource = null;
    int screen = (which & 16) == 16 ? 1 : 0;
    if (this.mIsSupportCMF) {
      try {
        String deviceColor = WallpaperManager.getDeviceColor(this.mContext);
        this.mColorCode = deviceColor;
        if (!TextUtils.isEmpty(deviceColor)) {
          Log.m98i(TAG, "getDefaultItem: colorCode = " + this.mColorCode + " , screen = " + screen);
          Iterator<Item> it = this.mImageItems.iterator();
          while (true) {
            if (!it.hasNext()) {
              break;
            }
            Item item = it.next();
            if (isDefaultResource(item, which, screen, this.mColorCode)) {
              break;
            }
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    boolean isFirst = false;
    if (defaultResource == null) {
      Iterator<Item> it2 = this.mImageItems.iterator();
      while (true) {
        if (!it2.hasNext()) {
          break;
        }
        Item item2 = it2.next();
        int resourceWhich = item2.which;
        if ((which & resourceWhich) == resourceWhich && screen == item2.screen) {
          if (!isFirst && !item2.isBespoke) {
            firstResource = item2;
            isFirst = true;
          }
          if (item2.isDefault && !item2.isBespoke) {
            defaultResource = item2;
            Log.m98i(
                TAG,
                "getDefaultItem by default attr: "
                    + defaultResource.fileName
                    + ", "
                    + defaultResource.index
                    + " , "
                    + defaultResource.cmfInfo);
            break;
          }
        }
      }
    }
    if (defaultResource == null && firstResource != null) {
      Log.m98i(
          TAG,
          "getDefaultItem by first resource: "
              + firstResource.fileName
              + ", "
              + firstResource.index
              + " , "
              + firstResource.cmfInfo);
      return firstResource;
    }
    return defaultResource;
  }

  private boolean isDefaultResource(Item item, int which, int screen, String colorCode) {
    boolean isDefaultResource = false;
    int resourceWhich = item.which;
    if ((which & resourceWhich) == resourceWhich
        && item.cmfInfo.size() != 0
        && screen == item.screen) {
      Iterator it = item.cmfInfo.iterator();
      while (it.hasNext()) {
        String cmfInfo = (String) it.next();
        if (!TextUtils.isEmpty(cmfInfo) && cmfInfo.contains(colorCode)) {
          isDefaultResource = true;
        }
      }
    }
    return isDefaultResource;
  }

  private void addItem(Item item) {
    if (item.type == 8) {
      this.mVideoItems.add(item);
    } else if (item.type == 0) {
      this.mImageItems.add(item);
    } else if (item.type == 10) {
      this.mLiveItems.add(item);
    }
  }

  private void parseJson() {
    Throwable th;
    InputStream is;
    int resId =
        this.mContext.getResources().getIdentifier("resources_info", "raw", WALLPAPER_PACKAGE);
    InputStream is2 = null;
    Writer writer = new StringWriter();
    char[] buffer = new char[1024];
    try {
      try {
        try {
          try {
            is2 = this.mContext.getResources().openRawResource(resId);
            Reader reader = new BufferedReader(new InputStreamReader(is2, "UTF-8"));
            while (true) {
              int n = reader.read(buffer);
              if (n == -1) {
                break;
              } else {
                writer.write(buffer, 0, n);
              }
            }
            if (is2 != null) {
              is2.close();
            }
          } catch (IOException e) {
            e.printStackTrace();
          }
        } catch (IOException e2) {
          Log.m98i(TAG, "parseJson: IOException");
          e2.printStackTrace();
          if (is2 != null) {
            is2.close();
          }
        }
      } catch (Resources.NotFoundException e3) {
        try {
          Log.m98i(TAG, "parseJson: json file is not exist");
          e3.printStackTrace();
          if (is2 != null) {
            is2.close();
          }
        } catch (Throwable th2) {
          th = th2;
          if (is2 == null) {
            throw th;
          }
          try {
            is2.close();
            throw th;
          } catch (IOException e4) {
            e4.printStackTrace();
            throw th;
          }
        }
      } catch (UnsupportedEncodingException e5) {
        Log.m98i(TAG, "parseJson: UnsupportedEncodingException");
        e5.printStackTrace();
        if (is2 != null) {
          is2.close();
        }
      }
      String jsonData = writer.toString();
      int lastParseSuccessItemIndex = -1;
      try {
        JSONObject jsonRoot = new JSONObject(jsonData);
        JSONArray jsonArray = jsonRoot.getJSONArray("phone");
        int itemCount = jsonArray.length();
        int i = 0;
        while (i < itemCount) {
          try {
            Item item = new Item();
            JSONObject jsonItem = jsonArray.getJSONObject(i);
            int resId2 = resId;
            try {
              is = is2;
            } catch (JSONException e6) {
              e = e6;
            }
            try {
              item.isDefault = jsonItem.getBoolean("isDefault");
              item.index = Integer.valueOf(jsonItem.getInt("index"));
              item.type = jsonItem.getInt("type");
              item.which = jsonItem.optInt("which", -1);
              item.screen = jsonItem.optInt("screen", -1);
              item.isBespoke = jsonItem.optBoolean("isBespoke", false);
              item.fileName = jsonItem.optString("filename", null);
              item.videoFrameInfo = jsonItem.optInt("frame_no", -1);
              item.isBlackFirstFrame = jsonItem.optBoolean("isBlackFirstFrame", false);
              parseCmfInfo(jsonItem.getJSONArray("cmf_info"), item);
              parseTypeParams(jsonItem.optJSONObject("type_params"), item);
              addItem(item);
              lastParseSuccessItemIndex = item.index.intValue();
              i++;
              is2 = is;
              resId = resId2;
            } catch (JSONException e7) {
              e = e7;
              Log.m97e(TAG, "parseJson: e=" + e, e);
              Log.m96e(
                  TAG, "parseJson: last parse success item index=" + lastParseSuccessItemIndex);
              Log.m96e(TAG, "parseJson: " + jsonData);
              return;
            }
          } catch (JSONException e8) {
            e = e8;
          }
        }
        try {
          JSONArray jsonArray2 = jsonRoot.optJSONArray("types");
          if (jsonArray2 != null) {
            int i2 = 0;
            while (i2 < jsonArray2.length()) {
              JSONObject object = jsonArray2.getJSONObject(i2);
              int screen = object.getInt("screen");
              int which = object.getInt("which");
              int type = object.getInt("type");
              JSONArray jsonArray3 = jsonArray2;
              setDefaultWallpaperType(screen, which, type);
              Writer writer2 = writer;
              if (type == 3) {
                try {
                  String style = object.getString("style");
                  setDefaultMultipackStyle(screen, which, style);
                } catch (JSONException e9) {
                  e = e9;
                  Log.m97e(TAG, "parseJson: e=" + e, e);
                  Log.m96e(
                      TAG, "parseJson: last parse success item index=" + lastParseSuccessItemIndex);
                  Log.m96e(TAG, "parseJson: " + jsonData);
                  return;
                }
              }
              i2++;
              jsonArray2 = jsonArray3;
              writer = writer2;
            }
          }
          JSONArray bespokeArray = jsonRoot.optJSONArray("bespoke");
          if (bespokeArray != null) {
            for (int i3 = 0; i3 < bespokeArray.length(); i3++) {
              String colorCode = ((String) bespokeArray.get(i3)).toLowerCase();
              this.mBespokeCode.add(colorCode);
              this.mKnownColorCode.add(colorCode);
            }
          }
        } catch (JSONException e10) {
          e = e10;
        }
      } catch (JSONException e11) {
        e = e11;
      }
    } catch (Throwable th3) {
      th = th3;
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
      typeParams.mExtras.putBundle(SemWallpaperProperties.KEY_SERVICE_SETTINGS, svcSettingBundle);
    }
  }

  private void parseCmfInfo(JSONArray cmfArray, Item outItem) {
    if (cmfArray == null || outItem == null) {
      return;
    }
    int cmfCount = cmfArray.length();
    for (int j = 0; j < cmfCount; j++) {
      String colorCode = cmfArray.optString(j, null);
      if (TextUtils.isEmpty(colorCode)) {
        Log.m102w(TAG, "parseCmfInfo: empty cmf detected. wp item index=" + outItem.index);
      } else {
        String colorCode2 = colorCode.toLowerCase();
        outItem.cmfInfo.add(colorCode2);
        this.mKnownColorCode.add(colorCode2);
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
        Log.m96e(TAG, "convertJsonObjectToBundle: failed to get value. key=" + key);
      }
    }
    return bundle;
  }

  private void ascendingSort() {
    Ascending ascending = new Ascending();
    ArrayList<Item> arrayList = this.mImageItems;
    if (arrayList != null && arrayList.size() > 1) {
      Collections.sort(this.mImageItems, ascending);
    }
    ArrayList<Item> arrayList2 = this.mVideoItems;
    if (arrayList2 != null && arrayList2.size() > 1) {
      Collections.sort(this.mVideoItems, ascending);
    }
    ArrayList<Item> arrayList3 = this.mLiveItems;
    if (arrayList3 != null && arrayList3.size() > 1) {
      Collections.sort(this.mLiveItems, ascending);
    }
  }

  private boolean checkCMF() {
    boolean isCMF = false;
    Iterator<Item> it = this.mImageItems.iterator();
    while (it.hasNext()) {
      Item item = it.next();
      if (item.cmfInfo.size() > 0 && (isCMF = isValidCode(item.cmfInfo))) {
        return true;
      }
    }
    if (!isCMF) {
      Iterator<Item> it2 = this.mVideoItems.iterator();
      while (it2.hasNext()) {
        Item item2 = it2.next();
        if (item2.cmfInfo.size() > 0 && (isCMF = isValidCode(item2.cmfInfo))) {
          return true;
        }
      }
    }
    if (!isCMF) {
      Iterator<Item> it3 = this.mLiveItems.iterator();
      while (it3.hasNext()) {
        Item item3 = it3.next();
        if (item3.cmfInfo.size() > 0 && (isCMF = isValidCode(item3.cmfInfo))) {
          return true;
        }
      }
    }
    return isCMF;
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

  public boolean isSupportCMF() {
    return this.mIsSupportCMF;
  }

  private boolean isPhone(int which) {
    return ((which & 8) == 8 || (which & 16) == 16) ? false : true;
  }

  private void setDefaultWallpaperType(int screen, int which, int type) {
    int mode = screen == 1 ? 0 | 16 : 0;
    if (screen == 0) {
      mode |= 4;
    }
    this.mDefaultTypeMap.put(Integer.valueOf(which | mode), Integer.valueOf(type));
  }

  public int getDefaultWallpaperType(int which) {
    if (isPhone(which)) {
      which |= 4;
    }
    int type = this.mDefaultTypeMap.getOrDefault(Integer.valueOf(which), 0).intValue();
    if (type == 10) {
      return 7;
    }
    return type;
  }

  private void setDefaultMultipackStyle(int screen, int which, String style) {
    int mode = screen == 1 ? 0 | 16 : 0;
    if (screen == 0) {
      mode |= 4;
    }
    this.mDefaultMultipackStyle.put(Integer.valueOf(which | mode), style);
  }

  public String getDefaultMultipackStyle(int which) {
    if (isPhone(which)) {
      which |= 4;
    }
    return this.mDefaultMultipackStyle.get(Integer.valueOf(which));
  }

  public boolean isDefaultVideo(int which) {
    return getDefaultWallpaperType(which) == 8;
  }

  public boolean isDefaultMultipack(int which) {
    return getDefaultWallpaperType(which) == 3;
  }

  static class Ascending implements Comparator<Item>, Serializable {
    Ascending() {}

    @Override // java.util.Comparator
    public int compare(Item r1, Item r2) {
      return r1.index.compareTo(r2.index);
    }
  }

  static class Item {
    private Integer index = -1;
    private int which = -1;
    private String fileName = null;
    private int type = -1;
    private TypeParams typeParams = new TypeParams();
    private int screen = 0;
    private int videoFrameInfo = -1;
    private boolean isBlackFirstFrame = false;
    private boolean isDefault = false;
    private boolean isBespoke = false;
    private ArrayList<String> cmfInfo = new ArrayList<>();

    Item() {}
  }
}
