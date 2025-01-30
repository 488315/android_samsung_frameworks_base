package com.android.server.wallpaper;

import android.R;
import android.app.SemWallpaperResourcesInfo;
import android.app.WallpaperColors;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.FileUtils;
import android.text.TextUtils;
import android.util.Slog;
import android.util.SparseArray;
import android.util.Xml;
import com.android.internal.util.JournaledFile;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.display.DisplayPowerController2;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.wallpaper.WallpaperDisplayHelper;
import com.samsung.android.wallpaper.Rune;
import com.samsung.android.wallpaper.utils.WallpaperExtraBundleHelper;
import com.samsung.android.wallpaper.utils.WhichChecker;
import com.samsung.server.wallpaper.Log;
import com.samsung.server.wallpaper.SemWallpaperManagerService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import libcore.io.IoUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes3.dex */
public class WallpaperDataParser {
    public static final String TAG = "WallpaperDataParser";
    public final Context mContext;
    public final ComponentName mImageWallpaper;
    public final boolean mIsLockscreenLiveWallpaperEnabled;
    public SemWallpaperManagerService mSemService;
    public SemWallpaperResourcesInfo mSemWallpaperResourcesInfo;
    public final WallpaperCropper mWallpaperCropper;
    public final WallpaperDisplayHelper mWallpaperDisplayHelper;

    public WallpaperDataParser(Context context, WallpaperDisplayHelper wallpaperDisplayHelper, WallpaperCropper wallpaperCropper, boolean z, SemWallpaperManagerService semWallpaperManagerService, SemWallpaperResourcesInfo semWallpaperResourcesInfo) {
        this.mContext = context;
        this.mWallpaperDisplayHelper = wallpaperDisplayHelper;
        this.mWallpaperCropper = wallpaperCropper;
        this.mImageWallpaper = ComponentName.unflattenFromString(context.getResources().getString(R.string.mime_type_presentation));
        this.mIsLockscreenLiveWallpaperEnabled = z;
        this.mSemService = semWallpaperManagerService;
        this.mSemWallpaperResourcesInfo = semWallpaperResourcesInfo;
    }

