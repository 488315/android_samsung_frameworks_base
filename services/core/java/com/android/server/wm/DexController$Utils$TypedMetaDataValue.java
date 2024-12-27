package com.android.server.wm;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class DexController$Utils$TypedMetaDataValue {
    public int data;
    public int unit;

    public static int getDimensionPixelSize(
            DexController$Utils$TypedMetaDataValue dexController$Utils$TypedMetaDataValue,
            int i,
            int i2,
            int i3) {
        int i4 = dexController$Utils$TypedMetaDataValue.data;
        if (i4 > 0) {
            i3 = i4;
        }
        int i5 = dexController$Utils$TypedMetaDataValue.unit;
        if (i5 != 0) {
            if (i5 == 1 || i5 == 2) {
                return (int) ((i3 * i * 0.00625f) + 0.5f);
            }
        } else if (i3 <= 100) {
            return (int) (i2 * i3 * 0.01d);
        }
        return i3;
    }

    public static DexController$Utils$TypedMetaDataValue parseSizeMetaData(String str) {
        DexController$Utils$TypedMetaDataValue dexController$Utils$TypedMetaDataValue =
                new DexController$Utils$TypedMetaDataValue();
        String[] split = str.split("\\D");
        if (split[0].isEmpty()) {
            split[0] = "0";
        }
        String upperCase = str.substring(str.lastIndexOf(split[0])).toUpperCase();
        if (upperCase.contains("%")) {
            dexController$Utils$TypedMetaDataValue.unit = 0;
        } else if (upperCase.contains("PX")) {
            dexController$Utils$TypedMetaDataValue.unit = 0;
        } else if (upperCase.contains("SP")) {
            dexController$Utils$TypedMetaDataValue.unit = 2;
        } else {
            dexController$Utils$TypedMetaDataValue.unit = 1;
        }
        dexController$Utils$TypedMetaDataValue.data = Integer.parseInt(split[0]);
        return dexController$Utils$TypedMetaDataValue;
    }
}
