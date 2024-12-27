package com.android.systemui.monet;

import com.google.ux.material.libmonet.hct.Hct;
import com.samsung.android.knox.license.KnoxEnterpriseLicenseManager;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class TonalPalette {
    public static final List SHADE_KEYS = Arrays.asList(0, 10, 50, 100, 200, 300, 400, 500, Integer.valueOf(VolteConstants.ErrorCode.BUSY_EVERYWHERE), Integer.valueOf(KnoxEnterpriseLicenseManager.ERROR_LICENSE_DEACTIVATED), 800, 900, 1000);
    public final List allShades;
    public final Map allShadesMapped;
    public final com.google.ux.material.libmonet.palettes.TonalPalette mMaterialTonalPalette;

    public TonalPalette(com.google.ux.material.libmonet.palettes.TonalPalette tonalPalette) {
        this.mMaterialTonalPalette = tonalPalette;
        List list = SHADE_KEYS;
        final int i = 0;
        this.allShades = (List) list.stream().map(new Function(this) { // from class: com.android.systemui.monet.TonalPalette$$ExternalSyntheticLambda0
            public final /* synthetic */ TonalPalette f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                int i2 = i;
                TonalPalette tonalPalette2 = this.f$0;
                Integer num = (Integer) obj;
                tonalPalette2.getClass();
                switch (i2) {
                }
                return Integer.valueOf(tonalPalette2.getAtTone(num.floatValue()));
            }
        }).collect(Collectors.toList());
        final int i2 = 1;
        this.allShadesMapped = (Map) list.stream().collect(Collectors.toMap(new TonalPalette$$ExternalSyntheticLambda1(), new Function(this) { // from class: com.android.systemui.monet.TonalPalette$$ExternalSyntheticLambda0
            public final /* synthetic */ TonalPalette f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                int i22 = i2;
                TonalPalette tonalPalette2 = this.f$0;
                Integer num = (Integer) obj;
                tonalPalette2.getClass();
                switch (i22) {
                }
                return Integer.valueOf(tonalPalette2.getAtTone(num.floatValue()));
            }
        }));
    }

    public final int getAtTone(float f) {
        int i = (int) ((1000.0f - f) / 10.0f);
        com.google.ux.material.libmonet.palettes.TonalPalette tonalPalette = this.mMaterialTonalPalette;
        Integer num = (Integer) ((HashMap) tonalPalette.cache).get(Integer.valueOf(i));
        if (num == null) {
            num = Integer.valueOf(Hct.from(tonalPalette.hue, tonalPalette.chroma, i).argb);
            ((HashMap) tonalPalette.cache).put(Integer.valueOf(i), num);
        }
        return num.intValue();
    }

    public final int getS100() {
        return ((Integer) this.allShades.get(3)).intValue();
    }

    public final int getS200() {
        return ((Integer) this.allShades.get(4)).intValue();
    }

    public final int getS50() {
        return ((Integer) this.allShades.get(2)).intValue();
    }

    public final int getS500() {
        return ((Integer) this.allShades.get(7)).intValue();
    }

    public final int getS700() {
        return ((Integer) this.allShades.get(9)).intValue();
    }

    public final int getS800() {
        return ((Integer) this.allShades.get(10)).intValue();
    }
}
