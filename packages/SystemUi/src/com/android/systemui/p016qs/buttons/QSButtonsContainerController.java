package com.android.systemui.p016qs.buttons;

import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import com.android.systemui.R;
import com.android.systemui.p016qs.SecQSPanelController;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.ViewController;
import java.util.Arrays;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class QSButtonsContainerController extends ViewController {
    public final SettingsHelper mSettingsHelper;
    public final QSButtonsContainerController$$ExternalSyntheticLambda0 mSettingsListener;
    public final Uri[] mSettingsValueList;

    /* JADX WARN: Type inference failed for: r1v8, types: [com.android.systemui.qs.buttons.QSButtonsContainerController$$ExternalSyntheticLambda0] */
    public QSButtonsContainerController(QSButtonsContainer qSButtonsContainer, SettingsHelper settingsHelper, SecQSPanelController secQSPanelController) {
        super(qSButtonsContainer);
        this.mSettingsValueList = new Uri[]{Settings.System.getUriFor("emergency_mode")};
        this.mSettingsHelper = settingsHelper;
        this.mSettingsListener = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.qs.buttons.QSButtonsContainerController$$ExternalSyntheticLambda0
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                QSButtonsContainerController qSButtonsContainerController = QSButtonsContainerController.this;
                if (Arrays.asList(qSButtonsContainerController.mSettingsValueList).contains(uri)) {
                    Log.d("QSButtonContainerController", "onChanged() - " + uri);
                    QSButtonsContainer qSButtonsContainer2 = (QSButtonsContainer) qSButtonsContainerController.mView;
                    qSButtonsContainer2.getClass();
                    qSButtonsContainer2.post(new QSButtonsContainer$$ExternalSyntheticLambda0(qSButtonsContainer2));
                }
            }
        };
        ((QSEditButton) ((QSButtonsContainer) this.mView).findViewById(R.id.edit_button_container)).mQsPanelController = secQSPanelController;
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        this.mSettingsHelper.registerCallback(this.mSettingsListener, this.mSettingsValueList);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        this.mSettingsHelper.unregisterCallback(this.mSettingsListener);
    }

    public final void setListening(boolean z, boolean z2) {
        QSButtonsContainer qSButtonsContainer = (QSButtonsContainer) this.mView;
        if (z != qSButtonsContainer.mListening) {
            qSButtonsContainer.mListening = z;
            if (DeviceState.supportsMultipleUsers()) {
                QSMumButton qSMumButton = qSButtonsContainer.mMumButton;
                if (z != qSMumButton.mListening) {
                    qSMumButton.mListening = z;
                    if (qSMumButton.mMumAndDexHelper != null && z) {
                        qSMumButton.post(new QSMumButton$$ExternalSyntheticLambda0(qSMumButton, 0));
                    }
                }
            }
        }
        QSButtonsContainer qSButtonsContainer2 = (QSButtonsContainer) this.mView;
        if (qSButtonsContainer2.mExpanded == z2) {
            return;
        }
        qSButtonsContainer2.mExpanded = z2;
        if (DeviceState.supportsMultipleUsers()) {
            QSMumButton qSMumButton2 = qSButtonsContainer2.mMumButton;
            if (qSMumButton2.mExpanded != z2) {
                qSMumButton2.mExpanded = z2;
            }
        }
        qSButtonsContainer2.post(new QSButtonsContainer$$ExternalSyntheticLambda0(qSButtonsContainer2));
    }

    @Override // com.android.systemui.util.ViewController
    public final void onInit() {
    }
}