    public JournaledFile makeJournaledFile(int i, int i2) {
        String absolutePath = new File(WallpaperUtils.getWallpaperDir(i), SemWallpaperManagerService.getFileName(i2, 2, 0)).getAbsolutePath();
        return new JournaledFile(new File(absolutePath), new File(absolutePath + ".tmp"));
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x012a  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0135  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x012c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean isSameWithPreviousWallpaper(WallpaperData wallpaperData, int i) {
        FileInputStream fileInputStream;
        int next;
        int i2 = wallpaperData.userId;
        File chooseForRead = makeJournaledFile(i2, i).chooseForRead();
        if (!chooseForRead.exists()) {
            Slog.v(TAG, "isSameWithPreviousWallpaper file is not exists.");
            return false;
        }
        WallpaperData wallpaperData2 = new WallpaperData(i2, WallpaperUtils.getWallpaperDir(i2), "wallpaper_orig", "wallpaper");
        try {
            fileInputStream = new FileInputStream(chooseForRead);
            try {
                TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(fileInputStream);
                do {
                    next = resolvePullParser.next();
                    if (next == 2 && "wp".equals(resolvePullParser.getName())) {
                        wallpaperData2.mSemWallpaperData.setWidth(Integer.parseInt(resolvePullParser.getAttributeValue((String) null, "width")));
                        wallpaperData2.mSemWallpaperData.setHeight(Integer.parseInt(resolvePullParser.getAttributeValue((String) null, "height")));
                        wallpaperData2.name = resolvePullParser.getAttributeValue((String) null, "name");
                        String attributeValue = resolvePullParser.getAttributeValue((String) null, "component");
                        wallpaperData2.wallpaperComponent = attributeValue != null ? ComponentName.unflattenFromString(attributeValue) : null;
                    }
                } while (next != 1);
            } catch (IOException e) {
                e = e;
                Slog.w(TAG, "failed parsing " + chooseForRead + " " + e);
                IoUtils.closeQuietly(fileInputStream);
                ComponentName componentName = wallpaperData2.wallpaperComponent;
                if (componentName != null) {
                }
                ComponentName componentName2 = wallpaperData.wallpaperComponent;
                if (componentName2 != null) {
                }
                String str = TAG;
                Slog.d(str, ".xml file : width=" + wallpaperData2.mSemWallpaperData.getWidth() + " height=" + wallpaperData2.mSemWallpaperData.getHeight() + " name=" + wallpaperData2.name + " component=" + r8);
                Slog.d(str, "service : width=" + wallpaperData.mSemWallpaperData.getWidth() + " height=" + wallpaperData.mSemWallpaperData.getHeight() + " name=" + wallpaperData.name + " component=" + r2);
                return wallpaperData2.isSameWallpaperData(wallpaperData);
            } catch (IndexOutOfBoundsException e2) {
                e = e2;
                Slog.w(TAG, "failed parsing " + chooseForRead + " " + e);
                IoUtils.closeQuietly(fileInputStream);
                ComponentName componentName3 = wallpaperData2.wallpaperComponent;
                if (componentName3 != null) {
                }
                ComponentName componentName22 = wallpaperData.wallpaperComponent;
                if (componentName22 != null) {
                }
                String str2 = TAG;
                Slog.d(str2, ".xml file : width=" + wallpaperData2.mSemWallpaperData.getWidth() + " height=" + wallpaperData2.mSemWallpaperData.getHeight() + " name=" + wallpaperData2.name + " component=" + r8);
                Slog.d(str2, "service : width=" + wallpaperData.mSemWallpaperData.getWidth() + " height=" + wallpaperData.mSemWallpaperData.getHeight() + " name=" + wallpaperData.name + " component=" + r2);
                return wallpaperData2.isSameWallpaperData(wallpaperData);
            } catch (NullPointerException e3) {
                e = e3;
                Slog.w(TAG, "failed parsing " + chooseForRead + " " + e);
                IoUtils.closeQuietly(fileInputStream);
                ComponentName componentName32 = wallpaperData2.wallpaperComponent;
                if (componentName32 != null) {
                }
                ComponentName componentName222 = wallpaperData.wallpaperComponent;
                if (componentName222 != null) {
                }
                String str22 = TAG;
                Slog.d(str22, ".xml file : width=" + wallpaperData2.mSemWallpaperData.getWidth() + " height=" + wallpaperData2.mSemWallpaperData.getHeight() + " name=" + wallpaperData2.name + " component=" + r8);
                Slog.d(str22, "service : width=" + wallpaperData.mSemWallpaperData.getWidth() + " height=" + wallpaperData.mSemWallpaperData.getHeight() + " name=" + wallpaperData.name + " component=" + r2);
                return wallpaperData2.isSameWallpaperData(wallpaperData);
            } catch (NumberFormatException e4) {
                e = e4;
                Slog.w(TAG, "failed parsing " + chooseForRead + " " + e);
                IoUtils.closeQuietly(fileInputStream);
                ComponentName componentName322 = wallpaperData2.wallpaperComponent;
                if (componentName322 != null) {
                }
                ComponentName componentName2222 = wallpaperData.wallpaperComponent;
                if (componentName2222 != null) {
                }
                String str222 = TAG;
                Slog.d(str222, ".xml file : width=" + wallpaperData2.mSemWallpaperData.getWidth() + " height=" + wallpaperData2.mSemWallpaperData.getHeight() + " name=" + wallpaperData2.name + " component=" + r8);
                Slog.d(str222, "service : width=" + wallpaperData.mSemWallpaperData.getWidth() + " height=" + wallpaperData.mSemWallpaperData.getHeight() + " name=" + wallpaperData.name + " component=" + r2);
                return wallpaperData2.isSameWallpaperData(wallpaperData);
            } catch (XmlPullParserException e5) {
                e = e5;
                Slog.w(TAG, "failed parsing " + chooseForRead + " " + e);
                IoUtils.closeQuietly(fileInputStream);
                ComponentName componentName3222 = wallpaperData2.wallpaperComponent;
                if (componentName3222 != null) {
                }
                ComponentName componentName22222 = wallpaperData.wallpaperComponent;
                if (componentName22222 != null) {
                }
                String str2222 = TAG;
                Slog.d(str2222, ".xml file : width=" + wallpaperData2.mSemWallpaperData.getWidth() + " height=" + wallpaperData2.mSemWallpaperData.getHeight() + " name=" + wallpaperData2.name + " component=" + r8);
                Slog.d(str2222, "service : width=" + wallpaperData.mSemWallpaperData.getWidth() + " height=" + wallpaperData.mSemWallpaperData.getHeight() + " name=" + wallpaperData.name + " component=" + r2);
                return wallpaperData2.isSameWallpaperData(wallpaperData);
            }
        } catch (IOException e6) {
            e = e6;
            fileInputStream = null;
        } catch (IndexOutOfBoundsException e7) {
            e = e7;
            fileInputStream = null;
        } catch (NullPointerException e8) {
            e = e8;
            fileInputStream = null;
        } catch (NumberFormatException e9) {
            e = e9;
            fileInputStream = null;
        } catch (XmlPullParserException e10) {
            e = e10;
            fileInputStream = null;
        }
        IoUtils.closeQuietly(fileInputStream);
        ComponentName componentName32222 = wallpaperData2.wallpaperComponent;
        String componentName4 = componentName32222 != null ? null : componentName32222.toString();
        ComponentName componentName222222 = wallpaperData.wallpaperComponent;
        String componentName5 = componentName222222 != null ? componentName222222.toString() : null;
        String str22222 = TAG;
        Slog.d(str22222, ".xml file : width=" + wallpaperData2.mSemWallpaperData.getWidth() + " height=" + wallpaperData2.mSemWallpaperData.getHeight() + " name=" + wallpaperData2.name + " component=" + componentName4);
        Slog.d(str22222, "service : width=" + wallpaperData.mSemWallpaperData.getWidth() + " height=" + wallpaperData.mSemWallpaperData.getHeight() + " name=" + wallpaperData.name + " component=" + componentName5);
        return wallpaperData2.isSameWallpaperData(wallpaperData);
    }

    public class WallpaperLoadingResult {
        public final WallpaperData mLockWallpaperData;
        public final boolean mSuccess;
        public final WallpaperData mSystemWallpaperData;

        public WallpaperLoadingResult(WallpaperData wallpaperData, WallpaperData wallpaperData2, boolean z) {
            this.mSystemWallpaperData = wallpaperData;
            this.mLockWallpaperData = wallpaperData2;
            this.mSuccess = z;
        }

        public WallpaperData getSystemWallpaperData() {
            return this.mSystemWallpaperData;
        }

        public WallpaperData getLockWallpaperData() {
            return this.mLockWallpaperData;
        }

        public boolean success() {
            return this.mSuccess;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x05f8  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0627  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0623  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public WallpaperLoadingResult loadSettingsLocked(int i, boolean z, WallpaperData wallpaperData, WallpaperData wallpaperData2, int i2, int i3) {
        WallpaperData wallpaperData3;
        WallpaperData wallpaperData4;
        WallpaperData wallpaperData5;
        WallpaperData wallpaperData6;
        WallpaperDisplayHelper.DisplayData displayData;
        boolean z2;
        boolean z3;
        int i4;
        String str;
        WallpaperData wallpaperData7;
        XmlPullParserException xmlPullParserException;
        String str2;
        NumberFormatException numberFormatException;
        String str3;
        NullPointerException nullPointerException;
        String str4;
        IOException iOException;
        String str5;
        File file;
        IndexOutOfBoundsException indexOutOfBoundsException;
        FileInputStream fileInputStream;
        WallpaperData wallpaperData8;
        boolean z4;
        WallpaperData wallpaperData9;
        String name;
        String str6;
        int i5;
        WallpaperData wallpaperData10;
        String str7 = "wp";
        String str8 = " ";
        File chooseForRead = makeJournaledFile(i, i3).chooseForRead();
        boolean z5 = this.mIsLockscreenLiveWallpaperEnabled;
        boolean z6 = (z5 && (i2 & 1) == 0) ? false : true;
        boolean z7 = (z5 && (i2 & 2) == 0) ? false : true;
        if (z5) {
            wallpaperData3 = null;
            wallpaperData4 = null;
        } else {
            wallpaperData3 = wallpaperData;
            wallpaperData4 = wallpaperData2;
        }
        if (wallpaperData3 == null && z6) {
            File wallpaperDir = WallpaperUtils.getWallpaperDir(i);
            String fileName = SemWallpaperManagerService.getFileName(i3, 0, 0);
            String fileName2 = SemWallpaperManagerService.getFileName(i3, 0, 1);
            String str9 = TAG;
            StringBuilder sb = new StringBuilder();
            wallpaperData5 = wallpaperData4;
            sb.append("loadSettingsLocked: oriFileName : ");
            sb.append(fileName);
            Log.m129d(str9, sb.toString());
            Log.m129d(str9, "loadSettingsLocked: cropFileName : " + fileName2);
            WallpaperData wallpaperData11 = new WallpaperData(i, wallpaperDir, fileName, fileName2);
            wallpaperData11.allowBackup = true;
            wallpaperData11.setWhich(i3 | 1);
            wallpaperData3 = wallpaperData11;
        } else {
            wallpaperData5 = wallpaperData4;
        }
        WallpaperDisplayHelper.DisplayData displayDataOrCreate = this.mWallpaperDisplayHelper.getDisplayDataOrCreate(0);
        try {
            try {
            } catch (FileNotFoundException unused) {
                wallpaperData6 = wallpaperData3;
                displayData = displayDataOrCreate;
                z2 = z6;
                z3 = z7;
                i4 = 1;
                wallpaperData7 = wallpaperData5;
                fileInputStream = null;
            } catch (IndexOutOfBoundsException e) {
                str5 = " ";
                file = chooseForRead;
                wallpaperData6 = wallpaperData3;
                displayData = displayDataOrCreate;
                z2 = z6;
                z3 = z7;
                i4 = 1;
                wallpaperData7 = wallpaperData5;
                indexOutOfBoundsException = e;
                fileInputStream = null;
            }
            try {
                fileInputStream = new FileInputStream(chooseForRead);
                try {
                    XmlPullParser resolvePullParser = Xml.resolvePullParser(fileInputStream);
                    wallpaperData7 = wallpaperData5;
                    while (true) {
                        try {
                            int next = resolvePullParser.next();
                            FileInputStream fileInputStream2 = fileInputStream;
                            if (next == 2) {
                                try {
                                    name = resolvePullParser.getName();
                                    wallpaperData6 = wallpaperData3;
                                    displayData = displayDataOrCreate;
                                    str5 = str8;
                                    if (str7.equals(name) && z6) {
                                        z2 = z6;
                                    } else {
                                        try {
                                            if ("kwp".equals(name)) {
                                                z2 = z6;
                                                if (!this.mIsLockscreenLiveWallpaperEnabled || !z7) {
                                                    str6 = str7;
                                                    file = chooseForRead;
                                                }
                                            } else {
                                                str6 = str7;
                                                file = chooseForRead;
                                                z2 = z6;
                                            }
                                            z3 = z7;
                                            if ("kwp".equals(name) && !this.mIsLockscreenLiveWallpaperEnabled) {
                                                int i6 = i3 | 2;
                                                if (WhichChecker.isSupportLock(i6)) {
                                                    i5 = 1;
                                                    try {
                                                        String fileName3 = SemWallpaperManagerService.getFileName(i3, 1, 0);
                                                        String fileName4 = SemWallpaperManagerService.getFileName(i3, 1, 1);
                                                        if (wallpaperData7 == null) {
                                                            wallpaperData7 = new WallpaperData(i, WallpaperUtils.getWallpaperLockDir(i), fileName3, fileName4);
                                                        }
                                                        parseWallpaperAttributes(resolvePullParser, wallpaperData7, false);
                                                        this.mSemService.initializeThumnailFile(wallpaperData7, i6, wallpaperData7.mSemWallpaperData.getWpType(), i);
                                                        if (Rune.SUPPORT_PREVIEW_LOCK_ONLY_LIVE_WALLPAPER) {
                                                            String attributeValue = resolvePullParser.getAttributeValue((String) null, "component");
                                                            if (!TextUtils.isEmpty(attributeValue)) {
                                                                wallpaperData7.wallpaperComponent = ComponentName.unflattenFromString(attributeValue);
                                                            }
                                                        }
                                                        if (!wallpaperData7.cropExists() && wallpaperData7.sourceExists()) {
                                                            this.mWallpaperCropper.generateCrop(wallpaperData7);
                                                        }
                                                        this.mSemService.generateResizedBitmap(wallpaperData7.cropFile, wallpaperData7.mSemWallpaperData);
                                                        String attributeValue2 = resolvePullParser.getAttributeValue((String) null, "callingPackage");
                                                        if (wallpaperData7.mSemWallpaperData.getWallpaperHistoryList() == null) {
                                                            wallpaperData7.mSemWallpaperData.parseWallpaperHistoryInfo(resolvePullParser);
                                                        }
                                                        if (attributeValue2 != null && !attributeValue2.isEmpty()) {
                                                            Log.m129d(TAG, "legacyCallingPackage: " + attributeValue2);
                                                            wallpaperData7.setCallingPackage(attributeValue2);
                                                        }
                                                        wallpaperData7.mSemWallpaperData.setWhich(i6);
                                                        Slog.d(TAG, "lockWallpaper.mSemWallpaperData.setWhich(" + i6 + ")");
                                                        this.mSemService.mLegibilityColor.initSemWallpaperColors(wallpaperData7.userId, wallpaperData7.mSemWallpaperData);
                                                    } catch (IOException e2) {
                                                        fileInputStream = fileInputStream2;
                                                        iOException = e2;
                                                        i4 = 1;
                                                        str4 = str5;
                                                        chooseForRead = file;
                                                        Slog.w(TAG, "failed parsing " + chooseForRead + str4 + iOException);
                                                        wallpaperData8 = wallpaperData7;
                                                        z4 = false;
                                                        IoUtils.closeQuietly(fileInputStream);
                                                        WallpaperDisplayHelper.DisplayData displayData2 = displayData;
                                                        this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayData2, 0);
                                                        if (z2) {
                                                        }
                                                        if (z3) {
                                                        }
                                                        return new WallpaperLoadingResult(wallpaperData9, wallpaperData8, z4);
                                                    } catch (IndexOutOfBoundsException e3) {
                                                        e = e3;
                                                        fileInputStream = fileInputStream2;
                                                        indexOutOfBoundsException = e;
                                                        i4 = i5;
                                                        Slog.w(TAG, "failed parsing " + file + str5 + indexOutOfBoundsException);
                                                        wallpaperData8 = wallpaperData7;
                                                        z4 = false;
                                                        IoUtils.closeQuietly(fileInputStream);
                                                        WallpaperDisplayHelper.DisplayData displayData22 = displayData;
                                                        this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayData22, 0);
                                                        if (z2) {
                                                        }
                                                        if (z3) {
                                                        }
                                                        return new WallpaperLoadingResult(wallpaperData9, wallpaperData8, z4);
                                                    } catch (NullPointerException e4) {
                                                        fileInputStream = fileInputStream2;
                                                        nullPointerException = e4;
                                                        i4 = 1;
                                                        str3 = str5;
                                                        chooseForRead = file;
                                                        Slog.w(TAG, "failed parsing " + chooseForRead + str3 + nullPointerException);
                                                        wallpaperData8 = wallpaperData7;
                                                        z4 = false;
                                                        IoUtils.closeQuietly(fileInputStream);
                                                        WallpaperDisplayHelper.DisplayData displayData222 = displayData;
                                                        this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayData222, 0);
                                                        if (z2) {
                                                        }
                                                        if (z3) {
                                                        }
                                                        return new WallpaperLoadingResult(wallpaperData9, wallpaperData8, z4);
                                                    } catch (NumberFormatException e5) {
                                                        fileInputStream = fileInputStream2;
                                                        numberFormatException = e5;
                                                        i4 = 1;
                                                        str2 = str5;
                                                        chooseForRead = file;
                                                        Slog.w(TAG, "failed parsing " + chooseForRead + str2 + numberFormatException);
                                                        wallpaperData8 = wallpaperData7;
                                                        z4 = false;
                                                        IoUtils.closeQuietly(fileInputStream);
                                                        WallpaperDisplayHelper.DisplayData displayData2222 = displayData;
                                                        this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayData2222, 0);
                                                        if (z2) {
                                                        }
                                                        if (z3) {
                                                        }
                                                        return new WallpaperLoadingResult(wallpaperData9, wallpaperData8, z4);
                                                    } catch (XmlPullParserException e6) {
                                                        fileInputStream = fileInputStream2;
                                                        xmlPullParserException = e6;
                                                        i4 = 1;
                                                        str = str5;
                                                        chooseForRead = file;
                                                        Slog.w(TAG, "failed parsing " + chooseForRead + str + xmlPullParserException);
                                                        wallpaperData8 = wallpaperData7;
                                                        z4 = false;
                                                        IoUtils.closeQuietly(fileInputStream);
                                                        WallpaperDisplayHelper.DisplayData displayData22222 = displayData;
                                                        this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayData22222, 0);
                                                        if (z2) {
                                                        }
                                                        if (z3) {
                                                        }
                                                        return new WallpaperLoadingResult(wallpaperData9, wallpaperData8, z4);
                                                    }
                                                }
                                            }
                                        } catch (FileNotFoundException unused2) {
                                            z2 = z6;
                                            z3 = z7;
                                            fileInputStream = fileInputStream2;
                                            i4 = 1;
                                            Slog.w(TAG, "no current wallpaper -- first boot?");
                                            wallpaperData8 = wallpaperData7;
                                            z4 = false;
                                            IoUtils.closeQuietly(fileInputStream);
                                            WallpaperDisplayHelper.DisplayData displayData222222 = displayData;
                                            this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayData222222, 0);
                                            if (z2) {
                                            }
                                            if (z3) {
                                            }
                                            return new WallpaperLoadingResult(wallpaperData9, wallpaperData8, z4);
                                        } catch (IOException e7) {
                                            e = e7;
                                            z2 = z6;
                                            z3 = z7;
                                            fileInputStream = fileInputStream2;
                                            iOException = e;
                                            str4 = str5;
                                            i4 = 1;
                                            Slog.w(TAG, "failed parsing " + chooseForRead + str4 + iOException);
                                            wallpaperData8 = wallpaperData7;
                                            z4 = false;
                                            IoUtils.closeQuietly(fileInputStream);
                                            WallpaperDisplayHelper.DisplayData displayData2222222 = displayData;
                                            this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayData2222222, 0);
                                            if (z2) {
                                            }
                                            if (z3) {
                                            }
                                            return new WallpaperLoadingResult(wallpaperData9, wallpaperData8, z4);
                                        } catch (IndexOutOfBoundsException e8) {
                                            e = e8;
                                            file = chooseForRead;
                                            z2 = z6;
                                            z3 = z7;
                                            fileInputStream = fileInputStream2;
                                            indexOutOfBoundsException = e;
                                            i4 = 1;
                                            Slog.w(TAG, "failed parsing " + file + str5 + indexOutOfBoundsException);
                                            wallpaperData8 = wallpaperData7;
                                            z4 = false;
                                            IoUtils.closeQuietly(fileInputStream);
                                            WallpaperDisplayHelper.DisplayData displayData22222222 = displayData;
                                            this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayData22222222, 0);
                                            if (z2) {
                                            }
                                            if (z3) {
                                            }
                                            return new WallpaperLoadingResult(wallpaperData9, wallpaperData8, z4);
                                        } catch (NullPointerException e9) {
                                            e = e9;
                                            z2 = z6;
                                            z3 = z7;
                                            fileInputStream = fileInputStream2;
                                            nullPointerException = e;
                                            str3 = str5;
                                            i4 = 1;
                                            Slog.w(TAG, "failed parsing " + chooseForRead + str3 + nullPointerException);
                                            wallpaperData8 = wallpaperData7;
                                            z4 = false;
                                            IoUtils.closeQuietly(fileInputStream);
                                            WallpaperDisplayHelper.DisplayData displayData222222222 = displayData;
                                            this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayData222222222, 0);
                                            if (z2) {
                                            }
                                            if (z3) {
                                            }
                                            return new WallpaperLoadingResult(wallpaperData9, wallpaperData8, z4);
                                        } catch (NumberFormatException e10) {
                                            e = e10;
                                            z2 = z6;
                                            z3 = z7;
                                            fileInputStream = fileInputStream2;
                                            numberFormatException = e;
                                            str2 = str5;
                                            i4 = 1;
                                            Slog.w(TAG, "failed parsing " + chooseForRead + str2 + numberFormatException);
                                            wallpaperData8 = wallpaperData7;
                                            z4 = false;
                                            IoUtils.closeQuietly(fileInputStream);
                                            WallpaperDisplayHelper.DisplayData displayData2222222222 = displayData;
                                            this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayData2222222222, 0);
                                            if (z2) {
                                            }
                                            if (z3) {
                                            }
                                            return new WallpaperLoadingResult(wallpaperData9, wallpaperData8, z4);
                                        } catch (XmlPullParserException e11) {
                                            e = e11;
                                            z2 = z6;
                                            z3 = z7;
                                            fileInputStream = fileInputStream2;
                                            xmlPullParserException = e;
                                            str = str5;
                                            i4 = 1;
                                            Slog.w(TAG, "failed parsing " + chooseForRead + str + xmlPullParserException);
                                            wallpaperData8 = wallpaperData7;
                                            z4 = false;
                                            IoUtils.closeQuietly(fileInputStream);
                                            WallpaperDisplayHelper.DisplayData displayData22222222222 = displayData;
                                            this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayData22222222222, 0);
                                            if (z2) {
                                            }
                                            if (z3) {
                                            }
                                            return new WallpaperLoadingResult(wallpaperData9, wallpaperData8, z4);
                                        }
                                    }
                                } catch (FileNotFoundException unused3) {
                                    wallpaperData6 = wallpaperData3;
                                    displayData = displayDataOrCreate;
                                } catch (IOException e12) {
                                    wallpaperData6 = wallpaperData3;
                                    displayData = displayDataOrCreate;
                                    z2 = z6;
                                    z3 = z7;
                                    fileInputStream = fileInputStream2;
                                    iOException = e12;
                                    str4 = str8;
                                } catch (IndexOutOfBoundsException e13) {
                                    e = e13;
                                    str5 = str8;
                                    file = chooseForRead;
                                    wallpaperData6 = wallpaperData3;
                                    displayData = displayDataOrCreate;
                                } catch (NullPointerException e14) {
                                    wallpaperData6 = wallpaperData3;
                                    displayData = displayDataOrCreate;
                                    z2 = z6;
                                    z3 = z7;
                                    fileInputStream = fileInputStream2;
                                    nullPointerException = e14;
                                    str3 = str8;
                                } catch (NumberFormatException e15) {
                                    wallpaperData6 = wallpaperData3;
                                    displayData = displayDataOrCreate;
                                    z2 = z6;
                                    z3 = z7;
                                    fileInputStream = fileInputStream2;
                                    numberFormatException = e15;
                                    str2 = str8;
                                } catch (XmlPullParserException e16) {
                                    wallpaperData6 = wallpaperData3;
                                    displayData = displayDataOrCreate;
                                    z2 = z6;
                                    z3 = z7;
                                    fileInputStream = fileInputStream2;
                                    xmlPullParserException = e16;
                                    str = str8;
                                }
                                try {
                                    if ("kwp".equals(name) && wallpaperData7 == null) {
                                        z3 = z7;
                                        i5 = 1;
                                        try {
                                            try {
                                            } catch (FileNotFoundException unused4) {
                                                fileInputStream = fileInputStream2;
                                                i4 = 1;
                                                Slog.w(TAG, "no current wallpaper -- first boot?");
                                                wallpaperData8 = wallpaperData7;
                                                z4 = false;
                                                IoUtils.closeQuietly(fileInputStream);
                                                WallpaperDisplayHelper.DisplayData displayData222222222222 = displayData;
                                                this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayData222222222222, 0);
                                                if (z2) {
                                                }
                                                if (z3) {
                                                }
                                                return new WallpaperLoadingResult(wallpaperData9, wallpaperData8, z4);
                                            }
                                        } catch (IOException e17) {
                                            fileInputStream = fileInputStream2;
                                            iOException = e17;
                                            i4 = 1;
                                            str4 = str5;
                                            Slog.w(TAG, "failed parsing " + chooseForRead + str4 + iOException);
                                            wallpaperData8 = wallpaperData7;
                                            z4 = false;
                                            IoUtils.closeQuietly(fileInputStream);
                                            WallpaperDisplayHelper.DisplayData displayData2222222222222 = displayData;
                                            this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayData2222222222222, 0);
                                            if (z2) {
                                            }
                                            if (z3) {
                                            }
                                            return new WallpaperLoadingResult(wallpaperData9, wallpaperData8, z4);
                                        } catch (IndexOutOfBoundsException e18) {
                                            e = e18;
                                            file = chooseForRead;
                                            fileInputStream = fileInputStream2;
                                            indexOutOfBoundsException = e;
                                            i4 = i5;
                                            Slog.w(TAG, "failed parsing " + file + str5 + indexOutOfBoundsException);
                                            wallpaperData8 = wallpaperData7;
                                            z4 = false;
                                            IoUtils.closeQuietly(fileInputStream);
                                            WallpaperDisplayHelper.DisplayData displayData22222222222222 = displayData;
                                            this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayData22222222222222, 0);
                                            if (z2) {
                                            }
                                            if (z3) {
                                            }
                                            return new WallpaperLoadingResult(wallpaperData9, wallpaperData8, z4);
                                        } catch (NullPointerException e19) {
                                            fileInputStream = fileInputStream2;
                                            nullPointerException = e19;
                                            i4 = 1;
                                            str3 = str5;
                                            Slog.w(TAG, "failed parsing " + chooseForRead + str3 + nullPointerException);
                                            wallpaperData8 = wallpaperData7;
                                            z4 = false;
                                            IoUtils.closeQuietly(fileInputStream);
                                            WallpaperDisplayHelper.DisplayData displayData222222222222222 = displayData;
                                            this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayData222222222222222, 0);
                                            if (z2) {
                                            }
                                            if (z3) {
                                            }
                                            return new WallpaperLoadingResult(wallpaperData9, wallpaperData8, z4);
                                        } catch (NumberFormatException e20) {
                                            fileInputStream = fileInputStream2;
                                            numberFormatException = e20;
                                            i4 = 1;
                                            str2 = str5;
                                            Slog.w(TAG, "failed parsing " + chooseForRead + str2 + numberFormatException);
                                            wallpaperData8 = wallpaperData7;
                                            z4 = false;
                                            IoUtils.closeQuietly(fileInputStream);
                                            WallpaperDisplayHelper.DisplayData displayData2222222222222222 = displayData;
                                            this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayData2222222222222222, 0);
                                            if (z2) {
                                            }
                                            if (z3) {
                                            }
                                            return new WallpaperLoadingResult(wallpaperData9, wallpaperData8, z4);
                                        } catch (XmlPullParserException e21) {
                                            fileInputStream = fileInputStream2;
                                            xmlPullParserException = e21;
                                            i4 = 1;
                                            str = str5;
                                            Slog.w(TAG, "failed parsing " + chooseForRead + str + xmlPullParserException);
                                            wallpaperData8 = wallpaperData7;
                                            z4 = false;
                                            IoUtils.closeQuietly(fileInputStream);
                                            WallpaperDisplayHelper.DisplayData displayData22222222222222222 = displayData;
                                            this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayData22222222222222222, 0);
                                            if (z2) {
                                            }
                                            if (z3) {
                                            }
                                            return new WallpaperLoadingResult(wallpaperData9, wallpaperData8, z4);
                                        }
                                        try {
                                            try {
                                                file = chooseForRead;
                                            } catch (FileNotFoundException unused5) {
                                                fileInputStream = fileInputStream2;
                                                i4 = 1;
                                                Slog.w(TAG, "no current wallpaper -- first boot?");
                                                wallpaperData8 = wallpaperData7;
                                                z4 = false;
                                                IoUtils.closeQuietly(fileInputStream);
                                                WallpaperDisplayHelper.DisplayData displayData222222222222222222 = displayData;
                                                this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayData222222222222222222, 0);
                                                if (z2) {
                                                }
                                                if (z3) {
                                                }
                                                return new WallpaperLoadingResult(wallpaperData9, wallpaperData8, z4);
                                            }
                                            try {
                                                wallpaperData10 = new WallpaperData(i, WallpaperUtils.getWallpaperLockDir(i), SemWallpaperManagerService.getFileName(i3, 1, 0), SemWallpaperManagerService.getFileName(i3, 1, 1));
                                            } catch (IOException e22) {
                                                fileInputStream = fileInputStream2;
                                                iOException = e22;
                                            } catch (IndexOutOfBoundsException e23) {
                                                e = e23;
                                                fileInputStream = fileInputStream2;
                                                indexOutOfBoundsException = e;
                                                i4 = 1;
                                                Slog.w(TAG, "failed parsing " + file + str5 + indexOutOfBoundsException);
                                                wallpaperData8 = wallpaperData7;
                                                z4 = false;
                                                IoUtils.closeQuietly(fileInputStream);
                                                WallpaperDisplayHelper.DisplayData displayData2222222222222222222 = displayData;
                                                this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayData2222222222222222222, 0);
                                                if (z2) {
                                                }
                                                if (z3) {
                                                }
                                                return new WallpaperLoadingResult(wallpaperData9, wallpaperData8, z4);
                                            } catch (NullPointerException e24) {
                                                fileInputStream = fileInputStream2;
                                                nullPointerException = e24;
                                            } catch (NumberFormatException e25) {
                                                fileInputStream = fileInputStream2;
                                                numberFormatException = e25;
                                            } catch (XmlPullParserException e26) {
                                                fileInputStream = fileInputStream2;
                                                xmlPullParserException = e26;
                                            }
                                        } catch (IOException e27) {
                                            e = e27;
                                            fileInputStream = fileInputStream2;
                                            iOException = e;
                                            str4 = str5;
                                            i4 = 1;
                                            Slog.w(TAG, "failed parsing " + chooseForRead + str4 + iOException);
                                            wallpaperData8 = wallpaperData7;
                                            z4 = false;
                                            IoUtils.closeQuietly(fileInputStream);
                                            WallpaperDisplayHelper.DisplayData displayData22222222222222222222 = displayData;
                                            this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayData22222222222222222222, 0);
                                            if (z2) {
                                            }
                                            if (z3) {
                                            }
                                            return new WallpaperLoadingResult(wallpaperData9, wallpaperData8, z4);
                                        } catch (IndexOutOfBoundsException e28) {
                                            e = e28;
                                            file = chooseForRead;
                                        } catch (NullPointerException e29) {
                                            e = e29;
                                            fileInputStream = fileInputStream2;
                                            nullPointerException = e;
                                            str3 = str5;
                                            i4 = 1;
                                            Slog.w(TAG, "failed parsing " + chooseForRead + str3 + nullPointerException);
                                            wallpaperData8 = wallpaperData7;
                                            z4 = false;
                                            IoUtils.closeQuietly(fileInputStream);
                                            WallpaperDisplayHelper.DisplayData displayData222222222222222222222 = displayData;
                                            this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayData222222222222222222222, 0);
                                            if (z2) {
                                            }
                                            if (z3) {
                                            }
                                            return new WallpaperLoadingResult(wallpaperData9, wallpaperData8, z4);
                                        } catch (NumberFormatException e30) {
                                            e = e30;
                                            fileInputStream = fileInputStream2;
                                            numberFormatException = e;
                                            str2 = str5;
                                            i4 = 1;
                                            Slog.w(TAG, "failed parsing " + chooseForRead + str2 + numberFormatException);
                                            wallpaperData8 = wallpaperData7;
                                            z4 = false;
                                            IoUtils.closeQuietly(fileInputStream);
                                            WallpaperDisplayHelper.DisplayData displayData2222222222222222222222 = displayData;
                                            this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayData2222222222222222222222, 0);
                                            if (z2) {
                                            }
                                            if (z3) {
                                            }
                                            return new WallpaperLoadingResult(wallpaperData9, wallpaperData8, z4);
                                        } catch (XmlPullParserException e31) {
                                            e = e31;
                                            fileInputStream = fileInputStream2;
                                            xmlPullParserException = e;
                                            str = str5;
                                            i4 = 1;
                                            Slog.w(TAG, "failed parsing " + chooseForRead + str + xmlPullParserException);
                                            wallpaperData8 = wallpaperData7;
                                            z4 = false;
                                            IoUtils.closeQuietly(fileInputStream);
                                            WallpaperDisplayHelper.DisplayData displayData22222222222222222222222 = displayData;
                                            this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayData22222222222222222222222, 0);
                                            if (z2) {
                                            }
                                            if (z3) {
                                            }
                                            return new WallpaperLoadingResult(wallpaperData9, wallpaperData8, z4);
                                        }
                                        try {
                                            wallpaperData10.setWhich(i3 | 2);
                                            wallpaperData7 = wallpaperData10;
                                        } catch (FileNotFoundException unused6) {
                                            fileInputStream = fileInputStream2;
                                            wallpaperData7 = wallpaperData10;
                                            i4 = 1;
                                            Slog.w(TAG, "no current wallpaper -- first boot?");
                                            wallpaperData8 = wallpaperData7;
                                            z4 = false;
                                            IoUtils.closeQuietly(fileInputStream);
                                            WallpaperDisplayHelper.DisplayData displayData222222222222222222222222 = displayData;
                                            this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayData222222222222222222222222, 0);
                                            if (z2) {
                                            }
                                            if (z3) {
                                            }
                                            return new WallpaperLoadingResult(wallpaperData9, wallpaperData8, z4);
                                        } catch (IOException e32) {
                                            fileInputStream = fileInputStream2;
                                            iOException = e32;
                                            wallpaperData7 = wallpaperData10;
                                            str4 = str5;
                                            chooseForRead = file;
                                            i4 = 1;
                                            Slog.w(TAG, "failed parsing " + chooseForRead + str4 + iOException);
                                            wallpaperData8 = wallpaperData7;
                                            z4 = false;
                                            IoUtils.closeQuietly(fileInputStream);
                                            WallpaperDisplayHelper.DisplayData displayData2222222222222222222222222 = displayData;
                                            this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayData2222222222222222222222222, 0);
                                            if (z2) {
                                            }
                                            if (z3) {
                                            }
                                            return new WallpaperLoadingResult(wallpaperData9, wallpaperData8, z4);
                                        } catch (IndexOutOfBoundsException e33) {
                                            fileInputStream = fileInputStream2;
                                            indexOutOfBoundsException = e33;
                                            wallpaperData7 = wallpaperData10;
                                            i4 = 1;
                                            Slog.w(TAG, "failed parsing " + file + str5 + indexOutOfBoundsException);
                                            wallpaperData8 = wallpaperData7;
                                            z4 = false;
                                            IoUtils.closeQuietly(fileInputStream);
                                            WallpaperDisplayHelper.DisplayData displayData22222222222222222222222222 = displayData;
                                            this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayData22222222222222222222222222, 0);
                                            if (z2) {
                                            }
                                            if (z3) {
                                            }
                                            return new WallpaperLoadingResult(wallpaperData9, wallpaperData8, z4);
                                        } catch (NullPointerException e34) {
                                            fileInputStream = fileInputStream2;
                                            nullPointerException = e34;
                                            wallpaperData7 = wallpaperData10;
                                            str3 = str5;
                                            chooseForRead = file;
                                            i4 = 1;
                                            Slog.w(TAG, "failed parsing " + chooseForRead + str3 + nullPointerException);
                                            wallpaperData8 = wallpaperData7;
                                            z4 = false;
                                            IoUtils.closeQuietly(fileInputStream);
                                            WallpaperDisplayHelper.DisplayData displayData222222222222222222222222222 = displayData;
                                            this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayData222222222222222222222222222, 0);
                                            if (z2) {
                                            }
                                            if (z3) {
                                            }
                                            return new WallpaperLoadingResult(wallpaperData9, wallpaperData8, z4);
                                        } catch (NumberFormatException e35) {
                                            fileInputStream = fileInputStream2;
                                            numberFormatException = e35;
                                            wallpaperData7 = wallpaperData10;
                                            str2 = str5;
                                            chooseForRead = file;
                                            i4 = 1;
                                            Slog.w(TAG, "failed parsing " + chooseForRead + str2 + numberFormatException);
                                            wallpaperData8 = wallpaperData7;
                                            z4 = false;
                                            IoUtils.closeQuietly(fileInputStream);
                                            WallpaperDisplayHelper.DisplayData displayData2222222222222222222222222222 = displayData;
                                            this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayData2222222222222222222222222222, 0);
                                            if (z2) {
                                            }
                                            if (z3) {
                                            }
                                            return new WallpaperLoadingResult(wallpaperData9, wallpaperData8, z4);
                                        } catch (XmlPullParserException e36) {
                                            fileInputStream = fileInputStream2;
                                            xmlPullParserException = e36;
                                            wallpaperData7 = wallpaperData10;
                                            str = str5;
                                            chooseForRead = file;
                                            i4 = 1;
                                            Slog.w(TAG, "failed parsing " + chooseForRead + str + xmlPullParserException);
                                            wallpaperData8 = wallpaperData7;
                                            z4 = false;
                                            IoUtils.closeQuietly(fileInputStream);
                                            WallpaperDisplayHelper.DisplayData displayData22222222222222222222222222222 = displayData;
                                            this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayData22222222222222222222222222222, 0);
                                            if (z2) {
                                            }
                                            if (z3) {
                                            }
                                            return new WallpaperLoadingResult(wallpaperData9, wallpaperData8, z4);
                                        }
                                    } else {
                                        file = chooseForRead;
                                        z3 = z7;
                                    }
                                    WallpaperData wallpaperData12 = str7.equals(name) ? wallpaperData6 : wallpaperData7;
                                    parseWallpaperAttributes(resolvePullParser, wallpaperData12, z);
                                    int i7 = str7.equals(name) ? 1 : 2;
                                    int i8 = i3 | i7;
                                    str6 = str7;
                                    this.mSemService.initializeThumnailFile(wallpaperData12, i8, wallpaperData12.mSemWallpaperData.getWpType(), i);
                                    if (i7 == 2) {
                                        if (!wallpaperData12.cropExists() && wallpaperData12.sourceExists()) {
                                            this.mWallpaperCropper.generateCrop(wallpaperData12);
                                        }
                                        this.mSemService.generateResizedBitmap(wallpaperData12.cropFile, wallpaperData12.mSemWallpaperData);
                                    }
                                    String attributeValue3 = resolvePullParser.getAttributeValue((String) null, "component");
                                    ComponentName unflattenFromString = attributeValue3 != null ? ComponentName.unflattenFromString(attributeValue3) : null;
                                    wallpaperData12.nextWallpaperComponent = unflattenFromString;
                                    if (unflattenFromString == null || "android".equals(unflattenFromString.getPackageName())) {
                                        wallpaperData12.nextWallpaperComponent = this.mImageWallpaper;
                                    }
                                    if (wallpaperData12.mSemWallpaperData.getWallpaperHistoryList() == null) {
                                        wallpaperData12.mSemWallpaperData.parseWallpaperHistoryInfo(resolvePullParser);
                                    }
                                    wallpaperData12.mSemWallpaperData.setWhich(i8);
                                    Slog.d(TAG, "wallpaperToParse.mSemWallpaperData.setWhich(" + i8 + ")");
                                    this.mSemService.mLegibilityColor.initSemWallpaperColors(wallpaperData12.userId, wallpaperData12.mSemWallpaperData);
                                } catch (FileNotFoundException unused7) {
                                    z3 = z7;
                                    fileInputStream = fileInputStream2;
                                    i4 = 1;
                                    Slog.w(TAG, "no current wallpaper -- first boot?");
                                    wallpaperData8 = wallpaperData7;
                                    z4 = false;
                                    IoUtils.closeQuietly(fileInputStream);
                                    WallpaperDisplayHelper.DisplayData displayData222222222222222222222222222222 = displayData;
                                    this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayData222222222222222222222222222222, 0);
                                    if (z2) {
                                    }
                                    if (z3) {
                                    }
                                    return new WallpaperLoadingResult(wallpaperData9, wallpaperData8, z4);
                                } catch (IOException e37) {
                                    e = e37;
                                    z3 = z7;
                                    fileInputStream = fileInputStream2;
                                    iOException = e;
                                    str4 = str5;
                                    i4 = 1;
                                    Slog.w(TAG, "failed parsing " + chooseForRead + str4 + iOException);
                                    wallpaperData8 = wallpaperData7;
                                    z4 = false;
                                    IoUtils.closeQuietly(fileInputStream);
                                    WallpaperDisplayHelper.DisplayData displayData2222222222222222222222222222222 = displayData;
                                    this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayData2222222222222222222222222222222, 0);
                                    if (z2) {
                                    }
                                    if (z3) {
                                    }
                                    return new WallpaperLoadingResult(wallpaperData9, wallpaperData8, z4);
                                } catch (IndexOutOfBoundsException e38) {
                                    e = e38;
                                    file = chooseForRead;
                                    z3 = z7;
                                    fileInputStream = fileInputStream2;
                                    indexOutOfBoundsException = e;
                                    i4 = 1;
                                    Slog.w(TAG, "failed parsing " + file + str5 + indexOutOfBoundsException);
                                    wallpaperData8 = wallpaperData7;
                                    z4 = false;
                                    IoUtils.closeQuietly(fileInputStream);
                                    WallpaperDisplayHelper.DisplayData displayData22222222222222222222222222222222 = displayData;
                                    this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayData22222222222222222222222222222222, 0);
                                    if (z2) {
                                    }
                                    if (z3) {
                                    }
                                    return new WallpaperLoadingResult(wallpaperData9, wallpaperData8, z4);
                                } catch (NullPointerException e39) {
                                    e = e39;
                                    z3 = z7;
                                    fileInputStream = fileInputStream2;
                                    nullPointerException = e;
                                    str3 = str5;
                                    i4 = 1;
                                    Slog.w(TAG, "failed parsing " + chooseForRead + str3 + nullPointerException);
                                    wallpaperData8 = wallpaperData7;
                                    z4 = false;
                                    IoUtils.closeQuietly(fileInputStream);
                                    WallpaperDisplayHelper.DisplayData displayData222222222222222222222222222222222 = displayData;
                                    this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayData222222222222222222222222222222222, 0);
                                    if (z2) {
                                    }
                                    if (z3) {
                                    }
                                    return new WallpaperLoadingResult(wallpaperData9, wallpaperData8, z4);
                                } catch (NumberFormatException e40) {
                                    e = e40;
                                    z3 = z7;
                                    fileInputStream = fileInputStream2;
                                    numberFormatException = e;
                                    str2 = str5;
                                    i4 = 1;
                                    Slog.w(TAG, "failed parsing " + chooseForRead + str2 + numberFormatException);
                                    wallpaperData8 = wallpaperData7;
                                    z4 = false;
                                    IoUtils.closeQuietly(fileInputStream);
                                    WallpaperDisplayHelper.DisplayData displayData2222222222222222222222222222222222 = displayData;
                                    this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayData2222222222222222222222222222222222, 0);
                                    if (z2) {
                                    }
                                    if (z3) {
                                    }
                                    return new WallpaperLoadingResult(wallpaperData9, wallpaperData8, z4);
                                } catch (XmlPullParserException e41) {
                                    e = e41;
                                    z3 = z7;
                                    fileInputStream = fileInputStream2;
                                    xmlPullParserException = e;
                                    str = str5;
                                    i4 = 1;
                                    Slog.w(TAG, "failed parsing " + chooseForRead + str + xmlPullParserException);
                                    wallpaperData8 = wallpaperData7;
                                    z4 = false;
                                    IoUtils.closeQuietly(fileInputStream);
                                    WallpaperDisplayHelper.DisplayData displayData22222222222222222222222222222222222 = displayData;
                                    this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayData22222222222222222222222222222222222, 0);
                                    if (z2) {
                                    }
                                    if (z3) {
                                    }
                                    return new WallpaperLoadingResult(wallpaperData9, wallpaperData8, z4);
                                }
                            } else {
                                str6 = str7;
                                str5 = str8;
                                file = chooseForRead;
                                wallpaperData6 = wallpaperData3;
                                displayData = displayDataOrCreate;
                                z2 = z6;
                                z3 = z7;
                            }
                            i4 = 1;
                            fileInputStream = fileInputStream2;
                            if (next == 1) {
                                break;
                            }
                            z6 = z2;
                            wallpaperData3 = wallpaperData6;
                            displayDataOrCreate = displayData;
                            str8 = str5;
                            z7 = z3;
                            chooseForRead = file;
                            str7 = str6;
                        } catch (FileNotFoundException unused8) {
                            wallpaperData6 = wallpaperData3;
                            displayData = displayDataOrCreate;
                            z2 = z6;
                            z3 = z7;
                        } catch (IOException e42) {
                            e = e42;
                            wallpaperData6 = wallpaperData3;
                            displayData = displayDataOrCreate;
                            z2 = z6;
                            z3 = z7;
                            i4 = 1;
                            iOException = e;
                            str4 = str8;
                            Slog.w(TAG, "failed parsing " + chooseForRead + str4 + iOException);
                            wallpaperData8 = wallpaperData7;
                            z4 = false;
                            IoUtils.closeQuietly(fileInputStream);
                            WallpaperDisplayHelper.DisplayData displayData222222222222222222222222222222222222 = displayData;
                            this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayData222222222222222222222222222222222222, 0);
                            if (z2) {
                            }
                            if (z3) {
                            }
                            return new WallpaperLoadingResult(wallpaperData9, wallpaperData8, z4);
                        } catch (IndexOutOfBoundsException e43) {
                            e = e43;
                            str5 = str8;
                            file = chooseForRead;
                            wallpaperData6 = wallpaperData3;
                            displayData = displayDataOrCreate;
                            z2 = z6;
                            z3 = z7;
                            i4 = 1;
                            indexOutOfBoundsException = e;
                            Slog.w(TAG, "failed parsing " + file + str5 + indexOutOfBoundsException);
                            wallpaperData8 = wallpaperData7;
                            z4 = false;
                            IoUtils.closeQuietly(fileInputStream);
                            WallpaperDisplayHelper.DisplayData displayData2222222222222222222222222222222222222 = displayData;
                            this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayData2222222222222222222222222222222222222, 0);
                            if (z2) {
                            }
                            if (z3) {
                            }
                            return new WallpaperLoadingResult(wallpaperData9, wallpaperData8, z4);
                        } catch (NullPointerException e44) {
                            e = e44;
                            wallpaperData6 = wallpaperData3;
                            displayData = displayDataOrCreate;
                            z2 = z6;
                            z3 = z7;
                            i4 = 1;
                            nullPointerException = e;
                            str3 = str8;
                            Slog.w(TAG, "failed parsing " + chooseForRead + str3 + nullPointerException);
                            wallpaperData8 = wallpaperData7;
                            z4 = false;
                            IoUtils.closeQuietly(fileInputStream);
                            WallpaperDisplayHelper.DisplayData displayData22222222222222222222222222222222222222 = displayData;
                            this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayData22222222222222222222222222222222222222, 0);
                            if (z2) {
                            }
                            if (z3) {
                            }
                            return new WallpaperLoadingResult(wallpaperData9, wallpaperData8, z4);
                        } catch (NumberFormatException e45) {
                            e = e45;
                            wallpaperData6 = wallpaperData3;
                            displayData = displayDataOrCreate;
                            z2 = z6;
                            z3 = z7;
                            i4 = 1;
                            numberFormatException = e;
                            str2 = str8;
                            Slog.w(TAG, "failed parsing " + chooseForRead + str2 + numberFormatException);
                            wallpaperData8 = wallpaperData7;
                            z4 = false;
                            IoUtils.closeQuietly(fileInputStream);
                            WallpaperDisplayHelper.DisplayData displayData222222222222222222222222222222222222222 = displayData;
                            this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayData222222222222222222222222222222222222222, 0);
                            if (z2) {
                            }
                            if (z3) {
                            }
                            return new WallpaperLoadingResult(wallpaperData9, wallpaperData8, z4);
                        } catch (XmlPullParserException e46) {
                            e = e46;
                            wallpaperData6 = wallpaperData3;
                            displayData = displayDataOrCreate;
                            z2 = z6;
                            z3 = z7;
                            i4 = 1;
                            xmlPullParserException = e;
                            str = str8;
                            Slog.w(TAG, "failed parsing " + chooseForRead + str + xmlPullParserException);
                            wallpaperData8 = wallpaperData7;
                            z4 = false;
                            IoUtils.closeQuietly(fileInputStream);
                            WallpaperDisplayHelper.DisplayData displayData2222222222222222222222222222222222222222 = displayData;
                            this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayData2222222222222222222222222222222222222222, 0);
                            if (z2) {
                            }
                            if (z3) {
                            }
                            return new WallpaperLoadingResult(wallpaperData9, wallpaperData8, z4);
                        }
                    }
                    z4 = true;
                    wallpaperData8 = wallpaperData7;
                } catch (FileNotFoundException unused9) {
                    wallpaperData6 = wallpaperData3;
                    displayData = displayDataOrCreate;
                    z2 = z6;
                    z3 = z7;
                    i4 = 1;
                    wallpaperData7 = wallpaperData5;
                } catch (IOException e47) {
                    e = e47;
                    wallpaperData6 = wallpaperData3;
                    displayData = displayDataOrCreate;
                    z2 = z6;
                    z3 = z7;
                    i4 = 1;
                    wallpaperData7 = wallpaperData5;
                } catch (IndexOutOfBoundsException e48) {
                    e = e48;
                    str5 = " ";
                    file = chooseForRead;
                    wallpaperData6 = wallpaperData3;
                    displayData = displayDataOrCreate;
                    z2 = z6;
                    z3 = z7;
                    i4 = 1;
                    wallpaperData7 = wallpaperData5;
                } catch (NullPointerException e49) {
                    e = e49;
                    wallpaperData6 = wallpaperData3;
                    displayData = displayDataOrCreate;
                    z2 = z6;
                    z3 = z7;
                    i4 = 1;
                    wallpaperData7 = wallpaperData5;
                } catch (NumberFormatException e50) {
                    e = e50;
                    wallpaperData6 = wallpaperData3;
                    displayData = displayDataOrCreate;
                    z2 = z6;
                    z3 = z7;
                    i4 = 1;
                    wallpaperData7 = wallpaperData5;
                } catch (XmlPullParserException e51) {
                    e = e51;
                    wallpaperData6 = wallpaperData3;
                    displayData = displayDataOrCreate;
                    z2 = z6;
                    z3 = z7;
                    i4 = 1;
                    wallpaperData7 = wallpaperData5;
                }
            } catch (IOException e52) {
                wallpaperData6 = wallpaperData3;
                displayData = displayDataOrCreate;
                z2 = z6;
                z3 = z7;
                i4 = 1;
                wallpaperData7 = wallpaperData5;
                iOException = e52;
                str4 = " ";
                fileInputStream = null;
                Slog.w(TAG, "failed parsing " + chooseForRead + str4 + iOException);
                wallpaperData8 = wallpaperData7;
                z4 = false;
                IoUtils.closeQuietly(fileInputStream);
                WallpaperDisplayHelper.DisplayData displayData22222222222222222222222222222222222222222 = displayData;
                this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayData22222222222222222222222222222222222222222, 0);
                if (z2) {
                }
                if (z3) {
                }
                return new WallpaperLoadingResult(wallpaperData9, wallpaperData8, z4);
            } catch (NullPointerException e53) {
                wallpaperData6 = wallpaperData3;
                displayData = displayDataOrCreate;
                z2 = z6;
                z3 = z7;
                i4 = 1;
                wallpaperData7 = wallpaperData5;
                nullPointerException = e53;
                str3 = " ";
                fileInputStream = null;
                Slog.w(TAG, "failed parsing " + chooseForRead + str3 + nullPointerException);
                wallpaperData8 = wallpaperData7;
                z4 = false;
                IoUtils.closeQuietly(fileInputStream);
                WallpaperDisplayHelper.DisplayData displayData222222222222222222222222222222222222222222 = displayData;
                this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayData222222222222222222222222222222222222222222, 0);
                if (z2) {
                }
                if (z3) {
                }
                return new WallpaperLoadingResult(wallpaperData9, wallpaperData8, z4);
            } catch (NumberFormatException e54) {
                wallpaperData6 = wallpaperData3;
                displayData = displayDataOrCreate;
                z2 = z6;
                z3 = z7;
                i4 = 1;
                wallpaperData7 = wallpaperData5;
                numberFormatException = e54;
                str2 = " ";
                fileInputStream = null;
                Slog.w(TAG, "failed parsing " + chooseForRead + str2 + numberFormatException);
                wallpaperData8 = wallpaperData7;
                z4 = false;
                IoUtils.closeQuietly(fileInputStream);
                WallpaperDisplayHelper.DisplayData displayData2222222222222222222222222222222222222222222 = displayData;
                this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayData2222222222222222222222222222222222222222222, 0);
                if (z2) {
                }
                if (z3) {
                }
                return new WallpaperLoadingResult(wallpaperData9, wallpaperData8, z4);
            } catch (XmlPullParserException e55) {
                wallpaperData6 = wallpaperData3;
                displayData = displayDataOrCreate;
                z2 = z6;
                z3 = z7;
                i4 = 1;
                wallpaperData7 = wallpaperData5;
                xmlPullParserException = e55;
                str = " ";
                fileInputStream = null;
                Slog.w(TAG, "failed parsing " + chooseForRead + str + xmlPullParserException);
                wallpaperData8 = wallpaperData7;
                z4 = false;
                IoUtils.closeQuietly(fileInputStream);
                WallpaperDisplayHelper.DisplayData displayData22222222222222222222222222222222222222222222 = displayData;
                this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayData22222222222222222222222222222222222222222222, 0);
                if (z2) {
                }
                if (z3) {
                }
                return new WallpaperLoadingResult(wallpaperData9, wallpaperData8, z4);
            }
        } catch (IOException e56) {
            wallpaperData6 = wallpaperData3;
            displayData = displayDataOrCreate;
            z2 = z6;
            z3 = z7;
            i4 = 1;
            str4 = " ";
            wallpaperData7 = wallpaperData5;
            iOException = e56;
        } catch (NullPointerException e57) {
            wallpaperData6 = wallpaperData3;
            displayData = displayDataOrCreate;
            z2 = z6;
            z3 = z7;
            i4 = 1;
            str3 = " ";
            wallpaperData7 = wallpaperData5;
            nullPointerException = e57;
        } catch (NumberFormatException e58) {
            wallpaperData6 = wallpaperData3;
            displayData = displayDataOrCreate;
            z2 = z6;
            z3 = z7;
            i4 = 1;
            str2 = " ";
            wallpaperData7 = wallpaperData5;
            numberFormatException = e58;
        } catch (XmlPullParserException e59) {
            wallpaperData6 = wallpaperData3;
            displayData = displayDataOrCreate;
            z2 = z6;
            z3 = z7;
            i4 = 1;
            str = " ";
            wallpaperData7 = wallpaperData5;
            xmlPullParserException = e59;
        }
        IoUtils.closeQuietly(fileInputStream);
        WallpaperDisplayHelper.DisplayData displayData222222222222222222222222222222222222222222222 = displayData;
        this.mWallpaperDisplayHelper.ensureSaneWallpaperDisplaySize(displayData222222222222222222222222222222222222222222222, 0);
        if (z2) {
            if (!z4) {
                wallpaperData9 = wallpaperData6;
                wallpaperData9.cropHint.set(0, 0, 0, 0);
                displayData222222222222222222222222222222222222222222222.mPadding.set(0, 0, 0, 0);
                wallpaperData9.name = "";
            } else {
                wallpaperData9 = wallpaperData6;
                if (wallpaperData9.wallpaperId <= 0) {
                    wallpaperData9.wallpaperId = WallpaperUtils.makeWallpaperIdLocked();
                }
            }
            ensureSaneWallpaperData(wallpaperData9);
            wallpaperData9.mWhich = (wallpaperData8 != null ? i4 : 3) | i3;
        } else {
            wallpaperData9 = wallpaperData6;
        }
        if (z3) {
            if (!z4) {
                wallpaperData8 = null;
            }
            if (wallpaperData8 != null) {
                ensureSaneWallpaperData(wallpaperData8);
                wallpaperData8.mWhich = 2 | i3;
            }
        }
        return new WallpaperLoadingResult(wallpaperData9, wallpaperData8, z4);
    }

    public void ensureSaneWallpaperData(WallpaperData wallpaperData) {
        if (wallpaperData.cropHint.width() < 0 || wallpaperData.cropHint.height() < 0) {
            wallpaperData.cropHint.set(0, 0, 0, 0);
        }
    }

    public void parseWallpaperAttributes(TypedXmlPullParser typedXmlPullParser, WallpaperData wallpaperData, boolean z) {
        int attributeInt = typedXmlPullParser.getAttributeInt((String) null, "id", -1);
        if (attributeInt != -1) {
            wallpaperData.wallpaperId = attributeInt;
            if (attributeInt > WallpaperUtils.getCurrentWallpaperId()) {
                WallpaperUtils.setCurrentWallpaperId(attributeInt);
            }
        } else {
            wallpaperData.wallpaperId = WallpaperUtils.makeWallpaperIdLocked();
        }
        WallpaperDisplayHelper.DisplayData displayDataOrCreate = this.mWallpaperDisplayHelper.getDisplayDataOrCreate(0);
        if (!z) {
            displayDataOrCreate.mWidth = typedXmlPullParser.getAttributeInt((String) null, "width");
            displayDataOrCreate.mHeight = typedXmlPullParser.getAttributeInt((String) null, "height");
        }
        wallpaperData.cropHint.left = getAttributeInt(typedXmlPullParser, "cropLeft", 0);
        wallpaperData.cropHint.top = getAttributeInt(typedXmlPullParser, "cropTop", 0);
        wallpaperData.cropHint.right = getAttributeInt(typedXmlPullParser, "cropRight", 0);
        wallpaperData.cropHint.bottom = getAttributeInt(typedXmlPullParser, "cropBottom", 0);
        displayDataOrCreate.mPadding.left = getAttributeInt(typedXmlPullParser, "paddingLeft", 0);
        displayDataOrCreate.mPadding.top = getAttributeInt(typedXmlPullParser, "paddingTop", 0);
        displayDataOrCreate.mPadding.right = getAttributeInt(typedXmlPullParser, "paddingRight", 0);
        displayDataOrCreate.mPadding.bottom = getAttributeInt(typedXmlPullParser, "paddingBottom", 0);
        wallpaperData.mWallpaperDimAmount = getAttributeFloat(typedXmlPullParser, "dimAmount", DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
        int attributeInt2 = getAttributeInt(typedXmlPullParser, "dimAmountsCount", 0);
        if (attributeInt2 > 0) {
            SparseArray sparseArray = new SparseArray(attributeInt2);
            for (int i = 0; i < attributeInt2; i++) {
                sparseArray.put(getAttributeInt(typedXmlPullParser, "dimUID" + i, 0), Float.valueOf(getAttributeFloat(typedXmlPullParser, "dimValue" + i, DisplayPowerController2.RATE_FROM_DOZE_TO_ON)));
            }
            wallpaperData.mUidToDimAmount = sparseArray;
        }
        int attributeInt3 = getAttributeInt(typedXmlPullParser, "colorsCount", 0);
        int attributeInt4 = getAttributeInt(typedXmlPullParser, "allColorsCount", 0);
        if (attributeInt4 > 0) {
            HashMap hashMap = new HashMap(attributeInt4);
            for (int i2 = 0; i2 < attributeInt4; i2++) {
                hashMap.put(Integer.valueOf(getAttributeInt(typedXmlPullParser, "allColorsValue" + i2, 0)), Integer.valueOf(getAttributeInt(typedXmlPullParser, "allColorsPopulation" + i2, 0)));
            }
            wallpaperData.primaryColors = new WallpaperColors(hashMap, getAttributeInt(typedXmlPullParser, "colorHints", 0));
        } else if (attributeInt3 > 0) {
            Color color = null;
            Color color2 = null;
            Color color3 = null;
            for (int i3 = 0; i3 < attributeInt3; i3++) {
                Color valueOf = Color.valueOf(getAttributeInt(typedXmlPullParser, "colorValue" + i3, 0));
                if (i3 == 0) {
                    color = valueOf;
                } else if (i3 == 1) {
                    color2 = valueOf;
                } else if (i3 != 2) {
                    break;
                } else {
                    color3 = valueOf;
                }
            }
            wallpaperData.primaryColors = new WallpaperColors(color, color2, color3, getAttributeInt(typedXmlPullParser, "colorHints", 0));
        }
        wallpaperData.name = typedXmlPullParser.getAttributeValue((String) null, "name");
        wallpaperData.allowBackup = typedXmlPullParser.getAttributeBoolean((String) null, "backup", false);
        wallpaperData.mSemWallpaperData.setCreationTime(typedXmlPullParser.getAttributeValue((String) null, "creationTime"));
        wallpaperData.mSemWallpaperData.setIsPreloaded("true".equals(typedXmlPullParser.getAttributeValue((String) null, "isPreloaded")));
        wallpaperData.mSemWallpaperData.setIsCopied("true".equals(typedXmlPullParser.getAttributeValue((String) null, "isCopied")));
        wallpaperData.mSemWallpaperData.setLastCallingPackage(typedXmlPullParser.getAttributeValue((String) null, "lastCallingPackage"));
        wallpaperData.mSemWallpaperData.setLastClearCallstackWithNullPackage(typedXmlPullParser.getAttributeValue((String) null, "lastClearCallstackWithNullPackage"));
        int attributeInt5 = getAttributeInt(typedXmlPullParser, "type", 0);
        if (attributeInt5 == 9) {
            attributeInt5 = 1000;
        }
        wallpaperData.mSemWallpaperData.setWpType(attributeInt5);
        if (wallpaperData.mSemWallpaperData.getWpType() == 1) {
            wallpaperData.mSemWallpaperData.setMotionPkgName(typedXmlPullParser.getAttributeValue((String) null, "motionPkgName"));
        }
        if (wallpaperData.mSemWallpaperData.getWpType() == 4) {
            wallpaperData.mSemWallpaperData.setAnimatedPkgName(typedXmlPullParser.getAttributeValue((String) null, "animatedPkgName"));
        }
        if (wallpaperData.mSemWallpaperData.getWpType() == 8) {
            wallpaperData.mSemWallpaperData.setVideoFilePath(typedXmlPullParser.getAttributeValue((String) null, "videoFilePath"));
            wallpaperData.mSemWallpaperData.setVideoPkgName(typedXmlPullParser.getAttributeValue((String) null, "videoPkgName"));
            wallpaperData.mSemWallpaperData.setVideoFileName(typedXmlPullParser.getAttributeValue((String) null, "videoFileName"));
            wallpaperData.mSemWallpaperData.setVideoDefaultHasBeenUsed("true".equals(typedXmlPullParser.getAttributeValue((String) null, "videoDefaultHasBeenUsed")));
        }
        wallpaperData.mSemWallpaperData.setExternalParams(WallpaperExtraBundleHelper.fromJson(typedXmlPullParser.getAttributeValue((String) null, "externalParams")));
        wallpaperData.mSemWallpaperData.setUri(typedXmlPullParser.getAttributeValue((String) null, "uri"));
        wallpaperData.mSemWallpaperData.parseWallpaperHistoryInfo(typedXmlPullParser.getAttributeValue((String) null, "history"));
    }

    public final int getAttributeInt(TypedXmlPullParser typedXmlPullParser, String str, int i) {
        return typedXmlPullParser.getAttributeInt((String) null, str, i);
    }

    public final float getAttributeFloat(TypedXmlPullParser typedXmlPullParser, String str, float f) {
        return typedXmlPullParser.getAttributeFloat((String) null, str, f);
    }

    public void saveSettingsLocked(int i, WallpaperData wallpaperData, WallpaperData wallpaperData2) {
        JournaledFile makeJournaledFile = makeJournaledFile(i, wallpaperData == null ? 0 : WhichChecker.getMode(wallpaperData.mWhich));
        FileOutputStream fileOutputStream = null;
        try {
            FileOutputStream fileOutputStream2 = new FileOutputStream(makeJournaledFile.chooseForWrite(), false);
            try {
                TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(fileOutputStream2);
                resolveSerializer.startDocument((String) null, Boolean.TRUE);
                if (wallpaperData != null) {
                    writeWallpaperAttributes(resolveSerializer, "wp", wallpaperData);
                }
                if (wallpaperData2 != null) {
                    writeWallpaperAttributes(resolveSerializer, "kwp", wallpaperData2);
                }
                resolveSerializer.endDocument();
                fileOutputStream2.flush();
                FileUtils.sync(fileOutputStream2);
                fileOutputStream2.close();
                makeJournaledFile.commit();
            } catch (IOException unused) {
                fileOutputStream = fileOutputStream2;
                IoUtils.closeQuietly(fileOutputStream);
                makeJournaledFile.rollback();
            }
        } catch (IOException unused2) {
        }
    }

    public void writeWallpaperAttributes(TypedXmlSerializer typedXmlSerializer, String str, WallpaperData wallpaperData) {
        String json;
        WallpaperDisplayHelper.DisplayData displayDataOrCreate = this.mWallpaperDisplayHelper.getDisplayDataOrCreate(0);
        typedXmlSerializer.startTag((String) null, str);
        typedXmlSerializer.attributeInt((String) null, "id", wallpaperData.wallpaperId);
        typedXmlSerializer.attributeInt((String) null, "width", displayDataOrCreate.mWidth);
        typedXmlSerializer.attributeInt((String) null, "height", displayDataOrCreate.mHeight);
        typedXmlSerializer.attributeInt((String) null, "cropLeft", wallpaperData.cropHint.left);
        typedXmlSerializer.attributeInt((String) null, "cropTop", wallpaperData.cropHint.top);
        typedXmlSerializer.attributeInt((String) null, "cropRight", wallpaperData.cropHint.right);
        typedXmlSerializer.attributeInt((String) null, "cropBottom", wallpaperData.cropHint.bottom);
        int i = displayDataOrCreate.mPadding.left;
        if (i != 0) {
            typedXmlSerializer.attributeInt((String) null, "paddingLeft", i);
        }
        int i2 = displayDataOrCreate.mPadding.top;
        if (i2 != 0) {
            typedXmlSerializer.attributeInt((String) null, "paddingTop", i2);
        }
        int i3 = displayDataOrCreate.mPadding.right;
        if (i3 != 0) {
            typedXmlSerializer.attributeInt((String) null, "paddingRight", i3);
        }
        int i4 = displayDataOrCreate.mPadding.bottom;
        if (i4 != 0) {
            typedXmlSerializer.attributeInt((String) null, "paddingBottom", i4);
        }
        typedXmlSerializer.attributeFloat((String) null, "dimAmount", wallpaperData.mWallpaperDimAmount);
        int size = wallpaperData.mUidToDimAmount.size();
        typedXmlSerializer.attributeInt((String) null, "dimAmountsCount", size);
        if (size > 0) {
            int i5 = 0;
            for (int i6 = 0; i6 < wallpaperData.mUidToDimAmount.size(); i6++) {
                typedXmlSerializer.attributeInt((String) null, "dimUID" + i5, wallpaperData.mUidToDimAmount.keyAt(i6));
                typedXmlSerializer.attributeFloat((String) null, "dimValue" + i5, ((Float) wallpaperData.mUidToDimAmount.valueAt(i6)).floatValue());
                i5++;
            }
        }
        WallpaperColors wallpaperColors = wallpaperData.primaryColors;
        if (wallpaperColors != null) {
            int size2 = wallpaperColors.getMainColors().size();
            typedXmlSerializer.attributeInt((String) null, "colorsCount", size2);
            if (size2 > 0) {
                for (int i7 = 0; i7 < size2; i7++) {
                    typedXmlSerializer.attributeInt((String) null, "colorValue" + i7, ((Color) wallpaperData.primaryColors.getMainColors().get(i7)).toArgb());
                }
            }
            int size3 = wallpaperData.primaryColors.getAllColors().size();
            typedXmlSerializer.attributeInt((String) null, "allColorsCount", size3);
            if (size3 > 0) {
                int i8 = 0;
                for (Map.Entry entry : wallpaperData.primaryColors.getAllColors().entrySet()) {
                    typedXmlSerializer.attributeInt((String) null, "allColorsValue" + i8, ((Integer) entry.getKey()).intValue());
                    typedXmlSerializer.attributeInt((String) null, "allColorsPopulation" + i8, ((Integer) entry.getValue()).intValue());
                    i8++;
                }
            }
            typedXmlSerializer.attributeInt((String) null, "colorHints", wallpaperData.primaryColors.getColorHints());
        }
        typedXmlSerializer.attribute((String) null, "name", wallpaperData.name);
        ComponentName componentName = wallpaperData.wallpaperComponent;
        if (componentName != null && !componentName.equals(this.mImageWallpaper)) {
            typedXmlSerializer.attribute((String) null, "component", wallpaperData.wallpaperComponent.flattenToShortString());
        }
        if (wallpaperData.allowBackup) {
            typedXmlSerializer.attributeBoolean((String) null, "backup", true);
        }
        if (wallpaperData.mSemWallpaperData.getCreationTime() != null) {
            typedXmlSerializer.attributeInterned((String) null, "creationTime", wallpaperData.mSemWallpaperData.getCreationTime());
        }
        if (wallpaperData.mSemWallpaperData.getIsPreloaded()) {
            typedXmlSerializer.attributeInterned((String) null, "isPreloaded", "true");
        }
        if (wallpaperData.mSemWallpaperData.getIsCopied()) {
            typedXmlSerializer.attributeInterned((String) null, "isCopied", "true");
        }
        if (wallpaperData.mSemWallpaperData.getLastCallingPackage() != null) {
            typedXmlSerializer.attributeInterned((String) null, "lastCallingPackage", wallpaperData.mSemWallpaperData.getLastCallingPackage());
        }
        if (wallpaperData.mSemWallpaperData.getLastClearCallstackWithNullPackage() != null) {
            typedXmlSerializer.attributeInterned((String) null, "lastClearCallstackWithNullPackage", wallpaperData.mSemWallpaperData.getLastClearCallstackWithNullPackage());
        }
        Bundle externalParams = wallpaperData.mSemWallpaperData.getExternalParams();
        if (externalParams != null && (json = WallpaperExtraBundleHelper.toJson(externalParams)) != null) {
            typedXmlSerializer.attributeInterned((String) null, "externalParams", json);
        }
        typedXmlSerializer.attributeInterned((String) null, "type", Integer.toString(wallpaperData.mSemWallpaperData.getWpType()));
        if (!WhichChecker.isDex(wallpaperData.mWhich) && wallpaperData.mSemWallpaperData.getWpType() == 8) {
            if (wallpaperData.mSemWallpaperData.getVideoFilePath() != null) {
                typedXmlSerializer.attributeInterned((String) null, "videoFilePath", wallpaperData.mSemWallpaperData.getVideoFilePath());
            }
            if (wallpaperData.mSemWallpaperData.getVideoPkgName() != null) {
                typedXmlSerializer.attributeInterned((String) null, "videoPkgName", wallpaperData.mSemWallpaperData.getVideoPkgName());
            }
            if (wallpaperData.mSemWallpaperData.getVideoFileName() != null) {
                typedXmlSerializer.attributeInterned((String) null, "videoFileName", wallpaperData.mSemWallpaperData.getVideoFileName());
            }
        }
        if (this.mSemWallpaperResourcesInfo.getDefaultWallpaperType(2, this.mSemService.mCMFWallpaper.getDeviceColor()) == 8 && wallpaperData.mSemWallpaperData.getVideoDefaultHasBeenUsed()) {
            typedXmlSerializer.attributeInterned((String) null, "videoDefaultHasBeenUsed", wallpaperData.mSemWallpaperData.getVideoDefaultHasBeenUsed() ? "true" : "false");
        }
        if (str.equals("kwp")) {
            if (!WhichChecker.isDex(wallpaperData.mWhich) && wallpaperData.mSemWallpaperData.getWpType() == 1 && wallpaperData.mSemWallpaperData.getMotionPkgName() != null) {
                typedXmlSerializer.attributeInterned((String) null, "motionPkgName", wallpaperData.mSemWallpaperData.getMotionPkgName());
            }
            if (!WhichChecker.isDex(wallpaperData.mWhich) && wallpaperData.mSemWallpaperData.getWpType() == 4 && wallpaperData.mSemWallpaperData.getAnimatedPkgName() != null) {
                typedXmlSerializer.attributeInterned((String) null, "animatedPkgName", wallpaperData.mSemWallpaperData.getAnimatedPkgName());
            }
        }
        if (!WhichChecker.isDex(wallpaperData.mWhich) && ((wallpaperData.mSemWallpaperData.getWpType() == 3 || wallpaperData.mSemWallpaperData.getWpType() == 5 || wallpaperData.mSemWallpaperData.getWpType() == 0 || wallpaperData.mSemWallpaperData.getWpType() == 1000 || wallpaperData.mSemWallpaperData.getWpType() == 9) && wallpaperData.mSemWallpaperData.getUri() != null)) {
            typedXmlSerializer.attributeInterned((String) null, "uri", wallpaperData.mSemWallpaperData.getUri().toString());
        }
        ArrayList wallpaperHistoryList = wallpaperData.mSemWallpaperData.getWallpaperHistoryList();
        if (wallpaperHistoryList != null) {
            StringBuffer stringBuffer = new StringBuffer();
            int size4 = wallpaperHistoryList.size() - 1;
            if (size4 >= 0) {
                for (int i9 = 0; i9 <= size4; i9++) {
                    stringBuffer.append((String) wallpaperHistoryList.get(i9));
                    if (i9 != size4) {
                        stringBuffer.append(KnoxVpnFirewallHelper.DELIMITER);
                    }
                }
                typedXmlSerializer.attributeInterned((String) null, "history", stringBuffer.toString());
            }
        }
        typedXmlSerializer.endTag((String) null, str);
    }

    /* JADX WARN: Code restructure failed: missing block: B:53:0x017f, code lost:
    
        if (r3 != 0) goto L73;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0130, code lost:
    
        libcore.io.IoUtils.closeQuietly((java.lang.AutoCloseable) r2);
        libcore.io.IoUtils.closeQuietly((java.lang.AutoCloseable) r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x012d, code lost:
    
        android.os.FileUtils.sync(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0157, code lost:
    
        if (r3 != 0) goto L73;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x012b, code lost:
    
        if (r3 != 0) goto L73;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v18, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v10, types: [java.lang.AutoCloseable] */
    /* JADX WARN: Type inference failed for: r2v11, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r2v12 */
    /* JADX WARN: Type inference failed for: r2v13 */
    /* JADX WARN: Type inference failed for: r2v14 */
    /* JADX WARN: Type inference failed for: r2v15 */
    /* JADX WARN: Type inference failed for: r2v16 */
    /* JADX WARN: Type inference failed for: r2v17 */
    /* JADX WARN: Type inference failed for: r2v18 */
    /* JADX WARN: Type inference failed for: r2v19 */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r2v22, types: [java.io.FileOutputStream, java.lang.AutoCloseable] */
    /* JADX WARN: Type inference failed for: r2v25 */
    /* JADX WARN: Type inference failed for: r2v26 */
    /* JADX WARN: Type inference failed for: r2v27 */
    /* JADX WARN: Type inference failed for: r2v3 */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* JADX WARN: Type inference failed for: r2v6, types: [java.io.FileOutputStream, java.lang.AutoCloseable] */
    /* JADX WARN: Type inference failed for: r2v7, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r2v8, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r2v9 */
    /* JADX WARN: Type inference failed for: r3v10, types: [java.io.FileOutputStream, java.lang.AutoCloseable] */
    /* JADX WARN: Type inference failed for: r3v11 */
    /* JADX WARN: Type inference failed for: r3v12 */
    /* JADX WARN: Type inference failed for: r3v13, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r3v14, types: [java.lang.AutoCloseable] */
    /* JADX WARN: Type inference failed for: r3v15 */
    /* JADX WARN: Type inference failed for: r3v16 */
    /* JADX WARN: Type inference failed for: r3v17 */
    /* JADX WARN: Type inference failed for: r3v18 */
    /* JADX WARN: Type inference failed for: r3v19 */
    /* JADX WARN: Type inference failed for: r3v20 */
    /* JADX WARN: Type inference failed for: r3v21 */
    /* JADX WARN: Type inference failed for: r3v22 */
    /* JADX WARN: Type inference failed for: r3v23 */
    /* JADX WARN: Type inference failed for: r3v25 */
    /* JADX WARN: Type inference failed for: r3v26 */
    /* JADX WARN: Type inference failed for: r3v27 */
    /* JADX WARN: Type inference failed for: r3v28 */
    /* JADX WARN: Type inference failed for: r3v29, types: [java.io.FileOutputStream, java.lang.AutoCloseable] */
    /* JADX WARN: Type inference failed for: r3v3 */
    /* JADX WARN: Type inference failed for: r3v32 */
    /* JADX WARN: Type inference failed for: r3v33 */
    /* JADX WARN: Type inference failed for: r3v34 */
    /* JADX WARN: Type inference failed for: r3v35 */
    /* JADX WARN: Type inference failed for: r3v4, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r3v5 */
    /* JADX WARN: Type inference failed for: r3v6 */
    /* JADX WARN: Type inference failed for: r3v7 */
    /* JADX WARN: Type inference failed for: r3v8 */
    /* JADX WARN: Type inference failed for: r3v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean restoreNamedResourceLocked(WallpaperData wallpaperData) {
        ?? r2 = 4;
        if (wallpaperData.name.length() > 4 && "res:".equals(wallpaperData.name.substring(0, 4))) {
            String substring = wallpaperData.name.substring(4);
            int indexOf = substring.indexOf(58);
            InputStream inputStream = null;
            String substring2 = indexOf > 0 ? substring.substring(0, indexOf) : null;
            int lastIndexOf = substring.lastIndexOf(47);
            String substring3 = lastIndexOf > 0 ? substring.substring(lastIndexOf + 1) : null;
            ?? substring4 = (indexOf <= 0 || lastIndexOf <= 0 || lastIndexOf - indexOf <= 1) ? 0 : substring.substring(indexOf + 1, lastIndexOf);
            if (substring2 != null && substring3 != null && substring4 != 0) {
                int i = -1;
                try {
                    try {
                        Resources resources = this.mContext.createPackageContext(substring2, 4).getResources();
                        i = resources.getIdentifier(substring, null, null);
                        if (i == 0) {
                            Slog.e(TAG, "couldn't resolve identifier pkg=" + substring2 + " type=" + substring4 + " ident=" + substring3);
                            IoUtils.closeQuietly((AutoCloseable) null);
                            IoUtils.closeQuietly((AutoCloseable) null);
                            IoUtils.closeQuietly((AutoCloseable) null);
                            return false;
                        }
                        InputStream openRawResource = resources.openRawResource(i);
                        try {
                            if (wallpaperData.wallpaperFile.exists()) {
                                wallpaperData.wallpaperFile.delete();
                                wallpaperData.cropFile.delete();
                            }
                            r2 = new FileOutputStream(wallpaperData.wallpaperFile);
                            try {
                                substring4 = new FileOutputStream(wallpaperData.cropFile);
                            } catch (PackageManager.NameNotFoundException unused) {
                                substring4 = 0;
                            } catch (Resources.NotFoundException unused2) {
                                substring4 = 0;
                            } catch (IOException e) {
                                e = e;
                                substring4 = 0;
                            } catch (Throwable th) {
                                th = th;
                                substring4 = 0;
                            }
                            try {
                                byte[] bArr = new byte[32768];
                                while (true) {
                                    int read = openRawResource.read(bArr);
                                    if (read <= 0) {
                                        Slog.v(TAG, "Restored wallpaper: " + substring);
                                        IoUtils.closeQuietly(openRawResource);
                                        FileUtils.sync(r2);
                                        FileUtils.sync(substring4);
                                        IoUtils.closeQuietly((AutoCloseable) r2);
                                        IoUtils.closeQuietly((AutoCloseable) substring4);
                                        return true;
                                    }
                                    r2.write(bArr, 0, read);
                                    substring4.write(bArr, 0, read);
                                }
                            } catch (PackageManager.NameNotFoundException unused3) {
                                inputStream = openRawResource;
                                r2 = r2;
                                substring4 = substring4;
                                Slog.e(TAG, "Package name " + substring2 + " not found");
                                IoUtils.closeQuietly(inputStream);
                                if (r2 != 0) {
                                    FileUtils.sync(r2);
                                }
                            } catch (Resources.NotFoundException unused4) {
                                inputStream = openRawResource;
                                r2 = r2;
                                substring4 = substring4;
                                Slog.e(TAG, "Resource not found: " + i);
                                IoUtils.closeQuietly(inputStream);
                                if (r2 != 0) {
                                    FileUtils.sync(r2);
                                }
                            } catch (IOException e2) {
                                e = e2;
                                inputStream = openRawResource;
                                e = e;
                                r2 = r2;
                                substring4 = substring4;
                                Slog.e(TAG, "IOException while restoring wallpaper ", e);
                                IoUtils.closeQuietly(inputStream);
                                if (r2 != 0) {
                                    FileUtils.sync(r2);
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                inputStream = openRawResource;
                                th = th;
                                IoUtils.closeQuietly(inputStream);
                                if (r2 != 0) {
                                    FileUtils.sync(r2);
                                }
                                if (substring4 != 0) {
                                    FileUtils.sync(substring4);
                                }
                                IoUtils.closeQuietly((AutoCloseable) r2);
                                IoUtils.closeQuietly((AutoCloseable) substring4);
                                throw th;
                            }
                        } catch (PackageManager.NameNotFoundException unused5) {
                            r2 = 0;
                            substring4 = 0;
                        } catch (Resources.NotFoundException unused6) {
                            r2 = 0;
                            substring4 = 0;
                        } catch (IOException e3) {
                            e = e3;
                            r2 = 0;
                            substring4 = 0;
                        } catch (Throwable th3) {
                            th = th3;
                            r2 = 0;
                            substring4 = 0;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                    }
                } catch (PackageManager.NameNotFoundException unused7) {
                    r2 = 0;
                    substring4 = 0;
                } catch (Resources.NotFoundException unused8) {
                    r2 = 0;
                    substring4 = 0;
                } catch (IOException e4) {
                    e = e4;
                    r2 = 0;
                    substring4 = 0;
                } catch (Throwable th5) {
                    th = th5;
                    r2 = 0;
                    substring4 = 0;
                }
            }
        }
        return false;
    }
}
