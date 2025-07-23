package androidx.compose.ui.platform;

import androidx.compose.ui.text.input.PlatformTextInputService;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class AndroidComposeView_androidKt$platformTextInputServiceInterceptor$1 extends Lambda implements Function1 {
    public static final AndroidComposeView_androidKt$platformTextInputServiceInterceptor$1 INSTANCE = new AndroidComposeView_androidKt$platformTextInputServiceInterceptor$1();

    public AndroidComposeView_androidKt$platformTextInputServiceInterceptor$1() {
        super(1);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        return (PlatformTextInputService) obj;
    }
}
