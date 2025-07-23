package com.samsung.android.allshare;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.samsung.android.allshare.Caption;
import com.samsung.android.allshare.Device;
import com.samsung.android.allshare.Item;
import com.samsung.android.allshare.media.AVPlayer;
import com.samsung.android.allshare.media.ContentInfo;
import com.sec.android.allshare.iface.CVMessage;
import com.sec.android.allshare.iface.IBundleHolder;
import com.sec.android.allshare.iface.IHandlerHolder;
import com.sec.android.allshare.iface.message.AllShareAction;
import com.sec.android.allshare.iface.message.AllShareEvent;
import com.sec.android.allshare.iface.message.AllShareKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/* loaded from: classes6.dex */
final class AVPlayerImpl extends AVPlayer implements IBundleHolder, IHandlerHolder {
    private static final String TAG_CLASS = "AVPlayerImpl";
    private IAllShareConnector mAllShareConnector;
    private DeviceImpl mDeviceImpl;
    private boolean mSupportControlCaption;
    private boolean mSupportGetAspectRatio;
    private boolean mSupportGetCaptionState;
    private boolean mSupportMove360View;
    private boolean mSupportOrigin360View;
    private boolean mSupportSetAspectRatio;
    private boolean mSupportZoom360View;
    private AVPlayer.IAVPlayerEventListener mAVPlayerEventListener = null;
    private AVPlayer.IAVPlayerExtensionEventListener mAVPlayerExtensionEventListener = null;
    private AVPlayer.IAVPlayerPlaybackResponseListener mAVPlaybackResponseListener = null;
    private AVPlayer.IAVPlayerVolumeResponseListener mAVPlayerVolumeResponseListener = null;
    private AVPlayer.IAVPlayerExtensionResponseListener mAVPlayerExtensionResponseListener = null;
    private boolean mIsSubscribed = false;
    private boolean mContentChangedNotified = true;
    private ArrayList<String> mPlayingContentUris = new ArrayList<>();
    private String mCurrentDMRUri = null;
    private AllShareEventHandler mEventHandler = new AllShareEventHandler(ServiceConnector.getMainLooper()) { // from class: com.samsung.android.allshare.AVPlayerImpl.1
        private HashMap<String, AVPlayer.AVPlayerState> mAVStateMap;

        {
            HashMap<String, AVPlayer.AVPlayerState> hashMap = new HashMap<>();
            this.mAVStateMap = hashMap;
            hashMap.put(AllShareEvent.EVENT_RENDERER_STATE_BUFFERING, AVPlayer.AVPlayerState.BUFFERING);
            this.mAVStateMap.put(AllShareEvent.EVENT_RENDERER_STATE_PAUSED, AVPlayer.AVPlayerState.PAUSED);
            this.mAVStateMap.put(AllShareEvent.EVENT_RENDERER_STATE_STOPPED, AVPlayer.AVPlayerState.STOPPED);
            this.mAVStateMap.put(AllShareEvent.EVENT_RENDERER_STATE_PLAYING, AVPlayer.AVPlayerState.PLAYING);
            this.mAVStateMap.put(AllShareEvent.EVENT_RENDERER_STATE_FINISHED, AVPlayer.AVPlayerState.FINISHED);
            this.mAVStateMap.put(AllShareEvent.EVENT_RENDERER_STATE_NOMEDIA, AVPlayer.AVPlayerState.STOPPED);
            this.mAVStateMap.put(AllShareEvent.EVENT_RENDERER_STATE_CONTENT_CHANGED, AVPlayer.AVPlayerState.CONTENT_CHANGED);
        }

        @Override // com.samsung.android.allshare.AllShareEventHandler
        public void handleEventMessage(CVMessage cVMessage) {
            try {
                Bundle bundle = cVMessage.getBundle();
                ERROR stringToEnum = ERROR.stringToEnum(bundle.getString("BUNDLE_ENUM_ERROR"));
                String actionID = cVMessage.getActionID();
                AVPlayer.AVPlayerState aVPlayerState = this.mAVStateMap.get(actionID);
                if (aVPlayerState == null) {
                    String string = bundle.getString(AllShareKey.BUNDLE_STRING_EXTENSION_EVENT_KEY);
                    if (actionID == null || string == null) {
                        return;
                    }
                    notifyExtensionEvent(actionID, string, stringToEnum);
                    return;
                }
                if (aVPlayerState.equals(AVPlayer.AVPlayerState.CONTENT_CHANGED)) {
                    String string2 = bundle.getString(AllShareKey.BUNDLE_STRING_APP_ITEM_ID);
                    if (string2 != null && !string2.isEmpty()) {
                        if (AVPlayerImpl.this.mContentChangedNotified) {
                            DLog.d_api(AVPlayerImpl.TAG_CLASS, "do not notify CONTENT_CHANGED event yet");
                            AVPlayerImpl.this.mCurrentDMRUri = string2;
                            return;
                        }
                        if (string2.equalsIgnoreCase(AVPlayerImpl.this.mCurrentDMRUri)) {
                            DLog.d_api(AVPlayerImpl.TAG_CLASS, "do not notify CONTENT_CHANGED event, mCurrentDMRUri is same as currentTrackUri " + string2);
                            return;
                        }
                        DLog.d_api(AVPlayerImpl.TAG_CLASS, "CONTENT_CHANGED, mCurrentDMRUri : " + AVPlayerImpl.this.mCurrentDMRUri + "  currentTrackUri : " + string2);
                        if (AVPlayerImpl.this.mCurrentDMRUri == null) {
                            DLog.d_api(AVPlayerImpl.TAG_CLASS, "do not notify CONTENT_CHANGED event, mCurrentDMRUri is null");
                            AVPlayerImpl.this.mCurrentDMRUri = string2;
                            return;
                        }
                        AVPlayerImpl.this.mCurrentDMRUri = string2;
                        if (AVPlayerImpl.this.mPlayingContentUris != null && !AVPlayerImpl.this.mPlayingContentUris.isEmpty()) {
                            if (isContains(string2, AVPlayerImpl.this.mPlayingContentUris)) {
                                DLog.d_api(AVPlayerImpl.TAG_CLASS, "handleEventMessage: this is playing content.");
                                DLog.i_api(AVPlayerImpl.TAG_CLASS, "do not notify CONTENT_CHANGED event, this is my=" + string2);
                                return;
                            }
                            AVPlayerImpl.this.mContentChangedNotified = true;
                            DLog.w_api(AVPlayerImpl.TAG_CLASS, "Notify CONTENT_CHANGED event, mPlayingContentUris[" + AVPlayerImpl.this.mPlayingContentUris + "] vs currentTrackUri[" + string2 + NavigationBarInflaterView.SIZE_MOD_END);
                        }
                        DLog.d_api(AVPlayerImpl.TAG_CLASS, "do not notify CONTENT_CHANGED event, mPlayingContentUris is null");
                        return;
                    }
                    DLog.d_api(AVPlayerImpl.TAG_CLASS, "do not notify CONTENT_CHANGED event, currentTrackUri is null");
                    return;
                }
                notifyEvent(aVPlayerState, stringToEnum);
            } catch (Error e) {
                DLog.w_api(AVPlayerImpl.TAG_CLASS, "handleEventMessage Error", e);
            } catch (Exception unused) {
                DLog.w_api(AVPlayerImpl.TAG_CLASS, "handleEventMessage Fail to notify event : Exception");
            }
        }

        private void notifyEvent(AVPlayer.AVPlayerState aVPlayerState, ERROR error) {
            if (AVPlayerImpl.this.mAVPlayerEventListener != null) {
                try {
                    AVPlayerImpl.this.mAVPlayerEventListener.onDeviceChanged(aVPlayerState, error);
                } catch (Exception e) {
                    DLog.w_api(AVPlayerImpl.TAG_CLASS, "mEventHandler.notifyEvent Error", e);
                }
            }
        }

        private void notifyExtensionEvent(String str, String str2, ERROR error) {
            if (AVPlayerImpl.this.mAVPlayerExtensionEventListener != null) {
                try {
                    AVPlayerImpl.this.mAVPlayerExtensionEventListener.onExtensionEvent(str, str2, error);
                } catch (Exception e) {
                    DLog.w_api(AVPlayerImpl.TAG_CLASS, "mEventExtensionHandler.notifyEvent Error", e);
                }
            }
        }

        private boolean isContains(String str, ArrayList<String> arrayList) {
            if (arrayList == null || str == null) {
                return false;
            }
            Iterator<String> it = arrayList.iterator();
            while (it.hasNext()) {
                if (str.endsWith(it.next())) {
                    return true;
                }
            }
            return false;
        }
    };
    private AllShareResponseHandler mAllShareRespHandler = new AllShareResponseHandler(ServiceConnector.getMainLooper()) { // from class: com.samsung.android.allshare.AVPlayerImpl.2
        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Code restructure failed: missing block: B:166:0x0258, code lost:
        
            if (r1.equals(com.sec.android.allshare.iface.message.AllShareAction.ACTION_AV_PLAYER_MOVE_360_VIEW) == false) goto L124;
         */
        /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
        @Override // com.samsung.android.allshare.AllShareResponseHandler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void handleResponseMessage(com.sec.android.allshare.iface.CVMessage r19) {
            /*
                Method dump skipped, instructions count: 1024
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.allshare.AVPlayerImpl.AnonymousClass2.handleResponseMessage(com.sec.android.allshare.iface.CVMessage):void");
        }

        private void notifyPlaybackEvent(Bundle bundle, ERROR error) {
            Bundle bundle2 = (Bundle) bundle.getParcelable(AllShareKey.BUNDLE_PARCELABLE_ITEM);
            ContentInfo build = new ContentInfo.Builder().setStartingPosition(bundle.getLong(AllShareKey.BUNDLE_LONG_CONTENT_INFO_STARTINGPOSITION)).build();
            Item fromBundle = ItemCreator.fromBundle(bundle2);
            String string = bundle2.getString(AllShareKey.BUNDLE_STRING_ITEM_CONSTRUCTOR_KEY);
            if (string != null && string.equals("WEB_CONTENT") && build != null) {
                ContentInfo.Builder builder = new ContentInfo.Builder();
                builder.setStartingPosition((int) (build.getStartingPosition() / 1000));
                build = builder.build();
            }
            if (fromBundle == null) {
                DLog.w_api(AVPlayerImpl.TAG_CLASS, "notifyPlaybackEvent : item is null");
                AVPlayerImpl.this.mAVPlaybackResponseListener.onPlayResponseReceived(fromBundle, build, ERROR.ITEM_NOT_EXIST);
                return;
            }
            if (build != null) {
                DLog.d_api(AVPlayerImpl.TAG_CLASS, "notifyPlaybackEvent : " + fromBundle + " position[" + build.getStartingPosition() + "]=" + error);
            } else {
                DLog.d_api(AVPlayerImpl.TAG_CLASS, "notifyPlaybackEvent : " + fromBundle + " = " + error);
            }
            AVPlayerImpl.this.mAVPlaybackResponseListener.onPlayResponseReceived(fromBundle, build, error);
        }
    };

    AVPlayerImpl(IAllShareConnector iAllShareConnector, DeviceImpl deviceImpl) {
        this.mDeviceImpl = null;
        this.mSupportSetAspectRatio = false;
        this.mSupportGetAspectRatio = false;
        this.mSupportMove360View = false;
        this.mSupportZoom360View = false;
        this.mSupportOrigin360View = false;
        this.mSupportControlCaption = false;
        this.mSupportGetCaptionState = false;
        if (iAllShareConnector == null) {
            DLog.w_api(TAG_CLASS, "Connection FAIL: AllShare Service Connector does not exist");
            return;
        }
        if (deviceImpl == null) {
            DLog.w_api(TAG_CLASS, "deviceImpl is null");
            return;
        }
        this.mAllShareConnector = iAllShareConnector;
        this.mDeviceImpl = deviceImpl;
        Bundle bundle = deviceImpl.getBundle();
        if (bundle == null) {
            DLog.w_api(TAG_CLASS, "deviceImpl.getBundle is null");
            return;
        }
        this.mSupportSetAspectRatio = bundle.getBoolean(AllShareKey.BUNDLE_BOOLEAN_SUPPORT_SET_ASPECT_RATIO);
        this.mSupportGetAspectRatio = bundle.getBoolean(AllShareKey.BUNDLE_BOOLEAN_SUPPORT_GET_ASPECT_RATIO);
        this.mSupportMove360View = bundle.getBoolean(AllShareKey.BUNDLE_BOOLEAN_SUPPORT_MOVE_360_VIEW);
        this.mSupportZoom360View = bundle.getBoolean(AllShareKey.BUNDLE_BOOLEAN_SUPPORT_ZOOM_360_VIEW);
        this.mSupportOrigin360View = bundle.getBoolean(AllShareKey.BUNDLE_BOOLEAN_SUPPORT_ORIGIN_360_VIEW);
        this.mSupportControlCaption = bundle.getBoolean(AllShareKey.BUNDLE_BOOLEAN_SUPPORT_CONTROL_CAPTION);
        this.mSupportGetCaptionState = bundle.getBoolean(AllShareKey.BUNDLE_BOOLEAN_SUPPORT_GET_CAPTION_STATE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.samsung.android.allshare.media.AVPlayer
    public void play(Item item, ContentInfo contentInfo) {
        if (!ServiceConnectionChecker.isAllShareServiceConnected(this.mAllShareConnector)) {
            DLog.w_api(TAG_CLASS, "play fail : SERVICE_NOT_CONNECTED");
            AVPlayer.IAVPlayerPlaybackResponseListener iAVPlayerPlaybackResponseListener = this.mAVPlaybackResponseListener;
            if (iAVPlayerPlaybackResponseListener != null) {
                iAVPlayerPlaybackResponseListener.onPlayResponseReceived(item, contentInfo, ERROR.SERVICE_NOT_CONNECTED);
                return;
            }
            return;
        }
        if (item == 0) {
            DLog.w_api(TAG_CLASS, "play item == null");
            AVPlayer.IAVPlayerPlaybackResponseListener iAVPlayerPlaybackResponseListener2 = this.mAVPlaybackResponseListener;
            if (iAVPlayerPlaybackResponseListener2 != null) {
                iAVPlayerPlaybackResponseListener2.onPlayResponseReceived(item, contentInfo, ERROR.INVALID_ARGUMENT);
                return;
            }
            return;
        }
        this.mPlayingContentUris.clear();
        this.mContentChangedNotified = false;
        Bundle bundle = new Bundle();
        if (item instanceof IBundleHolder) {
            bundle = ((IBundleHolder) item).getBundle();
        }
        String string = bundle.getString(AllShareKey.BUNDLE_STRING_ITEM_MIMETYPE);
        String string2 = bundle.getString(AllShareKey.BUNDLE_STRING_ITEM_CONSTRUCTOR_KEY);
        DLog.i_api(TAG_CLASS, "Playing Content URI : " + item.getURI().toString());
        if (string2 == null) {
            DLog.w_api(TAG_CLASS, "constructorKey == null");
            return;
        }
        if (string2.equals("WEB_CONTENT")) {
            DLog.i_api(TAG_CLASS, "play WEB_CONTENT - " + item.getTitle() + " to " + getName() + " [ " + item.getURI() + " ] ");
            if (item.getURI() == null) {
                DLog.w_api(TAG_CLASS, "uri == null");
                AVPlayer.IAVPlayerPlaybackResponseListener iAVPlayerPlaybackResponseListener3 = this.mAVPlaybackResponseListener;
                if (iAVPlayerPlaybackResponseListener3 != null) {
                    iAVPlayerPlaybackResponseListener3.onPlayResponseReceived(item, contentInfo, ERROR.INVALID_ARGUMENT);
                    return;
                }
                return;
            }
            if (contentInfo != null) {
                DLog.i_api(TAG_CLASS, "play position - " + contentInfo.getStartingPosition());
            }
            this.mPlayingContentUris.add(item.getURI().toString());
            playWebContent(item.getURI(), item, contentInfo, string);
            return;
        }
        if (string2.equals("MEDIA_SERVER")) {
            DLog.i_api(TAG_CLASS, "play MEDIA_SERVER - " + item.getTitle() + " to " + getName());
            if (contentInfo != null) {
                DLog.i_api(TAG_CLASS, "play position - " + contentInfo.getStartingPosition());
            }
            this.mPlayingContentUris.add(item.getURI().toString());
            playMediaContent(item, contentInfo);
            return;
        }
        if (string2.equals("LOCAL_CONTENT")) {
            Uri uri = item.getURI();
            if (uri == null) {
                DLog.w_api(TAG_CLASS, "play LOCAL_CONTENT : uri == null");
                this.mAVPlaybackResponseListener.onPlayResponseReceived(item, contentInfo, ERROR.INVALID_ARGUMENT);
                return;
            }
            String scheme = uri.getScheme();
            if (scheme != null && scheme.contains("file")) {
                String string3 = bundle.getString(AllShareKey.BUNDLE_STRING_FILEPATH);
                DLog.i_api(TAG_CLASS, "play LOCAL_CONTENT file - " + item.getTitle() + " to " + getName() + "[ " + string3 + " ] ");
                if (string3 == null) {
                    DLog.w_api(TAG_CLASS, "play LOCAL_CONTENT : uri == null");
                    this.mAVPlaybackResponseListener.onPlayResponseReceived(item, contentInfo, ERROR.INVALID_ARGUMENT);
                    return;
                } else {
                    if (!Item.LocalContentBuilder.checkFilePathValid(string3)) {
                        DLog.w_api(TAG_CLASS, "play  LOCAL_CONTENT: filePath is not valid");
                        this.mAVPlaybackResponseListener.onPlayResponseReceived(item, contentInfo, ERROR.INVALID_ARGUMENT);
                        return;
                    }
                    if (contentInfo != null) {
                        DLog.i_api(TAG_CLASS, "play position - " + contentInfo.getStartingPosition());
                    }
                    this.mPlayingContentUris.add(string3);
                    playLocalContent(string3, item, contentInfo, string);
                    return;
                }
            }
            if (scheme != null && scheme.contains("content")) {
                DLog.i_api(TAG_CLASS, "play LOCAL_CONTENT content - " + item.getTitle() + " to " + getName() + "[ " + uri + " ] ");
                if (contentInfo != null) {
                    DLog.i_api(TAG_CLASS, "play position - " + contentInfo.getStartingPosition());
                }
                this.mPlayingContentUris.add(parseUriFilePath(item.getURI()));
                playLocalContent(uri, item, contentInfo, string);
                return;
            }
            AVPlayer.IAVPlayerPlaybackResponseListener iAVPlayerPlaybackResponseListener4 = this.mAVPlaybackResponseListener;
            if (iAVPlayerPlaybackResponseListener4 != null) {
                iAVPlayerPlaybackResponseListener4.onPlayResponseReceived(item, contentInfo, ERROR.INVALID_ARGUMENT);
            }
        }
    }

    private String parseUriFilePath(Uri uri) {
        Context context;
        ContentResolver contentResolver;
        Cursor query;
        if (uri != null && (context = ServiceConnector.getContext()) != null && (contentResolver = context.getContentResolver()) != null && (query = contentResolver.query(uri, null, null, null, null)) != null) {
            if (query.moveToFirst()) {
                String string = query.getString(1);
                query.close();
                return string;
            }
            query.close();
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.samsung.android.allshare.media.AVPlayer
    public void prepare(Item item) {
        if (!ServiceConnectionChecker.isAllShareServiceConnected(this.mAllShareConnector)) {
            DLog.w_api(TAG_CLASS, "prepare : SERVICE_NOT_CONNECTED");
            return;
        }
        if (item == 0) {
            DLog.w_api(TAG_CLASS, "prepare Fail :  Item does not exist ");
            return;
        }
        CVMessage cVMessage = new CVMessage();
        cVMessage.setActionID(AllShareAction.ACTION_AV_PLAYER_PREPARE);
        Bundle bundle = new Bundle();
        bundle.putString("BUNDLE_STRING_ID", getID());
        if (item instanceof IBundleHolder) {
            bundle.putParcelable(AllShareKey.BUNDLE_PARCELABLE_ITEM, ((IBundleHolder) item).getBundle());
        }
        cVMessage.setBundle(bundle);
        this.mAllShareConnector.requestCVMAsync(cVMessage, this.mAllShareRespHandler);
        DLog.i_api(TAG_CLASS, "prepare : " + item.getTitle() + " to " + getName() + "uri : " + item.getURI());
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public void stop() {
        if (!ServiceConnectionChecker.isAllShareServiceConnected(this.mAllShareConnector)) {
            DLog.w_api(TAG_CLASS, "stop fail : SERVICE_NOT_CONNECTED");
            AVPlayer.IAVPlayerPlaybackResponseListener iAVPlayerPlaybackResponseListener = this.mAVPlaybackResponseListener;
            if (iAVPlayerPlaybackResponseListener != null) {
                iAVPlayerPlaybackResponseListener.onStopResponseReceived(ERROR.SERVICE_NOT_CONNECTED);
                return;
            }
            return;
        }
        DLog.i_api(TAG_CLASS, "stop : " + getName());
        CVMessage cVMessage = new CVMessage();
        cVMessage.setActionID(AllShareAction.ACTION_AV_PLAYER_STOP);
        Bundle bundle = new Bundle();
        bundle.putString("BUNDLE_STRING_ID", getID());
        cVMessage.setBundle(bundle);
        this.mAllShareConnector.requestCVMAsync(cVMessage, this.mAllShareRespHandler);
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public void seek(long j) {
        if (!ServiceConnectionChecker.isAllShareServiceConnected(this.mAllShareConnector)) {
            DLog.w_api(TAG_CLASS, "seek fail : SERVICE_NOT_CONNECTED");
            AVPlayer.IAVPlayerPlaybackResponseListener iAVPlayerPlaybackResponseListener = this.mAVPlaybackResponseListener;
            if (iAVPlayerPlaybackResponseListener != null) {
                iAVPlayerPlaybackResponseListener.onSeekResponseReceived(j, ERROR.SERVICE_NOT_CONNECTED);
                return;
            }
            return;
        }
        DLog.i_api(TAG_CLASS, "seek pos :" + j + " " + getName());
        CVMessage cVMessage = new CVMessage();
        cVMessage.setActionID(AllShareAction.ACTION_AV_PLAYER_SEEK);
        Bundle bundle = new Bundle();
        bundle.putString("BUNDLE_STRING_ID", getID());
        bundle.putLong(AllShareKey.BUNDLE_LONG_POSITION, j * 1000);
        cVMessage.setBundle(bundle);
        this.mAllShareConnector.requestCVMAsync(cVMessage, this.mAllShareRespHandler);
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public void pause() {
        if (!ServiceConnectionChecker.isAllShareServiceConnected(this.mAllShareConnector)) {
            DLog.w_api(TAG_CLASS, "pause fail : SERVICE_NOT_CONNECTED");
            AVPlayer.IAVPlayerPlaybackResponseListener iAVPlayerPlaybackResponseListener = this.mAVPlaybackResponseListener;
            if (iAVPlayerPlaybackResponseListener != null) {
                iAVPlayerPlaybackResponseListener.onPauseResponseReceived(ERROR.SERVICE_NOT_CONNECTED);
                return;
            }
            return;
        }
        DLog.i_api(TAG_CLASS, "pause " + getName());
        CVMessage cVMessage = new CVMessage();
        cVMessage.setActionID(AllShareAction.ACTION_AV_PLAYER_PAUSE);
        Bundle bundle = new Bundle();
        bundle.putString("BUNDLE_STRING_ID", getID());
        cVMessage.setBundle(bundle);
        this.mAllShareConnector.requestCVMAsync(cVMessage, this.mAllShareRespHandler);
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public void resume() {
        if (!ServiceConnectionChecker.isAllShareServiceConnected(this.mAllShareConnector)) {
            DLog.w_api(TAG_CLASS, "resume fail : SERVICE_NOT_CONNECTED");
            AVPlayer.IAVPlayerPlaybackResponseListener iAVPlayerPlaybackResponseListener = this.mAVPlaybackResponseListener;
            if (iAVPlayerPlaybackResponseListener != null) {
                iAVPlayerPlaybackResponseListener.onResumeResponseReceived(ERROR.SERVICE_NOT_CONNECTED);
                return;
            }
            return;
        }
        DLog.i_api(TAG_CLASS, "resume " + getName());
        CVMessage cVMessage = new CVMessage();
        cVMessage.setActionID(AllShareAction.ACTION_AV_PLAYER_RESUME);
        Bundle bundle = new Bundle();
        bundle.putString("BUNDLE_STRING_ID", getID());
        cVMessage.setBundle(bundle);
        this.mAllShareConnector.requestCVMAsync(cVMessage, this.mAllShareRespHandler);
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public void getVolume() {
        if (!ServiceConnectionChecker.isAllShareServiceConnected(this.mAllShareConnector)) {
            AVPlayer.IAVPlayerVolumeResponseListener iAVPlayerVolumeResponseListener = this.mAVPlayerVolumeResponseListener;
            if (iAVPlayerVolumeResponseListener != null) {
                iAVPlayerVolumeResponseListener.onGetVolumeResponseReceived(-1, ERROR.SERVICE_NOT_CONNECTED);
                return;
            }
            return;
        }
        CVMessage cVMessage = new CVMessage();
        cVMessage.setActionID(AllShareAction.ACTION_AV_PLAYER_REQUEST_GET_VOLUME);
        Bundle bundle = new Bundle();
        bundle.putString("BUNDLE_STRING_ID", getID());
        cVMessage.setBundle(bundle);
        this.mAllShareConnector.requestCVMAsync(cVMessage, this.mAllShareRespHandler);
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public void setVolume(int i) {
        if (!ServiceConnectionChecker.isAllShareServiceConnected(this.mAllShareConnector)) {
            DLog.w_api(TAG_CLASS, "setVolume fail : SERVICE_NOT_CONNECTED");
            AVPlayer.IAVPlayerVolumeResponseListener iAVPlayerVolumeResponseListener = this.mAVPlayerVolumeResponseListener;
            if (iAVPlayerVolumeResponseListener != null) {
                iAVPlayerVolumeResponseListener.onSetVolumeResponseReceived(i, ERROR.SERVICE_NOT_CONNECTED);
                return;
            }
            return;
        }
        if (i < 0 || i > 100) {
            DLog.w_api(TAG_CLASS, "setVolume fail : level (INVALID_ARGUMENT)");
            AVPlayer.IAVPlayerVolumeResponseListener iAVPlayerVolumeResponseListener2 = this.mAVPlayerVolumeResponseListener;
            if (iAVPlayerVolumeResponseListener2 != null) {
                iAVPlayerVolumeResponseListener2.onSetVolumeResponseReceived(i, ERROR.INVALID_ARGUMENT);
                return;
            }
            return;
        }
        DLog.i_api(TAG_CLASS, "setVolume -level : " + i + " " + getName());
        CVMessage cVMessage = new CVMessage();
        cVMessage.setActionID(AllShareAction.ACTION_AV_PLAYER_REQUEST_SET_VOLUME);
        Bundle bundle = new Bundle();
        bundle.putString("BUNDLE_STRING_ID", getID());
        bundle.putInt(AllShareKey.BUNDLE_INT_VOLUME, i);
        cVMessage.setBundle(bundle);
        this.mAllShareConnector.requestCVMAsync(cVMessage, this.mAllShareRespHandler);
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public void getMute() {
        if (!ServiceConnectionChecker.isAllShareServiceConnected(this.mAllShareConnector)) {
            AVPlayer.IAVPlayerVolumeResponseListener iAVPlayerVolumeResponseListener = this.mAVPlayerVolumeResponseListener;
            if (iAVPlayerVolumeResponseListener != null) {
                iAVPlayerVolumeResponseListener.onSetMuteResponseReceived(false, ERROR.SERVICE_NOT_CONNECTED);
                return;
            }
            return;
        }
        CVMessage cVMessage = new CVMessage();
        cVMessage.setActionID(AllShareAction.ACTION_AV_PLAYER_REQUEST_GET_MUTE);
        Bundle bundle = new Bundle();
        bundle.putString("BUNDLE_STRING_ID", getID());
        cVMessage.setBundle(bundle);
        this.mAllShareConnector.requestCVMAsync(cVMessage, this.mAllShareRespHandler);
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public void setMute(boolean z) {
        if (!ServiceConnectionChecker.isAllShareServiceConnected(this.mAllShareConnector)) {
            DLog.w_api(TAG_CLASS, "setMute fail : SERVICE_NOT_CONNECTED");
            AVPlayer.IAVPlayerVolumeResponseListener iAVPlayerVolumeResponseListener = this.mAVPlayerVolumeResponseListener;
            if (iAVPlayerVolumeResponseListener != null) {
                iAVPlayerVolumeResponseListener.onSetMuteResponseReceived(z, ERROR.SERVICE_NOT_CONNECTED);
                return;
            }
            return;
        }
        DLog.i_api(TAG_CLASS, "setMute - " + z + " " + getName());
        CVMessage cVMessage = new CVMessage();
        cVMessage.setActionID(AllShareAction.ACTION_AV_PLAYER_REQUEST_SET_MUTE);
        Bundle bundle = new Bundle();
        bundle.putString("BUNDLE_STRING_ID", getID());
        bundle.putBoolean(AllShareKey.BUNDLE_BOOLEAN_MUTE, z);
        cVMessage.setBundle(bundle);
        this.mAllShareConnector.requestCVMAsync(cVMessage, this.mAllShareRespHandler);
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public void getPlayPosition() {
        if (!ServiceConnectionChecker.isAllShareServiceConnected(this.mAllShareConnector)) {
            AVPlayer.IAVPlayerPlaybackResponseListener iAVPlayerPlaybackResponseListener = this.mAVPlaybackResponseListener;
            if (iAVPlayerPlaybackResponseListener != null) {
                iAVPlayerPlaybackResponseListener.onGetPlayPositionResponseReceived(-1L, ERROR.SERVICE_NOT_CONNECTED);
                return;
            }
            return;
        }
        CVMessage cVMessage = new CVMessage();
        cVMessage.setActionID(AllShareAction.ACTION_AV_PLAYER_REQUEST_PLAY_POSITION);
        Bundle bundle = new Bundle();
        bundle.putString("BUNDLE_STRING_ID", getID());
        cVMessage.setBundle(bundle);
        this.mAllShareConnector.requestCVMAsync(cVMessage, this.mAllShareRespHandler);
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public AVPlayer.AVPlayerState getPlayerState() {
        Bundle bundle;
        AVPlayer.AVPlayerState aVPlayerState = AVPlayer.AVPlayerState.UNKNOWN;
        if (ServiceConnectionChecker.isAllShareServiceConnected(this.mAllShareConnector)) {
            CVMessage cVMessage = new CVMessage();
            cVMessage.setActionID(AllShareAction.ACTION_AV_PLAYER_GET_PLAYER_STATE_SYNC);
            Bundle bundle2 = new Bundle();
            bundle2.putString("BUNDLE_STRING_ID", getID());
            cVMessage.setBundle(bundle2);
            CVMessage requestCVMSync = this.mAllShareConnector.requestCVMSync(cVMessage);
            if (requestCVMSync != null && (bundle = requestCVMSync.getBundle()) != null) {
                return AVPlayer.AVPlayerState.stringToEnum(bundle.getString(AllShareKey.BUNDLE_STRING_AV_PLAER_STATE));
            }
        }
        return aVPlayerState;
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public void setResponseListener(AVPlayer.IAVPlayerPlaybackResponseListener iAVPlayerPlaybackResponseListener) {
        this.mAVPlaybackResponseListener = iAVPlayerPlaybackResponseListener;
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public void setVolumeResponseListener(AVPlayer.IAVPlayerVolumeResponseListener iAVPlayerVolumeResponseListener) {
        this.mAVPlayerVolumeResponseListener = iAVPlayerVolumeResponseListener;
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public void setExtensionResponseListener(AVPlayer.IAVPlayerExtensionResponseListener iAVPlayerExtensionResponseListener) {
        this.mAVPlayerExtensionResponseListener = iAVPlayerExtensionResponseListener;
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public void setExtensionEventListener(AVPlayer.IAVPlayerExtensionEventListener iAVPlayerExtensionEventListener) {
        if (!ServiceConnectionChecker.isAllShareServiceConnected(this.mAllShareConnector)) {
            DLog.w_api(TAG_CLASS, "setEventListener error! AllShareService is not connected");
            return;
        }
        this.mAVPlayerExtensionEventListener = iAVPlayerExtensionEventListener;
        boolean z = this.mIsSubscribed;
        if (!z && iAVPlayerExtensionEventListener != null) {
            this.mAllShareConnector.subscribeAllShareEvent(AllShareEvent.EVENT_DEVICE_SUBSCRIBE, this.mDeviceImpl.getBundle(), this.mEventHandler);
            this.mIsSubscribed = true;
        } else if (z && iAVPlayerExtensionEventListener == null) {
            this.mAllShareConnector.unsubscribeAllShareEvent(AllShareEvent.EVENT_DEVICE_SUBSCRIBE, this.mDeviceImpl.getBundle(), this.mEventHandler);
            this.mIsSubscribed = false;
        }
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public void setEventListener(AVPlayer.IAVPlayerEventListener iAVPlayerEventListener) {
        if (!ServiceConnectionChecker.isAllShareServiceConnected(this.mAllShareConnector)) {
            DLog.w_api(TAG_CLASS, "setEventListener error! AllShareService is not connected");
            return;
        }
        this.mAVPlayerEventListener = iAVPlayerEventListener;
        boolean z = this.mIsSubscribed;
        if (!z && iAVPlayerEventListener != null) {
            this.mAllShareConnector.subscribeAllShareEvent(AllShareEvent.EVENT_DEVICE_SUBSCRIBE, this.mDeviceImpl.getBundle(), this.mEventHandler);
            this.mIsSubscribed = true;
        } else if (z && iAVPlayerEventListener == null) {
            this.mAllShareConnector.unsubscribeAllShareEvent(AllShareEvent.EVENT_DEVICE_SUBSCRIBE, this.mDeviceImpl.getBundle(), this.mEventHandler);
            this.mIsSubscribed = false;
        }
    }

    @Override // com.samsung.android.allshare.Device
    public String getModelName() {
        DeviceImpl deviceImpl = this.mDeviceImpl;
        if (deviceImpl == null) {
            return "";
        }
        return deviceImpl.getModelName();
    }

    @Override // com.samsung.android.allshare.Device
    public Device.DeviceType getDeviceType() {
        DeviceImpl deviceImpl = this.mDeviceImpl;
        if (deviceImpl == null) {
            return Device.DeviceType.UNKNOWN;
        }
        return deviceImpl.getDeviceType();
    }

    @Override // com.samsung.android.allshare.Device
    public String getIPAddress() {
        DeviceImpl deviceImpl = this.mDeviceImpl;
        if (deviceImpl == null) {
            return "";
        }
        return deviceImpl.getIPAddress();
    }

    @Override // com.samsung.android.allshare.Device
    public String getName() {
        DeviceImpl deviceImpl = this.mDeviceImpl;
        if (deviceImpl == null) {
            return "";
        }
        return deviceImpl.getName();
    }

    @Override // com.samsung.android.allshare.Device
    public Uri getIcon() {
        DeviceImpl deviceImpl = this.mDeviceImpl;
        if (deviceImpl == null) {
            return Uri.parse("");
        }
        return deviceImpl.getIcon();
    }

    @Override // com.samsung.android.allshare.Device
    public ArrayList<Icon> getIconList() {
        DeviceImpl deviceImpl = this.mDeviceImpl;
        if (deviceImpl == null) {
            return new ArrayList<>();
        }
        return deviceImpl.getIconList();
    }

    @Override // com.samsung.android.allshare.Device
    public String getID() {
        DeviceImpl deviceImpl = this.mDeviceImpl;
        if (deviceImpl == null) {
            return "";
        }
        return deviceImpl.getID();
    }

    @Override // com.samsung.android.allshare.Device
    public Device.DeviceDomain getDeviceDomain() {
        DeviceImpl deviceImpl = this.mDeviceImpl;
        if (deviceImpl == null) {
            return Device.DeviceDomain.UNKNOWN;
        }
        return deviceImpl.getDeviceDomain();
    }

    @Override // com.sec.android.allshare.iface.IBundleHolder
    public Bundle getBundle() {
        DeviceImpl deviceImpl = this.mDeviceImpl;
        if (deviceImpl == null) {
            return new Bundle();
        }
        return deviceImpl.getBundle();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private Bundle extractBundle(Item item) {
        Bundle bundle = new Bundle();
        if (item instanceof IBundleHolder) {
            bundle.putParcelable(AllShareKey.BUNDLE_PARCELABLE_ITEM, ((IBundleHolder) item).getBundle());
        }
        return bundle;
    }

    private void playMediaContent(Item item, ContentInfo contentInfo) {
        playItem(item, contentInfo);
    }

    private void playLocalContent(Uri uri, Item item, ContentInfo contentInfo, String str) {
        playUri(uri, item, contentInfo, str, AllShareAction.ACTION_AV_PLAYER_PLAY_LOCAL_CONTENS_URI);
    }

    private void playLocalContent(String str, Item item, ContentInfo contentInfo, String str2) {
        playFilePath(str, item, contentInfo, str2);
    }

    private void playWebContent(Uri uri, Item item, ContentInfo contentInfo, String str) {
        ContentInfo contentInfo2;
        if (contentInfo != null) {
            ContentInfo.Builder builder = new ContentInfo.Builder();
            builder.setStartingPosition(contentInfo.getStartingPosition() * 1000);
            contentInfo2 = builder.build();
        } else {
            contentInfo2 = null;
        }
        playUri(uri, item, contentInfo2, str, AllShareAction.ACTION_AV_PLAYER_PLAY_URI);
    }

    private void playItem(Item item, ContentInfo contentInfo) {
        if (!ServiceConnectionChecker.isAllShareServiceConnected(this.mAllShareConnector)) {
            this.mAVPlaybackResponseListener.onPlayResponseReceived(item, contentInfo, ERROR.SERVICE_NOT_CONNECTED);
            return;
        }
        CVMessage cVMessage = new CVMessage();
        cVMessage.setActionID(AllShareAction.ACTION_AV_PLAYER_PLAY);
        Bundle extractBundle = extractBundle(item);
        extractBundle.putString("BUNDLE_STRING_ID", getID());
        extractBundle.putLong(AllShareKey.BUNDLE_LONG_CONTENT_INFO_STARTINGPOSITION, contentInfo != null ? contentInfo.getStartingPosition() : 0L);
        cVMessage.setBundle(extractBundle);
        this.mAllShareConnector.requestCVMAsync(cVMessage, this.mAllShareRespHandler);
    }

    private void playUri(Uri uri, Item item, ContentInfo contentInfo, String str, String str2) {
        if (!ServiceConnectionChecker.isAllShareServiceConnected(this.mAllShareConnector)) {
            this.mAVPlaybackResponseListener.onPlayResponseReceived(item, contentInfo, ERROR.SERVICE_NOT_CONNECTED);
            return;
        }
        String title = item.getTitle();
        CVMessage cVMessage = new CVMessage();
        cVMessage.setActionID(str2);
        Bundle extractBundle = extractBundle(item);
        extractBundle.putString(AllShareKey.BUNDLE_STRING_TITLE, title);
        extractBundle.putString("BUNDLE_STRING_ID", getID());
        extractBundle.putParcelable(AllShareKey.BUNDLE_PARCELABLE_URI, uri);
        extractBundle.putLong(AllShareKey.BUNDLE_LONG_CONTENT_INFO_STARTINGPOSITION, contentInfo != null ? contentInfo.getStartingPosition() : 0L);
        cVMessage.setBundle(extractBundle);
        this.mAllShareConnector.requestCVMAsync(cVMessage, this.mAllShareRespHandler);
    }

    private void playFilePath(String str, Item item, ContentInfo contentInfo, String str2) {
        if (!ServiceConnectionChecker.isAllShareServiceConnected(this.mAllShareConnector)) {
            this.mAVPlaybackResponseListener.onPlayResponseReceived(item, contentInfo, ERROR.SERVICE_NOT_CONNECTED);
            return;
        }
        String title = item.getTitle();
        CVMessage cVMessage = new CVMessage();
        cVMessage.setActionID(AllShareAction.ACTION_AV_PLAYER_PLAY_LOCAL_CONTENS_FILEPATH_WITH_METADATA);
        Bundle extractBundle = extractBundle(item);
        extractBundle.putString(AllShareKey.BUNDLE_STRING_TITLE, title);
        extractBundle.putString("BUNDLE_STRING_ID", getID());
        extractBundle.putLong(AllShareKey.BUNDLE_LONG_CONTENT_INFO_STARTINGPOSITION, contentInfo != null ? contentInfo.getStartingPosition() : 0L);
        cVMessage.setBundle(extractBundle);
        this.mAllShareConnector.requestCVMAsync(cVMessage, this.mAllShareRespHandler);
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public void getMediaInfo() {
        if (!ServiceConnectionChecker.isAllShareServiceConnected(this.mAllShareConnector)) {
            AVPlayer.IAVPlayerPlaybackResponseListener iAVPlayerPlaybackResponseListener = this.mAVPlaybackResponseListener;
            if (iAVPlayerPlaybackResponseListener != null) {
                iAVPlayerPlaybackResponseListener.onGetMediaInfoResponseReceived(null, ERROR.SERVICE_NOT_CONNECTED);
                return;
            }
            return;
        }
        CVMessage cVMessage = new CVMessage();
        cVMessage.setActionID(AllShareAction.ACTION_AV_PLAYER_REQUEST_GET_MEDIA_INFO);
        Bundle bundle = new Bundle();
        bundle.putString("BUNDLE_STRING_ID", getID());
        cVMessage.setBundle(bundle);
        this.mAllShareConnector.requestCVMAsync(cVMessage, this.mAllShareRespHandler);
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public boolean isSupportVideo() {
        return isSupportedByType(2);
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public boolean isSupportAudio() {
        return isSupportedByType(3);
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public boolean isSupportRedirect() {
        Bundle bundle;
        if (!ServiceConnectionChecker.isAllShareServiceConnected(this.mAllShareConnector)) {
            return false;
        }
        CVMessage cVMessage = new CVMessage();
        cVMessage.setActionID(AllShareAction.ACTION_AV_PLAYER_IS_SUPPORT_REDIRECT_SYNC);
        Bundle bundle2 = new Bundle();
        bundle2.putString("BUNDLE_STRING_ID", getID());
        cVMessage.setBundle(bundle2);
        CVMessage requestCVMSync = this.mAllShareConnector.requestCVMSync(cVMessage);
        if (requestCVMSync == null || (bundle = requestCVMSync.getBundle()) == null) {
            return false;
        }
        if (ERROR.NOT_SUPPORTED_FRAMEWORK_VERSION.enumToString().equals(bundle.getString("BUNDLE_ENUM_ERROR"))) {
            DLog.w_api(TAG_CLASS, "isRedirectSupportable() Exception : NOT_SUPPORTED_FRAMEWORK_VERSION");
            return false;
        }
        try {
            return bundle.getBoolean(AllShareKey.BUNDLE_BOOLEAN_SUPPORT_REDIRECT);
        } catch (Exception e) {
            DLog.w_api(TAG_CLASS, "isRedirectSupportable Exception", e);
            return false;
        }
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    @Deprecated
    public boolean isRedirectSupportable() {
        return isSupportRedirect();
    }

    @Override // com.samsung.android.allshare.Device
    public String getNIC() {
        DeviceImpl deviceImpl = this.mDeviceImpl;
        if (deviceImpl == null) {
            return "";
        }
        return deviceImpl.getNIC();
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public void getState() {
        if (!ServiceConnectionChecker.isAllShareServiceConnected(this.mAllShareConnector)) {
            AVPlayer.IAVPlayerPlaybackResponseListener iAVPlayerPlaybackResponseListener = this.mAVPlaybackResponseListener;
            if (iAVPlayerPlaybackResponseListener != null) {
                iAVPlayerPlaybackResponseListener.onGetStateResponseReceived(AVPlayer.AVPlayerState.UNKNOWN, ERROR.SERVICE_NOT_CONNECTED);
                return;
            }
            return;
        }
        CVMessage cVMessage = new CVMessage();
        cVMessage.setActionID(AllShareAction.ACTION_AV_PLAYER_REQUEST_GET_PLAYER_STATE);
        Bundle bundle = new Bundle();
        bundle.putString("BUNDLE_STRING_ID", getID());
        cVMessage.setBundle(bundle);
        this.mAllShareConnector.requestCVMAsync(cVMessage, this.mAllShareRespHandler);
    }

    @Override // com.sec.android.allshare.iface.IHandlerHolder
    public void removeEventHandler() {
        this.mAllShareConnector.unsubscribeAllShareEvent(AllShareEvent.EVENT_DEVICE_SUBSCRIBE, getBundle(), this.mEventHandler);
        this.mIsSubscribed = false;
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public boolean isSupportDynamicBuffering() {
        Bundle bundle;
        if (!ServiceConnectionChecker.isAllShareServiceConnected(this.mAllShareConnector)) {
            return false;
        }
        CVMessage cVMessage = new CVMessage();
        cVMessage.setActionID(AllShareAction.ACTION_AV_PLAYER_IS_SUPPORT_DYNAMIC_BUFFERING);
        Bundle bundle2 = new Bundle();
        bundle2.putString("BUNDLE_STRING_ID", getID());
        cVMessage.setBundle(bundle2);
        CVMessage requestCVMSync = this.mAllShareConnector.requestCVMSync(cVMessage);
        if (requestCVMSync == null || (bundle = requestCVMSync.getBundle()) == null) {
            return false;
        }
        try {
            return bundle.getBoolean(AllShareKey.BUNDLE_BOOLEAN_SUPPORT_DYNAMIC_BUFFERING);
        } catch (Exception e) {
            DLog.w_api(TAG_CLASS, "isSupportDynamicBuffering Exception", e);
            return false;
        }
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public void skipDynamicBuffering() {
        if (ServiceConnectionChecker.isAllShareServiceConnected(this.mAllShareConnector)) {
            CVMessage cVMessage = new CVMessage();
            cVMessage.setActionID(AllShareAction.ACTION_AV_PLAYER_SKIP_DYNAMIC_BUFFERING);
            Bundle bundle = new Bundle();
            bundle.putString("BUNDLE_STRING_ID", getID());
            cVMessage.setBundle(bundle);
            this.mAllShareConnector.requestCVMAsync(cVMessage, this.mAllShareRespHandler);
        }
    }

    @Override // com.samsung.android.allshare.Device
    public boolean isSeekableOnPaused() {
        DeviceImpl deviceImpl = this.mDeviceImpl;
        if (deviceImpl == null) {
            return false;
        }
        return deviceImpl.isSeekableOnPaused();
    }

    @Override // com.samsung.android.allshare.Device
    public boolean isWholeHomeAudio() {
        DeviceImpl deviceImpl = this.mDeviceImpl;
        if (deviceImpl == null) {
            return false;
        }
        return deviceImpl.isWholeHomeAudio();
    }

    @Override // com.samsung.android.allshare.Device
    public String getP2pMacAddress() {
        DeviceImpl deviceImpl = this.mDeviceImpl;
        if (deviceImpl == null) {
            return "";
        }
        return deviceImpl.getP2pMacAddress();
    }

    @Override // com.samsung.android.allshare.Device
    public String getScreenSharingInfo() {
        DeviceImpl deviceImpl = this.mDeviceImpl;
        if (deviceImpl == null) {
            return "";
        }
        return deviceImpl.getScreenSharingInfo();
    }

    @Override // com.samsung.android.allshare.Device
    public void requestMobileToTV(String str, int i) {
        DLog.w_api(TAG_CLASS, "requestMobileToTV : call requestMobileToTV");
        DeviceImpl deviceImpl = this.mDeviceImpl;
        if (deviceImpl == null) {
            return;
        }
        deviceImpl.requestMobileToTV(str, i);
    }

    @Override // com.samsung.android.allshare.Device
    public String getSecProductP2pMacAddr() {
        DeviceImpl deviceImpl = this.mDeviceImpl;
        if (deviceImpl == null) {
            return "";
        }
        return deviceImpl.getSecProductP2pMacAddr();
    }

    @Override // com.samsung.android.allshare.Device
    public String getScreenSharingP2pMacAddr() {
        return "";
    }

    @Override // com.samsung.android.allshare.Device
    public String getProductCapInfo(Device.InformationType informationType) {
        DeviceImpl deviceImpl = this.mDeviceImpl;
        if (deviceImpl == null) {
            return "";
        }
        return deviceImpl.getProductCapInfo(informationType);
    }

    @Override // com.samsung.android.allshare.Device
    public String getScreenSharingInfo(Device.InformationType informationType) {
        DeviceImpl deviceImpl = this.mDeviceImpl;
        if (deviceImpl == null) {
            return "";
        }
        return deviceImpl.getScreenSharingInfo(informationType);
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public void setAspectRatio(String str) {
        DLog.i_api(TAG_CLASS, "setAspectRatio to " + str);
        if (!ServiceConnectionChecker.isAllShareServiceConnected(this.mAllShareConnector)) {
            DLog.w_api(TAG_CLASS, "setAspectRatio : SERVICE_NOT_CONNECTED");
            AVPlayer.IAVPlayerExtensionResponseListener iAVPlayerExtensionResponseListener = this.mAVPlayerExtensionResponseListener;
            if (iAVPlayerExtensionResponseListener != null) {
                iAVPlayerExtensionResponseListener.onSetAspectRatioResponseReceived(ERROR.SERVICE_NOT_CONNECTED);
                return;
            }
            return;
        }
        CVMessage cVMessage = new CVMessage();
        cVMessage.setActionID(AllShareAction.ACTION_AV_PLAYER_SET_ASPECT_RATIO);
        Bundle bundle = new Bundle();
        bundle.putString("BUNDLE_STRING_ID", getID());
        bundle.putString(AllShareKey.BUNDLE_STRING_ASPECT_RATIO, str);
        cVMessage.setBundle(bundle);
        this.mAllShareConnector.requestCVMAsync(cVMessage, this.mAllShareRespHandler);
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public void requestAspectRatioState() {
        DLog.i_api(TAG_CLASS, "requestAspectRatioState");
        if (!ServiceConnectionChecker.isAllShareServiceConnected(this.mAllShareConnector)) {
            DLog.w_api(TAG_CLASS, "getAspectRatio : SERVICE_NOT_CONNECTED");
            AVPlayer.IAVPlayerExtensionResponseListener iAVPlayerExtensionResponseListener = this.mAVPlayerExtensionResponseListener;
            if (iAVPlayerExtensionResponseListener != null) {
                iAVPlayerExtensionResponseListener.onAspectRatioStateResponseReceived(null, ERROR.SERVICE_NOT_CONNECTED);
                return;
            }
            return;
        }
        CVMessage cVMessage = new CVMessage();
        cVMessage.setActionID(AllShareAction.ACTION_AV_PLAYER_GET_ASPECT_RATIO);
        Bundle bundle = new Bundle();
        bundle.putString("BUNDLE_STRING_ID", getID());
        cVMessage.setBundle(bundle);
        this.mAllShareConnector.requestCVMAsync(cVMessage, this.mAllShareRespHandler);
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public void move360View(float f, float f2) {
        DLog.i_api(TAG_CLASS, "move360View to [latitudeOffset]" + f + " [longitudeOffset]" + f2);
        if (!ServiceConnectionChecker.isAllShareServiceConnected(this.mAllShareConnector)) {
            DLog.w_api(TAG_CLASS, "move360View : SERVICE_NOT_CONNECTED");
            AVPlayer.IAVPlayerExtensionResponseListener iAVPlayerExtensionResponseListener = this.mAVPlayerExtensionResponseListener;
            if (iAVPlayerExtensionResponseListener != null) {
                iAVPlayerExtensionResponseListener.onMove360ViewResponseReceived(ERROR.SERVICE_NOT_CONNECTED);
                return;
            }
            return;
        }
        CVMessage cVMessage = new CVMessage();
        cVMessage.setActionID(AllShareAction.ACTION_AV_PLAYER_MOVE_360_VIEW);
        Bundle bundle = new Bundle();
        bundle.putString("BUNDLE_STRING_ID", getID());
        bundle.putFloat(AllShareKey.BUNDLE_FLOATING_LATITUDE_OFFSET, f);
        bundle.putFloat("BUNDLE_LONG_PROGRESS", f2);
        cVMessage.setBundle(bundle);
        this.mAllShareConnector.requestCVMAsync(cVMessage, this.mAllShareRespHandler);
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public void zoom360View(float f) {
        DLog.i_api(TAG_CLASS, "zoom360View to " + f);
        if (!ServiceConnectionChecker.isAllShareServiceConnected(this.mAllShareConnector)) {
            DLog.w_api(TAG_CLASS, "zoom360View : SERVICE_NOT_CONNECTED");
            AVPlayer.IAVPlayerExtensionResponseListener iAVPlayerExtensionResponseListener = this.mAVPlayerExtensionResponseListener;
            if (iAVPlayerExtensionResponseListener != null) {
                iAVPlayerExtensionResponseListener.onZoom360ViewResponseReceived(ERROR.SERVICE_NOT_CONNECTED);
                return;
            }
            return;
        }
        CVMessage cVMessage = new CVMessage();
        cVMessage.setActionID(AllShareAction.ACTION_AV_PLAYER_ZOOM_360_VIEW);
        Bundle bundle = new Bundle();
        bundle.putString("BUNDLE_STRING_ID", getID());
        bundle.putFloat(AllShareKey.BUNDLE_FLOATING_SCALEFACTOR, f);
        cVMessage.setBundle(bundle);
        this.mAllShareConnector.requestCVMAsync(cVMessage, this.mAllShareRespHandler);
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public void reset360View() {
        DLog.i_api(TAG_CLASS, "reset360View");
        if (!ServiceConnectionChecker.isAllShareServiceConnected(this.mAllShareConnector)) {
            DLog.w_api(TAG_CLASS, "origin360View : SERVICE_NOT_CONNECTED");
            AVPlayer.IAVPlayerExtensionResponseListener iAVPlayerExtensionResponseListener = this.mAVPlayerExtensionResponseListener;
            if (iAVPlayerExtensionResponseListener != null) {
                iAVPlayerExtensionResponseListener.onReset360ViewResponseReceived(ERROR.SERVICE_NOT_CONNECTED);
                return;
            }
            return;
        }
        CVMessage cVMessage = new CVMessage();
        cVMessage.setActionID(AllShareAction.ACTION_AV_PLAYER_ORIGIN_360_VIEW);
        Bundle bundle = new Bundle();
        bundle.putString("BUNDLE_STRING_ID", getID());
        cVMessage.setBundle(bundle);
        this.mAllShareConnector.requestCVMAsync(cVMessage, this.mAllShareRespHandler);
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public void controlCaption(Caption.CaptionOperation captionOperation, Caption caption) {
        if (!ServiceConnectionChecker.isAllShareServiceConnected(this.mAllShareConnector)) {
            DLog.w_api(TAG_CLASS, "controlCaption : SERVICE_NOT_CONNECTED");
            AVPlayer.IAVPlayerExtensionResponseListener iAVPlayerExtensionResponseListener = this.mAVPlayerExtensionResponseListener;
            if (iAVPlayerExtensionResponseListener != null) {
                iAVPlayerExtensionResponseListener.onControlCaptionResponseReceived(ERROR.SERVICE_NOT_CONNECTED);
                return;
            }
            return;
        }
        if (captionOperation == null) {
            DLog.w_api(TAG_CLASS, "controlCaption: CaptionOperation is null, set Disable");
            captionOperation = Caption.CaptionOperation.DISABLE;
        }
        if (caption == null) {
            DLog.w_api(TAG_CLASS, "controlCaption: Caption is null, create empty caption");
            caption = new Caption();
        }
        DLog.i_api(TAG_CLASS, "controlCaption to [operation]" + captionOperation.enumToString() + " [caption]" + caption.toString());
        String join = TextUtils.join(",", caption.getLanguageList());
        Bundle bundle = new Bundle();
        bundle.putString("BUNDLE_STRING_ID", getID());
        bundle.putString(AllShareKey.BUNDLE_STRING_CAPTION_OPERATION, captionOperation.enumToString());
        bundle.putString(AllShareKey.BUNDLE_STRING_CAPTION_NAME, caption.getName());
        bundle.putString(AllShareKey.BUNDLE_STRING_CAPTION_RES_URI, caption.getResourceUri());
        bundle.putString(AllShareKey.BUNDLE_STRING_CAPTION_URI, caption.getCaptionUri());
        bundle.putString(AllShareKey.BUNDLE_STRING_CAPTION_TYPE, caption.getCaptionType().enumToString());
        bundle.putString(AllShareKey.BUNDLE_STRING_CAPTION_LANGUAGE, join);
        bundle.putString(AllShareKey.BUNDLE_STRING_CAPTION_ENCODING, caption.getEncoding());
        CVMessage cVMessage = new CVMessage();
        cVMessage.setActionID(AllShareAction.ACTION_AV_PLAYER_CONTROL_CAPTION);
        cVMessage.setBundle(bundle);
        this.mAllShareConnector.requestCVMAsync(cVMessage, this.mAllShareRespHandler);
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public void requestCaptionState() {
        DLog.i_api(TAG_CLASS, "requestCaptionState");
        if (!ServiceConnectionChecker.isAllShareServiceConnected(this.mAllShareConnector)) {
            DLog.w_api(TAG_CLASS, "getCaptionState : SERVICE_NOT_CONNECTED");
            AVPlayer.IAVPlayerExtensionResponseListener iAVPlayerExtensionResponseListener = this.mAVPlayerExtensionResponseListener;
            if (iAVPlayerExtensionResponseListener != null) {
                iAVPlayerExtensionResponseListener.onCaptionStateResponseReceived(null, null, ERROR.SERVICE_NOT_CONNECTED);
                return;
            }
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("BUNDLE_STRING_ID", getID());
        CVMessage cVMessage = new CVMessage();
        cVMessage.setActionID(AllShareAction.ACTION_AV_PLAYER_GET_CAPTION_STATE);
        cVMessage.setBundle(bundle);
        this.mAllShareConnector.requestCVMAsync(cVMessage, this.mAllShareRespHandler);
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public boolean isSupportAspectRatio() {
        boolean z = this.mSupportSetAspectRatio && this.mSupportGetAspectRatio;
        DLog.i_api(TAG_CLASS, "isSupportAspectRatio is " + z);
        return z;
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public boolean isSupport360View() {
        boolean z = this.mSupportMove360View && this.mSupportZoom360View && this.mSupportOrigin360View;
        DLog.i_api(TAG_CLASS, "isSupport360View is " + z);
        return z;
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public boolean isSupportCaptionControl() {
        boolean z = this.mSupportControlCaption && this.mSupportGetCaptionState;
        DLog.i_api(TAG_CLASS, "isSupportCaptionControl is " + z);
        return z;
    }

    @Override // com.samsung.android.allshare.media.AVPlayer
    public String getCaptionFilePathFromURI(String str) {
        DLog.i_api(TAG_CLASS, "getCaptionFilePathFromURI");
        if (!ServiceConnectionChecker.isAllShareServiceConnected(this.mAllShareConnector)) {
            DLog.w_api(TAG_CLASS, "getCaptionFilePathFromURI : SERVICE_NOT_CONNECTED");
            return "";
        }
        return this.mAllShareConnector.getCaptionFilePathFromURI(str);
    }

    @Override // com.samsung.android.allshare.Device
    public boolean isSupportedByType(int i) {
        DeviceImpl deviceImpl = this.mDeviceImpl;
        if (deviceImpl == null) {
            return false;
        }
        return deviceImpl.isSupportedByType(i);
    }
}
