package com.android.server.desktopmode;

import android.R;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemProperties;
import android.provider.Settings;
import android.view.Display;
import com.android.server.IoThread;
import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.feature.SemFloatingFeature;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes2.dex */
public abstract class Utils {
  public static final String TAG = "[DMS]" + Utils.class.getSimpleName();

  public static Object getOrPut(Map map, Object obj, Supplier supplier) {
    Object obj2 = map.get(obj);
    if (obj2 != null) {
      return obj2;
    }
    Object obj3 = supplier.get();
    map.put(obj, obj3);
    return obj3;
  }

  public static void runOnHandlerThread(Handler handler, Runnable runnable) {
    if (handler.getLooper() != Looper.myLooper()) {
      handler.post(runnable);
    } else {
      runnable.run();
    }
  }

  public static Context getDisplayContext(Context context, int i) {
    DisplayManager displayManager;
    Display display;
    if (context == null
        || i == -1
        || i == context.getDisplay().getDisplayId()
        || (displayManager = (DisplayManager) context.getSystemService("display")) == null
        || (display = displayManager.getDisplay(i)) == null) {
      return context;
    }
    Context createDisplayContext = context.createDisplayContext(display);
    createDisplayContext.setTheme(R.style.Theme.DeviceDefault.Light);
    return createDisplayContext;
  }

  public static int readFile(String str) {
    return readFile(str, -1);
  }

  public static int readFile(String str, int i) {
    try {
      return Integer.parseInt(readFile(str, Integer.toString(i)));
    } catch (NumberFormatException e) {
      Log.m9e(TAG, "Failed to readFile(" + str + ") ", e);
      return i;
    }
  }

  public static String readFile(String str, String str2) {
    File file = new File(str);
    if (file.exists()) {
      try {
        BufferedReader bufferedReader =
            new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
        try {
          str2 = bufferedReader.readLine();
          bufferedReader.close();
        } finally {
        }
      } catch (IOException e) {
        Log.m9e(TAG, "Failed to readFile(" + str + ") ", e);
      }
    }
    if (DesktopModeFeature.DEBUG) {
      Log.m7d(TAG, "readFile(" + str + ")=" + str2);
    }
    return str2;
  }

  public static void writeFile(String str, int i, boolean z) {
    writeFile(str, Integer.toString(i), z);
  }

  public static void writeFile(final String str, final String str2, final boolean z) {
    if (DesktopModeFeature.DEBUG) {
      Log.m7d(TAG, "writeFile(), path=" + str + ", value=" + str2 + ", async=" + z);
    }
    Runnable runnable = new Runnable() { // from class:
          // com.android.server.desktopmode.Utils$$ExternalSyntheticLambda0
          @Override // java.lang.Runnable
          public final void run() {
            Utils.lambda$writeFile$0(str, str2, z);
          }
        };
    if (z) {
      IoThread.getHandler().post(runnable);
    } else {
      runnable.run();
    }
  }

  public static /* synthetic */ void lambda$writeFile$0(String str, String str2, boolean z) {
    File file = new File(str);
    if (file.exists()) {
      try {
        BufferedWriter bufferedWriter =
            new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
        try {
          bufferedWriter.write(str2);
          bufferedWriter.close();
        } finally {
        }
      } catch (IOException e) {
        Log.m9e(TAG, "Failed to writeFile(), path=" + str + ", value=" + str2 + ", async=" + z, e);
      }
    }
    if (DesktopModeFeature.DEBUG) {
      Log.m7d(TAG, "writeFile(), path=" + str + ", value=" + str2 + ", async=" + z + ", returning");
    }
  }

  public static void dumpBundle(PrintWriter printWriter, Bundle bundle) {
    if (bundle == null) {
      printWriter.println("null");
      return;
    }
    ArrayList<String> arrayList = new ArrayList(bundle.keySet());
    Collections.sort(arrayList);
    for (String str : arrayList) {
      printWriter.println(str + "=" + bundle.get(str));
    }
  }

  public static String bundleToString(Bundle bundle) {
    if (bundle == null) {
      return "null";
    }
    StringBuilder sb = new StringBuilder(bundle.size() * 28);
    sb.append('{');
    for (String str : bundle.keySet()) {
      if (sb.length() > 1) {
        sb.append(", ");
      }
      sb.append(str);
      sb.append('=');
      sb.append(bundle.get(str));
    }
    sb.append('}');
    return sb.toString();
  }

  public static String dockStateToString(int i) {
    if (i == 0) {
      return "EXTRA_DOCK_STATE_UNDOCKED";
    }
    if (i == 114) {
      return "SEM_EXTRA_DOCK_STATE_DEX_PAD";
    }
    switch (i) {
      case 109:
        return "SEM_EXTRA_DOCK_STATE_MULTIPORT_ADAPTER";
      case 110:
        return "SEM_EXTRA_DOCK_STATE_DEX_STATION";
      case 111:
        return "SEM_EXTRA_DOCK_STATE_HDMI_ADAPTER";
      default:
        return "Unknown=" + i;
    }
  }

  public static boolean isRetailMode(Context context, int i) {
    return isLduModel() || isShopDemo(context, i);
  }

  public static boolean isLduModel() {
    String salesCode = getSalesCode();
    salesCode.hashCode();
    switch (salesCode) {
      case "FOP":
      case "LDU":
      case "PAP":
        return true;
      default:
        return false;
    }
  }

  public static void showTipsNotification(
      Context context, String str, String str2, String str3, String str4) {
    Intent intent = new Intent();
    Intent intent2 = new Intent();
    intent2.setClassName(
        "com.samsung.android.app.tips", "com.samsung.android.app.tips.TipsIntentService");
    intent2.putExtra("tips_extras", 7);
    intent2.putExtra("tips_extras2", str2);
    intent2.setFlags(268435456);
    intent.setClassName(
        "com.samsung.android.app.tips", "com.samsung.android.app.tips.TipsIntentService");
    intent.putExtra("tips_extras", 9);
    intent.putExtra("tips_id", str);
    intent.putExtra("tips_noti_category", "recommendation");
    intent.putExtra("tips_app_name", context.getPackageName());
    intent.putExtra("tips_title", str3);
    intent.putExtra("tips_text", str4);
    intent.putExtra("tips_action_type", 1);
    intent.putExtra("tips_action", intent2);
    intent.putExtra("tips_app_primary_color", "#ff6c39");
    intent.putExtra("tips_condition", 1);
    context.startForegroundService(intent);
  }

  public static String getSalesCode() {
    String str = SystemProperties.get("persist.omc.sales_code");
    if (!str.isEmpty()) {
      return str;
    }
    String str2 = SystemProperties.get("ro.csc.sales_code");
    return str2.isEmpty() ? SystemProperties.get("ril.sales_code") : str2;
  }

  public static boolean isShopDemo(Context context, int i) {
    return Settings.Secure.getIntForUser(context.getContentResolver(), "shopdemo", 0, i) == 1;
  }

  public static int getFixedZoomProperty() {
    List of =
        List.of(
            (Object[])
                SemFloatingFeature.getInstance()
                    .getString("SEC_FLOATING_FEATURE_COMMON_CONFIG_DEX_MODE")
                    .split(","));
    for (int i = 0; i < of.size(); i++) {
      String str = (String) of.get(i);
      if (str.contains("fixedzoom")) {
        Matcher matcher = Pattern.compile("\\((.*?)\\)").matcher(str);
        if (matcher.find()) {
          return Integer.parseInt(matcher.group(1));
        }
      }
    }
    return 0;
  }
}
