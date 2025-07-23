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

/* loaded from: classes6.dex */
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
        private HashMap<String, ImageViewer.ImageViewerState> mStateMap;

        {
            HashMap<String, ImageViewer.ImageViewerState> hashMap = new HashMap<>();
            this.mStateMap = hashMap;
            hashMap.put(AllShareEvent.EVENT_RENDERER_STATE_BUFFERING, ImageViewer.ImageViewerState.BUFFERING);
            this.mStateMap.put(AllShareEvent.EVENT_RENDERER_STATE_NOMEDIA, ImageViewer.ImageViewerState.STOPPED);
            this.mStateMap.put(AllShareEvent.EVENT_RENDERER_STATE_PAUSED, ImageViewer.ImageViewerState.SHOWING);
            this.mStateMap.put(AllShareEvent.EVENT_RENDERER_STATE_STOPPED, ImageViewer.ImageViewerState.STOPPED);
            this.mStateMap.put(AllShareEvent.EVENT_RENDERER_STATE_PLAYING, ImageViewer.ImageViewerState.SHOWING);
            this.mStateMap.put(AllShareEvent.EVENT_RENDERER_STATE_CONTENT_CHANGED, ImageViewer.ImageViewerState.CONTENT_CHANGED);
        }

        @Override // com.samsung.android.allshare.AllShareEventHandler
        public void handleEventMessage(CVMessage cVMessage) {
            try {
                ERROR error = ERROR.FAIL;
                Bundle bundle = cVMessage.getBundle();
                ImageViewer.ImageViewerState imageViewerState = this.mStateMap.get(cVMessage.getActionID());
                ERROR stringToEnum = ERROR.stringToEnum(bundle.getString("BUNDLE_ENUM_ERROR"));
                if (imageViewerState == null) {
                    imageViewerState = ImageViewer.ImageViewerState.UNKNOWN;
                }
                if (imageViewerState.equals(ImageViewer.ImageViewerState.CONTENT_CHANGED)) {
                    String string = bundle.getString(AllShareKey.BUNDLE_STRING_APP_ITEM_ID);
                    if (string != null && !string.isEmpty()) {
                        if (ImageViewerImpl.this.mContentChangedNotified) {
                            DLog.d_api(ImageViewerImpl.TAG_CLASS, "do not notify CONTENT_CHANGED event yet");
                            ImageViewerImpl.this.mCurrentDMRUri = string;
                            return;
                        }
                        if (ImageViewerImpl.this.mCurrentDMRUri != null && string.equalsIgnoreCase(ImageViewerImpl.this.mCurrentDMRUri)) {
                            DLog.d_api(ImageViewerImpl.TAG_CLASS, "do not notify CONTENT_CHANGED event, mCurrentDMRUri is same as currentTrackUri " + string);
                            return;
                        }
                        DLog.d_api(ImageViewerImpl.TAG_CLASS, "CONTENT_CHANGED, mCurrentDMRUri : " + ImageViewerImpl.this.mCurrentDMRUri + "  currentTrackUri : " + string);
                        if (ImageViewerImpl.this.mCurrentDMRUri == null) {
                            DLog.d_api(ImageViewerImpl.TAG_CLASS, "do not notify CONTENT_CHANGED event, mCurrentDMRUri is null");
                            ImageViewerImpl.this.mCurrentDMRUri = string;
                            return;
                        }
                        ImageViewerImpl.this.mCurrentDMRUri = string;
                        if (ImageViewerImpl.this.mPlayingContentUris != null && !ImageViewerImpl.this.mPlayingContentUris.isEmpty()) {
                            if (isContains(string)) {
                                DLog.d_api(ImageViewerImpl.TAG_CLASS, "handleEventMessage: this is playing content.");
                                DLog.i_api(ImageViewerImpl.TAG_CLASS, "do not notify CONTENT_CHANGED event, this is my=" + string);
                                return;
                            }
                            ImageViewerImpl.this.mContentChangedNotified = true;
                            DLog.w_api(ImageViewerImpl.TAG_CLASS, "Notify CONTENT_CHANGED event, mPlayingContentUris[" + ImageViewerImpl.this.mPlayingContentUris + "] vs currentTrackUri[" + string + NavigationBarInflaterView.SIZE_MOD_END);
                        }
                        DLog.d_api(ImageViewerImpl.TAG_CLASS, "do not notify CONTENT_CHANGED event, mPlayingContentUris is null");
                        return;
                    }
                    DLog.d_api(ImageViewerImpl.TAG_CLASS, "do not notify CONTENT_CHANGED event, currentTrackUri is null");
                    return;
                }
                notifyEvent(imageViewerState, stringToEnum);
            } catch (Error e) {
                DLog.w_api(ImageViewerImpl.TAG_CLASS, "mEventHandler.handleEventMessage Error", e);
            } catch (Exception unused) {
                DLog.w_api(ImageViewerImpl.TAG_CLASS, "mEventHandler.handleEventMessage Fail to notify event");
            }
        }

        private void notifyEvent(ImageViewer.ImageViewerState imageViewerState, ERROR error) {
            if (ImageViewerImpl.this.mEventListener != null) {
                try {
                    DLog.v_api(ImageViewerImpl.TAG_CLASS, "mEventHandler.notifyEvent to " + ImageViewerImpl.this.mEventListener + " state[" + imageViewerState.enumToString() + "] error[" + error.enumToString() + NavigationBarInflaterView.SIZE_MOD_END);
                    ImageViewerImpl.this.mEventListener.onDeviceChanged(imageViewerState, error);
                } catch (Error e) {
                    DLog.w_api(ImageViewerImpl.TAG_CLASS, "mEventHandler.notifyEvent Error", e);
                } catch (Exception e2) {
                    DLog.w_api(ImageViewerImpl.TAG_CLASS, "mEventHandler.notifyEvent Exception", e2);
                }
            }
        }

        private boolean isContains(String str) {
            if (ImageViewerImpl.this.mPlayingContentUris != null && str != null) {
                Iterator it = ImageViewerImpl.this.mPlayingContentUris.iterator();
                while (it.hasNext()) {
                    ArrayList arrayList = (ArrayList) it.next();
                    Iterator it2 = arrayList.iterator();
                    while (it2.hasNext()) {
                        if (str.endsWith((String) it2.next())) {
                            ImageViewerImpl.this.mPlayingContentUris.remove(arrayList);
                            return true;
                        }
                    }
                }
            }
            return false;
        }
    };
    AllShareResponseHandler mResponseHandler = new AllShareResponseHandler(ServiceConnector.getMainLooper()) { // from class: com.samsung.android.allshare.ImageViewerImpl.2
        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.samsung.android.allshare.AllShareResponseHandler
        public void handleResponseMessage(CVMessage cVMessage) {
            String actionID = cVMessage.getActionID();
            Bundle bundle = cVMessage.getBundle();
            if (actionID == null || bundle == null) {
                DLog.w_api(ImageViewerImpl.TAG_CLASS, "handleResponseMessage : actionID == null || resBundle == null");
                return;
            }
            ERROR error = ERROR.FAIL;
            String string = bundle.getString("BUNDLE_ENUM_ERROR");
            if (string != null) {
                error = ERROR.stringToEnum(string);
            }
            ContentInfo build = new ContentInfo.Builder().setStartingPosition(bundle.getLong(AllShareKey.BUNDLE_LONG_CONTENT_INFO_STARTINGPOSITION)).build();
            Item fromBundle = ItemCreator.fromBundle((Bundle) bundle.getParcelable(AllShareKey.BUNDLE_PARCELABLE_ITEM));
            if (actionID.equals(AllShareAction.ACTION_IMAGE_VIEWER_SHOW_LOCAL_CONTENT) || actionID.equals(AllShareAction.ACTION_IMAGE_VIEWER_SHOW_LOCAL_CONTENT_URI) || actionID.equals(AllShareAction.ACTION_IMAGE_VIEWER_SHOW_URI) || actionID.equals(AllShareAction.ACTION_IMAGE_VIEWER_SHOW)) {
                if (error.equals(ERROR.SUCCESS)) {
                    ImageViewerImpl.this.mContentChangedNotified = false;
                } else if (fromBundle != 0) {
                    if (actionID.equals(AllShareAction.ACTION_IMAGE_VIEWER_SHOW_URI) || actionID.equals(AllShareAction.ACTION_IMAGE_VIEWER_SHOW)) {
                        removeUri(fromBundle.getURI().toString());
                    } else if (actionID.equals(AllShareAction.ACTION_IMAGE_VIEWER_SHOW_LOCAL_CONTENT)) {
                        Bundle bundle2 = new Bundle();
                        if (fromBundle instanceof IBundleHolder) {
                            bundle2 = ((IBundleHolder) fromBundle).getBundle();
                        }
                        removeUri(bundle2.getString(AllShareKey.BUNDLE_STRING_FILEPATH));
                    } else if (actionID.equals(AllShareAction.ACTION_IMAGE_VIEWER_SHOW_LOCAL_CONTENT_URI)) {
                        removeUri(ImageViewerImpl.this.parseUriFilePath(fromBundle.getURI()));
                    }
                }
            }
            if (ImageViewerImpl.this.mResponseListener == null) {
                DLog.w_api(ImageViewerImpl.TAG_CLASS, "handleResponseMessage : mResponseListener == null");
                return;
            }
            if (actionID.equals(AllShareAction.ACTION_IMAGE_VIEWER_SHOW) || actionID.equals(AllShareAction.ACTION_IMAGE_VIEWER_SHOW_LOCAL_CONTENT) || actionID.equals(AllShareAction.ACTION_IMAGE_VIEWER_SHOW_LOCAL_CONTENT_URI) || actionID.equals(AllShareAction.ACTION_IMAGE_VIEWER_SHOW_URI)) {
                if (fromBundle == 0) {
                    ImageViewerImpl.this.mResponseListener.onShowResponseReceived(fromBundle, build, ERROR.ITEM_NOT_EXIST);
                    return;
                } else {
                    ImageViewerImpl.this.mResponseListener.onShowResponseReceived(fromBundle, build, error);
                    return;
                }
            }
            if (actionID.equals(AllShareAction.ACTION_IMAGE_VIEWER_STOP)) {
                ImageViewerImpl.this.mResponseListener.onStopResponseReceived(error);
            } else if (actionID.equals(AllShareAction.ACTION_IMAGE_VIEWER_REQUEST_GET_VIEWER_STATE)) {
                ImageViewerImpl.this.mResponseListener.onGetStateResponseReceived(ImageViewer.ImageViewerState.stringToEnum(bundle.getString(AllShareKey.BUNDLE_STRING_IMAGE_VIEWEW_STATE)), error);
            }
        }

        private void removeUri(String str) {
            if (ImageViewerImpl.this.mPlayingContentUris == null || str == null) {
                return;
            }
            Iterator it = ImageViewerImpl.this.mPlayingContentUris.iterator();
            while (it.hasNext()) {
                ArrayList arrayList = (ArrayList) it.next();
                if (arrayList != null) {
                    Iterator it2 = arrayList.iterator();
                    while (it2.hasNext()) {
                        String str2 = (String) it2.next();
                        if (str2 != null && str.endsWith(str2)) {
                            ImageViewerImpl.this.mPlayingContentUris.remove(arrayList);
                            return;
                        }
                    }
                }
            }
        }
    };

    @Override // com.samsung.android.allshare.Device
    public boolean isSeekableOnPaused() {
        return false;
    }

    @Override // com.samsung.android.allshare.Device
    public boolean isWholeHomeAudio() {
        return false;
    }

    ImageViewerImpl(IAllShareConnector iAllShareConnector, DeviceImpl deviceImpl) {
        this.mAllShareConnector = null;
        this.mDeviceImpl = null;
        if (iAllShareConnector == null) {
            DLog.w_api(TAG_CLASS, "Connection FAIL: AllShare Service Connector does not exist");
            return;
        }
        if (deviceImpl == null) {
            DLog.w_api(TAG_CLASS, "deviceImpl is null");
            return;
        }
        this.mDeviceImpl = deviceImpl;
        this.mAllShareConnector = iAllShareConnector;
        if (deviceImpl.getBundle() == null) {
            DLog.w_api(TAG_CLASS, "bundle is null");
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

    @Override // com.samsung.android.allshare.media.ImageViewer
    public void zoom(int i, int i2, int i3, int i4) {
        if (!ServiceConnectionChecker.isAllShareServiceConnected(this.mAllShareConnector)) {
            DLog.w_api(TAG_CLASS, "zoom : SERVICE_NOT_CONNECTED");
            return;
        }
        if (i3 < 0 || i4 < 0) {
            DLog.w_api(TAG_CLASS, "zoom Fail :  image width or height is wrong ");
            return;
        }
        CVMessage cVMessage = new CVMessage();
        cVMessage.setActionID(AllShareAction.ACTION_IMAGE_VIEWER_ZOOM);
        Bundle bundle = new Bundle();
        bundle.putString("BUNDLE_STRING_ID", getID());
        bundle.putInt(AllShareKey.BUNDLE_INT_IMAGE_X_COORDINATE, i);
        bundle.putInt(AllShareKey.BUNDLE_INT_IMAGE_Y_COORDINATE, i2);
        bundle.putInt(AllShareKey.BUNDLE_INT_IMAGE_WIDTH, i3);
        bundle.putInt(AllShareKey.BUNDLE_INT_IMAGE_HEIGHT, i4);
        cVMessage.setBundle(bundle);
        this.mAllShareConnector.requestCVMAsync(cVMessage, this.mResponseHandler);
        DLog.i_api(TAG_CLASS, "zoom_ScreenSharing : [ x : " + i + " y : " + i2 + " width : " + i3 + " height : " + i4 + " ] ");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.samsung.android.allshare.media.ImageViewer
    public void show(Item item, ContentInfo contentInfo) {
        String str;
        DLog.i_api(TAG_CLASS, "show() is called");
        if (!ServiceConnectionChecker.isAllShareServiceConnected(this.mAllShareConnector)) {
            DLog.w_api(TAG_CLASS, "show : SERVICE_NOT_CONNECTED");
            this.mResponseListener.onShowResponseReceived(item, contentInfo, ERROR.SERVICE_NOT_CONNECTED);
            return;
        }
        if (item == 0) {
            DLog.w_api(TAG_CLASS, "show : ai == null");
            ImageViewer.IImageViewerResponseListener iImageViewerResponseListener = this.mResponseListener;
            if (iImageViewerResponseListener != null) {
                iImageViewerResponseListener.onShowResponseReceived(item, contentInfo, ERROR.INVALID_ARGUMENT);
                return;
            }
            return;
        }
        this.mContentChangedNotified = false;
        boolean z = item instanceof IBundleHolder;
        if (!z || (str = ((IBundleHolder) item).getBundle().getString(AllShareKey.BUNDLE_STRING_ITEM_CONSTRUCTOR_KEY)) == null) {
            str = "LOCAL_CONTENT";
        }
        if (str.equals("WEB_CONTENT")) {
            ArrayList<String> arrayList = new ArrayList<>();
            Uri thumbnail = item.getThumbnail();
            if (thumbnail != null) {
                arrayList.add(thumbnail.toString());
            }
            Uri uri = item.getURI();
            if (uri != null) {
                arrayList.add(uri.toString());
            }
            this.mPlayingContentUris.add(arrayList);
            showWebContent(item, contentInfo);
            return;
        }
        if (str.equals("LOCAL_CONTENT")) {
            Uri uri2 = item.getURI();
            if (uri2 == null) {
                DLog.w_api(TAG_CLASS, "show : uri == null");
                ImageViewer.IImageViewerResponseListener iImageViewerResponseListener2 = this.mResponseListener;
                if (iImageViewerResponseListener2 != null) {
                    iImageViewerResponseListener2.onShowResponseReceived(item, contentInfo, ERROR.INVALID_ARGUMENT);
                    return;
                }
                return;
            }
            String scheme = uri2.getScheme();
            DLog.d_api(TAG_CLASS, "show : scheme = " + scheme);
            if (scheme.contains("content")) {
                String parseUriFilePath = parseUriFilePath(item.getURI());
                ArrayList<String> arrayList2 = new ArrayList<>();
                arrayList2.add(parseUriFilePath);
                this.mPlayingContentUris.add(arrayList2);
                showLocalContentContentScheme(item, contentInfo);
                return;
            }
            if (scheme.contains("file")) {
                Bundle bundle = new Bundle();
                if (z) {
                    bundle = ((IBundleHolder) item).getBundle();
                }
                String string = bundle.getString(AllShareKey.BUNDLE_STRING_FILEPATH);
                if (!Item.LocalContentBuilder.checkFilePathValid(string)) {
                    DLog.w_api(TAG_CLASS, "show : filePath is not valid");
                    ImageViewer.IImageViewerResponseListener iImageViewerResponseListener3 = this.mResponseListener;
                    if (iImageViewerResponseListener3 != null) {
                        iImageViewerResponseListener3.onShowResponseReceived(item, contentInfo, ERROR.INVALID_ARGUMENT);
                        return;
                    }
                }
                ArrayList<String> arrayList3 = new ArrayList<>();
                arrayList3.add(string);
                this.mPlayingContentUris.add(arrayList3);
                showLocalContentFileScheme(item, contentInfo);
                return;
            }
            ImageViewer.IImageViewerResponseListener iImageViewerResponseListener4 = this.mResponseListener;
            if (iImageViewerResponseListener4 != null) {
                iImageViewerResponseListener4.onShowResponseReceived(item, contentInfo, ERROR.INVALID_ARGUMENT);
                return;
            }
            return;
        }
        DLog.w_api(TAG_CLASS, "show : fail - INVALID ARG ");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String parseUriFilePath(Uri uri) {
        Context context;
        ContentResolver contentResolver;
        Cursor query;
        if (uri == null || (context = ServiceConnector.getContext()) == null || (contentResolver = context.getContentResolver()) == null || (query = contentResolver.query(uri, null, null, null, null)) == null) {
            return null;
        }
        query.moveToFirst();
        String string = query.getString(1);
        query.close();
        return string;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void showWebContent(Item item, ContentInfo contentInfo) {
        if (!ServiceConnectionChecker.isAllShareServiceConnected(this.mAllShareConnector)) {
            DLog.w_api(TAG_CLASS, "showWebContent : SERVICE_NOT_CONNECTED");
            this.mResponseListener.onShowResponseReceived(item, contentInfo, ERROR.SERVICE_NOT_CONNECTED);
            return;
        }
        if (item == 0) {
            DLog.w_api(TAG_CLASS, "showLocalContentContentScheme Fail :  Item does not exist ");
            this.mResponseListener.onShowResponseReceived(item, contentInfo, ERROR.ITEM_NOT_EXIST);
            return;
        }
        CVMessage cVMessage = new CVMessage();
        cVMessage.setActionID(AllShareAction.ACTION_IMAGE_VIEWER_SHOW_URI);
        Bundle bundle = new Bundle();
        bundle.putString("BUNDLE_STRING_ID", getID());
        if (item instanceof IBundleHolder) {
            bundle.putParcelable(AllShareKey.BUNDLE_PARCELABLE_ITEM, ((IBundleHolder) item).getBundle());
        }
        bundle.putLong(AllShareKey.BUNDLE_LONG_CONTENT_INFO_STARTINGPOSITION, contentInfo != null ? contentInfo.getStartingPosition() : 0L);
        cVMessage.setBundle(bundle);
        this.mAllShareConnector.requestCVMAsync(cVMessage, this.mResponseHandler);
        DLog.i_api(TAG_CLASS, "showWebContent : [ " + item.getTitle() + " ]  to " + getName() + " uri : " + item.getURI());
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void showLocalContentContentScheme(Item item, ContentInfo contentInfo) {
        DLog.v_api(TAG_CLASS, "showLocalContentContentScheme()");
        if (!ServiceConnectionChecker.isAllShareServiceConnected(this.mAllShareConnector)) {
            DLog.w_api(TAG_CLASS, "showLocalContentContentScheme Fail :  SERVICE_NOT_CONNECTED ");
            this.mResponseListener.onShowResponseReceived(item, contentInfo, ERROR.SERVICE_NOT_CONNECTED);
            return;
        }
        if (item == 0) {
            DLog.w_api(TAG_CLASS, "showLocalContentContentScheme Fail :  Item does not exist ");
            this.mResponseListener.onShowResponseReceived(item, contentInfo, ERROR.ITEM_NOT_EXIST);
            return;
        }
        Uri uri = item.getURI();
        if (uri == null) {
            DLog.w_api(TAG_CLASS, "showLocalContentContentScheme Fail :  uri == null ");
            this.mResponseListener.onShowResponseReceived(item, contentInfo, ERROR.SERVICE_NOT_CONNECTED);
            return;
        }
        ContentResolver contentResolver = this.mAllShareConnector.getContentResolver();
        if (contentResolver == null) {
            DLog.w_api(TAG_CLASS, "showLocalContentContentScheme Fail :  resolver == null ");
            this.mResponseListener.onShowResponseReceived(item, contentInfo, ERROR.SERVICE_NOT_CONNECTED);
            return;
        }
        Cursor query = contentResolver.query(uri, null, null, null, null);
        if (query == null) {
            DLog.w_api(TAG_CLASS, "showLocalContentContentScheme Fail :  INVALID_ARGUMENT (cur == null) ");
            this.mResponseListener.onShowResponseReceived(item, contentInfo, ERROR.INVALID_ARGUMENT);
            return;
        }
        query.moveToNext();
        if (query.getColumnIndex("_data") < 0) {
            DLog.w_api(TAG_CLASS, "showLocalContentContentScheme Fail :  INVALID_ARGUMENT(idx < 0)");
            this.mResponseListener.onShowResponseReceived(item, contentInfo, ERROR.INVALID_ARGUMENT);
            query.close();
            return;
        }
        query.close();
        CVMessage cVMessage = new CVMessage();
        cVMessage.setActionID(AllShareAction.ACTION_IMAGE_VIEWER_SHOW_LOCAL_CONTENT_URI);
        Bundle bundle = new Bundle();
        bundle.putString("BUNDLE_STRING_ID", getID());
        if (item instanceof IBundleHolder) {
            bundle.putParcelable(AllShareKey.BUNDLE_PARCELABLE_ITEM, ((IBundleHolder) item).getBundle());
        }
        bundle.putLong(AllShareKey.BUNDLE_LONG_CONTENT_INFO_STARTINGPOSITION, contentInfo != null ? contentInfo.getStartingPosition() : 0L);
        cVMessage.setBundle(bundle);
        this.mAllShareConnector.requestCVMAsync(cVMessage, this.mResponseHandler);
        DLog.i_api(TAG_CLASS, "showLocalContentContentScheme : [ " + item.getTitle() + " ]  to " + getName() + " uri : " + uri);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void showMediaContent(Item item, ContentInfo contentInfo) {
        DLog.v_api(TAG_CLASS, "showMediaContent()");
        if (!ServiceConnectionChecker.isAllShareServiceConnected(this.mAllShareConnector)) {
            DLog.w_api(TAG_CLASS, "showMediaContent : SERVICE_NOT_CONNECTED");
            this.mResponseListener.onShowResponseReceived(item, contentInfo, ERROR.SERVICE_NOT_CONNECTED);
            return;
        }
        CVMessage cVMessage = new CVMessage();
        cVMessage.setActionID(AllShareAction.ACTION_IMAGE_VIEWER_SHOW);
        Bundle bundle = new Bundle();
        bundle.putString("BUNDLE_STRING_ID", getID());
        if (item instanceof IBundleHolder) {
            bundle.putParcelable(AllShareKey.BUNDLE_PARCELABLE_ITEM, ((IBundleHolder) item).getBundle());
        }
        bundle.putLong(AllShareKey.BUNDLE_LONG_CONTENT_INFO_STARTINGPOSITION, contentInfo != null ? contentInfo.getStartingPosition() : 0L);
        cVMessage.setBundle(bundle);
        this.mAllShareConnector.requestCVMAsync(cVMessage, this.mResponseHandler);
        DLog.i_api(TAG_CLASS, "showMediaContent : " + item.getTitle() + " to " + getName());
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void showLocalContentFileScheme(Item item, ContentInfo contentInfo) {
        String str;
        String str2;
        DLog.v_api(TAG_CLASS, "showLocalContentFileScheme()");
        if (!ServiceConnectionChecker.isAllShareServiceConnected(this.mAllShareConnector)) {
            DLog.w_api(TAG_CLASS, "showLocalContentFileScheme : SERVICE_NOT_CONNECTED");
            this.mResponseListener.onShowResponseReceived(item, contentInfo, ERROR.SERVICE_NOT_CONNECTED);
            return;
        }
        boolean z = item instanceof IBundleHolder;
        if (!z) {
            str = "";
            str2 = "";
        } else {
            Bundle bundle = ((IBundleHolder) item).getBundle();
            str = bundle.getString(AllShareKey.BUNDLE_STRING_FILEPATH);
            str2 = bundle.getString(AllShareKey.BUNDLE_STRING_ITEM_MIMETYPE);
        }
        CVMessage cVMessage = new CVMessage();
        cVMessage.setActionID(AllShareAction.ACTION_IMAGE_VIEWER_SHOW_LOCAL_CONTENT);
        Bundle bundle2 = new Bundle();
        bundle2.putString("BUNDLE_STRING_ID", getID());
        bundle2.putString(AllShareKey.BUNDLE_STRING_FILEPATH, str);
        bundle2.putString(AllShareKey.BUNDLE_STRING_ITEM_MIMETYPE, str2);
        bundle2.putString(AllShareKey.BUNDLE_STRING_TITLE, item.getTitle());
        if (z) {
            bundle2.putParcelable(AllShareKey.BUNDLE_PARCELABLE_ITEM, ((IBundleHolder) item).getBundle());
        }
        bundle2.putLong(AllShareKey.BUNDLE_LONG_CONTENT_INFO_STARTINGPOSITION, contentInfo != null ? contentInfo.getStartingPosition() : 0L);
        cVMessage.setBundle(bundle2);
        this.mAllShareConnector.requestCVMAsync(cVMessage, this.mResponseHandler);
        DLog.i_api(TAG_CLASS, "showLocalContentFileScheme : [" + str2 + NavigationBarInflaterView.SIZE_MOD_END + item.getTitle() + NavigationBarInflaterView.KEY_CODE_START + str + ") to " + getName() + NavigationBarInflaterView.KEY_CODE_START + getIPAddress() + NavigationBarInflaterView.KEY_CODE_END);
    }

    @Override // com.samsung.android.allshare.media.ImageViewer
    public void stop() {
        if (!ServiceConnectionChecker.isAllShareServiceConnected(this.mAllShareConnector)) {
            DLog.w_api(TAG_CLASS, "stop : SERVICE_NOT_CONNECTED");
            this.mResponseListener.onStopResponseReceived(ERROR.SERVICE_NOT_CONNECTED);
            return;
        }
        CVMessage cVMessage = new CVMessage();
        cVMessage.setActionID(AllShareAction.ACTION_IMAGE_VIEWER_STOP);
        Bundle bundle = new Bundle();
        bundle.putString("BUNDLE_STRING_ID", getID());
        cVMessage.setBundle(bundle);
        this.mAllShareConnector.requestCVMAsync(cVMessage, this.mResponseHandler);
        DLog.i_api(TAG_CLASS, "stop : " + getName());
    }

    @Override // com.samsung.android.allshare.media.ImageViewer
    public ImageViewer.ImageViewerState getViewerState() {
        if (!ServiceConnectionChecker.isAllShareServiceConnected(this.mAllShareConnector)) {
            return ImageViewer.ImageViewerState.UNKNOWN;
        }
        Bundle bundle = new Bundle();
        Bundle bundle2 = getBundle();
        if (bundle2 == null) {
            return ImageViewer.ImageViewerState.UNKNOWN;
        }
        String string = bundle2.getString("BUNDLE_STRING_ID");
        if (string == null) {
            return ImageViewer.ImageViewerState.UNKNOWN;
        }
        bundle.putString("BUNDLE_STRING_ID", string);
        CVMessage cVMessage = new CVMessage();
        cVMessage.setActionID(AllShareAction.ACTION_IMAGE_VIEWER_GET_VIEWER_STATE_SYNC);
        cVMessage.setBundle(bundle);
        CVMessage requestCVMSync = this.mAllShareConnector.requestCVMSync(cVMessage);
        if (requestCVMSync == null) {
            return ImageViewer.ImageViewerState.UNKNOWN;
        }
        Bundle bundle3 = requestCVMSync.getBundle();
        if (bundle3 == null) {
            return ImageViewer.ImageViewerState.UNKNOWN;
        }
        return ImageViewer.ImageViewerState.stringToEnum(bundle3.getString(AllShareKey.BUNDLE_STRING_IMAGE_VIEWEW_STATE));
    }

    @Override // com.samsung.android.allshare.media.ImageViewer
    public void setResponseListener(ImageViewer.IImageViewerResponseListener iImageViewerResponseListener) {
        DLog.d_api(TAG_CLASS, "setResponseListener to " + iImageViewerResponseListener);
        this.mResponseListener = iImageViewerResponseListener;
    }

    @Override // com.samsung.android.allshare.media.ImageViewer
    public void setEventListener(ImageViewer.IImageViewerEventListener iImageViewerEventListener) {
        if (!ServiceConnectionChecker.isAllShareServiceConnected(this.mAllShareConnector)) {
            DLog.w_api(TAG_CLASS, "setEventListener error! AllShareService is not connected");
            return;
        }
        DLog.d_api(TAG_CLASS, "setEventListener to " + iImageViewerEventListener);
        this.mEventListener = iImageViewerEventListener;
        boolean z = this.mIsSubscribed;
        if (!z && iImageViewerEventListener != null) {
            this.mAllShareConnector.subscribeAllShareEvent(AllShareEvent.EVENT_DEVICE_SUBSCRIBE, this.mDeviceImpl.getBundle(), this.mEventHandler);
            this.mIsSubscribed = true;
        } else if (z && iImageViewerEventListener == null) {
            this.mAllShareConnector.unsubscribeAllShareEvent(AllShareEvent.EVENT_DEVICE_SUBSCRIBE, this.mDeviceImpl.getBundle(), this.mEventHandler);
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
        return deviceImpl.getDeviceType();
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
    public void getState() {
        if (!ServiceConnectionChecker.isAllShareServiceConnected(this.mAllShareConnector)) {
            this.mResponseListener.onGetStateResponseReceived(ImageViewer.ImageViewerState.UNKNOWN, ERROR.SERVICE_NOT_CONNECTED);
            return;
        }
        CVMessage cVMessage = new CVMessage();
        cVMessage.setActionID(AllShareAction.ACTION_IMAGE_VIEWER_REQUEST_GET_VIEWER_STATE);
        Bundle bundle = new Bundle();
        bundle.putString("BUNDLE_STRING_ID", getID());
        cVMessage.setBundle(bundle);
        this.mAllShareConnector.requestCVMAsync(cVMessage, this.mResponseHandler);
    }

    @Override // com.sec.android.allshare.iface.IHandlerHolder
    public void removeEventHandler() {
        this.mAllShareConnector.unsubscribeAllShareEvent(AllShareEvent.EVENT_DEVICE_SUBSCRIBE, getBundle(), this.mEventHandler);
        this.mIsSubscribed = false;
    }

    @Override // com.samsung.android.allshare.media.ImageViewer
    public boolean isSupportRedirect() {
        Bundle bundle;
        if (!ServiceConnectionChecker.isAllShareServiceConnected(this.mAllShareConnector)) {
            return false;
        }
        CVMessage cVMessage = new CVMessage();
        cVMessage.setActionID(AllShareAction.ACTION_IMAGE_VIEWER_IS_SUPPORT_REDIRECT_SYNC);
        Bundle bundle2 = new Bundle();
        bundle2.putString("BUNDLE_STRING_ID", getID());
        cVMessage.setBundle(bundle2);
        CVMessage requestCVMSync = this.mAllShareConnector.requestCVMSync(cVMessage);
        if (requestCVMSync == null || (bundle = requestCVMSync.getBundle()) == null) {
            return false;
        }
        String string = bundle.getString("BUNDLE_ENUM_ERROR");
        if (string != null && ERROR.NOT_SUPPORTED_FRAMEWORK_VERSION.enumToString().equals(string)) {
            DLog.w_api(TAG_CLASS, " isRedirectSupportable() Exception : NOT_SUPPORTED_FRAMEWORK_VERSION");
            return false;
        }
        try {
            return bundle.getBoolean(AllShareKey.BUNDLE_BOOLEAN_SUPPORT_REDIRECT);
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

    @Override // com.samsung.android.allshare.Device
    public boolean isSupportedByType(int i) {
        DeviceImpl deviceImpl = this.mDeviceImpl;
        if (deviceImpl == null) {
            return false;
        }
        return deviceImpl.isSupportedByType(i);
    }
}
