package com.samsung.android.allshare;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.p009os.Bundle;
import com.samsung.android.allshare.media.ContentInfo;
import com.samsung.android.allshare.media.ImageViewer;
import com.samsung.android.allshare.media.PlaylistPlayer;
import com.samsung.android.allshare.media.SlideShowPlayer;
import com.samsung.android.allshare.media.ViewController;
import com.samsung.android.allshare.media.ViewController2;
import com.sec.android.allshare.iface.CVMessage;
import com.sec.android.allshare.iface.IBundleHolder;
import com.sec.android.allshare.iface.IHandlerHolder;
import com.sec.android.allshare.iface.message.AllShareAction;
import com.sec.android.allshare.iface.message.AllShareEvent;
import com.sec.android.allshare.iface.message.AllShareKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/* loaded from: classes5.dex */
final class ImageViewerImpl extends ImageViewer implements IBundleHolder, IHandlerHolder {
  private static final String TAG_CLASS = "ImageViewerImpl";
  private IAllShareConnector mAllShareConnector;
  private DeviceImpl mDeviceImpl;
  private PlaylistPlayer mPlaylistPlayer;
  private ImageViewer.IImageViewerResponseListener mResponseListener = null;
  private ImageViewer.IImageViewerEventListener mEventListener = null;
  private ViewController mViewController = null;
  private ViewController2 mViewController2 = null;
  private SlideShowPlayer mSlideShowPlayer = null;
  private boolean mIsSubscribed = false;
  private boolean mContentChangedNotified = true;
  private ArrayList<ArrayList<String>> mPlayingContentUris = new ArrayList<>();
  private String mCurrentDMRUri = null;
  AllShareEventHandler mEventHandler =
      new AllShareEventHandler(
          ServiceConnector
              .getMainLooper()) { // from class: com.samsung.android.allshare.ImageViewerImpl.1
        private HashMap<String, ImageViewer.ImageViewerState> mStateMap;

        {
          HashMap<String, ImageViewer.ImageViewerState> hashMap = new HashMap<>();
          this.mStateMap = hashMap;
          hashMap.put(
              AllShareEvent.EVENT_RENDERER_STATE_BUFFERING, ImageViewer.ImageViewerState.BUFFERING);
          this.mStateMap.put(
              AllShareEvent.EVENT_RENDERER_STATE_NOMEDIA, ImageViewer.ImageViewerState.STOPPED);
          this.mStateMap.put(
              AllShareEvent.EVENT_RENDERER_STATE_PAUSED, ImageViewer.ImageViewerState.SHOWING);
          this.mStateMap.put(
              AllShareEvent.EVENT_RENDERER_STATE_STOPPED, ImageViewer.ImageViewerState.STOPPED);
          this.mStateMap.put(
              AllShareEvent.EVENT_RENDERER_STATE_PLAYING, ImageViewer.ImageViewerState.SHOWING);
          this.mStateMap.put(
              AllShareEvent.EVENT_RENDERER_STATE_CONTENT_CHANGED,
              ImageViewer.ImageViewerState.CONTENT_CHANGED);
        }

        @Override // com.samsung.android.allshare.AllShareEventHandler
        public void handleEventMessage(CVMessage cvm) {
          try {
            ERROR error = ERROR.FAIL;
            Bundle resBundle = cvm.getBundle();
            ImageViewer.ImageViewerState state = this.mStateMap.get(cvm.getActionID());
            String errorStr = resBundle.getString("BUNDLE_ENUM_ERROR");
            ERROR error2 = ERROR.stringToEnum(errorStr);
            if (state == null) {
              state = ImageViewer.ImageViewerState.UNKNOWN;
            }
            if (state.equals(ImageViewer.ImageViewerState.CONTENT_CHANGED)) {
              String currentTrackUri = resBundle.getString(AllShareKey.BUNDLE_STRING_APP_ITEM_ID);
              if (currentTrackUri != null && !currentTrackUri.isEmpty()) {
                if (ImageViewerImpl.this.mContentChangedNotified) {
                  DLog.d_api(ImageViewerImpl.TAG_CLASS, "do not notify CONTENT_CHANGED event yet");
                  ImageViewerImpl.this.mCurrentDMRUri = currentTrackUri;
                  return;
                }
                if (ImageViewerImpl.this.mCurrentDMRUri == null
                    || !currentTrackUri.equalsIgnoreCase(ImageViewerImpl.this.mCurrentDMRUri)) {
                  DLog.d_api(
                      ImageViewerImpl.TAG_CLASS,
                      "CONTENT_CHANGED, mCurrentDMRUri : "
                          + ImageViewerImpl.this.mCurrentDMRUri
                          + "  currentTrackUri : "
                          + currentTrackUri);
                  if (ImageViewerImpl.this.mCurrentDMRUri == null) {
                    DLog.d_api(
                        ImageViewerImpl.TAG_CLASS,
                        "do not notify CONTENT_CHANGED event, mCurrentDMRUri is null");
                    ImageViewerImpl.this.mCurrentDMRUri = currentTrackUri;
                    return;
                  }
                  ImageViewerImpl.this.mCurrentDMRUri = currentTrackUri;
                  if (ImageViewerImpl.this.mPlayingContentUris != null
                      && !ImageViewerImpl.this.mPlayingContentUris.isEmpty()) {
                    if (isContains(currentTrackUri)) {
                      DLog.d_api(
                          ImageViewerImpl.TAG_CLASS,
                          "handleEventMessage: this is playing content.");
                      DLog.i_api(
                          ImageViewerImpl.TAG_CLASS,
                          "do not notify CONTENT_CHANGED event, this is my=" + currentTrackUri);
                      return;
                    } else {
                      ImageViewerImpl.this.mContentChangedNotified = true;
                      DLog.w_api(
                          ImageViewerImpl.TAG_CLASS,
                          "Notify CONTENT_CHANGED event, mPlayingContentUris["
                              + ImageViewerImpl.this.mPlayingContentUris
                              + "] vs currentTrackUri["
                              + currentTrackUri
                              + NavigationBarInflaterView.SIZE_MOD_END);
                    }
                  }
                  DLog.d_api(
                      ImageViewerImpl.TAG_CLASS,
                      "do not notify CONTENT_CHANGED event, mPlayingContentUris is null");
                  return;
                }
                DLog.d_api(
                    ImageViewerImpl.TAG_CLASS,
                    "do not notify CONTENT_CHANGED event, mCurrentDMRUri is same as currentTrackUri"
                        + " "
                        + currentTrackUri);
                return;
              }
              DLog.d_api(
                  ImageViewerImpl.TAG_CLASS,
                  "do not notify CONTENT_CHANGED event, currentTrackUri is null");
              return;
            }
            notifyEvent(state, error2);
          } catch (Error err) {
            DLog.w_api(ImageViewerImpl.TAG_CLASS, "mEventHandler.handleEventMessage Error", err);
          } catch (Exception e) {
            DLog.w_api(
                ImageViewerImpl.TAG_CLASS, "mEventHandler.handleEventMessage Fail to notify event");
          }
        }

        private void notifyEvent(ImageViewer.ImageViewerState state, ERROR error) {
          if (ImageViewerImpl.this.mEventListener != null) {
            try {
              DLog.v_api(
                  ImageViewerImpl.TAG_CLASS,
                  "mEventHandler.notifyEvent to "
                      + ImageViewerImpl.this.mEventListener
                      + " state["
                      + state.enumToString()
                      + "] error["
                      + error.enumToString()
                      + NavigationBarInflaterView.SIZE_MOD_END);
              ImageViewerImpl.this.mEventListener.onDeviceChanged(state, error);
            } catch (Error err) {
              DLog.w_api(ImageViewerImpl.TAG_CLASS, "mEventHandler.notifyEvent Error", err);
            } catch (Exception e) {
              DLog.w_api(ImageViewerImpl.TAG_CLASS, "mEventHandler.notifyEvent Exception", e);
            }
          }
        }

        private boolean isContains(String currentTrackUri) {
          if (ImageViewerImpl.this.mPlayingContentUris == null || currentTrackUri == null) {
            return false;
          }
          Iterator it = ImageViewerImpl.this.mPlayingContentUris.iterator();
          while (it.hasNext()) {
            ArrayList<String> uris = (ArrayList) it.next();
            Iterator<String> it2 = uris.iterator();
            while (it2.hasNext()) {
              String uri = it2.next();
              if (currentTrackUri.endsWith(uri)) {
                ImageViewerImpl.this.mPlayingContentUris.remove(uris);
                return true;
              }
            }
          }
          return false;
        }
      };
  AllShareResponseHandler mResponseHandler =
      new AllShareResponseHandler(
          ServiceConnector
              .getMainLooper()) { // from class: com.samsung.android.allshare.ImageViewerImpl.2
        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.samsung.android.allshare.AllShareResponseHandler
        public void handleResponseMessage(CVMessage cvm) {
          String actionID = cvm.getActionID();
          Bundle resBundle = cvm.getBundle();
          if (actionID == null || resBundle == null) {
            DLog.w_api(
                ImageViewerImpl.TAG_CLASS,
                "handleResponseMessage : actionID == null || resBundle == null");
            return;
          }
          ERROR error = ERROR.FAIL;
          String errorStr = resBundle.getString("BUNDLE_ENUM_ERROR");
          if (errorStr != null) {
            error = ERROR.stringToEnum(errorStr);
          }
          long contentInfoStartingPosition =
              resBundle.getLong(AllShareKey.BUNDLE_LONG_CONTENT_INFO_STARTINGPOSITION);
          ContentInfo.Builder cb = new ContentInfo.Builder();
          ContentInfo info = cb.setStartingPosition(contentInfoStartingPosition).build();
          Bundle itemBundle = (Bundle) resBundle.getParcelable(AllShareKey.BUNDLE_PARCELABLE_ITEM);
          Item fromBundle = ItemCreator.fromBundle(itemBundle);
          if (actionID.equals(AllShareAction.ACTION_IMAGE_VIEWER_SHOW_LOCAL_CONTENT)
              || actionID.equals(AllShareAction.ACTION_IMAGE_VIEWER_SHOW_LOCAL_CONTENT_URI)
              || actionID.equals(AllShareAction.ACTION_IMAGE_VIEWER_SHOW_URI)
              || actionID.equals(AllShareAction.ACTION_IMAGE_VIEWER_SHOW)) {
            if (error.equals(ERROR.SUCCESS)) {
              ImageViewerImpl.this.mContentChangedNotified = false;
            } else if (fromBundle != 0) {
              if (actionID.equals(AllShareAction.ACTION_IMAGE_VIEWER_SHOW_URI)
                  || actionID.equals(AllShareAction.ACTION_IMAGE_VIEWER_SHOW)) {
                removeUri(fromBundle.getURI().toString());
              } else if (actionID.equals(AllShareAction.ACTION_IMAGE_VIEWER_SHOW_LOCAL_CONTENT)) {
                Bundle bundle = new Bundle();
                if (fromBundle instanceof IBundleHolder) {
                  bundle = ((IBundleHolder) fromBundle).getBundle();
                }
                String filePath = bundle.getString(AllShareKey.BUNDLE_STRING_FILEPATH);
                removeUri(filePath);
              } else if (actionID.equals(
                  AllShareAction.ACTION_IMAGE_VIEWER_SHOW_LOCAL_CONTENT_URI)) {
                String filePath2 = ImageViewerImpl.this.parseUriFilePath(fromBundle.getURI());
                removeUri(filePath2);
              }
            }
          }
          if (ImageViewerImpl.this.mResponseListener == null) {
            DLog.w_api(
                ImageViewerImpl.TAG_CLASS, "handleResponseMessage : mResponseListener == null");
            return;
          }
          if (actionID.equals(AllShareAction.ACTION_IMAGE_VIEWER_SHOW)
              || actionID.equals(AllShareAction.ACTION_IMAGE_VIEWER_SHOW_LOCAL_CONTENT)
              || actionID.equals(AllShareAction.ACTION_IMAGE_VIEWER_SHOW_LOCAL_CONTENT_URI)
              || actionID.equals(AllShareAction.ACTION_IMAGE_VIEWER_SHOW_URI)) {
            if (fromBundle == 0) {
              ImageViewerImpl.this.mResponseListener.onShowResponseReceived(
                  fromBundle, info, ERROR.ITEM_NOT_EXIST);
              return;
            } else {
              ImageViewerImpl.this.mResponseListener.onShowResponseReceived(
                  fromBundle, info, error);
              return;
            }
          }
          if (actionID.equals(AllShareAction.ACTION_IMAGE_VIEWER_STOP)) {
            ImageViewerImpl.this.mResponseListener.onStopResponseReceived(error);
          } else if (actionID.equals(AllShareAction.ACTION_IMAGE_VIEWER_REQUEST_GET_VIEWER_STATE)) {
            String state = resBundle.getString(AllShareKey.BUNDLE_STRING_IMAGE_VIEWEW_STATE);
            ImageViewer.ImageViewerState viewerState =
                ImageViewer.ImageViewerState.stringToEnum(state);
            ImageViewerImpl.this.mResponseListener.onGetStateResponseReceived(viewerState, error);
          }
        }

        private void removeUri(String currentTrackUri) {
          if (ImageViewerImpl.this.mPlayingContentUris == null || currentTrackUri == null) {
            return;
          }
          Iterator it = ImageViewerImpl.this.mPlayingContentUris.iterator();
          while (it.hasNext()) {
            ArrayList<String> uris = (ArrayList) it.next();
            if (uris != null) {
              Iterator<String> it2 = uris.iterator();
              while (it2.hasNext()) {
                String uri = it2.next();
                if (uri != null && currentTrackUri.endsWith(uri)) {
                  ImageViewerImpl.this.mPlayingContentUris.remove(uris);
                  return;
                }
              }
            }
          }
        }
      };

