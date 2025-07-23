package android.app.people;

import android.annotation.SystemApi;
import android.app.people.IConversationListener;
import android.app.people.IPeopleManager;
import android.app.people.PeopleManager;
import android.content.Context;
import android.content.pm.ParceledListSlice;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Pair;
import android.util.Slog;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public final class PeopleManager {
    private static final String LOG_TAG = "PeopleManager";
    private Context mContext;
    public Map<ConversationListener, Pair<Executor, IConversationListener>> mConversationListeners;
    private IPeopleManager mService;

    public interface ConversationListener {
        default void onConversationUpdate(ConversationChannel conversationChannel) {
        }
    }

    public PeopleManager(Context context) throws ServiceManager.ServiceNotFoundException {
        this.mConversationListeners = new HashMap();
        this.mContext = context;
        this.mService = IPeopleManager.Stub.asInterface(ServiceManager.getServiceOrThrow(Context.PEOPLE_SERVICE));
    }

    public PeopleManager(Context context, IPeopleManager iPeopleManager) {
        this.mConversationListeners = new HashMap();
        this.mContext = context;
        this.mService = iPeopleManager;
    }

    @SystemApi
    public boolean isConversation(String str, String str2) {
        Preconditions.checkStringNotEmpty(str);
        Preconditions.checkStringNotEmpty(str2);
        try {
            return this.mService.isConversation(str, this.mContext.getUserId(), str2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void addOrUpdateStatus(String str, ConversationStatus conversationStatus) {
        Preconditions.checkStringNotEmpty(str);
        Objects.requireNonNull(conversationStatus);
        try {
            this.mService.addOrUpdateStatus(this.mContext.getPackageName(), this.mContext.getUserId(), str, conversationStatus);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void clearStatus(String str, String str2) {
        Preconditions.checkStringNotEmpty(str);
        Preconditions.checkStringNotEmpty(str2);
        try {
            this.mService.clearStatus(this.mContext.getPackageName(), this.mContext.getUserId(), str, str2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void clearStatuses(String str) {
        Preconditions.checkStringNotEmpty(str);
        try {
            this.mService.clearStatuses(this.mContext.getPackageName(), this.mContext.getUserId(), str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<ConversationStatus> getStatuses(String str) {
        try {
            ParceledListSlice statuses = this.mService.getStatuses(this.mContext.getPackageName(), this.mContext.getUserId(), str);
            if (statuses != null) {
                return statuses.getList();
            }
            return new ArrayList();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerConversationListener(String str, int i, String str2, ConversationListener conversationListener, Executor executor) {
        Objects.requireNonNull(conversationListener, "Listener cannot be null");
        Objects.requireNonNull(str, "Package name cannot be null");
        Objects.requireNonNull(str2, "Shortcut ID cannot be null");
        synchronized (this.mConversationListeners) {
            ConversationListenerProxy conversationListenerProxy = new ConversationListenerProxy(executor, conversationListener);
            try {
                this.mService.registerConversationListener(str, i, str2, conversationListenerProxy);
                this.mConversationListeners.put(conversationListener, new Pair<>(executor, conversationListenerProxy));
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void unregisterConversationListener(ConversationListener conversationListener) {
        Objects.requireNonNull(conversationListener, "Listener cannot be null");
        synchronized (this.mConversationListeners) {
            if (this.mConversationListeners.containsKey(conversationListener)) {
                try {
                    this.mService.unregisterConversationListener(this.mConversationListeners.remove(conversationListener).second);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ConversationListenerProxy extends IConversationListener.Stub {
        private final Executor mExecutor;
        private final ConversationListener mListener;

        ConversationListenerProxy(Executor executor, ConversationListener conversationListener) {
            this.mExecutor = executor;
            this.mListener = conversationListener;
        }

        @Override // android.app.people.IConversationListener
        public void onConversationUpdate(final ConversationChannel conversationChannel) {
            Executor executor;
            if (this.mListener == null || (executor = this.mExecutor) == null) {
                Slog.e(PeopleManager.LOG_TAG, "Binder is dead");
            } else {
                executor.execute(new Runnable() { // from class: android.app.people.PeopleManager$ConversationListenerProxy$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        PeopleManager.ConversationListenerProxy.this.lambda$onConversationUpdate$0(conversationChannel);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onConversationUpdate$0(ConversationChannel conversationChannel) {
            this.mListener.onConversationUpdate(conversationChannel);
        }
    }
}
