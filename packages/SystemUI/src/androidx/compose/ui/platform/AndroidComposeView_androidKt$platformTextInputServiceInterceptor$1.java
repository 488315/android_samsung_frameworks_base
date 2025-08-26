package androidx.compose.ui.platform;

import androidx.compose.ui.text.input.PlatformTextInputService;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* loaded from: classes.dex */
final class AndroidComposeView_androidKt$platformTextInputServiceInterceptor$1 extends Lambda implements Function1 {
    public static final AndroidComposeView_androidKt$platformTextInputServiceInterceptor$1 INSTANCE = new AndroidComposeView_androidKt$platformTextInputServiceInterceptor$1();

    public AndroidComposeView_androidKt$platformTextInputServiceInterceptor$1() {
        super(1);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        return (PlatformTextInputService) obj;
    }
}
