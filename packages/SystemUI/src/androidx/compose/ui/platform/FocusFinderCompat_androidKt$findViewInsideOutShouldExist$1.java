package androidx.compose.ui.platform;

import android.view.View;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

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
    public final Object mo781invoke(Object obj) {
        return Boolean.valueOf(((View) obj).getId() == this.$id);
    }
}
