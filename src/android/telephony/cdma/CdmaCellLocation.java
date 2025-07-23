package android.telephony.cdma;

import android.content.Intent;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Bundle;
import android.telephony.CellLocation;
import com.android.internal.telephony.SemTelephonyUtils;
import com.android.internal.telephony.util.TelephonyUtils;
import com.android.telephony.Rlog;

@Deprecated
/* loaded from: classes4.dex */
public class CdmaCellLocation extends CellLocation {
    public static final int INVALID_LAT_LONG = Integer.MAX_VALUE;
    private int mBaseStationId;
    private int mBaseStationLatitude;
    private int mBaseStationLongitude;
    private int mNetworkId;
    private int mSystemId;

    public CdmaCellLocation() {
        this.mBaseStationId = -1;
        this.mBaseStationLatitude = Integer.MAX_VALUE;
        this.mBaseStationLongitude = Integer.MAX_VALUE;
        this.mSystemId = -1;
        this.mNetworkId = -1;
    }

    public CdmaCellLocation(Bundle bundle) {
        this.mBaseStationId = -1;
        this.mBaseStationLatitude = Integer.MAX_VALUE;
        this.mBaseStationLongitude = Integer.MAX_VALUE;
        this.mSystemId = -1;
        this.mNetworkId = -1;
        this.mBaseStationId = bundle.getInt("baseStationId", -1);
        this.mBaseStationLatitude = bundle.getInt("baseStationLatitude", this.mBaseStationLatitude);
        this.mBaseStationLongitude = bundle.getInt("baseStationLongitude", this.mBaseStationLongitude);
        this.mSystemId = bundle.getInt(Intent.EXTRA_SYSTEM_ID, this.mSystemId);
        this.mNetworkId = bundle.getInt("networkId", this.mNetworkId);
    }

    public int getBaseStationId() {
        return this.mBaseStationId;
    }

    public int getBaseStationLatitude() {
        return this.mBaseStationLatitude;
    }

    public int getBaseStationLongitude() {
        return this.mBaseStationLongitude;
    }

    public int getSystemId() {
        return this.mSystemId;
    }

    public int getNetworkId() {
        return this.mNetworkId;
    }

    @Override // android.telephony.CellLocation
    public void setStateInvalid() {
        this.mBaseStationId = -1;
        this.mBaseStationLatitude = Integer.MAX_VALUE;
        this.mBaseStationLongitude = Integer.MAX_VALUE;
        this.mSystemId = -1;
        this.mNetworkId = -1;
    }

    public void setCellLocationData(int i, int i2, int i3) {
        this.mBaseStationId = i;
        this.mBaseStationLatitude = i2;
        this.mBaseStationLongitude = i3;
    }

    public void setCellLocationData(int i, int i2, int i3, int i4, int i5) {
        this.mBaseStationId = i;
        this.mBaseStationLatitude = i2;
        this.mBaseStationLongitude = i3;
        this.mSystemId = i4;
        this.mNetworkId = i5;
    }

    public int hashCode() {
        return this.mNetworkId ^ (((this.mBaseStationId ^ this.mBaseStationLatitude) ^ this.mBaseStationLongitude) ^ this.mSystemId);
    }

    public boolean equals(Object obj) {
        CdmaCellLocation cdmaCellLocation;
        try {
            cdmaCellLocation = (CdmaCellLocation) obj;
        } catch (ClassCastException unused) {
        }
        return obj != null && equalsHandlesNulls(Integer.valueOf(this.mBaseStationId), Integer.valueOf(cdmaCellLocation.mBaseStationId)) && equalsHandlesNulls(Integer.valueOf(this.mBaseStationLatitude), Integer.valueOf(cdmaCellLocation.mBaseStationLatitude)) && equalsHandlesNulls(Integer.valueOf(this.mBaseStationLongitude), Integer.valueOf(cdmaCellLocation.mBaseStationLongitude)) && equalsHandlesNulls(Integer.valueOf(this.mSystemId), Integer.valueOf(cdmaCellLocation.mSystemId)) && equalsHandlesNulls(Integer.valueOf(this.mNetworkId), Integer.valueOf(cdmaCellLocation.mNetworkId));
    }

    public String toString() {
        if (SemTelephonyUtils.SHIP_BUILD) {
            return "[<MASKED>]";
        }
        return NavigationBarInflaterView.SIZE_MOD_START + this.mBaseStationId + "," + Rlog.pii(TelephonyUtils.IS_DEBUGGABLE, Integer.valueOf(this.mBaseStationLatitude)) + "," + Rlog.pii(TelephonyUtils.IS_DEBUGGABLE, Integer.valueOf(this.mBaseStationLongitude)) + "," + this.mSystemId + "," + this.mNetworkId + NavigationBarInflaterView.SIZE_MOD_END;
    }

    private static boolean equalsHandlesNulls(Object obj, Object obj2) {
        if (obj == null) {
            return obj2 == null;
        }
        return obj.equals(obj2);
    }

    @Override // android.telephony.CellLocation
    public void fillInNotifierBundle(Bundle bundle) {
        bundle.putInt("baseStationId", this.mBaseStationId);
        bundle.putInt("baseStationLatitude", this.mBaseStationLatitude);
        bundle.putInt("baseStationLongitude", this.mBaseStationLongitude);
        bundle.putInt(Intent.EXTRA_SYSTEM_ID, this.mSystemId);
        bundle.putInt("networkId", this.mNetworkId);
    }

    @Override // android.telephony.CellLocation
    public boolean isEmpty() {
        return this.mBaseStationId == -1 && this.mBaseStationLatitude == Integer.MAX_VALUE && this.mBaseStationLongitude == Integer.MAX_VALUE && this.mSystemId == -1 && this.mNetworkId == -1;
    }

    public static double convertQuartSecToDecDegrees(int i) {
        double d = i;
        if (!Double.isNaN(d) && i >= -2592000 && i <= 2592000) {
            return d / 14400.0d;
        }
        throw new IllegalArgumentException("Invalid coordiante value:" + i);
    }
}
