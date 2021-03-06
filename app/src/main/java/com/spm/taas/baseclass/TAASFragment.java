package com.spm.taas.baseclass;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.spm.taas.R;
import com.spm.taas.customview.TextViewIkarosRegular;

import java.util.LinkedList;

/**
 * Created by saikatpakira on 12/10/16.
 */

public class TAASFragment extends Fragment {

    private AlertDialog alertDialog = null;
    private LinkedList<AsyncTask> taskQueue = null;

    public void showError(final String title, final String message) {
        new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(message)
                .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


    public void showProgress() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dilaog_loder, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);
        alertDialog = dialogBuilder.create();
        alertDialog.show();

    }

    public void showProgress(final String message) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dilaog_loder, null);
        ((TextViewIkarosRegular) dialogView.findViewById(R.id.progress_text)).setText(message);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);
        alertDialog = dialogBuilder.create();
        alertDialog.show();

    }


    public void hideProgress() {
        alertDialog.dismiss();
    }

    @Override
    public void onStop() {
        super.onStop();

        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    public void addToThreadPool(final AsyncTask task_) {
        if (taskQueue == null) {
            taskQueue = new LinkedList<AsyncTask>();
        }
        taskQueue.add(task_);
        task_.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (taskQueue != null) {
            for (AsyncTask task_ : taskQueue) {
                task_.cancel(true);
            }
        }

    }
}
