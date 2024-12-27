package com.android.server.autofill.ui;

import android.content.IntentSender;
import android.service.autofill.Dataset;
import android.service.autofill.InlinePresentation;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.view.inputmethod.InlineSuggestion;
import android.view.inputmethod.InlineSuggestionInfo;
import android.view.inputmethod.InlineSuggestionsRequest;
import android.widget.inline.InlinePresentationSpec;

import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.autofill.Helper;

import java.util.List;

public abstract class InlineSuggestionFactory {

    /* renamed from: com.android.server.autofill.ui.InlineSuggestionFactory$1, reason: invalid class name */
    public final class AnonymousClass1 implements InlineFillUi.InlineSuggestionUiCallback {
        @Override // com.android.server.autofill.ui.InlineFillUi.InlineSuggestionUiCallback
        public final void authenticate() {}

        @Override // com.android.server.autofill.ui.InlineFillUi.InlineSuggestionUiCallback
        public final void autofill(Dataset dataset, int i) {}

        @Override // com.android.server.autofill.ui.InlineFillUi.InlineSuggestionUiCallback
        public final void onError() {
            Slog.w("InlineSuggestionFactory", "An error happened on the tooltip");
        }

        @Override // com.android.server.autofill.ui.InlineFillUi.InlineSuggestionUiCallback
        public final void onInflate() {}

        @Override // com.android.server.autofill.ui.InlineFillUi.InlineSuggestionUiCallback
        public final void startIntentSender(IntentSender intentSender) {}
    }

    public static InlineSuggestion createInlineSuggestionTooltip(
            InlineSuggestionsRequest inlineSuggestionsRequest,
            InlineFillUi.InlineFillUiInfo inlineFillUiInfo,
            String str,
            InlinePresentation inlinePresentation) {
        if (inlinePresentation == null) {
            return null;
        }
        InlinePresentationSpec inlineTooltipPresentationSpec =
                inlineSuggestionsRequest.getInlineTooltipPresentationSpec();
        InlinePresentationSpec inlinePresentationSpec =
                inlineTooltipPresentationSpec == null
                        ? inlinePresentation.getInlinePresentationSpec()
                        : new InlinePresentationSpec.Builder(
                                        inlinePresentation.getInlinePresentationSpec().getMinSize(),
                                        inlinePresentation.getInlinePresentationSpec().getMaxSize())
                                .setStyle(inlineTooltipPresentationSpec.getStyle())
                                .build();
        return new InlineSuggestion(
                new InlineSuggestionInfo(
                        inlinePresentationSpec,
                        str,
                        null,
                        "android:autofill:suggestion",
                        false,
                        null),
                new InlineContentProviderImpl(
                        new RemoteInlineSuggestionViewConnector(
                                inlineFillUiInfo,
                                new InlinePresentation(
                                        inlinePresentation.getSlice(),
                                        inlinePresentationSpec,
                                        false),
                                new InlineSuggestionFactory$$ExternalSyntheticLambda2(),
                                new AnonymousClass1()),
                        null));
    }

