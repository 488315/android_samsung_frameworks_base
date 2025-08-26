package com.google.ux.material.libmonet.temperature;

import com.google.ux.material.libmonet.hct.Hct;
import com.google.ux.material.libmonet.utils.ColorUtils;
import com.google.ux.material.libmonet.utils.MathUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/* loaded from: classes4.dex */
public final class TemperatureCache {
    public final Hct input;
    public List precomputedHctsByHue;
    public List precomputedHctsByTemp;
    public Map precomputedTempsByHct;

    private TemperatureCache() {
        throw new UnsupportedOperationException();
    }

    public final List getHctsByHue() {
        List list = this.precomputedHctsByHue;
        if (list != null) {
            return list;
        }
        ArrayList arrayList = new ArrayList();
        for (double d = 0.0d; d <= 360.0d; d += 1.0d) {
            Hct hct = this.input;
            arrayList.add(Hct.from(d, hct.chroma, hct.tone));
        }
        List listUnmodifiableList = Collections.unmodifiableList(arrayList);
        this.precomputedHctsByHue = listUnmodifiableList;
        return listUnmodifiableList;
    }

    public final List getHctsByTemp() {
        List list = this.precomputedHctsByTemp;
        if (list != null) {
            return list;
        }
        ArrayList arrayList = new ArrayList(getHctsByHue());
        arrayList.add(this.input);
        Collections.sort(arrayList, Comparator.comparing(new Function() { // from class: com.google.ux.material.libmonet.temperature.TemperatureCache$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return (Double) ((HashMap) this.f$0.getTempsByHct()).get((Hct) obj);
            }
        }, new TemperatureCache$$ExternalSyntheticLambda1()));
        this.precomputedHctsByTemp = arrayList;
        return arrayList;
    }

    public final double getRelativeTemperature(Hct hct) {
        double dDoubleValue = ((Double) ((HashMap) getTempsByHct()).get((Hct) ((ArrayList) getHctsByTemp()).get(((ArrayList) getHctsByTemp()).size() - 1))).doubleValue() - ((Double) ((HashMap) getTempsByHct()).get((Hct) ((ArrayList) getHctsByTemp()).get(0))).doubleValue();
        double dDoubleValue2 = ((Double) ((HashMap) getTempsByHct()).get(hct)).doubleValue() - ((Double) ((HashMap) getTempsByHct()).get((Hct) ((ArrayList) getHctsByTemp()).get(0))).doubleValue();
        if (dDoubleValue == 0.0d) {
            return 0.5d;
        }
        return dDoubleValue2 / dDoubleValue;
    }

    public final Map getTempsByHct() {
        Map map = this.precomputedTempsByHct;
        if (map != null) {
            return map;
        }
        ArrayList arrayList = new ArrayList(getHctsByHue());
        arrayList.add(this.input);
        HashMap map2 = new HashMap();
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            Hct hct = (Hct) obj;
            int i2 = hct.argb;
            double dLinearized = ColorUtils.linearized((i2 >> 16) & 255);
            double dLinearized2 = ColorUtils.linearized((i2 >> 8) & 255);
            double dLinearized3 = ColorUtils.linearized(i2 & 255);
            double[][] dArr = ColorUtils.SRGB_TO_XYZ;
            double[] dArr2 = dArr[0];
            double d = (dArr2[2] * dLinearized3) + (dArr2[1] * dLinearized2) + (dArr2[0] * dLinearized);
            double[] dArr3 = dArr[1];
            double d2 = (dArr3[2] * dLinearized3) + (dArr3[1] * dLinearized2) + (dArr3[0] * dLinearized);
            double[] dArr4 = dArr[2];
            double d3 = (dArr4[2] * dLinearized3) + (dArr4[1] * dLinearized2) + (dArr4[0] * dLinearized);
            double[] dArr5 = ColorUtils.WHITE_POINT_D65;
            double d4 = d / dArr5[0];
            double d5 = d2 / dArr5[1];
            double d6 = d3 / dArr5[2];
            double dLabF = ColorUtils.labF(d4);
            double dLabF2 = ColorUtils.labF(d5);
            double[] dArr6 = {(116.0d * dLabF2) - 16.0d, (dLabF - dLabF2) * 500.0d, (dLabF2 - ColorUtils.labF(d6)) * 200.0d};
            map2.put(hct, Double.valueOf((Math.cos(Math.toRadians(MathUtils.sanitizeDegreesDouble(MathUtils.sanitizeDegreesDouble(Math.toDegrees(Math.atan2(dArr6[2], dArr6[1]))) - 50.0d))) * (Math.pow(Math.hypot(dArr6[1], dArr6[2]), 1.07d) * 0.02d)) - 0.5d));
        }
        this.precomputedTempsByHct = map2;
        return map2;
    }

    public TemperatureCache(Hct hct) {
        this.input = hct;
    }
}
