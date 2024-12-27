package com.android.server.autofill.ui;

import android.content.IntentSender;
import android.service.autofill.Dataset;
import android.util.Pair;
import android.util.SparseArray;
import android.view.autofill.AutofillId;
import android.view.inputmethod.InlineSuggestion;
import android.view.inputmethod.InlineSuggestionInfo;
import android.view.inputmethod.InlineSuggestionsRequest;

import com.android.server.autofill.RemoteInlineSuggestionRenderService;

import java.util.ArrayList;

public final class InlineFillUi {
    public final AutofillId mAutofillId;
    public final ArrayList mDatasets;
    public boolean mFilterMatchingDisabled;
    public String mFilterText;
    public final ArrayList mInlineSuggestions;
    public final int mMaxInputLengthForAutofill;

    public final class InlineFillUiInfo {
        public final String mFilterText;
        public final AutofillId mFocusedId;
        public final InlineSuggestionsRequest mInlineRequest;
        public final RemoteInlineSuggestionRenderService mRemoteRenderService;
        public final int mSessionId;
        public final int mUserId;

        public InlineFillUiInfo(
                InlineSuggestionsRequest inlineSuggestionsRequest,
                AutofillId autofillId,
                String str,
                RemoteInlineSuggestionRenderService remoteInlineSuggestionRenderService,
                int i,
                int i2) {
            this.mUserId = i;
            this.mSessionId = i2;
            this.mInlineRequest = inlineSuggestionsRequest;
            this.mFocusedId = autofillId;
            this.mFilterText = str;
            this.mRemoteRenderService = remoteInlineSuggestionRenderService;
        }
    }

    public interface InlineSuggestionUiCallback {
        void authenticate();

        void autofill(Dataset dataset, int i);

        void onError();

        void onInflate();

        void startIntentSender(IntentSender intentSender);
    }

    public InlineFillUi(AutofillId autofillId) {
        this.mMaxInputLengthForAutofill = Integer.MAX_VALUE;
        this.mAutofillId = autofillId;
        this.mDatasets = new ArrayList(0);
        this.mInlineSuggestions = new ArrayList(0);
        this.mFilterText = null;
    }

    public InlineFillUi(InlineFillUiInfo inlineFillUiInfo, SparseArray sparseArray) {
        this.mMaxInputLengthForAutofill = Integer.MAX_VALUE;
        this.mAutofillId = inlineFillUiInfo.mFocusedId;
        int size = sparseArray.size();
        this.mDatasets = new ArrayList(size);
        this.mInlineSuggestions = new ArrayList(size);
        for (int i = 0; i < size; i++) {
            Pair pair = (Pair) sparseArray.valueAt(i);
            this.mDatasets.add((Dataset) pair.first);
            this.mInlineSuggestions.add((InlineSuggestion) pair.second);
        }
        this.mFilterText = inlineFillUiInfo.mFilterText;
    }

    public InlineFillUi(InlineFillUiInfo inlineFillUiInfo, SparseArray sparseArray, int i) {
        this.mMaxInputLengthForAutofill = Integer.MAX_VALUE;
        this.mAutofillId = inlineFillUiInfo.mFocusedId;
        int size = sparseArray.size();
        this.mDatasets = new ArrayList(size);
        this.mInlineSuggestions = new ArrayList(size);
        for (int i2 = 0; i2 < size; i2++) {
            Pair pair = (Pair) sparseArray.valueAt(i2);
            this.mDatasets.add((Dataset) pair.first);
            this.mInlineSuggestions.add((InlineSuggestion) pair.second);
        }
        this.mFilterText = inlineFillUiInfo.mFilterText;
        this.mMaxInputLengthForAutofill = i;
    }

    public InlineFillUi(
            InlineFillUiInfo inlineFillUiInfo, InlineSuggestion inlineSuggestion, int i) {
        this.mMaxInputLengthForAutofill = Integer.MAX_VALUE;
        this.mAutofillId = inlineFillUiInfo.mFocusedId;
        this.mDatasets = null;
        ArrayList arrayList = new ArrayList();
        this.mInlineSuggestions = arrayList;
        arrayList.add(inlineSuggestion);
        this.mFilterText = inlineFillUiInfo.mFilterText;
        this.mMaxInputLengthForAutofill = i;
    }

    public final InlineSuggestion copy(int i, InlineSuggestion inlineSuggestion) {
        InlineContentProviderImpl contentProvider = inlineSuggestion.getContentProvider();
        if (!(contentProvider instanceof InlineContentProviderImpl)) {
            return inlineSuggestion;
        }
        InlineSuggestionInfo info = inlineSuggestion.getInfo();
        InlineContentProviderImpl inlineContentProviderImpl = contentProvider;
        inlineContentProviderImpl.getClass();
        InlineSuggestion inlineSuggestion2 =
                new InlineSuggestion(
                        info,
                        new InlineContentProviderImpl(
                                inlineContentProviderImpl.mRemoteInlineSuggestionViewConnector,
                                inlineContentProviderImpl.mRemoteInlineSuggestionUi));
        this.mInlineSuggestions.set(i, inlineSuggestion2);
        return inlineSuggestion2;
    }
}