  ImageViewerImpl(IAllShareConnector connector, DeviceImpl deviceImpl) {
    this.mAllShareConnector = null;
    this.mDeviceImpl = null;
    if (connector == null) {
      DLog.w_api(TAG_CLASS, "Connection FAIL: AllShare Service Connector does not exist");
      return;
    }
    if (deviceImpl == null) {
      DLog.w_api(TAG_CLASS, "deviceImpl is null");
      return;
    }
    this.mDeviceImpl = deviceImpl;
    this.mAllShareConnector = connector;
    Bundle bundle = deviceImpl.getBundle();
    if (bundle == null) {
      DLog.w_api(TAG_CLASS, "bundle is null");
      return;
    }
    Boolean isSupportPlaylist =
        Boolean.valueOf(bundle.getBoolean(AllShareKey.BUNDLE_BOOLEAN_SUPPORT_PLAYLIST_PLAYER));
    if (isSupportPlaylist.booleanValue()) {
      this.mPlaylistPlayer = new PlaylistPlayerImpl(connector, deviceImpl);
    } else {
      this.mPlaylistPlayer = null;
    }
  }

  @Override // com.samsung.android.allshare.Device
  public String getNIC() {
    DeviceImpl deviceImpl = this.mDeviceImpl;
    if (deviceImpl == null) {
      return "";
    }
    return deviceImpl.getNIC();
  }

