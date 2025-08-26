package android.app;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.DebugUtils;
import android.util.Log;
import com.android.internal.app.IVoiceInteractor;
import com.android.internal.app.IVoiceInteractorCallback;
import com.android.internal.app.IVoiceInteractorRequest;
import com.android.internal.os.HandlerCaller;
import com.android.internal.os.SomeArgs;
import com.android.internal.util.function.pooled.PooledLambda;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public final class VoiceInteractor {
    static final boolean DEBUG = false;
    public static final String KEY_CANCELLATION_SIGNAL = "key_cancellation_signal";
    static final int MSG_ABORT_VOICE_RESULT = 4;
    static final int MSG_CANCEL_RESULT = 6;
    static final int MSG_COMMAND_RESULT = 5;
    static final int MSG_COMPLETE_VOICE_RESULT = 3;
    static final int MSG_CONFIRMATION_RESULT = 1;
    static final int MSG_PICK_OPTION_RESULT = 2;
    static final Request[] NO_REQUESTS = new Request[0];
    static final String TAG = "VoiceInteractor";
    final ArrayMap<IBinder, Request> mActiveRequests;
    Activity mActivity;
    final IVoiceInteractorCallback.Stub mCallback;
    Context mContext;
    final HandlerCaller mHandlerCaller;
    final HandlerCaller.Callback mHandlerCallerCallback;
    IVoiceInteractor mInteractor;
    final ArrayMap<Runnable, Executor> mOnDestroyCallbacks;
    boolean mRetaining;

    public static abstract class Request {
        Activity mActivity;
        Context mContext;
        String mName;
        IVoiceInteractorRequest mRequestInterface;

        public void onAttached(Activity activity) {
        }

        public void onCancel() {
        }

        public void onDetached() {
        }

        abstract IVoiceInteractorRequest submit(IVoiceInteractor iVoiceInteractor, String str, IVoiceInteractorCallback iVoiceInteractorCallback) throws RemoteException;

        Request() {
        }

        public String getName() {
            return this.mName;
        }

        public void cancel() {
            IVoiceInteractorRequest iVoiceInteractorRequest = this.mRequestInterface;
            if (iVoiceInteractorRequest == null) {
                throw new IllegalStateException("Request " + this + " is no longer active");
            }
            try {
                iVoiceInteractorRequest.cancel();
            } catch (RemoteException e) {
                Log.w(VoiceInteractor.TAG, "Voice interactor has died", e);
            }
        }

        public Context getContext() {
            return this.mContext;
        }

        public Activity getActivity() {
            return this.mActivity;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(128);
            DebugUtils.buildShortClassTag(this, sb);
            sb.append(" ");
            sb.append(getRequestTypeName());
            sb.append(" name=");
            sb.append(this.mName);
            sb.append('}');
            return sb.toString();
        }

        void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            printWriter.print(str);
            printWriter.print("mRequestInterface=");
            printWriter.println(this.mRequestInterface.asBinder());
            printWriter.print(str);
            printWriter.print("mActivity=");
            printWriter.println(this.mActivity);
            printWriter.print(str);
            printWriter.print("mName=");
            printWriter.println(this.mName);
        }

        String getRequestTypeName() {
            return "Request";
        }

        void clear() {
            this.mRequestInterface = null;
            this.mContext = null;
            this.mActivity = null;
            this.mName = null;
        }
    }

    public static class ConfirmationRequest extends Request {
        final Bundle mExtras;
        final Prompt mPrompt;

        public void onConfirmationResult(boolean z, Bundle bundle) {
        }

        public ConfirmationRequest(Prompt prompt, Bundle bundle) {
            this.mPrompt = prompt;
            this.mExtras = bundle;
        }

        public ConfirmationRequest(CharSequence charSequence, Bundle bundle) {
            this.mPrompt = charSequence != null ? new Prompt(charSequence) : null;
            this.mExtras = bundle;
        }

        @Override // android.app.VoiceInteractor.Request
        void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            super.dump(str, fileDescriptor, printWriter, strArr);
            printWriter.print(str);
            printWriter.print("mPrompt=");
            printWriter.println(this.mPrompt);
            if (this.mExtras != null) {
                printWriter.print(str);
                printWriter.print("mExtras=");
                printWriter.println(this.mExtras);
            }
        }

        @Override // android.app.VoiceInteractor.Request
        String getRequestTypeName() {
            return "Confirmation";
        }

        @Override // android.app.VoiceInteractor.Request
        IVoiceInteractorRequest submit(IVoiceInteractor iVoiceInteractor, String str, IVoiceInteractorCallback iVoiceInteractorCallback) throws RemoteException {
            return iVoiceInteractor.startConfirmation(str, iVoiceInteractorCallback, this.mPrompt, this.mExtras);
        }
    }

    public static class PickOptionRequest extends Request {
        final Bundle mExtras;
        final Option[] mOptions;
        final Prompt mPrompt;

        public void onPickOptionResult(boolean z, Option[] optionArr, Bundle bundle) {
        }

        public static final class Option implements Parcelable {
            public static final Parcelable.Creator<Option> CREATOR = new Parcelable.Creator<Option>() { // from class: android.app.VoiceInteractor.PickOptionRequest.Option.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public Option createFromParcel(Parcel parcel) {
                    return new Option(parcel);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public Option[] newArray(int i) {
                    return new Option[i];
                }
            };
            Bundle mExtras;
            final int mIndex;
            final CharSequence mLabel;
            ArrayList<CharSequence> mSynonyms;

            @Override // android.os.Parcelable
            public int describeContents() {
                return 0;
            }

            public Option(CharSequence charSequence) {
                this.mLabel = charSequence;
                this.mIndex = -1;
            }

            public Option(CharSequence charSequence, int i) {
                this.mLabel = charSequence;
                this.mIndex = i;
            }

            public Option addSynonym(CharSequence charSequence) {
                if (this.mSynonyms == null) {
                    this.mSynonyms = new ArrayList<>();
                }
                this.mSynonyms.add(charSequence);
                return this;
            }

            public CharSequence getLabel() {
                return this.mLabel;
            }

            public int getIndex() {
                return this.mIndex;
            }

            public int countSynonyms() {
                ArrayList<CharSequence> arrayList = this.mSynonyms;
                if (arrayList != null) {
                    return arrayList.size();
                }
                return 0;
            }

            public CharSequence getSynonymAt(int i) {
                ArrayList<CharSequence> arrayList = this.mSynonyms;
                if (arrayList != null) {
                    return arrayList.get(i);
                }
                return null;
            }

            public void setExtras(Bundle bundle) {
                this.mExtras = bundle;
            }

            public Bundle getExtras() {
                return this.mExtras;
            }

            Option(Parcel parcel) {
                this.mLabel = parcel.readCharSequence();
                this.mIndex = parcel.readInt();
                this.mSynonyms = parcel.readCharSequenceList();
                this.mExtras = parcel.readBundle();
            }

            @Override // android.os.Parcelable
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeCharSequence(this.mLabel);
                parcel.writeInt(this.mIndex);
                parcel.writeCharSequenceList(this.mSynonyms);
                parcel.writeBundle(this.mExtras);
            }
        }

        public PickOptionRequest(Prompt prompt, Option[] optionArr, Bundle bundle) {
            this.mPrompt = prompt;
            this.mOptions = optionArr;
            this.mExtras = bundle;
        }

        public PickOptionRequest(CharSequence charSequence, Option[] optionArr, Bundle bundle) {
            this.mPrompt = charSequence != null ? new Prompt(charSequence) : null;
            this.mOptions = optionArr;
            this.mExtras = bundle;
        }

        @Override // android.app.VoiceInteractor.Request
        void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            super.dump(str, fileDescriptor, printWriter, strArr);
            printWriter.print(str);
            printWriter.print("mPrompt=");
            printWriter.println(this.mPrompt);
            if (this.mOptions != null) {
                printWriter.print(str);
                printWriter.println("Options:");
                int i = 0;
                while (true) {
                    Option[] optionArr = this.mOptions;
                    if (i >= optionArr.length) {
                        break;
                    }
                    Option option = optionArr[i];
                    printWriter.print(str);
                    printWriter.print("  #");
                    printWriter.print(i);
                    printWriter.println(":");
                    printWriter.print(str);
                    printWriter.print("    mLabel=");
                    printWriter.println(option.mLabel);
                    printWriter.print(str);
                    printWriter.print("    mIndex=");
                    printWriter.println(option.mIndex);
                    if (option.mSynonyms != null && option.mSynonyms.size() > 0) {
                        printWriter.print(str);
                        printWriter.println("    Synonyms:");
                        for (int i2 = 0; i2 < option.mSynonyms.size(); i2++) {
                            printWriter.print(str);
                            printWriter.print("      #");
                            printWriter.print(i2);
                            printWriter.print(": ");
                            printWriter.println(option.mSynonyms.get(i2));
                        }
                    }
                    if (option.mExtras != null) {
                        printWriter.print(str);
                        printWriter.print("    mExtras=");
                        printWriter.println(option.mExtras);
                    }
                    i++;
                }
            }
            if (this.mExtras != null) {
                printWriter.print(str);
                printWriter.print("mExtras=");
                printWriter.println(this.mExtras);
            }
        }

        @Override // android.app.VoiceInteractor.Request
        String getRequestTypeName() {
            return "PickOption";
        }

        @Override // android.app.VoiceInteractor.Request
        IVoiceInteractorRequest submit(IVoiceInteractor iVoiceInteractor, String str, IVoiceInteractorCallback iVoiceInteractorCallback) throws RemoteException {
            return iVoiceInteractor.startPickOption(str, iVoiceInteractorCallback, this.mPrompt, this.mOptions, this.mExtras);
        }
    }

    public static class CompleteVoiceRequest extends Request {
        final Bundle mExtras;
        final Prompt mPrompt;

        public void onCompleteResult(Bundle bundle) {
        }

        public CompleteVoiceRequest(Prompt prompt, Bundle bundle) {
            this.mPrompt = prompt;
            this.mExtras = bundle;
        }

        public CompleteVoiceRequest(CharSequence charSequence, Bundle bundle) {
            this.mPrompt = charSequence != null ? new Prompt(charSequence) : null;
            this.mExtras = bundle;
        }

        @Override // android.app.VoiceInteractor.Request
        void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            super.dump(str, fileDescriptor, printWriter, strArr);
            printWriter.print(str);
            printWriter.print("mPrompt=");
            printWriter.println(this.mPrompt);
            if (this.mExtras != null) {
                printWriter.print(str);
                printWriter.print("mExtras=");
                printWriter.println(this.mExtras);
            }
        }

        @Override // android.app.VoiceInteractor.Request
        String getRequestTypeName() {
            return "CompleteVoice";
        }

        @Override // android.app.VoiceInteractor.Request
        IVoiceInteractorRequest submit(IVoiceInteractor iVoiceInteractor, String str, IVoiceInteractorCallback iVoiceInteractorCallback) throws RemoteException {
            return iVoiceInteractor.startCompleteVoice(str, iVoiceInteractorCallback, this.mPrompt, this.mExtras);
        }
    }

    public static class AbortVoiceRequest extends Request {
        final Bundle mExtras;
        final Prompt mPrompt;

        public void onAbortResult(Bundle bundle) {
        }

        public AbortVoiceRequest(Prompt prompt, Bundle bundle) {
            this.mPrompt = prompt;
            this.mExtras = bundle;
        }

        public AbortVoiceRequest(CharSequence charSequence, Bundle bundle) {
            this.mPrompt = charSequence != null ? new Prompt(charSequence) : null;
            this.mExtras = bundle;
        }

        @Override // android.app.VoiceInteractor.Request
        void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            super.dump(str, fileDescriptor, printWriter, strArr);
            printWriter.print(str);
            printWriter.print("mPrompt=");
            printWriter.println(this.mPrompt);
            if (this.mExtras != null) {
                printWriter.print(str);
                printWriter.print("mExtras=");
                printWriter.println(this.mExtras);
            }
        }

        @Override // android.app.VoiceInteractor.Request
        String getRequestTypeName() {
            return "AbortVoice";
        }

        @Override // android.app.VoiceInteractor.Request
        IVoiceInteractorRequest submit(IVoiceInteractor iVoiceInteractor, String str, IVoiceInteractorCallback iVoiceInteractorCallback) throws RemoteException {
            return iVoiceInteractor.startAbortVoice(str, iVoiceInteractorCallback, this.mPrompt, this.mExtras);
        }
    }

    public static class CommandRequest extends Request {
        final Bundle mArgs;
        final String mCommand;

        public void onCommandResult(boolean z, Bundle bundle) {
        }

        public CommandRequest(String str, Bundle bundle) {
            this.mCommand = str;
            this.mArgs = bundle;
        }

        @Override // android.app.VoiceInteractor.Request
        void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            super.dump(str, fileDescriptor, printWriter, strArr);
            printWriter.print(str);
            printWriter.print("mCommand=");
            printWriter.println(this.mCommand);
            if (this.mArgs != null) {
                printWriter.print(str);
                printWriter.print("mArgs=");
                printWriter.println(this.mArgs);
            }
        }

        @Override // android.app.VoiceInteractor.Request
        String getRequestTypeName() {
            return "Command";
        }

        @Override // android.app.VoiceInteractor.Request
        IVoiceInteractorRequest submit(IVoiceInteractor iVoiceInteractor, String str, IVoiceInteractorCallback iVoiceInteractorCallback) throws RemoteException {
            return iVoiceInteractor.startCommand(str, iVoiceInteractorCallback, this.mCommand, this.mArgs);
        }
    }

    public static class Prompt implements Parcelable {
        public static final Parcelable.Creator<Prompt> CREATOR = new Parcelable.Creator<Prompt>() { // from class: android.app.VoiceInteractor.Prompt.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Prompt createFromParcel(Parcel parcel) {
                return new Prompt(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Prompt[] newArray(int i) {
                return new Prompt[i];
            }
        };
        private final CharSequence mVisualPrompt;
        private final CharSequence[] mVoicePrompts;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public Prompt(CharSequence[] charSequenceArr, CharSequence charSequence) {
            if (charSequenceArr == null) {
                throw new NullPointerException("voicePrompts must not be null");
            }
            if (charSequenceArr.length == 0) {
                throw new IllegalArgumentException("voicePrompts must not be empty");
            }
            if (charSequence == null) {
                throw new NullPointerException("visualPrompt must not be null");
            }
            this.mVoicePrompts = charSequenceArr;
            this.mVisualPrompt = charSequence;
        }

        public Prompt(CharSequence charSequence) {
            this.mVoicePrompts = new CharSequence[]{charSequence};
            this.mVisualPrompt = charSequence;
        }

        public CharSequence getVoicePromptAt(int i) {
            return this.mVoicePrompts[i];
        }

        public int countVoicePrompts() {
            return this.mVoicePrompts.length;
        }

        public CharSequence getVisualPrompt() {
            return this.mVisualPrompt;
        }

        public String toString() {
            CharSequence[] charSequenceArr;
            StringBuilder sb = new StringBuilder(128);
            DebugUtils.buildShortClassTag(this, sb);
            CharSequence charSequence = this.mVisualPrompt;
            if (charSequence != null && (charSequenceArr = this.mVoicePrompts) != null && charSequenceArr.length == 1 && charSequence.equals(charSequenceArr[0])) {
                sb.append(" ");
                sb.append(this.mVisualPrompt);
            } else {
                if (this.mVisualPrompt != null) {
                    sb.append(" visual=");
                    sb.append(this.mVisualPrompt);
                }
                if (this.mVoicePrompts != null) {
                    sb.append(", voice=");
                    for (int i = 0; i < this.mVoicePrompts.length; i++) {
                        if (i > 0) {
                            sb.append(" | ");
                        }
                        sb.append(this.mVoicePrompts[i]);
                    }
                }
            }
            sb.append('}');
            return sb.toString();
        }

        Prompt(Parcel parcel) {
            this.mVoicePrompts = parcel.readCharSequenceArray();
            this.mVisualPrompt = parcel.readCharSequence();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeCharSequenceArray(this.mVoicePrompts);
            parcel.writeCharSequence(this.mVisualPrompt);
        }
    }

    VoiceInteractor(IVoiceInteractor iVoiceInteractor, Context context, Activity activity, Looper looper) {
        HandlerCaller.Callback callback = new HandlerCaller.Callback() { // from class: android.app.VoiceInteractor.1
            @Override // com.android.internal.os.HandlerCaller.Callback
            public void executeMessage(Message message) {
                SomeArgs someArgs = (SomeArgs) message.obj;
                switch (message.what) {
                    case 1:
                        Request requestPullRequest = VoiceInteractor.this.pullRequest((IVoiceInteractorRequest) someArgs.arg1, true);
                        if (requestPullRequest != null) {
                            ((ConfirmationRequest) requestPullRequest).onConfirmationResult(message.arg1 != 0, (Bundle) someArgs.arg2);
                            requestPullRequest.clear();
                            break;
                        }
                        break;
                    case 2:
                        boolean z = message.arg1 != 0;
                        Request requestPullRequest2 = VoiceInteractor.this.pullRequest((IVoiceInteractorRequest) someArgs.arg1, z);
                        if (requestPullRequest2 != null) {
                            ((PickOptionRequest) requestPullRequest2).onPickOptionResult(z, (PickOptionRequest.Option[]) someArgs.arg2, (Bundle) someArgs.arg3);
                            if (z) {
                                requestPullRequest2.clear();
                                break;
                            }
                        }
                        break;
                    case 3:
                        Request requestPullRequest3 = VoiceInteractor.this.pullRequest((IVoiceInteractorRequest) someArgs.arg1, true);
                        if (requestPullRequest3 != null) {
                            ((CompleteVoiceRequest) requestPullRequest3).onCompleteResult((Bundle) someArgs.arg2);
                            requestPullRequest3.clear();
                            break;
                        }
                        break;
                    case 4:
                        Request requestPullRequest4 = VoiceInteractor.this.pullRequest((IVoiceInteractorRequest) someArgs.arg1, true);
                        if (requestPullRequest4 != null) {
                            ((AbortVoiceRequest) requestPullRequest4).onAbortResult((Bundle) someArgs.arg2);
                            requestPullRequest4.clear();
                            break;
                        }
                        break;
                    case 5:
                        boolean z2 = message.arg1 != 0;
                        Request requestPullRequest5 = VoiceInteractor.this.pullRequest((IVoiceInteractorRequest) someArgs.arg1, z2);
                        if (requestPullRequest5 != null) {
                            ((CommandRequest) requestPullRequest5).onCommandResult(message.arg1 != 0, (Bundle) someArgs.arg2);
                            if (z2) {
                                requestPullRequest5.clear();
                                break;
                            }
                        }
                        break;
                    case 6:
                        Request requestPullRequest6 = VoiceInteractor.this.pullRequest((IVoiceInteractorRequest) someArgs.arg1, true);
                        if (requestPullRequest6 != null) {
                            requestPullRequest6.onCancel();
                            requestPullRequest6.clear();
                            break;
                        }
                        break;
                }
            }
        };
        this.mHandlerCallerCallback = callback;
        this.mCallback = new IVoiceInteractorCallback.Stub() { // from class: android.app.VoiceInteractor.2
            @Override // com.android.internal.app.IVoiceInteractorCallback
            public void deliverConfirmationResult(IVoiceInteractorRequest iVoiceInteractorRequest, boolean z, Bundle bundle) {
                VoiceInteractor.this.mHandlerCaller.sendMessage(VoiceInteractor.this.mHandlerCaller.obtainMessageIOO(1, z ? 1 : 0, iVoiceInteractorRequest, bundle));
            }

            @Override // com.android.internal.app.IVoiceInteractorCallback
            public void deliverPickOptionResult(IVoiceInteractorRequest iVoiceInteractorRequest, boolean z, PickOptionRequest.Option[] optionArr, Bundle bundle) {
                VoiceInteractor.this.mHandlerCaller.sendMessage(VoiceInteractor.this.mHandlerCaller.obtainMessageIOOO(2, z ? 1 : 0, iVoiceInteractorRequest, optionArr, bundle));
            }

            @Override // com.android.internal.app.IVoiceInteractorCallback
            public void deliverCompleteVoiceResult(IVoiceInteractorRequest iVoiceInteractorRequest, Bundle bundle) {
                VoiceInteractor.this.mHandlerCaller.sendMessage(VoiceInteractor.this.mHandlerCaller.obtainMessageOO(3, iVoiceInteractorRequest, bundle));
            }

            @Override // com.android.internal.app.IVoiceInteractorCallback
            public void deliverAbortVoiceResult(IVoiceInteractorRequest iVoiceInteractorRequest, Bundle bundle) {
                VoiceInteractor.this.mHandlerCaller.sendMessage(VoiceInteractor.this.mHandlerCaller.obtainMessageOO(4, iVoiceInteractorRequest, bundle));
            }

            @Override // com.android.internal.app.IVoiceInteractorCallback
            public void deliverCommandResult(IVoiceInteractorRequest iVoiceInteractorRequest, boolean z, Bundle bundle) {
                VoiceInteractor.this.mHandlerCaller.sendMessage(VoiceInteractor.this.mHandlerCaller.obtainMessageIOO(5, z ? 1 : 0, iVoiceInteractorRequest, bundle));
            }

            @Override // com.android.internal.app.IVoiceInteractorCallback
            public void deliverCancel(IVoiceInteractorRequest iVoiceInteractorRequest) {
                VoiceInteractor.this.mHandlerCaller.sendMessage(VoiceInteractor.this.mHandlerCaller.obtainMessageOO(6, iVoiceInteractorRequest, null));
            }

            @Override // com.android.internal.app.IVoiceInteractorCallback
            public void destroy() {
                VoiceInteractor.this.mHandlerCaller.getHandler().sendMessage(PooledLambda.obtainMessage(new VoiceInteractor$2$$ExternalSyntheticLambda0(), VoiceInteractor.this));
            }
        };
        this.mActiveRequests = new ArrayMap<>();
        this.mOnDestroyCallbacks = new ArrayMap<>();
        this.mInteractor = iVoiceInteractor;
        this.mContext = context;
        this.mActivity = activity;
        this.mHandlerCaller = new HandlerCaller(context, looper, callback, true);
        try {
            this.mInteractor.setKillCallback(new KillCallback(this));
        } catch (RemoteException unused) {
        }
    }

    Request pullRequest(IVoiceInteractorRequest iVoiceInteractorRequest, boolean z) {
        Request request;
        synchronized (this.mActiveRequests) {
            request = this.mActiveRequests.get(iVoiceInteractorRequest.asBinder());
            if (request != null && z) {
                this.mActiveRequests.remove(iVoiceInteractorRequest.asBinder());
            }
        }
        return request;
    }

    private ArrayList<Request> makeRequestList() {
        int size = this.mActiveRequests.size();
        if (size < 1) {
            return null;
        }
        ArrayList<Request> arrayList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            arrayList.add(this.mActiveRequests.valueAt(i));
        }
        return arrayList;
    }

    void attachActivity(Activity activity) {
        this.mRetaining = false;
        if (this.mActivity == activity) {
            return;
        }
        this.mContext = activity;
        this.mActivity = activity;
        ArrayList<Request> arrayListMakeRequestList = makeRequestList();
        if (arrayListMakeRequestList != null) {
            for (int i = 0; i < arrayListMakeRequestList.size(); i++) {
                Request request = arrayListMakeRequestList.get(i);
                request.mContext = activity;
                request.mActivity = activity;
                request.onAttached(activity);
            }
        }
    }

    void retainInstance() {
        this.mRetaining = true;
    }

    void detachActivity() {
        ArrayList<Request> arrayListMakeRequestList = makeRequestList();
        if (arrayListMakeRequestList != null) {
            for (int i = 0; i < arrayListMakeRequestList.size(); i++) {
                Request request = arrayListMakeRequestList.get(i);
                request.onDetached();
                request.mActivity = null;
                request.mContext = null;
            }
        }
        if (!this.mRetaining) {
            ArrayList<Request> arrayListMakeRequestList2 = makeRequestList();
            if (arrayListMakeRequestList2 != null) {
                for (int i2 = 0; i2 < arrayListMakeRequestList2.size(); i2++) {
                    arrayListMakeRequestList2.get(i2).cancel();
                }
            }
            this.mActiveRequests.clear();
        }
        this.mContext = null;
        this.mActivity = null;
    }

    void destroy() {
        for (int size = this.mActiveRequests.size() - 1; size >= 0; size--) {
            Request requestValueAt = this.mActiveRequests.valueAt(size);
            this.mActiveRequests.removeAt(size);
            requestValueAt.cancel();
        }
        for (int size2 = this.mOnDestroyCallbacks.size() - 1; size2 >= 0; size2--) {
            this.mOnDestroyCallbacks.valueAt(size2).execute(this.mOnDestroyCallbacks.keyAt(size2));
            this.mOnDestroyCallbacks.removeAt(size2);
        }
        this.mInteractor = null;
        Activity activity = this.mActivity;
        if (activity != null) {
            activity.setVoiceInteractor(null);
        }
    }

    public boolean submitRequest(Request request) {
        return submitRequest(request, null);
    }

    public boolean submitRequest(Request request, String str) {
        if (isDestroyed()) {
            Log.w(TAG, "Cannot interact with a destroyed voice interactor");
            return false;
        }
        try {
            if (request.mRequestInterface != null) {
                throw new IllegalStateException("Given " + request + " is already active");
            }
            IVoiceInteractorRequest iVoiceInteractorRequestSubmit = request.submit(this.mInteractor, this.mContext.getOpPackageName(), this.mCallback);
            request.mRequestInterface = iVoiceInteractorRequestSubmit;
            request.mContext = this.mContext;
            request.mActivity = this.mActivity;
            request.mName = str;
            synchronized (this.mActiveRequests) {
                this.mActiveRequests.put(iVoiceInteractorRequestSubmit.asBinder(), request);
            }
            return true;
        } catch (RemoteException e) {
            Log.w(TAG, "Remove voice interactor service died", e);
            return false;
        }
    }

    public Request[] getActiveRequests() {
        if (isDestroyed()) {
            Log.w(TAG, "Cannot interact with a destroyed voice interactor");
            return null;
        }
        synchronized (this.mActiveRequests) {
            int size = this.mActiveRequests.size();
            if (size <= 0) {
                return NO_REQUESTS;
            }
            Request[] requestArr = new Request[size];
            for (int i = 0; i < size; i++) {
                requestArr[i] = this.mActiveRequests.valueAt(i);
            }
            return requestArr;
        }
    }

    public Request getActiveRequest(String str) {
        int i;
        if (isDestroyed()) {
            Log.w(TAG, "Cannot interact with a destroyed voice interactor");
            return null;
        }
        synchronized (this.mActiveRequests) {
            int size = this.mActiveRequests.size();
            while (i < size) {
                Request requestValueAt = this.mActiveRequests.valueAt(i);
                i = (str != requestValueAt.getName() && (str == null || !str.equals(requestValueAt.getName()))) ? i + 1 : 0;
                return requestValueAt;
            }
            return null;
        }
    }

    public boolean[] supportsCommands(String[] strArr) {
        if (isDestroyed()) {
            Log.w(TAG, "Cannot interact with a destroyed voice interactor");
            return new boolean[strArr.length];
        }
        try {
            return this.mInteractor.supportsCommands(this.mContext.getOpPackageName(), strArr);
        } catch (RemoteException e) {
            throw new RuntimeException("Voice interactor has died", e);
        }
    }

    public boolean isDestroyed() {
        return this.mInteractor == null;
    }

    public boolean registerOnDestroyedCallback(Executor executor, Runnable runnable) {
        Objects.requireNonNull(executor);
        Objects.requireNonNull(runnable);
        if (isDestroyed()) {
            Log.w(TAG, "Cannot interact with a destroyed voice interactor");
            return false;
        }
        this.mOnDestroyCallbacks.put(runnable, executor);
        return true;
    }

    public boolean unregisterOnDestroyedCallback(Runnable runnable) {
        Objects.requireNonNull(runnable);
        if (!isDestroyed()) {
            return this.mOnDestroyCallbacks.remove(runnable) != null;
        }
        Log.w(TAG, "Cannot interact with a destroyed voice interactor");
        return false;
    }

    public void notifyDirectActionsChanged() {
        if (isDestroyed()) {
            Log.w(TAG, "Cannot interact with a destroyed voice interactor");
            return;
        }
        try {
            this.mInteractor.notifyDirectActionsChanged(this.mActivity.getTaskId(), this.mActivity.getAssistToken());
        } catch (RemoteException e) {
            Log.w(TAG, "Voice interactor has died", e);
        }
    }

    public String getPackageName() {
        String voiceInteractorPackageName;
        if (this.mActivity == null || this.mInteractor == null) {
            voiceInteractorPackageName = null;
        } else {
            try {
                voiceInteractorPackageName = ActivityTaskManager.getService().getVoiceInteractorPackageName(this.mInteractor.asBinder());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return voiceInteractorPackageName == null ? "" : voiceInteractorPackageName;
    }

    void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        String str2 = str + "    ";
        if (this.mActiveRequests.size() > 0) {
            printWriter.print(str);
            printWriter.println("Active voice requests:");
            for (int i = 0; i < this.mActiveRequests.size(); i++) {
                Request requestValueAt = this.mActiveRequests.valueAt(i);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i);
                printWriter.print(": ");
                printWriter.println(requestValueAt);
                requestValueAt.dump(str2, fileDescriptor, printWriter, strArr);
            }
        }
        printWriter.print(str);
        printWriter.println("VoiceInteractor misc state:");
        printWriter.print(str);
        printWriter.print("  mInteractor=");
        printWriter.println(this.mInteractor.asBinder());
        printWriter.print(str);
        printWriter.print("  mActivity=");
        printWriter.println(this.mActivity);
    }

    private static final class KillCallback extends ICancellationSignal.Stub {
        private final WeakReference<VoiceInteractor> mInteractor;

        KillCallback(VoiceInteractor voiceInteractor) {
            this.mInteractor = new WeakReference<>(voiceInteractor);
        }

        @Override // android.os.ICancellationSignal
        public void cancel() {
            VoiceInteractor voiceInteractor = this.mInteractor.get();
            if (voiceInteractor != null) {
                voiceInteractor.mHandlerCaller.getHandler().sendMessage(PooledLambda.obtainMessage(new VoiceInteractor$2$$ExternalSyntheticLambda0(), voiceInteractor));
            }
        }
    }
}
