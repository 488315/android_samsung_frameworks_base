package androidx.window.embedding;

import android.app.Activity;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.window.extensions.embedding.ActivityStack;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ActivityStack {
    public final List activitiesInProcess;
    public final boolean isEmpty;
    public final ActivityStack.Token token;

    public ActivityStack(List<? extends Activity> list, boolean z, ActivityStack.Token token) {
        this.activitiesInProcess = list;
        this.isEmpty = z;
        this.token = token;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ActivityStack)) {
            return false;
        }
        ActivityStack activityStack = (ActivityStack) obj;
        return Intrinsics.areEqual(this.activitiesInProcess, activityStack.activitiesInProcess) && this.isEmpty == activityStack.isEmpty && Intrinsics.areEqual(this.token, activityStack.token);
    }

    public final int hashCode() {
        int m = TransitionData$$ExternalSyntheticOutline0.m(this.activitiesInProcess.hashCode() * 31, 31, this.isEmpty);
        ActivityStack.Token token = this.token;
        return m + (token != null ? token.hashCode() : 0);
    }

    public final String toString() {
        return "ActivityStack{activitiesInProcess=" + this.activitiesInProcess + ", isEmpty=" + this.isEmpty + ", token=" + this.token + '}';
    }

    public ActivityStack(List<? extends Activity> list, boolean z) {
        this(list, z, null);
    }
}
