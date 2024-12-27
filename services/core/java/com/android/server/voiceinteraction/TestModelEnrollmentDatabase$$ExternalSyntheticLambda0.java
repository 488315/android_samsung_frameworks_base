package com.android.server.voiceinteraction;

import java.util.Map;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class TestModelEnrollmentDatabase$$ExternalSyntheticLambda0
        implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ TestModelEnrollmentDatabase$$ExternalSyntheticLambda0(
            int i, String str, int i2, int i3) {
        this.$r8$classId = i3;
        this.f$0 = i;
        this.f$1 = str;
        this.f$2 = i2;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                int i = this.f$0;
                String str = this.f$1;
                int i2 = this.f$2;
                TestModelEnrollmentDatabase.EnrollmentKey enrollmentKey =
                        (TestModelEnrollmentDatabase.EnrollmentKey) obj;
                if (enrollmentKey.mKeyphraseId != i
                        || !enrollmentKey.mLocale.equals(str)
                        || !enrollmentKey.mUserIds.contains(Integer.valueOf(i2))) {}
                break;
            default:
                int i3 = this.f$0;
                String str2 = this.f$1;
                int i4 = this.f$2;
                Map.Entry entry = (Map.Entry) obj;
                if (((TestModelEnrollmentDatabase.EnrollmentKey) entry.getKey()).mKeyphraseId != i3
                        || !((TestModelEnrollmentDatabase.EnrollmentKey) entry.getKey())
                                .mLocale.equals(str2)
                        || !((TestModelEnrollmentDatabase.EnrollmentKey) entry.getKey())
                                .mUserIds.contains(Integer.valueOf(i4))) {}
                break;
        }
        return false;
    }
}
