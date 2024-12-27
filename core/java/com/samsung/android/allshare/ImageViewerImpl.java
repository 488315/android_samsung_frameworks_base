package com.samsung.android.allshare;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.os.Bundle;
import com.samsung.android.allshare.Device;
import com.samsung.android.allshare.Item;
import com.samsung.android.allshare.media.ContentInfo;
import com.samsung.android.allshare.media.ImageViewer;
import com.sec.android.allshare.iface.CVMessage;
import com.sec.android.allshare.iface.IBundleHolder;
import com.sec.android.allshare.iface.IHandlerHolder;
import com.sec.android.allshare.iface.message.AllShareAction;
import com.sec.android.allshare.iface.message.AllShareEvent;
import com.sec.android.allshare.iface.message.AllShareKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/* loaded from: classes3.dex */
final class ImageViewerImpl extends ImageViewer implements IBundleHolder, IHandlerHolder {
    private static final String TAG_CLASS = "ImageViewerImpl";
    private IAllShareConnector mAllShareConnector;
    private DeviceImpl mDeviceImpl;
    private ImageViewer.IImageViewerResponseListener mResponseListener = null;
    private ImageViewer.IImageViewerEventListener mEventListener = null;
    private boolean mIsSubscribed = false;
    private boolean mContentChangedNotified = true;
    private ArrayList<ArrayList<String>> mPlayingContentUris = new ArrayList<>();
    private String mCurrentDMRUri = null;
    AllShareEventHandler mEventHandler = new AllShareEventHandler(ServiceConnector.getMainLooper()) { // from class: com.samsung.android.allshare.ImageViewerImpl.1
        private HashMap<String, ImageViewer.ImageViewerState> mStateMap = new HashMap<>();

        {
            this.mStateMap.put(AllShareEvent.EVENT_RENDERER_STATE_BUFFERING, ImageViewer.ImageViewerState.BUFFERING);
            this.mStateMap.put(AllShareEvent.EVENT_RENDERER_STATE_NOMEDIA, ImageViewer.ImageViewerState.STOPPED);
            this.mStateMap.put(AllShareEvent.EVENT_RENDERER_STATE_PAUSED, ImageViewer.ImageViewerState.SHOWING);
            this.mStateMap.put(AllShareEvent.EVENT_RENDERER_STATE_STOPPED, ImageViewer.ImageViewerState.STOPPED);
            this.mStateMap.put(AllShareEvent.EVENT_RENDERER_STATE_PLAYING, ImageViewer.ImageViewerState.SHOWING);
            this.mStateMap.put(AllShareEvent.EVENT_RENDERER_STATE_CONTENT_CHANGED, ImageViewer.ImageViewerState.CONTENT_CHANGED);
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
                        if (ImageViewerImpl.this.mCurrentDMRUri == null || !currentTrackUri.equalsIgnoreCase(ImageViewerImpl.this.mCurrentDMRUri)) {
                            DLog.d_api(ImageViewerImpl.TAG_CLASS, "CONTENT_CHANGED, mCurrentDMRUri : " + ImageViewerImpl.this.mCurrentDMRUri + "  currentTrackUri : " + currentTrackUri);
                            if (ImageViewerImpl.this.mCurrentDMRUri == null) {
                                DLog.d_api(ImageViewerImpl.TAG_CLASS, "do not notify CONTENT_CHANGED event, mCurrentDMRUri is null");
                                ImageViewerImpl.this.mCurrentDMRUri = currentTrackUri;
                                return;
                            }
                            ImageViewerImpl.this.mCurrentDMRUri = currentTrackUri;
                            if (ImageViewerImpl.this.mPlayingContentUris != null && !ImageViewerImpl.this.mPlayingContentUris.isEmpty()) {
                                if (isContains(currentTrackUri)) {
                                    DLog.d_api(ImageViewerImpl.TAG_CLASS, "handleEventMessage: this is playing content.");
                                    DLog.i_api(ImageViewerImpl.TAG_CLASS, "do not notify CONTENT_CHANGED event, this is my=" + currentTrackUri);
                                    return;
                                } else {
                                    ImageViewerImpl.this.mContentChangedNotified = true;
                                    DLog.w_api(ImageViewerImpl.TAG_CLASS, "Notify CONTENT_CHANGED event, mPlayingContentUris[" + ImageViewerImpl.this.mPlayingContentUris + "] vs currentTrackUri[" + currentTrackUri + NavigationBarInflaterView.SIZE_MOD_END);
                                }
                            }
                            DLog.d_api(ImageViewerImpl.TAG_CLASS, "do not notify CONTENT_CHANGED event, mPlayingContentUris is null");
                            return;
                        }
                        DLog.d_api(ImageViewerImpl.TAG_CLASS, "do not notify CONTENT_CHANGED event, mCurrentDMRUri is same as currentTrackUri " + currentTrackUri);
                        return;
                    }
                    DLog.d_api(ImageViewerImpl.TAG_CLASS, "do not notify CONTENT_CHANGED event, currentTrackUri is null");
                    return;
                }
                notifyEvent(state, error2);
            } catch (Error err) {
                DLog.w_api(ImageViewerImpl.TAG_CLASS, "mEventHandler.handleEventMessage Error", err);
            } catch (Exception e) {
                DLog.w_api(ImageViewerImpl.TAG_CLASS, "mEventHandler.handleEventMessage Fail to notify event");
            }
        }

        private void notifyEvent(ImageViewer.ImageViewerState state, ERROR error) {
            if (ImageViewerImpl.this.mEventListener != null) {
                try {
                    DLog.v_api(ImageViewerImpl.TAG_CLASS, "mEventHandler.notifyEvent to " + ImageViewerImpl.this.mEventListener + " state[" + state.enumToString() + "] error[" + error.enumToString() + NavigationBarInflaterView.SIZE_MOD_END);
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
    AllShareResponseHandler mResponseHandler = new AllShareResponseHandler(ServiceConnector.getMainLooper()) { // from class: com.samsung.android.allshare.ImageViewerImpl.2
        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.samsung.android.allshare.AllShareResponseHandler
        public void handleResponseMessage(CVMessage cvm) {
            String actionID = cvm.getActionID();
            Bundle resBundle = cvm.getBundle();
            if (actionID == null || resBundle == null) {
                DLog.w_api(ImageViewerImpl.TAG_CLASS, "handleResponseMessage : actionID == null || resBundle == null");
                return;
            }
            ERROR error = ERROR.FAIL;
            String errorStr = resBundle.getString("BUNDLE_ENUM_ERROR");
            if (errorStr != null) {
                error = ERROR.stringToEnum(errorStr);
            }
            long contentInfoStartingPosition = resBundle.getLong(AllShareKey.BUNDLE_LONG_CONTENT_INFO_STARTINGPOSITION);
            ContentInfo.Builder cb = new ContentInfo.Builder();
            ContentInfo info = cb.setStartingPosition(contentInfoStartingPosition).build();
            Bundle itemBundle = (Bundle) resBundle.getParcelable(AllShareKey.BUNDLE_PARCELABLE_ITEM);
            Item fromBundle = ItemCreator.fromBundle(itemBundle);
            if (actionID.equals(AllShareAction.ACTION_IMAGE_VIEWER_SHOW_LOCAL_CONTENT) || actionID.equals(AllShareAction.ACTION_IMAGE_VIEWER_SHOW_LOCAL_CONTENT_URI) || actionID.equals(AllShareAction.ACTION_IMAGE_VIEWER_SHOW_URI) || actionID.equals(AllShareAction.ACTION_IMAGE_VIEWER_SHOW)) {
                if (error.equals(ERROR.SUCCESS)) {
                    ImageViewerImpl.this.mContentChangedNotified = false;
                } else if (fromBundle != 0) {
                    if (actionID.equals(AllShareAction.ACTION_IMAGE_VIEWER_SHOW_URI) || actionID.equals(AllShareAction.ACTION_IMAGE_VIEWER_SHOW)) {
                        removeUri(fromBundle.getURI().toString());
                    } else if (actionID.equals(AllShareAction.ACTION_IMAGE_VIEWER_SHOW_LOCAL_CONTENT)) {
                        Bundle bundle = new Bundle();
                        if (fromBundle instanceof IBundleHolder) {
                            bundle = ((IBundleHolder) fromBundle).getBundle();
                        }
                        String filePath = bundle.getString(AllShareKey.BUNDLE_STRING_FILEPATH);
                        removeUri(filePath);
                    } else if (actionID.equals(AllShareAction.ACTION_IMAGE_VIEWER_SHOW_LOCAL_CONTENT_URI)) {
                        String filePath2 = ImageViewerImpl.this.parseUriFilePath(fromBundle.getURI());
                        removeUri(filePath2);
                    }
                }
            }
            if (ImageViewerImpl.this.mResponseListener == null) {
                DLog.w_api(ImageViewerImpl.TAG_CLASS, "handleResponseMessage : mResponseListener == null");
                return;
            }
            if (actionID.equals(AllShareAction.ACTION_IMAGE_VIEWER_SHOW) || actionID.equals(AllShareAction.ACTION_IMAGE_VIEWER_SHOW_LOCAL_CONTENT) || actionID.equals(AllShareAction.ACTION_IMAGE_VIEWER_SHOW_LOCAL_CONTENT_URI) || actionID.equals(AllShareAction.ACTION_IMAGE_VIEWER_SHOW_URI)) {
                if (fromBundle == 0) {
                    ImageViewerImpl.this.mResponseListener.onShowResponseReceived(fromBundle, info, ERROR.ITEM_NOT_EXIST);
                    return;
                } else {
                    ImageViewerImpl.this.mResponseListener.onShowResponseReceived(fromBundle, info, error);
                    return;
                }
            }
            if (actionID.equals(AllShareAction.ACTION_IMAGE_VIEWER_STOP)) {
                ImageViewerImpl.this.mResponseListener.onStopResponseReceived(error);
            } else if (actionID.equals(AllShareAction.ACTION_IMAGE_VIEWER_REQUEST_GET_VIEWER_STATE)) {
                String state = resBundle.getString(AllShareKey.BUNDLE_STRING_IMAGE_VIEWEW_STATE);
                ImageViewer.ImageViewerState viewerState = ImageViewer.ImageViewerState.stringToEnum(state);
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
        }
    }

    @Override // com.samsung.android.allshare.Device
    public String getNIC() {
        if (this.mDeviceImpl == null) {
            return "";
        }
        return this.mDeviceImpl.getNIC();
    }

    @Override // com.samsung.android.allshare.media.ImageViewer
    public void zoom(int x, int y, int sourceWidth, int sourceHeight) {
        if (this.mAllShareConnector == null || !this.mAllShareConnector.isAllShareServiceConnected()) {
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
        DLog.i_api(TAG_CLASS, "zoom_ScreenSharing : [ x : " + x + " y : " + y + " width : " + sourceWidth + " height : " + sourceHeight + " ] ");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.samsung.android.allshare.media.ImageViewer
    public void show(Item item, ContentInfo ci) {
        DLog.i_api(TAG_CLASS, "show() is called");
        if (this.mAllShareConnector == null || !this.mAllShareConnector.isAllShareServiceConnected()) {
            DLog.w_api(TAG_CLASS, "show : SERVICE_NOT_CONNECTED");
            this.mResponseListener.onShowResponseReceived(item, ci, ERROR.SERVICE_NOT_CONNECTED);
            return;
        }
        if (item == 0) {
            DLog.w_api(TAG_CLASS, "show : ai == null");
            if (this.mResponseListener != null) {
                this.mResponseListener.onShowResponseReceived(item, ci, ERROR.INVALID_ARGUMENT);
                return;
            }
            return;
        }
        this.mContentChangedNotified = false;
        String itemConstructorKey = "LOCAL_CONTENT";
        if (item instanceof IBundleHolder) {
            Bundle bundle = ((IBundleHolder) item).getBundle();
            itemConstructorKey = bundle.getString(AllShareKey.BUNDLE_STRING_ITEM_CONSTRUCTOR_KEY);
            if (itemConstructorKey == null) {
                itemConstructorKey = "LOCAL_CONTENT";
            }
        }
        if (itemConstructorKey.equals("WEB_CONTENT")) {
            ArrayList<String> uris = new ArrayList<>();
            Uri thumb_uri = item.getThumbnail();
            if (thumb_uri != null) {
                uris.add(thumb_uri.toString());
            }
            Uri uri = item.getURI();
            if (uri != null) {
                uris.add(uri.toString());
            }
            this.mPlayingContentUris.add(uris);
            showWebContent(item, ci);
            return;
        }
        if (itemConstructorKey.equals("LOCAL_CONTENT")) {
            Uri uri2 = item.getURI();
            if (uri2 == null) {
                DLog.w_api(TAG_CLASS, "show : uri == null");
                if (this.mResponseListener != null) {
                    this.mResponseListener.onShowResponseReceived(item, ci, ERROR.INVALID_ARGUMENT);
                    return;
                }
                return;
            }
            String scheme = uri2.getScheme();
            DLog.d_api(TAG_CLASS, "show : scheme = " + scheme);
            if (scheme.contains("content")) {
                String filePath = parseUriFilePath(item.getURI());
                ArrayList<String> uris2 = new ArrayList<>();
                uris2.add(filePath);
                this.mPlayingContentUris.add(uris2);
                showLocalContentContentScheme(item, ci);
                return;
            }
            if (scheme.contains("file")) {
                Bundle bundle2 = new Bundle();
                if (item instanceof IBundleHolder) {
                    bundle2 = ((IBundleHolder) item).getBundle();
                }
                String filePath2 = bundle2.getString(AllShareKey.BUNDLE_STRING_FILEPATH);
                if (!Item.LocalContentBuilder.checkFilePathValid(filePath2)) {
                    DLog.w_api(TAG_CLASS, "show : filePath is not valid");
                    if (this.mResponseListener != null) {
                        this.mResponseListener.onShowResponseReceived(item, ci, ERROR.INVALID_ARGUMENT);
                        return;
                    }
                }
                ArrayList<String> uris3 = new ArrayList<>();
                uris3.add(filePath2);
                this.mPlayingContentUris.add(uris3);
                showLocalContentFileScheme(item, ci);
                return;
            }
            if (this.mResponseListener != null) {
                this.mResponseListener.onShowResponseReceived(item, ci, ERROR.INVALID_ARGUMENT);
                return;
            }
            return;
        }
        DLog.w_api(TAG_CLASS, "show : fail - INVALID ARG ");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String parseUriFilePath(Uri uri) {
        Context scContext;
        ContentResolver cr;
        Cursor cursor;
        if (uri == null || (scContext = ServiceConnector.getContext()) == null || (cr = scContext.getContentResolver()) == null || (cursor = cr.query(uri, null, null, null, null)) == null) {
            return null;
        }
        cursor.moveToFirst();
        String str = cursor.getString(1);
        cursor.close();
        return str;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void showWebContent(Item item, ContentInfo ci) {
        if (this.mAllShareConnector == null || !this.mAllShareConnector.isAllShareServiceConnected()) {
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
        bundle.putLong(AllShareKey.BUNDLE_LONG_CONTENT_INFO_STARTINGPOSITION, ci != null ? ci.getStartingPosition() : 0L);
        cvm.setBundle(bundle);
        this.mAllShareConnector.requestCVMAsync(cvm, this.mResponseHandler);
        DLog.i_api(TAG_CLASS, "showWebContent : [ " + item.getTitle() + " ]  to " + getName() + " uri : " + item.getURI());
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void showLocalContentContentScheme(Item item, ContentInfo ci) {
        DLog.v_api(TAG_CLASS, "showLocalContentContentScheme()");
        if (this.mAllShareConnector == null || !this.mAllShareConnector.isAllShareServiceConnected()) {
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
            DLog.w_api(TAG_CLASS, "showLocalContentContentScheme Fail :  INVALID_ARGUMENT (cur == null) ");
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
        cur.close();
        CVMessage cvm = new CVMessage();
        cvm.setActionID(AllShareAction.ACTION_IMAGE_VIEWER_SHOW_LOCAL_CONTENT_URI);
        Bundle bundle = new Bundle();
        bundle.putString("BUNDLE_STRING_ID", getID());
        if (item instanceof IBundleHolder) {
            bundle.putParcelable(AllShareKey.BUNDLE_PARCELABLE_ITEM, ((IBundleHolder) item).getBundle());
        }
        bundle.putLong(AllShareKey.BUNDLE_LONG_CONTENT_INFO_STARTINGPOSITION, ci != null ? ci.getStartingPosition() : 0L);
        cvm.setBundle(bundle);
        this.mAllShareConnector.requestCVMAsync(cvm, this.mResponseHandler);
        DLog.i_api(TAG_CLASS, "showLocalContentContentScheme : [ " + item.getTitle() + " ]  to " + getName() + " uri : " + uri);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void showMediaContent(Item item, ContentInfo ci) {
        DLog.v_api(TAG_CLASS, "showMediaContent()");
        if (this.mAllShareConnector == null || !this.mAllShareConnector.isAllShareServiceConnected()) {
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
        bundle.putLong(AllShareKey.BUNDLE_LONG_CONTENT_INFO_STARTINGPOSITION, ci != null ? ci.getStartingPosition() : 0L);
        cvm.setBundle(bundle);
        this.mAllShareConnector.requestCVMAsync(cvm, this.mResponseHandler);
        DLog.i_api(TAG_CLASS, "showMediaContent : " + item.getTitle() + " to " + getName());
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void showLocalContentFileScheme(Item item, ContentInfo ci) {
        DLog.v_api(TAG_CLASS, "showLocalContentFileScheme()");
        if (this.mAllShareConnector == null || !this.mAllShareConnector.isAllShareServiceConnected()) {
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
        bundle2.putLong(AllShareKey.BUNDLE_LONG_CONTENT_INFO_STARTINGPOSITION, ci != null ? ci.getStartingPosition() : 0L);
        req_msg.setBundle(bundle2);
        this.mAllShareConnector.requestCVMAsync(req_msg, this.mResponseHandler);
        DLog.i_api(TAG_CLASS, "showLocalContentFileScheme : [" + mimeType + NavigationBarInflaterView.SIZE_MOD_END + item.getTitle() + NavigationBarInflaterView.KEY_CODE_START + filePath + ") to " + getName() + NavigationBarInflaterView.KEY_CODE_START + getIPAddress() + NavigationBarInflaterView.KEY_CODE_END);
    }

    @Override // com.samsung.android.allshare.media.ImageViewer
    public void stop() {
        if (this.mAllShareConnector == null || !this.mAllShareConnector.isAllShareServiceConnected()) {
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
        if (this.mAllShareConnector == null || !this.mAllShareConnector.isAllShareServiceConnected()) {
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
        return ImageViewer.ImageViewerState.stringToEnum(res_bundle.getString(AllShareKey.BUNDLE_STRING_IMAGE_VIEWEW_STATE));
    }

    @Override // com.samsung.android.allshare.media.ImageViewer
    public void setResponseListener(ImageViewer.IImageViewerResponseListener l) {
        DLog.d_api(TAG_CLASS, "setResponseListener to " + l);
        this.mResponseListener = l;
    }

    @Override // com.samsung.android.allshare.media.ImageViewer
    public void setEventListener(ImageViewer.IImageViewerEventListener l) {
        if (this.mAllShareConnector == null || !this.mAllShareConnector.isAllShareServiceConnected()) {
            DLog.w_api(TAG_CLASS, "setEventListener error! AllShareService is not connected");
            return;
        }
        DLog.d_api(TAG_CLASS, "setEventListener to " + l);
        this.mEventListener = l;
        if (!this.mIsSubscribed && l != null) {
            this.mAllShareConnector.subscribeAllShareEvent(AllShareEvent.EVENT_DEVICE_SUBSCRIBE, this.mDeviceImpl.getBundle(), this.mEventHandler);
            this.mIsSubscribed = true;
        } else if (this.mIsSubscribed && l == null) {
            this.mAllShareConnector.unsubscribeAllShareEvent(AllShareEvent.EVENT_DEVICE_SUBSCRIBE, this.mDeviceImpl.getBundle(), this.mEventHandler);
            this.mIsSubscribed = false;
        }
    }

    @Override // com.samsung.android.allshare.media.ImageViewer, com.samsung.android.allshare.Device
    public Device.DeviceDomain getDeviceDomain() {
        if (this.mDeviceImpl == null) {
            return Device.DeviceDomain.UNKNOWN;
        }
        return this.mDeviceImpl.getDeviceDomain();
    }

    @Override // com.samsung.android.allshare.media.ImageViewer, com.samsung.android.allshare.Device
    public String getID() {
        if (this.mDeviceImpl == null) {
            return "";
        }
        return this.mDeviceImpl.getID();
    }

    @Override // com.samsung.android.allshare.media.ImageViewer, com.samsung.android.allshare.Device
    public Uri getIcon() {
        if (this.mDeviceImpl == null) {
            return null;
        }
        return this.mDeviceImpl.getIcon();
    }

    @Override // com.samsung.android.allshare.Device
    public ArrayList<Icon> getIconList() {
        if (this.mDeviceImpl == null) {
            return new ArrayList<>();
        }
        return this.mDeviceImpl.getIconList();
    }

    @Override // com.samsung.android.allshare.media.ImageViewer, com.samsung.android.allshare.Device
    public String getName() {
        if (this.mDeviceImpl == null) {
            return "";
        }
        return this.mDeviceImpl.getName();
    }

    @Override // com.samsung.android.allshare.media.ImageViewer, com.samsung.android.allshare.Device
    public Device.DeviceType getDeviceType() {
        if (this.mDeviceImpl == null) {
            return Device.DeviceType.UNKNOWN;
        }
        Device.DeviceType result = this.mDeviceImpl.getDeviceType();
        return result;
    }

    @Override // com.samsung.android.allshare.media.ImageViewer, com.samsung.android.allshare.Device
    public String getModelName() {
        if (this.mDeviceImpl == null) {
            return "";
        }
        return this.mDeviceImpl.getModelName();
    }

    @Override // com.samsung.android.allshare.media.ImageViewer, com.samsung.android.allshare.Device
    public String getIPAddress() {
        if (this.mDeviceImpl == null) {
            return "";
        }
        return this.mDeviceImpl.getIPAddress();
    }

    @Override // com.sec.android.allshare.iface.IBundleHolder
    public Bundle getBundle() {
        if (this.mDeviceImpl == null) {
            return null;
        }
        return this.mDeviceImpl.getBundle();
    }

    @Override // com.samsung.android.allshare.media.ImageViewer
    public void getState() {
        if (this.mAllShareConnector == null || !this.mAllShareConnector.isAllShareServiceConnected()) {
            this.mResponseListener.onGetStateResponseReceived(ImageViewer.ImageViewerState.UNKNOWN, ERROR.SERVICE_NOT_CONNECTED);
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
        this.mAllShareConnector.unsubscribeAllShareEvent(AllShareEvent.EVENT_DEVICE_SUBSCRIBE, getBundle(), this.mEventHandler);
        this.mIsSubscribed = false;
    }

    @Override // com.samsung.android.allshare.media.ImageViewer
    public boolean isSupportRedirect() {
        Bundle res_bundle;
        if (this.mAllShareConnector == null || !this.mAllShareConnector.isAllShareServiceConnected()) {
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
        if (this.mDeviceImpl == null) {
            return "";
        }
        return this.mDeviceImpl.getP2pMacAddress();
    }

    @Override // com.samsung.android.allshare.Device
    public String getScreenSharingInfo() {
        if (this.mDeviceImpl == null) {
            return "";
        }
        return this.mDeviceImpl.getScreenSharingInfo();
    }

    @Override // com.samsung.android.allshare.Device
    public void requestMobileToTV(String ip, int port) {
        DLog.w_api(TAG_CLASS, "requestMobileToTV : call requestMobileToTV");
        if (this.mDeviceImpl == null) {
            return;
        }
        this.mDeviceImpl.requestMobileToTV(ip, port);
    }

    @Override // com.samsung.android.allshare.Device
    public String getSecProductP2pMacAddr() {
        if (this.mDeviceImpl == null) {
            return "";
        }
        return this.mDeviceImpl.getSecProductP2pMacAddr();
    }

    @Override // com.samsung.android.allshare.Device
    public String getScreenSharingP2pMacAddr() {
        return "";
    }

    @Override // com.samsung.android.allshare.Device
    public String getProductCapInfo(Device.InformationType infoType) {
        if (this.mDeviceImpl == null) {
            return "";
        }
        return this.mDeviceImpl.getProductCapInfo(infoType);
    }

    @Override // com.samsung.android.allshare.Device
    public String getScreenSharingInfo(Device.InformationType infoType) {
        if (this.mDeviceImpl == null) {
            return "";
        }
        return this.mDeviceImpl.getScreenSharingInfo(infoType);
    }

    @Override // com.samsung.android.allshare.Device
    public boolean isSupportedByType(int type) {
        if (this.mDeviceImpl == null) {
            return false;
        }
        return this.mDeviceImpl.isSupportedByType(type);
    }
}
