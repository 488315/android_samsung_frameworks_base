package com.android.internal.inputmethod;

import android.graphics.RectF;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.CorrectionInfo;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputContentInfo;
import android.view.inputmethod.ParcelableHandwritingGesture;
import android.view.inputmethod.TextAttribute;
import com.android.internal.infra.AndroidFuture;

/* loaded from: classes5.dex */
public interface IRemoteInputConnection extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.inputmethod.IRemoteInputConnection";

    public static class Default implements IRemoteInputConnection {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void beginBatchEdit(InputConnectionCommandHeader inputConnectionCommandHeader) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void cancelCancellationSignal(IBinder iBinder) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void clearMetaKeyStates(InputConnectionCommandHeader inputConnectionCommandHeader, int i) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void commitCompletion(InputConnectionCommandHeader inputConnectionCommandHeader, CompletionInfo completionInfo) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void commitContent(InputConnectionCommandHeader inputConnectionCommandHeader, InputContentInfo inputContentInfo, int i, Bundle bundle, AndroidFuture androidFuture) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void commitCorrection(InputConnectionCommandHeader inputConnectionCommandHeader, CorrectionInfo correctionInfo) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void commitText(InputConnectionCommandHeader inputConnectionCommandHeader, CharSequence charSequence, int i) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void commitTextWithTextAttribute(InputConnectionCommandHeader inputConnectionCommandHeader, CharSequence charSequence, int i, TextAttribute textAttribute) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void deleteSurroundingText(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void deleteSurroundingTextInCodePoints(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void endBatchEdit(InputConnectionCommandHeader inputConnectionCommandHeader) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void finishComposingText(InputConnectionCommandHeader inputConnectionCommandHeader) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void forgetCancellationSignal(IBinder iBinder) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void getCursorCapsMode(InputConnectionCommandHeader inputConnectionCommandHeader, int i, AndroidFuture androidFuture) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void getExtractedText(InputConnectionCommandHeader inputConnectionCommandHeader, ExtractedTextRequest extractedTextRequest, int i, AndroidFuture androidFuture) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void getSelectedText(InputConnectionCommandHeader inputConnectionCommandHeader, int i, AndroidFuture androidFuture) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void getSurroundingText(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, int i3, AndroidFuture androidFuture) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void getTextAfterCursor(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, AndroidFuture androidFuture) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void getTextBeforeCursor(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, AndroidFuture androidFuture) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void performContextMenuAction(InputConnectionCommandHeader inputConnectionCommandHeader, int i) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void performEditorAction(InputConnectionCommandHeader inputConnectionCommandHeader, int i) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void performHandwritingGesture(InputConnectionCommandHeader inputConnectionCommandHeader, ParcelableHandwritingGesture parcelableHandwritingGesture, ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void performPrivateCommand(InputConnectionCommandHeader inputConnectionCommandHeader, String str, Bundle bundle) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void performSpellCheck(InputConnectionCommandHeader inputConnectionCommandHeader) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void previewHandwritingGesture(InputConnectionCommandHeader inputConnectionCommandHeader, ParcelableHandwritingGesture parcelableHandwritingGesture, IBinder iBinder) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void replaceText(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, CharSequence charSequence, int i3, TextAttribute textAttribute) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void requestCursorUpdates(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, AndroidFuture androidFuture) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void requestCursorUpdatesWithFilter(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, int i3, AndroidFuture androidFuture) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void requestTextBoundsInfo(InputConnectionCommandHeader inputConnectionCommandHeader, RectF rectF, ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void sendKeyEvent(InputConnectionCommandHeader inputConnectionCommandHeader, KeyEvent keyEvent) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void setComposingRegion(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void setComposingRegionWithTextAttribute(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, TextAttribute textAttribute) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void setComposingText(InputConnectionCommandHeader inputConnectionCommandHeader, CharSequence charSequence, int i) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void setComposingTextWithTextAttribute(InputConnectionCommandHeader inputConnectionCommandHeader, CharSequence charSequence, int i, TextAttribute textAttribute) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void setImeConsumesInput(InputConnectionCommandHeader inputConnectionCommandHeader, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IRemoteInputConnection
        public void setSelection(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2) throws RemoteException {
        }
    }

    void beginBatchEdit(InputConnectionCommandHeader inputConnectionCommandHeader) throws RemoteException;

    void cancelCancellationSignal(IBinder iBinder) throws RemoteException;

    void clearMetaKeyStates(InputConnectionCommandHeader inputConnectionCommandHeader, int i) throws RemoteException;

    void commitCompletion(InputConnectionCommandHeader inputConnectionCommandHeader, CompletionInfo completionInfo) throws RemoteException;

    void commitContent(InputConnectionCommandHeader inputConnectionCommandHeader, InputContentInfo inputContentInfo, int i, Bundle bundle, AndroidFuture androidFuture) throws RemoteException;

    void commitCorrection(InputConnectionCommandHeader inputConnectionCommandHeader, CorrectionInfo correctionInfo) throws RemoteException;

    void commitText(InputConnectionCommandHeader inputConnectionCommandHeader, CharSequence charSequence, int i) throws RemoteException;

    void commitTextWithTextAttribute(InputConnectionCommandHeader inputConnectionCommandHeader, CharSequence charSequence, int i, TextAttribute textAttribute) throws RemoteException;

    void deleteSurroundingText(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2) throws RemoteException;

    void deleteSurroundingTextInCodePoints(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2) throws RemoteException;

    void endBatchEdit(InputConnectionCommandHeader inputConnectionCommandHeader) throws RemoteException;

    void finishComposingText(InputConnectionCommandHeader inputConnectionCommandHeader) throws RemoteException;

    void forgetCancellationSignal(IBinder iBinder) throws RemoteException;

    void getCursorCapsMode(InputConnectionCommandHeader inputConnectionCommandHeader, int i, AndroidFuture androidFuture) throws RemoteException;

    void getExtractedText(InputConnectionCommandHeader inputConnectionCommandHeader, ExtractedTextRequest extractedTextRequest, int i, AndroidFuture androidFuture) throws RemoteException;

    void getSelectedText(InputConnectionCommandHeader inputConnectionCommandHeader, int i, AndroidFuture androidFuture) throws RemoteException;

    void getSurroundingText(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, int i3, AndroidFuture androidFuture) throws RemoteException;

    void getTextAfterCursor(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, AndroidFuture androidFuture) throws RemoteException;

    void getTextBeforeCursor(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, AndroidFuture androidFuture) throws RemoteException;

    void performContextMenuAction(InputConnectionCommandHeader inputConnectionCommandHeader, int i) throws RemoteException;

    void performEditorAction(InputConnectionCommandHeader inputConnectionCommandHeader, int i) throws RemoteException;

    void performHandwritingGesture(InputConnectionCommandHeader inputConnectionCommandHeader, ParcelableHandwritingGesture parcelableHandwritingGesture, ResultReceiver resultReceiver) throws RemoteException;

    void performPrivateCommand(InputConnectionCommandHeader inputConnectionCommandHeader, String str, Bundle bundle) throws RemoteException;

    void performSpellCheck(InputConnectionCommandHeader inputConnectionCommandHeader) throws RemoteException;

    void previewHandwritingGesture(InputConnectionCommandHeader inputConnectionCommandHeader, ParcelableHandwritingGesture parcelableHandwritingGesture, IBinder iBinder) throws RemoteException;

    void replaceText(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, CharSequence charSequence, int i3, TextAttribute textAttribute) throws RemoteException;

    void requestCursorUpdates(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, AndroidFuture androidFuture) throws RemoteException;

    void requestCursorUpdatesWithFilter(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, int i3, AndroidFuture androidFuture) throws RemoteException;

    void requestTextBoundsInfo(InputConnectionCommandHeader inputConnectionCommandHeader, RectF rectF, ResultReceiver resultReceiver) throws RemoteException;

    void sendKeyEvent(InputConnectionCommandHeader inputConnectionCommandHeader, KeyEvent keyEvent) throws RemoteException;

    void setComposingRegion(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2) throws RemoteException;

    void setComposingRegionWithTextAttribute(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, TextAttribute textAttribute) throws RemoteException;

    void setComposingText(InputConnectionCommandHeader inputConnectionCommandHeader, CharSequence charSequence, int i) throws RemoteException;

    void setComposingTextWithTextAttribute(InputConnectionCommandHeader inputConnectionCommandHeader, CharSequence charSequence, int i, TextAttribute textAttribute) throws RemoteException;

    void setImeConsumesInput(InputConnectionCommandHeader inputConnectionCommandHeader, boolean z) throws RemoteException;

    void setSelection(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements IRemoteInputConnection {
        static final int TRANSACTION_beginBatchEdit = 17;
        static final int TRANSACTION_cancelCancellationSignal = 35;
        static final int TRANSACTION_clearMetaKeyStates = 20;
        static final int TRANSACTION_commitCompletion = 12;
        static final int TRANSACTION_commitContent = 31;
        static final int TRANSACTION_commitCorrection = 13;
        static final int TRANSACTION_commitText = 10;
        static final int TRANSACTION_commitTextWithTextAttribute = 11;
        static final int TRANSACTION_deleteSurroundingText = 5;
        static final int TRANSACTION_deleteSurroundingTextInCodePoints = 6;
        static final int TRANSACTION_endBatchEdit = 18;
        static final int TRANSACTION_finishComposingText = 9;
        static final int TRANSACTION_forgetCancellationSignal = 36;
        static final int TRANSACTION_getCursorCapsMode = 3;
        static final int TRANSACTION_getExtractedText = 4;
        static final int TRANSACTION_getSelectedText = 27;
        static final int TRANSACTION_getSurroundingText = 32;
        static final int TRANSACTION_getTextAfterCursor = 2;
        static final int TRANSACTION_getTextBeforeCursor = 1;
        static final int TRANSACTION_performContextMenuAction = 16;
        static final int TRANSACTION_performEditorAction = 15;
        static final int TRANSACTION_performHandwritingGesture = 23;
        static final int TRANSACTION_performPrivateCommand = 22;
        static final int TRANSACTION_performSpellCheck = 21;
        static final int TRANSACTION_previewHandwritingGesture = 24;
        static final int TRANSACTION_replaceText = 34;
        static final int TRANSACTION_requestCursorUpdates = 28;
        static final int TRANSACTION_requestCursorUpdatesWithFilter = 29;
        static final int TRANSACTION_requestTextBoundsInfo = 30;
        static final int TRANSACTION_sendKeyEvent = 19;
        static final int TRANSACTION_setComposingRegion = 25;
        static final int TRANSACTION_setComposingRegionWithTextAttribute = 26;
        static final int TRANSACTION_setComposingText = 7;
        static final int TRANSACTION_setComposingTextWithTextAttribute = 8;
        static final int TRANSACTION_setImeConsumesInput = 33;
        static final int TRANSACTION_setSelection = 14;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 35;
        }

        public Stub() {
            attachInterface(this, IRemoteInputConnection.DESCRIPTOR);
        }

        public static IRemoteInputConnection asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IRemoteInputConnection.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IRemoteInputConnection)) {
                return (IRemoteInputConnection) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getTextBeforeCursor";
                case 2:
                    return "getTextAfterCursor";
                case 3:
                    return "getCursorCapsMode";
                case 4:
                    return "getExtractedText";
                case 5:
                    return "deleteSurroundingText";
                case 6:
                    return "deleteSurroundingTextInCodePoints";
                case 7:
                    return "setComposingText";
                case 8:
                    return "setComposingTextWithTextAttribute";
                case 9:
                    return "finishComposingText";
                case 10:
                    return "commitText";
                case 11:
                    return "commitTextWithTextAttribute";
                case 12:
                    return "commitCompletion";
                case 13:
                    return "commitCorrection";
                case 14:
                    return "setSelection";
                case 15:
                    return "performEditorAction";
                case 16:
                    return "performContextMenuAction";
                case 17:
                    return "beginBatchEdit";
                case 18:
                    return "endBatchEdit";
                case 19:
                    return "sendKeyEvent";
                case 20:
                    return "clearMetaKeyStates";
                case 21:
                    return "performSpellCheck";
                case 22:
                    return "performPrivateCommand";
                case 23:
                    return "performHandwritingGesture";
                case 24:
                    return "previewHandwritingGesture";
                case 25:
                    return "setComposingRegion";
                case 26:
                    return "setComposingRegionWithTextAttribute";
                case 27:
                    return "getSelectedText";
                case 28:
                    return "requestCursorUpdates";
                case 29:
                    return "requestCursorUpdatesWithFilter";
                case 30:
                    return "requestTextBoundsInfo";
                case 31:
                    return "commitContent";
                case 32:
                    return "getSurroundingText";
                case 33:
                    return "setImeConsumesInput";
                case 34:
                    return "replaceText";
                case 35:
                    return "cancelCancellationSignal";
                case 36:
                    return "forgetCancellationSignal";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IRemoteInputConnection.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IRemoteInputConnection.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    InputConnectionCommandHeader inputConnectionCommandHeader = (InputConnectionCommandHeader) parcel.readTypedObject(InputConnectionCommandHeader.CREATOR);
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    AndroidFuture androidFuture = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    getTextBeforeCursor(inputConnectionCommandHeader, readInt, readInt2, androidFuture);
                    return true;
                case 2:
                    InputConnectionCommandHeader inputConnectionCommandHeader2 = (InputConnectionCommandHeader) parcel.readTypedObject(InputConnectionCommandHeader.CREATOR);
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    AndroidFuture androidFuture2 = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    getTextAfterCursor(inputConnectionCommandHeader2, readInt3, readInt4, androidFuture2);
                    return true;
                case 3:
                    InputConnectionCommandHeader inputConnectionCommandHeader3 = (InputConnectionCommandHeader) parcel.readTypedObject(InputConnectionCommandHeader.CREATOR);
                    int readInt5 = parcel.readInt();
                    AndroidFuture androidFuture3 = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    getCursorCapsMode(inputConnectionCommandHeader3, readInt5, androidFuture3);
                    return true;
                case 4:
                    InputConnectionCommandHeader inputConnectionCommandHeader4 = (InputConnectionCommandHeader) parcel.readTypedObject(InputConnectionCommandHeader.CREATOR);
                    ExtractedTextRequest extractedTextRequest = (ExtractedTextRequest) parcel.readTypedObject(ExtractedTextRequest.CREATOR);
                    int readInt6 = parcel.readInt();
                    AndroidFuture androidFuture4 = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    getExtractedText(inputConnectionCommandHeader4, extractedTextRequest, readInt6, androidFuture4);
                    return true;
                case 5:
                    InputConnectionCommandHeader inputConnectionCommandHeader5 = (InputConnectionCommandHeader) parcel.readTypedObject(InputConnectionCommandHeader.CREATOR);
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    deleteSurroundingText(inputConnectionCommandHeader5, readInt7, readInt8);
                    return true;
                case 6:
                    InputConnectionCommandHeader inputConnectionCommandHeader6 = (InputConnectionCommandHeader) parcel.readTypedObject(InputConnectionCommandHeader.CREATOR);
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    deleteSurroundingTextInCodePoints(inputConnectionCommandHeader6, readInt9, readInt10);
                    return true;
                case 7:
                    InputConnectionCommandHeader inputConnectionCommandHeader7 = (InputConnectionCommandHeader) parcel.readTypedObject(InputConnectionCommandHeader.CREATOR);
                    CharSequence charSequence = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setComposingText(inputConnectionCommandHeader7, charSequence, readInt11);
                    return true;
                case 8:
                    InputConnectionCommandHeader inputConnectionCommandHeader8 = (InputConnectionCommandHeader) parcel.readTypedObject(InputConnectionCommandHeader.CREATOR);
                    CharSequence charSequence2 = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    int readInt12 = parcel.readInt();
                    TextAttribute textAttribute = (TextAttribute) parcel.readTypedObject(TextAttribute.CREATOR);
                    parcel.enforceNoDataAvail();
                    setComposingTextWithTextAttribute(inputConnectionCommandHeader8, charSequence2, readInt12, textAttribute);
                    return true;
                case 9:
                    InputConnectionCommandHeader inputConnectionCommandHeader9 = (InputConnectionCommandHeader) parcel.readTypedObject(InputConnectionCommandHeader.CREATOR);
                    parcel.enforceNoDataAvail();
                    finishComposingText(inputConnectionCommandHeader9);
                    return true;
                case 10:
                    InputConnectionCommandHeader inputConnectionCommandHeader10 = (InputConnectionCommandHeader) parcel.readTypedObject(InputConnectionCommandHeader.CREATOR);
                    CharSequence charSequence3 = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    commitText(inputConnectionCommandHeader10, charSequence3, readInt13);
                    return true;
                case 11:
                    InputConnectionCommandHeader inputConnectionCommandHeader11 = (InputConnectionCommandHeader) parcel.readTypedObject(InputConnectionCommandHeader.CREATOR);
                    CharSequence charSequence4 = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    int readInt14 = parcel.readInt();
                    TextAttribute textAttribute2 = (TextAttribute) parcel.readTypedObject(TextAttribute.CREATOR);
                    parcel.enforceNoDataAvail();
                    commitTextWithTextAttribute(inputConnectionCommandHeader11, charSequence4, readInt14, textAttribute2);
                    return true;
                case 12:
                    InputConnectionCommandHeader inputConnectionCommandHeader12 = (InputConnectionCommandHeader) parcel.readTypedObject(InputConnectionCommandHeader.CREATOR);
                    CompletionInfo completionInfo = (CompletionInfo) parcel.readTypedObject(CompletionInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    commitCompletion(inputConnectionCommandHeader12, completionInfo);
                    return true;
                case 13:
                    InputConnectionCommandHeader inputConnectionCommandHeader13 = (InputConnectionCommandHeader) parcel.readTypedObject(InputConnectionCommandHeader.CREATOR);
                    CorrectionInfo correctionInfo = (CorrectionInfo) parcel.readTypedObject(CorrectionInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    commitCorrection(inputConnectionCommandHeader13, correctionInfo);
                    return true;
                case 14:
                    InputConnectionCommandHeader inputConnectionCommandHeader14 = (InputConnectionCommandHeader) parcel.readTypedObject(InputConnectionCommandHeader.CREATOR);
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSelection(inputConnectionCommandHeader14, readInt15, readInt16);
                    return true;
                case 15:
                    InputConnectionCommandHeader inputConnectionCommandHeader15 = (InputConnectionCommandHeader) parcel.readTypedObject(InputConnectionCommandHeader.CREATOR);
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    performEditorAction(inputConnectionCommandHeader15, readInt17);
                    return true;
                case 16:
                    InputConnectionCommandHeader inputConnectionCommandHeader16 = (InputConnectionCommandHeader) parcel.readTypedObject(InputConnectionCommandHeader.CREATOR);
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    performContextMenuAction(inputConnectionCommandHeader16, readInt18);
                    return true;
                case 17:
                    InputConnectionCommandHeader inputConnectionCommandHeader17 = (InputConnectionCommandHeader) parcel.readTypedObject(InputConnectionCommandHeader.CREATOR);
                    parcel.enforceNoDataAvail();
                    beginBatchEdit(inputConnectionCommandHeader17);
                    return true;
                case 18:
                    InputConnectionCommandHeader inputConnectionCommandHeader18 = (InputConnectionCommandHeader) parcel.readTypedObject(InputConnectionCommandHeader.CREATOR);
                    parcel.enforceNoDataAvail();
                    endBatchEdit(inputConnectionCommandHeader18);
                    return true;
                case 19:
                    InputConnectionCommandHeader inputConnectionCommandHeader19 = (InputConnectionCommandHeader) parcel.readTypedObject(InputConnectionCommandHeader.CREATOR);
                    KeyEvent keyEvent = (KeyEvent) parcel.readTypedObject(KeyEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendKeyEvent(inputConnectionCommandHeader19, keyEvent);
                    return true;
                case 20:
                    InputConnectionCommandHeader inputConnectionCommandHeader20 = (InputConnectionCommandHeader) parcel.readTypedObject(InputConnectionCommandHeader.CREATOR);
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearMetaKeyStates(inputConnectionCommandHeader20, readInt19);
                    return true;
                case 21:
                    InputConnectionCommandHeader inputConnectionCommandHeader21 = (InputConnectionCommandHeader) parcel.readTypedObject(InputConnectionCommandHeader.CREATOR);
                    parcel.enforceNoDataAvail();
                    performSpellCheck(inputConnectionCommandHeader21);
                    return true;
                case 22:
                    InputConnectionCommandHeader inputConnectionCommandHeader22 = (InputConnectionCommandHeader) parcel.readTypedObject(InputConnectionCommandHeader.CREATOR);
                    String readString = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    performPrivateCommand(inputConnectionCommandHeader22, readString, bundle);
                    return true;
                case 23:
                    InputConnectionCommandHeader inputConnectionCommandHeader23 = (InputConnectionCommandHeader) parcel.readTypedObject(InputConnectionCommandHeader.CREATOR);
                    ParcelableHandwritingGesture parcelableHandwritingGesture = (ParcelableHandwritingGesture) parcel.readTypedObject(ParcelableHandwritingGesture.CREATOR);
                    ResultReceiver resultReceiver = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    performHandwritingGesture(inputConnectionCommandHeader23, parcelableHandwritingGesture, resultReceiver);
                    return true;
                case 24:
                    InputConnectionCommandHeader inputConnectionCommandHeader24 = (InputConnectionCommandHeader) parcel.readTypedObject(InputConnectionCommandHeader.CREATOR);
                    ParcelableHandwritingGesture parcelableHandwritingGesture2 = (ParcelableHandwritingGesture) parcel.readTypedObject(ParcelableHandwritingGesture.CREATOR);
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    previewHandwritingGesture(inputConnectionCommandHeader24, parcelableHandwritingGesture2, readStrongBinder);
                    return true;
                case 25:
                    InputConnectionCommandHeader inputConnectionCommandHeader25 = (InputConnectionCommandHeader) parcel.readTypedObject(InputConnectionCommandHeader.CREATOR);
                    int readInt20 = parcel.readInt();
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setComposingRegion(inputConnectionCommandHeader25, readInt20, readInt21);
                    return true;
                case 26:
                    InputConnectionCommandHeader inputConnectionCommandHeader26 = (InputConnectionCommandHeader) parcel.readTypedObject(InputConnectionCommandHeader.CREATOR);
                    int readInt22 = parcel.readInt();
                    int readInt23 = parcel.readInt();
                    TextAttribute textAttribute3 = (TextAttribute) parcel.readTypedObject(TextAttribute.CREATOR);
                    parcel.enforceNoDataAvail();
                    setComposingRegionWithTextAttribute(inputConnectionCommandHeader26, readInt22, readInt23, textAttribute3);
                    return true;
                case 27:
                    InputConnectionCommandHeader inputConnectionCommandHeader27 = (InputConnectionCommandHeader) parcel.readTypedObject(InputConnectionCommandHeader.CREATOR);
                    int readInt24 = parcel.readInt();
                    AndroidFuture androidFuture5 = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    getSelectedText(inputConnectionCommandHeader27, readInt24, androidFuture5);
                    return true;
                case 28:
                    InputConnectionCommandHeader inputConnectionCommandHeader28 = (InputConnectionCommandHeader) parcel.readTypedObject(InputConnectionCommandHeader.CREATOR);
                    int readInt25 = parcel.readInt();
                    int readInt26 = parcel.readInt();
                    AndroidFuture androidFuture6 = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestCursorUpdates(inputConnectionCommandHeader28, readInt25, readInt26, androidFuture6);
                    return true;
                case 29:
                    InputConnectionCommandHeader inputConnectionCommandHeader29 = (InputConnectionCommandHeader) parcel.readTypedObject(InputConnectionCommandHeader.CREATOR);
                    int readInt27 = parcel.readInt();
                    int readInt28 = parcel.readInt();
                    int readInt29 = parcel.readInt();
                    AndroidFuture androidFuture7 = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestCursorUpdatesWithFilter(inputConnectionCommandHeader29, readInt27, readInt28, readInt29, androidFuture7);
                    return true;
                case 30:
                    InputConnectionCommandHeader inputConnectionCommandHeader30 = (InputConnectionCommandHeader) parcel.readTypedObject(InputConnectionCommandHeader.CREATOR);
                    RectF rectF = (RectF) parcel.readTypedObject(RectF.CREATOR);
                    ResultReceiver resultReceiver2 = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestTextBoundsInfo(inputConnectionCommandHeader30, rectF, resultReceiver2);
                    return true;
                case 31:
                    InputConnectionCommandHeader inputConnectionCommandHeader31 = (InputConnectionCommandHeader) parcel.readTypedObject(InputConnectionCommandHeader.CREATOR);
                    InputContentInfo inputContentInfo = (InputContentInfo) parcel.readTypedObject(InputContentInfo.CREATOR);
                    int readInt30 = parcel.readInt();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    AndroidFuture androidFuture8 = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    commitContent(inputConnectionCommandHeader31, inputContentInfo, readInt30, bundle2, androidFuture8);
                    return true;
                case 32:
                    InputConnectionCommandHeader inputConnectionCommandHeader32 = (InputConnectionCommandHeader) parcel.readTypedObject(InputConnectionCommandHeader.CREATOR);
                    int readInt31 = parcel.readInt();
                    int readInt32 = parcel.readInt();
                    int readInt33 = parcel.readInt();
                    AndroidFuture androidFuture9 = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    getSurroundingText(inputConnectionCommandHeader32, readInt31, readInt32, readInt33, androidFuture9);
                    return true;
                case 33:
                    InputConnectionCommandHeader inputConnectionCommandHeader33 = (InputConnectionCommandHeader) parcel.readTypedObject(InputConnectionCommandHeader.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setImeConsumesInput(inputConnectionCommandHeader33, readBoolean);
                    return true;
                case 34:
                    InputConnectionCommandHeader inputConnectionCommandHeader34 = (InputConnectionCommandHeader) parcel.readTypedObject(InputConnectionCommandHeader.CREATOR);
                    int readInt34 = parcel.readInt();
                    int readInt35 = parcel.readInt();
                    CharSequence charSequence5 = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    int readInt36 = parcel.readInt();
                    TextAttribute textAttribute4 = (TextAttribute) parcel.readTypedObject(TextAttribute.CREATOR);
                    parcel.enforceNoDataAvail();
                    replaceText(inputConnectionCommandHeader34, readInt34, readInt35, charSequence5, readInt36, textAttribute4);
                    return true;
                case 35:
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    cancelCancellationSignal(readStrongBinder2);
                    return true;
                case 36:
                    IBinder readStrongBinder3 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    forgetCancellationSignal(readStrongBinder3);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IRemoteInputConnection {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRemoteInputConnection.DESCRIPTOR;
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void getTextBeforeCursor(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, AndroidFuture androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void getTextAfterCursor(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, AndroidFuture androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void getCursorCapsMode(InputConnectionCommandHeader inputConnectionCommandHeader, int i, AndroidFuture androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void getExtractedText(InputConnectionCommandHeader inputConnectionCommandHeader, ExtractedTextRequest extractedTextRequest, int i, AndroidFuture androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeTypedObject(extractedTextRequest, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void deleteSurroundingText(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void deleteSurroundingTextInCodePoints(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void setComposingText(InputConnectionCommandHeader inputConnectionCommandHeader, CharSequence charSequence, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void setComposingTextWithTextAttribute(InputConnectionCommandHeader inputConnectionCommandHeader, CharSequence charSequence, int i, TextAttribute textAttribute) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeInt(i);
                    obtain.writeTypedObject(textAttribute, 0);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void finishComposingText(InputConnectionCommandHeader inputConnectionCommandHeader) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void commitText(InputConnectionCommandHeader inputConnectionCommandHeader, CharSequence charSequence, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void commitTextWithTextAttribute(InputConnectionCommandHeader inputConnectionCommandHeader, CharSequence charSequence, int i, TextAttribute textAttribute) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeInt(i);
                    obtain.writeTypedObject(textAttribute, 0);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void commitCompletion(InputConnectionCommandHeader inputConnectionCommandHeader, CompletionInfo completionInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeTypedObject(completionInfo, 0);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void commitCorrection(InputConnectionCommandHeader inputConnectionCommandHeader, CorrectionInfo correctionInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeTypedObject(correctionInfo, 0);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void setSelection(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void performEditorAction(InputConnectionCommandHeader inputConnectionCommandHeader, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void performContextMenuAction(InputConnectionCommandHeader inputConnectionCommandHeader, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void beginBatchEdit(InputConnectionCommandHeader inputConnectionCommandHeader) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void endBatchEdit(InputConnectionCommandHeader inputConnectionCommandHeader) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void sendKeyEvent(InputConnectionCommandHeader inputConnectionCommandHeader, KeyEvent keyEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeTypedObject(keyEvent, 0);
                    this.mRemote.transact(19, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void clearMetaKeyStates(InputConnectionCommandHeader inputConnectionCommandHeader, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(20, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void performSpellCheck(InputConnectionCommandHeader inputConnectionCommandHeader) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    this.mRemote.transact(21, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void performPrivateCommand(InputConnectionCommandHeader inputConnectionCommandHeader, String str, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(22, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void performHandwritingGesture(InputConnectionCommandHeader inputConnectionCommandHeader, ParcelableHandwritingGesture parcelableHandwritingGesture, ResultReceiver resultReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeTypedObject(parcelableHandwritingGesture, 0);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(23, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void previewHandwritingGesture(InputConnectionCommandHeader inputConnectionCommandHeader, ParcelableHandwritingGesture parcelableHandwritingGesture, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeTypedObject(parcelableHandwritingGesture, 0);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(24, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void setComposingRegion(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(25, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void setComposingRegionWithTextAttribute(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, TextAttribute textAttribute) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(textAttribute, 0);
                    this.mRemote.transact(26, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void getSelectedText(InputConnectionCommandHeader inputConnectionCommandHeader, int i, AndroidFuture androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(27, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void requestCursorUpdates(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, AndroidFuture androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(28, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void requestCursorUpdatesWithFilter(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, int i3, AndroidFuture androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(29, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void requestTextBoundsInfo(InputConnectionCommandHeader inputConnectionCommandHeader, RectF rectF, ResultReceiver resultReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeTypedObject(rectF, 0);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(30, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void commitContent(InputConnectionCommandHeader inputConnectionCommandHeader, InputContentInfo inputContentInfo, int i, Bundle bundle, AndroidFuture androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeTypedObject(inputContentInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(31, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void getSurroundingText(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, int i3, AndroidFuture androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(32, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void setImeConsumesInput(InputConnectionCommandHeader inputConnectionCommandHeader, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(33, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void replaceText(InputConnectionCommandHeader inputConnectionCommandHeader, int i, int i2, CharSequence charSequence, int i3, TextAttribute textAttribute) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeTypedObject(inputConnectionCommandHeader, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeInt(i3);
                    obtain.writeTypedObject(textAttribute, 0);
                    this.mRemote.transact(34, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void cancelCancellationSignal(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(35, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IRemoteInputConnection
            public void forgetCancellationSignal(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IRemoteInputConnection.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(36, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
