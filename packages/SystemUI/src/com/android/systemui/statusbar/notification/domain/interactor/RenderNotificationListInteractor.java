package com.android.systemui.statusbar.notification.domain.interactor;

import android.content.Context;
import android.os.Trace;
import com.android.app.tracing.TraceUtilsKt;
import com.android.systemui.statusbar.notification.collection.provider.SectionStyleProvider;
import com.android.systemui.statusbar.notification.data.repository.ActiveNotificationListRepository;
import com.android.systemui.statusbar.notification.data.repository.ActiveNotificationsStore;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class RenderNotificationListInteractor {
    public final Context context;
    public final ActiveNotificationListRepository repository;
    public final SectionStyleProvider sectionStyleProvider;

    public RenderNotificationListInteractor(ActiveNotificationListRepository activeNotificationListRepository, SectionStyleProvider sectionStyleProvider, Context context) {
        this.repository = activeNotificationListRepository;
        this.sectionStyleProvider = sectionStyleProvider;
        this.context = context;
    }

    public final void setRenderedList(final List list) {
        Object value;
        ActiveNotificationsStore.Builder builder;
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("RenderNotificationListInteractor.setRenderedList");
        }
        try {
            StateFlowImpl stateFlowImpl = this.repository.activeNotifications;
            do {
                value = stateFlowImpl.getValue();
                SectionStyleProvider sectionStyleProvider = this.sectionStyleProvider;
                Context context = this.context;
                Function1 function1 = new Function1() { // from class: com.android.systemui.statusbar.notification.domain.interactor.RenderNotificationListInteractor$$ExternalSyntheticLambda0
                    /* JADX WARN: Code restructure failed: missing block: B:31:0x007d, code lost:
                    
                        if (r4 == null) goto L25;
                     */
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object mo779invoke(java.lang.Object r8) {
                        /*
                            Method dump skipped, instructions count: 341
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.domain.interactor.RenderNotificationListInteractor$$ExternalSyntheticLambda0.mo779invoke(java.lang.Object):java.lang.Object");
                    }
                };
                ActiveNotificationsStoreBuilder activeNotificationsStoreBuilder = new ActiveNotificationsStoreBuilder((ActiveNotificationsStore) value, sectionStyleProvider, context);
                function1.mo779invoke(activeNotificationsStoreBuilder);
                builder = activeNotificationsStoreBuilder.builder;
            } while (!stateFlowImpl.compareAndSet(value, new ActiveNotificationsStore(builder.groups, builder.individuals, builder.renderList, builder.rankingsMap)));
            Unit unit = Unit.INSTANCE;
        } finally {
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
        }
    }
}
