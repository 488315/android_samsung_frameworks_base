package com.android.server.inputmethod;

import com.android.server.inputmethod.UserDataRepository;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class UserDataRepository$1$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ UserDataRepository.AnonymousClass1 f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ UserDataRepository$1$$ExternalSyntheticLambda0(UserDataRepository.AnonymousClass1 anonymousClass1, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = anonymousClass1;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                UserDataRepository.AnonymousClass1 anonymousClass1 = this.f$0;
                int i = this.f$1;
                anonymousClass1.getClass();
                synchronized (ImfLock.class) {
                    anonymousClass1.this$0.mUserData.remove(i);
                }
                return;
            default:
                UserDataRepository.AnonymousClass1 anonymousClass12 = this.f$0;
                int i2 = this.f$1;
                anonymousClass12.getClass();
                synchronized (ImfLock.class) {
                    anonymousClass12.this$0.getOrCreate(i2);
                }
                return;
        }
    }
}
