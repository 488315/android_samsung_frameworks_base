package com.android.systemui.qs.pipeline.data.domain.interactor;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.android.systemui.ScRune;
import com.android.systemui.qs.pipeline.dagger.QSType;
import com.android.systemui.qs.pipeline.simulation.data.repository.TestTileDataRepository;
import com.android.systemui.qs.pipeline.simulation.data.repository.TestTileDataRepositoryImpl;
import com.android.systemui.util.DeviceState;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class FotaUpdateInteractorImpl implements FotaUpdateInteractor {
    public final Map fotaUpdateMap = new LinkedHashMap();
    public final TestTileDataRepository testTileDataRepository;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public FotaUpdateInteractorImpl(Context context, TestTileDataRepository testTileDataRepository) {
        this.testTileDataRepository = testTileDataRepository;
        int i = 0;
        if (ScRune.QUICK_MANAGE_TILE_LIST_TEST && ((TestTileDataRepositoryImpl) testTileDataRepository).isFotaTest()) {
            QSType[] values = QSType.values();
            ArrayList arrayList = new ArrayList(values.length);
            int length = values.length;
            while (i < length) {
                arrayList.add((Boolean) this.fotaUpdateMap.put(values[i], Boolean.TRUE));
                i++;
            }
            return;
        }
        boolean isFotaUpdate = DeviceState.isFotaUpdate(context);
        QSType[] values2 = QSType.values();
        ArrayList arrayList2 = new ArrayList(values2.length);
        int length2 = values2.length;
        while (i < length2) {
            arrayList2.add((Boolean) this.fotaUpdateMap.put(values2[i], Boolean.valueOf(isFotaUpdate)));
            i++;
        }
    }

    public final void finishFotaUpdate(QSType qSType) {
        SharedPreferences.Editor edit;
        SharedPreferences.Editor putBoolean;
        this.fotaUpdateMap.put(qSType, Boolean.FALSE);
        if (ScRune.QUICK_MANAGE_TILE_LIST_TEST) {
            TestTileDataRepositoryImpl testTileDataRepositoryImpl = (TestTileDataRepositoryImpl) this.testTileDataRepository;
            if (testTileDataRepositoryImpl.isFotaTest()) {
                testTileDataRepositoryImpl.getClass();
                Log.i(TestTileDataRepositoryImpl.TAG, "finishFotaTest");
                SharedPreferences sharedPreferences = testTileDataRepositoryImpl.prefs;
                if (sharedPreferences == null || (edit = sharedPreferences.edit()) == null || (putBoolean = edit.putBoolean(TestTileDataRepositoryImpl.PROPERTY_NAME, false)) == null) {
                    return;
                }
                putBoolean.apply();
            }
        }
    }
}
