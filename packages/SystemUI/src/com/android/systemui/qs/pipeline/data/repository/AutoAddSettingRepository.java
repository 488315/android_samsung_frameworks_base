package com.android.systemui.qs.pipeline.data.repository;

import android.util.SparseArray;
import com.android.systemui.qs.pipeline.data.repository.UserAutoAddRepository;

public final class AutoAddSettingRepository implements AutoAddRepository {
    public final SparseArray userAutoAddRepositories;
    public final UserAutoAddRepository.Factory userAutoAddRepositoryFactory;

    public AutoAddSettingRepository(UserAutoAddRepository.Factory factory) {
        new SparseArray();
    }
}
