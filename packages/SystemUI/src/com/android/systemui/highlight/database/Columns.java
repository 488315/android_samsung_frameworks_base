package com.android.systemui.highlight.database;

import com.google.common.base.Platform;
import java.util.Arrays;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class Columns {
    public final String name;
    public final String option;
    public final String type;

    public Columns(String... strArr) {
        this.name = strArr[0];
        this.type = strArr[1];
        this.option = strArr.length > 2 ? strArr[2] : null;
    }

    public static Columns create(String... strArr) {
        if (strArr.length >= 2 && strArr.length <= 3) {
            return new Columns(strArr[0], strArr[1], strArr.length == 3 ? strArr[2] : null);
        }
        throw new IllegalArgumentException("Invalid column definition: " + Arrays.toString(strArr));
    }

    public final String toString() {
        CharSequence[] charSequenceArr = new CharSequence[3];
        charSequenceArr[0] = this.name;
        charSequenceArr[1] = this.type;
        int i = Platform.$r8$clinit;
        String str = this.option;
        if (str == null) {
            str = "";
        }
        charSequenceArr[2] = str;
        return String.join(" ", charSequenceArr);
    }
}