  /* JADX WARN: Multi-variable type inference failed */
  @Override // com.samsung.android.allshare.media.ImageViewer
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
    cvm.setActionID(AllShareAction.ACTION_IMAGE_VIEWER_PREPARE);
    Bundle bundle = new Bundle();
    bundle.putString("BUNDLE_STRING_ID", getID());
    bundle.putString(AllShareKey.BUNDLE_STRING_TITLE, item.getTitle());
    if (item instanceof IBundleHolder) {
      bundle.putParcelable(AllShareKey.BUNDLE_PARCELABLE_ITEM, ((IBundleHolder) item).getBundle());
    }
    cvm.setBundle(bundle);
    this.mAllShareConnector.requestCVMAsync(cvm, this.mResponseHandler);
    DLog.i_api(
        TAG_CLASS,
        "prepare : [ " + item.getTitle() + " to " + getName() + " uri : " + item.getURI() + " ] ");
  }

  @Override // com.samsung.android.allshare.media.ImageViewer
  public void zoom(int x, int y, int sourceWidth, int sourceHeight) {
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      DLog.w_api(TAG_CLASS, "zoom : SERVICE_NOT_CONNECTED");
      return;
    }
    if (sourceWidth < 0 || sourceHeight < 0) {
      DLog.w_api(TAG_CLASS, "zoom Fail :  image width or height is wrong ");
      return;
    }
    CVMessage cvm = new CVMessage();
    cvm.setActionID(AllShareAction.ACTION_IMAGE_VIEWER_ZOOM);
    Bundle bundle = new Bundle();
    bundle.putString("BUNDLE_STRING_ID", getID());
    bundle.putInt(AllShareKey.BUNDLE_INT_IMAGE_X_COORDINATE, x);
    bundle.putInt(AllShareKey.BUNDLE_INT_IMAGE_Y_COORDINATE, y);
    bundle.putInt(AllShareKey.BUNDLE_INT_IMAGE_WIDTH, sourceWidth);
    bundle.putInt(AllShareKey.BUNDLE_INT_IMAGE_HEIGHT, sourceHeight);
    cvm.setBundle(bundle);
    this.mAllShareConnector.requestCVMAsync(cvm, this.mResponseHandler);
    DLog.i_api(
        TAG_CLASS,
        "zoom_ScreenSharing : [ x : "
            + x
            + " y : "
            + y
            + " width : "
            + sourceWidth
            + " height : "
            + sourceHeight
            + " ] ");
  }

  /* JADX WARN: Multi-variable type inference failed */
  @Override // com.samsung.android.allshare.media.ImageViewer
  public void show(Item item, ContentInfo ci) {
    DLog.i_api(TAG_CLASS, "show() is called");
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      DLog.w_api(TAG_CLASS, "show : SERVICE_NOT_CONNECTED");
      this.mResponseListener.onShowResponseReceived(item, ci, ERROR.SERVICE_NOT_CONNECTED);
      return;
    }
    if (item == 0) {
      DLog.w_api(TAG_CLASS, "show : ai == null");
      ImageViewer.IImageViewerResponseListener iImageViewerResponseListener =
          this.mResponseListener;
      if (iImageViewerResponseListener != null) {
        iImageViewerResponseListener.onShowResponseReceived(item, ci, ERROR.INVALID_ARGUMENT);
      }
      return;
    }
    Item.MediaType type = item.getType();
    if (type == null) {
      DLog.w_api(TAG_CLASS, "show : type == null");
      ImageViewer.IImageViewerResponseListener iImageViewerResponseListener2 =
          this.mResponseListener;
      if (iImageViewerResponseListener2 != null) {
        iImageViewerResponseListener2.onShowResponseReceived(item, ci, ERROR.INVALID_ARGUMENT);
        return;
      }
      return;
    }
    this.mContentChangedNotified = false;
    switch (C48623.$SwitchMap$com$samsung$android$allshare$Item$MediaType[type.ordinal()]) {
      case 1:
        String itemConstructorKey = "MEDIA_SERVER";
        if (item instanceof IBundleHolder) {
          Bundle bundle = ((IBundleHolder) item).getBundle();
          itemConstructorKey = bundle.getString(AllShareKey.BUNDLE_STRING_ITEM_CONSTRUCTOR_KEY);
          if (itemConstructorKey == null) {
            itemConstructorKey = "MEDIA_SERVER";
          }
        }
        if (itemConstructorKey.equals("MEDIA_SERVER")) {
          ArrayList<String> uris = new ArrayList<>();
          ArrayList<Item.Resource> rsrcs = item.getResourceList();
          Uri thumb_uri = item.getThumbnail();
          if (thumb_uri != null) {
            uris.add(thumb_uri.toString());
          }
          Iterator<Item.Resource> it = rsrcs.iterator();
          while (it.hasNext()) {
            Item.Resource rsrc = it.next();
            Uri rsc_uri = rsrc.getURI();
            if (rsc_uri != null) {
              uris.add(rsc_uri.toString());
            }
          }
          Uri uri = item.getURI();
          if (uri != null) {
            uris.add(uri.toString());
          }
          this.mPlayingContentUris.add(uris);
          showMediaContent(item, ci);
          break;
        } else if (itemConstructorKey.equals("WEB_CONTENT")) {
          ArrayList<String> uris2 = new ArrayList<>();
          ArrayList<Item.Resource> rsrcs2 = item.getResourceList();
          Uri thumb_uri2 = item.getThumbnail();
          if (thumb_uri2 != null) {
            uris2.add(thumb_uri2.toString());
          }
          Iterator<Item.Resource> it2 = rsrcs2.iterator();
          while (it2.hasNext()) {
            Item.Resource rsrc2 = it2.next();
            Uri rsc_uri2 = rsrc2.getURI();
            if (rsc_uri2 != null) {
              uris2.add(rsc_uri2.toString());
            }
          }
          Uri uri2 = item.getURI();
          if (uri2 != null) {
            uris2.add(uri2.toString());
          }
          this.mPlayingContentUris.add(uris2);
          showWebContent(item, ci);
          break;
        } else if (itemConstructorKey.equals("LOCAL_CONTENT")) {
          Uri uri3 = item.getURI();
          if (uri3 == null) {
            DLog.w_api(TAG_CLASS, "show : uri == null");
            ImageViewer.IImageViewerResponseListener iImageViewerResponseListener3 =
                this.mResponseListener;
            if (iImageViewerResponseListener3 != null) {
              iImageViewerResponseListener3.onShowResponseReceived(
                  item, ci, ERROR.INVALID_ARGUMENT);
              break;
            }
          } else {
            String scheme = uri3.getScheme();
            DLog.d_api(TAG_CLASS, "show : scheme = " + scheme);
            if (!scheme.contains("content")) {
              if (scheme.contains("file")) {
                Bundle bundle2 = new Bundle();
                if (item instanceof IBundleHolder) {
                  bundle2 = ((IBundleHolder) item).getBundle();
                }
                String filePath = bundle2.getString(AllShareKey.BUNDLE_STRING_FILEPATH);
                if (!Item.LocalContentBuilder.checkFilePathValid(filePath)) {
                  DLog.w_api(TAG_CLASS, "show : filePath is not valid");
                  ImageViewer.IImageViewerResponseListener iImageViewerResponseListener4 =
                      this.mResponseListener;
                  if (iImageViewerResponseListener4 != null) {
                    iImageViewerResponseListener4.onShowResponseReceived(
                        item, ci, ERROR.INVALID_ARGUMENT);
                    break;
                  }
                }
                ArrayList<String> uris3 = new ArrayList<>();
                uris3.add(filePath);
                this.mPlayingContentUris.add(uris3);
                showLocalContentFileScheme(item, ci);
                break;
              } else {
                ImageViewer.IImageViewerResponseListener iImageViewerResponseListener5 =
                    this.mResponseListener;
                if (iImageViewerResponseListener5 != null) {
                  iImageViewerResponseListener5.onShowResponseReceived(
                      item, ci, ERROR.INVALID_ARGUMENT);
                  break;
                }
              }
            } else {
              String filePath2 = parseUriFilePath(item.getURI());
              ArrayList<String> uris4 = new ArrayList<>();
              uris4.add(filePath2);
              this.mPlayingContentUris.add(uris4);
              showLocalContentContentScheme(item, ci);
              break;
            }
          }
        } else {
          DLog.w_api(TAG_CLASS, "show : fail - INVALID ARG ");
          break;
        }
        break;
      default:
        DLog.w_api(TAG_CLASS, "show : Invalid media type");
        ImageViewer.IImageViewerResponseListener iImageViewerResponseListener6 =
            this.mResponseListener;
        if (iImageViewerResponseListener6 != null) {
          iImageViewerResponseListener6.onShowResponseReceived(item, ci, ERROR.INVALID_ARGUMENT);
          break;
        }
        break;
    }
  }

  /* renamed from: com.samsung.android.allshare.ImageViewerImpl$3 */
  static /* synthetic */ class C48623 {
    static final /* synthetic */ int[] $SwitchMap$com$samsung$android$allshare$Item$MediaType;

    static {
      int[] iArr = new int[Item.MediaType.values().length];
      $SwitchMap$com$samsung$android$allshare$Item$MediaType = iArr;
      try {
        iArr[Item.MediaType.ITEM_IMAGE.ordinal()] = 1;
      } catch (NoSuchFieldError e) {
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public String parseUriFilePath(Uri uri) {
    Context scContext;
    ContentResolver cr;
    Cursor cursor;
    if (uri == null
        || (scContext = ServiceConnector.getContext()) == null
        || (cr = scContext.getContentResolver()) == null
        || (cursor = cr.query(uri, null, null, null, null)) == null) {
      return null;
    }
    cursor.moveToFirst();
    String str = cursor.getString(1);
    cursor.close();
    return str;
  }

  /* JADX WARN: Multi-variable type inference failed */
  private void showWebContent(Item item, ContentInfo ci) {
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      DLog.w_api(TAG_CLASS, "showWebContent : SERVICE_NOT_CONNECTED");
      this.mResponseListener.onShowResponseReceived(item, ci, ERROR.SERVICE_NOT_CONNECTED);
      return;
    }
    if (item == 0) {
      DLog.w_api(TAG_CLASS, "showLocalContentContentScheme Fail :  Item does not exist ");
      this.mResponseListener.onShowResponseReceived(item, ci, ERROR.ITEM_NOT_EXIST);
      return;
    }
    CVMessage cvm = new CVMessage();
    cvm.setActionID(AllShareAction.ACTION_IMAGE_VIEWER_SHOW_URI);
    Bundle bundle = new Bundle();
    bundle.putString("BUNDLE_STRING_ID", getID());
    if (item instanceof IBundleHolder) {
      bundle.putParcelable(AllShareKey.BUNDLE_PARCELABLE_ITEM, ((IBundleHolder) item).getBundle());
    }
    bundle.putLong(
        AllShareKey.BUNDLE_LONG_CONTENT_INFO_STARTINGPOSITION,
        ci != null ? ci.getStartingPosition() : 0L);
    cvm.setBundle(bundle);
    this.mAllShareConnector.requestCVMAsync(cvm, this.mResponseHandler);
    DLog.i_api(
        TAG_CLASS,
        "showWebContent : [ "
            + item.getTitle()
            + " ]  to "
            + getName()
            + " uri : "
            + item.getURI());
  }

  /* JADX WARN: Multi-variable type inference failed */
  private void showLocalContentContentScheme(Item item, ContentInfo ci) {
    DLog.v_api(TAG_CLASS, "showLocalContentContentScheme()");
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      DLog.w_api(TAG_CLASS, "showLocalContentContentScheme Fail :  SERVICE_NOT_CONNECTED ");
      this.mResponseListener.onShowResponseReceived(item, ci, ERROR.SERVICE_NOT_CONNECTED);
      return;
    }
    if (item == 0) {
      DLog.w_api(TAG_CLASS, "showLocalContentContentScheme Fail :  Item does not exist ");
      this.mResponseListener.onShowResponseReceived(item, ci, ERROR.ITEM_NOT_EXIST);
      return;
    }
    Uri uri = item.getURI();
    if (uri == null) {
      DLog.w_api(TAG_CLASS, "showLocalContentContentScheme Fail :  uri == null ");
      this.mResponseListener.onShowResponseReceived(item, ci, ERROR.SERVICE_NOT_CONNECTED);
      return;
    }
    ContentResolver resolver = this.mAllShareConnector.getContentResolver();
    if (resolver == null) {
      DLog.w_api(TAG_CLASS, "showLocalContentContentScheme Fail :  resolver == null ");
      this.mResponseListener.onShowResponseReceived(item, ci, ERROR.SERVICE_NOT_CONNECTED);
      return;
    }
    Cursor cur = resolver.query(uri, null, null, null, null);
    if (cur == null) {
      DLog.w_api(
          TAG_CLASS, "showLocalContentContentScheme Fail :  INVALID_ARGUMENT (cur == null) ");
      this.mResponseListener.onShowResponseReceived(item, ci, ERROR.INVALID_ARGUMENT);
      return;
    }
    cur.moveToNext();
    int idx = cur.getColumnIndex("_data");
    if (idx < 0) {
      DLog.w_api(TAG_CLASS, "showLocalContentContentScheme Fail :  INVALID_ARGUMENT(idx < 0)");
      this.mResponseListener.onShowResponseReceived(item, ci, ERROR.INVALID_ARGUMENT);
      cur.close();
      return;
    }
    PlaylistPlayer playlistPlayer = this.mPlaylistPlayer;
    if (playlistPlayer != null) {
      ((PlaylistPlayerImpl) playlistPlayer).setCurrentFilePath(cur.getString(idx));
    }
    cur.close();
    CVMessage cvm = new CVMessage();
    cvm.setActionID(AllShareAction.ACTION_IMAGE_VIEWER_SHOW_LOCAL_CONTENT_URI);
    Bundle bundle = new Bundle();
    bundle.putString("BUNDLE_STRING_ID", getID());
    if (item instanceof IBundleHolder) {
      bundle.putParcelable(AllShareKey.BUNDLE_PARCELABLE_ITEM, ((IBundleHolder) item).getBundle());
    }
    bundle.putLong(
        AllShareKey.BUNDLE_LONG_CONTENT_INFO_STARTINGPOSITION,
        ci != null ? ci.getStartingPosition() : 0L);
    cvm.setBundle(bundle);
    this.mAllShareConnector.requestCVMAsync(cvm, this.mResponseHandler);
    DLog.i_api(
        TAG_CLASS,
        "showLocalContentContentScheme : [ "
            + item.getTitle()
            + " ]  to "
            + getName()
            + " uri : "
            + uri);
  }

  /* JADX WARN: Multi-variable type inference failed */
  private void showMediaContent(Item item, ContentInfo ci) {
    DLog.v_api(TAG_CLASS, "showMediaContent()");
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      DLog.w_api(TAG_CLASS, "showMediaContent : SERVICE_NOT_CONNECTED");
      this.mResponseListener.onShowResponseReceived(item, ci, ERROR.SERVICE_NOT_CONNECTED);
      return;
    }
    CVMessage cvm = new CVMessage();
    cvm.setActionID(AllShareAction.ACTION_IMAGE_VIEWER_SHOW);
    Bundle bundle = new Bundle();
    bundle.putString("BUNDLE_STRING_ID", getID());
    if (item instanceof IBundleHolder) {
      bundle.putParcelable(AllShareKey.BUNDLE_PARCELABLE_ITEM, ((IBundleHolder) item).getBundle());
    }
    bundle.putLong(
        AllShareKey.BUNDLE_LONG_CONTENT_INFO_STARTINGPOSITION,
        ci != null ? ci.getStartingPosition() : 0L);
    cvm.setBundle(bundle);
    this.mAllShareConnector.requestCVMAsync(cvm, this.mResponseHandler);
    DLog.i_api(TAG_CLASS, "showMediaContent : " + item.getTitle() + " to " + getName());
  }

  /* JADX WARN: Multi-variable type inference failed */
  private void showLocalContentFileScheme(Item item, ContentInfo ci) {
    DLog.v_api(TAG_CLASS, "showLocalContentFileScheme()");
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      DLog.w_api(TAG_CLASS, "showLocalContentFileScheme : SERVICE_NOT_CONNECTED");
      this.mResponseListener.onShowResponseReceived(item, ci, ERROR.SERVICE_NOT_CONNECTED);
      return;
    }
    String filePath = "";
    String mimeType = "";
    if (item instanceof IBundleHolder) {
      Bundle bundle = ((IBundleHolder) item).getBundle();
      filePath = bundle.getString(AllShareKey.BUNDLE_STRING_FILEPATH);
      mimeType = bundle.getString(AllShareKey.BUNDLE_STRING_ITEM_MIMETYPE);
    }
    PlaylistPlayer playlistPlayer = this.mPlaylistPlayer;
    if (playlistPlayer != null) {
      ((PlaylistPlayerImpl) playlistPlayer).setCurrentFilePath(filePath);
    }
    CVMessage req_msg = new CVMessage();
    req_msg.setActionID(AllShareAction.ACTION_IMAGE_VIEWER_SHOW_LOCAL_CONTENT);
    Bundle bundle2 = new Bundle();
    bundle2.putString("BUNDLE_STRING_ID", getID());
    bundle2.putString(AllShareKey.BUNDLE_STRING_FILEPATH, filePath);
    bundle2.putString(AllShareKey.BUNDLE_STRING_ITEM_MIMETYPE, mimeType);
    bundle2.putString(AllShareKey.BUNDLE_STRING_TITLE, item.getTitle());
    if (item instanceof IBundleHolder) {
      bundle2.putParcelable(AllShareKey.BUNDLE_PARCELABLE_ITEM, ((IBundleHolder) item).getBundle());
    }
    bundle2.putLong(
        AllShareKey.BUNDLE_LONG_CONTENT_INFO_STARTINGPOSITION,
        ci != null ? ci.getStartingPosition() : 0L);
    req_msg.setBundle(bundle2);
    this.mAllShareConnector.requestCVMAsync(req_msg, this.mResponseHandler);
    DLog.i_api(
        TAG_CLASS,
        "showLocalContentFileScheme : ["
            + mimeType
            + NavigationBarInflaterView.SIZE_MOD_END
            + item.getTitle()
            + NavigationBarInflaterView.KEY_CODE_START
            + filePath
            + ") to "
            + getName()
            + NavigationBarInflaterView.KEY_CODE_START
            + getIPAddress()
            + NavigationBarInflaterView.KEY_CODE_END);
  }

  @Override // com.samsung.android.allshare.media.ImageViewer
  public void stop() {
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      DLog.w_api(TAG_CLASS, "stop : SERVICE_NOT_CONNECTED");
      this.mResponseListener.onStopResponseReceived(ERROR.SERVICE_NOT_CONNECTED);
      return;
    }
    CVMessage req_msg = new CVMessage();
    req_msg.setActionID(AllShareAction.ACTION_IMAGE_VIEWER_STOP);
    Bundle req_bundle = new Bundle();
    req_bundle.putString("BUNDLE_STRING_ID", getID());
    req_msg.setBundle(req_bundle);
    this.mAllShareConnector.requestCVMAsync(req_msg, this.mResponseHandler);
    DLog.i_api(TAG_CLASS, "stop : " + getName());
  }

  @Override // com.samsung.android.allshare.media.ImageViewer
  public ImageViewer.ImageViewerState getViewerState() {
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      return ImageViewer.ImageViewerState.UNKNOWN;
    }
    Bundle parmBundle = new Bundle();
    Bundle b = getBundle();
    if (b == null) {
      return ImageViewer.ImageViewerState.UNKNOWN;
    }
    String id = b.getString("BUNDLE_STRING_ID");
    if (id == null) {
      return ImageViewer.ImageViewerState.UNKNOWN;
    }
    parmBundle.putString("BUNDLE_STRING_ID", id);
    CVMessage req_msg = new CVMessage();
    req_msg.setActionID(AllShareAction.ACTION_IMAGE_VIEWER_GET_VIEWER_STATE_SYNC);
    req_msg.setBundle(parmBundle);
    CVMessage res_message = this.mAllShareConnector.requestCVMSync(req_msg);
    if (res_message == null) {
      return ImageViewer.ImageViewerState.UNKNOWN;
    }
    Bundle res_bundle = res_message.getBundle();
    if (res_bundle == null) {
      return ImageViewer.ImageViewerState.UNKNOWN;
    }
    return ImageViewer.ImageViewerState.stringToEnum(
        res_bundle.getString(AllShareKey.BUNDLE_STRING_IMAGE_VIEWEW_STATE));
  }

  @Override // com.samsung.android.allshare.media.ImageViewer
  public void setResponseListener(ImageViewer.IImageViewerResponseListener l) {
    DLog.d_api(TAG_CLASS, "setResponseListener to " + l);
    this.mResponseListener = l;
  }

  @Override // com.samsung.android.allshare.media.ImageViewer
  public void setEventListener(ImageViewer.IImageViewerEventListener l) {
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      DLog.w_api(TAG_CLASS, "setEventListener error! AllShareService is not connected");
      return;
    }
    DLog.d_api(TAG_CLASS, "setEventListener to " + l);
    this.mEventListener = l;
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

  @Override // com.samsung.android.allshare.media.ImageViewer, com.samsung.android.allshare.Device
  public Device.DeviceDomain getDeviceDomain() {
    DeviceImpl deviceImpl = this.mDeviceImpl;
    if (deviceImpl == null) {
      return Device.DeviceDomain.UNKNOWN;
    }
    return deviceImpl.getDeviceDomain();
  }

  @Override // com.samsung.android.allshare.media.ImageViewer, com.samsung.android.allshare.Device
  public String getID() {
    DeviceImpl deviceImpl = this.mDeviceImpl;
    if (deviceImpl == null) {
      return "";
    }
    return deviceImpl.getID();
  }

  @Override // com.samsung.android.allshare.media.ImageViewer, com.samsung.android.allshare.Device
  public Uri getIcon() {
    DeviceImpl deviceImpl = this.mDeviceImpl;
    if (deviceImpl == null) {
      return null;
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

  @Override // com.samsung.android.allshare.media.ImageViewer, com.samsung.android.allshare.Device
  public String getName() {
    DeviceImpl deviceImpl = this.mDeviceImpl;
    if (deviceImpl == null) {
      return "";
    }
    return deviceImpl.getName();
  }

  @Override // com.samsung.android.allshare.media.ImageViewer, com.samsung.android.allshare.Device
  public Device.DeviceType getDeviceType() {
    DeviceImpl deviceImpl = this.mDeviceImpl;
    if (deviceImpl == null) {
      return Device.DeviceType.UNKNOWN;
    }
    Device.DeviceType result = deviceImpl.getDeviceType();
    return result;
  }

  @Override // com.samsung.android.allshare.media.ImageViewer, com.samsung.android.allshare.Device
  public String getModelName() {
    DeviceImpl deviceImpl = this.mDeviceImpl;
    if (deviceImpl == null) {
      return "";
    }
    return deviceImpl.getModelName();
  }

  @Override // com.samsung.android.allshare.media.ImageViewer, com.samsung.android.allshare.Device
  @Deprecated
  public String getIPAdress() {
    return getIPAddress();
  }

  @Override // com.samsung.android.allshare.media.ImageViewer, com.samsung.android.allshare.Device
  public String getIPAddress() {
    DeviceImpl deviceImpl = this.mDeviceImpl;
    if (deviceImpl == null) {
      return "";
    }
    return deviceImpl.getIPAddress();
  }

  @Override // com.sec.android.allshare.iface.IBundleHolder
  public Bundle getBundle() {
    DeviceImpl deviceImpl = this.mDeviceImpl;
    if (deviceImpl == null) {
      return null;
    }
    return deviceImpl.getBundle();
  }

  @Override // com.samsung.android.allshare.media.ImageViewer
  public PlaylistPlayer getPlaylistPlayer() {
    return this.mPlaylistPlayer;
  }

  @Override // com.samsung.android.allshare.media.ImageViewer
  public ViewController getViewController() {
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      return null;
    }
    Bundle parmBundle = new Bundle();
    Bundle b = getBundle();
    if (b == null) {
      DLog.w_api(TAG_CLASS, "getViewController : bundle is Null");
      return null;
    }
    String deviceId = b.getString(AllShareKey.BUNDLE_STRING_DEVICE_ID);
    if (deviceId == null || deviceId.length() == 0) {
      DLog.w_api(TAG_CLASS, "getViewController : deviceId is Null");
      return null;
    }
    parmBundle.putString(AllShareKey.BUNDLE_STRING_DEVICE_ID, deviceId);
    CVMessage req_msg = new CVMessage();
    req_msg.setActionID(AllShareAction.ACTION_IMAGE_VIEWER_SET_VIEW_CONTROLLER_SYNC);
    req_msg.setBundle(parmBundle);
    CVMessage res_message = this.mAllShareConnector.requestCVMSync(req_msg);
    if (res_message == null) {
      DLog.w_api(TAG_CLASS, "res_message is Null");
      return null;
    }
    Bundle res_bundle = res_message.getBundle();
    if (res_bundle == null) {
      DLog.w_api(TAG_CLASS, "res_bundle is Null");
      return null;
    }
    int nTvWidth = res_bundle.getInt(AllShareKey.BUNDLE_INT_TV_WIDTH_RESOLUTION);
    int nTvHeight = res_bundle.getInt(AllShareKey.BUNDLE_INT_TV_HEIGHT_RESOLUTION);
    boolean isZoomable = res_bundle.getBoolean(AllShareKey.BUNDLE_BOOLEAN_ZOOMABLE);
    boolean isRotatable = res_bundle.getBoolean(AllShareKey.BUNDLE_BOOLEAN_ROTATABLE);
    if (!isZoomable || !isRotatable) {
      return null;
    }
    if (this.mViewController == null) {
      this.mViewController =
          new ViewControllerImpl(this.mAllShareConnector, this.mDeviceImpl, nTvWidth, nTvHeight);
    }
    return this.mViewController;
  }

  @Override // com.samsung.android.allshare.media.ImageViewer
  public ViewController2 getViewController2() {
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      return null;
    }
    Bundle parmBundle = new Bundle();
    Bundle b = getBundle();
    if (b == null) {
      DLog.w_api(TAG_CLASS, "getViewController2 : bundle is Null");
      return null;
    }
    parmBundle.putString("BUNDLE_STRING_ID", getID());
    CVMessage req_msg = new CVMessage();
    req_msg.setActionID(AllShareAction.ACTION_IMAGE_VIEWER_GET_VIEW_CONTROLLER2_SYNC);
    req_msg.setBundle(parmBundle);
    CVMessage res_message = this.mAllShareConnector.requestCVMSync(req_msg);
    if (res_message == null) {
      DLog.w_api(TAG_CLASS, "res_message is Null");
      return null;
    }
    Bundle res_bundle = res_message.getBundle();
    if (res_bundle == null) {
      DLog.w_api(TAG_CLASS, "res_bundle is Null");
      return null;
    }
    boolean isSupport = res_bundle.getBoolean(AllShareKey.BUNDLE_BOOLEAN_SUPPORT_SPC_IMAGEZOOM);
    if (!isSupport) {
      return null;
    }
    if (this.mViewController2 == null) {
      this.mViewController2 = new ViewController2Impl(this.mAllShareConnector, this.mDeviceImpl);
    }
    return this.mViewController2;
  }

  @Override // com.samsung.android.allshare.media.ImageViewer
  public SlideShowPlayer getSlideShowPlayer() {
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      return null;
    }
    Bundle parmBundle = new Bundle();
    Bundle b = getBundle();
    if (b == null) {
      DLog.w_api(TAG_CLASS, "getSlideShowPlayer : bundle is Null");
      return null;
    }
    parmBundle.putString("BUNDLE_STRING_ID", getID());
    CVMessage req_msg = new CVMessage();
    req_msg.setActionID(AllShareAction.ACTION_IMAGE_VIEWER_GET_SLIDESHOWPLAYER_SYNC);
    req_msg.setBundle(parmBundle);
    CVMessage res_message = this.mAllShareConnector.requestCVMSync(req_msg);
    if (res_message == null) {
      DLog.w_api(TAG_CLASS, "res_message is Null");
      return null;
    }
    Bundle res_bundle = res_message.getBundle();
    if (res_bundle == null) {
      DLog.w_api(TAG_CLASS, "res_bundle is Null");
      return null;
    }
    boolean isSupport = res_bundle.getBoolean(AllShareKey.BUNDLE_BOOLEAN_SUPPORT_SPC_SLIDESHOW);
    if (!isSupport) {
      return null;
    }
    if (this.mSlideShowPlayer == null) {
      this.mSlideShowPlayer = new SlideShowPlayerImpl(this.mAllShareConnector, this.mDeviceImpl);
    }
    return this.mSlideShowPlayer;
  }

  @Override // com.samsung.android.allshare.media.ImageViewer
  public void getState() {
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      this.mResponseListener.onGetStateResponseReceived(
          ImageViewer.ImageViewerState.UNKNOWN, ERROR.SERVICE_NOT_CONNECTED);
      return;
    }
    CVMessage req_msg = new CVMessage();
    req_msg.setActionID(AllShareAction.ACTION_IMAGE_VIEWER_REQUEST_GET_VIEWER_STATE);
    Bundle req_bundle = new Bundle();
    req_bundle.putString("BUNDLE_STRING_ID", getID());
    req_msg.setBundle(req_bundle);
    this.mAllShareConnector.requestCVMAsync(req_msg, this.mResponseHandler);
  }

  @Override // com.sec.android.allshare.iface.IHandlerHolder
  public void removeEventHandler() {
    this.mAllShareConnector.unsubscribeAllShareEvent(
        AllShareEvent.EVENT_DEVICE_SUBSCRIBE, getBundle(), this.mEventHandler);
    this.mIsSubscribed = false;
    ViewController viewController = this.mViewController;
    if (viewController != null) {
      ((ViewControllerImpl) viewController).removeEventHandler();
    }
    ViewController2 viewController2 = this.mViewController2;
    if (viewController2 != null) {
      ((ViewController2Impl) viewController2).removeEventHandler();
    }
    SlideShowPlayer slideShowPlayer = this.mSlideShowPlayer;
    if (slideShowPlayer != null) {
      ((SlideShowPlayerImpl) slideShowPlayer).removeEventHandler();
    }
    PlaylistPlayer playlistPlayer = this.mPlaylistPlayer;
    if (playlistPlayer != null) {
      ((PlaylistPlayerImpl) playlistPlayer).removeEventHandler();
    }
  }

  @Override // com.samsung.android.allshare.media.ImageViewer
  public boolean isSupportRedirect() {
    Bundle res_bundle;
    IAllShareConnector iAllShareConnector = this.mAllShareConnector;
    if (iAllShareConnector == null || !iAllShareConnector.isAllShareServiceConnected()) {
      return false;
    }
    CVMessage req_msg = new CVMessage();
    req_msg.setActionID(AllShareAction.ACTION_IMAGE_VIEWER_IS_SUPPORT_REDIRECT_SYNC);
    Bundle req_bundle = new Bundle();
    req_bundle.putString("BUNDLE_STRING_ID", getID());
    req_msg.setBundle(req_bundle);
    CVMessage res_msg = this.mAllShareConnector.requestCVMSync(req_msg);
    if (res_msg == null || (res_bundle = res_msg.getBundle()) == null) {
      return false;
    }
    String err = res_bundle.getString("BUNDLE_ENUM_ERROR");
    if (err != null && ERROR.NOT_SUPPORTED_FRAMEWORK_VERSION.enumToString().equals(err)) {
      DLog.w_api(TAG_CLASS, " isRedirectSupportable() Exception : NOT_SUPPORTED_FRAMEWORK_VERSION");
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

  @Override // com.samsung.android.allshare.media.ImageViewer
  @Deprecated
  public boolean isRedirectSupportable() {
    return isSupportRedirect();
  }

  @Override // com.samsung.android.allshare.Device
  public boolean isSeekableOnPaused() {
    return false;
  }

  @Override // com.samsung.android.allshare.Device
  public boolean isWholeHomeAudio() {
    return false;
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

  @Override // com.samsung.android.allshare.Device
  public boolean isSupportedByType(int type) {
    DeviceImpl deviceImpl = this.mDeviceImpl;
    if (deviceImpl == null) {
      return false;
    }
    return deviceImpl.isSupportedByType(type);
  }
}
