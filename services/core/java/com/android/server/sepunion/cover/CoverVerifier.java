package com.android.server.sepunion.cover;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.SystemProperties;
import android.view.Display;
import android.view.WindowManager;

import com.android.server.ServiceKeeper$$ExternalSyntheticOutline0;

import com.samsung.android.cover.CoverState;
import com.samsung.android.sepunion.Log;

import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class CoverVerifier {
    public Context mContext;
    public int mDefaultClearCoverHeight;
    public int mDefaultClearCoverWidth;
    public boolean mIsCoverAttached;
    public boolean mIsCoverVerified;

    public final void dump(PrintWriter printWriter) {
        printWriter.println(" Current CoverVerifier state:");
        printWriter.print("  mIsCoverVerified=");
        printWriter.print(this.mIsCoverVerified);
        printWriter.print("  mIsCoverAttached=");
        printWriter.println(this.mIsCoverAttached);
        printWriter.print("  mDefaultCoverType=");
        printWriter.print(getDefaultTypeOfCover());
        printWriter.print("  mDefaultCoverColor=");
        printWriter.println(0);
        printWriter.print("  mDefaultSViewCoverWidth=");
        printWriter.print(0);
        printWriter.print("  mDefaultSViewCoverHeight=");
        printWriter.print(0);
        printWriter.print("  mDefaultMiniSViewCoverWidth=");
        printWriter.print(0);
        printWriter.print("  mDefaultMiniSViewCoverHeight=");
        printWriter.println(0);
        printWriter.println("  ");
    }

    public final int getDefaultTypeOfCover() {
        Feature.getInstance(this.mContext).getClass();
        if (Feature.sIsDeviceSupportVerifyCover) {
            Feature.getInstance(this.mContext).getClass();
            if (Feature.sIsSupportFlipCover) {
                if ((CoverTestModeUtils.SHIPPED ? -1 : CoverTestModeUtils.sCurrentTestMode) == 0) {
                    return 0;
                }
            }
            Feature.getInstance(this.mContext).getClass();
            if (Feature.sIsSupportClearCover) {
                if ((CoverTestModeUtils.SHIPPED ? -1 : CoverTestModeUtils.sCurrentTestMode) == 8) {
                    return 8;
                }
            }
            Feature.getInstance(this.mContext).getClass();
            if (Feature.sIsSupportNeonCover) {
                if ((CoverTestModeUtils.SHIPPED ? -1 : CoverTestModeUtils.sCurrentTestMode) == 11) {
                    return 11;
                }
            }
            return 2;
        }
        Feature.getInstance(this.mContext).getClass();
        String str = Feature.sDeviceTypeProperty;
        if (str == null || !str.contains("tablet")) {
            Feature.getInstance(this.mContext).getClass();
            if (Feature.sIsSupportFlipCover) {
                return 0;
            }
            Feature.getInstance(this.mContext).getClass();
            if (Feature.sIsSupportClearCover) {
                return 8;
            }
            Feature.getInstance(this.mContext).getClass();
            if (Feature.sIsSupportNeonCover) {
                return 11;
            }
        } else {
            Feature.getInstance(this.mContext).getClass();
            if (Feature.sIsSupportFlipCover) {
                return 0;
            }
        }
        return 2;
    }

    public final void getScreenSizeForClearCover() {
        Display defaultDisplay =
                ((WindowManager) this.mContext.getSystemService("window")).getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getRealSize(point);
        int rotation = defaultDisplay.getRotation();
        if (rotation != 0) {
            if (rotation != 1) {
                if (rotation != 2) {
                    if (rotation != 3) {
                        this.mDefaultClearCoverWidth = point.x;
                        this.mDefaultClearCoverHeight = point.y;
                        return;
                    }
                }
            }
            this.mDefaultClearCoverWidth = point.y;
            this.mDefaultClearCoverHeight = point.x;
            return;
        }
        this.mDefaultClearCoverWidth = point.x;
        this.mDefaultClearCoverHeight = point.y;
    }

    public final int getSupportSViewCoverHeight(int i) {
        if (i == 8 || i == 11) {
            return this.mDefaultClearCoverHeight;
        }
        return 0;
    }

    public final int getSupportSViewCoverWidth(int i) {
        if (i == 8 || i == 11) {
            return this.mDefaultClearCoverWidth;
        }
        return 0;
    }

    public final void updateCoverPropertiesLocked(CoverState coverState, CoverState coverState2) {
        int defaultTypeOfCover;
        int i;
        int i2;
        Feature.getInstance(this.mContext).getClass();
        if (!Feature.sIsNfcAuthSystemFeatureEnabled) {
            if (CoverTestModeUtils.isTestMode() || !this.mIsCoverAttached) {
                defaultTypeOfCover = getDefaultTypeOfCover();
                i = 0;
                i2 = 0;
            } else {
                int valueFromSysFS =
                        CoverManagerUtils.getValueFromSysFS(
                                getDefaultTypeOfCover(),
                                "/sys/devices/w1_bus_master1/w1_master_check_id");
                int valueFromSysFS2 =
                        CoverManagerUtils.getValueFromSysFS(
                                0, "/sys/devices/w1_bus_master1/w1_master_check_color");
                defaultTypeOfCover = valueFromSysFS;
                i2 =
                        CoverManagerUtils.getValueFromSysFS(
                                0, "/sys/bus/w1/devices/w1_bus_master1/w1_master_check_model");
                i = valueFromSysFS2;
            }
            int supportSViewCoverWidth = getSupportSViewCoverWidth(defaultTypeOfCover);
            int supportSViewCoverHeight = getSupportSViewCoverHeight(defaultTypeOfCover);
            if ("factory".equals(SystemProperties.get("ro.factory.factory_binary"))) {
                return;
            }
            coverState.updateCoverState(
                    defaultTypeOfCover,
                    i,
                    supportSViewCoverWidth,
                    supportSViewCoverHeight,
                    this.mIsCoverAttached,
                    i2);
            return;
        }
        if (coverState2 != null) {
            if ("factory".equals(SystemProperties.get("ro.factory.factory_binary"))) {
                return;
            }
            coverState.copyFrom(coverState2);
            int i3 = coverState.type;
            if (i3 == 8) {
                getScreenSizeForClearCover();
            }
            if (i3 != 255) {
                coverState.setWindowWidth(getSupportSViewCoverWidth(i3));
                coverState.setWindowHeight(getSupportSViewCoverHeight(i3));
                return;
            }
            return;
        }
        if (CoverTestModeUtils.isTestMode()) {
            int i4 = CoverTestModeUtils.SHIPPED ? -1 : CoverTestModeUtils.sCurrentTestMode;
            int supportSViewCoverWidth2 = getSupportSViewCoverWidth(i4);
            int supportSViewCoverHeight2 = getSupportSViewCoverHeight(i4);
            if (!"factory".equals(SystemProperties.get("ro.factory.factory_binary"))) {
                coverState.updateCoverState(
                        i4,
                        0,
                        supportSViewCoverWidth2,
                        supportSViewCoverHeight2,
                        this.mIsCoverAttached,
                        0);
            }
            Rect rect = CoverTestModeUtils.sCurrentTestVisibleRect;
            if (!rect.isEmpty()) {
                coverState.setVisibleRect(rect);
            }
            Log.d(
                    "CoverManager_CoverVerifier",
                    "[SmartCover] CoverVerify : sview cover test mode enabled");
        }
    }

    public final boolean updateCoverVerification() {
        Feature.getInstance(this.mContext).getClass();
        boolean z = true;
        if (Feature.sIsNfcAuthSystemFeatureEnabled) {
            if (!this.mIsCoverVerified) {
                this.mIsCoverVerified = true;
            }
            z = false;
        } else {
            Feature.getInstance(this.mContext).getClass();
            if (!Feature.sIsDeviceSupportVerifyCover) {
                if (!this.mIsCoverVerified) {
                    this.mIsCoverVerified = true;
                }
                z = false;
            } else if (CoverTestModeUtils.isTestMode()) {
                if (!this.mIsCoverVerified) {
                    this.mIsCoverVerified = true;
                }
                z = false;
            } else {
                boolean z2 =
                        CoverManagerUtils.getValueFromSysFS(
                                        -1, "/sys/devices/w1_bus_master1/w1_master_verify_mac")
                                == 0;
                if (this.mIsCoverVerified != z2) {
                    this.mIsCoverVerified = z2;
                }
                z = false;
            }
        }
        this.mIsCoverAttached = this.mIsCoverVerified;
        Log.d(
                "CoverManager_CoverVerifier",
                "updateCoverVerification : mIsCoverVerified ="
                        + this.mIsCoverVerified
                        + ", change="
                        + z);
        return z;
    }

    public final void updateCoverWindowSize(CoverState coverState) {
        if (coverState == null) {
            Log.d("CoverManager_CoverVerifier", "updateCoverWindowSize(): CoverState is null");
            return;
        }
        int supportSViewCoverHeight = getSupportSViewCoverHeight(coverState.getType());
        int supportSViewCoverWidth = getSupportSViewCoverWidth(coverState.getType());
        StringBuilder sb = new StringBuilder("updateCoverWindowSize(): old window = ");
        sb.append(coverState.heightPixel);
        sb.append("x");
        ServiceKeeper$$ExternalSyntheticOutline0.m(
                coverState.widthPixel, supportSViewCoverHeight, " new window = ", "x", sb);
        sb.append(supportSViewCoverWidth);
        Log.d("CoverManager_CoverVerifier", sb.toString());
        coverState.setWindowWidth(supportSViewCoverWidth);
        coverState.setWindowHeight(supportSViewCoverHeight);
    }
}
