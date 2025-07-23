package androidx.constraintlayout.core.motion;

import androidx.compose.runtime.OpaqueKey$$ExternalSyntheticOutline0;
import androidx.compose.ui.autofill.PopulateViewStructure_androidKt$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.samsung.android.knox.net.vpn.VpnErrorValues;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        String m = OpaqueKey$$ExternalSyntheticOutline0.m(new StringBuilder(), this.mName, ':');
        switch (this.mType) {
            case 900:
                StringBuilder m2 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(m);
                m2.append(this.mIntegerValue);
                return m2.toString();
            case 901:
                StringBuilder m3 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(m);
                m3.append(this.mFloatValue);
                return m3.toString();
            case VpnErrorValues.ERROR_USB_TETHERING_FAILED /* 902 */:
                StringBuilder m4 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(m);
                m4.append("#" + ("00000000" + Integer.toHexString(this.mIntegerValue)).substring(r3.length() - 8));
                return m4.toString();
            case 903:
                StringBuilder m5 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(m);
                m5.append(this.mStringValue);
                return m5.toString();
            case 904:
                StringBuilder m6 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(m);
                m6.append(Boolean.valueOf(this.mBooleanValue));
                return m6.toString();
            case 905:
                StringBuilder m7 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(m);
                m7.append(this.mFloatValue);
                return m7.toString();
            default:
                return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(m, "????");
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
