package android.hardware.scontext;

import android.os.Bundle;
import android.util.Log;
import java.util.ArrayList;

@Deprecated
/* loaded from: classes2.dex */
public class SContextExerciseAttribute extends SContextAttribute {
    private static int REQUIRED_DATA_BAROMETER = 2;
    private static int REQUIRED_DATA_GPS = 1;
    private static int REQUIRED_DATA_PEDOMETER = 4;
    private static final String TAG = "SContextExerciseAttribute";
    private int[] mRequiredDataType;

    SContextExerciseAttribute() {
        this.mRequiredDataType = new int[]{1};
        setAttribute();
    }

    public SContextExerciseAttribute(int[] iArr) {
        this.mRequiredDataType = iArr;
        setAttribute();
    }

    @Override // android.hardware.scontext.SContextAttribute, com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (true) {
            int[] iArr = this.mRequiredDataType;
            if (i >= iArr.length) {
                return true;
            }
            int i2 = iArr[i];
            if (i2 < 1 || i2 > 3) {
                break;
            }
            arrayList.add(Integer.valueOf(i2));
            for (int i3 = 0; i3 < i; i3++) {
                if (arrayList.get(i) == arrayList.get(i3)) {
                    Log.e(TAG, "This required data type cannot have duplicated type.");
                    return false;
                }
            }
            i++;
        }
        Log.e(TAG, "The required data type is wrong.");
        return false;
    }

    private void setAttribute() {
        int i;
        StringBuffer stringBuffer = new StringBuffer("Required data type : ");
        int i2 = 0;
        for (int i3 = 0; i3 < this.mRequiredDataType.length; i3++) {
            if (i3 != 0) {
                stringBuffer.append(", ");
            }
            int i4 = this.mRequiredDataType[i3];
            if (i4 == 1) {
                i = REQUIRED_DATA_GPS;
                stringBuffer.append("GPS");
            } else if (i4 == 2) {
                i = REQUIRED_DATA_BAROMETER;
                stringBuffer.append("Barometer");
            } else if (i4 != 3) {
                i = 0;
            } else {
                i = REQUIRED_DATA_PEDOMETER;
                stringBuffer.append("Pedometer");
            }
            i2 |= i;
        }
        Log.d(TAG, stringBuffer.toString());
        Bundle bundle = new Bundle();
        bundle.putInt("required_data_type", i2);
        super.setAttribute(40, bundle);
    }
}
