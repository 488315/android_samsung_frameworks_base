package com.samsung.android.displayport;

import android.content.Context;
import android.os.Build;
import android.util.Slog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public final class DisplayportHelper {
    private static final boolean DEBUG;
    private static final String DP_DEBUG_CLASS = "com.samsung.android.displayport.DisplayportDebug";
    private static final String DP_UTIL_CLASS = "com.samsung.android.displayport.DisplayportUtil";
    private static final String TAG = "DisplayportHelper";
    private static DPDebugInterface mDPDebugInterface;
    private static DPUtilInterface mDPUtilInterface;
    private static DisplayportHelper mThisInstance;
    Context mContext;

    public interface DPDebugInterface {
        void startSettingsObserver();

        void startUEventObserver();
    }

    public interface DPUtilInterface {
        void startRatioChanger();

        void startUEventObserver();
    }

    static {
        String str = Build.TYPE;
        DEBUG = "eng".equals(str) || "userdebug".equals(str);
        mThisInstance = null;
        mDPUtilInterface = null;
        mDPDebugInterface = null;
    }

    private DisplayportHelper() {
        this.mContext = null;
    }

    private DisplayportHelper(Context context) {
        this.mContext = context;
        loadDPUtilClass();
        loadDPDebugClass();
    }

    public static synchronized DisplayportHelper getInstance(Context context) {
        DisplayportHelper displayportHelper;
        synchronized (DisplayportHelper.class) {
            if (mThisInstance == null) {
                mThisInstance = new DisplayportHelper(context);
            }
            displayportHelper = mThisInstance;
        }
        return displayportHelper;
    }

    public static void registerDPUtil(DPUtilInterface dPUtilInterface) {
        mDPUtilInterface = dPUtilInterface;
    }

    public static void registerDPDebug(DPDebugInterface dPDebugInterface) {
        mDPDebugInterface = dPDebugInterface;
    }

    public void startDPRatioChanger() {
        Slog.d(TAG, "startDPRatioChanger");
        DPUtilInterface dPUtilInterface = mDPUtilInterface;
        if (dPUtilInterface == null) {
            return;
        }
        dPUtilInterface.startRatioChanger();
    }

    public void startDPBackOff() {
        Slog.d(TAG, "startDPBackOff");
        DPUtilInterface dPUtilInterface = mDPUtilInterface;
        if (dPUtilInterface == null) {
            return;
        }
        dPUtilInterface.startUEventObserver();
    }

    public void startDPDebug() {
        Slog.d(TAG, "startDPDebug");
        DPDebugInterface dPDebugInterface = mDPDebugInterface;
        if (dPDebugInterface == null) {
            return;
        }
        dPDebugInterface.startUEventObserver();
        mDPDebugInterface.startSettingsObserver();
    }

    private void loadDPUtilClass() {
        Slog.d(TAG, "loadDPUtilClass");
        try {
            Class.forName(DP_UTIL_CLASS).getDeclaredConstructor(Context.class).newInstance(this.mContext);
        } catch (ClassNotFoundException unused) {
            Slog.d(TAG, "Util Class not found");
        } catch (IllegalAccessException unused2) {
            Slog.d(TAG, "Util Illegal access");
        } catch (InstantiationException unused3) {
            Slog.d(TAG, "Util InstantiationException");
        } catch (NoSuchMethodException unused4) {
            Slog.d(TAG, "Util Method not found");
        } catch (InvocationTargetException unused5) {
            Slog.d(TAG, "Util InvocationTargetException");
        }
    }

    private void loadDPDebugClass() {
        Slog.d(TAG, "loadDPDebugClass");
        try {
            Class.forName(DP_DEBUG_CLASS).getDeclaredConstructor(Context.class).newInstance(this.mContext);
        } catch (ClassNotFoundException unused) {
            Slog.d(TAG, "Debug Class not found");
        } catch (IllegalAccessException unused2) {
            Slog.d(TAG, "Debug Illegal access");
        } catch (InstantiationException unused3) {
            Slog.d(TAG, "Debug InstantiationException");
        } catch (NoSuchMethodException unused4) {
            Slog.d(TAG, "Debug Method not found");
        } catch (InvocationTargetException unused5) {
            Slog.d(TAG, "Debug InvocationTargetException");
        }
    }
}
