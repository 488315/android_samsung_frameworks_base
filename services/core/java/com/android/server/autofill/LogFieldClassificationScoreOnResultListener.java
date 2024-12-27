package com.android.server.autofill;

import android.os.Bundle;
import android.os.RemoteCallback;
import android.service.autofill.AutofillFieldClassificationService;
import android.service.autofill.FieldClassification;
import android.util.ArrayMap;
import android.util.Slog;
import android.view.autofill.AutofillId;

import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class LogFieldClassificationScoreOnResultListener
        implements RemoteCallback.OnResultListener {
    public final AutofillId[] mAutofillIds;
    public final String[] mCategoryIds;
    public final int mCommitReason;
    public final ArrayList mDetectedFieldClassifications;
    public final ArrayList mDetectedFieldIds;
    public final int mSaveDialogNotShowReason;
    public Session mSession;
    public final String[] mUserValues;
    public final int mViewsSize;

    public LogFieldClassificationScoreOnResultListener(
            Session session,
            int i,
            int i2,
            int i3,
            AutofillId[] autofillIdArr,
            String[] strArr,
            String[] strArr2,
            ArrayList arrayList,
            ArrayList arrayList2) {
        this.mSession = session;
        this.mSaveDialogNotShowReason = i;
        this.mCommitReason = i2;
        this.mViewsSize = i3;
        this.mAutofillIds = autofillIdArr;
        this.mUserValues = strArr;
        this.mCategoryIds = strArr2;
        this.mDetectedFieldIds = arrayList;
        this.mDetectedFieldClassifications = arrayList2;
    }

    public final void onResult(Bundle bundle) {
        Session session;
        String[] strArr;
        LogFieldClassificationScoreOnResultListener logFieldClassificationScoreOnResultListener =
                this;
        Session session2 = logFieldClassificationScoreOnResultListener.mSession;
        if (session2 == null) {
            Slog.wtf(
                    "LogFieldClassificationScoreOnResultListener",
                    "session is null when calling onResult()");
            return;
        }
        int i = logFieldClassificationScoreOnResultListener.mSaveDialogNotShowReason;
        int i2 = logFieldClassificationScoreOnResultListener.mCommitReason;
        int i3 = logFieldClassificationScoreOnResultListener.mViewsSize;
        AutofillId[] autofillIdArr = logFieldClassificationScoreOnResultListener.mAutofillIds;
        String[] strArr2 = logFieldClassificationScoreOnResultListener.mUserValues;
        String[] strArr3 = logFieldClassificationScoreOnResultListener.mCategoryIds;
        ArrayList arrayList = logFieldClassificationScoreOnResultListener.mDetectedFieldIds;
        ArrayList arrayList2 =
                logFieldClassificationScoreOnResultListener.mDetectedFieldClassifications;
        ArrayMap arrayMap = null;
        if (bundle == null) {
            if (Helper.sDebug) {
                Slog.d("AutofillSession", "setFieldClassificationScore(): no results");
            }
            synchronized (session2.mLock) {
                session2.logContextCommittedLocked(null, null, i, i2);
            }
        } else {
            AutofillFieldClassificationService.Scores scores =
                    (AutofillFieldClassificationService.Scores)
                            bundle.getParcelable(
                                    "scores", AutofillFieldClassificationService.Scores.class);
            if (scores != null) {
                int i4 = 0;
                int i5 = 0;
                while (i4 < i3) {
                    try {
                        AutofillId autofillId = autofillIdArr[i4];
                        int i6 = i3;
                        ArrayMap arrayMap2 = arrayMap;
                        int i7 = 0;
                        while (i7 < strArr2.length) {
                            try {
                                String str = strArr3[i7];
                                AutofillId[] autofillIdArr2 = autofillIdArr;
                                float f = scores.scores[i4][i7];
                                String[] strArr4 = strArr3;
                                if (f > FullScreenMagnificationGestureHandler.MAX_SCALE) {
                                    if (arrayMap2 == null) {
                                        arrayMap2 = new ArrayMap(strArr2.length);
                                    }
                                    Float f2 = (Float) arrayMap2.get(str);
                                    if (f2 == null || f2.floatValue() <= f) {
                                        if (Helper.sVerbose) {
                                            StringBuilder sb = new StringBuilder();
                                            strArr = strArr2;
                                            sb.append("adding score ");
                                            sb.append(f);
                                            sb.append(" at index ");
                                            sb.append(i7);
                                            sb.append(" and id ");
                                            sb.append(autofillId);
                                            Slog.v("AutofillSession", sb.toString());
                                        } else {
                                            strArr = strArr2;
                                        }
                                        arrayMap2.put(str, Float.valueOf(f));
                                    } else {
                                        if (Helper.sVerbose) {
                                            Slog.v(
                                                    "AutofillSession",
                                                    "skipping score "
                                                            + f
                                                            + " because it's less than "
                                                            + f2);
                                        }
                                        strArr = strArr2;
                                    }
                                } else {
                                    strArr = strArr2;
                                    if (Helper.sVerbose) {
                                        Slog.v(
                                                "AutofillSession",
                                                "skipping score 0 at index "
                                                        + i7
                                                        + " and id "
                                                        + autofillId);
                                    }
                                }
                                i7++;
                                autofillIdArr = autofillIdArr2;
                                strArr2 = strArr;
                                strArr3 = strArr4;
                            } catch (ArrayIndexOutOfBoundsException e) {
                                e = e;
                                i5 = i7;
                            }
                        }
                        AutofillId[] autofillIdArr3 = autofillIdArr;
                        String[] strArr5 = strArr2;
                        String[] strArr6 = strArr3;
                        if (arrayMap2 == null) {
                            if (Helper.sVerbose) {
                                Slog.v("AutofillSession", "no score for autofillId=" + autofillId);
                            }
                            i5 = i7;
                        } else {
                            ArrayList arrayList3 = new ArrayList(arrayMap2.size());
                            int i8 = 0;
                            while (i8 < arrayMap2.size()) {
                                try {
                                    arrayList3.add(
                                            new FieldClassification.Match(
                                                    (String) arrayMap2.keyAt(i8),
                                                    ((Float) arrayMap2.valueAt(i8)).floatValue()));
                                    i8++;
                                } catch (ArrayIndexOutOfBoundsException e2) {
                                    e = e2;
                                    i5 = i8;
                                    session2.wtf(
                                            e,
                                            "Error accessing FC score at [%d, %d] (%s): %s",
                                            Integer.valueOf(i4),
                                            Integer.valueOf(i5),
                                            scores,
                                            e);
                                    session = null;
                                    logFieldClassificationScoreOnResultListener = this;
                                    logFieldClassificationScoreOnResultListener.mSession = session;
                                }
                            }
                            arrayList.add(autofillId);
                            arrayList2.add(new FieldClassification(arrayList3));
                            i5 = i8;
                        }
                        i4++;
                        arrayMap = null;
                        i3 = i6;
                        autofillIdArr = autofillIdArr3;
                        strArr2 = strArr5;
                        strArr3 = strArr6;
                    } catch (ArrayIndexOutOfBoundsException e3) {
                        e = e3;
                    }
                }
                synchronized (session2.mLock) {
                    session2.logContextCommittedLocked(arrayList, arrayList2, i, i2);
                }
                session = null;
                logFieldClassificationScoreOnResultListener = this;
                logFieldClassificationScoreOnResultListener.mSession = session;
            }
            Slog.w("AutofillSession", "No field classification score on " + bundle);
        }
        session = null;
        logFieldClassificationScoreOnResultListener.mSession = session;
    }
}
