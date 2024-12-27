package com.android.systemui.volume;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.collection.ArraySet;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.slice.ArrayUtils;
import androidx.slice.Slice;
import androidx.slice.SliceMetadata;
import androidx.slice.widget.SliceLiveData;
import com.android.settingslib.bluetooth.A2dpProfile;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import com.android.systemui.R;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public final class VolumePanelDialog extends SystemUIDialog implements LifecycleOwner {
    public final ActivityStarter mActivityStarter;
    public final Handler mHandler;
    public final LifecycleRegistry mLifecycleRegistry;
    public final HashSet mLoadedSlices;
    public LocalBluetoothProfileManager mProfileManager;
    public final Map mSliceLiveData;
    public boolean mSlicesReadyToLoad;
    public RecyclerView mVolumePanelSlices;
    public VolumePanelSlicesAdapter mVolumePanelSlicesAdapter;
    public static final Uri REMOTE_MEDIA_SLICE_URI = new Uri.Builder().scheme("content").authority("com.android.settings.slices").appendPath("action").appendPath("remote_media").build();
    public static final Uri VOLUME_MEDIA_URI = new Uri.Builder().scheme("content").authority("com.android.settings.slices").appendPath("action").appendPath("media_volume").build();
    public static final Uri MEDIA_OUTPUT_INDICATOR_SLICE_URI = new Uri.Builder().scheme("content").authority("com.android.settings.slices").appendPath("intent").appendPath("media_output_indicator").build();
    public static final Uri VOLUME_CALL_URI = new Uri.Builder().scheme("content").authority("com.android.settings.slices").appendPath("action").appendPath("call_volume").build();
    public static final Uri VOLUME_RINGER_URI = new Uri.Builder().scheme("content").authority("com.android.settings.slices").appendPath("action").appendPath("ring_volume").build();
    public static final Uri VOLUME_ALARM_URI = new Uri.Builder().scheme("content").authority("com.android.settings.slices").appendPath("action").appendPath("alarm_volume").build();

    public VolumePanelDialog(Context context, ActivityStarter activityStarter, boolean z) {
        super(context);
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mSliceLiveData = new LinkedHashMap();
        this.mLoadedSlices = new HashSet();
        this.mActivityStarter = activityStarter;
        this.mLifecycleRegistry = new LifecycleRegistry(this);
        if (z) {
            return;
        }
        getWindow().setType(2038);
    }

    @Override // androidx.lifecycle.LifecycleOwner
    public final Lifecycle getLifecycle() {
        return this.mLifecycleRegistry;
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog, android.app.AlertDialog, android.app.Dialog
    public final void onCreate(Bundle bundle) {
        A2dpProfile a2dpProfile;
        super.onCreate(bundle);
        Log.d("VolumePanelDialog", "onCreate");
        Uri uri = null;
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.volume_panel_dialog, (ViewGroup) null);
        getWindow().setContentView(inflate);
        final int i = 0;
        ((Button) inflate.findViewById(R.id.done_button)).setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.volume.VolumePanelDialog$$ExternalSyntheticLambda0
            public final /* synthetic */ VolumePanelDialog f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i2 = i;
                VolumePanelDialog volumePanelDialog = this.f$0;
                switch (i2) {
                    case 0:
                        volumePanelDialog.dismiss();
                        break;
                    default:
                        volumePanelDialog.dismiss();
                        Intent intent = new Intent("android.settings.SOUND_SETTINGS");
                        intent.addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                        volumePanelDialog.mActivityStarter.startActivity(intent, true);
                        break;
                }
            }
        });
        final int i2 = 1;
        ((Button) inflate.findViewById(R.id.settings_button)).setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.volume.VolumePanelDialog$$ExternalSyntheticLambda0
            public final /* synthetic */ VolumePanelDialog f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i22 = i2;
                VolumePanelDialog volumePanelDialog = this.f$0;
                switch (i22) {
                    case 0:
                        volumePanelDialog.dismiss();
                        break;
                    default:
                        volumePanelDialog.dismiss();
                        Intent intent = new Intent("android.settings.SOUND_SETTINGS");
                        intent.addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                        volumePanelDialog.mActivityStarter.startActivity(intent, true);
                        break;
                }
            }
        });
        LocalBluetoothManager localBluetoothManager = LocalBluetoothManager.getInstance(getContext(), null);
        if (localBluetoothManager != null) {
            this.mProfileManager = localBluetoothManager.mProfileManager;
        }
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.volume_panel_parent_layout);
        this.mVolumePanelSlices = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ((LinkedHashMap) this.mSliceLiveData).clear();
        this.mLoadedSlices.clear();
        ArrayList arrayList = new ArrayList();
        arrayList.add(REMOTE_MEDIA_SLICE_URI);
        arrayList.add(VOLUME_MEDIA_URI);
        LocalBluetoothProfileManager localBluetoothProfileManager = this.mProfileManager;
        BluetoothDevice activeDevice = (localBluetoothProfileManager == null || (a2dpProfile = localBluetoothProfileManager.mA2dpProfile) == null) ? null : a2dpProfile.getActiveDevice();
        if (activeDevice != null) {
            int width = getWindow().getWindowManager().getCurrentWindowMetrics().getBounds().width() - (getContext().getResources().getDimensionPixelSize(R.dimen.volume_panel_slice_horizontal_padding) * 2);
            String controlUriMetaData = BluetoothUtils.getControlUriMetaData(activeDevice);
            if (!TextUtils.isEmpty(controlUriMetaData)) {
                try {
                    uri = Uri.parse(controlUriMetaData + width);
                } catch (NullPointerException unused) {
                    Log.d("VolumePanelDialog", "unable to parse extra control uri");
                }
            }
        }
        if (uri != null) {
            Log.d("VolumePanelDialog", "add extra control slice");
            arrayList.add(uri);
        }
        arrayList.add(MEDIA_OUTPUT_INDICATOR_SLICE_URI);
        arrayList.add(VOLUME_CALL_URI);
        arrayList.add(VOLUME_RINGER_URI);
        arrayList.add(VOLUME_ALARM_URI);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            final Uri uri2 = (Uri) it.next();
            Context context = getContext();
            VolumePanelDialog$$ExternalSyntheticLambda2 volumePanelDialog$$ExternalSyntheticLambda2 = new VolumePanelDialog$$ExternalSyntheticLambda2(this, uri2);
            ArraySet arraySet = SliceLiveData.SUPPORTED_SPECS;
            SliceLiveData.SliceLiveDataImpl sliceLiveDataImpl = new SliceLiveData.SliceLiveDataImpl(context.getApplicationContext(), uri2, volumePanelDialog$$ExternalSyntheticLambda2);
            this.mSliceLiveData.put(uri2, sliceLiveDataImpl);
            sliceLiveDataImpl.observe(this, new Observer() { // from class: com.android.systemui.volume.VolumePanelDialog$$ExternalSyntheticLambda3
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    final Uri uri3 = uri2;
                    Slice slice = (Slice) obj;
                    final VolumePanelDialog volumePanelDialog = VolumePanelDialog.this;
                    if (volumePanelDialog.mLoadedSlices.contains(uri3)) {
                        return;
                    }
                    StringBuilder sb = new StringBuilder("received slice: ");
                    sb.append(slice == null ? null : Uri.parse(slice.mUri));
                    Log.d("VolumePanelDialog", sb.toString());
                    SliceMetadata from = SliceMetadata.from(volumePanelDialog.getContext(), slice);
                    if (slice == null || ArrayUtils.contains(from.mSlice.mHints, "error")) {
                        if (!volumePanelDialog.removeSliceLiveData(uri3)) {
                            volumePanelDialog.mLoadedSlices.add(uri3);
                        }
                    } else if (from.getLoadingState() == 2) {
                        volumePanelDialog.mLoadedSlices.add(uri3);
                    } else {
                        volumePanelDialog.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.volume.VolumePanelDialog$$ExternalSyntheticLambda4
                            @Override // java.lang.Runnable
                            public final void run() {
                                VolumePanelDialog volumePanelDialog2 = VolumePanelDialog.this;
                                volumePanelDialog2.mLoadedSlices.add(uri3);
                                volumePanelDialog2.setupAdapterWhenReady();
                            }
                        }, 200L);
                    }
                    volumePanelDialog.setupAdapterWhenReady();
                }
            });
        }
        this.mLifecycleRegistry.setCurrentState(Lifecycle.State.CREATED);
    }

    public final boolean removeSliceLiveData(Uri uri) {
        if (!uri.equals(MEDIA_OUTPUT_INDICATOR_SLICE_URI)) {
            Log.d("VolumePanelDialog", "remove uri: " + uri);
            r1 = this.mSliceLiveData.remove(uri) != null;
            VolumePanelSlicesAdapter volumePanelSlicesAdapter = this.mVolumePanelSlicesAdapter;
            if (volumePanelSlicesAdapter != null) {
                ArrayList arrayList = new ArrayList(((LinkedHashMap) this.mSliceLiveData).values());
                ((ArrayList) volumePanelSlicesAdapter.mSliceLiveData).clear();
                ((ArrayList) volumePanelSlicesAdapter.mSliceLiveData).addAll(arrayList);
                volumePanelSlicesAdapter.notifyDataSetChanged();
            }
        }
        return r1;
    }

    public final void setupAdapterWhenReady() {
        if (this.mLoadedSlices.size() != this.mSliceLiveData.size() || this.mSlicesReadyToLoad) {
            return;
        }
        this.mSlicesReadyToLoad = true;
        VolumePanelSlicesAdapter volumePanelSlicesAdapter = new VolumePanelSlicesAdapter(this, this.mSliceLiveData);
        this.mVolumePanelSlicesAdapter = volumePanelSlicesAdapter;
        volumePanelSlicesAdapter.mOnSliceActionListener = new VolumePanelDialog$$ExternalSyntheticLambda5(this);
        if (this.mSliceLiveData.size() < 4) {
            this.mVolumePanelSlices.setMinimumHeight(0);
        }
        this.mVolumePanelSlices.setAdapter(this.mVolumePanelSlicesAdapter);
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog
    public final void start() {
        Log.d("VolumePanelDialog", "onStart");
        this.mLifecycleRegistry.setCurrentState(Lifecycle.State.STARTED);
        this.mLifecycleRegistry.setCurrentState(Lifecycle.State.RESUMED);
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog
    public final void stop() {
        Log.d("VolumePanelDialog", "onStop");
        this.mLifecycleRegistry.setCurrentState(Lifecycle.State.DESTROYED);
    }
}
