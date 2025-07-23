package com.android.systemui.communal.data.repository;

import android.app.backup.BackupManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.ComponentName;
import android.os.UserHandle;
import android.os.UserManager;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.common.data.repository.PackageChangeRepository;
import com.android.systemui.communal.data.backup.CommunalBackupUtils;
import com.android.systemui.communal.data.db.CommunalWidgetDao;
import com.android.systemui.communal.data.db.CommunalWidgetDao_Impl;
import com.android.systemui.communal.data.db.DefaultWidgetPopulation;
import com.android.systemui.communal.shared.model.SpanValue;
import com.android.systemui.communal.widgets.CommunalAppWidgetHost;
import com.android.systemui.communal.widgets.CommunalWidgetHost;
import com.android.systemui.communal.widgets.WidgetConfigurator;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.Logger;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CommunalWidgetRepositoryLocalImpl implements CommunalWidgetRepository {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CommunalAppWidgetHost appWidgetHost;
    public final BackupManager backupManager;
    public final CommunalBackupUtils backupUtils;
    public final CoroutineScope bgScope;
    public final CommunalWidgetDao communalWidgetDao;
    public final CommunalWidgetHost communalWidgetHost;
    public final Flow communalWidgets;
    public final DefaultWidgetPopulation defaultWidgetPopulation;
    public final Logger logger;
    public final UserManager userManager;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 widgetEntries;

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

    public CommunalWidgetRepositoryLocalImpl(CommunalAppWidgetHost communalAppWidgetHost, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, CommunalWidgetHost communalWidgetHost, CommunalWidgetDao communalWidgetDao, LogBuffer logBuffer, BackupManager backupManager, CommunalBackupUtils communalBackupUtils, PackageChangeRepository packageChangeRepository, UserManager userManager, DefaultWidgetPopulation defaultWidgetPopulation) {
        this.appWidgetHost = communalAppWidgetHost;
        this.bgScope = coroutineScope;
        this.communalWidgetHost = communalWidgetHost;
        this.communalWidgetDao = communalWidgetDao;
        this.backupManager = backupManager;
        this.backupUtils = communalBackupUtils;
        this.userManager = userManager;
        this.defaultWidgetPopulation = defaultWidgetPopulation;
        this.logger = new Logger(logBuffer, "CommunalWidgetRepositoryLocalImpl");
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(((CommunalWidgetDao_Impl) communalWidgetDao).getWidgets(), communalWidgetHost.appWidgetProviders, new CommunalWidgetRepositoryLocalImpl$widgetEntries$1(null));
        this.widgetEntries = flowKt__ZipKt$combine$$inlined$unsafeFlow$1;
        this.communalWidgets = FlowKt.flowOn(FlowKt.transformLatest(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, new CommunalWidgetRepositoryLocalImpl$special$$inlined$flatMapLatest$1(null, packageChangeRepository, this)), coroutineDispatcher);
    }

    @Override // com.android.systemui.communal.data.repository.CommunalWidgetRepository
    public final void abortRestoreWidgets() {
        CoroutineTracingKt.launchTraced$default(this.bgScope, null, null, new CommunalWidgetRepositoryLocalImpl$abortRestoreWidgets$1(this, null), 7);
    }

    @Override // com.android.systemui.communal.data.repository.CommunalWidgetRepository
    public final void addWidget(ComponentName componentName, UserHandle userHandle, Integer num, WidgetConfigurator widgetConfigurator) {
        CoroutineTracingKt.launchTraced$default(this.bgScope, null, null, new CommunalWidgetRepositoryLocalImpl$addWidget$1(this, componentName, userHandle, widgetConfigurator, num, null), 7);
    }

    @Override // com.android.systemui.communal.data.repository.CommunalWidgetRepository
    public final void deleteWidget(int i) {
        CoroutineTracingKt.launchTraced$default(this.bgScope, null, null, new CommunalWidgetRepositoryLocalImpl$deleteWidget$1(this, i, null), 7);
    }

    @Override // com.android.systemui.communal.data.repository.CommunalWidgetRepository
    public final Flow getCommunalWidgets() {
        return this.communalWidgets;
    }

    @Override // com.android.systemui.communal.data.repository.CommunalWidgetRepository
    public final void resizeWidget(Map map, int i, int i2) {
        CoroutineTracingKt.launchTraced$default(this.bgScope, null, null, new CommunalWidgetRepositoryLocalImpl$resizeWidget$1(this, i, SpanValue.Fixed.m1075boximpl(i2), map, i2, null), 7);
    }

    @Override // com.android.systemui.communal.data.repository.CommunalWidgetRepository
    public final void restoreWidgets(Map map) {
        CoroutineTracingKt.launchTraced$default(this.bgScope, null, null, new CommunalWidgetRepositoryLocalImpl$restoreWidgets$1(this, map, null), 7);
    }

    @Override // com.android.systemui.communal.data.repository.CommunalWidgetRepository
    public final void updateWidgetOrder(Map map) {
        CoroutineTracingKt.launchTraced$default(this.bgScope, null, null, new CommunalWidgetRepositoryLocalImpl$updateWidgetOrder$1(this, map, null), 7);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class CommunalWidgetEntry {
        public final int appWidgetId;
        public final String componentName;
        public final AppWidgetProviderInfo providerInfo;
        public final int rank;
        public final int spanY;

        public CommunalWidgetEntry(int i, String str, int i2, int i3, AppWidgetProviderInfo appWidgetProviderInfo) {
            this.appWidgetId = i;
            this.componentName = str;
            this.rank = i2;
            this.spanY = i3;
            this.providerInfo = appWidgetProviderInfo;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof CommunalWidgetEntry)) {
                return false;
            }
            CommunalWidgetEntry communalWidgetEntry = (CommunalWidgetEntry) obj;
            return this.appWidgetId == communalWidgetEntry.appWidgetId && Intrinsics.areEqual(this.componentName, communalWidgetEntry.componentName) && this.rank == communalWidgetEntry.rank && this.spanY == communalWidgetEntry.spanY && Intrinsics.areEqual(this.providerInfo, communalWidgetEntry.providerInfo);
        }

        public final int hashCode() {
            int m = ReorderTile$$ExternalSyntheticOutline0.m(this.spanY, ReorderTile$$ExternalSyntheticOutline0.m(this.rank, PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(Integer.hashCode(this.appWidgetId) * 31, 31, this.componentName), 31), 31);
            AppWidgetProviderInfo appWidgetProviderInfo = this.providerInfo;
            return m + (appWidgetProviderInfo == null ? 0 : appWidgetProviderInfo.hashCode());
        }

        public final String toString() {
            return "CommunalWidgetEntry(appWidgetId=" + this.appWidgetId + ", componentName=" + this.componentName + ", rank=" + this.rank + ", spanY=" + this.spanY + ", providerInfo=" + this.providerInfo + ")";
        }

        public /* synthetic */ CommunalWidgetEntry(int i, String str, int i2, int i3, AppWidgetProviderInfo appWidgetProviderInfo, int i4, DefaultConstructorMarker defaultConstructorMarker) {
            this(i, str, i2, i3, (i4 & 16) != 0 ? null : appWidgetProviderInfo);
        }
    }
}
