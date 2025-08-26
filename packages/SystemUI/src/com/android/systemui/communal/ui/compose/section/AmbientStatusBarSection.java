package com.android.systemui.communal.ui.compose.section;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.viewinterop.AndroidView_androidKt;
import com.android.compose.animation.scene.ContentScope;
import com.android.systemui.R;
import com.android.systemui.ambient.statusbar.dagger.AmbientStatusBarComponent;
import com.android.systemui.ambient.statusbar.ui.AmbientStatusBarView;
import com.android.systemui.communal.ui.compose.Communal$Elements;
import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
public final class AmbientStatusBarSection {
    public final AmbientStatusBarComponent.Factory factory;

    public AmbientStatusBarSection(AmbientStatusBarComponent.Factory factory) {
        this.factory = factory;
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0059  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void AmbientStatusBar(final ContentScope contentScope, final Modifier modifier, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1461877625);
        int i2 = (composerImpl.changed(contentScope) ? 4 : 2) | i | (composerImpl.changed(this) ? 256 : 128);
        if ((i2 & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.communal.ui.compose.section.AmbientStatusBarSection.AmbientStatusBar (AmbientStatusBarSection.kt:35)");
            }
            composerImpl.startReplaceGroup(-1714590095);
            boolean z = (i2 & 896) == 256;
            Object objRememberedValue = composerImpl.rememberedValue();
            if (!z) {
                Composer.Companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new Function1() { // from class: com.android.systemui.communal.ui.compose.section.AmbientStatusBarSection$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj) {
                            Context context = (Context) obj;
                            AmbientStatusBarView ambientStatusBarView = (AmbientStatusBarView) LayoutInflater.from(context).inflate(R.layout.ambient_status_bar_view, (ViewGroup) new FrameLayout(context), false);
                            ambientStatusBarView.setVisibility(0);
                            ((DaggerReferenceGlobalRootComponent.AmbientStatusBarComponentImpl) this.f$0.factory.create(ambientStatusBarView)).getController().init();
                            return ambientStatusBarView;
                        }
                    };
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                composerImpl.end(false);
                Communal$Elements.INSTANCE.getClass();
                AndroidView_androidKt.AndroidView((Function1) objRememberedValue, contentScope.element(modifier, Communal$Elements.StatusBar), null, composerImpl, 0, 4);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(contentScope, modifier, i) { // from class: com.android.systemui.communal.ui.compose.section.AmbientStatusBarSection$$ExternalSyntheticLambda1
                public final /* synthetic */ ContentScope f$1;
                public final /* synthetic */ Modifier f$2;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(49);
                    ContentScope contentScope2 = this.f$1;
                    Modifier modifier2 = this.f$2;
                    this.f$0.AmbientStatusBar(contentScope2, modifier2, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
