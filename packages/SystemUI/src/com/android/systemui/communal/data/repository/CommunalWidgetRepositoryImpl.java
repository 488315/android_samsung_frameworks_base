package com.android.systemui.communal.data.repository;

import android.app.backup.BackupManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.ComponentName;
import android.os.UserHandle;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.room.coroutines.FlowUtil;
import com.android.systemui.common.data.repository.PackageChangeRepository;
import com.android.systemui.communal.data.backup.CommunalBackupUtils;
import com.android.systemui.communal.data.db.CommunalWidgetDao;
import com.android.systemui.communal.data.db.CommunalWidgetDao_Impl;
import com.android.systemui.communal.data.db.CommunalWidgetDao_Impl$$ExternalSyntheticLambda1;
import com.android.systemui.communal.widgets.CommunalAppWidgetHost;
import com.android.systemui.communal.widgets.CommunalWidgetHost;
import com.android.systemui.communal.widgets.WidgetConfigurator;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.Logger;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class CommunalWidgetRepositoryImpl implements CommunalWidgetRepository {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CommunalAppWidgetHost appWidgetHost;
    public final BackupManager backupManager;
    public final CommunalBackupUtils backupUtils;
    public final CoroutineScope bgScope;
    public final CommunalWidgetDao communalWidgetDao;
    public final CommunalWidgetHost communalWidgetHost;
    public final Flow communalWidgets;
    public final Logger logger;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 widgetEntries;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public CommunalWidgetRepositoryImpl(CommunalAppWidgetHost communalAppWidgetHost, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, CommunalWidgetHost communalWidgetHost, CommunalWidgetDao communalWidgetDao, LogBuffer logBuffer, BackupManager backupManager, CommunalBackupUtils communalBackupUtils, PackageChangeRepository packageChangeRepository) {
        this.appWidgetHost = communalAppWidgetHost;
        this.bgScope = coroutineScope;
        this.communalWidgetHost = communalWidgetHost;
        this.communalWidgetDao = communalWidgetDao;
        this.backupManager = backupManager;
        this.backupUtils = communalBackupUtils;
        this.logger = new Logger(logBuffer, "CommunalWidgetRepository");
        CommunalWidgetDao_Impl$$ExternalSyntheticLambda1 communalWidgetDao_Impl$$ExternalSyntheticLambda1 = new CommunalWidgetDao_Impl$$ExternalSyntheticLambda1(0);
        this.communalWidgets = FlowKt.flowOn(FlowKt.transformLatest(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(FlowUtil.createFlow(((CommunalWidgetDao_Impl) communalWidgetDao).__db, new String[]{"communal_widget_table", "communal_item_rank_table"}, communalWidgetDao_Impl$$ExternalSyntheticLambda1), communalWidgetHost.appWidgetProviders, new CommunalWidgetRepositoryImpl$widgetEntries$1(null)), new CommunalWidgetRepositoryImpl$special$$inlined$flatMapLatest$1(null, packageChangeRepository, this)), coroutineDispatcher);
    }

    public final void abortRestoreWidgets() {
        BuildersKt.launch$default(this.bgScope, null, null, new CommunalWidgetRepositoryImpl$abortRestoreWidgets$1(this, null), 3);
    }

    public final void addWidget(ComponentName componentName, UserHandle userHandle, int i, WidgetConfigurator widgetConfigurator) {
        BuildersKt.launch$default(this.bgScope, null, null, new CommunalWidgetRepositoryImpl$addWidget$1(this, componentName, userHandle, widgetConfigurator, i, null), 3);
    }

    public final void deleteWidget(int i) {
        BuildersKt.launch$default(this.bgScope, null, null, new CommunalWidgetRepositoryImpl$deleteWidget$1(this, i, null), 3);
    }

    public final void restoreWidgets(Map map) {
        BuildersKt.launch$default(this.bgScope, null, null, new CommunalWidgetRepositoryImpl$restoreWidgets$1(this, map, null), 3);
    }

    public final void updateWidgetOrder(Map map) {
        BuildersKt.launch$default(this.bgScope, null, null, new CommunalWidgetRepositoryImpl$updateWidgetOrder$1(this, map, null), 3);
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class CommunalWidgetEntry {
        public final int appWidgetId;
        public final String componentName;
        public final int priority;
        public final AppWidgetProviderInfo providerInfo;

        public CommunalWidgetEntry(int i, String str, int i2, AppWidgetProviderInfo appWidgetProviderInfo) {
            this.appWidgetId = i;
            this.componentName = str;
            this.priority = i2;
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
            return this.appWidgetId == communalWidgetEntry.appWidgetId && Intrinsics.areEqual(this.componentName, communalWidgetEntry.componentName) && this.priority == communalWidgetEntry.priority && Intrinsics.areEqual(this.providerInfo, communalWidgetEntry.providerInfo);
        }

        public final int hashCode() {
            int m = KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.priority, PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(Integer.hashCode(this.appWidgetId) * 31, 31, this.componentName), 31);
            AppWidgetProviderInfo appWidgetProviderInfo = this.providerInfo;
            return m + (appWidgetProviderInfo == null ? 0 : appWidgetProviderInfo.hashCode());
        }

        public final String toString() {
            return "CommunalWidgetEntry(appWidgetId=" + this.appWidgetId + ", componentName=" + this.componentName + ", priority=" + this.priority + ", providerInfo=" + this.providerInfo + ")";
        }

        public /* synthetic */ CommunalWidgetEntry(int i, String str, int i2, AppWidgetProviderInfo appWidgetProviderInfo, int i3, DefaultConstructorMarker defaultConstructorMarker) {
            this(i, str, i2, (i3 & 8) != 0 ? null : appWidgetProviderInfo);
        }
    }
}
