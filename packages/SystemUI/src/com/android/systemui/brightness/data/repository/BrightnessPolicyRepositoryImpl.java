package com.android.systemui.brightness.data.repository;

import android.content.Context;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.utils.UserRestrictionChecker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

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
