package com.android.ims.internal.uce;

import com.android.ims.internal.uce.common.UceLong;
import com.android.ims.internal.uce.options.IOptionsListener;
import com.android.ims.internal.uce.options.IOptionsService;
import com.android.ims.internal.uce.presence.IPresenceListener;
import com.android.ims.internal.uce.presence.IPresenceService;
import com.android.ims.internal.uce.uceservice.IUceListener;
import com.android.ims.internal.uce.uceservice.IUceService;

/* loaded from: classes5.dex */
public abstract class UceServiceBase {
    private UceServiceBinder mBinder;

    protected int onCreateOptionsService(IOptionsListener iOptionsListener, UceLong uceLong) {
        return 0;
    }

    protected int onCreateOptionsService(IOptionsListener iOptionsListener, UceLong uceLong, String str) {
        return 0;
    }

    protected int onCreatePresService(IPresenceListener iPresenceListener, UceLong uceLong) {
        return 0;
    }

    protected int onCreatePresService(IPresenceListener iPresenceListener, UceLong uceLong, String str) {
        return 0;
    }

    protected void onDestroyOptionsService(int i) {
    }

    protected void onDestroyPresService(int i) {
    }

    protected IOptionsService onGetOptionsService() {
        return null;
    }

    protected IOptionsService onGetOptionsService(String str) {
        return null;
    }

    protected IPresenceService onGetPresenceService() {
        return null;
    }

    protected IPresenceService onGetPresenceService(String str) {
        return null;
    }

    protected boolean onGetServiceStatus() {
        return false;
    }

    protected boolean onIsServiceStarted() {
        return false;
    }

    protected boolean onServiceStart(IUceListener iUceListener) {
        return false;
    }

    protected boolean onStopService() {
        return false;
    }

    private final class UceServiceBinder extends IUceService.Stub {
        private UceServiceBinder() {
        }

        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public boolean startService(IUceListener iUceListener) {
            return UceServiceBase.this.onServiceStart(iUceListener);
        }

        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public boolean stopService() {
            return UceServiceBase.this.onStopService();
        }

        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public boolean isServiceStarted() {
            return UceServiceBase.this.onIsServiceStarted();
        }

        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public int createOptionsService(IOptionsListener iOptionsListener, UceLong uceLong) {
            return UceServiceBase.this.onCreateOptionsService(iOptionsListener, uceLong);
        }

        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public int createOptionsServiceForSubscription(IOptionsListener iOptionsListener, UceLong uceLong, String str) {
            return UceServiceBase.this.onCreateOptionsService(iOptionsListener, uceLong, str);
        }

        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public void destroyOptionsService(int i) {
            UceServiceBase.this.onDestroyOptionsService(i);
        }

        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public int createPresenceService(IPresenceListener iPresenceListener, UceLong uceLong) {
            return UceServiceBase.this.onCreatePresService(iPresenceListener, uceLong);
        }

        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public int createPresenceServiceForSubscription(IPresenceListener iPresenceListener, UceLong uceLong, String str) {
            return UceServiceBase.this.onCreatePresService(iPresenceListener, uceLong, str);
        }

        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public void destroyPresenceService(int i) {
            UceServiceBase.this.onDestroyPresService(i);
        }

        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public boolean getServiceStatus() {
            return UceServiceBase.this.onGetServiceStatus();
        }

        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public IPresenceService getPresenceService() {
            return UceServiceBase.this.onGetPresenceService();
        }

        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public IPresenceService getPresenceServiceForSubscription(String str) {
            return UceServiceBase.this.onGetPresenceService(str);
        }

        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public IOptionsService getOptionsService() {
            return UceServiceBase.this.onGetOptionsService();
        }

        @Override // com.android.ims.internal.uce.uceservice.IUceService
        public IOptionsService getOptionsServiceForSubscription(String str) {
            return UceServiceBase.this.onGetOptionsService(str);
        }
    }

    public UceServiceBinder getBinder() {
        if (this.mBinder == null) {
            this.mBinder = new UceServiceBinder();
        }
        return this.mBinder;
    }
}
