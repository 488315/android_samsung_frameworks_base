package com.android.server.voiceinteraction;

import android.hardware.broadcastradio.AmFmBandRange$$ExternalSyntheticOutline0;
import android.hardware.soundtrigger.SoundTrigger;
import com.android.server.voiceinteraction.TestModelEnrollmentDatabase;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class TestModelEnrollmentDatabase implements IEnrolledModelDb {
    public final Map mModelMap = new HashMap();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class EnrollmentKey {
        public final int mKeyphraseId;
        public final String mLocale;
        public final List mUserIds;

        public EnrollmentKey(int i, String str, List list) {
            this.mKeyphraseId = i;
            Objects.requireNonNull(list);
            this.mUserIds = list;
            Objects.requireNonNull(str);
            this.mLocale = str;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || !(obj instanceof EnrollmentKey)) {
                return false;
            }
            EnrollmentKey enrollmentKey = (EnrollmentKey) obj;
            return this.mKeyphraseId == enrollmentKey.mKeyphraseId && this.mUserIds.equals(enrollmentKey.mUserIds) && this.mLocale.equals(enrollmentKey.mLocale);
        }

        public final int hashCode() {
            return this.mLocale.hashCode() + ((this.mUserIds.hashCode() + ((this.mKeyphraseId + 31) * 31)) * 31);
        }

        public final String toString() {
            StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
            StringBuilder m = AmFmBandRange$$ExternalSyntheticOutline0.m(new StringBuilder("keyphraseId: "), this.mKeyphraseId, stringJoiner, "userIds: ");
            m.append(this.mUserIds.toString());
            stringJoiner.add(m.toString());
            stringJoiner.add("locale: " + this.mLocale.toString());
            return AmFmBandRange$$ExternalSyntheticOutline0.m(stringJoiner, new StringBuilder("EnrollmentKey: "));
        }
    }

    @Override // com.android.server.voiceinteraction.IEnrolledModelDb
    public final boolean deleteKeyphraseSoundModel(int i, int i2, String str) {
        return ((HashMap) this.mModelMap).keySet().removeIf(new TestModelEnrollmentDatabase$$ExternalSyntheticLambda0(i, str, i2, 0));
    }

    @Override // com.android.server.voiceinteraction.IEnrolledModelDb
    public final void dump(PrintWriter printWriter) {
        printWriter.println("Using test enrollment database, with enrolled models:");
        printWriter.println(this.mModelMap);
    }

    @Override // com.android.server.voiceinteraction.IEnrolledModelDb
    public final SoundTrigger.KeyphraseSoundModel getKeyphraseSoundModel(int i, int i2, String str) {
        return (SoundTrigger.KeyphraseSoundModel) ((HashMap) this.mModelMap).entrySet().stream().filter(new TestModelEnrollmentDatabase$$ExternalSyntheticLambda0(i, str, i2, 1)).findFirst().map(new TestModelEnrollmentDatabase$$ExternalSyntheticLambda2(0)).orElse(null);
    }

    @Override // com.android.server.voiceinteraction.IEnrolledModelDb
    public final SoundTrigger.KeyphraseSoundModel getKeyphraseSoundModel(final int i, final String str, final String str2) {
        return (SoundTrigger.KeyphraseSoundModel) ((HashMap) this.mModelMap).entrySet().stream().filter(new Predicate() { // from class: com.android.server.voiceinteraction.TestModelEnrollmentDatabase$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                Map.Entry entry = (Map.Entry) obj;
                return ((SoundTrigger.KeyphraseSoundModel) entry.getValue()).getKeyphrases()[0].getText().equals(str) && ((TestModelEnrollmentDatabase.EnrollmentKey) entry.getKey()).mLocale.equals(str2) && ((TestModelEnrollmentDatabase.EnrollmentKey) entry.getKey()).mUserIds.contains(Integer.valueOf(i));
            }
        }).findFirst().map(new TestModelEnrollmentDatabase$$ExternalSyntheticLambda2(1)).orElse(null);
    }

    @Override // com.android.server.voiceinteraction.IEnrolledModelDb
    public final boolean updateKeyphraseSoundModel(SoundTrigger.KeyphraseSoundModel keyphraseSoundModel) {
        SoundTrigger.Keyphrase keyphrase = keyphraseSoundModel.getKeyphrases()[0];
        Map map = this.mModelMap;
        HashMap hashMap = (HashMap) map;
        hashMap.put(new EnrollmentKey(keyphrase.getId(), keyphrase.getLocale().toLanguageTag(), Arrays.stream(keyphrase.getUsers()).boxed().toList()), keyphraseSoundModel);
        return true;
    }
}
