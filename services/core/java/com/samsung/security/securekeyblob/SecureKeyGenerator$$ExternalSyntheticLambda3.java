package com.samsung.security.securekeyblob;

import android.security.keystore.ArrayUtils;
import android.security.keystore.KeyProperties;

import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class SecureKeyGenerator$$ExternalSyntheticLambda3
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ List f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ SecureKeyGenerator$$ExternalSyntheticLambda3(
            List list, int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = list;
        this.f$1 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = 1;
        switch (this.$r8$classId) {
            case 0:
                List list = this.f$0;
                int[] iArr = (int[]) this.f$1;
                Integer num = (Integer) obj;
                list.add(SecureKeyGenerator.makeEnum(536870918, num.intValue()));
                if (num.intValue() == 2) {
                    boolean[] zArr = {false};
                    ArrayUtils.forEach(
                            iArr, new SecureKeyGenerator$$ExternalSyntheticLambda3(list, i, zArr));
                    if (!zArr[0]) {
                        list.add(
                                SecureKeyGenerator.makeEnum(
                                        536871115, KeyProperties.Digest.toKeymaster("SHA-1")));
                        break;
                    }
                }
                break;
            default:
                List list2 = this.f$0;
                boolean[] zArr2 = (boolean[]) this.f$1;
                Integer num2 = (Integer) obj;
                list2.add(SecureKeyGenerator.makeEnum(536871115, num2.intValue()));
                zArr2[0] =
                        num2.equals(Integer.valueOf(KeyProperties.Digest.toKeymaster("SHA-1")))
                                | zArr2[0];
                break;
        }
    }
}
