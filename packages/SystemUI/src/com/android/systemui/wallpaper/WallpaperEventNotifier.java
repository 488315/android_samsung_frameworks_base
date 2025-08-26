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
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.colors.KeyguardWallpaperColors;
import com.android.systemui.widget.SystemUIWidgetCallback;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class WallpaperEventNotifier {
    public static final boolean DEBUG = !DeviceType.isShipBuild();
    public final Handler mHandler;
    public final KeyguardWallpaperColors mKeyguardWallpaperColors;
    public final SelectedUserInteractor mSelectedUserInteractor;
    public final WallpaperManager mWallpaperManager;
    public long mCurStatusFlag = 0;
    public final ArrayList mCallbacks = new ArrayList();
    public final ArrayList mCoverCallbacks = new ArrayList();
    public boolean mIsThemeApplying = false;
    public final ArrayList mLogs = new ArrayList();

    public class DebugLog {
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
        this.mKeyguardWallpaperColors = new KeyguardWallpaperColors(context, settingsHelper, selectedUserInteractor.getSelectedUserId());
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
        StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(str, ": ");
        sbM.append(sb.toString());
        Log.d("WallpaperEventNotifier", sbM.toString());
        addLog(str + ": " + sb.toString());
    }

    public final SemWallpaperColors getSemWallpaperColors(boolean z) {
        KeyguardWallpaperColors keyguardWallpaperColors = this.mKeyguardWallpaperColors;
        return keyguardWallpaperColors.getSemWallpaperColors(keyguardWallpaperColors.mSelectedUserId, z);
    }

    /* JADX WARN: Removed duplicated region for block: B:47:0x00b5  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00db  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void registerCallback(boolean z, SystemUIWidgetCallback systemUIWidgetCallback, long j) {
        long j2;
        long j3;
        synchronized ((z ? this.mCoverCallbacks : this.mCallbacks)) {
            try {
                ArrayList arrayList = z ? this.mCoverCallbacks : this.mCallbacks;
                for (int i = 0; i < arrayList.size(); i++) {
                    if (((WeakReference) ((Pair) arrayList.get(i)).first).get() == systemUIWidgetCallback) {
                        Log.e("WallpaperEventNotifier", "registerCallback: Object tried to add another callback " + Debug.getCaller());
                        return;
                    }
                }
                arrayList.add(Pair.create(new WeakReference(systemUIWidgetCallback), Long.valueOf(j)));
                removeCallback(z, null);
                if (this.mIsThemeApplying) {
                    Log.d("WallpaperEventNotifier", "sendUpdates: Ignore update while theme is applying...");
                    addLog("sendUpdates: Ignore update while theme is applying...");
                    return;
                }
                KeyguardWallpaperColors keyguardWallpaperColors = this.mKeyguardWallpaperColors;
                SemWallpaperColors semWallpaperColors = keyguardWallpaperColors.getSemWallpaperColors(keyguardWallpaperColors.mSelectedUserId, z);
                if (semWallpaperColors == null) {
                    Log.d("WallpaperEventNotifier", "sendUpdates: We don't have any colors to notify for now.");
                    addLog("sendUpdates: We don't have any colors to notify for now");
                    return;
                }
                if (!z) {
                    long j4 = this.mCurStatusFlag;
                    if ((1 & j4) == 0) {
                        j &= -2;
                    }
                    if ((2 & j4) == 0) {
                        j &= -3;
                    }
                    j3 = (j4 & 1024) == 0 ? -1025L : -1028L;
                    j2 = j;
                    if (j2 != 0) {
                        Log.d("WallpaperEventNotifier", "sendUpdates: Nothing to notify");
                        addLog("sendUpdates: Nothing to notify");
                        return;
                    }
                    if (DEBUG) {
                        debugNotify(z, j2, semWallpaperColors, "sendUpdates");
                    }
                    Log.d("WallpaperEventNotifier", "sendUpdates: typesTobeNotified = " + KeyguardWallpaperColors.getChangeFlagsString(j2));
                    systemUIWidgetCallback.updateStyle(j2, semWallpaperColors);
                    return;
                }
                j &= j3;
                j2 = j;
                if (j2 != 0) {
                }
            } catch (Throwable th) {
                throw th;
            }
        }
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
        long jCheckBaseUpdates = this.mKeyguardWallpaperColors.checkBaseUpdates(semWallpaperColors);
        if (z) {
            return;
        }
        this.mCurStatusFlag = jCheckBaseUpdates;
    }

    public final void update(boolean z, long j, final SemWallpaperColors semWallpaperColors) {
        if (this.mIsThemeApplying) {
            addLog("update: Ignore update while theme is applying...");
            Log.d("WallpaperEventNotifier", "update: Ignore update while theme is applying...");
            return;
        }
        if (z) {
            j &= LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY ? -2L : -1028L;
        }
        final long j2 = j;
        setCurStatusFlag(z, semWallpaperColors);
        if (j2 != 0) {
            debugNotify(z, j2, semWallpaperColors, "notifyUpdate");
            synchronized ((z ? this.mCoverCallbacks : this.mCallbacks)) {
                try {
                    ArrayList arrayList = z ? this.mCoverCallbacks : this.mCallbacks;
                    for (int i = 0; i < arrayList.size(); i++) {
                        Pair pair = (Pair) arrayList.get(i);
                        final SystemUIWidgetCallback systemUIWidgetCallback = (SystemUIWidgetCallback) ((WeakReference) pair.first).get();
                        long jLongValue = ((Long) pair.second).longValue();
                        if (systemUIWidgetCallback != null) {
                            if ((jLongValue & j2) != 0) {
                                this.mHandler.post(new Runnable() { // from class: com.android.systemui.wallpaper.WallpaperEventNotifier$$ExternalSyntheticLambda1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        SystemUIWidgetCallback systemUIWidgetCallback2 = systemUIWidgetCallback;
                                        long j3 = j2;
                                        SemWallpaperColors semWallpaperColors2 = semWallpaperColors;
                                        boolean z2 = WallpaperEventNotifier.DEBUG;
                                        systemUIWidgetCallback2.updateStyle(j3, semWallpaperColors2);
                                    }
                                });
                                break;
                            }
                            if ((8 & jLongValue) != 0) {
                                for (int i2 = 0; i2 < KeyguardWallpaperColors.NUM_SEPARATED_AREA; i2++) {
                                    if ((KeyguardWallpaperColors.UPDATE_FLAGS[i2] & jLongValue) != 0 && (KeyguardWallpaperColors.UPDATE_FLAGS_SHADOW[i2] & j2) != 0) {
                                        break;
                                    }
                                }
                            }
                            if ((4 & jLongValue) != 0) {
                                for (int i3 = 0; i3 < KeyguardWallpaperColors.NUM_SEPARATED_AREA; i3++) {
                                    if ((KeyguardWallpaperColors.UPDATE_FLAGS[i3] & jLongValue) != 0 && (KeyguardWallpaperColors.UPDATE_FLAGS_ADAPTIVE_CONTRAST[i3] & j2) != 0) {
                                        this.mHandler.post(new Runnable() { // from class: com.android.systemui.wallpaper.WallpaperEventNotifier$$ExternalSyntheticLambda1
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                SystemUIWidgetCallback systemUIWidgetCallback2 = systemUIWidgetCallback;
                                                long j3 = j2;
                                                SemWallpaperColors semWallpaperColors2 = semWallpaperColors;
                                                boolean z2 = WallpaperEventNotifier.DEBUG;
                                                systemUIWidgetCallback2.updateStyle(j3, semWallpaperColors2);
                                            }
                                        });
                                        break;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }
}
