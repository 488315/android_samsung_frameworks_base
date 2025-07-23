package androidx.compose.ui.platform;

import android.view.View;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class FocusFinderCompat_androidKt$findViewInsideOutShouldExist$1 extends Lambda implements Function1 {
    final /* synthetic */ int $id;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FocusFinderCompat_androidKt$findViewInsideOutShouldExist$1(int i) {
        super(1);
        this.$id = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        return Boolean.valueOf(((View) obj).getId() == this.$id);
    }
}
