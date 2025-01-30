package com.samsung.android.allshare;

import android.p009os.Bundle;
import com.samsung.android.allshare.media.SlideShowPlayer;
import com.sec.android.allshare.iface.CVMessage;
import com.sec.android.allshare.iface.IBundleHolder;
import com.sec.android.allshare.iface.message.AllShareAction;
import com.sec.android.allshare.iface.message.AllShareEvent;
import com.sec.android.allshare.iface.message.AllShareKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/* loaded from: classes5.dex */
final class SlideShowPlayerImpl extends SlideShowPlayer implements IBundleHolder {
    private static final String TAG = "SlideShowPlayerImpl";
    private IAllShareConnector mAllShareConnector;
    private DeviceImpl mDeviceImpl;
    private SlideShowPlayer.ISlideShowPlayerEventListener mEventListener = null;
    private SlideShowPlayer.ISlideShowPlayerResponseListener mResponseListener = null;
    private boolean mIsSubscribed = false;
    AllShareResponseHandler mResponseHandler = new AllShareResponseHandler(ServiceConnector.getMainLooper()) { // from class: com.samsung.android.allshare.SlideShowPlayerImpl.1
        @Override // com.samsung.android.allshare.AllShareResponseHandler
        public void handleResponseMessage(CVMessage cvm) {
            String actionID = cvm.getActionID();
            Bundle resBundle = cvm.getBundle();
            if (actionID == null || resBundle == null) {
                return;
            }
            ERROR error = ERROR.FAIL;
            String errorStr = resBundle.getString("BUNDLE_ENUM_ERROR");
            if (errorStr != null) {
                error = ERROR.stringToEnum(errorStr);
            }
            if (actionID.equals(AllShareAction.ACTION_SLIDESHOW_PLAYER_START)) {
                int interval = resBundle.getInt(AllShareKey.BUNDLE_INT_SLIDESHOW_BGM_VOLUME);
                if (SlideShowPlayerImpl.this.mResponseListener != null) {
                    SlideShowPlayerImpl.this.mResponseListener.onStartResponseReceived(interval, error);
                    return;
                }
                return;
            }
            if (actionID.equals(AllShareAction.ACTION_SLIDESHOW_PLAYER_STOP)) {
                if (SlideShowPlayerImpl.this.mResponseListener != null) {
                    SlideShowPlayerImpl.this.mResponseListener.onStopResponseReceived(error);
                    return;
                }
                return;
            }
            if (actionID.equals(AllShareAction.ACTION_SLIDESHOW_PLAYER_SETLIST)) {
                String albumTitle = resBundle.getString(AllShareKey.BUNDLE_STRING_SLIDESHOW_ALBUM_TITLE);
                ArrayList<Bundle> slideUriList = resBundle.getParcelableArrayList(AllShareKey.BUNDLE_PARCELABLE_ARRAYLIST_SLIDESHOW_IMAGE_CONTENT_URI);
                ArrayList<Item> itemList = new ArrayList<>();
                if (slideUriList != null) {
                    Iterator<Bundle> it = slideUriList.iterator();
                    while (it.hasNext()) {
                        Bundle itemBundle = it.next();
                        Item item = ItemCreator.fromBundle(itemBundle);
                        itemList.add(item);
                    }
                }
                if (SlideShowPlayerImpl.this.mResponseListener != null) {
                    SlideShowPlayerImpl.this.mResponseListener.onSetListResponseReceived(albumTitle, itemList, error);
                    return;
                }
                return;
            }
            if (actionID.equals(AllShareAction.ACTION_SLIDESHOW_PLAYER_SETBGMLIST)) {
                ArrayList<Bundle> bgmUriList = resBundle.getParcelableArrayList(AllShareKey.BUNDLE_PARCELABLE_ARRAYLIST_SLIDESHOW_AUDIO_CONTENT_URI);
                ArrayList<Item> itemList2 = new ArrayList<>();
                if (bgmUriList != null) {
                    Iterator<Bundle> it2 = bgmUriList.iterator();
                    while (it2.hasNext()) {
                        Bundle itemBundle2 = it2.next();
                        Item item2 = ItemCreator.fromBundle(itemBundle2);
                        itemList2.add(item2);
                    }
                }
                if (SlideShowPlayerImpl.this.mResponseListener != null) {
                    SlideShowPlayerImpl.this.mResponseListener.onSetBGMListResponseReceived(itemList2, error);
                    return;
                }
                return;
            }
            if (actionID.equals(AllShareAction.ACTION_SLIDESHOW_PLAYER_SETBGMVOLUME)) {
                int level = resBundle.getInt(AllShareKey.BUNDLE_INT_SLIDESHOW_BGM_VOLUME);
                if (SlideShowPlayerImpl.this.mResponseListener != null) {
                    SlideShowPlayerImpl.this.mResponseListener.onSetBGMVolumeResponseReceived(level, error);
                }
            }
        }
    };
    private AllShareEventHandler mAllShareEventHandler = new AllShareEventHandler(ServiceConnector.getMainLooper()) { // from class: com.samsung.android.allshare.SlideShowPlayerImpl.2
        private HashMap<String, SlideShowPlayer.SlideShowPlayerState> mStateMap;

        {
            HashMap<String, SlideShowPlayer.SlideShowPlayerState> hashMap = new HashMap<>();
            this.mStateMap = hashMap;
            hashMap.put(AllShareEvent.EVENT_RENDERER_STATE_BUFFERING, SlideShowPlayer.SlideShowPlayerState.BUFFERING);
            this.mStateMap.put(AllShareEvent.EVENT_RENDERER_STATE_NOMEDIA, SlideShowPlayer.SlideShowPlayerState.STOPPED);
            this.mStateMap.put(AllShareEvent.EVENT_RENDERER_STATE_PAUSED, SlideShowPlayer.SlideShowPlayerState.PLAYING);
            this.mStateMap.put(AllShareEvent.EVENT_RENDERER_STATE_STOPPED, SlideShowPlayer.SlideShowPlayerState.STOPPED);
            this.mStateMap.put(AllShareEvent.EVENT_RENDERER_STATE_PLAYING, SlideShowPlayer.SlideShowPlayerState.PLAYING);
        }

        @Override // com.samsung.android.allshare.AllShareEventHandler
        public void handleEventMessage(CVMessage cvm) {
            Bundle resBundle = cvm.getBundle();
            if (resBundle == null) {
                return;
            }
            ERROR error = ERROR.FAIL;
            SlideShowPlayer.SlideShowPlayerState state = this.mStateMap.get(cvm.getActionID());
            String errorStr = resBundle.getString("BUNDLE_ENUM_ERROR");
            ERROR error2 = ERROR.stringToEnum(errorStr);
            if (state == null) {
                state = SlideShowPlayer.SlideShowPlayerState.UNKNOWN;
            }
            notifyEvent(state, 0, error2);
        }

        private void notifyEvent(SlideShowPlayer.SlideShowPlayerState state, int trackNumber, ERROR error) {
            if (SlideShowPlayerImpl.this.mEventListener != null) {
                try {
                    SlideShowPlayerImpl.this.mEventListener.onSlideShowPlayerStateChanged(state, trackNumber, error);
                } catch (Error err) {
                    DLog.w_api(SlideShowPlayerImpl.TAG, "mEventHandler.notifyEvent Error", err);
                } catch (Exception e) {
                    DLog.w_api(SlideShowPlayerImpl.TAG, "mEventHandler.notifyEvent Exception", e);
                }
            }
        }
    };

