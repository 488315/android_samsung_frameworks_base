package com.android.systemui.qs;

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
import com.android.systemui.Dependency;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.util.ViewUtil;
import java.util.function.Supplier;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class SecSTQuickControlRequestReceiver extends BroadcastReceiver {
    public static final /* synthetic */ int $r8$clinit = 0;
    public RemoteViews currentRemoteView;
    public Supplier expandedSupplier;
    public final IntentFilter filter;
    public Runnable hideBarRunnable;
    public Supplier isShowingSupplier;
    public RemoteViews newRemoteView;
    public FrameLayout remoteViewsContainer;
    public Runnable showBarRunnable;
    public final Lazy broadcastDispatcher$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.SecSTQuickControlRequestReceiver$broadcastDispatcher$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return (BroadcastDispatcher) Dependency.sDependency.getDependencyInner(BroadcastDispatcher.class);
        }
    });
    public final Lazy handler$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.SecSTQuickControlRequestReceiver$handler$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return (Handler) Dependency.sDependency.getDependencyInner(Dependency.BG_HANDLER);
        }
    });

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

    public SecSTQuickControlRequestReceiver() {
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
            /* JADX WARN: Code restructure failed: missing block: B:41:0x00e6, code lost:
            
                if (r1.equals("android.intent.action.PACKAGES_UNSUSPENDED") == false) goto L71;
             */
            /* JADX WARN: Code restructure failed: missing block: B:42:0x012f, code lost:
            
                r8 = r0.hideBarRunnable;
             */
            /* JADX WARN: Code restructure failed: missing block: B:43:0x0131, code lost:
            
                if (r8 == null) goto L71;
             */
            /* JADX WARN: Code restructure failed: missing block: B:44:0x0133, code lost:
            
                r8.run();
             */
            /* JADX WARN: Code restructure failed: missing block: B:46:0x00ef, code lost:
            
                if (r1.equals("android.intent.action.PACKAGE_REMOVED") == false) goto L71;
             */
            /* JADX WARN: Code restructure failed: missing block: B:48:0x00f8, code lost:
            
                if (r1.equals("android.intent.action.PACKAGE_DATA_CLEARED") == false) goto L71;
             */
            /* JADX WARN: Code restructure failed: missing block: B:57:0x011a, code lost:
            
                if (r1.equals("android.intent.action.PACKAGE_RESTARTED") == false) goto L71;
             */
            /* JADX WARN: Code restructure failed: missing block: B:59:0x0123, code lost:
            
                if (r1.equals("android.intent.action.PACKAGE_REPLACED") != false) goto L68;
             */
            /* JADX WARN: Code restructure failed: missing block: B:61:0x012c, code lost:
            
                if (r1.equals("android.intent.action.PACKAGES_SUSPENDED") == false) goto L71;
             */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void run() {
                /*
                    Method dump skipped, instructions count: 346
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
