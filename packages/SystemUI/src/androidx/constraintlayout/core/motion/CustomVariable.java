package androidx.constraintlayout.core.motion;

import androidx.compose.runtime.OpaqueKey$$ExternalSyntheticOutline0;
import androidx.compose.ui.autofill.PopulateViewStructure_androidKt$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.samsung.android.knox.net.vpn.VpnErrorValues;

/* loaded from: classes.dex */
public class CustomVariable {
    public boolean mBooleanValue;
    public float mFloatValue;
    public int mIntegerValue;
    public final String mName;
    public String mStringValue;
    public final int mType;

    public CustomVariable(CustomVariable customVariable) {
        this.mIntegerValue = Integer.MIN_VALUE;
        this.mFloatValue = Float.NaN;
        this.mStringValue = null;
        this.mName = customVariable.mName;
        this.mType = customVariable.mType;
        this.mIntegerValue = customVariable.mIntegerValue;
        this.mFloatValue = customVariable.mFloatValue;
        this.mStringValue = customVariable.mStringValue;
        this.mBooleanValue = customVariable.mBooleanValue;
    }

    public final void setValue(Object obj) {
        switch (this.mType) {
            case 900:
            case 906:
                this.mIntegerValue = ((Integer) obj).intValue();
                break;
            case 901:
                this.mFloatValue = ((Float) obj).floatValue();
                break;
            case VpnErrorValues.ERROR_USB_TETHERING_FAILED /* 902 */:
                this.mIntegerValue = ((Integer) obj).intValue();
                break;
            case 903:
                this.mStringValue = (String) obj;
                break;
            case 904:
                this.mBooleanValue = ((Boolean) obj).booleanValue();
                break;
            case 905:
                this.mFloatValue = ((Float) obj).floatValue();
                break;
        }
    }

    public final String toString() {
        String strM = OpaqueKey$$ExternalSyntheticOutline0.m(new StringBuilder(), this.mName, ':');
        switch (this.mType) {
            case 900:
                StringBuilder sbM = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(strM);
                sbM.append(this.mIntegerValue);
                return sbM.toString();
            case 901:
                StringBuilder sbM2 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(strM);
                sbM2.append(this.mFloatValue);
                return sbM2.toString();
            case VpnErrorValues.ERROR_USB_TETHERING_FAILED /* 902 */:
                StringBuilder sbM3 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(strM);
                sbM3.append("#" + ("00000000" + Integer.toHexString(this.mIntegerValue)).substring(r3.length() - 8));
                return sbM3.toString();
            case 903:
                StringBuilder sbM4 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(strM);
                sbM4.append(this.mStringValue);
                return sbM4.toString();
            case 904:
                StringBuilder sbM5 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(strM);
                sbM5.append(Boolean.valueOf(this.mBooleanValue));
                return sbM5.toString();
            case 905:
                StringBuilder sbM6 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(strM);
                sbM6.append(this.mFloatValue);
                return sbM6.toString();
            default:
                return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(strM, "????");
        }
    }

    public CustomVariable(String str, int i, String str2) {
        this.mIntegerValue = Integer.MIN_VALUE;
        this.mFloatValue = Float.NaN;
        this.mName = str;
        this.mType = i;
        this.mStringValue = str2;
    }

    public CustomVariable(String str, int i, int i2) {
        this.mIntegerValue = Integer.MIN_VALUE;
        this.mFloatValue = Float.NaN;
        this.mStringValue = null;
        this.mName = str;
        this.mType = i;
        if (i == 901) {
            this.mFloatValue = i2;
        } else {
            this.mIntegerValue = i2;
        }
    }

    public CustomVariable(String str, int i, float f) {
        this.mIntegerValue = Integer.MIN_VALUE;
        this.mStringValue = null;
        this.mName = str;
        this.mType = i;
        this.mFloatValue = f;
    }

    public CustomVariable(String str, int i, boolean z) {
        this.mIntegerValue = Integer.MIN_VALUE;
        this.mFloatValue = Float.NaN;
        this.mStringValue = null;
        this.mName = str;
        this.mType = i;
        this.mBooleanValue = z;
    }

    public CustomVariable(String str, int i) {
        this.mIntegerValue = Integer.MIN_VALUE;
        this.mFloatValue = Float.NaN;
        this.mStringValue = null;
        this.mName = str;
        this.mType = i;
    }

    public CustomVariable(String str, int i, Object obj) {
        this.mIntegerValue = Integer.MIN_VALUE;
        this.mFloatValue = Float.NaN;
        this.mStringValue = null;
        this.mName = str;
        this.mType = i;
        setValue(obj);
    }

    public CustomVariable(CustomVariable customVariable, Object obj) {
        this.mIntegerValue = Integer.MIN_VALUE;
        this.mFloatValue = Float.NaN;
        this.mStringValue = null;
        this.mName = customVariable.mName;
        this.mType = customVariable.mType;
        setValue(obj);
    }
}
