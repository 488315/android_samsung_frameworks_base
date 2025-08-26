package android.view.autofill;

import android.util.ArrayMap;
import android.util.Log;
import android.util.Slog;
import android.view.View;
import android.view.autofill.AutofillManager;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes4.dex */
public final class AutofillStateFingerprint {
    private static final String TAG = "AutofillStateFingerprint";
    ArrayList<AutofillId> mPriorAutofillIds;
    private int mSessionId;
    private boolean mUseRelativePosition;
    ArrayList<Integer> mViewHashCodes;
    boolean mHideHighlight = false;
    Map<Integer, AutofillId> mHashToAutofillIdMap = new ArrayMap();
    Map<AutofillId, AutofillId> mOldIdsToCurrentAutofillIdMap = new ArrayMap();
    private ArrayList<AutofillId> mFailedIds = new ArrayList<>();
    private ArrayList<AutofillValue> mFailedAutofillValues = new ArrayList<>();

    public static AutofillStateFingerprint createInstance() {
        return new AutofillStateFingerprint();
    }

    private AutofillStateFingerprint() {
    }

    void setSessionId(int i) {
        this.mSessionId = i;
    }

    void setUseRelativePosition(boolean z) {
        this.mUseRelativePosition = z;
    }

    void storeStatePriorToAuthentication(AutofillManager.AutofillClient autofillClient, Set<AutofillId> set) {
        if (this.mUseRelativePosition) {
            List<View> listAutofillClientFindAutofillableViewsByTraversal = autofillClient.autofillClientFindAutofillableViewsByTraversal();
            if (Helper.sDebug) {
                Log.d(TAG, "Autofillable views count prior to auth:" + listAutofillClientFindAutofillableViewsByTraversal.size());
            }
            for (Map.Entry<Integer, View> entry : getFingerprintIds(listAutofillClientFindAutofillableViewsByTraversal).entrySet()) {
                View value = entry.getValue();
                if (value != null) {
                    this.mHashToAutofillIdMap.put(entry.getKey(), value.getAutofillId());
                } else if (Helper.sDebug) {
                    Log.d(TAG, "Encountered null view");
                }
            }
            return;
        }
        if (Helper.sDebug) {
            Log.d(TAG, "Size of autofillId's being stored: " + set.size() + " list:" + set);
        }
        AutofillId[] array = Helper.toArray(set);
        View[] viewArrAutofillClientFindViewsByAutofillIdTraversal = autofillClient.autofillClientFindViewsByAutofillIdTraversal(array);
        for (int i = 0; i < array.length; i++) {
            View view = viewArrAutofillClientFindViewsByAutofillIdTraversal[i];
            if (view != null) {
                this.mHashToAutofillIdMap.put(Integer.valueOf(getEphemeralFingerprintId(view, 0)), view.getAutofillId());
            } else if (Helper.sDebug) {
                Log.d(TAG, "Encountered null view");
            }
        }
    }

    void storeFailedIdsAndValues(ArrayList<AutofillId> arrayList, ArrayList<AutofillValue> arrayList2, boolean z) {
        Iterator<AutofillId> it = arrayList.iterator();
        while (it.hasNext()) {
            AutofillId next = it.next();
            if (next != null) {
                next.setSessionId(this.mSessionId);
            } else if (Helper.sDebug) {
                Log.d(TAG, "Got null failed ids");
            }
        }
        this.mFailedIds = arrayList;
        this.mFailedAutofillValues = arrayList2;
        this.mHideHighlight = z;
    }

    private void dumpCurrentState() {
        Log.d(TAG, "FailedId's: " + this.mFailedIds);
        Log.d(TAG, "Hashes from map" + this.mHashToAutofillIdMap);
    }

    boolean attemptRefill(List<View> list, final AutofillManager autofillManager) {
        if (Helper.sDebug) {
            dumpCurrentState();
        }
        ArrayMap<Integer, View> fingerprintIds = getFingerprintIds(list);
        HashMap map = new HashMap();
        for (Map.Entry<Integer, View> entry : fingerprintIds.entrySet()) {
            View value = entry.getValue();
            Integer key = entry.getKey();
            int iIntValue = key.intValue();
            AutofillId autofillId = value.getAutofillId();
            autofillId.setSessionId(this.mSessionId);
            if (this.mHashToAutofillIdMap.containsKey(key)) {
                AutofillId autofillId2 = this.mHashToAutofillIdMap.get(key);
                autofillId2.setSessionId(this.mSessionId);
                this.mOldIdsToCurrentAutofillIdMap.put(autofillId2, autofillId);
                Log.i(TAG, "Mapping current autofill id: " + value.getAutofillId() + " to existing autofill id " + autofillId2);
                map.put(autofillId2, value);
            } else {
                Log.i(TAG, "Couldn't map current autofill id: " + value.getAutofillId() + " with currentHash:" + iIntValue + " for view:" + value);
            }
        }
        final View[] viewArr = new View[this.mFailedIds.size()];
        int i = 0;
        for (int i2 = 0; i2 < this.mFailedIds.size(); i2++) {
            AutofillId autofillId3 = this.mFailedIds.get(i2);
            AutofillId autofillId4 = this.mOldIdsToCurrentAutofillIdMap.get(autofillId3);
            if (autofillId4 == null && Helper.sDebug) {
                Log.d(TAG, "currentAutofillId = null");
            }
            this.mFailedIds.set(i2, autofillId4);
            View view = (View) map.get(autofillId3);
            viewArr[i2] = view;
            if (view != null) {
                i++;
            }
        }
        if (Helper.sDebug) {
            dumpCurrentState();
        }
        Slog.i(TAG, "Attempting refill of views. Found " + i + " views to refill from previously " + this.mFailedIds.size() + " failed ids:" + this.mFailedIds);
        autofillManager.post(new Runnable() { // from class: android.view.autofill.AutofillStateFingerprint$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$attemptRefill$0(autofillManager, viewArr);
            }
        });
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$attemptRefill$0(AutofillManager autofillManager, View[] viewArr) {
        autofillManager.autofill(viewArr, this.mFailedIds, this.mFailedAutofillValues, this.mHideHighlight, true);
    }

