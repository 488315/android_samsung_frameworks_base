package android.net.ipmemorystore;


public interface OnStatusListener {
    static IOnStatusListener toAIDL(OnStatusListener onStatusListener) {
        return new IOnStatusListener
                .Stub() { // from class: android.net.ipmemorystore.OnStatusListener.1
            @Override // android.net.ipmemorystore.IOnStatusListener
            public final String getInterfaceHash() {
                return "d5ea5eb3ddbdaa9a986ce6ba70b0804ca3e39b0c";
            }

            @Override // android.net.ipmemorystore.IOnStatusListener
            public final int getInterfaceVersion() {
                return 10;
            }

            @Override // android.net.ipmemorystore.IOnStatusListener
            public final void onComplete(StatusParcelable statusParcelable) {
                OnStatusListener onStatusListener2 = OnStatusListener.this;
                if (onStatusListener2 != null) {
                    onStatusListener2.onComplete(new Status(statusParcelable));
                }
            }
        };
    }

    void onComplete(Status status);
}
