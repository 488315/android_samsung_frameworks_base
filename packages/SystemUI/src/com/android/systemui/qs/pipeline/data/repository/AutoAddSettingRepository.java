package com.android.systemui.qs.pipeline.data.repository;

import android.util.SparseArray;
import com.android.systemui.qs.pipeline.data.repository.UserAutoAddRepository;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class AutoAddSettingRepository implements AutoAddRepository {
    public final SparseArray userAutoAddRepositories;
    public final UserAutoAddRepository.Factory userAutoAddRepositoryFactory;

    public AutoAddSettingRepository(UserAutoAddRepository.Factory factory) {
        new SparseArray();
    }
}
