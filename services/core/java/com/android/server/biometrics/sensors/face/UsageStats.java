package com.android.server.biometrics.sensors.face;

import android.content.Context;
import android.hardware.face.FaceManager;
import android.util.SparseIntArray;
import android.util.SparseLongArray;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.Iterator;

/* loaded from: classes.dex */
public class UsageStats {
    public int mAcceptCount;
    public long mAcceptLatency;
    public int mAuthAttemptCount;
    public Context mContext;
    public int mErrorCount;
    public long mErrorLatency;
    public int mRejectCount;
    public long mRejectLatency;
    public ArrayDeque mAuthenticationEvents = new ArrayDeque();
    public SparseIntArray mErrorFrequencyMap = new SparseIntArray();
    public SparseLongArray mErrorLatencyMap = new SparseLongArray();

    public final class AuthenticationEvent {
        public boolean mAuthenticated;
        public int mError;
        public long mLatency;
        public long mStartTime;
        public int mUser;
        public int mVendorError;

        public AuthenticationEvent(long j, long j2, boolean z, int i, int i2, int i3) {
            this.mStartTime = j;
            this.mLatency = j2;
            this.mAuthenticated = z;
            this.mError = i;
            this.mVendorError = i2;
            this.mUser = i3;
        }

        public String toString(Context context) {
            StringBuilder sb = new StringBuilder();
            sb.append("Start: ");
            sb.append(this.mStartTime);
            sb.append("\tLatency: ");
            sb.append(this.mLatency);
            sb.append("\tAuthenticated: ");
            sb.append(this.mAuthenticated);
            sb.append("\tError: ");
            sb.append(this.mError);
            sb.append("\tVendorCode: ");
            sb.append(this.mVendorError);
            sb.append("\tUser: ");
            sb.append(this.mUser);
            sb.append("\t");
            int i = this.mError;
            sb.append(i == 0 ? "" : FaceManager.getErrorString(context, i, this.mVendorError));
            return sb.toString();
        }
    }

    public UsageStats(Context context) {
        this.mContext = context;
    }

    public void addEvent(AuthenticationEvent authenticationEvent) {
        this.mAuthAttemptCount++;
        if (this.mAuthenticationEvents.size() >= 100) {
            this.mAuthenticationEvents.removeFirst();
        }
        this.mAuthenticationEvents.add(authenticationEvent);
        if (authenticationEvent.mAuthenticated) {
            this.mAcceptCount++;
            this.mAcceptLatency += authenticationEvent.mLatency;
        } else if (authenticationEvent.mError == 0) {
            this.mRejectCount++;
            this.mRejectLatency += authenticationEvent.mLatency;
        } else {
            this.mErrorCount++;
            this.mErrorLatency += authenticationEvent.mLatency;
            this.mErrorFrequencyMap.put(authenticationEvent.mError, this.mErrorFrequencyMap.get(authenticationEvent.mError, 0) + 1);
            this.mErrorLatencyMap.put(authenticationEvent.mError, this.mErrorLatencyMap.get(authenticationEvent.mError, 0L) + authenticationEvent.mLatency);
        }
    }

    public void print(PrintWriter printWriter) {
        printWriter.println("Printing most recent events since last reboot(" + this.mAuthenticationEvents.size() + " events)");
        Iterator it = this.mAuthenticationEvents.iterator();
        while (it.hasNext()) {
            printWriter.println(((AuthenticationEvent) it.next()).toString(this.mContext));
        }
        printWriter.println("");
        StringBuilder sb = new StringBuilder();
        sb.append("Accept Count: ");
        sb.append(this.mAcceptCount);
        sb.append("\tLatency: ");
        sb.append(this.mAcceptLatency);
        sb.append("\tAverage: ");
        int i = this.mAcceptCount;
        sb.append(i > 0 ? this.mAcceptLatency / i : 0L);
        printWriter.println(sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Reject Count: ");
        sb2.append(this.mRejectCount);
        sb2.append("\tLatency: ");
        sb2.append(this.mRejectLatency);
        sb2.append("\tAverage: ");
        int i2 = this.mRejectCount;
        sb2.append(i2 > 0 ? this.mRejectLatency / i2 : 0L);
        printWriter.println(sb2.toString());
        StringBuilder sb3 = new StringBuilder();
        sb3.append("Total Error Count: ");
        sb3.append(this.mErrorCount);
        sb3.append("\tLatency: ");
        sb3.append(this.mErrorLatency);
        sb3.append("\tAverage: ");
        int i3 = this.mErrorCount;
        sb3.append(i3 > 0 ? this.mErrorLatency / i3 : 0L);
        printWriter.println(sb3.toString());
        printWriter.println("Total Attempts: " + this.mAuthAttemptCount);
        printWriter.println("");
        for (int i4 = 0; i4 < this.mErrorFrequencyMap.size(); i4++) {
            int keyAt = this.mErrorFrequencyMap.keyAt(i4);
            int i5 = this.mErrorFrequencyMap.get(keyAt);
            StringBuilder sb4 = new StringBuilder();
            sb4.append("Error");
            sb4.append(keyAt);
            sb4.append("\tCount: ");
            sb4.append(i5);
            sb4.append("\tLatency: ");
            sb4.append(this.mErrorLatencyMap.get(keyAt, 0L));
            sb4.append("\tAverage: ");
            sb4.append(i5 > 0 ? this.mErrorLatencyMap.get(keyAt, 0L) / i5 : 0L);
            sb4.append("\t");
            sb4.append(FaceManager.getErrorString(this.mContext, keyAt, 0));
            printWriter.println(sb4.toString());
        }
    }
}
