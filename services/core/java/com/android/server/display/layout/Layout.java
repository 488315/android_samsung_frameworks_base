package com.android.server.display.layout;

import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Slog;
import android.view.DisplayAddress;

import com.android.server.display.LogicalDisplayMapper$$ExternalSyntheticLambda2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class Layout {
    public final List mDisplays = new ArrayList(2);

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Display {
        public final DisplayAddress mAddress;
        public final String mDisplayGroupName;
        public final boolean mIsEnabled;
        public final DisplayAddress mLeadDisplayAddress;
        public int mLeadDisplayId = -1;
        public final int mLogicalDisplayId;
        public final int mPosition;
        public final String mPowerThrottlingMapId;
        public final String mRefreshRateZoneId;
        public final String mThermalBrightnessThrottlingMapId;
        public final String mThermalRefreshRateThrottlingMapId;

        public Display(
                DisplayAddress displayAddress,
                int i,
                boolean z,
                String str,
                String str2,
                int i2,
                DisplayAddress displayAddress2,
                String str3,
                String str4,
                String str5) {
            this.mAddress = displayAddress;
            this.mLogicalDisplayId = i;
            this.mIsEnabled = z;
            this.mDisplayGroupName = str;
            this.mPosition = i2;
            this.mThermalBrightnessThrottlingMapId = str2;
            this.mLeadDisplayAddress = displayAddress2;
            this.mRefreshRateZoneId = str3;
            this.mThermalRefreshRateThrottlingMapId = str4;
            this.mPowerThrottlingMapId = str5;
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof Display)) {
                return false;
            }
            Display display = (Display) obj;
            return display.mIsEnabled == this.mIsEnabled
                    && display.mPosition == this.mPosition
                    && display.mLogicalDisplayId == this.mLogicalDisplayId
                    && this.mDisplayGroupName.equals(display.mDisplayGroupName)
                    && this.mAddress.equals(display.mAddress)
                    && Objects.equals(
                            this.mThermalBrightnessThrottlingMapId,
                            display.mThermalBrightnessThrottlingMapId)
                    && Objects.equals(display.mRefreshRateZoneId, this.mRefreshRateZoneId)
                    && this.mLeadDisplayId == display.mLeadDisplayId
                    && Objects.equals(this.mLeadDisplayAddress, display.mLeadDisplayAddress)
                    && Objects.equals(
                            this.mThermalRefreshRateThrottlingMapId,
                            display.mThermalRefreshRateThrottlingMapId)
                    && Objects.equals(this.mPowerThrottlingMapId, display.mPowerThrottlingMapId);
        }

        public final int hashCode() {
            return Objects.hashCode(this.mPowerThrottlingMapId)
                    + ((Objects.hashCode(this.mThermalRefreshRateThrottlingMapId)
                                    + ((Objects.hashCode(this.mLeadDisplayAddress)
                                                    + ((((Objects.hashCode(this.mRefreshRateZoneId)
                                                                                    + ((Objects
                                                                                                            .hashCode(
                                                                                                                    this
                                                                                                                            .mThermalBrightnessThrottlingMapId)
                                                                                                    + ((this
                                                                                                                            .mAddress
                                                                                                                            .hashCode()
                                                                                                                    + ((this
                                                                                                                                            .mDisplayGroupName
                                                                                                                                            .hashCode()
                                                                                                                                    + ((((((Boolean
                                                                                                                                                                                            .hashCode(
                                                                                                                                                                                                    this
                                                                                                                                                                                                            .mIsEnabled)
                                                                                                                                                                                    + 31)
                                                                                                                                                                            * 31)
                                                                                                                                                                    + this
                                                                                                                                                                            .mPosition)
                                                                                                                                                            * 31)
                                                                                                                                                    + this
                                                                                                                                                            .mLogicalDisplayId)
                                                                                                                                            * 31))
                                                                                                                            * 31))
                                                                                                            * 31))
                                                                                            * 31))
                                                                            * 31)
                                                                    + this.mLeadDisplayId)
                                                            * 31))
                                            * 31))
                            * 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("{dispId: ");
            sb.append(this.mLogicalDisplayId);
            sb.append("(");
            sb.append(this.mIsEnabled ? "ON" : "OFF");
            sb.append("), displayGroupName: ");
            sb.append(this.mDisplayGroupName);
            sb.append(", addr: ");
            sb.append(this.mAddress);
            int i = this.mPosition;
            sb.append(
                    i == -1
                            ? ""
                            : VibrationParam$1$$ExternalSyntheticOutline0.m(i, ", position: "));
            sb.append(", mThermalBrightnessThrottlingMapId: ");
            sb.append(this.mThermalBrightnessThrottlingMapId);
            sb.append(", mRefreshRateZoneId: ");
            sb.append(this.mRefreshRateZoneId);
            sb.append(", mLeadDisplayId: ");
            sb.append(this.mLeadDisplayId);
            sb.append(", mLeadDisplayAddress: ");
            sb.append(this.mLeadDisplayAddress);
            sb.append(", mThermalRefreshRateThrottlingMapId: ");
            sb.append(this.mThermalRefreshRateThrottlingMapId);
            sb.append(", mPowerThrottlingMapId: ");
            return AudioOffloadInfo$$ExternalSyntheticOutline0.m(
                    sb, this.mPowerThrottlingMapId, "}");
        }
    }

    public final void createDisplayLocked(
            DisplayAddress displayAddress,
            boolean z,
            boolean z2,
            String str,
            LogicalDisplayMapper$$ExternalSyntheticLambda2
                    logicalDisplayMapper$$ExternalSyntheticLambda2,
            int i,
            DisplayAddress displayAddress2,
            String str2,
            String str3,
            String str4,
            String str5) {
        if (getByAddress(displayAddress) != null) {
            Slog.w(
                    "Layout",
                    "Attempting to add second definition for display-device: " + displayAddress);
            return;
        }
        if (z && getById(0) != null) {
            Slog.w("Layout", "Ignoring attempt to add a second default display: " + displayAddress);
            return;
        }
        String str6 = str == null ? "" : str;
        if (z && !str6.equals("")) {
            throw new IllegalArgumentException("Default display should own DEFAULT_DISPLAY_GROUP");
        }
        if (z && displayAddress2 != null) {
            throw new IllegalArgumentException("Default display cannot have a lead display");
        }
        if (displayAddress.equals(displayAddress2)) {
            throw new IllegalArgumentException(
                    "Lead display address cannot be the same as display  address");
        }
        ((ArrayList) this.mDisplays)
                .add(
                        new Display(
                                displayAddress,
                                !z ? 1 : logicalDisplayMapper$$ExternalSyntheticLambda2.getId(z),
                                z2,
                                str6,
                                str2,
                                i,
                                displayAddress2,
                                str3,
                                str4,
                                str5));
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof Layout)) {
            return false;
        }
        return ((ArrayList) this.mDisplays).equals(((Layout) obj).mDisplays);
    }

    public final Display getByAddress(DisplayAddress displayAddress) {
        for (int i = 0; i < ((ArrayList) this.mDisplays).size(); i++) {
            Display display = (Display) ((ArrayList) this.mDisplays).get(i);
            if (displayAddress.equals(display.mAddress)
                    || DisplayAddress.Physical.isPortMatch(displayAddress, display.mAddress)) {
                return display;
            }
        }
        return null;
    }

    public final Display getById(int i) {
        for (int i2 = 0; i2 < ((ArrayList) this.mDisplays).size(); i2++) {
            Display display = (Display) ((ArrayList) this.mDisplays).get(i2);
            if (i == display.mLogicalDisplayId) {
                return display;
            }
        }
        return null;
    }

    public final int hashCode() {
        return ((ArrayList) this.mDisplays).hashCode();
    }

    public final void postProcessLocked() {
        for (int i = 0; i < ((ArrayList) this.mDisplays).size(); i++) {
            Display display = (Display) ((ArrayList) this.mDisplays).get(i);
            if (display.mLogicalDisplayId == 0) {
                display.mLeadDisplayId = -1;
            } else {
                DisplayAddress displayAddress = display.mLeadDisplayAddress;
                if (displayAddress == null) {
                    display.mLeadDisplayId = -1;
                } else {
                    Display byAddress = getByAddress(displayAddress);
                    if (byAddress == null) {
                        throw new IllegalArgumentException(
                                "Cannot find a lead display whose address is " + displayAddress);
                    }
                    if (!TextUtils.equals(display.mDisplayGroupName, byAddress.mDisplayGroupName)) {
                        throw new IllegalArgumentException(
                                "Lead display("
                                        + byAddress
                                        + ") should be in the same display group of the display("
                                        + display
                                        + ")");
                    }
                    ArraySet arraySet = new ArraySet();
                    Display display2 = display;
                    while (display2 != null) {
                        if (arraySet.contains(display2)) {
                            throw new IllegalArgumentException(
                                    "Display(" + display + ") has a cyclic lead display");
                        }
                        arraySet.add(display2);
                        DisplayAddress displayAddress2 = display2.mLeadDisplayAddress;
                        display2 = displayAddress2 == null ? null : getByAddress(displayAddress2);
                    }
                    display.mLeadDisplayId = byAddress.mLogicalDisplayId;
                }
            }
        }
    }

    public final String toString() {
        return this.mDisplays.toString();
    }
}
