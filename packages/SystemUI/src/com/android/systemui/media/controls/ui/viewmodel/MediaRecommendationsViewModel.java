package com.android.systemui.media.controls.ui.viewmodel;

import android.content.Context;
import com.android.systemui.media.controls.domain.pipeline.interactor.MediaRecommendationsInteractor;
import com.android.systemui.media.controls.util.MediaUiEventLogger;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class MediaRecommendationsViewModel {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context applicationContext;
    public final CoroutineDispatcher backgroundDispatcher;
    public final MediaRecommendationsInteractor interactor;
    public final MediaUiEventLogger logger;
    public final Flow mediaRecsCard;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    public MediaRecommendationsViewModel(Context context, CoroutineDispatcher coroutineDispatcher, MediaRecommendationsInteractor mediaRecommendationsInteractor, MediaUiEventLogger mediaUiEventLogger) {
        this.applicationContext = context;
        this.backgroundDispatcher = coroutineDispatcher;
        this.interactor = mediaRecommendationsInteractor;
        this.logger = mediaUiEventLogger;
        FlowKt.flowOn(FlowKt.distinctUntilChanged(FlowKt.transformLatest(mediaRecommendationsInteractor.onAnyMediaConfigurationChange, new MediaRecommendationsViewModel$special$$inlined$flatMapLatest$1(null, this))), coroutineDispatcher);
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0031, code lost:
    
        r11 = r11.getString("com.google.android.apps.gsa.smartspace.extra.SMARTSPACE_INTENT");
     */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0068  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void access$onClicked(com.android.systemui.media.controls.ui.viewmodel.MediaRecommendationsViewModel r8, com.android.systemui.animation.Expandable r9, android.content.Intent r10, java.lang.String r11, com.android.internal.logging.InstanceId r12, int r13) {
        /*
            r8.getClass()
            if (r10 == 0) goto L6e
            android.os.Bundle r0 = r10.getExtras()
            if (r0 != 0) goto Lc
            goto L6e
        Lc:
            r0 = 0
            r1 = -1
            com.android.systemui.media.controls.util.MediaUiEventLogger r2 = r8.logger
            if (r13 != r1) goto L1a
            com.android.internal.logging.UiEventLogger r13 = r2.logger
            com.android.systemui.media.controls.util.MediaUiEvent r1 = com.android.systemui.media.controls.util.MediaUiEvent.MEDIA_RECOMMENDATION_CARD_TAP
            r13.logWithInstanceId(r1, r0, r11, r12)
            goto L25
        L1a:
            com.android.internal.logging.UiEventLogger r2 = r2.logger
            com.android.systemui.media.controls.util.MediaUiEvent r3 = com.android.systemui.media.controls.util.MediaUiEvent.MEDIA_RECOMMENDATION_ITEM_TAP
            r4 = 0
            r5 = r11
            r6 = r12
            r7 = r13
            r2.logWithInstanceIdAndPosition(r3, r4, r5, r6, r7)
        L25:
            com.android.systemui.media.controls.domain.pipeline.interactor.MediaRecommendationsInteractor r8 = r8.interactor
            com.android.systemui.media.controls.data.repository.MediaFilterRepository r12 = r8.repository
            r12.mediaFromRecPackageName = r11
            android.os.Bundle r11 = r10.getExtras()
            if (r11 == 0) goto L55
            java.lang.String r12 = "com.google.android.apps.gsa.smartspace.extra.SMARTSPACE_INTENT"
            java.lang.String r11 = r11.getString(r12)
            if (r11 != 0) goto L3a
            goto L55
        L3a:
            r12 = 1
            android.content.Intent r12 = android.content.Intent.parseUri(r11, r12)     // Catch: java.net.URISyntaxException -> L46
            java.lang.String r13 = "KEY_OPEN_IN_FOREGROUND"
            boolean r11 = r12.getBooleanExtra(r13, r0)     // Catch: java.net.URISyntaxException -> L46
            goto L56
        L46:
            r12 = move-exception
            java.lang.String r13 = "Failed to create intent from URI: "
            java.lang.String r11 = r13.concat(r11)
            java.lang.String r13 = "MediaRecommendationsInteractor"
            android.util.Log.wtf(r13, r11)
            r12.printStackTrace()
        L55:
            r11 = r0
        L56:
            if (r11 == 0) goto L68
            r11 = 31
            java.lang.Integer r11 = java.lang.Integer.valueOf(r11)
            com.android.systemui.animation.ActivityTransitionAnimator$Controller r9 = r9.activityTransitionController(r11)
            com.android.systemui.plugins.ActivityStarter r8 = r8.activityStarter
            r8.postStartActivityDismissingKeyguard(r10, r0, r9)
            goto L75
        L68:
            android.content.Context r8 = r8.applicationContext
            r8.startActivity(r10)
            goto L75
        L6e:
            java.lang.String r8 = "MediaRecommendationsViewModel"
            java.lang.String r9 = "No tap action can be set up"
            android.util.Log.e(r8, r9)
        L75:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.controls.ui.viewmodel.MediaRecommendationsViewModel.access$onClicked(com.android.systemui.media.controls.ui.viewmodel.MediaRecommendationsViewModel, com.android.systemui.animation.Expandable, android.content.Intent, java.lang.String, com.android.internal.logging.InstanceId, int):void");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:13:0x013a  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x027a  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00b1  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x002a  */
    /* JADX WARN: Type inference failed for: r15v8, types: [java.lang.CharSequence] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:48:0x023d -> B:10:0x0259). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$toRecsViewModel(com.android.systemui.media.controls.ui.viewmodel.MediaRecommendationsViewModel r39, com.android.systemui.media.controls.shared.model.MediaRecommendationsModel r40, kotlin.coroutines.Continuation r41) {
        /*
            Method dump skipped, instructions count: 773
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.controls.ui.viewmodel.MediaRecommendationsViewModel.access$toRecsViewModel(com.android.systemui.media.controls.ui.viewmodel.MediaRecommendationsViewModel, com.android.systemui.media.controls.shared.model.MediaRecommendationsModel, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
