package androidx.mediarouter.media;

import android.content.ComponentName;
import android.content.Context;
import android.content.IntentFilter;
import android.media.MediaRouter;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import androidx.mediarouter.media.MediaRouteDescriptor;
import androidx.mediarouter.media.MediaRouteProvider;
import androidx.mediarouter.media.MediaRouteProviderDescriptor;
import androidx.mediarouter.media.MediaRouter;
import androidx.mediarouter.media.SystemMediaRouteProvider;
import com.android.systemui.R;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.sec.ims.IMSParameter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class SystemMediaRouteProvider extends MediaRouteProvider {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Api24Impl extends JellybeanMr2Impl {
        public Api24Impl(Context context, SyncCallback syncCallback) {
            super(context, syncCallback);
        }

        @Override // androidx.mediarouter.media.SystemMediaRouteProvider.JellybeanMr2Impl, androidx.mediarouter.media.SystemMediaRouteProvider.JellybeanMr1Impl, androidx.mediarouter.media.SystemMediaRouteProvider.JellybeanImpl
        public final void onBuildSystemRouteDescriptor(JellybeanImpl.SystemRouteRecord systemRouteRecord, MediaRouteDescriptor.Builder builder) {
            super.onBuildSystemRouteDescriptor(systemRouteRecord, builder);
            builder.mBundle.putInt("deviceType", ((MediaRouter.RouteInfo) systemRouteRecord.mRouteObj).getDeviceType());
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class JellybeanImpl extends SystemMediaRouteProvider implements MediaRouterJellybean$Callback, MediaRouterJellybean$VolumeCallback {
        public static final ArrayList LIVE_AUDIO_CONTROL_FILTERS;
        public static final ArrayList LIVE_VIDEO_CONTROL_FILTERS;
        public boolean mActiveScan;
        public final Object mCallbackObj;
        public boolean mCallbackRegistered;
        public MediaRouterJellybean$GetDefaultRouteWorkaround mGetDefaultRouteWorkaround;
        public int mRouteTypes;
        public final Object mRouterObj;
        public MediaRouterJellybean$SelectRouteWorkaround mSelectRouteWorkaround;
        public final SyncCallback mSyncCallback;
        public final ArrayList mSystemRouteRecords;
        public final Object mUserRouteCategoryObj;
        public final ArrayList mUserRouteRecords;
        public final MediaRouterJellybean$VolumeCallbackProxy mVolumeCallbackObj;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class SystemRouteController extends MediaRouteProvider.RouteController {
            public final Object mRouteObj;

            public SystemRouteController(Object obj) {
                this.mRouteObj = obj;
            }

            @Override // androidx.mediarouter.media.MediaRouteProvider.RouteController
            public final void onSetVolume(int i) {
                ((MediaRouter.RouteInfo) this.mRouteObj).requestSetVolume(i);
            }

            @Override // androidx.mediarouter.media.MediaRouteProvider.RouteController
            public final void onUpdateVolume(int i) {
                ((MediaRouter.RouteInfo) this.mRouteObj).requestUpdateVolume(i);
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class SystemRouteRecord {
            public MediaRouteDescriptor mRouteDescriptor;
            public final String mRouteDescriptorId;
            public final Object mRouteObj;

            public SystemRouteRecord(Object obj, String str) {
                this.mRouteObj = obj;
                this.mRouteDescriptorId = str;
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class UserRouteRecord {
            public final MediaRouter.RouteInfo mRoute;
            public final Object mRouteObj;

            public UserRouteRecord(MediaRouter.RouteInfo routeInfo, Object obj) {
                this.mRoute = routeInfo;
                this.mRouteObj = obj;
            }
        }

        static {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addCategory("android.media.intent.category.LIVE_AUDIO");
            ArrayList arrayList = new ArrayList();
            LIVE_AUDIO_CONTROL_FILTERS = arrayList;
            arrayList.add(intentFilter);
            IntentFilter intentFilter2 = new IntentFilter();
            intentFilter2.addCategory("android.media.intent.category.LIVE_VIDEO");
            ArrayList arrayList2 = new ArrayList();
            LIVE_VIDEO_CONTROL_FILTERS = arrayList2;
            arrayList2.add(intentFilter2);
        }

        public JellybeanImpl(Context context, SyncCallback syncCallback) {
            super(context);
            this.mSystemRouteRecords = new ArrayList();
            this.mUserRouteRecords = new ArrayList();
            this.mSyncCallback = syncCallback;
            Object systemService = context.getSystemService("media_router");
            this.mRouterObj = systemService;
            this.mCallbackObj = createCallbackObj();
            this.mVolumeCallbackObj = new MediaRouterJellybean$VolumeCallbackProxy(this);
            this.mUserRouteCategoryObj = ((android.media.MediaRouter) systemService).createRouteCategory((CharSequence) context.getResources().getString(R.string.mr_user_route_category_name), false);
            updateSystemRoutes();
        }

        public static UserRouteRecord getUserRouteRecord(Object obj) {
            Object tag = ((MediaRouter.RouteInfo) obj).getTag();
            if (tag instanceof UserRouteRecord) {
                return (UserRouteRecord) tag;
            }
            return null;
        }

        public final boolean addSystemRouteNoPublish(Object obj) {
            String format;
            String format2;
            if (getUserRouteRecord(obj) != null || findSystemRouteRecord(obj) >= 0) {
                return false;
            }
            boolean z = getDefaultRoute() == obj;
            Context context = this.mContext;
            if (z) {
                format = "DEFAULT_ROUTE";
            } else {
                Locale locale = Locale.US;
                CharSequence name = ((MediaRouter.RouteInfo) obj).getName(context);
                format = String.format(locale, "ROUTE_%08x", Integer.valueOf((name != null ? name.toString() : "").hashCode()));
            }
            String str = format;
            if (findSystemRouteRecordByDescriptorId(str) >= 0) {
                int i = 2;
                while (true) {
                    format2 = String.format(Locale.US, "%s_%d", str, Integer.valueOf(i));
                    if (findSystemRouteRecordByDescriptorId(format2) < 0) {
                        break;
                    }
                    i++;
                }
                str = format2;
            }
            SystemRouteRecord systemRouteRecord = new SystemRouteRecord(obj, str);
            CharSequence name2 = ((MediaRouter.RouteInfo) systemRouteRecord.mRouteObj).getName(context);
            MediaRouteDescriptor.Builder builder = new MediaRouteDescriptor.Builder(systemRouteRecord.mRouteDescriptorId, name2 != null ? name2.toString() : "");
            onBuildSystemRouteDescriptor(systemRouteRecord, builder);
            systemRouteRecord.mRouteDescriptor = builder.build();
            this.mSystemRouteRecords.add(systemRouteRecord);
            return true;
        }

        public Object createCallbackObj() {
            return new MediaRouterJellybean$CallbackProxy(this);
        }

        public final int findSystemRouteRecord(Object obj) {
            ArrayList arrayList = this.mSystemRouteRecords;
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                if (((SystemRouteRecord) arrayList.get(i)).mRouteObj == obj) {
                    return i;
                }
            }
            return -1;
        }

        public final int findSystemRouteRecordByDescriptorId(String str) {
            ArrayList arrayList = this.mSystemRouteRecords;
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                if (((SystemRouteRecord) arrayList.get(i)).mRouteDescriptorId.equals(str)) {
                    return i;
                }
            }
            return -1;
        }

        public final int findUserRouteRecord(MediaRouter.RouteInfo routeInfo) {
            ArrayList arrayList = this.mUserRouteRecords;
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                if (((UserRouteRecord) arrayList.get(i)).mRoute == routeInfo) {
                    return i;
                }
            }
            return -1;
        }

        /* JADX WARN: Type inference failed for: r0v3, types: [androidx.mediarouter.media.MediaRouterJellybean$GetDefaultRouteWorkaround] */
        public Object getDefaultRoute() {
            if (this.mGetDefaultRouteWorkaround == null) {
                this.mGetDefaultRouteWorkaround = new Object() { // from class: androidx.mediarouter.media.MediaRouterJellybean$GetDefaultRouteWorkaround
                    {
                        throw new UnsupportedOperationException();
                    }
                };
            }
            getClass();
            return ((android.media.MediaRouter) this.mRouterObj).getRouteAt(0);
        }

        public void onBuildSystemRouteDescriptor(SystemRouteRecord systemRouteRecord, MediaRouteDescriptor.Builder builder) {
            int supportedTypes = ((MediaRouter.RouteInfo) systemRouteRecord.mRouteObj).getSupportedTypes();
            if ((supportedTypes & 1) != 0) {
                builder.addControlFilters(LIVE_AUDIO_CONTROL_FILTERS);
            }
            if ((supportedTypes & 2) != 0) {
                builder.addControlFilters(LIVE_VIDEO_CONTROL_FILTERS);
            }
            MediaRouter.RouteInfo routeInfo = (MediaRouter.RouteInfo) systemRouteRecord.mRouteObj;
            int playbackType = routeInfo.getPlaybackType();
            Bundle bundle = builder.mBundle;
            bundle.putInt("playbackType", playbackType);
            bundle.putInt("playbackStream", routeInfo.getPlaybackStream());
            bundle.putInt("volume", routeInfo.getVolume());
            bundle.putInt("volumeMax", routeInfo.getVolumeMax());
            bundle.putInt("volumeHandling", routeInfo.getVolumeHandling());
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider
        public final MediaRouteProvider.RouteController onCreateRouteController(String str) {
            int findSystemRouteRecordByDescriptorId = findSystemRouteRecordByDescriptorId(str);
            if (findSystemRouteRecordByDescriptorId >= 0) {
                return new SystemRouteController(((SystemRouteRecord) this.mSystemRouteRecords.get(findSystemRouteRecordByDescriptorId)).mRouteObj);
            }
            return null;
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider
        public final void onDiscoveryRequestChanged(MediaRouteDiscoveryRequest mediaRouteDiscoveryRequest) {
            boolean z;
            int i = 0;
            if (mediaRouteDiscoveryRequest != null) {
                mediaRouteDiscoveryRequest.ensureSelector();
                ArrayList arrayList = (ArrayList) mediaRouteDiscoveryRequest.mSelector.getControlCategories();
                int size = arrayList.size();
                int i2 = 0;
                while (i < size) {
                    String str = (String) arrayList.get(i);
                    i2 = str.equals("android.media.intent.category.LIVE_AUDIO") ? i2 | 1 : str.equals("android.media.intent.category.LIVE_VIDEO") ? i2 | 2 : i2 | QuickStepContract.SYSUI_STATE_BUBBLES_MANAGE_MENU_EXPANDED;
                    i++;
                }
                z = mediaRouteDiscoveryRequest.isActiveScan();
                i = i2;
            } else {
                z = false;
            }
            if (this.mRouteTypes == i && this.mActiveScan == z) {
                return;
            }
            this.mRouteTypes = i;
            this.mActiveScan = z;
            updateSystemRoutes();
        }

        public final void onSyncRouteAdded(MediaRouter.RouteInfo routeInfo) {
            MediaRouteProvider providerInstance = routeInfo.getProviderInstance();
            Object obj = this.mRouterObj;
            if (providerInstance == this) {
                int findSystemRouteRecord = findSystemRouteRecord(((android.media.MediaRouter) obj).getSelectedRoute(8388611));
                if (findSystemRouteRecord < 0 || !((SystemRouteRecord) this.mSystemRouteRecords.get(findSystemRouteRecord)).mRouteDescriptorId.equals(routeInfo.mDescriptorId)) {
                    return;
                }
                routeInfo.select();
                return;
            }
            android.media.MediaRouter mediaRouter = (android.media.MediaRouter) obj;
            MediaRouter.UserRouteInfo createUserRoute = mediaRouter.createUserRoute((MediaRouter.RouteCategory) this.mUserRouteCategoryObj);
            UserRouteRecord userRouteRecord = new UserRouteRecord(routeInfo, createUserRoute);
            createUserRoute.setTag(userRouteRecord);
            createUserRoute.setVolumeCallback(this.mVolumeCallbackObj);
            updateUserRouteProperties(userRouteRecord);
            this.mUserRouteRecords.add(userRouteRecord);
            mediaRouter.addUserRoute(createUserRoute);
        }

        public final void onSyncRouteRemoved(MediaRouter.RouteInfo routeInfo) {
            int findUserRouteRecord;
            if (routeInfo.getProviderInstance() == this || (findUserRouteRecord = findUserRouteRecord(routeInfo)) < 0) {
                return;
            }
            UserRouteRecord userRouteRecord = (UserRouteRecord) this.mUserRouteRecords.remove(findUserRouteRecord);
            ((MediaRouter.RouteInfo) userRouteRecord.mRouteObj).setTag(null);
            MediaRouter.UserRouteInfo userRouteInfo = (MediaRouter.UserRouteInfo) userRouteRecord.mRouteObj;
            userRouteInfo.setVolumeCallback(null);
            ((android.media.MediaRouter) this.mRouterObj).removeUserRoute(userRouteInfo);
        }

        public final void onSyncRouteSelected(MediaRouter.RouteInfo routeInfo) {
            if (routeInfo.isSelected()) {
                if (routeInfo.getProviderInstance() != this) {
                    int findUserRouteRecord = findUserRouteRecord(routeInfo);
                    if (findUserRouteRecord >= 0) {
                        selectRoute(((UserRouteRecord) this.mUserRouteRecords.get(findUserRouteRecord)).mRouteObj);
                        return;
                    }
                    return;
                }
                int findSystemRouteRecordByDescriptorId = findSystemRouteRecordByDescriptorId(routeInfo.mDescriptorId);
                if (findSystemRouteRecordByDescriptorId >= 0) {
                    selectRoute(((SystemRouteRecord) this.mSystemRouteRecords.get(findSystemRouteRecordByDescriptorId)).mRouteObj);
                }
            }
        }

        @Override // androidx.mediarouter.media.MediaRouterJellybean$VolumeCallback
        public final void onVolumeSetRequest(int i, Object obj) {
            UserRouteRecord userRouteRecord = getUserRouteRecord(obj);
            if (userRouteRecord != null) {
                userRouteRecord.mRoute.requestSetVolume(i);
            }
        }

        @Override // androidx.mediarouter.media.MediaRouterJellybean$VolumeCallback
        public final void onVolumeUpdateRequest(int i, Object obj) {
            UserRouteRecord userRouteRecord = getUserRouteRecord(obj);
            if (userRouteRecord != null) {
                userRouteRecord.mRoute.requestUpdateVolume(i);
            }
        }

        public final void publishRoutes() {
            MediaRouteProviderDescriptor.Builder builder = new MediaRouteProviderDescriptor.Builder();
            ArrayList arrayList = this.mSystemRouteRecords;
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                builder.addRoute(((SystemRouteRecord) arrayList.get(i)).mRouteDescriptor);
            }
            setDescriptor(new MediaRouteProviderDescriptor(builder.mRoutes, builder.mSupportsDynamicGroupRoute));
        }

        /* JADX WARN: Type inference failed for: r0v6, types: [androidx.mediarouter.media.MediaRouterJellybean$SelectRouteWorkaround] */
        public void selectRoute(Object obj) {
            if (this.mSelectRouteWorkaround == null) {
                this.mSelectRouteWorkaround = new Object() { // from class: androidx.mediarouter.media.MediaRouterJellybean$SelectRouteWorkaround
                    {
                        throw new UnsupportedOperationException();
                    }
                };
            }
            getClass();
            android.media.MediaRouter mediaRouter = (android.media.MediaRouter) this.mRouterObj;
            MediaRouter.RouteInfo routeInfo = (MediaRouter.RouteInfo) obj;
            if ((routeInfo.getSupportedTypes() & QuickStepContract.SYSUI_STATE_BUBBLES_MANAGE_MENU_EXPANDED) == 0) {
                Log.w("MediaRouterJellybean", "Cannot programmatically select non-user route because the platform is missing the selectRouteInt() method.  Media routing may not work.");
            }
            mediaRouter.selectRoute(8388611, routeInfo);
        }

        public void updateCallback() {
            boolean z = this.mCallbackRegistered;
            Object obj = this.mCallbackObj;
            Object obj2 = this.mRouterObj;
            if (z) {
                this.mCallbackRegistered = false;
                ((android.media.MediaRouter) obj2).removeCallback((MediaRouter.Callback) obj);
            }
            int i = this.mRouteTypes;
            if (i != 0) {
                this.mCallbackRegistered = true;
                ((android.media.MediaRouter) obj2).addCallback(i, (MediaRouter.Callback) obj);
            }
        }

        public final void updateSystemRoutes() {
            updateCallback();
            android.media.MediaRouter mediaRouter = (android.media.MediaRouter) this.mRouterObj;
            int routeCount = mediaRouter.getRouteCount();
            ArrayList arrayList = new ArrayList(routeCount);
            boolean z = false;
            for (int i = 0; i < routeCount; i++) {
                arrayList.add(mediaRouter.getRouteAt(i));
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                z |= addSystemRouteNoPublish(it.next());
            }
            if (z) {
                publishRoutes();
            }
        }

        public void updateUserRouteProperties(UserRouteRecord userRouteRecord) {
            Object obj = userRouteRecord.mRouteObj;
            MediaRouter.RouteInfo routeInfo = userRouteRecord.mRoute;
            ((MediaRouter.UserRouteInfo) obj).setName(routeInfo.mName);
            int i = routeInfo.mPlaybackType;
            MediaRouter.UserRouteInfo userRouteInfo = (MediaRouter.UserRouteInfo) userRouteRecord.mRouteObj;
            userRouteInfo.setPlaybackType(i);
            userRouteInfo.setPlaybackStream(routeInfo.mPlaybackStream);
            userRouteInfo.setVolume(routeInfo.mVolume);
            userRouteInfo.setVolumeMax(routeInfo.mVolumeMax);
            userRouteInfo.setVolumeHandling(routeInfo.getVolumeHandling());
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class JellybeanMr1Impl extends JellybeanImpl implements MediaRouterJellybeanMr1$Callback {
        public MediaRouterJellybeanMr1$ActiveScanWorkaround mActiveScanWorkaround;
        public MediaRouterJellybeanMr1$IsConnectingWorkaround mIsConnectingWorkaround;

        public JellybeanMr1Impl(Context context, SyncCallback syncCallback) {
            super(context, syncCallback);
        }

        @Override // androidx.mediarouter.media.SystemMediaRouteProvider.JellybeanImpl
        public final Object createCallbackObj() {
            return new MediaRouterJellybean$CallbackProxy(this) { // from class: androidx.mediarouter.media.MediaRouterJellybeanMr1$CallbackProxy
                @Override // android.media.MediaRouter.Callback
                public final void onRoutePresentationDisplayChanged(android.media.MediaRouter mediaRouter, MediaRouter.RouteInfo routeInfo) {
                    Display display;
                    SystemMediaRouteProvider.JellybeanMr1Impl jellybeanMr1Impl = (SystemMediaRouteProvider.JellybeanMr1Impl) ((MediaRouterJellybeanMr1$Callback) this.mCallback);
                    int findSystemRouteRecord = jellybeanMr1Impl.findSystemRouteRecord(routeInfo);
                    if (findSystemRouteRecord >= 0) {
                        SystemMediaRouteProvider.JellybeanImpl.SystemRouteRecord systemRouteRecord = (SystemMediaRouteProvider.JellybeanImpl.SystemRouteRecord) jellybeanMr1Impl.mSystemRouteRecords.get(findSystemRouteRecord);
                        try {
                            display = routeInfo.getPresentationDisplay();
                        } catch (NoSuchMethodError e) {
                            Log.w("MediaRouterJellybeanMr1", "Cannot get presentation display for the route.", e);
                            display = null;
                        }
                        int displayId = display != null ? display.getDisplayId() : -1;
                        if (displayId != systemRouteRecord.mRouteDescriptor.mBundle.getInt("presentationDisplayId", -1)) {
                            MediaRouteDescriptor.Builder builder = new MediaRouteDescriptor.Builder(systemRouteRecord.mRouteDescriptor);
                            builder.mBundle.putInt("presentationDisplayId", displayId);
                            systemRouteRecord.mRouteDescriptor = builder.build();
                            jellybeanMr1Impl.publishRoutes();
                        }
                    }
                }
            };
        }

        /* JADX WARN: Type inference failed for: r0v1, types: [androidx.mediarouter.media.MediaRouterJellybeanMr1$IsConnectingWorkaround] */
        public boolean isConnecting(JellybeanImpl.SystemRouteRecord systemRouteRecord) {
            if (this.mIsConnectingWorkaround == null) {
                this.mIsConnectingWorkaround = new Object() { // from class: androidx.mediarouter.media.MediaRouterJellybeanMr1$IsConnectingWorkaround
                    {
                        throw new UnsupportedOperationException();
                    }
                };
            }
            MediaRouterJellybeanMr1$IsConnectingWorkaround mediaRouterJellybeanMr1$IsConnectingWorkaround = this.mIsConnectingWorkaround;
            Object obj = systemRouteRecord.mRouteObj;
            mediaRouterJellybeanMr1$IsConnectingWorkaround.getClass();
            return false;
        }

        @Override // androidx.mediarouter.media.SystemMediaRouteProvider.JellybeanImpl
        public void onBuildSystemRouteDescriptor(JellybeanImpl.SystemRouteRecord systemRouteRecord, MediaRouteDescriptor.Builder builder) {
            Display display;
            super.onBuildSystemRouteDescriptor(systemRouteRecord, builder);
            Object obj = systemRouteRecord.mRouteObj;
            boolean isEnabled = ((MediaRouter.RouteInfo) obj).isEnabled();
            Bundle bundle = builder.mBundle;
            if (!isEnabled) {
                bundle.putBoolean("enabled", false);
            }
            if (isConnecting(systemRouteRecord)) {
                bundle.putInt(IMSParameter.GENERAL.CONNECTION_STATE, 1);
            }
            try {
                display = ((MediaRouter.RouteInfo) obj).getPresentationDisplay();
            } catch (NoSuchMethodError e) {
                Log.w("MediaRouterJellybeanMr1", "Cannot get presentation display for the route.", e);
                display = null;
            }
            if (display != null) {
                bundle.putInt("presentationDisplayId", display.getDisplayId());
            }
        }

        /* JADX WARN: Type inference failed for: r0v3, types: [androidx.mediarouter.media.MediaRouterJellybeanMr1$ActiveScanWorkaround] */
        @Override // androidx.mediarouter.media.SystemMediaRouteProvider.JellybeanImpl
        public void updateCallback() {
            super.updateCallback();
            if (this.mActiveScanWorkaround == null) {
                final Context context = this.mContext;
                final MediaRouteProvider.ProviderHandler providerHandler = this.mHandler;
                this.mActiveScanWorkaround = new Runnable(context, providerHandler) { // from class: androidx.mediarouter.media.MediaRouterJellybeanMr1$ActiveScanWorkaround
                    {
                        throw new UnsupportedOperationException();
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                    }
                };
            }
            MediaRouterJellybeanMr1$ActiveScanWorkaround mediaRouterJellybeanMr1$ActiveScanWorkaround = this.mActiveScanWorkaround;
            if (((this.mActiveScan ? this.mRouteTypes : 0) & 2) == 0) {
                mediaRouterJellybeanMr1$ActiveScanWorkaround.getClass();
            } else {
                mediaRouterJellybeanMr1$ActiveScanWorkaround.getClass();
                Log.w("MediaRouterJellybeanMr1", "Cannot scan for wifi displays because the DisplayManager.scanWifiDisplays() method is not available on this device.");
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class JellybeanMr2Impl extends JellybeanMr1Impl {
        public JellybeanMr2Impl(Context context, SyncCallback syncCallback) {
            super(context, syncCallback);
        }

        @Override // androidx.mediarouter.media.SystemMediaRouteProvider.JellybeanImpl
        public final Object getDefaultRoute() {
            return ((android.media.MediaRouter) this.mRouterObj).getDefaultRoute();
        }

        @Override // androidx.mediarouter.media.SystemMediaRouteProvider.JellybeanMr1Impl
        public final boolean isConnecting(JellybeanImpl.SystemRouteRecord systemRouteRecord) {
            return ((MediaRouter.RouteInfo) systemRouteRecord.mRouteObj).isConnecting();
        }

        @Override // androidx.mediarouter.media.SystemMediaRouteProvider.JellybeanMr1Impl, androidx.mediarouter.media.SystemMediaRouteProvider.JellybeanImpl
        public void onBuildSystemRouteDescriptor(JellybeanImpl.SystemRouteRecord systemRouteRecord, MediaRouteDescriptor.Builder builder) {
            super.onBuildSystemRouteDescriptor(systemRouteRecord, builder);
            CharSequence description = ((MediaRouter.RouteInfo) systemRouteRecord.mRouteObj).getDescription();
            if (description != null) {
                builder.mBundle.putString(IMSParameter.CALL.STATUS, description.toString());
            }
        }

        @Override // androidx.mediarouter.media.SystemMediaRouteProvider.JellybeanImpl
        public final void selectRoute(Object obj) {
            ((android.media.MediaRouter) this.mRouterObj).selectRoute(8388611, (MediaRouter.RouteInfo) obj);
        }

        @Override // androidx.mediarouter.media.SystemMediaRouteProvider.JellybeanMr1Impl, androidx.mediarouter.media.SystemMediaRouteProvider.JellybeanImpl
        public final void updateCallback() {
            boolean z = this.mCallbackRegistered;
            Object obj = this.mCallbackObj;
            Object obj2 = this.mRouterObj;
            if (z) {
                ((android.media.MediaRouter) obj2).removeCallback((MediaRouter.Callback) obj);
            }
            this.mCallbackRegistered = true;
            ((android.media.MediaRouter) obj2).addCallback(this.mRouteTypes, (MediaRouter.Callback) obj, (this.mActiveScan ? 1 : 0) | 2);
        }

        @Override // androidx.mediarouter.media.SystemMediaRouteProvider.JellybeanImpl
        public final void updateUserRouteProperties(JellybeanImpl.UserRouteRecord userRouteRecord) {
            super.updateUserRouteProperties(userRouteRecord);
            ((MediaRouter.UserRouteInfo) userRouteRecord.mRouteObj).setDescription(userRouteRecord.mRoute.mDescription);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface SyncCallback {
    }

    public SystemMediaRouteProvider(Context context) {
        super(context, new MediaRouteProvider.ProviderMetadata(new ComponentName("android", SystemMediaRouteProvider.class.getName())));
    }
}
