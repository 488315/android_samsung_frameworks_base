package com.android.systemui.statusbar.pipeline.mobile.data.model;

import android.telephony.TelephonyDisplayInfo;
import androidx.picker.model.viewdata.AppInfoViewData$$ExternalSyntheticOutline0;
import com.android.systemui.log.table.Diffable;
import com.android.systemui.log.table.TableLogBuffer;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MobileServiceState implements Diffable {
    public final int dataRegState;
    public final int dataRoamingType;
    public final int femtoCellIndicator;
    public final int mSimSubmode;
    public final int optionalRadioTech;
    public final SimCardModel simCardInfo;
    public final TelephonyDisplayInfo telephonyDisplayInfo;
    public final boolean vioceCallAvailable;
    public final int voiceNetworkType;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public MobileServiceState() {
        this(0, false, 0, 0, 0, 0, 0, null, null, 511, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MobileServiceState)) {
            return false;
        }
        MobileServiceState mobileServiceState = (MobileServiceState) obj;
        return this.optionalRadioTech == mobileServiceState.optionalRadioTech && this.vioceCallAvailable == mobileServiceState.vioceCallAvailable && this.dataRegState == mobileServiceState.dataRegState && this.dataRoamingType == mobileServiceState.dataRoamingType && this.femtoCellIndicator == mobileServiceState.femtoCellIndicator && this.voiceNetworkType == mobileServiceState.voiceNetworkType && this.mSimSubmode == mobileServiceState.mSimSubmode && Intrinsics.areEqual(this.telephonyDisplayInfo, mobileServiceState.telephonyDisplayInfo) && Intrinsics.areEqual(this.simCardInfo, mobileServiceState.simCardInfo);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        int hashCode = Integer.hashCode(this.optionalRadioTech) * 31;
        boolean z = this.vioceCallAvailable;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        return this.simCardInfo.hashCode() + ((this.telephonyDisplayInfo.hashCode() + AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.mSimSubmode, AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.voiceNetworkType, AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.femtoCellIndicator, AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.dataRoamingType, AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.dataRegState, (hashCode + i) * 31, 31), 31), 31), 31), 31)) * 31);
    }

    @Override // com.android.systemui.log.table.Diffable
    public final void logDiffs(Diffable diffable, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
        MobileServiceState mobileServiceState = (MobileServiceState) diffable;
        int i = mobileServiceState.optionalRadioTech;
        int i2 = this.optionalRadioTech;
        if (i != i2) {
            tableRowLoggerImpl.logChange(i2, "optionalRadioTech");
        }
        boolean z = mobileServiceState.vioceCallAvailable;
        boolean z2 = this.vioceCallAvailable;
        if (z != z2) {
            tableRowLoggerImpl.logChange("voiceCallAvailable", z2);
        }
        int i3 = mobileServiceState.dataRegState;
        int i4 = this.dataRegState;
        if (i3 != i4) {
            tableRowLoggerImpl.logChange(i4, "dataRegtate");
        }
        int i5 = mobileServiceState.dataRoamingType;
        int i6 = this.dataRoamingType;
        if (i5 != i6) {
            tableRowLoggerImpl.logChange(i6, "dataRoaming");
        }
        int i7 = mobileServiceState.femtoCellIndicator;
        int i8 = this.femtoCellIndicator;
        if (i7 != i8) {
            tableRowLoggerImpl.logChange(i8, "getFemtoCall");
        }
        int i9 = mobileServiceState.voiceNetworkType;
        int i10 = this.voiceNetworkType;
        if (i9 != i10) {
            tableRowLoggerImpl.logChange(i10, "voiceNetworkType");
        }
        int i11 = mobileServiceState.mSimSubmode;
        int i12 = this.mSimSubmode;
        if (i11 != i12) {
            tableRowLoggerImpl.logChange(i12, "mSimSubmode");
        }
        SimCardModel simCardModel = mobileServiceState.simCardInfo;
        SimCardModel simCardModel2 = this.simCardInfo;
        if (Intrinsics.areEqual(simCardModel, simCardModel2)) {
            return;
        }
        tableRowLoggerImpl.logChange("simCard", simCardModel2.simType.toString());
    }

    public final String toString() {
        return "MobileServiceState(optionalRadioTech=" + this.optionalRadioTech + ", vioceCallAvailable=" + this.vioceCallAvailable + ", dataRegState=" + this.dataRegState + ", dataRoamingType=" + this.dataRoamingType + ", femtoCellIndicator=" + this.femtoCellIndicator + ", voiceNetworkType=" + this.voiceNetworkType + ", mSimSubmode=" + this.mSimSubmode + ", telephonyDisplayInfo=" + this.telephonyDisplayInfo + ", simCardInfo=" + this.simCardInfo + ")";
    }

    public MobileServiceState(int i, boolean z, int i2, int i3, int i4, int i5, int i6, TelephonyDisplayInfo telephonyDisplayInfo, SimCardModel simCardModel) {
        this.optionalRadioTech = i;
        this.vioceCallAvailable = z;
        this.dataRegState = i2;
        this.dataRoamingType = i3;
        this.femtoCellIndicator = i4;
        this.voiceNetworkType = i5;
        this.mSimSubmode = i6;
        this.telephonyDisplayInfo = telephonyDisplayInfo;
        this.simCardInfo = simCardModel;
    }

    public MobileServiceState(int i, boolean z, int i2, int i3, int i4, int i5, int i6, TelephonyDisplayInfo telephonyDisplayInfo, SimCardModel simCardModel, int i7, DefaultConstructorMarker defaultConstructorMarker) {
        this((i7 & 1) != 0 ? 0 : i, (i7 & 2) != 0 ? false : z, (i7 & 4) != 0 ? 0 : i2, (i7 & 8) != 0 ? 0 : i3, (i7 & 16) != 0 ? 0 : i4, (i7 & 32) != 0 ? 0 : i5, (i7 & 64) != 0 ? 0 : i6, (i7 & 128) != 0 ? new TelephonyDisplayInfo(0, 0) : telephonyDisplayInfo, (i7 & 256) != 0 ? SimCardModelKt.NO_SIM_MODEL : simCardModel);
    }

    @Override // com.android.systemui.log.table.Diffable
    public final void logFull(TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
    }
}
