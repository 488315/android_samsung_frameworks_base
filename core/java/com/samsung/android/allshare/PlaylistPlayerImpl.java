package com.samsung.android.allshare;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.p009os.Bundle;
import com.samsung.android.allshare.media.Playlist;
import com.samsung.android.allshare.media.PlaylistPlayer;
import com.sec.android.allshare.iface.CVMessage;
import com.sec.android.allshare.iface.IBundleHolder;
import com.sec.android.allshare.iface.message.AllShareAction;
import com.sec.android.allshare.iface.message.AllShareEvent;
import com.sec.android.allshare.iface.message.AllShareKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/* loaded from: classes5.dex */
final class PlaylistPlayerImpl extends PlaylistPlayer {
    private static final int MIN_TRACK_NUMBER = 1;
    private static final String TAG_CLASS = "PlaylistPlayerImpl";
    private IAllShareConnector mAllShareConnector;
    private DeviceImpl mDeviceImpl;
    private boolean mIsAutoSlideShowMode;
    private boolean mIsNavigateInPause;
    private boolean mIsSeekable;
    private PlaylistPlayer.IPlaylistPlayerPlaybackResponseListener mPlaylistPlayerResponseListener = null;
    private PlaylistPlayer.IPlaylistPlayerEventListener mPlaylistPlayerEventListener = null;
    private String mFilePath = "";
    private Playlist mPlaylist = null;
    private int mTrackNumber = 0;
    private boolean mIsSubscribed = false;
    ArrayList<String> mCurrentPlayingContentUriStrList = null;
    AllShareEventHandler mEventHandler = new AllShareEventHandler(ServiceConnector.getMainLooper()) { // from class: com.samsung.android.allshare.PlaylistPlayerImpl.1
        private HashMap<String, PlaylistPlayer.PlayerState> mPlaylistStateMap;

        {
            HashMap<String, PlaylistPlayer.PlayerState> hashMap = new HashMap<>();
            this.mPlaylistStateMap = hashMap;
            hashMap.put(AllShareEvent.EVENT_RENDERER_STATE_BUFFERING, PlaylistPlayer.PlayerState.BUFFERING);
            this.mPlaylistStateMap.put(AllShareEvent.EVENT_RENDERER_STATE_PAUSED, PlaylistPlayer.PlayerState.PAUSED);
            this.mPlaylistStateMap.put(AllShareEvent.EVENT_RENDERER_STATE_STOPPED, PlaylistPlayer.PlayerState.STOPPED);
            this.mPlaylistStateMap.put(AllShareEvent.EVENT_RENDERER_STATE_PLAYING, PlaylistPlayer.PlayerState.PLAYING);
            this.mPlaylistStateMap.put(AllShareEvent.EVENT_RENDERER_STATE_CONTENT_CHANGED, PlaylistPlayer.PlayerState.CONTENT_CHANGED);
        }

        @Override // com.samsung.android.allshare.AllShareEventHandler
        public void handleEventMessage(CVMessage cvm) {
            Bundle resBundle;
            ERROR error;
            PlaylistPlayer.PlayerState state = null;
            try {
                state = this.mPlaylistStateMap.get(cvm.getActionID());
            } catch (Exception e) {
                DLog.w_api(PlaylistPlayerImpl.TAG_CLASS, "mEventHandler.handleEventMessage Exception ", e);
            }
            if (state == null || (resBundle = cvm.getBundle()) == null) {
                return;
            }
            String errStr = resBundle.getString("BUNDLE_ENUM_ERROR");
            if (errStr == null) {
                error = ERROR.FAIL;
            } else {
                error = ERROR.stringToEnum(errStr);
            }
            if (state.equals(PlaylistPlayer.PlayerState.CONTENT_CHANGED)) {
                if (PlaylistPlayerImpl.this.mCurrentPlayingContentUriStrList != null && !isContains(resBundle.getString(AllShareKey.BUNDLE_STRING_APP_ITEM_ID))) {
                    PlaylistPlayerImpl.this.mCurrentPlayingContentUriStrList = null;
                    notifyEvent(state, error);
                    return;
                }
                return;
            }
            notifyEvent(state, error);
        }

        private void notifyEvent(PlaylistPlayer.PlayerState state, ERROR error) {
            if (PlaylistPlayerImpl.this.mPlaylistPlayerEventListener != null) {
                try {
                    PlaylistPlayerImpl.this.mPlaylistPlayerEventListener.onPlaylistPlayerStateChanged(state, error);
                } catch (Exception e) {
                    DLog.w_api(PlaylistPlayerImpl.TAG_CLASS, "mEventHandler.notifyEvent Exception", e);
                }
            }
        }

        private boolean isContains(String fullUri) {
            if (fullUri == null) {
                return false;
            }
            Iterator<String> it = PlaylistPlayerImpl.this.mCurrentPlayingContentUriStrList.iterator();
            while (it.hasNext()) {
                String uri = it.next();
                if (fullUri.endsWith(uri)) {
                    return true;
                }
            }
            return false;
        }
    };
    AllShareResponseHandler mAllShareRespHandler = new AllShareResponseHandler(ServiceConnector.getMainLooper()) { // from class: com.samsung.android.allshare.PlaylistPlayerImpl.2
        @Override // com.samsung.android.allshare.AllShareResponseHandler
        public void handleResponseMessage(CVMessage cvm) {
            Item item;
            Item item2;
            Item item3;
            String actionID = cvm.getActionID();
            Bundle resBundle = cvm.getBundle();
            if (actionID == null || resBundle == null) {
                return;
            }
            if (actionID.equals(AllShareAction.ACTION_PLAYLIST_PLAYER_PLAY)) {
                PlaylistPlayerImpl.this.mCurrentPlayingContentUriStrList = resBundle.getStringArrayList(AllShareKey.BUNDLE_STRING_SERVER_URI_LIST);
            }
            if (PlaylistPlayerImpl.this.mPlaylistPlayerResponseListener == null) {
                return;
            }
            ERROR error = ERROR.stringToEnum(resBundle.getString("BUNDLE_ENUM_ERROR"));
            if (!actionID.equals(AllShareAction.ACTION_PLAYLIST_PLAYER_PLAY)) {
                if (actionID.equals(AllShareAction.ACTION_PLAYLIST_PLAYER_SEEK)) {
                    int trackNum = resBundle.getInt(AllShareKey.BUNDLE_INT_TRACKNUM);
                    PlaylistPlayerImpl.this.mPlaylistPlayerResponseListener.onSeekResponseReceived(trackNum, error);
                    return;
                }
                if (actionID.equals(AllShareAction.ACTION_PLAYLIST_PLAYER_NEXT)) {
                    PlaylistPlayerImpl.this.mPlaylistPlayerResponseListener.onNextResponseReceived(error);
                    return;
                }
                if (actionID.equals(AllShareAction.ACTION_PLAYLIST_PLAYER_PERVIOUS)) {
                    PlaylistPlayerImpl.this.mPlaylistPlayerResponseListener.onPreviousResponseReceived(error);
                    return;
                }
                if (actionID.equals(AllShareAction.ACTION_PLAYLIST_PLAYER_STOP)) {
                    PlaylistPlayerImpl.this.mPlaylistPlayerResponseListener.onStopResponseReceived(error);
                    return;
                }
                if (actionID.equals(AllShareAction.ACTION_PLAYLIST_PLAYER_PAUSE)) {
                    PlaylistPlayerImpl.this.mPlaylistPlayerResponseListener.onPauseResponseReceived(error);
                    return;
                }
                if (actionID.equals(AllShareAction.ACTION_PLAYLIST_PLAYER_RESUME)) {
                    PlaylistPlayerImpl.this.mPlaylistPlayerResponseListener.onResumeResponseReceived(error);
                    return;
                } else {
                    if (actionID.equals(AllShareAction.ACTION_PLAYLIST_PLAYER_REQUEST_PLAY_POSITION)) {
                        int trackNum2 = resBundle.getInt(AllShareKey.BUNDLE_INT_TRACKNUM);
                        PlaylistPlayerImpl.this.mPlaylistPlayerResponseListener.onGetPlayPositionResponseReceived(trackNum2, error);
                        return;
                    }
                    return;
                }
            }
            ArrayList<Bundle> playlist = resBundle.getParcelableArrayList(AllShareKey.BUNDLE_PARCELABLE_ARRAYLIST_CONTENT_URI);
            String mimeType = resBundle.getString(AllShareKey.BUNDLE_STRING_MIME_TYPE);
            if (mimeType != null) {
                if (mimeType.contains("audio")) {
                    Playlist.AudioListBuilder playlistBuilder = new Playlist.AudioListBuilder();
                    Iterator<Bundle> it = playlist.iterator();
                    while (it.hasNext()) {
                        Bundle itemBundle = it.next();
                        if (itemBundle != null && (item3 = ItemCreator.fromBundle(itemBundle)) != null) {
                            playlistBuilder.addItem(item3);
                        }
                    }
                    int trackNum3 = resBundle.getInt(AllShareKey.BUNDLE_INT_PLAYLIST_TRACKNUMBER, 0);
                    PlaylistPlayerImpl.this.mPlaylistPlayerResponseListener.onPlayResponseReceived(playlistBuilder.build(), trackNum3, error);
                    return;
                }
                if (mimeType.contains("video")) {
                    Playlist.VideoListBuilder playlistBuilder2 = new Playlist.VideoListBuilder();
                    Iterator<Bundle> it2 = playlist.iterator();
                    while (it2.hasNext()) {
                        Bundle itemBundle2 = it2.next();
                        if (itemBundle2 != null && (item2 = ItemCreator.fromBundle(itemBundle2)) != null) {
                            playlistBuilder2.addItem(item2);
                        }
                    }
                    int trackNum4 = resBundle.getInt(AllShareKey.BUNDLE_INT_PLAYLIST_TRACKNUMBER, 0);
                    PlaylistPlayerImpl.this.mPlaylistPlayerResponseListener.onPlayResponseReceived(playlistBuilder2.build(), trackNum4, error);
                    return;
                }
                if (mimeType.contains("image")) {
                    Playlist.ImageListBuilder playlistBuilder3 = new Playlist.ImageListBuilder();
                    Iterator<Bundle> it3 = playlist.iterator();
                    while (it3.hasNext()) {
                        Bundle itemBundle3 = it3.next();
                        if (itemBundle3 != null && (item = ItemCreator.fromBundle(itemBundle3)) != null) {
                            playlistBuilder3.addItem(item);
                        }
                    }
                    int trackNum5 = resBundle.getInt(AllShareKey.BUNDLE_INT_PLAYLIST_TRACKNUMBER, 0);
                    PlaylistPlayerImpl.this.mPlaylistPlayerResponseListener.onPlayResponseReceived(playlistBuilder3.build(), trackNum5, error);
                }
            }
        }
    };