    SlideShowPlayerImpl(IAllShareConnector connector, DeviceImpl deviceImpl) {
        this.mAllShareConnector = null;
        this.mDeviceImpl = null;
        if (connector == null) {
            DLog.w_api(TAG, "Connection FAIL: AllShare Service Connector does not exist");
        } else {
            this.mAllShareConnector = connector;
            this.mDeviceImpl = deviceImpl;
        }
    }

    public String getID() {
        DeviceImpl deviceImpl = this.mDeviceImpl;
        if (deviceImpl == null) {
            return "";
        }
        return deviceImpl.getID();
    }

    @Override // com.sec.android.allshare.iface.IBundleHolder
    public Bundle getBundle() {
        DeviceImpl deviceImpl = this.mDeviceImpl;
        if (deviceImpl == null) {
            return null;
        }
        return deviceImpl.getBundle();
    }

    @Override // com.samsung.android.allshare.media.SlideShowPlayer
    public void start(int interval) {
        IAllShareConnector iAllShareConnector = this.mAllShareConnector;
        if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
            SlideShowPlayer.ISlideShowPlayerResponseListener iSlideShowPlayerResponseListener = this.mResponseListener;
            if (iSlideShowPlayerResponseListener != null) {
                iSlideShowPlayerResponseListener.onStartResponseReceived(interval, ERROR.SERVICE_NOT_CONNECTED);
                return;
            }
            return;
        }
        CVMessage req_msg = new CVMessage();
        req_msg.setActionID(AllShareAction.ACTION_SLIDESHOW_PLAYER_START);
        Bundle req_bundle = new Bundle();
        req_bundle.putString("BUNDLE_STRING_ID", getID());
        req_bundle.putInt(AllShareKey.BUNDLE_INT_SLIDESHOW_INTERVAL, interval);
        req_msg.setBundle(req_bundle);
        this.mAllShareConnector.requestCVMAsync(req_msg, this.mResponseHandler);
    }

    @Override // com.samsung.android.allshare.media.SlideShowPlayer
    public void stop() {
        IAllShareConnector iAllShareConnector = this.mAllShareConnector;
        if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
            SlideShowPlayer.ISlideShowPlayerResponseListener iSlideShowPlayerResponseListener = this.mResponseListener;
            if (iSlideShowPlayerResponseListener != null) {
                iSlideShowPlayerResponseListener.onStopResponseReceived(ERROR.SERVICE_NOT_CONNECTED);
                return;
            }
            return;
        }
        CVMessage req_msg = new CVMessage();
        req_msg.setActionID(AllShareAction.ACTION_SLIDESHOW_PLAYER_STOP);
        Bundle req_bundle = new Bundle();
        req_bundle.putString("BUNDLE_STRING_ID", getID());
        req_msg.setBundle(req_bundle);
        this.mAllShareConnector.requestCVMAsync(req_msg, this.mResponseHandler);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.samsung.android.allshare.media.SlideShowPlayer
    public void setList(String albumTitle, ArrayList<Item> imageItems) {
        IAllShareConnector iAllShareConnector = this.mAllShareConnector;
        if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
            SlideShowPlayer.ISlideShowPlayerResponseListener iSlideShowPlayerResponseListener = this.mResponseListener;
            if (iSlideShowPlayerResponseListener != null) {
                iSlideShowPlayerResponseListener.onSetListResponseReceived(albumTitle, imageItems, ERROR.SERVICE_NOT_CONNECTED);
                return;
            }
            return;
        }
        ArrayList<Bundle> bundleList = new ArrayList<>();
        Iterator<Item> it = imageItems.iterator();
        while (it.hasNext()) {
            Item next = it.next();
            if (next != 0) {
                String mimeType = next.getMimetype();
                if (!mimeType.contains("image")) {
                    SlideShowPlayer.ISlideShowPlayerResponseListener iSlideShowPlayerResponseListener2 = this.mResponseListener;
                    if (iSlideShowPlayerResponseListener2 != null) {
                        iSlideShowPlayerResponseListener2.onSetListResponseReceived(albumTitle, imageItems, ERROR.INVALID_OBJECT);
                        return;
                    }
                    return;
                }
                if (next instanceof IBundleHolder) {
                    bundleList.add(((IBundleHolder) next).getBundle());
                }
            }
        }
        CVMessage req_msg = new CVMessage();
        req_msg.setActionID(AllShareAction.ACTION_SLIDESHOW_PLAYER_SETLIST);
        Bundle req_bundle = new Bundle();
        req_bundle.putString("BUNDLE_STRING_ID", getID());
        req_bundle.putString(AllShareKey.BUNDLE_STRING_SLIDESHOW_ALBUM_TITLE, albumTitle);
        req_bundle.putParcelableArrayList(AllShareKey.BUNDLE_PARCELABLE_ARRAYLIST_SLIDESHOW_IMAGE_CONTENT_URI, bundleList);
        req_msg.setBundle(req_bundle);
        this.mAllShareConnector.requestCVMAsync(req_msg, this.mResponseHandler);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.samsung.android.allshare.media.SlideShowPlayer
    public void setBGMList(ArrayList<Item> items) {
        IAllShareConnector iAllShareConnector = this.mAllShareConnector;
        if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
            SlideShowPlayer.ISlideShowPlayerResponseListener iSlideShowPlayerResponseListener = this.mResponseListener;
            if (iSlideShowPlayerResponseListener != null) {
                iSlideShowPlayerResponseListener.onSetBGMListResponseReceived(items, ERROR.SERVICE_NOT_CONNECTED);
                return;
            }
            return;
        }
        ArrayList<Bundle> bundleList = new ArrayList<>();
        Iterator<Item> it = items.iterator();
        while (it.hasNext()) {
            Item next = it.next();
            if (next != 0) {
                String mimeType = next.getMimetype();
                if (!mimeType.contains("audio")) {
                    SlideShowPlayer.ISlideShowPlayerResponseListener iSlideShowPlayerResponseListener2 = this.mResponseListener;
                    if (iSlideShowPlayerResponseListener2 != null) {
                        iSlideShowPlayerResponseListener2.onSetBGMListResponseReceived(items, ERROR.INVALID_OBJECT);
                        return;
                    }
                    return;
                }
                if (next instanceof IBundleHolder) {
                    bundleList.add(((IBundleHolder) next).getBundle());
                }
            }
        }
        CVMessage req_msg = new CVMessage();
        req_msg.setActionID(AllShareAction.ACTION_SLIDESHOW_PLAYER_SETBGMLIST);
        Bundle req_bundle = new Bundle();
        req_bundle.putString("BUNDLE_STRING_ID", getID());
        req_bundle.putParcelableArrayList(AllShareKey.BUNDLE_PARCELABLE_ARRAYLIST_SLIDESHOW_AUDIO_CONTENT_URI, bundleList);
        req_msg.setBundle(req_bundle);
        this.mAllShareConnector.requestCVMAsync(req_msg, this.mResponseHandler);
    }

    @Override // com.samsung.android.allshare.media.SlideShowPlayer
    public void setBGMVolume(int volume) {
        IAllShareConnector iAllShareConnector = this.mAllShareConnector;
        if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
            SlideShowPlayer.ISlideShowPlayerResponseListener iSlideShowPlayerResponseListener = this.mResponseListener;
            if (iSlideShowPlayerResponseListener != null) {
                iSlideShowPlayerResponseListener.onSetBGMVolumeResponseReceived(volume, ERROR.SERVICE_NOT_CONNECTED);
                return;
            }
            return;
        }
        CVMessage req_msg = new CVMessage();
        req_msg.setActionID(AllShareAction.ACTION_SLIDESHOW_PLAYER_SETBGMVOLUME);
        Bundle req_bundle = new Bundle();
        req_bundle.putString("BUNDLE_STRING_ID", getID());
        req_bundle.putInt(AllShareKey.BUNDLE_INT_SLIDESHOW_BGM_VOLUME, volume);
        req_msg.setBundle(req_bundle);
        this.mAllShareConnector.requestCVMAsync(req_msg, this.mResponseHandler);
    }

    @Override // com.samsung.android.allshare.media.SlideShowPlayer
    public void setEventListener(SlideShowPlayer.ISlideShowPlayerEventListener listener) {
        IAllShareConnector iAllShareConnector = this.mAllShareConnector;
        if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
            DLog.w_api(TAG, "setEventListener error! AllShareService is not connected");
            return;
        }
        this.mEventListener = listener;
        boolean z = this.mIsSubscribed;
        if (!z && listener != null) {
            this.mAllShareConnector.subscribeAllShareEvent(AllShareEvent.EVENT_DEVICE_SUBSCRIBE, this.mDeviceImpl.getBundle(), this.mAllShareEventHandler);
            this.mIsSubscribed = true;
        } else if (z && listener == null) {
            this.mAllShareConnector.unsubscribeAllShareEvent(AllShareEvent.EVENT_DEVICE_SUBSCRIBE, this.mDeviceImpl.getBundle(), this.mAllShareEventHandler);
            this.mIsSubscribed = false;
        }
    }

    public void removeEventHandler() {
        this.mAllShareConnector.unsubscribeAllShareEvent(AllShareEvent.EVENT_DEVICE_SUBSCRIBE, this.mDeviceImpl.getBundle(), this.mAllShareEventHandler);
        this.mIsSubscribed = false;
    }

    @Override // com.samsung.android.allshare.media.SlideShowPlayer
    public void setResponseListener(SlideShowPlayer.ISlideShowPlayerResponseListener listener) {
        this.mResponseListener = listener;
    }
}
