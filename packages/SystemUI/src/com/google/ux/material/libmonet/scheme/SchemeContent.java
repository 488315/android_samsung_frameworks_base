package com.google.ux.material.libmonet.scheme;

import com.google.ux.material.libmonet.dislike.DislikeAnalyzer;
import com.google.ux.material.libmonet.dynamiccolor.DynamicScheme;
import com.google.ux.material.libmonet.dynamiccolor.Variant;
import com.google.ux.material.libmonet.hct.Hct;
import com.google.ux.material.libmonet.palettes.TonalPalette;
import com.google.ux.material.libmonet.temperature.TemperatureCache;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class SchemeContent extends DynamicScheme {
    public SchemeContent(Hct hct, boolean z, double d) {
        Variant variant = Variant.CONTENT;
        TonalPalette tonalPaletteFromHueAndChroma = TonalPalette.fromHueAndChroma(hct.hue, hct.chroma);
        double d2 = hct.hue;
        double d3 = hct.chroma;
        TonalPalette tonalPaletteFromHueAndChroma2 = TonalPalette.fromHueAndChroma(d2, Math.max(d3 - 32.0d, d3 * 0.5d));
        TemperatureCache temperatureCache = new TemperatureCache(hct);
        Hct hct2 = temperatureCache.input;
        int iRound = (int) Math.round(hct2.hue);
        Hct hct3 = (Hct) temperatureCache.getHctsByHue().get(iRound);
        double relativeTemperature = temperatureCache.getRelativeTemperature(hct3);
        ArrayList arrayList = new ArrayList();
        arrayList.add(hct3);
        int i = 0;
        double dAbs = 0.0d;
        while (i < 360) {
            int i2 = (iRound + i) % 360;
            if (i2 < 0) {
                i2 += 360;
            }
            double relativeTemperature2 = temperatureCache.getRelativeTemperature((Hct) temperatureCache.getHctsByHue().get(i2));
            dAbs += Math.abs(relativeTemperature2 - relativeTemperature);
            i++;
            relativeTemperature = relativeTemperature2;
        }
        int i3 = 6;
        double d4 = dAbs / 6;
        double relativeTemperature3 = temperatureCache.getRelativeTemperature(hct3);
        int i4 = 1;
        double dAbs2 = 0.0d;
        while (true) {
            if (arrayList.size() >= i3) {
                break;
            }
            int i5 = (iRound + i4) % 360;
            Hct hct4 = (Hct) temperatureCache.getHctsByHue().get(i5 < 0 ? i5 + 360 : i5);
            double relativeTemperature4 = temperatureCache.getRelativeTemperature(hct4);
            dAbs2 = Math.abs(relativeTemperature4 - relativeTemperature3) + dAbs2;
            boolean z2 = dAbs2 >= ((double) arrayList.size()) * d4;
            int i6 = 1;
            while (z2 && arrayList.size() < i3) {
                arrayList.add(hct4);
                int i7 = i4;
                z2 = dAbs2 >= ((double) (arrayList.size() + i6)) * d4;
                i6++;
                i4 = i7;
                i3 = 6;
            }
            i4++;
            if (i4 > 360) {
                while (arrayList.size() < 6) {
                    arrayList.add(hct4);
                }
            } else {
                i3 = 6;
                relativeTemperature3 = relativeTemperature4;
            }
        }
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(hct2);
        int iFloor = (int) Math.floor((3 - 1.0d) / 2.0d);
        for (int i8 = 1; i8 < iFloor + 1; i8++) {
            int size = 0 - i8;
            while (size < 0) {
                size += arrayList.size();
            }
            if (size >= arrayList.size()) {
                size %= arrayList.size();
            }
            arrayList2.add(0, (Hct) arrayList.get(size));
        }
        int i9 = 3 - iFloor;
        for (int i10 = 1; i10 < i9; i10++) {
            int size2 = i10;
            while (size2 < 0) {
                size2 += arrayList.size();
            }
            if (size2 >= arrayList.size()) {
                size2 %= arrayList.size();
            }
            arrayList2.add((Hct) arrayList.get(size2));
        }
        super(hct, variant, z, d, tonalPaletteFromHueAndChroma, tonalPaletteFromHueAndChroma2, TonalPalette.fromHct(DislikeAnalyzer.fixIfDisliked((Hct) arrayList2.get(2))), TonalPalette.fromHueAndChroma(hct.hue, hct.chroma / 8.0d), TonalPalette.fromHueAndChroma(hct.hue, (hct.chroma / 8.0d) + 4.0d));
    }
}
