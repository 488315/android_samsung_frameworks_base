package com.android.server.sepunion.cover;

import android.content.Context;
import android.database.ContentObserver;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import com.samsung.android.sepunion.Log;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* loaded from: classes3.dex */
public class CoverTestModeUtils {
  public OnCoverTestModeChanged mCallback;
  public Context mContext;
  public ContentObserver mObserver;
  public Handler mTestModeChangeHandler =
      new Handler() { // from class: com.android.server.sepunion.cover.CoverTestModeUtils.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
          if (CoverTestModeUtils.sCurrentTestMode != -1) {
            CoverTestModeUtils.this.mCallback.onCoverTestModeChanged(
                CoverTestModeUtils.sCurrentTestMode, true);
          }
        }
      };
  public ContentObserver mVisibleRectObserver;
  public static final String TAG = "CoverManager_" + CoverTestModeUtils.class.getSimpleName();
  public static final boolean SHIPPED = Debug.semIsProductDev() ^ true;
  public static int sCurrentTestMode = -1;
  public static Rect sCurrentTestVisibleRect = new Rect();

  public interface OnCoverTestModeChanged {
    void onCoverTestModeChanged(int i, boolean z);
  }

  public CoverTestModeUtils(Context context, OnCoverTestModeChanged onCoverTestModeChanged) {
    if (SHIPPED) {
      Log.d(TAG, "This version has been shipped!! Then cover test mode is not available");
      return;
    }
    this.mContext = context;
    this.mCallback = onCoverTestModeChanged;
    this.mObserver =
        new ContentObserver(
            new Handler()) { // from class: com.android.server.sepunion.cover.CoverTestModeUtils.2
          @Override // android.database.ContentObserver
          public void onChange(boolean z, Uri uri) {
            CoverTestModeUtils.this.updateCoverTestMode(
                CoverTestModeUtils.this.getTestModeFromSetting());
          }
        };
    this.mContext
        .getContentResolver()
        .registerContentObserver(
            Settings.System.getUriFor("cover_test_mode"), false, this.mObserver);
    sCurrentTestMode = getTestModeFromSetting();
    this.mVisibleRectObserver =
        new ContentObserver(
            new Handler()) { // from class: com.android.server.sepunion.cover.CoverTestModeUtils.3
          @Override // android.database.ContentObserver
          public void onChange(boolean z, Uri uri) {
            CoverTestModeUtils.sCurrentTestVisibleRect =
                CoverTestModeUtils.this.getTestVisibleRectFromSetting();
          }
        };
    this.mContext
        .getContentResolver()
        .registerContentObserver(
            Settings.System.getUriFor("cover_test_visible_rect"), false, this.mVisibleRectObserver);
    sCurrentTestVisibleRect = getTestVisibleRectFromSetting();
  }

  public final int getTestModeFromSetting() {
    return Settings.System.getInt(this.mContext.getContentResolver(), "cover_test_mode", -1);
  }

  public void setTestModeToSetting(int i) {
    Settings.System.putInt(this.mContext.getContentResolver(), "cover_test_mode", i);
  }

  public final Rect getTestVisibleRectFromSetting() {
    Rect rect = new Rect();
    String string =
        Settings.System.getString(this.mContext.getContentResolver(), "cover_test_visible_rect");
    if (string != null) {
      try {
        String[] split = string.split(",");
        rect.left = Integer.parseInt(split[0]);
        rect.top = Integer.parseInt(split[1]);
        rect.right = Integer.parseInt(split[2]);
        rect.bottom = Integer.parseInt(split[3]);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return rect;
  }

  public void setTestVisibleRectToSetting(Rect rect) {
    Settings.System.putString(
        this.mContext.getContentResolver(),
        "cover_test_visible_rect",
        rect.left + "," + rect.top + "," + rect.right + "," + rect.bottom);
  }

  public final void updateCoverTestMode(int i) {
    OnCoverTestModeChanged onCoverTestModeChanged;
    Log.d(
        TAG,
        "updateCoverTestMode() sCurrentTestMode = "
            + sCurrentTestMode
            + ", currentTestMode = "
            + i);
    int i2 = sCurrentTestMode;
    if (i2 == i || (onCoverTestModeChanged = this.mCallback) == null) {
      return;
    }
    if (i2 != -1) {
      onCoverTestModeChanged.onCoverTestModeChanged(i2, false);
    }
    sCurrentTestMode = i;
    this.mTestModeChangeHandler.sendEmptyMessageDelayed(0, 500L);
  }

  public static boolean isTestMode() {
    return (SHIPPED || sCurrentTestMode == -1) ? false : true;
  }

  public static int getTestCoverType() {
    if (SHIPPED) {
      return -1;
    }
    return sCurrentTestMode;
  }

  public static Rect getTestVisibleRect() {
    return sCurrentTestVisibleRect;
  }

  public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    printWriter.println(" Current CoverTestModeUtils state:");
    printWriter.print("  SHIPPED=");
    printWriter.println(SHIPPED);
    printWriter.print("  sCurrentTestMode=");
    printWriter.println(sCurrentTestMode);
    printWriter.print("  sCurrentTestVisibleRect=");
    printWriter.println(sCurrentTestVisibleRect);
    printWriter.println("  ");
  }
}
