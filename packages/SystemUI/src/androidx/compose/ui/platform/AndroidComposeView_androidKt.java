package androidx.compose.ui.platform;

import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public abstract class AndroidComposeView_androidKt {
    public static final Function1 platformTextInputServiceInterceptor = AndroidComposeView_androidKt$platformTextInputServiceInterceptor$1.INSTANCE;

    /* renamed from: dot-p89u6pk, reason: not valid java name */
    public static final float m702dotp89u6pk(float[] fArr, int i, float[] fArr2, int i2) {
        int i3 = i * 4;
        return (fArr[i3 + 3] * fArr2[12 + i2]) + (fArr[i3 + 2] * fArr2[8 + i2]) + (fArr[i3 + 1] * fArr2[4 + i2]) + (fArr[i3] * fArr2[i2]);
    }
}
