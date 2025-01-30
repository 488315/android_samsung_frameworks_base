package com.android.server.autofill;

import android.os.Bundle;
import android.os.RemoteCallback;
import android.util.Slog;
import android.view.autofill.AutofillId;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class LogFieldClassificationScoreOnResultListener
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

  public void onResult(Bundle bundle) {
    Session session = this.mSession;
    if (session == null) {
      Slog.wtf(
          "LogFieldClassificationScoreOnResultListener", "session is null when calling onResult()");
    } else {
      session.handleLogFieldClassificationScore(
          bundle,
          this.mSaveDialogNotShowReason,
          this.mCommitReason,
          this.mViewsSize,
          this.mAutofillIds,
          this.mUserValues,
          this.mCategoryIds,
          this.mDetectedFieldIds,
          this.mDetectedFieldClassifications);
      this.mSession = null;
    }
  }
}
