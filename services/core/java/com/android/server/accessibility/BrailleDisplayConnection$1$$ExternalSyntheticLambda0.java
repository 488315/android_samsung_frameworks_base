package com.android.server.accessibility;

import com.android.server.accessibility.BrailleDisplayConnection;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class BrailleDisplayConnection$1$$ExternalSyntheticLambda0 implements Function {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BrailleDisplayConnection.NativeInterface f$0;

    public /* synthetic */ BrailleDisplayConnection$1$$ExternalSyntheticLambda0(BrailleDisplayConnection.NativeInterface nativeInterface, int i) {
        this.$r8$classId = i;
        this.f$0 = nativeInterface;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        int i = this.$r8$classId;
        BrailleDisplayConnection.NativeInterface nativeInterface = this.f$0;
        Integer num = (Integer) obj;
        switch (i) {
            case 0:
                int hidrawDescSize = nativeInterface.getHidrawDescSize(num.intValue());
                if (hidrawDescSize > 0) {
                    return nativeInterface.getHidrawDesc(num.intValue(), hidrawDescSize);
                }
                return null;
            case 1:
                return nativeInterface.getHidrawName(num.intValue());
            case 2:
                return Integer.valueOf(nativeInterface.getHidrawBusType(num.intValue()));
            default:
                return nativeInterface.getHidrawUniq(num.intValue());
        }
    }
}
