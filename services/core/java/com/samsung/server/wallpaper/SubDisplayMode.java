package com.samsung.server.wallpaper;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ServiceManager;
import android.view.IDisplayFoldListener;
import android.view.IWindowManager;
import com.android.server.input.InputManagerService;
import com.android.server.wallpaper.WallpaperManagerService;
import com.samsung.android.wallpaper.Rune;
import com.samsung.android.wallpaper.utils.WhichChecker;

/* loaded from: classes2.dex */
public class SubDisplayMode {
    public final WallpaperManagerService.SemCallback mCallback;
    public IWindowManager mIWindowManager;
    public int mLidState = -1;
    public final Handler mHandler = new Handler(Looper.getMainLooper()) { // from class: com.samsung.server.wallpaper.SubDisplayMode.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 1010) {
                return;
            }
            SubDisplayMode.this.mCallback.switchWallpaperByDisplayChanged(message.arg1, message.arg2);
        }
    };
    public final IDisplayFoldListener.Stub mDisplayFoldListener = new IDisplayFoldListener.Stub() { // from class: com.samsung.server.wallpaper.SubDisplayMode.2
        public void onDisplayFoldChanged(int i, boolean z) {
            Log.m129d("SubDisplayMode", "onDisplayFoldChanged: displayId = " + i + ", folded = " + z);
            int lidState = SubDisplayMode.this.getLidState();
            SubDisplayMode.this.setLidState(!z ? 1 : 0);
            int lidState2 = SubDisplayMode.this.getLidState();
            if (lidState2 == -1 || lidState2 == lidState) {
                return;
            }
            SubDisplayMode.this.mCallback.updateDisplayData();
            if (!Rune.SUPPORT_COVER_DISPLAY_WATCHFACE) {
                SubDisplayMode.this.setFolderState(lidState, lidState2);
            } else {
                if (z) {
                    return;
                }
                SubDisplayMode.this.mCallback.notifySemWallpaperColors(6);
                SubDisplayMode.this.mCallback.notifySemWallpaperColors(5);
            }
        }
    };

    public SubDisplayMode(WallpaperManagerService.SemCallback semCallback) {
        Log.m129d("SubDisplayMode", "SubDisplayMode");
        this.mCallback = semCallback;
        initSubDisplayMode();
    }

    public void initSubDisplayMode() {
        if (Rune.SUPPORT_SUB_DISPLAY_MODE) {
            IWindowManager asInterface = IWindowManager.Stub.asInterface(ServiceManager.getService("window"));
            this.mIWindowManager = asInterface;
            try {
                asInterface.registerDisplayFoldListener(this.mDisplayFoldListener);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setLidState(int i) {
        this.mLidState = i;
    }

    public int getLidState() {
        return this.mLidState;
    }

    public int getFolderStateBasedWhich(int i) {
        return (Rune.SUPPORT_SUB_DISPLAY_MODE && WhichChecker.getMode(i) == 0) ? getFolderStateBasedWhich(i, getLidState()) : i;
    }

    public int getFolderStateBasedWhich(int i, int i2) {
        if (!Rune.SUPPORT_SUB_DISPLAY_MODE) {
            return i;
        }
        Log.m129d("SubDisplayMode", "getFolderStateBasedWhich state = " + i2);
        return i2 == 0 ? i | 16 : i | 4;
    }

    public void setFolderState(int i, int i2) {
        if (this.mHandler.hasMessages(1010)) {
            this.mHandler.removeMessages(1010);
        }
        Message obtainMessage = this.mHandler.obtainMessage(1010);
        obtainMessage.arg1 = i;
        obtainMessage.arg2 = i2;
        this.mHandler.sendMessage(obtainMessage);
    }

    public void updateLidStateFromInputManager() {
        int i = -1;
        try {
            InputManagerService inputManagerService = (InputManagerService) ServiceManager.getService("input");
            if (inputManagerService == null) {
                Log.m129d("SubDisplayMode", "updateLidStateFromInputManager: inputManagerService is null! Setting lidState to UNKNOWN(ABSENT)");
            } else {
                int switchState = inputManagerService.getSwitchState(-1, -256, 0);
                if (switchState > 0) {
                    i = 0;
                } else if (switchState == 0) {
                    i = 1;
                }
            }
        } catch (Exception unused) {
        }
        Log.m129d("SubDisplayMode", "updateLidStateFromInputManager: lidState = " + i);
        setLidState(i);
    }
}
