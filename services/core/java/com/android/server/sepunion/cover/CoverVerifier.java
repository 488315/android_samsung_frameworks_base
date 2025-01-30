package com.android.server.sepunion.cover;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.SystemProperties;
import android.view.Display;
import android.view.WindowManager;
import com.samsung.android.cover.CoverState;
import com.samsung.android.sepunion.Log;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* loaded from: classes3.dex */
public final class CoverVerifier {
  public static final String TAG = "CoverManager_" + CoverVerifier.class.getSimpleName();
  public Context mContext;
  public int mDefaultCoverColor = 0;
  public int mDefaultCoverModel = 0;
  public int mDefaultSViewCoverWidth = 0;
  public int mDefaultSViewCoverHeight = 0;
  public int mDefaultMiniSViewCoverWidth = 0;
  public int mDefaultMiniSViewCoverHeight = 0;
  public int mDefaultClearCoverWidth = 0;
  public int mDefaultClearCoverHeight = 0;
  public boolean mIsCoverVerified = false;
  public boolean mIsCoverAttached = false;

  public CoverVerifier(Context context) {
    this.mContext = context;
    initializeDefaultCoverState();
  }

  public final int getDefaultTypeOfCover() {
    if (Feature.getInstance(this.mContext).isSupportVerifyCover()) {
      if (Feature.getInstance(this.mContext).isSupportFlipCover()
          && CoverTestModeUtils.getTestCoverType() == 0) {
        return 0;
      }
      if (Feature.getInstance(this.mContext).isSupportClearCover()
          && CoverTestModeUtils.getTestCoverType() == 8) {
        return 8;
      }
      return (Feature.getInstance(this.mContext).isSupportNeonCover()
              && CoverTestModeUtils.getTestCoverType() == 11)
          ? 11
          : 2;
    }
    if (Feature.getInstance(this.mContext).isTablet()) {
      if (Feature.getInstance(this.mContext).isSupportFlipCover()) {
        return 0;
      }
    } else {
      if (Feature.getInstance(this.mContext).isSupportFlipCover()) {
        return 0;
      }
      if (Feature.getInstance(this.mContext).isSupportClearCover()) {
        return 8;
      }
      if (Feature.getInstance(this.mContext).isSupportNeonCover()) {
        return 11;
      }
    }
    return 2;
  }

  public final int getSupportSViewCoverWidth(int i) {
    if (i == 8 || i == 11) {
      return this.mDefaultClearCoverWidth;
    }
    return 0;
  }

  public final int getSupportSViewCoverHeight(int i) {
    if (i == 8 || i == 11) {
      return this.mDefaultClearCoverHeight;
    }
    return 0;
  }

