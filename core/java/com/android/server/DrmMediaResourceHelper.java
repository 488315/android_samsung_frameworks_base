package com.android.server;

import android.util.Log;

import com.samsung.android.media.SemMediaResourceHelper;

import java.util.ArrayList;
import java.util.Iterator;

class DrmMediaResourceHelper {
    private String TAG = "DrmMediaResourceHelper";
    private SemMediaResourceHelper mSemMediaResourceHelper = null;
    private ArrayList<Integer> BoostedPIDs = new ArrayList<>(5);
    private final SemMediaResourceHelper.CodecStateChangedListener
            mMediaResourceCodecStateChangeListener =
                    new SemMediaResourceHelper
                            .CodecStateChangedListener() { // from class:
                                                           // com.android.server.DrmMediaResourceHelper.1
                        @Override // com.samsung.android.media.SemMediaResourceHelper.CodecStateChangedListener
                        public void onStateChanged(
                                ArrayList<SemMediaResourceHelper.MediaResourceInfo>
                                        mediaResourceInfo) {
                            if (mediaResourceInfo != null && mediaResourceInfo.size() > 0) {
                                Log.i(
                                        DrmMediaResourceHelper.this.TAG,
                                        "onStateChanged size = " + mediaResourceInfo.size());
                                for (int i = 0; i < mediaResourceInfo.size(); i++) {
                                    Log.i(
                                            DrmMediaResourceHelper.this.TAG,
                                            "resource type: "
                                                    + mediaResourceInfo.get(i).getResourceType()
                                                    + ",,,isSecured :  "
                                                    + mediaResourceInfo.get(i).isSecured()
                                                    + "IsEncoder : "
                                                    + mediaResourceInfo.get(i).isEncoder()
                                                    + "getCodecState : "
                                                    + mediaResourceInfo.get(i).getCodecState()
                                                    + "getpid : "
                                                    + mediaResourceInfo.get(i).getPid());
                                    int pid = mediaResourceInfo.get(i).getPid();
                                    if (pid > 0
                                            && mediaResourceInfo.get(i).isSecured()
                                            && !mediaResourceInfo.get(i).isEncoder()
                                            && mediaResourceInfo.get(i).getCodecState() == 1) {
                                        Log.e(
                                                DrmMediaResourceHelper.this.TAG,
                                                "CodecStateChangedListener resource type: "
                                                        + mediaResourceInfo.get(i).getResourceType()
                                                        + ",,,isSecured :  "
                                                        + mediaResourceInfo.get(i).isSecured()
                                                        + "IsEncoder : "
                                                        + mediaResourceInfo.get(i).isEncoder()
                                                        + "getCodecState : "
                                                        + mediaResourceInfo.get(i).getCodecState()
                                                        + "getpid : "
                                                        + mediaResourceInfo.get(i).getPid());
                                        DrmEventService.mDrmManagerClient.dpDRM(1);
                                    }
                                }
                            }
                        }
                    };
    private final SemMediaResourceHelper.ResourceInfoChangedListener
            mMediaResourceInfoChangedListener =
                    new SemMediaResourceHelper.ResourceInfoChangedListener() { // from class:
                        // com.android.server.DrmMediaResourceHelper.2
                        @Override // com.samsung.android.media.SemMediaResourceHelper.ResourceInfoChangedListener
                        public void onAdd(
                                ArrayList<SemMediaResourceHelper.MediaResourceInfo>
                                        mediaResourceInfo) {
                            if (mediaResourceInfo != null && mediaResourceInfo.size() > 0) {
                                Log.i(
                                        DrmMediaResourceHelper.this.TAG,
                                        "onAdd size = " + mediaResourceInfo.size());
                                for (int i = 0; i < mediaResourceInfo.size(); i++) {
                                    int pid = mediaResourceInfo.get(i).getPid();
                                    if (pid > 0
                                            && mediaResourceInfo.get(i).isSecured()
                                            && !mediaResourceInfo.get(i).isEncoder()
                                            && !DrmMediaResourceHelper.this.isResourcePresent(
                                                    pid)) {
                                        Log.i(
                                                DrmMediaResourceHelper.this.TAG,
                                                "onAdd secure = true, pid = " + pid);
                                        boolean ret =
                                                DrmEventService.mDrmManagerClient.toggleCPUBoost(
                                                        pid, true);
                                        DrmMediaResourceHelper.this.addPIDToBoostedList(pid);
                                        Log.i(
                                                DrmMediaResourceHelper.this.TAG,
                                                "onAdd ret of toggle CPU boost to ON =  " + ret);
                                    }
                                }
                            }
                        }

                        @Override // com.samsung.android.media.SemMediaResourceHelper.ResourceInfoChangedListener
                        public void onRemove(
                                ArrayList<SemMediaResourceHelper.MediaResourceInfo>
                                        mediaResourceInfo) {
                            if (mediaResourceInfo != null
                                    && mediaResourceInfo.size() == 0
                                    && DrmMediaResourceHelper.this.BoostedPIDs.size() > 0) {
                                Log.i(
                                        DrmMediaResourceHelper.this.TAG,
                                        "onRemove size = 0 (All resources removed)");
                                Iterator i = DrmMediaResourceHelper.this.BoostedPIDs.iterator();
                                while (i.hasNext()) {
                                    Integer in = (Integer) i.next();
                                    int pid = in.intValue();
                                    DrmEventService.mDrmManagerClient.toggleCPUBoost(pid, false);
                                    DrmMediaResourceHelper.this.removePID(pid);
                                    Log.i(
                                            DrmMediaResourceHelper.this.TAG,
                                            "onRemove toggle CPU boost to OFF for pid =  " + pid);
                                }
                                Log.e(
                                        DrmMediaResourceHelper.this.TAG,
                                        "onRemove making Dpdrm to 0  at first point ");
                                DrmEventService.mDrmManagerClient.dpDRM(0);
                                return;
                            }
                            if (mediaResourceInfo != null && mediaResourceInfo.size() > 0) {
                                Log.i(
                                        DrmMediaResourceHelper.this.TAG,
                                        "onRemove size = " + mediaResourceInfo.size());
                                ArrayList<Integer> temp = new ArrayList<>(5);
                                for (int i2 = 0;
                                        i2 < DrmMediaResourceHelper.this.BoostedPIDs.size();
                                        i2++) {
                                    Iterator it = mediaResourceInfo.iterator();
                                    int pid2 =
                                            ((Integer)
                                                            DrmMediaResourceHelper.this.BoostedPIDs
                                                                    .get(i2))
                                                    .intValue();
                                    boolean flag = false;
                                    while (true) {
                                        if (!it.hasNext()) {
                                            break;
                                        }
                                        SemMediaResourceHelper.MediaResourceInfo info = it.next();
                                        if (info.getPid() == pid2) {
                                            flag = true;
                                            break;
                                        }
                                    }
                                    if (!flag) {
                                        DrmEventService.mDrmManagerClient.toggleCPUBoost(
                                                pid2, false);
                                        Log.i(
                                                DrmMediaResourceHelper.this.TAG,
                                                "onRemove toggle CPU boost to OFF for pid for"
                                                    + " multiple objs =  "
                                                        + pid2);
                                        temp.add(Integer.valueOf(pid2));
                                    }
                                }
                                for (int i3 = 0; i3 < temp.size(); i3++) {
                                    DrmMediaResourceHelper.this.removePID(temp.get(i3).intValue());
                                }
                                temp.clear();
                                if (DrmMediaResourceHelper.this.BoostedPIDs.size() == 0) {
                                    Log.e(
                                            DrmMediaResourceHelper.this.TAG,
                                            "onRemove making Dpdrm to 0 second point ");
                                    DrmEventService.mDrmManagerClient.dpDRM(0);
                                }
                            }
                        }

                        @Override // com.samsung.android.media.SemMediaResourceHelper.ResourceInfoChangedListener
                        public void onError(SemMediaResourceHelper semMediaResourceHelper) {
                            Log.e(DrmMediaResourceHelper.this.TAG, "Onerror ");
                        }
                    };

    public DrmMediaResourceHelper() {
        createMediaResourceHelper();
    }

    private void createMediaResourceHelper() {
        Log.i(this.TAG, "createMediaResourceHelper");
        this.mSemMediaResourceHelper = SemMediaResourceHelper.createInstance(2, false);
        try {
            this.mSemMediaResourceHelper.setCodecStateChangedListener(
                    this.mMediaResourceCodecStateChangeListener);
            this.mSemMediaResourceHelper.setResourceInfoChangedListener(
                    this.mMediaResourceInfoChangedListener);
        } catch (IllegalStateException e) {
            Log.i(this.TAG, "IllegalStateException SemMediaResourceHelper");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean addPIDToBoostedList(int pid) {
        return this.BoostedPIDs.add(Integer.valueOf(pid));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isResourcePresent(int pid) {
        Iterator i = this.BoostedPIDs.iterator();
        while (i.hasNext()) {
            Integer in = i.next();
            if (in.intValue() == pid) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removePID(int pid) {
        Iterator i = this.BoostedPIDs.iterator();
        while (i.hasNext()) {
            Integer in = i.next();
            if (in.intValue() == pid) {
                i.remove();
                return;
            }
        }
    }
}
