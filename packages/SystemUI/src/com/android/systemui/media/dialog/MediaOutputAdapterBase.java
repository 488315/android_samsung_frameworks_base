package com.android.systemui.media.dialog;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.PowerExemptionManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;
import com.android.settingslib.media.MediaDevice;
import com.android.settingslib.utils.ThreadUtils;
import com.android.systemui.R;
import com.android.systemui.media.dialog.MediaOutputAdapterBase;
import com.android.systemui.util.wakelock.WakeLock;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class MediaOutputAdapterBase extends RecyclerView.Adapter {
    public static final boolean DEBUG = Log.isLoggable("MediaOutputAdapterBase", 3);
    public final MediaSwitchingController mController;
    public final List mMediaItemList = new CopyOnWriteArrayList();
    public boolean mShouldGroupSelectedMediaItems = true;
    public int mCurrentActivePosition = -1;
    public boolean mIsDragging = false;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Api34Impl {
        private Api34Impl() {
        }

        public static View.OnClickListener getClickListenerBasedOnSelectionBehavior(MediaDevice mediaDevice, MediaSwitchingController mediaSwitchingController, View.OnClickListener onClickListener) {
            int selectionBehavior = mediaDevice.getSelectionBehavior();
            if (selectionBehavior != 0) {
                return selectionBehavior != 2 ? onClickListener : new MediaOutputAdapterBase$Api34Impl$$ExternalSyntheticLambda0(mediaSwitchingController, mediaDevice, 0);
            }
            return null;
        }

        public static Drawable getDeviceStatusIconBasedOnSelectionBehavior(MediaDevice mediaDevice, Context context) {
            int selectionBehavior = mediaDevice.getSelectionBehavior();
            if (selectionBehavior == 0) {
                return context.getDrawable(R.drawable.media_output_status_failed);
            }
            if (selectionBehavior != 2) {
                return null;
            }
            return context.getDrawable(R.drawable.media_output_status_help);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public enum ConnectionState {
        CONNECTED,
        CONNECTING,
        DISCONNECTED
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class GroupStatus extends Record {
        public final Boolean deselectable;
        public final Boolean selected;

        public GroupStatus(Boolean bool, Boolean bool2) {
            this.selected = bool;
            this.deselectable = bool2;
        }

        @Override // java.lang.Record
        public final boolean equals(Object obj) {
            if (!(obj instanceof GroupStatus)) {
                return false;
            }
            GroupStatus groupStatus = (GroupStatus) obj;
            return Objects.equals(this.selected, groupStatus.selected) && Objects.equals(this.deselectable, groupStatus.deselectable);
        }

        @Override // java.lang.Record
        public final int hashCode() {
            Boolean bool = this.selected;
            Boolean bool2 = this.deselectable;
            return Objects.hashCode(bool2) + (Objects.hashCode(bool) * 31);
        }

        @Override // java.lang.Record
        public final String toString() {
            Object[] objArr = {this.selected, this.deselectable};
            String[] split = "selected;deselectable".length() == 0 ? new String[0] : "selected;deselectable".split(";");
            StringBuilder sb = new StringBuilder();
            sb.append(GroupStatus.class.getSimpleName());
            sb.append("[");
            for (int i = 0; i < split.length; i++) {
                sb.append(split[i]);
                sb.append("=");
                sb.append(objArr[i]);
                if (i != split.length - 1) {
                    sb.append(", ");
                }
            }
            sb.append("]");
            return sb.toString();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class MediaDeviceViewHolderBase extends RecyclerView.ViewHolder {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final Context mContext;

        public MediaDeviceViewHolderBase(View view, Context context) {
            super(view);
            this.mContext = context;
        }

        public abstract void disableSeekBar();

        public final void onItemClick(MediaDevice mediaDevice) {
            MediaDevice currentConnectedDevice = MediaOutputAdapterBase.this.mController.mLocalMediaManager.getCurrentConnectedDevice();
            if (currentConnectedDevice == null || !MediaDevice.Api34Impl.isHostForOngoingSession(currentConnectedDevice.mItem)) {
                transferOutput(mediaDevice);
            } else {
                showCustomEndSessionDialog(mediaDevice);
            }
        }

        public abstract void renderDeviceGroupItem();

        public abstract void renderDeviceItem(boolean z, MediaDevice mediaDevice, ConnectionState connectionState, boolean z2, GroupStatus groupStatus, OngoingSessionStatus ongoingSessionStatus, View.OnClickListener onClickListener, boolean z3, String str, Drawable drawable);

        public void showCustomEndSessionDialog(final MediaDevice mediaDevice) {
            Context context = this.mContext;
            Runnable runnable = new Runnable() { // from class: com.android.systemui.media.dialog.MediaOutputAdapterBase$MediaDeviceViewHolderBase$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    MediaOutputAdapterBase.MediaDeviceViewHolderBase mediaDeviceViewHolderBase = MediaOutputAdapterBase.MediaDeviceViewHolderBase.this;
                    MediaDevice mediaDevice2 = mediaDevice;
                    int i = MediaOutputAdapterBase.MediaDeviceViewHolderBase.$r8$clinit;
                    mediaDeviceViewHolderBase.transferOutput(mediaDevice2);
                }
            };
            MediaOutputAdapterBase mediaOutputAdapterBase = MediaOutputAdapterBase.this;
            new MediaSessionReleaseDialog(context, runnable, mediaOutputAdapterBase.mController.mMediaOutputColorSchemeLegacy.getColorButtonBackground(), mediaOutputAdapterBase.mController.mMediaOutputColorSchemeLegacy.getColorItemContent()).show();
        }

        public final void transferOutput(final MediaDevice mediaDevice) {
            String str;
            MediaOutputAdapterBase mediaOutputAdapterBase = MediaOutputAdapterBase.this;
            if (mediaOutputAdapterBase.mController.isAnyDeviceTransferring()) {
                return;
            }
            if (mediaOutputAdapterBase.isCurrentlyConnected(mediaDevice)) {
                Log.d("MediaOutputAdapterBase", "This device is already connected! : " + mediaDevice.getName());
                return;
            }
            final MediaSwitchingController mediaSwitchingController = mediaOutputAdapterBase.mController;
            PowerExemptionManager powerExemptionManager = mediaSwitchingController.mPowerExemptionManager;
            if (powerExemptionManager == null || (str = mediaSwitchingController.mPackageName) == null) {
                Log.w("MediaSwitchingController", "powerExemptionManager or package name is null");
            } else {
                powerExemptionManager.addToTemporaryAllowList(str, 325, "mediaoutput:remote_transfer", WakeLock.DEFAULT_MAX_TIMEOUT);
            }
            mediaOutputAdapterBase.mCurrentActivePosition = -1;
            MediaOutputMetricLogger mediaOutputMetricLogger = mediaSwitchingController.mMetricLogger;
            mediaOutputMetricLogger.mSourceDevice = mediaSwitchingController.mLocalMediaManager.getCurrentConnectedDevice();
            mediaOutputMetricLogger.mTargetDevice = mediaDevice;
            if (MediaOutputMetricLogger.DEBUG) {
                Log.d("MediaOutputMetricLogger", "updateOutputEndPoints - source:" + mediaOutputMetricLogger.mSourceDevice.toString() + " target:" + mediaOutputMetricLogger.mTargetDevice.toString());
            }
            ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.android.systemui.media.dialog.MediaSwitchingController$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    MediaSwitchingController mediaSwitchingController2 = MediaSwitchingController.this;
                    mediaSwitchingController2.mLocalMediaManager.connectDevice(mediaDevice);
                }
            });
            mediaDevice.mState = 1;
            mediaOutputAdapterBase.notifyDataSetChanged();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class OngoingSessionStatus extends Record {
        public final boolean host;

        public OngoingSessionStatus(boolean z) {
            this.host = z;
        }

        @Override // java.lang.Record
        public final boolean equals(Object obj) {
            return (obj instanceof OngoingSessionStatus) && this.host == ((OngoingSessionStatus) obj).host;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return Boolean.hashCode(this.host);
        }

        @Override // java.lang.Record
        public final String toString() {
            Object[] objArr = {Boolean.valueOf(this.host)};
            String[] split = "host".length() == 0 ? new String[0] : "host".split(";");
            StringBuilder sb = new StringBuilder();
            sb.append(OngoingSessionStatus.class.getSimpleName());
            sb.append("[");
            for (int i = 0; i < split.length; i++) {
                sb.append(split[i]);
                sb.append("=");
                sb.append(objArr[i]);
                if (i != split.length - 1) {
                    sb.append(", ");
                }
            }
            sb.append("]");
            return sb.toString();
        }
    }

    public MediaOutputAdapterBase(MediaSwitchingController mediaSwitchingController) {
        this.mController = mediaSwitchingController;
        setHasStableIds(true);
    }

    public static boolean isDeviceIncluded(List list, MediaDevice mediaDevice) {
        ArrayList arrayList = (ArrayList) list;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            if (TextUtils.equals(((MediaDevice) obj).getId(), mediaDevice.getId())) {
                return true;
            }
        }
        return false;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return ((CopyOnWriteArrayList) this.mMediaItemList).size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int i) {
        if (i >= ((CopyOnWriteArrayList) this.mMediaItemList).size()) {
            ListPopupWindow$$ExternalSyntheticOutline0.m(i, "Incorrect position for item id: ", "MediaOutputAdapterBase");
            return i;
        }
        return ((MediaItem) ((CopyOnWriteArrayList) this.mMediaItemList).get(i)).mMediaDeviceOptional.isPresent() ? ((MediaDevice) r1.mMediaDeviceOptional.get()).getId().hashCode() : i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemViewType(int i) {
        if (i < ((CopyOnWriteArrayList) this.mMediaItemList).size()) {
            return ((MediaItem) ((CopyOnWriteArrayList) this.mMediaItemList).get(i)).mMediaItemType;
        }
        ListPopupWindow$$ExternalSyntheticOutline0.m(i, "Incorrect position for item type: ", "MediaOutputAdapterBase");
        return 1;
    }

    public final boolean isCurrentlyConnected(MediaDevice mediaDevice) {
        MediaSwitchingController mediaSwitchingController = this.mController;
        MediaDevice currentConnectedDevice = mediaSwitchingController.mLocalMediaManager.getCurrentConnectedDevice();
        return (currentConnectedDevice != null && TextUtils.equals(mediaDevice.getId(), currentConnectedDevice.getId())) || (((ArrayList) mediaSwitchingController.mLocalMediaManager.getSelectedMediaDevice()).size() == 1 && isDeviceIncluded(mediaSwitchingController.mLocalMediaManager.getSelectedMediaDevice(), mediaDevice));
    }
}
