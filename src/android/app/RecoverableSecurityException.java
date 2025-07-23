package android.app;

import android.app.AlertDialog;
import android.app.Notification;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.R;
import java.util.Objects;

/* loaded from: classes.dex */
public final class RecoverableSecurityException extends SecurityException implements Parcelable {
    public static final Parcelable.Creator<RecoverableSecurityException> CREATOR = new Parcelable.Creator<RecoverableSecurityException>() { // from class: android.app.RecoverableSecurityException.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RecoverableSecurityException createFromParcel(Parcel parcel) {
            return new RecoverableSecurityException(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RecoverableSecurityException[] newArray(int i) {
            return new RecoverableSecurityException[i];
        }
    };
    private static final String TAG = "RecoverableSecurityException";
    private final RemoteAction mUserAction;
    private final CharSequence mUserMessage;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public RecoverableSecurityException(Parcel parcel) {
        this(new SecurityException(parcel.readString()), parcel.readCharSequence(), RemoteAction.CREATOR.createFromParcel(parcel));
    }

    public RecoverableSecurityException(Throwable th, CharSequence charSequence, RemoteAction remoteAction) {
        super(th.getMessage());
        this.mUserMessage = (CharSequence) Objects.requireNonNull(charSequence);
        this.mUserAction = (RemoteAction) Objects.requireNonNull(remoteAction);
    }

    public CharSequence getUserMessage() {
        return this.mUserMessage;
    }

    public RemoteAction getUserAction() {
        return this.mUserAction;
    }

    public void showAsNotification(Context context, String str) {
        ((NotificationManager) context.getSystemService(NotificationManager.class)).notify(TAG, this.mUserAction.getActionIntent().getCreatorUid(), new Notification.Builder(context, str).setSmallIcon(R.drawable.ic_print_error).setContentTitle(this.mUserAction.getTitle()).setContentText(this.mUserMessage).setContentIntent(this.mUserAction.getActionIntent()).setCategory(Notification.CATEGORY_ERROR).build());
    }

    public void showAsDialog(Activity activity) {
        LocalDialog localDialog = new LocalDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable(TAG, this);
        localDialog.setArguments(bundle);
        String str = "RecoverableSecurityException_" + this.mUserAction.getActionIntent().getCreatorUid();
        FragmentManager fragmentManager = activity.getFragmentManager();
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        Fragment findFragmentByTag = fragmentManager.findFragmentByTag(str);
        if (findFragmentByTag != null) {
            beginTransaction.remove(findFragmentByTag);
        }
        beginTransaction.add(localDialog, str);
        beginTransaction.commitAllowingStateLoss();
    }

    public static class LocalDialog extends DialogFragment {
        @Override // android.app.DialogFragment
        public Dialog onCreateDialog(Bundle bundle) {
            final RecoverableSecurityException recoverableSecurityException = (RecoverableSecurityException) getArguments().getParcelable(RecoverableSecurityException.TAG, RecoverableSecurityException.class);
            return new AlertDialog.Builder(getActivity()).setMessage(recoverableSecurityException.mUserMessage).setPositiveButton(recoverableSecurityException.mUserAction.getTitle(), new DialogInterface.OnClickListener() { // from class: android.app.RecoverableSecurityException$LocalDialog$$ExternalSyntheticLambda0
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    RecoverableSecurityException.this.mUserAction.getActionIntent().send();
                }
            }).setNegativeButton(17039360, (DialogInterface.OnClickListener) null).create();
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getMessage());
        parcel.writeCharSequence(this.mUserMessage);
        this.mUserAction.writeToParcel(parcel, i);
    }
}
