package com.android.systemui.qs;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RemoteViews;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.settingslib.volume.MediaSessions$H$$ExternalSyntheticOutline0;
import com.android.systemui.qs.bar.QuickControlBar$$ExternalSyntheticLambda0;
import com.android.systemui.qs.bar.QuickControlBar$$ExternalSyntheticLambda1;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractor;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractorImpl;
import com.android.systemui.util.RecoilEffectUtil;
import com.android.systemui.util.ViewUtil;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

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
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.android.systemui.qs.SecSTQuickControlRequestReceiver.onReceive.1
            /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
            /* JADX WARN: Removed duplicated region for block: B:96:0x01ad  */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void run() {
                QuickControlBar$$ExternalSyntheticLambda1 quickControlBar$$ExternalSyntheticLambda1;
                QuickControlBar$$ExternalSyntheticLambda1 quickControlBar$$ExternalSyntheticLambda12;
                View view;
                View view2;
                QuickControlBar$$ExternalSyntheticLambda1 quickControlBar$$ExternalSyntheticLambda13;
                String action = intent.getAction();
                if (action == null || action.hashCode() != -1602036936 || !action.equals("com.android.systemui.qs.action.ST_QUICK_CONTROL")) {
                    SecSTQuickControlRequestReceiver secSTQuickControlRequestReceiver = this;
                    Intent intent2 = intent;
                    int i = SecSTQuickControlRequestReceiver.$r8$clinit;
                    secSTQuickControlRequestReceiver.getClass();
                    Uri data = intent2.getData();
                    String schemeSpecificPart = data != null ? data.getSchemeSpecificPart() : null;
                    MediaSessions$H$$ExternalSyntheticOutline0.m("onReceiveForPackage action = ", intent2.getAction(), ", pkgName = ", schemeSpecificPart, "SecSTQuickControlRequestReceiver");
                    if ("com.samsung.android.oneconnect".equals(schemeSpecificPart)) {
                        String action2 = intent2.getAction();
                        if (action2 != null) {
                            switch (action2.hashCode()) {
                                case -1001645458:
                                    if (action2.equals("android.intent.action.PACKAGES_SUSPENDED")) {
                                        QuickControlBar$$ExternalSyntheticLambda1 quickControlBar$$ExternalSyntheticLambda14 = secSTQuickControlRequestReceiver.hideBarRunnable;
                                        if (quickControlBar$$ExternalSyntheticLambda14 != null) {
                                            quickControlBar$$ExternalSyntheticLambda14.run();
                                            break;
                                        }
                                    }
                                    break;
                                case -810471698:
                                    if (action2.equals("android.intent.action.PACKAGE_REPLACED")) {
                                    }
                                    break;
                                case -757780528:
                                    if (action2.equals("android.intent.action.PACKAGE_RESTARTED")) {
                                    }
                                    break;
                                case 172491798:
                                    if (action2.equals("android.intent.action.PACKAGE_CHANGED") && intent2.hasExtra("android.intent.extra.DONT_KILL_APP") && (quickControlBar$$ExternalSyntheticLambda1 = secSTQuickControlRequestReceiver.hideBarRunnable) != null) {
                                        quickControlBar$$ExternalSyntheticLambda1.run();
                                        break;
                                    }
                                    break;
                                case 267468725:
                                    if (action2.equals("android.intent.action.PACKAGE_DATA_CLEARED")) {
                                    }
                                    break;
                                case 525384130:
                                    if (action2.equals("android.intent.action.PACKAGE_REMOVED")) {
                                    }
                                    break;
                                case 1290767157:
                                    if (action2.equals("android.intent.action.PACKAGES_UNSUSPENDED")) {
                                    }
                                    break;
                            }
                        }
                        secSTQuickControlRequestReceiver.newRemoteView = null;
                        secSTQuickControlRequestReceiver.currentRemoteView = null;
                        return;
                    }
                    return;
                }
                final SecSTQuickControlRequestReceiver secSTQuickControlRequestReceiver2 = this;
                Context context2 = context;
                Intent intent3 = intent;
                int i2 = SecSTQuickControlRequestReceiver.$r8$clinit;
                secSTQuickControlRequestReceiver2.getClass();
                boolean z = context2.getPackageManager().checkSignatures("android", "com.samsung.android.oneconnect") == 0;
                EmergencyButtonController$$ExternalSyntheticOutline0.m("onReceiveForRemoteViews isInstalledST = ", "SecSTQuickControlRequestReceiver", z);
                if (z) {
                    secSTQuickControlRequestReceiver2.newRemoteView = (RemoteViews) intent3.getParcelableExtra("REMOTE_VIEWS");
                    secSTQuickControlRequestReceiver2.clickPendingIntent = (PendingIntent) intent3.getParcelableExtra("CONTENT_CLICK");
                    PendingIntent pendingIntent = (PendingIntent) intent3.getParcelableExtra("CONTENT_LONG_CLICK");
                    secSTQuickControlRequestReceiver2.longClickPendingIntent = pendingIntent;
                    Log.d("SecSTQuickControlRequestReceiver", "clickPendingIntent = " + secSTQuickControlRequestReceiver2.clickPendingIntent + ", longClickPendingIntent = " + pendingIntent);
                    QuickControlBar$$ExternalSyntheticLambda0 quickControlBar$$ExternalSyntheticLambda0 = secSTQuickControlRequestReceiver2.isShowingSupplier;
                    Boolean bool = quickControlBar$$ExternalSyntheticLambda0 != null ? (Boolean) quickControlBar$$ExternalSyntheticLambda0.get() : null;
                    Log.d("SecSTQuickControlRequestReceiver", "onReceiveForRemoteViews intent = " + intent3 + ", newRemoteView = " + secSTQuickControlRequestReceiver2.newRemoteView + ", this = " + ViewUtil.INSTANCE.toShortIdSting(secSTQuickControlRequestReceiver2) + ", isShowing = " + bool);
                    if (secSTQuickControlRequestReceiver2.newRemoteView != null) {
                        if (Intrinsics.areEqual(bool, Boolean.FALSE) && (quickControlBar$$ExternalSyntheticLambda13 = secSTQuickControlRequestReceiver2.showBarRunnable) != null) {
                            quickControlBar$$ExternalSyntheticLambda13.run();
                        }
                    } else if (Intrinsics.areEqual(bool, Boolean.TRUE) && (quickControlBar$$ExternalSyntheticLambda12 = secSTQuickControlRequestReceiver2.hideBarRunnable) != null) {
                        quickControlBar$$ExternalSyntheticLambda12.run();
                    }
                    QuickControlBar$$ExternalSyntheticLambda0 quickControlBar$$ExternalSyntheticLambda02 = secSTQuickControlRequestReceiver2.expandedSupplier;
                    if (quickControlBar$$ExternalSyntheticLambda02 != null ? quickControlBar$$ExternalSyntheticLambda02.get().equals(Boolean.TRUE) : false) {
                        secSTQuickControlRequestReceiver2.updateRemoteView(context2);
                    }
                    final PendingIntent pendingIntent2 = secSTQuickControlRequestReceiver2.clickPendingIntent;
                    if (pendingIntent2 != null && (view2 = secSTQuickControlRequestReceiver2.rootView) != null) {
                        view2.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.SecSTQuickControlRequestReceiver$updateRootView$1$1
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view3) throws PendingIntent.CanceledException {
                                Log.d("SecSTQuickControlRequestReceiver", "start-ST app by onClick");
                                SecSTQuickControlRequestReceiver secSTQuickControlRequestReceiver3 = secSTQuickControlRequestReceiver2;
                                PendingIntent pendingIntent3 = pendingIntent2;
                                secSTQuickControlRequestReceiver3.getClass();
                                if (pendingIntent3 != null) {
                                    try {
                                        pendingIntent3.send();
                                        ((PanelInteractorImpl) secSTQuickControlRequestReceiver3.panelInteractor).collapsePanels();
                                    } catch (PendingIntent.CanceledException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                    }
                    final PendingIntent pendingIntent3 = secSTQuickControlRequestReceiver2.longClickPendingIntent;
                    if (pendingIntent3 != null && (view = secSTQuickControlRequestReceiver2.rootView) != null) {
                        view.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.systemui.qs.SecSTQuickControlRequestReceiver$updateRootView$2$1
                            @Override // android.view.View.OnLongClickListener
                            public final boolean onLongClick(View view3) throws PendingIntent.CanceledException {
                                Log.d("SecSTQuickControlRequestReceiver", "start-ST app by onLongClick");
                                SecSTQuickControlRequestReceiver secSTQuickControlRequestReceiver3 = secSTQuickControlRequestReceiver2;
                                PendingIntent pendingIntent4 = pendingIntent3;
                                secSTQuickControlRequestReceiver3.getClass();
                                if (pendingIntent4 == null) {
                                    return true;
                                }
                                try {
                                    pendingIntent4.send();
                                    ((PanelInteractorImpl) secSTQuickControlRequestReceiver3.panelInteractor).collapsePanels();
                                    return true;
                                } catch (PendingIntent.CanceledException e) {
                                    e.printStackTrace();
                                    return true;
                                }
                            }
                        });
                    }
                    View view3 = secSTQuickControlRequestReceiver2.rootView;
                    if (view3 != null) {
                        if (secSTQuickControlRequestReceiver2.clickPendingIntent == null && secSTQuickControlRequestReceiver2.longClickPendingIntent == null) {
                            view3.setStateListAnimator(null);
                        } else {
                            view3.setStateListAnimator(RecoilEffectUtil.getRecoilLargeAnimator(context2));
                        }
                    }
                }
            }
        });
    }

    public final void updateRemoteView(Context context) {
        View viewApply;
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
        if (remoteViews3 != null && (viewApply = remoteViews3.apply(context, this.remoteViewsContainer)) != null && (frameLayout = this.remoteViewsContainer) != null) {
            frameLayout.addView(viewApply);
        }
        this.currentRemoteView = this.newRemoteView;
    }
}
