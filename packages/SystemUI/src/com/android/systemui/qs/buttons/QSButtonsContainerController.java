package com.android.systemui.qs.buttons;

import android.net.Uri;
import android.os.Handler;
import android.os.UserManager;
import android.provider.Settings;
import android.util.Log;
import com.android.systemui.R;
import com.android.systemui.qs.SecQSPanelController;
import com.android.systemui.qs.buttons.QSButtonsContainer;
import com.android.systemui.qs.buttons.QSMumButton;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.ViewController;
import java.util.Arrays;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class QSButtonsContainerController extends ViewController {
    private final SettingsHelper mSettingsHelper;
    private final SettingsHelper.OnChangedCallback mSettingsListener;
    public final Uri[] mSettingsValueList;

    public static void $r8$lambda$sgux4KK4mXMVCKnJbWX2C18Qu0k(QSButtonsContainerController qSButtonsContainerController, Uri uri) {
        if (Arrays.asList(qSButtonsContainerController.mSettingsValueList).contains(uri)) {
            Log.d("QSButtonsContainerController", "onChanged() - " + uri);
            QSButtonsContainer qSButtonsContainer = (QSButtonsContainer) qSButtonsContainerController.mView;
            qSButtonsContainer.getClass();
            qSButtonsContainer.post(new QSButtonsContainer$$ExternalSyntheticLambda0(qSButtonsContainer));
        }
    }

    public QSButtonsContainerController(QSButtonsContainer qSButtonsContainer, SettingsHelper settingsHelper, SecQSPanelController secQSPanelController, Handler handler) {
        super(qSButtonsContainer);
        this.mSettingsValueList = new Uri[]{Settings.System.getUriFor(SettingsHelper.INDEX_EMERGENCY_MODE)};
        this.mSettingsListener = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.qs.buttons.QSButtonsContainerController$$ExternalSyntheticLambda0
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                QSButtonsContainerController.$r8$lambda$sgux4KK4mXMVCKnJbWX2C18Qu0k(QSButtonsContainerController.this, uri);
            }
        };
        this.mSettingsHelper = settingsHelper;
        ((QSEditButton) ((QSButtonsContainer) this.mView).findViewById(R.id.edit_button_container)).mQsPanelController = secQSPanelController;
        ((QSMumButton) ((QSButtonsContainer) this.mView).findViewById(R.id.mum_button_container)).mMainHandler = handler;
    }

    public final void closeQSTooltip() {
        QSButtonsContainer qSButtonsContainer = (QSButtonsContainer) this.mView;
        QSButtonsContainer.CloseTooltipWindow closeTooltipWindow = qSButtonsContainer.mCloseTooltipWindow;
        if (closeTooltipWindow != null) {
            closeTooltipWindow.closeTooltip();
            qSButtonsContainer.mCloseTooltipWindow = null;
        }
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
            if (UserManager.supportsMultipleUsers()) {
                QSMumButton qSMumButton = qSButtonsContainer.mMumButton;
                if (z != qSMumButton.mListening) {
                    qSMumButton.mListening = z;
                    QSMumButton.MumAndDexHelper mumAndDexHelper = qSMumButton.mMumAndDexHelper;
                    if (mumAndDexHelper != null && z) {
                        qSMumButton.post(new QSMumButton$$ExternalSyntheticLambda0(mumAndDexHelper, 0));
                    }
                }
            }
        }
        QSButtonsContainer qSButtonsContainer2 = (QSButtonsContainer) this.mView;
        if (qSButtonsContainer2.mExpanded == z2) {
            return;
        }
        qSButtonsContainer2.mExpanded = z2;
        if (UserManager.supportsMultipleUsers()) {
            QSMumButton qSMumButton2 = qSButtonsContainer2.mMumButton;
            if (qSMumButton2.mExpanded != z2) {
                qSMumButton2.mExpanded = z2;
            }
        }
        qSButtonsContainer2.post(new QSButtonsContainer$$ExternalSyntheticLambda0(qSButtonsContainer2));
    }
}
