package com.android.systemui.media.mediaoutput.compose;

import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.lazy.LazyItemScope;
import androidx.compose.foundation.lazy.LazyListInterval;
import androidx.compose.foundation.lazy.LazyListIntervalContent;
import androidx.compose.foundation.lazy.LazyListScope;
import androidx.compose.material3.IconButtonKt;
import androidx.compose.material3.ProgressIndicatorKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.painter.Painter;
import androidx.compose.ui.unit.Dp;
import com.android.systemui.media.mediaoutput.compose.ext.CharSequenceExtKt;
import com.android.systemui.media.mediaoutput.compose.theme.ColorKt;
import com.android.systemui.media.mediaoutput.compose.widget.IconExtKt;
import com.android.systemui.media.mediaoutput.controller.media.SessionController;
import com.android.systemui.media.mediaoutput.entity.AudioDevice;
import com.android.systemui.media.mediaoutput.entity.EntityString;
import com.android.systemui.media.mediaoutput.entity.MediaAction;
import com.android.systemui.media.mediaoutput.viewmodel.AudioPathInteraction;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;

/* loaded from: classes2.dex */
public final /* synthetic */ class MediaCardKt$$ExternalSyntheticLambda25 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ EntityString f$1;

    public /* synthetic */ MediaCardKt$$ExternalSyntheticLambda25(Object obj, EntityString entityString, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = entityString;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                final List list = (List) ((MutableState) this.f$0).getValue();
                final MediaCardKt$ControlArea$lambda$76$lambda$75$$inlined$items$default$1 mediaCardKt$ControlArea$lambda$76$lambda$75$$inlined$items$default$1 = new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$ControlArea$lambda$76$lambda$75$$inlined$items$default$1
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj2) {
                        return null;
                    }
                };
                int size = list.size();
                Function1 function1 = new Function1() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$ControlArea$lambda$76$lambda$75$$inlined$items$default$3
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj2) {
                        return mediaCardKt$ControlArea$lambda$76$lambda$75$$inlined$items$default$1.mo781invoke(list.get(((Number) obj2).intValue()));
                    }
                };
                final SessionController sessionController = (SessionController) this.f$1;
                ComposableLambdaImpl composableLambdaImpl = new ComposableLambdaImpl(-632812321, true, new Function4() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$ControlArea$lambda$76$lambda$75$$inlined$items$default$4
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(4);
                    }

                    /* JADX WARN: Removed duplicated region for block: B:33:0x00c3  */
                    @Override // kotlin.jvm.functions.Function4
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object invoke(Object obj2, Object obj3, Object obj4, Object obj5) {
                        int i;
                        LazyItemScope lazyItemScope = (LazyItemScope) obj2;
                        int iIntValue = ((Number) obj3).intValue();
                        Composer composer = (Composer) obj4;
                        int iIntValue2 = ((Number) obj5).intValue();
                        if ((iIntValue2 & 6) == 0) {
                            i = (((ComposerImpl) composer).changed(lazyItemScope) ? 4 : 2) | iIntValue2;
                        } else {
                            i = iIntValue2;
                        }
                        if ((iIntValue2 & 48) == 0) {
                            i |= ((ComposerImpl) composer).changed(iIntValue) ? 32 : 16;
                        }
                        ComposerImpl composerImpl = (ComposerImpl) composer;
                        if (composerImpl.shouldExecute(i & 1, (i & 147) != 146)) {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("androidx.compose.foundation.lazy.items.<anonymous> (LazyDsl.kt:178)");
                            }
                            final MediaAction mediaAction = (MediaAction) list.get(iIntValue);
                            composerImpl.startReplaceGroup(1994594141);
                            if (mediaAction.id == -1) {
                                composerImpl.startReplaceGroup(1994621327);
                                Dp.Companion companion = Dp.Companion;
                                ProgressIndicatorKt.m279CircularProgressIndicator4lLiAd8(SizeKt.m140size3ABfNKs(Modifier.Companion, 40), 0L, 0.0f, 0L, 0, 0.0f, composerImpl, 6, 62);
                                composerImpl = composerImpl;
                                composerImpl.end(false);
                            } else {
                                composerImpl.startReplaceGroup(1994731563);
                                composerImpl.startReplaceGroup(-351294431);
                                boolean zChangedInstance = composerImpl.changedInstance(sessionController) | composerImpl.changedInstance(mediaAction);
                                Object objRememberedValue = composerImpl.rememberedValue();
                                if (!zChangedInstance) {
                                    Composer.Companion.getClass();
                                    if (objRememberedValue == Composer.Companion.Empty) {
                                        final SessionController sessionController2 = sessionController;
                                        objRememberedValue = new Function0() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$ControlArea$1$1$1$1$1
                                            @Override // kotlin.jvm.functions.Function0
                                            public final Object invoke() {
                                                sessionController2.execute(mediaAction.id, 0L);
                                                return Unit.INSTANCE;
                                            }
                                        };
                                        composerImpl.updateRememberedValue(objRememberedValue);
                                    }
                                    composerImpl.end(false);
                                    Dp.Companion companion2 = Dp.Companion;
                                    IconButtonKt.IconButton(1572912, 56, null, null, composerImpl, SizeKt.m140size3ABfNKs(Modifier.Companion, 40), null, (Function0) objRememberedValue, ComposableLambdaKt.rememberComposableLambda(-351342483, new Function2() { // from class: com.android.systemui.media.mediaoutput.compose.MediaCardKt$ControlArea$1$1$1$2
                                        /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
                                        @Override // kotlin.jvm.functions.Function2
                                        /*
                                            Code decompiled incorrectly, please refer to instructions dump.
                                        */
                                        public final Object invoke(Object obj6, Object obj7) {
                                            Composer composer2 = (Composer) obj6;
                                            if ((((Number) obj7).intValue() & 3) == 2) {
                                                ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                                if (composerImpl2.getSkipping()) {
                                                    composerImpl2.skipToGroupEnd();
                                                } else {
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ControlArea.<anonymous>.<anonymous>.<anonymous>.<anonymous> (MediaCard.kt:422)");
                                                    }
                                                    MediaAction mediaAction2 = mediaAction;
                                                    Painter painter = mediaAction2.icon;
                                                    String strText = CharSequenceExtKt.text(mediaAction2.description, composer2);
                                                    Dp.Companion companion3 = Dp.Companion;
                                                    Modifier modifierM125padding3ABfNKs = PaddingKt.m125padding3ABfNKs(Modifier.Companion, 4);
                                                    ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                                    composerImpl3.startReplaceGroup(506712694);
                                                    long jMediaPrimaryColor = mediaAction2.enabled ? ColorKt.mediaPrimaryColor(composerImpl3) : androidx.compose.ui.graphics.ColorKt.Color(4288256414L);
                                                    composerImpl3.end(false);
                                                    IconExtKt.m2633IconExtww6aTOc(painter, strText, modifierM125padding3ABfNKs, jMediaPrimaryColor, composerImpl3, 384, 0);
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventEnd();
                                                    }
                                                }
                                            }
                                            return Unit.INSTANCE;
                                        }
                                    }, composerImpl), mediaAction.enabled);
                                    composerImpl.end(false);
                                }
                            }
                            composerImpl.end(false);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        } else {
                            composerImpl.skipToGroupEnd();
                        }
                        return Unit.INSTANCE;
                    }
                });
                LazyListIntervalContent lazyListIntervalContent = (LazyListIntervalContent) ((LazyListScope) obj);
                lazyListIntervalContent.getClass();
                lazyListIntervalContent.intervals.addInterval(size, new LazyListInterval(null, function1, composableLambdaImpl));
                break;
            default:
                boolean zBooleanValue = ((Boolean) obj).booleanValue();
                AudioDevice audioDevice = (AudioDevice) this.f$1;
                AudioPathInteraction audioPathInteraction = (AudioPathInteraction) this.f$0;
                if (zBooleanValue) {
                    audioPathInteraction.select(audioDevice);
                } else if (audioDevice.getDeselectable()) {
                    audioPathInteraction.deselect(audioDevice);
                }
                break;
        }
        return Unit.INSTANCE;
    }
}
