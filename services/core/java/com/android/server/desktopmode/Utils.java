package com.android.server.desktopmode;

import android.R;
import android.content.Context;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Display;

import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;

import com.samsung.android.desktopmode.DesktopModeFeature;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class Utils {
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
                return VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Unknown=");
        }
    }

    public static void dumpBundle(Bundle bundle, PrintWriter printWriter) {
        if (bundle == null) {
            printWriter.println("null");
            return;
        }
        ArrayList arrayList = new ArrayList(bundle.keySet());
        Collections.sort(arrayList);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, "=");
            m.append(bundle.get(str));
            printWriter.println(m.toString());
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

    public static int readFile(int i, String str) {
        try {
            return Integer.parseInt(readFile(str, Integer.toString(i)));
        } catch (NumberFormatException e) {
            Log.e("[DMS]Utils", "Failed to readFile(" + str + ") ", e);
            return i;
        }
    }

    public static String readFile(String str, String str2) {
        File file = new File(str);
        if (file.exists()) {
            try {
                BufferedReader bufferedReader =
                        new BufferedReader(
                                new InputStreamReader(
                                        new FileInputStream(file), StandardCharsets.UTF_8));
                try {
                    str2 = bufferedReader.readLine();
                    bufferedReader.close();
                } finally {
                }
            } catch (IOException e) {
                Log.e("[DMS]Utils", "Failed to readFile(" + str + ") ", e);
            }
        }
        if (DesktopModeFeature.DEBUG) {
            Log.d("[DMS]Utils", "readFile(" + str + ")=" + str2);
        }
        return str2;
    }

    public static void runOnHandlerThread(Handler handler, Runnable runnable) {
        if (handler.getLooper() != Looper.myLooper()) {
            handler.post(runnable);
        } else {
            runnable.run();
        }
    }
}
