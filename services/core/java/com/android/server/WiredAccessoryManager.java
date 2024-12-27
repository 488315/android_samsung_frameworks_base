package com.android.server;

import android.R;
import android.content.Context;
import android.database.ContentObserver;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.media.AudioManager;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.UEventObserver;
import android.provider.Settings;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.view.ContextThemeWrapper;
import android.widget.Toast;

import com.android.server.input.InputManagerService;

import com.samsung.android.audio.Rune;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class WiredAccessoryManager implements InputManagerService.WiredAccessoryCallbacks {
    public final AudioManager mAudioManager;
    public final Context mContext;
    public int mHeadsetState;
    public final InputManagerService mInputManager;
    public final WiredAccessoryObserver mObserver;
    public int mSwitchValues;
    public final boolean mUseDevInputEventForAudioJack;
    public final PowerManager.WakeLock mWakeLock;
    public final Object mLock = new Object();
    public final AnonymousClass2 mHandler =
            new Handler(
                    Looper.myLooper()) { // from class: com.android.server.WiredAccessoryManager.2
                @Override // android.os.Handler
                public final void handleMessage(Message message) {
                    int i;
                    int i2;
                    FileReader fileReader;
                    int i3 = message.what;
                    int i4 = 1;
                    int i5 = 1024;
                    if (i3 == 1) {
                        WiredAccessoryManager wiredAccessoryManager = WiredAccessoryManager.this;
                        int i6 = message.arg1;
                        int i7 = message.arg2;
                        String str = (String) message.obj;
                        synchronized (wiredAccessoryManager.mLock) {
                            int i8 = 63;
                            int i9 = 1;
                            while (i8 != 0) {
                                if ((i9 & i8) != 0) {
                                    int i10 = i6 & i9;
                                    if (i10 != (i7 & i9)) {
                                        int i11 = i10 != 0 ? i4 : 0;
                                        if (i9 == i4) {
                                            i2 = -2147483632;
                                            i = 4;
                                        } else {
                                            i = 8;
                                            if (i9 != 2) {
                                                if (i9 == 32) {
                                                    i = 131072;
                                                } else if (i9 == 4) {
                                                    i = 2048;
                                                } else if (i9 == 8) {
                                                    i = 4096;
                                                } else if (i9 == 16) {
                                                    i = i5;
                                                } else {
                                                    Slog.e(
                                                            "WiredAccessoryManager",
                                                            "setDeviceState() invalid headset type:"
                                                                + " "
                                                                    + i9);
                                                }
                                            }
                                            i2 = 0;
                                        }
                                        if (i != 0) {
                                            wiredAccessoryManager.mAudioManager
                                                    .setWiredDeviceConnectionState(i, i11, "", str);
                                        }
                                        if (i2 != 0) {
                                            wiredAccessoryManager.mAudioManager
                                                    .setWiredDeviceConnectionState(
                                                            i2, i11, "", str);
                                        }
                                    }
                                    i8 = (~i9) & i8;
                                }
                                i9 <<= 1;
                                i4 = 1;
                                i5 = 1024;
                            }
                        }
                        WiredAccessoryManager.this.mWakeLock.release();
                        return;
                    }
                    if (i3 != 2) {
                        if (i3 != 3) {
                            return;
                        }
                        WiredAccessoryManager wiredAccessoryManager2 = WiredAccessoryManager.this;
                        wiredAccessoryManager2.getClass();
                        Toast.makeText(
                                        new ContextThemeWrapper(
                                                wiredAccessoryManager2.mContext,
                                                R.style.Theme.DeviceDefault.Light),
                                        wiredAccessoryManager2
                                                .mContext
                                                .getResources()
                                                .getString(R.string.mediasize_iso_b7),
                                        1)
                                .show();
                        WiredAccessoryManager.this.mWakeLock.release();
                        return;
                    }
                    WiredAccessoryManager wiredAccessoryManager3 = WiredAccessoryManager.this;
                    if (wiredAccessoryManager3.mUseDevInputEventForAudioJack) {
                        int i12 =
                                wiredAccessoryManager3.mInputManager.mNative.getSwitchState(
                                                        -1, -256, 2)
                                                == 1
                                        ? 4
                                        : 0;
                        if (wiredAccessoryManager3.mInputManager.mNative.getSwitchState(-1, -256, 4)
                                == 1) {
                            i12 |= 16;
                        }
                        if (wiredAccessoryManager3.mInputManager.mNative.getSwitchState(-1, -256, 6)
                                == 1) {
                            i12 |= 64;
                        }
                        wiredAccessoryManager3.notifyWiredAccessoryChanged(i12, 84);
                    }
                    WiredAccessoryObserver wiredAccessoryObserver =
                            wiredAccessoryManager3.mObserver;
                    synchronized (WiredAccessoryManager.this.mLock) {
                        for (int i13 = 0;
                                i13 < ((ArrayList) wiredAccessoryObserver.mUEventInfo).size();
                                i13++) {
                            WiredAccessoryObserver.UEventInfo uEventInfo =
                                    (WiredAccessoryObserver.UEventInfo)
                                            ((ArrayList) wiredAccessoryObserver.mUEventInfo)
                                                    .get(i13);
                            try {
                                fileReader = new FileReader(uEventInfo.getSwitchStatePath());
                            } catch (FileNotFoundException unused) {
                                Slog.w(
                                        "WiredAccessoryManager",
                                        uEventInfo.getSwitchStatePath()
                                                + " not found while attempting to determine initial"
                                                + " switch state");
                            } catch (Exception e) {
                                Slog.e(
                                        "WiredAccessoryManager",
                                        "Error while attempting to determine initial switch state"
                                            + " for "
                                                + uEventInfo.mDevName,
                                        e);
                            }
                            try {
                                char[] cArr = new char[1024];
                                int parseInt =
                                        Integer.parseInt(
                                                new String(cArr, 0, fileReader.read(cArr, 0, 1024))
                                                        .trim());
                                fileReader.close();
                                if (parseInt > 0) {
                                    wiredAccessoryObserver.updateStateLocked(
                                            parseInt, uEventInfo.getDevPath(), uEventInfo.mDevName);
                                }
                            } finally {
                            }
                        }
                    }
                    for (int i14 = 0;
                            i14 < ((ArrayList) wiredAccessoryObserver.mUEventInfo).size();
                            i14++) {
                        wiredAccessoryObserver.startObserving(
                                "DEVPATH="
                                        .concat(
                                                ((WiredAccessoryObserver.UEventInfo)
                                                                ((ArrayList)
                                                                                wiredAccessoryObserver
                                                                                        .mUEventInfo)
                                                                        .get(i14))
                                                        .getDevPath()));
                    }
                    WiredAccessoryManager.this.mWakeLock.release();
                }
            };
    public boolean mBikeMode = false;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class WiredAccessoryExtconObserver extends ExtconUEventObserver {
        public WiredAccessoryExtconObserver() {
            ExtconUEventObserver.ExtconInfo.getExtconInfoForTypes(
                    new String[] {"HEADPHONE", "MICROPHONE", "HDMI", "LINE-OUT"});
        }

        @Override // com.android.server.ExtconUEventObserver
        public final void onUEvent(
                ExtconUEventObserver.ExtconInfo extconInfo, UEventObserver.UEvent uEvent) {
            String str = uEvent.get("NAME");
            String str2 = uEvent.get("STATE");
            int[] iArr = {0, 0};
            if (extconInfo.mDeviceTypes.contains("HEADPHONE")) {
                WiredAccessoryManager.m113$$Nest$smupdateBit(str2, "HEADPHONE", 2, iArr);
            }
            if (extconInfo.mDeviceTypes.contains("MICROPHONE")) {
                WiredAccessoryManager.m113$$Nest$smupdateBit(str2, "MICROPHONE", 1, iArr);
            }
            if (extconInfo.mDeviceTypes.contains("HDMI")) {
                WiredAccessoryManager.m113$$Nest$smupdateBit(str2, "HDMI", 16, iArr);
            }
            if (extconInfo.mDeviceTypes.contains("LINE-OUT")) {
                WiredAccessoryManager.m113$$Nest$smupdateBit(str2, "LINE-OUT", 32, iArr);
            }
            Pair create = Pair.create(Integer.valueOf(iArr[0]), Integer.valueOf(iArr[1]));
            if (create != null) {
                synchronized (WiredAccessoryManager.this.mLock) {
                    int intValue = ((Integer) create.first).intValue();
                    int intValue2 = ((Integer) create.second).intValue();
                    WiredAccessoryManager wiredAccessoryManager = WiredAccessoryManager.this;
                    wiredAccessoryManager.updateLocked(
                            (intValue2 & intValue)
                                    | (wiredAccessoryManager.mHeadsetState
                                            & (~((~intValue2) & intValue))),
                            str);
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class WiredAccessoryObserver extends UEventObserver {
        public final List mUEventInfo;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class UEventInfo {
            public final String mDevName;
            public final int mState1Bits;
            public final int mState2Bits;
            public final int mStateNbits;

            public UEventInfo(String str, int i, int i2, int i3) {
                this.mDevName = str;
                this.mState1Bits = i;
                this.mState2Bits = i2;
                this.mStateNbits = i3;
            }

            public final boolean checkSwitchExists() {
                return new File(getSwitchStatePath()).exists();
            }

            public final String getDevPath() {
                Locale locale = Locale.US;
                return "/devices/virtual/switch/" + this.mDevName;
            }

            public final String getSwitchStatePath() {
                Locale locale = Locale.US;
                return AudioOffloadInfo$$ExternalSyntheticOutline0.m(
                        new StringBuilder("/sys/class/switch/"), this.mDevName, "/state");
            }
        }

        public WiredAccessoryObserver() {
            ArrayList arrayList = new ArrayList();
            if (!WiredAccessoryManager.this.mUseDevInputEventForAudioJack) {
                UEventInfo uEventInfo = new UEventInfo("h2w", 1, 2, 32);
                if (uEventInfo.checkSwitchExists()) {
                    arrayList.add(uEventInfo);
                } else {
                    Slog.w(
                            "WiredAccessoryManager",
                            "This kernel does not have wired headset support");
                }
            }
            UEventInfo uEventInfo2 = new UEventInfo("usb_audio", 4, 8, 0);
            if (uEventInfo2.checkSwitchExists()) {
                arrayList.add(uEventInfo2);
            } else {
                Slog.w("WiredAccessoryManager", "This kernel does not have usb audio support");
            }
            UEventInfo uEventInfo3 = new UEventInfo("ch_hdmi_audio", 16, 0, 0);
            if (uEventInfo3.checkSwitchExists()) {
                arrayList.add(uEventInfo3);
            } else {
                UEventInfo uEventInfo4 = new UEventInfo("hdmi_audio", 16, 0, 0);
                if (uEventInfo4.checkSwitchExists()) {
                    arrayList.add(uEventInfo4);
                } else {
                    UEventInfo uEventInfo5 = new UEventInfo("hdmi", 16, 0, 0);
                    if (uEventInfo5.checkSwitchExists()) {
                        arrayList.add(uEventInfo5);
                    } else {
                        Slog.w(
                                "WiredAccessoryManager",
                                "This kernel does not have HDMI audio support");
                    }
                }
            }
            this.mUEventInfo = arrayList;
        }

        public final void onUEvent(UEventObserver.UEvent uEvent) {
            try {
                String str = uEvent.get("DEVPATH");
                String str2 = uEvent.get("SWITCH_NAME");
                int parseInt = Integer.parseInt(uEvent.get("SWITCH_STATE"));
                synchronized (WiredAccessoryManager.this.mLock) {
                    updateStateLocked(parseInt, str, str2);
                }
            } catch (NumberFormatException unused) {
                Slog.e(
                        "WiredAccessoryManager",
                        "Could not parse switch state from event " + uEvent);
            }
        }

        public final void updateStateLocked(int i, String str, String str2) {
            int i2 = 0;
            for (int i3 = 0; i3 < ((ArrayList) this.mUEventInfo).size(); i3++) {
                UEventInfo uEventInfo = (UEventInfo) ((ArrayList) this.mUEventInfo).get(i3);
                if (str.equals(uEventInfo.getDevPath())) {
                    if ("ch_hdmi_audio".equals(uEventInfo.mDevName)) {
                        if ((i & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT)
                                == 0) {
                            Slog.v(
                                    "WiredAccessoryManager",
                                    "This is for DVI and No-Speaker HDMI Device");
                            return;
                        }
                        String valueOf = String.valueOf(i);
                        i = i == -1 ? 0 : 1;
                        Slog.v("WiredAccessoryManager", "HDMI config " + valueOf + ", state " + i);
                        str2 = valueOf;
                    }
                    WiredAccessoryManager wiredAccessoryManager = WiredAccessoryManager.this;
                    int i4 = wiredAccessoryManager.mHeadsetState;
                    int i5 = uEventInfo.mState1Bits;
                    int i6 = uEventInfo.mState2Bits;
                    int i7 = uEventInfo.mStateNbits;
                    int i8 = ~(i5 | i6 | i7);
                    if (i == 1) {
                        i2 = i5;
                    } else if (i == 2) {
                        i2 = i6;
                    } else if (i == i7) {
                        i2 = i7;
                    }
                    wiredAccessoryManager.updateLocked((i4 & i8) | i2, str2);
                    return;
                }
            }
        }
    }

    /* renamed from: -$$Nest$smupdateBit, reason: not valid java name */
    public static void m113$$Nest$smupdateBit(String str, String str2, int i, int[] iArr) {
        iArr[0] = iArr[0] | i;
        if (str.contains(str2.concat("=1"))) {
            iArr[0] = iArr[0] | i;
            iArr[1] = iArr[1] | i;
        } else if (str.contains(str2.concat("=0"))) {
            iArr[0] = iArr[0] | i;
            iArr[1] = iArr[1] & (~i);
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.server.WiredAccessoryManager$2] */
    public WiredAccessoryManager(Context context, InputManagerService inputManagerService) {
        PowerManager.WakeLock newWakeLock =
                ((PowerManager) context.getSystemService("power"))
                        .newWakeLock(1, "WiredAccessoryManager");
        this.mWakeLock = newWakeLock;
        newWakeLock.setReferenceCounted(false);
        this.mAudioManager = (AudioManager) context.getSystemService("audio");
        this.mInputManager = inputManagerService;
        this.mUseDevInputEventForAudioJack =
                context.getResources()
                        .getBoolean(R.bool.config_viewRotaryEncoderHapticScrollFedbackEnabled);
        new WiredAccessoryExtconObserver();
        this.mObserver = new WiredAccessoryObserver();
        this.mContext = context;
        if (Rune.SEC_AUDIO_BIKE_MODE) {
            context.getContentResolver()
                    .registerContentObserver(
                            Settings.Secure.getUriFor("isBikeMode"),
                            false,
                            new ContentObserver(
                                    new Handler()) { // from class:
                                                     // com.android.server.WiredAccessoryManager.1
                                @Override // android.database.ContentObserver
                                public final void onChange(boolean z) {
                                    super.onChange(z);
                                    WiredAccessoryManager wiredAccessoryManager =
                                            WiredAccessoryManager.this;
                                    int i = 0;
                                    wiredAccessoryManager.mBikeMode =
                                            Settings.Secure.getInt(
                                                            wiredAccessoryManager.mContext
                                                                    .getContentResolver(),
                                                            "isBikeMode",
                                                            0)
                                                    == 1;
                                    wiredAccessoryManager.mWakeLock.acquire();
                                    boolean z2 = wiredAccessoryManager.mBikeMode;
                                    AnonymousClass2 anonymousClass2 =
                                            wiredAccessoryManager.mHandler;
                                    if (z2) {
                                        Slog.w(
                                                "WiredAccessoryManager",
                                                "Earphones are disabled in bike mode");
                                        if (wiredAccessoryManager.mHeadsetState != 0) {
                                            anonymousClass2.sendMessage(
                                                    anonymousClass2.obtainMessage(3, 0, 0, null));
                                        }
                                    } else {
                                        int i2 = wiredAccessoryManager.mHeadsetState;
                                        wiredAccessoryManager.mHeadsetState = 0;
                                        i = i2;
                                    }
                                    anonymousClass2.sendMessage(
                                            anonymousClass2.obtainMessage(
                                                    1,
                                                    i,
                                                    wiredAccessoryManager.mHeadsetState,
                                                    "h2w"));
                                    if (wiredAccessoryManager.mBikeMode) {
                                        return;
                                    }
                                    wiredAccessoryManager.mHeadsetState = i;
                                }
                            });
        }
    }

    public final void notifyWiredAccessoryChanged(int i, int i2) {
        synchronized (this.mLock) {
            int i3 = i | ((~i2) & this.mSwitchValues);
            this.mSwitchValues = i3;
            int i4 = i3 & 84;
            int i5 = 0;
            if (i4 != 0) {
                if (i4 == 4) {
                    i5 = 2;
                } else if (i4 == 16 || i4 == 20) {
                    i5 = 1;
                } else if (i4 == 64) {
                    i5 = 32;
                }
            }
            updateLocked(i5 | (this.mHeadsetState & (-36)), "h2w");
        }
    }

    public final void updateLocked(int i, String str) {
        boolean z;
        boolean z2;
        int i2 = i & 63;
        int i3 = i & 4;
        int i4 = i & 8;
        int i5 = i & 35;
        if (this.mHeadsetState == i2) {
            Log.e("WiredAccessoryManager", "No state change.");
            return;
        }
        if (i5 == 35) {
            Log.e("WiredAccessoryManager", "Invalid combination, unsetting h2w flag");
            z = false;
        } else {
            z = true;
        }
        if (i3 == 4 && i4 == 8) {
            Log.e("WiredAccessoryManager", "Invalid combination, unsetting usb flag");
            z2 = false;
        } else {
            z2 = true;
        }
        if (!z && !z2) {
            Log.e("WiredAccessoryManager", "invalid transition, returning ...");
            return;
        }
        this.mWakeLock.acquire();
        boolean z3 = Rune.SEC_AUDIO_BIKE_MODE;
        AnonymousClass2 anonymousClass2 = this.mHandler;
        if (z3 && this.mBikeMode) {
            if (i5 != 0) {
                Slog.w("WiredAccessoryManager", "Bike mode is ON. Earphones disabled");
                anonymousClass2.sendMessage(anonymousClass2.obtainMessage(3, 0, 0, null));
            }
        } else if (!this.mBikeMode || i5 == 0) {
            Log.i("WiredAccessoryManager", "MSG_NEW_DEVICE_STATE");
            anonymousClass2.sendMessage(
                    anonymousClass2.obtainMessage(1, i2, this.mHeadsetState, str));
        }
        this.mHeadsetState = i2;
    }
}
