package com.android.server.slice;

import android.text.TextUtils;

import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class SliceClientPermissions$SliceAuthority$$ExternalSyntheticLambda0
        implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        String[] strArr = (String[]) obj;
        switch (this.$r8$classId) {
            case 0:
                return TextUtils.join(",", strArr);
            case 1:
                return TextUtils.join(",", strArr);
            default:
                return TextUtils.join("/", strArr);
        }
    }
}
