package com.android.server.slice;

import android.text.TextUtils;

import java.util.function.Function;

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
