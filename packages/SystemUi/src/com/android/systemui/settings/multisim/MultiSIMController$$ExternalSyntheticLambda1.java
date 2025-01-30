package com.android.systemui.settings.multisim;

import android.util.Log;
import com.android.systemui.settings.multisim.MultiSIMController;
import com.android.systemui.settings.multisim.MultiSIMPreferredSlotView;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class MultiSIMController$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MultiSIMController f$0;

    public /* synthetic */ MultiSIMController$$ExternalSyntheticLambda1(MultiSIMController multiSIMController, int i) {
        this.$r8$classId = i;
        this.f$0 = multiSIMController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        MultiSIMPreferredSlotView.PrefferedSlotPopupWindow prefferedSlotPopupWindow;
        int i = 0;
        switch (this.$r8$classId) {
            case 0:
                MultiSIMController multiSIMController = this.f$0;
                if (!multiSIMController.mData.equals(multiSIMController.mDataNotified)) {
                    while (true) {
                        ArrayList arrayList = multiSIMController.mDataCallbacks;
                        if (i >= arrayList.size()) {
                            multiSIMController.mDataNotified.copyFrom(multiSIMController.mData);
                            break;
                        } else {
                            MultiSIMController.MultiSIMDataChangedCallback multiSIMDataChangedCallback = (MultiSIMController.MultiSIMDataChangedCallback) ((WeakReference) arrayList.get(i)).get();
                            if (multiSIMDataChangedCallback != null) {
                                MultiSIMData multiSIMData = new MultiSIMData();
                                multiSIMData.copyFrom(multiSIMController.mData);
                                multiSIMController.reverseSlotIfNeed(multiSIMData);
                                multiSIMDataChangedCallback.onDataChanged(multiSIMData);
                            }
                            i++;
                        }
                    }
                }
                break;
            case 1:
                MultiSIMController multiSIMController2 = this.f$0;
                int i2 = 0;
                while (true) {
                    ArrayList arrayList2 = multiSIMController2.mVisCallbacks;
                    if (i2 >= arrayList2.size()) {
                        break;
                    } else {
                        MultiSIMController.MultiSIMVisibilityChangedCallback multiSIMVisibilityChangedCallback = (MultiSIMController.MultiSIMVisibilityChangedCallback) ((WeakReference) arrayList2.get(i2)).get();
                        if (multiSIMVisibilityChangedCallback != null) {
                            boolean z = multiSIMController2.mUIVisible;
                            MultiSIMPreferredSlotView multiSIMPreferredSlotView = (MultiSIMPreferredSlotView) multiSIMVisibilityChangedCallback;
                            multiSIMPreferredSlotView.setVisibility(z ? 0 : 8);
                            multiSIMPreferredSlotView.updateButtonList();
                            if (!z && (prefferedSlotPopupWindow = multiSIMPreferredSlotView.mPopupWindow) != null) {
                                prefferedSlotPopupWindow.dismiss();
                                multiSIMPreferredSlotView.mPopupWindow = null;
                            }
                        }
                        i2++;
                    }
                }
                break;
            default:
                MultiSIMController multiSIMController3 = this.f$0;
                multiSIMController3.getClass();
                Log.d("MultiSIMController", "updateCurrentDefaultSlot list");
                ArrayList arrayList3 = multiSIMController3.mDefaultIdUpdateList;
                ArrayList arrayList4 = new ArrayList(arrayList3);
                arrayList3.clear();
                Iterator it = arrayList4.iterator();
                while (it.hasNext()) {
                    multiSIMController3.updateCurrentDefaultSlot((MultiSIMController.ButtonType) it.next());
                }
                break;
        }
    }
}
