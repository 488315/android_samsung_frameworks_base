package android.net.ipmemorystore;


/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public interface OnDeleteStatusListener {
    static IOnStatusAndCountListener toAIDL(OnDeleteStatusListener onDeleteStatusListener) {
        return new IOnStatusAndCountListener
                .Stub() { // from class: android.net.ipmemorystore.OnDeleteStatusListener.1
            @Override // android.net.ipmemorystore.IOnStatusAndCountListener
            public final String getInterfaceHash() {
                return "d5ea5eb3ddbdaa9a986ce6ba70b0804ca3e39b0c";
            }

            @Override // android.net.ipmemorystore.IOnStatusAndCountListener
            public final int getInterfaceVersion() {
                return 10;
            }

            @Override // android.net.ipmemorystore.IOnStatusAndCountListener
            public final void onComplete(StatusParcelable statusParcelable, int i) {
                OnDeleteStatusListener onDeleteStatusListener2 = OnDeleteStatusListener.this;
                if (onDeleteStatusListener2 != null) {
                    onDeleteStatusListener2.onComplete(new Status(statusParcelable), i);
                }
            }
        };
    }

    void onComplete(Status status, int i);
}
