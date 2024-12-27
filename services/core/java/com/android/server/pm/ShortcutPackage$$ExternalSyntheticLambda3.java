package com.android.server.pm;

import android.content.pm.ShortcutInfo;

import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class ShortcutPackage$$ExternalSyntheticLambda3 implements Function {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ boolean[] f$0;

    public /* synthetic */ ShortcutPackage$$ExternalSyntheticLambda3(int i, boolean[] zArr) {
        this.$r8$classId = i;
        this.f$0 = zArr;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        int i = this.$r8$classId;
        boolean[] zArr = this.f$0;
        ShortcutInfo shortcutInfo = (ShortcutInfo) obj;
        switch (i) {
            case 0:
                zArr[0] = true;
                return Boolean.TRUE;
            default:
                if (shortcutInfo.isDeclaredInManifest()) {
                    return Boolean.FALSE;
                }
                zArr[0] = true;
                return Boolean.TRUE;
        }
    }
}
