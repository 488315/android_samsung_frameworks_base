package android.hardware.display;

import android.os.Parcelable;

/* loaded from: classes2.dex */
public class SemWifiDisplayStatus {
    public static final int DISPLAY_STATE_CONNECTED = 2;
    public static final int DISPLAY_STATE_CONNECTING = 1;
    public static final int DISPLAY_STATE_DISCONNECTING = 3;
    public static final int DISPLAY_STATE_NOT_CONNECTED = 0;
    public static final int FEATURE_STATE_DISABLED = 1;
    public static final int FEATURE_STATE_OFF = 2;
    public static final int FEATURE_STATE_ON = 3;
    public static final int FEATURE_STATE_UNAVAILABLE = 0;
    public static final int SCREEN_SHARING_STATE_LANDSCAPE = 8;
    public static final int SCREEN_SHARING_STATE_PAUSED = 7;
    public static final int SCREEN_SHARING_STATE_PORTRAIT = 9;
    public static final int SCREEN_SHARING_STATE_RESUMED = 6;
    private final WifiDisplayStatus mWds;

    public SemWifiDisplayStatus(WifiDisplayStatus wifiDisplayStatus) {
        this.mWds = wifiDisplayStatus;
    }

    public SemWifiDisplayStatus(Parcelable parcelable) {
        if (parcelable instanceof WifiDisplayStatus) {
            this.mWds = (WifiDisplayStatus) parcelable;
            return;
        }
        throw new IllegalArgumentException("parameter must be WifiDisplayStatus type");
    }

    public SemWifiDisplay[] getDisplays() {
        WifiDisplay[] displays = this.mWds.getDisplays();
        int i = 0;
        if (displays.length > 0) {
            SemWifiDisplay[] semWifiDisplayArr = new SemWifiDisplay[displays.length];
            int length = displays.length;
            int i2 = 0;
            while (i < length) {
                semWifiDisplayArr[i2] = new SemWifiDisplay(displays[i]);
                i++;
                i2++;
            }
            return semWifiDisplayArr;
        }
        return new SemWifiDisplay[0];
    }

    public String getGroupId() {
        return this.mWds.getSessionInfo().getGroupId();
    }

    public int getSessionId() {
        return this.mWds.getSessionInfo().getSessionId();
    }

    public String getSessionSummary() {
        return this.mWds.getSessionInfo().toString();
    }

    public boolean isScanning() {
        return this.mWds.getScanState() == 1;
    }

    public int getFeatureState() {
        return this.mWds.getFeatureState();
    }

    public int getActiveDisplayState() {
        return this.mWds.getActiveDisplayState();
    }

    public SemWifiDisplay getActiveDisplay() {
        WifiDisplay activeDisplay = this.mWds.getActiveDisplay();
        if (activeDisplay != null) {
            return new SemWifiDisplay(activeDisplay);
        }
        return null;
    }

    public int getConnectedState() {
        return this.mWds.getConnectedState();
    }
}
