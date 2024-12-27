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

    public TemplateManager(MetaDataManager metadataManager, ThemePalette themePalette) {
        this.mThemePalette = themePalette;
        this.mMetaDataManager = metadataManager;
    }

    public void loadStaticTemplate(Context context) {
        try {
            InputStream is = context.getResources().openRawResource(R.raw.wallpapertheme_template);
            try {
                String str = readFromInputStream(is);
                initTemplate(str);
                if (is != null) {
                    is.close();
                }
            } finally {
            }
        } catch (IOException e) {
            Log.e(this.TAG, "loading UID template, error = " + e);
        }
        Log.i(this.TAG, "static templates loaded, uidsize:" + this.mUidTemplate.size() + ", colorsize:" + this.mColorTemplate.size());
    }

    public void loadTemplateFromUri(Context context, Uri path) {
        try {
            InputStream in = context.getContentResolver().openInputStream(path);
            try {
                String jsonString = readFromInputStream(in);
                initTemplate(jsonString);
                writeThemeParkTemplate(jsonString);
                if (in != null) {
                    in.close();
                }
            } finally {
            }
        } catch (Exception e) {
            Log.e(this.TAG, "Failed at loadTemplate, e = ", e);
        }
        Log.i(this.TAG, "loadTemplateFromUri uidsize:" + this.mUidTemplate.size() + ", colorsize:" + this.mColorTemplate.size());
    }

    private void writeThemeParkTemplate(String jsonString) {
        File isThemeParkApplied = new File(SamsungThemeConstants.PATH_THEMEPARK_STATE_CHECK);
        if (isThemeParkApplied.exists()) {
            try {
                FileOutputStream fos = new FileOutputStream(isThemeParkApplied);
                try {
                    OutputStreamWriter osw = new OutputStreamWriter(fos);
                    try {
                        osw.write(jsonString);
                        osw.close();
                        fos.close();
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

    public void initTemplate(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            try {
                JSONArray jArray = jsonObject.getJSONArray("template");
                initUidTemplate(jArray, this.mUidTemplate);
            } catch (JSONException jsonException) {
                ThemeUtil.saveSWTLog(this.TAG, "loading uid template, ex = " + jsonException);
            }
            try {
                JSONArray jArray2 = jsonObject.getJSONArray("color-template");
                initColorTemplate(jArray2, this.mColorTemplate);
            } catch (JSONException jsonException2) {
                ThemeUtil.saveSWTLog(this.TAG, "loading color template, ex = " + jsonException2);
            }
        } catch (JSONException jsonException3) {
            ThemeUtil.saveSWTLog(this.TAG, "loading template file, ex = " + jsonException3);
        }
    }

    private void initUidTemplate(JSONArray jArray, List<UidItem> template) throws JSONException {
        template.clear();
        for (int i = 0; i < jArray.length(); i++) {
            JSONObject obj = jArray.getJSONObject(i);
            UidItem item = new UidItem();
            String[] split = obj.getString("theme").split(",");
            item.theme = split[0];
            if (split.length == 2) {
                item.themes = split;
            }
            item.uid = obj.getString("uid");
            item.opacity = Integer.valueOf(obj.isNull("opacity") ? 100 : obj.getInt("opacity"));
            template.add(item);
        }
    }

    private void initColorTemplate(JSONArray jArray, HashMap<String, ColorItem> template) throws JSONException {
        template.clear();
        for (int i = 0; i < jArray.length(); i++) {
            JSONObject obj = jArray.getJSONObject(i);
            if (!obj.isNull("name") && !obj.isNull("colorLight")) {
                ColorItem item = new ColorItem();
                item.name = obj.getString("name");
                item.colorLight = obj.getString("colorLight");
                item.colorDark = obj.isNull("colorDark") ? item.colorLight : obj.getString("colorDark");
                item.colorLightGray = obj.isNull("colorLightGray") ? item.colorLight : obj.getString("colorLightGray");
                item.colorDarkGray = obj.isNull("colorDarkGray") ? item.colorDark : obj.getString("colorDarkGray");
                checkValidTemplate(item.colorLight);
                checkValidTemplate(item.colorDark);
                checkValidTemplate(item.colorLightGray);
                checkValidTemplate(item.colorDarkGray);
                template.put(item.name, item);
            }
        }
    }

    private void checkValidTemplate(String colorValue) {
        if (colorValue != null && getColorFromName(colorValue) == null) {
            Log.e(this.TAG, "Error in color mapping. wrong value : " + colorValue);
        }
    }

    public void update(ApplicationInfo appInfo) {
        String pkgName;
        Bundle pkgMetaData;
        Resources res;
        InputStream is;
        try {
            pkgName = appInfo.packageName;
            pkgMetaData = appInfo.metaData;
            res = WallpaperThemeUtils.getPackageResources(appInfo);
        } catch (Exception e) {
            ThemeUtil.saveSWTLog(this.TAG, "updateTemplateFromPkg, error = " + e);
        }
        if (res == null) {
            return;
        }
        String templateNames = pkgMetaData.getString(WallpaperThemeConstants.THEMING_TEMPLATE);
        if (templateNames != null && !templateNames.isEmpty()) {
            String[] templateArr = templateNames.split(",|\\s");
            for (String templateName : templateArr) {
                int templateResId = res.getIdentifier(templateName, "raw", appInfo.packageName);
                if (templateResId > 0) {
                    try {
                        is = res.openRawResource(templateResId);
                        try {
                            String str = readFromInputStream(is);
                            updateTemplate(str, pkgName);
                            if (is != null) {
                                is.close();
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    } catch (IOException e2) {
                        ThemeUtil.saveSWTLog(this.TAG, "update template from apk, error = " + e2);
                    }
                } else {
                    Log.e(this.TAG, "template file not found in res/xml : " + templateName);
                }
            }
            return;
        }
        int templateResId2 = pkgMetaData.getInt(WallpaperThemeConstants.THEMING_TEMPLATE);
        if (templateResId2 > 0) {
            try {
                is = res.openRawResource(templateResId2);
                try {
                    String str2 = readFromInputStream(is);
                    updateTemplate(str2, pkgName);
                    if (is != null) {
                        is.close();
                        return;
                    }
                    return;
                } finally {
                    if (is != null) {
                        try {
                            is.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                }
            } catch (IOException e3) {
                ThemeUtil.saveSWTLog(this.TAG, "update template from apk, error = " + e3);
                return;
            }
        }
        return;
        ThemeUtil.saveSWTLog(this.TAG, "updateTemplateFromPkg, error = " + e);
    }

    private void updateTemplate(String jsonString, String pkgName) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            try {
                JSONArray jArray = jsonObject.getJSONArray("template");
                updateUidTemplate(jArray, pkgName);
            } catch (JSONException jsonException) {
                ThemeUtil.saveSWTLog(this.TAG, "loading uid template for update, ex = " + jsonException);
            }
            try {
                JSONArray jArray2 = jsonObject.getJSONArray("color-template");
                updateColorTemplate(jArray2, pkgName);
            } catch (JSONException jsonException2) {
                ThemeUtil.saveSWTLog(this.TAG, "loading color template for update, ex = " + jsonException2);
            }
        } catch (JSONException jsonException3) {
            ThemeUtil.saveSWTLog(this.TAG, "loading template file for update, ex = " + jsonException3);
        }
    }

    private void updateUidTemplate(JSONArray jArray, String pkgName) throws JSONException {
        JSONObject firstObj = jArray.getJSONObject(0);
        String firstUid = firstObj.getString("uid");
        String pkgUidNum = firstUid.split(NativeLibraryHelper.CLEAR_ABI_OVERRIDE)[0];
        ThemeUtil.saveSWTLog(this.TAG, "template rpUID [" + pkgUidNum + "] replaced by " + pkgName);
        List<UidItem> existedPkgUid = new ArrayList<>();
        for (UidItem item : this.mUidTemplate) {
            if (item.uid != null && item.uid.startsWith(pkgUidNum + NativeLibraryHelper.CLEAR_ABI_OVERRIDE)) {
                existedPkgUid.add(item);
            }
        }
        Iterator<UidItem> it = existedPkgUid.iterator();
        while (it.hasNext()) {
            this.mUidTemplate.remove(it.next());
        }
        for (int i = 0; i < jArray.length(); i++) {
            JSONObject obj = jArray.getJSONObject(i);
            UidItem item2 = new UidItem();
            item2.theme = obj.getString("theme");
            item2.uid = obj.getString("uid");
            item2.opacity = Integer.valueOf(obj.isNull("opacity") ? 100 : obj.getInt("opacity"));
            this.mUidTemplate.add(item2);
        }
    }

    private void updateColorTemplate(JSONArray jArray, String pkgName) throws JSONException {
        for (int i = 0; i < jArray.length(); i++) {
            JSONObject obj = jArray.getJSONObject(i);
            if (obj.isNull("name") || obj.isNull("colorLight")) {
                Log.i(this.TAG, "abnormal color template is ignored, obj : " + obj);
            } else {
                ColorItem item = new ColorItem();
                item.name = obj.getString("name");
                item.colorLight = obj.getString("colorLight");
                item.colorDark = obj.isNull("colorDark") ? item.colorLight : obj.getString("colorDark");
                item.colorLightGray = obj.isNull("colorLightGray") ? item.colorLight : obj.getString("colorLightGray");
                item.colorDarkGray = obj.isNull("colorDarkGray") ? item.colorDark : obj.getString("colorDarkGray");
                Iterator<ColorItem> it = this.mColorTemplate.values().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    ColorItem colorItem = it.next();
                    if (colorItem.name.equals(item.name)) {
                        ThemeUtil.saveSWTLog(this.TAG, "template COLOR [" + item.name + "] replaced by " + pkgName);
                        this.mColorTemplate.remove(colorItem.name);
                        break;
                    }
                }
                this.mColorTemplate.put(item.name, item);
            }
        }
    }

    private String readFromInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        while (true) {
            int length = inputStream.read(buffer);
            if (length != -1) {
                result.write(buffer, 0, length);
            } else {
                return result.toString("UTF-8");
            }
        }
    }

    private <T> T search(String uid, Function<UidItem, T> listener) {
        String targetUid = uid;
        for (int count = 0; count < 20; count++) {
            for (UidItem item : this.mUidTemplate) {
                if (item.uid.equals(targetUid)) {
                    return listener.apply(item);
                }
            }
            targetUid = this.mMetaDataManager.getRefUid(targetUid);
            if (targetUid == null) {
                return null;
            }
        }
        return null;
    }

    public List<Integer> getColors(String uid) {
        return (List) search(uid, new Function<UidItem, List<Integer>>() { // from class: android.content.om.wallpapertheme.TemplateManager.1
            @Override // java.util.function.Function
            public List<Integer> apply(UidItem item) {
                Integer calculatedLight;
                Integer calculatedDark;
                String[] themes = item.themes != null ? item.themes : new String[]{item.theme, item.theme};
                if (themes[0].startsWith("#")) {
                    calculatedLight = Integer.valueOf(Color.parseColor(themes[0]));
                } else {
                    ColorItem colorItem = TemplateManager.this.mColorTemplate.get(themes[0]);
                    if (colorItem == null) {
                        return null;
                    }
                    calculatedLight = TemplateManager.this.getColorFromName(TemplateManager.this.mThemePalette.mIsGray ? colorItem.colorLightGray : colorItem.colorLight);
                }
                if (calculatedLight == null) {
                    return null;
                }
                if (themes[1].startsWith("#")) {
                    calculatedDark = Integer.valueOf(Color.parseColor(themes[1]));
                } else {
                    ColorItem colorItem2 = TemplateManager.this.mColorTemplate.get(themes[1]);
                    if (colorItem2 == null) {
                        return null;
                    }
                    calculatedDark = TemplateManager.this.getColorFromName(TemplateManager.this.mThemePalette.mIsGray ? colorItem2.colorDarkGray : colorItem2.colorDark);
                }
                if (calculatedDark == null) {
                    return null;
                }
                if (item.opacity.intValue() != 100) {
                    calculatedLight = ThemeUtil.adjustAlpha(item.opacity.intValue() / 100.0f, calculatedLight.intValue());
                    calculatedDark = ThemeUtil.adjustAlpha(item.opacity.intValue() / 100.0f, calculatedDark.intValue());
                }
                return new ArrayList(Arrays.asList(calculatedLight, calculatedDark));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x00ed, code lost:
    
        if (r5.equals("50") != false) goto L78;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Integer getColorFromName(java.lang.String r12) {
        /*
            Method dump skipped, instructions count: 428
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.om.wallpapertheme.TemplateManager.getColorFromName(java.lang.String):java.lang.Integer");
    }

    public List<Boolean> getBooleans(String uid) {
        return (List) search(uid, new Function<UidItem, List<Boolean>>() { // from class: android.content.om.wallpapertheme.TemplateManager.2
            @Override // java.util.function.Function
            public List<Boolean> apply(UidItem item) {
                Boolean boolLight = null;
                Boolean boolDark = null;
                String[] themes = item.themes != null ? item.themes : new String[]{item.theme, item.theme};
                if ("true".equalsIgnoreCase(themes[0])) {
                    boolLight = Boolean.TRUE;
                } else if ("false".equalsIgnoreCase(themes[0])) {
                    boolLight = Boolean.FALSE;
                }
                if ("true".equalsIgnoreCase(themes[1])) {
                    boolDark = Boolean.TRUE;
                } else if ("false".equalsIgnoreCase(themes[1])) {
                    boolDark = Boolean.FALSE;
                }
                if (boolLight == null || boolDark == null) {
                    return null;
                }
                return new ArrayList(Arrays.asList(boolLight, boolDark));
            }
        });
    }

    public Integer getInteger(String uid) {
        return (Integer) search(uid, new Function<UidItem, Integer>() { // from class: android.content.om.wallpapertheme.TemplateManager.3
            @Override // java.util.function.Function
            public Integer apply(UidItem item) {
                try {
                    return Integer.valueOf(Integer.parseInt(item.theme));
                } catch (Exception e) {
                    Log.e(TemplateManager.this.TAG, "error = " + e);
                    return null;
                }
            }
        });
    }

    public String getString(String uid) {
        return (String) search(uid, new Function<UidItem, String>() { // from class: android.content.om.wallpapertheme.TemplateManager.4
            @Override // java.util.function.Function
            public String apply(UidItem item) {
                if (item.theme != null && item.theme.startsWith("@")) {
                    return item.theme.substring(1);
                }
                return null;
            }
        });
    }

    public void dump(PrintWriter pw) {
        pw.println("- TEMPLATE -");
        pw.println(" [UID ITEMS]");
        for (UidItem uidItem : this.mUidTemplate) {
            pw.println("   - UID:" + uidItem.uid + ", THEME:" + uidItem.theme + ", OPA:" + uidItem.opacity);
        }
        pw.println(" [COLOR ITEMS]");
        for (ColorItem c : this.mColorTemplate.values()) {
            pw.println("   - NAME:" + c.name + ", COLOR:" + c.colorLight + NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER + c.colorDark + NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER + c.colorLightGray + NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER + c.colorDarkGray);
        }
    }
}
