package com.android.systemui.knox;

import com.android.systemui.Dumpable;
import com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback;
import com.samsung.android.knox.custom.StatusbarIconItem;
import java.io.PrintWriter;

public final class CustomSdkMonitor extends IKnoxCustomManagerSystemUiCallback.Stub implements Dumpable {
    public final KnoxStateMonitorImpl knoxStateMonitor;
    public boolean mKnoxCustomUnlockSimOnBootState = false;
    public boolean mKnoxCustomQuickPanelButtonUsers = true;
    public boolean mKnoxCustomDoubleTapState = false;
    public boolean mStatusBarIconsState = true;
    public boolean mStatusBarNotificationsState = true;
    public boolean mChargerConnectionSoundEnabledState = true;
    public boolean mVolumePanelEnabledState = true;
    public boolean mIsCustomSdkStatusBarHidden = false;
    public boolean mHardKeyIntentState = false;
    public int mKnoxCustomLockScreenHiddenItems = 0;
    public int mKnoxCustomLockScreenOverrideMode = 0;
    public int mKnoxCustomQuickPanelButtons = 7;
    public int mKnoxCustomQuickPanelEditMode = 1;
    public final int mStatusBarMode = 2;
    public int mStatusBarTextStyle = 0;
    public int mStatusBarTextSize = 0;
    public int mStatusBarTextWidth = 0;
    public int mHideNotificationMessages = 0;
    public String mUnlockSimPin = null;
    public String mQuickPanelItems = null;
    public String mStatusBarText = null;
    public String mQuickPanelUnavailableButtons = null;
    public StatusbarIconItem mBatteryLevelColourItem = null;

    public CustomSdkMonitor(KnoxStateMonitorImpl knoxStateMonitorImpl) {
        this.knoxStateMonitor = knoxStateMonitorImpl;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("CustomSdkMonitor state:");
        printWriter.print("-mKnoxCustomLockScreenHiddenItems=");
        printWriter.println(this.mKnoxCustomLockScreenHiddenItems);
        printWriter.print("-mKnoxCustomLockScreenOverrideMode=");
        printWriter.println(this.mKnoxCustomLockScreenOverrideMode);
        printWriter.print("-mKnoxCustomUnlockSimOnBootState=");
        printWriter.println(this.mKnoxCustomUnlockSimOnBootState);
        printWriter.print("-mUnlockSimPin=");
        printWriter.println(this.mUnlockSimPin);
        printWriter.print("-mKnoxCustomQuickPanelButtons=");
        printWriter.println(this.mKnoxCustomQuickPanelButtons);
        printWriter.print("-mKnoxCustomQuickPanelButtonUsers=");
        printWriter.println(this.mKnoxCustomQuickPanelButtonUsers);
        printWriter.print("-mKnoxCustomQuickPanelEditMode=");
        printWriter.println(this.mKnoxCustomQuickPanelEditMode);
        printWriter.print("-mQuickPanelItems=");
        printWriter.println(this.mQuickPanelItems);
        printWriter.print("-mQuickPanelUnavailableButtons=");
        printWriter.println(this.mQuickPanelUnavailableButtons);
        printWriter.print("-mKnoxCustomDoubleTapState=");
        printWriter.println(this.mKnoxCustomDoubleTapState);
        printWriter.print("-mStatusBarText=");
        printWriter.println(this.mStatusBarText);
        printWriter.print("-mStatusBarMode=");
        printWriter.println(this.mStatusBarMode);
        printWriter.print("-mStatusBarTextStyle=");
        printWriter.println(this.mStatusBarTextStyle);
        printWriter.print("-mStatusBarTextSize=");
        printWriter.println(this.mStatusBarTextSize);
        printWriter.print("-mStatusBarTextWidth=");
        printWriter.println(this.mStatusBarTextWidth);
        printWriter.print("-mStatusBarIconsState=");
        printWriter.println(this.mStatusBarIconsState);
        if (this.mBatteryLevelColourItem != null) {
            printWriter.print("-mBatteryLevelColourItem=");
            printWriter.println(this.mBatteryLevelColourItem.toString());
        } else {
            printWriter.print("-mBatteryLevelColourItem=null");
        }
        printWriter.print("-mHideNotificationMessages=");
        printWriter.println(this.mHideNotificationMessages);
        printWriter.print("-mStatusBarNotificationsState=");
        printWriter.println(this.mStatusBarNotificationsState);
        printWriter.print("-mChargerConnectionSoundEnabledState=");
        printWriter.println(this.mChargerConnectionSoundEnabledState);
        printWriter.print("-mVolumePanelEnabledState=");
        printWriter.println(this.mVolumePanelEnabledState);
        printWriter.print("-mIsCustomSdkStatusBarHidden=");
        printWriter.println(this.mIsCustomSdkStatusBarHidden);
        printWriter.print("-mHardKeyIntentState=");
        printWriter.println(this.mHardKeyIntentState);
    }

    @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
    public final void setBatteryLevelColourItem(StatusbarIconItem statusbarIconItem) {
        this.mBatteryLevelColourItem = statusbarIconItem;
        this.knoxStateMonitor.mHandler.removeMessages(5018);
        this.knoxStateMonitor.mHandler.sendEmptyMessage(5018);
    }

    @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
    public final void setChargerConnectionSoundEnabledState(boolean z) {
        if (this.mChargerConnectionSoundEnabledState != z) {
            this.mChargerConnectionSoundEnabledState = z;
        }
    }

