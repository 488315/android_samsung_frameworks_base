package com.android.internal.graphics.palette;

import java.util.List;

public class CelebiQuantizer implements Quantizer {
    private List<Palette.Swatch> mSwatches;

    @Override // com.android.internal.graphics.palette.Quantizer
    public void quantize(int[] pixels, int maxColors) {
        WuQuantizer wu = new WuQuantizer();
        wu.quantize(pixels, maxColors);
        WSMeansQuantizer kmeans =
                new WSMeansQuantizer(
                        wu.getColors(), new LABPointProvider(), wu.inputPixelToCount());
        kmeans.quantize(pixels, maxColors);
        this.mSwatches = kmeans.getQuantizedColors();
    }

    @Override // com.android.internal.graphics.palette.Quantizer
    public List<Palette.Swatch> getQuantizedColors() {
        return this.mSwatches;
    }
}
