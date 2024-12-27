package com.android.internal.graphics.palette;

import java.util.List;

public interface Quantizer {
    List<Palette.Swatch> getQuantizedColors();

    void quantize(int[] iArr, int i);
}