    @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
    public final void setHardKeyIntentState(boolean z) {
        if (this.mHardKeyIntentState != z) {
            this.mHardKeyIntentState = z;
            this.knoxStateMonitor.mHandler.removeMessages(5027);
            this.knoxStateMonitor.mHandler.sendMessage(this.knoxStateMonitor.mHandler.obtainMessage(5027, Boolean.valueOf(z)));
        }
    }

    @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
    public final void setHideNotificationMessages(int i) {
        if (this.mHideNotificationMessages != i) {
            this.mHideNotificationMessages = i;
        }
    }

    @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
    public final void setLockScreenHiddenItems(int i) {
        if (this.mKnoxCustomLockScreenHiddenItems != i) {
            this.mKnoxCustomLockScreenHiddenItems = i;
            this.knoxStateMonitor.mHandler.removeMessages(5010);
            this.knoxStateMonitor.mHandler.sendEmptyMessage(5010);
        }
    }

    @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
    public final void setLockScreenOverrideMode(int i) {
        if (this.mKnoxCustomLockScreenOverrideMode != i) {
            this.mKnoxCustomLockScreenOverrideMode = i;
            this.knoxStateMonitor.mHandler.removeMessages(5011);
            this.knoxStateMonitor.mHandler.sendEmptyMessage(5011);
        }
    }

    @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
    public final void setQuickPanelButtonUsers(boolean z) {
        if (this.mKnoxCustomQuickPanelButtonUsers != z) {
            this.mKnoxCustomQuickPanelButtonUsers = z;
            this.knoxStateMonitor.mHandler.removeMessages(5023);
            this.knoxStateMonitor.mHandler.sendEmptyMessage(5023);
        }
    }

    @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
    public final void setQuickPanelButtons(int i) {
        if (this.mKnoxCustomQuickPanelButtons != i) {
            this.mKnoxCustomQuickPanelButtons = i;
            this.knoxStateMonitor.mHandler.removeMessages(5012);
            this.knoxStateMonitor.mHandler.sendEmptyMessage(5012);
        }
    }

    @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
    public final void setQuickPanelEditMode(int i) {
        if (this.mKnoxCustomQuickPanelEditMode != i) {
            this.mKnoxCustomQuickPanelEditMode = i;
            this.knoxStateMonitor.mHandler.removeMessages(5013);
            this.knoxStateMonitor.mHandler.sendEmptyMessage(5013);
        }
    }

    @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
    public final void setQuickPanelItems(String str) {
        if ((str == null || str.equals(this.mQuickPanelItems)) && (str != null || this.mQuickPanelItems == null)) {
            return;
        }
        this.mQuickPanelItems = str;
        this.knoxStateMonitor.mHandler.removeMessages(5014);
        this.knoxStateMonitor.mHandler.sendEmptyMessage(5014);
    }

    @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
    public final void setQuickPanelUnavailableButtons(String str) {
        if ((str == null || str.equals(this.mQuickPanelUnavailableButtons)) && (str != null || this.mQuickPanelUnavailableButtons == null)) {
            return;
        }
        this.mQuickPanelUnavailableButtons = str;
        this.knoxStateMonitor.mHandler.removeMessages(5028);
        this.knoxStateMonitor.mHandler.sendEmptyMessage(5028);
    }

    @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
    public final void setScreenOffOnStatusBarDoubleTapState(boolean z) {
        this.mKnoxCustomDoubleTapState = z;
    }

    @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
    public final void setStatusBarHidden(boolean z) {
        if (this.mIsCustomSdkStatusBarHidden != z) {
            this.mIsCustomSdkStatusBarHidden = z;
            this.knoxStateMonitor.mHandler.removeMessages(5019);
            this.knoxStateMonitor.mHandler.sendEmptyMessage(5019);
        }
    }

    @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
    public final void setStatusBarIconsState(boolean z) {
        if (this.mStatusBarIconsState != z) {
            this.mStatusBarIconsState = z;
            this.knoxStateMonitor.mHandler.removeMessages(5017);
            this.knoxStateMonitor.mHandler.sendEmptyMessage(5017);
        }
    }

    @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
    public final void setStatusBarNotificationsState(boolean z) {
        if (this.mStatusBarNotificationsState != z) {
            this.mStatusBarNotificationsState = z;
        }
    }

    @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
    public final void setStatusBarTextInfo(String str, int i, int i2, int i3) {
        if ((str == null || str.equals(this.mStatusBarText)) && ((str != null || this.mStatusBarText == null) && this.mStatusBarTextStyle == i && this.mStatusBarTextSize == i2 && this.mStatusBarTextWidth == i3)) {
            return;
        }
        this.mStatusBarText = str;
        this.mStatusBarTextStyle = i;
        this.mStatusBarTextSize = i2;
        this.mStatusBarTextWidth = i3;
        this.knoxStateMonitor.mHandler.removeMessages(5015);
        this.knoxStateMonitor.mHandler.sendEmptyMessage(5015);
    }

    @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
    public final void setUnlockSimOnBootState(boolean z) {
        if (this.mKnoxCustomUnlockSimOnBootState != z) {
            this.mKnoxCustomUnlockSimOnBootState = z;
        }
    }

    @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
    public final void setUnlockSimPin(String str) {
        if ((str == null || str.equals(this.mUnlockSimPin)) && (str != null || this.mUnlockSimPin == null)) {
            return;
        }
        this.mUnlockSimPin = str;
    }

    @Override // com.samsung.android.knox.custom.IKnoxCustomManagerSystemUiCallback
    public final void setVolumePanelEnabledState(boolean z) {
        if (this.mVolumePanelEnabledState != z) {
            this.mVolumePanelEnabledState = z;
        }
    }
}
