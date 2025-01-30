package com.android.systemui.popup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import com.android.systemui.CoreStartable;
import com.android.systemui.Dependency;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.popup.viewmodel.PopupUIViewModel;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PopupUI implements CoreStartable, ConfigurationController.ConfigurationListener {
    public final Context mContext;
    public final LogWrapper mLogWrapper;
    public final C19231 mPopupUIReceiver = new BroadcastReceiver() { // from class: com.android.systemui.popup.PopupUI.1
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            PopupUI.this.mLogWrapper.m98d("PopupUI", "PopupUIReceiver.onReceive() action : " + action);
            action.getClass();
            if (!action.equals("android.intent.action.CLOSE_SYSTEM_DIALOGS")) {
                Iterator it = PopupUI.this.mViewModelList.iterator();
                while (it.hasNext()) {
                    ((PopupUIViewModel) it.next()).show(intent);
                }
            } else {
                intent.getIntExtra("displayId", -1);
                Iterator it2 = PopupUI.this.mViewModelList.iterator();
                while (it2.hasNext()) {
                    ((PopupUIViewModel) it2.next()).dismiss();
                }
            }
        }
    };
    public final List mViewModelList;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.popup.PopupUI$1] */
    public PopupUI(Context context, LogWrapper logWrapper, List<PopupUIViewModel> list) {
        this.mContext = context;
        this.mLogWrapper = logWrapper;
        this.mViewModelList = list;
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onUiModeChanged() {
        Iterator it = this.mViewModelList.iterator();
        while (it.hasNext()) {
            ((PopupUIViewModel) it.next()).dismiss();
        }
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        ((ConfigurationControllerImpl) ((ConfigurationController) Dependency.get(ConfigurationController.class))).addCallback(this);
        IntentFilter intentFilter = new IntentFilter();
        Iterator it = this.mViewModelList.iterator();
        while (it.hasNext()) {
            String action = ((PopupUIViewModel) it.next()).getAction();
            if (!intentFilter.hasAction(action)) {
                intentFilter.addAction(action);
            }
        }
        intentFilter.addAction("android.intent.action.CLOSE_SYSTEM_DIALOGS");
        this.mContext.registerReceiverAsUser(this.mPopupUIReceiver, UserHandle.ALL, intentFilter, "com.samsung.systemui.POPUP_UI_PERMISSION", null, 2);
    }
}
