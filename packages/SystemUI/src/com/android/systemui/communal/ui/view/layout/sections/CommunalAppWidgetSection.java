package com.android.systemui.communal.ui.view.layout.sections;

import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import com.android.systemui.R;
import com.android.systemui.communal.ui.view.layout.sections.CommunalAppWidgetSection;
import com.android.systemui.communal.ui.viewmodel.CommunalAppWidgetViewModel;
import com.android.systemui.communal.widgets.WidgetInteractionHandler;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CommunalAppWidgetSection {
    public static final Companion Companion = new Companion(null);
    public static final int LISTENER_TAG = R.id.communal_widget_listener_tag;
    public static final Lazy poolSize$delegate;
    public static final Lazy widgetExecutor$delegate;
    public final WidgetInteractionHandler interactionHandler;
    public final Executor uiBgExecutor;
    public final CommunalAppWidgetViewModel.Factory viewModelFactory;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                        int availableProcessors = Runtime.getRuntime().availableProcessors();
                        if (availableProcessors < 2) {
                            availableProcessors = 2;
                        }
                        return Integer.valueOf(availableProcessors);
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
                        int availableProcessors = Runtime.getRuntime().availableProcessors();
                        if (availableProcessors < 2) {
                            availableProcessors = 2;
                        }
                        return Integer.valueOf(availableProcessors);
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

    /* JADX WARN: Code restructure failed: missing block: B:58:0x00ba, code lost:
    
        if (r10 == androidx.compose.runtime.Composer.Companion.Empty) goto L59;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x00fb, code lost:
    
        if (r12 == androidx.compose.runtime.Composer.Companion.Empty) goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x011f, code lost:
    
        if (r11 == androidx.compose.runtime.Composer.Companion.Empty) goto L73;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void Widget(final boolean r19, final kotlin.jvm.functions.Function0 r20, final com.android.systemui.communal.domain.model.CommunalContentModel.WidgetContent.Widget r21, final android.util.SizeF r22, final androidx.compose.ui.Modifier r23, androidx.compose.runtime.Composer r24, final int r25) {
        /*
            Method dump skipped, instructions count: 450
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.ui.view.layout.sections.CommunalAppWidgetSection.Widget(boolean, kotlin.jvm.functions.Function0, com.android.systemui.communal.domain.model.CommunalContentModel$WidgetContent$Widget, android.util.SizeF, androidx.compose.ui.Modifier, androidx.compose.runtime.Composer, int):void");
    }
}
