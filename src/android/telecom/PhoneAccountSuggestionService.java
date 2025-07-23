package android.telecom;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import com.android.internal.telecom.IPhoneAccountSuggestionCallback;
import com.android.internal.telecom.IPhoneAccountSuggestionService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SystemApi
/* loaded from: classes4.dex */
public class PhoneAccountSuggestionService extends Service {
    public static final String SERVICE_INTERFACE = "android.telecom.PhoneAccountSuggestionService";
    private IPhoneAccountSuggestionService mInterface = new IPhoneAccountSuggestionService.Stub() { // from class: android.telecom.PhoneAccountSuggestionService.1
        @Override // com.android.internal.telecom.IPhoneAccountSuggestionService
        public void onAccountSuggestionRequest(IPhoneAccountSuggestionCallback iPhoneAccountSuggestionCallback, String str) {
            PhoneAccountSuggestionService.this.mCallbackMap.put(str, iPhoneAccountSuggestionCallback);
            PhoneAccountSuggestionService.this.onAccountSuggestionRequest(str);
        }
    };
    private final Map<String, IPhoneAccountSuggestionCallback> mCallbackMap = new HashMap();

    public void onAccountSuggestionRequest(String str) {
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return this.mInterface.asBinder();
    }

    public final void suggestPhoneAccounts(String str, List<PhoneAccountSuggestion> list) {
        IPhoneAccountSuggestionCallback remove = this.mCallbackMap.remove(str);
        if (remove == null) {
            Log.w(this, "No suggestions requested for the number %s", Log.pii(str));
            return;
        }
        try {
            remove.suggestPhoneAccounts(str, list);
        } catch (RemoteException unused) {
            Log.w(this, "Remote exception calling suggestPhoneAccounts", new Object[0]);
        }
    }
}
