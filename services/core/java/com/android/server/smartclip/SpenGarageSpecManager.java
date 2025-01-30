package com.android.server.smartclip;

import android.text.TextUtils;
import android.util.Log;
import com.samsung.android.feature.SemFloatingFeature;
import java.util.ArrayList;

/* compiled from: SpenGestureManagerService.java */
/* loaded from: classes3.dex */
public class SpenGarageSpecManager {
  public static String TAG = "SpenGarageSpecManager";
  public static SpenGarageSpecManager sInstance;
  public boolean mIsBundledSpenSupported = false;
  public ArrayList mSupportedExternalSpenFeatures = new ArrayList();

  /* compiled from: SpenGestureManagerService.java */
  enum SupportedExternalSpenFeature {
    REMOTE
  }

  public static synchronized SpenGarageSpecManager getInstance() {
    SpenGarageSpecManager spenGarageSpecManager;
    synchronized (SpenGarageSpecManager.class) {
      if (sInstance == null) {
        sInstance = new SpenGarageSpecManager();
      }
      spenGarageSpecManager = sInstance;
    }
    return spenGarageSpecManager;
  }

  public SpenGarageSpecManager() {
    parseSpenGarageSpec();
  }

  public final void parseSpenGarageSpec() {
    String string =
        SemFloatingFeature.getInstance()
            .getString("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_SPEN_GARAGE_SPEC");
    if (TextUtils.isEmpty(string)) {
      Log.i(TAG, "Spen Garage Spec is empty");
      return;
    }
    this.mIsBundledSpenSupported = true;
    String replaceAll = string.toLowerCase().replaceAll(" ", "");
    for (String str : replaceAll.split(",")) {
      String[] split = str.split("=");
      if (split.length != 2) {
        Log.e(TAG, "Incorrect Spec, strSpec : " + replaceAll);
      } else {
        String str2 = split[0];
        String str3 = split[1];
        str2.hashCode();
        if (str2.equals("unbundled_spec")) {
          String[] split2 = str3.split("\\|");
          Log.i(TAG, "unbundled_spec = " + str3);
          int length = split2.length;
          for (int i = 0; i < length; i++) {
            SupportedExternalSpenFeature valueOf =
                SupportedExternalSpenFeature.valueOf(split2[i].toUpperCase());
            if (valueOf != null) {
              this.mSupportedExternalSpenFeatures.add(valueOf);
            }
          }
        } else if (str2.equals("bundled")) {
          this.mIsBundledSpenSupported = Boolean.valueOf(str3).booleanValue();
          Log.i(TAG, "bundle = " + str3);
        }
      }
    }
  }

  public boolean isBundledSpenSupported() {
    return this.mIsBundledSpenSupported;
  }

  public boolean isUnbundledRemoteSpenSupported() {
    return this.mSupportedExternalSpenFeatures.contains(SupportedExternalSpenFeature.REMOTE);
  }
}
