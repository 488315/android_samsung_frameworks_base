package androidx.compose.ui.text.font;

import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;
import androidx.compose.ui.text.internal.InlineClassHelperKt;
import com.samsung.android.knox.license.KnoxEnterpriseLicenseManager;
import com.sec.ims.volte2.data.VolteConstants;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class FontWeight implements Comparable<FontWeight> {
    public static final FontWeight Bold;
    public static final Companion Companion = new Companion(null);
    public static final FontWeight Light = null;
    public static final FontWeight Medium;
    public static final FontWeight Normal;
    public static final FontWeight W400;
    public static final FontWeight W500;
    public static final FontWeight W600;
    public static final FontWeight W700;
    public final int weight;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        FontWeight fontWeight = new FontWeight(100);
        FontWeight fontWeight2 = new FontWeight(200);
        FontWeight fontWeight3 = new FontWeight(300);
        FontWeight fontWeight4 = new FontWeight(400);
        W400 = fontWeight4;
        FontWeight fontWeight5 = new FontWeight(500);
        W500 = fontWeight5;
        FontWeight fontWeight6 = new FontWeight(VolteConstants.ErrorCode.BUSY_EVERYWHERE);
        W600 = fontWeight6;
        FontWeight fontWeight7 = new FontWeight(KnoxEnterpriseLicenseManager.ERROR_LICENSE_DEACTIVATED);
        W700 = fontWeight7;
        FontWeight fontWeight8 = new FontWeight(800);
        FontWeight fontWeight9 = new FontWeight(900);
        Normal = fontWeight4;
        Medium = fontWeight5;
        Bold = fontWeight7;
        CollectionsKt__CollectionsKt.listOf(fontWeight, fontWeight2, fontWeight3, fontWeight4, fontWeight5, fontWeight6, fontWeight7, fontWeight8, fontWeight9);
    }

    public FontWeight(int i) {
        this.weight = i;
        boolean z = false;
        if (1 <= i && i < 1001) {
            z = true;
        }
        if (z) {
            return;
        }
        InlineClassHelperKt.throwIllegalArgumentException("Font weight can be in range [1, 1000]. Current value: " + i);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof FontWeight) && this.weight == ((FontWeight) obj).weight;
    }

    public final int hashCode() {
        return this.weight;
    }

    public final String toString() {
        return BackEventCompat$$ExternalSyntheticOutline0.m(new StringBuilder("FontWeight(weight="), this.weight, ')');
    }

    @Override // java.lang.Comparable
    public final int compareTo(FontWeight fontWeight) {
        return Intrinsics.compare(this.weight, fontWeight.weight);
    }
}
