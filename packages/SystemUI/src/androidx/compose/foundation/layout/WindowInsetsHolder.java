package androidx.compose.foundation.layout;

import android.view.View;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.DisposableEffectResult;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.snapshots.Snapshot;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.core.graphics.Insets;
import androidx.core.view.DisplayCutoutCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.android.systemui.R;
import java.util.WeakHashMap;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

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
            Insets insetsIgnoringVisibility;
            companion.getClass();
            if (windowInsetsCompat == null || (insetsIgnoringVisibility = windowInsetsCompat.mImpl.getInsetsIgnoringVisibility(i)) == null) {
                insetsIgnoringVisibility = Insets.NONE;
            }
            return new ValueInsets(WindowInsets_androidKt.toInsetsValues(insetsIgnoringVisibility), str);
        }

        /* JADX WARN: Removed duplicated region for block: B:18:0x0045  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public static WindowInsetsHolder current(Composer composer) {
            final WindowInsetsHolder windowInsetsHolder;
            Object obj;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.foundation.layout.WindowInsetsHolder.Companion.current (WindowInsets.android.kt:578)");
            }
            ComposerImpl composerImpl = (ComposerImpl) composer;
            final View view = (View) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalView);
            WeakHashMap weakHashMap = WindowInsetsHolder.viewMap;
            synchronized (weakHashMap) {
                try {
                    Object obj2 = weakHashMap.get(view);
                    Object obj3 = obj2;
                    if (obj2 == null) {
                        WindowInsetsHolder windowInsetsHolder2 = new WindowInsetsHolder(null, view, false ? 1 : 0);
                        weakHashMap.put(view, windowInsetsHolder2);
                        obj3 = windowInsetsHolder2;
                    }
                    windowInsetsHolder = (WindowInsetsHolder) obj3;
                } catch (Throwable th) {
                    throw th;
                }
            }
            boolean zChangedInstance = composerImpl.changedInstance(windowInsetsHolder) | composerImpl.changedInstance(view);
            Object objRememberedValue = composerImpl.rememberedValue();
            if (!zChangedInstance) {
                Composer.Companion.getClass();
                obj = objRememberedValue;
                if (objRememberedValue == Composer.Companion.Empty) {
                    Function1 function1 = new Function1() { // from class: androidx.compose.foundation.layout.WindowInsetsHolder$Companion$current$1$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj4) {
                            WindowInsetsHolder windowInsetsHolder3 = windowInsetsHolder;
                            View view2 = view;
                            if (windowInsetsHolder3.accessCount == 0) {
                                WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                                InsetsListener insetsListener = windowInsetsHolder3.insetsListener;
                                ViewCompat.Api21Impl.setOnApplyWindowInsetsListener(view2, insetsListener);
                                if (view2.isAttachedToWindow()) {
                                    view2.requestApplyInsets();
                                }
                                view2.addOnAttachStateChangeListener(insetsListener);
                                ViewCompat.setWindowInsetsAnimationCallback(view2, insetsListener);
                            }
                            windowInsetsHolder3.accessCount++;
                            final WindowInsetsHolder windowInsetsHolder4 = windowInsetsHolder;
                            final View view3 = view;
                            return new DisposableEffectResult() { // from class: androidx.compose.foundation.layout.WindowInsetsHolder$Companion$current$1$1$invoke$$inlined$onDispose$1
                                @Override // androidx.compose.runtime.DisposableEffectResult
                                public final void dispose() {
                                    View view4 = view3;
                                    WindowInsetsHolder windowInsetsHolder5 = windowInsetsHolder4;
                                    int i = windowInsetsHolder5.accessCount - 1;
                                    windowInsetsHolder5.accessCount = i;
                                    if (i == 0) {
                                        WeakHashMap weakHashMap3 = ViewCompat.sViewPropertyAnimatorMap;
                                        ViewCompat.Api21Impl.setOnApplyWindowInsetsListener(view4, null);
                                        view4.setWindowInsetsAnimationCallback(null);
                                        view4.removeOnAttachStateChangeListener(windowInsetsHolder5.insetsListener);
                                    }
                                }
                            };
                        }
                    };
                    composerImpl.updateRememberedValue(function1);
                    obj = function1;
                }
            }
            EffectsKt.DisposableEffect(windowInsetsHolder, (Function1) obj, composerImpl);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            return windowInsetsHolder;
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
        AndroidWindowInsets androidWindowInsetsAccess$systemInsets = Companion.access$systemInsets(companion, windowInsetsCompat, 128, "displayCutout");
        this.displayCutout = androidWindowInsetsAccess$systemInsets;
        AndroidWindowInsets androidWindowInsetsAccess$systemInsets2 = Companion.access$systemInsets(companion, windowInsetsCompat, 8, "ime");
        this.ime = androidWindowInsetsAccess$systemInsets2;
        AndroidWindowInsets androidWindowInsetsAccess$systemInsets3 = Companion.access$systemInsets(companion, windowInsetsCompat, 32, "mandatorySystemGestures");
        this.mandatorySystemGestures = androidWindowInsetsAccess$systemInsets3;
        this.navigationBars = Companion.access$systemInsets(companion, windowInsetsCompat, 2, "navigationBars");
        this.statusBars = Companion.access$systemInsets(companion, windowInsetsCompat, 1, "statusBars");
        AndroidWindowInsets androidWindowInsetsAccess$systemInsets4 = Companion.access$systemInsets(companion, windowInsetsCompat, 7, "systemBars");
        this.systemBars = androidWindowInsetsAccess$systemInsets4;
        AndroidWindowInsets androidWindowInsetsAccess$systemInsets5 = Companion.access$systemInsets(companion, windowInsetsCompat, 16, "systemGestures");
        this.systemGestures = androidWindowInsetsAccess$systemInsets5;
        AndroidWindowInsets androidWindowInsetsAccess$systemInsets6 = Companion.access$systemInsets(companion, windowInsetsCompat, 64, "tappableElement");
        this.tappableElement = androidWindowInsetsAccess$systemInsets6;
        ValueInsets valueInsets = new ValueInsets(WindowInsets_androidKt.toInsetsValues((windowInsetsCompat == null || (displayCutout = windowInsetsCompat.mImpl.getDisplayCutout()) == null || (compatInsets = Insets.toCompatInsets(displayCutout.mDisplayCutout.getWaterfallInsets())) == null) ? Insets.NONE : compatInsets), "waterfall");
        this.waterfall = valueInsets;
        UnionInsets unionInsets = new UnionInsets(new UnionInsets(androidWindowInsetsAccess$systemInsets4, androidWindowInsetsAccess$systemInsets2), androidWindowInsetsAccess$systemInsets);
        this.safeDrawing = unionInsets;
        WindowInsetsKt.union(unionInsets, new UnionInsets(new UnionInsets(new UnionInsets(androidWindowInsetsAccess$systemInsets6, androidWindowInsetsAccess$systemInsets3), androidWindowInsetsAccess$systemInsets5), valueInsets));
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
