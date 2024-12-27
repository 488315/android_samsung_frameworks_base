package com.android.server.autofill;

import android.graphics.Rect;
import android.service.autofill.FillResponse;
import android.util.DebugUtils;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillValue;

public final class ViewState {
    public final AutofillId id;
    public AutofillValue mAutofilledValue;
    public AutofillValue mCandidateSaveValue;
    public AutofillValue mCurrentValue;
    public String mDatasetId;
    public final boolean mIsPrimaryCredential;
    public final Listener mListener;
    public FillResponse mPrimaryFillResponse;
    public AutofillValue mSanitizedValue;
    public FillResponse mSecondaryFillResponse;
    public int mState;
    public Rect mVirtualBounds;

    public interface Listener {}

    public ViewState(AutofillId autofillId, Listener listener, int i, boolean z) {
        this.id = autofillId;
        this.mListener = listener;
        this.mState = i;
        this.mIsPrimaryCredential = z;
    }

    public final String getStateAsString() {
        return DebugUtils.flagsToString(ViewState.class, "STATE_", this.mState);
    }

    /* JADX WARN: Code restructure failed: missing block: B:151:0x0041, code lost:

       r2 = r17.mPrimaryFillResponse;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void maybeCallOnFillReady(int r18) {
        /*
            Method dump skipped, instructions count: 705
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.autofill.ViewState.maybeCallOnFillReady(int):void");
    }

    public final void resetState(int i) {
        this.mState = (~i) & this.mState;
    }

    public final void setState(int i) {
        int i2 = this.mState;
        if (i2 == 1) {
            this.mState = i;
        } else {
            this.mState = i2 | i;
        }
        if (i == 4) {
            this.mState |= 2048;
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ViewState: [id=");
        sb.append(this.id);
        if (this.mDatasetId != null) {
            sb.append(", datasetId:");
            sb.append(this.mDatasetId);
        }
        sb.append(", state:");
        sb.append(getStateAsString());
        if (this.mCurrentValue != null) {
            sb.append(", currentValue:");
            sb.append(this.mCurrentValue);
        }
        if (this.mCandidateSaveValue != null) {
            sb.append(", candidateSaveValue:");
            sb.append(this.mCandidateSaveValue);
        }
        if (this.mAutofilledValue != null) {
            sb.append(", autofilledValue:");
            sb.append(this.mAutofilledValue);
        }
        if (this.mSanitizedValue != null) {
            sb.append(", sanitizedValue:");
            sb.append(this.mSanitizedValue);
        }
        if (this.mVirtualBounds != null) {
            sb.append(", virtualBounds:");
            sb.append(this.mVirtualBounds);
        }
        sb.append("]");
        return sb.toString();
    }
}
