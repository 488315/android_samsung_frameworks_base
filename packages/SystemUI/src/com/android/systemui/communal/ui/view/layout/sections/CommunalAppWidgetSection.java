package com.android.systemui.communal.ui.view.layout.sections;

import android.content.Context;
import android.os.Bundle;
import android.util.SizeF;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.viewinterop.AndroidView_androidKt;
import com.android.systemui.R;
import com.android.systemui.communal.domain.model.CommunalContentModel;
import com.android.systemui.communal.ui.view.layout.sections.CommunalAppWidgetSection;
import com.android.systemui.communal.ui.viewmodel.CommunalAppWidgetViewModel;
import com.android.systemui.communal.ui.viewmodel.SetListener;
import com.android.systemui.communal.ui.viewmodel.UpdateSize;
import com.android.systemui.communal.widgets.CommunalAppWidgetHostView;
import com.android.systemui.communal.widgets.WidgetInteractionHandler;
import com.android.systemui.lifecycle.SysUiViewModelKt;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class CommunalAppWidgetSection {
    public static final Companion Companion = new Companion(null);
    public static final int LISTENER_TAG = R.id.communal_widget_listener_tag;
    public static final Lazy poolSize$delegate;
    public static final Lazy widgetExecutor$delegate;
    public final WidgetInteractionHandler interactionHandler;
    public final Executor uiBgExecutor;
    public final CommunalAppWidgetViewModel.Factory viewModelFactory;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class WidgetAccessibilityDelegate extends View.AccessibilityDelegate {
        public final Function0 longClickAction;
        public final String longClickLabel;

        public WidgetAccessibilityDelegate(String str, Function0 function0) {
            this.longClickLabel = str;
            this.longClickAction = function0;
        }

        @Override // android.view.View.AccessibilityDelegate
        public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
            accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_LONG_CLICK.getId(), this.longClickLabel.toLowerCase(Locale.ROOT)));
        }

        @Override // android.view.View.AccessibilityDelegate
        public final boolean performAccessibilityAction(View view, int i, Bundle bundle) {
            if (i != AccessibilityNodeInfo.AccessibilityAction.ACTION_LONG_CLICK.getId()) {
                return super.performAccessibilityAction(view, i, bundle);
            }
            this.longClickAction.invoke();
            return true;
        }
    }

    static {
        final int i = 0;
        poolSize$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.communal.ui.view.layout.sections.CommunalAppWidgetSection$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        CommunalAppWidgetSection.Companion companion = CommunalAppWidgetSection.Companion;
                        int iAvailableProcessors = Runtime.getRuntime().availableProcessors();
                        if (iAvailableProcessors < 2) {
                            iAvailableProcessors = 2;
                        }
                        return Integer.valueOf(iAvailableProcessors);
                    default:
                        CommunalAppWidgetSection.Companion companion2 = CommunalAppWidgetSection.Companion;
                        CommunalAppWidgetSection.Companion.getClass();
                        Lazy lazy = CommunalAppWidgetSection.poolSize$delegate;
                        return new ThreadPoolExecutor(((Number) lazy.getValue()).intValue(), ((Number) lazy.getValue()).intValue(), 1L, TimeUnit.SECONDS, new LinkedBlockingQueue());
                }
            }
        });
        final int i2 = 1;
        widgetExecutor$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.communal.ui.view.layout.sections.CommunalAppWidgetSection$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        CommunalAppWidgetSection.Companion companion = CommunalAppWidgetSection.Companion;
                        int iAvailableProcessors = Runtime.getRuntime().availableProcessors();
                        if (iAvailableProcessors < 2) {
                            iAvailableProcessors = 2;
                        }
                        return Integer.valueOf(iAvailableProcessors);
                    default:
                        CommunalAppWidgetSection.Companion companion2 = CommunalAppWidgetSection.Companion;
                        CommunalAppWidgetSection.Companion.getClass();
                        Lazy lazy = CommunalAppWidgetSection.poolSize$delegate;
                        return new ThreadPoolExecutor(((Number) lazy.getValue()).intValue(), ((Number) lazy.getValue()).intValue(), 1L, TimeUnit.SECONDS, new LinkedBlockingQueue());
                }
            }
        });
    }

    public CommunalAppWidgetSection(Executor executor, WidgetInteractionHandler widgetInteractionHandler, CommunalAppWidgetViewModel.Factory factory) {
        this.uiBgExecutor = executor;
        this.interactionHandler = widgetInteractionHandler;
        this.viewModelFactory = factory;
    }

    /* JADX WARN: Removed duplicated region for block: B:59:0x00bc  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x00fd  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0121  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void Widget(final boolean z, final Function0 function0, final CommunalContentModel.WidgetContent.Widget widget, final SizeF sizeF, final Modifier modifier, Composer composer, final int i) {
        boolean z2;
        int i2;
        boolean z3;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1019968658);
        if ((i & 6) == 0) {
            z2 = z;
            i2 = (composerImpl.changed(z2) ? 4 : 2) | i;
        } else {
            z2 = z;
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changedInstance(function0) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changedInstance(widget) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl.changedInstance(sizeF) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i2 |= composerImpl.changed(modifier) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((196608 & i) == 0) {
            i2 |= composerImpl.changedInstance(this) ? 131072 : 65536;
        }
        int i3 = i2;
        if ((i3 & 74899) == 74898 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.communal.ui.view.layout.sections.CommunalAppWidgetSection.Widget (CommunalAppWidgetSection.kt:79)");
            }
            composerImpl.startReplaceGroup(136203573);
            boolean zChangedInstance = composerImpl.changedInstance(this);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion companion = Composer.Companion;
            if (!zChangedInstance) {
                companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new Function0() { // from class: com.android.systemui.communal.ui.view.layout.sections.CommunalAppWidgetSection$$ExternalSyntheticLambda2
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            return this.f$0.viewModelFactory.create();
                        }
                    };
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                composerImpl.end(false);
                final CommunalAppWidgetViewModel communalAppWidgetViewModel = (CommunalAppWidgetViewModel) SysUiViewModelKt.rememberViewModel("CommunalAppWidgetSection#viewModel", null, (Function0) objRememberedValue, composerImpl, 0, 2);
                String strStringResource = StringResources_androidKt.stringResource(R.string.accessibility_action_label_edit_widgets, composerImpl);
                composerImpl.startReplaceGroup(136209184);
                boolean zChanged = ((i3 & 112) == 32) | composerImpl.changed(strStringResource);
                Object objRememberedValue2 = composerImpl.rememberedValue();
                if (!zChanged) {
                    companion.getClass();
                    if (objRememberedValue2 == Composer.Companion.Empty) {
                        objRememberedValue2 = new WidgetAccessibilityDelegate(strStringResource, function0);
                        composerImpl.updateRememberedValue(objRememberedValue2);
                    }
                    final WidgetAccessibilityDelegate widgetAccessibilityDelegate = (WidgetAccessibilityDelegate) objRememberedValue2;
                    composerImpl.end(false);
                    composerImpl.startReplaceGroup(136215180);
                    boolean zChangedInstance2 = composerImpl.changedInstance(this);
                    Object objRememberedValue3 = composerImpl.rememberedValue();
                    if (!zChangedInstance2) {
                        companion.getClass();
                        if (objRememberedValue3 == Composer.Companion.Empty) {
                            objRememberedValue3 = new Function1() { // from class: com.android.systemui.communal.ui.view.layout.sections.CommunalAppWidgetSection$$ExternalSyntheticLambda3
                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo781invoke(Object obj) {
                                    CommunalAppWidgetSection.Companion companion2 = CommunalAppWidgetSection.Companion;
                                    CommunalAppWidgetHostView communalAppWidgetHostView = new CommunalAppWidgetHostView((Context) obj, this.f$0.interactionHandler);
                                    CommunalAppWidgetSection.Companion.getClass();
                                    communalAppWidgetHostView.setExecutor((ThreadPoolExecutor) CommunalAppWidgetSection.widgetExecutor$delegate.getValue());
                                    return communalAppWidgetHostView;
                                }
                            };
                            composerImpl.updateRememberedValue(objRememberedValue3);
                        }
                        Function1 function1 = (Function1) objRememberedValue3;
                        composerImpl.end(false);
                        composerImpl.startReplaceGroup(136259770);
                        Object objRememberedValue4 = composerImpl.rememberedValue();
                        companion.getClass();
                        Object obj = Composer.Companion.Empty;
                        if (objRememberedValue4 == obj) {
                            objRememberedValue4 = new CommunalAppWidgetSection$$ExternalSyntheticLambda4();
                            composerImpl.updateRememberedValue(objRememberedValue4);
                        }
                        Function1 function12 = (Function1) objRememberedValue4;
                        composerImpl.end(false);
                        composerImpl.startReplaceGroup(136227386);
                        boolean zChanged2 = composerImpl.changed(widgetAccessibilityDelegate) | ((i3 & 14) == 4) | composerImpl.changedInstance(widget) | composerImpl.changedInstance(communalAppWidgetViewModel) | composerImpl.changedInstance(sizeF);
                        Object objRememberedValue5 = composerImpl.rememberedValue();
                        if (zChanged2 || objRememberedValue5 == obj) {
                            final boolean z4 = z2;
                            z3 = false;
                            Object obj2 = new Function1() { // from class: com.android.systemui.communal.ui.view.layout.sections.CommunalAppWidgetSection$$ExternalSyntheticLambda5
                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo781invoke(Object obj3) {
                                    SizeF sizeF2 = sizeF;
                                    CommunalAppWidgetHostView communalAppWidgetHostView = (CommunalAppWidgetHostView) obj3;
                                    CommunalAppWidgetSection.Companion companion2 = CommunalAppWidgetSection.Companion;
                                    communalAppWidgetHostView.setAccessibilityDelegate(widgetAccessibilityDelegate);
                                    communalAppWidgetHostView.setImportantForAccessibility(z4 ? 0 : 4);
                                    CommunalContentModel.WidgetContent.Widget widget2 = widget;
                                    communalAppWidgetHostView.setAppWidget(widget2.appWidgetId, widget2.providerInfo);
                                    int i4 = CommunalAppWidgetSection.LISTENER_TAG;
                                    Object tag = communalAppWidgetHostView.getTag(i4);
                                    Integer num = tag instanceof Integer ? (Integer) tag : null;
                                    CommunalAppWidgetViewModel communalAppWidgetViewModel2 = communalAppWidgetViewModel;
                                    int i5 = widget2.appWidgetId;
                                    if (num == null || num.intValue() != i5) {
                                        communalAppWidgetViewModel2.requests.mo3475trySendJP2dKIU(new SetListener(i5, communalAppWidgetHostView));
                                        communalAppWidgetHostView.setTag(i4, Integer.valueOf(i5));
                                    }
                                    communalAppWidgetViewModel2.requests.mo3475trySendJP2dKIU(new UpdateSize(sizeF2, communalAppWidgetHostView));
                                    return Unit.INSTANCE;
                                }
                            };
                            composerImpl.updateRememberedValue(obj2);
                            objRememberedValue5 = obj2;
                        } else {
                            z3 = false;
                        }
                        composerImpl.end(z3);
                        AndroidView_androidKt.AndroidView(function1, modifier, function12, null, (Function1) objRememberedValue5, composerImpl, ((i3 >> 9) & 112) | 384, 8);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                    }
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.communal.ui.view.layout.sections.CommunalAppWidgetSection$$ExternalSyntheticLambda6
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj3, Object obj4) {
                    SizeF sizeF2 = sizeF;
                    Composer composer2 = (Composer) obj3;
                    ((Integer) obj4).getClass();
                    CommunalAppWidgetSection.Companion companion2 = CommunalAppWidgetSection.Companion;
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    this.f$0.Widget(z, function0, widget, sizeF2, modifier, composer2, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
