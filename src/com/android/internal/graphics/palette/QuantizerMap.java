package com.android.internal.graphics.palette;

import android.app.AppOpsManager$$ExternalSyntheticLambda4;
import com.android.internal.graphics.palette.Palette;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes5.dex */
public final class QuantizerMap implements Quantizer {
    private HashMap<Integer, Integer> mColorToCount;
    private Palette mPalette;

    @Override // com.android.internal.graphics.palette.Quantizer
    public void quantize(int[] iArr, int i) {
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (int i2 : iArr) {
            hashMap.merge(Integer.valueOf(i2), 1, new AppOpsManager$$ExternalSyntheticLambda4());
        }
        this.mColorToCount = hashMap;
        ArrayList arrayList = new ArrayList();
        for (Map.Entry<Integer, Integer> entry : hashMap.entrySet()) {
            arrayList.add(new Palette.Swatch(entry.getKey().intValue(), entry.getValue().intValue()));
        }
        this.mPalette = Palette.from(arrayList);
    }

    @Override // com.android.internal.graphics.palette.Quantizer
    public List<Palette.Swatch> getQuantizedColors() {
        return this.mPalette.getSwatches();
    }

    public Map<Integer, Integer> getColorToCount() {
        return this.mColorToCount;
    }
}
