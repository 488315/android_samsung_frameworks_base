package com.android.internal.graphics.palette;

import com.android.internal.graphics.palette.Palette;
import java.util.List;

/* loaded from: classes5.dex */
public class CelebiQuantizer implements Quantizer {
    private List<Palette.Swatch> mSwatches;

    @Override // com.android.internal.graphics.palette.Quantizer
    public void quantize(int[] iArr, int i) {
        WuQuantizer wuQuantizer = new WuQuantizer();
        wuQuantizer.quantize(iArr, i);
        WSMeansQuantizer wSMeansQuantizer = new WSMeansQuantizer(wuQuantizer.getColors(), new LABPointProvider(), wuQuantizer.inputPixelToCount());
        wSMeansQuantizer.quantize(iArr, i);
        this.mSwatches = wSMeansQuantizer.getQuantizedColors();
    }

    @Override // com.android.internal.graphics.palette.Quantizer
    public List<Palette.Swatch> getQuantizedColors() {
        return this.mSwatches;
    }
}
