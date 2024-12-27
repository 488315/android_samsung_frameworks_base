package com.android.systemui.popup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.UserHandle;
import com.android.systemui.CoreStartable;
import com.android.systemui.Dependency;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.popup.viewmodel.PopupUIViewModel;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class PopupUI implements CoreStartable, ConfigurationController.ConfigurationListener {
    public static final String TAG = "PopupUI";
    private final Context mContext;
    private LogWrapper mLogWrapper;
    private BroadcastReceiver mPopupUIReceiver = new BroadcastReceiver() { // from class: com.android.systemui.popup.PopupUI.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            PopupUI.this.mLogWrapper.d(PopupUI.TAG, "PopupUIReceiver.onReceive() action : " + action);
            action.getClass();
            if (action.equals(PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                Iterator it = PopupUI.this.mViewModelList.iterator();
                while (it.hasNext()) {
                    ((PopupUIViewModel) it.next()).dismiss();
                }
            } else {
                Iterator it2 = PopupUI.this.mViewModelList.iterator();
                while (it2.hasNext()) {
                    ((PopupUIViewModel) it2.next()).show(intent);
                }
            }
        }
    };
    private List<PopupUIViewModel> mViewModelList;

    public PopupUI(Context context, LogWrapper logWrapper, List<PopupUIViewModel> list) {
        this.mContext = context;
        this.mLogWrapper = logWrapper;
        this.mViewModelList = list;
    }

    @Override // com.android.systemui.CoreStartable
    public /* bridge */ /* synthetic */ boolean isDumpCritical() {
        return true;
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public void onUiModeChanged() {
        Iterator<PopupUIViewModel> it = this.mViewModelList.iterator();
        while (it.hasNext()) {
            it.next().dismiss();
        }
    }

    @Override // com.android.systemui.CoreStartable
    public void start() {
        ((ConfigurationControllerImpl) ((ConfigurationController) Dependency.sDependency.getDependencyInner(ConfigurationController.class))).addCallback(this);
        IntentFilter intentFilter = new IntentFilter();
        Iterator<PopupUIViewModel> it = this.mViewModelList.iterator();
        while (it.hasNext()) {
            String action = it.next().getAction();
            if (!intentFilter.hasAction(action)) {
                intentFilter.addAction(action);
            }
        }
        intentFilter.addAction(PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS);
        this.mContext.registerReceiverAsUser(this.mPopupUIReceiver, UserHandle.ALL, intentFilter, PopupUIUtil.POPUP_UI_PERMISSON, null, 2);
    }

    @Override // com.android.systemui.CoreStartable
    public /* bridge */ /* synthetic */ void onBootCompleted() {
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public /* bridge */ /* synthetic */ void onDensityOrFontScaleChanged() {
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public /* bridge */ /* synthetic */ void onDisplayDeviceTypeChanged() {
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public /* bridge */ /* synthetic */ void onLocaleListChanged() {
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public /* bridge */ /* synthetic */ void onMaxBoundsChanged() {
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public /* bridge */ /* synthetic */ void onSmallestScreenWidthChanged() {
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public /* bridge */ /* synthetic */ void onThemeChanged() {
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public /* bridge */ /* synthetic */ void onConfigChanged(Configuration configuration) {
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public /* bridge */ /* synthetic */ void onLayoutDirectionChanged(boolean z) {
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public /* bridge */ /* synthetic */ void onOrientationChanged(int i) {
    }

    @Override // com.android.systemui.CoreStartable
    public /* bridge */ /* synthetic */ void onTrimMemory(int i) {
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public /* bridge */ /* synthetic */ void dump(PrintWriter printWriter, String[] strArr) {
    }
}
