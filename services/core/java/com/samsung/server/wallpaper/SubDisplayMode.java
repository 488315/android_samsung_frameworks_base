package com.samsung.server.wallpaper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ServiceManager;
import android.view.IDisplayFoldListener;
import android.view.IWindowManager;

import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.ServiceKeeper$$ExternalSyntheticOutline0;
import com.android.server.input.InputManagerService;
import com.android.server.wallpaper.WallpaperData;
import com.android.server.wallpaper.WallpaperManagerService;

import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.wallpaper.Rune;
import com.samsung.android.wallpaper.utils.WhichChecker;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SubDisplayMode {
    public final WallpaperManagerService.SemCallback mCallback;
    public final AnonymousClass2 mDisplayFoldListener;
    public int mLidState = -1;
    public final AnonymousClass1 mHandler =
            new Handler(
                    Looper
                            .getMainLooper()) { // from class:
                                                // com.samsung.server.wallpaper.SubDisplayMode.1
                @Override // android.os.Handler
                public final void handleMessage(Message message) {
                    int i;
                    int i2;
                    WallpaperData peekWallpaperDataLocked;
                    if (message.what != 1010) {
                        return;
                    }
                    WallpaperManagerService.SemCallback semCallback = SubDisplayMode.this.mCallback;
                    int i3 = message.arg1;
                    int i4 = message.arg2;
                    WallpaperManagerService wallpaperManagerService = WallpaperManagerService.this;
                    wallpaperManagerService.mSemService.mSubDisplayMode.getClass();
                    int mode = WhichChecker.getMode(SubDisplayMode.getFolderStateBasedWhich(1, i3));
                    wallpaperManagerService.mSemService.mSubDisplayMode.getClass();
                    int mode2 =
                            WhichChecker.getMode(SubDisplayMode.getFolderStateBasedWhich(1, i4));
                    int i5 = wallpaperManagerService.mCurrentUserId;
                    int i6 = wallpaperManagerService.mSemService.mOldUserId;
                    if (SemPersonaManager.isSecureFolderId(i5)) {
                        i5 = i6 == -10000 ? 0 : i6;
                    }
                    int modeEnsuredWhich =
                            wallpaperManagerService.mSemService.getModeEnsuredWhich(1);
                    int modeEnsuredWhich2 =
                            wallpaperManagerService.mSemService.getModeEnsuredWhich(2);
                    StringBuilder m =
                            ArrayUtils$$ExternalSyntheticOutline0.m(
                                    i3,
                                    i4,
                                    "switchWallpaperByDisplayChanged   prevFolderState : ",
                                    " curFolderState : ",
                                    " oldMode : ");
                    ServiceKeeper$$ExternalSyntheticOutline0.m(
                            mode, mode2, " newMode : ", " mCurrentUserId : ", m);
                    m.append(wallpaperManagerService.mCurrentUserId);
                    m.append(" mOldUserId : ");
                    m.append(wallpaperManagerService.mSemService.mOldUserId);
                    m.append(" userId : ");
                    m.append(i5);
                    Log.d("WallpaperManagerService", m.toString());
                    int i7 = mode | 1;
                    WallpaperData peekWallpaperDataLocked2 =
                            wallpaperManagerService.peekWallpaperDataLocked(i5, i7);
                    if (peekWallpaperDataLocked2 != null) {
                        i =
                                WallpaperManagerService
                                        .m1040$$Nest$mcheckSameComponentSetOtherDisplay(
                                                wallpaperManagerService, peekWallpaperDataLocked2);
                        if (i > 0) {
                            wallpaperManagerService.forceRebindWallpaper(i, i5);
                        }
                    } else {
                        i = 0;
                    }
                    if (WhichChecker.isSystemAndLock(i)
                            || (peekWallpaperDataLocked =
                                            wallpaperManagerService.peekWallpaperDataLocked(
                                                    i5, mode | 2))
                                    == null) {
                        i2 = 0;
                    } else {
                        i2 =
                                WallpaperManagerService
                                        .m1040$$Nest$mcheckSameComponentSetOtherDisplay(
                                                wallpaperManagerService, peekWallpaperDataLocked);
                        if (i2 > 0) {
                            wallpaperManagerService.forceRebindWallpaper(i2, i5);
                        }
                    }
                    Log.d(
                            "WallpaperManagerService",
                            "switchWallpaperByDisplayChanged  sameWallpaperWithPrevSystem : "
                                    + i
                                    + " sameWallpaperWithPrevLock : "
                                    + i2);
                    WallpaperData wallpaperSafeLocked =
                            wallpaperManagerService.getWallpaperSafeLocked(
                                    wallpaperManagerService.mCurrentUserId, modeEnsuredWhich);
                    SemWallpaperData semWallpaperData = wallpaperSafeLocked.mSemWallpaperData;
                    if (semWallpaperData.mPrimarySemColors == null) {
                        wallpaperManagerService.mSemService.mLegibilityColor.extractColor(
                                semWallpaperData.mWhich, false);
                    } else {
                        wallpaperManagerService.notifySemColorListeners(0, wallpaperSafeLocked);
                    }
                    WallpaperData wallpaperSafeLocked2 =
                            wallpaperManagerService.getWallpaperSafeLocked(
                                    wallpaperManagerService.mCurrentUserId, modeEnsuredWhich2);
                    SemWallpaperData semWallpaperData2 = wallpaperSafeLocked2.mSemWallpaperData;
                    if (semWallpaperData2.mPrimarySemColors == null) {
                        wallpaperManagerService.mSemService.mLegibilityColor.extractColor(
                                semWallpaperData2.mWhich, false);
                    } else {
                        wallpaperManagerService.notifySemColorListeners(0, wallpaperSafeLocked2);
                    }
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isFolded", i4 == 0);
                    if (mode != mode2) {
                        wallpaperManagerService.semSendWallpaperCommand(
                                i7, "switch_display", bundle);
                        if (!wallpaperManagerService.mSemService.isSystemAndLockPaired(mode, i5)) {
                            wallpaperManagerService.semSendWallpaperCommand(
                                    mode | 2, "switch_display", bundle);
                        }
                    }
                    wallpaperManagerService.semSendWallpaperCommand(
                            mode2 | 1, "switch_display", bundle);
                    if (wallpaperManagerService.mSemService.isSystemAndLockPaired(mode2, i5)) {
                        return;
                    }
                    wallpaperManagerService.semSendWallpaperCommand(
                            mode2 | 2, "switch_display", bundle);
                }
            };

    /* JADX WARN: Type inference failed for: r0v1, types: [com.samsung.server.wallpaper.SubDisplayMode$1] */
    public SubDisplayMode(WallpaperManagerService.SemCallback semCallback) {
        IDisplayFoldListener.Stub stub =
                new IDisplayFoldListener
                        .Stub() { // from class: com.samsung.server.wallpaper.SubDisplayMode.2
                    public final void onDisplayFoldChanged(int i, boolean z) {
                        Log.d(
                                "SubDisplayMode",
                                "onDisplayFoldChanged: displayId = " + i + ", folded = " + z);
                        SubDisplayMode subDisplayMode = SubDisplayMode.this;
                        int i2 = subDisplayMode.mLidState;
                        int i3 = !z ? 1 : 0;
                        subDisplayMode.mLidState = i3;
                        if (i3 == -1 || i3 == i2) {
                            return;
                        }
                        subDisplayMode.mCallback.updateDisplayData();
                        if (Rune.SUPPORT_COVER_DISPLAY_WATCHFACE) {
                            if (z) {
                                return;
                            }
                            SubDisplayMode.this.mCallback.notifySemWallpaperColors(6);
                            SubDisplayMode.this.mCallback.notifySemWallpaperColors(5);
                            return;
                        }
                        AnonymousClass1 anonymousClass1 = SubDisplayMode.this.mHandler;
                        if (anonymousClass1.hasMessages(1010)) {
                            anonymousClass1.removeMessages(1010);
                        }
                        Message obtainMessage = anonymousClass1.obtainMessage(1010);
                        obtainMessage.arg1 = i2;
                        obtainMessage.arg2 = i3;
                        anonymousClass1.sendMessage(obtainMessage);
                    }
                };
        Log.d("SubDisplayMode", "SubDisplayMode");
        this.mCallback = semCallback;
        if (Rune.SUPPORT_SUB_DISPLAY_MODE) {
            try {
                IWindowManager.Stub.asInterface(ServiceManager.getService("window"))
                        .registerDisplayFoldListener(stub);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static int getFolderStateBasedWhich(int i, int i2) {
        if (!Rune.SUPPORT_SUB_DISPLAY_MODE) {
            return i;
        }
        Log.d("SubDisplayMode", "getFolderStateBasedWhich state = " + i2);
        return i2 == 0 ? i | 16 : i | 4;
    }

    public final int getFolderStateBasedWhich(int i) {
        return (Rune.SUPPORT_SUB_DISPLAY_MODE && WhichChecker.getMode(i) == 0)
                ? getFolderStateBasedWhich(i, this.mLidState)
                : i;
    }

    public final void updateLidStateFromInputManager() {
        int i = -1;
        try {
            InputManagerService inputManagerService =
                    (InputManagerService) ServiceManager.getService("input");
            if (inputManagerService == null) {
                Log.d(
                        "SubDisplayMode",
                        "updateLidStateFromInputManager: inputManagerService is null! Setting"
                            + " lidState to UNKNOWN(ABSENT)");
            } else {
                int switchState = inputManagerService.mNative.getSwitchState(-1, -256, 0);
                if (switchState > 0) {
                    i = 0;
                } else if (switchState == 0) {
                    i = 1;
                }
            }
        } catch (Exception unused) {
        }
        Log.d("SubDisplayMode", "updateLidStateFromInputManager: lidState = " + i);
        this.mLidState = i;
    }
}
