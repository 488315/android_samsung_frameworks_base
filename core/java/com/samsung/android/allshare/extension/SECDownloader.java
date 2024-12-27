package com.samsung.android.allshare.extension;

import android.os.Bundle;
import android.os.Parcelable;

import com.samsung.android.allshare.DLog;
import com.samsung.android.allshare.IAllShareConnector;
import com.samsung.android.allshare.Item;
import com.sec.android.allshare.iface.CVMessage;
import com.sec.android.allshare.iface.IBundleHolder;
import com.sec.android.allshare.iface.message.AllShareAction;
import com.sec.android.allshare.iface.message.AllShareKey;

import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes3.dex */
public class SECDownloader {
    private static final String TAG_CLASS = "SECDownloader";
    private IAllShareConnector mAllShareConnector;

    public SECDownloader(IAllShareConnector connector) {
        this.mAllShareConnector = null;
        if (connector == null) {
            DLog.w_api(TAG_CLASS, "Connection FAIL: AllShare Service Connector does not exist");
        } else {
            this.mAllShareConnector = connector;
        }
    }

    public boolean Download(String serverName, ArrayList<Item> itemList) {
        boolean result = false;
        if (this.mAllShareConnector == null
                || !this.mAllShareConnector.isAllShareServiceConnected()) {
            DLog.w_api(TAG_CLASS, "Download, AllShare Service is not available");
            return false;
        }
        if (itemList == null || itemList.isEmpty()) {
            DLog.w_api(TAG_CLASS, "Download, itemList is null or empty");
            return false;
        }
        ArrayList<Bundle> bundleList = new ArrayList<>();
        Iterator<Item> it = itemList.iterator();
        while (it.hasNext()) {
            Parcelable parcelable = (Item) it.next();
            if (parcelable != null && (parcelable instanceof IBundleHolder)) {
                bundleList.add(((IBundleHolder) parcelable).getBundle());
                if (bundleList.size() >= 50) {
                    break;
                }
            }
        }
        CVMessage req_msg = new CVMessage();
        req_msg.setActionID(AllShareAction.ACTION_DOWNLOAD_REQUEST);
        Bundle req_bundle = new Bundle();
        req_bundle.putString(AllShareKey.BUNDLE_STRING_DEVICE_NAME, serverName);
        req_bundle.putParcelableArrayList(
                AllShareKey.BUNDLE_PARCELABLE_ARRAYLIST_CONTENT_URI, bundleList);
        req_msg.setBundle(req_bundle);
        CVMessage res_msg = this.mAllShareConnector.requestCVMSync(req_msg);
        if (res_msg == null) {
            DLog.w_api(TAG_CLASS, "Download, res_msg is null");
            return false;
        }
        Bundle res_bundle = res_msg.getBundle();
        if (res_bundle == null) {
            DLog.w_api(TAG_CLASS, "Download, res_bundle is null");
            return false;
        }
        try {
            result = res_bundle.getBoolean(AllShareKey.BUNDLE_BOOLEAN_RESULT);
        } catch (Exception e) {
            DLog.w_api(TAG_CLASS, "Download Exception", e);
        }
        if (result && itemList.size() > 50) {
            downloadRemains(serverName, itemList);
        }
        return result;
    }

    private void downloadRemains(final String serverName, final ArrayList<Item> itemList) {
        new Thread(
                        new Runnable() { // from class:
                                         // com.samsung.android.allshare.extension.SECDownloader.1
                            @Override // java.lang.Runnable
                            public void run() {
                                DLog.i_api(
                                        SECDownloader.TAG_CLASS,
                                        "downloadRemains, Thread Start!!!");
                                int i = 50;
                                while (true) {
                                    ArrayList<Bundle> bundleList = new ArrayList<>();
                                    while (i < itemList.size() && bundleList.size() < 50) {
                                        Parcelable parcelable = (Item) itemList.get(i);
                                        if (parcelable != null
                                                && (parcelable instanceof IBundleHolder)) {
                                            bundleList.add(
                                                    ((IBundleHolder) parcelable).getBundle());
                                        }
                                        i++;
                                    }
                                    CVMessage req_msg = new CVMessage();
                                    req_msg.setActionID(AllShareAction.ACTION_DOWNLOAD_REQUEST);
                                    Bundle req_bundle = new Bundle();
                                    req_bundle.putString(
                                            AllShareKey.BUNDLE_STRING_DEVICE_NAME, serverName);
                                    req_bundle.putParcelableArrayList(
                                            AllShareKey.BUNDLE_PARCELABLE_ARRAYLIST_CONTENT_URI,
                                            bundleList);
                                    req_msg.setBundle(req_bundle);
                                    CVMessage res_msg =
                                            SECDownloader.this.mAllShareConnector.requestCVMSync(
                                                    req_msg);
                                    if (res_msg == null) {
                                        DLog.w_api(
                                                SECDownloader.TAG_CLASS,
                                                "downloadRemains, res_msg is null");
                                        break;
                                    }
                                    Bundle res_bundle = res_msg.getBundle();
                                    if (res_bundle == null) {
                                        DLog.w_api(
                                                SECDownloader.TAG_CLASS,
                                                "downloadRemains, res_bundle is null");
                                        break;
                                    } else if (i == itemList.size()) {
                                        DLog.i_api(
                                                SECDownloader.TAG_CLASS,
                                                "downloadRemains, finish size = " + i);
                                        break;
                                    }
                                }
                                DLog.i_api(
                                        SECDownloader.TAG_CLASS, "downloadRemains, Thread End!!!");
                            }
                        })
                .start();
    }
}
