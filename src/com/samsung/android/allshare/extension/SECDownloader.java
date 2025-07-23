package com.samsung.android.allshare.extension;

import android.os.Bundle;
import android.os.Parcelable;
import com.samsung.android.allshare.DLog;
import com.samsung.android.allshare.IAllShareConnector;
import com.samsung.android.allshare.Item;
import com.samsung.android.allshare.ServiceConnectionChecker;
import com.sec.android.allshare.iface.CVMessage;
import com.sec.android.allshare.iface.IBundleHolder;
import com.sec.android.allshare.iface.message.AllShareAction;
import com.sec.android.allshare.iface.message.AllShareKey;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes6.dex */
public class SECDownloader {
    private static final String TAG_CLASS = "SECDownloader";
    private IAllShareConnector mAllShareConnector;

    public SECDownloader(IAllShareConnector iAllShareConnector) {
        this.mAllShareConnector = null;
        if (iAllShareConnector == null) {
            DLog.w_api(TAG_CLASS, "Connection FAIL: AllShare Service Connector does not exist");
        } else {
            this.mAllShareConnector = iAllShareConnector;
        }
    }

    public boolean Download(String str, ArrayList<Item> arrayList) {
        boolean z = false;
        if (!ServiceConnectionChecker.isAllShareServiceConnected(this.mAllShareConnector)) {
            DLog.w_api(TAG_CLASS, "Download, AllShare Service is not available");
            return false;
        }
        if (arrayList == null || arrayList.isEmpty()) {
            DLog.w_api(TAG_CLASS, "Download, itemList is null or empty");
            return false;
        }
        ArrayList<? extends Parcelable> arrayList2 = new ArrayList<>();
        Iterator<Item> it = arrayList.iterator();
        while (it.hasNext()) {
            Parcelable parcelable = (Item) it.next();
            if (parcelable != null && (parcelable instanceof IBundleHolder)) {
                arrayList2.add(((IBundleHolder) parcelable).getBundle());
                if (arrayList2.size() >= 50) {
                    break;
                }
            }
        }
        CVMessage cVMessage = new CVMessage();
        cVMessage.setActionID(AllShareAction.ACTION_DOWNLOAD_REQUEST);
        Bundle bundle = new Bundle();
        bundle.putString(AllShareKey.BUNDLE_STRING_DEVICE_NAME, str);
        bundle.putParcelableArrayList(AllShareKey.BUNDLE_PARCELABLE_ARRAYLIST_CONTENT_URI, arrayList2);
        cVMessage.setBundle(bundle);
        CVMessage requestCVMSync = this.mAllShareConnector.requestCVMSync(cVMessage);
        if (requestCVMSync == null) {
            DLog.w_api(TAG_CLASS, "Download, res_msg is null");
            return false;
        }
        Bundle bundle2 = requestCVMSync.getBundle();
        if (bundle2 == null) {
            DLog.w_api(TAG_CLASS, "Download, res_bundle is null");
            return false;
        }
        try {
            z = bundle2.getBoolean(AllShareKey.BUNDLE_BOOLEAN_RESULT);
        } catch (Exception e) {
            DLog.w_api(TAG_CLASS, "Download Exception", e);
        }
        if (z && arrayList.size() > 50) {
            downloadRemains(str, arrayList);
        }
        return z;
    }

    private void downloadRemains(final String str, final ArrayList<Item> arrayList) {
        new Thread(new Runnable() { // from class: com.samsung.android.allshare.extension.SECDownloader.1
            @Override // java.lang.Runnable
            public void run() {
                DLog.i_api(SECDownloader.TAG_CLASS, "downloadRemains, Thread Start!!!");
                int i = 50;
                while (true) {
                    ArrayList<? extends Parcelable> arrayList2 = new ArrayList<>();
                    while (i < arrayList.size() && arrayList2.size() < 50) {
                        Parcelable parcelable = (Item) arrayList.get(i);
                        if (parcelable != null && (parcelable instanceof IBundleHolder)) {
                            arrayList2.add(((IBundleHolder) parcelable).getBundle());
                        }
                        i++;
                    }
                    CVMessage cVMessage = new CVMessage();
                    cVMessage.setActionID(AllShareAction.ACTION_DOWNLOAD_REQUEST);
                    Bundle bundle = new Bundle();
                    bundle.putString(AllShareKey.BUNDLE_STRING_DEVICE_NAME, str);
                    bundle.putParcelableArrayList(AllShareKey.BUNDLE_PARCELABLE_ARRAYLIST_CONTENT_URI, arrayList2);
                    cVMessage.setBundle(bundle);
                    CVMessage requestCVMSync = SECDownloader.this.mAllShareConnector.requestCVMSync(cVMessage);
                    if (requestCVMSync == null) {
                        DLog.w_api(SECDownloader.TAG_CLASS, "downloadRemains, res_msg is null");
                        break;
                    }
                    if (requestCVMSync.getBundle() == null) {
                        DLog.w_api(SECDownloader.TAG_CLASS, "downloadRemains, res_bundle is null");
                        break;
                    } else if (i == arrayList.size()) {
                        DLog.i_api(SECDownloader.TAG_CLASS, "downloadRemains, finish size = " + i);
                        break;
                    }
                }
                DLog.i_api(SECDownloader.TAG_CLASS, "downloadRemains, Thread End!!!");
            }
        }).start();
    }
}
