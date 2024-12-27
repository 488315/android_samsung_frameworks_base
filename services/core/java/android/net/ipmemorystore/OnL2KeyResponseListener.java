package android.net.ipmemorystore;


/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public interface OnL2KeyResponseListener {
    static IOnL2KeyResponseListener toAIDL(OnL2KeyResponseListener onL2KeyResponseListener) {
        return new IOnL2KeyResponseListener
                .Stub() { // from class: android.net.ipmemorystore.OnL2KeyResponseListener.1
            @Override // android.net.ipmemorystore.IOnL2KeyResponseListener
            public final String getInterfaceHash() {
                return "d5ea5eb3ddbdaa9a986ce6ba70b0804ca3e39b0c";
            }

            @Override // android.net.ipmemorystore.IOnL2KeyResponseListener
            public final int getInterfaceVersion() {
                return 10;
            }

            @Override // android.net.ipmemorystore.IOnL2KeyResponseListener
            public final void onL2KeyResponse(StatusParcelable statusParcelable, String str) {
                OnL2KeyResponseListener onL2KeyResponseListener2 = OnL2KeyResponseListener.this;
                if (onL2KeyResponseListener2 != null) {
                    onL2KeyResponseListener2.onL2KeyResponse(new Status(statusParcelable), str);
                }
            }
        };
    }

    void onL2KeyResponse(Status status, String str);
}
