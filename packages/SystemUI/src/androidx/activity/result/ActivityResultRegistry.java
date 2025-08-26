package androidx.activity.result;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.random.Random;
import kotlin.sequences.ConstrainedOnceSequence;
import kotlin.sequences.GeneratorSequence;
import kotlin.sequences.SequencesKt__SequencesKt$$ExternalSyntheticLambda1;

/* loaded from: classes.dex */
public abstract class ActivityResultRegistry {
    public final Map rcToKey = new LinkedHashMap();
    public final Map keyToRc = new LinkedHashMap();
    public final Map keyToLifecycleContainers = new LinkedHashMap();
    public final List launchedKeys = new ArrayList();
    public final transient Map keyToCallback = new LinkedHashMap();
    public final Map parsedPendingResults = new LinkedHashMap();
    public final Bundle pendingResults = new Bundle();

    public final class CallbackAndContract {
        public final ActivityResultCallback callback;
        public final ActivityResultContract contract;

        public CallbackAndContract(ActivityResultCallback activityResultCallback, ActivityResultContract activityResultContract) {
            this.callback = activityResultCallback;
            this.contract = activityResultContract;
        }
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class LifecycleContainer {
        public final Lifecycle lifecycle;
        public final List observers = new ArrayList();

        public LifecycleContainer(Lifecycle lifecycle) {
            this.lifecycle = lifecycle;
        }
    }

    /* renamed from: androidx.activity.result.ActivityResultRegistry$register$3, reason: invalid class name */
    public final class AnonymousClass3 extends ActivityResultLauncher {
        public final /* synthetic */ String $key;

        public AnonymousClass3(String str, ActivityResultContract activityResultContract) {
            this.$key = str;
        }

        public final void unregister() {
            Integer num;
            ActivityResultRegistry activityResultRegistry = ActivityResultRegistry.this;
            ArrayList arrayList = (ArrayList) activityResultRegistry.launchedKeys;
            String str = this.$key;
            if (!arrayList.contains(str) && (num = (Integer) activityResultRegistry.keyToRc.remove(str)) != null) {
                activityResultRegistry.rcToKey.remove(num);
            }
            activityResultRegistry.keyToCallback.remove(str);
            if (activityResultRegistry.parsedPendingResults.containsKey(str)) {
                StringBuilder sbM = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("Dropping pending result for request ", str, ": ");
                sbM.append(((LinkedHashMap) activityResultRegistry.parsedPendingResults).get(str));
                Log.w("ActivityResultRegistry", sbM.toString());
                activityResultRegistry.parsedPendingResults.remove(str);
            }
            if (activityResultRegistry.pendingResults.containsKey(str)) {
                Log.w("ActivityResultRegistry", "Dropping pending result for request " + str + ": " + ((ActivityResult) activityResultRegistry.pendingResults.getParcelable(str, ActivityResult.class)));
                activityResultRegistry.pendingResults.remove(str);
            }
            LifecycleContainer lifecycleContainer = (LifecycleContainer) ((LinkedHashMap) activityResultRegistry.keyToLifecycleContainers).get(str);
            if (lifecycleContainer != null) {
                ArrayList arrayList2 = (ArrayList) lifecycleContainer.observers;
                int size = arrayList2.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList2.get(i);
                    i++;
                    lifecycleContainer.lifecycle.removeObserver((LifecycleEventObserver) obj);
                }
                ((ArrayList) lifecycleContainer.observers).clear();
                activityResultRegistry.keyToLifecycleContainers.remove(str);
            }
        }
    }

    static {
        new Companion(null);
    }

    public final boolean dispatchResult(int i, int i2, Intent intent) {
        String str = (String) ((LinkedHashMap) this.rcToKey).get(Integer.valueOf(i));
        if (str == null) {
            return false;
        }
        CallbackAndContract callbackAndContract = (CallbackAndContract) ((LinkedHashMap) this.keyToCallback).get(str);
        if ((callbackAndContract != null ? callbackAndContract.callback : null) == null || !((ArrayList) this.launchedKeys).contains(str)) {
            this.parsedPendingResults.remove(str);
            this.pendingResults.putParcelable(str, new ActivityResult(i2, intent));
            return true;
        }
        callbackAndContract.callback.onActivityResult(callbackAndContract.contract.parseResult(i2, intent));
        ((ArrayList) this.launchedKeys).remove(str);
        return true;
    }

    public final AnonymousClass3 register(String str, ActivityResultContract activityResultContract, ActivityResultCallback activityResultCallback) {
        if (((Integer) ((LinkedHashMap) this.keyToRc).get(str)) == null) {
            ActivityResultRegistry$generateRandomNumber$1 activityResultRegistry$generateRandomNumber$1 = new Function0() { // from class: androidx.activity.result.ActivityResultRegistry$generateRandomNumber$1
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Random.Default.getClass();
                    return Integer.valueOf(Random.defaultRandom.getImpl().nextInt(2147418112) + 65536);
                }
            };
            Iterator it = new ConstrainedOnceSequence(new GeneratorSequence(activityResultRegistry$generateRandomNumber$1, new SequencesKt__SequencesKt$$ExternalSyntheticLambda1(activityResultRegistry$generateRandomNumber$1))).iterator();
            while (it.hasNext()) {
                Number number = (Number) it.next();
                if (!this.rcToKey.containsKey(Integer.valueOf(number.intValue()))) {
                    int iIntValue = number.intValue();
                    this.rcToKey.put(Integer.valueOf(iIntValue), str);
                    this.keyToRc.put(str, Integer.valueOf(iIntValue));
                }
            }
            throw new NoSuchElementException("Sequence contains no element matching the predicate.");
        }
        this.keyToCallback.put(str, new CallbackAndContract(activityResultCallback, activityResultContract));
        if (this.parsedPendingResults.containsKey(str)) {
            Object obj = ((LinkedHashMap) this.parsedPendingResults).get(str);
            this.parsedPendingResults.remove(str);
            activityResultCallback.onActivityResult(obj);
        }
        ActivityResult activityResult = (ActivityResult) this.pendingResults.getParcelable(str, ActivityResult.class);
        if (activityResult != null) {
            this.pendingResults.remove(str);
            activityResultCallback.onActivityResult(activityResultContract.parseResult(activityResult.resultCode, activityResult.data));
        }
        return new AnonymousClass3(str, activityResultContract);
    }
}
