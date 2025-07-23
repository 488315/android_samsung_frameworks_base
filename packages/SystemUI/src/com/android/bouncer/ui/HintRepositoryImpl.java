package com.android.bouncer.ui;

import com.android.internal.widget.LockPatternUtils;
import com.android.systemui.user.data.repository.UserRepository;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
