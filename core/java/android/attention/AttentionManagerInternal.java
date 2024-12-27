package android.attention;

/* loaded from: classes.dex */
public abstract class AttentionManagerInternal {

    public abstract static class AttentionCallbackInternal {
        public abstract void onFailure(int i);

        public abstract void onSuccess(int i, long j);
    }

    public interface ProximityUpdateCallbackInternal {
        void onProximityUpdate(double d);
    }

    public abstract void cancelAttentionCheck(AttentionCallbackInternal attentionCallbackInternal);

    public abstract boolean checkAttention(
            long j, AttentionCallbackInternal attentionCallbackInternal);

    public abstract boolean isAttentionServiceSupported();

    public abstract boolean isProximitySupported();

    public abstract boolean onStartProximityUpdates(
            ProximityUpdateCallbackInternal proximityUpdateCallbackInternal);

    public abstract void onStopProximityUpdates(
            ProximityUpdateCallbackInternal proximityUpdateCallbackInternal);
}
