package android.net.ipmemorystore;


/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public interface OnBlobRetrievedListener {
    static IOnBlobRetrievedListener toAIDL(OnBlobRetrievedListener onBlobRetrievedListener) {
        return new IOnBlobRetrievedListener
                .Stub() { // from class: android.net.ipmemorystore.OnBlobRetrievedListener.1
            @Override // android.net.ipmemorystore.IOnBlobRetrievedListener
            public final String getInterfaceHash() {
                return "d5ea5eb3ddbdaa9a986ce6ba70b0804ca3e39b0c";
            }

            @Override // android.net.ipmemorystore.IOnBlobRetrievedListener
            public final int getInterfaceVersion() {
                return 10;
            }

            @Override // android.net.ipmemorystore.IOnBlobRetrievedListener
            public final void onBlobRetrieved(
                    StatusParcelable statusParcelable, String str, String str2, Blob blob) {
                OnBlobRetrievedListener onBlobRetrievedListener2 = OnBlobRetrievedListener.this;
                if (onBlobRetrievedListener2 != null) {
                    onBlobRetrievedListener2.onBlobRetrieved(
                            new Status(statusParcelable), str, str2, blob);
                }
            }
        };
    }

    void onBlobRetrieved(Status status, String str, String str2, Blob blob);
}
