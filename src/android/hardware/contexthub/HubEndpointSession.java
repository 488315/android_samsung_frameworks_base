package android.hardware.contexthub;

import android.annotation.SystemApi;
import android.hardware.location.ContextHubTransaction;
import android.hardware.location.ContextHubTransactionHelper;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.util.CloseGuard;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

@SystemApi
/* loaded from: classes2.dex */
public class HubEndpointSession implements AutoCloseable {
    private final HubEndpointInfo mDestination;
    private final HubEndpoint mHubEndpoint;
    private final int mId;
    private final HubEndpointInfo mInitiator;
    private final String mServiceDescriptor;
    private final CloseGuard mCloseGuard = new CloseGuard();
    private final AtomicBoolean mIsClosed = new AtomicBoolean(true);

    HubEndpointSession(int i, HubEndpoint hubEndpoint, HubEndpointInfo hubEndpointInfo, HubEndpointInfo hubEndpointInfo2, String str) {
        this.mId = i;
        this.mHubEndpoint = hubEndpoint;
        this.mDestination = hubEndpointInfo;
        this.mInitiator = hubEndpointInfo2;
        this.mServiceDescriptor = str;
    }

    public ContextHubTransaction<Void> sendMessage(HubMessage hubMessage) {
        if (this.mIsClosed.get()) {
            throw new IllegalStateException("Session is already closed.");
        }
        boolean isResponseRequired = hubMessage.isResponseRequired();
        ContextHubTransaction<Void> contextHubTransaction = new ContextHubTransaction<>(isResponseRequired ? 7 : 6);
        if (!isResponseRequired) {
            this.mHubEndpoint.sendMessage(this, hubMessage, null);
            contextHubTransaction.setResponse(new ContextHubTransaction.Response<>(0, null));
            return contextHubTransaction;
        }
        this.mHubEndpoint.sendMessage(this, hubMessage, ContextHubTransactionHelper.createTransactionCallback(contextHubTransaction));
        return contextHubTransaction;
    }

    public int getId() {
        return this.mId;
    }

    public void setOpened() {
        this.mIsClosed.set(false);
        this.mCloseGuard.open("close");
    }

    public void setClosed() {
        this.mIsClosed.set(true);
        this.mCloseGuard.close();
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        if (this.mIsClosed.getAndSet(true)) {
            return;
        }
        this.mCloseGuard.close();
        this.mHubEndpoint.closeSession(this);
    }

    public String getServiceDescriptor() {
        return this.mServiceDescriptor;
    }

    public String toString() {
        return "Session [" + this.mId + "]: [" + this.mInitiator + "]->[" + this.mDestination + NavigationBarInflaterView.SIZE_MOD_END;
    }

    public boolean equals(Object obj) {
        boolean z;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof HubEndpointSession)) {
            return false;
        }
        HubEndpointSession hubEndpointSession = (HubEndpointSession) obj;
        boolean z2 = hubEndpointSession.getId() == this.mId;
        String str = this.mServiceDescriptor;
        if (str != null) {
            z = str.equals(hubEndpointSession.getServiceDescriptor());
        } else {
            z = hubEndpointSession.getServiceDescriptor() == null;
        }
        return z2 & z & (this.mInitiator.equals(hubEndpointSession.mInitiator) && this.mDestination.equals(hubEndpointSession.mDestination));
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mId), this.mServiceDescriptor, this.mInitiator, this.mDestination);
    }

    protected void finalize() throws Throwable {
        try {
            CloseGuard closeGuard = this.mCloseGuard;
            if (closeGuard != null) {
                closeGuard.warnIfOpen();
            }
            close();
        } finally {
            super.finalize();
        }
    }
}
