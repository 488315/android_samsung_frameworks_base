package androidx.mediarouter.media;

import android.content.Context;
import android.media.MediaRoute2Info;
import android.media.MediaRouter2;
import android.media.RouteDiscoveryPreference;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.SparseArray;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.mediarouter.media.MediaRoute2Provider;
import androidx.mediarouter.media.MediaRouteDescriptor;
import androidx.mediarouter.media.MediaRouteProvider;
import androidx.mediarouter.media.MediaRouteProviderDescriptor;
import androidx.mediarouter.media.MediaRouteSelector;
import androidx.mediarouter.media.MediaRouter;
import com.android.systemui.R;
import com.sec.ims.IMSParameter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaRoute2Provider extends MediaRouteProvider {
    public final Callback mCallback;
    public final ControllerCallback mControllerCallback;
    public final Map mControllerMap;
    public final MediaRoute2Provider$$ExternalSyntheticLambda0 mHandlerExecutor;
    public final MediaRouter2 mMediaRouter2;
    public final RouteCallback mRouteCallback;
    public final Map mRouteIdToOriginalRouteIdMap;
    public List mRoutes;
    public final TransferCallback mTransferCallback;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class Callback {
        public abstract void onReleaseController(MediaRouteProvider.RouteController routeController);

        public abstract void onSelectFallbackRoute();

        public abstract void onSelectRoute(String str);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ControllerCallback extends MediaRouter2.ControllerCallback {
        public ControllerCallback() {
        }

        @Override // android.media.MediaRouter2.ControllerCallback
        public final void onControllerUpdated(MediaRouter2.RoutingController routingController) {
            MediaRoute2Provider.this.setDynamicRouteDescriptors(routingController);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class GroupRouteController extends MediaRouteProvider.DynamicGroupRouteController {
        public final Handler mControllerHandler;
        public MediaRouteDescriptor mGroupRouteDescriptor;
        public final String mInitialMemberRouteId;
        public final Messenger mReceiveMessenger;
        public final MediaRouter2.RoutingController mRoutingController;
        public final Messenger mServiceMessenger;
        public final SparseArray mPendingCallbacks = new SparseArray();
        public final AtomicInteger mNextRequestId = new AtomicInteger(1);
        public final RunnableC0337xd009781b mClearOptimisticVolumeRunnable = new Runnable() { // from class: androidx.mediarouter.media.MediaRoute2Provider$GroupRouteController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                MediaRoute2Provider.GroupRouteController.this.mOptimisticVolume = -1;
            }
        };
        public int mOptimisticVolume = -1;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class ReceiveHandler extends Handler {
            public ReceiveHandler() {
                super(Looper.getMainLooper());
            }

            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                int i = message.what;
                int i2 = message.arg1;
                Object obj = message.obj;
                Bundle peekData = message.peekData();
                MediaRouter.ControlRequestCallback controlRequestCallback = (MediaRouter.ControlRequestCallback) GroupRouteController.this.mPendingCallbacks.get(i2);
                if (controlRequestCallback == null) {
                    Log.w("MR2Provider", "Pending callback not found for control request.");
                    return;
                }
                GroupRouteController.this.mPendingCallbacks.remove(i2);
                if (i == 3) {
                    controlRequestCallback.onResult((Bundle) obj);
                } else {
                    if (i != 4) {
                        return;
                    }
                    controlRequestCallback.onError(peekData == null ? null : peekData.getString("error"), (Bundle) obj);
                }
            }
        }

        /* JADX WARN: Type inference failed for: r2v3, types: [androidx.mediarouter.media.MediaRoute2Provider$GroupRouteController$$ExternalSyntheticLambda0] */
        public GroupRouteController(MediaRouter2.RoutingController routingController, String str) {
            Bundle controlHints;
            this.mRoutingController = routingController;
            this.mInitialMemberRouteId = str;
            Messenger messenger = (routingController == null || (controlHints = routingController.getControlHints()) == null) ? null : (Messenger) controlHints.getParcelable("androidx.mediarouter.media.KEY_MESSENGER");
            this.mServiceMessenger = messenger;
            this.mReceiveMessenger = messenger != null ? new Messenger(new ReceiveHandler()) : null;
            this.mControllerHandler = new Handler(Looper.getMainLooper());
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider.DynamicGroupRouteController
        public final void onAddMemberRoute(String str) {
            if (str == null || str.isEmpty()) {
                Log.w("MR2Provider", "onAddMemberRoute: Ignoring null or empty routeId.");
                return;
            }
            MediaRoute2Info routeById = MediaRoute2Provider.this.getRouteById(str);
            if (routeById == null) {
                Log.w("MR2Provider", "onAddMemberRoute: Specified route not found. routeId=".concat(str));
            } else {
                this.mRoutingController.selectRoute(routeById);
            }
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider.RouteController
        public final void onRelease() {
            this.mRoutingController.release();
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider.DynamicGroupRouteController
        public final void onRemoveMemberRoute(String str) {
            if (str == null || str.isEmpty()) {
                Log.w("MR2Provider", "onRemoveMemberRoute: Ignoring null or empty routeId.");
                return;
            }
            MediaRoute2Info routeById = MediaRoute2Provider.this.getRouteById(str);
            if (routeById == null) {
                Log.w("MR2Provider", "onRemoveMemberRoute: Specified route not found. routeId=".concat(str));
            } else {
                this.mRoutingController.deselectRoute(routeById);
            }
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider.RouteController
        public final void onSetVolume(int i) {
            MediaRouter2.RoutingController routingController = this.mRoutingController;
            if (routingController == null) {
                return;
            }
            routingController.setVolume(i);
            this.mOptimisticVolume = i;
            Handler handler = this.mControllerHandler;
            RunnableC0337xd009781b runnableC0337xd009781b = this.mClearOptimisticVolumeRunnable;
            handler.removeCallbacks(runnableC0337xd009781b);
            handler.postDelayed(runnableC0337xd009781b, 1000L);
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider.DynamicGroupRouteController
        public final void onUpdateMemberRoutes(List list) {
            if (list == null || list.isEmpty()) {
                Log.w("MR2Provider", "onUpdateMemberRoutes: Ignoring null or empty routeIds.");
                return;
            }
            String str = (String) list.get(0);
            MediaRoute2Provider mediaRoute2Provider = MediaRoute2Provider.this;
            MediaRoute2Info routeById = mediaRoute2Provider.getRouteById(str);
            if (routeById == null) {
                MotionLayout$$ExternalSyntheticOutline0.m23m("onUpdateMemberRoutes: Specified route not found. routeId=", str, "MR2Provider");
            } else {
                mediaRoute2Provider.mMediaRouter2.transferTo(routeById);
            }
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider.RouteController
        public final void onUpdateVolume(int i) {
            MediaRouter2.RoutingController routingController = this.mRoutingController;
            if (routingController == null) {
                return;
            }
            int i2 = this.mOptimisticVolume;
            if (i2 < 0) {
                i2 = routingController.getVolume();
            }
            int max = Math.max(0, Math.min(i2 + i, routingController.getVolumeMax()));
            this.mOptimisticVolume = max;
            routingController.setVolume(max);
            Handler handler = this.mControllerHandler;
            RunnableC0337xd009781b runnableC0337xd009781b = this.mClearOptimisticVolumeRunnable;
            handler.removeCallbacks(runnableC0337xd009781b);
            handler.postDelayed(runnableC0337xd009781b, 1000L);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MemberRouteController extends MediaRouteProvider.RouteController {
        public final GroupRouteController mGroupRouteController;
        public final String mOriginalRouteId;

        public MemberRouteController(MediaRoute2Provider mediaRoute2Provider, String str, GroupRouteController groupRouteController) {
            this.mOriginalRouteId = str;
            this.mGroupRouteController = groupRouteController;
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider.RouteController
        public final void onSetVolume(int i) {
            GroupRouteController groupRouteController;
            MediaRouter2.RoutingController routingController;
            Messenger messenger;
            String str = this.mOriginalRouteId;
            if (str == null || (groupRouteController = this.mGroupRouteController) == null || (routingController = groupRouteController.mRoutingController) == null || routingController.isReleased() || (messenger = groupRouteController.mServiceMessenger) == null) {
                return;
            }
            int andIncrement = groupRouteController.mNextRequestId.getAndIncrement();
            Message obtain = Message.obtain();
            obtain.what = 7;
            obtain.arg1 = andIncrement;
            Bundle bundle = new Bundle();
            bundle.putInt("volume", i);
            bundle.putString("routeId", str);
            obtain.setData(bundle);
            obtain.replyTo = groupRouteController.mReceiveMessenger;
            try {
                messenger.send(obtain);
            } catch (DeadObjectException unused) {
            } catch (RemoteException e) {
                Log.e("MR2Provider", "Could not send control request to service.", e);
            }
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider.RouteController
        public final void onUpdateVolume(int i) {
            GroupRouteController groupRouteController;
            MediaRouter2.RoutingController routingController;
            Messenger messenger;
            String str = this.mOriginalRouteId;
            if (str == null || (groupRouteController = this.mGroupRouteController) == null || (routingController = groupRouteController.mRoutingController) == null || routingController.isReleased() || (messenger = groupRouteController.mServiceMessenger) == null) {
                return;
            }
            int andIncrement = groupRouteController.mNextRequestId.getAndIncrement();
            Message obtain = Message.obtain();
            obtain.what = 8;
            obtain.arg1 = andIncrement;
            Bundle bundle = new Bundle();
            bundle.putInt("volume", i);
            bundle.putString("routeId", str);
            obtain.setData(bundle);
            obtain.replyTo = groupRouteController.mReceiveMessenger;
            try {
                messenger.send(obtain);
            } catch (DeadObjectException unused) {
            } catch (RemoteException e) {
                Log.e("MR2Provider", "Could not send control request to service.", e);
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class RouteCallback extends MediaRouter2.RouteCallback {
        public RouteCallback() {
        }

        @Override // android.media.MediaRouter2.RouteCallback
        public final void onRoutesAdded(List list) {
            MediaRoute2Provider.this.refreshRoutes();
        }

        @Override // android.media.MediaRouter2.RouteCallback
        public final void onRoutesChanged(List list) {
            MediaRoute2Provider.this.refreshRoutes();
        }

        @Override // android.media.MediaRouter2.RouteCallback
        public final void onRoutesRemoved(List list) {
            MediaRoute2Provider.this.refreshRoutes();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class TransferCallback extends MediaRouter2.TransferCallback {
        public TransferCallback() {
        }

        @Override // android.media.MediaRouter2.TransferCallback
        public final void onStop(MediaRouter2.RoutingController routingController) {
            MediaRouteProvider.RouteController routeController = (MediaRouteProvider.RouteController) ((ArrayMap) MediaRoute2Provider.this.mControllerMap).remove(routingController);
            if (routeController != null) {
                MediaRoute2Provider.this.mCallback.onReleaseController(routeController);
                return;
            }
            Log.w("MR2Provider", "onStop: No matching routeController found. routingController=" + routingController);
        }

        @Override // android.media.MediaRouter2.TransferCallback
        public final void onTransfer(MediaRouter2.RoutingController routingController, MediaRouter2.RoutingController routingController2) {
            ((ArrayMap) MediaRoute2Provider.this.mControllerMap).remove(routingController);
            if (routingController2 == MediaRoute2Provider.this.mMediaRouter2.getSystemController()) {
                MediaRoute2Provider.this.mCallback.onSelectFallbackRoute();
                return;
            }
            List<MediaRoute2Info> selectedRoutes = routingController2.getSelectedRoutes();
            if (selectedRoutes.isEmpty()) {
                Log.w("MR2Provider", "Selected routes are empty. This shouldn't happen.");
                return;
            }
            String id = selectedRoutes.get(0).getId();
            ((ArrayMap) MediaRoute2Provider.this.mControllerMap).put(routingController2, MediaRoute2Provider.this.new GroupRouteController(routingController2, id));
            MediaRoute2Provider.this.mCallback.onSelectRoute(id);
            MediaRoute2Provider.this.setDynamicRouteDescriptors(routingController2);
        }

        @Override // android.media.MediaRouter2.TransferCallback
        public final void onTransferFailure(MediaRoute2Info mediaRoute2Info) {
            Log.w("MR2Provider", "Transfer failed. requestedRoute=" + mediaRoute2Info);
        }
    }

    public MediaRoute2Provider(Context context, Callback callback) {
        super(context);
        this.mControllerMap = new ArrayMap();
        this.mRouteCallback = new RouteCallback();
        this.mTransferCallback = new TransferCallback();
        this.mControllerCallback = new ControllerCallback();
        this.mRoutes = new ArrayList();
        this.mRouteIdToOriginalRouteIdMap = new ArrayMap();
        this.mMediaRouter2 = MediaRouter2.getInstance(context);
        this.mCallback = callback;
        this.mHandlerExecutor = new MediaRoute2Provider$$ExternalSyntheticLambda0(new Handler(Looper.getMainLooper()));
    }

    public final MediaRoute2Info getRouteById(String str) {
        if (str == null) {
            return null;
        }
        for (MediaRoute2Info mediaRoute2Info : this.mRoutes) {
            if (TextUtils.equals(mediaRoute2Info.getId(), str)) {
                return mediaRoute2Info;
            }
        }
        return null;
    }

    @Override // androidx.mediarouter.media.MediaRouteProvider
    public final MediaRouteProvider.DynamicGroupRouteController onCreateDynamicGroupRouteController(String str) {
        Iterator it = ((ArrayMap) this.mControllerMap).entrySet().iterator();
        while (it.hasNext()) {
            GroupRouteController groupRouteController = (GroupRouteController) ((Map.Entry) it.next()).getValue();
            if (TextUtils.equals(str, groupRouteController.mInitialMemberRouteId)) {
                return groupRouteController;
            }
        }
        return null;
    }

    @Override // androidx.mediarouter.media.MediaRouteProvider
    public final MediaRouteProvider.RouteController onCreateRouteController(String str) {
        return new MemberRouteController(this, (String) ((ArrayMap) this.mRouteIdToOriginalRouteIdMap).get(str), null);
    }

    @Override // androidx.mediarouter.media.MediaRouteProvider
    public final void onDiscoveryRequestChanged(MediaRouteDiscoveryRequest mediaRouteDiscoveryRequest) {
        RouteDiscoveryPreference build;
        String str;
        int i = MediaRouter.sGlobal == null ? 0 : MediaRouter.getGlobalRouter().mCallbackCount;
        ControllerCallback controllerCallback = this.mControllerCallback;
        TransferCallback transferCallback = this.mTransferCallback;
        RouteCallback routeCallback = this.mRouteCallback;
        MediaRouter2 mediaRouter2 = this.mMediaRouter2;
        if (i <= 0) {
            mediaRouter2.unregisterRouteCallback(routeCallback);
            mediaRouter2.unregisterTransferCallback(transferCallback);
            mediaRouter2.unregisterControllerCallback(controllerCallback);
            return;
        }
        MediaRouter.getGlobalRouter();
        if (mediaRouteDiscoveryRequest == null) {
            mediaRouteDiscoveryRequest = new MediaRouteDiscoveryRequest(MediaRouteSelector.EMPTY, false);
        }
        mediaRouteDiscoveryRequest.ensureSelector();
        List controlCategories = mediaRouteDiscoveryRequest.mSelector.getControlCategories();
        ((ArrayList) controlCategories).remove("android.media.intent.category.LIVE_AUDIO");
        MediaRouteSelector.Builder builder = new MediaRouteSelector.Builder();
        builder.addControlCategories(controlCategories);
        MediaRouteDiscoveryRequest mediaRouteDiscoveryRequest2 = new MediaRouteDiscoveryRequest(builder.build(), mediaRouteDiscoveryRequest.isActiveScan());
        mediaRouteDiscoveryRequest2.ensureSelector();
        mediaRouteDiscoveryRequest2.mSelector.ensureControlCategories();
        if (!r13.mControlCategories.contains(null)) {
            boolean isActiveScan = mediaRouteDiscoveryRequest2.isActiveScan();
            ArrayList arrayList = new ArrayList();
            mediaRouteDiscoveryRequest2.ensureSelector();
            Iterator it = ((ArrayList) mediaRouteDiscoveryRequest2.mSelector.getControlCategories()).iterator();
            while (it.hasNext()) {
                str = (String) it.next();
                str.getClass();
                switch (str) {
                    case "android.media.intent.category.REMOTE_PLAYBACK":
                        str = "android.media.route.feature.REMOTE_PLAYBACK";
                        break;
                    case "android.media.intent.category.LIVE_AUDIO":
                        str = "android.media.route.feature.LIVE_AUDIO";
                        break;
                    case "android.media.intent.category.LIVE_VIDEO":
                        str = "android.media.route.feature.LIVE_VIDEO";
                        break;
                }
                arrayList.add(str);
            }
            build = new RouteDiscoveryPreference.Builder(arrayList, isActiveScan).build();
        } else {
            build = new RouteDiscoveryPreference.Builder(new ArrayList(), false).build();
        }
        MediaRoute2Provider$$ExternalSyntheticLambda0 mediaRoute2Provider$$ExternalSyntheticLambda0 = this.mHandlerExecutor;
        mediaRouter2.registerRouteCallback(mediaRoute2Provider$$ExternalSyntheticLambda0, routeCallback, build);
        mediaRouter2.registerTransferCallback(mediaRoute2Provider$$ExternalSyntheticLambda0, transferCallback);
        mediaRouter2.registerControllerCallback(mediaRoute2Provider$$ExternalSyntheticLambda0, controllerCallback);
    }

    public final void refreshRoutes() {
        ArrayList arrayList = new ArrayList();
        ArraySet arraySet = new ArraySet();
        for (MediaRoute2Info mediaRoute2Info : this.mMediaRouter2.getRoutes()) {
            if (mediaRoute2Info != null && !arraySet.contains(mediaRoute2Info) && !mediaRoute2Info.isSystemRoute()) {
                arraySet.add(mediaRoute2Info);
                arrayList.add(mediaRoute2Info);
            }
        }
        if (arrayList.equals(this.mRoutes)) {
            return;
        }
        this.mRoutes = arrayList;
        ArrayMap arrayMap = (ArrayMap) this.mRouteIdToOriginalRouteIdMap;
        arrayMap.clear();
        Iterator it = ((ArrayList) this.mRoutes).iterator();
        while (it.hasNext()) {
            MediaRoute2Info mediaRoute2Info2 = (MediaRoute2Info) it.next();
            Bundle extras = mediaRoute2Info2.getExtras();
            if (extras == null || extras.getString("androidx.mediarouter.media.KEY_ORIGINAL_ROUTE_ID") == null) {
                Log.w("MR2Provider", "Cannot find the original route Id. route=" + mediaRoute2Info2);
            } else {
                arrayMap.put(mediaRoute2Info2.getId(), extras.getString("androidx.mediarouter.media.KEY_ORIGINAL_ROUTE_ID"));
            }
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator it2 = ((ArrayList) this.mRoutes).iterator();
        while (it2.hasNext()) {
            MediaRoute2Info mediaRoute2Info3 = (MediaRoute2Info) it2.next();
            MediaRouteDescriptor mediaRouteDescriptor = MediaRouter2Utils.toMediaRouteDescriptor(mediaRoute2Info3);
            if (mediaRoute2Info3 != null) {
                arrayList2.add(mediaRouteDescriptor);
            }
        }
        MediaRouteProviderDescriptor.Builder builder = new MediaRouteProviderDescriptor.Builder();
        builder.mSupportsDynamicGroupRoute = true;
        if (!arrayList2.isEmpty()) {
            Iterator it3 = arrayList2.iterator();
            while (it3.hasNext()) {
                builder.addRoute((MediaRouteDescriptor) it3.next());
            }
        }
        setDescriptor(new MediaRouteProviderDescriptor(builder.mRoutes, builder.mSupportsDynamicGroupRoute));
    }

    public final void setDynamicRouteDescriptors(MediaRouter2.RoutingController routingController) {
        GroupRouteController groupRouteController = (GroupRouteController) ((ArrayMap) this.mControllerMap).get(routingController);
        if (groupRouteController == null) {
            Log.w("MR2Provider", "setDynamicRouteDescriptors: No matching routeController found. routingController=" + routingController);
            return;
        }
        List<MediaRoute2Info> selectedRoutes = routingController.getSelectedRoutes();
        if (selectedRoutes.isEmpty()) {
            Log.w("MR2Provider", "setDynamicRouteDescriptors: No selected routes. This may happen when the selected routes become invalid.routingController=" + routingController);
            return;
        }
        List routeIds = MediaRouter2Utils.getRouteIds(selectedRoutes);
        MediaRouteDescriptor mediaRouteDescriptor = MediaRouter2Utils.toMediaRouteDescriptor(selectedRoutes.get(0));
        Bundle controlHints = routingController.getControlHints();
        String string = this.mContext.getString(R.string.mr_dialog_default_group_name);
        MediaRouteDescriptor mediaRouteDescriptor2 = null;
        if (controlHints != null) {
            try {
                String string2 = controlHints.getString("androidx.mediarouter.media.KEY_SESSION_NAME");
                if (!TextUtils.isEmpty(string2)) {
                    string = string2;
                }
                Bundle bundle = controlHints.getBundle("androidx.mediarouter.media.KEY_GROUP_ROUTE");
                if (bundle != null) {
                    mediaRouteDescriptor2 = new MediaRouteDescriptor(bundle);
                }
            } catch (Exception e) {
                Log.w("MR2Provider", "Exception while unparceling control hints.", e);
            }
        }
        if (mediaRouteDescriptor2 == null) {
            MediaRouteDescriptor.Builder builder = new MediaRouteDescriptor.Builder(routingController.getId(), string);
            Bundle bundle2 = builder.mBundle;
            bundle2.putInt(IMSParameter.GENERAL.CONNECTION_STATE, 2);
            bundle2.putInt("playbackType", 1);
            bundle2.putInt("volume", routingController.getVolume());
            bundle2.putInt("volumeMax", routingController.getVolumeMax());
            bundle2.putInt("volumeHandling", routingController.getVolumeHandling());
            mediaRouteDescriptor.ensureControlFilters();
            builder.addControlFilters(mediaRouteDescriptor.mControlFilters);
            ArrayList arrayList = (ArrayList) routeIds;
            if (!arrayList.isEmpty()) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    String str = (String) it.next();
                    if (TextUtils.isEmpty(str)) {
                        throw new IllegalArgumentException("groupMemberId must not be empty");
                    }
                    if (builder.mGroupMemberIds == null) {
                        builder.mGroupMemberIds = new ArrayList();
                    }
                    if (!builder.mGroupMemberIds.contains(str)) {
                        builder.mGroupMemberIds.add(str);
                    }
                }
            }
            mediaRouteDescriptor2 = builder.build();
        }
        List routeIds2 = MediaRouter2Utils.getRouteIds(routingController.getSelectableRoutes());
        List routeIds3 = MediaRouter2Utils.getRouteIds(routingController.getDeselectableRoutes());
        MediaRouteProviderDescriptor mediaRouteProviderDescriptor = this.mDescriptor;
        if (mediaRouteProviderDescriptor == null) {
            Log.w("MR2Provider", "setDynamicRouteDescriptors: providerDescriptor is not set.");
            return;
        }
        ArrayList arrayList2 = new ArrayList();
        List<MediaRouteDescriptor> list = mediaRouteProviderDescriptor.mRoutes;
        if (!list.isEmpty()) {
            for (MediaRouteDescriptor mediaRouteDescriptor3 : list) {
                String id = mediaRouteDescriptor3.getId();
                MediaRouteProvider.DynamicGroupRouteController.DynamicRouteDescriptor.Builder builder2 = new MediaRouteProvider.DynamicGroupRouteController.DynamicRouteDescriptor.Builder(mediaRouteDescriptor3);
                builder2.mSelectionState = ((ArrayList) routeIds).contains(id) ? 3 : 1;
                builder2.mIsGroupable = ((ArrayList) routeIds2).contains(id);
                arrayList2.add(new MediaRouteProvider.DynamicGroupRouteController.DynamicRouteDescriptor(builder2.mRouteDescriptor, builder2.mSelectionState, ((ArrayList) routeIds3).contains(id), builder2.mIsGroupable, true));
            }
        }
        groupRouteController.mGroupRouteDescriptor = mediaRouteDescriptor2;
        groupRouteController.notifyDynamicRoutesChanged(mediaRouteDescriptor2, arrayList2);
    }

    @Override // androidx.mediarouter.media.MediaRouteProvider
    public final MediaRouteProvider.RouteController onCreateRouteController(String str, String str2) {
        String id;
        String str3 = (String) ((ArrayMap) this.mRouteIdToOriginalRouteIdMap).get(str);
        for (GroupRouteController groupRouteController : ((ArrayMap) this.mControllerMap).values()) {
            MediaRouteDescriptor mediaRouteDescriptor = groupRouteController.mGroupRouteDescriptor;
            if (mediaRouteDescriptor != null) {
                id = mediaRouteDescriptor.getId();
            } else {
                id = groupRouteController.mRoutingController.getId();
            }
            if (TextUtils.equals(str2, id)) {
                return new MemberRouteController(this, str3, groupRouteController);
            }
        }
        Log.w("MR2Provider", "Could not find the matching GroupRouteController. routeId=" + str + ", routeGroupId=" + str2);
        return new MemberRouteController(this, str3, null);
    }
}
