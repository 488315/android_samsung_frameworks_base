package com.android.internal.telecom;

import android.telecom.PhoneAccountHandle;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes4.dex */
public class ClientTransactionalServiceRepository {
    private static final Map<PhoneAccountHandle, ClientTransactionalServiceWrapper> LOOKUP_TABLE = new ConcurrentHashMap();

    public ClientTransactionalServiceWrapper addNewCallForTransactionalServiceWrapper(PhoneAccountHandle phoneAccountHandle) {
        ClientTransactionalServiceWrapper transactionalServiceWrapper;
        if (!hasExistingServiceWrapper(phoneAccountHandle)) {
            transactionalServiceWrapper = new ClientTransactionalServiceWrapper(phoneAccountHandle, this);
        } else {
            transactionalServiceWrapper = getTransactionalServiceWrapper(phoneAccountHandle);
        }
        LOOKUP_TABLE.put(phoneAccountHandle, transactionalServiceWrapper);
        return transactionalServiceWrapper;
    }

    private ClientTransactionalServiceWrapper getTransactionalServiceWrapper(PhoneAccountHandle phoneAccountHandle) {
        return LOOKUP_TABLE.get(phoneAccountHandle);
    }

    private boolean hasExistingServiceWrapper(PhoneAccountHandle phoneAccountHandle) {
        return LOOKUP_TABLE.containsKey(phoneAccountHandle);
    }

    public boolean removeServiceWrapper(PhoneAccountHandle phoneAccountHandle) {
        if (!hasExistingServiceWrapper(phoneAccountHandle)) {
            return false;
        }
        LOOKUP_TABLE.remove(phoneAccountHandle);
        return true;
    }

    public boolean removeCallFromServiceWrapper(PhoneAccountHandle phoneAccountHandle, String str) {
        if (!hasExistingServiceWrapper(phoneAccountHandle)) {
            return false;
        }
        LOOKUP_TABLE.get(phoneAccountHandle).untrackCall(str);
        return true;
    }
}
