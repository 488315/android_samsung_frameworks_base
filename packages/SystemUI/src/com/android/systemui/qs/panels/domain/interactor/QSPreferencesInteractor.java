package com.android.systemui.qs.panels.domain.interactor;

import android.content.SharedPreferences;
import com.android.systemui.qs.panels.data.repository.QSPreferencesRepository;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.settings.UserFileManagerImpl;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class QSPreferencesInteractor {
    public final Flow largeTilesSpecs;
    public final QSPreferencesRepository repo;

    public QSPreferencesInteractor(QSPreferencesRepository qSPreferencesRepository) {
        this.repo = qSPreferencesRepository;
        this.largeTilesSpecs = qSPreferencesRepository.largeTilesSpecs;
    }

    public final void setLargeTilesSpecs(Set set) {
        QSPreferencesRepository qSPreferencesRepository = this.repo;
        SharedPreferences sharedPreferences$1 = ((UserFileManagerImpl) qSPreferencesRepository.userFileManager).getSharedPreferences$1(((UserRepositoryImpl) qSPreferencesRepository.userRepository).getSelectedUserInfo().id, "quick_settings_prefs");
        SharedPreferences.Editor edit = sharedPreferences$1.edit();
        Set set2 = set;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(set2, 10));
        Iterator it = set2.iterator();
        while (it.hasNext()) {
            arrayList.add(((TileSpec) it.next()).getSpec());
        }
        edit.putStringSet("large_tiles_specs", CollectionsKt___CollectionsKt.toSet(arrayList)).apply();
        sharedPreferences$1.edit().putBoolean("large_tiles_default", false).apply();
    }
}
