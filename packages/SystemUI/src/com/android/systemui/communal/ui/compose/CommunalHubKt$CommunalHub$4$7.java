package com.android.systemui.communal.ui.compose;

import android.content.pm.UserInfo;
import android.os.UserHandle;
import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.communal.ui.viewmodel.CommunalViewModel;
import com.android.systemui.settings.UserTrackerImpl;
import java.util.Iterator;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final /* synthetic */ class CommunalHubKt$CommunalHub$4$7 extends FunctionReferenceImpl implements Function0 {
    public CommunalHubKt$CommunalHub$4$7(Object obj) {
        super(0, obj, CommunalViewModel.class, "onEnableWorkProfileDialogConfirm", "onEnableWorkProfileDialogConfirm()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        Object obj;
        UserHandle userHandle;
        CommunalViewModel communalViewModel = (CommunalViewModel) this.receiver;
        CommunalInteractor communalInteractor = communalViewModel.communalInteractor;
        Iterator it = ((UserTrackerImpl) communalInteractor.userTracker).getUserProfiles().iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            if (((UserInfo) obj).isManagedProfile()) {
                break;
            }
        }
        UserInfo userInfo = (UserInfo) obj;
        if (userInfo != null && (userHandle = userInfo.getUserHandle()) != null) {
            communalInteractor.userManager.requestQuietModeEnabled(false, userHandle);
        }
        communalViewModel._isEnableWorkProfileDialogShowing.updateState(null, Boolean.FALSE);
        return Unit.INSTANCE;
    }
}
