package com.android.internal.inputmethod;

import android.os.BadParcelableException;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.view.InputChannel;
import android.view.MotionEvent;
import android.view.inputmethod.CursorAnchorInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.ImeTracker;
import android.view.inputmethod.InputBinding;
import android.view.inputmethod.InputMethodSubtype;
import android.window.ImeOnBackInvokedDispatcher;
import com.android.internal.inputmethod.IConnectionlessHandwritingCallback;
import com.android.internal.inputmethod.IInlineSuggestionsRequestCallback;
import com.android.internal.inputmethod.IInputMethodPrivilegedOperations;
import com.android.internal.inputmethod.IInputMethodSession;
import com.android.internal.inputmethod.IInputMethodSessionCallback;
import com.android.internal.inputmethod.IRemoteInputConnection;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public interface IInputMethod extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.inputmethod.IInputMethod";

    public static class Default implements IInputMethod {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.inputmethod.IInputMethod
        public void bindInput(InputBinding inputBinding) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethod
        public void canStartStylusHandwriting(int i, IConnectionlessHandwritingCallback iConnectionlessHandwritingCallback, CursorAnchorInfo cursorAnchorInfo, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethod
        public void changeInputMethodSubtype(InputMethodSubtype inputMethodSubtype) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethod
        public void commitHandwritingDelegationTextIfAvailable() throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethod
        public void createSession(InputChannel inputChannel, IInputMethodSessionCallback iInputMethodSessionCallback) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethod
        public void discardHandwritingDelegationText() throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethod
        public void finishStylusHandwriting() throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethod
        public void hideSoftInput(IBinder iBinder, ImeTracker.Token token, int i, ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethod
        public void initInkWindow() throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethod
        public void initializeInternal(InitParams initParams) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethod
        public void minimizeSoftInput(int i) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethod
        public void onCreateInlineSuggestionsRequest(InlineSuggestionsRequestInfo inlineSuggestionsRequestInfo, IInlineSuggestionsRequestCallback iInlineSuggestionsRequestCallback) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethod
        public void onNavButtonFlagsChanged(int i) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethod
        public void removeStylusHandwritingWindow() throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethod
        public void setSessionEnabled(IInputMethodSession iInputMethodSession, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethod
        public void setStylusWindowIdleTimeoutForTest(long j) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethod
        public void showSoftInput(IBinder iBinder, ImeTracker.Token token, int i, ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethod
        public void startInput(StartInputParams startInputParams) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethod
        public void startStylusHandwriting(int i, InputChannel inputChannel, List<MotionEvent> list) throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethod
        public void unbindInput() throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethod
        public void undoMinimizeSoftInput() throws RemoteException {
        }

        @Override // com.android.internal.inputmethod.IInputMethod
        public void updateEditorToolType(int i) throws RemoteException {
        }
    }

    void bindInput(InputBinding inputBinding) throws RemoteException;

    void canStartStylusHandwriting(int i, IConnectionlessHandwritingCallback iConnectionlessHandwritingCallback, CursorAnchorInfo cursorAnchorInfo, boolean z) throws RemoteException;

    void changeInputMethodSubtype(InputMethodSubtype inputMethodSubtype) throws RemoteException;

    void commitHandwritingDelegationTextIfAvailable() throws RemoteException;

    void createSession(InputChannel inputChannel, IInputMethodSessionCallback iInputMethodSessionCallback) throws RemoteException;

    void discardHandwritingDelegationText() throws RemoteException;

    void finishStylusHandwriting() throws RemoteException;

    void hideSoftInput(IBinder iBinder, ImeTracker.Token token, int i, ResultReceiver resultReceiver) throws RemoteException;

    void initInkWindow() throws RemoteException;

    void initializeInternal(InitParams initParams) throws RemoteException;

    void minimizeSoftInput(int i) throws RemoteException;

    void onCreateInlineSuggestionsRequest(InlineSuggestionsRequestInfo inlineSuggestionsRequestInfo, IInlineSuggestionsRequestCallback iInlineSuggestionsRequestCallback) throws RemoteException;

    void onNavButtonFlagsChanged(int i) throws RemoteException;

    void removeStylusHandwritingWindow() throws RemoteException;

    void setSessionEnabled(IInputMethodSession iInputMethodSession, boolean z) throws RemoteException;

    void setStylusWindowIdleTimeoutForTest(long j) throws RemoteException;

    void showSoftInput(IBinder iBinder, ImeTracker.Token token, int i, ResultReceiver resultReceiver) throws RemoteException;

    void startInput(StartInputParams startInputParams) throws RemoteException;

    void startStylusHandwriting(int i, InputChannel inputChannel, List<MotionEvent> list) throws RemoteException;

    void unbindInput() throws RemoteException;

    void undoMinimizeSoftInput() throws RemoteException;

    void updateEditorToolType(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IInputMethod {
        static final int TRANSACTION_bindInput = 3;
        static final int TRANSACTION_canStartStylusHandwriting = 13;
        static final int TRANSACTION_changeInputMethodSubtype = 12;
        static final int TRANSACTION_commitHandwritingDelegationTextIfAvailable = 15;
        static final int TRANSACTION_createSession = 7;
        static final int TRANSACTION_discardHandwritingDelegationText = 16;
        static final int TRANSACTION_finishStylusHandwriting = 18;
        static final int TRANSACTION_hideSoftInput = 10;
        static final int TRANSACTION_initInkWindow = 17;
        static final int TRANSACTION_initializeInternal = 1;
        static final int TRANSACTION_minimizeSoftInput = 21;
        static final int TRANSACTION_onCreateInlineSuggestionsRequest = 2;
        static final int TRANSACTION_onNavButtonFlagsChanged = 6;
        static final int TRANSACTION_removeStylusHandwritingWindow = 19;
        static final int TRANSACTION_setSessionEnabled = 8;
        static final int TRANSACTION_setStylusWindowIdleTimeoutForTest = 20;
        static final int TRANSACTION_showSoftInput = 9;
        static final int TRANSACTION_startInput = 5;
        static final int TRANSACTION_startStylusHandwriting = 14;
        static final int TRANSACTION_unbindInput = 4;
        static final int TRANSACTION_undoMinimizeSoftInput = 22;
        static final int TRANSACTION_updateEditorToolType = 11;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 21;
        }

        public Stub() {
            attachInterface(this, IInputMethod.DESCRIPTOR);
        }

        public static IInputMethod asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IInputMethod.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IInputMethod)) {
                return (IInputMethod) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "initializeInternal";
                case 2:
                    return "onCreateInlineSuggestionsRequest";
                case 3:
                    return "bindInput";
                case 4:
                    return "unbindInput";
                case 5:
                    return "startInput";
                case 6:
                    return "onNavButtonFlagsChanged";
                case 7:
                    return "createSession";
                case 8:
                    return "setSessionEnabled";
                case 9:
                    return "showSoftInput";
                case 10:
                    return "hideSoftInput";
                case 11:
                    return "updateEditorToolType";
                case 12:
                    return "changeInputMethodSubtype";
                case 13:
                    return "canStartStylusHandwriting";
                case 14:
                    return "startStylusHandwriting";
                case 15:
                    return "commitHandwritingDelegationTextIfAvailable";
                case 16:
                    return "discardHandwritingDelegationText";
                case 17:
                    return "initInkWindow";
                case 18:
                    return "finishStylusHandwriting";
                case 19:
                    return "removeStylusHandwritingWindow";
                case 20:
                    return "setStylusWindowIdleTimeoutForTest";
                case 21:
                    return "minimizeSoftInput";
                case 22:
                    return "undoMinimizeSoftInput";
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
                parcel.enforceInterface(IInputMethod.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IInputMethod.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    InitParams initParams = (InitParams) parcel.readTypedObject(InitParams.CREATOR);
                    parcel.enforceNoDataAvail();
                    initializeInternal(initParams);
                    return true;
                case 2:
                    InlineSuggestionsRequestInfo inlineSuggestionsRequestInfo = (InlineSuggestionsRequestInfo) parcel.readTypedObject(InlineSuggestionsRequestInfo.CREATOR);
                    IInlineSuggestionsRequestCallback iInlineSuggestionsRequestCallbackAsInterface = IInlineSuggestionsRequestCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onCreateInlineSuggestionsRequest(inlineSuggestionsRequestInfo, iInlineSuggestionsRequestCallbackAsInterface);
                    return true;
                case 3:
                    InputBinding inputBinding = (InputBinding) parcel.readTypedObject(InputBinding.CREATOR);
                    parcel.enforceNoDataAvail();
                    bindInput(inputBinding);
                    return true;
                case 4:
                    unbindInput();
                    return true;
                case 5:
                    StartInputParams startInputParams = (StartInputParams) parcel.readTypedObject(StartInputParams.CREATOR);
                    parcel.enforceNoDataAvail();
                    startInput(startInputParams);
                    return true;
                case 6:
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onNavButtonFlagsChanged(i3);
                    return true;
                case 7:
                    InputChannel inputChannel = (InputChannel) parcel.readTypedObject(InputChannel.CREATOR);
                    IInputMethodSessionCallback iInputMethodSessionCallbackAsInterface = IInputMethodSessionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    createSession(inputChannel, iInputMethodSessionCallbackAsInterface);
                    return true;
                case 8:
                    IInputMethodSession iInputMethodSessionAsInterface = IInputMethodSession.Stub.asInterface(parcel.readStrongBinder());
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSessionEnabled(iInputMethodSessionAsInterface, z);
                    return true;
                case 9:
                    IBinder strongBinder = parcel.readStrongBinder();
                    ImeTracker.Token token = (ImeTracker.Token) parcel.readTypedObject(ImeTracker.Token.CREATOR);
                    int i4 = parcel.readInt();
                    ResultReceiver resultReceiver = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    showSoftInput(strongBinder, token, i4, resultReceiver);
                    return true;
                case 10:
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    ImeTracker.Token token2 = (ImeTracker.Token) parcel.readTypedObject(ImeTracker.Token.CREATOR);
                    int i5 = parcel.readInt();
                    ResultReceiver resultReceiver2 = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    hideSoftInput(strongBinder2, token2, i5, resultReceiver2);
                    return true;
                case 11:
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateEditorToolType(i6);
                    return true;
                case 12:
                    InputMethodSubtype inputMethodSubtype = (InputMethodSubtype) parcel.readTypedObject(InputMethodSubtype.CREATOR);
                    parcel.enforceNoDataAvail();
                    changeInputMethodSubtype(inputMethodSubtype);
                    return true;
                case 13:
                    int i7 = parcel.readInt();
                    IConnectionlessHandwritingCallback iConnectionlessHandwritingCallbackAsInterface = IConnectionlessHandwritingCallback.Stub.asInterface(parcel.readStrongBinder());
                    CursorAnchorInfo cursorAnchorInfo = (CursorAnchorInfo) parcel.readTypedObject(CursorAnchorInfo.CREATOR);
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    canStartStylusHandwriting(i7, iConnectionlessHandwritingCallbackAsInterface, cursorAnchorInfo, z2);
                    return true;
                case 14:
                    int i8 = parcel.readInt();
                    InputChannel inputChannel2 = (InputChannel) parcel.readTypedObject(InputChannel.CREATOR);
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(MotionEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    startStylusHandwriting(i8, inputChannel2, arrayListCreateTypedArrayList);
                    return true;
                case 15:
                    commitHandwritingDelegationTextIfAvailable();
                    return true;
                case 16:
                    discardHandwritingDelegationText();
                    return true;
                case 17:
                    initInkWindow();
                    return true;
                case 18:
                    finishStylusHandwriting();
                    return true;
                case 19:
                    removeStylusHandwritingWindow();
                    return true;
                case 20:
                    long j = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setStylusWindowIdleTimeoutForTest(j);
                    return true;
                case 21:
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    minimizeSoftInput(i9);
                    return true;
                case 22:
                    undoMinimizeSoftInput();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IInputMethod {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IInputMethod.DESCRIPTOR;
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void initializeInternal(InitParams initParams) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethod.DESCRIPTOR);
                    parcelObtain.writeTypedObject(initParams, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void onCreateInlineSuggestionsRequest(InlineSuggestionsRequestInfo inlineSuggestionsRequestInfo, IInlineSuggestionsRequestCallback iInlineSuggestionsRequestCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethod.DESCRIPTOR);
                    parcelObtain.writeTypedObject(inlineSuggestionsRequestInfo, 0);
                    parcelObtain.writeStrongInterface(iInlineSuggestionsRequestCallback);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void bindInput(InputBinding inputBinding) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethod.DESCRIPTOR);
                    parcelObtain.writeTypedObject(inputBinding, 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void unbindInput() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethod.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void startInput(StartInputParams startInputParams) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethod.DESCRIPTOR);
                    parcelObtain.writeTypedObject(startInputParams, 0);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void onNavButtonFlagsChanged(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethod.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void createSession(InputChannel inputChannel, IInputMethodSessionCallback iInputMethodSessionCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethod.DESCRIPTOR);
                    parcelObtain.writeTypedObject(inputChannel, 0);
                    parcelObtain.writeStrongInterface(iInputMethodSessionCallback);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void setSessionEnabled(IInputMethodSession iInputMethodSession, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethod.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iInputMethodSession);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void showSoftInput(IBinder iBinder, ImeTracker.Token token, int i, ResultReceiver resultReceiver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethod.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(token, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void hideSoftInput(IBinder iBinder, ImeTracker.Token token, int i, ResultReceiver resultReceiver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethod.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(token, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void updateEditorToolType(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethod.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(11, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void changeInputMethodSubtype(InputMethodSubtype inputMethodSubtype) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethod.DESCRIPTOR);
                    parcelObtain.writeTypedObject(inputMethodSubtype, 0);
                    this.mRemote.transact(12, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void canStartStylusHandwriting(int i, IConnectionlessHandwritingCallback iConnectionlessHandwritingCallback, CursorAnchorInfo cursorAnchorInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethod.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iConnectionlessHandwritingCallback);
                    parcelObtain.writeTypedObject(cursorAnchorInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(13, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void startStylusHandwriting(int i, InputChannel inputChannel, List<MotionEvent> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethod.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(inputChannel, 0);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(14, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void commitHandwritingDelegationTextIfAvailable() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethod.DESCRIPTOR);
                    this.mRemote.transact(15, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void discardHandwritingDelegationText() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethod.DESCRIPTOR);
                    this.mRemote.transact(16, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void initInkWindow() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethod.DESCRIPTOR);
                    this.mRemote.transact(17, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void finishStylusHandwriting() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethod.DESCRIPTOR);
                    this.mRemote.transact(18, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void removeStylusHandwritingWindow() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethod.DESCRIPTOR);
                    this.mRemote.transact(19, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void setStylusWindowIdleTimeoutForTest(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethod.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(20, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void minimizeSoftInput(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethod.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(21, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void undoMinimizeSoftInput() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInputMethod.DESCRIPTOR);
                    this.mRemote.transact(22, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }

    public static class InitParams implements Parcelable {
        public static final Parcelable.Creator<InitParams> CREATOR = new Parcelable.Creator<InitParams>() { // from class: com.android.internal.inputmethod.IInputMethod.InitParams.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public InitParams createFromParcel(Parcel parcel) {
                InitParams initParams = new InitParams();
                initParams.readFromParcel(parcel);
                return initParams;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public InitParams[] newArray(int i) {
                return new InitParams[i];
            }
        };
        public int navigationBarFlags = 0;
        public IInputMethodPrivilegedOperations privilegedOperations;
        public IBinder token;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            int iDataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeStrongBinder(this.token);
            parcel.writeStrongInterface(this.privilegedOperations);
            parcel.writeInt(this.navigationBarFlags);
            int iDataPosition2 = parcel.dataPosition();
            parcel.setDataPosition(iDataPosition);
            parcel.writeInt(iDataPosition2 - iDataPosition);
            parcel.setDataPosition(iDataPosition2);
        }

        public final void readFromParcel(Parcel parcel) {
            int iDataPosition = parcel.dataPosition();
            int i = parcel.readInt();
            try {
                if (i < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.token = parcel.readStrongBinder();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.privilegedOperations = IInputMethodPrivilegedOperations.Stub.asInterface(parcel.readStrongBinder());
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.navigationBarFlags = parcel.readInt();
                            if (iDataPosition > Integer.MAX_VALUE - i) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (iDataPosition > Integer.MAX_VALUE - i) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (iDataPosition > Integer.MAX_VALUE - i) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(iDataPosition + i);
            } catch (Throwable th) {
                if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(iDataPosition + i);
                throw th;
            }
        }
    }

    public static class StartInputParams implements Parcelable {
        public static final Parcelable.Creator<StartInputParams> CREATOR = new Parcelable.Creator<StartInputParams>() { // from class: com.android.internal.inputmethod.IInputMethod.StartInputParams.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public StartInputParams createFromParcel(Parcel parcel) {
                StartInputParams startInputParams = new StartInputParams();
                startInputParams.readFromParcel(parcel);
                return startInputParams;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public StartInputParams[] newArray(int i) {
                return new StartInputParams[i];
            }
        };
        public EditorInfo editorInfo;
        public ImeOnBackInvokedDispatcher imeDispatcher;
        public IRemoteInputConnection remoteInputConnection;
        public IBinder startInputToken;
        public boolean restarting = false;
        public int navigationBarFlags = 0;

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            int iDataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeStrongBinder(this.startInputToken);
            parcel.writeStrongInterface(this.remoteInputConnection);
            parcel.writeTypedObject(this.editorInfo, i);
            parcel.writeBoolean(this.restarting);
            parcel.writeInt(this.navigationBarFlags);
            parcel.writeTypedObject(this.imeDispatcher, i);
            int iDataPosition2 = parcel.dataPosition();
            parcel.setDataPosition(iDataPosition);
            parcel.writeInt(iDataPosition2 - iDataPosition);
            parcel.setDataPosition(iDataPosition2);
        }

        public final void readFromParcel(Parcel parcel) {
            int iDataPosition = parcel.dataPosition();
            int i = parcel.readInt();
            try {
                if (i < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.startInputToken = parcel.readStrongBinder();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.remoteInputConnection = IRemoteInputConnection.Stub.asInterface(parcel.readStrongBinder());
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.editorInfo = (EditorInfo) parcel.readTypedObject(EditorInfo.CREATOR);
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.restarting = parcel.readBoolean();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.navigationBarFlags = parcel.readInt();
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.imeDispatcher = (ImeOnBackInvokedDispatcher) parcel.readTypedObject(ImeOnBackInvokedDispatcher.CREATOR);
                                        if (iDataPosition > Integer.MAX_VALUE - i) {
                                            throw new BadParcelableException("Overflow in the size of parcelable");
                                        }
                                    } else if (iDataPosition > Integer.MAX_VALUE - i) {
                                        throw new BadParcelableException("Overflow in the size of parcelable");
                                    }
                                } else if (iDataPosition > Integer.MAX_VALUE - i) {
                                    throw new BadParcelableException("Overflow in the size of parcelable");
                                }
                            } else if (iDataPosition > Integer.MAX_VALUE - i) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (iDataPosition > Integer.MAX_VALUE - i) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (iDataPosition > Integer.MAX_VALUE - i) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(iDataPosition + i);
            } catch (Throwable th) {
                if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(iDataPosition + i);
                throw th;
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return describeContents(this.imeDispatcher) | describeContents(this.editorInfo);
        }

        private int describeContents(Object obj) {
            if (obj != null && (obj instanceof Parcelable)) {
                return ((Parcelable) obj).describeContents();
            }
            return 0;
        }
    }
}