    PlaylistPlayerImpl(IAllShareConnector connector, DeviceImpl deviceImpl) {
        this.mAllShareConnector = null;
        this.mDeviceImpl = null;
        this.mIsSeekable = false;
        this.mIsNavigateInPause = false;
        this.mIsAutoSlideShowMode = false;
        if (connector == null) {
            DLog.w_api(TAG_CLASS, "Connection FAIL: AllShare Service Connector does not exist");
            return;
        }
        this.mDeviceImpl = deviceImpl;
        this.mAllShareConnector = connector;
        Bundle bundle = deviceImpl.getBundle();
        if (bundle == null) {
            DLog.w_api(TAG_CLASS, "Cannot get bundle from deviceImpl");
            return;
        }
        this.mIsSeekable = bundle.getBoolean(AllShareKey.BUNDLE_BOOLEAN_SEEKABLE);
        this.mIsNavigateInPause = bundle.getBoolean(AllShareKey.BUNDLE_BOOLEAN_NAVIGATE_IN_PAUSE);
        this.mIsAutoSlideShowMode = bundle.getBoolean(AllShareKey.BUNDLE_BOOLEAN_AUTO_SLIDE_SHOW);
    }

    String getCurrentFilePath() {
        return this.mFilePath;
    }

    void setCurrentFilePath(String strFilePath) {
        this.mFilePath = strFilePath;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.samsung.android.allshare.media.PlaylistPlayer
    public void play(Playlist playlist, int trackNumber) {
        IAllShareConnector iAllShareConnector = this.mAllShareConnector;
        if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
            this.mPlaylistPlayerResponseListener.onPlayResponseReceived(playlist, trackNumber, ERROR.SERVICE_NOT_CONNECTED);
            return;
        }
        if (playlist == null) {
            this.mPlaylistPlayerResponseListener.onPlayResponseReceived(playlist, trackNumber, ERROR.INVALID_ARGUMENT);
            this.mFilePath = null;
            return;
        }
        if (trackNumber < 1) {
            this.mPlaylistPlayerResponseListener.onPlayResponseReceived(playlist, trackNumber, ERROR.INVALID_ARGUMENT);
            this.mFilePath = null;
            return;
        }
        ArrayList<Item> itemList = playlist.getPlaylist();
        if (itemList == null || itemList.isEmpty()) {
            this.mPlaylistPlayerResponseListener.onPlayResponseReceived(playlist, trackNumber, ERROR.INVALID_ARGUMENT);
            this.mFilePath = null;
            return;
        }
        this.mCurrentPlayingContentUriStrList = null;
        ArrayList<Bundle> bundleList = new ArrayList<>();
        String mimeType = "";
        Iterator<Item> it = itemList.iterator();
        while (it.hasNext()) {
            Item next = it.next();
            if (next != 0) {
                mimeType = next.getMimetype();
                if (next instanceof IBundleHolder) {
                    bundleList.add(((IBundleHolder) next).getBundle());
                }
            }
        }
        CVMessage req_msg = new CVMessage();
        req_msg.setActionID(AllShareAction.ACTION_PLAYLIST_PLAYER_PLAY);
        Bundle req_bundle = new Bundle();
        req_bundle.putParcelableArrayList(AllShareKey.BUNDLE_PARCELABLE_ARRAYLIST_CONTENT_URI, bundleList);
        req_bundle.putString("BUNDLE_STRING_ID", this.mDeviceImpl.getID());
        req_bundle.putString(AllShareKey.BUNDLE_STRING_MIME_TYPE, mimeType);
        req_bundle.putInt(AllShareKey.BUNDLE_INT_PLAYLIST_TRACKNUMBER, trackNumber);
        req_msg.setBundle(req_bundle);
        this.mAllShareConnector.requestCVMAsync(req_msg, this.mAllShareRespHandler);
        registerFilePath(playlist, itemList, trackNumber);
    }

