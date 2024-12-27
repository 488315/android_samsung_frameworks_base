package com.android.systemui.communal.data.db;

import androidx.room.RoomDatabase;
import com.android.systemui.communal.widgets.CommunalWidgetHost;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.Logger;
import javax.inject.Provider;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

public final class DefaultWidgetPopulation extends RoomDatabase.Callback {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CoroutineScope applicationScope;
    public final CoroutineDispatcher bgDispatcher;
    public final Provider communalWidgetDaoProvider;
    public final CommunalWidgetHost communalWidgetHost;
    public final String[] defaultWidgets;
    public final Logger logger;

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

    public DefaultWidgetPopulation(CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, CommunalWidgetHost communalWidgetHost, Provider provider, String[] strArr, LogBuffer logBuffer) {
        this.applicationScope = coroutineScope;
        this.bgDispatcher = coroutineDispatcher;
        this.communalWidgetHost = communalWidgetHost;
        this.communalWidgetDaoProvider = provider;
        this.defaultWidgets = strArr;
        this.logger = new Logger(logBuffer, "DefaultWidgetPopulation");
    }

    @Override // androidx.room.RoomDatabase.Callback
    public final void onCreate() {
        BuildersKt.launch$default(this.applicationScope, null, null, new DefaultWidgetPopulation$onCreate$1(this, null), 3);
    }
}
