package com.android.systemui.screenshot.data.repository;

import android.os.UserManager;
import com.android.systemui.screenshot.data.model.ProfileType;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.sync.Mutex;
import kotlinx.coroutines.sync.MutexImpl;
import kotlinx.coroutines.sync.MutexKt;

/* loaded from: classes2.dex */
public final class ProfileTypeRepositoryImpl implements ProfileTypeRepository {
    public final CoroutineDispatcher background;
    public final Map cache = new LinkedHashMap();
    public final MutexImpl mutex = MutexKt.Mutex$default();
    public final UserManager userManager;

    /* renamed from: com.android.systemui.screenshot.data.repository.ProfileTypeRepositoryImpl$getProfileType$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        int I$0;
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ProfileTypeRepositoryImpl.this.getProfileType(0, this);
        }
    }

    public ProfileTypeRepositoryImpl(UserManager userManager, CoroutineDispatcher coroutineDispatcher) {
        this.userManager = userManager;
        this.background = coroutineDispatcher;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Type inference failed for: r7v9, types: [kotlinx.coroutines.sync.Mutex] */
    /* JADX WARN: Type inference failed for: r8v10, types: [kotlinx.coroutines.sync.Mutex] */
    /* JADX WARN: Type inference failed for: r8v11 */
    /* JADX WARN: Type inference failed for: r8v2 */
    /* JADX WARN: Type inference failed for: r8v3 */
    /* JADX WARN: Type inference failed for: r8v4, types: [kotlinx.coroutines.sync.Mutex] */
    /* JADX WARN: Type inference failed for: r8v5 */
    /* JADX WARN: Type inference failed for: r8v6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object getProfileType(int i, ContinuationImpl continuationImpl) throws Throwable {
        AnonymousClass1 anonymousClass1;
        MutexImpl mutexImpl;
        ?? r8;
        ProfileType profileType;
        ProfileTypeRepositoryImpl profileTypeRepositoryImpl;
        int i2;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i3 = anonymousClass1.label;
            if ((i3 & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i3 - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i4 = anonymousClass1.label;
        try {
            if (i4 == 0) {
                ResultKt.throwOnFailure(obj);
                mutexImpl = this.mutex;
                anonymousClass1.L$0 = this;
                anonymousClass1.L$1 = mutexImpl;
                anonymousClass1.I$0 = i;
                anonymousClass1.label = 1;
                if (mutexImpl.lock(anonymousClass1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            if (i4 != 1) {
                if (i4 != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                i2 = anonymousClass1.I$0;
                r8 = (Mutex) anonymousClass1.L$1;
                profileTypeRepositoryImpl = (ProfileTypeRepositoryImpl) anonymousClass1.L$0;
                try {
                    ResultKt.throwOnFailure(obj);
                    r8 = r8;
                    Integer num = new Integer(i2);
                    profileTypeRepositoryImpl.cache.put(num, (ProfileType) obj);
                    profileType = (ProfileType) obj;
                    mutexImpl = r8;
                    mutexImpl.unlock(null);
                    return profileType;
                } catch (Throwable th) {
                    th = th;
                    r8.unlock(null);
                    throw th;
                }
            }
            i = anonymousClass1.I$0;
            ?? r7 = (Mutex) anonymousClass1.L$1;
            ProfileTypeRepositoryImpl profileTypeRepositoryImpl2 = (ProfileTypeRepositoryImpl) anonymousClass1.L$0;
            ResultKt.throwOnFailure(obj);
            mutexImpl = r7;
            this = profileTypeRepositoryImpl2;
            profileType = (ProfileType) ((LinkedHashMap) this.cache).get(new Integer(i));
            if (profileType == null) {
                CoroutineDispatcher coroutineDispatcher = this.background;
                ProfileTypeRepositoryImpl$getProfileType$2$1 profileTypeRepositoryImpl$getProfileType$2$1 = new ProfileTypeRepositoryImpl$getProfileType$2$1(this, i, null);
                anonymousClass1.L$0 = this;
                anonymousClass1.L$1 = mutexImpl;
                anonymousClass1.I$0 = i;
                anonymousClass1.label = 2;
                Object objWithContext = BuildersKt.withContext(coroutineDispatcher, profileTypeRepositoryImpl$getProfileType$2$1, anonymousClass1);
                if (objWithContext != coroutineSingletons) {
                    profileTypeRepositoryImpl = this;
                    i2 = i;
                    r8 = mutexImpl;
                    obj = objWithContext;
                    Integer num2 = new Integer(i2);
                    profileTypeRepositoryImpl.cache.put(num2, (ProfileType) obj);
                    profileType = (ProfileType) obj;
                    mutexImpl = r8;
                }
                return coroutineSingletons;
            }
            mutexImpl.unlock(null);
            return profileType;
        } catch (Throwable th2) {
            th = th2;
            r8 = mutexImpl;
            r8.unlock(null);
            throw th;
        }
    }
}
