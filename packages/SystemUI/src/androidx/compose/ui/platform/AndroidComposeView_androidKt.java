package androidx.compose.ui.platform;

import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class AndroidComposeView_androidKt {
    public static final Function1 platformTextInputServiceInterceptor = AndroidComposeView_androidKt$platformTextInputServiceInterceptor$1.INSTANCE;

    /* renamed from: dot-p89u6pk, reason: not valid java name */
    public static final float m700dotp89u6pk(float[] fArr, int i, float[] fArr2, int i2) {
        int i3 = i * 4;
        return (fArr[i3 + 3] * fArr2[12 + i2]) + (fArr[i3 + 2] * fArr2[8 + i2]) + (fArr[i3 + 1] * fArr2[4 + i2]) + (fArr[i3] * fArr2[i2]);
    }
}
