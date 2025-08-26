package com.android.systemui.shade.domain.interactor;

import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.shade.data.repository.ShadeRepository;
import com.android.systemui.shade.data.repository.ShadeRepositoryImpl;
import com.android.systemui.shade.shared.model.ShadeMode;
import com.android.systemui.shared.settings.data.repository.SecureSettingsRepository;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* loaded from: classes3.dex */
public final class ShadeModeInteractorImpl implements ShadeModeInteractor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 isDualShadeEnabled;
    public final ReadonlyStateFlow isShadeLayoutWide;
    public final ShadeRepository repository;
    public final ReadonlyStateFlow shadeMode;

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

    public ShadeModeInteractorImpl(CoroutineScope coroutineScope, ShadeRepository shadeRepository, SecureSettingsRepository secureSettingsRepository, TableLogBuffer tableLogBuffer) {
        this.repository = shadeRepository;
        FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
        this.isDualShadeEnabled = flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        ReadonlyStateFlow readonlyStateFlow = ((ShadeRepositoryImpl) shadeRepository).isShadeLayoutWide;
        this.isShadeLayoutWide = readonlyStateFlow;
        Flow flowLogDiffsForTable = DiffableKt.logDiffsForTable(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, readonlyStateFlow, new ShadeModeInteractorImpl$shadeMode$1(this)), tableLogBuffer, "", ((Boolean) ((ShadeRepositoryImpl) shadeRepository).isShadeLayoutWide.$$delegate_0.getValue()).booleanValue() ? ShadeMode.Split.INSTANCE : ShadeMode.Single.INSTANCE);
        SharingStarted.Companion.getClass();
        this.shadeMode = FlowKt.stateIn(flowLogDiffsForTable, coroutineScope, SharingStarted.Companion.Eagerly, ((Boolean) ((ShadeRepositoryImpl) shadeRepository).isShadeLayoutWide.$$delegate_0.getValue()).booleanValue() ? ShadeMode.Split.INSTANCE : ShadeMode.Single.INSTANCE);
    }
}
