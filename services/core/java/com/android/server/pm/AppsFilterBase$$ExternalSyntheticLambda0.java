package com.android.server.pm;

import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.SparseArray;

import com.android.internal.util.function.QuadFunction;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class AppsFilterBase$$ExternalSyntheticLambda0 {
    public final /* synthetic */ SparseArray f$0;
    public final /* synthetic */ int[] f$1;
    public final /* synthetic */ QuadFunction f$2;

    public /* synthetic */ AppsFilterBase$$ExternalSyntheticLambda0(
            SparseArray sparseArray,
            int[] iArr,
            ComputerEngine$$ExternalSyntheticLambda6 computerEngine$$ExternalSyntheticLambda6) {
        this.f$0 = sparseArray;
        this.f$1 = iArr;
        this.f$2 = computerEngine$$ExternalSyntheticLambda6;
    }

    public final String toString(Object obj) {
        SparseArray sparseArray = this.f$0;
        QuadFunction quadFunction = this.f$2;
        Integer num = (Integer) obj;
        String str = (String) sparseArray.get(num.intValue());
        if (str == null) {
            int callingUid = Binder.getCallingUid();
            int appId = UserHandle.getAppId(num.intValue());
            int[] iArr = this.f$1;
            int length = iArr.length;
            String[] strArr = null;
            for (int i = 0; strArr == null && i < length; i++) {
                strArr =
                        (String[])
                                quadFunction.apply(
                                        Integer.valueOf(callingUid),
                                        Integer.valueOf(iArr[i]),
                                        Integer.valueOf(appId),
                                        Boolean.FALSE);
            }
            str =
                    strArr == null
                            ? "[app id " + num + " not installed]"
                            : strArr.length == 1
                                    ? strArr[0]
                                    : AudioOffloadInfo$$ExternalSyntheticOutline0.m(
                                            new StringBuilder("["),
                                            TextUtils.join(",", strArr),
                                            "]");
            sparseArray.put(num.intValue(), str);
        }
        return str;
    }
}
