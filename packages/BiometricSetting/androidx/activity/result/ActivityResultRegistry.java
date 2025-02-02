package androidx.activity.result;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/* loaded from: classes.dex */
public abstract class ActivityResultRegistry {
    private Random mRandom = new Random();
    private final Map<Integer, String> mRcToKey = new HashMap();
    final Map<String, Integer> mKeyToRc = new HashMap();
    private final Map<String, LifecycleContainer> mKeyToLifecycleContainers = new HashMap();
    ArrayList<String> mLaunchedKeys = new ArrayList<>();
    final transient Map<String, CallbackAndContract<?>> mKeyToCallback = new HashMap();
    final Map<String, Object> mParsedPendingResults = new HashMap();
    final Bundle mPendingResults = new Bundle();

    /* renamed from: androidx.activity.result.ActivityResultRegistry$1 */
    class C00081 implements LifecycleEventObserver {
        @Override // androidx.lifecycle.LifecycleEventObserver
        public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
            if (Lifecycle.Event.ON_START.equals(event)) {
                throw null;
            }
            if (Lifecycle.Event.ON_STOP.equals(event)) {
                throw null;
            }
            if (Lifecycle.Event.ON_DESTROY.equals(event)) {
                throw null;
            }
        }
    }

    private static class CallbackAndContract<O> {
        final ActivityResultCallback<O> mCallback;
        final ActivityResultContract<?, O> mContract;

        CallbackAndContract(ActivityResultCallback<O> activityResultCallback, ActivityResultContract<?, O> activityResultContract) {
            this.mCallback = activityResultCallback;
            this.mContract = activityResultContract;
        }
    }

    private static class LifecycleContainer {
    }

    public final boolean dispatchResult(int i, int i2, Intent intent) {
        ActivityResultCallback<O> activityResultCallback;
        String str = (String) ((HashMap) this.mRcToKey).get(Integer.valueOf(i));
        if (str == null) {
            return false;
        }
        CallbackAndContract callbackAndContract = (CallbackAndContract) ((HashMap) this.mKeyToCallback).get(str);
        if (callbackAndContract == null || (activityResultCallback = callbackAndContract.mCallback) == 0 || !this.mLaunchedKeys.contains(str)) {
            ((HashMap) this.mParsedPendingResults).remove(str);
            this.mPendingResults.putParcelable(str, new ActivityResult(intent, i2));
            return true;
        }
        activityResultCallback.onActivityResult(callbackAndContract.mContract.parseResult(intent, i2));
        this.mLaunchedKeys.remove(str);
        return true;
    }

    public final void onRestoreInstanceState(Bundle bundle) {
        ArrayList<Integer> integerArrayList = bundle.getIntegerArrayList("KEY_COMPONENT_ACTIVITY_REGISTERED_RCS");
        ArrayList<String> stringArrayList = bundle.getStringArrayList("KEY_COMPONENT_ACTIVITY_REGISTERED_KEYS");
        if (stringArrayList == null || integerArrayList == null) {
            return;
        }
        this.mLaunchedKeys = bundle.getStringArrayList("KEY_COMPONENT_ACTIVITY_LAUNCHED_KEYS");
        this.mRandom = (Random) bundle.getSerializable("KEY_COMPONENT_ACTIVITY_RANDOM_OBJECT");
        Bundle bundle2 = bundle.getBundle("KEY_COMPONENT_ACTIVITY_PENDING_RESULT");
        Bundle bundle3 = this.mPendingResults;
        bundle3.putAll(bundle2);
        for (int i = 0; i < stringArrayList.size(); i++) {
            String str = stringArrayList.get(i);
            Map<String, Integer> map = this.mKeyToRc;
            HashMap hashMap = (HashMap) map;
            boolean containsKey = hashMap.containsKey(str);
            Map<Integer, String> map2 = this.mRcToKey;
            if (containsKey) {
                Integer num = (Integer) hashMap.remove(str);
                if (!bundle3.containsKey(str)) {
                    ((HashMap) map2).remove(num);
                }
            }
            int intValue = integerArrayList.get(i).intValue();
            String str2 = stringArrayList.get(i);
            ((HashMap) map2).put(Integer.valueOf(intValue), str2);
            ((HashMap) map).put(str2, Integer.valueOf(intValue));
        }
    }

    public final void onSaveInstanceState(Bundle bundle) {
        HashMap hashMap = (HashMap) this.mKeyToRc;
        bundle.putIntegerArrayList("KEY_COMPONENT_ACTIVITY_REGISTERED_RCS", new ArrayList<>(hashMap.values()));
        bundle.putStringArrayList("KEY_COMPONENT_ACTIVITY_REGISTERED_KEYS", new ArrayList<>(hashMap.keySet()));
        bundle.putStringArrayList("KEY_COMPONENT_ACTIVITY_LAUNCHED_KEYS", new ArrayList<>(this.mLaunchedKeys));
        bundle.putBundle("KEY_COMPONENT_ACTIVITY_PENDING_RESULT", (Bundle) this.mPendingResults.clone());
        bundle.putSerializable("KEY_COMPONENT_ACTIVITY_RANDOM_OBJECT", this.mRandom);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <I, O> ActivityResultLauncher<I> register(final String str, ActivityResultContract<I, O> activityResultContract, ActivityResultCallback<O> activityResultCallback) {
        int i;
        Map<Integer, String> map;
        Map<String, Integer> map2 = this.mKeyToRc;
        if (((Integer) ((HashMap) map2).get(str)) == null) {
            int nextInt = this.mRandom.nextInt(2147418112);
            while (true) {
                i = nextInt + 65536;
                map = this.mRcToKey;
                if (!((HashMap) map).containsKey(Integer.valueOf(i))) {
                    break;
                }
                nextInt = this.mRandom.nextInt(2147418112);
            }
            ((HashMap) map).put(Integer.valueOf(i), str);
            ((HashMap) map2).put(str, Integer.valueOf(i));
        }
        ((HashMap) this.mKeyToCallback).put(str, new CallbackAndContract(activityResultCallback, activityResultContract));
        HashMap hashMap = (HashMap) this.mParsedPendingResults;
        if (hashMap.containsKey(str)) {
            Object obj = hashMap.get(str);
            hashMap.remove(str);
            activityResultCallback.onActivityResult(obj);
        }
        Bundle bundle = this.mPendingResults;
        ActivityResult activityResult = (ActivityResult) bundle.getParcelable(str);
        if (activityResult != null) {
            bundle.remove(str);
            activityResultCallback.onActivityResult(activityResultContract.parseResult(activityResult.getData(), activityResult.getResultCode()));
        }
        return new ActivityResultLauncher<I>() { // from class: androidx.activity.result.ActivityResultRegistry.3
            @Override // androidx.activity.result.ActivityResultLauncher
            public final void unregister() {
                ActivityResultRegistry.this.unregister(str);
            }
        };
    }

    final void unregister(String str) {
        Integer num;
        if (!this.mLaunchedKeys.contains(str) && (num = (Integer) ((HashMap) this.mKeyToRc).remove(str)) != null) {
            ((HashMap) this.mRcToKey).remove(num);
        }
        ((HashMap) this.mKeyToCallback).remove(str);
        Map<String, Object> map = this.mParsedPendingResults;
        if (((HashMap) map).containsKey(str)) {
            Log.w("ActivityResultRegistry", "Dropping pending result for request " + str + ": " + ((HashMap) map).get(str));
            ((HashMap) map).remove(str);
        }
        Bundle bundle = this.mPendingResults;
        if (bundle.containsKey(str)) {
            Log.w("ActivityResultRegistry", "Dropping pending result for request " + str + ": " + bundle.getParcelable(str));
            bundle.remove(str);
        }
        if (((LifecycleContainer) ((HashMap) this.mKeyToLifecycleContainers).get(str)) != null) {
            throw null;
        }
    }
}
