package com.android.systemui.brightness.data.repository;

import android.content.Context;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.utils.UserRestrictionChecker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class BrightnessPolicyRepositoryImpl implements BrightnessPolicyRepository {
    public final Context applicationContext;
    public final Flow restrictionPolicy;
    public final UserRestrictionChecker userRestrictionChecker;

    public BrightnessPolicyRepositoryImpl(UserRepository userRepository, UserRestrictionChecker userRestrictionChecker, Context context, CoroutineDispatcher coroutineDispatcher) {
        this.userRestrictionChecker = userRestrictionChecker;
        this.applicationContext = context;
        this.restrictionPolicy = FlowKt.flowOn(FlowKt.mapLatest(((UserRepositoryImpl) userRepository).selectedUserInfo, new BrightnessPolicyRepositoryImpl$restrictionPolicy$1(this, null)), coroutineDispatcher);
    }
}