    ArrayMap<Integer, View> getFingerprintIds(List<View> list) {
        ArrayMap<Integer, View> arrayMap = new ArrayMap<>();
        if (this.mUseRelativePosition) {
            Collections.sort(list, new Comparator() { // from class: android.view.autofill.AutofillStateFingerprint$$ExternalSyntheticLambda0
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return this.f$0.lambda$getFingerprintIds$1((View) obj, (View) obj2);
                }
            });
        }
        for (int i = 0; i < list.size(); i++) {
            View view = list.get(i);
            arrayMap.put(Integer.valueOf(getEphemeralFingerprintId(view, i)), view);
        }
        return arrayMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$getFingerprintIds$1(View view, View view2) {
        int[] locationOnScreen = view.getLocationOnScreen();
        int[] locationOnScreen2 = view2.getLocationOnScreen();
        int i = locationOnScreen[0] - locationOnScreen2[0];
        if (i != 0) {
            return i;
        }
        int i2 = locationOnScreen[1] - locationOnScreen2[1];
        if (i2 != 0) {
            return i2;
        }
        int iCompareTop = compareTop(view, view2);
        if (iCompareTop != 0) {
            return iCompareTop;
        }
        int iCompareBottom = compareBottom(view, view2);
        if (iCompareBottom != 0) {
            return iCompareBottom;
        }
        int iCompareLeft = compareLeft(view, view2);
        return iCompareLeft != 0 ? iCompareLeft : compareRight(view, view2);
    }

    public int getEphemeralFingerprintId(View view, int i) {
        int inputType;
        boolean zIsSingleLine;
        CharSequence hint;
        int imeOptions;
        if (view == null) {
            return -1;
        }
        if (!(view instanceof TextView)) {
            inputType = Integer.MIN_VALUE;
            zIsSingleLine = false;
            hint = "";
            imeOptions = Integer.MIN_VALUE;
        } else {
            TextView textView = (TextView) view;
            inputType = textView.getInputType();
            hint = textView.getHint();
            zIsSingleLine = textView.isSingleLine();
            imeOptions = textView.getImeOptions();
        }
        CharSequence charSequence = hint;
        CharSequence contentDescription = view.getContentDescription();
        CharSequence tooltipText = view.getTooltipText();
        int autofillType = view.getAutofillType();
        String[] autofillHints = view.getAutofillHints();
        int visibility = view.getVisibility();
        int paddingLeft = view.getPaddingLeft();
        int paddingRight = view.getPaddingRight();
        int paddingTop = view.getPaddingTop();
        int paddingBottom = view.getPaddingBottom();
        int height = view.getHeight();
        int width = view.getWidth();
        boolean z = zIsSingleLine;
        int iHash = Objects.hash(Integer.valueOf(visibility), Integer.valueOf(inputType), Integer.valueOf(imeOptions), Boolean.valueOf(z), charSequence, contentDescription, tooltipText, Integer.valueOf(autofillType), Integer.valueOf(Arrays.deepHashCode(autofillHints)), Integer.valueOf(paddingBottom), Integer.valueOf(paddingTop), Integer.valueOf(paddingRight), Integer.valueOf(paddingLeft));
        if (this.mUseRelativePosition) {
            iHash = Objects.hash(Integer.valueOf(iHash), Integer.valueOf(i));
        }
        if (Helper.sDebug) {
            Log.d(TAG, "Hash: " + iHash + " for AutofillId:" + view.getAutofillId() + " visibility:" + visibility + " inputType:" + inputType + " imeOptions:" + imeOptions + " isSingleLine:" + z + " hints:" + ((Object) charSequence) + " contentDesc:" + ((Object) contentDescription) + " tooltipText:" + ((Object) tooltipText) + " autofillType:" + autofillType + " autofillHints:" + Arrays.toString(autofillHints) + " height:" + height + " width:" + width + " paddingLeft:" + paddingLeft + " paddingRight:" + paddingRight + " paddingTop:" + paddingTop + " paddingBottom:" + paddingBottom + " mUseRelativePosition" + this.mUseRelativePosition + " position:" + i);
        }
        return iHash;
    }

    private int compareTop(View view, View view2) {
        return view.getTop() - view2.getTop();
    }

    private int compareBottom(View view, View view2) {
        return view.getBottom() - view2.getBottom();
    }

    private int compareLeft(View view, View view2) {
        return view.getLeft() - view2.getLeft();
    }

    private int compareRight(View view, View view2) {
        return view.getRight() - view2.getRight();
    }
}
