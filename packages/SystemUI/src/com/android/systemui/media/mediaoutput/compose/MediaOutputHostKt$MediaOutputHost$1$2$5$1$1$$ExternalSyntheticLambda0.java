package com.android.systemui.media.mediaoutput.compose;

import androidx.navigation.NavController;
import androidx.navigation.NavHostController;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class MediaOutputHostKt$MediaOutputHost$1$2$5$1$1$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NavHostController f$0;

    public /* synthetic */ MediaOutputHostKt$MediaOutputHost$1$2$5$1$1$$ExternalSyntheticLambda0(NavHostController navHostController, int i) {
        this.$r8$classId = i;
        this.f$0 = navHostController;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                NavController.navigate$default(this.f$0, (String) obj, null, 6);
                break;
            case 1:
                NavController.navigate$default(this.f$0, (String) obj, null, 6);
                break;
            default:
                NavController.navigate$default(this.f$0, ((Screen) obj).route, null, 6);
                break;
        }
        return Unit.INSTANCE;
    }
}
