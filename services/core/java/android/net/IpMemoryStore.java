package android.net;

import android.content.Context;
import android.net.networkstack.ModuleNetworkStackClient;
import android.util.Log;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public class IpMemoryStore extends IpMemoryStoreClient {
    private static final String TAG = "IpMemoryStore";
    private final CompletableFuture mService;
    private final AtomicReference mTailNode;

    public IpMemoryStore(Context context) {
        super(context);
        CompletableFuture completableFuture = new CompletableFuture();
        this.mService = completableFuture;
        this.mTailNode = new AtomicReference(completableFuture);
        getModuleNetworkStackClient(context)
                .fetchIpMemoryStore(
                        new IIpMemoryStoreCallbacks
                                .Stub() { // from class: android.net.IpMemoryStore.1
                            @Override // android.net.IIpMemoryStoreCallbacks
                            public final String getInterfaceHash() {
                                return "d5ea5eb3ddbdaa9a986ce6ba70b0804ca3e39b0c";
                            }

                            @Override // android.net.IIpMemoryStoreCallbacks
                            public final int getInterfaceVersion() {
                                return 10;
                            }

                            @Override // android.net.IIpMemoryStoreCallbacks
                            public final void onIpMemoryStoreFetched(
                                    IIpMemoryStore iIpMemoryStore) {
                                IpMemoryStore.this.mService.complete(iIpMemoryStore);
                            }
                        });
    }

    public static IpMemoryStore getMemoryStore(Context context) {
        return new IpMemoryStore(context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ IIpMemoryStore lambda$runWhenServiceReady$0(
            Consumer consumer, IIpMemoryStore iIpMemoryStore, Throwable th) {
        if (th != null) {
            Log.wtf(TAG, "Error fetching IpMemoryStore", th);
            return iIpMemoryStore;
        }
        try {
            consumer.accept(iIpMemoryStore);
        } catch (Exception e) {
            Log.wtf(TAG, "Exception occurred: " + e.getMessage());
        }
        return iIpMemoryStore;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ CompletableFuture lambda$runWhenServiceReady$1(
            final Consumer consumer, CompletableFuture completableFuture) {
        return completableFuture.handle(
                new BiFunction() { // from class:
                                   // android.net.IpMemoryStore$$ExternalSyntheticLambda1
                    @Override // java.util.function.BiFunction
                    public final Object apply(Object obj, Object obj2) {
                        IIpMemoryStore lambda$runWhenServiceReady$0;
                        lambda$runWhenServiceReady$0 =
                                IpMemoryStore.lambda$runWhenServiceReady$0(
                                        consumer, (IIpMemoryStore) obj, (Throwable) obj2);
                        return lambda$runWhenServiceReady$0;
                    }
                });
    }

    public ModuleNetworkStackClient getModuleNetworkStackClient(Context context) {
        return ModuleNetworkStackClient.getInstance(context);
    }

    @Override // android.net.IpMemoryStoreClient
    public void runWhenServiceReady(final Consumer consumer) throws ExecutionException {
        this.mTailNode.getAndUpdate(
                new UnaryOperator() { // from class:
                                      // android.net.IpMemoryStore$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        CompletableFuture lambda$runWhenServiceReady$1;
                        lambda$runWhenServiceReady$1 =
                                IpMemoryStore.lambda$runWhenServiceReady$1(
                                        consumer, (CompletableFuture) obj);
                        return lambda$runWhenServiceReady$1;
                    }
                });
    }
}