    private void registerFilePath(Playlist playlist, ArrayList<Item> itemList, int trackNumber) {
        this.mPlaylist = playlist;
        this.mTrackNumber = 0;
        Cursor cur = getCursorFromSelectedItem(itemList, 0);
        if (cur == null) {
            this.mFilePath = null;
            return;
        }
        cur.moveToNext();
        int idx = cur.getColumnIndex("_data");
        this.mFilePath = cur.getString(idx);
        cur.close();
        DLog.w_api(TAG_CLASS, "Play:" + this.mFilePath + " " + trackNumber);
    }

    @Override // com.samsung.android.allshare.media.PlaylistPlayer
    public void seek(int trackNumber) {
        IAllShareConnector iAllShareConnector = this.mAllShareConnector;
        if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
            return;
        }
        if (trackNumber < 1) {
            this.mPlaylistPlayerResponseListener.onSeekResponseReceived(trackNumber, ERROR.INVALID_ARGUMENT);
            return;
        }
        CVMessage mMessage = new CVMessage();
        mMessage.setActionID(AllShareAction.ACTION_PLAYLIST_PLAYER_SEEK);
        Bundle mBundle = new Bundle();
        mBundle.putString("BUNDLE_STRING_ID", this.mDeviceImpl.getID());
        mBundle.putInt(AllShareKey.BUNDLE_INT_PLAYLIST_TRACKNUMBER, trackNumber);
        mMessage.setBundle(mBundle);
        this.mAllShareConnector.requestCVMAsync(mMessage, this.mAllShareRespHandler);
        createFilePathForZoom(trackNumber - 1);
    }

    private void createFilePathForZoom(int trackNumber) {
        Playlist playlist = this.mPlaylist;
        if (playlist == null || trackNumber < 0) {
            return;
        }
        ArrayList<Item> itemList = playlist.getPlaylist();
        Cursor cur = getCursorFromSelectedItem(itemList, trackNumber);
        if (cur == null) {
            return;
        }
        cur.moveToNext();
        int idx = cur.getColumnIndex("_data");
        this.mFilePath = cur.getString(idx);
        cur.close();
        this.mTrackNumber = trackNumber;
    }

    private Cursor getCursorFromSelectedItem(ArrayList<Item> itemList, int trackNumber) {
        IAllShareConnector iAllShareConnector = this.mAllShareConnector;
        if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
            return null;
        }
        int itemIndex = trackNumber - 1;
        if (itemIndex < 0 || itemIndex >= itemList.size()) {
            this.mFilePath = null;
            return null;
        }
        Item item = itemList.get(itemIndex);
        if (item == null) {
            this.mFilePath = null;
            return null;
        }
        Uri uri = item.getURI();
        if (uri == null) {
            this.mFilePath = null;
            return null;
        }
        ContentResolver resolver = this.mAllShareConnector.getContentResolver();
        if (resolver == null) {
            this.mFilePath = null;
            return null;
        }
        Cursor cur = resolver.query(uri, null, null, null, null);
        return cur;
    }

    @Override // com.samsung.android.allshare.media.PlaylistPlayer
    public void previous() {
        IAllShareConnector iAllShareConnector = this.mAllShareConnector;
        if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
            this.mPlaylistPlayerResponseListener.onPreviousResponseReceived(ERROR.SERVICE_NOT_CONNECTED);
            return;
        }
        CVMessage req_msg = new CVMessage();
        req_msg.setActionID(AllShareAction.ACTION_PLAYLIST_PLAYER_PERVIOUS);
        Bundle req_bundle = new Bundle();
        req_bundle.putString("BUNDLE_STRING_ID", this.mDeviceImpl.getID());
        req_msg.setBundle(req_bundle);
        this.mAllShareConnector.requestCVMAsync(req_msg, this.mAllShareRespHandler);
        if (this.mPlaylist != null) {
            int i = this.mTrackNumber - 1;
            this.mTrackNumber = i;
            if (i < 0) {
                i = 0;
            }
            this.mTrackNumber = i;
            createFilePathForZoom(i);
        }
    }

    @Override // com.samsung.android.allshare.media.PlaylistPlayer
    public void next() {
        IAllShareConnector iAllShareConnector = this.mAllShareConnector;
        if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
            this.mPlaylistPlayerResponseListener.onNextResponseReceived(ERROR.SERVICE_NOT_CONNECTED);
            return;
        }
        CVMessage req_msg = new CVMessage();
        req_msg.setActionID(AllShareAction.ACTION_PLAYLIST_PLAYER_NEXT);
        Bundle req_bundle = new Bundle();
        req_bundle.putString("BUNDLE_STRING_ID", this.mDeviceImpl.getID());
        req_msg.setBundle(req_bundle);
        this.mAllShareConnector.requestCVMAsync(req_msg, this.mAllShareRespHandler);
        Playlist playlist = this.mPlaylist;
        if (playlist != null) {
            int i = this.mTrackNumber + 1;
            this.mTrackNumber = i;
            int size = i >= playlist.getPlaylist().size() ? this.mPlaylist.getPlaylist().size() - 1 : this.mTrackNumber;
            this.mTrackNumber = size;
            createFilePathForZoom(size);
        }
    }

    @Override // com.samsung.android.allshare.media.PlaylistPlayer
    public void stop() {
        IAllShareConnector iAllShareConnector = this.mAllShareConnector;
        if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
            this.mPlaylistPlayerResponseListener.onStopResponseReceived(ERROR.SERVICE_NOT_CONNECTED);
            return;
        }
        CVMessage req_msg = new CVMessage();
        req_msg.setActionID(AllShareAction.ACTION_PLAYLIST_PLAYER_STOP);
        Bundle req_bundle = new Bundle();
        req_bundle.putString("BUNDLE_STRING_ID", this.mDeviceImpl.getID());
        req_msg.setBundle(req_bundle);
        this.mAllShareConnector.requestCVMAsync(req_msg, this.mAllShareRespHandler);
    }

    @Override // com.samsung.android.allshare.media.PlaylistPlayer
    public void pause() {
        IAllShareConnector iAllShareConnector = this.mAllShareConnector;
        if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
            this.mPlaylistPlayerResponseListener.onPauseResponseReceived(ERROR.SERVICE_NOT_CONNECTED);
            return;
        }
        CVMessage req_msg = new CVMessage();
        req_msg.setActionID(AllShareAction.ACTION_PLAYLIST_PLAYER_PAUSE);
        Bundle req_bundle = new Bundle();
        req_bundle.putString("BUNDLE_STRING_ID", this.mDeviceImpl.getID());
        req_msg.setBundle(req_bundle);
        this.mAllShareConnector.requestCVMAsync(req_msg, this.mAllShareRespHandler);
    }

    @Override // com.samsung.android.allshare.media.PlaylistPlayer
    public void resume() {
        IAllShareConnector iAllShareConnector = this.mAllShareConnector;
        if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
            this.mPlaylistPlayerResponseListener.onResumeResponseReceived(ERROR.SERVICE_NOT_CONNECTED);
            return;
        }
        CVMessage req_msg = new CVMessage();
        req_msg.setActionID(AllShareAction.ACTION_PLAYLIST_PLAYER_RESUME);
        Bundle req_bundle = new Bundle();
        req_bundle.putString("BUNDLE_STRING_ID", this.mDeviceImpl.getID());
        req_msg.setBundle(req_bundle);
        this.mAllShareConnector.requestCVMAsync(req_msg, this.mAllShareRespHandler);
    }

    @Override // com.samsung.android.allshare.media.PlaylistPlayer
    public void getPlayPosition() {
        IAllShareConnector iAllShareConnector = this.mAllShareConnector;
        if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
            this.mPlaylistPlayerResponseListener.onGetPlayPositionResponseReceived(-1, ERROR.SERVICE_NOT_CONNECTED);
            return;
        }
        CVMessage req_msg = new CVMessage();
        req_msg.setActionID(AllShareAction.ACTION_PLAYLIST_PLAYER_REQUEST_PLAY_POSITION);
        Bundle req_bundle = new Bundle();
        req_bundle.putString("BUNDLE_STRING_ID", this.mDeviceImpl.getID());
        req_msg.setBundle(req_bundle);
        this.mAllShareConnector.requestCVMAsync(req_msg, this.mAllShareRespHandler);
    }

    @Override // com.samsung.android.allshare.media.PlaylistPlayer
    public void setPlaybackResponseListener(PlaylistPlayer.IPlaylistPlayerPlaybackResponseListener l) {
        this.mPlaylistPlayerResponseListener = l;
    }

    @Override // com.samsung.android.allshare.media.PlaylistPlayer
    public void setPlaylistPlayerEventListener(PlaylistPlayer.IPlaylistPlayerEventListener l) {
        IAllShareConnector iAllShareConnector = this.mAllShareConnector;
        if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
            DLog.w_api(TAG_CLASS, "setEventListener error! AllShareService is not connected");
            return;
        }
        this.mPlaylistPlayerEventListener = l;
        boolean z = this.mIsSubscribed;
        if (!z && l != null) {
            this.mAllShareConnector.subscribeAllShareEvent(AllShareEvent.EVENT_DEVICE_SUBSCRIBE, this.mDeviceImpl.getBundle(), this.mEventHandler);
            this.mIsSubscribed = true;
        } else if (z && l == null) {
            this.mAllShareConnector.unsubscribeAllShareEvent(AllShareEvent.EVENT_DEVICE_SUBSCRIBE, this.mDeviceImpl.getBundle(), this.mEventHandler);
            this.mIsSubscribed = false;
        }
    }

    @Override // com.samsung.android.allshare.media.PlaylistPlayer
    public boolean isSeekable() {
        return this.mIsSeekable;
    }

    @Override // com.samsung.android.allshare.media.PlaylistPlayer
    public void setAutoFlipMode(boolean onoff) {
        IAllShareConnector iAllShareConnector = this.mAllShareConnector;
        if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
            return;
        }
        CVMessage req_msg = new CVMessage();
        req_msg.setActionID(AllShareAction.ACTION_PLAYLIST_PLAYER_SET_AUTO_FLIP_MODE);
        Bundle req_bundle = new Bundle();
        req_bundle.putString("BUNDLE_STRING_ID", this.mDeviceImpl.getID());
        req_bundle.putBoolean(AllShareKey.BUNDLE_BOOLEAN_AUTO_SLIDE_SHOW, onoff);
        req_msg.setBundle(req_bundle);
        this.mAllShareConnector.requestCVMAsync(req_msg, this.mAllShareRespHandler);
    }

    @Override // com.samsung.android.allshare.media.PlaylistPlayer
    public void setQuickNavigate(boolean onoff) {
        IAllShareConnector iAllShareConnector = this.mAllShareConnector;
        if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
            return;
        }
        CVMessage req_msg = new CVMessage();
        req_msg.setActionID(AllShareAction.ACTION_PLAYLIST_PLAYER_SET_QUICK_NAVIGATE);
        Bundle req_bundle = new Bundle();
        req_bundle.putString("BUNDLE_STRING_ID", this.mDeviceImpl.getID());
        req_bundle.putBoolean(AllShareKey.BUNDLE_BOOLEAN_NAVIGATE_IN_PAUSE, onoff);
        req_msg.setBundle(req_bundle);
        this.mAllShareConnector.requestCVMAsync(req_msg, this.mAllShareRespHandler);
    }

    @Override // com.samsung.android.allshare.media.PlaylistPlayer
    public boolean isSupportNavigateInPause() {
        return this.mIsNavigateInPause;
    }

    @Override // com.samsung.android.allshare.media.PlaylistPlayer
    public boolean isSupportSetAutoFlipMode() {
        return this.mIsAutoSlideShowMode;
    }

    @Override // com.samsung.android.allshare.media.PlaylistPlayer
    public boolean isSupportImage() {
        Bundle res_bundle;
        IAllShareConnector iAllShareConnector = this.mAllShareConnector;
        if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
            return false;
        }
        CVMessage req_msg = new CVMessage();
        req_msg.setActionID(AllShareAction.ACTION_AV_PLAYER_IS_SUPPORT_IMAGE_PLAYLIST_PLAYER_SYNC);
        Bundle req_bundle = new Bundle();
        req_bundle.putString("BUNDLE_STRING_ID", this.mDeviceImpl.getID());
        req_msg.setBundle(req_bundle);
        CVMessage res_msg = this.mAllShareConnector.requestCVMSync(req_msg);
        if (res_msg == null || (res_bundle = res_msg.getBundle()) == null) {
            return false;
        }
        try {
            boolean result = res_bundle.getBoolean(AllShareKey.BUNDLE_BOOLEAN_SUPPORT_PLAYLIST_PLAYER);
            return result;
        } catch (Exception e) {
            DLog.w_api(TAG_CLASS, "isSupportImage Exception", e);
            return false;
        }
    }

    @Override // com.samsung.android.allshare.media.PlaylistPlayer
    public boolean isSupportAudio() {
        Bundle res_bundle;
        IAllShareConnector iAllShareConnector = this.mAllShareConnector;
        if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
            return false;
        }
        CVMessage req_msg = new CVMessage();
        req_msg.setActionID(AllShareAction.ACTION_AV_PLAYER_IS_SUPPORT_AUDIO_PLAYLIST_PLAYER_SYNC);
        Bundle req_bundle = new Bundle();
        req_bundle.putString("BUNDLE_STRING_ID", this.mDeviceImpl.getID());
        req_msg.setBundle(req_bundle);
        CVMessage res_msg = this.mAllShareConnector.requestCVMSync(req_msg);
        if (res_msg == null || (res_bundle = res_msg.getBundle()) == null) {
            return false;
        }
        try {
            boolean result = res_bundle.getBoolean(AllShareKey.BUNDLE_BOOLEAN_SUPPORT_AUDIO_PLAYLIST_PLAYER);
            return result;
        } catch (Exception e) {
            DLog.w_api(TAG_CLASS, "isSupportAudio Exception", e);
            return false;
        }
    }

    @Override // com.samsung.android.allshare.media.PlaylistPlayer
    public boolean isSupportVideo() {
        Bundle res_bundle;
        IAllShareConnector iAllShareConnector = this.mAllShareConnector;
        if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
            return false;
        }
        CVMessage req_msg = new CVMessage();
        req_msg.setActionID(AllShareAction.ACTION_AV_PLAYER_IS_SUPPORT_VIDEO_PLAYLIST_PLAYER_SYNC);
        Bundle req_bundle = new Bundle();
        req_bundle.putString("BUNDLE_STRING_ID", this.mDeviceImpl.getID());
        req_msg.setBundle(req_bundle);
        CVMessage res_msg = this.mAllShareConnector.requestCVMSync(req_msg);
        if (res_msg == null || (res_bundle = res_msg.getBundle()) == null) {
            return false;
        }
        try {
            boolean result = res_bundle.getBoolean(AllShareKey.BUNDLE_BOOLEAN_SUPPORT_VIDEO_PLAYLIST_PLAYER);
            return result;
        } catch (Exception e) {
            DLog.w_api(TAG_CLASS, "isSupportVideo Exception", e);
            return false;
        }
    }

    public void removeEventHandler() {
        this.mAllShareConnector.unsubscribeAllShareEvent(AllShareEvent.EVENT_DEVICE_SUBSCRIBE, this.mDeviceImpl.getBundle(), this.mEventHandler);
        this.mIsSubscribed = false;
    }
}
