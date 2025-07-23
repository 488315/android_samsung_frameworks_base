package com.android.systemui.media.controls.ui.viewmodel;

import android.app.PendingIntent;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.util.Log;
import com.android.internal.logging.InstanceId;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.R;
import com.android.systemui.animation.DialogCuj;
import com.android.systemui.animation.Expandable;
import com.android.systemui.media.controls.domain.pipeline.interactor.MediaControlInteractor;
import com.android.systemui.media.controls.shared.model.MediaControlModel;
import com.android.systemui.media.controls.shared.model.MediaDeviceData;
import com.android.systemui.media.controls.ui.viewmodel.MediaControlViewModel;
import com.android.systemui.media.controls.ui.viewmodel.SeekBarViewModel;
import com.android.systemui.media.controls.util.MediaUiEvent;
import com.android.systemui.media.controls.util.MediaUiEventLogger;
import com.android.systemui.media.dialog.MediaOutputDialogManager;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class MediaControlViewModel$$ExternalSyntheticLambda1 implements Function1 {
    public final /* synthetic */ int $r8$classId = 2;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ MediaControlViewModel f$1;
    public final /* synthetic */ MediaControlModel f$2;

    public /* synthetic */ MediaControlViewModel$$ExternalSyntheticLambda1(MediaControlModel mediaControlModel, MediaControlViewModel mediaControlViewModel, MediaController mediaController) {
        this.f$2 = mediaControlModel;
        this.f$1 = mediaControlViewModel;
        this.f$0 = mediaController;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        PendingIntent pendingIntent;
        Double d;
        MediaControlViewModel mediaControlViewModel = this.f$1;
        MediaControlModel mediaControlModel = this.f$2;
        Object obj2 = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                if (!((Boolean) obj).booleanValue()) {
                    String string = mediaControlViewModel.applicationContext.getString(R.string.controls_media_playing_item_description, mediaControlModel.songName, mediaControlModel.artistName, mediaControlModel.appName);
                    string.getClass();
                    break;
                } else {
                    MediaControlViewModel.Companion companion = MediaControlViewModel.Companion;
                    break;
                }
            case 1:
                Expandable expandable = (Expandable) obj;
                MediaUiEventLogger mediaUiEventLogger = mediaControlViewModel.logger;
                int i = mediaControlModel.uid;
                InstanceId instanceId = mediaControlModel.instanceId;
                UiEventLogger uiEventLogger = mediaUiEventLogger.logger;
                MediaUiEvent mediaUiEvent = MediaUiEvent.OPEN_OUTPUT_SWITCHER;
                String str = mediaControlModel.packageName;
                uiEventLogger.logWithInstanceId(mediaUiEvent, i, str, instanceId);
                MediaDeviceData mediaDeviceData = (MediaDeviceData) obj2;
                MediaControlInteractor mediaControlInteractor = mediaControlViewModel.interactor;
                if (mediaDeviceData == null || (pendingIntent = mediaDeviceData.intent) == null) {
                    MediaSession.Token token = mediaControlModel.token;
                    mediaControlInteractor.getClass();
                    MediaOutputDialogManager.createAndShowWithController$default(mediaControlInteractor.mediaOutputDialogManager, str, true, expandable.dialogTransitionController(new DialogCuj(58, "media_output")), token, 8);
                } else {
                    mediaControlInteractor.getClass();
                    if (!pendingIntent.isActivity()) {
                        Log.w("MediaControlInteractor", "Device pending intent of instanceId=" + mediaControlInteractor.instanceId + " is not an activity.");
                    } else if (!mediaControlInteractor.launchOverLockscreen(null, pendingIntent)) {
                        mediaControlInteractor.activityStarter.postStartActivityDismissingKeyguard(pendingIntent);
                    }
                }
                break;
            default:
                final MediaController mediaController = (MediaController) obj2;
                final SeekBarViewModel seekBarViewModel = (SeekBarViewModel) obj;
                MediaControlViewModel.Companion companion2 = MediaControlViewModel.Companion;
                if (!mediaControlModel.isResume || (d = mediaControlModel.resumeProgress) == null) {
                    mediaControlViewModel.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.controls.ui.viewmodel.MediaControlViewModel$toViewModel$6$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            SeekBarViewModel.this.updateController(mediaController);
                        }
                    });
                } else {
                    double doubleValue = d.doubleValue();
                    seekBarViewModel.getClass();
                    seekBarViewModel.set_data(new SeekBarViewModel.Progress(true, false, false, false, Integer.valueOf((int) (doubleValue * 100)), 100, false));
                }
                break;
        }
        return Unit.INSTANCE;
    }

    public /* synthetic */ MediaControlViewModel$$ExternalSyntheticLambda1(GutsViewModel gutsViewModel, MediaControlViewModel mediaControlViewModel, MediaControlModel mediaControlModel) {
        this.f$0 = gutsViewModel;
        this.f$1 = mediaControlViewModel;
        this.f$2 = mediaControlModel;
    }

    public /* synthetic */ MediaControlViewModel$$ExternalSyntheticLambda1(boolean z, MediaControlViewModel mediaControlViewModel, MediaControlModel mediaControlModel, MediaDeviceData mediaDeviceData) {
        this.f$1 = mediaControlViewModel;
        this.f$2 = mediaControlModel;
        this.f$0 = mediaDeviceData;
    }
}
