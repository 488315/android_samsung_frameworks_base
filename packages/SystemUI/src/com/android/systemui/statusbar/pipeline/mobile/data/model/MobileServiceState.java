package com.android.systemui.statusbar.pipeline.mobile.data.model;

import android.telephony.TelephonyDisplayInfo;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import com.android.systemui.log.table.Diffable;
import com.android.systemui.log.table.TableLogBuffer;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class MobileServiceState implements Diffable {
    public final int dataRegState;
    public final int dataRoamingType;
    public final int mSimSubmode;
    public final int optionalRadioTech;
    public final SimCardModel simCardInfo;
    public final TelephonyDisplayInfo telephonyDisplayInfo;
    public final boolean vioceCallAvailable;
    public final int voiceNetworkType;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        this(0, false, 0, 0, 0, 0, null, null, 255, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MobileServiceState)) {
            return false;
        }
        MobileServiceState mobileServiceState = (MobileServiceState) obj;
        return this.optionalRadioTech == mobileServiceState.optionalRadioTech && this.vioceCallAvailable == mobileServiceState.vioceCallAvailable && this.dataRegState == mobileServiceState.dataRegState && this.dataRoamingType == mobileServiceState.dataRoamingType && this.voiceNetworkType == mobileServiceState.voiceNetworkType && this.mSimSubmode == mobileServiceState.mSimSubmode && Intrinsics.areEqual(this.telephonyDisplayInfo, mobileServiceState.telephonyDisplayInfo) && Intrinsics.areEqual(this.simCardInfo, mobileServiceState.simCardInfo);
    }

    public final int hashCode() {
        return this.simCardInfo.hashCode() + ((this.telephonyDisplayInfo.hashCode() + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.mSimSubmode, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.voiceNetworkType, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.dataRoamingType, KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.dataRegState, TransitionData$$ExternalSyntheticOutline0.m(Integer.hashCode(this.optionalRadioTech) * 31, 31, this.vioceCallAvailable), 31), 31), 31), 31)) * 31);
    }

    @Override // com.android.systemui.log.table.Diffable
    public final void logDiffs(Object obj, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
        MobileServiceState mobileServiceState = (MobileServiceState) obj;
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
        int i7 = mobileServiceState.voiceNetworkType;
        int i8 = this.voiceNetworkType;
        if (i7 != i8) {
            tableRowLoggerImpl.logChange(i8, "voiceNetworkType");
        }
        int i9 = mobileServiceState.mSimSubmode;
        int i10 = this.mSimSubmode;
        if (i9 != i10) {
            tableRowLoggerImpl.logChange(i10, "mSimSubmode");
        }
        SimCardModel simCardModel = mobileServiceState.simCardInfo;
        SimCardModel simCardModel2 = this.simCardInfo;
        if (Intrinsics.areEqual(simCardModel, simCardModel2)) {
            return;
        }
        tableRowLoggerImpl.logChange("simCard", simCardModel2.simType.toString());
    }

    public final String toString() {
        return "MobileServiceState(optionalRadioTech=" + this.optionalRadioTech + ", vioceCallAvailable=" + this.vioceCallAvailable + ", dataRegState=" + this.dataRegState + ", dataRoamingType=" + this.dataRoamingType + ", voiceNetworkType=" + this.voiceNetworkType + ", mSimSubmode=" + this.mSimSubmode + ", telephonyDisplayInfo=" + this.telephonyDisplayInfo + ", simCardInfo=" + this.simCardInfo + ")";
    }

    public MobileServiceState(int i, boolean z, int i2, int i3, int i4, int i5, TelephonyDisplayInfo telephonyDisplayInfo, SimCardModel simCardModel) {
        this.optionalRadioTech = i;
        this.vioceCallAvailable = z;
        this.dataRegState = i2;
        this.dataRoamingType = i3;
        this.voiceNetworkType = i4;
        this.mSimSubmode = i5;
        this.telephonyDisplayInfo = telephonyDisplayInfo;
        this.simCardInfo = simCardModel;
    }

    public MobileServiceState(int i, boolean z, int i2, int i3, int i4, int i5, TelephonyDisplayInfo telephonyDisplayInfo, SimCardModel simCardModel, int i6, DefaultConstructorMarker defaultConstructorMarker) {
        this((i6 & 1) != 0 ? 0 : i, (i6 & 2) != 0 ? false : z, (i6 & 4) != 0 ? 0 : i2, (i6 & 8) != 0 ? 0 : i3, (i6 & 16) != 0 ? 0 : i4, (i6 & 32) != 0 ? 0 : i5, (i6 & 64) != 0 ? new TelephonyDisplayInfo(0, 0) : telephonyDisplayInfo, (i6 & 128) != 0 ? SimCardModelKt.NO_SIM_MODEL : simCardModel);
    }
}
