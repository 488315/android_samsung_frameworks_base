package com.android.systemui.media.mediaoutput.activity;

import android.content.Context;
import android.widget.PopupWindow;
import javax.inject.Provider;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.StandaloneCoroutine;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class MediaOutputWindow {
    public static final Companion Companion = new Companion(null);
    public final Context context;
    public StandaloneCoroutine job;
    public final Provider mediaOutputViewProvider;
    public PopupWindow popupWindow;
    public final Lazy params$delegate = LazyKt__LazyJVMKt.lazy(new MediaOutputWindow$$ExternalSyntheticLambda0());
    public final Lazy isNightMode$delegate = LazyKt__LazyJVMKt.lazy(new MediaOutputWindow$$ExternalSyntheticLambda1(this, 0));

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public MediaOutputWindow(Context context, Provider provider) {
        this.context = context;
        this.mediaOutputViewProvider = provider;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0068  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void show(android.content.Intent r6) {
        /*
            r5 = this;
            r0 = 1
            com.android.systemui.media.mediaoutput.compose.common.Feature$Builder r1 = new com.android.systemui.media.mediaoutput.compose.common.Feature$Builder
            r1.<init>()
            java.lang.String r2 = "extra_from"
            r3 = -1
            int r2 = r6.getIntExtra(r2, r3)
            com.android.systemui.media.mediaoutput.compose.common.Feature r3 = r1.getFeature()
            r3.from = r2
            com.android.systemui.media.mediaoutput.compose.common.Feature r2 = r1.getFeature()
            r2.isWindow = r0
            com.android.systemui.media.mediaoutput.activity.MediaOutputWindow$$ExternalSyntheticLambda1 r2 = new com.android.systemui.media.mediaoutput.activity.MediaOutputWindow$$ExternalSyntheticLambda1
            r2.<init>(r5, r0)
            com.android.systemui.media.mediaoutput.compose.common.Feature r3 = r1.getFeature()
            r3.dismissCallback = r2
            java.lang.String r2 = "extra_device_ids"
            java.util.ArrayList r6 = r6.getStringArrayListExtra(r2)
            r2 = 0
            if (r6 == 0) goto L4f
            boolean r3 = r6.isEmpty()
            if (r3 != 0) goto L34
            goto L35
        L34:
            r6 = r2
        L35:
            if (r6 == 0) goto L4f
            com.android.systemui.media.mediaoutput.compose.common.Feature r3 = r1.getFeature()
            int r4 = r6.size()
            if (r4 <= r0) goto L44
            com.android.systemui.media.mediaoutput.compose.Screen$Selector r0 = com.android.systemui.media.mediaoutput.compose.Screen.Selector.INSTANCE
            goto L46
        L44:
            com.android.systemui.media.mediaoutput.compose.Screen$TV r0 = com.android.systemui.media.mediaoutput.compose.Screen.TV.INSTANCE
        L46:
            r3.defaultScreen = r0
            com.android.systemui.media.mediaoutput.compose.common.Feature r0 = r1.getFeature()
            r0.deviceIds = r6
            goto L57
        L4f:
            com.android.systemui.media.mediaoutput.compose.common.Feature r6 = r1.getFeature()
            com.android.systemui.media.mediaoutput.compose.Screen$Phone r0 = com.android.systemui.media.mediaoutput.compose.Screen.Phone.INSTANCE
            r6.defaultScreen = r0
        L57:
            com.android.systemui.media.mediaoutput.compose.common.Feature r6 = r1.getFeature()
            android.widget.PopupWindow r0 = r5.popupWindow
            java.lang.String r1 = "MediaOutputWindow"
            if (r0 == 0) goto L68
            java.lang.String r5 = "show() - already shown"
            android.util.Log.d(r1, r5)
            return
        L68:
            java.lang.String r0 = "show()"
            android.util.Log.d(r1, r0)
            javax.inject.Provider r0 = r5.mediaOutputViewProvider
            java.lang.Object r0 = r0.get()
            com.android.systemui.media.MediaOutputView r0 = (com.android.systemui.media.MediaOutputView) r0
            r1 = 16908290(0x1020002, float:2.3877235E-38)
            r0.setId(r1)
            r0.feature = r6
            boolean r6 = com.android.systemui.QpRune.QUICK_PANEL_BLUR_DEFAULT
            if (r6 == 0) goto Lac
            android.view.SemBlurInfo$Builder r6 = new android.view.SemBlurInfo$Builder
            r1 = 0
            r6.<init>(r1)
            kotlin.Lazy r1 = r5.isNightMode$delegate
            java.lang.Object r1 = r1.getValue()
            java.lang.Boolean r1 = (java.lang.Boolean) r1
            boolean r1 = r1.booleanValue()
            if (r1 == 0) goto L99
            r1 = 135(0x87, float:1.89E-43)
            goto L9b
        L99:
            r1 = 132(0x84, float:1.85E-43)
        L9b:
            android.view.SemBlurInfo$Builder r6 = r6.setColorCurvePreset(r1)
            r1 = 0
            android.view.SemBlurInfo$Builder r6 = r6.setBackgroundCornerRadius(r1)
            android.view.SemBlurInfo r6 = r6.build()
            r0.semSetBlurInfo(r6)
            goto Lb5
        Lac:
            java.lang.String r6 = "#5D5D5D"
            int r6 = android.graphics.Color.parseColor(r6)
            r0.setBackgroundColor(r6)
        Lb5:
            android.widget.PopupWindow r6 = new android.widget.PopupWindow
            r6.<init>(r0)
            com.android.systemui.media.mediaoutput.activity.MediaOutputWindow$show$2$1$1 r0 = new com.android.systemui.media.mediaoutput.activity.MediaOutputWindow$show$2$1$1
            r0.<init>()
            r6.setOnDismissListener(r0)
            r5.popupWindow = r6
            kotlinx.coroutines.scheduling.DefaultScheduler r6 = kotlinx.coroutines.Dispatchers.Default
            kotlinx.coroutines.android.HandlerContext r6 = kotlinx.coroutines.internal.MainDispatcherLoader.dispatcher
            kotlinx.coroutines.internal.ContextScope r6 = kotlinx.coroutines.CoroutineScopeKt.CoroutineScope(r6)
            com.android.systemui.media.mediaoutput.activity.MediaOutputWindow$show$2$2 r0 = new com.android.systemui.media.mediaoutput.activity.MediaOutputWindow$show$2$2
            r0.<init>(r5, r2)
            r1 = 3
            kotlinx.coroutines.StandaloneCoroutine r6 = kotlinx.coroutines.BuildersKt.launch$default(r6, r2, r2, r0, r1)
            r5.job = r6
            android.widget.PopupWindow r6 = r5.popupWindow
            if (r6 != 0) goto Ldd
            goto Lde
        Ldd:
            r2 = r6
        Lde:
            kotlin.Lazy r5 = r5.params$delegate
            java.lang.Object r5 = r5.getValue()
            android.view.WindowManager$LayoutParams r5 = (android.view.WindowManager.LayoutParams) r5
            r2.semShowPopupWindow(r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.activity.MediaOutputWindow.show(android.content.Intent):void");
    }
}
