package com.samsung.android.allshare;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.samsung.android.allshare.media.AVPlayer;
import com.samsung.android.allshare.media.ContentInfo;
import com.samsung.android.allshare.media.MediaInfo;
import com.samsung.android.allshare.media.PlaylistPlayer;
import com.sec.android.allshare.iface.CVMessage;
import com.sec.android.allshare.iface.IBundleHolder;
import com.sec.android.allshare.iface.IHandlerHolder;
import com.sec.android.allshare.iface.message.AllShareAction;
import com.sec.android.allshare.iface.message.AllShareEvent;
import com.sec.android.allshare.iface.message.AllShareKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes5.dex */
final class AVPlayerImpl extends AVPlayer implements IBundleHolder, IHandlerHolder {
  private static final String TAG_CLASS = "AVPlayerImpl";
  private IAllShareConnector mAllShareConnector;
  private DeviceImpl mDeviceImpl;
  private PlaylistPlayer mPlaylistPlayer;
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
  private AllShareEventHandler mEventHandler =
      new AllShareEventHandler(
          ServiceConnector
              .getMainLooper()) { // from class: com.samsung.android.allshare.AVPlayerImpl.1
        private HashMap<String, AVPlayer.AVPlayerState> mAVStateMap;

        {
          HashMap<String, AVPlayer.AVPlayerState> hashMap = new HashMap<>();
          this.mAVStateMap = hashMap;
          hashMap.put(
              AllShareEvent.EVENT_RENDERER_STATE_BUFFERING, AVPlayer.AVPlayerState.BUFFERING);
          this.mAVStateMap.put(
              AllShareEvent.EVENT_RENDERER_STATE_PAUSED, AVPlayer.AVPlayerState.PAUSED);
          this.mAVStateMap.put(
              AllShareEvent.EVENT_RENDERER_STATE_STOPPED, AVPlayer.AVPlayerState.STOPPED);
          this.mAVStateMap.put(
              AllShareEvent.EVENT_RENDERER_STATE_PLAYING, AVPlayer.AVPlayerState.PLAYING);
          this.mAVStateMap.put(
              AllShareEvent.EVENT_RENDERER_STATE_FINISHED, AVPlayer.AVPlayerState.FINISHED);
          this.mAVStateMap.put(
              AllShareEvent.EVENT_RENDERER_STATE_NOMEDIA, AVPlayer.AVPlayerState.STOPPED);
          this.mAVStateMap.put(
              AllShareEvent.EVENT_RENDERER_STATE_CONTENT_CHANGED,
              AVPlayer.AVPlayerState.CONTENT_CHANGED);
        }

        @Override // com.samsung.android.allshare.AllShareEventHandler
        public void handleEventMessage(CVMessage cvm) {
          try {
            Bundle resBundle = cvm.getBundle();
            String errorStr = resBundle.getString("BUNDLE_ENUM_ERROR");
            ERROR error = ERROR.stringToEnum(errorStr);
            String actionId = cvm.getActionID();
            AVPlayer.AVPlayerState state = this.mAVStateMap.get(actionId);
            if (state == null) {
              String eventValue =
                  resBundle.getString(AllShareKey.BUNDLE_STRING_EXTENSION_EVENT_KEY);
              if (actionId != null && eventValue != null) {
                notifyExtensionEvent(actionId, eventValue, error);
              }
              return;
            }
            if (state.equals(AVPlayer.AVPlayerState.CONTENT_CHANGED)) {
              String currentTrackUri = resBundle.getString(AllShareKey.BUNDLE_STRING_APP_ITEM_ID);
              if (currentTrackUri != null && !currentTrackUri.isEmpty()) {
                if (AVPlayerImpl.this.mContentChangedNotified) {
                  DLog.d_api(AVPlayerImpl.TAG_CLASS, "do not notify CONTENT_CHANGED event yet");
                  AVPlayerImpl.this.mCurrentDMRUri = currentTrackUri;
                  return;
                }
                if (currentTrackUri.equalsIgnoreCase(AVPlayerImpl.this.mCurrentDMRUri)) {
                  DLog.d_api(
                      AVPlayerImpl.TAG_CLASS,
                      "do not notify CONTENT_CHANGED event, mCurrentDMRUri is same as"
                          + " currentTrackUri "
                          + currentTrackUri);
                  return;
                }
                DLog.d_api(
                    AVPlayerImpl.TAG_CLASS,
                    "CONTENT_CHANGED, mCurrentDMRUri : "
                        + AVPlayerImpl.this.mCurrentDMRUri
                        + "  currentTrackUri : "
                        + currentTrackUri);
                if (AVPlayerImpl.this.mCurrentDMRUri == null) {
                  DLog.d_api(
                      AVPlayerImpl.TAG_CLASS,
                      "do not notify CONTENT_CHANGED event, mCurrentDMRUri is null");
                  AVPlayerImpl.this.mCurrentDMRUri = currentTrackUri;
                  return;
                }
                AVPlayerImpl.this.mCurrentDMRUri = currentTrackUri;
                if (AVPlayerImpl.this.mPlayingContentUris != null
                    && !AVPlayerImpl.this.mPlayingContentUris.isEmpty()) {
                  if (isContains(currentTrackUri, AVPlayerImpl.this.mPlayingContentUris)) {
                    DLog.d_api(
                        AVPlayerImpl.TAG_CLASS, "handleEventMessage: this is playing content.");
                    DLog.i_api(
                        AVPlayerImpl.TAG_CLASS,
                        "do not notify CONTENT_CHANGED event, this is my=" + currentTrackUri);
                    return;
                  } else {
                    AVPlayerImpl.this.mContentChangedNotified = true;
                    DLog.w_api(
                        AVPlayerImpl.TAG_CLASS,
                        "Notify CONTENT_CHANGED event, mPlayingContentUris["
                            + AVPlayerImpl.this.mPlayingContentUris
                            + "] vs currentTrackUri["
                            + currentTrackUri
                            + NavigationBarInflaterView.SIZE_MOD_END);
                  }
                }
                DLog.d_api(
                    AVPlayerImpl.TAG_CLASS,
                    "do not notify CONTENT_CHANGED event, mPlayingContentUris is null");
                return;
              }
              DLog.d_api(
                  AVPlayerImpl.TAG_CLASS,
                  "do not notify CONTENT_CHANGED event, currentTrackUri is null");
              return;
            }
            notifyEvent(state, error);
          } catch (Error err) {
            DLog.w_api(AVPlayerImpl.TAG_CLASS, "handleEventMessage Error", err);
          } catch (Exception e) {
            DLog.w_api(
                AVPlayerImpl.TAG_CLASS, "handleEventMessage Fail to notify event : Exception");
          }
        }

        private void notifyEvent(AVPlayer.AVPlayerState state, ERROR error) {
          if (AVPlayerImpl.this.mAVPlayerEventListener != null) {
            try {
              AVPlayerImpl.this.mAVPlayerEventListener.onDeviceChanged(state, error);
            } catch (Exception e) {
              DLog.w_api(AVPlayerImpl.TAG_CLASS, "mEventHandler.notifyEvent Error", e);
            }
          }
        }

        private void notifyExtensionEvent(String eventName, String eventValue, ERROR error) {
          if (AVPlayerImpl.this.mAVPlayerExtensionEventListener != null) {
            try {
              AVPlayerImpl.this.mAVPlayerExtensionEventListener.onExtensionEvent(
                  eventName, eventValue, error);
            } catch (Exception e) {
              DLog.w_api(AVPlayerImpl.TAG_CLASS, "mEventExtensionHandler.notifyEvent Error", e);
            }
          }
        }

        private boolean isContains(String currentTrackUri, ArrayList<String> playingContentUris) {
          if (playingContentUris == null || currentTrackUri == null) {
            return false;
          }
          Iterator<String> it = playingContentUris.iterator();
          while (it.hasNext()) {
            String uri = it.next();
            if (currentTrackUri.endsWith(uri)) {
              return true;
            }
          }
          return false;
        }
      };
  private AllShareResponseHandler mAllShareRespHandler =
      new AllShareResponseHandler(
          ServiceConnector
              .getMainLooper()) { // from class: com.samsung.android.allshare.AVPlayerImpl.2
        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Code restructure failed: missing block: B:151:0x022c, code lost:

           if (r1.equals(com.sec.android.allshare.iface.message.AllShareAction.ACTION_AV_PLAYER_ORIGIN_360_VIEW) != false) goto L131;
        */
        /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
        @Override // com.samsung.android.allshare.AllShareResponseHandler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void handleResponseMessage(CVMessage cvm) {
          String actionID = cvm.getActionID();
          Bundle resBundle = cvm.getBundle();
          if (actionID == null || resBundle == null) {
            return;
          }
          ERROR error = ERROR.stringToEnum(resBundle.getString("BUNDLE_ENUM_ERROR"));
          if (actionID.equals(AllShareAction.ACTION_AV_PLAYER_PLAY_LOCAL_CONTENS_URI)
              || actionID.equals(
                  AllShareAction.ACTION_AV_PLAYER_PLAY_LOCAL_CONTENS_FILEPATH_WITH_METADATA)
              || actionID.equals(AllShareAction.ACTION_AV_PLAYER_PLAY_LOCAL_CONTENS_FILEPATH)
              || actionID.equals(AllShareAction.ACTION_AV_PLAYER_PLAY_URI)
              || actionID.equals(AllShareAction.ACTION_AV_PLAYER_PLAY)) {
            if (error.equals(ERROR.SUCCESS)) {
              AVPlayerImpl.this.mContentChangedNotified = false;
            } else {
              AVPlayerImpl.this.mPlayingContentUris.clear();
            }
          }
          char c = 4;
          if (AVPlayerImpl.this.mAVPlaybackResponseListener != null) {
            switch (actionID) {
              case "com.sec.android.allshare.action.ACTION_AV_PLAYER_PLAY":
              case "com.sec.android.allshare.action.ACTION_AV_PLAYER_PLAY_LOCAL_CONTENS_URI":
              case "com.sec.android.allshare.action.ACTION_AV_PLAYER_PLAY_LOCAL_CONTENS_FILEPATH_WITH_METADATA":
              case "com.sec.android.allshare.action.ACTION_AV_PLAYER_PLAY_LOCAL_CONTENS_FILEPATH":
              case "com.sec.android.allshare.action.ACTION_AV_PLAYER_PLAY_WEB_CONTENTS":
              case "com.sec.android.allshare.action.ACTION_AV_PLAYER_PLAY_URI":
                notifyPlaybackEvent(resBundle, error);
                break;
              case "com.sec.android.allshare.action.ACTION_AV_PLAYER_STOP":
                AVPlayerImpl.this.mAVPlaybackResponseListener.onStopResponseReceived(error);
                break;
              case "com.sec.android.allshare.action.ACTION_AV_PLAYER_SEEK":
                long position = resBundle.getLong(AllShareKey.BUNDLE_LONG_POSITION);
                AVPlayerImpl.this.mAVPlaybackResponseListener.onSeekResponseReceived(
                    position, error);
                break;
              case "com.sec.android.allshare.action.ACTION_AV_PLAYER_PAUSE":
                AVPlayerImpl.this.mAVPlaybackResponseListener.onPauseResponseReceived(error);
                break;
              case "com.sec.android.allshare.action.ACTION_AV_PLAYER_RESUME":
                AVPlayerImpl.this.mAVPlaybackResponseListener.onResumeResponseReceived(error);
                break;
              case "com.sec.android.allshare.action.ACTION_AV_PLAYER_REQUEST_PLAY_POSITION":
                long position2 = resBundle.getLong(AllShareKey.BUNDLE_LONG_POSITION);
                AVPlayerImpl.this.mAVPlaybackResponseListener.onGetPlayPositionResponseReceived(
                    position2, error);
                break;
              case "com.sec.android.allshare.action.ACTION_AV_PLAYER_REQUEST_GET_MEDIA_INFO":
                MediaInfo mediaInfo = null;
                if (ERROR.SUCCESS.equals(error)) {
                  mediaInfo = new MediaInfoImpl(resBundle);
                }
                AVPlayerImpl.this.mAVPlaybackResponseListener.onGetMediaInfoResponseReceived(
                    mediaInfo, error);
                break;
              case "com.sec.android.allshare.action.ACTION_AV_PLAYER_REQUEST_GET_PLAYER_STATE":
                String state = resBundle.getString(AllShareKey.BUNDLE_STRING_AV_PLAER_STATE);
                AVPlayer.AVPlayerState playerState = AVPlayer.AVPlayerState.stringToEnum(state);
                AVPlayerImpl.this.mAVPlaybackResponseListener.onGetStateResponseReceived(
                    playerState, error);
                break;
            }
          }
          if (AVPlayerImpl.this.mAVPlayerVolumeResponseListener != null) {
            switch (actionID) {
              case "com.sec.android.allshare.action.ACTION_AV_PLAYER_REQUEST_GET_VOLUME":
                int level = resBundle.getInt(AllShareKey.BUNDLE_INT_VOLUME);
                AVPlayerImpl.this.mAVPlayerVolumeResponseListener.onGetVolumeResponseReceived(
                    level, error);
                break;
              case "com.sec.android.allshare.action.ACTION_AV_PLAYER_REQUEST_SET_VOLUME":
                int level2 = resBundle.getInt(AllShareKey.BUNDLE_INT_VOLUME);
                AVPlayerImpl.this.mAVPlayerVolumeResponseListener.onSetVolumeResponseReceived(
                    level2, error);
                break;
              case "com.sec.android.allshare.action.ACTION_AV_PLAYER_REQUEST_GET_MUTE":
                boolean onoff = resBundle.getBoolean(AllShareKey.BUNDLE_BOOLEAN_MUTE);
                AVPlayerImpl.this.mAVPlayerVolumeResponseListener.onGetMuteResponseReceived(
                    onoff, error);
                break;
              case "com.sec.android.allshare.action.ACTION_AV_PLAYER_REQUEST_SET_MUTE":
                boolean onoff2 = resBundle.getBoolean(AllShareKey.BUNDLE_BOOLEAN_MUTE);
                AVPlayerImpl.this.mAVPlayerVolumeResponseListener.onSetMuteResponseReceived(
                    onoff2, error);
                break;
            }
          }
          if (AVPlayerImpl.this.mAVPlayerExtensionResponseListener != null) {
            switch (actionID.hashCode()) {
              case -2046630951:
                if (actionID.equals(AllShareAction.ACTION_AV_PLAYER_MOVE_360_VIEW)) {
                  c = 2;
                  break;
                }
                c = 65535;
                break;
              case -1826863324:
                break;
              case -1717516936:
                if (actionID.equals(AllShareAction.ACTION_AV_PLAYER_CONTROL_CAPTION)) {
                  c = 5;
                  break;
                }
                c = 65535;
                break;
              case -1031151239:
                if (actionID.equals(AllShareAction.ACTION_AV_PLAYER_GET_ASPECT_RATIO)) {
                  c = 1;
                  break;
                }
                c = 65535;
                break;
              case 850714135:
                if (actionID.equals(AllShareAction.ACTION_AV_PLAYER_ZOOM_360_VIEW)) {
                  c = 3;
                  break;
                }
                c = 65535;
                break;
              case 933211939:
                if (actionID.equals(AllShareAction.ACTION_AV_PLAYER_GET_CAPTION_STATE)) {
                  c = 6;
                  break;
                }
                c = 65535;
                break;
              case 1432373229:
                if (actionID.equals(AllShareAction.ACTION_AV_PLAYER_SET_ASPECT_RATIO)) {
                  c = 0;
                  break;
                }
                c = 65535;
                break;
              default:
                c = 65535;
                break;
            }
            switch (c) {
              case 0:
                AVPlayerImpl.this.mAVPlayerExtensionResponseListener
                    .onSetAspectRatioResponseReceived(error);
                break;
              case 1:
                String aspectRatio = resBundle.getString(AllShareKey.BUNDLE_STRING_ASPECT_RATIO);
                DLog.i_api(
                    AVPlayerImpl.TAG_CLASS, "ACTION_AV_PLAYER_GET_ASPECT_RATIO : " + aspectRatio);
                AVPlayerImpl.this.mAVPlayerExtensionResponseListener
                    .onAspectRatioStateResponseReceived(aspectRatio, error);
                break;
              case 2:
                AVPlayerImpl.this.mAVPlayerExtensionResponseListener.onMove360ViewResponseReceived(
                    error);
                break;
              case 3:
                AVPlayerImpl.this.mAVPlayerExtensionResponseListener.onZoom360ViewResponseReceived(
                    error);
                break;
              case 4:
                AVPlayerImpl.this.mAVPlayerExtensionResponseListener.onReset360ViewResponseReceived(
                    error);
                break;
              case 5:
                AVPlayerImpl.this.mAVPlayerExtensionResponseListener
                    .onControlCaptionResponseReceived(error);
                break;
              case 6:
                String captions = resBundle.getString(AllShareKey.BUNDLE_STRING_CAPTION_CAPTIONS);
                String enabledCaptions =
                    resBundle.getString(AllShareKey.BUNDLE_STRING_CAPTION_ENABLEDCAPTIONS);
                List<Caption> tempCaptionsList = Caption.parseCaption(captions);
                List<Caption> tempEnabledCaptionsList = Caption.parseCaption(enabledCaptions);
                List<Caption> captionsList = new ArrayList<>();
                List<Caption> enabledCaptionsList = new ArrayList<>();
                if (tempCaptionsList != null) {
                  for (Caption caption : tempCaptionsList) {
                    String captionUri =
                        AVPlayerImpl.this.getCaptionFilePathFromURI(caption.getCaptionUri());
                    if (captionUri != null && !captionUri.isEmpty()) {
                      caption.setCaptionUri(captionUri);
                    }
                    captionsList.add(caption);
                    DLog.i_api(
                        AVPlayerImpl.TAG_CLASS,
                        "ACTION_AV_PLAYER_GET_CAPTION_STATE : [available caption]"
                            + caption.toString());
                  }
                }
                if (tempEnabledCaptionsList != null) {
                  for (Caption caption2 : tempEnabledCaptionsList) {
                    String captionUri2 =
                        AVPlayerImpl.this.getCaptionFilePathFromURI(caption2.getCaptionUri());
                    if (captionUri2 != null && !captionUri2.isEmpty()) {
                      caption2.setCaptionUri(captionUri2);
                    }
                    enabledCaptionsList.add(caption2);
                    DLog.i_api(
                        AVPlayerImpl.TAG_CLASS,
                        "ACTION_AV_PLAYER_GET_CAPTION_STATE : [enabled caption]"
                            + caption2.toString());
                  }
                }
                AVPlayerImpl.this.mAVPlayerExtensionResponseListener.onCaptionStateResponseReceived(
                    captionsList, enabledCaptionsList, error);
                break;
            }
          }
          if (AVPlayerImpl.this.mAVPlayerWHAResponseListener != null) {
            switch (actionID) {
              case "com.sec.android.allshare.action.ACTION_WHA_GET_DEVICE_STATUS_INFO":
                DLog.i_api(AVPlayerImpl.TAG_CLASS, "ACTION_WHA_GET_DEVICE_STATUS_INFO");
                WHADeviceInfo whaDeviceInfo = null;
                if (ERROR.SUCCESS.equals(error)) {
                  whaDeviceInfo = new WHADeviceInfo(resBundle);
                }
                AVPlayerImpl.this.mAVPlayerWHAResponseListener.onGetWHADeviceStatusInfo(
                    whaDeviceInfo, error);
                break;
              case "com.sec.android.allshare.action.ACTION_WHA_CREATE_PARTY":
                String partyId = resBundle.getString(AllShareKey.BUNDLE_STRING_WHA_PARTY_ID);
                DLog.i_api(AVPlayerImpl.TAG_CLASS, "ACTION_WHA_CREATE_PARTY : " + partyId);
                AVPlayerImpl.this.mAVPlayerWHAResponseListener.onCreateWHAParty(partyId, error);
                break;
              case "com.sec.android.allshare.action.ACTION_WHA_JOIN_PARTY":
                DLog.i_api(AVPlayerImpl.TAG_CLASS, "ACTION_WHA_JOIN_PARTY");
                AVPlayerImpl.this.mAVPlayerWHAResponseListener.onJoinWHAParty(error);
                break;
              case "com.sec.android.allshare.action.ACTION_WHA_LEAVE_PARTY":
                DLog.i_api(AVPlayerImpl.TAG_CLASS, "ACTION_WHA_LEAVE_PARTY");
                AVPlayerImpl.this.mAVPlayerWHAResponseListener.onLeaveWHAParty(error);
                break;
            }
          }
        }

