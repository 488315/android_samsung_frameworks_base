package android.service.autofill;

import android.os.Parcelable;
import android.util.Log;
import android.util.Pair;
import android.view.autofill.Helper;
import android.widget.RemoteViews;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public abstract class InternalTransformation implements Transformation, Parcelable {
    private static final String TAG = "InternalTransformation";

    abstract void apply(ValueFinder valueFinder, RemoteViews remoteViews, int i) throws Exception;

    public static boolean batchApply(ValueFinder valueFinder, RemoteViews remoteViews, ArrayList<Pair<Integer, InternalTransformation>> arrayList) {
        int size = arrayList.size();
        if (Helper.sDebug) {
            Log.d(TAG, "getPresentation(): applying " + size + " transformations");
        }
        for (int i = 0; i < size; i++) {
            Pair<Integer, InternalTransformation> pair = arrayList.get(i);
            int iIntValue = pair.first.intValue();
            InternalTransformation internalTransformation = pair.second;
            if (Helper.sDebug) {
                Log.d(TAG, "#" + i + ": " + internalTransformation);
            }
            try {
                internalTransformation.apply(valueFinder, remoteViews, iIntValue);
            } catch (Exception e) {
                Log.e(TAG, "Could not apply transformation " + internalTransformation + ": " + e.getClass());
                return false;
            }
        }
        return true;
    }
}
