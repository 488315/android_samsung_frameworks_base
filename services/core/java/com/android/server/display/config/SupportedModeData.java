package com.android.server.display.config;

import java.util.ArrayList;
import java.util.List;

public final class SupportedModeData {
    public final float refreshRate;
    public final float vsyncRate;

    public SupportedModeData(float f, float f2) {
        this.refreshRate = f;
        this.vsyncRate = f2;
    }

    public static List load(NonNegativeFloatToFloatMap nonNegativeFloatToFloatMap) {
        ArrayList arrayList = new ArrayList();
        if (nonNegativeFloatToFloatMap != null) {
            for (NonNegativeFloatToFloatPoint nonNegativeFloatToFloatPoint :
                    nonNegativeFloatToFloatMap.getPoint()) {
                arrayList.add(
                        new SupportedModeData(
                                nonNegativeFloatToFloatPoint.first.floatValue(),
                                nonNegativeFloatToFloatPoint.second.floatValue()));
            }
        }
        return arrayList;
    }

    public final String toString() {
        return "SupportedModeData{refreshRate= "
                + this.refreshRate
                + ", vsyncRate= "
                + this.vsyncRate
                + '}';
    }
}
