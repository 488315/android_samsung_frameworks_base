package com.android.server.autofill;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.service.autofill.CompositeUserData;
import android.service.autofill.FieldClassificationUserData;
import android.service.autofill.FillResponse;
import android.service.autofill.UserData;
import android.util.ArrayMap;
import android.util.Slog;
import android.view.autofill.AutofillId;
import com.android.internal.util.function.TriConsumer;
import com.android.server.am.PendingIntentController$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class Session$$ExternalSyntheticLambda1 implements TriConsumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ Session$$ExternalSyntheticLambda1(int i) {
        this.$r8$classId = i;
    }

    private final void accept$com$android$server$autofill$Session$$ExternalSyntheticLambda1(Object obj, Object obj2, Object obj3) {
        FillResponse lastResponseLocked;
        UserData userData;
        Collection values;
        Session session = (Session) obj;
        int intValue = ((Integer) obj2).intValue();
        int intValue2 = ((Integer) obj3).intValue();
        synchronized (session.mLock) {
            lastResponseLocked = session.getLastResponseLocked("logContextCommited(%s)");
        }
        if (lastResponseLocked == null) {
            Slog.w("AutofillSession", "handleLogContextCommitted(): last response is null");
            return;
        }
        AutofillManagerServiceImpl autofillManagerServiceImpl = session.mService;
        synchronized (autofillManagerServiceImpl.mLock) {
            userData = autofillManagerServiceImpl.mUserData;
        }
        FieldClassificationUserData userData2 = lastResponseLocked.getUserData();
        if (userData2 == null && userData == null) {
            userData2 = null;
        } else if (userData2 != null && userData != null) {
            userData2 = new CompositeUserData(userData, userData2);
        } else if (userData2 == null) {
            AutofillManagerServiceImpl autofillManagerServiceImpl2 = session.mService;
            synchronized (autofillManagerServiceImpl2.mLock) {
                userData2 = autofillManagerServiceImpl2.mUserData;
            }
        }
        FieldClassificationStrategy fieldClassificationStrategy = session.mService.mFieldClassificationStrategy;
        if (userData2 == null || fieldClassificationStrategy == null) {
            synchronized (session.mLock) {
                session.logContextCommittedLocked(null, null, intValue, intValue2);
            }
            return;
        }
        String[] values2 = userData2.getValues();
        String[] categoryIds = userData2.getCategoryIds();
        String fieldClassificationAlgorithm = userData2.getFieldClassificationAlgorithm();
        Bundle defaultFieldClassificationArgs = userData2.getDefaultFieldClassificationArgs();
        ArrayMap fieldClassificationAlgorithms = userData2.getFieldClassificationAlgorithms();
        ArrayMap fieldClassificationArgs = userData2.getFieldClassificationArgs();
        if (values2 == null || categoryIds == null || values2.length != categoryIds.length) {
            PendingIntentController$$ExternalSyntheticOutline0.m(values2 == null ? -1 : values2.length, categoryIds != null ? categoryIds.length : -1, "setScores(): user data mismatch: values.length = ", ", ids.length = ", "AutofillSession");
            return;
        }
        int maxFieldClassificationIdsSize = UserData.getMaxFieldClassificationIdsSize();
        ArrayList arrayList = new ArrayList(maxFieldClassificationIdsSize);
        ArrayList arrayList2 = new ArrayList(maxFieldClassificationIdsSize);
        synchronized (session.mLock) {
            values = session.mViewStates.values();
        }
        int size = values.size();
        AutofillId[] autofillIdArr = new AutofillId[size];
        ArrayList arrayList3 = new ArrayList(size);
        Iterator it = values.iterator();
        int i = 0;
        while (it.hasNext()) {
            Iterator it2 = it;
            ViewState viewState = (ViewState) it.next();
            arrayList3.add(viewState.mCurrentValue);
            autofillIdArr[i] = viewState.id;
            it = it2;
            i++;
            fieldClassificationArgs = fieldClassificationArgs;
        }
        fieldClassificationStrategy.calculateScores(new RemoteCallback(new LogFieldClassificationScoreOnResultListener(session, intValue, intValue2, size, autofillIdArr, values2, categoryIds, arrayList, arrayList2)), arrayList3, values2, categoryIds, fieldClassificationAlgorithm, defaultFieldClassificationArgs, fieldClassificationAlgorithms, fieldClassificationArgs);
    }

    public final void accept(Object obj, Object obj2, Object obj3) {
        switch (this.$r8$classId) {
            case 0:
                accept$com$android$server$autofill$Session$$ExternalSyntheticLambda1(obj, obj2, obj3);
                break;
            default:
                Session session = (Session) obj;
                IntentSender intentSender = (IntentSender) obj2;
                Intent intent = (Intent) obj3;
                session.getClass();
                try {
                    synchronized (session.mLock) {
                        session.mClient.startIntentSender(intentSender, intent);
                    }
                    break;
                } catch (RemoteException e) {
                    Slog.e("AutofillSession", "Error launching auth intent", e);
                }
        }
    }
}
