package com.example.nontrivialapi;

import static com.google.ar.core.ArCoreApk.InstallStatus.INSTALLED;
import static com.google.ar.core.ArCoreApk.InstallStatus.INSTALL_REQUESTED;

import androidx.appcompat.app.AppCompatActivity;
import com.example.nontrivialapi.CameraPermissionHelper;
import com.google.ar.core.ArCoreApk;
import com.google.ar.core.Session;
import com.google.ar.core.exceptions.UnavailableApkTooOldException;
import com.google.ar.core.exceptions.UnavailableArcoreNotInstalledException;
import com.google.ar.core.exceptions.UnavailableDeviceNotCompatibleException;
import com.google.ar.core.exceptions.UnavailableSdkTooOldException;
import com.google.ar.core.exceptions.UnavailableUserDeclinedInstallationException;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        maybeEnableArButton();
    }

    //Function was included with arcore
    //Checking if ARcore is supported on the current device
    void maybeEnableArButton() {
        ArCoreApk.Availability availability = ArCoreApk.getInstance().checkAvailability(this);
        if (availability.isTransient()) {
            // Continue to query availability at 5Hz while compatibility is checked in the background.
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    maybeEnableArButton();
                }
            }, 200);
        }
//        View mArButton = null;
//        if (availability.isSupported()) {
//            mArButton.setVisibility(View.VISIBLE);
//            mArButton.setEnabled(true);
//        } else { // The device is unsupported or unknown.
//            mArButton.setVisibility(View.INVISIBLE);
//            mArButton.setEnabled(false);
//        }
    }

    private boolean mUserRequestedInstall = true;
    Session mSession;
    @Override
    protected void onResume() {
        super.onResume();
        //Checking Camera Permissions
        if (!CameraPermissionHelper.hasCameraPermission(this)) {
            CameraPermissionHelper.requestCameraPermission(this);
            return;
        }
        //Making sure all required services are installed and up to date
        try {
            if (mSession == null) {
                switch (ArCoreApk.getInstance().requestInstall(this, mUserRequestedInstall)) {
                    case INSTALLED:
                        mSession = new Session(this);
                        break;
                    case INSTALL_REQUESTED:
                        mUserRequestedInstall = false;
                        return;
                }
            }
        }
        catch(UnavailableUserDeclinedInstallationException | UnavailableDeviceNotCompatibleException e){
            Toast.makeText(this,"Exception"+e,Toast.LENGTH_LONG);
            return;
        } catch (UnavailableArcoreNotInstalledException e) {
            e.printStackTrace();
        } catch (UnavailableSdkTooOldException e) {
            e.printStackTrace();
        } catch (UnavailableApkTooOldException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] results) {
        super.onRequestPermissionsResult(requestCode, permissions, results);
        if (!CameraPermissionHelper.hasCameraPermission(this)) {
            Toast.makeText(this, "Camera permission is needed to run this application", Toast.LENGTH_LONG)
                    .show();
            if (!CameraPermissionHelper.shouldShowRequestPermissionRationale(this)) {
                // Permission denied with checking "Do not ask again".
                CameraPermissionHelper.launchPermissionSettings(this);
            }
            finish();
        }
    }
}

//metehan
//switched to mac/xcode/swift this week to test it out
//Still learning the ropes of xcode, so hasn't made much progress towards his app. Has been working on relearning the basics and hasn't started working on the api portion of the app yet