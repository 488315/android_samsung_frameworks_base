package com.android.systemui.wallet.util;

import android.service.quickaccesswallet.WalletCard;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public abstract class WalletCardUtilsKt {
    public static final List getPaymentCards(List list) {
        List list2 = list;
        if (!(list2 instanceof Collection) || !list2.isEmpty()) {
            Iterator it = list2.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                if (((WalletCard) it.next()).getCardType() != 0) {
                    list = new ArrayList();
                    for (Object obj : list2) {
                        if (((WalletCard) obj).getCardType() == 1) {
                            list.add(obj);
                        }
                    }
                }
            }
        }
        return list;
    }
}
