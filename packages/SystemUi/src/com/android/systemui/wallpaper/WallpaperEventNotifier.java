package com.android.systemui.wallpaper;

import android.app.SemWallpaperColors;
import android.app.WallpaperManager;
import android.content.Context;
import android.os.Debug;
import android.os.Handler;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import android.util.Pair;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.colors.ColorData;
import com.android.systemui.wallpaper.colors.KeyguardWallpaperColors;
import com.android.systemui.widget.SystemUIWidgetCallback;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WallpaperEventNotifier {
    public static final boolean DEBUG = Debug.semIsProductDev();
    public final Handler mHandler;
    public final KeyguardWallpaperColors mKeyguardWallpaperColors;
    public final WallpaperManager mWallpaperManager;
    public long mCurStatusFlag = 0;
    public final ArrayList mCallbacks = new ArrayList();
    public final ArrayList mCoverCallbacks = new ArrayList();
    public boolean mIsThemeApplying = false;
    public final ArrayList mLogs = new ArrayList();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DebugLog {
        public final String text;
        public final long time = System.currentTimeMillis();

        public DebugLog(String str) {
            this.text = str;
        }
    }

    public WallpaperEventNotifier(Context context, SettingsHelper settingsHelper, Handler handler) {
        this.mHandler = handler;
        this.mWallpaperManager = (WallpaperManager) context.getSystemService("wallpaper");
        this.mKeyguardWallpaperColors = new KeyguardWallpaperColors(context, settingsHelper);
    }

    public static WallpaperEventNotifier getInstance() {
        return (WallpaperEventNotifier) Dependency.get(WallpaperEventNotifier.class);
    }

    public final void addLog(String str) {
        DebugLog debugLog = new DebugLog(str);
        ArrayList arrayList = this.mLogs;
        arrayList.add(debugLog);
        if (arrayList.size() > 200) {
            arrayList.remove(0);
        }
    }

    public final void debugNotify(boolean z, long j, SemWallpaperColors semWallpaperColors, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(KeyguardWallpaperColors.getChangeFlagsString(j));
        sb.append(", isCover = " + z);
        sb.append(", colors = ");
        if (semWallpaperColors != null) {
            sb.append(semWallpaperColors.toSimpleString());
        } else {
            sb.append("null");
        }
        StringBuilder m2m = AbstractC0000x2c234b15.m2m(str, ": ");
        m2m.append(sb.toString());
        Log.d("WallpaperEventNotifier", m2m.toString());
        addLog(str + ": " + sb.toString());
    }

    public final SemWallpaperColors getSemWallpaperColors(boolean z) {
        KeyguardWallpaperColors keyguardWallpaperColors = this.mKeyguardWallpaperColors;
        keyguardWallpaperColors.getClass();
        int currentUser = KeyguardUpdateMonitor.getCurrentUser();
        try {
            return z ? ((ColorData) keyguardWallpaperColors.mSemWallpaperColorsCover.get(currentUser)).colors : ((ColorData) keyguardWallpaperColors.mSemWallpaperColors.get(currentUser)).colors;
        } catch (NullPointerException unused) {
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x00c6  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00ee  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void registerCallback(boolean z, SystemUIWidgetCallback systemUIWidgetCallback, long j) {
        long j2;
        synchronized ((z ? this.mCoverCallbacks : this.mCallbacks)) {
            ArrayList arrayList = z ? this.mCoverCallbacks : this.mCallbacks;
            for (int i = 0; i < arrayList.size(); i++) {
                if (((WeakReference) ((Pair) arrayList.get(i)).first).get() == systemUIWidgetCallback) {
                    Log.e("WallpaperEventNotifier", "registerCallback: Object tried to add another callback " + Debug.getCaller());
                    return;
                }
            }
            arrayList.add(Pair.create(new WeakReference(systemUIWidgetCallback), Long.valueOf(j)));
            SemWallpaperColors semWallpaperColors = null;
            removeCallback(z, null);
            if (this.mIsThemeApplying) {
                Log.d("WallpaperEventNotifier", "sendUpdates: Ignore update while theme is applying...");
                addLog("sendUpdates: Ignore update while theme is applying...");
                return;
            }
            KeyguardWallpaperColors keyguardWallpaperColors = this.mKeyguardWallpaperColors;
            keyguardWallpaperColors.getClass();
            int currentUser = KeyguardUpdateMonitor.getCurrentUser();
            try {
                semWallpaperColors = z ? ((ColorData) keyguardWallpaperColors.mSemWallpaperColorsCover.get(currentUser)).colors : ((ColorData) keyguardWallpaperColors.mSemWallpaperColors.get(currentUser)).colors;
            } catch (NullPointerException unused) {
            }
            if (semWallpaperColors == null) {
                Log.d("WallpaperEventNotifier", "sendUpdates: We don't have any colors to notify for now.");
                addLog("sendUpdates: We don't have any colors to notify for now");
                return;
            }
            if (!z) {
                long j3 = this.mCurStatusFlag;
                if ((1 & j3) == 0) {
                    j &= -2;
                }
                if ((2 & j3) == 0) {
                    j &= -3;
                }
                j2 = (j3 & 1024) == 0 ? -1025L : -1028L;
                if (j != 0) {
                    Log.d("WallpaperEventNotifier", "sendUpdates: Nothing to notify");
                    addLog("sendUpdates: Nothing to notify");
                    return;
                }
                if (DEBUG) {
                    debugNotify(z, j, semWallpaperColors, "sendUpdates");
                }
                Log.d("WallpaperEventNotifier", "sendUpdates: typesTobeNotified = " + KeyguardWallpaperColors.getChangeFlagsString(j));
                systemUIWidgetCallback.updateStyle(j, semWallpaperColors);
                return;
            }
            j &= j2;
            if (j != 0) {
            }
        }
    }

    public final void removeCallback(boolean z, SystemUIWidgetCallback systemUIWidgetCallback) {
        synchronized ((z ? this.mCoverCallbacks : this.mCallbacks)) {
            ArrayList arrayList = z ? this.mCoverCallbacks : this.mCallbacks;
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                if (((WeakReference) ((Pair) arrayList.get(size)).first).get() == systemUIWidgetCallback) {
                    arrayList.remove(size);
                }
            }
        }
    }

    public final void setCurStatusFlag(boolean z, SemWallpaperColors semWallpaperColors) {
        if (!z) {
            this.mCurStatusFlag = 0L;
        }
        if (semWallpaperColors == null) {
            addLog("setCurStatusFlag: colors is null. May cause unexptected behaviour!");
            return;
        }
        KeyguardWallpaperColors keyguardWallpaperColors = this.mKeyguardWallpaperColors;
        keyguardWallpaperColors.getClass();
        ColorData colorData = new ColorData(SemWallpaperColors.getBlankWallpaperColors(), false, false, false);
        SettingsHelper settingsHelper = keyguardWallpaperColors.mSettingsHelper;
        long checkUpdates = keyguardWallpaperColors.checkUpdates(colorData, new ColorData(semWallpaperColors, settingsHelper.isOpenThemeLook(), settingsHelper.isOpenThemeLockWallpaper(), false));
        if (z) {
            return;
        }
        this.mCurStatusFlag = checkUpdates;
    }

    public final void update(boolean z, long j, final SemWallpaperColors semWallpaperColors) {
        boolean z2;
        if (this.mIsThemeApplying) {
            addLog("update: Ignore update while theme is applying...");
            Log.d("WallpaperEventNotifier", "update: Ignore update while theme is applying...");
            return;
        }
        final long j2 = z ? j & (LsRune.SUPPORT_LARGE_FRONT_SUB_DISPLAY ? -2L : -1028L) : j;
        setCurStatusFlag(z, semWallpaperColors);
        if (j2 != 0) {
            debugNotify(z, j2, semWallpaperColors, "notifyUpdate");
            synchronized ((z ? this.mCoverCallbacks : this.mCallbacks)) {
                ArrayList arrayList = z ? this.mCoverCallbacks : this.mCallbacks;
                for (int i = 0; i < arrayList.size(); i++) {
                    Pair pair = (Pair) arrayList.get(i);
                    final SystemUIWidgetCallback systemUIWidgetCallback = (SystemUIWidgetCallback) ((WeakReference) pair.first).get();
                    long longValue = ((Long) pair.second).longValue();
                    if (systemUIWidgetCallback != null) {
                        if ((longValue & j2) == 0) {
                            boolean z3 = true;
                            if ((8 & longValue) != 0) {
                                for (int i2 = 0; i2 < KeyguardWallpaperColors.NUM_SEPARATED_AREA; i2++) {
                                    if ((KeyguardWallpaperColors.UPDATE_FLAGS[i2] & longValue) != 0 && (KeyguardWallpaperColors.UPDATE_FLAGS_SHADOW[i2] & j2) != 0) {
                                        z2 = true;
                                        break;
                                    }
                                }
                            }
                            z2 = false;
                            if (!z2) {
                                if ((4 & longValue) != 0) {
                                    for (int i3 = 0; i3 < KeyguardWallpaperColors.NUM_SEPARATED_AREA; i3++) {
                                        if ((KeyguardWallpaperColors.UPDATE_FLAGS[i3] & longValue) != 0 && (KeyguardWallpaperColors.UPDATE_FLAGS_ADAPTIVE_CONTRAST[i3] & j2) != 0) {
                                            break;
                                        }
                                    }
                                }
                                z3 = false;
                                if (!z3) {
                                }
                            }
                        }
                        this.mHandler.post(new Runnable() { // from class: com.android.systemui.wallpaper.WallpaperEventNotifier$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                SystemUIWidgetCallback.this.updateStyle(j2, semWallpaperColors);
                            }
                        });
                    }
                }
            }
        }
    }
}
