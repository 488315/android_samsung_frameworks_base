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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IInputMethod.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IInputMethod)) {
                return (IInputMethod) queryLocalInterface;
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
                    IInlineSuggestionsRequestCallback asInterface = IInlineSuggestionsRequestCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onCreateInlineSuggestionsRequest(inlineSuggestionsRequestInfo, asInterface);
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
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onNavButtonFlagsChanged(readInt);
                    return true;
                case 7:
                    InputChannel inputChannel = (InputChannel) parcel.readTypedObject(InputChannel.CREATOR);
                    IInputMethodSessionCallback asInterface2 = IInputMethodSessionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    createSession(inputChannel, asInterface2);
                    return true;
                case 8:
                    IInputMethodSession asInterface3 = IInputMethodSession.Stub.asInterface(parcel.readStrongBinder());
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSessionEnabled(asInterface3, readBoolean);
                    return true;
                case 9:
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    ImeTracker.Token token = (ImeTracker.Token) parcel.readTypedObject(ImeTracker.Token.CREATOR);
                    int readInt2 = parcel.readInt();
                    ResultReceiver resultReceiver = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    showSoftInput(readStrongBinder, token, readInt2, resultReceiver);
                    return true;
                case 10:
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    ImeTracker.Token token2 = (ImeTracker.Token) parcel.readTypedObject(ImeTracker.Token.CREATOR);
                    int readInt3 = parcel.readInt();
                    ResultReceiver resultReceiver2 = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    hideSoftInput(readStrongBinder2, token2, readInt3, resultReceiver2);
                    return true;
                case 11:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateEditorToolType(readInt4);
                    return true;
                case 12:
                    InputMethodSubtype inputMethodSubtype = (InputMethodSubtype) parcel.readTypedObject(InputMethodSubtype.CREATOR);
                    parcel.enforceNoDataAvail();
                    changeInputMethodSubtype(inputMethodSubtype);
                    return true;
                case 13:
                    int readInt5 = parcel.readInt();
                    IConnectionlessHandwritingCallback asInterface4 = IConnectionlessHandwritingCallback.Stub.asInterface(parcel.readStrongBinder());
                    CursorAnchorInfo cursorAnchorInfo = (CursorAnchorInfo) parcel.readTypedObject(CursorAnchorInfo.CREATOR);
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    canStartStylusHandwriting(readInt5, asInterface4, cursorAnchorInfo, readBoolean2);
                    return true;
                case 14:
                    int readInt6 = parcel.readInt();
                    InputChannel inputChannel2 = (InputChannel) parcel.readTypedObject(InputChannel.CREATOR);
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(MotionEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    startStylusHandwriting(readInt6, inputChannel2, createTypedArrayList);
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
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setStylusWindowIdleTimeoutForTest(readLong);
                    return true;
                case 21:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    minimizeSoftInput(readInt7);
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
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputMethod.DESCRIPTOR);
                    obtain.writeTypedObject(initParams, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void onCreateInlineSuggestionsRequest(InlineSuggestionsRequestInfo inlineSuggestionsRequestInfo, IInlineSuggestionsRequestCallback iInlineSuggestionsRequestCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputMethod.DESCRIPTOR);
                    obtain.writeTypedObject(inlineSuggestionsRequestInfo, 0);
                    obtain.writeStrongInterface(iInlineSuggestionsRequestCallback);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void bindInput(InputBinding inputBinding) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputMethod.DESCRIPTOR);
                    obtain.writeTypedObject(inputBinding, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void unbindInput() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputMethod.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void startInput(StartInputParams startInputParams) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputMethod.DESCRIPTOR);
                    obtain.writeTypedObject(startInputParams, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void onNavButtonFlagsChanged(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputMethod.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void createSession(InputChannel inputChannel, IInputMethodSessionCallback iInputMethodSessionCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputMethod.DESCRIPTOR);
                    obtain.writeTypedObject(inputChannel, 0);
                    obtain.writeStrongInterface(iInputMethodSessionCallback);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void setSessionEnabled(IInputMethodSession iInputMethodSession, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputMethod.DESCRIPTOR);
                    obtain.writeStrongInterface(iInputMethodSession);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void showSoftInput(IBinder iBinder, ImeTracker.Token token, int i, ResultReceiver resultReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputMethod.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(token, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void hideSoftInput(IBinder iBinder, ImeTracker.Token token, int i, ResultReceiver resultReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputMethod.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(token, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void updateEditorToolType(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputMethod.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void changeInputMethodSubtype(InputMethodSubtype inputMethodSubtype) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputMethod.DESCRIPTOR);
                    obtain.writeTypedObject(inputMethodSubtype, 0);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void canStartStylusHandwriting(int i, IConnectionlessHandwritingCallback iConnectionlessHandwritingCallback, CursorAnchorInfo cursorAnchorInfo, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputMethod.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iConnectionlessHandwritingCallback);
                    obtain.writeTypedObject(cursorAnchorInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void startStylusHandwriting(int i, InputChannel inputChannel, List<MotionEvent> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputMethod.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(inputChannel, 0);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void commitHandwritingDelegationTextIfAvailable() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputMethod.DESCRIPTOR);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void discardHandwritingDelegationText() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputMethod.DESCRIPTOR);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void initInkWindow() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputMethod.DESCRIPTOR);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void finishStylusHandwriting() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputMethod.DESCRIPTOR);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void removeStylusHandwritingWindow() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputMethod.DESCRIPTOR);
                    this.mRemote.transact(19, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void setStylusWindowIdleTimeoutForTest(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputMethod.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(20, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void minimizeSoftInput(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputMethod.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(21, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.inputmethod.IInputMethod
            public void undoMinimizeSoftInput() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInputMethod.DESCRIPTOR);
                    this.mRemote.transact(22, obtain, null, 1);
                } finally {
                    obtain.recycle();
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
            int dataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeStrongBinder(this.token);
            parcel.writeStrongInterface(this.privilegedOperations);
            parcel.writeInt(this.navigationBarFlags);
            int dataPosition2 = parcel.dataPosition();
            parcel.setDataPosition(dataPosition);
            parcel.writeInt(dataPosition2 - dataPosition);
            parcel.setDataPosition(dataPosition2);
        }

        public final void readFromParcel(Parcel parcel) {
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            try {
                if (readInt < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.token = parcel.readStrongBinder();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.privilegedOperations = IInputMethodPrivilegedOperations.Stub.asInterface(parcel.readStrongBinder());
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.navigationBarFlags = parcel.readInt();
                            if (dataPosition > Integer.MAX_VALUE - readInt) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } catch (Throwable th) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
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
            int dataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeStrongBinder(this.startInputToken);
            parcel.writeStrongInterface(this.remoteInputConnection);
            parcel.writeTypedObject(this.editorInfo, i);
            parcel.writeBoolean(this.restarting);
            parcel.writeInt(this.navigationBarFlags);
            parcel.writeTypedObject(this.imeDispatcher, i);
            int dataPosition2 = parcel.dataPosition();
            parcel.setDataPosition(dataPosition);
            parcel.writeInt(dataPosition2 - dataPosition);
            parcel.setDataPosition(dataPosition2);
        }

        public final void readFromParcel(Parcel parcel) {
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            try {
                if (readInt < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.startInputToken = parcel.readStrongBinder();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.remoteInputConnection = IRemoteInputConnection.Stub.asInterface(parcel.readStrongBinder());
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.editorInfo = (EditorInfo) parcel.readTypedObject(EditorInfo.CREATOR);
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.restarting = parcel.readBoolean();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.navigationBarFlags = parcel.readInt();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.imeDispatcher = (ImeOnBackInvokedDispatcher) parcel.readTypedObject(ImeOnBackInvokedDispatcher.CREATOR);
                                        if (dataPosition > Integer.MAX_VALUE - readInt) {
                                            throw new BadParcelableException("Overflow in the size of parcelable");
                                        }
                                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                        throw new BadParcelableException("Overflow in the size of parcelable");
                                    }
                                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                    throw new BadParcelableException("Overflow in the size of parcelable");
                                }
                            } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } catch (Throwable th) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
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
