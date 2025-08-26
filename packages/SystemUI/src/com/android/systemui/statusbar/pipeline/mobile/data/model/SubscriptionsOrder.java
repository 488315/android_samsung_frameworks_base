package com.android.systemui.statusbar.pipeline.mobile.data.model;

import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public final class SubscriptionsOrder {
    public final SubscriptionManager subscriptionManager;
    public final Map subscriptionsOrder = new LinkedHashMap();

    public SubscriptionsOrder(SubscriptionManager subscriptionManager) {
        this.subscriptionManager = subscriptionManager;
    }

    public final int getSimOrder(int i, List list) {
        ((LinkedHashMap) this.subscriptionsOrder).clear();
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            SubscriptionInfo subscriptionInfo = (SubscriptionInfo) it.next();
            if (subscriptionInfo != null) {
                arrayList.add(subscriptionInfo);
            }
        }
        int iIntValue = 0;
        if (arrayList.isEmpty()) {
            return 0;
        }
        synchronized (this) {
            try {
                int size = arrayList.size();
                int i2 = 0;
                int i3 = 0;
                while (i3 < size) {
                    Object obj = arrayList.get(i3);
                    i3++;
                    if (((SubscriptionInfo) obj).isEmbedded()) {
                        i2++;
                    }
                }
                if (i2 == 0) {
                    int size2 = arrayList.size();
                    int i4 = 0;
                    while (i4 < size2) {
                        Object obj2 = arrayList.get(i4);
                        i4++;
                        SubscriptionInfo subscriptionInfo2 = (SubscriptionInfo) obj2;
                        this.subscriptionsOrder.put(Integer.valueOf(subscriptionInfo2.getSubscriptionId()), Integer.valueOf(SubscriptionManager.getSlotIndex(subscriptionInfo2.getSubscriptionId()) == -1 ? 0 : SubscriptionManager.getSlotIndex(subscriptionInfo2.getSubscriptionId())));
                    }
                } else if (i2 != 1) {
                    if (i2 == 2) {
                        if (((SubscriptionInfo) arrayList.get(0)).getSubscriptionId() > ((SubscriptionInfo) arrayList.get(1)).getSubscriptionId()) {
                            this.subscriptionsOrder.put(Integer.valueOf(((SubscriptionInfo) arrayList.get(0)).getSubscriptionId()), 1);
                        } else {
                            this.subscriptionsOrder.put(Integer.valueOf(((SubscriptionInfo) arrayList.get(0)).getSubscriptionId()), 0);
                        }
                    }
                } else if (arrayList.size() == 1) {
                    this.subscriptionsOrder.put(Integer.valueOf(((SubscriptionInfo) arrayList.get(0)).getSubscriptionId()), 0);
                } else if (arrayList.size() == 2) {
                    int size3 = arrayList.size();
                    int i5 = 0;
                    while (i5 < size3) {
                        Object obj3 = arrayList.get(i5);
                        i5++;
                        SubscriptionInfo subscriptionInfo3 = (SubscriptionInfo) obj3;
                        if (subscriptionInfo3.isEmbedded()) {
                        }
                    }
                }
                if (!this.subscriptionsOrder.isEmpty()) {
                    if (((LinkedHashMap) this.subscriptionsOrder).get(Integer.valueOf(i)) != null) {
                        Object obj4 = ((LinkedHashMap) this.subscriptionsOrder).get(Integer.valueOf(i));
                        obj4.getClass();
                        iIntValue = ((Number) obj4).intValue();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return iIntValue;
    }

    public final int getSimOrderByIds(int i, List list) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            SubscriptionInfo activeSubscriptionInfo = this.subscriptionManager.getActiveSubscriptionInfo(((Number) it.next()).intValue());
            if (activeSubscriptionInfo != null) {
                arrayList.add(activeSubscriptionInfo);
            }
        }
        return getSimOrder(i, arrayList);
    }
}
