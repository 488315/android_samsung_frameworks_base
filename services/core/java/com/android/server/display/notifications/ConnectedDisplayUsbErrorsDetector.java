package com.android.server.display.notifications;

import android.content.Context;
import android.hardware.usb.DisplayPortAltModeInfo;
import android.hardware.usb.UsbManager;

import com.android.server.display.feature.DisplayManagerFlags;

public final class ConnectedDisplayUsbErrorsDetector
        implements UsbManager.DisplayPortAltModeInfoListener {
    public final Context mContext;
    public final Injector mInjector;
    public final boolean mIsConnectedDisplayErrorHandlingEnabled;
    public DisplayNotificationManager mListener;

    public interface Injector {}

    public ConnectedDisplayUsbErrorsDetector(
            DisplayManagerFlags displayManagerFlags, Context context, Injector injector) {
        this.mContext = context;
        this.mInjector = injector;
        this.mIsConnectedDisplayErrorHandlingEnabled =
                displayManagerFlags.mConnectedDisplayErrorHandlingFlagState.isEnabled();
    }

    public final void onDisplayPortAltModeInfoChanged(
            String str, DisplayPortAltModeInfo displayPortAltModeInfo) {
        if (this.mListener == null) {
            return;
        }
        if (2 == displayPortAltModeInfo.getPartnerSinkStatus()
                && 1 == displayPortAltModeInfo.getCableStatus()) {
            this.mListener.onCableNotCapableDisplayPort();
        } else if (2 == displayPortAltModeInfo.getLinkTrainingStatus()) {
            this.mListener.onDisplayPortLinkTrainingFailure();
        }
    }
}
