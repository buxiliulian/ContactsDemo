package com.bxll.contactsdemo.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

/**
 * Created by David Chow on 2019-12-17.
 */
public class RationaleDialogFragment extends DialogFragment {
    private Callback mCallback;
    public interface Callback {
        void onPass();
    }

    private static final String ARGS_TITLE = "args_title";
    private static final String ARGS_MSG = "args_msg";

    public RationaleDialogFragment() {}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mCallback = (Callback) context;
    }

    public static RationaleDialogFragment newInstance(String title, String msg) {
        RationaleDialogFragment fragment = new RationaleDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARGS_TITLE, title);
        bundle.putString(ARGS_MSG, msg);
        fragment.setArguments(bundle);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        String title = arguments.getString(ARGS_TITLE, "");
        String msg = arguments.getString(ARGS_MSG, "");
        return new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mCallback != null) {
                            mCallback.onPass();
                        }
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }
}