    public static SparseArray createInlineSuggestions(
            InlineFillUi.InlineFillUiInfo inlineFillUiInfo,
            String str,
            List list,
            final InlineFillUi.InlineSuggestionUiCallback inlineSuggestionUiCallback,
            boolean z) {
        boolean z2;
        InlineSuggestion inlineSuggestion;
        String str2;
        InlineSuggestionsRequest inlineSuggestionsRequest;
        String str3 = str;
        String str4 = "InlineSuggestionFactory";
        if (Helper.sDebug) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    "createInlineSuggestions(source=", str3, ") called", "InlineSuggestionFactory");
        }
        InlineSuggestionsRequest inlineSuggestionsRequest2 = inlineFillUiInfo.mInlineRequest;
        SparseArray sparseArray = new SparseArray(list.size());
        boolean z3 = false;
        final int i = 0;
        while (i < list.size()) {
            final Dataset dataset = (Dataset) list.get(i);
            int indexOf = dataset.getFieldIds().indexOf(inlineFillUiInfo.mFocusedId);
            if (indexOf < 0) {
                Slog.w(str4, "AutofillId=" + inlineFillUiInfo.mFocusedId + " not found in dataset");
            } else {
                InlinePresentation fieldInlinePresentation =
                        dataset.getFieldInlinePresentation(indexOf);
                if (fieldInlinePresentation == null) {
                    Slog.w(str4, "InlinePresentation not found in dataset");
                } else {
                    String str5 =
                            dataset.getAuthentication() == null
                                    ? "android:autofill:suggestion"
                                    : "android:autofill:action";
                    if (z3) {
                        z2 = z3;
                        inlineSuggestion = null;
                    } else {
                        InlineSuggestion createInlineSuggestionTooltip =
                                createInlineSuggestionTooltip(
                                        inlineSuggestionsRequest2,
                                        inlineFillUiInfo,
                                        str3,
                                        dataset.getFieldInlineTooltipPresentation(indexOf));
                        if (createInlineSuggestionTooltip != null) {
                            z3 = true;
                        }
                        z2 = z3;
                        inlineSuggestion = createInlineSuggestionTooltip;
                    }
                    Runnable runnable =
                            new Runnable() { // from class:
                                             // com.android.server.autofill.ui.InlineSuggestionFactory$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    InlineFillUi.InlineSuggestionUiCallback.this.autofill(
                                            dataset, i);
                                }
                            };
                    InlinePresentation mergedInlinePresentation =
                            mergedInlinePresentation(
                                    inlineSuggestionsRequest2, i, fieldInlinePresentation, z);
                    str2 = str4;
                    inlineSuggestionsRequest = inlineSuggestionsRequest2;
                    sparseArray.append(
                            i,
                            Pair.create(
                                    dataset,
                                    new InlineSuggestion(
                                            new InlineSuggestionInfo(
                                                    mergedInlinePresentation
                                                            .getInlinePresentationSpec(),
                                                    str,
                                                    mergedInlinePresentation.getAutofillHints(),
                                                    str5,
                                                    mergedInlinePresentation.isPinned(),
                                                    inlineSuggestion),
                                            new InlineContentProviderImpl(
                                                    new RemoteInlineSuggestionViewConnector(
                                                            inlineFillUiInfo,
                                                            mergedInlinePresentation,
                                                            runnable,
                                                            inlineSuggestionUiCallback),
                                                    null))));
                    z3 = z2;
                    i++;
                    str3 = str;
                    str4 = str2;
                    inlineSuggestionsRequest2 = inlineSuggestionsRequest;
                }
            }
            str2 = str4;
            inlineSuggestionsRequest = inlineSuggestionsRequest2;
            i++;
            str3 = str;
            str4 = str2;
            inlineSuggestionsRequest2 = inlineSuggestionsRequest;
        }
        return sparseArray;
    }

    public static InlinePresentation mergedInlinePresentation(
            InlineSuggestionsRequest inlineSuggestionsRequest,
            int i,
            InlinePresentation inlinePresentation,
            boolean z) {
        List<InlinePresentationSpec> inlinePresentationSpecs =
                inlineSuggestionsRequest.getInlinePresentationSpecs();
        if (inlinePresentationSpecs.isEmpty()) {
            return inlinePresentation;
        }
        InlinePresentationSpec inlinePresentationSpec =
                inlinePresentationSpecs.get(Math.min(inlinePresentationSpecs.size() - 1, i));
        if (z) {
            inlinePresentationSpec = inlinePresentation.getInlinePresentationSpec();
        }
        return new InlinePresentation(
                inlinePresentation.getSlice(),
                new InlinePresentationSpec.Builder(
                                inlinePresentation.getInlinePresentationSpec().getMinSize(),
                                inlinePresentation.getInlinePresentationSpec().getMaxSize())
                        .setStyle(inlinePresentationSpec.getStyle())
                        .build(),
                inlinePresentation.isPinned());
    }
}
