package androidx.asynclayoutinflater.view;

import androidx.asynclayoutinflater.view.AsyncLayoutInflater;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class AsyncLayoutInflater$1$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ AsyncLayoutInflater.InflateRequest f$1;

    public /* synthetic */ AsyncLayoutInflater$1$$ExternalSyntheticLambda0(Object obj, AsyncLayoutInflater.InflateRequest inflateRequest, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = inflateRequest;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                AsyncLayoutInflater.triggerCallbacks(this.f$1, ((AsyncLayoutInflater.C01091) this.f$0).this$0.mInflateThread);
                break;
            default:
                AsyncLayoutInflater.InflateThread inflateThread = (AsyncLayoutInflater.InflateThread) this.f$0;
                AsyncLayoutInflater.InflateRequest inflateRequest = this.f$1;
                AsyncLayoutInflater.InflateThread inflateThread2 = AsyncLayoutInflater.InflateThread.sInstance;
                inflateThread.getClass();
                AsyncLayoutInflater.triggerCallbacks(inflateRequest, inflateThread);
                break;
        }
    }
}
