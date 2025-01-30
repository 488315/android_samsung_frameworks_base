package com.android.server.om.wallpapertheme;

import android.content.Context;
import android.content.om.IOverlayManager;
import android.content.om.OverlayIdentifier;
import android.content.om.OverlayInfo;
import android.content.om.OverlayManagerTransaction;
import android.net.Uri;
import android.os.Binder;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import com.android.server.om.OverlayManagerSettingsHelper;
import com.android.server.om.OverlayPolicyManager;
import com.samsung.android.rune.PMRune;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class SemWallpaperThemeManagerWrapper implements ISemWallpaperThemeManager {
    public static SemWallpaperThemeManagerWrapper instance;
    public static Context mContext;
    public static OverlayIdentifier sColorThemeOverlayId = new OverlayIdentifier("android", "SemWT_G_MonetPalette");
    public static int sGlobalColorThemeSeq = 0;
    public OverlayManagerSettingsHelper mSettings;
    public boolean mIsSuccessLoadMetadata = false;
    public boolean mIsThemeParkApplied = false;
    public SemWallpaperThemeManager mWallpaperThemeManager = SemWallpaperThemeManager.getInstance(mContext);

    public SemWallpaperThemeManagerWrapper() {
        OverlayPolicyManager.registerPolicy(new SemWallpaperThemeOverlayPolicy(this.mWallpaperThemeManager));
    }

    public static synchronized ISemWallpaperThemeManager getInstance(Context context, OverlayManagerSettingsHelper overlayManagerSettingsHelper) {
        SemWallpaperThemeManagerWrapper semWallpaperThemeManagerWrapper;
        synchronized (SemWallpaperThemeManagerWrapper.class) {
            mContext = context;
            if (instance == null) {
                SemWallpaperThemeManagerWrapper semWallpaperThemeManagerWrapper2 = new SemWallpaperThemeManagerWrapper();
                instance = semWallpaperThemeManagerWrapper2;
                semWallpaperThemeManagerWrapper2.mSettings = overlayManagerSettingsHelper;
            }
            semWallpaperThemeManagerWrapper = instance;
        }
        return semWallpaperThemeManagerWrapper;
    }

    public final void initTemplateMetadataIfNeeded() {
        if (this.mIsSuccessLoadMetadata) {
            return;
        }
        this.mWallpaperThemeManager.initTemplateMetadataStatic();
        this.mWallpaperThemeManager.initTemplateMetadataFromPkg();
        this.mIsSuccessLoadMetadata = true;
    }

    @Override // com.android.server.om.wallpapertheme.ISemWallpaperThemeManager
    public void initWallpaperTheme() {
        try {
            long uptimeMillis = SystemClock.uptimeMillis();
            if (getColorThemeState() == 1) {
                initTemplateMetadataIfNeeded();
                Slog.d("SWT_WTM_Wrapper", "init color theme metadata has finished : " + (SystemClock.uptimeMillis() - uptimeMillis) + "ms");
                List readLastPalette = this.mWallpaperThemeManager.readLastPalette();
                if (!readLastPalette.isEmpty()) {
                    Slog.i("SWT_WTM_Wrapper", "restore palette overlays");
                    ArrayList arrayList = new ArrayList();
                    ArrayList arrayList2 = new ArrayList();
                    this.mWallpaperThemeManager.setPalette(arrayList, arrayList2, this.mWallpaperThemeManager.splitPalette(readLastPalette, arrayList, arrayList2));
                }
                if (mContext.getPackageManager().isDeviceUpgrading()) {
                    OverlayManagerTransaction.Builder builder = new OverlayManagerTransaction.Builder();
                    this.mWallpaperThemeManager.restoreMonetOverlay(builder, this.mSettings.getNullableOverlayInfo(new OverlayIdentifier("android", "MonetPalette"), 0));
                    Slog.i("SWT_WTM_Wrapper", "regenerate color theme overlays");
                    this.mWallpaperThemeManager.registerThemeOverlay(builder);
                    this.mWallpaperThemeManager.enableThemeOverlay(builder);
                    Set<Pair> allIdentifiersAndBaseCodePaths = this.mSettings.getAllIdentifiersAndBaseCodePaths();
                    ArrayList arrayList3 = new ArrayList();
                    for (Pair pair : allIdentifiersAndBaseCodePaths) {
                        if (((OverlayIdentifier) pair.first).getOverlayName() != null && ((OverlayIdentifier) pair.first).getOverlayName().startsWith("SemWT_")) {
                            arrayList3.add((OverlayIdentifier) pair.first);
                        }
                    }
                    if (!arrayList3.isEmpty()) {
                        Slog.i("SWT_WTM_Wrapper", "unregister unnecessary color theme overlays for fota upgrade");
                        this.mWallpaperThemeManager.unregisterNotExistedOverlay(builder, arrayList3);
                    }
                    this.mWallpaperThemeManager.setPermissionsDirIfExisted();
                    long uptimeMillis2 = SystemClock.uptimeMillis();
                    IOverlayManager.Stub.asInterface(ServiceManager.getService("overlay")).commit(builder.build());
                    Slog.d("SWT_WTM_Wrapper", "restoring color theme has finished, ready:" + (uptimeMillis2 - uptimeMillis) + "ms, commit:" + (SystemClock.uptimeMillis() - uptimeMillis2) + "ms");
                }
            }
        } catch (Exception e) {
            Slog.e("SWT_WTM_Wrapper", "failed initWallpaperTheme, wallpaper theming will not working, ex = " + e);
        }
    }

    @Override // com.android.server.om.wallpapertheme.ISemWallpaperThemeManager
    public void onPackageAdded(String str) {
        int colorThemeState = getColorThemeState();
        if (colorThemeState != -1) {
            try {
                initTemplateMetadataIfNeeded();
                this.mWallpaperThemeManager.updateTemplateMetadataFromPkg(str);
                OverlayManagerTransaction.Builder builder = new OverlayManagerTransaction.Builder();
                this.mWallpaperThemeManager.updateThemeOverlay(builder, str, colorThemeState);
                IOverlayManager.Stub.asInterface(ServiceManager.getService("overlay")).commit(builder.build());
            } catch (Exception e) {
                Log.e("SWT_WTM_Wrapper", "FAILED at commit for packageAdded, e=" + e);
            }
        }
    }

    @Override // com.android.server.om.wallpapertheme.ISemWallpaperThemeManager
    public void onUserAdded(int i) {
        this.mWallpaperThemeManager.syncWallpaperThemeStateForUser(i);
    }

    @Override // com.android.server.om.wallpapertheme.ISemWallpaperThemeManager
    public boolean isRequestForColorTheme(OverlayManagerTransaction overlayManagerTransaction) {
        OverlayIdentifier overlayIdentifier;
        if (overlayManagerTransaction.getRequests() == null || !overlayManagerTransaction.getRequests().hasNext()) {
            return false;
        }
        OverlayManagerTransaction.Request request = (OverlayManagerTransaction.Request) overlayManagerTransaction.getRequests().next();
        String overlayName = (request == null || (overlayIdentifier = request.overlay) == null) ? null : overlayIdentifier.getOverlayName();
        return overlayName != null && overlayName.startsWith("SemWT_");
    }

    @Override // com.android.server.om.wallpapertheme.ISemWallpaperThemeManager
    public void applyWallpaperColors(List list, int i, int i2) {
        Log.i("SWT_WTM_Wrapper", "(Deprecated) invoked applyWallpaperColors, colors=" + list);
        applyWallpaperColor(list, list, false, false);
    }

    @Override // com.android.server.om.wallpapertheme.ISemWallpaperThemeManager
    public void applyWallpaperColor(List list, List list2, boolean z) {
        applyWallpaperColor(list, list2, z, false);
    }

    public void applyWallpaperColor(List list, List list2, boolean z, boolean z2) {
        int callingUid = Binder.getCallingUid();
        if (mContext.getPackageManager().checkSignatures(1000, callingUid) != 0) {
            SemWallpaperThemeManager.saveSWTLog("SWT_WTM_Wrapper", "applyWallpaperColor called by abnormal uid : " + callingUid);
            return;
        }
        Log.i("SWT_WTM_Wrapper", "invoked applyWallpaperColor, isGray=" + z + ", colorSS=" + list + ", colorGG=" + list2);
        try {
            try {
                Trace.traceBegin(8192L, "SWT:applyWallpaperColors, wallpaperColors:" + list);
                initTemplateMetadataIfNeeded();
                if (!z2 && this.mIsThemeParkApplied) {
                    this.mWallpaperThemeManager.setTemplateAsColorTheme();
                    this.mIsThemeParkApplied = false;
                }
                if (list != null) {
                    long uptimeMillis = SystemClock.uptimeMillis();
                    this.mWallpaperThemeManager.setPalette(list, list2, z);
                    OverlayManagerTransaction.Builder builder = new OverlayManagerTransaction.Builder();
                    this.mWallpaperThemeManager.registerMonetOverlay(builder);
                    this.mWallpaperThemeManager.registerThemeOverlay(builder);
                    this.mWallpaperThemeManager.enableMonetOverlay(builder);
                    this.mWallpaperThemeManager.enableThemeOverlay(builder);
                    unregisterThemeParkOverlays(builder);
                    try {
                        increaseColorThemeSeq();
                        long uptimeMillis2 = SystemClock.uptimeMillis();
                        IOverlayManager.Stub.asInterface(ServiceManager.getService("overlay")).commit(builder.build());
                        this.mWallpaperThemeManager.saveWallpaperThemeColor();
                        this.mWallpaperThemeManager.applyDynamicColor();
                        this.mWallpaperThemeManager.saveWallpaperThemeState(1);
                        SemWallpaperThemeManager.saveSWTLog("SWT_WTM_Wrapper", "[Enable] commit OverlayManagerTransaction, dur1:" + (uptimeMillis2 - uptimeMillis) + ", dur2:" + (SystemClock.uptimeMillis() - uptimeMillis2));
                    } catch (Exception e) {
                        SemWallpaperThemeManager.saveSWTLog("SWT_WTM_Wrapper", "FAILED at commit, e=" + e);
                        this.mWallpaperThemeManager.clearCurrentThemeOverlays();
                    }
                    if (!z2) {
                        setStateAsColorTheme();
                    }
                } else {
                    this.mWallpaperThemeManager.setPalette(list, list2, z);
                    OverlayManagerTransaction.Builder builder2 = new OverlayManagerTransaction.Builder();
                    this.mWallpaperThemeManager.disableMonetOverlay(builder2);
                    this.mWallpaperThemeManager.disableThemeOverlay(builder2);
                    unregisterThemeParkOverlays(builder2);
                    try {
                        increaseColorThemeSeq();
                        long uptimeMillis3 = SystemClock.uptimeMillis();
                        IOverlayManager.Stub.asInterface(ServiceManager.getService("overlay")).commit(builder2.build());
                        this.mWallpaperThemeManager.saveWallpaperThemeColor();
                        this.mWallpaperThemeManager.saveWallpaperThemeState(0);
                        SemWallpaperThemeManager.saveSWTLog("SWT_WTM_Wrapper", "[Disable] commit OverlayManagerTransaction, dur:" + (SystemClock.uptimeMillis() - uptimeMillis3));
                    } catch (Exception e2) {
                        SemWallpaperThemeManager.saveSWTLog("SWT_WTM_Wrapper", "FAILED at commit, e=" + e2);
                        this.mWallpaperThemeManager.clearCurrentThemeOverlays();
                    }
                    setStateAsColorTheme();
                }
            } catch (Exception e3) {
                Log.e("SWT_WTM_Wrapper", "FAILED at applyWallpaperColor, e=" + e3);
                this.mWallpaperThemeManager.clearCurrentThemeOverlays();
            }
        } finally {
            Trace.traceEnd(8192L);
        }
    }

    @Override // com.android.server.om.wallpapertheme.ISemWallpaperThemeManager
    public List getWallpaperColors() {
        return this.mWallpaperThemeManager.getPalette();
    }

    @Override // com.android.server.om.wallpapertheme.ISemWallpaperThemeManager
    public List readLastPalette() {
        List readLastPalette = this.mWallpaperThemeManager.readLastPalette();
        if (readLastPalette.isEmpty()) {
            return null;
        }
        return readLastPalette.subList(0, 65);
    }

    @Override // com.android.server.om.wallpapertheme.ISemWallpaperThemeManager
    public boolean getLastPalette(List list, List list2) {
        List readLastPalette = this.mWallpaperThemeManager.readLastPalette();
        if (readLastPalette.isEmpty()) {
            return false;
        }
        return this.mWallpaperThemeManager.splitPalette(readLastPalette, list, list2);
    }

    @Override // com.android.server.om.wallpapertheme.ISemWallpaperThemeManager
    public void dump(PrintWriter printWriter) {
        printWriter.println("\n Color palette history :");
        printWriter.println(this.mWallpaperThemeManager.getLogText());
        if (PMRune.THEME_WALLPAPER_THEMING_DEBUG) {
            this.mWallpaperThemeManager.dumpInDetail(printWriter);
        }
    }

    @Override // com.android.server.om.wallpapertheme.ISemWallpaperThemeManager
    public boolean isColoThemeApplied() {
        return getColorThemeState() == 1;
    }

    public final int getColorThemeState() {
        OverlayInfo nullableOverlayInfo = this.mSettings.getNullableOverlayInfo(sColorThemeOverlayId, 0);
        if (nullableOverlayInfo != null) {
            nullableOverlayInfo.isEnabled();
        }
        if (nullableOverlayInfo == null) {
            return -1;
        }
        return nullableOverlayInfo.isEnabled() ? 1 : 0;
    }

    public final void increaseColorThemeSeq() {
        int i = sGlobalColorThemeSeq + 1;
        sGlobalColorThemeSeq = i;
        sGlobalColorThemeSeq = Math.max(i, 1);
        SystemProperties.set("debug.wallpaper.theme.seq", "" + sGlobalColorThemeSeq);
    }

    @Override // com.android.server.om.wallpapertheme.ISemWallpaperThemeManager
    public void applyThemeParkWallpaperColor(Uri uri) {
        initTemplateMetadataIfNeeded();
        this.mWallpaperThemeManager.setTemplate(uri);
        try {
            InputStream openInputStream = mContext.getContentResolver().openInputStream(uri);
            try {
                ArrayList arrayList = new ArrayList();
                JSONArray jSONArray = new JSONObject(readFromInputStream(openInputStream)).getJSONArray("wallpaperColors");
                for (int i = 0; i < jSONArray.length(); i++) {
                    arrayList.add(Integer.valueOf(jSONArray.optInt(i)));
                }
                applyWallpaperColor(arrayList, arrayList, false, true);
                this.mIsThemeParkApplied = true;
                if (openInputStream != null) {
                    openInputStream.close();
                }
            } finally {
            }
        } catch (Exception e) {
            Log.e("SWT_WTM_Wrapper", "Failed at applyThemeParkWallpaperColor, e = ", e);
        }
    }

    public final String readFromInputStream(InputStream inputStream) {
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

    public final void setStateAsColorTheme() {
        try {
            File file = new File("/data/overlays/themepark/state_applied.txt");
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            Log.e("SWT_WTM_Wrapper", "Failed to delete themepark state file, e = ", e);
        }
    }

    public final void unregisterThemeParkOverlays(OverlayManagerTransaction.Builder builder) {
        Set<Pair> allIdentifiersAndBaseCodePaths = this.mSettings.getAllIdentifiersAndBaseCodePaths();
        ArrayList arrayList = new ArrayList();
        for (Pair pair : allIdentifiersAndBaseCodePaths) {
            if (((OverlayIdentifier) pair.first).getOverlayName() != null && ((OverlayIdentifier) pair.first).getOverlayName().startsWith("ThemePark_")) {
                arrayList.add((OverlayIdentifier) pair.first);
            }
        }
        if (arrayList.isEmpty()) {
            return;
        }
        this.mWallpaperThemeManager.unregisterThemeParkOverlay(builder, arrayList);
    }
}
