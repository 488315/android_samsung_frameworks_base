package com.android.systemui.qs;

import android.util.Log;
import com.android.systemui.ScRune;
import com.android.systemui.privacy.PrivacyItem;
import com.android.systemui.privacy.PrivacyItemController;
import com.android.systemui.privacy.PrivacyType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class HeaderPrivacyIconsController$picCallback$1 implements PrivacyItemController.Callback {
    public final /* synthetic */ HeaderPrivacyIconsController this$0;

    public HeaderPrivacyIconsController$picCallback$1(HeaderPrivacyIconsController headerPrivacyIconsController) {
        this.this$0 = headerPrivacyIconsController;
    }

    @Override // com.android.systemui.privacy.PrivacyConfig.Callback
    public final void onFlagLocationChanged(boolean z) {
        HeaderPrivacyIconsController headerPrivacyIconsController = this.this$0;
        if (headerPrivacyIconsController.locationIndicatorsEnabled != z) {
            headerPrivacyIconsController.locationIndicatorsEnabled = z;
            update$2$1();
        }
    }

    @Override // com.android.systemui.privacy.PrivacyConfig.Callback
    public final void onFlagMicCameraChanged(boolean z) {
        HeaderPrivacyIconsController headerPrivacyIconsController = this.this$0;
        if (headerPrivacyIconsController.micCameraIndicatorsEnabled != z) {
            headerPrivacyIconsController.micCameraIndicatorsEnabled = z;
            update$2$1();
        }
    }

    @Override // com.android.systemui.privacy.PrivacyItemController.Callback
    public final void onPrivacyItemsChanged(List list) {
        boolean z = true;
        if (ScRune.QUICK_SUPPORT_LOCATION_PRIVACY_CHIP) {
            List privacyList$frameworks__base__packages__SystemUI__android_common__SystemUI_core = this.this$0.getPrivacyList$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
            HeaderPrivacyIconsController headerPrivacyIconsController = this.this$0;
            ArrayList arrayList = new ArrayList();
            for (Object obj : privacyList$frameworks__base__packages__SystemUI__android_common__SystemUI_core) {
                PrivacyItem privacyItem = (PrivacyItem) obj;
                if (privacyItem.privacyType == PrivacyType.TYPE_LOCATION) {
                    headerPrivacyIconsController.getClass();
                    List<PrivacyItem> list2 = list;
                    if (!(list2 instanceof Collection) || !list2.isEmpty()) {
                        for (PrivacyItem privacyItem2 : list2) {
                            if (privacyItem2.privacyType != privacyItem.privacyType || !Intrinsics.areEqual(privacyItem2.application, privacyItem.application) || privacyItem2.timeStampElapsed != privacyItem.timeStampElapsed) {
                            }
                        }
                    }
                    arrayList.add(obj);
                }
            }
            HeaderPrivacyIconsController headerPrivacyIconsController2 = this.this$0;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj2 = arrayList.get(i);
                i++;
                PrivacyItem privacyItem3 = (PrivacyItem) obj2;
                privacyItem3.timeStampRemoved = headerPrivacyIconsController2.systemClock.elapsedRealtime();
                Log.i("HeaderPrivacyIconsController", "keepShowingItem  " + privacyItem3);
            }
            HeaderPrivacyIconsController headerPrivacyIconsController3 = this.this$0;
            List recentLocationPrivacyList$frameworks__base__packages__SystemUI__android_common__SystemUI_core = headerPrivacyIconsController3.getRecentLocationPrivacyList$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
            HeaderPrivacyIconsController headerPrivacyIconsController4 = this.this$0;
            ArrayList arrayList2 = new ArrayList();
            for (Object obj3 : recentLocationPrivacyList$frameworks__base__packages__SystemUI__android_common__SystemUI_core) {
                if (((PrivacyItem) obj3).timeStampRemoved > headerPrivacyIconsController4.systemClock.elapsedRealtime() - headerPrivacyIconsController4.UPDATE_CHIP_VISIBILITY) {
                    arrayList2.add(obj3);
                }
            }
            List listPlus = CollectionsKt___CollectionsKt.plus((Iterable) arrayList, (Collection) arrayList2);
            synchronized (headerPrivacyIconsController3) {
                headerPrivacyIconsController3.recentLocationPrivacyList = listPlus;
            }
            Log.i("HeaderPrivacyIconsController", "update recentLocationPrivacyList  " + this.this$0.getRecentLocationPrivacyList$frameworks__base__packages__SystemUI__android_common__SystemUI_core());
            HeaderPrivacyIconsController headerPrivacyIconsController5 = this.this$0;
            synchronized (headerPrivacyIconsController5) {
                headerPrivacyIconsController5.privacyList = list;
            }
            HeaderPrivacyIconsController headerPrivacyIconsController6 = this.this$0;
            List list3 = list;
            headerPrivacyIconsController6.privacyChip.setPrivacyList(CollectionsKt___CollectionsKt.plus((Iterable) headerPrivacyIconsController6.getRecentLocationPrivacyList$frameworks__base__packages__SystemUI__android_common__SystemUI_core(), (Collection) list3));
            HeaderPrivacyIconsController headerPrivacyIconsController7 = this.this$0;
            if (list3.isEmpty() && this.this$0.getRecentLocationPrivacyList$frameworks__base__packages__SystemUI__android_common__SystemUI_core().isEmpty()) {
                z = false;
            }
            headerPrivacyIconsController7.setChipVisibility(z);
            List privacyList$frameworks__base__packages__SystemUI__android_common__SystemUI_core2 = this.this$0.getPrivacyList$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
            ArrayList arrayList3 = new ArrayList();
            for (Object obj4 : privacyList$frameworks__base__packages__SystemUI__android_common__SystemUI_core2) {
                if (((PrivacyItem) obj4).privacyType == PrivacyType.TYPE_LOCATION) {
                    arrayList3.add(obj4);
                }
            }
            if (arrayList3.isEmpty() && !this.this$0.getRecentLocationPrivacyList$frameworks__base__packages__SystemUI__android_common__SystemUI_core().isEmpty()) {
                final HeaderPrivacyIconsController headerPrivacyIconsController8 = this.this$0;
                headerPrivacyIconsController8.delayableUiExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.qs.HeaderPrivacyIconsController$picCallback$1$onPrivacyItemsChanged$3
                    @Override // java.lang.Runnable
                    public final void run() {
                        List privacyList$frameworks__base__packages__SystemUI__android_common__SystemUI_core3 = headerPrivacyIconsController8.getPrivacyList$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
                        ArrayList arrayList4 = new ArrayList();
                        for (Object obj5 : privacyList$frameworks__base__packages__SystemUI__android_common__SystemUI_core3) {
                            if (((PrivacyItem) obj5).privacyType == PrivacyType.TYPE_LOCATION) {
                                arrayList4.add(obj5);
                            }
                        }
                        if (!arrayList4.isEmpty() || headerPrivacyIconsController8.getRecentLocationPrivacyList$frameworks__base__packages__SystemUI__android_common__SystemUI_core().isEmpty()) {
                            return;
                        }
                        Log.d("HeaderPrivacyIconsController", "Do executeDelayed  " + headerPrivacyIconsController8.getRecentLocationPrivacyList$frameworks__base__packages__SystemUI__android_common__SystemUI_core());
                        this.onPrivacyItemsChanged(headerPrivacyIconsController8.getPrivacyList$frameworks__base__packages__SystemUI__android_common__SystemUI_core());
                    }
                }, this.this$0.UPDATE_CHIP_VISIBILITY);
            }
        } else {
            this.this$0.privacyChip.setPrivacyList(list);
            this.this$0.setChipVisibility(true ^ list.isEmpty());
        }
        HeaderPrivacyIconsController headerPrivacyIconsController9 = this.this$0;
        int i2 = HeaderPrivacyIconsController.$r8$clinit;
        headerPrivacyIconsController9.notifyPrivacyItemsChanged(list);
    }

    public final void update$2$1() {
        int i = HeaderPrivacyIconsController.$r8$clinit;
        HeaderPrivacyIconsController headerPrivacyIconsController = this.this$0;
        headerPrivacyIconsController.updatePrivacyIconSlots();
        headerPrivacyIconsController.setChipVisibility(!headerPrivacyIconsController.privacyChip.privacyList.isEmpty());
        headerPrivacyIconsController.notifyPrivacyItemsChanged(headerPrivacyIconsController.privacyChip.privacyList);
    }
}
