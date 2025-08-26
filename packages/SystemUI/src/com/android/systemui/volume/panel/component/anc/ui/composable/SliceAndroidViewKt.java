package com.android.systemui.volume.panel.component.anc.ui.composable;

import android.content.Context;
import android.view.ContextThemeWrapper;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.viewinterop.AndroidView_androidKt;
import androidx.slice.Slice;
import androidx.slice.widget.ListContent;
import androidx.slice.widget.RowContent;
import androidx.slice.widget.SliceViewPolicy;
import androidx.slice.widget.TemplateView;
import com.android.bouncer.ui.composable.SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* loaded from: classes3.dex */
public abstract class SliceAndroidViewKt {
    /* JADX WARN: Removed duplicated region for block: B:35:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00a4  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00c0  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00c2  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00c9  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00d3  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00fc  */
    /* JADX WARN: Removed duplicated region for block: B:67:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void SliceAndroidView(final Slice slice, final Modifier modifier, final Function1 function1, boolean z, Composer composer, final int i, final int i2) {
        Modifier modifier2;
        boolean z2;
        final boolean z3;
        Object objRememberedValue;
        Composer$Companion$Empty$1 composer$Companion$Empty$1;
        Object objM;
        boolean zChangedInstance;
        Object objRememberedValue2;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-417492503);
        int i3 = (composerImpl.changedInstance(slice) ? 4 : 2) | i;
        if ((i & 48) == 0) {
            modifier2 = modifier;
            i3 |= composerImpl.changed(modifier2) ? 32 : 16;
        } else {
            modifier2 = modifier;
        }
        int i4 = i3 | (composerImpl.changedInstance(function1) ? 256 : 128);
        int i5 = i2 & 8;
        if (i5 == 0) {
            if ((i & 3072) == 0) {
                z2 = z;
                i4 |= composerImpl.changed(z2) ? 2048 : 1024;
            }
            if ((i4 & 1171) == 1170 || !composerImpl.getSkipping()) {
                z3 = i5 == 0 ? true : z2;
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.volume.panel.component.anc.ui.composable.SliceAndroidView (SliceAndroidView.kt:37)");
                }
                composerImpl.startReplaceGroup(1794093340);
                objRememberedValue = composerImpl.rememberedValue();
                Composer.Companion.getClass();
                composer$Companion$Empty$1 = Composer.Companion.Empty;
                if (objRememberedValue == composer$Companion$Empty$1) {
                    final int i6 = 0;
                    objRememberedValue = new Function1() { // from class: com.android.systemui.volume.panel.component.anc.ui.composable.SliceAndroidViewKt$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj) {
                            RowContent rowContent;
                            switch (i6) {
                                case 0:
                                    ComposeSliceView composeSliceView = new ComposeSliceView(new ContextThemeWrapper((Context) obj, R.style.Widget_SliceView_VolumePanel));
                                    composeSliceView.setMode(2);
                                    SliceViewPolicy sliceViewPolicy = composeSliceView.mViewPolicy;
                                    boolean z4 = sliceViewPolicy.mScrollable;
                                    if (z4 && z4) {
                                        sliceViewPolicy.mScrollable = false;
                                        TemplateView templateView = sliceViewPolicy.mListener;
                                        if (templateView != null) {
                                            templateView.mRecyclerView.setNestedScrollingEnabled(false);
                                            ListContent listContent = templateView.mListContent;
                                            if (listContent != null) {
                                                templateView.updateDisplayedItems(listContent.getHeight(templateView.mSliceStyle, templateView.mViewPolicy));
                                            }
                                        }
                                    }
                                    composeSliceView.setImportantForAccessibility(2);
                                    composeSliceView.mShowTitleItems = true;
                                    ListContent listContent2 = composeSliceView.mListContent;
                                    if (listContent2 != null && (rowContent = listContent2.mHeaderContent) != null) {
                                        rowContent.mShowTitleItems = true;
                                    }
                                    return composeSliceView;
                                default:
                                    ComposeSliceView composeSliceView2 = (ComposeSliceView) obj;
                                    OnWidthChangedLayoutListener onWidthChangedLayoutListener = composeSliceView2.layoutListener;
                                    if (onWidthChangedLayoutListener != null) {
                                        composeSliceView2.removeOnLayoutChangeListener(onWidthChangedLayoutListener);
                                    }
                                    composeSliceView2.layoutListener = null;
                                    composeSliceView2.setSlice(null);
                                    composeSliceView2.enableAccessibility = true;
                                    return Unit.INSTANCE;
                            }
                        }
                    };
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                Function1 function12 = (Function1) objRememberedValue;
                objM = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl, false, 1794115515);
                if (objM == composer$Companion$Empty$1) {
                    final int i7 = 1;
                    objM = new Function1() { // from class: com.android.systemui.volume.panel.component.anc.ui.composable.SliceAndroidViewKt$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj) {
                            RowContent rowContent;
                            switch (i7) {
                                case 0:
                                    ComposeSliceView composeSliceView = new ComposeSliceView(new ContextThemeWrapper((Context) obj, R.style.Widget_SliceView_VolumePanel));
                                    composeSliceView.setMode(2);
                                    SliceViewPolicy sliceViewPolicy = composeSliceView.mViewPolicy;
                                    boolean z4 = sliceViewPolicy.mScrollable;
                                    if (z4 && z4) {
                                        sliceViewPolicy.mScrollable = false;
                                        TemplateView templateView = sliceViewPolicy.mListener;
                                        if (templateView != null) {
                                            templateView.mRecyclerView.setNestedScrollingEnabled(false);
                                            ListContent listContent = templateView.mListContent;
                                            if (listContent != null) {
                                                templateView.updateDisplayedItems(listContent.getHeight(templateView.mSliceStyle, templateView.mViewPolicy));
                                            }
                                        }
                                    }
                                    composeSliceView.setImportantForAccessibility(2);
                                    composeSliceView.mShowTitleItems = true;
                                    ListContent listContent2 = composeSliceView.mListContent;
                                    if (listContent2 != null && (rowContent = listContent2.mHeaderContent) != null) {
                                        rowContent.mShowTitleItems = true;
                                    }
                                    return composeSliceView;
                                default:
                                    ComposeSliceView composeSliceView2 = (ComposeSliceView) obj;
                                    OnWidthChangedLayoutListener onWidthChangedLayoutListener = composeSliceView2.layoutListener;
                                    if (onWidthChangedLayoutListener != null) {
                                        composeSliceView2.removeOnLayoutChangeListener(onWidthChangedLayoutListener);
                                    }
                                    composeSliceView2.layoutListener = null;
                                    composeSliceView2.setSlice(null);
                                    composeSliceView2.enableAccessibility = true;
                                    return Unit.INSTANCE;
                            }
                        }
                    };
                    composerImpl.updateRememberedValue(objM);
                }
                Function1 function13 = (Function1) objM;
                composerImpl.end(false);
                composerImpl.startReplaceGroup(1794107418);
                zChangedInstance = ((i4 & 896) != 256) | composerImpl.changedInstance(slice) | ((i4 & 7168) == 2048);
                objRememberedValue2 = composerImpl.rememberedValue();
                if (!zChangedInstance || objRememberedValue2 == composer$Companion$Empty$1) {
                    objRememberedValue2 = new Function1() { // from class: com.android.systemui.volume.panel.component.anc.ui.composable.SliceAndroidViewKt$$ExternalSyntheticLambda2
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj) {
                            ComposeSliceView composeSliceView = (ComposeSliceView) obj;
                            composeSliceView.setSlice(slice);
                            Function1 function14 = function1;
                            OnWidthChangedLayoutListener onWidthChangedLayoutListener = function14 != null ? new OnWidthChangedLayoutListener(function14) : null;
                            OnWidthChangedLayoutListener onWidthChangedLayoutListener2 = composeSliceView.layoutListener;
                            if (onWidthChangedLayoutListener2 != null) {
                                composeSliceView.removeOnLayoutChangeListener(onWidthChangedLayoutListener2);
                            }
                            composeSliceView.layoutListener = onWidthChangedLayoutListener;
                            if (onWidthChangedLayoutListener != null) {
                                composeSliceView.addOnLayoutChangeListener(onWidthChangedLayoutListener);
                            }
                            composeSliceView.enableAccessibility = z3;
                            return Unit.INSTANCE;
                        }
                    };
                    composerImpl.updateRememberedValue(objRememberedValue2);
                }
                composerImpl.end(false);
                AndroidView_androidKt.AndroidView(function12, modifier2, null, function13, (Function1) objRememberedValue2, composerImpl, (i4 & 112) | 3078, 4);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            } else {
                composerImpl.skipToGroupEnd();
                z3 = z2;
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
                recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.volume.panel.component.anc.ui.composable.SliceAndroidViewKt$$ExternalSyntheticLambda3
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        ((Integer) obj2).getClass();
                        int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                        boolean z4 = z3;
                        SliceAndroidViewKt.SliceAndroidView(slice, modifier, function1, z4, (Composer) obj, iUpdateChangedFlags, i2);
                        return Unit.INSTANCE;
                    }
                };
                return;
            }
            return;
        }
        i4 |= 3072;
        z2 = z;
        if ((i4 & 1171) == 1170) {
            if (i5 == 0) {
            }
            if (ComposerKt.isTraceInProgress()) {
            }
            composerImpl.startReplaceGroup(1794093340);
            objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (objRememberedValue == composer$Companion$Empty$1) {
            }
            Function1 function122 = (Function1) objRememberedValue;
            objM = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl, false, 1794115515);
            if (objM == composer$Companion$Empty$1) {
            }
            Function1 function132 = (Function1) objM;
            composerImpl.end(false);
            composerImpl.startReplaceGroup(1794107418);
            zChangedInstance = ((i4 & 896) != 256) | composerImpl.changedInstance(slice) | ((i4 & 7168) == 2048);
            objRememberedValue2 = composerImpl.rememberedValue();
            if (!zChangedInstance) {
                objRememberedValue2 = new Function1() { // from class: com.android.systemui.volume.panel.component.anc.ui.composable.SliceAndroidViewKt$$ExternalSyntheticLambda2
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        ComposeSliceView composeSliceView = (ComposeSliceView) obj;
                        composeSliceView.setSlice(slice);
                        Function1 function14 = function1;
                        OnWidthChangedLayoutListener onWidthChangedLayoutListener = function14 != null ? new OnWidthChangedLayoutListener(function14) : null;
                        OnWidthChangedLayoutListener onWidthChangedLayoutListener2 = composeSliceView.layoutListener;
                        if (onWidthChangedLayoutListener2 != null) {
                            composeSliceView.removeOnLayoutChangeListener(onWidthChangedLayoutListener2);
                        }
                        composeSliceView.layoutListener = onWidthChangedLayoutListener;
                        if (onWidthChangedLayoutListener != null) {
                            composeSliceView.addOnLayoutChangeListener(onWidthChangedLayoutListener);
                        }
                        composeSliceView.enableAccessibility = z3;
                        return Unit.INSTANCE;
                    }
                };
                composerImpl.updateRememberedValue(objRememberedValue2);
                composerImpl.end(false);
                AndroidView_androidKt.AndroidView(function122, modifier2, null, function132, (Function1) objRememberedValue2, composerImpl, (i4 & 112) | 3078, 4);
                if (ComposerKt.isTraceInProgress()) {
                }
            }
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }
}
