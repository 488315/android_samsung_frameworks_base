package com.android.systemui.qs.cinema;

import android.view.View;
import com.android.systemui.logging.PanelScreenShotLogger;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.SecQSPanelController;
import com.android.systemui.qs.cinema.QSCinemaLogger;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class QSCinemaCompany implements QSHost.Callback, View.OnLayoutChangeListener, View.OnAttachStateChangeListener {
    public final QSCinemaDirector mDirector;
    public final QSCinemaLogger mLogger;
    public final QSCinemaProvider mProvider;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ProviderToCompanyCallback {
        public ProviderToCompanyCallback(QSCinemaCompany qSCinemaCompany) {
        }
    }

    public QSCinemaCompany(QSCinemaDirector qSCinemaDirector, QSCinemaProvider qSCinemaProvider, QSCinemaLogger qSCinemaLogger) {
        this.mDirector = qSCinemaDirector;
        this.mProvider = qSCinemaProvider;
        new ProviderToCompanyCallback(this);
        qSCinemaProvider.getClass();
        this.mLogger = qSCinemaLogger;
        qSCinemaProvider.mQSTileHost.addCallback(this);
        SecQSPanelController secQSPanelController = qSCinemaProvider.mQSPanelController;
        View view = secQSPanelController.mView;
        if (view != null) {
            view.addOnAttachStateChangeListener(this);
        }
        qSCinemaProvider.mQs.getView().addOnLayoutChangeListener(this);
        View view2 = secQSPanelController.mView;
        if (view2 != null && view2.isAttachedToWindow()) {
            onViewAttachedToWindow(null);
        }
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewAttachedToWindow(View view) {
        QSCinemaLogger qSCinemaLogger = this.mLogger;
        qSCinemaLogger.getClass();
        PanelScreenShotLogger.INSTANCE.addLogProvider("QSCinemaLogger", new QSCinemaLogger.ScreenShotLogProvider(qSCinemaLogger, 0));
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewDetachedFromWindow(View view) {
        this.mProvider.mQSTileHost.removeCallback(this);
        this.mLogger.getClass();
        synchronized (PanelScreenShotLogger.INSTANCE) {
            PanelScreenShotLogger.providers.remove("QSCinemaLogger");
        }
    }

    @Override // com.android.systemui.qs.QSHost.Callback
    public final void onTilesChanged() {
    }

    @Override // android.view.View.OnLayoutChangeListener
    public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
    }
}
