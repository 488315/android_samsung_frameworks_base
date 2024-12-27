package com.android.server.broadcastradio;

import android.content.Context;
import android.hardware.radio.IRadioService;
import android.os.ServiceManager;
import com.android.server.SystemService;
import com.android.server.broadcastradio.aidl.BroadcastRadioServiceImpl;
import com.android.server.utils.Slogf;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class BroadcastRadioService extends SystemService {
    public final IRadioService mServiceImpl;

    public BroadcastRadioService(Context context) {
        super(context);
        IRadioService iRadioService;
        List list = IRadioServiceAidlImpl.SERVICE_NAMES;
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (true) {
            List list2 = IRadioServiceAidlImpl.SERVICE_NAMES;
            if (i >= list2.size()) {
                break;
            }
            if (ServiceManager.waitForDeclaredService((String) list2.get(i)) != null) {
                arrayList.add((String) list2.get(i));
            }
            i++;
        }
        if (arrayList.isEmpty()) {
            iRadioService = new IRadioServiceHidlImpl(this);
        } else {
            IRadioService iRadioServiceAidlImpl = new IRadioServiceAidlImpl(this, new BroadcastRadioServiceImpl(arrayList));
            Slogf.i("BcRadioSrvAidl", "Initialize BroadcastRadioServiceAidl(%s)", this);
            iRadioService = iRadioServiceAidlImpl;
        }
        this.mServiceImpl = iRadioService;
    }

    public final void enforcePolicyAccess() {
        if (getContext().checkCallingPermission("android.permission.ACCESS_BROADCAST_RADIO") != 0) {
            throw new SecurityException("ACCESS_BROADCAST_RADIO permission not granted");
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("broadcastradio", this.mServiceImpl.asBinder());
    }
}
