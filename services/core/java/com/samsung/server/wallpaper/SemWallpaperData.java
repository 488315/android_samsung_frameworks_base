package com.samsung.server.wallpaper;

import android.app.SemWallpaperColors;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;

import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;

import com.samsung.android.wallpaper.utils.WallpaperExtraBundleHelper;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SemWallpaperData implements Cloneable {
    public SemWallpaperColors mDlsSemColors;
    public SemWallpaperColors[] mLandscapeColors;
    public int mOrientation;
    public SemWallpaperColors mPrimarySemColors;
    public Rect mSmartCropOriginalRect;
    public Rect mSmartCropRect;
    public int mWpType;
    public boolean mIsCopied = false;
    public boolean mIsPreloaded = true;
    public int mWhich = -1;
    public int mWidth = -1;
    public int mHeight = -1;
    public String mLastCallingPackage = null;
    public String mLastClearCallstackWithNullPackage = null;
    public String mUri = null;
    public Bundle mExternalParams = null;
    public WallpaperHistroy mWallpaperHistory = new WallpaperHistroy();
    public String mTimeCreated = null;
    public String mMotionPkgName = null;
    public File mMotionBackground = null;
    public String mAnimatedPkgName = null;
    public File mAnimatedBackground = null;
    public File mVideoFirstFrameFile = null;
    public String mVideoFilePath = null;
    public String mVideoPkgName = null;
    public String mVideoFileName = null;
    public boolean mVideoDefaultHasBeenUsed = false;
    public boolean mIsDesktopWallpaper = false;
    public boolean mWaitingForUnlockUser = false;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class WallpaperHistroy implements Cloneable {
        public final ArrayList wallpaperHistoryDataList = new ArrayList();

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class WallpaperHistoryData {
            public String callingPackageName;
            public String pkgName;
            public String time;
            public String type;

            public final String getWallpaperHistoryData() {
                String str = this.time + "::" + this.callingPackageName;
                String str2 = this.type;
                if (!TextUtils.isEmpty(str2)) {
                    str = AnyMotionDetector$$ExternalSyntheticOutline0.m(str, "::", str2);
                }
                String str3 = this.pkgName;
                return !TextUtils.isEmpty(str3)
                        ? AnyMotionDetector$$ExternalSyntheticOutline0.m(str, "::", str3)
                        : str;
            }
        }

        public WallpaperHistroy() {}

        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public final WallpaperHistroy m1238clone() {
            WallpaperHistroy wallpaperHistroy = SemWallpaperData.this.new WallpaperHistroy();
            if (this.wallpaperHistoryDataList.size() > 0) {
                wallpaperHistroy.wallpaperHistoryDataList.addAll(this.wallpaperHistoryDataList);
            }
            return wallpaperHistroy;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            synchronized (this.wallpaperHistoryDataList) {
                try {
                    sb.append("\n\thistory - [time]::[callingPkg]::[type]::[contents]");
                    int size = this.wallpaperHistoryDataList.size();
                    for (int i = 0; i < size; i++) {
                        sb.append(
                                "\n\t"
                                        + ((WallpaperHistoryData)
                                                        this.wallpaperHistoryDataList.get(i))
                                                .getWallpaperHistoryData());
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
            return sb.toString();
        }
    }

    public SemWallpaperData() {
        Log.d("SemWallpaperData", "SemWallpaperData");
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public final SemWallpaperData m1237clone() {
        try {
            SemWallpaperData semWallpaperData = (SemWallpaperData) super.clone();
            semWallpaperData.mLastCallingPackage = this.mLastCallingPackage;
            semWallpaperData.mLastClearCallstackWithNullPackage =
                    this.mLastClearCallstackWithNullPackage;
            semWallpaperData.mUri = this.mUri;
            semWallpaperData.mWallpaperHistory = this.mWallpaperHistory.m1238clone();
            semWallpaperData.mTimeCreated = this.mTimeCreated;
            Bundle bundle = this.mExternalParams;
            if (bundle != null) {
                semWallpaperData.mExternalParams = WallpaperExtraBundleHelper.cloneBundle(bundle);
            }
            int i = this.mWpType;
            if (i == 1) {
                semWallpaperData.mMotionPkgName = this.mMotionPkgName;
            } else if (i == 4) {
                semWallpaperData.mAnimatedPkgName = this.mAnimatedPkgName;
            } else if (i == 8) {
                semWallpaperData.mVideoFilePath = this.mVideoFilePath;
                semWallpaperData.mVideoPkgName = this.mVideoPkgName;
                semWallpaperData.mVideoFileName = this.mVideoFileName;
            }
            SemWallpaperColors semWallpaperColors = this.mPrimarySemColors;
            if (semWallpaperColors != null) {
                semWallpaperData.mPrimarySemColors = semWallpaperColors.clone();
            }
            SemWallpaperColors semWallpaperColors2 = this.mDlsSemColors;
            if (semWallpaperColors2 != null) {
                semWallpaperData.mDlsSemColors = semWallpaperColors2.clone();
            }
            if (this.mSmartCropOriginalRect != null) {
                semWallpaperData.mSmartCropOriginalRect = new Rect(this.mSmartCropOriginalRect);
            }
            if (this.mSmartCropRect != null) {
                semWallpaperData.mSmartCropRect = new Rect(this.mSmartCropRect);
            }
            SemWallpaperColors[] semWallpaperColorsArr = this.mLandscapeColors;
            if (semWallpaperColorsArr != null) {
                semWallpaperData.mLandscapeColors =
                        new SemWallpaperColors[semWallpaperColorsArr.length];
                int i2 = 0;
                while (true) {
                    SemWallpaperColors[] semWallpaperColorsArr2 = this.mLandscapeColors;
                    if (i2 >= semWallpaperColorsArr2.length) {
                        break;
                    }
                    SemWallpaperColors semWallpaperColors3 = semWallpaperColorsArr2[i2];
                    if (semWallpaperColors3 != null) {
                        semWallpaperData.mLandscapeColors[i2] = semWallpaperColors3.clone();
                    }
                    i2++;
                }
            }
            Log.d("SemWallpaperData", "clone: " + semWallpaperData);
            return semWallpaperData;
        } catch (CloneNotSupportedException e) {
            Log.e("SemWallpaperData", "" + e.getMessage());
            return null;
        }
    }

    public final String getLastCallingPackage(boolean z) {
        if (z) {
            return this.mLastCallingPackage;
        }
        if (TextUtils.isEmpty(this.mLastCallingPackage)) {
            return null;
        }
        try {
            int length = this.mLastCallingPackage.length();
            int indexOf =
                    this.mLastCallingPackage.contains("]")
                            ? this.mLastCallingPackage.indexOf("]") + 1
                            : 0;
            if (this.mLastCallingPackage.contains("(")) {
                length = this.mLastCallingPackage.indexOf("(");
            }
            return this.mLastCallingPackage.substring(indexOf, length);
        } catch (IndexOutOfBoundsException e) {
            Log.e("SemWallpaperData", "getLastCallingPackage: " + e.getMessage());
            return this.mLastCallingPackage;
        }
    }

    public final String getWallpaperHistory() {
        String str;
        WallpaperHistroy wallpaperHistroy = this.mWallpaperHistory;
        synchronized (wallpaperHistroy.wallpaperHistoryDataList) {
            try {
                if (wallpaperHistroy.wallpaperHistoryDataList.isEmpty()) {
                    str = "";
                } else {
                    str =
                            ((WallpaperHistroy.WallpaperHistoryData)
                                            wallpaperHistroy.wallpaperHistoryDataList.get(
                                                    wallpaperHistroy.wallpaperHistoryDataList.size()
                                                            - 1))
                                    .callingPackageName;
                }
            } finally {
            }
        }
        return str;
    }

    public final ArrayList getWallpaperHistoryList() {
        WallpaperHistroy wallpaperHistroy = this.mWallpaperHistory;
        synchronized (wallpaperHistroy.wallpaperHistoryDataList) {
            try {
                ArrayList arrayList = new ArrayList();
                if (wallpaperHistroy.wallpaperHistoryDataList.isEmpty()) {
                    return null;
                }
                Iterator it = wallpaperHistroy.wallpaperHistoryDataList.iterator();
                while (it.hasNext()) {
                    WallpaperHistroy.WallpaperHistoryData wallpaperHistoryData =
                            (WallpaperHistroy.WallpaperHistoryData) it.next();
                    if (wallpaperHistoryData != null) {
                        arrayList.add(wallpaperHistoryData.getWallpaperHistoryData());
                    }
                }
                return arrayList;
            } finally {
            }
        }
    }

    public final void parseWallpaperHistoryInfo(XmlPullParser xmlPullParser) {
        try {
            String nextText = xmlPullParser.nextText();
            if (nextText.isEmpty()) {
                return;
            }
            for (String str : nextText.split(";")) {
                String[] split = str.split("::");
                setWallpaperHistory(
                        split[1],
                        split[0],
                        split.length > 2 ? split[2] : "",
                        split.length > 3 ? split[3] : "");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e2) {
            e2.printStackTrace();
        }
    }

    public final void setWallpaperHistory(String str, String str2, String str3, String str4) {
        this.mLastCallingPackage = str;
        WallpaperHistroy wallpaperHistroy = this.mWallpaperHistory;
        synchronized (wallpaperHistroy.wallpaperHistoryDataList) {
            try {
                ArrayList arrayList = wallpaperHistroy.wallpaperHistoryDataList;
                WallpaperHistroy.WallpaperHistoryData wallpaperHistoryData =
                        new WallpaperHistroy.WallpaperHistoryData();
                wallpaperHistoryData.callingPackageName = str;
                if (str2 == null) {
                    SemWallpaperData.this.getClass();
                    wallpaperHistoryData.time =
                            SimpleDateFormat.getDateTimeInstance()
                                    .format(new Date(System.currentTimeMillis()));
                } else {
                    wallpaperHistoryData.time = str2;
                }
                wallpaperHistoryData.type = str3;
                wallpaperHistoryData.pkgName = str4;
                arrayList.add(wallpaperHistoryData);
                if (wallpaperHistroy.wallpaperHistoryDataList.size() > 20) {
                    wallpaperHistroy.wallpaperHistoryDataList.remove(0);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("\n    mTimeCreated=");
            sb.append(this.mTimeCreated);
            sb.append("\n    mWidth=");
            sb.append(this.mWidth);
            sb.append("\n    mHeight=");
            sb.append(this.mHeight);
            sb.append("\n    mIsCopied=");
            sb.append(this.mIsCopied);
            sb.append("\n    mIsPreloaded=");
            sb.append(this.mIsPreloaded);
            sb.append("\n    mOrientation=");
            sb.append(this.mOrientation);
            sb.append("\n    mWhich=0x");
            sb.append(Integer.toHexString(this.mWhich));
            sb.append("\n    mWpType=");
            sb.append(this.mWpType);
            sb.append("\n    mUri=");
            sb.append(this.mUri);
            sb.append("\n    mLastCallingPackage=");
            sb.append(this.mLastCallingPackage);
            sb.append("\n    mExternalParams=");
            sb.append(WallpaperExtraBundleHelper.toJson(this.mExternalParams));
            if (this.mWpType == 1) {
                sb.append("\n    mMotionPkgName=");
                sb.append(this.mMotionPkgName);
            }
            if (this.mWpType == 4) {
                sb.append("\n    mAnimatedPkgName=");
                sb.append(this.mAnimatedPkgName);
            }
            if (this.mWpType == 8) {
                sb.append("\n    mVideoFilePath=");
                sb.append(this.mVideoFilePath);
                sb.append(", mVideoPkgName=");
                sb.append(this.mVideoPkgName);
                sb.append(", mVideoFileName=");
                sb.append(this.mVideoFileName);
                sb.append(", mVideoDefaultHasBeenUsed=");
                sb.append(this.mVideoDefaultHasBeenUsed);
            }
            if (this.mWpType == 7) {
                sb.append(", mVideoFileName=");
                sb.append(this.mVideoFileName);
            }
            sb.append("\n    mIsDesktopWallpaper=");
            sb.append(this.mIsDesktopWallpaper);
            sb.append("\n    mPrimarySemColors=");
            sb.append(this.mPrimarySemColors);
            sb.append("\n    mDlsSemColors=");
            sb.append(this.mDlsSemColors);
            sb.append("\n    mSmartCropOriginalRect=");
            sb.append(this.mSmartCropOriginalRect);
            sb.append(", mSmartCropRect=");
            sb.append(this.mSmartCropRect);
            SemWallpaperColors[] semWallpaperColorsArr = this.mLandscapeColors;
            if (semWallpaperColorsArr != null) {
                for (SemWallpaperColors semWallpaperColors : semWallpaperColorsArr) {
                    sb.append("\n    mLandscapeColors=");
                    sb.append(semWallpaperColors);
                }
            }
            sb.append("\n    mWallpaperHistory=");
            sb.append(this.mWallpaperHistory);
            sb.append("\n    mLastClearCallstackWithNullPackage=");
            sb.append(this.mLastClearCallstackWithNullPackage);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
