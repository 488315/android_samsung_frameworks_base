package androidx.core.provider;

import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public class FontsContractCompat$FontFamilyResult {
    public final List mFonts;
    public final int mStatusCode;

    @Deprecated
    public FontsContractCompat$FontFamilyResult(int i, FontsContractCompat$FontInfo[] fontsContractCompat$FontInfoArr) {
        this.mStatusCode = i;
        this.mFonts = Collections.singletonList(fontsContractCompat$FontInfoArr);
    }

    public FontsContractCompat$FontFamilyResult(int i, List<FontsContractCompat$FontInfo[]> list) {
        this.mStatusCode = i;
        this.mFonts = list;
    }
}
