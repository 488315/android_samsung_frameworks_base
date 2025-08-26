package com.android.systemui.qs.pipeline.domain.startable;

import android.os.Build;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import com.android.systemui.CoreStartable;
import com.android.systemui.Prefs;
import com.android.systemui.qs.QSBackupRestoreManager;
import com.android.systemui.qs.pipeline.data.domain.interactor.ResetTilesInteractor;
import com.android.systemui.qs.pipeline.data.domain.interactor.TileOrderLoggingInteractor;
import com.android.systemui.qs.pipeline.data.domain.interactor.TileSearchInteractor;
import com.android.systemui.qs.pipeline.data.domain.interactor.TileVisibilityInteractor;
import com.android.systemui.qs.pipeline.data.domain.interactor.TilesBackUpRestoreInteractor;
import com.android.systemui.qs.pipeline.data.domain.interactor.TilesBackUpRestoreInteractorImpl;
import com.android.systemui.qs.pipeline.domain.interactor.AccessibilityTilesInteractor;
import com.android.systemui.qs.pipeline.domain.interactor.AutoAddInteractor;
import com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor;
import com.android.systemui.qs.pipeline.domain.interactor.RestoreReconciliationInteractor;
import com.android.systemui.qs.pipeline.shared.QSPipelineFlagsRepository;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.shared.QSSettingsPackageRepository;
import com.android.systemui.util.settings.SecureSettings;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* loaded from: classes2.dex */
public final class QSPipelineCoreStartable implements CoreStartable {
    public final AccessibilityTilesInteractor accessibilityTilesInteractor;
    public final AutoAddInteractor autoAddInteractor;
    public final CurrentTilesInteractor currentTilesInteractor;
    public final RestoreReconciliationInteractor restoreReconciliationInteractor;
    public final QSSettingsPackageRepository settingsPackageRepository;
    public final TileOrderLoggingInteractor tileOrderLoggingInteractor;
    public final TilesBackUpRestoreInteractor tilesBackUpRestoreInteractor;

    public QSPipelineCoreStartable(CurrentTilesInteractor currentTilesInteractor, AccessibilityTilesInteractor accessibilityTilesInteractor, AutoAddInteractor autoAddInteractor, QSPipelineFlagsRepository qSPipelineFlagsRepository, QSSettingsPackageRepository qSSettingsPackageRepository, RestoreReconciliationInteractor restoreReconciliationInteractor, TileVisibilityInteractor tileVisibilityInteractor, ResetTilesInteractor resetTilesInteractor, TileSearchInteractor tileSearchInteractor, TilesBackUpRestoreInteractor tilesBackUpRestoreInteractor, TileOrderLoggingInteractor tileOrderLoggingInteractor) {
        this.currentTilesInteractor = currentTilesInteractor;
        this.accessibilityTilesInteractor = accessibilityTilesInteractor;
        this.autoAddInteractor = autoAddInteractor;
        this.settingsPackageRepository = qSSettingsPackageRepository;
        this.restoreReconciliationInteractor = restoreReconciliationInteractor;
        this.tilesBackUpRestoreInteractor = tilesBackUpRestoreInteractor;
        this.tileOrderLoggingInteractor = tileOrderLoggingInteractor;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        AccessibilityTilesInteractor accessibilityTilesInteractor = this.accessibilityTilesInteractor;
        CurrentTilesInteractor currentTilesInteractor = this.currentTilesInteractor;
        accessibilityTilesInteractor.init(currentTilesInteractor);
        this.autoAddInteractor.init(currentTilesInteractor);
        this.settingsPackageRepository.init();
        this.restoreReconciliationInteractor.start();
        final TilesBackUpRestoreInteractorImpl tilesBackUpRestoreInteractorImpl = (TilesBackUpRestoreInteractorImpl) this.tilesBackUpRestoreInteractor;
        tilesBackUpRestoreInteractorImpl.dumpManager.registerNormalDumpable("TileBackUpRestoreInteractor", tilesBackUpRestoreInteractorImpl);
        ((QSBackupRestoreManager) tilesBackUpRestoreInteractorImpl.qsBackupRestoreManager$delegate.getValue()).addCallback("TileList", new QSBackupRestoreManager.Callback() { // from class: com.android.systemui.qs.pipeline.data.domain.interactor.TilesBackUpRestoreInteractorImpl$init$1
            @Override // com.android.systemui.qs.QSBackupRestoreManager.Callback
            public final boolean isValidDB() {
                return true;
            }

            @Override // com.android.systemui.qs.QSBackupRestoreManager.Callback
            public final String onBackup(boolean z) {
                TilesBackUpRestoreInteractorImpl tilesBackUpRestoreInteractorImpl2 = tilesBackUpRestoreInteractorImpl;
                int userId = tilesBackUpRestoreInteractorImpl2.context.getUserId();
                SecureSettings secureSettings = tilesBackUpRestoreInteractorImpl2.secureSettings;
                String stringForUser = secureSettings.getStringForUser("sysui_qs_tiles", userId);
                if (stringForUser == null) {
                    stringForUser = "";
                }
                boolean z2 = Prefs.getBoolean(tilesBackUpRestoreInteractorImpl2.context, "QsHasEditedQuickTileList", false);
                Iterable iterable = (Iterable) ((RemovedTilesInteractorImpl) tilesBackUpRestoreInteractorImpl2.removedTilesInteractor).removedTiles.$$delegate_0.getValue();
                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(iterable, 10));
                Iterator it = iterable.iterator();
                while (it.hasNext()) {
                    arrayList.add(((TileSpec) it.next()).getSpec());
                }
                String stringForUser2 = secureSettings.getStringForUser("sysui_quick_qs_tiles", tilesBackUpRestoreInteractorImpl2.context.getUserId());
                String str = stringForUser2 != null ? stringForUser2 : "";
                boolean z3 = Prefs.getBoolean(tilesBackUpRestoreInteractorImpl2.context, "QQsHasEditedQuickTileList", false);
                StringBuilder sb = new StringBuilder("TAG::sep_version::");
                sb.append(Build.VERSION.SEM_PLATFORM_INT);
                sb.append("::TAG::has_edited::");
                sb.append(z2);
                sb.append("::TAG::removed_tile_list::");
                MoveResult$$ExternalSyntheticOutline0.m(sb, CollectionsKt___CollectionsKt.joinToString$default(arrayList, ",", null, null, null, 62), "::TAG::tile_list::", stringForUser, "::TAG::qqs_has_edited::");
                sb.append(z3);
                sb.append("::TAG::qqs_tile_list::");
                sb.append(str);
                String string = sb.toString();
                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("getBackUpData: ", string, "TileBackUpRestoreInteractor");
                return string;
            }

            @Override // com.android.systemui.qs.QSBackupRestoreManager.Callback
            public final void onRestore(String str) throws NumberFormatException {
                tilesBackUpRestoreInteractorImpl.setRestoreData(str);
            }
        });
        this.tileOrderLoggingInteractor.start();
    }
}
