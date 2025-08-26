package com.android.systemui.usb;

import android.R;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.hardware.usb.IUsbManager;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbDevice;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.Log;
import android.widget.CheckBox;
import com.android.internal.app.IntentForwarderActivity;
import com.android.internal.app.ResolverActivity;
import com.android.internal.app.chooser.TargetInfo;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

/* loaded from: classes3.dex */
public class UsbResolverActivity extends ResolverActivity {
    public UsbAccessory mAccessory;
    public UsbDevice mDevice;
    public UsbDisconnectedReceiver mDisconnectedReceiver;
    public ResolveInfo mForwardResolveInfo;
    public Intent mOtherProfileIntent;

    /* JADX WARN: Multi-variable type inference failed */
    public final void onCreate(Bundle bundle) {
        boolean hasAudioCapture;
        getWindow().addSystemFlags(NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME);
        Intent intent = getIntent();
        Parcelable parcelableExtra = intent.getParcelableExtra("android.intent.extra.INTENT");
        if (!(parcelableExtra instanceof Intent)) {
            super_onCreate(bundle);
            Log.w("UsbResolverActivity", "Target is not an intent: " + parcelableExtra);
            finish();
            return;
        }
        Intent intent2 = (Intent) parcelableExtra;
        ArrayList arrayList = new ArrayList(intent.getParcelableArrayListExtra("rlist"));
        ArrayList<? extends Parcelable> arrayList2 = new ArrayList<>();
        this.mForwardResolveInfo = null;
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ResolveInfo resolveInfo = (ResolveInfo) it.next();
            if (resolveInfo.getComponentInfo().name.equals(IntentForwarderActivity.FORWARD_INTENT_TO_MANAGED_PROFILE)) {
                this.mForwardResolveInfo = resolveInfo;
            } else if (UserHandle.getUserId(resolveInfo.activityInfo.applicationInfo.uid) != UserHandle.myUserId()) {
                it.remove();
                arrayList2.add(resolveInfo);
            }
        }
        UsbDevice usbDevice = (UsbDevice) intent2.getParcelableExtra("device");
        this.mDevice = usbDevice;
        boolean z = false;
        if (usbDevice != null) {
            this.mDisconnectedReceiver = new UsbDisconnectedReceiver((Activity) this, this.mDevice);
            hasAudioCapture = this.mDevice.getHasAudioCapture();
        } else {
            UsbAccessory usbAccessory = (UsbAccessory) intent2.getParcelableExtra("accessory");
            this.mAccessory = usbAccessory;
            if (usbAccessory == null) {
                super_onCreate(bundle);
                Log.e("UsbResolverActivity", "no device or accessory");
                finish();
                return;
            }
            this.mDisconnectedReceiver = new UsbDisconnectedReceiver((Activity) this, this.mAccessory);
            hasAudioCapture = false;
        }
        if (this.mForwardResolveInfo != null) {
            if (arrayList2.size() > 1) {
                Intent intent3 = new Intent(intent);
                this.mOtherProfileIntent = intent3;
                intent3.putParcelableArrayListExtra("rlist", arrayList2);
            } else {
                Intent intent4 = new Intent();
                this.mOtherProfileIntent = intent4;
                intent4.setComponent(ComponentName.unflattenFromString(getResources().getString(R.string.factorytest_not_system)));
                this.mOtherProfileIntent.putExtra("rinfo", (Parcelable) arrayList2.get(0));
                UsbDevice usbDevice2 = this.mDevice;
                if (usbDevice2 != null) {
                    this.mOtherProfileIntent.putExtra("device", usbDevice2);
                }
                UsbAccessory usbAccessory2 = this.mAccessory;
                if (usbAccessory2 != null) {
                    this.mOtherProfileIntent.putExtra("accessory", usbAccessory2);
                }
            }
        }
        getIntent().putExtra("is_audio_capture_device", hasAudioCapture);
        Collections.sort(arrayList, new Comparator(this) { // from class: com.android.systemui.usb.UsbResolverActivity.1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return ((ResolveInfo) obj).getComponentInfo().packageName.compareTo(((ResolveInfo) obj2).getComponentInfo().packageName);
            }
        });
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            String str = ((ResolveInfo) it2.next()).getComponentInfo().packageName;
            if (str.equals("com.sec.android.easyMover")) {
                z = true;
            } else if (z && str.equals("com.sec.android.easyMover.Agent")) {
                Log.d("UsbResolverActivity", "Remove package from list=".concat(str));
                it2.remove();
            }
        }
        super.onCreate(bundle, intent2, getResources().getText(R.string.config_systemTelevisionRemoteService), (Intent[]) null, arrayList, true);
        CheckBox checkBox = (CheckBox) findViewById(R.id.autofill_save_no);
        if (checkBox != null) {
            if (this.mDevice == null) {
                checkBox.setText(com.android.systemui.R.string.always_use_accessory);
            } else {
                checkBox.setText(com.android.systemui.R.string.always_use_device);
            }
        }
    }

    public final void onDestroy() {
        UsbDisconnectedReceiver usbDisconnectedReceiver = this.mDisconnectedReceiver;
        if (usbDisconnectedReceiver != null) {
            unregisterReceiver(usbDisconnectedReceiver);
        }
        super.onDestroy();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean onTargetSelected(TargetInfo targetInfo, boolean z) {
        ResolveInfo resolveInfo = targetInfo.getResolveInfo();
        ResolveInfo resolveInfo2 = this.mForwardResolveInfo;
        if (resolveInfo == resolveInfo2) {
            startActivityAsUser(this.mOtherProfileIntent, (Bundle) null, UserHandle.of(resolveInfo2.targetUserId));
            return true;
        }
        try {
            IUsbManager iUsbManagerAsInterface = IUsbManager.Stub.asInterface(ServiceManager.getService("usb"));
            int i = resolveInfo.activityInfo.applicationInfo.uid;
            int iMyUserId = UserHandle.myUserId();
            UsbDevice usbDevice = this.mDevice;
            if (usbDevice != null) {
                iUsbManagerAsInterface.grantDevicePermission(usbDevice, i);
                if (z) {
                    iUsbManagerAsInterface.setDevicePackage(this.mDevice, resolveInfo.activityInfo.packageName, iMyUserId);
                } else {
                    iUsbManagerAsInterface.setDevicePackage(this.mDevice, (String) null, iMyUserId);
                }
            } else {
                UsbAccessory usbAccessory = this.mAccessory;
                if (usbAccessory != null) {
                    iUsbManagerAsInterface.grantAccessoryPermission(usbAccessory, i);
                    if (z) {
                        iUsbManagerAsInterface.setAccessoryPackage(this.mAccessory, resolveInfo.activityInfo.packageName, iMyUserId);
                    } else {
                        iUsbManagerAsInterface.setAccessoryPackage(this.mAccessory, (String) null, iMyUserId);
                    }
                }
            }
            try {
                targetInfo.startAsUser(this, (Bundle) null, UserHandle.of(iMyUserId));
            } catch (ActivityNotFoundException e) {
                Log.e("UsbResolverActivity", "startActivity failed", e);
            }
        } catch (RemoteException e2) {
            Log.e("UsbResolverActivity", "onIntentSelected failed", e2);
        }
        return true;
    }

    public final boolean shouldShowTabs() {
        return false;
    }
}
