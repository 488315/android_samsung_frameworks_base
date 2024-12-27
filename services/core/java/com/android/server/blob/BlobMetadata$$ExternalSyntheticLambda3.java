package com.android.server.blob;

import com.android.server.blob.BlobMetadata;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class BlobMetadata$$ExternalSyntheticLambda3 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;
    public final /* synthetic */ String f$1;

    public /* synthetic */ BlobMetadata$$ExternalSyntheticLambda3(int i, String str, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
        this.f$1 = str;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                int i = this.f$0;
                String str = this.f$1;
                BlobMetadata.Leasee leasee = (BlobMetadata.Leasee) obj;
                if (leasee.uid != i || !leasee.packageName.equals(str)) {
                }
                break;
            default:
                int i2 = this.f$0;
                String str2 = this.f$1;
                BlobMetadata.Committer committer = (BlobMetadata.Committer) obj;
                if (committer.uid != i2 || !committer.packageName.equals(str2)) {
                }
                break;
        }
        return false;
    }
}
