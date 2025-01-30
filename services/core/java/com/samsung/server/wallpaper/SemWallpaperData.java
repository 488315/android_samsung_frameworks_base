package com.samsung.server.wallpaper;

import android.app.SemWallpaperColors;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.samsung.android.wallpaper.utils.WallpaperExtraBundleHelper;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes2.dex */
public class SemWallpaperData implements Cloneable {
    public Bitmap mCroppedBitmap;
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

    public SemWallpaperData() {
        Log.m129d("SemWallpaperData", "SemWallpaperData");
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("\n    mTimeCreated=");
            sb.append(this.mTimeCreated);
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

    public void setIsCopied(boolean z) {
        this.mIsCopied = z;
    }

    public boolean getIsCopied() {
        return this.mIsCopied;
    }

    public void setIsPreloaded(boolean z) {
        this.mIsPreloaded = z;
    }

    public boolean getIsPreloaded() {
        return this.mIsPreloaded;
    }

    public void setOrientation(int i) {
        this.mOrientation = i;
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public void setWhich(int i) {
        this.mWhich = i;
    }

    public int getWhich() {
        return this.mWhich;
    }

    public void setWpType(int i) {
        this.mWpType = i;
    }

    public int getWpType() {
        return this.mWpType;
    }

    public void setWidth(int i) {
        this.mWidth = i;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public void setHeight(int i) {
        this.mHeight = i;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public void setLastCallingPackage(String str) {
        this.mLastCallingPackage = str;
    }

    public String getLastCallingPackage() {
        return getLastCallingPackage(true);
    }

    public String getLastCallingPackage(boolean z) {
        if (z) {
            return this.mLastCallingPackage;
        }
        if (TextUtils.isEmpty(this.mLastCallingPackage)) {
            return null;
        }
        try {
            int length = this.mLastCallingPackage.length();
            int indexOf = this.mLastCallingPackage.contains("]") ? this.mLastCallingPackage.indexOf("]") + 1 : 0;
            if (this.mLastCallingPackage.contains("(")) {
                length = this.mLastCallingPackage.indexOf("(");
            }
            return this.mLastCallingPackage.substring(indexOf, length);
        } catch (IndexOutOfBoundsException e) {
            Log.m130e("SemWallpaperData", "getLastCallingPackage: " + e.getMessage());
            return this.mLastCallingPackage;
        }
    }

    public void setLastClearCallstackWithNullPackage(String str) {
        this.mLastClearCallstackWithNullPackage = str;
    }

    public String getLastClearCallstackWithNullPackage() {
        return this.mLastClearCallstackWithNullPackage;
    }

    public void setUri(String str) {
        this.mUri = str;
    }

    public String getUri() {
        return this.mUri;
    }

    public Bundle getExternalParams() {
        return this.mExternalParams;
    }

    public void setExternalParams(Bundle bundle) {
        this.mExternalParams = bundle;
    }

    public void setWaitingForUnlockUser(boolean z) {
        this.mWaitingForUnlockUser = z;
    }

    public boolean getWaitingForUnlockUser() {
        return this.mWaitingForUnlockUser;
    }

    public void setCroppedBitmap(Bitmap bitmap) {
        this.mCroppedBitmap = bitmap;
    }

    public Bitmap getCroppedBitmap() {
        return this.mCroppedBitmap;
    }

    public String getWallpaperHistory() {
        return this.mWallpaperHistory.getCallingPackage();
    }

    public void setWallpaperHistory(String str, String str2, String str3, String str4) {
        this.mLastCallingPackage = str;
        this.mWallpaperHistory.addWallpaperHistoryData(str, str2, str3, str4);
    }

    public WallpaperHistroy getWallpaperHistories() {
        return this.mWallpaperHistory;
    }

    public void setWallpaperHistories(WallpaperHistroy wallpaperHistroy) {
        this.mWallpaperHistory = wallpaperHistroy.m14848clone();
    }

    public ArrayList getWallpaperHistoryList() {
        return this.mWallpaperHistory.getWallpaperHistoryDataList();
    }

    public void parseWallpaperHistoryInfo(XmlPullParser xmlPullParser) {
        try {
            String nextText = xmlPullParser.nextText();
            if (nextText.isEmpty()) {
                return;
            }
            for (String str : nextText.split(KnoxVpnFirewallHelper.DELIMITER)) {
                String[] split = str.split("::");
                setWallpaperHistory(split[1], split[0], split.length > 2 ? split[2] : "", split.length > 3 ? split[3] : "");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e2) {
            e2.printStackTrace();
        }
    }

    public void parseWallpaperHistoryInfo(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        for (String str2 : str.split(KnoxVpnFirewallHelper.DELIMITER)) {
            String[] split = str2.split("::");
            String str3 = split[1];
            String str4 = split[0];
            String str5 = "";
            String str6 = split.length > 2 ? split[2] : "";
            if (split.length > 3) {
                str5 = split[3];
            }
            setWallpaperHistory(str3, str4, str6, str5);
        }
    }

    public final String getDlsStringType() {
        String[] split;
        String str = this.mUri;
        return (str == null || (split = str.split("/")) == null || split.length <= 1) ? "default" : split[1];
    }

    public String getWallpaperTypeString() {
        int wpType = getWpType();
        return wpType != 0 ? wpType != 1 ? wpType != 3 ? wpType != 4 ? wpType != 5 ? wpType != 7 ? wpType != 8 ? wpType != 1000 ? "default" : getDlsStringType() : "video" : "live" : "gif" : "animated" : "multiple" : "motion" : "image";
    }

    public boolean isThemeContents() {
        String wallpaperHistory = getWallpaperHistory();
        if (TextUtils.isEmpty(wallpaperHistory) || !wallpaperHistory.equals("com.samsung.android.themecenter")) {
            return false;
        }
        Log.m129d("SemWallpaperData", "Theme contents.");
        return true;
    }

    public void setCreationTime(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.mTimeCreated = str;
    }

    public String getCreationTime() {
        return this.mTimeCreated;
    }

    public String getCurrentTimeString() {
        return SimpleDateFormat.getDateTimeInstance().format(new Date(System.currentTimeMillis()));
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public SemWallpaperData m14847clone() {
        try {
            SemWallpaperData semWallpaperData = (SemWallpaperData) super.clone();
            semWallpaperData.mLastCallingPackage = this.mLastCallingPackage;
            semWallpaperData.mLastClearCallstackWithNullPackage = this.mLastClearCallstackWithNullPackage;
            semWallpaperData.mUri = this.mUri;
            semWallpaperData.mWallpaperHistory = this.mWallpaperHistory.m14848clone();
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
                semWallpaperData.mLandscapeColors = new SemWallpaperColors[semWallpaperColorsArr.length];
                int i2 = 0;
                while (true) {
                    SemWallpaperColors[] semWallpaperColorsArr2 = this.mLandscapeColors;
                    if (i2 >= semWallpaperColorsArr2.length) {
                        break;
                    }
                    semWallpaperData.mLandscapeColors[i2] = semWallpaperColorsArr2[i2].clone();
                    i2++;
                }
            }
            Log.m129d("SemWallpaperData", "clone: " + semWallpaperData);
            return semWallpaperData;
        } catch (CloneNotSupportedException e) {
            Log.m130e("SemWallpaperData", "" + e.getMessage());
            return null;
        }
    }

    public class WallpaperHistroy implements Cloneable {
        public final ArrayList wallpaperHistoryDataList = new ArrayList();

        public WallpaperHistroy() {
        }

        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public WallpaperHistroy m14848clone() {
            WallpaperHistroy wallpaperHistroy = SemWallpaperData.this.new WallpaperHistroy();
            if (this.wallpaperHistoryDataList.size() > 0) {
                wallpaperHistroy.wallpaperHistoryDataList.addAll(this.wallpaperHistoryDataList);
            }
            return wallpaperHistroy;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            synchronized (this.wallpaperHistoryDataList) {
                try {
                    sb.append("\n\thistory - [time]::[callingPkg]::[type]::[contents]");
                    int size = this.wallpaperHistoryDataList.size();
                    for (int i = 0; i < size; i++) {
                        sb.append("\n\t" + ((WallpaperHistoryData) this.wallpaperHistoryDataList.get(i)).getWallpaperHistoryData());
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
            return sb.toString();
        }

        public String getCallingPackage() {
            synchronized (this.wallpaperHistoryDataList) {
                if (this.wallpaperHistoryDataList.isEmpty()) {
                    return "";
                }
                return ((WallpaperHistoryData) this.wallpaperHistoryDataList.get(this.wallpaperHistoryDataList.size() - 1)).getCallingPackageName();
            }
        }

        public void addWallpaperHistoryData(String str, String str2, String str3, String str4) {
            synchronized (this.wallpaperHistoryDataList) {
                this.wallpaperHistoryDataList.add(new WallpaperHistoryData(str, str2, str3, str4));
                if (this.wallpaperHistoryDataList.size() > 20) {
                    this.wallpaperHistoryDataList.remove(0);
                }
            }
        }

        public ArrayList getWallpaperHistoryDataList() {
            synchronized (this.wallpaperHistoryDataList) {
                ArrayList arrayList = new ArrayList();
                if (this.wallpaperHistoryDataList.isEmpty()) {
                    return null;
                }
                Iterator it = this.wallpaperHistoryDataList.iterator();
                while (it.hasNext()) {
                    WallpaperHistoryData wallpaperHistoryData = (WallpaperHistoryData) it.next();
                    if (wallpaperHistoryData != null) {
                        arrayList.add(wallpaperHistoryData.getWallpaperHistoryData());
                    }
                }
                return arrayList;
            }
        }

        public class WallpaperHistoryData {
            public String callingPackageName;
            public String pkgName;
            public String time;
            public String type;

            public WallpaperHistoryData(String str, String str2, String str3, String str4) {
                this.callingPackageName = str;
                if (str2 == null) {
                    this.time = SemWallpaperData.this.getCurrentTimeString();
                } else {
                    this.time = str2;
                }
                this.type = str3;
                this.pkgName = str4;
            }

            public String getWallpaperHistoryData() {
                String str = this.time + "::" + this.callingPackageName;
                if (!TextUtils.isEmpty(this.type)) {
                    str = str + "::" + this.type;
                }
                if (TextUtils.isEmpty(this.pkgName)) {
                    return str;
                }
                return str + "::" + this.pkgName;
            }

            public String getCallingPackageName() {
                return this.callingPackageName;
            }
        }
    }

    public void setMotionPkgName(String str) {
        this.mMotionPkgName = str;
    }

    public String getMotionPkgName() {
        return this.mMotionPkgName;
    }

    public void setMotionBackground(File file) {
        this.mMotionBackground = file;
    }

    public File getMotionBackground() {
        return this.mMotionBackground;
    }

    public void setAnimatedPkgName(String str) {
        this.mAnimatedPkgName = str;
    }

    public String getAnimatedPkgName() {
        return this.mAnimatedPkgName;
    }

    public void setAnimatedBackground(File file) {
        this.mAnimatedBackground = file;
    }

    public File getAnimatedBackground() {
        return this.mAnimatedBackground;
    }

    public void setVideoFirstFrameFile(File file) {
        this.mVideoFirstFrameFile = file;
    }

    public File getVideoFirstFrameFile() {
        return this.mVideoFirstFrameFile;
    }

    public void setVideoFilePath(String str) {
        this.mVideoFilePath = str;
    }

    public String getVideoFilePath() {
        return this.mVideoFilePath;
    }

    public void setVideoPkgName(String str) {
        this.mVideoPkgName = str;
    }

    public String getVideoPkgName() {
        return this.mVideoPkgName;
    }

    public void setVideoFileName(String str) {
        this.mVideoFileName = str;
    }

    public String getVideoFileName() {
        return this.mVideoFileName;
    }

    public void setVideoDefaultHasBeenUsed(boolean z) {
        this.mVideoDefaultHasBeenUsed = z;
    }

    public boolean getVideoDefaultHasBeenUsed() {
        return this.mVideoDefaultHasBeenUsed;
    }

    public void setIsDesktopWallpaper(boolean z) {
        this.mIsDesktopWallpaper = z;
    }

    public boolean getIsDesktopWallpaper() {
        return this.mIsDesktopWallpaper;
    }

    public void setPrimarySemColors(SemWallpaperColors semWallpaperColors) {
        this.mPrimarySemColors = semWallpaperColors;
    }

    public SemWallpaperColors getPrimarySemColors() {
        return this.mPrimarySemColors;
    }

    public void setDlsSemColors(SemWallpaperColors semWallpaperColors) {
        this.mDlsSemColors = semWallpaperColors;
    }

    public SemWallpaperColors getDlsSemColors() {
        return this.mDlsSemColors;
    }

    public void setSmartCropOriginalRect(Rect rect) {
        this.mSmartCropOriginalRect = rect;
    }

    public Rect getSmartCropOriginalRect() {
        return this.mSmartCropOriginalRect;
    }

    public void setSmartCropRect(Rect rect) {
        this.mSmartCropRect = rect;
    }

    public Rect getSmartCropRect() {
        return this.mSmartCropRect;
    }

    public void setLandscapeColors(SemWallpaperColors[] semWallpaperColorsArr) {
        this.mLandscapeColors = semWallpaperColorsArr;
    }

    public SemWallpaperColors[] getLandscapeColors() {
        return this.mLandscapeColors;
    }
}
