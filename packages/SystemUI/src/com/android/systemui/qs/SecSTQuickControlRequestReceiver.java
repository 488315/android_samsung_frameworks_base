package com.android.systemui.qs;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RemoteViews;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.systemui.qs.bar.QuickControlBar$$ExternalSyntheticLambda0;
import com.android.systemui.qs.bar.QuickControlBar$$ExternalSyntheticLambda1;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractor;
import com.android.systemui.util.ViewUtil;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SecSTQuickControlRequestReceiver extends BroadcastReceiver {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Lazy broadcastDispatcher$delegate = LazyKt__LazyJVMKt.lazy(new SecSTQuickControlRequestReceiver$$ExternalSyntheticLambda0());
    public PendingIntent clickPendingIntent;
    public RemoteViews currentRemoteView;
    public QuickControlBar$$ExternalSyntheticLambda0 expandedSupplier;
    public final IntentFilter filter;
    public final Handler handler;
    public QuickControlBar$$ExternalSyntheticLambda1 hideBarRunnable;
    public QuickControlBar$$ExternalSyntheticLambda0 isShowingSupplier;
    public PendingIntent longClickPendingIntent;
    public RemoteViews newRemoteView;
    public final PanelInteractor panelInteractor;
    public FrameLayout remoteViewsContainer;
    public View rootView;
    public QuickControlBar$$ExternalSyntheticLambda1 showBarRunnable;

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

    public SecSTQuickControlRequestReceiver(Handler handler, PanelInteractor panelInteractor) {
        this.handler = handler;
        this.panelInteractor = panelInteractor;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.android.systemui.qs.action.ST_QUICK_CONTROL");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_RESTARTED");
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
        intentFilter.addAction("android.intent.action.PACKAGES_SUSPENDED");
        intentFilter.addAction("android.intent.action.PACKAGES_UNSUSPENDED");
        intentFilter.addAction("android.intent.action.PACKAGE_DATA_CLEARED");
        intentFilter.addDataScheme("package");
        this.filter = intentFilter;
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(final Context context, final Intent intent) {
        if (intent == null || context == null) {
            return;
        }
        Log.d("SecSTQuickControlRequestReceiver", "onReceive intent = " + intent);
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.android.systemui.qs.SecSTQuickControlRequestReceiver$onReceive$1
            /* JADX WARN: Code restructure failed: missing block: B:67:0x0164, code lost:
            
                if (r2.equals("android.intent.action.PACKAGES_UNSUSPENDED") == false) goto L99;
             */
            /* JADX WARN: Code restructure failed: missing block: B:68:0x01ad, code lost:
            
                r10 = r0.hideBarRunnable;
             */
            /* JADX WARN: Code restructure failed: missing block: B:69:0x01af, code lost:
            
                if (r10 == null) goto L99;
             */
            /* JADX WARN: Code restructure failed: missing block: B:70:0x01b1, code lost:
            
                r10.run();
             */
            /* JADX WARN: Code restructure failed: missing block: B:72:0x016d, code lost:
            
                if (r2.equals("android.intent.action.PACKAGE_REMOVED") == false) goto L99;
             */
            /* JADX WARN: Code restructure failed: missing block: B:74:0x0176, code lost:
            
                if (r2.equals("android.intent.action.PACKAGE_DATA_CLEARED") == false) goto L99;
             */
            /* JADX WARN: Code restructure failed: missing block: B:83:0x0198, code lost:
            
                if (r2.equals("android.intent.action.PACKAGE_RESTARTED") == false) goto L99;
             */
            /* JADX WARN: Code restructure failed: missing block: B:85:0x01a1, code lost:
            
                if (r2.equals("android.intent.action.PACKAGE_REPLACED") != false) goto L96;
             */
            /* JADX WARN: Code restructure failed: missing block: B:87:0x01aa, code lost:
            
                if (r2.equals("android.intent.action.PACKAGES_SUSPENDED") == false) goto L99;
             */
            /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void run() {
                /*
                    Method dump skipped, instructions count: 472
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.SecSTQuickControlRequestReceiver$onReceive$1.run():void");
            }
        });
    }

    public final void updateRemoteView(Context context) {
        View apply;
        FrameLayout frameLayout;
        RemoteViews remoteViews = this.newRemoteView;
        RemoteViews remoteViews2 = this.currentRemoteView;
        String shortIdSting = ViewUtil.INSTANCE.toShortIdSting(this);
        StringBuilder sb = new StringBuilder("updateRemoteView newRemoteView = ");
        sb.append(remoteViews);
        sb.append(", currentRemoteView = ");
        sb.append(remoteViews2);
        sb.append(", this = ");
        ExifInterface$$ExternalSyntheticOutline0.m(sb, shortIdSting, "SecSTQuickControlRequestReceiver");
        if (Intrinsics.areEqual(this.newRemoteView, this.currentRemoteView)) {
            return;
        }
        FrameLayout frameLayout2 = this.remoteViewsContainer;
        if (frameLayout2 != null) {
            frameLayout2.removeAllViews();
        }
        RemoteViews remoteViews3 = this.newRemoteView;
        if (remoteViews3 != null && (apply = remoteViews3.apply(context, this.remoteViewsContainer)) != null && (frameLayout = this.remoteViewsContainer) != null) {
            frameLayout.addView(apply);
        }
        this.currentRemoteView = this.newRemoteView;
    }
}
