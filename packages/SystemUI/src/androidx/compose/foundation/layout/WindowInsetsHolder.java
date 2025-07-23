package androidx.compose.foundation.layout;

import android.view.View;
import androidx.compose.runtime.snapshots.Snapshot;
import androidx.core.graphics.Insets;
import androidx.core.view.DisplayCutoutCompat;
import androidx.core.view.WindowInsetsCompat;
import com.android.systemui.R;
import java.util.WeakHashMap;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class WindowInsetsHolder {
    public static final Companion Companion = new Companion(null);
    public static final WeakHashMap viewMap = new WeakHashMap();
    public int accessCount;
    public final AndroidWindowInsets captionBar;
    public final ValueInsets captionBarIgnoringVisibility;
    public final boolean consumes;
    public final AndroidWindowInsets displayCutout;
    public final AndroidWindowInsets ime;
    public final ValueInsets imeAnimationSource;
    public final ValueInsets imeAnimationTarget;
    public final InsetsListener insetsListener;
    public final AndroidWindowInsets mandatorySystemGestures;
    public final AndroidWindowInsets navigationBars;
    public final ValueInsets navigationBarsIgnoringVisibility;
    public final WindowInsets safeDrawing;
    public final AndroidWindowInsets statusBars;
    public final ValueInsets statusBarsIgnoringVisibility;
    public final AndroidWindowInsets systemBars;
    public final ValueInsets systemBarsIgnoringVisibility;
    public final AndroidWindowInsets systemGestures;
    public final AndroidWindowInsets tappableElement;
    public final ValueInsets tappableElementIgnoringVisibility;
    public final ValueInsets waterfall;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static final AndroidWindowInsets access$systemInsets(Companion companion, WindowInsetsCompat windowInsetsCompat, int i, String str) {
            companion.getClass();
            AndroidWindowInsets androidWindowInsets = new AndroidWindowInsets(i, str);
            if (windowInsetsCompat != null) {
                androidWindowInsets.update$foundation_layout(windowInsetsCompat, i);
            }
            return androidWindowInsets;
        }

        public static final ValueInsets access$valueInsetsIgnoringVisibility(Companion companion, WindowInsetsCompat windowInsetsCompat, int i, String str) {
            Insets insets;
            companion.getClass();
            if (windowInsetsCompat == null || (insets = windowInsetsCompat.mImpl.getInsetsIgnoringVisibility(i)) == null) {
                insets = Insets.NONE;
            }
            return new ValueInsets(WindowInsets_androidKt.toInsetsValues(insets), str);
        }

        /* JADX WARN: Code restructure failed: missing block: B:15:0x0043, code lost:
        
            if (r3 == androidx.compose.runtime.Composer.Companion.Empty) goto L18;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public static androidx.compose.foundation.layout.WindowInsetsHolder current(androidx.compose.runtime.Composer r4) {
            /*
                boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                if (r0 == 0) goto Lb
                java.lang.String r0 = "androidx.compose.foundation.layout.WindowInsetsHolder.Companion.current (WindowInsets.android.kt:578)"
                androidx.compose.runtime.ComposerKt.traceEventStart(r0)
            Lb:
                androidx.compose.runtime.StaticProvidableCompositionLocal r0 = androidx.compose.ui.platform.AndroidCompositionLocals_androidKt.LocalView
                androidx.compose.runtime.ComposerImpl r4 = (androidx.compose.runtime.ComposerImpl) r4
                java.lang.Object r0 = r4.consume(r0)
                android.view.View r0 = (android.view.View) r0
                java.util.WeakHashMap r1 = androidx.compose.foundation.layout.WindowInsetsHolder.viewMap
                monitor-enter(r1)
                java.lang.Object r2 = r1.get(r0)     // Catch: java.lang.Throwable -> L28
                if (r2 != 0) goto L2a
                androidx.compose.foundation.layout.WindowInsetsHolder r2 = new androidx.compose.foundation.layout.WindowInsetsHolder     // Catch: java.lang.Throwable -> L28
                r3 = 0
                r2.<init>(r3, r0, r3)     // Catch: java.lang.Throwable -> L28
                r1.put(r0, r2)     // Catch: java.lang.Throwable -> L28
                goto L2a
            L28:
                r4 = move-exception
                goto L5c
            L2a:
                androidx.compose.foundation.layout.WindowInsetsHolder r2 = (androidx.compose.foundation.layout.WindowInsetsHolder) r2     // Catch: java.lang.Throwable -> L28
                monitor-exit(r1)
                boolean r1 = r4.changedInstance(r2)
                boolean r3 = r4.changedInstance(r0)
                r1 = r1 | r3
                java.lang.Object r3 = r4.rememberedValue()
                if (r1 != 0) goto L45
                androidx.compose.runtime.Composer$Companion r1 = androidx.compose.runtime.Composer.Companion
                r1.getClass()
                androidx.compose.runtime.Composer$Companion$Empty$1 r1 = androidx.compose.runtime.Composer.Companion.Empty
                if (r3 != r1) goto L4d
            L45:
                androidx.compose.foundation.layout.WindowInsetsHolder$Companion$current$1$1 r3 = new androidx.compose.foundation.layout.WindowInsetsHolder$Companion$current$1$1
                r3.<init>()
                r4.updateRememberedValue(r3)
            L4d:
                kotlin.jvm.functions.Function1 r3 = (kotlin.jvm.functions.Function1) r3
                androidx.compose.runtime.EffectsKt.DisposableEffect(r2, r3, r4)
                boolean r4 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                if (r4 == 0) goto L5b
                androidx.compose.runtime.ComposerKt.traceEventEnd()
            L5b:
                return r2
            L5c:
                monitor-exit(r1)
                throw r4
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.layout.WindowInsetsHolder.Companion.current(androidx.compose.runtime.Composer):androidx.compose.foundation.layout.WindowInsetsHolder");
        }

        private Companion() {
        }
    }

    public /* synthetic */ WindowInsetsHolder(WindowInsetsCompat windowInsetsCompat, View view, DefaultConstructorMarker defaultConstructorMarker) {
        this(windowInsetsCompat, view);
    }

    public static void update$default(WindowInsetsHolder windowInsetsHolder, WindowInsetsCompat windowInsetsCompat) {
        windowInsetsHolder.captionBar.update$foundation_layout(windowInsetsCompat, 0);
        windowInsetsHolder.ime.update$foundation_layout(windowInsetsCompat, 0);
        windowInsetsHolder.displayCutout.update$foundation_layout(windowInsetsCompat, 0);
        windowInsetsHolder.navigationBars.update$foundation_layout(windowInsetsCompat, 0);
        windowInsetsHolder.statusBars.update$foundation_layout(windowInsetsCompat, 0);
        windowInsetsHolder.systemBars.update$foundation_layout(windowInsetsCompat, 0);
        windowInsetsHolder.systemGestures.update$foundation_layout(windowInsetsCompat, 0);
        windowInsetsHolder.tappableElement.update$foundation_layout(windowInsetsCompat, 0);
        windowInsetsHolder.mandatorySystemGestures.update$foundation_layout(windowInsetsCompat, 0);
        windowInsetsHolder.captionBarIgnoringVisibility.setValue$foundation_layout(WindowInsets_androidKt.toInsetsValues(windowInsetsCompat.mImpl.getInsetsIgnoringVisibility(4)));
        WindowInsetsCompat.Impl impl = windowInsetsCompat.mImpl;
        windowInsetsHolder.navigationBarsIgnoringVisibility.setValue$foundation_layout(WindowInsets_androidKt.toInsetsValues(impl.getInsetsIgnoringVisibility(2)));
        windowInsetsHolder.statusBarsIgnoringVisibility.setValue$foundation_layout(WindowInsets_androidKt.toInsetsValues(impl.getInsetsIgnoringVisibility(1)));
        windowInsetsHolder.systemBarsIgnoringVisibility.setValue$foundation_layout(WindowInsets_androidKt.toInsetsValues(impl.getInsetsIgnoringVisibility(7)));
        windowInsetsHolder.tappableElementIgnoringVisibility.setValue$foundation_layout(WindowInsets_androidKt.toInsetsValues(impl.getInsetsIgnoringVisibility(64)));
        DisplayCutoutCompat displayCutout = impl.getDisplayCutout();
        if (displayCutout != null) {
            windowInsetsHolder.waterfall.setValue$foundation_layout(WindowInsets_androidKt.toInsetsValues(Insets.toCompatInsets(displayCutout.mDisplayCutout.getWaterfallInsets())));
        }
        Snapshot.Companion.getClass();
        Snapshot.Companion.sendApplyNotifications();
    }

    private WindowInsetsHolder(WindowInsetsCompat windowInsetsCompat, View view) {
        DisplayCutoutCompat displayCutout;
        Insets compatInsets;
        Companion companion = Companion;
        this.captionBar = Companion.access$systemInsets(companion, windowInsetsCompat, 4, "captionBar");
        AndroidWindowInsets access$systemInsets = Companion.access$systemInsets(companion, windowInsetsCompat, 128, "displayCutout");
        this.displayCutout = access$systemInsets;
        AndroidWindowInsets access$systemInsets2 = Companion.access$systemInsets(companion, windowInsetsCompat, 8, "ime");
        this.ime = access$systemInsets2;
        AndroidWindowInsets access$systemInsets3 = Companion.access$systemInsets(companion, windowInsetsCompat, 32, "mandatorySystemGestures");
        this.mandatorySystemGestures = access$systemInsets3;
        this.navigationBars = Companion.access$systemInsets(companion, windowInsetsCompat, 2, "navigationBars");
        this.statusBars = Companion.access$systemInsets(companion, windowInsetsCompat, 1, "statusBars");
        AndroidWindowInsets access$systemInsets4 = Companion.access$systemInsets(companion, windowInsetsCompat, 7, "systemBars");
        this.systemBars = access$systemInsets4;
        AndroidWindowInsets access$systemInsets5 = Companion.access$systemInsets(companion, windowInsetsCompat, 16, "systemGestures");
        this.systemGestures = access$systemInsets5;
        AndroidWindowInsets access$systemInsets6 = Companion.access$systemInsets(companion, windowInsetsCompat, 64, "tappableElement");
        this.tappableElement = access$systemInsets6;
        ValueInsets valueInsets = new ValueInsets(WindowInsets_androidKt.toInsetsValues((windowInsetsCompat == null || (displayCutout = windowInsetsCompat.mImpl.getDisplayCutout()) == null || (compatInsets = Insets.toCompatInsets(displayCutout.mDisplayCutout.getWaterfallInsets())) == null) ? Insets.NONE : compatInsets), "waterfall");
        this.waterfall = valueInsets;
        UnionInsets unionInsets = new UnionInsets(new UnionInsets(access$systemInsets4, access$systemInsets2), access$systemInsets);
        this.safeDrawing = unionInsets;
        WindowInsetsKt.union(unionInsets, new UnionInsets(new UnionInsets(new UnionInsets(access$systemInsets6, access$systemInsets3), access$systemInsets5), valueInsets));
        this.captionBarIgnoringVisibility = Companion.access$valueInsetsIgnoringVisibility(companion, windowInsetsCompat, 4, "captionBarIgnoringVisibility");
        this.navigationBarsIgnoringVisibility = Companion.access$valueInsetsIgnoringVisibility(companion, windowInsetsCompat, 2, "navigationBarsIgnoringVisibility");
        this.statusBarsIgnoringVisibility = Companion.access$valueInsetsIgnoringVisibility(companion, windowInsetsCompat, 1, "statusBarsIgnoringVisibility");
        this.systemBarsIgnoringVisibility = Companion.access$valueInsetsIgnoringVisibility(companion, windowInsetsCompat, 7, "systemBarsIgnoringVisibility");
        this.tappableElementIgnoringVisibility = Companion.access$valueInsetsIgnoringVisibility(companion, windowInsetsCompat, 64, "tappableElementIgnoringVisibility");
        this.imeAnimationTarget = Companion.access$valueInsetsIgnoringVisibility(companion, windowInsetsCompat, 8, "imeAnimationTarget");
        this.imeAnimationSource = Companion.access$valueInsetsIgnoringVisibility(companion, windowInsetsCompat, 8, "imeAnimationSource");
        Object parent = view.getParent();
        View view2 = parent instanceof View ? (View) parent : null;
        Object tag = view2 != null ? view2.getTag(R.id.consume_window_insets_tag) : null;
        Boolean bool = tag instanceof Boolean ? (Boolean) tag : null;
        this.consumes = bool != null ? bool.booleanValue() : true;
        this.insetsListener = new InsetsListener(this);
    }
}
