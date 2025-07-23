package com.android.systemui.communal.data.db;

import android.os.UserManager;
import androidx.room.RoomDatabase;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.communal.widgets.CommunalWidgetHost;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.Logger;
import com.android.systemui.user.domain.interactor.UserLockedInteractor;
import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import javax.inject.Provider;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DefaultWidgetPopulation extends RoomDatabase.Callback {
    public final CoroutineScope bgScope;
    public final Provider communalWidgetDaoProvider;
    public final CommunalWidgetHost communalWidgetHost;
    public final String[] defaultWidgets;
    public final Logger logger;
    public SkipReason skipReason = SkipReason.NONE;
    public final UserLockedInteractor userLockedInteractor;
    public final UserManager userManager;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class SkipReason {
        public static final /* synthetic */ SkipReason[] $VALUES;
        public static final SkipReason NONE;
        public static final SkipReason RESTORED_FROM_BACKUP;

        static {
            SkipReason skipReason = new SkipReason(PeripheralBarcodeConstants.Symbology.Type.TYPE_NONE, 0);
            NONE = skipReason;
            SkipReason skipReason2 = new SkipReason("RESTORED_FROM_BACKUP", 1);
            RESTORED_FROM_BACKUP = skipReason2;
            SkipReason[] skipReasonArr = {skipReason, skipReason2};
            $VALUES = skipReasonArr;
            EnumEntriesKt.enumEntries(skipReasonArr);
        }

        private SkipReason(String str, int i) {
        }

        public static SkipReason valueOf(String str) {
            return (SkipReason) Enum.valueOf(SkipReason.class, str);
        }

        public static SkipReason[] values() {
            return (SkipReason[]) $VALUES.clone();
        }
    }

    static {
        new Companion(null);
    }

    public DefaultWidgetPopulation(CoroutineScope coroutineScope, CommunalWidgetHost communalWidgetHost, Provider provider, String[] strArr, LogBuffer logBuffer, UserManager userManager, UserLockedInteractor userLockedInteractor) {
        this.bgScope = coroutineScope;
        this.communalWidgetHost = communalWidgetHost;
        this.communalWidgetDaoProvider = provider;
        this.defaultWidgets = strArr;
        this.userManager = userManager;
        this.userLockedInteractor = userLockedInteractor;
        this.logger = new Logger(logBuffer, "DefaultWidgetPopulation");
    }

    @Override // androidx.room.RoomDatabase.Callback
    public final void onCreate() {
        SkipReason skipReason = this.skipReason;
        if (skipReason == SkipReason.NONE) {
            CoroutineTracingKt.launchTraced$default(this.bgScope, null, null, new DefaultWidgetPopulation$onCreate$1(this, null), 7);
        } else {
            Logger.i$default(this.logger, "Skipped populating default widgets. Reason: " + skipReason, null, 2, null);
        }
    }
}
