package androidx.activity.result;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.lifecycle.Lifecycle;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ActivityResultRegistry {
    public final Map rcToKey = new LinkedHashMap();
    public final Map keyToRc = new LinkedHashMap();
    public final Map keyToLifecycleContainers = new LinkedHashMap();
    public final List launchedKeys = new ArrayList();
    public final transient Map keyToCallback = new LinkedHashMap();
    public final Map parsedPendingResults = new LinkedHashMap();
    public final Bundle pendingResults = new Bundle();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class CallbackAndContract {
        public final ActivityResultCallback callback;
        public final ActivityResultContract contract;

        public CallbackAndContract(ActivityResultCallback activityResultCallback, ActivityResultContract activityResultContract) {
            this.callback = activityResultCallback;
            this.contract = activityResultContract;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class LifecycleContainer {
        public final Lifecycle lifecycle;
        public final List observers = new ArrayList();

        public LifecycleContainer(Lifecycle lifecycle) {
            this.lifecycle = lifecycle;
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

    public final ActivityResultRegistry$register$3 register(String str, ActivityResultContract activityResultContract, ActivityResultCallback activityResultCallback) {
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
                    int intValue = number.intValue();
                    this.rcToKey.put(Integer.valueOf(intValue), str);
                    this.keyToRc.put(str, Integer.valueOf(intValue));
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
        return new ActivityResultRegistry$register$3(this, str, activityResultContract);
    }
}
