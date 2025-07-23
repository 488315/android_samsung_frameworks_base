package android.content.om.wallpapertheme;

import android.content.Context;
import android.content.om.SamsungThemeConstants;
import android.content.om.WallpaperThemeConstants;
import android.content.om.WallpaperThemeUtils;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.telecom.Logging.Session;
import android.util.Log;
import android.util.NtpTrustedTime;
import com.android.internal.R;
import com.android.internal.content.NativeLibraryHelper;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class TemplateManager {
    private MetaDataManager mMetaDataManager;
    private ThemePalette mThemePalette;
    private String TAG = "SWT_TemplateManager";
    public List<UidItem> mUidTemplate = new ArrayList();
    public HashMap<String, ColorItem> mColorTemplate = new HashMap<>();

    public static class ColorItem {
        public String colorDark;
        public String colorDarkGray;
        public String colorLight;
        public String colorLightGray;
        public String name;
    }

    public static class UidItem {
        public Integer opacity;
        public String theme;
        public String[] themes;
        public String uid;
    }

    public TemplateManager(MetaDataManager metaDataManager, ThemePalette themePalette) {
        this.mThemePalette = themePalette;
        this.mMetaDataManager = metaDataManager;
    }

    public void loadStaticTemplate(Context context) {
        try {
            InputStream openRawResource = context.getResources().openRawResource(R.raw.wallpapertheme_template);
            try {
                initTemplate(readFromInputStream(openRawResource));
                if (openRawResource != null) {
                    openRawResource.close();
                }
            } finally {
            }
        } catch (IOException e) {
            Log.e(this.TAG, "loading UID template, error = " + e);
        }
        Log.i(this.TAG, "static templates loaded, uidsize:" + this.mUidTemplate.size() + ", colorsize:" + this.mColorTemplate.size());
    }

    public void loadTemplateFromUri(Context context, Uri uri) {
        try {
            InputStream openInputStream = context.getContentResolver().openInputStream(uri);
            try {
                String readFromInputStream = readFromInputStream(openInputStream);
                initTemplate(readFromInputStream);
                writeThemeParkTemplate(readFromInputStream);
                if (openInputStream != null) {
                    openInputStream.close();
                }
            } finally {
            }
        } catch (Exception e) {
            Log.e(this.TAG, "Failed at loadTemplate, e = ", e);
        }
        Log.i(this.TAG, "loadTemplateFromUri uidsize:" + this.mUidTemplate.size() + ", colorsize:" + this.mColorTemplate.size());
    }

    private void writeThemeParkTemplate(String str) {
        File file = new File(SamsungThemeConstants.PATH_THEMEPARK_STATE_CHECK);
        if (file.exists()) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                try {
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                    try {
                        outputStreamWriter.write(str);
                        outputStreamWriter.close();
                        fileOutputStream.close();
                        return;
                    } finally {
                    }
                } finally {
                }
            } catch (Exception e) {
                Log.e(this.TAG, "Failed to write ThemePark's template, e = ", e);
                return;
            }
        }
        Log.e(this.TAG, "Failed to write ThemePark's template, couldn't find a ThemePark state file");
    }

    public void initTemplate(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            try {
                initUidTemplate(jSONObject.getJSONArray("template"), this.mUidTemplate);
            } catch (JSONException e) {
                ThemeUtil.saveSWTLog(this.TAG, "loading uid template, ex = " + e);
            }
            try {
                initColorTemplate(jSONObject.getJSONArray("color-template"), this.mColorTemplate);
            } catch (JSONException e2) {
                ThemeUtil.saveSWTLog(this.TAG, "loading color template, ex = " + e2);
            }
        } catch (JSONException e3) {
            ThemeUtil.saveSWTLog(this.TAG, "loading template file, ex = " + e3);
        }
    }

    private void initUidTemplate(JSONArray jSONArray, List<UidItem> list) throws JSONException {
        list.clear();
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject jSONObject = jSONArray.getJSONObject(i);
            UidItem uidItem = new UidItem();
            String[] split = jSONObject.getString("theme").split(",");
            uidItem.theme = split[0];
            if (split.length == 2) {
                uidItem.themes = split;
            }
            uidItem.uid = jSONObject.getString("uid");
            uidItem.opacity = Integer.valueOf(jSONObject.isNull("opacity") ? 100 : jSONObject.getInt("opacity"));
            list.add(uidItem);
        }
    }

    private void initColorTemplate(JSONArray jSONArray, HashMap<String, ColorItem> hashMap) throws JSONException {
        hashMap.clear();
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject jSONObject = jSONArray.getJSONObject(i);
            if (!jSONObject.isNull("name") && !jSONObject.isNull("colorLight")) {
                ColorItem colorItem = new ColorItem();
                colorItem.name = jSONObject.getString("name");
                colorItem.colorLight = jSONObject.getString("colorLight");
                colorItem.colorDark = jSONObject.isNull("colorDark") ? colorItem.colorLight : jSONObject.getString("colorDark");
                colorItem.colorLightGray = jSONObject.isNull("colorLightGray") ? colorItem.colorLight : jSONObject.getString("colorLightGray");
                colorItem.colorDarkGray = jSONObject.isNull("colorDarkGray") ? colorItem.colorDark : jSONObject.getString("colorDarkGray");
                checkValidTemplate(colorItem.colorLight);
                checkValidTemplate(colorItem.colorDark);
                checkValidTemplate(colorItem.colorLightGray);
                checkValidTemplate(colorItem.colorDarkGray);
                hashMap.put(colorItem.name, colorItem);
            }
        }
    }

    private void checkValidTemplate(String str) {
        if (str == null || getColorFromName(str) != null) {
            return;
        }
        Log.e(this.TAG, "Error in color mapping. wrong value : " + str);
    }

    public void update(ApplicationInfo applicationInfo) {
        String str;
        Bundle bundle;
        Resources packageResources;
        int intValue;
        InputStream openRawResource;
        try {
            str = applicationInfo.packageName;
            bundle = applicationInfo.metaData;
            packageResources = WallpaperThemeUtils.getPackageResources(applicationInfo);
        } catch (Exception e) {
            ThemeUtil.saveSWTLog(this.TAG, "updateTemplateFromPkg, error = " + e);
        }
        if (packageResources == null) {
            return;
        }
        Object obj = bundle.get(WallpaperThemeConstants.THEMING_TEMPLATE);
        if (obj instanceof String) {
            for (String str2 : ((String) obj).split(",\\s*")) {
                int identifier = packageResources.getIdentifier(str2, "raw", applicationInfo.packageName);
                if (identifier > 0) {
                    try {
                        openRawResource = packageResources.openRawResource(identifier);
                        try {
                            updateTemplate(readFromInputStream(openRawResource), str);
                            if (openRawResource != null) {
                                openRawResource.close();
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    } catch (IOException e2) {
                        ThemeUtil.saveSWTLog(this.TAG, "update template from apk, error = " + e2);
                    }
                } else {
                    Log.e(this.TAG, "template file not found in res/xml : " + str2);
                }
            }
            return;
        }
        if (!(obj instanceof Integer) || (intValue = ((Integer) obj).intValue()) <= 0) {
            return;
        }
        try {
            openRawResource = packageResources.openRawResource(intValue);
            try {
                updateTemplate(readFromInputStream(openRawResource), str);
                if (openRawResource != null) {
                    openRawResource.close();
                    return;
                }
                return;
            } finally {
                if (openRawResource != null) {
                    try {
                        openRawResource.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
            }
        } catch (IOException e3) {
            ThemeUtil.saveSWTLog(this.TAG, "update template from apk, error = " + e3);
            return;
        }
        ThemeUtil.saveSWTLog(this.TAG, "updateTemplateFromPkg, error = " + e);
    }

    private void updateTemplate(String str, String str2) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            try {
                updateUidTemplate(jSONObject.getJSONArray("template"), str2);
            } catch (JSONException e) {
                ThemeUtil.saveSWTLog(this.TAG, "loading uid template for update, ex = " + e);
            }
            try {
                updateColorTemplate(jSONObject.getJSONArray("color-template"), str2);
            } catch (JSONException e2) {
                ThemeUtil.saveSWTLog(this.TAG, "loading color template for update, ex = " + e2);
            }
        } catch (JSONException e3) {
            ThemeUtil.saveSWTLog(this.TAG, "loading template file for update, ex = " + e3);
        }
    }

    private void updateUidTemplate(JSONArray jSONArray, String str) throws JSONException {
        String str2 = jSONArray.getJSONObject(0).getString("uid").split(NativeLibraryHelper.CLEAR_ABI_OVERRIDE)[0];
        ThemeUtil.saveSWTLog(this.TAG, "template rpUID [" + str2 + "] replaced by " + str);
        ArrayList arrayList = new ArrayList();
        for (UidItem uidItem : this.mUidTemplate) {
            if (uidItem.uid != null) {
                if (uidItem.uid.startsWith(str2 + NativeLibraryHelper.CLEAR_ABI_OVERRIDE)) {
                    arrayList.add(uidItem);
                }
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            this.mUidTemplate.remove((UidItem) it.next());
        }
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject jSONObject = jSONArray.getJSONObject(i);
            UidItem uidItem2 = new UidItem();
            uidItem2.theme = jSONObject.getString("theme");
            uidItem2.uid = jSONObject.getString("uid");
            uidItem2.opacity = Integer.valueOf(jSONObject.isNull("opacity") ? 100 : jSONObject.getInt("opacity"));
            this.mUidTemplate.add(uidItem2);
        }
    }

    private void updateColorTemplate(JSONArray jSONArray, String str) throws JSONException {
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject jSONObject = jSONArray.getJSONObject(i);
            if (jSONObject.isNull("name") || jSONObject.isNull("colorLight")) {
                Log.i(this.TAG, "abnormal color template is ignored, obj : " + jSONObject);
            } else {
                ColorItem colorItem = new ColorItem();
                colorItem.name = jSONObject.getString("name");
                colorItem.colorLight = jSONObject.getString("colorLight");
                colorItem.colorDark = jSONObject.isNull("colorDark") ? colorItem.colorLight : jSONObject.getString("colorDark");
                colorItem.colorLightGray = jSONObject.isNull("colorLightGray") ? colorItem.colorLight : jSONObject.getString("colorLightGray");
                colorItem.colorDarkGray = jSONObject.isNull("colorDarkGray") ? colorItem.colorDark : jSONObject.getString("colorDarkGray");
                Iterator<ColorItem> it = this.mColorTemplate.values().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    ColorItem next = it.next();
                    if (next.name.equals(colorItem.name)) {
                        ThemeUtil.saveSWTLog(this.TAG, "template COLOR [" + colorItem.name + "] replaced by " + str);
                        this.mColorTemplate.remove(next.name);
                        break;
                    }
                }
                this.mColorTemplate.put(colorItem.name, colorItem);
            }
        }
    }

    private String readFromInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[1024];
        while (true) {
            int read = inputStream.read(bArr);
            if (read != -1) {
                byteArrayOutputStream.write(bArr, 0, read);
            } else {
                return byteArrayOutputStream.toString("UTF-8");
            }
        }
    }

    private <T> T search(String str, Function<UidItem, T> function) {
        for (int i = 0; i < 20; i++) {
            for (UidItem uidItem : this.mUidTemplate) {
                if (uidItem.uid.equals(str)) {
                    return function.apply(uidItem);
                }
            }
            str = this.mMetaDataManager.getRefUid(str);
            if (str == null) {
                return null;
            }
        }
        return null;
    }

    public List<Integer> getColors(String str) {
        return (List) search(str, new Function<UidItem, List<Integer>>() { // from class: android.content.om.wallpapertheme.TemplateManager.1
            @Override // java.util.function.Function
            public List<Integer> apply(UidItem uidItem) {
                Integer colorFromName;
                Integer colorFromName2;
                String[] strArr = uidItem.themes != null ? uidItem.themes : new String[]{uidItem.theme, uidItem.theme};
                if (strArr[0].startsWith("#")) {
                    colorFromName = Integer.valueOf(Color.parseColor(strArr[0]));
                } else {
                    ColorItem colorItem = TemplateManager.this.mColorTemplate.get(strArr[0]);
                    if (colorItem == null) {
                        return null;
                    }
                    TemplateManager templateManager = TemplateManager.this;
                    colorFromName = templateManager.getColorFromName(templateManager.mThemePalette.mIsGray ? colorItem.colorLightGray : colorItem.colorLight);
                }
                if (colorFromName == null) {
                    return null;
                }
                if (strArr[1].startsWith("#")) {
                    colorFromName2 = Integer.valueOf(Color.parseColor(strArr[1]));
                } else {
                    ColorItem colorItem2 = TemplateManager.this.mColorTemplate.get(strArr[1]);
                    if (colorItem2 == null) {
                        return null;
                    }
                    TemplateManager templateManager2 = TemplateManager.this;
                    colorFromName2 = templateManager2.getColorFromName(templateManager2.mThemePalette.mIsGray ? colorItem2.colorDarkGray : colorItem2.colorDark);
                }
                if (colorFromName2 == null) {
                    return null;
                }
                if (uidItem.opacity.intValue() != 100) {
                    colorFromName = ThemeUtil.adjustAlpha(uidItem.opacity.intValue() / 100.0f, colorFromName.intValue());
                    colorFromName2 = ThemeUtil.adjustAlpha(uidItem.opacity.intValue() / 100.0f, colorFromName2.intValue());
                }
                return new ArrayList(Arrays.asList(colorFromName, colorFromName2));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public Integer getColorFromName(String str) {
        boolean z;
        int i;
        TemplateManager templateManager;
        if (str.startsWith("#")) {
            return Integer.valueOf(Color.parseColor(str));
        }
        String[] split = str.split(Session.SESSION_SEPARATION_CHAR_CHILD);
        int i2 = 2;
        if (split.length != 2) {
            return null;
        }
        String str2 = split[0];
        str2.hashCode();
        switch (str2.hashCode()) {
            case -1177623385:
                if (str2.equals(WallpaperThemeConstants.STRING_ACCENT1)) {
                    z = false;
                    break;
                }
                z = -1;
                break;
            case -1177623384:
                if (str2.equals(WallpaperThemeConstants.STRING_ACCENT2)) {
                    z = true;
                    break;
                }
                z = -1;
                break;
            case -1177623383:
                if (str2.equals(WallpaperThemeConstants.STRING_ACCENT3)) {
                    z = 2;
                    break;
                }
                z = -1;
                break;
            case 1339398986:
                if (str2.equals(WallpaperThemeConstants.STRING_NEUTRAL1)) {
                    z = 3;
                    break;
                }
                z = -1;
                break;
            case 1339398987:
                if (str2.equals(WallpaperThemeConstants.STRING_NEUTRAL2)) {
                    z = 4;
                    break;
                }
                z = -1;
                break;
            default:
                z = -1;
                break;
        }
        switch (z) {
            case false:
                i = 0;
                break;
            case true:
                i = 1;
                break;
            case true:
                i = 2;
                break;
            case true:
                i = 3;
                break;
            case true:
                i = 4;
                break;
            default:
                return null;
        }
        String str3 = split[1];
        str3.hashCode();
        switch (str3) {
            case "0":
                templateManager = this;
                i2 = 0;
                break;
            case "10":
                templateManager = this;
                i2 = 1;
                break;
            case "50":
                templateManager = this;
                break;
            case "100":
                templateManager = this;
                i2 = 3;
                break;
            case "200":
                templateManager = this;
                i2 = 4;
                break;
            case "300":
                templateManager = this;
                i2 = 5;
                break;
            case "400":
                templateManager = this;
                i2 = 6;
                break;
            case "500":
                templateManager = this;
                i2 = 7;
                break;
            case "600":
                templateManager = this;
                i2 = 8;
                break;
            case "700":
                templateManager = this;
                i2 = 9;
                break;
            case "800":
                templateManager = this;
                i2 = 10;
                break;
            case "900":
                templateManager = this;
                i2 = 11;
                break;
            case "1000":
                templateManager = this;
                i2 = 12;
                break;
            default:
                return null;
        }
        return Integer.valueOf(templateManager.mThemePalette.getMonetColorSS(i, i2));
    }

    public List<Boolean> getBooleans(String str) {
        return (List) search(str, new Function<UidItem, List<Boolean>>(this) { // from class: android.content.om.wallpapertheme.TemplateManager.2
            @Override // java.util.function.Function
            public List<Boolean> apply(UidItem uidItem) {
                Boolean bool;
                Boolean bool2;
                String[] strArr = uidItem.themes != null ? uidItem.themes : new String[]{uidItem.theme, uidItem.theme};
                if ("true".equalsIgnoreCase(strArr[0])) {
                    bool = Boolean.TRUE;
                } else {
                    bool = "false".equalsIgnoreCase(strArr[0]) ? Boolean.FALSE : null;
                }
                if ("true".equalsIgnoreCase(strArr[1])) {
                    bool2 = Boolean.TRUE;
                } else {
                    bool2 = "false".equalsIgnoreCase(strArr[1]) ? Boolean.FALSE : null;
                }
                if (bool == null || bool2 == null) {
                    return null;
                }
                return new ArrayList(Arrays.asList(bool, bool2));
            }
        });
    }

    public Integer getInteger(String str) {
        return (Integer) search(str, new Function<UidItem, Integer>() { // from class: android.content.om.wallpapertheme.TemplateManager.3
            @Override // java.util.function.Function
            public Integer apply(UidItem uidItem) {
                try {
                    return Integer.valueOf(Integer.parseInt(uidItem.theme));
                } catch (Exception e) {
                    Log.e(TemplateManager.this.TAG, "error = " + e);
                    return null;
                }
            }
        });
    }

    public String getString(String str) {
        return (String) search(str, new Function<UidItem, String>(this) { // from class: android.content.om.wallpapertheme.TemplateManager.4
            @Override // java.util.function.Function
            public String apply(UidItem uidItem) {
                if (uidItem.theme != null && uidItem.theme.startsWith("@")) {
                    return uidItem.theme.substring(1);
                }
                return null;
            }
        });
    }

    public void dump(PrintWriter printWriter) {
        printWriter.println("- TEMPLATE -");
        printWriter.println(" [UID ITEMS]");
        for (UidItem uidItem : this.mUidTemplate) {
            printWriter.println("   - UID:" + uidItem.uid + ", THEME:" + uidItem.theme + ", OPA:" + uidItem.opacity);
        }
        printWriter.println(" [COLOR ITEMS]");
        for (ColorItem colorItem : this.mColorTemplate.values()) {
            printWriter.println("   - NAME:" + colorItem.name + ", COLOR:" + colorItem.colorLight + NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER + colorItem.colorDark + NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER + colorItem.colorLightGray + NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER + colorItem.colorDarkGray);
        }
    }
}