        private void notifyPlaybackEvent(Bundle resBundle, ERROR error) {
          Bundle bundle = (Bundle) resBundle.getParcelable(AllShareKey.BUNDLE_PARCELABLE_ITEM);
          long contentInfoStartingPosition =
              resBundle.getLong(AllShareKey.BUNDLE_LONG_CONTENT_INFO_STARTINGPOSITION);
          ContentInfo.Builder cb = new ContentInfo.Builder();
          ContentInfo contentInfo = cb.setStartingPosition(contentInfoStartingPosition).build();
          Item item = ItemCreator.fromBundle(bundle);
          String itemConstructor = bundle.getString(AllShareKey.BUNDLE_STRING_ITEM_CONSTRUCTOR_KEY);
          if (itemConstructor != null
              && itemConstructor.equals("WEB_CONTENT")
              && contentInfo != null) {
            ContentInfo.Builder builder = new ContentInfo.Builder();
            builder.setStartingPosition((int) (contentInfo.getStartingPosition() / 1000));
            contentInfo = builder.build();
          }
          if (item == null) {
            DLog.w_api(AVPlayerImpl.TAG_CLASS, "notifyPlaybackEvent : item is null");
            AVPlayerImpl.this.mAVPlaybackResponseListener.onPlayResponseReceived(
                item, contentInfo, ERROR.ITEM_NOT_EXIST);
          } else {
            if (contentInfo == null) {
              DLog.d_api(AVPlayerImpl.TAG_CLASS, "notifyPlaybackEvent : " + item + " = " + error);
            } else {
              DLog.d_api(
                  AVPlayerImpl.TAG_CLASS,
                  "notifyPlaybackEvent : "
                      + item
                      + " position["
                      + contentInfo.getStartingPosition()
                      + "]="
                      + error);
            }
            AVPlayerImpl.this.mAVPlaybackResponseListener.onPlayResponseReceived(
                item, contentInfo, error);
          }
        }
      };
  private AVPlayer.IAVPlayerWHAResponseListener mAVPlayerWHAResponseListener = null;

  AVPlayerImpl(IAllShareConnector connector, DeviceImpl deviceImpl) {
    this.mDeviceImpl = null;
    this.mPlaylistPlayer = null;
    this.mSupportSetAspectRatio = false;
    this.mSupportGetAspectRatio = false;
    this.mSupportMove360View = false;
    this.mSupportZoom360View = false;
    this.mSupportOrigin360View = false;
    this.mSupportControlCaption = false;
    this.mSupportGetCaptionState = false;
    if (connector == null) {
      DLog.w_api(TAG_CLASS, "Connection FAIL: AllShare Service Connector does not exist");
      return;
    }
    if (deviceImpl == null) {
      DLog.w_api(TAG_CLASS, "deviceImpl is null");
      return;
    }
    this.mAllShareConnector = connector;
    this.mDeviceImpl = deviceImpl;
    Bundle bundle = deviceImpl.getBundle();
    if (bundle == null) {
      DLog.w_api(TAG_CLASS, "deviceImpl.getBundle is null");
      return;
    }
    Boolean isSupportAudioPlaylist =
        Boolean.valueOf(
            bundle.getBoolean(AllShareKey.BUNDLE_BOOLEAN_SUPPORT_AUDIO_PLAYLIST_PLAYER));
    Boolean isSupportVideoPlaylist =
        Boolean.valueOf(
            bundle.getBoolean(AllShareKey.BUNDLE_BOOLEAN_SUPPORT_VIDEO_PLAYLIST_PLAYER));
    this.mSupportSetAspectRatio =
        bundle.getBoolean(AllShareKey.BUNDLE_BOOLEAN_SUPPORT_SET_ASPECT_RATIO);
    this.mSupportGetAspectRatio =
        bundle.getBoolean(AllShareKey.BUNDLE_BOOLEAN_SUPPORT_GET_ASPECT_RATIO);
    this.mSupportMove360View = bundle.getBoolean(AllShareKey.BUNDLE_BOOLEAN_SUPPORT_MOVE_360_VIEW);
    this.mSupportZoom360View = bundle.getBoolean(AllShareKey.BUNDLE_BOOLEAN_SUPPORT_ZOOM_360_VIEW);
    this.mSupportOrigin360View =
        bundle.getBoolean(AllShareKey.BUNDLE_BOOLEAN_SUPPORT_ORIGIN_360_VIEW);
    this.mSupportControlCaption =
        bundle.getBoolean(AllShareKey.BUNDLE_BOOLEAN_SUPPORT_CONTROL_CAPTION);
    this.mSupportGetCaptionState =
        bundle.getBoolean(AllShareKey.BUNDLE_BOOLEAN_SUPPORT_GET_CAPTION_STATE);
    if (isSupportAudioPlaylist.booleanValue() || isSupportVideoPlaylist.booleanValue()) {
      this.mPlaylistPlayer = new PlaylistPlayerImpl(connector, deviceImpl);
    } else {
      this.mPlaylistPlayer = null;
    }
  }

  /* JADX WARN: Multi-variable type inference failed */
  @Override // com.samsung.android.allshare.media.AVPlayer
  public void play(Item item, ContentInfo contentInfo) {
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      DLog.w_api(TAG_CLASS, "play fail : SERVICE_NOT_CONNECTED");
      AVPlayer.IAVPlayerPlaybackResponseListener iAVPlayerPlaybackResponseListener =
          this.mAVPlaybackResponseListener;
      if (iAVPlayerPlaybackResponseListener != null) {
        iAVPlayerPlaybackResponseListener.onPlayResponseReceived(
            item, contentInfo, ERROR.SERVICE_NOT_CONNECTED);
        return;
      }
      return;
    }
    if (item == 0) {
      DLog.w_api(TAG_CLASS, "play item == null");
      AVPlayer.IAVPlayerPlaybackResponseListener iAVPlayerPlaybackResponseListener2 =
          this.mAVPlaybackResponseListener;
      if (iAVPlayerPlaybackResponseListener2 != null) {
        iAVPlayerPlaybackResponseListener2.onPlayResponseReceived(
            item, contentInfo, ERROR.INVALID_ARGUMENT);
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
    String mimeType = bundle.getString(AllShareKey.BUNDLE_STRING_ITEM_MIMETYPE);
    String constructorKey = bundle.getString(AllShareKey.BUNDLE_STRING_ITEM_CONSTRUCTOR_KEY);
    DLog.i_api(TAG_CLASS, "Playing Content URI : " + item.getURI().toString());
    if (constructorKey == null) {
      DLog.w_api(TAG_CLASS, "constructorKey == null");
      return;
    }
    if (constructorKey.equals("WEB_CONTENT")) {
      DLog.i_api(
          TAG_CLASS,
          "play WEB_CONTENT - "
              + item.getTitle()
              + " to "
              + getName()
              + " [ "
              + item.getURI()
              + " ] ");
      if (item.getURI() == null) {
        DLog.w_api(TAG_CLASS, "uri == null");
        AVPlayer.IAVPlayerPlaybackResponseListener iAVPlayerPlaybackResponseListener3 =
            this.mAVPlaybackResponseListener;
        if (iAVPlayerPlaybackResponseListener3 != null) {
          iAVPlayerPlaybackResponseListener3.onPlayResponseReceived(
              item, contentInfo, ERROR.INVALID_ARGUMENT);
          return;
        }
        return;
      }
      if (contentInfo != null) {
        DLog.i_api(TAG_CLASS, "play position - " + contentInfo.getStartingPosition());
      }
      this.mPlayingContentUris.add(item.getURI().toString());
      playWebContent(item.getURI(), item, contentInfo, mimeType);
      return;
    }
    if (constructorKey.equals("MEDIA_SERVER")) {
      DLog.i_api(TAG_CLASS, "play MEDIA_SERVER - " + item.getTitle() + " to " + getName());
      if (contentInfo != null) {
        DLog.i_api(TAG_CLASS, "play position - " + contentInfo.getStartingPosition());
      }
      this.mPlayingContentUris.add(item.getURI().toString());
      playMediaContent(item, contentInfo);
      return;
    }
    if (constructorKey.equals("LOCAL_CONTENT")) {
      Uri uri = item.getURI();
      if (uri == null) {
        DLog.w_api(TAG_CLASS, "play LOCAL_CONTENT : uri == null");
        this.mAVPlaybackResponseListener.onPlayResponseReceived(
            item, contentInfo, ERROR.INVALID_ARGUMENT);
        return;
      }
      String scheme = uri.getScheme();
      if (scheme != null && scheme.contains("file")) {
        String filePath = bundle.getString(AllShareKey.BUNDLE_STRING_FILEPATH);
        DLog.i_api(
            TAG_CLASS,
            "play LOCAL_CONTENT file - "
                + item.getTitle()
                + " to "
                + getName()
                + "[ "
                + filePath
                + " ] ");
        if (filePath == null) {
          DLog.w_api(TAG_CLASS, "play LOCAL_CONTENT : uri == null");
          this.mAVPlaybackResponseListener.onPlayResponseReceived(
              item, contentInfo, ERROR.INVALID_ARGUMENT);
          return;
        } else if (!Item.LocalContentBuilder.checkFilePathValid(filePath)) {
          DLog.w_api(TAG_CLASS, "play  LOCAL_CONTENT: filePath is not valid");
          this.mAVPlaybackResponseListener.onPlayResponseReceived(
              item, contentInfo, ERROR.INVALID_ARGUMENT);
          return;
        } else {
          if (contentInfo != null) {
            DLog.i_api(TAG_CLASS, "play position - " + contentInfo.getStartingPosition());
          }
          this.mPlayingContentUris.add(filePath);
          playLocalContent(filePath, item, contentInfo, mimeType);
          return;
        }
      }
      if (scheme != null && scheme.contains("content")) {
        DLog.i_api(
            TAG_CLASS,
            "play LOCAL_CONTENT content - "
                + item.getTitle()
                + " to "
                + getName()
                + "[ "
                + uri
                + " ] ");
        if (contentInfo != null) {
          DLog.i_api(TAG_CLASS, "play position - " + contentInfo.getStartingPosition());
        }
        this.mPlayingContentUris.add(parseUriFilePath(item.getURI()));
        playLocalContent(uri, item, contentInfo, mimeType);
        return;
      }
      AVPlayer.IAVPlayerPlaybackResponseListener iAVPlayerPlaybackResponseListener4 =
          this.mAVPlaybackResponseListener;
      if (iAVPlayerPlaybackResponseListener4 != null) {
        iAVPlayerPlaybackResponseListener4.onPlayResponseReceived(
            item, contentInfo, ERROR.INVALID_ARGUMENT);
      }
    }
  }

  private String parseUriFilePath(Uri uri) {
    Context scContext;
    ContentResolver cr;
    Cursor cursor;
    if (uri != null
        && (scContext = ServiceConnector.getContext()) != null
        && (cr = scContext.getContentResolver()) != null
        && (cursor = cr.query(uri, null, null, null, null)) != null) {
      if (cursor.moveToFirst()) {
        String str = cursor.getString(1);
        cursor.close();
        return str;
      }
      cursor.close();
    }
    return null;
  }

  /* JADX WARN: Multi-variable type inference failed */
  @Override // com.samsung.android.allshare.media.AVPlayer
  public void prepare(Item item) {
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      DLog.w_api(TAG_CLASS, "prepare : SERVICE_NOT_CONNECTED");
      return;
    }
    if (item == 0) {
      DLog.w_api(TAG_CLASS, "prepare Fail :  Item does not exist ");
      return;
    }
    CVMessage cvm = new CVMessage();
    cvm.setActionID(AllShareAction.ACTION_AV_PLAYER_PREPARE);
    Bundle bundle = new Bundle();
    bundle.putString("BUNDLE_STRING_ID", getID());
    if (item instanceof IBundleHolder) {
      bundle.putParcelable(AllShareKey.BUNDLE_PARCELABLE_ITEM, ((IBundleHolder) item).getBundle());
    }
    cvm.setBundle(bundle);
    this.mAllShareConnector.requestCVMAsync(cvm, this.mAllShareRespHandler);
    DLog.i_api(
        TAG_CLASS, "prepare : " + item.getTitle() + " to " + getName() + "uri : " + item.getURI());
  }

  @Override // com.samsung.android.allshare.media.AVPlayer
  public void stop() {
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      DLog.w_api(TAG_CLASS, "stop fail : SERVICE_NOT_CONNECTED");
      AVPlayer.IAVPlayerPlaybackResponseListener iAVPlayerPlaybackResponseListener =
          this.mAVPlaybackResponseListener;
      if (iAVPlayerPlaybackResponseListener != null) {
        iAVPlayerPlaybackResponseListener.onStopResponseReceived(ERROR.SERVICE_NOT_CONNECTED);
        return;
      }
      return;
    }
    DLog.i_api(TAG_CLASS, "stop : " + getName());
    CVMessage req_msg = new CVMessage();
    req_msg.setActionID(AllShareAction.ACTION_AV_PLAYER_STOP);
    Bundle req_bundle = new Bundle();
    req_bundle.putString("BUNDLE_STRING_ID", getID());
    req_msg.setBundle(req_bundle);
    this.mAllShareConnector.requestCVMAsync(req_msg, this.mAllShareRespHandler);
  }

  @Override // com.samsung.android.allshare.media.AVPlayer
  public void seek(long p) {
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      DLog.w_api(TAG_CLASS, "seek fail : SERVICE_NOT_CONNECTED");
      AVPlayer.IAVPlayerPlaybackResponseListener iAVPlayerPlaybackResponseListener =
          this.mAVPlaybackResponseListener;
      if (iAVPlayerPlaybackResponseListener != null) {
        iAVPlayerPlaybackResponseListener.onSeekResponseReceived(p, ERROR.SERVICE_NOT_CONNECTED);
        return;
      }
      return;
    }
    DLog.i_api(TAG_CLASS, "seek pos :" + p + " " + getName());
    CVMessage req_msg = new CVMessage();
    req_msg.setActionID(AllShareAction.ACTION_AV_PLAYER_SEEK);
    Bundle req_bundle = new Bundle();
    req_bundle.putString("BUNDLE_STRING_ID", getID());
    long miliTime = 1000 * p;
    req_bundle.putLong(AllShareKey.BUNDLE_LONG_POSITION, miliTime);
    req_msg.setBundle(req_bundle);
    this.mAllShareConnector.requestCVMAsync(req_msg, this.mAllShareRespHandler);
  }

  @Override // com.samsung.android.allshare.media.AVPlayer
  public void pause() {
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      DLog.w_api(TAG_CLASS, "pause fail : SERVICE_NOT_CONNECTED");
      AVPlayer.IAVPlayerPlaybackResponseListener iAVPlayerPlaybackResponseListener =
          this.mAVPlaybackResponseListener;
      if (iAVPlayerPlaybackResponseListener != null) {
        iAVPlayerPlaybackResponseListener.onPauseResponseReceived(ERROR.SERVICE_NOT_CONNECTED);
        return;
      }
      return;
    }
    DLog.i_api(TAG_CLASS, "pause " + getName());
    CVMessage req_msg = new CVMessage();
    req_msg.setActionID(AllShareAction.ACTION_AV_PLAYER_PAUSE);
    Bundle req_bundle = new Bundle();
    req_bundle.putString("BUNDLE_STRING_ID", getID());
    req_msg.setBundle(req_bundle);
    this.mAllShareConnector.requestCVMAsync(req_msg, this.mAllShareRespHandler);
  }

  @Override // com.samsung.android.allshare.media.AVPlayer
  public void resume() {
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      DLog.w_api(TAG_CLASS, "resume fail : SERVICE_NOT_CONNECTED");
      AVPlayer.IAVPlayerPlaybackResponseListener iAVPlayerPlaybackResponseListener =
          this.mAVPlaybackResponseListener;
      if (iAVPlayerPlaybackResponseListener != null) {
        iAVPlayerPlaybackResponseListener.onResumeResponseReceived(ERROR.SERVICE_NOT_CONNECTED);
        return;
      }
      return;
    }
    DLog.i_api(TAG_CLASS, "resume " + getName());
    CVMessage req_msg = new CVMessage();
    req_msg.setActionID(AllShareAction.ACTION_AV_PLAYER_RESUME);
    Bundle req_bundle = new Bundle();
    req_bundle.putString("BUNDLE_STRING_ID", getID());
    req_msg.setBundle(req_bundle);
    this.mAllShareConnector.requestCVMAsync(req_msg, this.mAllShareRespHandler);
  }

  @Override // com.samsung.android.allshare.media.AVPlayer
  public void getVolume() {
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      AVPlayer.IAVPlayerVolumeResponseListener iAVPlayerVolumeResponseListener =
          this.mAVPlayerVolumeResponseListener;
      if (iAVPlayerVolumeResponseListener != null) {
        iAVPlayerVolumeResponseListener.onGetVolumeResponseReceived(
            -1, ERROR.SERVICE_NOT_CONNECTED);
        return;
      }
      return;
    }
    CVMessage req_msg = new CVMessage();
    req_msg.setActionID(AllShareAction.ACTION_AV_PLAYER_REQUEST_GET_VOLUME);
    Bundle req_bundle = new Bundle();
    req_bundle.putString("BUNDLE_STRING_ID", getID());
    req_msg.setBundle(req_bundle);
    this.mAllShareConnector.requestCVMAsync(req_msg, this.mAllShareRespHandler);
  }

  @Override // com.samsung.android.allshare.media.AVPlayer
  public void setVolume(int level) {
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      DLog.w_api(TAG_CLASS, "setVolume fail : SERVICE_NOT_CONNECTED");
      AVPlayer.IAVPlayerVolumeResponseListener iAVPlayerVolumeResponseListener =
          this.mAVPlayerVolumeResponseListener;
      if (iAVPlayerVolumeResponseListener != null) {
        iAVPlayerVolumeResponseListener.onSetVolumeResponseReceived(
            level, ERROR.SERVICE_NOT_CONNECTED);
        return;
      }
      return;
    }
    if (level < 0 || level > 100) {
      DLog.w_api(TAG_CLASS, "setVolume fail : level (INVALID_ARGUMENT)");
      AVPlayer.IAVPlayerVolumeResponseListener iAVPlayerVolumeResponseListener2 =
          this.mAVPlayerVolumeResponseListener;
      if (iAVPlayerVolumeResponseListener2 != null) {
        iAVPlayerVolumeResponseListener2.onSetVolumeResponseReceived(level, ERROR.INVALID_ARGUMENT);
        return;
      }
      return;
    }
    DLog.i_api(TAG_CLASS, "setVolume -level : " + level + " " + getName());
    CVMessage req_msg = new CVMessage();
    req_msg.setActionID(AllShareAction.ACTION_AV_PLAYER_REQUEST_SET_VOLUME);
    Bundle req_bundle = new Bundle();
    req_bundle.putString("BUNDLE_STRING_ID", getID());
    req_bundle.putInt(AllShareKey.BUNDLE_INT_VOLUME, level);
    req_msg.setBundle(req_bundle);
    this.mAllShareConnector.requestCVMAsync(req_msg, this.mAllShareRespHandler);
  }

  @Override // com.samsung.android.allshare.media.AVPlayer
  public void getMute() {
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      AVPlayer.IAVPlayerVolumeResponseListener iAVPlayerVolumeResponseListener =
          this.mAVPlayerVolumeResponseListener;
      if (iAVPlayerVolumeResponseListener != null) {
        iAVPlayerVolumeResponseListener.onSetMuteResponseReceived(
            false, ERROR.SERVICE_NOT_CONNECTED);
        return;
      }
      return;
    }
    CVMessage req_msg = new CVMessage();
    req_msg.setActionID(AllShareAction.ACTION_AV_PLAYER_REQUEST_GET_MUTE);
    Bundle req_bundle = new Bundle();
    req_bundle.putString("BUNDLE_STRING_ID", getID());
    req_msg.setBundle(req_bundle);
    this.mAllShareConnector.requestCVMAsync(req_msg, this.mAllShareRespHandler);
  }

  @Override // com.samsung.android.allshare.media.AVPlayer
  public void setMute(boolean m) {
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      DLog.w_api(TAG_CLASS, "setMute fail : SERVICE_NOT_CONNECTED");
      AVPlayer.IAVPlayerVolumeResponseListener iAVPlayerVolumeResponseListener =
          this.mAVPlayerVolumeResponseListener;
      if (iAVPlayerVolumeResponseListener != null) {
        iAVPlayerVolumeResponseListener.onSetMuteResponseReceived(m, ERROR.SERVICE_NOT_CONNECTED);
        return;
      }
      return;
    }
    DLog.i_api(TAG_CLASS, "setMute - " + m + " " + getName());
    CVMessage req_msg = new CVMessage();
    req_msg.setActionID(AllShareAction.ACTION_AV_PLAYER_REQUEST_SET_MUTE);
    Bundle req_bundle = new Bundle();
    req_bundle.putString("BUNDLE_STRING_ID", getID());
    req_bundle.putBoolean(AllShareKey.BUNDLE_BOOLEAN_MUTE, m);
    req_msg.setBundle(req_bundle);
    this.mAllShareConnector.requestCVMAsync(req_msg, this.mAllShareRespHandler);
  }

  @Override // com.samsung.android.allshare.media.AVPlayer
  public void getPlayPosition() {
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      AVPlayer.IAVPlayerPlaybackResponseListener iAVPlayerPlaybackResponseListener =
          this.mAVPlaybackResponseListener;
      if (iAVPlayerPlaybackResponseListener != null) {
        iAVPlayerPlaybackResponseListener.onGetPlayPositionResponseReceived(
            -1L, ERROR.SERVICE_NOT_CONNECTED);
        return;
      }
      return;
    }
    CVMessage req_msg = new CVMessage();
    req_msg.setActionID(AllShareAction.ACTION_AV_PLAYER_REQUEST_PLAY_POSITION);
    Bundle req_bundle = new Bundle();
    req_bundle.putString("BUNDLE_STRING_ID", getID());
    req_msg.setBundle(req_bundle);
    this.mAllShareConnector.requestCVMAsync(req_msg, this.mAllShareRespHandler);
  }

  @Override // com.samsung.android.allshare.media.AVPlayer
  public AVPlayer.AVPlayerState getPlayerState() {
    AVPlayer.AVPlayerState state = AVPlayer.AVPlayerState.UNKNOWN;
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      return state;
    }
    CVMessage req_msg = new CVMessage();
    req_msg.setActionID(AllShareAction.ACTION_AV_PLAYER_GET_PLAYER_STATE_SYNC);
    Bundle req_bundle = new Bundle();
    req_bundle.putString("BUNDLE_STRING_ID", getID());
    req_msg.setBundle(req_bundle);
    CVMessage res_msg = this.mAllShareConnector.requestCVMSync(req_msg);
    if (res_msg == null) {
      return state;
    }
    Bundle res_bundle = res_msg.getBundle();
    if (res_bundle == null) {
      return state;
    }
    return AVPlayer.AVPlayerState.stringToEnum(
        res_bundle.getString(AllShareKey.BUNDLE_STRING_AV_PLAER_STATE));
  }

  @Override // com.samsung.android.allshare.media.AVPlayer
  public void setResponseListener(AVPlayer.IAVPlayerPlaybackResponseListener l) {
    this.mAVPlaybackResponseListener = l;
  }

  @Override // com.samsung.android.allshare.media.AVPlayer
  public void setVolumeResponseListener(AVPlayer.IAVPlayerVolumeResponseListener l) {
    this.mAVPlayerVolumeResponseListener = l;
  }

  @Override // com.samsung.android.allshare.media.AVPlayer
  public void setExtensionResponseListener(AVPlayer.IAVPlayerExtensionResponseListener l) {
    this.mAVPlayerExtensionResponseListener = l;
  }

  @Override // com.samsung.android.allshare.media.AVPlayer
  public void setExtensionEventListener(AVPlayer.IAVPlayerExtensionEventListener l) {
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      DLog.w_api(TAG_CLASS, "setEventListener error! AllShareService is not connected");
      return;
    }
    this.mAVPlayerExtensionEventListener = l;
    boolean z = this.mIsSubscribed;
    if (!z && l != null) {
      this.mAllShareConnector.subscribeAllShareEvent(
          AllShareEvent.EVENT_DEVICE_SUBSCRIBE, this.mDeviceImpl.getBundle(), this.mEventHandler);
      this.mIsSubscribed = true;
    } else if (z && l == null) {
      this.mAllShareConnector.unsubscribeAllShareEvent(
          AllShareEvent.EVENT_DEVICE_SUBSCRIBE, this.mDeviceImpl.getBundle(), this.mEventHandler);
      this.mIsSubscribed = false;
    }
  }

  @Override // com.samsung.android.allshare.media.AVPlayer
  public void setEventListener(AVPlayer.IAVPlayerEventListener l) {
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      DLog.w_api(TAG_CLASS, "setEventListener error! AllShareService is not connected");
      return;
    }
    this.mAVPlayerEventListener = l;
    boolean z = this.mIsSubscribed;
    if (!z && l != null) {
      this.mAllShareConnector.subscribeAllShareEvent(
          AllShareEvent.EVENT_DEVICE_SUBSCRIBE, this.mDeviceImpl.getBundle(), this.mEventHandler);
      this.mIsSubscribed = true;
    } else if (z && l == null) {
      this.mAllShareConnector.unsubscribeAllShareEvent(
          AllShareEvent.EVENT_DEVICE_SUBSCRIBE, this.mDeviceImpl.getBundle(), this.mEventHandler);
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
  @Deprecated
  public String getIPAdress() {
    return getIPAddress();
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

  private void playLocalContent(Uri uri, Item item, ContentInfo contentInfo, String mimeType) {
    playUri(
        uri, item, contentInfo, mimeType, AllShareAction.ACTION_AV_PLAYER_PLAY_LOCAL_CONTENS_URI);
  }

  private void playLocalContent(
      String filepath, Item item, ContentInfo contentInfo, String mimeType) {
    playFilePath(filepath, item, contentInfo, mimeType);
  }

  private void playWebContent(Uri uri, Item item, ContentInfo contentInfo, String mimeType) {
    ContentInfo ci = null;
    if (contentInfo != null) {
      ContentInfo.Builder builder = new ContentInfo.Builder();
      builder.setStartingPosition(contentInfo.getStartingPosition() * 1000);
      ci = builder.build();
    }
    playUri(uri, item, ci, mimeType, AllShareAction.ACTION_AV_PLAYER_PLAY_URI);
  }

  private void playItem(Item item, ContentInfo contentInfo) {
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      this.mAVPlaybackResponseListener.onPlayResponseReceived(
          item, contentInfo, ERROR.SERVICE_NOT_CONNECTED);
      return;
    }
    CVMessage req_msg = new CVMessage();
    req_msg.setActionID(AllShareAction.ACTION_AV_PLAYER_PLAY);
    Bundle req_bundle = extractBundle(item);
    req_bundle.putString("BUNDLE_STRING_ID", getID());
    req_bundle.putLong(
        AllShareKey.BUNDLE_LONG_CONTENT_INFO_STARTINGPOSITION,
        contentInfo != null ? contentInfo.getStartingPosition() : 0L);
    req_msg.setBundle(req_bundle);
    this.mAllShareConnector.requestCVMAsync(req_msg, this.mAllShareRespHandler);
  }

  private void playUri(
      Uri uri, Item item, ContentInfo contentInfo, String mimeType, String actionID) {
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      this.mAVPlaybackResponseListener.onPlayResponseReceived(
          item, contentInfo, ERROR.SERVICE_NOT_CONNECTED);
      return;
    }
    String title = item.getTitle();
    CVMessage req_msg = new CVMessage();
    req_msg.setActionID(actionID);
    Bundle req_bundle = extractBundle(item);
    req_bundle.putString(AllShareKey.BUNDLE_STRING_TITLE, title);
    req_bundle.putString("BUNDLE_STRING_ID", getID());
    req_bundle.putParcelable(AllShareKey.BUNDLE_PARCELABLE_URI, uri);
    req_bundle.putLong(
        AllShareKey.BUNDLE_LONG_CONTENT_INFO_STARTINGPOSITION,
        contentInfo != null ? contentInfo.getStartingPosition() : 0L);
    req_msg.setBundle(req_bundle);
    this.mAllShareConnector.requestCVMAsync(req_msg, this.mAllShareRespHandler);
  }

  private void playFilePath(String filePath, Item item, ContentInfo contentInfo, String mimeType) {
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      this.mAVPlaybackResponseListener.onPlayResponseReceived(
          item, contentInfo, ERROR.SERVICE_NOT_CONNECTED);
      return;
    }
    String title = item.getTitle();
    CVMessage req_msg = new CVMessage();
    req_msg.setActionID(AllShareAction.ACTION_AV_PLAYER_PLAY_LOCAL_CONTENS_FILEPATH_WITH_METADATA);
    Bundle req_bundle = extractBundle(item);
    req_bundle.putString(AllShareKey.BUNDLE_STRING_TITLE, title);
    req_bundle.putString("BUNDLE_STRING_ID", getID());
    req_bundle.putLong(
        AllShareKey.BUNDLE_LONG_CONTENT_INFO_STARTINGPOSITION,
        contentInfo != null ? contentInfo.getStartingPosition() : 0L);
    req_msg.setBundle(req_bundle);
    this.mAllShareConnector.requestCVMAsync(req_msg, this.mAllShareRespHandler);
  }

  @Override // com.samsung.android.allshare.media.AVPlayer
  public void getMediaInfo() {
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      AVPlayer.IAVPlayerPlaybackResponseListener iAVPlayerPlaybackResponseListener =
          this.mAVPlaybackResponseListener;
      if (iAVPlayerPlaybackResponseListener != null) {
        iAVPlayerPlaybackResponseListener.onGetMediaInfoResponseReceived(
            null, ERROR.SERVICE_NOT_CONNECTED);
        return;
      }
      return;
    }
    CVMessage req_msg = new CVMessage();
    req_msg.setActionID(AllShareAction.ACTION_AV_PLAYER_REQUEST_GET_MEDIA_INFO);
    Bundle req_bundle = new Bundle();
    req_bundle.putString("BUNDLE_STRING_ID", getID());
    req_msg.setBundle(req_bundle);
    this.mAllShareConnector.requestCVMAsync(req_msg, this.mAllShareRespHandler);
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
    Bundle res_bundle;
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      return false;
    }
    CVMessage req_msg = new CVMessage();
    req_msg.setActionID(AllShareAction.ACTION_AV_PLAYER_IS_SUPPORT_REDIRECT_SYNC);
    Bundle req_bundle = new Bundle();
    req_bundle.putString("BUNDLE_STRING_ID", getID());
    req_msg.setBundle(req_bundle);
    CVMessage res_msg = this.mAllShareConnector.requestCVMSync(req_msg);
    if (res_msg == null || (res_bundle = res_msg.getBundle()) == null) {
      return false;
    }
    String err = res_bundle.getString("BUNDLE_ENUM_ERROR");
    if (ERROR.NOT_SUPPORTED_FRAMEWORK_VERSION.enumToString().equals(err)) {
      DLog.w_api(TAG_CLASS, "isRedirectSupportable() Exception : NOT_SUPPORTED_FRAMEWORK_VERSION");
      return false;
    }
    try {
      boolean result = res_bundle.getBoolean(AllShareKey.BUNDLE_BOOLEAN_SUPPORT_REDIRECT);
      return result;
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
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      AVPlayer.IAVPlayerPlaybackResponseListener iAVPlayerPlaybackResponseListener =
          this.mAVPlaybackResponseListener;
      if (iAVPlayerPlaybackResponseListener != null) {
        iAVPlayerPlaybackResponseListener.onGetStateResponseReceived(
            AVPlayer.AVPlayerState.UNKNOWN, ERROR.SERVICE_NOT_CONNECTED);
        return;
      }
      return;
    }
    CVMessage req_msg = new CVMessage();
    req_msg.setActionID(AllShareAction.ACTION_AV_PLAYER_REQUEST_GET_PLAYER_STATE);
    Bundle req_bundle = new Bundle();
    req_bundle.putString("BUNDLE_STRING_ID", getID());
    req_msg.setBundle(req_bundle);
    this.mAllShareConnector.requestCVMAsync(req_msg, this.mAllShareRespHandler);
  }

  @Override // com.samsung.android.allshare.media.AVPlayer
  public PlaylistPlayer getPlaylistPlayer() {
    return this.mPlaylistPlayer;
  }

  @Override // com.sec.android.allshare.iface.IHandlerHolder
  public void removeEventHandler() {
    this.mAllShareConnector.unsubscribeAllShareEvent(
        AllShareEvent.EVENT_DEVICE_SUBSCRIBE, getBundle(), this.mEventHandler);
    this.mIsSubscribed = false;
  }

  @Override // com.samsung.android.allshare.media.AVPlayer
  public boolean isSupportDynamicBuffering() {
    Bundle res_bundle;
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      return false;
    }
    CVMessage req_msg = new CVMessage();
    req_msg.setActionID(AllShareAction.ACTION_AV_PLAYER_IS_SUPPORT_DYNAMIC_BUFFERING);
    Bundle req_bundle = new Bundle();
    req_bundle.putString("BUNDLE_STRING_ID", getID());
    req_msg.setBundle(req_bundle);
    CVMessage res_msg = this.mAllShareConnector.requestCVMSync(req_msg);
    if (res_msg == null || (res_bundle = res_msg.getBundle()) == null) {
      return false;
    }
    try {
      boolean result = res_bundle.getBoolean(AllShareKey.BUNDLE_BOOLEAN_SUPPORT_DYNAMIC_BUFFERING);
      return result;
    } catch (Exception e) {
      DLog.w_api(TAG_CLASS, "isSupportDynamicBuffering Exception", e);
      return false;
    }
  }

  @Override // com.samsung.android.allshare.media.AVPlayer
  public void skipDynamicBuffering() {
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      return;
    }
    CVMessage req_msg = new CVMessage();
    req_msg.setActionID(AllShareAction.ACTION_AV_PLAYER_SKIP_DYNAMIC_BUFFERING);
    Bundle req_bundle = new Bundle();
    req_bundle.putString("BUNDLE_STRING_ID", getID());
    req_msg.setBundle(req_bundle);
    this.mAllShareConnector.requestCVMAsync(req_msg, this.mAllShareRespHandler);
  }

  @Override // com.samsung.android.allshare.media.AVPlayer
  public void SetWHAResponseListener(AVPlayer.IAVPlayerWHAResponseListener listener) {
    this.mAVPlayerWHAResponseListener = listener;
  }

  @Override // com.samsung.android.allshare.media.AVPlayer
  public void GetWHADeviceStatusInfo() {
    DLog.i_api(TAG_CLASS, "GetWHADeviceStatusInfo");
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      AVPlayer.IAVPlayerWHAResponseListener iAVPlayerWHAResponseListener =
          this.mAVPlayerWHAResponseListener;
      if (iAVPlayerWHAResponseListener != null) {
        iAVPlayerWHAResponseListener.onGetWHADeviceStatusInfo(null, ERROR.SERVICE_NOT_CONNECTED);
        return;
      }
      return;
    }
    Bundle req_bundle = new Bundle();
    req_bundle.putString("BUNDLE_STRING_ID", getID());
    CVMessage req_msg = new CVMessage();
    req_msg.setActionID(AllShareAction.ACTION_WHA_GET_DEVICE_STATUS_INFO);
    req_msg.setBundle(req_bundle);
    this.mAllShareConnector.requestCVMAsync(req_msg, this.mAllShareRespHandler);
  }

  @Override // com.samsung.android.allshare.media.AVPlayer
  public void CreateWHAParty() {
    DLog.i_api(TAG_CLASS, "CreateWHAParty");
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      AVPlayer.IAVPlayerWHAResponseListener iAVPlayerWHAResponseListener =
          this.mAVPlayerWHAResponseListener;
      if (iAVPlayerWHAResponseListener != null) {
        iAVPlayerWHAResponseListener.onCreateWHAParty(null, ERROR.SERVICE_NOT_CONNECTED);
        return;
      }
      return;
    }
    Bundle req_bundle = new Bundle();
    req_bundle.putString("BUNDLE_STRING_ID", getID());
    CVMessage req_msg = new CVMessage();
    req_msg.setActionID(AllShareAction.ACTION_WHA_CREATE_PARTY);
    req_msg.setBundle(req_bundle);
    this.mAllShareConnector.requestCVMAsync(req_msg, this.mAllShareRespHandler);
  }

  @Override // com.samsung.android.allshare.media.AVPlayer
  public void JoinWHAParty(String partyID) {
    DLog.i_api(TAG_CLASS, "JoinWHAParty to " + partyID);
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      AVPlayer.IAVPlayerWHAResponseListener iAVPlayerWHAResponseListener =
          this.mAVPlayerWHAResponseListener;
      if (iAVPlayerWHAResponseListener != null) {
        iAVPlayerWHAResponseListener.onJoinWHAParty(ERROR.SERVICE_NOT_CONNECTED);
        return;
      }
      return;
    }
    Bundle req_bundle = new Bundle();
    req_bundle.putString("BUNDLE_STRING_ID", getID());
    req_bundle.putString(AllShareKey.BUNDLE_STRING_WHA_REQUEST_PARTY_ID, partyID);
    CVMessage req_msg = new CVMessage();
    req_msg.setActionID(AllShareAction.ACTION_WHA_JOIN_PARTY);
    req_msg.setBundle(req_bundle);
    this.mAllShareConnector.requestCVMAsync(req_msg, this.mAllShareRespHandler);
  }

  @Override // com.samsung.android.allshare.media.AVPlayer
  public void LeaveWHAParty() {
    DLog.i_api(TAG_CLASS, "LeaveWHAParty");
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      AVPlayer.IAVPlayerWHAResponseListener iAVPlayerWHAResponseListener =
          this.mAVPlayerWHAResponseListener;
      if (iAVPlayerWHAResponseListener != null) {
        iAVPlayerWHAResponseListener.onLeaveWHAParty(ERROR.SERVICE_NOT_CONNECTED);
        return;
      }
      return;
    }
    Bundle req_bundle = new Bundle();
    req_bundle.putString("BUNDLE_STRING_ID", getID());
    CVMessage req_msg = new CVMessage();
    req_msg.setActionID(AllShareAction.ACTION_WHA_LEAVE_PARTY);
    req_msg.setBundle(req_bundle);
    this.mAllShareConnector.requestCVMAsync(req_msg, this.mAllShareRespHandler);
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
  public void requestMobileToTV(String ip, int port) {
    DLog.w_api(TAG_CLASS, "requestMobileToTV : call requestMobileToTV");
    DeviceImpl deviceImpl = this.mDeviceImpl;
    if (deviceImpl == null) {
      return;
    }
    deviceImpl.requestMobileToTV(ip, port);
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
  public String getProductCapInfo(Device.InformationType infoType) {
    DeviceImpl deviceImpl = this.mDeviceImpl;
    if (deviceImpl == null) {
      return "";
    }
    return deviceImpl.getProductCapInfo(infoType);
  }

  @Override // com.samsung.android.allshare.Device
  public String getScreenSharingInfo(Device.InformationType infoType) {
    DeviceImpl deviceImpl = this.mDeviceImpl;
    if (deviceImpl == null) {
      return "";
    }
    return deviceImpl.getScreenSharingInfo(infoType);
  }

  @Override // com.samsung.android.allshare.media.AVPlayer
  public void setAspectRatio(String aspectRatio) {
    DLog.i_api(TAG_CLASS, "setAspectRatio to " + aspectRatio);
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      DLog.w_api(TAG_CLASS, "setAspectRatio : SERVICE_NOT_CONNECTED");
      AVPlayer.IAVPlayerExtensionResponseListener iAVPlayerExtensionResponseListener =
          this.mAVPlayerExtensionResponseListener;
      if (iAVPlayerExtensionResponseListener != null) {
        iAVPlayerExtensionResponseListener.onSetAspectRatioResponseReceived(
            ERROR.SERVICE_NOT_CONNECTED);
        return;
      }
      return;
    }
    CVMessage req_msg = new CVMessage();
    req_msg.setActionID(AllShareAction.ACTION_AV_PLAYER_SET_ASPECT_RATIO);
    Bundle req_bundle = new Bundle();
    req_bundle.putString("BUNDLE_STRING_ID", getID());
    req_bundle.putString(AllShareKey.BUNDLE_STRING_ASPECT_RATIO, aspectRatio);
    req_msg.setBundle(req_bundle);
    this.mAllShareConnector.requestCVMAsync(req_msg, this.mAllShareRespHandler);
  }

  @Override // com.samsung.android.allshare.media.AVPlayer
  public void requestAspectRatioState() {
    DLog.i_api(TAG_CLASS, "requestAspectRatioState");
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      DLog.w_api(TAG_CLASS, "getAspectRatio : SERVICE_NOT_CONNECTED");
      AVPlayer.IAVPlayerExtensionResponseListener iAVPlayerExtensionResponseListener =
          this.mAVPlayerExtensionResponseListener;
      if (iAVPlayerExtensionResponseListener != null) {
        iAVPlayerExtensionResponseListener.onAspectRatioStateResponseReceived(
            null, ERROR.SERVICE_NOT_CONNECTED);
        return;
      }
      return;
    }
    CVMessage req_msg = new CVMessage();
    req_msg.setActionID(AllShareAction.ACTION_AV_PLAYER_GET_ASPECT_RATIO);
    Bundle req_bundle = new Bundle();
    req_bundle.putString("BUNDLE_STRING_ID", getID());
    req_msg.setBundle(req_bundle);
    this.mAllShareConnector.requestCVMAsync(req_msg, this.mAllShareRespHandler);
  }

  @Override // com.samsung.android.allshare.media.AVPlayer
  public void move360View(float latitudeOffset, float longitudeOffset) {
    DLog.i_api(
        TAG_CLASS,
        "move360View to [latitudeOffset]"
            + latitudeOffset
            + " [longitudeOffset]"
            + longitudeOffset);
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      DLog.w_api(TAG_CLASS, "move360View : SERVICE_NOT_CONNECTED");
      AVPlayer.IAVPlayerExtensionResponseListener iAVPlayerExtensionResponseListener =
          this.mAVPlayerExtensionResponseListener;
      if (iAVPlayerExtensionResponseListener != null) {
        iAVPlayerExtensionResponseListener.onMove360ViewResponseReceived(
            ERROR.SERVICE_NOT_CONNECTED);
        return;
      }
      return;
    }
    CVMessage req_msg = new CVMessage();
    req_msg.setActionID(AllShareAction.ACTION_AV_PLAYER_MOVE_360_VIEW);
    Bundle req_bundle = new Bundle();
    req_bundle.putString("BUNDLE_STRING_ID", getID());
    req_bundle.putFloat(AllShareKey.BUNDLE_FLOATING_LATITUDE_OFFSET, latitudeOffset);
    req_bundle.putFloat("BUNDLE_LONG_PROGRESS", longitudeOffset);
    req_msg.setBundle(req_bundle);
    this.mAllShareConnector.requestCVMAsync(req_msg, this.mAllShareRespHandler);
  }

  @Override // com.samsung.android.allshare.media.AVPlayer
  public void zoom360View(float ScaleFactor) {
    DLog.i_api(TAG_CLASS, "zoom360View to " + ScaleFactor);
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      DLog.w_api(TAG_CLASS, "zoom360View : SERVICE_NOT_CONNECTED");
      AVPlayer.IAVPlayerExtensionResponseListener iAVPlayerExtensionResponseListener =
          this.mAVPlayerExtensionResponseListener;
      if (iAVPlayerExtensionResponseListener != null) {
        iAVPlayerExtensionResponseListener.onZoom360ViewResponseReceived(
            ERROR.SERVICE_NOT_CONNECTED);
        return;
      }
      return;
    }
    CVMessage req_msg = new CVMessage();
    req_msg.setActionID(AllShareAction.ACTION_AV_PLAYER_ZOOM_360_VIEW);
    Bundle req_bundle = new Bundle();
    req_bundle.putString("BUNDLE_STRING_ID", getID());
    req_bundle.putFloat(AllShareKey.BUNDLE_FLOATING_SCALEFACTOR, ScaleFactor);
    req_msg.setBundle(req_bundle);
    this.mAllShareConnector.requestCVMAsync(req_msg, this.mAllShareRespHandler);
  }

  @Override // com.samsung.android.allshare.media.AVPlayer
  public void reset360View() {
    DLog.i_api(TAG_CLASS, "reset360View");
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      DLog.w_api(TAG_CLASS, "origin360View : SERVICE_NOT_CONNECTED");
      AVPlayer.IAVPlayerExtensionResponseListener iAVPlayerExtensionResponseListener =
          this.mAVPlayerExtensionResponseListener;
      if (iAVPlayerExtensionResponseListener != null) {
        iAVPlayerExtensionResponseListener.onReset360ViewResponseReceived(
            ERROR.SERVICE_NOT_CONNECTED);
        return;
      }
      return;
    }
    CVMessage req_msg = new CVMessage();
    req_msg.setActionID(AllShareAction.ACTION_AV_PLAYER_ORIGIN_360_VIEW);
    Bundle req_bundle = new Bundle();
    req_bundle.putString("BUNDLE_STRING_ID", getID());
    req_msg.setBundle(req_bundle);
    this.mAllShareConnector.requestCVMAsync(req_msg, this.mAllShareRespHandler);
  }

  @Override // com.samsung.android.allshare.media.AVPlayer
  public void controlCaption(Caption.CaptionOperation operation, Caption caption) {
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      DLog.w_api(TAG_CLASS, "controlCaption : SERVICE_NOT_CONNECTED");
      AVPlayer.IAVPlayerExtensionResponseListener iAVPlayerExtensionResponseListener =
          this.mAVPlayerExtensionResponseListener;
      if (iAVPlayerExtensionResponseListener != null) {
        iAVPlayerExtensionResponseListener.onControlCaptionResponseReceived(
            ERROR.SERVICE_NOT_CONNECTED);
        return;
      }
      return;
    }
    if (operation == null) {
      DLog.w_api(TAG_CLASS, "controlCaption: CaptionOperation is null, set Disable");
      operation = Caption.CaptionOperation.DISABLE;
    }
    if (caption == null) {
      DLog.w_api(TAG_CLASS, "controlCaption: Caption is null, create empty caption");
      caption = new Caption();
    }
    DLog.i_api(
        TAG_CLASS,
        "controlCaption to [operation]"
            + operation.enumToString()
            + " [caption]"
            + caption.toString());
    List<String> languageList = caption.getLanguageList();
    String language = TextUtils.join(",", languageList);
    Bundle req_bundle = new Bundle();
    req_bundle.putString("BUNDLE_STRING_ID", getID());
    req_bundle.putString(AllShareKey.BUNDLE_STRING_CAPTION_OPERATION, operation.enumToString());
    req_bundle.putString(AllShareKey.BUNDLE_STRING_CAPTION_NAME, caption.getName());
    req_bundle.putString(AllShareKey.BUNDLE_STRING_CAPTION_RES_URI, caption.getResourceUri());
    req_bundle.putString(AllShareKey.BUNDLE_STRING_CAPTION_URI, caption.getCaptionUri());
    req_bundle.putString(
        AllShareKey.BUNDLE_STRING_CAPTION_TYPE, caption.getCaptionType().enumToString());
    req_bundle.putString(AllShareKey.BUNDLE_STRING_CAPTION_LANGUAGE, language);
    req_bundle.putString(AllShareKey.BUNDLE_STRING_CAPTION_ENCODING, caption.getEncoding());
    CVMessage req_msg = new CVMessage();
    req_msg.setActionID(AllShareAction.ACTION_AV_PLAYER_CONTROL_CAPTION);
    req_msg.setBundle(req_bundle);
    this.mAllShareConnector.requestCVMAsync(req_msg, this.mAllShareRespHandler);
  }

  @Override // com.samsung.android.allshare.media.AVPlayer
  public void requestCaptionState() {
    DLog.i_api(TAG_CLASS, "requestCaptionState");
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      DLog.w_api(TAG_CLASS, "getCaptionState : SERVICE_NOT_CONNECTED");
      AVPlayer.IAVPlayerExtensionResponseListener iAVPlayerExtensionResponseListener =
          this.mAVPlayerExtensionResponseListener;
      if (iAVPlayerExtensionResponseListener != null) {
        iAVPlayerExtensionResponseListener.onCaptionStateResponseReceived(
            null, null, ERROR.SERVICE_NOT_CONNECTED);
        return;
      }
      return;
    }
    Bundle req_bundle = new Bundle();
    req_bundle.putString("BUNDLE_STRING_ID", getID());
    CVMessage req_msg = new CVMessage();
    req_msg.setActionID(AllShareAction.ACTION_AV_PLAYER_GET_CAPTION_STATE);
    req_msg.setBundle(req_bundle);
    this.mAllShareConnector.requestCVMAsync(req_msg, this.mAllShareRespHandler);
  }

  @Override // com.samsung.android.allshare.media.AVPlayer
  public boolean isSupportAspectRatio() {
    boolean result = this.mSupportSetAspectRatio && this.mSupportGetAspectRatio;
    DLog.i_api(TAG_CLASS, "isSupportAspectRatio is " + result);
    return result;
  }

  @Override // com.samsung.android.allshare.media.AVPlayer
  public boolean isSupport360View() {
    boolean result =
        this.mSupportMove360View && this.mSupportZoom360View && this.mSupportOrigin360View;
    DLog.i_api(TAG_CLASS, "isSupport360View is " + result);
    return result;
  }

  @Override // com.samsung.android.allshare.media.AVPlayer
  public boolean isSupportCaptionControl() {
    boolean result = this.mSupportControlCaption && this.mSupportGetCaptionState;
    DLog.i_api(TAG_CLASS, "isSupportCaptionControl is " + result);
    return result;
  }

  @Override // com.samsung.android.allshare.media.AVPlayer
  public String getCaptionFilePathFromURI(String uri) {
    DLog.i_api(TAG_CLASS, "getCaptionFilePathFromURI");
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      DLog.w_api(TAG_CLASS, "getCaptionFilePathFromURI : SERVICE_NOT_CONNECTED");
      return "";
    }
    return this.mAllShareConnector.getCaptionFilePathFromURI(uri);
  }

  @Override // com.samsung.android.allshare.Device
  public boolean isSupportedByType(int type) {
    DeviceImpl deviceImpl = this.mDeviceImpl;
    if (deviceImpl == null) {
      return false;
    }
    return deviceImpl.isSupportedByType(type);
  }
}