  public void initializeDefaultCoverState() {
    if (this.mContext.getResources() != null) {
      getScreenSizeForClearCover();
    }
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

  public void updateCoverWindowSize(CoverState coverState) {
    if (coverState == null) {
      Log.d(TAG, "updateCoverWindowSize(): CoverState is null");
      return;
    }
    int supportSViewCoverHeight = getSupportSViewCoverHeight(coverState.getType());
    int supportSViewCoverWidth = getSupportSViewCoverWidth(coverState.getType());
    Log.d(
        TAG,
        "updateCoverWindowSize(): old window = "
            + coverState.heightPixel
            + "x"
            + coverState.widthPixel
            + " new window = "
            + supportSViewCoverHeight
            + "x"
            + supportSViewCoverWidth);
    coverState.setWindowWidth(supportSViewCoverWidth);
    coverState.setWindowHeight(supportSViewCoverHeight);
  }

  public void updateCoverPropertiesLocked(CoverState coverState, CoverState coverState2) {
    int defaultTypeOfCover;
    int i;
    int i2;
    if (!Feature.getInstance(this.mContext).isNfcAuthEnabled()) {
      if (!CoverTestModeUtils.isTestMode() && this.mIsCoverAttached) {
        defaultTypeOfCover =
            CoverManagerUtils.getValueFromSysFS(
                "/sys/devices/w1_bus_master1/w1_master_check_id", getDefaultTypeOfCover());
        i =
            CoverManagerUtils.getValueFromSysFS(
                "/sys/devices/w1_bus_master1/w1_master_check_color", this.mDefaultCoverColor);
        i2 =
            CoverManagerUtils.getValueFromSysFS(
                "/sys/bus/w1/devices/w1_bus_master1/w1_master_check_model",
                this.mDefaultCoverModel);
      } else {
        defaultTypeOfCover = getDefaultTypeOfCover();
        i = this.mDefaultCoverColor;
        i2 = this.mDefaultCoverModel;
      }
      int i3 = defaultTypeOfCover;
      int i4 = i;
      int i5 = i2;
      int supportSViewCoverWidth = getSupportSViewCoverWidth(i3);
      int supportSViewCoverHeight = getSupportSViewCoverHeight(i3);
      if (isFactoryMode()) {
        return;
      }
      coverState.updateCoverState(
          i3, i4, supportSViewCoverWidth, supportSViewCoverHeight, this.mIsCoverAttached, i5);
      return;
    }
    if (coverState2 == null) {
      if (CoverTestModeUtils.isTestMode()) {
        int testCoverType = CoverTestModeUtils.getTestCoverType();
        int i6 = this.mDefaultCoverColor;
        int i7 = this.mDefaultCoverModel;
        int supportSViewCoverWidth2 = getSupportSViewCoverWidth(testCoverType);
        int supportSViewCoverHeight2 = getSupportSViewCoverHeight(testCoverType);
        if (!isFactoryMode()) {
          coverState.updateCoverState(
              testCoverType,
              i6,
              supportSViewCoverWidth2,
              supportSViewCoverHeight2,
              this.mIsCoverAttached,
              i7);
        }
        Rect testVisibleRect = CoverTestModeUtils.getTestVisibleRect();
        if (!testVisibleRect.isEmpty()) {
          coverState.setVisibleRect(testVisibleRect);
        }
        Log.d(TAG, "[SmartCover] CoverVerify : sview cover test mode enabled");
        return;
      }
      return;
    }
    if (isFactoryMode()) {
      return;
    }
    coverState.copyFrom(coverState2);
    int i8 = coverState.type;
    if (i8 == 8) {
      getScreenSizeForClearCover();
    }
    if (i8 != 255) {
      coverState.setWindowWidth(getSupportSViewCoverWidth(i8));
      coverState.setWindowHeight(getSupportSViewCoverHeight(i8));
    }
  }

  public boolean updateCoverVerification() {
    boolean z = true;
    if (Feature.getInstance(this.mContext).isNfcAuthEnabled()) {
      if (!this.mIsCoverVerified) {
        this.mIsCoverVerified = true;
      }
      z = false;
    } else if (Feature.getInstance(this.mContext).isSupportVerifyCover()) {
      if (CoverTestModeUtils.isTestMode()) {
        if (!this.mIsCoverVerified) {
          this.mIsCoverVerified = true;
        }
        z = false;
      } else {
        boolean z2 =
            CoverManagerUtils.getValueFromSysFS(
                    "/sys/devices/w1_bus_master1/w1_master_verify_mac", -1)
                == 0;
        if (this.mIsCoverVerified != z2) {
          this.mIsCoverVerified = z2;
        }
        z = false;
      }
    } else {
      if (!this.mIsCoverVerified) {
        this.mIsCoverVerified = true;
      }
      z = false;
    }
    this.mIsCoverAttached = this.mIsCoverVerified;
    Log.d(
        TAG,
        "updateCoverVerification : mIsCoverVerified =" + this.mIsCoverVerified + ", change=" + z);
    return z;
  }

  public boolean updateCoverAttachState(boolean z) {
    boolean z2 = true;
    if (Feature.getInstance(this.mContext).isNfcAuthEnabled()) {
      if (this.mIsCoverAttached != z) {
        this.mIsCoverAttached = z;
      }
      z2 = false;
    } else if (Feature.getInstance(this.mContext).isSupportVerifyCover()) {
      if (CoverTestModeUtils.isTestMode()) {
        if (!this.mIsCoverAttached) {
          this.mIsCoverAttached = true;
        }
        z2 = false;
      } else {
        if (this.mIsCoverAttached != z) {
          this.mIsCoverAttached = z;
        }
        z2 = false;
      }
    } else {
      if (!this.mIsCoverAttached) {
        this.mIsCoverAttached = true;
      }
      z2 = false;
    }
    this.mIsCoverVerified = this.mIsCoverAttached;
    Log.d(
        TAG,
        "updateCoverAttachState : mIsCoverVerified ="
            + this.mIsCoverAttached
            + ", attached="
            + z
            + ", change="
            + z2);
    return z2;
  }

  public boolean isCoverAttached() {
    return this.mIsCoverAttached;
  }

  public static boolean isFactoryMode() {
    return "factory".equals(SystemProperties.get("ro.factory.factory_binary"));
  }

  public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    printWriter.println(" Current CoverVerifier state:");
    printWriter.print("  mIsCoverVerified=");
    printWriter.print(this.mIsCoverVerified);
    printWriter.print("  mIsCoverAttached=");
    printWriter.println(this.mIsCoverAttached);
    printWriter.print("  mDefaultCoverType=");
    printWriter.print(getDefaultTypeOfCover());
    printWriter.print("  mDefaultCoverColor=");
    printWriter.println(this.mDefaultCoverColor);
    printWriter.print("  mDefaultSViewCoverWidth=");
    printWriter.print(this.mDefaultSViewCoverWidth);
    printWriter.print("  mDefaultSViewCoverHeight=");
    printWriter.print(this.mDefaultSViewCoverHeight);
    printWriter.print("  mDefaultMiniSViewCoverWidth=");
    printWriter.print(this.mDefaultMiniSViewCoverWidth);
    printWriter.print("  mDefaultMiniSViewCoverHeight=");
    printWriter.println(this.mDefaultMiniSViewCoverHeight);
    printWriter.println("  ");
  }
}
