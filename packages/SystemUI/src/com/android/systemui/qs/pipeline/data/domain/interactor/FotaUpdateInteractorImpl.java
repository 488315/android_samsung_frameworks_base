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

/* loaded from: classes2.dex */
public final class FotaUpdateInteractorImpl implements FotaUpdateInteractor {
    public final Map fotaUpdateMap = new LinkedHashMap();
    public final TestTileDataRepository testTileDataRepository;

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
            QSType[] qSTypeArrValues = QSType.values();
            ArrayList arrayList = new ArrayList(qSTypeArrValues.length);
            int length = qSTypeArrValues.length;
            while (i < length) {
                arrayList.add((Boolean) this.fotaUpdateMap.put(qSTypeArrValues[i], Boolean.TRUE));
                i++;
            }
            return;
        }
        boolean zIsFotaUpdate = DeviceState.isFotaUpdate(context);
        QSType[] qSTypeArrValues2 = QSType.values();
        ArrayList arrayList2 = new ArrayList(qSTypeArrValues2.length);
        int length2 = qSTypeArrValues2.length;
        while (i < length2) {
            arrayList2.add((Boolean) this.fotaUpdateMap.put(qSTypeArrValues2[i], Boolean.valueOf(zIsFotaUpdate)));
            i++;
        }
    }

    public final void finishFotaUpdate(QSType qSType) {
        SharedPreferences.Editor editorEdit;
        SharedPreferences.Editor editorPutBoolean;
        this.fotaUpdateMap.put(qSType, Boolean.FALSE);
        if (ScRune.QUICK_MANAGE_TILE_LIST_TEST) {
            TestTileDataRepositoryImpl testTileDataRepositoryImpl = (TestTileDataRepositoryImpl) this.testTileDataRepository;
            if (testTileDataRepositoryImpl.isFotaTest()) {
                testTileDataRepositoryImpl.getClass();
                Log.i(TestTileDataRepositoryImpl.TAG, "finishFotaTest");
                SharedPreferences sharedPreferences = testTileDataRepositoryImpl.prefs;
                if (sharedPreferences == null || (editorEdit = sharedPreferences.edit()) == null || (editorPutBoolean = editorEdit.putBoolean(TestTileDataRepositoryImpl.PROPERTY_NAME, false)) == null) {
                    return;
                }
                editorPutBoolean.apply();
            }
        }
    }
}
