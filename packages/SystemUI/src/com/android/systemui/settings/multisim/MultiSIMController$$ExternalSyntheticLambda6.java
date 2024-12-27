package com.android.systemui.settings.multisim;

import android.util.Log;
import com.android.systemui.settings.multisim.MultiSIMController;
import com.android.systemui.settings.multisim.MultiSIMPreferredSlotView;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

public final /* synthetic */ class MultiSIMController$$ExternalSyntheticLambda6 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MultiSIMController f$0;

    public /* synthetic */ MultiSIMController$$ExternalSyntheticLambda6(MultiSIMController multiSIMController, int i) {
        this.$r8$classId = i;
        this.f$0 = multiSIMController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        MultiSIMPreferredSlotView.PrefferedSlotPopupWindow prefferedSlotPopupWindow;
        int i = this.$r8$classId;
        MultiSIMController multiSIMController = this.f$0;
        switch (i) {
            case 0:
                multiSIMController.getClass();
                Log.d("MultiSIMController", "updateCurrentDefaultSlot list");
                ArrayList arrayList = new ArrayList(multiSIMController.mDefaultIdUpdateList);
                multiSIMController.mDefaultIdUpdateList.clear();
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    multiSIMController.updateCurrentDefaultSlot((MultiSIMController.ButtonType) it.next());
                }
                break;
            case 1:
                if (!multiSIMController.mData.equals(multiSIMController.mDataNotified)) {
                    for (int i2 = 0; i2 < multiSIMController.mDataCallbacks.size(); i2++) {
                        MultiSIMController.MultiSIMDataChangedCallback multiSIMDataChangedCallback = (MultiSIMController.MultiSIMDataChangedCallback) ((WeakReference) multiSIMController.mDataCallbacks.get(i2)).get();
                        if (multiSIMDataChangedCallback != null) {
                            MultiSIMData multiSIMData = new MultiSIMData();
                            multiSIMData.copyFrom(multiSIMController.mData);
                            multiSIMController.reverseSlotIfNeed(multiSIMData);
                            multiSIMDataChangedCallback.onDataChanged(multiSIMData);
                        }
                    }
                    multiSIMController.mDataNotified.copyFrom(multiSIMController.mData);
                    break;
                }
                break;
            default:
                for (int i3 = 0; i3 < multiSIMController.mVisCallbacks.size(); i3++) {
                    MultiSIMController.MultiSIMVisibilityChangedCallback multiSIMVisibilityChangedCallback = (MultiSIMController.MultiSIMVisibilityChangedCallback) ((WeakReference) multiSIMController.mVisCallbacks.get(i3)).get();
                    if (multiSIMVisibilityChangedCallback != null) {
                        boolean z = multiSIMController.mUIVisible;
                        MultiSIMPreferredSlotView multiSIMPreferredSlotView = (MultiSIMPreferredSlotView) multiSIMVisibilityChangedCallback;
                        multiSIMPreferredSlotView.updateButtonList();
                        if (!z && (prefferedSlotPopupWindow = multiSIMPreferredSlotView.mPopupWindow) != null) {
                            prefferedSlotPopupWindow.dismiss();
                            multiSIMPreferredSlotView.mPopupWindow = null;
                        }
                    }
                }
                break;
        }
    }
}
