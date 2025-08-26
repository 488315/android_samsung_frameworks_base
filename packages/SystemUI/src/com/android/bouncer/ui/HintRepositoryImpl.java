package com.android.bouncer.ui;

import com.android.internal.widget.LockPatternUtils;
import com.android.systemui.user.data.repository.UserRepository;
import kotlinx.coroutines.CoroutineDispatcher;

/* loaded from: classes.dex */
public final class HintRepositoryImpl implements HintRepository {
    public final CoroutineDispatcher backgroundDispatcher;
    public final LockPatternUtils lockPatternUtils;
    public final UserRepository userRepository;

    public HintRepositoryImpl(CoroutineDispatcher coroutineDispatcher, LockPatternUtils lockPatternUtils, UserRepository userRepository) {
        this.backgroundDispatcher = coroutineDispatcher;
        this.lockPatternUtils = lockPatternUtils;
        this.userRepository = userRepository;
    }
}
