package android.hardware.scontext;

import android.os.Bundle;
import android.util.Log;
import java.util.ArrayList;

@Deprecated
/* loaded from: classes2.dex */
public class SContextExerciseAttribute extends SContextAttribute {
    private static final String TAG = "SContextExerciseAttribute";
    private int[] mRequiredDataType;
    private static int REQUIRED_DATA_GPS = 1;
    private static int REQUIRED_DATA_BAROMETER = 2;
    private static int REQUIRED_DATA_PEDOMETER = 4;

    SContextExerciseAttribute() {
        this.mRequiredDataType = new int[]{1};
        setAttribute();
    }

    public SContextExerciseAttribute(int[] requiredDataType) {
        this.mRequiredDataType = new int[]{1};
        this.mRequiredDataType = requiredDataType;
        setAttribute();
    }

    @Override // android.hardware.scontext.SContextAttribute, com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < this.mRequiredDataType.length; i++) {
            if (this.mRequiredDataType[i] < 1 || this.mRequiredDataType[i] > 3) {
                Log.e(TAG, "The required data type is wrong.");
                return false;
            }
            list.add(Integer.valueOf(this.mRequiredDataType[i]));
            for (int j = 0; j < i; j++) {
                if (list.get(i) == list.get(j)) {
                    Log.e(TAG, "This required data type cannot have duplicated type.");
                    return false;
                }
            }
        }
        return true;
    }

    private void setAttribute() {
        int result = 0;
        StringBuffer sb = new StringBuffer();
        sb.append("Required data type : ");
        for (int i = 0; i < this.mRequiredDataType.length; i++) {
            int required_data = 0;
            if (i != 0) {
                sb.append(", ");
            }
            switch (this.mRequiredDataType[i]) {
                case 1:
                    required_data = REQUIRED_DATA_GPS;
                    sb.append("GPS");
                    break;
                case 2:
                    required_data = REQUIRED_DATA_BAROMETER;
                    sb.append("Barometer");
                    break;
                case 3:
                    required_data = REQUIRED_DATA_PEDOMETER;
                    sb.append("Pedometer");
                    break;
            }
            result |= required_data;
        }
        Log.d(TAG, sb.toString());
        Bundle attribute = new Bundle();
        attribute.putInt("required_data_type", result);
        super.setAttribute(40, attribute);
    }
}
