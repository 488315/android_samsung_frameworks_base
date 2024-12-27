package com.android.systemui.controls.util;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import java.util.HashMap;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ControlsBackUpRestore$BNRResponse {
    public final ControlsBackUpRestore$BNRErrCode errCode;
    public final String exportSessionTime;
    public final HashMap extraErrCode;
    public final String intentAction;
    public final int reqSize;
    public final ControlsBackUpRestore$BNRResult result;
    public final String source;

    public ControlsBackUpRestore$BNRResponse(String str, ControlsBackUpRestore$BNRResult controlsBackUpRestore$BNRResult, ControlsBackUpRestore$BNRErrCode controlsBackUpRestore$BNRErrCode, int i, String str2, HashMap<String, Integer> hashMap, String str3) {
        this.intentAction = str;
        this.result = controlsBackUpRestore$BNRResult;
        this.errCode = controlsBackUpRestore$BNRErrCode;
        this.reqSize = i;
        this.source = str2;
        this.extraErrCode = hashMap;
        this.exportSessionTime = str3;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ControlsBackUpRestore$BNRResponse)) {
            return false;
        }
        ControlsBackUpRestore$BNRResponse controlsBackUpRestore$BNRResponse = (ControlsBackUpRestore$BNRResponse) obj;
        return Intrinsics.areEqual(this.intentAction, controlsBackUpRestore$BNRResponse.intentAction) && this.result == controlsBackUpRestore$BNRResponse.result && this.errCode == controlsBackUpRestore$BNRResponse.errCode && this.reqSize == controlsBackUpRestore$BNRResponse.reqSize && Intrinsics.areEqual(this.source, controlsBackUpRestore$BNRResponse.source) && Intrinsics.areEqual(this.extraErrCode, controlsBackUpRestore$BNRResponse.extraErrCode) && Intrinsics.areEqual(this.exportSessionTime, controlsBackUpRestore$BNRResponse.exportSessionTime);
    }

    public final int hashCode() {
        String str = this.intentAction;
        int m = KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.reqSize, (this.errCode.hashCode() + ((this.result.hashCode() + ((str == null ? 0 : str.hashCode()) * 31)) * 31)) * 31, 31);
        String str2 = this.source;
        int hashCode = (m + (str2 == null ? 0 : str2.hashCode())) * 31;
        HashMap hashMap = this.extraErrCode;
        int hashCode2 = (hashCode + (hashMap == null ? 0 : hashMap.hashCode())) * 31;
        String str3 = this.exportSessionTime;
        return hashCode2 + (str3 != null ? str3.hashCode() : 0);
    }

    public final String toString() {
        HashMap hashMap = this.extraErrCode;
        StringBuilder sb = new StringBuilder("BNRResponse(intentAction=");
        sb.append(this.intentAction);
        sb.append(", result=");
        sb.append(this.result);
        sb.append(", errCode=");
        sb.append(this.errCode);
        sb.append(", reqSize=");
        sb.append(this.reqSize);
        sb.append(", source=");
        sb.append(this.source);
        sb.append(", extraErrCode=");
        sb.append(hashMap);
        sb.append(", exportSessionTime=");
        return ComponentActivity$1$$ExternalSyntheticOutline0.m(sb, this.exportSessionTime, ")");
    }

    public /* synthetic */ ControlsBackUpRestore$BNRResponse(String str, ControlsBackUpRestore$BNRResult controlsBackUpRestore$BNRResult, ControlsBackUpRestore$BNRErrCode controlsBackUpRestore$BNRErrCode, int i, String str2, HashMap hashMap, String str3, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, controlsBackUpRestore$BNRResult, controlsBackUpRestore$BNRErrCode, i, str2, (i2 & 32) != 0 ? null : hashMap, (i2 & 64) != 0 ? null : str3);
    }
}
