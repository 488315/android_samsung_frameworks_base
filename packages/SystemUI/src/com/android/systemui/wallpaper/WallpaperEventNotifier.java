package com.android.systemui.wallpaper;

import android.app.SemWallpaperColors;
import android.app.WallpaperManager;
import android.content.Context;
import android.os.Debug;
import android.os.Handler;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.util.Pair;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.colors.KeyguardWallpaperColors;
import com.android.systemui.widget.SystemUIWidgetCallback;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public final class WallpaperEventNotifier {
    public static final boolean DEBUG = Debug.semIsProductDev();
    public final Handler mHandler;
    public final KeyguardWallpaperColors mKeyguardWallpaperColors;
    public final SelectedUserInteractor mSelectedUserInteractor;
    public final WallpaperManager mWallpaperManager;
    public long mCurStatusFlag = 0;
    public final ArrayList mCallbacks = new ArrayList();
    public final ArrayList mCoverCallbacks = new ArrayList();
    public boolean mIsThemeApplying = false;
    public final ArrayList mLogs = new ArrayList();

    public final class DebugLog {
        public final String text;
        public final long time = System.currentTimeMillis();

        public DebugLog(String str) {
            this.text = str;
        }
    }

    public WallpaperEventNotifier(Context context, SettingsHelper settingsHelper, SelectedUserInteractor selectedUserInteractor, Handler handler) {
        this.mSelectedUserInteractor = selectedUserInteractor;
        this.mHandler = handler;
        this.mWallpaperManager = (WallpaperManager) context.getSystemService("wallpaper");
        this.mKeyguardWallpaperColors = new KeyguardWallpaperColors(context, settingsHelper, selectedUserInteractor.getSelectedUserId(false));
    }

    public static WallpaperEventNotifier getInstance() {
        return (WallpaperEventNotifier) Dependency.sDependency.getDependencyInner(WallpaperEventNotifier.class);
    }

    public final void addLog(String str) {
        this.mLogs.add(new DebugLog(str));
        if (this.mLogs.size() > 200) {
            this.mLogs.remove(0);
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
        StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(str, ": ");
        m.append(sb.toString());
        Log.d("WallpaperEventNotifier", m.toString());
        addLog(str + ": " + sb.toString());
    }

    public final SemWallpaperColors getSemWallpaperColors(boolean z) {
        KeyguardWallpaperColors keyguardWallpaperColors = this.mKeyguardWallpaperColors;
        return keyguardWallpaperColors.getSemWallpaperColors(keyguardWallpaperColors.mSelectedUserId, z);
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00da  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void registerCallback(boolean r10, com.android.systemui.widget.SystemUIWidgetCallback r11, long r12) {
        /*
            Method dump skipped, instructions count: 230
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.wallpaper.WallpaperEventNotifier.registerCallback(boolean, com.android.systemui.widget.SystemUIWidgetCallback, long):void");
    }

    public final void removeCallback(boolean z, SystemUIWidgetCallback systemUIWidgetCallback) {
        synchronized ((z ? this.mCoverCallbacks : this.mCallbacks)) {
            try {
                ArrayList arrayList = z ? this.mCoverCallbacks : this.mCallbacks;
                for (int size = arrayList.size() - 1; size >= 0; size--) {
                    if (((WeakReference) ((Pair) arrayList.get(size)).first).get() == systemUIWidgetCallback) {
                        arrayList.remove(size);
                    }
                }
            } catch (Throwable th) {
                throw th;
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
        long checkBaseUpdates = this.mKeyguardWallpaperColors.checkBaseUpdates(semWallpaperColors);
        if (z) {
            return;
        }
        this.mCurStatusFlag = checkBaseUpdates;
    }

    public final void update(boolean z, long j, final SemWallpaperColors semWallpaperColors) {
        if (this.mIsThemeApplying) {
            addLog("update: Ignore update while theme is applying...");
            Log.d("WallpaperEventNotifier", "update: Ignore update while theme is applying...");
            return;
        }
        final long j2 = z ? j & (LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY ? -2L : -1028L) : j;
        setCurStatusFlag(z, semWallpaperColors);
        if (j2 != 0) {
            debugNotify(z, j2, semWallpaperColors, "notifyUpdate");
            synchronized ((z ? this.mCoverCallbacks : this.mCallbacks)) {
                try {
                    ArrayList arrayList = z ? this.mCoverCallbacks : this.mCallbacks;
                    for (int i = 0; i < arrayList.size(); i++) {
                        Pair pair = (Pair) arrayList.get(i);
                        final SystemUIWidgetCallback systemUIWidgetCallback = (SystemUIWidgetCallback) ((WeakReference) pair.first).get();
                        long longValue = ((Long) pair.second).longValue();
                        if (systemUIWidgetCallback != null) {
                            if ((longValue & j2) == 0) {
                                if ((8 & longValue) != 0) {
                                    for (int i2 = 0; i2 < KeyguardWallpaperColors.NUM_SEPARATED_AREA; i2++) {
                                        if ((KeyguardWallpaperColors.UPDATE_FLAGS[i2] & longValue) != 0 && (KeyguardWallpaperColors.UPDATE_FLAGS_SHADOW[i2] & j2) != 0) {
                                            break;
                                        }
                                    }
                                }
                                if ((4 & longValue) != 0) {
                                    for (int i3 = 0; i3 < KeyguardWallpaperColors.NUM_SEPARATED_AREA; i3++) {
                                        if ((KeyguardWallpaperColors.UPDATE_FLAGS[i3] & longValue) == 0 || (KeyguardWallpaperColors.UPDATE_FLAGS_ADAPTIVE_CONTRAST[i3] & j2) == 0) {
                                        }
                                    }
                                }
                            }
                            this.mHandler.post(new Runnable() { // from class: com.android.systemui.wallpaper.WallpaperEventNotifier$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    SystemUIWidgetCallback.this.updateStyle(j2, semWallpaperColors);
                                }
                            });
                            break;
                        }
                    }
                } finally {
                }
            }
        }
    }
}
