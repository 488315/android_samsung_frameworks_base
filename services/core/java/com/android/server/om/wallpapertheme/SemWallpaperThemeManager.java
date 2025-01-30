package com.android.server.om.wallpapertheme;

import android.app.AppGlobals;
import android.content.ContentResolver;
import android.content.Context;
import android.content.om.FabricatedOverlay;
import android.content.om.OverlayIdentifier;
import android.content.om.OverlayInfo;
import android.content.om.OverlayManagerTransaction;
import android.content.om.WallpaperThemeConstants;
import android.content.p000pm.PackageManagerInternal;
import android.content.pm.IPackageManager;
import android.net.Uri;
import android.os.Binder;
import android.os.FileUtils;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Trace;
import android.provider.Settings;
import android.util.Log;
import com.android.server.LocalServices;
import com.android.server.pm.UserManagerService;
import com.android.server.pm.pkg.AndroidPackage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class SemWallpaperThemeManager {
  public static SemWallpaperThemeManager sInstance;
  public Context mContext;
  public ArrayList mCurrentMonetOverlays;
  public ArrayList mCurrentThemeOverlays;
  public Handler mExceptionHandler;
  public MetaDataManager mMetaDataManager;
  public OverlayGenerator mOverlayGenerator;
  public final IPackageManager mPackageManager;
  public final PackageManagerInternal mPackageManagerInternal;
  public TemplateManager mTemplateManager;
  public UserManagerService mUserManager;
  public String TAG = "SWT_ThemeManager";
  public ThemePalette mPalette = new ThemePalette();

  public static SemWallpaperThemeManager getInstance(Context context) {
    if (sInstance == null) {
      sInstance = new SemWallpaperThemeManager(context);
    }
    return sInstance;
  }

  public SemWallpaperThemeManager(Context context) {
    MetaDataManager metaDataManager = new MetaDataManager();
    this.mMetaDataManager = metaDataManager;
    TemplateManager templateManager = new TemplateManager(metaDataManager, this.mPalette);
    this.mTemplateManager = templateManager;
    this.mOverlayGenerator =
        new OverlayGenerator(this.mMetaDataManager, templateManager, this.mPalette);
    this.mExceptionHandler = new Handler();
    this.mContext = context;
    this.mPackageManager = AppGlobals.getPackageManager();
    this.mPackageManagerInternal =
        (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
    this.mUserManager = UserManagerService.getInstance();
  }

  public ArrayList getPackageList() {
    return this.mMetaDataManager.getPackageList();
  }

  public ArrayList getPackageNameList() {
    ArrayList arrayList = new ArrayList();
    Iterator it = getPackageList().iterator();
    while (it.hasNext()) {
      arrayList.add(((MetaDataManager.Package) it.next()).getPackageName());
    }
    return arrayList;
  }

  public void setPalette(List list, List list2, boolean z) {
    this.mPalette.setPalette(list, list2, z);
  }

  public void setTemplate(Uri uri) {
    this.mTemplateManager.loadTemplateFromUri(this.mContext, uri);
  }

  public void setTemplateAsColorTheme() {
    try {
      try {
        this.mTemplateManager.loadStaticTemplate(this.mContext);
      } catch (Exception e) {
        saveSWTLog(this.TAG, "loading template, error = " + e);
      }
      try {
        Iterator it = this.mPackageManager.getAllPackages().iterator();
        while (it.hasNext()) {
          AndroidPackage androidPackage =
              this.mPackageManagerInternal.getPackage((String) it.next());
          if (SemWallpaperThemeUtils.hasWallpaperThemeTemplate(androidPackage)) {
            this.mTemplateManager.update(androidPackage);
          }
        }
      } catch (Exception e2) {
        saveSWTLog(this.TAG, "initTemplateFromPkg, ex = " + e2);
      }
    } catch (Exception e3) {
      saveSWTLog(this.TAG, "failed at setTemplateAsColorTheme, ex = " + e3);
    }
  }

  public List getPalette() {
    return this.mPalette.getPaletteSS();
  }

  public boolean initTemplateMetadataStatic() {
    try {
      Trace.traceBegin(8192L, "SWT:initTemplateMetadataStatic");
      try {
        this.mTemplateManager.loadStaticTemplate(this.mContext);
        try {
          this.mMetaDataManager.loadStaticMetadata(this.mContext);
          Trace.traceEnd(8192L);
          return true;
        } catch (Exception e) {
          saveSWTLog(this.TAG, "loading metadata, error = " + e);
          return false;
        }
      } catch (Exception e2) {
        saveSWTLog(this.TAG, "loading template, error = " + e2);
        return false;
      }
    } catch (Exception e3) {
      saveSWTLog(
          this.TAG,
          "failed loading template and metadata, wallpaper theming will not working, ex:" + e3);
      return false;
    } finally {
      Trace.traceEnd(8192L);
    }
  }

  public void initTemplateMetadataFromPkg() {
    try {
      try {
        Trace.traceBegin(8192L, "SWT:initTemplateMetadataFromPkg");
        Iterator it = this.mPackageManager.getAllPackages().iterator();
        while (it.hasNext()) {
          updateTemplateMetadataFromPkg((String) it.next());
        }
      } catch (Exception e) {
        saveSWTLog(this.TAG, "initTemplateMetadataFromPkg, ex = " + e);
      }
    } finally {
      Trace.traceEnd(8192L);
    }
  }

  public void updateTemplateMetadataFromPkg(String str) {
    AndroidPackage androidPackage;
    try {
      try {
        Trace.traceBegin(8192L, "SWT:updateTemplateMetadataFromPkg, pkg:" + str);
        androidPackage = this.mPackageManagerInternal.getPackage(str);
      } catch (Exception e) {
        saveSWTLog(this.TAG, "updateTemplateMetadataFromPkg, packageName = " + str + ", ex = " + e);
      }
      if (SemWallpaperThemeUtils.hasWallpaperThemeNotSupport(androidPackage)) {
        WallpaperThemeConstants.colorThemingDisableList.add(androidPackage.getPackageName());
        return;
      }
      WallpaperThemeConstants.colorThemingDisableList.remove(androidPackage.getPackageName());
      if (SemWallpaperThemeUtils.hasWallpaperThemeTemplate(androidPackage)) {
        Log.i(this.TAG, "Update installed package's template : " + str);
        this.mTemplateManager.update(androidPackage);
      }
      if (SemWallpaperThemeUtils.hasWallpaperThemeMeta(androidPackage)) {
        Log.i(this.TAG, "Update installed package's metadata : " + str);
        this.mMetaDataManager.update(androidPackage);
      }
    } finally {
      Trace.traceEnd(8192L);
    }
  }

  public void setPermissionsDirIfExisted() {
    try {
      File file = new File("/data/overlays/wallpapertheme/");
      if (file.exists()) {
        FileUtils.setPermissions(file, 511, -1, -1);
        Log.i(this.TAG, "success to change color theme directory permissions");
      }
    } catch (Exception e) {
      Log.w(this.TAG, "failed setPermissionsDirIfExisted, e:" + e);
    }
  }

  public List readLastPalette() {
    ArrayList arrayList = new ArrayList();
    File file = new File("/data/overlays/wallpapertheme/last_palette.txt");
    if (file.exists()) {
      BufferedReader bufferedReader = null;
      try {
        BufferedReader bufferedReader2 =
            new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        while (true) {
          try {
            String readLine = bufferedReader2.readLine();
            if (readLine == null) {
              break;
            }
            arrayList.add(Integer.valueOf(Integer.parseInt(readLine.trim())));
          } catch (Exception e) {
            e = e;
            bufferedReader = bufferedReader2;
            e.printStackTrace();
            if (bufferedReader != null) {
              try {
                bufferedReader.close();
              } catch (Exception e2) {
                e2.printStackTrace();
              }
            }
            return arrayList;
          }
        }
        bufferedReader2.close();
      } catch (Exception e3) {
        e = e3;
      }
    }
    return arrayList;
  }

  public boolean splitPalette(List list, List list2, List list3) {
    if (list.size() > 65) {
      list2.addAll(list.subList(0, 65));
      list3.addAll(list.subList(65, 130));
      return ((Integer) list.get(130)).intValue() == 1;
    }
    list2.addAll(list);
    list3.addAll(list);
    return false;
  }

  public void registerThemeOverlay(OverlayManagerTransaction.Builder builder) {
    try {
      Trace.traceBegin(8192L, "SWT:registerThemeOverlay");
      generateThemeOverlay();
      Iterator it = this.mCurrentThemeOverlays.iterator();
      while (it.hasNext()) {
        builder.registerFabricatedOverlay((FabricatedOverlay) it.next());
      }
      Log.i(this.TAG, "register color theme overlay");
    } finally {
      Trace.traceEnd(8192L);
    }
  }

  public void updateThemeOverlay(OverlayManagerTransaction.Builder builder, String str, int i) {
    try {
      Trace.traceBegin(8192L, "SWT:updateThemeOverlay : " + str);
      if (i != -1) {
        FabricatedOverlay createThemeOverlay =
            this.mOverlayGenerator.createThemeOverlay("SemWT", str);
        Log.i(
            this.TAG,
            "update color theme overlay, pkg = " + str + ", overlay = " + createThemeOverlay);
        if (createThemeOverlay != null) {
          builder.registerFabricatedOverlay(createThemeOverlay);
          int i2 = 0;
          if (i == 1) {
            for (int i3 : this.mUserManager.getUserIds()) {
              builder.setEnabled(createThemeOverlay.getIdentifier(), true, i3);
            }
          }
          if (this.mCurrentThemeOverlays != null) {
            String str2 = "SemWT_" + str;
            while (true) {
              if (i2 >= this.mCurrentThemeOverlays.size()) {
                break;
              }
              if (str2.equals(
                  ((FabricatedOverlay) this.mCurrentThemeOverlays.get(i2))
                      .getIdentifier()
                      .getOverlayName())) {
                this.mCurrentThemeOverlays.remove(i2);
                Log.i(this.TAG, "Remove old one from array, pkg = " + str);
                break;
              }
              i2++;
            }
            this.mCurrentThemeOverlays.add(createThemeOverlay);
          }
        }
      }
    } finally {
      Trace.traceEnd(8192L);
    }
  }

  public void unregisterNotExistedOverlay(
      OverlayManagerTransaction.Builder builder, ArrayList arrayList) {
    boolean z;
    try {
      Trace.traceBegin(8192L, "SWT:unregisterNotExistedOverlay");
      long uptimeMillis = SystemClock.uptimeMillis();
      Iterator it = arrayList.iterator();
      while (it.hasNext()) {
        OverlayIdentifier overlayIdentifier = (OverlayIdentifier) it.next();
        String overlayName = overlayIdentifier.getOverlayName();
        if (overlayName == null
            || (!overlayName.equals("SemWT_MonetPalette")
                && !overlayName.equals("SemWT_G_MonetPalette"))) {
          Iterator it2 = this.mCurrentThemeOverlays.iterator();
          while (true) {
            if (!it2.hasNext()) {
              z = false;
              break;
            } else if (((FabricatedOverlay) it2.next()).getIdentifier().equals(overlayIdentifier)) {
              z = true;
              break;
            }
          }
          if (!z) {
            saveSWTLog(this.TAG, "unregister not existed overlay : " + overlayName);
            builder.unregisterFabricatedOverlay(overlayIdentifier);
          }
        }
      }
      Log.i(
          this.TAG,
          "unregisterNotExistedOverlay, dur:" + (SystemClock.uptimeMillis() - uptimeMillis));
    } finally {
      Trace.traceEnd(8192L);
    }
  }

  public void registerMonetOverlay(OverlayManagerTransaction.Builder builder) {
    try {
      Trace.traceBegin(8192L, "SWT:registerMonetOverlay");
      generateMonetOverlay();
      Iterator it = this.mCurrentMonetOverlays.iterator();
      while (it.hasNext()) {
        builder.registerFabricatedOverlay((FabricatedOverlay) it.next());
      }
      Log.i(this.TAG, "register palette theme overlay");
    } finally {
      Trace.traceEnd(8192L);
    }
  }

  public void restoreMonetOverlay(
      OverlayManagerTransaction.Builder builder, OverlayInfo overlayInfo) {
    try {
      Trace.traceBegin(8192L, "SWT:restoreMonetOverlay");
      if (overlayInfo != null) {
        builder.unregisterFabricatedOverlay(new OverlayIdentifier("android", "MonetPalette"));
      }
      ArrayList createMonetOverlay = this.mOverlayGenerator.createMonetOverlay();
      this.mCurrentMonetOverlays = createMonetOverlay;
      Iterator it = createMonetOverlay.iterator();
      while (it.hasNext()) {
        builder.registerFabricatedOverlay((FabricatedOverlay) it.next());
      }
      for (int i : this.mUserManager.getUserIds()) {
        builder.setEnabled(new OverlayIdentifier("android", "SemWT_MonetPalette"), true, i);
        builder.setEnabled(new OverlayIdentifier("android", "SemWT_G_MonetPalette"), true, i);
      }
    } finally {
      Trace.traceEnd(8192L);
    }
  }

  public void generateThemeOverlay() {
    try {
      Trace.traceBegin(8192L, "SWT:generateThemeOverlay");
      Log.i(this.TAG, "generate color theme overlay");
      this.mCurrentThemeOverlays = this.mOverlayGenerator.createThemeOverlays("SemWT");
    } finally {
      Trace.traceEnd(8192L);
    }
  }

  public void generateMonetOverlay() {
    try {
      Trace.traceBegin(8192L, "SWT:generateMonetOverlay");
      Log.i(this.TAG, "generate palette theme overlay");
      this.mCurrentMonetOverlays = this.mOverlayGenerator.createMonetOverlay();
      this.mPalette.writeLastPalette();
    } finally {
      Trace.traceEnd(8192L);
    }
  }

  public void enableThemeOverlay(OverlayManagerTransaction.Builder builder) {
    try {
      Trace.traceBegin(8192L, "SWT:enableThemeOverlay");
      if (this.mCurrentThemeOverlays == null) {
        generateThemeOverlay();
      }
      int[] userIds = this.mUserManager.getUserIds();
      Iterator it = this.mCurrentThemeOverlays.iterator();
      while (it.hasNext()) {
        FabricatedOverlay fabricatedOverlay = (FabricatedOverlay) it.next();
        for (int i : userIds) {
          builder.setEnabled(fabricatedOverlay.getIdentifier(), true, i);
        }
      }
      Log.i(this.TAG, "enable color theme overlay");
    } finally {
      Trace.traceEnd(8192L);
    }
  }

  public void enableMonetOverlay(OverlayManagerTransaction.Builder builder) {
    try {
      Trace.traceBegin(8192L, "SWT:enableMonetOverlay");
      if (this.mCurrentMonetOverlays == null && this.mPalette.getPaletteSS() != null) {
        generateMonetOverlay();
      }
      for (int i : this.mUserManager.getUserIds()) {
        builder.setEnabled(new OverlayIdentifier("android", "SemWT_MonetPalette"), true, i);
        builder.setEnabled(new OverlayIdentifier("android", "SemWT_G_MonetPalette"), true, i);
      }
      Log.i(this.TAG, "enable palette theme overlay");
    } finally {
      Trace.traceEnd(8192L);
    }
  }

  public void disableThemeOverlay(OverlayManagerTransaction.Builder builder) {
    try {
      Trace.traceBegin(8192L, "SWT:disableThemeOverlay");
      if (this.mCurrentThemeOverlays == null) {
        generateThemeOverlay();
      }
      int[] userIds = this.mUserManager.getUserIds();
      Iterator it = this.mCurrentThemeOverlays.iterator();
      while (it.hasNext()) {
        FabricatedOverlay fabricatedOverlay = (FabricatedOverlay) it.next();
        for (int i : userIds) {
          builder.setEnabled(fabricatedOverlay.getIdentifier(), false, i);
        }
      }
      Log.i(this.TAG, "disable color theme overlay");
    } finally {
      Trace.traceEnd(8192L);
    }
  }

  public void disableMonetOverlay(OverlayManagerTransaction.Builder builder) {
    try {
      Trace.traceBegin(8192L, "SWT:disableMonetOverlay");
      for (int i : this.mUserManager.getUserIds()) {
        builder.setEnabled(new OverlayIdentifier("android", "SemWT_MonetPalette"), false, i);
        builder.setEnabled(new OverlayIdentifier("android", "SemWT_G_MonetPalette"), false, i);
      }
      Log.i(this.TAG, "disable palette theme overlay");
    } finally {
      Trace.traceEnd(8192L);
    }
  }

  public void unregisterThemeParkOverlay(
      OverlayManagerTransaction.Builder builder, ArrayList arrayList) {
    Iterator it = arrayList.iterator();
    while (it.hasNext()) {
      builder.unregisterFabricatedOverlay((OverlayIdentifier) it.next());
    }
    Log.i(this.TAG, "[ThemePark FRRO] unregister ThemePark overlay : " + arrayList);
  }

  public void clearCurrentThemeOverlays() {
    this.mCurrentMonetOverlays = null;
    this.mCurrentThemeOverlays = null;
  }

  public void saveWallpaperThemeState(final int i) {
    try {
      for (int i2 : this.mUserManager.getUserIds()) {
        Settings.System.putIntForUser(
            this.mContext.getContentResolver(), "wallpapertheme_state", i, i2);
      }
    } catch (Exception e) {
      Log.e(this.TAG, "saveWallpaperThemeState posted delay, due: " + e);
      this.mExceptionHandler.postDelayed(
          new Runnable() { // from class:
                           // com.android.server.om.wallpapertheme.SemWallpaperThemeManager$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
              SemWallpaperThemeManager.this.lambda$saveWallpaperThemeState$0(i);
            }
          },
          1000L);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$saveWallpaperThemeState$0(int i) {
    saveWallpaperThemeState(i);
    Log.e(this.TAG, "saveWallpaperThemeState done");
  }

  public void saveWallpaperThemeColor() {
    String str;
    String str2;
    boolean z;
    if (this.mPalette.getPaletteSS() == null || this.mPalette.getPaletteGG() == null) {
      str = "";
      str2 = "";
      z = false;
    } else {
      str = this.mPalette.getPaletteSS().toString();
      str2 = this.mPalette.getPaletteGG().toString();
      z = this.mPalette.mIsGray;
    }
    try {
      ContentResolver contentResolver = this.mContext.getContentResolver();
      for (int i : this.mUserManager.getUserIds()) {
        Settings.System.putStringForUser(contentResolver, "wallpapertheme_color", str, i);
        Settings.System.putStringForUser(contentResolver, "wallpapertheme_color_for_g", str2, i);
        Settings.System.putStringForUser(
            contentResolver, "wallpapertheme_color_isgray", z ? "1" : "0", i);
      }
    } catch (Exception e) {
      Log.e(this.TAG, "saveWallpaperThemeColor posted delay, due: " + e);
      this.mExceptionHandler.postDelayed(
          new Runnable() { // from class:
                           // com.android.server.om.wallpapertheme.SemWallpaperThemeManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              SemWallpaperThemeManager.this.lambda$saveWallpaperThemeColor$1();
            }
          },
          1000L);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$saveWallpaperThemeColor$1() {
    saveWallpaperThemeColor();
    Log.e(this.TAG, "saveWallpaperThemeColor done");
  }

  public void applyDynamicColor() {
    JSONObject dynamicColorPalette =
        setDynamicColorPalette(getDynamicSeedColor(), getDynamicPaletteType());
    try {
      ContentResolver contentResolver = this.mContext.getContentResolver();
      for (int i : this.mUserManager.getUserIds()) {
        Settings.Secure.putStringForUser(
            contentResolver,
            "theme_customization_overlay_packages",
            dynamicColorPalette.toString(),
            i);
      }
    } catch (Exception e) {
      Log.e(this.TAG, "applyDynamicColor posted delay, due: " + e);
      this.mExceptionHandler.postDelayed(
          new Runnable() { // from class:
                           // com.android.server.om.wallpapertheme.SemWallpaperThemeManager$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
              SemWallpaperThemeManager.this.lambda$applyDynamicColor$2();
            }
          },
          1000L);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$applyDynamicColor$2() {
    applyDynamicColor();
    Log.e(this.TAG, "applyDynamicColor done");
  }

  public final JSONObject setDynamicColorPalette(String str, String str2) {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("_applied_timestamp", new Timestamp(System.currentTimeMillis()).getTime());
      jSONObject.put("android.theme.customization.color_index", "0");
      jSONObject.put("android.theme.customization.system_palette", str);
      jSONObject.put("android.theme.customization.accent_color", str);
      jSONObject.put("android.theme.customization.color_source", "preset");
      jSONObject.put("android.theme.customization.theme_style", str2);
    } catch (JSONException e) {
      Log.e(this.TAG, e.getMessage());
    }
    return jSONObject;
  }

  public final String getDynamicSeedColor() {
    ThemePalette themePalette = this.mPalette;
    if (themePalette.mIsGray) {
      return Integer.toHexString(themePalette.getMonetColorSS(0, 8));
    }
    return Integer.toHexString(themePalette.getMonetColorSS(0, 5));
  }

  public final String getDynamicPaletteType() {
    return this.mPalette.mIsGray ? "MONOCHROMATIC" : "TONAL_SPOT";
  }

  public void syncWallpaperThemeStateForUser(final int i) {
    try {
      ContentResolver contentResolver = this.mContext.getContentResolver();
      int i2 = Settings.System.getInt(contentResolver, "wallpapertheme_state", -1);
      if (i2 == 1) {
        String string = Settings.System.getString(contentResolver, "wallpapertheme_color");
        String string2 = Settings.System.getString(contentResolver, "wallpapertheme_color_for_g");
        String string3 = Settings.System.getString(contentResolver, "wallpapertheme_color_isgray");
        Settings.System.putIntForUser(contentResolver, "wallpapertheme_state", i2, i);
        if (string != null && string2 != null && string3 != null) {
          Settings.System.putStringForUser(contentResolver, "wallpapertheme_color", string, i);
          Settings.System.putStringForUser(
              contentResolver, "wallpapertheme_color_for_g", string2, i);
          Settings.System.putStringForUser(
              contentResolver, "wallpapertheme_color_isgray", string3, i);
        }
        Log.i(this.TAG, "syncWallpaperThemeStateForUser for user:" + i + ", state:" + i2);
      }
    } catch (Exception e) {
      Log.e(this.TAG, "syncWallpaperThemeStateForUser posted delay, due: " + e);
      this.mExceptionHandler.postDelayed(
          new Runnable() { // from class:
                           // com.android.server.om.wallpapertheme.SemWallpaperThemeManager$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
              SemWallpaperThemeManager.this.lambda$syncWallpaperThemeStateForUser$3(i);
            }
          },
          1000L);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$syncWallpaperThemeStateForUser$3(int i) {
    syncWallpaperThemeStateForUser(i);
    Log.e(this.TAG, "syncWallpaperThemeStateForUser done");
  }

  public void dumpInDetail(PrintWriter printWriter) {
    printWriter.println("<ColorPalette Information>");
    this.mMetaDataManager.dump(printWriter);
    this.mTemplateManager.dump(printWriter);
  }

  public static void saveSWTLog(String str, String str2) {
    LogWrapper.save(str, str2);
  }

  public StringBuilder getLogText() {
    return LogWrapper.getLogText();
  }

  public abstract class LogWrapper {
    public static FileHandler fileHandler;
    public static Logger logger;
    public static final SimpleDateFormat formatter =
        new SimpleDateFormat("MM-dd HH:mm:ss.SSS: ", Locale.getDefault());
    public static final Date date = new Date();

    static {
      try {
        FileHandler fileHandler2 =
            new FileHandler("/data/log/color_palette_log%g.txt", 5120, 2, true);
        fileHandler = fileHandler2;
        fileHandler2.setFormatter(
            new Formatter() { // from class:
                              // com.android.server.om.wallpapertheme.SemWallpaperThemeManager.LogWrapper.1
              @Override // java.util.logging.Formatter
              public String format(LogRecord logRecord) {
                LogWrapper.date.setTime(System.currentTimeMillis());
                StringBuilder sb = new StringBuilder(80);
                sb.append(LogWrapper.formatter.format(LogWrapper.date));
                sb.append(logRecord.getMessage());
                return sb.toString();
              }
            });
        Logger logger2 = Logger.getLogger(LogWrapper.class.getName());
        logger = logger2;
        logger2.addHandler(fileHandler);
        logger.setLevel(Level.ALL);
        logger.setUseParentHandlers(false);
      } catch (Exception e) {
        Log.i("LogWrapper", "Can not use LogWrapper " + e.toString());
      }
    }

    public static void save(String str, String str2) {
      Logger logger2 = logger;
      if (logger2 != null) {
        logger2.log(
            Level.INFO,
            String.format("V %s(%d): %s%n", str, Integer.valueOf(Binder.getCallingPid()), str2));
      }
      Log.i(str, str2);
    }

    public static StringBuilder getLogText() {
      File[] fileArr = {
        new File("/data/log/color_palette_log0.txt"), new File("/data/log/color_palette_log1.txt")
      };
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < 2; i++) {
        File file = fileArr[i];
        if (file.exists()) {
          try {
            BufferedReader bufferedReader =
                new BufferedReader(new FileReader(file, StandardCharsets.UTF_8));
            while (true) {
              try {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                  break;
                }
                sb.append(readLine);
                sb.append('\n');
              } finally {
              }
            }
            bufferedReader.close();
            sb.append('\n');
          } catch (IOException e) {
            Log.e("LogWrapper", "Can not use getLogText : " + e);
            return null;
          }
        }
      }
      return sb;
    }
  }
}
